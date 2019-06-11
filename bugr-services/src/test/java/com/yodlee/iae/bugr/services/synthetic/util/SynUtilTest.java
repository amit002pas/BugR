/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;

import com.google.gson.Gson;
import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.synthetic.create.CreateSyntheticBug;

public class SynUtilTest {

	@InjectMocks
	private SynUtil synUtil;

	@Mock
	private IBugzillaRPCClient bugzillaGateway;

	private static Gson gson = new Gson();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testValidateStr() {
		String str = null;
		SynUtil.validateStr(str);
		str = "Test String";
		SynUtil.validateStr(str);
		str = "";
		SynUtil.validateStr(str);
	}

	@Test
	public void testValidateObj() {
		Object obj = null;
		SynUtil.validateObj(obj);
		obj = "Test String";
		SynUtil.validateObj(obj);
		obj = "";
		SynUtil.validateObj(obj);
	}


	@Test
	public void testValidateList() {
		List<String> strList = new ArrayList<>();
		String str = "Test String";
		strList.add(str);
		SynUtil.validateList(strList);

		List<String> strList1 = new ArrayList<>();
		SynUtil.validateList(strList1);

		List<String> strList2 = null;
		SynUtil.validateList(strList2);
	}

	@Test
	public void testCalImpactPercentage() {
		String str = "Total Count:82;Failure Count:29;Success Count:80;Impact Percentage: 35.0;Updated At: 2019-03-03 09:12";
		SynUtil.calImpactPercentage(str);
		str = "Failure Count:29";
		SynUtil.calImpactPercentage(str);
		str = "Total Count:82";
		SynUtil.calImpactPercentage(str);
	}

	@Test
	public void testMergeReconHoldingDetails() {
		String codeReviewComments = "{\"holdings\":[{\"sumInfoId\":14579,\"agentName\":\"BISYSRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10273568,\"itemAccountId\":10533019,\"date\":\"null\",\"entityType\":\"TRANSACTION\",\"entityId\":30081461,\"description\":\"GM Loomis Sayles Bond Retail - Diversified Bond - Dividend\",\"price\":\"null\",\"xignitePrice\":\"null\",\"xigniteSymbol\":\"null\",\"xigniteCusipNumber\":\"null\",\"endOfTheDayPriceDate\":\"null\",\"xigniteDescripion\":\"null\",\"analysisDescription\":\"Populated Through T2H Mapping\"},{\"sumInfoId\":14579,\"agentName\":\"BISYSRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10273568,\"itemAccountId\":10533019,\"date\":\"null\",\"entityType\":\"TRANSACTION\",\"entityId\":30081462,\"description\":\"GM Loomis Sayles Bond Retail - Diversified Bond - Monthly AFCA Deposit\",\"price\":\"null\",\"xignitePrice\":\"null\",\"xigniteSymbol\":\"null\",\"xigniteCusipNumber\":\"null\",\"endOfTheDayPriceDate\":\"null\",\"xigniteDescripion\":\"null\",\"analysisDescription\":\"Populated Through T2H Mapping\"}]}";
		String codeReviewCommentsDupe = "{\"holdings\":[{\"sumInfoId\":14579,\"agentName\":\"BISYSRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10273568,\"itemAccountId\":10533019,\"date\":\"null\",\"entityType\":\"TRANSACTION\",\"entityId\":30081461,\"description\":\"GM Loomis Sayles Bond Retail - Diversified Bond - Dividend\",\"price\":\"null\",\"xignitePrice\":\"null\",\"xigniteSymbol\":\"null\",\"xigniteCusipNumber\":\"null\",\"endOfTheDayPriceDate\":\"null\",\"xigniteDescripion\":\"null\",\"analysisDescription\":\"Populated Through T2H Mapping\"},{\"sumInfoId\":14579,\"agentName\":\"BISYSRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10273568,\"itemAccountId\":10533019,\"date\":\"null\",\"entityType\":\"TRANSACTION\",\"entityId\":30081462,\"description\":\"GM Loomis Sayles Bond Retail - Diversified Bond - Monthly AFCA Deposit\",\"price\":\"null\",\"xignitePrice\":\"null\",\"xigniteSymbol\":\"null\",\"xigniteCusipNumber\":\"null\",\"endOfTheDayPriceDate\":\"null\",\"xigniteDescripion\":\"null\",\"analysisDescription\":\"Populated Through T2H Mapping\"}]}";
		SynUtil.mergeReconHoldingDetails(codeReviewComments, codeReviewCommentsDupe);
	}

