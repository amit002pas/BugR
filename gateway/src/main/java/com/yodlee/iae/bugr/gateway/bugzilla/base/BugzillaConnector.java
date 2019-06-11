/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.gateway.bugzilla.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.LogIn;
import com.yodlee.iae.bugr.gateway.bugzilla.exception.IntegrationException;
import com.yodlee.iae.bugr.gateway.bugzilla.resource.BugRequest;
import com.yodlee.iae.bugr.gateway.bugzilla.util.IntegrationConstant;

/**
 * @author KChandrarajan
 *
 */
@Component
public class BugzillaConnector {

	private com.j2bugzilla.base.BugzillaConnector con = new com.j2bugzilla.base.BugzillaConnector();

	private static String BUGZILLA_CONNECTION_EXCEPTION_MSG1 = "must log in before using";
	private static String BUGZILLA_CONNECTION_EXCEPTION_MSG2 = "Cannot execute a method without connecting";

	@Value("${bugzilla.userName}")
	private String userName;

	@Value("${bugzilla.password}")
	private String password;

	@PostConstruct
	public void login() {
		try {
			con.connectTo(IntegrationConstant.BUGZILLA_HOST);
		} catch (ConnectionException e) {
			throw new IntegrationException(e);
		}
		
		LogIn login = new LogIn(userName, password);
		try {
			con.executeMethod(login);
		} catch (BugzillaException e) {
			throw new IntegrationException(e);
		}
	}

	/**
	 * * Execute Bugzilla request
	 * 
	 * @param param
	 */
	public void executeMethod(BugRequest param) {
		try {
			con.executeMethod(param.getMethod());
		} catch (BugzillaException e) {
			if (e.getMessage().contains(BUGZILLA_CONNECTION_EXCEPTION_MSG1)
					|| e.getMessage().contains(BUGZILLA_CONNECTION_EXCEPTION_MSG2)) {
				login();
				try {
					con.executeMethod(param.getMethod());
				} catch (BugzillaException e1) {
					throw new IntegrationException(e1);
				}
			} else {
				throw new IntegrationException(e);
			}
		}
	}

}
