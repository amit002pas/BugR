package com.yodlee.iae.bugr.services.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ServiceMethodsTest {

	@InjectMocks
	private ServiceMethods serviceMethods;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetImpactPercentage() {
		
		String impactString ="Refresh Count: 0;Failure Count: 15;Predicted Failure: 1792;Impact Percentage: 83.33;Updated At: 2019-03-04 00:17";
		serviceMethods.getImpactPercentage(impactString);
		
		String impactString1 ="Refresh Count: 0;Failure Count: 15;Predicted Failure: 1792;Updated At: 2019-03-04 00:17";
		serviceMethods.getImpactPercentage(impactString);
		
	}
}
