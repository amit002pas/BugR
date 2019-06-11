package com.yodlee.iae.bugr.services.synthetic.dedupe;

import java.util.Optional;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

/**
 * @author KChandrarajan
 *
 */
public interface SyntheticBugDeduper {

	/**
	 * 
	 * Find the Duplicate Bug based on Portfolio
	 * 
	 * @param sBug
	 * @return
	 */
	public Optional<SyntheticBug> findDuplicateBug(SyntheticBug sBug);

	/**
	 * 
	 * Update the Duplicate bug and save in DB
	 * 
	 * @param sbug
	 * @param duplicateBug
	 */
	public void updateDuplicateBug(SyntheticBug sbug, SyntheticBug duplicateBug);

}
