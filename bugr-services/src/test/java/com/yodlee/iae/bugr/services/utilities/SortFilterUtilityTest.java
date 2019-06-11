/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.utilities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.google.gson.Gson;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;

public class SortFilterUtilityTest {
	
	private static Gson gson = new Gson();
	
	@Mock
	private CachedBugsServiceImpl cachedBugsServiceImpl;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);}
	
	@Test
	public void testGetSimilarBugsByStackTraces() {
		
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
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);
		bugProps.put("cf_agents", "AuMacquarie,Aumacquarie1");
		bugProps.put("cf_errorcode", "403 ");
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		SortFilterUtility.getSimilarBugsByStackTrace(bugList, bugList);
	}
	
	@Test
	public void testGetSimilarBugsByStackTracesTrimCase() {
		
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
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);
		bugProps.put("cf_agents", "AuMacquarie,Aumacquarie1");
		bugProps.put("cf_errorcode", "403 ");
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		
		
		BugEntity bugEntity1 = new BugEntity();
		bugEntity1.setComment("As a part of Orphic testing");
		bugEntity1.setCf_initiative("IAE");
		bugEntity1.setProduct("IAE");
		bugEntity1.setComponent("Agent");
		bugEntity1.setSummary("<Issue> Dummy issue Proactive monitoring created for 7503");
		bugEntity1.setVersion("2131");
		bugEntity1.setCf_environment("Production");
		bugEntity1.setCf_department("IAE");
		bugEntity1.setCf_workflow("New");
		bugEntity1.setCf_customer("All");
		bugEntity1.setCf_portfolio("Recon_Servces");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps1 = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps1 = bugProps1.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps1.put("cf_site_id", "1233");
		bugProps1.put("id", 2222);
		bugProps1.put("cf_agents", "AuMacquarie");
		bugProps1.put("cf_errorcode", "403 ");
		bugProps1.put("cf_code_review_comments_gen", "com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		Bug bug1 = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps1);
		bugList.add(bug);
		bugList.add(bug1);
		
		SortFilterUtility.getSimilarBugsByStackTrace(bugList, bugList);
	}
	
	@Test
	public void testGetSimilarBugsByStackTraces1() {
		
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
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);
		bugProps.put("cf_agents", "");
		bugProps.put("cf_errorcode", "");
		bugProps.put("cf_code_review_comments_gen", "");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		SortFilterUtility.getSimilarBugsByStackTrace(bugList, bugList);
	}
	
	
	@Test
	public void testFilterBugsByLastUpdatedTime() throws ParseException {
		
		int numOfHrs=30000000;
		
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
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28;");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);
		bugProps.put("cf_agents", "AuMacquarie,Aumacquarie1");
		bugProps.put("cf_errorcode", "403 ");
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		
		
		BugEntity bugEntity1 = new BugEntity();
		bugEntity1.setComment("As a part of Orphic testing");
		bugEntity1.setCf_initiative("IAE");
		bugEntity1.setProduct("IAE");
		bugEntity1.setComponent("Agent");
		bugEntity1.setSummary("<Issue> Dummy issue Proactive monitoring created for 7503");
		bugEntity1.setVersion("2131");
		bugEntity1.setCf_environment("Production");
		bugEntity1.setCf_department("IAE");
		bugEntity1.setCf_workflow("New");
		bugEntity1.setCf_customer("All");
		bugEntity1.setCf_portfolio("Recon_Servces");
		bugEntity1.setCf_impact("Refresh Count: 0;Failure Count: 48;Predicted Failure: 40;Impact Percentage: 12.77;");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps1 = gson.fromJson(gson.toJson(bugEntity1), HashMap.class);
		bugProps1 = bugProps1.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps1.put("cf_site_id", "1233");
		bugProps1.put("id", 2222);
		bugProps1.put("cf_agents", "AuMacquarie");
		bugProps1.put("cf_errorcode", "403 ");
		bugProps1.put("cf_code_review_comments_gen", "com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps1.put("last_change_time", "Wed Oct 16 00:00:00 CEST 2013");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		Bug bug1 = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps1);
		bugList.add(bug);
		bugList.add(bug1);
		
		SortFilterUtility.filterBugsByLastUpdatedTime(bugList, numOfHrs);
		
		
		int numOfHrsfailCase=150;
		SortFilterUtility.filterBugsByLastUpdatedTime(bugList, numOfHrsfailCase);
	}
	
	@Test
	public void testFilterBugsByLastUpdatedTimeNumHrs() throws ParseException {
		
		int numOfHrs=30;
		
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
		bugEntity.setCf_impact("Updated At:2019-04-03 06:28;");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);
		bugProps.put("cf_agents", "AuMacquarie,Aumacquarie1");
		bugProps.put("cf_errorcode", "403 ");
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		
		
		BugEntity bugEntity1 = new BugEntity();
		bugEntity1.setComment("As a part of Orphic testing");
		bugEntity1.setCf_initiative("IAE");
		bugEntity1.setProduct("IAE");
		bugEntity1.setComponent("Agent");
		bugEntity1.setSummary("<Issue> Dummy issue Proactive monitoring created for 7503");
		bugEntity1.setVersion("2131");
		bugEntity1.setCf_environment("Production");
		bugEntity1.setCf_department("IAE");
		bugEntity1.setCf_workflow("New");
		bugEntity1.setCf_customer("All");
		bugEntity1.setCf_portfolio("Recon_Servces");
		bugEntity1.setCf_impact("Refresh Count: 0;Failure Count: 48;Predicted Failure: 40;Impact Percentage: 12.77;");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps1 = gson.fromJson(gson.toJson(bugEntity1), HashMap.class);
		bugProps1 = bugProps1.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps1.put("cf_site_id", "1233");
		bugProps1.put("id", 2222);
		bugProps1.put("cf_agents", "AuMacquarie");
		bugProps1.put("cf_errorcode", "403 ");
		bugProps1.put("cf_code_review_comments_gen", "com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps1.put("last_change_time", "Wed Oct 16 00:00:00 CEST 2013");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		Bug bug1 = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps1);
		bugList.add(bug);
		bugList.add(bug1);
		
		SortFilterUtility.filterBugsByLastUpdatedTime(bugList, numOfHrs);
		
		
		int numOfHrsfailCase=150;
		SortFilterUtility.filterBugsByLastUpdatedTime(bugList, numOfHrsfailCase);
	}
	
	@Test
	public void testFilterBugsByLastUpdatedTimeSuccessCase() throws ParseException {
		
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
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);
		bugProps.put("cf_agents", "AuMacquarie,Aumacquarie1");
		bugProps.put("cf_errorcode", "403 ");
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		
		int numOfHrsfailCase=150;
		SortFilterUtility.filterBugsByLastUpdatedTime(bugList, numOfHrsfailCase);
	}
	
	@Test
	public void testFilterIATBugs() {
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
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "IAT");
		bugProps.put("status", "NEW");
		bugProps.put("creator", "Serviceissueanalyzertool");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		SortFilterUtility.filterIATBugs(bugList);
	}
	@Test
	public void testFilterIATBugsFailCase() {
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
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "IAT");
		bugProps.put("status", "NEW");
		bugProps.put("creator", "presr");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		SortFilterUtility.filterIATBugs(bugList);
	}
	
	@Test
	public void testFilterTTRBugs() {
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
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "TTR_I18N");
		bugProps.put("status", "NEW");
		bugProps.put("creator", "presr");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		SortFilterUtility.filterTTRBugs(bugList);
	}
	
	@Test
	public void testFilterPreSRBugs() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("Proactive Monitoring Bugs created by PreSR AgentName: accntSumBanks ErrorCode:403 COBRAND ID:10005640");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "TTR,PreSR_ErrorSegment");
		bugProps.put("status", "NEW");
		bugProps.put("creator", "presr");
		bugProps.put("cf_errorcode", "403 ");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		List<String> reconWealthErrorCodesList = Arrays.asList("563,7300,7301,7302,7303,7304,7305,7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7321,7323,7324,7325,7326,7327,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7415,7419,7420,7422,7424,7500,7501,7502,7503,7505,7506,7508,7600,7601,7602");
		List<String> reconBankErrorCodesList = Arrays.asList("3000,3001,3002,3300,3600,3601,3602,3603,3604");
		
		SortFilterUtility.filterPreSRBugs(bugList, reconWealthErrorCodesList, reconBankErrorCodesList);
	}
	
	@Test
	public void testFilterPreSRBugsSummaryCase() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("REPORTED ERROR :");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "IAT,PreSR_ErrorSegment");
		bugProps.put("status", "NEW");
		bugProps.put("creator", "Serviceissueanalyzertool");
		bugProps.put("cf_errorcode", "403 ");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		List<String> reconWealthErrorCodesList = Arrays.asList("563,7300,7301,7302,7303,7304,7305,7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7321,7323,7324,7325,7326,7327,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7415,7419,7420,7422,7424,7500,7501,7502,7503,7505,7506,7508,7600,7601,7602");
		List<String> reconBankErrorCodesList = Arrays.asList("3000,3001,3002,3300,3600,3601,3602,3603,3604");
		
		SortFilterUtility.filterPreSRBugs(bugList, reconWealthErrorCodesList, reconBankErrorCodesList);
	}
	
	@Test
	public void testFilterPreSRBugsSummaryCaseWhiteboard1() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("REPORTED ERROR :");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "IAT");
		bugProps.put("status", "RESOLVED");
		bugProps.put("creator", "Testtingtool");
		bugProps.put("cf_errorcode", "403 ");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		List<String> reconWealthErrorCodesList = Arrays.asList("563,7300,7301,7302,7303,7304,7305,7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7321,7323,7324,7325,7326,7327,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7415,7419,7420,7422,7424,7500,7501,7502,7503,7505,7506,7508,7600,7601,7602");
		List<String> reconBankErrorCodesList = Arrays.asList("3000,3001,3002,3300,3600,3601,3602,3603,3604");
		
		SortFilterUtility.filterPreSRBugs(bugList, reconWealthErrorCodesList, reconBankErrorCodesList);
	}
	
	@Test
	public void testFilterPreSRBugsSummaryCaseWhiteboard2() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("REPORTED ERROR :");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "IAT");
		bugProps.put("status", "New");
		bugProps.put("creator", "Serviceissueanalyzertool");
		bugProps.put("cf_errorcode", "403 ");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		List<String> reconWealthErrorCodesList = Arrays.asList("563,7300,7301,7302,7303,7304,7305,7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7321,7323,7324,7325,7326,7327,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7415,7419,7420,7422,7424,7500,7501,7502,7503,7505,7506,7508,7600,7601,7602");
		List<String> reconBankErrorCodesList = Arrays.asList("3000,3001,3002,3300,3600,3601,3602,3603,3604");
		
		SortFilterUtility.filterPreSRBugs(bugList, reconWealthErrorCodesList, reconBankErrorCodesList);
	}
	
	@Test
	public void testFilterPreSRBugsSummaryCaseWhiteboard3() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("REPORTED ERROR :");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		bugEntity.setCf_impact("Updated At:2019-03-26 06:28");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_code_review_comments_gen", "Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.RequiredFieldUnavailableException: Errors encountered during validation, errors are --> balanceValidation : Total balance!=sum of all holdings value+marginBalance+cash-401kLoan	at com.yodlee.dap.gatherer.vali||{\"274d7a54-60a5-43df-94bc-013301d12b0b\":\"1#40\"}");
		bugProps.put("whiteboard", "IAT_TESTING_INVALID");
		bugProps.put("status", "CLOSED");
		bugProps.put("creator", "Serviceissueanalyzertool");
		bugProps.put("cf_errorcode", "403 ");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		
		List<String> reconWealthErrorCodesList = Arrays.asList("");
		List<String> reconBankErrorCodesList = Arrays.asList("");
		
		SortFilterUtility.filterPreSRBugs(bugList, reconWealthErrorCodesList, reconBankErrorCodesList);
	}
	
	
}
