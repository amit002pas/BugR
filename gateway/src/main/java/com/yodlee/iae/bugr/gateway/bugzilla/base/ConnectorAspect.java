/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
/*package com.yodlee.iae.bugr.gateway.bugzilla.base;

import java.util.stream.IntStream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.LogIn;
import com.yodlee.iae.bugr.gateway.bugzilla.exception.IntegrationException;
import com.yodlee.iae.bugr.gateway.bugzilla.resource.BugRequest;
import com.yodlee.iae.bugr.gateway.bugzilla.util.IntegrationConstant;

*//**
 * @author KChandrarajan
 *
 *//*
@Component
@Aspect
public class ConnectorAspect {

	private String host = IntegrationConstant.BUGZILLA_HOST;

	*//**
	 * use this method to create BugRequest Object based on username and password
	 * 
	 * @param joinPoint
	 * @throws IntegrationException
	 * @throws IntegrationException
	 *//*
//	@Before("execution(* com.yodlee.iae.bugr.gateway.bugzilla.base.BugzillaConnector.executeMethod(com.yodlee.iae.bugr.gateway.bugzilla.resource.BugRequest))")
	public void populateBeforeCall(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		IntStream.range(0, args.length).forEach(i -> {
			if (args[i] instanceof BugRequest) {
				BugRequest arg = ((BugRequest) args[i]);
				try {
					arg.setConnector(createConnector(host, arg.getUsername(), arg.getPassword()));
				} catch (BugzillaException | ConnectionException e) {
					throw new IntegrationException(e.getMessage());
				}
			}
		});
	}

	*//**
	 * Use this method to designate a host to connect to
	 * 
	 * @param host
	 * @param username
	 * @param password
	 * @return BugzillaConnector
	 * @throws BugzillaException
	 * @throws ConnectionException
	 *//*
	public static BugzillaConnector createConnector(final String host, String username, String password)
			throws BugzillaException, ConnectionException {
		BugzillaConnector con = new BugzillaConnector();
		con.connectTo(host);
		LogIn login = new LogIn(username, password);
		con.executeMethod(login);
		return con;
	}
}
*/