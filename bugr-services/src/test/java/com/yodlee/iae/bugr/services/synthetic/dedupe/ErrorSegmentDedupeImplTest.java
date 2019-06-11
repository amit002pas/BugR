/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.dedupe;

import java.util.ArrayList;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Matchers.any;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

public class ErrorSegmentDedupeImplTest {

	@InjectMocks
	ErrorSegmentDedupeImpl errorSegmentDedupeImpl;
	
	@Mock
	private SyntheticBugRepository syntheticBugRepository;

	@Mock
	private MongoOperations mongoOperations;
	
	@Mock
	private SynUtil synUtil;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindDuplicateBug() {
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact("Impact Percentage:2.36%");
		bugFieldsDup.setWorkflowStatus("New");
		sbugDup.setBugFields(bugFieldsDup);
		
		List<SyntheticBug> synBugList = new ArrayList<>();
		synBugList.add(sbugDup);
		//Mockito.when(synUtil.STACK_TRACE_MATCHER.test(any(String.class),any(String.class))).thenReturn(true);
		//Mockito.when(syntheticBugRepository.getDuplicatePreSrBug(any(String.class), any(String.class))).thenReturn(synBugList);
		errorSegmentDedupeImpl.findDuplicateBug(sbugDup);
		
	}
	
	@Test
	public void testFindDuplicateBug2() {
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact("Impact Percentage:2.36%");
		bugFieldsDup.setBugStatus("NEW");
		bugFieldsDup.setCodeReviewComments("Stack Trace:org.openqa.selenium.NoSuchElementException: unable to locate elementFor documentation on this error, please visit: http://seleniumhq.org/exceptions/no_such_element.htmlBuild info: version: '2.49.1', revision: '808c23b0963853d375cbe54b90bbd052e2528a54||{\"c2954c67-b8b8-4135-a901-3fabf456dfb2\":\"11#100\",\"f2fc0909-f1c9-4595-a144-047c6db7e508\":\"1#17\",\"5475462b-7e55-4d91-a659-1ed66afa6b08\":\"1#2\",\"d6895998-b238-47a5-8d80-5e345131e50f\":\"1#1\",\"58cf472b-7e7a-4bfe-851d-cd50610f5e71\":\"4#44\",\"0b381e07-9d50-4bcf-bddf-9aeec835aafe\":\"2#10\"}");
		sbugDup.setBugFields(bugFieldsDup);
		
		List<SyntheticBug> synBugList = new ArrayList<>();
		synBugList.add(sbugDup);
		//Mockito.when(synUtil.STACK_TRACE_MATCHER.test(any(String.class),any(String.class))).thenReturn(true);
		Mockito.when(syntheticBugRepository.getDuplicatePreSrBug(any(String.class), any(String.class))).thenReturn(synBugList);
		errorSegmentDedupeImpl.findDuplicateBug(sbugDup);
		
	}
	
	@Test
	public void testUpdateDuplicateBug() {
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%");
		sbug.setBugFields(bugFields);
		
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing2");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact("Impact Percentage:2.36%");
		sbugDup.setBugFields(bugFieldsDup);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		sbugDup.setSyntheticFields(syntheticFields);
		
		errorSegmentDedupeImpl.updateDuplicateBug(sbug, sbugDup);
	}
	
	@Test
	public void testUpdateDuplicateBugImpactCase() {
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing2");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact("Impact Percentage:2.36%");
		sbugDup.setBugFields(bugFieldsDup);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		sbugDup.setSyntheticFields(syntheticFields);
		
		errorSegmentDedupeImpl.updateDuplicateBug(sbug, sbugDup);
	}
}
