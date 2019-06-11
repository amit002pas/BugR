/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Named;

import com.google.gson.Gson;
import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.MiscellaneousFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

/**
 * @author KChandrarajan
 *
 */
@Named
public class Mapper {

	/**
	 * 
	 */
	private Mapper() {
		//
	}

	private static Gson gson = new Gson();

	/**
	 * Synthetic to Bugzilla conversion
	 * 
	 * @param synBugFields
	 * @return
	 */
	public static BugEntity getBugEntityFromSynthetic(BugFields synBugFields) {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setAssigned_to(synBugFields.getAssigneee());
		bugEntity.setCf_p4_label(synBugFields.getLabel());
		bugEntity.setCf_customer(synBugFields.getCustomer());
		bugEntity.setCf_memitem(synBugFields.getMemitem());
		bugEntity.setCf_department(synBugFields.getDepartment());
		bugEntity.setRep_platform(synBugFields.getPlatform());
		bugEntity.setCf_site_id(synBugFields.getSiteId());
		bugEntity.setCf_backend_frontend(synBugFields.getSource());
		bugEntity.setCf_agents(synBugFields.getAgentName());
		bugEntity.setCf_portfolio(synBugFields.getPortfolio());
		bugEntity.setSummary(synBugFields.getSummary());
		bugEntity.setProduct(synBugFields.getProduct());
		bugEntity.setStatus_whiteboard(synBugFields.getWhiteboard());
		bugEntity.setCf_impact(synBugFields.getImpact());
		bugEntity.setCf_suminfo(synBugFields.getSuminfo());
		bugEntity.setCf_code_review_comments_gen(synBugFields.getCodeReviewComments());
		bugEntity.setCf_errorcode(synBugFields.getErrorcode());
		bugEntity.setCf_initiative(synBugFields.getInitiative());
		bugEntity.setPriority(synBugFields.getPriority());
		bugEntity.setVersion(synBugFields.getVersion());
		bugEntity.setCf_workflow(synBugFields.getWorkflow());
		bugEntity.setOp_sys(synBugFields.getOpSys());
		bugEntity.setCf_environment(synBugFields.getEnvironment());
		bugEntity.setComponent(synBugFields.getComponent());
		bugEntity.setCf_bugtype(synBugFields.getBugtype());
		bugEntity.setCf_workflow_status(synBugFields.getWorkflowStatus());
		bugEntity.setBug_status(synBugFields.getBugStatus());
		bugEntity.setResolution(synBugFields.getResolution());
		bugEntity.setComment(synBugFields.getComment());
		bugEntity.setCf_readme(synBugFields.getReadme());
		bugEntity.setKeywords(synBugFields.getKeywords());
		bugEntity.setCf_eta(synBugFields.getEta());
		bugEntity.setCf_public(synBugFields.getPublicc());
		bugEntity.setCf_agent_version(synBugFields.getAgentVersion());
		bugEntity.setBug_severity(synBugFields.getSeverity());
		bugEntity.setCf_comments_release_notes(synBugFields.getCommentsReleaseNotes());
		bugEntity.setDeadline(synBugFields.getDeadline());
		bugEntity.setCf_service_request_id(synBugFields.getSrId());
		bugEntity.setCf_mem_site_acc_id(synBugFields.getMemSiteAccId());
		bugEntity.setCf_idle_time(synBugFields.getIdleTime());
		bugEntity.setQa_contact(synBugFields.getQaContact());
		bugEntity.setCf_agent_status(synBugFields.getAgentStatus());
		bugEntity.setCf_agent_type(synBugFields.getAgentType());
		bugEntity.setCf_rca_categories(synBugFields.getRcaCategories());
		bugEntity.setCf_iae_resolution(synBugFields.getIaeResolution());
		bugEntity.setDepends_on(synBugFields.getDepends_on());
		return bugEntity;
	}

