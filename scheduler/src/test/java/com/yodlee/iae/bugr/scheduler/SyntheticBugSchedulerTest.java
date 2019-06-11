/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.yodlee.iae.bugr.resources.mongo.AuditSchedulerBase;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.ClosedBugsAuditScheduler;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.bugzillasync.SyncSyntheticBugsWithBugzilla;
import com.yodlee.iae.bugr.services.synthetic.dependson.JNTTRDependsOnUpdate;

public class SyntheticBugSchedulerTest {

	@InjectMocks
	private SyntheticBugsScheduler syntheticBugsScheduler;
	
	@Mock
	private SyncSyntheticBugsWithBugzilla syncSyntheticBugsWithBugzilla;

	@Mock
	private ApplicationContext context;

	@Mock
	private MongoOperations mongoOperations;

	@Mock
	JNTTRDependsOnUpdate jNTTRDependsOnUpdate;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSyncSyntheticBugsWithBugzilla() {
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

		List<SyntheticBug> synBugList = new ArrayList<>();
		synBugList.add(sbug);
		
		Mockito.when(syncSyntheticBugsWithBugzilla.process(any(Date.class))).thenReturn(synBugList);
		syntheticBugsScheduler.syncSyntheticBugsWithBugzilla();
		
		ClosedBugsAuditScheduler value = new ClosedBugsAuditScheduler();
		List<String> bugIdList = new ArrayList<>();
		bugIdList.add("1232141asabds");
		value.setSyntheticBugIds(bugIdList);
		Mockito.when(mongoOperations.findOne(any(Query.class), ClosedBugsAuditScheduler.class, mongoOperations.getCollectionName(AuditSchedulerBase.class))).thenReturn(value);
		
	}
	
	@Test
	public void testSyncSyntheticBugsWithBugzillaAuditCase() {
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

		List<SyntheticBug> synBugList = new ArrayList<>();
		synBugList.add(sbug);
		
		ClosedBugsAuditScheduler value = new ClosedBugsAuditScheduler();
		List<String> bugIdList = new ArrayList<>();
		bugIdList.add("1232141asabds");
		value.setSyntheticBugIds(bugIdList);
		
		Query query = new Query();
		query.limit(1);
		query.with(new Sort(Sort.Direction.DESC, "createdDate"));
		Mockito.when(mongoOperations.findOne(query, ClosedBugsAuditScheduler.class, mongoOperations.getCollectionName(AuditSchedulerBase.class))).thenReturn(value);
		
		Mockito.when(syncSyntheticBugsWithBugzilla.process(any(Date.class))).thenReturn(synBugList);
		syntheticBugsScheduler.syncSyntheticBugsWithBugzilla();
	}
	
	@Test
	public void testCacheTTRandJNSyntheticBugs() {
		syntheticBugsScheduler.cacheTTRandJNSyntheticBugs();
	}

}
