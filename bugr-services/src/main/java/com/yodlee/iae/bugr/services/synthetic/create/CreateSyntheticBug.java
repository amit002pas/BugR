/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.create;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;
import com.yodlee.iae.bugr.services.synthetic.async.IAsyncSyntheticProcess;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.dedupe.SyntheticBugDeduper;
import com.yodlee.iae.bugr.services.synthetic.manager.SyntheticBugManager;
import com.yodlee.iae.bugr.services.synthetic.mapper.SyntheticBugMapper;
import com.yodlee.iae.bugr.services.synthetic.update.UpdateBugzillaBug;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.EncryptionUtils;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class CreateSyntheticBug implements ServiceIO<BugEntity, SynBugResponse> {

	@Inject
	private MongoOperations mongoOperations;

	@Inject
	private SyntheticBugMapper syntheticBugMapper;

	@Inject
	private SyntheticBugManager syntheticBugManager;

	@Inject
	private ApplicationContext ctx;

	@Value("${errorcodes.reconwealth}")
	private String reconWealthErrorCodes;

	private List<String> reconWealthErrorCodesList;
	private Optional<BugEntity> reqBugOpt;
	private Optional<SyntheticBug> syntheticBugOpt;
	private SynBugResponse response;

	/**
	 * Set input to the synthetic bug creator
	 * 
	 * @param jsonObjectBugProps
	 * @return
	 */
	@Override
	public void accept(BugEntity bugEntity) {
		reqBugOpt = Optional.ofNullable(bugEntity);
		response = new SynBugResponse();
		syntheticBugOpt = Optional.ofNullable(null);
	}

	@Override
	public SynBugResponse get() {
		return response;
	}

	@Override
	public void mapInput() {
		reqBugOpt.ifPresent(bugEntity -> syntheticBugOpt = Optional
				.ofNullable(syntheticBugMapper.doMapping(Arrays.asList(bugEntity)).get().get(0)));
		reconWealthErrorCodesList = Arrays.asList(reconWealthErrorCodes.split(","));
	}

	@Override
	public void executeImpl() {
		syntheticBugOpt.ifPresent(sBug -> {
			BugFields synBugFields = sBug.getBugFields();
			Portfolio portfolio = getPortfolio(synBugFields);
			sBug.getSyntheticFields().setPortfolio(portfolio);
			response = syntheticBugManager.getBugValidator(portfolio).validateBug(sBug);
			if (response.getStatus().equals(ResponseConstants.STATUS_SUCCESS)) {
				syntheticBugManager.getBugProcessor(portfolio).processBugContents(sBug);
				SyntheticBugDeduper syntheticBugDeduper = syntheticBugManager.getBugDeduper(portfolio);
				Optional<SyntheticBug> duplicatebugOpt = syntheticBugDeduper.findDuplicateBug(sBug);
				if (duplicatebugOpt.isPresent()) {
					syntheticBugDeduper.updateDuplicateBug(sBug, duplicatebugOpt.get());
					sBug = duplicatebugOpt.get();
					syntheticBugOpt = Optional.ofNullable(sBug);
					if (sBug.getSyntheticFields().isBugzillaBugCreated()) {
						IAsyncSyntheticProcess.executeAsync(ctx.getBean(UpdateBugzillaBug.class), sBug.getSyntheticBugid());
					} else if(reqBugOpt.get().getCreateBugzillaBug() && !sBug.getSyntheticFields().isBugzillaBugCreated()) {
						IAsyncSyntheticProcess.executeAsync(ctx.getBean(CreateBugzillaBug.class), sBug.getSyntheticBugid());	
					}
				} else {
					mongoOperations.save(sBug, mongoOperations.getCollectionName(SyntheticBug.class));
					if (reqBugOpt.get().getCreateBugzillaBug() && !sBug.getSyntheticFields().isBugzillaBugCreated()) {
						IAsyncSyntheticProcess.executeAsync(ctx.getBean(CreateBugzillaBug.class), sBug.getSyntheticBugid());
					}
				}
			}
		});  
	}

	@Override
	public void mapOutput() {
		syntheticBugOpt.ifPresent(sB -> {
			if (response.getStatus().equals(ResponseConstants.STATUS_FAILURE)) {
				return;
			}

			switch (sB.getSyntheticFields().getSyntheticBugStatus()) {
			case ACTIVE:
				response.setStatus(ResponseConstants.STATUS_SUCCESS);
				response.setMessage(ResponseConstants.MSG_CREATE_SYNTHETIC_BUG_CREATED);
				response.setSyntheticBugId(EncryptionUtils.encryptBugAnalyserId(sB.getSyntheticBugid()));
				break;
			case INVALID:
				response.setStatus(ResponseConstants.STATUS_FAILURE);
				response.setMessage(ResponseConstants.MSG_CREATE_SYNTHETIC_BUG_NOT_CREATED);
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void validate() {
		//
	}

	/**
	 * 
	 * Designating a Portfolio for the request for processing
	 * 
	 * @param synBugFields
	 * @return
	 */
	private Portfolio getPortfolio(BugFields synBugFields) {
		if (synBugFields.getWhiteboard() != null && (synBugFields.getWhiteboard().contains(SynUtil.CUSIP)
				|| synBugFields.getWhiteboard().contains(SynUtil.SYMBOL))) {
			return Portfolio.RECON_HOLDING;
		} else if (reconWealthErrorCodesList.contains(synBugFields.getErrorcode())
				|| (null != synBugFields.getWhiteboard() && synBugFields.getWhiteboard().contains("Recon_Services"))) {
			return Portfolio.RECON_SERVICES;
		} else if (synBugFields.getWhiteboard() == null
				|| (synBugFields.getWhiteboard().contains("PreSR_ErrorSegment"))) {
			return Portfolio.PRE_SR;
		}
		return null;
	}

}
