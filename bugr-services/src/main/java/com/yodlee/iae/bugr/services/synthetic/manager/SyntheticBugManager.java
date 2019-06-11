package com.yodlee.iae.bugr.services.synthetic.manager;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.services.synthetic.dedupe.DefaultDeduperImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ErrorSegmentDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ReconHoldingDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.ReconServicesDedupeImpl;
import com.yodlee.iae.bugr.services.synthetic.dedupe.SyntheticBugDeduper;
import com.yodlee.iae.bugr.services.synthetic.processor.DefaultBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.ErrorSegmentBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.ReconHoldingBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.ReconServicesBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.processor.SyntheticBugProcessor;
import com.yodlee.iae.bugr.services.synthetic.search.category.DefaultSearchBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.ISearchBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchPreSrBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchPreSrJNBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchPreSrTTRBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchReconHoldingBugs;
import com.yodlee.iae.bugr.services.synthetic.search.category.SearchReconServicesBugs;
import com.yodlee.iae.bugr.services.synthetic.validation.DefaultBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ErrorSegmentBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ReconHoldingBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ReconServicesBugValidator;
import com.yodlee.iae.bugr.services.synthetic.validation.ISyntheticBugValidator;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class SyntheticBugManager {

	@Inject
	private ErrorSegmentDedupeImpl errorSegmentDedupeImpl;

	@Inject
	private ReconHoldingDedupeImpl reconHoldingDedupeImpl;

	@Inject
	private ReconServicesDedupeImpl reconServicesDedupeImpl;

	@Inject
	private DefaultDeduperImpl defaultDeduperImpl;

	@Inject
	private ReconHoldingBugProcessor reconHoldingBugProcessor;

	@Inject
	private ReconServicesBugProcessor reconServicesBugProcessor;

	@Inject
	private ErrorSegmentBugProcessor errorSegmentBugProcessor;

	@Inject
	private DefaultBugProcessor defaultBugProcessor;

	@Inject
	private SearchPreSrBugs searchPreSrBugs;

	@Inject
	private SearchReconHoldingBugs searchReconHoldingBugs;

	@Inject
	private SearchReconServicesBugs searchReconServicesBugs;

	@Inject
	private SearchPreSrJNBugs searchPreSrJNBugs;

	@Inject
	private SearchPreSrTTRBugs searchPreSrTTRBugs;

	@Inject
	private DefaultSearchBugs defaultSearchBugs;

	@Inject
	private DefaultBugValidator defaultBugValidator;

	@Inject
	private ErrorSegmentBugValidator errorSegmentBugValidator;

	@Inject
	private ReconHoldingBugValidator reconHoldingBugValidator;

	@Inject
	private ReconServicesBugValidator reconServicesBugValidator;

	/**
	 * Get Bug Validator based on the Portfolio category
	 * 
	 * @param portfolio
	 * @return
	 */
	public ISyntheticBugValidator getBugValidator(Portfolio portfolio) {
		if (portfolio == null) {
			return defaultBugValidator;
		}
		switch (portfolio) {
		case PRE_SR:
			return errorSegmentBugValidator;
		case RECON_HOLDING:
			return reconHoldingBugValidator;
		case RECON_SERVICES:
			return reconServicesBugValidator;
		default:
			return null;
		}
	}

	/**
	 * Get Bug Deduper based on the Portfolio category for finding duplicate bugs
	 * 
	 * @param portfolio
	 * @return
	 */
	public SyntheticBugDeduper getBugDeduper(Portfolio portfolio) {
		if (portfolio == null) {
			return defaultDeduperImpl;
		}
		switch (portfolio) {
		case PRE_SR:
			return errorSegmentDedupeImpl;
		case RECON_HOLDING:
			return reconHoldingDedupeImpl;
		case RECON_SERVICES:
			return reconServicesDedupeImpl;
		default:
			return null;
		}
	}

	/**
	 * 
	 * Get Bug Processor based on the Portfolio category for bug processing
	 * 
	 * @param portfolio
	 * @return
	 */
	public SyntheticBugProcessor getBugProcessor(Portfolio portfolio) {
		if (portfolio == null) {
			return defaultBugProcessor;
		}
		switch (portfolio) {
		case PRE_SR:
			return errorSegmentBugProcessor;
		case RECON_HOLDING:
			return reconHoldingBugProcessor;
		case RECON_SERVICES:
			return reconServicesBugProcessor;
		default:
			return null;
		}
	}

	/**
	 * Get Bug Searcher based on the Portfolio category
	 * 
	 * @param portfolio
	 * @return
	 */
	public ISearchBugs getBugSearcher(Portfolio portfolio) {
		if (portfolio == null) {
			return defaultSearchBugs;
		}
		switch (portfolio) {
		case PRE_SR:
			return searchPreSrBugs;
		case RECON_HOLDING:
			return searchReconHoldingBugs;
		case RECON_SERVICES:
			return searchReconServicesBugs;
		case PRE_SR_JUGGERNAUT:
			return searchPreSrJNBugs;
		case PRE_SR_TTR:
			return searchPreSrTTRBugs;
		default:
			return null;
		}
	}

}
