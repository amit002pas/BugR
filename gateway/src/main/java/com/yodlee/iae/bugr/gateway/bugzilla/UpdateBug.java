/*
 * Copyright 2011 Thomas Golden
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yodlee.iae.bugr.gateway.bugzilla;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaMethod;

/**
 * The {@code UpdateBug} class allows clients to update an existing {@link Bug} on the installation with
 * new values. Currently, this method only allows one bug at a time to be updated.
 * 
 * Note that Bugzilla 3.6 does not allow updating bugs via the webservice.
 * @author Tom & Sanyam Jain
 *
 */
public class UpdateBug implements BugzillaMethod {
	
	/**
	 * The method name for this webservice operation.
	 */
	private static final String METHOD_NAME = "Bug.update";

	/**
	 * A {@link Bug} to update on the installation.
	 */
	private final Bug bug;
	
	/**
	 * Creates a new {@link UpdateBug} object to submit to the Bugzilla webservice. The {@link Bug} on the
	 * installation identified by the id or alias of the bug provided will have its fields updated 
	 * to match those of the values in the provided bug.
	 * @param bug
	 */
	public UpdateBug(Bug bug) {
		this.bug = bug;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResultMap(Map<Object, Object> hash) {
		Object[] modified = (Object[])hash.get("bugs");
		
		//For now, we only modify one bug at a time, thus this array should be a single element
		assert(modified.length == 1);
		//There aren't a ton of useful elements returned, so for now just discard the map.	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Object, Object> getParameterMap() {
		Map<Object, Object> params = new HashMap<Object, Object>();
		
		params.put("ids", bug.getID());
		
		
		copyNotNull(params, "alias", bug.getAlias());
		copyNotNull(params, "summary", bug.getSummary());
		copyNotNull(params, "priority", bug.getPriority());
		copyNotNull(params, "product", bug.getProduct());
		copyNotNull(params, "component", bug.getComponent());
		copyNotNull(params, "version", bug.getVersion());
		copyNotNull(params, "status", bug.getStatus());
		copyNotNull(params, "resolution", bug.getResolution());
		copyNotNull(params, "op_sys", bug.getOperatingSystem());
		copyNotNull(params, "platform", bug.getPlatform());
		copyNotNull(params, "severity", bug.getSeverity());
		//copyNotNull(params, "cf_impact", bug.getParameterMap().get("cf_impact"));
		//copyNotNull(params, "cf_workflow_status", bug.getParameterMap().get("cf_workflow_status"));
		
		copyNotNull(params, BugzillaConstants.BUG_ID, bug.getParameterMap().get(BugzillaConstants.BUG_ID));
		copyNotNull(params, BugzillaConstants.ASSIGNED_TO, bug.getParameterMap().get(BugzillaConstants.ASSIGNED_TO));
		copyNotNull(params, BugzillaConstants.BUG_FILE_LOC, bug.getParameterMap().get(BugzillaConstants.BUG_FILE_LOC));
		copyNotNull(params, BugzillaConstants.BUG_SEVERITY, bug.getParameterMap().get(BugzillaConstants.BUG_SEVERITY));
		copyNotNull(params, BugzillaConstants.BUG_STATUS, bug.getParameterMap().get(BugzillaConstants.BUG_STATUS));
		copyNotNull(params, BugzillaConstants.CREATION_TS, bug.getParameterMap().get(BugzillaConstants.CREATION_TS));
		copyNotNull(params, BugzillaConstants.DELTA_TS, bug.getParameterMap().get(BugzillaConstants.DELTA_TS));
		copyNotNull(params, BugzillaConstants.SHORT_DESC, bug.getParameterMap().get(BugzillaConstants.SHORT_DESC));
		copyNotNull(params, BugzillaConstants.OP_SYS, bug.getParameterMap().get(BugzillaConstants.OP_SYS));
		copyNotNull(params, BugzillaConstants.PRIORITY, bug.getParameterMap().get(BugzillaConstants.PRIORITY));
		copyNotNull(params, BugzillaConstants.PRODUCT_ID, bug.getParameterMap().get(BugzillaConstants.PRODUCT_ID));
		copyNotNull(params, BugzillaConstants.REP_PLATFORM, bug.getParameterMap().get(BugzillaConstants.REP_PLATFORM));
		copyNotNull(params, BugzillaConstants.REPORTER, bug.getParameterMap().get(BugzillaConstants.REPORTER));
		copyNotNull(params, BugzillaConstants.VERSION, bug.getParameterMap().get(BugzillaConstants.VERSION));
		copyNotNull(params, BugzillaConstants.COMPONENT_ID, bug.getParameterMap().get(BugzillaConstants.COMPONENT_ID));
		copyNotNull(params, BugzillaConstants.RESOLUTION, bug.getParameterMap().get(BugzillaConstants.RESOLUTION));
		copyNotNull(params, BugzillaConstants.TARGET_MILESTONE, bug.getParameterMap().get(BugzillaConstants.TARGET_MILESTONE));
		copyNotNull(params, BugzillaConstants.QA_CONTACT, bug.getParameterMap().get(BugzillaConstants.QA_CONTACT));
		copyNotNull(params, BugzillaConstants.STATUS_WHITEBOARD, bug.getParameterMap().get(BugzillaConstants.STATUS_WHITEBOARD));
		copyNotNull(params, BugzillaConstants.VOTES, bug.getParameterMap().get(BugzillaConstants.VOTES));
		copyNotNull(params, BugzillaConstants.LASTDIFFED, bug.getParameterMap().get(BugzillaConstants.LASTDIFFED));
		copyNotNull(params, BugzillaConstants.EVERCONFIRMED, bug.getParameterMap().get(BugzillaConstants.EVERCONFIRMED));
		copyNotNull(params, BugzillaConstants.REPORTER_ACCESSIBLE, bug.getParameterMap().get(BugzillaConstants.REPORTER_ACCESSIBLE));
		copyNotNull(params, BugzillaConstants.CCLIST_ACCESSIBLE, bug.getParameterMap().get(BugzillaConstants.CCLIST_ACCESSIBLE));
		copyNotNull(params, BugzillaConstants.ESTIMATED_TIME, bug.getParameterMap().get(BugzillaConstants.ESTIMATED_TIME));
		copyNotNull(params, BugzillaConstants.REMAINING_TIME, bug.getParameterMap().get(BugzillaConstants.REMAINING_TIME));
		copyNotNull(params, BugzillaConstants.DEADLINE, bug.getParameterMap().get(BugzillaConstants.DEADLINE));
		copyNotNull(params, BugzillaConstants.ALIAS, bug.getParameterMap().get(BugzillaConstants.ALIAS));
		copyNotNull(params, BugzillaConstants.CF_AGENTS, bug.getParameterMap().get(BugzillaConstants.CF_AGENTS));
		copyNotNull(params, BugzillaConstants.CF_SUMINFO, bug.getParameterMap().get(BugzillaConstants.CF_SUMINFO));
		copyNotNull(params, BugzillaConstants.CF_MEMITEM, bug.getParameterMap().get(BugzillaConstants.CF_MEMITEM));
		copyNotNull(params, BugzillaConstants.CF_ERRORCODE, bug.getParameterMap().get(BugzillaConstants.CF_ERRORCODE));
		copyNotNull(params, BugzillaConstants.CF_BUILD_FOUND, bug.getParameterMap().get(BugzillaConstants.CF_BUILD_FOUND));
		copyNotNull(params, BugzillaConstants.CF_ENVIRONMENT, bug.getParameterMap().get(BugzillaConstants.CF_ENVIRONMENT));
		copyNotNull(params, BugzillaConstants.CF_AGENT_TYPE, bug.getParameterMap().get(BugzillaConstants.CF_AGENT_TYPE));
		copyNotNull(params, BugzillaConstants.CF_CUSTOMER_NOTE, bug.getParameterMap().get(BugzillaConstants.CF_CUSTOMER_NOTE));
		copyNotNull(params, BugzillaConstants.CF_CUSTOMER, bug.getParameterMap().get(BugzillaConstants.CF_CUSTOMER));
		copyNotNull(params, BugzillaConstants.CF_REMEDY_ID, bug.getParameterMap().get(BugzillaConstants.CF_REMEDY_ID));
		copyNotNull(params, BugzillaConstants.CF_CHANGELIST_NO, bug.getParameterMap().get(BugzillaConstants.CF_CHANGELIST_NO));
		copyNotNull(params, BugzillaConstants.CF_ROOT_CAUSE, bug.getParameterMap().get(BugzillaConstants.CF_ROOT_CAUSE));
		copyNotNull(params, BugzillaConstants.CF_BUILD_FIXED, bug.getParameterMap().get(BugzillaConstants.CF_BUILD_FIXED));
		copyNotNull(params, BugzillaConstants.CF_RELEASE_NOTES, bug.getParameterMap().get(BugzillaConstants.CF_RELEASE_NOTES));
		copyNotNull(params, BugzillaConstants.CF_COMMENTS_RELEASE_NOTES, bug.getParameterMap().get(BugzillaConstants.CF_COMMENTS_RELEASE_NOTES));
		copyNotNull(params, BugzillaConstants.CF_AGENT_STATUS, bug.getParameterMap().get(BugzillaConstants.CF_AGENT_STATUS));
		copyNotNull(params, BugzillaConstants.CF_PUBLIC, bug.getParameterMap().get(BugzillaConstants.CF_PUBLIC));
		copyNotNull(params, BugzillaConstants.CF_BUGTYPE, bug.getParameterMap().get(BugzillaConstants.CF_BUGTYPE));
		copyNotNull(params, BugzillaConstants.CF_RCA, bug.getParameterMap().get(BugzillaConstants.CF_RCA));
		copyNotNull(params, BugzillaConstants.CF_TEST_CASE_ID, bug.getParameterMap().get(BugzillaConstants.CF_TEST_CASE_ID));
		copyNotNull(params, BugzillaConstants.CF_FIXED_RELEASE, bug.getParameterMap().get(BugzillaConstants.CF_FIXED_RELEASE));
		copyNotNull(params, BugzillaConstants.CF_BACKEND_FRONTEND, bug.getParameterMap().get(BugzillaConstants.CF_BACKEND_FRONTEND));
		copyNotNull(params, BugzillaConstants.CF_DEPARTMENT, bug.getParameterMap().get(BugzillaConstants.CF_DEPARTMENT));
		copyNotNull(params, BugzillaConstants.CF_OLDBUGZILLA_ID, bug.getParameterMap().get(BugzillaConstants.CF_OLDBUGZILLA_ID));
		copyNotNull(params, BugzillaConstants.CF_TRIAGE_INDICATOR, bug.getParameterMap().get(BugzillaConstants.CF_TRIAGE_INDICATOR));
		copyNotNull(params, BugzillaConstants.CF_RECOMENTED_ACTION, bug.getParameterMap().get(BugzillaConstants.CF_RECOMENTED_ACTION));
		copyNotNull(params, BugzillaConstants.CF_BUG_COMPLEXITY, bug.getParameterMap().get(BugzillaConstants.CF_BUG_COMPLEXITY));
		copyNotNull(params, BugzillaConstants.CF_TRIAGE_INDICATOR1, bug.getParameterMap().get(BugzillaConstants.CF_TRIAGE_INDICATOR1));
		copyNotNull(params, BugzillaConstants.CF_BUG_COMPLEXITY1, bug.getParameterMap().get(BugzillaConstants.CF_BUG_COMPLEXITY1));
		copyNotNull(params, BugzillaConstants.CF_ERR_PREFIX_NO, bug.getParameterMap().get(BugzillaConstants.CF_ERR_PREFIX_NO));
		copyNotNull(params, BugzillaConstants.CF_ERR_POSTFIX_NO, bug.getParameterMap().get(BugzillaConstants.CF_ERR_POSTFIX_NO));
		copyNotNull(params, BugzillaConstants.CF_REVIEWER, bug.getParameterMap().get(BugzillaConstants.CF_REVIEWER));
		copyNotNull(params, BugzillaConstants.CF_MENTOR, bug.getParameterMap().get(BugzillaConstants.CF_MENTOR));
		copyNotNull(params, BugzillaConstants.CF_ANALYST, bug.getParameterMap().get(BugzillaConstants.CF_ANALYST));
		copyNotNull(params, BugzillaConstants.CF_IMPACT, bug.getParameterMap().get(BugzillaConstants.CF_IMPACT));
		copyNotNull(params, BugzillaConstants.CF_CODE_REVIEW_COMMENTS, bug.getParameterMap().get(BugzillaConstants.CF_CODE_REVIEW_COMMENTS));
		copyNotNull(params, BugzillaConstants.CF_SERVICE_REQUEST_ID, bug.getParameterMap().get(BugzillaConstants.CF_SERVICE_REQUEST_ID));
		copyNotNull(params, BugzillaConstants.CF_AGENT_VERSION, bug.getParameterMap().get(BugzillaConstants.CF_AGENT_VERSION));
		copyNotNull(params, BugzillaConstants.CF_CATEGORY, bug.getParameterMap().get(BugzillaConstants.CF_CATEGORY));
		copyNotNull(params, BugzillaConstants.CF_PLAN, bug.getParameterMap().get(BugzillaConstants.CF_PLAN));
		copyNotNull(params, BugzillaConstants.CF_WORKFLOW_STATUS, bug.getParameterMap().get(BugzillaConstants.CF_WORKFLOW_STATUS));
		copyNotNull(params, BugzillaConstants.CF_IDLE_TIME, bug.getParameterMap().get(BugzillaConstants.CF_IDLE_TIME));
		copyNotNull(params, BugzillaConstants.CF_SHAREPOINT_LINK, bug.getParameterMap().get(BugzillaConstants.CF_SHAREPOINT_LINK));
		copyNotNull(params, BugzillaConstants.CF_RESOLUTION1, bug.getParameterMap().get(BugzillaConstants.CF_RESOLUTION1));
		copyNotNull(params, BugzillaConstants.CF_RESOLUTION2, bug.getParameterMap().get(BugzillaConstants.CF_RESOLUTION2));
		copyNotNull(params, BugzillaConstants.CF_RESOLUTION3, bug.getParameterMap().get(BugzillaConstants.CF_RESOLUTION3));
		copyNotNull(params, BugzillaConstants.CF_PATCH_BUILD_FIXED, bug.getParameterMap().get(BugzillaConstants.CF_PATCH_BUILD_FIXED));
		copyNotNull(params, BugzillaConstants.CF_PATCHES_BUILD_FIXED, bug.getParameterMap().get(BugzillaConstants.CF_PATCHES_BUILD_FIXED));
		copyNotNull(params, BugzillaConstants.CF_BUILD_COMPONENTS, bug.getParameterMap().get(BugzillaConstants.CF_BUILD_COMPONENTS));
		copyNotNull(params, BugzillaConstants.CF_CHOOSE_FROM, bug.getParameterMap().get(BugzillaConstants.CF_CHOOSE_FROM));
		copyNotNull(params, BugzillaConstants.CF_COBRAND_BUG_ID, bug.getParameterMap().get(BugzillaConstants.CF_COBRAND_BUG_ID));
		copyNotNull(params, BugzillaConstants.CF_CONFIGURATION_CHANGES, bug.getParameterMap().get(BugzillaConstants.CF_CONFIGURATION_CHANGES));
		copyNotNull(params, BugzillaConstants.CF_DOCUMENTATION_CHANGES, bug.getParameterMap().get(BugzillaConstants.CF_DOCUMENTATION_CHANGES));
		copyNotNull(params, BugzillaConstants.CF_P4_LABEL, bug.getParameterMap().get(BugzillaConstants.CF_P4_LABEL));
		copyNotNull(params, BugzillaConstants.CF_README, bug.getParameterMap().get(BugzillaConstants.CF_README));
		copyNotNull(params, BugzillaConstants.CF_5X_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_5X_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_COBRAND_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_COBRAND_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_901_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_901_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_90_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_90_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_811_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_811_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_89_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_89_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_91_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_91_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_92_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_92_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_93_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_93_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_94_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_94_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_95_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_95_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_96_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_96_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_97_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_97_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_QE_RCA, bug.getParameterMap().get(BugzillaConstants.CF_QE_RCA));
		copyNotNull(params, BugzillaConstants.CF_BUILD_FOUND_NEW, bug.getParameterMap().get(BugzillaConstants.CF_BUILD_FOUND_NEW));
		copyNotNull(params, BugzillaConstants.CF_BUILD_FIXED_NEW, bug.getParameterMap().get(BugzillaConstants.CF_BUILD_FIXED_NEW));
		copyNotNull(params, BugzillaConstants.CF_91_CHANGELIST_1, bug.getParameterMap().get(BugzillaConstants.CF_91_CHANGELIST_1));
		copyNotNull(params, BugzillaConstants.CF_CF_902_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_CF_902_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_902_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_902_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_903_CHANGELIST, bug.getParameterMap().get(BugzillaConstants.CF_903_CHANGELIST));
		copyNotNull(params, BugzillaConstants.CF_FIDELITY_ASP, bug.getParameterMap().get(BugzillaConstants.CF_FIDELITY_ASP));
		copyNotNull(params, BugzillaConstants.CF_STORY_ID, bug.getParameterMap().get(BugzillaConstants.CF_STORY_ID));
		copyNotNull(params, BugzillaConstants.CF_RCA_QE, bug.getParameterMap().get(BugzillaConstants.CF_RCA_QE));
		copyNotNull(params, BugzillaConstants.CF_RCA_DEV, bug.getParameterMap().get(BugzillaConstants.CF_RCA_DEV));
		copyNotNull(params, BugzillaConstants.CF_RCA_REOPENED, bug.getParameterMap().get(BugzillaConstants.CF_RCA_REOPENED));
		copyNotNull(params, BugzillaConstants.CF_RCA_WID, bug.getParameterMap().get(BugzillaConstants.CF_RCA_WID));
		copyNotNull(params, BugzillaConstants.CF_RCA_REOPENED_DEV, bug.getParameterMap().get(BugzillaConstants.CF_RCA_REOPENED_DEV));
		copyNotNull(params, BugzillaConstants.CF_RCA_REOPENED_QE, bug.getParameterMap().get(BugzillaConstants.CF_RCA_REOPENED_QE));
		copyNotNull(params, BugzillaConstants.CF_TEA_EVALUATION, bug.getParameterMap().get(BugzillaConstants.CF_TEA_EVALUATION));
		copyNotNull(params, BugzillaConstants.CF_TEA_CAUSE, bug.getParameterMap().get(BugzillaConstants.CF_TEA_CAUSE));
		copyNotNull(params, BugzillaConstants.CF_TEA_DISPOSITION, bug.getParameterMap().get(BugzillaConstants.CF_TEA_DISPOSITION));
		copyNotNull(params, BugzillaConstants.CF_TEA__DISPOSITION, bug.getParameterMap().get(BugzillaConstants.CF_TEA__DISPOSITION));
		copyNotNull(params, BugzillaConstants.CF_TEA_MANAGER, bug.getParameterMap().get(BugzillaConstants.CF_TEA_MANAGER));
		copyNotNull(params, BugzillaConstants.CF_QA_BROWSER_DETAILS, bug.getParameterMap().get(BugzillaConstants.CF_QA_BROWSER_DETAILS));
		copyNotNull(params, BugzillaConstants.CF_BRANCH_FOUND, bug.getParameterMap().get(BugzillaConstants.CF_BRANCH_FOUND));
		copyNotNull(params, BugzillaConstants.CF_PORTFOLIO, bug.getParameterMap().get(BugzillaConstants.CF_PORTFOLIO));
		copyNotNull(params, BugzillaConstants.CF_RELEASE_FOUND, bug.getParameterMap().get(BugzillaConstants.CF_RELEASE_FOUND));
		copyNotNull(params, BugzillaConstants.CF_BUG_FIXED_ON, bug.getParameterMap().get(BugzillaConstants.CF_BUG_FIXED_ON));
		copyNotNull(params, BugzillaConstants.CF_WORKFLOW, bug.getParameterMap().get(BugzillaConstants.CF_WORKFLOW));
		copyNotNull(params, BugzillaConstants.CF_BUSINESS, bug.getParameterMap().get(BugzillaConstants.CF_BUSINESS));
		copyNotNull(params, BugzillaConstants.CF_SUB_BRAND, bug.getParameterMap().get(BugzillaConstants.CF_SUB_BRAND));
		copyNotNull(params, BugzillaConstants.CF_USERNAME, bug.getParameterMap().get(BugzillaConstants.CF_USERNAME));
		copyNotNull(params, BugzillaConstants.CF_SLA_TYPE, bug.getParameterMap().get(BugzillaConstants.CF_SLA_TYPE));
		copyNotNull(params, BugzillaConstants.CF_CODE_REVIEWER, bug.getParameterMap().get(BugzillaConstants.CF_CODE_REVIEWER));
		copyNotNull(params, BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN, bug.getParameterMap().get(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN));
		copyNotNull(params, BugzillaConstants.CF_IS_REGRESSION, bug.getParameterMap().get(BugzillaConstants.CF_IS_REGRESSION));
		copyNotNull(params, BugzillaConstants.CF_FEATURE, bug.getParameterMap().get(BugzillaConstants.CF_FEATURE));
		copyNotNull(params, BugzillaConstants.CF_SITE_ID, bug.getParameterMap().get(BugzillaConstants.CF_SITE_ID));
		copyNotNull(params, BugzillaConstants.CF_MEM_SITE_ACC_ID, bug.getParameterMap().get(BugzillaConstants.CF_MEM_SITE_ACC_ID));
		copyNotNull(params, BugzillaConstants.CF_IS_AUTOMATION, bug.getParameterMap().get(BugzillaConstants.CF_IS_AUTOMATION));
		copyNotNull(params, BugzillaConstants.CF_ETA, bug.getParameterMap().get(BugzillaConstants.CF_ETA));
		copyNotNull(params, BugzillaConstants.CF_RCA_CATEGORIES, bug.getParameterMap().get(BugzillaConstants.CF_RCA_CATEGORIES));
		copyNotNull(params, BugzillaConstants.CF_RCA_SUB_CATEGORIES, bug.getParameterMap().get(BugzillaConstants.CF_RCA_SUB_CATEGORIES));
		copyNotNull(params, BugzillaConstants.CF_ERRCATEGORY, bug.getParameterMap().get(BugzillaConstants.CF_ERRCATEGORY));
		copyNotNull(params, BugzillaConstants.CF_JSON_OUT, bug.getParameterMap().get(BugzillaConstants.CF_JSON_OUT));
		copyNotNull(params, BugzillaConstants.CF_ESCALATION_STATUS, bug.getParameterMap().get(BugzillaConstants.CF_ESCALATION_STATUS));
		copyNotNull(params, BugzillaConstants.COMMENT, bug.getParameterMap().get(BugzillaConstants.COMMENT));
		copyNotNull(params, BugzillaConstants.CF_IAE_RESOLUTION, bug.getParameterMap().get(BugzillaConstants.CF_IAE_RESOLUTION));
		copyNotNull(params, BugzillaConstants.DEPENDS_ON, bug.getParameterMap().get(BugzillaConstants.DEPENDS_ON));
		
		return Collections.unmodifiableMap(params);
	}
	
	private void copyNotNull(Map<Object, Object> map, String key, Object value) {
		if(value != null) {
			map.put(key, value);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMethodName() {
		return METHOD_NAME;
	}
}
