/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

/**
 * @author Sanyam Jain
 */
@Named
public final class SortFilterUtility {

	public static final String DATE_FORMAT_1 = "E MMM dd HH:mm:ss Z yyyy";
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd hh:mm";

	public static final String SORT_ASCENDING = "ascending";
	public static final String SORT_DESCENDING = "descending";

	public static Map<Integer, List<Integer>> getSimilarBugsByStackTrace(List<Bug> bugsList, List<Bug> preSRBugs) {
		Map<Integer, List<Integer>> bugIdsMap = new HashMap<Integer, List<Integer>>();
		List<Integer> similarBugs = null;
		for (Bug b : preSRBugs) {
			similarBugs = new ArrayList<Integer>();
			String agentName = b.getParameterMap().get(BugzillaConstants.CF_AGENTS).toString().trim();
			String errorCode = b.getParameterMap().get(BugzillaConstants.CF_ERRORCODE).toString().trim();
			String stackTrace = b.getParameterMap().get(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN).toString()
					.trim();
			if (!StringUtils.isEmpty(stackTrace) && agentName != null && errorCode != null) {
				if (stackTrace.contains("||")) {
					stackTrace = stackTrace.substring(0, stackTrace.indexOf("||") - 1).trim();
				}
				if (stackTrace.contains("Stack Trace:")) {
					stackTrace = stackTrace.replace("Stack Trace:", "").trim();
				}
				for (Bug bb : bugsList) {
					int length = 0;
					String cvsSplitBy = ",";
					String agentName1 = bb.getParameterMap().get(BugzillaConstants.CF_AGENTS).toString().trim();
					if (agentName1.contains(",")) {
						String[] agentNameArray = agentName1.split(cvsSplitBy);
						for (String aName : agentNameArray) {
							if (aName.trim().equals(agentName.trim())) {
								agentName1 = aName.trim();
								break;
							}
						}
					}
					String errorCode1 = bb.getParameterMap().get(BugzillaConstants.CF_ERRORCODE).toString().trim();
					String stackTrace1 = bb.getParameterMap().get(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN)
							.toString().trim();
					if (!StringUtils.isEmpty(stackTrace1) && agentName1 != null && errorCode1 != null) {
						if (stackTrace1.length() > stackTrace.length()) {
							length = stackTrace.length();
							stackTrace1 = stackTrace1.substring(0, length);
						}
						if (!StringUtils.isEmpty(stackTrace1) && !StringUtils.isEmpty(stackTrace)) {
							if (SynUtil.STACK_TRACE_MATCHER.test(stackTrace, stackTrace1)
									&& agentName1.equals(agentName) && errorCode1.equals(errorCode)) {
								if (bb.getID() != b.getID()) {
									similarBugs.add(bb.getID());
								}
							}
						}

					}
				}
			}
			if (!similarBugs.isEmpty())
				bugIdsMap.put(b.getID(), similarBugs);
		}

		return bugIdsMap;
	}

	public static List<Bug> filterBugsByLastUpdatedTime(List<Bug> bugsList, int numHours) throws ParseException {
		List<Bug> finalBugs = new ArrayList<Bug>();
		Date updatedTime = null;
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(DATE_FORMAT_1);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DATE_FORMAT_2);

		for (Bug b : bugsList) {
			String tempString = b.getParameterMap().get(BugzillaConstants.CF_IMPACT).toString().trim();
			if (tempString.contains("Updated At:")) {
				tempString = tempString.substring(tempString.indexOf("Updated At:") + 11).trim();
				if (tempString.contains(";")) {
					tempString = tempString.substring(0, tempString.indexOf(";")).trim();
				} else {
					tempString = tempString.substring(0, tempString.length()).trim();
				}
				updatedTime = simpleDateFormat2.parse(tempString);
			} else {
				tempString = b.getParameterMap().get("last_change_time").toString().trim();
				updatedTime = simpleDateFormat1.parse(tempString);
			}
			Date sysDate = new Date();
			long diff = sysDate.getTime() - updatedTime.getTime();
			long hoursDiff = diff / 1000 / 60 / 60;
			if (hoursDiff <= numHours)
				finalBugs.add(b);
		}
		return finalBugs;
	}

	public static List<Bug> filterIATBugs(List<Bug> bugList) {

		List<Bug> iatBugs = new ArrayList<>();

		for (Bug b : bugList) {
			String whiteboard = b.getParameterMap().get(BugzillaConstants.WHITEBOARD).toString().trim();
			String status = b.getParameterMap().get(BugzillaConstants.STATUS).toString().trim();
			String reporter = b.getParameterMap().get(BugzillaConstants.CREATOR).toString().trim();

			if (!status.equals("RESOLVED") 
				&& !status.equals("CLOSED") 
				&& whiteboard.contains("IAT")
				&& !whiteboard.contains("IAT_TESTING_INVALID") 
				&& reporter.contains("Serviceissueanalyzertool")) {
				
				iatBugs.add(b);
			}
		}
		return iatBugs;
	}

	public static List<Bug> filterTTRBugs(List<Bug> bugList) {
		List<Bug> ttrBugs = new ArrayList<Bug>();

		for (Bug b : bugList) {
			String whiteboard = b.getParameterMap().get(BugzillaConstants.WHITEBOARD).toString().trim();
			String status = b.getParameterMap().get(BugzillaConstants.STATUS).toString().trim();

			if (!status.equals("RESOLVED") && !status.equals("CLOSED")
					&& (whiteboard.toUpperCase().contains("TTR_DOMESTIC") || whiteboard.toUpperCase().contains("TTR_CA")
							|| whiteboard.toUpperCase().contains("TTR_ANZ")
							|| whiteboard.toUpperCase().contains("TTR_SA")
							|| whiteboard.toUpperCase().contains("TTR_UK")
							|| whiteboard.toUpperCase().contains("TTR_IN")
							|| whiteboard.toUpperCase().contains("TTR_I18N"))) {
				ttrBugs.add(b);
			}
		}
		return ttrBugs;
	}

	public static List<Bug> filterPreSRBugs(List<Bug> bugList, List<String> reconWealthErrorCodesList,
			List<String> reconBankErrorCodesList) {
		List<Bug> preSRBugs = new ArrayList<Bug>();

		for (Bug b : bugList) {
			String whiteboard = b.getParameterMap().get(BugzillaConstants.WHITEBOARD).toString().trim();
			String errorCode = b.getParameterMap().get(BugzillaConstants.CF_ERRORCODE).toString().trim();
			String status = b.getParameterMap().get(BugzillaConstants.STATUS).toString().trim();
			String reporter = b.getParameterMap().get(BugzillaConstants.CREATOR).toString().trim();
			String summary = b.getParameterMap().get(BugzillaConstants.SUMMARY).toString().trim();
			if (!status.equals("RESOLVED") && !status.equals("CLOSED")
					&& ((whiteboard.contains("IAT") && !whiteboard.contains("IAT_TESTING_INVALID")
							&& reporter.contains("Serviceissueanalyzertool")
							&& whiteboard.contains("PreSR_ErrorSegment")) || whiteboard.contains("PreSR_ErrorSegment")
							|| (whiteboard.contains("TTR") && whiteboard.contains("PreSR_ErrorSegment")))
					&& !reconBankErrorCodesList.contains(errorCode) && !reconWealthErrorCodesList.contains(errorCode)) {
				if (summary.contains("REPORTED ERROR :")) {
					continue;
				} else {
					preSRBugs.add(b);
				}
			}
		}
		return preSRBugs;
	}

}