	/**
	 * Bugzilla to Synthetic conversion with null and empty checks
	 * 
	 * @param bugEntity
	 * @param sBug
	 */
	public static void updateSynBugFields(BugEntity bugEntity, SyntheticBug sBug) {
		BugFields synBugFields = sBug.getBugFields();

		SynUtil.validateStr(bugEntity.getAssigned_to()).ifPresent(synBugFields::setAssigneee);
		SynUtil.validateStr(bugEntity.getCf_p4_label()).ifPresent(synBugFields::setLabel);
		SynUtil.validateStr(bugEntity.getCf_customer()).ifPresent(synBugFields::setCustomer);
		SynUtil.validateStr(bugEntity.getCf_memitem()).ifPresent(synBugFields::setMemitem);
		SynUtil.validateStr(bugEntity.getCf_department()).ifPresent(synBugFields::setDepartment);
		SynUtil.validateStr(bugEntity.getRep_platform()).ifPresent(synBugFields::setPlatform);
		SynUtil.validateStr(bugEntity.getCf_site_id()).ifPresent(synBugFields::setSiteId);
		SynUtil.validateStr(bugEntity.getCf_backend_frontend()).ifPresent(synBugFields::setSource);
		SynUtil.validateStr(bugEntity.getCf_agents()).ifPresent(synBugFields::setAgentName);
		SynUtil.validateStr(bugEntity.getCf_portfolio()).ifPresent(synBugFields::setPortfolio);
		SynUtil.validateStr(bugEntity.getSummary()).ifPresent(synBugFields::setSummary);
		SynUtil.validateStr(bugEntity.getProduct()).ifPresent(synBugFields::setProduct);
		SynUtil.validateStr(bugEntity.getStatus_whiteboard()).ifPresent(synBugFields::setWhiteboard);
		SynUtil.validateStr(bugEntity.getCf_impact()).ifPresent(synBugFields::setImpact);
		SynUtil.validateStr(bugEntity.getCf_suminfo()).ifPresent(synBugFields::setSuminfo);
		SynUtil.validateStr(bugEntity.getCf_code_review_comments_gen()).ifPresent(synBugFields::setCodeReviewComments);
		SynUtil.validateStr(bugEntity.getCf_errorcode()).ifPresent(synBugFields::setErrorcode);
		SynUtil.validateStr(bugEntity.getCf_initiative()).ifPresent(synBugFields::setInitiative);
		SynUtil.validateStr(bugEntity.getPriority()).ifPresent(synBugFields::setPriority);
		SynUtil.validateStr(bugEntity.getVersion()).ifPresent(synBugFields::setVersion);
		SynUtil.validateStr(bugEntity.getCf_workflow()).ifPresent(synBugFields::setWorkflow);
		SynUtil.validateStr(bugEntity.getOp_sys()).ifPresent(synBugFields::setOpSys);
		SynUtil.validateStr(bugEntity.getCf_environment()).ifPresent(synBugFields::setEnvironment);
		SynUtil.validateStr(bugEntity.getComponent()).ifPresent(synBugFields::setComponent);
		SynUtil.validateStr(bugEntity.getCf_bugtype()).ifPresent(synBugFields::setBugtype);
		SynUtil.validateStr(bugEntity.getCf_workflow_status()).ifPresent(workflowStatus -> {
			if (workflowStatus.equals("Closed")||workflowStatus.equals("Dependent on other teams")||workflowStatus.equals("Duplicate - Already Fixed")||workflowStatus.equals("Reassigned")) {
				sBug.getSyntheticFields().setSyntheticBugStatus(SyntheticBugStatus.CLOSED);
			}
			synBugFields.setWorkflowStatus(workflowStatus);
		});
		
		SynUtil.validateStr(bugEntity.getCf_readme()).ifPresent(synBugFields::setReadme);
		SynUtil.validateStr(bugEntity.getKeywords()).ifPresent(synBugFields::setKeywords);
		SynUtil.validateStr(bugEntity.getBug_status()).ifPresent(status -> {
			if (status.equals("RESOLVED")) {
				sBug.getSyntheticFields().setSyntheticBugStatus(SyntheticBugStatus.CLOSED);
			}
			synBugFields.setBugStatus(status);
		});
		SynUtil.validateStr(bugEntity.getResolution()).ifPresent(synBugFields::setResolution);
		SynUtil.validateStr(bugEntity.getComment()).ifPresent(synBugFields::setComment);
		SynUtil.validateStr(bugEntity.getCf_eta()).ifPresent(synBugFields::setEta);
		SynUtil.validateStr(bugEntity.getCf_public()).ifPresent(synBugFields::setPublicc);
		SynUtil.validateStr(bugEntity.getBug_severity()).ifPresent(synBugFields::setSeverity);
		SynUtil.validateStr(bugEntity.getCf_agent_version()).ifPresent(synBugFields::setAgentVersion);
		SynUtil.validateStr(bugEntity.getCf_comments_release_notes()).ifPresent(synBugFields::setCommentsReleaseNotes);
		SynUtil.validateStr(bugEntity.getDeadline()).ifPresent(synBugFields::setDeadline);
		SynUtil.validateStr(bugEntity.getCf_service_request_id()).ifPresent(synBugFields::setSrId);
		SynUtil.validateStr(bugEntity.getCf_mem_site_acc_id()).ifPresent(synBugFields::setMemSiteAccId);
		SynUtil.validateStr(bugEntity.getCf_idle_time()).ifPresent(synBugFields::setIdleTime);
		SynUtil.validateStr(bugEntity.getQa_contact()).ifPresent(synBugFields::setQaContact);
		SynUtil.validateStr(bugEntity.getCf_agent_status()).ifPresent(synBugFields::setAgentStatus);
		SynUtil.validateStr(bugEntity.getCf_agent_type()).ifPresent(synBugFields::setAgentType);
		SynUtil.validateStr(bugEntity.getCf_rca_categories()).ifPresent(synBugFields::setRcaCategories);
		SynUtil.validateStr(bugEntity.getCf_iae_resolution()).ifPresent(synBugFields::setIaeResolution);
		SynUtil.validateStr(bugEntity.getDepends_on()).ifPresent(synBugFields::setDepends_on);
		sBug.setUpdatedDate(new Date());

		Optional.ofNullable(bugEntity.getProgressStatus()).ifPresent(status -> {
			sBug.getSyntheticFields().setStatusProgress(status);
		});
		Optional.ofNullable(bugEntity.getRoute()).ifPresent(route -> {
			sBug.getMiscellaneousFields().setRoute(route);
		});
		Optional.ofNullable(bugEntity.getJnAnalysisId()).ifPresent(jnId -> {
			sBug.getMiscellaneousFields().setJnAnalysisId(jnId);
		});
				
	}

