/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */ 
package com.yodlee.iae.bugr.gateway.bugzilla;
/**
 *@author Sanyam Jain
 */

public final class BugzillaConstants {

	public static final String BUG_ID = "bug_id";
	public static final String ASSIGNED_TO = "assigned_to";
	public static final String BUG_FILE_LOC = "bug_file_loc";
	public static final String BUG_SEVERITY = "bug_severity";
	public static final String SEVERITY = "severity";
	public static final String BUG_STATUS = "bug_status";
	public static final String CREATION_TS = "creation_ts";
	public static final String DELTA_TS = "delta_ts";
	public static final String SHORT_DESC = "short_desc";
	public static final String OP_SYS = "op_sys";
	public static final String PRIORITY = "priority";
	public static final String PRODUCT_ID = "product_id";
	public static final String REP_PLATFORM = "rep_platform";
	public static final String PLATFORM = "platform";
	public static final String REPORTER = "reporter";
	public static final String VERSION = "version";
	public static final String COMPONENT_ID = "component_id";
	public static final String RESOLUTION = "resolution";
	public static final String TARGET_MILESTONE = "target_milestone";
	public static final String QA_CONTACT = "qa_contact";
	public static final String STATUS_WHITEBOARD = "status_whiteboard";
	public static final String VOTES = "votes";
	public static final String LASTDIFFED = "lastdiffed";
	public static final String EVERCONFIRMED = "everconfirmed";
	public static final String REPORTER_ACCESSIBLE = "reporter_accessible";
	public static final String CCLIST_ACCESSIBLE = "cclist_accessible";
	public static final String ESTIMATED_TIME = "estimated_time";
	public static final String REMAINING_TIME = "remaining_time";
	public static final String DEADLINE = "deadline";
	public static final String ALIAS = "alias";
	public static final String CF_AGENTS = "cf_agents";
	public static final String CF_SUMINFO = "cf_suminfo";
	public static final String CF_MEMITEM = "cf_memitem";
	public static final String CF_ERRORCODE = "cf_errorcode";
	public static final String CF_BUILD_FOUND = "cf_build_found";
	public static final String CF_ENVIRONMENT = "cf_environment";
	public static final String CF_AGENT_TYPE = "cf_agent_type";
	public static final String CF_CUSTOMER_NOTE = "cf_customer_note";
	public static final String CF_CUSTOMER = "cf_customer";
	public static final String CF_REMEDY_ID = "cf_remedy_id";
	public static final String CF_CHANGELIST_NO = "cf_changelist_no";
	public static final String CF_ROOT_CAUSE = "cf_root_cause";
	public static final String CF_BUILD_FIXED = "cf_build_fixed";
	public static final String CF_RELEASE_NOTES = "cf_release_notes";
	public static final String CF_COMMENTS_RELEASE_NOTES = "cf_comments_release_notes";
	public static final String CF_AGENT_STATUS = "cf_agent_status";
	public static final String CF_PUBLIC = "cf_public";
	public static final String CF_BUGTYPE = "cf_bugtype";
	public static final String CF_RCA = "cf_rca";
	public static final String CF_TEST_CASE_ID = "cf_test_case_id";
	public static final String CF_FIXED_RELEASE = "cf_fixed_release";
	public static final String CF_BACKEND_FRONTEND = "cf_backend_frontend";
	public static final String CF_DEPARTMENT = "cf_department";
	public static final String CF_OLDBUGZILLA_ID = "cf_oldbugzilla_id";
	public static final String CF_TRIAGE_INDICATOR = "cf_triage_indicator";
	public static final String CF_RECOMENTED_ACTION = "cf_recomented_action";
	public static final String CF_BUG_COMPLEXITY = "cf_bug_complexity";
	public static final String CF_TRIAGE_INDICATOR1 = "cf_triage_indicator1";
	public static final String CF_BUG_COMPLEXITY1 = "cf_bug_complexity1";
	public static final String CF_ERR_PREFIX_NO = "cf_err_prefix_no";
	public static final String CF_ERR_POSTFIX_NO = "cf_err_postfix_no";
	public static final String CF_REVIEWER = "cf_reviewer";
	public static final String CF_MENTOR = "cf_mentor";
	public static final String CF_ANALYST = "cf_analyst";
	public static final String CF_IMPACT = "cf_impact";
	public static final String CF_CODE_REVIEW_COMMENTS = "cf_code_review_comments";
	public static final String CF_SERVICE_REQUEST_ID = "cf_service_request_id";
	public static final String CF_AGENT_VERSION = "cf_agent_version";
	public static final String CF_CATEGORY = "cf_category";
	public static final String CF_PLAN = "cf_plan";
	public static final String CF_WORKFLOW_STATUS = "cf_workflow_status";
	public static final String CF_IDLE_TIME = "cf_idle_time";
	public static final String CF_SHAREPOINT_LINK = "cf_sharepoint_link";
	public static final String CF_RESOLUTION1 = "cf_resolution1";
	public static final String CF_RESOLUTION2 = "cf_resolution2";
	public static final String CF_RESOLUTION3 = "cf_resolution3";
	public static final String CF_PATCH_BUILD_FIXED = "cf_patch_build_fixed";
	public static final String CF_PATCHES_BUILD_FIXED = "cf_patches_build_fixed";
	public static final String CF_BUILD_COMPONENTS = "cf_build_components";
	public static final String CF_CHOOSE_FROM = "cf_choose_from";
	public static final String CF_COBRAND_BUG_ID = "cf_cobrand_bug_id";
	public static final String CF_CONFIGURATION_CHANGES = "cf_configuration_changes";
	public static final String CF_DOCUMENTATION_CHANGES = "cf_documentation_changes";
	public static final String CF_P4_LABEL = "cf_p4_label";
	public static final String CF_README = "cf_readme";
	public static final String CF_5X_CHANGELIST = "cf_5x_changelist";
	public static final String CF_COBRAND_CHANGELIST = "cf_cobrand_changelist";
	public static final String CF_901_CHANGELIST = "cf_901_changelist";
	public static final String CF_90_CHANGELIST = "cf_90_changelist";
	public static final String CF_811_CHANGELIST = "cf_811_changelist";
	public static final String CF_89_CHANGELIST = "cf_89_changelist";
	public static final String CF_91_CHANGELIST = "cf_91_changelist";
	public static final String CF_92_CHANGELIST = "cf_92_changelist";
	public static final String CF_93_CHANGELIST = "cf_93_changelist";
	public static final String CF_94_CHANGELIST = "cf_94_changelist";
	public static final String CF_95_CHANGELIST = "cf_95_changelist";
	public static final String CF_96_CHANGELIST = "cf_96_changelist";
	public static final String CF_97_CHANGELIST = "cf_97_changelist";
	public static final String CF_QE_RCA = "cf_qe_rca";
	public static final String CF_BUILD_FOUND_NEW = "cf_build_found_new";
	public static final String CF_BUILD_FIXED_NEW = "cf_build_fixed_new";
	public static final String CF_91_CHANGELIST_1 = "cf_91_changelist_1";
	public static final String CF_CF_902_CHANGELIST = "cf_cf_902_changelist";
	public static final String CF_902_CHANGELIST = "cf_902_changelist";
	public static final String CF_903_CHANGELIST = "cf_903_changelist";
	public static final String CF_FIDELITY_ASP = "cf_fidelity_asp";
	public static final String CF_STORY_ID = "cf_story_id";
	public static final String CF_RCA_QE = "cf_rca_qe";
	public static final String CF_RCA_DEV = "cf_rca_dev";
	public static final String CF_RCA_REOPENED = "cf_rca_reopened";
	public static final String CF_RCA_WID = "cf_rca_wid";
	public static final String CF_RCA_REOPENED_DEV = "cf_rca_reopened_dev";
	public static final String CF_RCA_REOPENED_QE = "cf_rca_reopened_qe";
	public static final String CF_TEA_EVALUATION = "cf_tea_evaluation";
	public static final String CF_TEA_CAUSE = "cf_tea_cause";
	public static final String CF_TEA_DISPOSITION = "cf_tea_disposition";
	public static final String CF_TEA__DISPOSITION = "cf_tea__disposition";
	public static final String CF_TEA_MANAGER = "cf_tea_manager";
	public static final String CF_QA_BROWSER_DETAILS = "cf_qa_browser_details";
	public static final String CF_BRANCH_FOUND = "cf_branch_found";
	public static final String CF_PORTFOLIO = "cf_portfolio";
	public static final String CF_RELEASE_FOUND = "cf_release_found";
	public static final String CF_BUG_FIXED_ON = "cf_bug_fixed_on";
	public static final String CF_WORKFLOW = "cf_workflow";
	public static final String CF_BUSINESS = "cf_business";
	public static final String CF_SUB_BRAND = "cf_sub_brand";
	public static final String CF_USERNAME = "cf_username";
	public static final String CF_SLA_TYPE = "cf_sla_type";
	public static final String CF_CODE_REVIEWER = "cf_code_reviewer";
	public static final String CF_CODE_REVIEW_COMMENTS_GEN = "cf_code_review_comments_gen";
	public static final String CF_IS_REGRESSION = "cf_is_regression";
	public static final String CF_FEATURE = "cf_feature";
	public static final String CF_SITE_ID = "cf_site_id";
	public static final String CF_MEM_SITE_ACC_ID = "cf_mem_site_acc_id";
	public static final String CF_IS_AUTOMATION = "cf_is_automation";
	public static final String CF_ETA = "cf_eta";
	public static final String CF_RCA_CATEGORIES = "cf_rca_categories";
	public static final String CF_RCA_SUB_CATEGORIES = "cf_rca_sub_categories";
	public static final String CF_ERRCATEGORY = "cf_errcategory";
	public static final String CF_JSON_OUT = "cf_json_out";
	public static final String CF_ESCALATION_STATUS = "cf_escalation_status";
	public static final String KEYWORDS_CONTAINER = "keywords_container";
	public static final String BZ_QA_CONTACT_EDIT_CONTAINER = "bz_qa_contact_edit_container";
	public static final String CF_IAE_RESOLUTION = "cf_iae_resolution";
	public static final String DEPENDS_ON = "depends_on";
	public static final String LAST_CHANGE_TIME = "last_change_time";

