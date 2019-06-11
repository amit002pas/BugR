package com.yodlee.iae.bugr.services.synthetic.processor;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

/**
 * @author KChandrarajan
 *
 */
public interface SyntheticBugProcessor {

	/**
	 * Process bug Contents based on Portfolio category
	 * 
	 * @param sBug
	 */
	public void processBugContents(SyntheticBug sBug);

}
