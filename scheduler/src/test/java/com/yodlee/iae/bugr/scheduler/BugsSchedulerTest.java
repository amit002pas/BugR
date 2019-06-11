/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.scheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;

public class BugsSchedulerTest {

	@InjectMocks
	private BugsScheduler bugsScheduler;

	@Mock   
	private CachedBugsServiceImpl cachedBugsServiceImpl;

	@Mock
	private SyntheticBugsScheduler syntheticBugsScheduler;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCacheBugsForSearchScheduler() {
		bugsScheduler.cacheBugsForSearchScheduler();
	}
	
}
