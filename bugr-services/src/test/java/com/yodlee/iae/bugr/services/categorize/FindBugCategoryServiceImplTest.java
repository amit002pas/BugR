/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.

 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.categorize;

import java.util.LinkedHashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class FindBugCategoryServiceImplTest {
	
	@InjectMocks
	private FindBugCategoryServiceImpl findBugCategoryServiceImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(findBugCategoryServiceImpl, "loginErrorCodes", "402,419,506,519,429,407");
		ReflectionTestUtils.setField(findBugCategoryServiceImpl, "infraErrorCodes", "404,601,401");
		ReflectionTestUtils.setField(findBugCategoryServiceImpl, "reconWealthErrorCodes", "563,7300,7301,7302,7303,7304,7305,7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7321,7323,7324,7325,7326,7327,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7415,7419,7420,7422,7424,7500,7501,7502,7503,7505,7506,7508,7600,7601,7602");
	}
	
	@Test
	public void testGetCategory1() {
		String comments="Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.GeneralException: java.net.ConnectException: Connection timed outConnection timed out	at com.yodlee.dap.gatherer.commonutils.XProcessor.getFeedContents(XProcessor.java:410)	at com.yodlee.dap.gatherer.commonut||{}";
		String errorCode="403";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		errorCode="7503";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		errorCode="Latency";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		errorCode="DQ";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		errorCode="413";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		errorCode="IIA";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		errorCode="419";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
	}
	
	@Test
	public void testGetCategory2() {
		String comments="StackTrace:com.yodlee.dap.gatherer.gather.exceptions.UnreachableBrowserException: java.net.ConnectException: Connection timed outConnection timed out	at com.yodlee.dap.gatherer.commonutils.XProcessor.getFeedContents(XProcessor.java:410)	at com.yodlee.dap.gatherer.commonut||{}";
		String errorCode="403";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="StackTrace:com.yodlee.dap.gatherer.gather.";
		LinkedHashSet<String> set=  new LinkedHashSet<>();
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Accounts not loaded...	at USAABankBase.getAccounts(USAABankBase.java:2526)	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\":\"1#14\"}";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Accounts not loaded...	at USAABankBase.getTransaction(USAABankBase.java:2526)	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\":\"1#14\"}";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Accounts not loaded...	at USAABankBase.login(USAABankBase.java:2526)	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\":\"1#14\"}";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Accounts not loaded...	at USAABankBase.getholding(USAABankBase.java:2526)	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\":\"1#14\"}";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Accounts not loaded...	at USAABankBase.getstatement(USAABankBase.java:2526)	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\":\"1#14\"}";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Accounts not loaded...	at USAABankBase.getProfile(USAABankBase.java:2526)	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\":\"1#14\"}";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		errorCode="";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
	}
	
	@Test
	public void testGetCategory3() {
		String comments="Recon Error:7503,ErrorGroup:";
		String errorCode="404";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="Recon Error:7503,ErrorGroup:";
		errorCode="403";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
		comments="";
		findBugCategoryServiceImpl.getCategory(comments, errorCode);
	}
	

}
