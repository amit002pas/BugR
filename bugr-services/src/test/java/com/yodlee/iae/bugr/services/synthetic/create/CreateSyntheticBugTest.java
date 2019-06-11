/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.create;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ErrorSegmentDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ReconServicesDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.manager.SyntheticBugManager;
import com.yodlee.iae.bugr.services.synthetic.mapper.SyntheticBugMapper;
import com.yodlee.iae.bugr.services.synthetic.processor.ReconServicesBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.ErrorSegmentBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.update.UpdateBugzillaBug;
import com.yodlee.iae.bugr.services.synthetic.validation.ErrorSegmentBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ReconServicesBugValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CreateSyntheticBugTest {

	@InjectMocks
	private CreateSyntheticBug createSyntheticBug;

	@Mock
	private MongoOperations mongoOperations;

	@Mock
	private SyntheticBugMapper syntheticBugMapper;

	@Mock
	private CreateBugzillaBug createBugzillaBug;

	@Mock
	private UpdateBugzillaBug updateBugzillaBug;
	
	@Mock
	private SyntheticBugManager syntheticBugManager;
	
	@Mock
	private ReconServicesBugValidator reconServicesBugValidator;
	
	@Mock
	private ReconServicesBugProcessor reconServicesBugProcessor;
	
	@Mock
	private ReconServicesDedupeImpl reconServicesDedupeImpl;
	
	@Mock
	private ErrorSegmentBugValidator errorSegmentBugValidator;
	
	@Mock
	private ErrorSegmentBugProcessor errorSegmentBugProcessor;
	
	@Mock
	private ErrorSegmentDedupeImpl errorSegmentDedupeImpl;
	
	@Mock
	private ApplicationContext ctx;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(createSyntheticBug, "reconWealthErrorCodes", "563,7300,7301,7302,7303,7304,7305,7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7321,7323,7324,7325,7326,7327,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7415,7419,7420,7422,7424,7500,7501,7502,7503,7505,7506,7508,7600,7601,7602");
	}

	@Test
	public void testCreateSynBugReconCase() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Proactive monitoring created for 7503");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing");
		bugFieldsDup.setErrorcode("7503");
		bugFieldsDup.setWhiteboard("Recon_Services");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFieldsDup.setAgentName("AuMacquarie");
		sbugDup.setBugFields(bugFieldsDup);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(true);
		syntheticFields.setSyntheticBugStatus(SyntheticBugStatus.ACTIVE);
		sbugDup.setSyntheticFields(syntheticFields);

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbugDup);

		SynBugResponse synBugResponse = new SynBugResponse();
		synBugResponse.setStatus("success");
		
		
		Mockito.when(syntheticBugManager.getBugProcessor(any(Portfolio.class))).thenReturn(reconServicesBugProcessor);
		Mockito.doNothing().when(reconServicesBugProcessor).processBugContents(any(SyntheticBug.class));
		Mockito.when(syntheticBugManager.getBugDeduper(any(Portfolio.class))).thenReturn(reconServicesDedupeImpl);
		Mockito.<Optional<SyntheticBug>>when(reconServicesDedupeImpl.findDuplicateBug(any(SyntheticBug.class))).thenReturn(Optional.of(sbugDup));
		Mockito.doNothing().when(reconServicesDedupeImpl).updateDuplicateBug(any(SyntheticBug.class),any(SyntheticBug.class));
		Mockito.<Optional<List<SyntheticBug>>>when(syntheticBugMapper.doMapping(any(List.class))).thenReturn(Optional.of(synBugList));
		Mockito.when(syntheticBugManager.getBugValidator(any(Portfolio.class))).thenReturn(reconServicesBugValidator);
		Mockito.when(reconServicesBugValidator.validateBug(any(SyntheticBug.class))).thenReturn(synBugResponse);
		createSyntheticBug.process(bugEntity);
		
	}
	
	@Test
	public void testCreateSynBugPreSrCase() {

		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Proactive monitoring created for 412");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCreateBugzillaBug(true);
		
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("PreSR_ErrorSegment");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFieldsDup.setAgentName("AuMacquarie");
		sbugDup.setBugFields(bugFieldsDup);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(false);
		syntheticFields.setSyntheticBugStatus(SyntheticBugStatus.INVALID);
		sbugDup.setSyntheticFields(syntheticFields);

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbugDup);

		SynBugResponse synBugResponse = new SynBugResponse();
		synBugResponse.setStatus("success");
		
		
		Mockito.when(syntheticBugManager.getBugProcessor(any(Portfolio.class))).thenReturn(errorSegmentBugProcessor);
		Mockito.doNothing().when(errorSegmentBugProcessor).processBugContents(any(SyntheticBug.class));
		Mockito.when(syntheticBugManager.getBugDeduper(any(Portfolio.class))).thenReturn(errorSegmentDedupeImpl);
		Mockito.<Optional<SyntheticBug>>when(errorSegmentDedupeImpl.findDuplicateBug(any(SyntheticBug.class))).thenReturn(Optional.of(sbugDup));
		Mockito.doNothing().when(errorSegmentDedupeImpl).updateDuplicateBug(any(SyntheticBug.class),any(SyntheticBug.class));
		Mockito.<Optional<List<SyntheticBug>>>when(syntheticBugMapper.doMapping(any(List.class))).thenReturn(Optional.of(synBugList));
		Mockito.when(syntheticBugManager.getBugValidator(any(Portfolio.class))).thenReturn(errorSegmentBugValidator);
		Mockito.when(errorSegmentBugValidator.validateBug(any(SyntheticBug.class))).thenReturn(synBugResponse);
		createSyntheticBug.process(bugEntity);
		
	
	}
	
	@Test
	public void testCreateSynBugNoDupBugCase() {
		
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Proactive monitoring created for 412");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCreateBugzillaBug(true);
		
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("PreSR_ErrorSegment");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFieldsDup.setAgentName("AuMacquarie");
		sbugDup.setBugFields(bugFieldsDup);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(false);
		syntheticFields.setSyntheticBugStatus(SyntheticBugStatus.INVALID);
		sbugDup.setSyntheticFields(syntheticFields);

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbugDup);

		SynBugResponse synBugResponse = new SynBugResponse();
		synBugResponse.setStatus("success");
		
		Mockito.when(syntheticBugManager.getBugProcessor(any(Portfolio.class))).thenReturn(errorSegmentBugProcessor);
		Mockito.doNothing().when(errorSegmentBugProcessor).processBugContents(any(SyntheticBug.class));
		Mockito.when(syntheticBugManager.getBugDeduper(any(Portfolio.class))).thenReturn(errorSegmentDedupeImpl);	
		Mockito.<Optional<SyntheticBug>>when(errorSegmentDedupeImpl.findDuplicateBug(any(SyntheticBug.class))).thenReturn(Optional.empty());
		Mockito.doNothing().when(errorSegmentDedupeImpl).updateDuplicateBug(any(SyntheticBug.class),any(SyntheticBug.class));
		Mockito.<Optional<List<SyntheticBug>>>when(syntheticBugMapper.doMapping(any(List.class))).thenReturn(Optional.of(synBugList));
		Mockito.when(syntheticBugManager.getBugValidator(any(Portfolio.class))).thenReturn(errorSegmentBugValidator);
		Mockito.when(errorSegmentBugValidator.validateBug(any(SyntheticBug.class))).thenReturn(synBugResponse);
		Mockito.doNothing().when(mongoOperations).save(any(SyntheticBug.class),any(String.class));
		createSyntheticBug.process(bugEntity);
	}
	
	@Test
	public void testCreateSynBugRespFailAndReconHoldingCase() {
		
		BugEntity bugEntity = new BugEntity();
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Proactive monitoring created for 412");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCreateBugzillaBug(false);
		
		SyntheticBug sbugDup = new SyntheticBug();
		BugFields bugFieldsDup = new BugFields();
		bugFieldsDup.setComment("As a part of Orphic testing");
		bugFieldsDup.setErrorcode("403");
		bugFieldsDup.setWhiteboard("PreSR_ErrorSegment");
		bugFieldsDup.setCustomer("All");
		bugFieldsDup.setImpact(
				"Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		bugFieldsDup.setAgentName("AuMacquarie");
		sbugDup.setBugFields(bugFieldsDup);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(false);
		syntheticFields.setSyntheticBugStatus(SyntheticBugStatus.INACTIVE);
		sbugDup.setSyntheticFields(syntheticFields);

		List<SyntheticBug> synBugList = new ArrayList<SyntheticBug>();
		synBugList.add(sbugDup);

		SynBugResponse synBugResponse = new SynBugResponse();
		synBugResponse.setStatus("failed");
		
		Mockito.<Optional<List<SyntheticBug>>>when(syntheticBugMapper.doMapping(any(List.class))).thenReturn(Optional.of(synBugList));
		Mockito.when(syntheticBugManager.getBugValidator(any(Portfolio.class))).thenReturn(errorSegmentBugValidator);
		Mockito.when(errorSegmentBugValidator.validateBug(any(SyntheticBug.class))).thenReturn(synBugResponse);
		createSyntheticBug.process(bugEntity);
		
		bugFieldsDup.setWhiteboard("Cusip:");
		sbugDup.setBugFields(bugFieldsDup);
		createSyntheticBug.process(bugEntity);
		
		bugFieldsDup.setWhiteboard("");
		sbugDup.setBugFields(bugFieldsDup);
		createSyntheticBug.process(bugEntity);
	}
	
}
