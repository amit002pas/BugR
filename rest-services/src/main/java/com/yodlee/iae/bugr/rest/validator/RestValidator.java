package com.yodlee.iae.bugr.rest.validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.resources.responses.BugAttachmentResponse;
import com.yodlee.iae.bugr.resources.responses.FetchBugResp;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SynBugResponse;
import com.yodlee.iae.bugr.resources.responses.SyntheticBaseResponse;
import com.yodlee.iae.bugr.resources.responses.SyntheticSearchBugsResponse;
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

/**
 * @author Shreyas
 *
 */
@Named
@Scope("prototype")
public class RestValidator {

	@Inject
	private ValidationMessageReader validationMessageReader;

	@Inject
	private ValidationUtil validationUtil;

	/**
	 * Returns the SynBugResponse after all the Create validations check
	 * 
	 * @param bugEntity
	 * @return SynBugResponse
	 */
	public SyntheticBaseResponse validateCreate(BugEntity bugEntity) {
		SynBugResponse synBugResponse = new SynBugResponse();
		List<String> responseMessages = new ArrayList<>();
		if (!ValidationUtility.isNullOrEmpty(bugEntity.getSynId())) {
			synBugResponse.setStatus(ResponseConstants.STATUS_SUCCESS);
			return synBugResponse;
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getCf_initiative())) {
			responseMessages.add(ValidationConstants.CREATE_INITIATIVE);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getProduct())) {
			responseMessages.add(ValidationConstants.CREATE_PRODUCT);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getComponent())) {
			responseMessages.add(ValidationConstants.CREATE_COMPONENT);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getSummary())) {
			responseMessages.add(ValidationConstants.CREATE_SUMMARY);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getVersion())) {
			responseMessages.add(ValidationConstants.CREATE_VERSION);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getCf_environment())) {
			responseMessages.add(ValidationConstants.CREATE_ENVIRONMENT);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getCf_department())) {
			responseMessages.add(ValidationConstants.CREATE_DEPARTMENT);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getCf_workflow())) {
			responseMessages.add(ValidationConstants.CREATE_WORKFLOW);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getCf_customer())) {
			responseMessages.add(ValidationConstants.CREATE_CUSTOMER);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getCf_portfolio())) {
			responseMessages.add(ValidationConstants.CREATE_PORTFOLIO);
		}
		if ((bugEntity.getComment() != null && bugEntity.getComment().length() > 65535)) {
			responseMessages.add(ValidationConstants.CREATE_COMLEN);
		}

		List<String> responses = validationUtil.getFieldLengthFailures(bugEntity);
		responses.addAll(validationMessageReader.getPropertyByKey(responseMessages));

		return validationUtil.getResponseFromValidationMessages(synBugResponse, responses);
	}

	/**
	 * Returns the SynBugResponse after all the Update validations check
	 * 
	 * @param synBugId
	 * @param bugEntity
	 * @return SynBugResponse
	 */
	public SyntheticBaseResponse validateUpdate(BugEntity bugEntity) {
		SynBugResponse synBugResponse = new SynBugResponse();
		List<String> responseMessages = new ArrayList<>();

		List<String> responses = validationUtil.getFieldLengthFailures(bugEntity);

		if (ValidationUtility.isNullOrEmpty(bugEntity.getSynId())) {
			responseMessages.add(ValidationConstants.UPDATE_BUGID);
		}
		if (ValidationUtility.isNullOrEmpty(bugEntity.getComment())) {
			responseMessages.add(ValidationConstants.UPDATE_COMREQ);
		}
		if (bugEntity.getComment().length() > 65535) {
			responseMessages.add(ValidationConstants.UPDATE_COMLEN);
		}
		if ((ValidationConstants.NEW.equals(bugEntity.getBug_status())
				|| ValidationConstants.ASSIGNED.equals(bugEntity.getBug_status()))
				&& !ValidationUtility.isNullOrEmpty(bugEntity.getResolution())) {
			responseMessages.add(ValidationConstants.UPDATE_BUGSTAT);
		}
		if (ValidationConstants.RESOLVED.equals(bugEntity.getBug_status())) {
			if (ValidationUtility.isNullOrEmpty(bugEntity.getResolution())) {
				responseMessages.add(ValidationConstants.UPDATE_BUGSTATNORESOLVE);
			}
			if (!(ValidationConstants.FIXED.equals(bugEntity.getResolution())
					|| ValidationConstants.INVALID.equals(bugEntity.getResolution())
					|| ValidationConstants.DUPLICATE.equals(bugEntity.getResolution())
					|| ValidationConstants.MOVED.equals(bugEntity.getResolution())
					|| ValidationConstants.WORKSFORME.equals(bugEntity.getResolution()))) {
				responseMessages.add(ValidationConstants.UPDATE_BUGRESOLVED);
			} else if(responses.isEmpty()) {
				synBugResponse.setStatus(ResponseConstants.STATUS_SUCCESS);
				return synBugResponse;
			}
		}
		responses.addAll(validationMessageReader.getPropertyByKey(responseMessages));
		return validationUtil.getResponseFromValidationMessages(synBugResponse, responses);
	}

	/**
	 * Returns the SearchBugResponse after all the Search validations check
	 * 
	 * @param bugSearchParam
	 * @return SearchBugResponse
	 */
	public SyntheticBaseResponse validateSearch(BugSearchParam bugSearchParam) {
		List<String> responseMessages = new ArrayList<>();
		if (ValidationUtility.isNullOrEmpty(bugSearchParam.getTimeStamp().getStartTime())) {
			responseMessages.add(ValidationConstants.SEARCH_STARTTIME);
		}
		if (ValidationUtility.isNullOrEmpty(bugSearchParam.getTimeStamp().getEndTime())) {
			responseMessages.add(ValidationConstants.SEARCH_ENDTIME);
		}
		if (ValidationUtility.isNullOrEmpty(bugSearchParam.getPageNum())) {
			responseMessages.add(ValidationConstants.SEARCH_PAGENUM);
		}
		return validationUtil.getResponseFromValidationMessages(new SyntheticSearchBugsResponse(),
				validationMessageReader.getPropertyByKey(responseMessages));
	}

	/**
	 * Returns the FetchBugResp after all the Fetch validations check
	 * 
	 * @param id
	 * @return FetchBugResp
	 */
	public SyntheticBaseResponse validateFetch(String id) {
		if (ValidationUtility.isNullOrEmpty(id)) {
			List<String> responseMessages = new ArrayList<>();
			responseMessages.add(ValidationConstants.FETCH_REQFIELDS);
			return validationUtil.getResponseFromValidationMessages(new FetchBugResp(),
					validationMessageReader.getPropertyByKey(responseMessages));
		} else {
			return validationUtil.getResponseFromValidationMessages(new FetchBugResp(), null);
		}
	}

	/**
	 * Returns the BugAttachmentResponse after all the AddAttachment validations
	 * check
	 * 
	 * @param bugId
	 * @param attachments
	 * @return BugAttachmentResponse
	 */
	public SyntheticBaseResponse validateAddAttachment(SyntheticAttachmentRequest req) {
		String bugId = req.getBugId();
		List<AttachmentRequest> attachments = req.getAttachments();
		List<String> responseMessages = new ArrayList<>();
		if (ValidationUtility.isNullOrEmpty(bugId)) {
			responseMessages.add(ValidationConstants.ATTACH_BUGID);
		}
		for (int i = 0; i < (attachments.size()); i++) {
			if ((ValidationUtility.isNullOrEmpty(attachments.get(i).getAttachment()))
					|| (ValidationUtility.isNullOrEmpty(attachments.get(i).getAttachmentName()))) {
				responseMessages.add(ValidationConstants.ATTACH_REQFIELDS);
			} else if (attachments.get(i).getAttachment().length > 512000) {
				responseMessages.add(ValidationConstants.ATTACH_SIZE);
			}
		}
		return validationUtil.getResponseFromValidationMessages(new BugAttachmentResponse(),
				validationMessageReader.getPropertyByKey(responseMessages));
	}

}
