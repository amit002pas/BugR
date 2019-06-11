package com.yodlee.iae.bugr.services.synthetic.processor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;

public class ReconHoldingBugProcessorTest {
	
	@InjectMocks
	ReconHoldingBugProcessor reconHoldingBugProcessor;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testProcessBugContents() {
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Holdings;Cusip:2138213;Symbol:AHSBVCJ");
		bugFields.setCustomer("All");
		bugFields.setImpact("Impact Percentage:2.36%");
		bugFields.setCodeReviewComments("{\"holdings\":[{\"sumInfoId\":12267,\"agentName\":\"ADPRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10261814,\"itemAccountId\":10510692,\"date\":\"03/01/2019\",\"entityType\":\"TRANSACTION\",\"entityId\":30553354,\"description\":\"VANGUARD HEALTH CARE INDEX FUND - ADMIRAL CLASS-CONTRIBUTION - 401(K) MATCH CONTRIBUTIONS\",\"price\":\"87.8\",\"xignitePrice\":\"87.8\",\"xigniteSymbol\":\"VHCIX\",\"xigniteCusipNumber\":\"92204A827\",\"endOfTheDayPriceDate\":\"3/1/2019\",\"xigniteDescripion\":\"Vanguard Healthcare Index Fd Admiral Shs \",\"analysisDescription\":\"Populated Through Enrich DB\"},{\"sumInfoId\":12267,\"agentName\":\"ADPRetirement\",\"cobrandId\":10019356,\"cacheItemId\":10261814,\"itemAccountId\":10510692,\"date\":\"03/01/2019\",\"entityType\":\"TRANSACTION\",\"entityId\":30553353,\"description\":\"VANGUARD HEALTH CARE INDEX FUND - ADMIRAL CLASS-CONTRIBUTION - SAL DEF CONTRIBUTIONS\",\"price\":\"87.8\",\"xignitePrice\":\"87.8\",\"xigniteSymbol\":\"VHCIX\",\"xigniteCusipNumber\":\"92204A827\",\"endOfTheDayPriceDate\":\"3/1/2019\",\"xigniteDescripion\":\"Vanguard Healthcare Index Fd Admiral Shs \",\"analysisDescription\":\"Populated Through Enrich DB\"}]}");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		sbug.setSyntheticFields(syntheticFields);
		
		reconHoldingBugProcessor.processBugContents(sbug);
	}
}