	public static final String WHITEBOARD = "whiteboard";
	public static final String PRODUCT = "product";
	public static final String COMPONENT = "component";
	public static final String SUMMARY = "summary";
	public static final String COMMENT = "comment";
	public static final String BODY = "body";
	public static final String IS_PRIVATE = "is_private";
	public static final String SET = "set";
	
	public static final String ID = "id";
	public static final String STATUS = "status";
	public static final String BUGZILLA_DUPLICATE_BUG_VERIFICATION_QUERY_TTR = "select b.bug_id, thetext from longdescs as ld,bugs as b where b.cf_department LIKE 'IAE' and (b.status_whiteboard LIKE '%IAT%' or b.status_whiteboard LIKE '%PreSR%' or b.status_whiteboard LIKE '%PRESR_LATENCY%' or b.status_whiteboard LIKE '%TTR%') and ld.bug_id=b.bug_id and ld.thetext LIKE '%"+"INPUT_EXCEPTION_NAME"+"%' and ld.thetext LIKE '%"+"INPUT_EXCEPTION_DESCRIPTION"+"%' and b.cf_errorcode LIKE '%INPUT_ERROR_CODE%' and b.cf_site_id LIKE '%INPUT_SITE_ID%' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by b.bug_id desc LIMIT 1;";
	/**
	 * @author RRaj
	 * modifying query to get duplicate bug
	 */
	public static final String BUGZILLA_DUPLICATE_BUG_VERIFICATION_QUERY_PRESR_RECON_OLD = "select b.bug_id, thetext from longdescs as ld,bugs as b where b.cf_department LIKE 'IAE' and (b.status_whiteboard LIKE '%PreSR%' or b.status_whiteboard LIKE '%PRESR_LATENCY%') and ld.bug_id=b.bug_id and ld.thetext LIKE '%"+"INPUT_EXCEPTION_NAME"+"%' and ld.thetext LIKE '%"+"INPUT_EXCEPTION_DESCRIPTION"+"%' and b.cf_errorcode LIKE '%INPUT_ERROR_CODE%' and b.cf_agents LIKE 'INPUT_AGENT_NAME%' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by b.bug_id desc LIMIT 1;";
	public static final String BUGZILLA_DUPLICATE_BUG_VERIFICATION_QUERY_PRESR_RECON_NEW = "select bug_id from bugs where cf_department LIKE 'IAE' and status_whiteboard LIKE '%PreSR_Recon%' and cf_p4_label LIKE 'INPUT_LABEL' and cf_errorcode LIKE 'INPUT_ERROR_CODE' and cf_agents LIKE 'INPUT_AGENT_NAME' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by bug_id desc LIMIT 1;";
	public static final String BUGZILLA_DUPLICATE_BUG_VERIFICATION_QUERY = "select bug_id from bugs where cf_department LIKE 'IAE' and status_whiteboard LIKE '%PreSR_ErrorSegment%' and (case when cf_code_review_comments_gen like '%com.yodlee.dap.gatherer.gather.JController%' then SUBSTRING_INDEX(cf_code_review_comments_gen,'com.yodlee.dap.gatherer.gather.JController',1) else SUBSTRING_INDEX(cf_code_review_comments_gen,'||',1) end) LIKE '%INPUT_CODE_REVIEW_COMMENTS%' and cf_errorcode LIKE '%INPUT_ERROR_CODE%' and cf_agents LIKE 'INPUT_AGENT_NAME%' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by bug_id desc LIMIT 1;";
	public static final String BUGZILLA_RECON_DUPLICATE_BUG_VERIFICATION_QUERY_FOR_SECNORM = "select b.bug_id from bugs as b where b.cf_department LIKE 'IAE' and b.status_whiteboard LIKE '%Recon_Services%' and b.status_whiteboard LIKE '%Cusip:%' and b.status_whiteboard LIKE '%Symbol:%' and b.status_whiteboard LIKE '%INPUT_CUSIP%' and b.status_whiteboard LIKE '%INPUT_SYMBOL%' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by b.bug_id desc LIMIT 1;";
	public static final String BUGZILLA_RECON_DUPLICATE_BUG_VERIFICATION_QUERY_FOR_AGENTBUG = "select b.bug_id from bugs as b where b.cf_department LIKE 'IAE' and b.status_whiteboard LIKE '%Recon_Services%' and b.status_whiteboard LIKE '%SegmentId:%' and b.status_whiteboard LIKE '%INPUT_ACTION_CODE%' and b.status_whiteboard LIKE '%INPUT_SEGID%' and  b.cf_errorcode LIKE '%INPUT_ERROR_CODE%' and b.cf_suminfo LIKE 'INPUT_SUMINFO_ID%' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by b.bug_id desc LIMIT 1;";
	//public static final String BUGZILLA_DUPLICATE_BUG_VERIFICATION_QUERY = "select bug_id from bugs where cf_department LIKE 'IAE' and status_whiteboard LIKE '%PreSR_ErrorSegment%' and cf_code_review_comments_gen LIKE '%INPUT_CODE_REVIEW_COMMENTS%' and cf_errorcode LIKE '%INPUT_ERROR_CODE%' and cf_agents LIKE 'INPUT_AGENT_NAME%' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by bug_id desc LIMIT 1;";
	public static final String BUGZILLA_RECON_DUPLICATE_BUG_VERIFICATION_QUERY = "select b.bug_id from bugs as b where b.cf_department LIKE 'IAE' and b.status_whiteboard LIKE '%Recon_Services%' and b.cf_errorcode LIKE '%INPUT_ERROR_CODE%' and b.cf_agents LIKE 'INPUT_AGENT_NAME%' and cf_workflow_status!='Closed' and bug_status != 'RESOLVED' and bug_status != 'CLOSED' order by b.bug_id desc LIMIT 1;";
	public static final String BUGZILLA_GET_SITEID_BUGID_QUERY_FOR_TTR = "select cf_site_id, bug_id from bugs where lastdiffed BETWEEN CURDATE() - INTERVAL 50 DAY AND CURDATE() + INTERVAL 1 DAY and bug_status != 'CLOSED' and bug_status != 'RESOLVED' and short_desc LIKE '%<TTR-ALERT>%' and cf_site_id != '' order by bug_id asc;";
	public static final String BUGZILLA_DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String BUGZILLA_DB_HOST1 = "jdbc:mysql://192.168.57.101:3306/bugs";
	public static final String BUGZILLA_DB_USERNAME = "presr";
	public static final String BUGZILLA_DB_PWD = "presr321";
	public static final String CREATOR = "creator";

