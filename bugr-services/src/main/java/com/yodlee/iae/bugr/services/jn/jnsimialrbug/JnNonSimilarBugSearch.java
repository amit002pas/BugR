/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.jn.jnsimialrbug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.datatypes.bugs.Criteria;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SyntheticSearchBugsResponse;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.search.cache.JNBugsCache;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

/**
 * @author karthik
 *
 */
@Named
@Scope("prototype")
public class JnNonSimilarBugSearch implements ServiceIO<BugSearchParam, SyntheticSearchBugsResponse> {

	@Inject
	private JNBugsCache jnBugsCache;

	@Inject
	private SyntheticSearchBugsResponse searchBugsResponse;
	
	@Inject
	private SynUtil synUtil;

	private BugSearchParam bugSearchParam;

	private boolean isStepSuccess;

	private boolean isCSVRequired;

	public static final String DATE_FORMAT = "E MMM dd HH:mm:ss z yyyy";

	@Override
	public void accept(BugSearchParam obj) {
		this.bugSearchParam = obj;
	}

	@Override
	public SyntheticSearchBugsResponse get() {
		return searchBugsResponse;
	}

	@Override
	public void mapInput() {
		isStepSuccess = false;
		isCSVRequired = false;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeImpl() {

		if (Criteria.JUGGERNAUT_HARDERROR.equals(bugSearchParam.getFilters().getCriteria())) {
			List<SyntheticBug> synBugList = jnBugsCache.getHardErrorJNBugs().get();
			searchBugsResponse.setBugCount(synBugList.size());
			synBugList = applySortandPagination(synBugList);
			if (!isCSVRequired) {
				synBugList.stream().forEach(SynUtil::convertAttachmentsToBase64);
				searchBugsResponse.setBugs(synBugList);
			} else {
				List<Map<String, String>> finalList = synUtil.getDataForCSV(synBugList);
				searchBugsResponse.setCsvDetails(finalList);
			}
			setAllUniqueAgentNamesAndErrorCodes(synBugList);
			isStepSuccess = true;
		} else if (Criteria.JUGGERNAUT_DQERROR.equals(bugSearchParam.getFilters().getCriteria())) {
			List<SyntheticBug> synBugList = jnBugsCache.getDQJNBugs().get();
			searchBugsResponse.setBugCount(synBugList.size());
			synBugList = applySortandPagination(synBugList);
			if (!isCSVRequired) {
				synBugList.stream().forEach(SynUtil::convertAttachmentsToBase64);
				searchBugsResponse.setBugs(synBugList);
			} else {
				List<Map<String, String>> finalList = synUtil.getDataForCSV(synBugList);
				searchBugsResponse.setCsvDetails(finalList);
			}
			setAllUniqueAgentNamesAndErrorCodes(synBugList);
			isStepSuccess = true;
		} else if ((Criteria.JUGGERNAUT_NONSIMILAR).equals(bugSearchParam.getFilters().getCriteria())) {
			List<SyntheticBug> allNonSimilarJn = new ArrayList<>();
			allNonSimilarJn.addAll(jnBugsCache.getHardErrorJNBugs().get());
			allNonSimilarJn.addAll(jnBugsCache.getDQJNBugs().get());
			searchBugsResponse.setBugCount(allNonSimilarJn.size());
			allNonSimilarJn = applySortandPagination(allNonSimilarJn);
			if (!isCSVRequired) {
				allNonSimilarJn.stream().forEach(SynUtil::convertAttachmentsToBase64);
				searchBugsResponse.setBugs(allNonSimilarJn);
			} else {
				List<Map<String, String>> finalList = synUtil.getDataForCSV(allNonSimilarJn);
				searchBugsResponse.setCsvDetails(finalList);
			}
			setAllUniqueAgentNamesAndErrorCodes(allNonSimilarJn);
			isStepSuccess = true;
		}
	}

	/**
	 * Sort filter and pagination handling
	 * 
	 * @param synBugList
	 * @return
	 */
	private List<SyntheticBug> applySortandPagination(List<SyntheticBug> synBugList) {

		Comparator<SyntheticBug> compare = (s1, s2) -> {
			int sort = 1;
			if (BugSortBy.LAST_CHANGE_TIME.equals(bugSearchParam.getSort().getCategory())) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
				try {
					sort = LocalDateTime.parse(s1.getBugFields().getLast_change_time(), formatter)
							.compareTo(LocalDateTime.parse(s2.getBugFields().getLast_change_time(), formatter));
				} catch (Exception e) {
					sort = 1;
				}
			}
			if (BugSortOrder.DESCENDING.equals(bugSearchParam.getSort().getOrder()))
				sort = sort * -1;
			return sort;
		};
		synBugList = synBugList.stream().sorted(compare).collect(Collectors.toList());
		if (bugSearchParam.getPageNum() != -1) {
			synBugList = synBugList.stream().skip((long) 10 * (bugSearchParam.getPageNum() - 1)).limit(10)
					.collect(Collectors.toList());
		} else {
			isCSVRequired = true;
		}

		return synBugList;
	}

	private void setAllUniqueAgentNamesAndErrorCodes(List<SyntheticBug> synBugList) {
		Set<String> agentNames = new HashSet<>();
		Set<String> errorCodes = new HashSet<>();
		synBugList.stream().forEach(synBug -> {
			agentNames.add(synBug.getBugFields().getAgentName());
			errorCodes.add(synBug.getBugFields().getErrorcode());
		});
		searchBugsResponse.setAgentValues(agentNames);
		searchBugsResponse.setErrorCodeValues(errorCodes);
	}

	@Override
	public void mapOutput() {
		if (isStepSuccess) {
			searchBugsResponse.setStatus(ResponseConstants.STATUS_SUCCESS);
		} else {
			searchBugsResponse.setStatus(ResponseConstants.STATUS_FAILURE);
		}
	}

}
