package com.yodlee.iae.bugr.services.synthetic.validation;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;

/**
 * @author KChandrarajan
 *
 */
public interface ISyntheticBugValidator {

	/**
	 * Validates bug based on the Portfolio category
	 * 
	 * @param sBug
	 * @return
	 */
	public SynBugResponse validateBug(SyntheticBug sBug);

}
