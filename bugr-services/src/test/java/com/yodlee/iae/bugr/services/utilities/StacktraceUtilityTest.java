/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;
import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;

public class StacktraceUtilityTest {
	
	private static Gson gson = new Gson();
	
	@InjectMocks
	private StacktraceUtility stacktraceUtility;
	
	@Mock
	private CachedBugsServiceImpl cachedBugsServiceImpl;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);}
	
	@Test
	public void testFormatComment1() {
		
		String stacktraceComment="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException: Accounts not loaded...	at USAABankBase.getTransaction(USAABankBase.java:2526)	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\\\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\\\":\\\"1#14\\\"}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment2() {
		
		String stacktraceComment="Stack Trace:org.openqa.selenium.WebDriverException: chrome not reachable  (Session info: chrome=43.0.2357.125)  (Driver info: chromedriver=2.16.333243 (0bfa1d3575fc1044244f21ddb82bf870944ef961),platform=Linux 3.10.0-862.11.6.el7.x86_64 x86_64) (WARNING: The serv||{\"0e6bb796-b6f8-4b7c-8f41-49eb2343b3be\":\"1#14\"}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "WebDriverException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment3() {
		
		String stacktraceComment="Stack trace:com.yodlee.dap.gatherer.gather.exceptions.GeneralException: couldn't find table with text(s): [Description]	at com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement(HTMLUtils.java:1172)	at JackHenryCC.scrapeTransactions(JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment4() {
		
		String stacktraceComment="ExceptionStacktrace:org.openqa.selenium.WebDriverException: Remote browser not reachable  (Session info: chrome=43.0.2357.125)  (Driver info: chromedriver=2.16.333243 (0bfa1d3575fc1044244f21ddb82bf870944ef961),platform=Linux 3.10.0-862.11.6.el7.x86_64 x86_64) (WARNING: The serv||{\"0e6bb796-b6f8-4b7c-8f41-49eb2343b3be\":\"1#14\"}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "WebDriverException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment5() {
		
		String stacktraceComment="stacktrace:org.openqa.selenium.GeneralException: Remote browser not reachable  (Session info: chrome=43.0.2357.125)  (Driver info: chromedriver=2.16.333243 (0bfa1d3575fc1044244f21ddb82bf870944ef961),platform=Linux 3.10.0-862.11.6.el7.x86_64 x86_64) (WARNING: The serv||{\\\"0e6bb796-b6f8-4b7c-8f41-49eb2343b3be\\\":\\\"1#14\\\"}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment6() {
		
		String stacktraceComment="Stack trace:couldn't find table with text(s): [Description] exception:	com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement  (HTMLUtils.java:1172) at JackHenryCC.scrapeTransactions(JackHenryCC.java:3)	at JackHenryCC.scrapeTransactions( com.yodlee.dap.gatherer.gather.exceptions.GeneralException: JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment7() {
		
		String stacktraceComment="Stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException Error communicating with the remote browser	com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement  (HTMLUtils.java:1172) at JackHenryCC.scrapeTransactions(JackHenryCC.java:3)	at JackHenryCC.scrapeTransactions( com.yodlee.dap.gatherer.gather.exceptions.GeneralException: JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment8() {
		
		String stacktraceComment="stack trace:com.yodlee.dap.gatherer.gather.exceptions.SiteApplicationErrorException com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement  (HTMLUtils.java:1172) at JackHenryCC.scrapeTransactions(JackHenryCC.java:3)	at JackHenryCC.scrapeTransactions( com.yodlee.dap.gatherer.gather.exceptions.GeneralException: JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment9() {
		
		String stacktraceComment="Stack trace:com.yodlee.dap.gatherer.gather.exceptions.Generalexception: couldn't find table with text(s): [Description]	at com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement(HTMLUtils.java:1172)	at JackHenryCC.scrapeTransactions(JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	
	@Test
	public void testFormatComment10() {
		
		String stacktraceComment="Stack trace:couldn't find table with text(s): [Description] exception:	com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement at JackHenryCC.settrans (HTMLUtils.java:1172) at JackHenryCC.scrapeTransactions(JackHenryCC.java:3)	at JackHenryCC.scrapeTransactions( com.yodlee.dap.gatherer.gather.exceptions.GeneralException: JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment11() {
		
		String stacktraceComment="Stack trace:couldn't find table with text(s): [Description] exception:	com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement GeneralException: JackHenryCC.java:3 (HTMLUtils.java:1172) at JackHenryCC.scrapeTransactions(JackHenryCC.java:3)	at JackHenryCC.scrapeTransactions( com.yodlee.dap.gatherer.gather.exceptions.GeneralException: JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment12() {
		
		String stacktraceComment="Stack trace:couldn't find table with text(s): [Description] exception:	com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement Generalexception:  JackHenryCC.settrans (HTMLUtils.java:1172)  JackHenryCC.scrapeTransactions(JackHenryCC.java:3)	at JackHenryCC.scrapeTransactions( com.yodlee.dap.gatherer.gather.exceptions.GeneralException: JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment13() {
		
		String stacktraceComment="SiteApplicationErrorexception: Accounts not loaded...	at USAABankBase.getTransaction(USAABankBase.java:2526)  stack trace:com.yodlee.dap.gatherer.gather.exceptions.	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\\\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\\\":\\\"1#14\\\"}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment14() {
		
		String stacktraceComment="SiteApplicationErrorexception: Accounts not loaded...	find table with text(s): [Description] : at USAABankBase.getTransaction(USAABankBase.java:2526)  stack trace:com.yodlee.dap.gatherer.gather.exceptions.	at com.yodlee.dap.gatherer.validationutils.LoanContainerHelper.getLoans(LoanContainerHelper.java:508)||{\\\"1df5337e-3dfe-4232-9ffc-a03cddc075c9\\\":\\\"1#14\\\"}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
	
	@Test
	public void testFormatComment15() {
		
		String stacktraceComment="Stack trace:couldn't find table with text(s): [Description] exception:	com.yodlee.dap.gatherer.validationutils.HTMLUtils.getTableElement at JackHenryCC.settrans (HTMLUtils.java:1172) at JackHenryCC.scrapeTransactions(JackHenryCC.java:3)	at JackHenryCC.scrapeTransactions( com.yodlee.dap.gatherer.gather.exceptions.GeneralException: JackHenryCC.java:3||{}";
		StacktraceUtility.formatComment(stacktraceComment);
		StacktraceUtility.findExceptionName(stacktraceComment);
		StacktraceUtility.findExceptionDescription(stacktraceComment, "GeneralException");
		StacktraceUtility.findAllMethods(stacktraceComment);
	}
}