	/**
	 * Create Synthetic bug from bug entity with not null and empty checks
	 * 
	 * @param bugEntity
	 * @return
	 */
	public static BugFields mapBugToSynthetic(BugEntity bugEntity) {
		SyntheticBug synBug = new SyntheticBug();
		synBug.setBugFields(new BugFields());
		synBug.setSyntheticFields(new SyntheticFields());
		synBug.setMiscellaneousFields(new MiscellaneousFields());
		updateSynBugFields(bugEntity, synBug);
		return synBug.getBugFields();
	}

	/**
	 * Get bug props map for Bugzilla process, from Synthetic bug
	 * 
	 * @param syntheticBug
	 * @return
	 */
	public static Map<String, Object> mapSyntheticToBugzilla(SyntheticBug syntheticBug) {
		BugEntity bugEntity = getBugEntityFromSynthetic(syntheticBug.getBugFields());

		@SuppressWarnings("unchecked")
		Map<String, Object> bugProps = gson.fromJson(gson.toJson(bugEntity), HashMap.class);

		bugProps.remove("attachments");
		bugProps.remove("apiKey");

		bugProps = bugProps.entrySet().stream().filter(set -> set.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return bugProps;
	}

	/**
	 * Set Bugzilla Fields to Synthetic Bug if changed
	 * 
	 * @param bug
	 * @param sBug
	 */
	public static void setBugzillaBugFieldsToSyntheticBug(Bug bug, SyntheticBug sBug) {
		SyntheticFields syntheticFields = sBug.getSyntheticFields();
		String bugId = bug.getParameterMap().get(BugzillaConstants.ID).toString();
		syntheticFields.setBugzillaBugId(Integer.parseInt(bugId));
		BugFields synBugFields = sBug.getBugFields();
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_AGENTS)).ifPresent(synBugFields::setAgentName);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_AGENT_STATUS)).ifPresent(synBugFields::setAgentStatus);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_AGENT_TYPE)).ifPresent(synBugFields::setAgentType);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_AGENT_VERSION)).ifPresent(synBugFields::setAgentVersion);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.ASSIGNED_TO)).ifPresent(synBugFields::setAssigneee);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.STATUS)).ifPresent(synBugFields::setBugStatus);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_BUGTYPE)).ifPresent(synBugFields::setBugtype);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN)).ifPresent(synBugFields::setCodeReviewComments);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.COMMENT)).ifPresent(synBugFields::setComment);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_COMMENTS_RELEASE_NOTES)).ifPresent(synBugFields::setCommentsReleaseNotes);
		SynUtil.validateStr(bug.getComponent()).ifPresent(synBugFields::setComponent);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_CUSTOMER)).ifPresent(synBugFields::setCustomer);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.DEADLINE)).ifPresent(synBugFields::setDeadline);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.DEPENDS_ON)).ifPresent(synBugFields::setDepends_on);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_DEPARTMENT)).ifPresent(synBugFields::setDepartment);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_ENVIRONMENT)).ifPresent(synBugFields::setEnvironment);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_ERRORCODE)).ifPresent(synBugFields::setErrorcode);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.ESTIMATED_TIME)).ifPresent(synBugFields::setEta);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_IAE_RESOLUTION)).ifPresent(synBugFields::setIaeResolution);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_IDLE_TIME)).ifPresent(synBugFields::setIdleTime);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_IMPACT)).ifPresent(synBugFields::setImpact);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_P4_LABEL)).ifPresent(synBugFields::setLabel);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_MEMITEM)).ifPresent(synBugFields::setMemitem);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_MEM_SITE_ACC_ID)).ifPresent(synBugFields::setMemSiteAccId);
		SynUtil.validateStr(bug.getOperatingSystem()).ifPresent(synBugFields::setOpSys);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.PLATFORM)).ifPresent(synBugFields::setPlatform);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_PORTFOLIO)).ifPresent(synBugFields::setPortfolio);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.PRIORITY)).ifPresent(synBugFields::setPriority);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.PRODUCT)).ifPresent(synBugFields::setProduct);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_PUBLIC)).ifPresent(synBugFields::setPublicc);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.QA_CONTACT)).ifPresent(synBugFields::setQaContact);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_RCA_CATEGORIES)).ifPresent(synBugFields::setRcaCategories);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_README)).ifPresent(synBugFields::setReadme);
		SynUtil.validateStr(bug.getResolution()).ifPresent(synBugFields::setResolution);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.SEVERITY)).ifPresent(synBugFields::setSeverity);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_SITE_ID)).ifPresent(synBugFields::setSiteId);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_BACKEND_FRONTEND)).ifPresent(synBugFields::setSource);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_SERVICE_REQUEST_ID)).ifPresent(synBugFields::setSrId);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_SUMINFO)).ifPresent(synBugFields::setSuminfo);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.WHITEBOARD)).ifPresent(synBugFields::setWhiteboard);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_WORKFLOW)).ifPresent(synBugFields::setWorkflow);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.CF_WORKFLOW_STATUS)).ifPresent(synBugFields::setWorkflowStatus);
		SynUtil.validateObj(bug.getParameterMap().get(BugzillaConstants.LAST_CHANGE_TIME)).ifPresent(synBugFields::setLast_change_time);
		SynUtil.validateStr(bug.getSummary()).ifPresent(synBugFields::setSummary);
		SynUtil.validateStr(bug.getVersion()).ifPresent(synBugFields::setVersion);
		sBug.setSyntheticFields(syntheticFields);
		sBug.setBugFields(synBugFields);

	}

}
