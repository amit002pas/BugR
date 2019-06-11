/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.gateway.bugzilla;

import java.util.List;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;

/**
 * @author Sanyam Jain & Rajkumar Uppati
 */
public interface IBugzillaGateway {

	/*
	 * This method will connect to Bugzilla gateway and will maintain a session for
	 * all the services.
	 */
	public void connectToBugzilla() throws ConnectionException, BugzillaException;

	/*
	 * This method will disconnect from Bugzilla gateway and will logout the
	 * session.
	 */
	public void disconnectFromBugzilla() throws ConnectionException, BugzillaException;

	/*
	 * This method will return NEW, ASSIGNED, REOPENED bugs in Bugzilla having days
	 * difference of creation as provided in the number of days param.
	 */
	public List<Bug> getBugsFromBugzilla(int numDays) throws ConnectionException, BugzillaException;

}