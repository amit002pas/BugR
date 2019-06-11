
/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.datatypes.bugs;

import java.util.List;

import javax.inject.Named;

import lombok.Data;

/**
 * @author Sanyam Jain
 *
 */
@Named
public @Data class BugEntity {

	private int id;
	
	private String synId;
	private Boolean createBugzillaBug;
	
	private String assigned_to;
	private String comment;
	private String bug_status;
	private String summary;
	private String op_sys;
	private String priority;
	private String product;
	private String rep_platform;
	private String version;
	private String component;
	private String status_whiteboard;
	private String cf_agents;
	private String cf_suminfo;
	private String cf_memitem;
	private String cf_errorcode;
	private String cf_environment;
	private String cf_customer;
	private String cf_bugtype;
	private String cf_backend_frontend; // Source
	private String cf_department;
	private String cf_impact;
	private String cf_service_request_id;
	private String cf_portfolio;
	private String cf_workflow;
	private String cf_site_id;
	private String cf_mem_site_acc_id;
	private String cf_eta;
	private String cf_agent_version;
	private String cf_workflow_status;
	private String deadline;
	private String cf_idle_time;
	private String cf_comments_release_notes;
	private String cf_readme;
	private String cf_public;
	private String bug_severity;
	private String keywords;
	private String qa_contact;
	private String cf_code_review_comments_gen;
	private String cf_p4_label;
	private String cf_agent_status;
	private String cf_agent_type;
	private String cf_rca_categories;
	private String cf_iae_resolution;
	private String resolution;
	private String cf_initiative;
	private List<AttachmentRequest> attachments;
	private String apiKey;
	private String action;
	private StatusProgress progressStatus;
	private String depends_on;
	private String jnAnalysisId;
	private String route;



}
