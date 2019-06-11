package com.yodlee.iae.bugr.services.synthetic.validation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

public class DefaultBugValidatorTest {

	@InjectMocks
	DefaultBugValidator defaultBugValidator;

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
		bugFields.setWhiteboard("");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%");
		bugFields.setAgentName("AuMacquarie");
		sbug.setBugFields(bugFields);
		defaultBugValidator.validateBug(sbug);
	}

}
