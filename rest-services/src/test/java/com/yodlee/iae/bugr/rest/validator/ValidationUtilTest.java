package com.yodlee.iae.bugr.rest.validator;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.any;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.resources.responses.SyntheticBaseResponse;

public class ValidationUtilTest {
	
	@InjectMocks
	ValidationUtil validationUtil;
	
	@Mock
	private ValidationMessageReader reader;
	
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetResponseFromValidationMessages(){
		SyntheticBaseResponse response = new SyntheticBaseResponse();
		List<String> messages = new ArrayList<>();
		messages.add("Comment length is greater than 65535 characters");
		validationUtil.getResponseFromValidationMessages(response, messages);
		
		List<String> messages2 = new ArrayList<>();
		messages.add("");
		validationUtil.getResponseFromValidationMessages(response, messages2);
		
		List<String> messages3 = new ArrayList<>();
		messages.add(null);
		validationUtil.getResponseFromValidationMessages(response, messages3);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetFieldLengthFailures(){
		BugEntity bugEntity = new BugEntity();
		bugEntity.setStatus_whiteboard("status_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not found status_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not found status_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not found status_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not found status_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not found status_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundstatus_whiteboard Stacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not foundStacktrace: Login Page not found");
		bugEntity.setSummary("summary");
		bugEntity.setCf_agent_status("New");
		bugEntity.setCf_suminfo("1234");
		bugEntity.setCf_memitem("2374232");
		bugEntity.setCf_errorcode("412");
		bugEntity.setCf_impact("Stacktrace: Login Page not found");
		bugEntity.setCf_service_request_id("93284");
		bugEntity.setCf_mem_site_acc_id("2342");
		bugEntity.setCf_site_id("3242");
		bugEntity.setCf_agent_version("342");
		bugEntity.setCf_code_review_comments_gen("Stacktrace: Login Page not found");
		bugEntity.setCf_readme("873123");
		bugEntity.setCf_p4_label("234123");
		List<String> listresp= new ArrayList<>();
		listresp.add("WhiteBoard length is greater than 254 characters");
		Mockito.when(reader.getPropertyByKey(any(ArrayList.class))).thenReturn(listresp);
		validationUtil.getFieldLengthFailures(bugEntity);
		//validationUtil.getFieldLengthFailures(bugEntity);
	}

}
