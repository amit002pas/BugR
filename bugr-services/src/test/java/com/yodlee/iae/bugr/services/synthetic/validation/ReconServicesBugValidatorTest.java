package com.yodlee.iae.bugr.services.synthetic.validation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.any;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

public class ReconServicesBugValidatorTest {
	
	@InjectMocks
	ReconServicesBugValidator reconServicesBugValidator;
	
	@Mock
	private ServiceMessageReader validationMessageReader;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testValidateBug() {
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%");
		bugFields.setAgentName("AuMacquarie");
		bugFields.setSuminfo("3242312");
		sbug.setBugFields(bugFields);
		reconServicesBugValidator.validateBug(sbug);
		
		bugFields.setErrorcode("");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setImpact("");
		bugFields.setAgentName("");
		bugFields.setSuminfo("");
		sbug.setBugFields(bugFields);
		reconServicesBugValidator.validateBug(sbug);
		
		bugFields.setWhiteboard("Recon_Services;SegmentId:N/A;Action:More Analysis Needed;");
		reconServicesBugValidator.validateBug(sbug);
		
		bugFields.setWhiteboard("");
		String NA="N/A";
		Mockito.when(validationMessageReader.getPropertyByKey(any(String.class))).thenReturn(NA);
		reconServicesBugValidator.validateBug(sbug);
	}
}
