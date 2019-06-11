package com.yodlee.iae.bugr.services.synthetic.bugzillasync;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

public class SyncSyntheticBugsWithBugzillaTest {
	
	@InjectMocks
	private SyncSyntheticBugsWithBugzilla syncSyntheticBugsWithBugzilla;
	@Mock
	private SynUtil synUtil;
	@Mock
	private SyntheticBugRepository syntheticBugRepository;
	@Mock
	private MongoOperations mongoOperations;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testProcess() {
		Date date = new Date();
		syncSyntheticBugsWithBugzilla.process(date);
		
		/*List<Bug> bugList = new ArrayList<>();
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug();
		bugList.add(bug);
		
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
		
		
		
		Mockito.when(synUtil.getBugzillaClosedBugs(any(Date.class))).thenReturn(bugList);
		Mockito.when(syntheticBugRepository.getSynBugsFromBugzillaIds(eq(intList))).thenReturn(synBugList);
		syncSyntheticBugsWithBugzilla.process(date);*/
	}
}