	public static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	public static final String COUNTRY_NAME_FETCH_QUERY_REPALDA = "SELECT COUNTRY.COUNTRY_ISO_CODE FROM COUNTRY INNER JOIN LOCALE ON COUNTRY.COUNTRY_ID = LOCALE.LOCALE_ID INNER JOIN SUM_INFO_SPTD_LOCALE ON LOCALE.LOCALE_ID = SUM_INFO_SPTD_LOCALE.LOCALE_ID INNER JOIN SUM_INFO ON SUM_INFO_SPTD_LOCALE.SUM_INFO_ID = SUM_INFO.SUM_INFO_ID AND SUM_INFO.SUM_INFO_ID = SUMINFO";
	public static final String SUM_INFO_CONTAINER_NAME_FETCH_QUERY = "SELECT SUM_INFO.SUM_INFO_ID, TAG.TAG FROM SUM_INFO INNER JOIN TAG ON TAG.TAG_ID = SUM_INFO.TAG_ID AND SUM_INFO.IS_READY = 1 AND SUM_INFO.IS_BETA = 0 AND SUM_INFO.CLASS_NAME = 'AGENT_NAME'";
	public static final String REPALDA_DB_HOST = "jdbc:oracle:thin:@192.168.84.20:1521/repalda";
	public static final String REPALDA_DB_USERNAME = "read";
	public static final String REPALDA_DB_PWD = "read";

	public static final String AGENT_CATEGORY_ORDERS = "Orders";
	public static final String AGENT_CATEGORY_BANK = "Bank";
	public static final String AGENT_CATEGORY_CREDIT_CARD = "Credit Card";
	public static final String AGENT_CATEGORY_LOAN = "Loan";
	public static final String AGENT_CATEGORY_MORTGAGE = "Mortgage";
	public static final String AGENT_CATEGORY_INVESTMENT= "Investment";
	public static final String AGENT_CATEGORY_INSURANCE = "Insurance";
	public static final String AGENT_CATEGORY_EMAIL = "Email";
	public static final String AGENT_CATEGORY_NEWS = "News";
	public static final String AGENT_CATEGORY_REWARDS = "Rewards";
	public static final String AGENT_CATEGORY_PAYMENT_SERVICES = "Payment Services";
	public static final String AGENT_CATEGORY_BILLS = "Bills";
	public static final Object KEYWORDS = "keywords";
	public static final Object CF_INITIATIVE = "cf_initiative";

}