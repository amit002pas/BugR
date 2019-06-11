package com.yodlee.iae.bugr.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.datatypes.bugs.Filters;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.Sort;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;


public class SyntheticBugRepositoryTest {

	@InjectMocks
	SyntheticBugRepository syntheticBugRepository;

	@Mock
	private MongoOperations mongoOperations;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testValidateStr() {
		String str = null;
		SyntheticBugRepository.validateStr(str);
		str = "Test String";
		SyntheticBugRepository.validateStr(str);
		str = "";
		SyntheticBugRepository.validateStr(str);
	}
	
	@Test
	public void testValidateList() {
		List<String> strList = new ArrayList<>();
		String str = "Test String";
		strList.add(str);
		SyntheticBugRepository.validateList(strList);

		List<String> strList1 = new ArrayList<>();
		SyntheticBugRepository.validateList(strList1);

		List<String> strList2 = null;
		SyntheticBugRepository.validateList(strList2);
	}
	
	@Test
	public void testGetSynBugsFromBugzillaIds() {
		List<Integer> intList = new ArrayList<>();
		Integer bugId1 = 12378213;
		Integer bugId2 = 23848912;
		intList.add(bugId1);
		intList.add(bugId2);
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);

		List<SyntheticBug> synBugList = new ArrayList<>();
		synBugList.add(sbug);
		
