package com.yodlee.iae.bugr.services.synthetic.search.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Matchers.any;

import com.google.gson.Gson;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.datatypes.bugs.CachedBugsData;
import com.yodlee.iae.bugr.datatypes.bugs.Filters;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.Sort;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;
import com.yodlee.iae.bugr.services.utilities.SortFilterUtility;

public class TTRBugsCacheTest {
	
	@InjectMocks
	private TTRBugsCache ttrBugsCache;

	@Mock
	private SyntheticBugRepository syntheticBugRepository;

	@Mock
	private CachedBugsServiceImpl cachedBugsServiceImpl;
	
	@Mock
	CachedBugsData cachedBugsData;
	
	private static Gson gson = new Gson();
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testTTRCache() throws ParseException {
		
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
		
		
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue><TTR-ALERT> Dummy issue Proactive monitoring created for 7503");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
	//	bugEntity.setId("7777777");
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("status","NEW");
		bugProps.put("creator","Serviceissueanalyzertool");
		bugProps.put("whiteboard","IAT_TESTING_INVALI");
		
		List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		cachedBugsData.setBugs(bugList);
		
		Mockito.when(syntheticBugRepository.searchBugs(any(BugSearchParam.class))).thenReturn(synBugList);
		Mockito.when(cachedBugsServiceImpl.getCachedBugsData()).thenReturn(cachedBugsData);
		Mockito.when(cachedBugsData.getBugs()).thenReturn(bugList);
		SortFilterUtility.filterIATBugs(bugList);
		ttrBugsCache.get();
		ttrBugsCache.getCachedBugs();
		
		Map<String, Set<Integer>> map = new HashMap<>();
		ttrBugsCache.accept(map);
		ttrBugsCache.run();
	}

}
