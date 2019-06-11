/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.rest.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.services.jn.jnsimialrbug.JnSimilarClosedBug;


public class JnSimilarBugRestImplTest {

	@InjectMocks
	private JnSimilarBugRestImpl jnSimilarBugRestImplImpl;
	
	@Mock
	private JnSimilarClosedBug jnSimilarClosedBug;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetJnSimilarClosedBugs() {
		
	String param="{\r\n" + 
			"    \"startTime\": \"4324323234\",\r\n" + 
			"    \"endTime\": \"43452432432432\"\r\n" + 
			"  }\r\n" + 
			"";
	jnSimilarBugRestImplImpl.getJnSimilarClosedBugs(param);
	}
	
	@Test
	public void testGetTTRBugsCatchCase() {
		
	String param="{\r\n" + 
			"  \"timeStamp\": {\r\n" + 
			"    \"startTime\": \"2019-02-13 01:16:57\",\r\n" + 
			"    \"endTime\": \"2019-03-06 01:16:57\"\r\n" + 
			"  }\r\n" + 
			"}";
	jnSimilarBugRestImplImpl.getJnSimilarClosedBugs(param);
	}
}
