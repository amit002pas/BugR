/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.prioritize;

import static org.mockito.Mockito.doNothing;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class BugPrioritizationServiceImplTest {
	
	@InjectMocks
	BugPrioritizationServiceImpl bugPrioritizationServiceImpl;
	
	@Mock
	BugPrioritizerUtility bugPrioritizerUtility;
	
	@Before
	public void setUp() throws Exception{   
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(bugPrioritizerUtility, "PRIORITY_ONE", 5);
 } 
	
	@Test
	public void testGetPriority() {
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("preSRUser", 5);
		map.put("all",2);
		HashMap<Integer, String> mapPriority = new HashMap<>();
		mapPriority.put(5, "P1");
		TreeMap<Integer, Integer> mapImpactWeight = new TreeMap<>();
		mapImpactWeight.put(50, 50);
		doNothing().when(bugPrioritizerUtility).initializeValues();
		Mockito.when(bugPrioritizerUtility.getKeyWeightMap()).thenReturn(map);
		Mockito.when(bugPrioritizerUtility.getCobrandWeightMap()).thenReturn(map);
		Mockito.when(bugPrioritizerUtility.getImpactWeightMap()).thenReturn(mapImpactWeight);
		
		Mockito.when(bugPrioritizerUtility.getPriorityMap()).thenReturn(mapPriority);
		bugPrioritizationServiceImpl.getPriority(58,"preSRUser","All");
	}
	
	@Test
	public void testGetPriorityWeightNotEqualCase() {
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("preSRUser", 1);
		map.put("all",2);
		HashMap<Integer, String> mapPriority = new HashMap<>();
		mapPriority.put(5, "P1");
		TreeMap<Integer, Integer> mapImpactWeight = new TreeMap<>();
		mapImpactWeight.put(50, 5);
		doNothing().when(bugPrioritizerUtility).initializeValues();
		Mockito.when(bugPrioritizerUtility.getKeyWeightMap()).thenReturn(map);
		Mockito.when(bugPrioritizerUtility.getCobrandWeightMap()).thenReturn(map);
		Mockito.when(bugPrioritizerUtility.getImpactWeightMap()).thenReturn(mapImpactWeight);
		
		Mockito.when(bugPrioritizerUtility.getPriorityMap()).thenReturn(mapPriority);
		bugPrioritizationServiceImpl.getPriority(5,"preSRUser","All");
	}
	

}
