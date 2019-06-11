/**
 *Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms. 
 */
package com.yodlee.iae.bugr.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class SyntheticBugRepository {

	private static final String CREATED_DATE = "createdDate";
	private static final String UPDATED_DATE = "updatedDate";

	private static final String ERROR_CODE = "bugFields.errorcode";
	private static final String SUM_INFO_ID = "bugFields.suminfo";
	private static final String AGENT_NAME = "bugFields.agentName";

	private static final String WHITEBOARD = "bugFields.whiteboard";
	private static final String KEYWORDS = "bugFields.keywords";
	private static final String SITEID = "bugFields.siteId";
	private static final String SUMMARY = "bugFields.summary";
	private static final String BUGFIELD_WORKFLOWSTATUS = "bugFields.workflowStatus";
	private static final String BUGFIELDS_BUGSTATUS = "bugFields.bugStatus";
	public static final String CLOSED = "Closed";
	public static final String DOOT = "Dependent on other teams";
	public static final String DUPLICATE_ALREADY_FIXED = "Duplicate - Already Fixed";
	public static final String REASSIGNED = "Reassigned";
	public static final String RESOLVED = "RESOLVED";

	private static final String SYN_STATUS = "syntheticFields.syntheticBugStatus";
	private static final String PORTFOLIO = "syntheticFields.portfolio";
	private static final String SEGMENT_ID = "syntheticFields.segmentId";
	private static final String ACTION = "syntheticFields.action";
	private static final String BUGZILLA_BUG_ID = "syntheticFields.bugzillaBugId";
	private static final String CUSIP = "syntheticFields.cusip";
	private static final String SYMBOL = "syntheticFields.symbol";

	private static final String PRESR_ERRORSEGMENT = "PreSR_ErrorSegment";

	private static final Predicate<String> strPred = str -> str != null && !str.isEmpty();
	private static final Predicate<List<String>> listPred = list -> list != null && !list.isEmpty();

	public static Optional<String> validateStr(String str) {
		return strPred.test(str) ? Optional.ofNullable(str) : Optional.ofNullable(null);
	}

	public static Optional<List<String>> validateList(List<String> list) {
		return listPred.test(list) ? Optional.ofNullable(list) : Optional.ofNullable(null);
	}

	@Inject
	private MongoOperations mongoOperations;

	public List<SyntheticBug> getSynBugsFromBugzillaIds(List<Integer> bugIds) {
		Query query = new Query();
		query.addCriteria(Criteria.where(BUGZILLA_BUG_ID).in(bugIds));
		return mongoOperations.find(query, SyntheticBug.class);
	}

	public SyntheticBug getSyntheticBugFromBugzillaId(Integer bugId) {
		return mongoOperations.findOne(new Query().addCriteria(Criteria.where(BUGZILLA_BUG_ID).is(bugId)),
				SyntheticBug.class);
	}

	public SyntheticBug findDuplicateReconServicesBugs(String sumInfoId, String errorCode, String segID,
			String action) {
		Query query = new Query();
		Criteria criteria = Criteria.where(SUM_INFO_ID).is(sumInfoId).and(ERROR_CODE).is(errorCode).and(SEGMENT_ID)
				.is(segID);
		if (null != action) {
			criteria.and(ACTION).is(action);
		} else {
			criteria.and(ACTION).exists(false);
		}
		criteria.and(SYN_STATUS)
				.nin(Arrays.asList(SyntheticBugStatus.INACTIVE.name(), SyntheticBugStatus.CLOSED.name()));
		query.addCriteria(criteria);
		return mongoOperations.findOne(query, SyntheticBug.class);
	}

	public SyntheticBug findDuplicateReconHoldingBugs(String cusip, String symbol) {
		Query query = new Query();
		query.addCriteria(Criteria.where(CUSIP).is(cusip).and(SYMBOL).is(symbol).and(SYN_STATUS)
				.nin(Arrays.asList(SyntheticBugStatus.INACTIVE.name(), SyntheticBugStatus.CLOSED.name())));
		return mongoOperations.findOne(query, SyntheticBug.class);
	}

	public List<SyntheticBug> getDuplicatePreSrBug(String agentName, String errorCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where(AGENT_NAME).is(agentName).and(ERROR_CODE).is(errorCode).and(WHITEBOARD)
				.regex(Pattern.compile(PRESR_ERRORSEGMENT)).and(SYN_STATUS)
				.nin(Arrays.asList(SyntheticBugStatus.INACTIVE.name(), SyntheticBugStatus.CLOSED.name())));
		return mongoOperations.find(query, SyntheticBug.class);
	}

	public List<SyntheticBug> searchBugs(BugSearchParam param) {
		Criteria criteria;
		if (null != param.getFilters() && param.getFilters().getSourceProduct() != null)
			criteria = Criteria.where(PORTFOLIO).is(param.getFilters().getSourceProduct().name());
		else
			criteria = Criteria.where("someField").exists(false);
		setBasicSearchCriteria(criteria, param);
		return mongoOperations.find(new Query().addCriteria(criteria), SyntheticBug.class);
	}

	private void setBasicSearchCriteria(Criteria criteria, BugSearchParam param) {
		Optional.ofNullable(param.getFilters()).ifPresent(filters -> {
			validateList(filters.getAgentNames()).ifPresent(x -> criteria.and(AGENT_NAME).in(x));
			validateList(filters.getErrorCodes()).ifPresent(x -> criteria.and(ERROR_CODE).in(x));

			validateStr(filters.getSiteid()).ifPresent(x -> criteria.and(SITEID).is(x));

			validateStr(filters.getWhiteboard()).ifPresent(x -> criteria.and(WHITEBOARD).regex(Pattern.compile(x)));
			validateStr(filters.getKeyword()).ifPresent(x -> criteria.and(KEYWORDS).regex(Pattern.compile(x)));
			validateStr(filters.getSummary()).ifPresent(x -> criteria.and(SUMMARY).regex(Pattern.compile(x)));
		});
		if (param.getTimeStamp() != null && param.getTimeStamp().getStartTime() != null
				&& param.getTimeStamp().getEndTime() != null) {
			criteria.and(UPDATED_DATE).gt(param.getTimeStamp().getStartTime()).lt(param.getTimeStamp().getEndTime());
		}
		criteria.and(SYN_STATUS)
				.nin(Arrays.asList(SyntheticBugStatus.INACTIVE.name(), SyntheticBugStatus.CLOSED.name()));
	}

	public List<SyntheticBug> searchClosedBugs(BugSearchParam param) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (param.getTimeStamp() != null && param.getTimeStamp().getStartTime() != null
				&& param.getTimeStamp().getEndTime() != null) {
			criteria.and(UPDATED_DATE).gt(param.getTimeStamp().getStartTime()).lt(param.getTimeStamp().getEndTime());
		}
		criteria.orOperator(Criteria.where(BUGFIELDS_BUGSTATUS).is(RESOLVED),
				Criteria.where(BUGFIELD_WORKFLOWSTATUS).in(CLOSED, DOOT, DUPLICATE_ALREADY_FIXED, REASSIGNED));
		query.addCriteria(criteria);
		return mongoOperations.find(query, SyntheticBug.class);
	}

}
