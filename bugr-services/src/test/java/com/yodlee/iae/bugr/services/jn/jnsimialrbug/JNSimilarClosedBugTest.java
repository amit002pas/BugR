/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.jn.jnsimialrbug;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.google.gson.Gson;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.CachedBugsData;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.services.jn.jnsimialrbug.JnSimilarClosedBug;
import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;

public class JNSimilarClosedBugTest {

	@InjectMocks
	private JnSimilarClosedBug jnSimilarClosedBug;
	
	@Mock
	private SyntheticBugRepository syntheticBugRepository;
	@Mock
	private CachedBugsServiceImpl cachedBugsServiceImpl;
	@Mock
	CachedBugsData cachedBugsData;
	
	private static Gson gson = new Gson();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetTTRBugs() throws ParseException {
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1="2019-02-24 14:04:33";
		String date2="2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue><TTR-ALERT> Dummy issue Proactive monitoring created for 7503");
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
		bugProps.put("status","NEW");
		bugProps.put("creator","Serviceissueanalyzertool");
		bugProps.put("whiteboard","IAT_TESTING_INVALI");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		cachedBugsData.setBugs(bugList);
		
		Mockito.when(cachedBugsServiceImpl.getCachedBugsData()).thenReturn(cachedBugsData);
		jnSimilarClosedBug.process(timeStamp);
	}
}
