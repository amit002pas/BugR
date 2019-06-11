/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.AttachmentFactory;
import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.gateway.bugzilla.BugSearch.SearchLimiter;
import com.yodlee.iae.bugr.gateway.bugzilla.BugSearch.SearchQuery;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class SynUtil {

	/**
	 * 
	 */
	private SynUtil() {
	}

	@Inject
	private IBugzillaRPCClient bugzillaGateway;

	public static final String IMPACT_PERCENTAGE = "Impact Percentage: ";

	public static final String UPDATED_AT = ";Updated At: ";
	public static final String SEGMENT_ID = "SegmentId:";
	public static final String ACTION = "Action:";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String NEW_PRIORITY = "P1";

	public static final String HOLDINGS = "holdings";
	public static final String CUSIP = "Cusip:";
	public static final String SYMBOL = "Symbol:";
	public static final String SUMINFOID = "sumInfoId";
	public static final String DESCRIPTION = "description";

	public static final String TOTAL_COUNT = "Total Count:";
	public static final String FAILURE_COUNT = "Failure Count:";
	public static final String cachedDQJNBugs = "cachedDQJNBugs";
	public static final String filteredNonSimilarJnBugs = "filteredNonSimilarJnBugs";

	public static final String STACK_TRACE = "Stack Trace:";
	public static final String JCONTROLLER_SS = "com.yodlee.dap.gatherer.gather.JController";
	public static final String JCONTROLLER = "JController.java:";
	public static final String SELENIUM_SS = "org.openqa.selenium";
	public static final String JAVA_SS = "(?<=.java:)\\d+";

	public static final String SEMI_COLON = ";";
	public static final String EMPTY_STRING = "";
	public static final String APOSTROPHE = "'";
	public static final String SEPARATOR = "||";
	public static final String CLOSINGBRACE = "\\)";

	public static final String WHITESPACE = "\\s+";
	public static final String TAB = "\t";
	public static final String NEWLINE = "\n";
	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String RESOLVED = "RESOLVED";
	public static final String AGENT = "Agent";
	public static final String IAE = "IAE";
	public static final String WHITEBOARD = "whiteboard";
	public static final String CLOSED = "Closed";
	public static final String DOOT = "Dependent on other teams";
	public static final String DUPLICATE_ALREADY_FIXED = "Duplicate - Already Fixed";
	public static final String REASSIGNED = "Reassigned";
	public static final String RECON_SERVICES = "Recon_Services";
	public static final String PRESR_ERRORSEGMENT = "PreSR_ErrorSegment";
	public static final int IST_PST_HOUR_DIFF = 13;
	public static final int IST_PST_MIN_DIFF = 30;
	public static final String LIMIT = "10000";
	public static final String OFFSET = "0";

	private static Gson gson = new Gson();

	public static final BiPredicate<String, String> STACK_TRACE_MATCHER = SynUtil::stackTraceSimilarity;

	private static final Predicate<Object> objPred = obj -> obj != null && !obj.toString().isEmpty();
	private static final Predicate<String> strPred = obj -> obj != null && !obj.isEmpty();
	private static final Predicate<List<String>> listPred = list -> objPred.test(list) && !list.isEmpty();

	/**
	 * Validates the string for null or empty and returns an optional
	 * 
	 * @param str
	 * @return
	 */
	public static Optional<String> validateStr(String str) {
		return strPred.test(str) ? Optional.ofNullable(str) : Optional.ofNullable(null);
	}

	/**
	 * Validates the object for null or empty and returns an optional
	 * 
	 * @param str
	 * @return
	 */
	public static Optional<String> validateObj(Object obj) {
		return objPred.test(obj) ? Optional.ofNullable(obj.toString()) : Optional.ofNullable(null);
	}

	/**
	 * Validates the list for null or empty and returns an optional
	 * 
	 * @param list
	 * @return
	 */
	public static Optional<List<String>> validateList(List<String> list) {
		return listPred.test(list) ? Optional.ofNullable(list) : Optional.ofNullable(null);
	}

	/**
	 * Calculates impact percentage from bugzilla impact field string
	 * 
	 * @param impactString
	 * @return
	 */
	public static double calImpactPercentage(String impactString) {
		Double totalCount = 1.00;
		Double failCount = 0.00;
		if (impactString.contains(TOTAL_COUNT)) {
			String total = impactString.substring(impactString.indexOf(TOTAL_COUNT) + 12).trim();
			if (total.contains(SEMI_COLON)) {
				total = total.substring(0, total.indexOf(SEMI_COLON)).trim();
			} else {
				total = total.substring(0, total.length()).trim();
			}
			totalCount = Double.parseDouble(total);
		}
		if (impactString.contains(FAILURE_COUNT)) {
			String failure = impactString.substring(impactString.indexOf(FAILURE_COUNT) + 14).trim();
			if (failure.contains(SEMI_COLON)) {
				failure = failure.substring(0, failure.indexOf(SEMI_COLON)).trim();
			} else {
				failure = failure.substring(0, failure.length()).trim();
			}
			failCount = Double.parseDouble(failure);
		}
		Double impactPercentage = (failCount / totalCount) * 100;
		return (double) Math.round(impactPercentage);
	}

	/**
	 * Recon merge holding details (dedupe)
	 * 
	 * @param codeReviewComments
	 * @param codeReviewCommentsDupl
	 * @return
	 */
	public static String mergeReconHoldingDetails(String codeReviewComments, String codeReviewCommentsDupl) {
		BiPredicate<JsonObject, JsonObject> holdingPred = (json1,
				json2) -> json1.get(SUMINFOID).equals(json2.get(SUMINFOID))
						&& json1.get(DESCRIPTION).equals(json2.get(DESCRIPTION));
		JsonArray array = gson.fromJson(codeReviewComments, JsonObject.class).get(HOLDINGS).getAsJsonArray();
		JsonArray arrayDupl = gson.fromJson(codeReviewCommentsDupl, JsonObject.class).get(HOLDINGS).getAsJsonArray();
		JsonArray jsonArray = new JsonArray();
		for (JsonElement holding1 : array) {
			boolean present = false;
			for (JsonElement holding2 : arrayDupl) {
				if (holdingPred.test(holding1.getAsJsonObject(), holding2.getAsJsonObject())) {
					present = true;
				}
			}
			if (!present) {
				jsonArray.add(holding1);
			}
		}
		arrayDupl.addAll(jsonArray);
		JsonObject jObj = new JsonObject();
		jObj.add(HOLDINGS, arrayDupl);
		return jObj.getAsJsonObject().toString();
	}

	/**
	 * Retriece Cusip and Symbol from whiteboard
	 * 
	 * @param whiteboard
	 * @return
	 */
	public static String[] getCusipAndSymbol(String whiteboard) {
		String cusip = whiteboard
				.substring(whiteboard.indexOf(SynUtil.CUSIP) + SynUtil.CUSIP.length(), whiteboard.length() - 1).trim();
		cusip = cusip.substring(0, cusip.indexOf(SynUtil.SEMI_COLON)).trim();
		String symbol = whiteboard
				.substring(whiteboard.indexOf(SynUtil.SYMBOL) + SynUtil.SYMBOL.length(), whiteboard.length() - 1)
				.trim();
		return new String[] { cusip, symbol };
	}

	/**
	 * Retrieve impact from impact field string
	 * 
	 * @param impact
	 * @return
	 */
	public static String formImpactField(String impact) {
		if (impact.contains(IMPACT_PERCENTAGE)) {
			return impact;
		}
		return IMPACT_PERCENTAGE + impact + UPDATED_AT + new SimpleDateFormat(DATE_FORMAT).format(new Date());
	}

	/**
	 * Retrieve impact percentage from impact field string
	 * 
	 * @param impact
	 * @return
	 */
	public static double getImpactPercentageFromString(String impact) {
		impact = impact.substring(impact.indexOf(SynUtil.IMPACT_PERCENTAGE) + SynUtil.IMPACT_PERCENTAGE.length());
		impact = impact.substring(0, impact.indexOf(SynUtil.SEMI_COLON)).trim();
		return Double.parseDouble(impact);
	}

	/**
	 * Retrieve impact count from impact field string
	 * 
	 * @param impactCount
	 * @return
	 */
	public static int getImpactCountFromString(String impactCount) {
		impactCount = impactCount
				.substring(impactCount.indexOf(SynUtil.FAILURE_COUNT) + SynUtil.FAILURE_COUNT.length());
		impactCount = impactCount.substring(0, impactCount.indexOf(SynUtil.SEMI_COLON)).trim();
		return Integer.parseInt(impactCount);
	}

	/**
	 * Retrieve Stack trace from code review comments field string
	 * 
	 * @param str
	 * @return
	 */
	public static String getStackTraceFromCRC(String str) {
		if (str.contains(SynUtil.SEPARATOR)) {
			str = str.substring(0, str.indexOf(SynUtil.SEPARATOR) - 1).trim();
		}
		if (str.contains(SynUtil.STACK_TRACE)) {
			str = str.replace(SynUtil.STACK_TRACE, SynUtil.EMPTY_STRING).trim();
		}
		return str;
	}

	/**
	 * Get Similar bugs from Synthetic bugs list and Bugzilla bugs list
	 * 
	 * @param sBugs
	 * @param bugs
	 * @return
	 */
	public static Map<String, Set<Integer>> getSimilarBugs(List<SyntheticBug> sBugs, List<Bug> bugs) {
		Map<String, Set<Integer>> finalMap = new ConcurrentHashMap<>();
		sBugs.stream().filter(
				sBug -> null != sBug.getBugFields().getAgentName() && null != sBug.getBugFields().getErrorcode())
				.forEach(sBug -> bugs.stream().filter(bug -> {
					Map<Object, Object> map = bug.getParameterMap();
					String sBugAgentName = sBug.getBugFields().getAgentName();
					String sBugErrorCode = sBug.getBugFields().getErrorcode();
					return !(null == map || null == map.get(BugzillaConstants.CF_AGENTS)
							|| null == map.get(BugzillaConstants.CF_ERRORCODE)
							|| null == map.get(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN)
							|| !map.get(BugzillaConstants.CF_AGENTS).toString().contains(sBugAgentName)
							|| !map.get(BugzillaConstants.CF_ERRORCODE).toString().contains(sBugErrorCode));
				}).forEach(bug -> {
					try {
						Map<Object, Object> map = bug.getParameterMap();
						String sBugStackTrace = SynUtil
								.getStackTraceFromCRC(sBug.getBugFields().getCodeReviewComments()).trim();
						String stackTrace = map.get(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN).toString().trim();
						if (null != sBugStackTrace && null != stackTrace && stackTrace.length() > 0
								&& sBugStackTrace.length() > 0
								&& STACK_TRACE_MATCHER.test(sBugStackTrace, stackTrace)) {

							Set<Integer> bugIdSet = null != finalMap.get(sBug.getSyntheticBugid())
									? finalMap.get(sBug.getSyntheticBugid())
									: new HashSet<>();
							bugIdSet.add(bug.getID());
							finalMap.put(sBug.getSyntheticBugid(), bugIdSet);
						}
					} catch (Exception e) {
						System.out.println("Exception ");
						e.printStackTrace();
						System.out.println("^^^^^^^^" + bug.getParameterMap().toString());
						System.out.println("^^^^^^^^" + sBug.toString());
					}
				}));
		return finalMap;
	}

	/**
	 * Convert attachments to base64 format
	 * 
	 * @param synBug
	 * @return
	 */
	public static SyntheticBug convertAttachmentsToBase64(SyntheticBug synBug) {
		Optional.ofNullable(synBug.getAttachments()).ifPresent(attachments -> {
			try {
				attachments.stream().forEach(
						attachment -> attachment.setAttachment(Base64.decodeBase64(attachment.getAttachment())));
			} catch (Exception e) {
				//
			}
		});
		return synBug;
	}

	/**
	 * Convert to Bugzilla attachment object from attachment requests byte array
	 * 
	 * @param attReq
	 * @return
	 */
	public static Attachment convertToBugzillaAttachment(AttachmentRequest attReq) {
		AttachmentFactory attachmentFactory = new AttachmentFactory();
		byte[] byteArray = Base64.decodeBase64(attReq.getAttachment());

		String fileName = attReq.getAttachmentName();
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeType = fileTypeMap.getContentType(fileName);

		return attachmentFactory.newAttachment().setData(byteArray).setName(attReq.getAttachmentName())
				.setMime(mimeType).setSummary(attReq.getAttachmentName()).createAttachment();
	}

	/**
	 * Get non Similar bugs from similar jn bugs and JN Bugzilla bugs list
	 * 
	 * @param similarJNBugs
	 * @param jnBugs
	 * @return
	 */
	public static List<Bug> getNonSimilarBugs(Map<String, Set<Integer>> similarJNBugs, List<Bug> jnBugs) {

		List<Bug> nonSimilarJnBugs = new ArrayList<>(jnBugs);
		similarJNBugs.entrySet().forEach(entry -> {
			Set<String> jnBugString = new HashSet<>();
			entry.getValue().forEach(bug -> jnBugString.add(bug.toString()));

			jnBugs.forEach(jnBug -> {
				Map<Object, Object> map = jnBug.getParameterMap();

				String jnBugId = map.get(BugzillaConstants.ID).toString();

				if (jnBugString.contains(jnBugId)) {
					nonSimilarJnBugs.remove(jnBug);
				}
			});
		});
		return nonSimilarJnBugs;
	}

	public static Map<String, List<SyntheticBug>> getFilteredJnBugs(List<SyntheticBug> nonSimilarJNBugs) {
		Map<String, List<SyntheticBug>> filteredJnBugs = new HashMap<>();
		List<SyntheticBug> listOfHardJNBugs = new ArrayList<>();
		List<SyntheticBug> listOfDQJNBugs = new ArrayList<>();
		nonSimilarJNBugs.forEach(jnBug -> {
			if (!(jnBug.getBugFields().getWorkflowStatus().contains(CLOSED)
					|| jnBug.getBugFields().getWorkflowStatus().contains(DOOT)
					|| jnBug.getBugFields().getWorkflowStatus().contains(DUPLICATE_ALREADY_FIXED)
					|| jnBug.getBugFields().getWorkflowStatus().contains(REASSIGNED))) {
				String errorCode = jnBug.getBugFields().getErrorcode();
				if (StringUtils.isNumeric(errorCode)) {
					listOfHardJNBugs.add(jnBug);
					filteredJnBugs.put(filteredNonSimilarJnBugs, listOfHardJNBugs);
				} else {
					listOfDQJNBugs.add(jnBug);
					filteredJnBugs.put(cachedDQJNBugs, listOfDQJNBugs);
				}
			}
		});
		return filteredJnBugs;
	}

	/**
	 * Add attachments to Bugzilla asynchronously
	 * 
	 * @param list
	 * @param bugId
	 * @return
	 */
	public CompletableFuture<Void> addAttachmentstoBugzillaAsync(List<AttachmentRequest> list, Integer bugId) {
		if (null != list && !list.isEmpty() && null != bugId) {
			return CompletableFuture.runAsync(
					() -> list.stream().map(SynUtil::convertToBugzillaAttachment)
							.forEach(attachment -> bugzillaGateway.setAttachment(attachment, bugId)),
					Executors.newSingleThreadExecutor());
		}
		return null;
	}

	/**
	 * Generate response with CORS enabled headers
	 * 
	 * @param obj
	 * @return
	 */
	public static Response generateResponse(Object obj) {
		return Response.ok(obj).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
				.header("Access-Control-Max-Age", "3600")
				.header("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token")
				.header("Access-Control-Expose-Headers", "xsrf-token").build();
	}

	/**
	 * Removing unwanted characters from stack trace
	 * 
	 * @param s
	 * @return
	 */
	public static String stackTraceFinal(String s) {
		s = stackTraceParser(s);
		s = s.replaceAll(SynUtil.WHITESPACE, SynUtil.EMPTY_STRING);
		s = s.replaceAll(SynUtil.TAB, SynUtil.EMPTY_STRING);
		s = s.replaceAll(SynUtil.STACK_TRACE, SynUtil.EMPTY_STRING);
		s = s.replaceAll(SynUtil.NEWLINE, SynUtil.EMPTY_STRING);
		return s;
	}

	/**
	 * Checking stack trace similarity
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean stackTraceSimilarity(String s1, String s2) {
		Cosine cosine = new Cosine();
		s1 = stackTraceFinal(s1);
		s2 = stackTraceFinal(s2);

		String tempS1 = null;
		String tempS2 = null;
		if (s1.length() > s2.length()) {
			tempS1 = s1.substring(0, s2.length());
			tempS2 = s2;
		} else {
			tempS2 = s2.substring(0, s1.length());
			tempS1 = s1;
		}

		double value = cosine.similarity(tempS1, tempS2);
		return value >= 0.97;

	}

	/**
	 * Stack Trace : remove unwanted strings
	 * 
	 * @param stackTrace
	 * @return
	 */
	private static String stackTraceParser(String stackTrace) {
		if (stackTrace.contains(SynUtil.SEPARATOR)) {
			stackTrace = stackTrace.substring(0, stackTrace.indexOf(SynUtil.SEPARATOR)).trim();
		}
		if (stackTrace.contains(SynUtil.STACK_TRACE)) {
			stackTrace = stackTrace.replace(SynUtil.STACK_TRACE, SynUtil.EMPTY_STRING).trim();
		}
		/*
		 * if (stackTrace.contains(SynUtil.APOSTROPHE)) { stackTrace =
		 * stackTrace.substring(0, stackTrace.indexOf(SynUtil.APOSTROPHE)); }
		 */
		return stackTrace;
	}

	/**
	 * Get validation response format with validation failed messages
	 * 
	 * @param status
	 * @param messages
	 * @return
	 */
	public static SynBugResponse sendResponse(List<String> messages) {
		SynBugResponse response = new SynBugResponse();
		String status = messages.isEmpty() ? ResponseConstants.STATUS_SUCCESS : ResponseConstants.STATUS_FAILURE;
		response.setStatus(status);
		response.setMessages(messages);
		return response;
	}

	/**
	 * Gives closed bugs in given number of days
	 * 
	 * @param numDays
	 * @return
	 */
	public List<Bug> getBugzillaClosedBugs(Date date) {
		String formattedString = new SimpleDateFormat(FORMAT).format(date);

		SearchQuery preSr = new SearchQuery(SearchLimiter.SUMMARY, "Proactive Monitoring Bugs created by PreSR");
		SearchQuery recon = new SearchQuery(SearchLimiter.SUMMARY, "<ReconIssue>");

		SearchQuery createdDate = new SearchQuery(SearchLimiter.TIMESTAMP, formattedString);
		SearchQuery component = new SearchQuery(SearchLimiter.COMPONENT, AGENT);
		SearchQuery cfInitiative = new SearchQuery(SearchLimiter.CF_INITIATIVE, IAE);
		SearchQuery limitQuery = new SearchQuery(SearchLimiter.LIMIT, LIMIT);
		SearchQuery offsetQuery = new SearchQuery(SearchLimiter.OFFSET, OFFSET);

		SearchQuery workflowStatusClosed = new SearchQuery(SearchLimiter.WORKFLOW_STATUS, CLOSED);
		SearchQuery workflowStatusDoot = new SearchQuery(SearchLimiter.WORKFLOW_STATUS, DOOT);
		SearchQuery workflowStatusDuplicateFixed = new SearchQuery(SearchLimiter.WORKFLOW_STATUS,
				DUPLICATE_ALREADY_FIXED);
		SearchQuery workflowStatusReassigned = new SearchQuery(SearchLimiter.WORKFLOW_STATUS, REASSIGNED);
		SearchQuery statusResolved = new SearchQuery(SearchLimiter.STATUS, RESOLVED);

		List<Bug> preSrClosedBugs = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery, component,
				cfInitiative, preSr, workflowStatusClosed);
		List<Bug> reconClosedBugs = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery, component,
				cfInitiative, recon, workflowStatusClosed);

		List<Bug> preSrClosedBugsDoot = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery, component,
				cfInitiative, preSr, workflowStatusDoot);
		List<Bug> reconClosedBugsDoot = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery, component,
				cfInitiative, recon, workflowStatusDoot);

		List<Bug> preSrClosedBugsDuplicateFixed = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery,
				component, cfInitiative, preSr, workflowStatusDuplicateFixed);
		List<Bug> reconClosedBugsDuplicateFixed = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery,
				component, cfInitiative, recon, workflowStatusDuplicateFixed);

		List<Bug> preSrClosedBugsReassigned = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery, component,
				cfInitiative, preSr, workflowStatusReassigned);
		List<Bug> reconClosedBugsReassigned = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery, component,
				cfInitiative, recon, workflowStatusReassigned);

		List<Bug> preSrClosedBugsStatusResolved = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery,
				component, cfInitiative, preSr, statusResolved);
		List<Bug> reconClosedBugsStatusResolved = bugzillaGateway.searchBug(createdDate, limitQuery, offsetQuery,
				component, cfInitiative, recon, statusResolved);

		List<Bug> newList = new ArrayList<>(preSrClosedBugs);
		newList.addAll(reconClosedBugs);
		newList.addAll(preSrClosedBugsDoot);
		newList.addAll(reconClosedBugsDoot);
		newList.addAll(preSrClosedBugsDuplicateFixed);
		newList.addAll(reconClosedBugsDuplicateFixed);
		newList.addAll(preSrClosedBugsReassigned);
		newList.addAll(reconClosedBugsReassigned);
		newList.addAll(preSrClosedBugsStatusResolved);
		newList.addAll(reconClosedBugsStatusResolved);

		return newList;
	}

	/**
	 * Gets CSV details for JN and Orphic bugs.
	 * 
	 * @param
	 * @return
	 */
	public List<Map<String, String>> getDataForCSV(List<SyntheticBug> synBgList) {
		List<Map<String, String>> finalList = new ArrayList<Map<String, String>>();
		for (SyntheticBug sb : synBgList) {
			Map<String, String> csvDetailsMap = new HashMap<String, String>();
			csvDetailsMap.put("syntheticBugID", sb.getSyntheticBugid());
			if (sb.getSyntheticFields().getBugzillaBugId() != null) {
				csvDetailsMap.put(BugzillaConstants.BUG_ID, sb.getSyntheticFields().getBugzillaBugId().toString());
			}
			csvDetailsMap.put(BugzillaConstants.CF_AGENTS, sb.getBugFields().getAgentName());
			csvDetailsMap.put(BugzillaConstants.CF_ERRORCODE, sb.getBugFields().getErrorcode());
			csvDetailsMap.put(BugzillaConstants.STATUS, sb.getBugFields().getWorkflowStatus());
			csvDetailsMap.put(BugzillaConstants.CF_IMPACT, sb.getBugFields().getImpact());
			String sumInfo = sb.getBugFields().getSuminfo();
			if (sumInfo != null) {
				if (sumInfo.contains(",")) {
					sumInfo = sumInfo.replaceAll(",", " ");
				}
				csvDetailsMap.put(BugzillaConstants.CF_SUMINFO, sumInfo);
			}
			csvDetailsMap.put(BugzillaConstants.SUMMARY, sb.getBugFields().getSummary());
			csvDetailsMap.put(BugzillaConstants.WHITEBOARD, sb.getBugFields().getWhiteboard());
			String codeReviewComments = sb.getBugFields().getCodeReviewComments();
			if (codeReviewComments != null) {
				if (codeReviewComments.contains("|")) {
					codeReviewComments = codeReviewComments.substring(0, codeReviewComments.indexOf("|"));
				}
				csvDetailsMap.put(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN, codeReviewComments);
			}
			finalList.add(csvDetailsMap);
		}
		return finalList;
	}

}
