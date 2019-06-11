package com.yodlee.iae.bugr.rest.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.services.synthetic.search.TTRBugSearch;

public class SitewiseTTRBugsRestImplTest {
	
	@InjectMocks
	private SitewiseTTRBugsRestImpl sitewiseTTRBugsRestImpl;
	
	@Mock
	private TTRBugSearch getTTRBugs;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetTTRBugs() {
		Mockito.doNothing().when(getTTRBugs).executeImpl();
		sitewiseTTRBugsRestImpl.getTTRBugs();
	}
}
