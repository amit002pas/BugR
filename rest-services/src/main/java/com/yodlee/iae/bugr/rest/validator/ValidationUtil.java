package com.yodlee.iae.bugr.rest.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SyntheticBaseResponse;

/**
 * @author Shreyas
 *
 */
@Named
@Scope("prototype")
public class ValidationUtil {

	@Inject
	private ValidationMessageReader reader;

	public SyntheticBaseResponse getResponseFromValidationMessages(SyntheticBaseResponse response,
			List<String> messages) {
		response.setMessages(messages);
		String status = messages == null || messages.isEmpty() ? ResponseConstants.STATUS_SUCCESS
				: ResponseConstants.STATUS_FAILURE;
		response.setStatus(status);
		return response;
	}

	public static final Predicate<String> isFieldLength254 = str -> null != str && str.length() > 254;

	public void add254Failure(List<String> list, String field, String replacer) {
		if (isFieldLength254.test(field)) {
			list.add(reader.getPropertyByKey(Arrays.asList(ValidationConstants.FIELD_LENGTH_254)).get(0)
					.replace(ResponseConstants.FIELD_STR, replacer));
		}
	}

	public List<String> getFieldLengthFailures(BugEntity bugEntity) {
		List<String> list = new ArrayList<>();
		add254Failure(list, bugEntity.getStatus_whiteboard(), BugzillaConstants.STATUS_WHITEBOARD);
		add254Failure(list, bugEntity.getSummary(), BugzillaConstants.SUMMARY);
		add254Failure(list, bugEntity.getCf_agents(), BugzillaConstants.CF_AGENTS);
		add254Failure(list, bugEntity.getCf_suminfo(), BugzillaConstants.CF_SUMINFO);
		add254Failure(list, bugEntity.getCf_memitem(), BugzillaConstants.CF_MEMITEM);
		add254Failure(list, bugEntity.getCf_errorcode(), BugzillaConstants.CF_ERRORCODE);
		add254Failure(list, bugEntity.getCf_impact(), BugzillaConstants.CF_IMPACT);
		add254Failure(list, bugEntity.getCf_service_request_id(), BugzillaConstants.CF_SERVICE_REQUEST_ID);
		add254Failure(list, bugEntity.getCf_site_id(), BugzillaConstants.CF_SITE_ID);
		add254Failure(list, bugEntity.getCf_mem_site_acc_id(), BugzillaConstants.CF_MEM_SITE_ACC_ID);
		add254Failure(list, bugEntity.getCf_agent_version(), BugzillaConstants.CF_AGENT_VERSION);
		add254Failure(list, bugEntity.getCf_comments_release_notes(), BugzillaConstants.CF_COMMENTS_RELEASE_NOTES);
		add254Failure(list, bugEntity.getCf_readme(), BugzillaConstants.CF_README);
		add254Failure(list, bugEntity.getCf_p4_label(), BugzillaConstants.CF_P4_LABEL);
		return list;
	}

}
