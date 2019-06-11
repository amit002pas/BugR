package com.yodlee.iae.bugr.services.synthetic.create;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;

import com.j2bugzilla.base.Attachment;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

public class CreateBugzillaBugTest {

	@InjectMocks
	CreateBugzillaBug createBugzillaBug;
	
	@Mock
	private MongoOperations mongoOperations;

	@Mock
	private IBugzillaRPCClient bugzillaGateway;

	@Mock
	private SynUtil synUtil;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateBugzillaBug(){
		String synId="5c5d6857ae6f506dc47f277e";
		String bugId="213213412";
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
		
		Mockito.when(bugzillaGateway.createBug(any(Map.class), any(Attachment.class))).thenReturn(bugId);
		Mockito.when(mongoOperations.findById(any(String.class), eq(SyntheticBug.class))).thenReturn(sbug);
		createBugzillaBug.process(synId);
	}
	
	@Test
	public void testCreateBugzillaBugCatchCase(){
		String synId="5c5d6857ae6f506dc47f277e";
		String bugId="213213412";
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
		
		Mockito.when(bugzillaGateway.createBug(any(Map.class), any(Attachment.class))).thenThrow(new NullPointerException());
		Mockito.when(mongoOperations.findById(any(String.class), eq(SyntheticBug.class))).thenReturn(sbug);
		createBugzillaBug.process(synId);
	}
}
