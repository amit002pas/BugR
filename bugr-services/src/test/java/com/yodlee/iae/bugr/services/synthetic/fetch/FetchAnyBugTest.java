package com.yodlee.iae.bugr.services.synthetic.fetch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;


public class FetchAnyBugTest {

	@InjectMocks
	FetchAnyBug fetchAnyBug;
	
	@Mock
	private MongoOperations mongoOperations;

	@Mock
	private IBugzillaRPCClient bugzillaGateway;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		//ReflectionTestUtils.setField(fetchAnyBug, bugObjectOpt, "[class com.yodlee.iae.bugr.services.synthetic.fetch.FetchAnyBug]");
	}
	
	@Test
	public void testFetchAnyBug(){
		String bugId="BAID-5c7cd24e8bc89837d46cc671-BGCR";
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
		Mockito.when(mongoOperations.findById(any(String.class), eq(SyntheticBug.class))).thenReturn(sbug);
		fetchAnyBug.process(bugId);
		
		bugId="10231223";
		Bug bug = null;
		Mockito.when(bugzillaGateway.fetchBug(any(Integer.class))).thenReturn(bug);
		fetchAnyBug.process(bugId);
		
		bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug();
		Mockito.when(bugzillaGateway.fetchBug(any(Integer.class))).thenReturn(bug);
		fetchAnyBug.process(bugId);
		
		Mockito.when(bugzillaGateway.fetchBug(any(Integer.class))).thenThrow(new NullPointerException());
		fetchAnyBug.process(bugId);
		
	}
}
