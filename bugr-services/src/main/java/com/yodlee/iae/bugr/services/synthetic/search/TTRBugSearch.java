/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.TTRBugResponse;
import com.yodlee.iae.common.services.ServiceBase;

/**
* @author draghuvanshi
*
*/

@Named
public class TTRBugSearch extends ServiceBase {
	
	@Inject
	private IBugzillaRPCClient iBugzillaRPCClient;

	public static TTRBugResponse response = null;
	

	private final String TTR_SUMMARY ="<TTR-ALERT>";
	private final String STATUS_NEW ="New";
	private final String STATUS_ASSIGNED ="Assigned";
	private final String STATUS_REOPENED ="Reopened";
	

	private static final Logger LOG = LoggerFactory.getLogger(TTRBugSearch.class);

	public void executeImpl() {
		response = new TTRBugResponse();
		Map<String, Integer> siteBugList = null;
		try {
			siteBugList = getSiteAndBugIds();
			if (siteBugList != null && siteBugList.size() > 0) {
				response.setSiteBugList(siteBugList);
				response.setMessage(ResponseConstants.MSG_BUGS_FIND_SUCCESS);
				response.setStatus(ResponseConstants.STATUS_SUCCESS);
				response.setError("No Errors");
			} else {
				LOG.debug("Bugs are coming out as null from gateway.");
				response.setSiteBugList(null);
				response.setMessage(ResponseConstants.MSG_BUGS_FIND_FAILED);
				response.setStatus(ResponseConstants.STATUS_FAILURE);
				response.setError(ResponseConstants.MSG_UNEXPECTED_ERROR);
			}
		} catch (Exception e) {
			String errorMessage = ResponseConstants.ERRORCODE_GENERALEXCEPTION + " : " + e.getMessage();
			LOG.debug(Arrays.toString(e.getStackTrace()));
			response.setSiteBugList(null);
			response.setMessage(ResponseConstants.MSG_BUGS_FIND_FAILED);
			response.setStatus(ResponseConstants.STATUS_FAILURE);
			response.setError(errorMessage);
		}

	}

	public TTRBugResponse get() {
		return response;
	}

	public Map<String, Integer> getSiteAndBugIds() throws ClassNotFoundException, SQLException {
		Map<String, Integer> bugRes = new HashMap<String, Integer>();
		bugRes = getTTRSiteBugIds();
		LOG.debug("Post Filtering num of bugs for TTR are:: %d", bugRes.size());
		return bugRes;

	}
	
	public Map<String, Integer> getTTRSiteBugIds()
			throws ClassNotFoundException, SQLException {
		LOG.debug("Getting site ids and Bug ids for TTR");
		Map<String, Integer> siteBugIDMap = new HashMap<String, Integer>();
		List<Bug> bugsListNew = new ArrayList<Bug>();
		List<Bug> bugsListAssigned = new ArrayList<Bug>();
		List<Bug> bugsListReopened = new ArrayList<Bug>();

		List<Bug> finalBugList = new ArrayList<Bug>();
		Map<Object, Object> queryMap = new HashMap<Object, Object>();

		queryMap.put(BugzillaConstants.SHORT_DESC, TTR_SUMMARY);
		queryMap.put(BugzillaConstants.STATUS, STATUS_NEW);
		bugsListNew = iBugzillaRPCClient.searchBug(queryMap);
		queryMap.put(BugzillaConstants.STATUS, STATUS_ASSIGNED);
		bugsListAssigned = iBugzillaRPCClient.searchBug(queryMap);
		queryMap.put(BugzillaConstants.STATUS, STATUS_REOPENED);
		bugsListReopened = iBugzillaRPCClient.searchBug(queryMap);

		finalBugList.addAll(bugsListNew);
		finalBugList.addAll(bugsListAssigned);
		finalBugList.addAll(bugsListReopened);
		for (Bug b : finalBugList) {
			String summary = b.getParameterMap().get(BugzillaConstants.SUMMARY)
					.toString().trim();
			String siteId = b.getParameterMap()
					.get(BugzillaConstants.CF_SITE_ID).toString().trim();
			if (summary != null && summary.contains(TTR_SUMMARY)
					&& !siteId.isEmpty()) {

				siteBugIDMap.put(
						b.getParameterMap().get(BugzillaConstants.CF_SITE_ID)
								.toString(), ((Integer) b.getParameterMap()
								.get(BugzillaConstants.ID)).intValue());

			}
		}

		return siteBugIDMap;
	}


	@Override
	public void mapInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mapOutput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}


}
