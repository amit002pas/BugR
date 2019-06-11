/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.categorize;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.yodlee.iae.bugr.services.utilities.StacktraceUtility;

/**
 * @author Sanyam Jain
 */
@Named
public class FindBugCategoryServiceImpl {

	@Value("${errorcodes.login}")
	private String loginErrorCodes;

	@Value("${errorcodes.infra}")
	private String infraErrorCodes;

	@Value("${errorcodes.reconwealth}")
	private String reconWealthErrorCodes;

	private static final Logger LOG = LoggerFactory.getLogger(FindBugCategoryServiceImpl.class);

	private static String BUG_CATEGORY_ERRORCODE = "Error_Code";
	private static String BUG_CATEGORY_LOGINERROR = "Login_Issue";
	private static String BUG_CATEGORY_ACCOUNTSCRAPING = "Account_Scraping_Issue";
	private static String BUG_CATEGORY_TRANSACTION = "Transaction_Issue";
	private static String BUG_CATEGORY_STATEMENTDOWNLOAD = "Statement_Download_Issue";
	private static String BUG_CATEGORY_HOLDING = "Holding_Issue";
	private static String BUG_CATEGORY_BROSWER = "Browser_Issue";
	private static String BUG_CATEGORY_LATENCY = "Latency_Issue";
	private static String BUG_CATEGORY_DQ = "DQ_Issue";
	private static String BUG_CATEGORY_RECON = "Recon_Issue";
	private static String BUG_CATEGORY_OTHER = "Other_Issue";
	private static String BUG_CATEGORY_INFRA = "Infra_Issue";
	private static String BUG_CATEGORY_VALIDATION = "Validation_Issue";
	private static String BUG_CATEGORY_INCORRECT_IMPACT_ANALYSIS = "Incorrect_Impact_Analysis";

	public String getCategory(String comments, String errorCode) {

		String exceptionName = "";
		String exceptionDesc = "";
		LinkedHashSet<String> methodList = new LinkedHashSet<>();

		if (comments.toLowerCase().trim().contains("stack trace:") || comments.toLowerCase().trim().contains("stacktrace:")) {
			comments = StacktraceUtility.formatComment(comments);
			exceptionName = StacktraceUtility.findExceptionName(comments);
			exceptionDesc = StacktraceUtility.findExceptionDescription(comments, exceptionName);
			methodList.addAll(StacktraceUtility.findAllMethods(comments));
		} else if(comments.contains("Recon Error:") && comments.contains("ErrorGroup")) {
			exceptionName = "Recon Error";
			exceptionDesc = "";
			methodList.add("");
		} else {
			exceptionName = "";
			exceptionDesc = "";
			methodList.add("");
		}

		return categorizeBug(errorCode, methodList, exceptionName, exceptionDesc);
	}

	private String categorizeBug(String errorCode, LinkedHashSet<String> methods, String exception, String exceptionDesc) {

		String bugCategory = null;

		List<String> loginErrorCodesList = Arrays.asList(loginErrorCodes.split(","));
		List<String> infraErrorCodesList = Arrays.asList(infraErrorCodes.split(","));
		List<String> reconWealthErrorCodesList = Arrays.asList(reconWealthErrorCodes.split(","));

		if (loginErrorCodesList.contains(errorCode)) {
			bugCategory = BUG_CATEGORY_LOGINERROR;
		} else if (infraErrorCodesList.contains(errorCode)) {
			bugCategory = BUG_CATEGORY_INFRA;
		} else if (reconWealthErrorCodesList.contains(errorCode) || exception.equals("Recon Error")) {
			bugCategory = BUG_CATEGORY_RECON;
		} else if (errorCode.toLowerCase().contains("latency")) {
			bugCategory = BUG_CATEGORY_LATENCY;
		} else if (errorCode.toLowerCase().contains("dq")) {
			bugCategory = BUG_CATEGORY_DQ;
		} else if (errorCode.contains("413")) {
			bugCategory = BUG_CATEGORY_VALIDATION;
		} else if(errorCode.toLowerCase().contains("iia")) {
			bugCategory = BUG_CATEGORY_INCORRECT_IMPACT_ANALYSIS;
		} else if (exception.isEmpty()) {
			LOG.debug("No stacktrace bug-------");
			bugCategory = BUG_CATEGORY_OTHER;

		} else if (exception.contains("UnreachableBrowserException")
				|| exceptionDesc.toLowerCase().contains("chrome not reachable")) {
			bugCategory = BUG_CATEGORY_BROSWER;
		} else if (methods.size() == 0) {
			bugCategory = BUG_CATEGORY_OTHER;
		} else {
			boolean foundGroup = false;
			for (String method : methods) {
				method = method.toLowerCase().trim();
				if (method.toLowerCase().contains("login") || method.toLowerCase().contains("prepareforexecute")) {
					foundGroup = true;
					bugCategory = BUG_CATEGORY_LOGINERROR;
				} else if (method.toLowerCase().contains("transaction")) {
					foundGroup = true;
					bugCategory = BUG_CATEGORY_TRANSACTION;
				} else if (method.toLowerCase().endsWith("account") || method.toLowerCase().endsWith("accounts")) {
					foundGroup = true;
					bugCategory = BUG_CATEGORY_ACCOUNTSCRAPING;
				} else if (method.toLowerCase().contains("holding")) {
					foundGroup = true;
					bugCategory = BUG_CATEGORY_HOLDING;
				} else if (method.toLowerCase().contains("statement") || method.toLowerCase().contains("download")) {
					foundGroup = true;
					bugCategory = BUG_CATEGORY_STATEMENTDOWNLOAD;
				}
			}
			if (!foundGroup) {
				if (errorCode != null && !errorCode.isEmpty())
					bugCategory = BUG_CATEGORY_ERRORCODE;
				else
					bugCategory = BUG_CATEGORY_OTHER;
			}
		}
		return bugCategory;

	}

}
