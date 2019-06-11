/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.gateway.bugzilla.base;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import com.j2bugzilla.rpc.GetBug;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.gateway.bugzilla.resource.BugRequest;

public class BugzillaConnectorTest {
	
	@InjectMocks
	private BugzillaConnector bugzillaConnector;
	
	@Mock
	private com.j2bugzilla.base.BugzillaConnector con;
	
	@Value("${bugzilla.userName}")
	private String userName;

	@Value("${bugzilla.password}")
	private String password;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testExecuteMethod() {
		String bugId = "132213";
		GetBug bug = new GetBug(bugId);
		bugzillaConnector.executeMethod(new BugRequest(bug, userName, password));
		
		bugId = "324231";
		bug = new GetBug(bugId);
		bugzillaConnector.executeMethod(new BugRequest(bug, userName, password));
		
		Map<Object, Object> queryMap = new HashMap<Object, Object>();
		queryMap.put(BugzillaConstants.SHORT_DESC, "<TTR-ALERT>");
		queryMap.put(BugzillaConstants.STATUS, "New");
		com.yodlee.iae.bugr.gateway.bugzilla.resource.BugSearch bugSearch = new com.yodlee.iae.bugr.gateway.bugzilla.resource.BugSearch(queryMap);
		bugzillaConnector.executeMethod(new BugRequest(bugSearch, userName, password));
		
		bugId = "432234";
		bug = new GetBug(bugId);
		bugzillaConnector.executeMethod(new BugRequest(bug, userName, password));
		
		bugId = "546456";
		bug = new GetBug(bugId);
		bugzillaConnector.executeMethod(new BugRequest(bug, userName, password));
		
		bugzillaConnector.executeMethod(new BugRequest(bugSearch, userName, password));
		bugzillaConnector.executeMethod(new BugRequest(bugSearch, userName, password));
		bugzillaConnector.executeMethod(new BugRequest(bugSearch, userName, password));
		
		bugzillaConnector.executeMethod(new BugRequest(bugSearch, userName, password));
		bugzillaConnector.executeMethod(new BugRequest(bugSearch, userName, password));
	}

}
