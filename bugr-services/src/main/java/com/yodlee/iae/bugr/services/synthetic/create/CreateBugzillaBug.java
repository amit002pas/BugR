/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.create;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.mapper.Mapper;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class CreateBugzillaBug implements ServiceIO<String, SynBugResponse> {

	@Inject
	private MongoOperations mongoOperations;

	@Inject
	private IBugzillaRPCClient bugzillaGateway;

	@Inject
	private SynUtil synUtil;

	private static final Logger LOG = LoggerFactory.getLogger(CreateBugzillaBug.class);

	private String syntheticBugId;
	private Optional<SyntheticBug> syntheticBugOpt;

	private String bugzillaBugId;
	private String failureMessage;
	private boolean isStepSuccess;
	private SynBugResponse sbr;

	@Override
	public void accept(String obj) {
		this.syntheticBugId = obj;
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
			try {
				bugzillaBugId = bugzillaGateway.createBug(bugProps, null);
				sB.getSyntheticFields().setBugzillaBugCreated(true);
				sB.getSyntheticFields().setBugzillaBugId(Integer.valueOf(bugzillaBugId));
				synUtil.addAttachmentstoBugzillaAsync(sB.getAttachments(), sB.getSyntheticFields().getBugzillaBugId());
				isStepSuccess = true;
				sB.getSyntheticFields().setSyntheticBugStatus(SyntheticBugStatus.ACTIVE);
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
			sbr.setBugID(bugzillaBugId);
		} else {
			sbr.setStatus(ResponseConstants.STATUS_FAILURE);
			sbr.setMessage("Bug not created");
			sbr.setMessages(Arrays.asList(failureMessage));
		}
	}

	@Override
	public void validate() {
		//
	}

}
