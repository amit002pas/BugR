package com.yodlee.iae.bugr.services.synthetic.processor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

public class DefaultBugProcessorTest {

	@InjectMocks
	DefaultBugProcessor defaultBugProcessor;
	
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
		bugFields.setWhiteboard("");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%");
		sbug.setBugFields(bugFields);
		
		defaultBugProcessor.processBugContents(sbug);
	
	}
}
