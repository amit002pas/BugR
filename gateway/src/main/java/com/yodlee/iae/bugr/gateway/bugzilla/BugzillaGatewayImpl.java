/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.gateway.bugzilla;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.LogIn;
import com.j2bugzilla.rpc.LogOut;
import com.yodlee.iae.bugr.gateway.bugzilla.BugSearch.SearchLimiter;
import com.yodlee.iae.bugr.gateway.bugzilla.BugSearch.SearchQuery;

/**
 * @author Sanyam Jain & Rajkumar Uppati
 */
@Named
public class BugzillaGatewayImpl implements IBugzillaGateway {

	// @Inject
	private BugzillaConnector bugzillaConnector = null;

	@Value("${bugzilla.userName}")
	private String bugzillaUserName;

	@Value("${bugzilla.password}")
	private String bugzillaPassword;

	@Value("${bugzilla.url}")
	private String bugzillaHostName;

	private LogIn login;

	private static final Logger LOG = LoggerFactory.getLogger(BugzillaGatewayImpl.class);

	private static String BUGZILLA_CONNECTION_EXCEPTION_MSG1 = "must log in before using";
	private static String BUGZILLA_CONNECTION_EXCEPTION_MSG2 = "Cannot execute a method without connecting";

	@PostConstruct
	public void connectToBugzilla() throws ConnectionException, BugzillaException {
		LOG.debug("Connecting to Bugzilla...");
		bugzillaConnector = new BugzillaConnector();
		bugzillaConnector.connectTo(this.bugzillaHostName);
		login = new LogIn(this.bugzillaUserName, this.bugzillaPassword);
		bugzillaConnector.executeMethod(login);
		LOG.debug("Connection Established");
	}

	@PreDestroy
	public void disconnectFromBugzilla() throws ConnectionException, BugzillaException {
		LOG.debug("Disconnecting from Bugzilla...");
		LogOut logout = new LogOut();
		bugzillaConnector.executeMethod(logout);
		LOG.debug("Logged out successfully");
	}

	public List<Bug> getBugsFromBugzilla(int numDays) throws ConnectionException, BugzillaException {
		return getBugsFromBugzilla(numDays, false);
	}

	private List<Bug> getBugsFromBugzilla(int time, boolean isHours) throws ConnectionException, BugzillaException {
		List<Bug> resultsNew = null;
		List<Bug> resultsAssigned = null;
		List<Bug> resultsReopend = null;
		List<Bug> finalBugs = null;
		if (time <= 0) {
			time = 0;
		}
		LOG.debug("Fetching %d days bugs",time);

		LocalDate sysDate = isHours ? LocalDate.now().minus(time, ChronoUnit.HOURS) : LocalDate.now().minusDays(time);

		SearchQuery creation_ts = new SearchQuery(SearchLimiter.TIMESTAMP, sysDate.toString());
		SearchQuery status_new = new SearchQuery(SearchLimiter.STATUS, "NEW");
		SearchQuery status_assigned = new SearchQuery(SearchLimiter.STATUS, "ASSIGNED");
		SearchQuery status_reopened = new SearchQuery(SearchLimiter.STATUS, "REOPENED");
		SearchQuery cf_initiative = new SearchQuery(SearchLimiter.CF_INITIATIVE, "IAE");

		SearchQuery limitQuery = new SearchQuery(SearchLimiter.LIMIT, "10000");
		SearchQuery offsetQuery = new SearchQuery(SearchLimiter.OFFSET, "40");
		BugSearch searchNew = new BugSearch(creation_ts, limitQuery, offsetQuery, status_new, cf_initiative);
		BugSearch searchAssigned = new BugSearch(creation_ts, limitQuery, offsetQuery, status_assigned, cf_initiative);
		BugSearch searchReopened = new BugSearch(creation_ts, limitQuery, offsetQuery, status_reopened, cf_initiative);
		try {
			bugzillaConnector.executeMethod(searchNew);
			bugzillaConnector.executeMethod(searchAssigned);
			bugzillaConnector.executeMethod(searchReopened);
		} catch (BugzillaException | IllegalStateException ex) {
			System.out.println("%%");
			ex.printStackTrace();
			if (ex.getMessage().contains(BUGZILLA_CONNECTION_EXCEPTION_MSG1)
					|| ex.getMessage().contains(BUGZILLA_CONNECTION_EXCEPTION_MSG2)) {
				connectToBugzilla();
				bugzillaConnector.executeMethod(searchNew);
				bugzillaConnector.executeMethod(searchAssigned);
				bugzillaConnector.executeMethod(searchReopened);
			} else {
				throw ex;
			}
		}
		resultsNew = searchNew.getSearchResults();
		resultsAssigned = searchAssigned.getSearchResults();
		resultsReopend = searchReopened.getSearchResults();
		finalBugs = new ArrayList<Bug>(resultsNew);
		finalBugs.addAll(resultsAssigned);
		finalBugs.addAll(resultsReopend);
		LOG.debug("Fetched %d bugs",finalBugs.size());
		System.out.println("Fetched bugs:"+finalBugs.size());
		return finalBugs;
	}

}