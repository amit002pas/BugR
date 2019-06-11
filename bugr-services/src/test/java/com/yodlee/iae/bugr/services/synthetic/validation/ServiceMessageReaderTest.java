package com.yodlee.iae.bugr.services.synthetic.validation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:ServiceValidation.properties")
public class ServiceMessageReaderTest {

	@InjectMocks
	private ServiceMessageReader serviceMessageReader;
	
	@Mock
	private Environment environment;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetPropertyByKey() {
		String key = "reconservices.suminfoError";
		serviceMessageReader.getPropertyByKey(key);
	}
}
