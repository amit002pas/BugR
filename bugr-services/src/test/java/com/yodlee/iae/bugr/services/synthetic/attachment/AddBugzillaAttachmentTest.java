/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.attachment;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.AttachmentFactory;
import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.synthetic.update.UpdateBugzillaBug;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

public class AddBugzillaAttachmentTest {

	@InjectMocks
	AddBugzillaAttachment addBugzillaAttachment;
	
	@Mock
	private SynUtil synUtil;

	@Mock
	private IBugzillaRPCClient bugzillaGateway;

	@Mock
	private MongoOperations mongoOperations;

	@Mock
	private UpdateBugzillaBug updateBugzillaBug;
	
	@Mock
	private ApplicationContext ctx;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAddAttachSynBug() throws InterruptedException, ExecutionException{
		SyntheticAttachmentRequest request = new SyntheticAttachmentRequest();
		request.setBugId("12323123");
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		byte[] bytestream= {107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110};
		AttachmentRequest attachmentRequest= new AttachmentRequest();
		attachmentRequest.setAttachmentName("Attach");
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);
		AttachmentRequest attachmentRequest1= new AttachmentRequest();
		attachmentRequest1.setAttachmentName("Attach1");
		attachmentRequest1.setAttachment(bytestream);
		attachmentList.add(attachmentRequest1);
		request.setAttachments(attachmentList);	
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(true);
		syntheticFields.setBugzillaBugId(12384321);
		sbug.setSyntheticFields(syntheticFields);
		
		Mockito.when(mongoOperations.findById(any(String.class),eq(SyntheticBug.class))).thenReturn(sbug);
		
		List<Attachment> attachmentBugList = new ArrayList<>();
		AttachmentFactory attachmentFactory = new AttachmentFactory();
		byte[] byteArray = {107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110};
		Attachment attachment = attachmentFactory.newAttachment().setData(byteArray).setName("Attach").setSummary("Summary").createAttachment();
		Attachment attachment1 = attachmentFactory.newAttachment().setData(byteArray).setName("Attach1").setSummary("Summary1").createAttachment();
		attachmentBugList.add(attachment);
		attachmentBugList.add(attachment1);
		
		Mockito.when(synUtil.addAttachmentstoBugzillaAsync(any(List.class), any(Integer.class))).thenReturn(CompletableFuture.completedFuture(null));
		Mockito.when(bugzillaGateway.getAttachments(any(Integer.class))).thenReturn(attachmentBugList);
		addBugzillaAttachment.process(request);
	}
	
	@Test
	public void testAddAttachSynBugNoBugCase() throws InterruptedException, ExecutionException{
		SyntheticAttachmentRequest request = new SyntheticAttachmentRequest();
		request.setBugId("12323123");
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		byte[] bytestream= {107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110};
		AttachmentRequest attachmentRequest= new AttachmentRequest();
		attachmentRequest.setAttachmentName("Attach");
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);
		AttachmentRequest attachmentRequest1= new AttachmentRequest();
		attachmentRequest1.setAttachmentName("Attach1");
		attachmentRequest1.setAttachment(bytestream);
		attachmentList.add(attachmentRequest1);
		request.setAttachments(attachmentList);	
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(false);
		syntheticFields.setBugzillaBugId(12384321);
		sbug.setSyntheticFields(syntheticFields);
		
		Mockito.when(mongoOperations.findById(any(String.class),eq(SyntheticBug.class))).thenReturn(sbug);
		
		addBugzillaAttachment.process(request);
	}
}
