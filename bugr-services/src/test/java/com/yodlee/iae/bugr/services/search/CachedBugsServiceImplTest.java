/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.gson.Gson;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.CachedBugsData;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaGatewayImpl;

public class CachedBugsServiceImplTest {

	@InjectMocks
	private CachedBugsServiceImpl cachedBugsServiceImpl;
	
	@Mock
	private BugzillaGatewayImpl bugzillaGateway;
	
	@Mock
	private Bug bug;
	
	private static Gson gson = new Gson();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(cachedBugsServiceImpl, "reconWealthErrorCodes", "563,7300,7301,7302,7303,7304,7305,7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7321,7323,7324,7325,7326,7327,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7415,7419,7420,7422,7424,7500,7501,7502,7503,7505,7506,7508,7600,7601,7602");
		ReflectionTestUtils.setField(cachedBugsServiceImpl, "reconBankErrorCodes", "3000,3001,3002,3300,3600,3601,3602,3603,3604");
	}
	
	@Test
	public void testFetchBugs() throws ConnectionException, BugzillaException {
		int numDays=1;
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
		bugProps.put("cf_errorcode", "403");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		Mockito.when(bugzillaGateway.getBugsFromBugzilla(numDays)).thenReturn(bugList);
		cachedBugsServiceImpl.fetchBugs(numDays);
		cachedBugsServiceImpl.getCachedBugsData();
		CachedBugsData cachedBugsData = null;
		cachedBugsServiceImpl.setCachedBugsData(cachedBugsData);
		cachedBugsServiceImpl.evictCache();
		cachedBugsServiceImpl.execute();
	}
	
	@Test
	public void testFetchBugsCatchCase() throws ConnectionException, BugzillaException {
		int numDays=1;
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
		bugProps.put("cf_errorcode", "403");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		Mockito.when(bugzillaGateway.getBugsFromBugzilla(numDays)).thenThrow(new BugzillaException("Failed"));
		cachedBugsServiceImpl.fetchBugs(numDays);
		cachedBugsServiceImpl.getCachedBugsData();
		CachedBugsData cachedBugsData = null;
		cachedBugsServiceImpl.setCachedBugsData(cachedBugsData);
		cachedBugsServiceImpl.evictCache();
		cachedBugsServiceImpl.execute();
	}
}
