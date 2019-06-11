package com.yodlee.iae.bugr.services.synthetic.validation;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class ReconHoldingBugValidator implements ISyntheticBugValidator {

	/**
	 * Used to validate fields for recon-holding bugs
	 * 
	 * @param sBug
	 * @return response
	 */
	@Override
	public SynBugResponse validateBug(SyntheticBug sBug) {
		SynBugResponse response = new SynBugResponse();
		response.setStatus(ResponseConstants.STATUS_SUCCESS);
		return response;
	}
}
