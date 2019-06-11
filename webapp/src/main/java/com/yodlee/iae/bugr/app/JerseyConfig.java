/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.app;

import javax.inject.Named;

import org.glassfish.jersey.server.ResourceConfig;

import com.yodlee.iae.bugr.exceptionhandler.CustomExceptionHandler;
import com.yodlee.iae.bugr.rest.impl.JnSimilarBugRestImpl;
import com.yodlee.iae.bugr.rest.impl.SitewiseTTRBugsRestImpl;
import com.yodlee.iae.bugr.rest.impl.SynBugRRestImpl;

/**
 * @author Rajkumar Uppati
 */

@Named
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(SynBugRRestImpl.class);
		register(CustomExceptionHandler.class);
		register(JnSimilarBugRestImpl.class);
		register(SitewiseTTRBugsRestImpl.class);
	}

}
