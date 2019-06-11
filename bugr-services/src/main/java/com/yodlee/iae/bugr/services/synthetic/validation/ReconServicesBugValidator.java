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
public class ReconServicesBugValidator implements ISyntheticBugValidator {

	@Inject
	private ServiceMessageReader reader;

	/**
	 * Used to validate impact,suminfo,segID and agent fields for recon-services
	 * bugs
	 * 
	 * @param sBug
	 * @return response
	 */
	@Override
	public SynBugResponse validateBug(SyntheticBug sBug) {
		List<String> responseList = new ArrayList<>();

		String whiteBoard = sBug.getBugFields().getWhiteboard();

		String sumInfo = sBug.getBugFields().getSuminfo();
		String segID = null;
		String impact = sBug.getBugFields().getImpact();
		String agent = sBug.getBugFields().getAgentName();

		if (ValidationUtility.isNullOrEmpty(impact)) {
			responseList.add(reader.getPropertyByKey(ServiceValidationConstants.ERROR_SEGMENT_IMPACT_ERROR));
		}
		if (ValidationUtility.isNullOrEmpty(agent)) {
			responseList.add(reader.getPropertyByKey(ServiceValidationConstants.ERROR_SEGMENT_AGENT_ERROR));
		}
		if (!whiteBoard.contains(SynUtil.SEGMENT_ID)) {
			responseList.add(reader.getPropertyByKey(ServiceValidationConstants.RECON_SERVICES_SEGMENT_ID_ERROR));
		}
		if (whiteBoard.contains(SynUtil.SEGMENT_ID)) {
			segID = whiteBoard.substring(whiteBoard.indexOf(SynUtil.SEGMENT_ID) + SynUtil.SEGMENT_ID.length(),
					whiteBoard.indexOf(SynUtil.ACTION) - 1).trim();
			if (!segID
					.equalsIgnoreCase(reader.getPropertyByKey(ServiceValidationConstants.RECON_SERVICES_SEGMENT_ID_NA))
					&& (segID.length() != 36)) {
				responseList.add(
						reader.getPropertyByKey(ServiceValidationConstants.RECON_SERVICES_SEGMENT_ID_LENGTH_ERROR));
			}
		}
		if (ValidationUtility.isNullOrEmpty(sumInfo)) {
			responseList.add(reader.getPropertyByKey(ServiceValidationConstants.RECON_SERVICES_SUMINFO_ERROR));
		}
		return SynUtil.sendResponse(responseList);
	}
}
