package com.yodlee.iae.bugr.services.synthetic.validation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

/**
 * @author aojha
 *
 */
@Named
@Scope("prototype")
public class ErrorSegmentBugValidator implements ISyntheticBugValidator {

	@Inject
	private ServiceMessageReader validationMessageReader;

	/**
	 * Used to validate impact and agent fields for error-segment bugs
	 * 
	 * @param sBug
	 * @return response
	 */

	@Override
	public SynBugResponse validateBug(SyntheticBug sBug) {
		List<String> responseList = new ArrayList<>();

		String impact = sBug.getBugFields().getImpact();
		String agent = sBug.getBugFields().getAgentName();

		if (ValidationUtility.isNullOrEmpty(impact)) {
			responseList.add(
					validationMessageReader.getPropertyByKey(ServiceValidationConstants.ERROR_SEGMENT_IMPACT_ERROR));
		}
		if (ValidationUtility.isNullOrEmpty(agent)) {
			responseList.add(
					validationMessageReader.getPropertyByKey(ServiceValidationConstants.ERROR_SEGMENT_AGENT_ERROR));
		}
		return SynUtil.sendResponse(responseList);
	}

}
