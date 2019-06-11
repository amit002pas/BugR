/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.mapper.Mapper;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class UpdateBugzillaBug implements ServiceIO<String, SynBugResponse> {

	@Inject
	private MongoOperations mongoOperations;

	@Inject
	private IBugzillaRPCClient bugzillaGateway;

	private static final Logger LOG = LoggerFactory.getLogger(UpdateBugzillaBug.class);

	private String syntheticBugId;
	private Optional<SyntheticBug> syntheticBugOpt;
	private Integer bugzillaBugId;
	private String failureMessage;
	private boolean isStepSuccess;
	private SynBugResponse sbr;

	public static final String CLOSED = "Closed";
	public static final String DOOT = "Dependent on other teams";
	public static final String DUPLICATE_ALREADY_FIXED = "Duplicate - Already Fixed";
	public static final String REASSIGNED = "Reassigned";
	public static final String RESOLVED = "RESOLVED";

	@Override
	public void accept(String syntheticBugId) {
		this.syntheticBugId = syntheticBugId;
		syntheticBugOpt = Optional.empty();
	}

	@Override
	public SynBugResponse get() {
		return sbr;
	}

	@Override
	public void executeImpl() {
		syntheticBugOpt.ifPresent(sB -> {
			
			Map<String, Object> bugProps = Mapper.mapSyntheticToBugzilla(sB);
			removeBugFields(sB, bugProps);
			bugProps.put(BugzillaConstants.ID, sB.getSyntheticFields().getBugzillaBugId());
			updateCommentField(bugProps);
			updateDependsonField(bugProps);
			try {
				bugzillaGateway.updateBug(bugProps, null);
				isStepSuccess = true;
				bugzillaBugId = sB.getSyntheticFields().getBugzillaBugId();
				if (SyntheticBugStatus.INVALID.equals(sB.getSyntheticFields().getSyntheticBugStatus())) {
					sB.getSyntheticFields().setSyntheticBugStatus(SyntheticBugStatus.ACTIVE);
				}
			} catch (Exception e) {
				failureMessage = e.getMessage();
				isStepSuccess = false;
				sB.getSyntheticFields().setSyntheticBugStatus(SyntheticBugStatus.INVALID);
				sB.getSyntheticFields().setFailureMessage(failureMessage);
				LOG.debug("Bug Not created for " + sB.getSyntheticBugid());
			}
			mongoOperations.save(sB);
		});
	}

	private void updateDependsonField(Map<String, Object> bugProps) {
		if (null != bugProps.get(BugzillaConstants.DEPENDS_ON)) {
			String bugValues = bugProps.get(BugzillaConstants.DEPENDS_ON).toString();
			List<Integer> listOfBugs = Arrays.stream(bugValues.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());
			HashMap<String, Object> dependsonMap = new HashMap<>();
			dependsonMap.put(BugzillaConstants.SET, listOfBugs);
			bugProps.put(BugzillaConstants.DEPENDS_ON, dependsonMap);
		}
	}

	private void updateCommentField(Map<String, Object> bugProps) {
		if (null != bugProps.get(BugzillaConstants.COMMENT)) {
			String comment = bugProps.get(BugzillaConstants.COMMENT).toString();
			HashMap<String, Object> commentMap = new HashMap<>();
			commentMap.put(BugzillaConstants.BODY, comment);
			commentMap.put(BugzillaConstants.IS_PRIVATE, false);
			bugProps.put(BugzillaConstants.COMMENT, commentMap);
		}
	}

	private void removeBugFields(SyntheticBug sB, Map<String, Object> bugProps) {
		if (!(bugProps.get(BugzillaConstants.CF_WORKFLOW_STATUS).equals(CLOSED)
				|| bugProps.get(BugzillaConstants.CF_WORKFLOW_STATUS).equals(DOOT)
				|| bugProps.get(BugzillaConstants.CF_WORKFLOW_STATUS).equals(DUPLICATE_ALREADY_FIXED)
				|| bugProps.get(BugzillaConstants.CF_WORKFLOW_STATUS).equals(REASSIGNED))) {
			bugProps.remove(BugzillaConstants.BUG_STATUS);
			bugProps.remove(BugzillaConstants.CF_WORKFLOW_STATUS);
			bugProps.remove(BugzillaConstants.RESOLUTION);
		}
		if (Portfolio.PRE_SR.equals(sB.getSyntheticFields().getPortfolio())) {
			bugProps.remove(BugzillaConstants.STATUS_WHITEBOARD);
			bugProps.remove(BugzillaConstants.KEYWORDS);
			bugProps.remove(BugzillaConstants.CF_WORKFLOW);
		}
	}

	@Override
	public void mapInput() {
		syntheticBugOpt = Optional.ofNullable(mongoOperations.findById(syntheticBugId, SyntheticBug.class));
		bugzillaBugId = null;
		failureMessage = null;
		isStepSuccess = false;
		sbr = null;
	}

	@Override
	public void mapOutput() {
		sbr = new SynBugResponse();
		sbr.setSyntheticBugId(syntheticBugId);
		if (isStepSuccess) {
			sbr.setStatus(ResponseConstants.STATUS_SUCCESS);
			sbr.setMessage("Bug created");
			sbr.setBugID(String.valueOf(bugzillaBugId));
		} else {
			sbr.setStatus(ResponseConstants.STATUS_FAILURE);
			sbr.setMessage("Bug not created:" + failureMessage);
		}
	}

	@Override
	public void validate() {
		//
	}

}
