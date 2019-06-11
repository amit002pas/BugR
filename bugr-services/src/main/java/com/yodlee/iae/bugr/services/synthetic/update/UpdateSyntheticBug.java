/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.MiscellaneousFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;
import com.yodlee.iae.bugr.services.synthetic.async.IAsyncSyntheticProcess;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.mapper.Mapper;
import com.yodlee.iae.bugr.services.synthetic.util.ServiceMethods;
import com.yodlee.iae.bugr.services.synthetic.validation.ServiceMessageReader;
import com.yodlee.iae.bugr.services.utilities.EncryptionUtils;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class UpdateSyntheticBug implements ServiceIO<BugEntity, SynBugResponse> {

	@Inject
	private MongoOperations mongoOperations;

	@Inject
	private SyntheticBugRepository syntheticBugRepository;

	@Inject
	private ServiceMessageReader serviceMessageReader;

	@Inject
	private ApplicationContext ctx;

	private Optional<BugEntity> reqBugsOpt;
	private String anyBugId;
	private Optional<SyntheticBug> syntheticBugOpt;
	private SynBugResponse synBugResponse;

	private boolean isValid;
	private HashMap<Boolean, String> map = new HashMap<>();

	@Override
	public void accept(BugEntity obj) {
		BugEntity bE = obj;
		reqBugsOpt = Optional.ofNullable(bE);
		if (null != bE.getSynId()) {
			this.anyBugId = bE.getSynId();
		} else {
			this.anyBugId = String.valueOf(bE.getId());
		}
		syntheticBugOpt = Optional.ofNullable(null);
		synBugResponse = new SynBugResponse();
		isValid = true;
	}

	@Override
	public SynBugResponse get() {
		return synBugResponse;
	}

	@Override
	public void mapInput() {
		reqBugsOpt.ifPresent(bugEntity -> {
			SyntheticBug sBug;
			if (StringUtils.isNumeric(anyBugId)) {
				sBug = syntheticBugRepository.getSyntheticBugFromBugzillaId(Integer.parseInt(anyBugId));
			} else {
				sBug = mongoOperations.findById(anyBugId, SyntheticBug.class);
			}

			if (sBug != null) {
				map = ServiceMethods.validateUpdate(bugEntity, sBug);
				if (map.containsKey(false)) {
					isValid = false;
				}
				if(null == sBug.getMiscellaneousFields()){
					sBug.setMiscellaneousFields(new MiscellaneousFields());
				}
				Mapper.updateSynBugFields(bugEntity, sBug);
			}
			syntheticBugOpt = Optional.ofNullable(sBug);
		});
	}

	@Override
	public void executeImpl() {
		if (isValid) {
			syntheticBugOpt.ifPresent(sBug -> {
				mongoOperations.save(sBug, mongoOperations.getCollectionName(SyntheticBug.class));
				if (sBug.getSyntheticFields().isBugzillaBugCreated()) {
					IAsyncSyntheticProcess.executeAsync(ctx.getBean(UpdateBugzillaBug.class), sBug.getSyntheticBugid());
				}
			});
		}
	}

	@Override
	public void mapOutput() {
		if (isValid) {
			syntheticBugOpt.ifPresent(sB -> {
				if (null != sB.getSyntheticFields().getBugzillaBugId())
					synBugResponse.setBugID(String.valueOf(sB.getSyntheticFields().getBugzillaBugId()));
				switch (sB.getSyntheticFields().getSyntheticBugStatus()) {
				case ACTIVE:
					synBugResponse.setStatus(ResponseConstants.STATUS_SUCCESS);
					synBugResponse.setMessage(ResponseConstants.MSG_CREATE_SYNTHETIC_BUG_UPDATED);
					synBugResponse.setSyntheticBugId(EncryptionUtils.encryptBugAnalyserId(sB.getSyntheticBugid()));
					break;
				case CLOSED:
					synBugResponse.setStatus(ResponseConstants.STATUS_SUCCESS);
					synBugResponse.setMessage(ResponseConstants.MSG_CREATE_SYNTHETIC_BUG_CLOSED);
					synBugResponse.setSyntheticBugId(EncryptionUtils.encryptBugAnalyserId(sB.getSyntheticBugid()));
					break;
				case INVALID:
					synBugResponse.setStatus(ResponseConstants.STATUS_FAILURE);
					synBugResponse.setMessage(ResponseConstants.MSG_CREATE_SYNTHETIC_BUG_NOT_UPDATED);
					break;
				default:
					break;
				}
			});
		} else {
			synBugResponse.setStatus(ResponseConstants.STATUS_FAILURE);
			synBugResponse.setMessages(Arrays.asList(serviceMessageReader.getPropertyByKey(map.get(false))));
		}
	}

	@Override
	public void validate() {
		//
	}

}
