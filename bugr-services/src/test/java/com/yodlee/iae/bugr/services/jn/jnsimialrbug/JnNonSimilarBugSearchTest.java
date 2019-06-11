/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.jn.jnsimialrbug;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.datatypes.bugs.Criteria;
import com.yodlee.iae.bugr.datatypes.bugs.Filters;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.Sort;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.resources.responses.SyntheticSearchBugsResponse;
import com.yodlee.iae.bugr.services.synthetic.search.cache.JNBugsCache;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

public class JnNonSimilarBugSearchTest {

	@InjectMocks
	JnNonSimilarBugSearch jnNonSimilarBugSearch;

	@Mock
	private JNBugsCache jnBugsCache;

	@Mock
	private SyntheticSearchBugsResponse searchBugsResponse;

	@Mock
	private SynUtil synUtil;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testJnNonSimilarBugSearchDQ() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setPageNum(1);
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.LAST_CHANGE_TIME;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		Criteria criteria = Criteria.JUGGERNAUT_DQERROR;
		filters.setCriteria(criteria);
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("403");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSuminfo("4479");
		bugFields2.setImpact("");
		bugFields2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbug2.setBugFields(bugFields2);

		SyntheticFields syntheticFields2 = new SyntheticFields();
		sbug2.setSyntheticFields(syntheticFields2);

		SyntheticBug sbugDup2 = new SyntheticBug();
		BugFields bugFieldsDup2 = new BugFields();
		bugFieldsDup2.setComment("As a part of Orphic testing");
		bugFieldsDup2.setErrorcode("403");
		bugFieldsDup2.setWhiteboard("");
		bugFieldsDup2.setCustomer("All");
		bugFieldsDup2.setImpact("");
		bugFieldsDup2.setAgentName("AuMacquarie");
		bugFieldsDup2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbugDup2.setBugFields(bugFieldsDup2);

		List<SyntheticBug> synBugList2 = new ArrayList<SyntheticBug>();
		synBugList2.add(sbugDup2);
		synBugList2.add(sbug2);
		List<Map<String, String>> listOfmap = new ArrayList<Map<String,String>>();

		Mockito.when(jnBugsCache.getDQJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(synUtil.getDataForCSV(synBugList2)).thenReturn(listOfmap);

		jnNonSimilarBugSearch.process(bugSearchParam);

	}
	@Test
	public void testJnNonSimilarBugSearchDQFull() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setPageNum(-1);
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.LAST_CHANGE_TIME;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		Criteria criteria = Criteria.JUGGERNAUT_DQERROR;
		filters.setCriteria(criteria);
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("403");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSuminfo("4479");
		bugFields2.setImpact("");
		bugFields2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbug2.setBugFields(bugFields2);

		SyntheticFields syntheticFields2 = new SyntheticFields();
		sbug2.setSyntheticFields(syntheticFields2);

		SyntheticBug sbugDup2 = new SyntheticBug();
		BugFields bugFieldsDup2 = new BugFields();
		bugFieldsDup2.setComment("As a part of Orphic testing");
		bugFieldsDup2.setErrorcode("403");
		bugFieldsDup2.setWhiteboard("");
		bugFieldsDup2.setCustomer("All");
		bugFieldsDup2.setImpact("");
		bugFieldsDup2.setAgentName("AuMacquarie");
		bugFieldsDup2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbugDup2.setBugFields(bugFieldsDup2);

		List<SyntheticBug> synBugList2 = new ArrayList<SyntheticBug>();
		synBugList2.add(sbugDup2);
		synBugList2.add(sbug2);
		List<Map<String, String>> listOfmap = new ArrayList<Map<String,String>>();

		Mockito.when(jnBugsCache.getDQJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(synUtil.getDataForCSV(synBugList2)).thenReturn(listOfmap);

		jnNonSimilarBugSearch.process(bugSearchParam);

	}

	@Test
	public void testJnNonSimilarBugSearchHARD() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setPageNum(1);
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.LAST_CHANGE_TIME;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		Criteria criteria = Criteria.JUGGERNAUT_HARDERROR;
		filters.setCriteria(criteria);
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("403");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSuminfo("4479");
		bugFields2.setImpact("");
		bugFields2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbug2.setBugFields(bugFields2);

		SyntheticFields syntheticFields2 = new SyntheticFields();
		sbug2.setSyntheticFields(syntheticFields2);

		SyntheticBug sbugDup2 = new SyntheticBug();
		BugFields bugFieldsDup2 = new BugFields();
		bugFieldsDup2.setComment("As a part of Orphic testing");
		bugFieldsDup2.setErrorcode("403");
		bugFieldsDup2.setWhiteboard("");
		bugFieldsDup2.setCustomer("All");
		bugFieldsDup2.setImpact("");
		bugFieldsDup2.setAgentName("AuMacquarie");
		bugFieldsDup2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbugDup2.setBugFields(bugFieldsDup2);

		List<SyntheticBug> synBugList2 = new ArrayList<SyntheticBug>();
		synBugList2.add(sbugDup2);
		synBugList2.add(sbug2);
		List<Map<String, String>> listOfmap = new ArrayList<Map<String,String>>();

		Mockito.when(jnBugsCache.getHardErrorJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(synUtil.getDataForCSV(synBugList2)).thenReturn(listOfmap);

		jnNonSimilarBugSearch.process(bugSearchParam);

	}

