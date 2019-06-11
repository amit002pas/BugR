package com.yodlee.iae.bugr.services.synthetic.attachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.BugAttachmentResponse;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class AddAttachmentSynthetic implements ServiceIO<SyntheticAttachmentRequest, BugAttachmentResponse> {

	@Inject
	private AddBugzillaAttachment addBugzillaAttachment;

	@Inject
	private MongoOperations mongoOperations;

	private BugAttachmentResponse response;
	private List<AttachmentRequest> attchReqList;
	private Optional<String> bugIDOpt;
	private Optional<SyntheticBug> syntheticBugOpt;

	@Override
	public void accept(SyntheticAttachmentRequest t) {
		SyntheticAttachmentRequest request = t;
		bugIDOpt = Optional.ofNullable(request.getBugId());
		this.attchReqList = request.getAttachments();
	}

	@Override
	public BugAttachmentResponse get() {
		return response;
	}

	@Override
	public void mapInput() {
		bugIDOpt.ifPresent(bugID -> {
			SyntheticBug sBug = mongoOperations.findById(bugID, SyntheticBug.class);
			syntheticBugOpt = Optional.ofNullable(sBug);
		});
		response = new BugAttachmentResponse();
	}

	@Override
	public void executeImpl() {
		syntheticBugOpt.ifPresent(sBug -> {
			List<AttachmentRequest> list = sBug.getAttachments() != null ? sBug.getAttachments() : new ArrayList<>();
			list.addAll(attchReqList);
			sBug.setAttachments(list);
			response.setTotalAttachmentsCount(list.size());
			mongoOperations.save(sBug, mongoOperations.getCollectionName(SyntheticBug.class));
			if (sBug.getSyntheticFields().isBugzillaBugCreated()) {
				SyntheticAttachmentRequest req = new SyntheticAttachmentRequest();
				req.setAttachments(attchReqList);
				req.setBugId(sBug.getSyntheticBugid());
				CompletableFuture.runAsync(() -> addBugzillaAttachment.process(req),
						Executors.newSingleThreadExecutor());
			}
		});
	}

	@Override
	public void mapOutput() {
		if (syntheticBugOpt.isPresent()) {
			response.setStatus(ResponseConstants.STATUS_SUCCESS);
			response.setMessage(ResponseConstants.MSG_ADD_ATTACHMENT_SUCCESS);
			response.setNoOfAttachmentsAdded(attchReqList.size());
		} else {
			response.setStatus(ResponseConstants.STATUS_FAILURE);
			response.setMessage(ResponseConstants.MSG_ADD_ATTACHMENT_FAILED);
			response.setNoOfAttachmentsAdded(0);
		}
	}

	@Override
	public void validate() {
		//
	}

}
