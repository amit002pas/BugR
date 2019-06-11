package com.yodlee.iae.bugr.rest.validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import com.mysql.fabric.xmlrpc.base.Array;

public class ValidationMessageReaderTest {
	
	@Mock
	private Environment env;
	
	@InjectMocks
	private ValidationMessageReader validationMessageReader;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	//	ReflectionTestUtils.setField( validationMessageReader, "update.bugid", "BugID cannot be empty");
	//	ReflectionTestUtils.setField( validationMessageReader, "update.bugresolved", "update.bugresolved");
	//	ReflectionTestUtils.setField( validationMessageReader, "update.bugstat", "Resolution should be empty if Bug Status is null/empty or Bug status is NEW/ASSIGNED");
		//ReflectionTestUtils.set
	}
	
	@Test
	public void testGetPropertByKey(){
		List<String> strlist = new ArrayList<>();
		strlist.add(ValidationConstants.UPDATE_BUGID);
		strlist.add(ValidationConstants.UPDATE_BUGRESOLVED);
		strlist.add(ValidationConstants.UPDATE_BUGSTAT);
		validationMessageReader.getPropertyByKey(strlist);
	}
}
