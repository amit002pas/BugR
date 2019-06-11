/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.update;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.mockito.Matchers.any;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;


public class UpdateSyntheticBugTest {
	
	@InjectMocks
	UpdateSyntheticBug updateSyntheticBug;
	
	@Mock
	private SyntheticBugRepository syntheticBugRepository;
	
	@Mock
	private MongoOperations mongoOperations;
	
	@Mock
	private ApplicationContext ctx;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateSynBug(){
		BugEntity bugEntity=  new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_workflow_status("Closed");
		bugEntity.setBug_status("RESOLVED");
		bugEntity.setResolution("INVALID");
		
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
		sbug.setSyntheticFields(syntheticFields);
		sbug.setSyntheticBugid("BAID-5c7cd24e8bc89837d46cc671-BGCR");
		
		Mockito.when(syntheticBugRepository.getSyntheticBugFromBugzillaId(any(Integer.class))).thenReturn(sbug);
		updateSyntheticBug.process(bugEntity);
		
		
		
		BugEntity bugEntity1=  new BugEntity();
		bugEntity1.setComment("As a part of Orphic testing");
		bugEntity1.setCf_workflow_status("New");
		bugEntity1.setBug_status("NEW");
		bugEntity1.setResolution("");
		
		SyntheticBug sbug1 = new SyntheticBug();
		BugFields bugFields1 = new BugFields();
		bugFields1.setComment("As a part of Orphic testing");
		bugFields1.setErrorcode("403");
		bugFields1.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields1.setCustomer("All");
		bugFields1.setSuminfo("4479");
		bugFields1.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug1.setBugFields(bugFields1);
		
		SyntheticFields syntheticFields1 = new SyntheticFields();
		syntheticFields1.setBugzillaBugCreated(true);
		syntheticFields1.setBugzillaBugId(1012021);
		syntheticFields1.setSyntheticBugStatus(SyntheticBugStatus.ACTIVE);
		sbug1.setSyntheticFields(syntheticFields1);
		sbug1.setSyntheticBugid("BAID-5c7cd24e8bc89837d46cc671-BGCR");
		
		
		Mockito.when(syntheticBugRepository.getSyntheticBugFromBugzillaId(any(Integer.class))).thenReturn(sbug1);
		updateSyntheticBug.process(bugEntity1);
		
		
		
		BugEntity bugEntity2=  new BugEntity();
		bugEntity2.setComment("As a part of Orphic testing");
		bugEntity2.setCf_workflow_status("New");
		bugEntity2.setBug_status("NEW");
		bugEntity2.setResolution("");
		
		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("403");
		bugFields2.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSuminfo("4479");
		bugFields2.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug2.setBugFields(bugFields2);
		
		SyntheticFields syntheticFields2 = new SyntheticFields();
		syntheticFields2.setBugzillaBugCreated(true);
		syntheticFields2.setBugzillaBugId(1012021);
		syntheticFields2.setSyntheticBugStatus(SyntheticBugStatus.INVALID);
		sbug2.setSyntheticFields(syntheticFields2);
		sbug2.setSyntheticBugid("BAID-5c7cd24e8bc89837d46cc671-BGCR");
		
		
		Mockito.when(syntheticBugRepository.getSyntheticBugFromBugzillaId(any(Integer.class))).thenReturn(sbug2);
		updateSyntheticBug.process(bugEntity2);
		
		
		BugEntity bugEntity3=  new BugEntity();
		bugEntity3.setComment("As a part of Orphic testing");
		bugEntity3.setCf_workflow_status("New");
		bugEntity3.setBug_status("NEW");
		bugEntity3.setResolution("");
		bugEntity3.setSynId("BAID-5c7cd24e8bc89837d46cc671-BGCR");
		
		SyntheticBug sbug3 = new SyntheticBug();
		BugFields bugFields3 = new BugFields();
		bugFields3.setComment("As a part of Orphic testing");
		sbug3.setBugFields(bugFields3);
		
		SyntheticFields syntheticFields3 = new SyntheticFields();
		syntheticFields3.setSyntheticBugStatus(SyntheticBugStatus.INACTIVE);
		sbug3.setSyntheticFields(syntheticFields3);
		
		
		Mockito.when(syntheticBugRepository.getSyntheticBugFromBugzillaId(any(Integer.class))).thenReturn(sbug3);
		updateSyntheticBug.process(bugEntity3);
	}

}