		Mockito.when(mongoOperations.find(any(Query.class), eq(SyntheticBug.class))).thenReturn(synBugList);
		syntheticBugRepository.getSynBugsFromBugzillaIds(intList);
	}
	
	@Test
	public void testGetSyntheticBugFromBugzillaId() {
		Integer bugId1 = 12378213;
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		Mockito.when(mongoOperations.findOne(any(Query.class), eq(SyntheticBug.class))).thenReturn(sbug);
		syntheticBugRepository.getSyntheticBugFromBugzillaId(bugId1);
	}
	
	@Test
	public void testFindDuplicateReconServicesBugs() {
		String sumInfo="1287323";
		String errCode="7503";
		String action="More Analysis Needed";
		String segId="6310c9a1-eaee-4415-8b0b-e2e293d70919";
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		Mockito.when(mongoOperations.findOne(any(Query.class), eq(SyntheticBug.class))).thenReturn(sbug);
		syntheticBugRepository.findDuplicateReconServicesBugs(sumInfo, errCode, segId, action);
			
		action = null;
		syntheticBugRepository.findDuplicateReconServicesBugs(sumInfo, errCode, segId, action);
	}
	
	@Test
	public void testFindDuplicateReconHoldingBugs() {
		String cusip = "378690788";
		String symbol = "GTLOX";
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("7503");
		bugFields.setWhiteboard(
				"Recon_Services;Cusip:378690788;Symbol:GTLOX;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		Mockito.when(mongoOperations.findOne(any(Query.class), eq(SyntheticBug.class))).thenReturn(sbug);
		syntheticBugRepository.findDuplicateReconHoldingBugs(cusip, symbol);
	}
	
	@Test
	public void testGetDuplicatePreSrBug() {
		String agentName="AuMacquarie";
		String errorCode="413";
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("413");
		bugFields.setWhiteboard(
				"PreSR_ErrorSegment, IAE_Category:Error_Code, Triaged 04-03-2019_ORPHIC_WEALTH");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		Mockito.when(mongoOperations.findOne(any(Query.class), eq(SyntheticBug.class))).thenReturn(sbug);
		syntheticBugRepository.getDuplicatePreSrBug(agentName, errorCode);
	}
	
	@Test
	public void testSearchBugs() throws ParseException {
		
		BugSearchParam bugSearchParam = new BugSearchParam();
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		bugSearchParam.setPageNum(1);
		
		Filters filters = new Filters();
		filters.setSourceProduct(Portfolio.PRE_SR);
		List<String> agentList = new ArrayList<>();
		String str = "AuMacquarie";
		agentList.add(str);
		filters.setAgentNames(agentList);
		List<String> errList = new ArrayList<>();
		String strErr = "403";
		errList.add(strErr);
		filters.setErrorCodes(errList);
		filters.setSiteid("43872");
		filters.setSummary("Proactive Monitoring Bugs created by PreSR AgentName: BBandTMortgage ErrorCode:403 COBRAND ID:10002812");
		filters.setWhiteboard("PreSR_ErrorSegment, IAE_Category:Error_Code, Triaged 04-03-2019_ORPHIC_WEALTH");
		filters.setKeyword("ABCD");
		bugSearchParam.setFilters(filters);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.FAILURE;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);
		
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard(
				"Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);

		List<SyntheticBug> synBugList = new ArrayList<>();
		synBugList.add(sbug);
		
		Mockito.when(mongoOperations.findOne(any(Query.class), eq(SyntheticBug.class))).thenReturn(sbug);
		syntheticBugRepository.searchBugs(bugSearchParam);
		
		
		BugSearchParam bugSearchParam1 = new BugSearchParam();
		TimeStamp timeStamp1 = new TimeStamp();
		timeStamp1.setStartTime(null);
		timeStamp1.setEndTime(null);
		bugSearchParam1.setTimeStamp(timeStamp1);

		bugSearchParam1.setPageNum(1);

		Sort sort1 = new Sort();
		BugSortBy bugSortBy1 = BugSortBy.FAILURE;
		BugSortOrder bugSortOrder1 = BugSortOrder.DESCENDING;
		sort1.setCategory(bugSortBy1);
		sort1.setOrder(bugSortOrder1);
		bugSearchParam1.setSort(sort1);
		
		syntheticBugRepository.searchBugs(bugSearchParam1);
		
		BugSearchParam bugSearchParam2= new BugSearchParam();
		Filters filters2 = new Filters();
		bugSearchParam2.setFilters(filters2);
		syntheticBugRepository.searchBugs(bugSearchParam2);
		
		TimeStamp timeStamp2 = new TimeStamp();
		bugSearchParam2.setTimeStamp(timeStamp2);
		syntheticBugRepository.searchBugs(bugSearchParam2);
		
	}
	
	@Test
	public void testSearchClosedBugs() throws ParseException {
		
		BugSearchParam bugSearchParam = new BugSearchParam();
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		bugSearchParam.setPageNum(1);
		
		Filters filters = new Filters();
		filters.setSourceProduct(Portfolio.PRE_SR);
		List<String> agentList = new ArrayList<>();
		String str = "AuMacquarie";
		agentList.add(str);
		filters.setAgentNames(agentList);
		List<String> errList = new ArrayList<>();
		String strErr = "403";
		errList.add(strErr);
		filters.setErrorCodes(errList);
		filters.setSiteid("43872");
		filters.setSummary("Proactive Monitoring Bugs created by PreSR AgentName: BBandTMortgage ErrorCode:403 COBRAND ID:10002812");
		filters.setWhiteboard("PreSR_ErrorSegment, IAE_Category:Error_Code, Triaged 04-03-2019_ORPHIC_WEALTH");
		filters.setKeyword("ABCD");
		bugSearchParam.setFilters(filters);
		syntheticBugRepository.searchClosedBugs(bugSearchParam);
		
		BugSearchParam bugSearchParam1 = new BugSearchParam();
		TimeStamp timeStamp1 = new TimeStamp();
		timeStamp1.setStartTime(null);
		timeStamp1.setEndTime(null);
		bugSearchParam1.setTimeStamp(timeStamp1);

		bugSearchParam1.setPageNum(1);
		syntheticBugRepository.searchBugs(bugSearchParam1);
		
		timeStamp1.setStartTime(dateFormat.parse(date1));
		timeStamp1.setEndTime(null);
		bugSearchParam1.setTimeStamp(timeStamp1);
		syntheticBugRepository.searchBugs(bugSearchParam1);
		
		timeStamp1.setStartTime(null);
		timeStamp1.setEndTime(dateFormat.parse(date2));
		bugSearchParam1.setTimeStamp(timeStamp1);
		syntheticBugRepository.searchBugs(bugSearchParam1);
		
		bugSearchParam1.setTimeStamp(null);
		syntheticBugRepository.searchBugs(bugSearchParam1);
	}

}
