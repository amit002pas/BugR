package com.yodlee.iae.bugr.services.synthetic.exception;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SyntheticExceptionTest {
	
	@InjectMocks
	SyntheticException syntheticException;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetMessage() {
		syntheticException.getMessage();
	}
	
	@Test
	public void testSetMessage() {
		String message="Bug Creation failed";
		syntheticException.setMessage(message);
	}

}
