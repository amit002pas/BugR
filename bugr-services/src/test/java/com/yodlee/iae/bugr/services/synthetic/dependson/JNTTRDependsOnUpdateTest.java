/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.dependson;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.synthetic.search.cache.JNBugsCache;
import com.yodlee.iae.bugr.services.synthetic.search.cache.TTRBugsCache;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JNTTRDependsOnUpdateTest {

	@InjectMocks
	private JNTTRDependsOnUpdate jNTTRDependsOnUpdate;
	
	@Mock
	private JNBugsCache jNBugsCache;

	@Mock
	private TTRBugsCache tTRBugsCache;

	@Mock
	private MongoOperations mongoOperations;
	
	@Mock
	ApplicationContext ctx;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testWithException(){
		Map<String, Set<Integer>> map = new HashMap<>();
		Set<Integer> values = new HashSet<>();
		values.add(12345);
		map.put("123", values);
		Mockito.when(jNBugsCache.getCachedBugs()).thenReturn(Optional.of(map));
		Map<String, Set<Integer>> map1 = new HashMap<>();
		Set<Integer> value = new HashSet<>();
		value.add(123445);
		map1.put("1234", value);
		Mockito.when(tTRBugsCache.getCachedBugs()).thenReturn(Optional.of(map1));
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFields.setDepends_on("12345");
		sbug.setBugFields(bugFields);
		sbug.setSyntheticBugid("12434343");
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(true);
		sbug.setSyntheticFields(syntheticFields);
		
		Mockito.when(mongoOperations.findById(any(String.class),eq(SyntheticBug.class))).thenReturn(sbug);
		
		
		jNTTRDependsOnUpdate.process(null);
		
	}
	
	@Test
	public void testWithExceptionFalse(){
		Map<String, Set<Integer>> map = new HashMap<>();
		Set<Integer> values = new HashSet<>();
		values.add(12345);
		map.put("123", values);
		Mockito.when(jNBugsCache.getCachedBugs()).thenReturn(Optional.of(map));
		Map<String, Set<Integer>> map1 = new HashMap<>();
		Set<Integer> value = new HashSet<>();
		value.add(123445);
		map1.put("1234", value);
		Mockito.when(tTRBugsCache.getCachedBugs()).thenReturn(Optional.of(map1));
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFields.setDepends_on("12345");
		sbug.setBugFields(bugFields);
		sbug.setSyntheticBugid("12434343");
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(false);
		sbug.setSyntheticFields(syntheticFields);
		
		Mockito.when(mongoOperations.findById(any(String.class),eq(SyntheticBug.class))).thenReturn(sbug);
		
		
		jNTTRDependsOnUpdate.process(null);
		
	}
	
	@Test
	public void testSuccess(){
		Map<String, Set<Integer>> map = new HashMap<>();
		Set<Integer> values = new HashSet<>();
		values.add(12345);
		map.put("123", values);
		Mockito.when(jNBugsCache.getCachedBugs()).thenReturn(Optional.of(map));
		Map<String, Set<Integer>> map1 = new HashMap<>();
		Set<Integer> value = new HashSet<>();
		value.add(123445);
		map1.put("1234", value);
		Mockito.when(tTRBugsCache.getCachedBugs()).thenReturn(Optional.of(map1));
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFields.setDepends_on(null);
		sbug.setBugFields(bugFields);
		sbug.setSyntheticBugid("12434343");
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(true);
		sbug.setSyntheticFields(syntheticFields);
		Mockito.when(mongoOperations.findById(any(String.class),eq(SyntheticBug.class))).thenReturn(sbug);
		
		jNTTRDependsOnUpdate.process(null);
		
	}
}
