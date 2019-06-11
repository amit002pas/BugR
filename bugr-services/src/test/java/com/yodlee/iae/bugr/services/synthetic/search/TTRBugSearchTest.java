package com.yodlee.iae.bugr.services.synthetic.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.any;

import com.google.gson.Gson;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;

public class TTRBugSearchTest {

	@InjectMocks
	TTRBugSearch ttrBugSearch;
	
	@Mock
	private IBugzillaRPCClient iBugzillaRPCClient;
	
	private static Gson gson = new Gson();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void TestTTRBugSearch() {
		List<Bug> bugList = new ArrayList<>();
		Mockito.when(iBugzillaRPCClient.searchBug(any(Map.class))).thenReturn(bugList);
		ttrBugSearch.mapInput();
		ttrBugSearch.validate();
		ttrBugSearch.executeImpl();
		ttrBugSearch.mapOutput();
		
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
		bugProps.put("cf_site_id", "1233");
		bugProps.put("id", 1111);
		
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		Mockito.when(iBugzillaRPCClient.searchBug(any(Map.class))).thenReturn(bugList);
		ttrBugSearch.executeImpl();
		
	}
	
	@Test
	public void TestTTRBugSearchCatchCase() {
		List<Bug> bugList = new ArrayList<>();
		Mockito.when(iBugzillaRPCClient.searchBug(any(Map.class))).thenReturn(bugList);
		ttrBugSearch.mapInput();
		ttrBugSearch.validate();
		ttrBugSearch.executeImpl();
		ttrBugSearch.mapOutput();
		
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
		bugEntity.setId(7777777);
		
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);
		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bugProps.put("cf_site_id", "1233");
		
		Bug bug = new BugFactory().newBug().setComponent("Test").setProduct("Test").setSummary("Testing report").setVersion("1.0.6").createBug(bugProps);
		bugList.add(bug);
		Mockito.when(iBugzillaRPCClient.searchBug(any(Map.class))).thenReturn(bugList);
		ttrBugSearch.executeImpl();
		ttrBugSearch.get();
		
	}
}