	@Test
	public void testJnNonSimilarBugSearchHARDFull() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setPageNum(-1);
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.LAST_CHANGE_TIME;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		Criteria criteria = Criteria.JUGGERNAUT_HARDERROR;
		filters.setCriteria(criteria);
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("403");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSuminfo("4479");
		bugFields2.setImpact("");
		bugFields2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbug2.setBugFields(bugFields2);

		SyntheticFields syntheticFields2 = new SyntheticFields();
		sbug2.setSyntheticFields(syntheticFields2);

		SyntheticBug sbugDup2 = new SyntheticBug();
		BugFields bugFieldsDup2 = new BugFields();
		bugFieldsDup2.setComment("As a part of Orphic testing");
		bugFieldsDup2.setErrorcode("403");
		bugFieldsDup2.setWhiteboard("");
		bugFieldsDup2.setCustomer("All");
		bugFieldsDup2.setImpact("");
		bugFieldsDup2.setAgentName("AuMacquarie");
		bugFieldsDup2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbugDup2.setBugFields(bugFieldsDup2);

		List<SyntheticBug> synBugList2 = new ArrayList<SyntheticBug>();
		synBugList2.add(sbugDup2);
		synBugList2.add(sbug2);
		List<Map<String, String>> listOfmap = new ArrayList<Map<String,String>>();

		Mockito.when(jnBugsCache.getHardErrorJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(synUtil.getDataForCSV(synBugList2)).thenReturn(listOfmap);

		jnNonSimilarBugSearch.process(bugSearchParam);

	}
	@Test
	public void testJnNonSimilarBugSearchNONSimilar() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setPageNum(1);
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.LAST_CHANGE_TIME;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		Criteria criteria = Criteria.JUGGERNAUT_NONSIMILAR;
		filters.setCriteria(criteria);
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("403");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSuminfo("4479");
		bugFields2.setImpact("");
		bugFields2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbug2.setBugFields(bugFields2);

		SyntheticFields syntheticFields2 = new SyntheticFields();
		sbug2.setSyntheticFields(syntheticFields2);

		SyntheticBug sbugDup2 = new SyntheticBug();
		BugFields bugFieldsDup2 = new BugFields();
		bugFieldsDup2.setComment("As a part of Orphic testing");
		bugFieldsDup2.setErrorcode("403");
		bugFieldsDup2.setWhiteboard("");
		bugFieldsDup2.setCustomer("All");
		bugFieldsDup2.setImpact("");
		bugFieldsDup2.setAgentName("AuMacquarie");
		bugFieldsDup2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbugDup2.setBugFields(bugFieldsDup2);

		List<SyntheticBug> synBugList2 = new ArrayList<SyntheticBug>();
		synBugList2.add(sbugDup2);
		synBugList2.add(sbug2);
		List<Map<String, String>> listOfmap = new ArrayList<Map<String,String>>();

		Mockito.when(jnBugsCache.getDQJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(jnBugsCache.getHardErrorJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(synUtil.getDataForCSV(synBugList2)).thenReturn(listOfmap);

		jnNonSimilarBugSearch.process(bugSearchParam);

	}
	@Test
	public void testJnNonSimilarBugSearchNONSimilarFull() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setPageNum(-1);
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.LAST_CHANGE_TIME;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		Criteria criteria = Criteria.JUGGERNAUT_NONSIMILAR;
		filters.setCriteria(criteria);
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

		SyntheticBug sbug2 = new SyntheticBug();
		BugFields bugFields2 = new BugFields();
		bugFields2.setComment("As a part of Orphic testing");
		bugFields2.setErrorcode("403");
		bugFields2.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields2.setCustomer("All");
		bugFields2.setSuminfo("4479");
		bugFields2.setImpact("");
		bugFields2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbug2.setBugFields(bugFields2);

		SyntheticFields syntheticFields2 = new SyntheticFields();
		sbug2.setSyntheticFields(syntheticFields2);

		SyntheticBug sbugDup2 = new SyntheticBug();
		BugFields bugFieldsDup2 = new BugFields();
		bugFieldsDup2.setComment("As a part of Orphic testing");
		bugFieldsDup2.setErrorcode("403");
		bugFieldsDup2.setWhiteboard("");
		bugFieldsDup2.setCustomer("All");
		bugFieldsDup2.setImpact("");
		bugFieldsDup2.setAgentName("AuMacquarie");
		bugFieldsDup2.setLast_change_time("Sun Mar 31 09:18:40 PDT 2019");
		sbugDup2.setBugFields(bugFieldsDup2);

		List<SyntheticBug> synBugList2 = new ArrayList<SyntheticBug>();
		synBugList2.add(sbugDup2);
		synBugList2.add(sbug2);
		List<Map<String, String>> listOfmap = new ArrayList<Map<String,String>>();

		Mockito.when(jnBugsCache.getDQJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(jnBugsCache.getHardErrorJNBugs()).thenReturn(Optional.of(synBugList2));
		Mockito.when(synUtil.getDataForCSV(synBugList2)).thenReturn(listOfmap);

		jnNonSimilarBugSearch.process(bugSearchParam);

	}
	@Test
	public void testJnNonSimilarBugSearchFalse() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setPageNum(1);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.LAST_CHANGE_TIME;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		Criteria criteria = null;
		filters.setCriteria(criteria);
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

		jnNonSimilarBugSearch.process(bugSearchParam);

	}
}
