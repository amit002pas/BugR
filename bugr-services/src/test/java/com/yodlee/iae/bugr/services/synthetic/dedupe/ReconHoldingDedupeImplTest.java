package com.yodlee.iae.bugr.services.synthetic.dedupe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;

public class ReconHoldingDedupeImplTest {
	
	@InjectMocks
	ReconHoldingDedupeImpl reconHoldingDedupeImpl;
	
	@Mock
	private SyntheticBugRepository syntheticBugRepository;
	
	@Mock
	private MongoOperations mongoOperations;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindDuplicateBug() {
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setSegmentId("6310c9a1-eaee-4415-8b0b-e2e293d70919");
		sbug.setSyntheticFields(syntheticFields);
		//Mockito.when(synUtil.STACK_TRACE_MATCHER.test(any(String.class),any(String.class))).thenReturn(true);
		//Mockito.when(syntheticBugRepository.getDuplicatePreSrBug(any(String.class), any(String.class))).thenReturn(synBugList);
		reconHoldingDedupeImpl.findDuplicateBug(sbug);
		
	}
	
	@Test
	public void testUpdateDuplicateBug() {
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage: 2;Updated At: 2019-03-03 23:22");
		bugFields.setPriority("P5");
		bugFields.setCodeReviewComments("{\"holdings\":[{\"sumInfoId\":12267,\"agentName\":\"ADPRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10261814,\"itemAccountId\":10510692,\"date\":\"03/01/2019\",\"entityType\":\"TRANSACTION\",\"entityId\":30553354,\"description\":\"VANGUARD HEALTH CARE INDEX FUND - ADMIRAL CLASS-CONTRIBUTION - 401(K) MATCH CONTRIBUTIONS\",\"price\":\"87.8\",\"xignitePrice\":\"87.8\",\"xigniteSymbol\":\"VHCIX\",\"xigniteCusipNumber\":\"92204A827\",\"endOfTheDayPriceDate\":\"3/1/2019\",\"xigniteDescripion\":\"Vanguard Healthcare Index Fd Admiral Shs \",\"analysisDescription\":\"Populated Through Enrich DB\"},{\"sumInfoId\":12267,\"agentName\":\"ADPRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10261814,\"itemAccountId\":10510692,\"date\":\"03/01/2019\",\"entityType\":\"TRANSACTION\",\"entityId\":30553353,\"description\":\"VANGUARD HEALTH CARE INDEX FUND - ADMIRAL CLASS-CONTRIBUTION - SAL DEF CONTRIBUTIONS\",\"price\":\"87.8\",\"xignitePrice\":\"87.8\",\"xigniteSymbol\":\"VHCIX\",\"xigniteCusipNumber\":\"92204A827\",\"endOfTheDayPriceDate\":\"3/1/2019\",\"xigniteDescripion\":\"Vanguard Healthcare Index Fd Admiral Shs \",\"analysisDescription\":\"Populated Through Enrich DB\"}]}");
		sbug.setBugFields(bugFields);
		
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing2");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact("Impact Percentage:2.36%");
		sbugDup.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		sbugDup.setSyntheticFields(syntheticFields);
		
		reconHoldingDedupeImpl.updateDuplicateBug(sbug, sbugDup);
	}


}