	@Test
	public void testMergeReconHoldingDetailsEmpty() {
		String codeReviewComments = "{\"holdings\":[{\"sumInfoId\":14579,\"agentName\":\"BISYSRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10273568,\"itemAccountId\":10533019,\"date\":\"null\",\"entityType\":\"TRANSACTION\",\"entityId\":30081461,\"description\":\"GM Loomis Sayles Bond Retail - Diversified Bond - Dividend\",\"price\":\"null\",\"xignitePrice\":\"null\",\"xigniteSymbol\":\"null\",\"xigniteCusipNumber\":\"null\",\"endOfTheDayPriceDate\":\"null\",\"xigniteDescripion\":\"null\",\"analysisDescription\":\"Populated Through T2H Mapping\"},{\"sumInfoId\":14579,\"agentName\":\"BISYSRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10273568,\"itemAccountId\":10533019,\"date\":\"null\",\"entityType\":\"TRANSACTION\",\"entityId\":30081462,\"description\":\"GM Loomis Sayles Bond Retail - Diversified Bond - Monthly AFCA Deposit\",\"price\":\"null\",\"xignitePrice\":\"null\",\"xigniteSymbol\":\"null\",\"xigniteCusipNumber\":\"null\",\"endOfTheDayPriceDate\":\"null\",\"xigniteDescripion\":\"null\",\"analysisDescription\":\"Populated Through T2H Mapping\"}]}";
		String codeReviewCommentsDupe = "{\"holdings\":[]}";
		SynUtil.mergeReconHoldingDetails(codeReviewComments, codeReviewCommentsDupe);
	}

	@Test
	public void testGetCusipAndSymbol() {
		String str = "Recon_Services;Cusip:543495832;Symbol:LSBRX;";
		SynUtil.getCusipAndSymbol(str);
	}

	@Test
	public void testFormImpactField() {
		String str = "Impact Percentage: 1;Updated At: 2019-03-06 00:51";
		SynUtil.formImpactField(str);
		str = "";
		SynUtil.formImpactField(str);
	}

	@Test
	public void testGetImpactPercentageFromString() {
		String str = "Impact Percentage: 1;Updated At: 2019-03-06 00:51";
		SynUtil.getImpactPercentageFromString(str);
	}

	@Test
	public void testGetImpactCountFromString() {
		String str = "Refresh Count: 0;Failure Count: 15;Predicted Failure: 1792;Impact Percentage: 83.33;Updated At: 2019-03-04 00:17";
		SynUtil.getImpactCountFromString(str);
	}

	@Test
	public void testGetStackTraceFromCRC() {
		String str = "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.SiteTerminatedSessionException: You are logged out. Please try againtat GBTCBase.verifyProperTransactionPage(GBTCBase.java:4331)tat GBTCBase.navigateToAccountTxn(GBTCBase.java:4732)tat GBTCBank.getAccountTran||{}";
		SynUtil.getStackTraceFromCRC(str);
		str = "";
		SynUtil.getStackTraceFromCRC(str);
	}

	@Test
	public void testGetSimilarBugs() {
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("7503");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setAgentName("AuMacquarie");
		bugFields.setSuminfo("4479");
		bugFields.setCodeReviewComments("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\\\"0926bb06-43be-478b-9492-477327b0d6f4\\\":\\\"1#245\\\",\\\"4f209884-cecc-4305-a895-7dd2709e5387\\\":\\\"8#1546\\\"}");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);

