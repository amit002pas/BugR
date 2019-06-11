package com.yodlee.iae.bugr.services.synthetic.processor;

import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.prioritize.BugPrioritizationServiceImpl;

public class ReconServicesBugProcessorTest {

	@InjectMocks
	ReconServicesBugProcessor reconServicesBugProcessor;
	
	@Mock
	BugPrioritizationServiceImpl prioritizationService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testProcessBugContents() {
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
		
		String priority="P5";
		Mockito.when(prioritizationService.getPriority(any(Double.class),any(String.class), any(String.class))).thenReturn(priority);
		
		reconServicesBugProcessor.processBugContents(sbug);
		
		
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;");
		sbug.setBugFields(bugFields);
		reconServicesBugProcessor.processBugContents(sbug);
		
		bugFields.setImpact(null);
		bugFields.setWhiteboard("");
		bugFields.setSuminfo("");
		sbug.setBugFields(bugFields);
		reconServicesBugProcessor.processBugContents(sbug);
	}	
}
