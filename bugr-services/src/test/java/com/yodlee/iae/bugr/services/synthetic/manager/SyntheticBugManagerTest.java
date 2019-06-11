package com.yodlee.iae.bugr.services.synthetic.manager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.services.synthetic.dedupe.DefaultDeduperImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ErrorSegmentDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ReconHoldingDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ReconServicesDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.processor.DefaultBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.ErrorSegmentBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.ReconHoldingBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.ReconServicesBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.search.category.DefaultSearchBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchPreSrBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchPreSrJNBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchPreSrTTRBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchReconHoldingBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchReconServicesBugs;
import com.yodlee.iae.bugr.services.synthetic.validation.DefaultBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ErrorSegmentBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ReconHoldingBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ReconServicesBugValidator;

public class SyntheticBugManagerTest {
	
	@InjectMocks
	private SyntheticBugManager syntheticBugManager;
	
	@Mock
	private ErrorSegmentDedupeImpl errorSegmentDedupeImpl;

	@Mock
	private ReconHoldingDedupeImpl reconHoldingDedupeImpl;

	@Mock
	private ReconServicesDedupeImpl reconServicesDedupeImpl;

	@Mock
	private DefaultDeduperImpl defaultDeduperImpl;

	@Mock
	private ReconHoldingBugProcessor reconHoldingBugProcessor;

	@Mock
	private ReconServicesBugProcessor reconServicesBugProcessor;

	@Mock
	private ErrorSegmentBugProcessor errorSegmentBugProcessor;

	@Mock
	private DefaultBugProcessor defaultBugProcessor;

	@Mock
	private SearchPreSrBugs searchPreSrBugs;

	@Mock
	private SearchReconHoldingBugs searchReconHoldingBugs;

	@Mock
	private SearchReconServicesBugs searchReconServicesBugs;

	@Mock
	private SearchPreSrJNBugs searchPreSrJNBugs;

	@Mock
	private SearchPreSrTTRBugs searchPreSrTTRBugs;

	@Mock
	private DefaultSearchBugs defaultSearchBugs;

	@Mock
	private DefaultBugValidator defaultBugValidator;

	@Mock
	private ErrorSegmentBugValidator errorSegmentBugValidator;

	@Mock
	private ReconHoldingBugValidator reconHoldingBugValidator;

	@Mock
	private ReconServicesBugValidator reconServicesBugValidator;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetBugValidator() {
		syntheticBugManager.getBugValidator(Portfolio.PRE_SR);
		syntheticBugManager.getBugValidator(Portfolio.PRE_SR_JUGGERNAUT);
		syntheticBugManager.getBugValidator(Portfolio.PRE_SR_TTR);
		syntheticBugManager.getBugValidator(Portfolio.RECON_HOLDING);
		syntheticBugManager.getBugValidator(Portfolio.RECON_SERVICES);
		syntheticBugManager.getBugValidator(null);
	}
	
	@Test
	public void testGetBugDeduper() {
		syntheticBugManager.getBugDeduper(Portfolio.PRE_SR);
		syntheticBugManager.getBugDeduper(Portfolio.PRE_SR_JUGGERNAUT);
		syntheticBugManager.getBugDeduper(Portfolio.PRE_SR_TTR);
		syntheticBugManager.getBugDeduper(Portfolio.RECON_HOLDING);
		syntheticBugManager.getBugDeduper(Portfolio.RECON_SERVICES);
		syntheticBugManager.getBugDeduper(null);
	}
	
	@Test
	public void testGetBugProcessor() {
		syntheticBugManager.getBugProcessor(Portfolio.PRE_SR);
		syntheticBugManager.getBugProcessor(Portfolio.PRE_SR_JUGGERNAUT);
		syntheticBugManager.getBugProcessor(Portfolio.PRE_SR_TTR);
		syntheticBugManager.getBugProcessor(Portfolio.RECON_HOLDING);
		syntheticBugManager.getBugProcessor(Portfolio.RECON_SERVICES);
		syntheticBugManager.getBugProcessor(null);
	}
	
	@Test
	public void testGetBugSearcher() {
		syntheticBugManager.getBugSearcher(Portfolio.PRE_SR);
		syntheticBugManager.getBugSearcher(Portfolio.PRE_SR_JUGGERNAUT);
		syntheticBugManager.getBugSearcher(Portfolio.PRE_SR_TTR);
		syntheticBugManager.getBugSearcher(Portfolio.RECON_HOLDING);
		syntheticBugManager.getBugSearcher(Portfolio.RECON_SERVICES);
		syntheticBugManager.getBugSearcher(null);
	}
}
