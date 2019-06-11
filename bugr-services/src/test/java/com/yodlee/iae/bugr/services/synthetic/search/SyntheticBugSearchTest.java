package com.yodlee.iae.bugr.services.synthetic.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.any;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.datatypes.bugs.Filters;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.Sort;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.resources.responses.SyntheticSearchBugsResponse;
import com.yodlee.iae.bugr.services.synthetic.manager.SyntheticBugManager;
import com.yodlee.iae.bugr.services.synthetic.search.cache.JNBugsCache;
import com.yodlee.iae.bugr.services.synthetic.search.cache.TTRBugsCache;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchPreSrBugs;

public class SyntheticBugSearchTest {

	@InjectMocks
	SyntheticBugSearch syntheticBugSearch;

	@Mock
	private SyntheticBugManager syntheticBugManager;

	@Mock
	private SyntheticSearchBugsResponse synSearchBugsResp;

	@Mock
	private SearchPreSrBugs searchPreSrBugs;

	@Mock
	private TTRBugsCache ttrBugsCache;

	@Mock
	private JNBugsCache jnBugsCache;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSyntheticBugSearch1() throws ParseException {
		BugSearchParam bugSearchParam = new BugSearchParam();
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		bugSearchParam.setPageNum(1);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.FAILURE;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);

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

		SyntheticFields syntheticFields = new SyntheticFields();
		sbug.setSyntheticFields(syntheticFields);

		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFieldsDup.setAgentName("AuMacquarie");
		sbugDup.setBugFields(bugFieldsDup);

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbugDup);
		synBugList.add(sbug);

		Map<String, Set<Integer>> map = new HashMap<>();
		Mockito.when(syntheticBugManager.getBugSearcher(any(Portfolio.class))).thenReturn(searchPreSrBugs);
		Mockito.<Optional<List<SyntheticBug>>>when(searchPreSrBugs.getBugs(any(BugSearchParam.class)))
				.thenReturn(Optional.of(synBugList));
		Mockito.<Optional<Map<String, Set<Integer>>>>when(jnBugsCache.getCachedBugs()).thenReturn(Optional.of(map));
		Mockito.<Optional<Map<String, Set<Integer>>>>when(ttrBugsCache.getCachedBugs()).thenReturn(Optional.of(map));
		syntheticBugSearch.process(bugSearchParam);

	}

	@Test
	public void testSyntheticBugSearchApplySortAndPaginationCatch() throws ParseException {

		BugSearchParam bugSearchParam = new BugSearchParam();
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = "2019-02-24 14:04:33";
		String date2 = "2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);

		bugSearchParam.setPageNum(1);

		Sort sort = new Sort();
		BugSortBy bugSortBy = BugSortBy.FAILURE;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);

		Portfolio portfolio = Portfolio.PRE_SR;
		Filters filters = new Filters();
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
		sbugDup2.setBugFields(bugFieldsDup2);

		List<SyntheticBug> synBugList2 = new ArrayList<SyntheticBug>();
		synBugList2.add(sbugDup2);
		synBugList2.add(sbug2);

		Map<String, Set<Integer>> map2 = new HashMap<>();
		Mockito.when(syntheticBugManager.getBugSearcher(any(Portfolio.class))).thenReturn(searchPreSrBugs);
		Mockito.<Optional<List<SyntheticBug>>>when(searchPreSrBugs.getBugs(any(BugSearchParam.class)))
				.thenReturn(Optional.of(synBugList2));
		Mockito.<Optional<Map<String, Set<Integer>>>>when(jnBugsCache.getCachedBugs()).thenReturn(Optional.of(map2));
		Mockito.<Optional<Map<String, Set<Integer>>>>when(ttrBugsCache.getCachedBugs()).thenReturn(Optional.of(map2));
		syntheticBugSearch.process(bugSearchParam);

	}

	
}
