/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.attachment;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.async.IAsyncSyntheticProcess;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.update.UpdateBugzillaBug;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

@Named
@Scope("prototype")
public class AddBugzillaAttachment implements ServiceIO<SyntheticAttachmentRequest, Object> {

	@Inject
	private SynUtil synUtil;

	@Inject
	private IBugzillaRPCClient bugzillaGateway;

	@Inject
	private MongoOperations mongoOperations;
	
	@Inject
	private ApplicationContext ctx;

	private static final Logger LOG = LoggerFactory.getLogger(AddBugzillaAttachment.class);

	private List<AttachmentRequest> attReqList;
	private SyntheticBug sBug;

	@Override
	public void accept(SyntheticAttachmentRequest req) {
		this.attReqList = req.getAttachments();
		this.sBug = mongoOperations.findById(req.getBugId(), SyntheticBug.class);
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mapInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeImpl() {
		if (sBug.getSyntheticFields().isBugzillaBugCreated()) {
			try {
				synUtil.addAttachmentstoBugzillaAsync(attReqList, sBug.getSyntheticFields().getBugzillaBugId()).get();
			} catch (InterruptedException | ExecutionException e) {
				LOG.debug("Failed to add bugzilla attachment for bug : " + sBug.getSyntheticBugid());
				return;
			}
			bugzillaGateway.getAttachments(Integer.valueOf(sBug.getSyntheticFields().getBugzillaBugId())).stream()
					.reduce((first, second) -> second).ifPresent(attachment -> {
						sBug.getBugFields().setCommentsReleaseNotes(String.valueOf(attachment.getAttachmentID()));
						mongoOperations.save(sBug);
						IAsyncSyntheticProcess.executeAsync(ctx.getBean(UpdateBugzillaBug.class), sBug.getSyntheticBugid());
					});
		}
	}

	@Override
	public void mapOutput() {
		// TODO Auto-generated method stub

	}

}
