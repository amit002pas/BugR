/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */ 
package com.yodlee.iae.bugr.rest.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.rest.SynBugRRest;
import com.yodlee.iae.bugr.rest.handler.HandleRest;
import com.yodlee.iae.bugr.rest.validator.RestValidator;
import com.yodlee.iae.bugr.services.jn.jnsimialrbug.JnNonSimilarBugSearch;
import com.yodlee.iae.bugr.services.synthetic.attachment.AddAttachmentSynthetic;
import com.yodlee.iae.bugr.services.synthetic.create.CreateBugzillaBug;
import com.yodlee.iae.bugr.services.synthetic.create.CreateSyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.fetch.FetchAnyBug;
import com.yodlee.iae.bugr.services.synthetic.search.SyntheticBugSearch;
import com.yodlee.iae.bugr.services.synthetic.update.UpdateSyntheticBug;
import com.yodlee.iae.bugr.services.utilities.EncryptionUtils;

/**
 * @author KChandrarajan and Shreyas
 *
 */
@Named
@EnableAutoConfiguration
public class SynBugRRestImpl implements SynBugRRest {

	@Inject
	private SyntheticBugSearch syntheticBugSearch;

	@Inject
	private JnNonSimilarBugSearch jnNonSimilarBugSearch;
	
	@Inject
	private CreateSyntheticBug createSyntheticBug;

	@Inject
	private UpdateSyntheticBug updateSyntheticBug;

	@Inject
	private AddAttachmentSynthetic addAttachmentSynthetic;

	@Inject
	private FetchAnyBug fetchAnyBug;

	@Inject
	private CreateBugzillaBug createBugzillaBug;

	@Inject
	private RestValidator restValidator;

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * Returns the Response after all the Create validations check and Bug Creation
	 * process is done.
	 * 
	 * @param bugEntityString
	 * @return Response
	 */
	@Override
	public Response create(String bugEntityString) {
		BugEntity bugEntity = gson.fromJson(bugEntityString, BugEntity.class);
		if (bugEntity.getSynId() != null) {
			bugEntity.setSynId(EncryptionUtils.decryptBugAnalyserId(bugEntity.getSynId()));
			return HandleRest.apply(createBugzillaBug, bugEntity.getSynId(), restValidator.validateCreate(bugEntity));
		}
		return HandleRest.apply(createSyntheticBug, bugEntity, restValidator.validateCreate(bugEntity));
	}

	/**
	 * Returns the Response after all the Update validations check and Bug Updation
	 * process is done.
	 * 
	 * @param synBugid
	 * @param bugEntityString
	 * @return Response
	 */
	@Override
	public Response update(String synBugid, String bugEntityString) {
		BugEntity bugEntity = gson.fromJson(bugEntityString, BugEntity.class);
		bugEntity.setSynId(EncryptionUtils.decryptBugAnalyserId(synBugid));
		return HandleRest.apply(updateSyntheticBug, bugEntity, restValidator.validateUpdate(bugEntity));
	}

	/**
	 * Returns the Response after all the Fetch validations check and Bug Fetch
	 * process is done.
	 * 
	 * @param id
	 * @return Response
	 */
	@Override
	public Response fetch(String id) {
		return HandleRest.apply(fetchAnyBug, id, restValidator.validateFetch(id));
	}

	/**
	 * Returns the Response after all the Search validations check and Search
	 * process is done.
	 * 
	 * @param bugSearchParamString
	 * @return Response
	 */
	@Override
	public Response search(String bugSearchParamString) {
		BugSearchParam bugSearchParam = gson.fromJson(bugSearchParamString, BugSearchParam.class);
		if(bugSearchParam.getFilters().getCriteria()!=null){
			return HandleRest.apply(jnNonSimilarBugSearch, bugSearchParam, restValidator.validateSearch(bugSearchParam));
		}else{
			return HandleRest.apply(syntheticBugSearch, bugSearchParam, restValidator.validateSearch(bugSearchParam));
		}
	}	

	/**
	 * Returns the Response after all the attachment validations check and Bug
	 * Attachment adding process is done.
	 * 
	 * @param bugid
	 * @param attachments
	 * @return Response
	 */
	@Override
	public Response attachment(String bugid, List<AttachmentRequest> attachments) {
		SyntheticAttachmentRequest req = new SyntheticAttachmentRequest();
		req.setAttachments(attachments);
		req.setBugId(EncryptionUtils.decryptBugAnalyserId(bugid));
		return HandleRest.apply(addAttachmentSynthetic, req, restValidator.validateAddAttachment(req));
	}

}