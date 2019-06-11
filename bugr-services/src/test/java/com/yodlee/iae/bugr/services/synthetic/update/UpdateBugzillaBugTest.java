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
import org.springframework.data.mongodb.core.MongoOperations;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;

import java.util.Map;

import com.j2bugzilla.base.Attachment;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;


public class UpdateBugzillaBugTest {

	@InjectMocks
	UpdateBugzillaBug updateBugzillaBug;
	
	@Mock
	private MongoOperations mongoOperations;

	@Mock
	private IBugzillaRPCClient bugzillaGateway;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testUpdateBugzillaBug(){
		String synId="5c5d6857ae6f506dc47f277e";
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFields.setWorkflowStatus("New");
		bugFields.setBugStatus("New");
		bugFields.setDepends_on("314821");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		sbug.setSyntheticFields(syntheticFields);
		sbug.setSyntheticBugid("BAID-5c7cd24e8bc89837d46cc671-BGCR");
		
		Mockito.when(mongoOperations.findById(any(String.class), eq(SyntheticBug.class))).thenReturn(sbug);
		updateBugzillaBug.process(synId);
	}
	
	@Test
	public void testUpdateBugzillaBug1(){
		String synId="5c7cd24e8bc89837d46cc671";
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFields.setWorkflowStatus("Closed");
		bugFields.setBugStatus("New");
		bugFields.setDepends_on("314821,387241");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setPortfolio(Portfolio.PRE_SR);
		syntheticFields.setSyntheticBugStatus(SyntheticBugStatus.INVALID);
		syntheticFields.setBugzillaBugId(1239238213);
		sbug.setSyntheticFields(syntheticFields);
		sbug.setSyntheticBugid("BAID-5c7cd24e8bc89837d46cc671-BGCR");
		
		Mockito.doNothing().when(bugzillaGateway).updateBug(any(Map.class), any(Attachment.class));
		Mockito.when(mongoOperations.findById(any(String.class), eq(SyntheticBug.class))).thenReturn(sbug);
		updateBugzillaBug.process(synId);
	}
	
	@Test
	public void testUpdateBugzillaBug2(){
		String synId="5c7cd24e8bc89837d46cc671";
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFields.setWorkflowStatus("New");
		bugFields.setBugStatus("New");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setPortfolio(Portfolio.PRE_SR);
		syntheticFields.setSyntheticBugStatus(SyntheticBugStatus.INVALID);
		syntheticFields.setBugzillaBugId(1239238213);
		sbug.setSyntheticFields(syntheticFields);
		sbug.setSyntheticBugid("BAID-5c7cd24e8bc89837d46cc671-BGCR");
		doThrow(new NullPointerException()).when(bugzillaGateway).updateBug(any(Map.class), any(Attachment.class));
		Mockito.when(mongoOperations.findById(any(String.class), eq(SyntheticBug.class))).thenReturn(sbug);
		updateBugzillaBug.process(synId);
	}
}
