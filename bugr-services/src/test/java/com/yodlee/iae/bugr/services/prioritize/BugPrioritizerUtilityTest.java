/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.prioritize;

/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BugPrioritizerUtilityTest {
	
	@InjectMocks
	private BugPrioritizerUtility bugPrioritizerUtility;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetImpactWeightMap() {
		bugPrioritizerUtility.initializeValues();
		bugPrioritizerUtility.getImpactWeightMap();
		bugPrioritizerUtility.getCobrandWeightMap();
		bugPrioritizerUtility.getKeyWeightMap();
		bugPrioritizerUtility.getPriorityInputMap();
		bugPrioritizerUtility.getPriorityMap();
	}

}