		sbug.setSyntheticBugid("BAID-5c790490ae6f50699c87e235-BGCR");

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbug);

		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Dummy issue Proactive monitoring created for 7503");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_agents("AuMacquarie");
		bugEntity.setCf_code_review_comments_gen("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\"0926bb06-43be-478b-9492-477327b0d6f4\":\"1#245\",\"4f209884-cecc-4305-a895-7dd2709e5387\":\"8#1546\"}");
		bugEntity.setCf_errorcode("7503");
		//	bugEntity.setId("7777777");

		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);

		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report")
				.setVersion("1.0.6").createBug(bugProps);
		Bug bug1 = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report")
				.setVersion("1.0.6").createBug(bugProps);

		bugList.add(bug);
		bugList.add(bug1);

		SynUtil.getSimilarBugs(synBugList, bugList);
	}

	@Test
	public void testConvertAttachmentsToBase64() {

		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);

		List<AttachmentRequest> attachmentList = new ArrayList<>();
		byte[] bytestream = { 107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110 };
		AttachmentRequest attachmentRequest = new AttachmentRequest();
		attachmentRequest.setAttachmentName("Attach1");
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);

		sbug.setAttachments(attachmentList);

		SynUtil.convertAttachmentsToBase64(sbug);
	}

	@Test
	public void testConvertToBugzillaAttachment() {

		byte[] bytestream = { 107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110 };
		AttachmentRequest attachmentRequest = new AttachmentRequest();
		attachmentRequest.setAttachmentName("Attach1");
		attachmentRequest.setAttachment(bytestream);

		SynUtil.convertToBugzillaAttachment(attachmentRequest);
	}

	@Test
	public void testAddAttachmentstoBugzillaAsync() {

		Integer bugId = 2143234;
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		synUtil.addAttachmentstoBugzillaAsync(attachmentList, bugId);

		byte[] bytestream = { 107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110 };
		AttachmentRequest attachmentRequest = new AttachmentRequest();
		attachmentRequest.setAttachmentName("Attach1");
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);

		Mockito.doNothing().when(bugzillaGateway).setAttachment(any(Attachment.class), any(Integer.class));
		synUtil.addAttachmentstoBugzillaAsync(attachmentList, bugId);
	}

	@Test
	public void testAddAttachmentstoBugzillaAsyncBugEmpty() {

		Integer bugId = null;
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		synUtil.addAttachmentstoBugzillaAsync(attachmentList, bugId);

		byte[] bytestream = { 107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110 };
		AttachmentRequest attachmentRequest = new AttachmentRequest();
		attachmentRequest.setAttachmentName("Attach1");
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);
		synUtil.addAttachmentstoBugzillaAsync(attachmentList, bugId);
	}
	@Test
	public void testAddAttachmentstoBugzillaAsyncListEmpty() {

		Integer bugId = 2143234;
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		synUtil.addAttachmentstoBugzillaAsync(attachmentList, bugId);
	}
	
	@Test
	public void testGenerateResponse() {
		CreateSyntheticBug createSyntheticBug = new CreateSyntheticBug();
		SynUtil.generateResponse(createSyntheticBug);
	}

	@Test
	public void teststackTraceSimilarity5() throws Exception {
		String stacktrace1 = "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Account summary page is not loading....       at ChaseBase.prepareForExecute(ChaseBase.java:4038)     at ChaseRequestManager.prepareForExecute(ChaseRequestManager.java:237)        at ChaseInvest";
		String stacktrace2 = "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Account summary page is not loading....       at ChaseBase.prepareForExecute(ChaseBase.java:1030)     at ChaseRequestManager.prepareForExecute(ChaseRequestManager.java:229)        at ChaseInvest";
		SynUtil.stackTraceSimilarity(stacktrace1, stacktrace2);
	}

	@Test
	public void teststackTraceSimilarity6() throws Exception {
		String stacktrace1 = "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Account summary page is not loading....       at ChaseBase.prepareForExecute(ChaseBase.java:4038)     at  ||ChaseRequestManager.prepare'ForExecute(JController.java:237)        at ChaseInvest";
		String stacktrace2 = "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Account summary page is not loading....       at ChaseBase.prepareForExecute(ChaseBase.java:1030)     a't ChaseRequestManager.prepareForExecute(JController.java:229)        at ChaseInvest";
		SynUtil.stackTraceSimilarity(stacktrace1, stacktrace2);
	}

	@Test
	public void teststackTraceSimilarity7() throws Exception {
		String stacktrace1 = "org.openqa.selenium:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Account summary page is not loading....       at ChaseBase.prepareForExecute(ChaseBase.java:4038)     at  ||ajhgsajg.prepare'ForExecute(JController.java:237)  at ChaseInvest";
		String stacktrace2 = "org.openqa.selenium:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Account summary page is not loading....       at ChaseBase.prepareForExecute(ChaseBase.java:1030)     a't ChaseRequestManager.prepareForExecute(JController.java:229)        at ChaseInvest";
		SynUtil.stackTraceSimilarity(stacktrace1, stacktrace2);
	}

	@Test
	public void teststackTraceSimilarity8() throws Exception {
		String stacktrace1 = "org.openqa.selenium:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Account summary page is not loading....       at ChaseBase.prepareForExecute(ChaseBase.java:4038)     at  ||ajhgsajg.prepare'ForExecute(JController.java:237)  at ChaseInvest";
		String stacktrace2 = "asdbkajdshjsadhlkjsadkjhsadksa";
		SynUtil.stackTraceSimilarity(stacktrace1, stacktrace2);
	}
	@Test
	public void testSendResponse() {
		List<String> strList = new ArrayList<>();
		SynUtil.sendResponse(strList);

		String str1="TestString 1";
		String str2="TestString 2";
		strList.add(str1);
		strList.add(str2);
		SynUtil.sendResponse(strList);
	}

	@Test
	public void testGetBugzillaClosedBugs() {
		Date date = new Date();
		synUtil.getBugzillaClosedBugs(date);
	}

	@Test
	public void testNonSimilarBugs(){

		Map<String, Set<Integer>> similarJNBugs = new HashMap<>();
		Set<Integer> setIn = new HashSet<>();
		setIn.add(1111);
		similarJNBugs.put("11122", setIn);
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Dummy issue Proactive monitoring created for 7503");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_agents("AuMacquarie");
		bugEntity.setCf_code_review_comments_gen("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\"0926bb06-43be-478b-9492-477327b0d6f4\":\"1#245\",\"4f209884-cecc-4305-a895-7dd2709e5387\":\"8#1546\"}");
		bugEntity.setCf_errorcode("7503");

		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);

		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report")
				.setVersion("1.0.6").createBug(bugProps);
		Bug bug1 = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report")
				.setVersion("1.0.6").createBug(bugProps);

		bugList.add(bug);
		bugList.add(bug1);
		synUtil.getNonSimilarBugs(similarJNBugs, bugList);

	}

	@Test
	public void testNonSimilarBugsNotcontain(){

		Map<String, Set<Integer>> similarJNBugs = new HashMap<>();
		Set<Integer> setIn = new HashSet<>();
		setIn.add(222);
		similarJNBugs.put("11122", setIn);
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Dummy issue Proactive monitoring created for 7503");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_agents("AuMacquarie");
		bugEntity.setCf_code_review_comments_gen("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\"0926bb06-43be-478b-9492-477327b0d6f4\":\"1#245\",\"4f209884-cecc-4305-a895-7dd2709e5387\":\"8#1546\"}");
		bugEntity.setCf_errorcode("7503");

		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);

		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report")
				.setVersion("1.0.6").createBug(bugProps);
		Bug bug1 = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report")
				.setVersion("1.0.6").createBug(bugProps);

		bugList.add(bug);
		bugList.add(bug1);
		synUtil.getNonSimilarBugs(similarJNBugs, bugList);

	}
	@Test
	public void testFilteredJnBugs(){

		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("7503");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setAgentName("AuMacquarie");
		bugFields.setSuminfo("4479");
		bugFields.setCodeReviewComments("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\\\"0926bb06-43be-478b-9492-477327b0d6f4\\\":\\\"1#245\\\",\\\"4f209884-cecc-4305-a895-7dd2709e5387\\\":\\\"8#1546\\\"}");
		bugFields.setWorkflowStatus("New");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);

		sbug.setSyntheticBugid("BAID-5c790490ae6f50699c87e235-BGCR");

		SyntheticBug sbug1 = new SyntheticBug();
		BugFields bugFields1 = new BugFields();
		bugFields1.setComment("As a part of Orphic testing");
		bugFields1.setErrorcode("DQ");
		bugFields1.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields1.setCustomer("All");
		bugFields1.setAgentName("AuMacquarie");
		bugFields1.setSuminfo("4479");
		bugFields1.setCodeReviewComments("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\\\"0926bb06-43be-478b-9492-477327b0d6f4\\\":\\\"1#245\\\",\\\"4f209884-cecc-4305-a895-7dd2709e5387\\\":\\\"8#1546\\\"}");
		bugFields1.setWorkflowStatus("New");
		bugFields1.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug1.setBugFields(bugFields1);

		sbug1.setSyntheticBugid("BAID-5c790490ae6f50699c87e235-BGCR");

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("DQ");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setAgentName("AuMacquarie");
		bugFields2.setSuminfo("4479");
		bugFields2.setCodeReviewComments("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\\\"0926bb06-43be-478b-9492-477327b0d6f4\\\":\\\"1#245\\\",\\\"4f209884-cecc-4305-a895-7dd2709e5387\\\":\\\"8#1546\\\"}");
		bugFields2.setWorkflowStatus("Closed");
		bugFields2.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug2.setBugFields(bugFields2);

		sbug2.setSyntheticBugid("BAID-5c790490ae6f50699c87e235-BGCR");

		SyntheticBug sbug3 = new SyntheticBug();
		BugFields bugFields3 = new BugFields();
		bugFields3.setWorkflowStatus("Dependent on other teams");
		sbug3.setBugFields(bugFields3);
		SyntheticBug sbug4 = new SyntheticBug();
		BugFields bugFields4 = new BugFields();
		bugFields4.setWorkflowStatus("Duplicate - Already Fixed");
		sbug4.setBugFields(bugFields4);
		SyntheticBug sbug5 = new SyntheticBug();
		BugFields bugFields5 = new BugFields();
		bugFields5.setWorkflowStatus("Reassigned");
		sbug5.setBugFields(bugFields5);

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbug);
		synBugList.add(sbug1);
		synBugList.add(sbug2);
		synBugList.add(sbug3);
		synBugList.add(sbug4);
		synBugList.add(sbug5);

		synUtil.getFilteredJnBugs(synBugList);
	}

	@Test
	public void testGetDataForCSV(){
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugId(12345);
		bugFields.setSummary("Proactive monitoring");
		bugFields.setWhiteboard("TTR");
		bugFields.setCodeReviewComments("adshadshjsa|ajdsjhsad");
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("7503");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setAgentName("AuMacquarie");
		bugFields.setSuminfo("4479");
		bugFields.setCodeReviewComments("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\\\"0926bb06-43be-478b-9492-477327b0d6f4\\\":\\\"1#245\\\",\\\"4f209884-cecc-4305-a895-7dd2709e5387\\\":\\\"8#1546\\\"}");
		bugFields.setWorkflowStatus("New");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		sbug.setSyntheticFields(syntheticFields);
		sbug.setSyntheticBugid("BAID-5c790490ae6f50699c87e235-BGCR");

		SyntheticBug sbug1 = new SyntheticBug();
		BugFields bugFields1 = new BugFields();
		SyntheticFields syntheticFields1 = new SyntheticFields();
		syntheticFields1.setBugzillaBugId(232323);
		bugFields1.setComment("As a part of Orphic testing");
		bugFields1.setErrorcode("DQ");
		bugFields1.setSummary("Proactive monitoring");
		bugFields1.setWhiteboard("TTR");
		bugFields1.setCodeReviewComments("adshadshjsa|ajdsjhsad");
		bugFields1.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields1.setCustomer("All");
		bugFields1.setAgentName("AuMacquarie");
		bugFields1.setSuminfo("4479");
		bugFields1.setCodeReviewComments("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\\\"0926bb06-43be-478b-9492-477327b0d6f4\\\":\\\"1#245\\\",\\\"4f209884-cecc-4305-a895-7dd2709e5387\\\":\\\"8#1546\\\"}");
		bugFields1.setWorkflowStatus("New");
		bugFields1.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug1.setBugFields(bugFields1);
		sbug1.setSyntheticFields(syntheticFields1);
		sbug1.setSyntheticBugid("BAID-5c790490ae6f50699c87e235-BGCR");

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		SyntheticFields syntheticFields2 = new SyntheticFields();
		syntheticFields2.setBugzillaBugId(3434);
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("111");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSummary("Proactive monitoring");
		bugFields2.setWhiteboard("TTR");
		bugFields2.setCodeReviewComments("adshadshjsa|ajdsjhsad");
		bugFields2.setAgentName("AuMacquarie");
		bugFields2.setSuminfo("4479,12121");
		bugFields2.setCodeReviewComments("Stack Trace:java.lang.NullPointerException	at BBandTMortgage.navigateToDetailsPage(BBandTMortgage.java:355)	at BBandTMortgage.getAccountDetails(BBandTMortgage.java:506)	at BBandTMortgage.execute(BBandTMortgage.java:175)	at com.yodlee.dap.gatherer.gather.JControl||{\\\"0926bb06-43be-478b-9492-477327b0d6f4\\\":\\\"1#245\\\",\\\"4f209884-cecc-4305-a895-7dd2709e5387\\\":\\\"8#1546\\\"}");
		bugFields2.setWorkflowStatus("Closed");
		bugFields2.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug2.setBugFields(bugFields2);
		sbug2.setSyntheticFields(syntheticFields2);
		sbug2.setSyntheticBugid("BAID-5c790490ae6f50699c87e235-BGCR");

		SyntheticBug sbug3 = new SyntheticBug();
		SyntheticFields syntheticFields3 = new SyntheticFields();
		syntheticFields3.setBugzillaBugId(565656);
		BugFields bugFields3 = new BugFields();
		bugFields3.setErrorcode("232");
		bugFields3.setAgentName("AuMacquarie");
		bugFields3.setWorkflowStatus("Dependent on other teams");
		bugFields3.setSummary("Proactive monitoring");
		bugFields3.setWhiteboard("TTR");
		bugFields3.setCodeReviewComments("adshadshjsa");
		bugFields3.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug3.setBugFields(bugFields3);
		sbug3.setSyntheticFields(syntheticFields3);

		SyntheticBug sbug4 = new SyntheticBug();
		SyntheticFields syntheticFields4 = new SyntheticFields();
		syntheticFields4.setBugzillaBugId(4323434);
		BugFields bugFields4 = new BugFields();
		bugFields4.setErrorcode("401");
		bugFields4.setAgentName("AuMacquarie");
		bugFields4.setSummary("Proactive monitoring");
		bugFields4.setWhiteboard("TTR");
		bugFields4.setCodeReviewComments("adshadshjsa|ajdsjhsad");
		bugFields4.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFields4.setWorkflowStatus("Duplicate - Already Fixed");
		sbug4.setSyntheticFields(syntheticFields4);
		sbug4.setBugFields(bugFields4);
		SyntheticBug sbug5 = new SyntheticBug();
		BugFields bugFields5 = new BugFields();
		SyntheticFields syntheticFields5 = new SyntheticFields();
		syntheticFields5.setBugzillaBugId(null);
		bugFields5.setSummary("Proactive monitoring");
		bugFields5.setWhiteboard("TTR");
		bugFields5.setCodeReviewComments(null);
		bugFields5.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
			bugFields5.setErrorcode("411");
		bugFields5.setAgentName("AuMacquarie");
		bugFields5.setWorkflowStatus("Reassigned");
		sbug5.setBugFields(bugFields5);
		sbug5.setSyntheticFields(syntheticFields5);

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbug);
		synBugList.add(sbug1);
		synBugList.add(sbug2);
		synBugList.add(sbug3);
		synBugList.add(sbug4);
		synBugList.add(sbug5);

		synUtil.getDataForCSV(synBugList);
	}
}
