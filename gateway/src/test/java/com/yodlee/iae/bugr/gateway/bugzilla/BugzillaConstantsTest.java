package com.yodlee.iae.bugr.gateway.bugzilla;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *@author Sanyam Jain
 */
@RunWith(SpringJUnit4ClassRunner.class) 
public class BugzillaConstantsTest {

	@InjectMocks
	BugzillaConstants bugzillaC;
	
	@Before
	public void setUp() throws Exception{   
		MockitoAnnotations.initMocks(this);     
	} 

	@SuppressWarnings("static-access")
	@Test
	public void testConstants() {
		assertEquals(bugzillaC.BUG_ID, bugzillaC.BUG_ID);
		assertEquals(bugzillaC.ASSIGNED_TO, bugzillaC.ASSIGNED_TO);
		assertEquals(bugzillaC.BUG_FILE_LOC, bugzillaC.BUG_FILE_LOC);
		assertEquals(bugzillaC.BUG_SEVERITY, bugzillaC.BUG_SEVERITY);
		assertEquals(bugzillaC.BUG_STATUS, bugzillaC.BUG_STATUS);
		assertEquals(bugzillaC.CREATION_TS, bugzillaC.CREATION_TS);
		assertEquals(bugzillaC.DELTA_TS, bugzillaC.DELTA_TS);
		assertEquals(bugzillaC.SHORT_DESC, bugzillaC.SHORT_DESC);
		assertEquals(bugzillaC.OP_SYS, bugzillaC.OP_SYS);
		assertEquals(bugzillaC.PRIORITY, bugzillaC.PRIORITY);
		assertEquals(bugzillaC.PRODUCT_ID, bugzillaC.PRODUCT_ID);
		assertEquals(bugzillaC.REP_PLATFORM, bugzillaC.REP_PLATFORM);
		assertEquals(bugzillaC.REPORTER, bugzillaC.REPORTER);
		assertEquals(bugzillaC.VERSION, bugzillaC.VERSION);
		assertEquals(bugzillaC.COMPONENT_ID, bugzillaC.COMPONENT_ID);
		assertEquals(bugzillaC.RESOLUTION, bugzillaC.RESOLUTION);
		assertEquals(bugzillaC.TARGET_MILESTONE, bugzillaC.TARGET_MILESTONE);
		assertEquals(bugzillaC.QA_CONTACT, bugzillaC.QA_CONTACT);
		assertEquals(bugzillaC.STATUS_WHITEBOARD, bugzillaC.STATUS_WHITEBOARD);
		assertEquals(bugzillaC.VOTES, bugzillaC.VOTES);
		assertEquals(bugzillaC.LASTDIFFED, bugzillaC.LASTDIFFED);
		assertEquals(bugzillaC.EVERCONFIRMED, bugzillaC.EVERCONFIRMED);
		assertEquals(bugzillaC.REPORTER_ACCESSIBLE, bugzillaC.REPORTER_ACCESSIBLE);
		assertEquals(bugzillaC.CCLIST_ACCESSIBLE, bugzillaC.CCLIST_ACCESSIBLE);
		assertEquals(bugzillaC.ESTIMATED_TIME, bugzillaC.ESTIMATED_TIME);
		assertEquals(bugzillaC.REMAINING_TIME, bugzillaC.REMAINING_TIME);
		assertEquals(bugzillaC.DEADLINE, bugzillaC.DEADLINE);
		assertEquals(bugzillaC.ALIAS, bugzillaC.ALIAS);
		assertEquals(bugzillaC.CF_AGENTS, bugzillaC.CF_AGENTS);
		assertEquals(bugzillaC.CF_SUMINFO, bugzillaC.CF_SUMINFO);
		assertEquals(bugzillaC.CF_MEMITEM, bugzillaC.CF_MEMITEM);
		assertEquals(bugzillaC.CF_ERRORCODE, bugzillaC.CF_ERRORCODE);
		assertEquals(bugzillaC.CF_BUILD_FOUND, bugzillaC.CF_BUILD_FOUND);
		assertEquals(bugzillaC.CF_ENVIRONMENT, bugzillaC.CF_ENVIRONMENT);
		assertEquals(bugzillaC.CF_AGENT_TYPE, bugzillaC.CF_AGENT_TYPE);
		assertEquals(bugzillaC.CF_CUSTOMER_NOTE, bugzillaC.CF_CUSTOMER_NOTE);
		assertEquals(bugzillaC.CF_CUSTOMER, bugzillaC.CF_CUSTOMER);
		assertEquals(bugzillaC.CF_REMEDY_ID, bugzillaC.CF_REMEDY_ID);
		assertEquals(bugzillaC.CF_CHANGELIST_NO, bugzillaC.CF_CHANGELIST_NO);
		assertEquals(bugzillaC.CF_ROOT_CAUSE, bugzillaC.CF_ROOT_CAUSE);
		assertEquals(bugzillaC.CF_BUILD_FIXED, bugzillaC.CF_BUILD_FIXED);
		assertEquals(bugzillaC.CF_RELEASE_NOTES, bugzillaC.CF_RELEASE_NOTES);
		assertEquals(bugzillaC.CF_COMMENTS_RELEASE_NOTES, bugzillaC.CF_COMMENTS_RELEASE_NOTES);
		assertEquals(bugzillaC.CF_AGENT_STATUS, bugzillaC.CF_AGENT_STATUS);
		assertEquals(bugzillaC.CF_PUBLIC, bugzillaC.CF_PUBLIC);
		assertEquals(bugzillaC.CF_BUGTYPE, bugzillaC.CF_BUGTYPE);
		assertEquals(bugzillaC.CF_RCA, bugzillaC.CF_RCA);
		assertEquals(bugzillaC.CF_TEST_CASE_ID, bugzillaC.CF_TEST_CASE_ID);
		assertEquals(bugzillaC.CF_FIXED_RELEASE, bugzillaC.CF_FIXED_RELEASE);
		assertEquals(bugzillaC.CF_BACKEND_FRONTEND, bugzillaC.CF_BACKEND_FRONTEND);
		assertEquals(bugzillaC.CF_DEPARTMENT, bugzillaC.CF_DEPARTMENT);
		assertEquals(bugzillaC.CF_OLDBUGZILLA_ID, bugzillaC.CF_OLDBUGZILLA_ID);
		assertEquals(bugzillaC.CF_TRIAGE_INDICATOR, bugzillaC.CF_TRIAGE_INDICATOR);
		assertEquals(bugzillaC.CF_RECOMENTED_ACTION, bugzillaC.CF_RECOMENTED_ACTION);
		assertEquals(bugzillaC.CF_BUG_COMPLEXITY, bugzillaC.CF_BUG_COMPLEXITY);
		assertEquals(bugzillaC.CF_TRIAGE_INDICATOR1, bugzillaC.CF_TRIAGE_INDICATOR1);
		assertEquals(bugzillaC.CF_BUG_COMPLEXITY1, bugzillaC.CF_BUG_COMPLEXITY1);
		assertEquals(bugzillaC.CF_ERR_PREFIX_NO, bugzillaC.CF_ERR_PREFIX_NO);
		assertEquals(bugzillaC.CF_ERR_POSTFIX_NO, bugzillaC.CF_ERR_POSTFIX_NO);
		assertEquals(bugzillaC.CF_REVIEWER, bugzillaC.CF_REVIEWER);
		assertEquals(bugzillaC.CF_MENTOR, bugzillaC.CF_MENTOR);
		assertEquals(bugzillaC.CF_ANALYST, bugzillaC.CF_ANALYST);
		assertEquals(bugzillaC.CF_IMPACT, bugzillaC.CF_IMPACT);
		assertEquals(bugzillaC.CF_CODE_REVIEW_COMMENTS, bugzillaC.CF_CODE_REVIEW_COMMENTS);
		assertEquals(bugzillaC.CF_SERVICE_REQUEST_ID, bugzillaC.CF_SERVICE_REQUEST_ID);
		assertEquals(bugzillaC.CF_AGENT_VERSION, bugzillaC.CF_AGENT_VERSION);
		assertEquals(bugzillaC.CF_CATEGORY, bugzillaC.CF_CATEGORY);
		assertEquals(bugzillaC.CF_PLAN, bugzillaC.CF_PLAN);
		assertEquals(bugzillaC.CF_WORKFLOW_STATUS, bugzillaC.CF_WORKFLOW_STATUS);
		assertEquals(bugzillaC.CF_IDLE_TIME, bugzillaC.CF_IDLE_TIME);
		assertEquals(bugzillaC.CF_SHAREPOINT_LINK, bugzillaC.CF_SHAREPOINT_LINK);
		assertEquals(bugzillaC.CF_RESOLUTION1, bugzillaC.CF_RESOLUTION1);
		assertEquals(bugzillaC.CF_RESOLUTION2, bugzillaC.CF_RESOLUTION2);
		assertEquals(bugzillaC.CF_RESOLUTION3, bugzillaC.CF_RESOLUTION3);
		assertEquals(bugzillaC.CF_PATCH_BUILD_FIXED, bugzillaC.CF_PATCH_BUILD_FIXED);
		assertEquals(bugzillaC.CF_PATCHES_BUILD_FIXED, bugzillaC.CF_PATCHES_BUILD_FIXED);
		assertEquals(bugzillaC.CF_BUILD_COMPONENTS, bugzillaC.CF_BUILD_COMPONENTS);
		assertEquals(bugzillaC.CF_CHOOSE_FROM, bugzillaC.CF_CHOOSE_FROM);
		assertEquals(bugzillaC.CF_COBRAND_BUG_ID, bugzillaC.CF_COBRAND_BUG_ID);
		assertEquals(bugzillaC.CF_CONFIGURATION_CHANGES, bugzillaC.CF_CONFIGURATION_CHANGES);
		assertEquals(bugzillaC.CF_DOCUMENTATION_CHANGES, bugzillaC.CF_DOCUMENTATION_CHANGES);
		assertEquals(bugzillaC.CF_P4_LABEL, bugzillaC.CF_P4_LABEL);
		assertEquals(bugzillaC.CF_README, bugzillaC.CF_README);
		assertEquals(bugzillaC.CF_5X_CHANGELIST, bugzillaC.CF_5X_CHANGELIST);
		assertEquals(bugzillaC.CF_COBRAND_CHANGELIST, bugzillaC.CF_COBRAND_CHANGELIST);
		assertEquals(bugzillaC.CF_901_CHANGELIST, bugzillaC.CF_901_CHANGELIST);
		assertEquals(bugzillaC.CF_90_CHANGELIST, bugzillaC.CF_90_CHANGELIST);
		assertEquals(bugzillaC.CF_811_CHANGELIST, bugzillaC.CF_811_CHANGELIST);
		assertEquals(bugzillaC.CF_89_CHANGELIST, bugzillaC.CF_89_CHANGELIST);
		assertEquals(bugzillaC.CF_91_CHANGELIST, bugzillaC.CF_91_CHANGELIST);
		assertEquals(bugzillaC.CF_92_CHANGELIST, bugzillaC.CF_92_CHANGELIST);
		assertEquals(bugzillaC.CF_93_CHANGELIST, bugzillaC.CF_93_CHANGELIST);
		assertEquals(bugzillaC.CF_94_CHANGELIST, bugzillaC.CF_94_CHANGELIST);
		assertEquals(bugzillaC.CF_95_CHANGELIST, bugzillaC.CF_95_CHANGELIST);
		assertEquals(bugzillaC.CF_96_CHANGELIST, bugzillaC.CF_96_CHANGELIST);
		assertEquals(bugzillaC.CF_97_CHANGELIST, bugzillaC.CF_97_CHANGELIST);
		assertEquals(bugzillaC.CF_QE_RCA, bugzillaC.CF_QE_RCA);
		assertEquals(bugzillaC.CF_BUILD_FOUND_NEW, bugzillaC.CF_BUILD_FOUND_NEW);
		assertEquals(bugzillaC.CF_BUILD_FIXED_NEW, bugzillaC.CF_BUILD_FIXED_NEW);
		assertEquals(bugzillaC.CF_91_CHANGELIST_1, bugzillaC.CF_91_CHANGELIST_1);
		assertEquals(bugzillaC.CF_CF_902_CHANGELIST, bugzillaC.CF_CF_902_CHANGELIST);
		assertEquals(bugzillaC.CF_902_CHANGELIST, bugzillaC.CF_902_CHANGELIST);
		assertEquals(bugzillaC.CF_903_CHANGELIST, bugzillaC.CF_903_CHANGELIST);
		assertEquals(bugzillaC.CF_FIDELITY_ASP, bugzillaC.CF_FIDELITY_ASP);
		assertEquals(bugzillaC.CF_STORY_ID, bugzillaC.CF_STORY_ID);
		assertEquals(bugzillaC.CF_RCA_QE, bugzillaC.CF_RCA_QE);
		assertEquals(bugzillaC.CF_RCA_DEV, bugzillaC.CF_RCA_DEV);
		assertEquals(bugzillaC.CF_RCA_REOPENED, bugzillaC.CF_RCA_REOPENED);
		assertEquals(bugzillaC.CF_RCA_WID, bugzillaC.CF_RCA_WID);
		assertEquals(bugzillaC.CF_RCA_REOPENED_DEV, bugzillaC.CF_RCA_REOPENED_DEV);
		assertEquals(bugzillaC.CF_RCA_REOPENED_QE, bugzillaC.CF_RCA_REOPENED_QE);
		assertEquals(bugzillaC.CF_TEA_EVALUATION, bugzillaC.CF_TEA_EVALUATION);
		assertEquals(bugzillaC.CF_TEA_CAUSE, bugzillaC.CF_TEA_CAUSE);
		assertEquals(bugzillaC.CF_TEA_DISPOSITION, bugzillaC.CF_TEA_DISPOSITION);
		assertEquals(bugzillaC.CF_TEA__DISPOSITION, bugzillaC.CF_TEA__DISPOSITION);
		assertEquals(bugzillaC.CF_TEA_MANAGER, bugzillaC.CF_TEA_MANAGER);
		assertEquals(bugzillaC.CF_QA_BROWSER_DETAILS, bugzillaC.CF_QA_BROWSER_DETAILS);
		assertEquals(bugzillaC.CF_BRANCH_FOUND, bugzillaC.CF_BRANCH_FOUND);
		assertEquals(bugzillaC.CF_PORTFOLIO, bugzillaC.CF_PORTFOLIO);
		assertEquals(bugzillaC.CF_RELEASE_FOUND, bugzillaC.CF_RELEASE_FOUND);
		assertEquals(bugzillaC.CF_BUG_FIXED_ON, bugzillaC.CF_BUG_FIXED_ON);
		assertEquals(bugzillaC.CF_WORKFLOW, bugzillaC.CF_WORKFLOW);
		assertEquals(bugzillaC.CF_BUSINESS, bugzillaC.CF_BUSINESS);
		assertEquals(bugzillaC.CF_SUB_BRAND, bugzillaC.CF_SUB_BRAND);
		assertEquals(bugzillaC.CF_USERNAME, bugzillaC.CF_USERNAME);
		assertEquals(bugzillaC.CF_SLA_TYPE, bugzillaC.CF_SLA_TYPE);
		assertEquals(bugzillaC.CF_CODE_REVIEWER, bugzillaC.CF_CODE_REVIEWER);
		assertEquals(bugzillaC.CF_CODE_REVIEW_COMMENTS_GEN, bugzillaC.CF_CODE_REVIEW_COMMENTS_GEN);
		assertEquals(bugzillaC.CF_IS_REGRESSION, bugzillaC.CF_IS_REGRESSION);
		assertEquals(bugzillaC.CF_FEATURE, bugzillaC.CF_FEATURE);
		assertEquals(bugzillaC.CF_SITE_ID, bugzillaC.CF_SITE_ID);
		assertEquals(bugzillaC.CF_MEM_SITE_ACC_ID, bugzillaC.CF_MEM_SITE_ACC_ID);
		assertEquals(bugzillaC.CF_IS_AUTOMATION, bugzillaC.CF_IS_AUTOMATION);
		assertEquals(bugzillaC.CF_ETA, bugzillaC.CF_ETA);
		assertEquals(bugzillaC.CF_RCA_CATEGORIES, bugzillaC.CF_RCA_CATEGORIES);
		assertEquals(bugzillaC.CF_RCA_SUB_CATEGORIES, bugzillaC.CF_RCA_SUB_CATEGORIES);
		assertEquals(bugzillaC.CF_ERRCATEGORY, bugzillaC.CF_ERRCATEGORY);
		assertEquals(bugzillaC.CF_JSON_OUT, bugzillaC.CF_JSON_OUT);
		assertEquals(bugzillaC.CF_ESCALATION_STATUS, bugzillaC.CF_ESCALATION_STATUS);
	}
}
