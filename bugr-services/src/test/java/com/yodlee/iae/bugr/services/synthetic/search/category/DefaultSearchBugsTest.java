package com.yodlee.iae.bugr.services.synthetic.search.category;

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
import static org.mockito.Matchers.any;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.datatypes.bugs.Sort;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

public class DefaultSearchBugsTest {

	@InjectMocks
	private DefaultSearchBugs defaultSearchBugs;

	@Mock
	private SyntheticBugRepository syntheticBugRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetBugs() throws ParseException {
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

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbug);
		
		Mockito.when(syntheticBugRepository.searchBugs(any(BugSearchParam.class))).thenReturn(synBugList);
		defaultSearchBugs.getBugs(bugSearchParam);

	}
}
