package com.yodlee.iae.bugr.services.synthetic.processor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.any;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.categorize.FindBugCategoryServiceImpl;
import com.yodlee.iae.bugr.services.prioritize.BugPrioritizationServiceImpl;
import com.yodlee.iae.bugr.services.utilities.ServiceMethods;



public class ErrorSegmentBugProcessorTest {
	
	@InjectMocks
	ErrorSegmentBugProcessor errorSegmentBugProcessor;
	
	@Mock
	FindBugCategoryServiceImpl categorizerService;
	
	@Mock
	private ServiceMethods serviceMethods;
	
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
		bugFields.setWhiteboard("");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%");
		sbug.setBugFields(bugFields);
		
		String bugCategory= "Error_Code";
		Double impactPercentageFP=2.36;
		String priority="P5";
		Mockito.when(categorizerService.getCategory(any(String.class), any(String.class))).thenReturn(bugCategory);
		Mockito.when(serviceMethods.getImpactPercentage(any(String.class))).thenReturn(impactPercentageFP);
		Mockito.when(prioritizationService.getPriority(any(Double.class),any(String.class), any(String.class))).thenReturn(priority);
		errorSegmentBugProcessor.processBugContents(sbug);
		
		bugFields.setWhiteboard(", IAE_Category:Error_Code");
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setCustomer("All");
		bugFields.setImpact("");
		errorSegmentBugProcessor.processBugContents(sbug);
		
		bugFields.setComment("");
		bugFields.setCustomer("");
		errorSegmentBugProcessor.processBugContents(sbug);
	}

}
