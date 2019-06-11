package com.yodlee.iae.bugr.gateway.bugzilla;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.j2bugzilla.base.Bug;

/**
 *@author Sanyam Jain
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UpdateBugTest {

	@InjectMocks
	UpdateBug updatebug;
	
	@Mock
	Bug bug;
	
	@Before
	public void setUp() throws Exception{   
		MockitoAnnotations.initMocks(this);     
	} 
	
	@Test
	public void updatebugTest() {
		updatebug.getMethodName();
		
		
		Map<Object, Object> hash = new HashMap<Object, Object>();
		Object[] bugArray = new Object[1];
		Map<String, Object> bug = new HashMap<String, Object>();
		
		Map<String, String> internals = new HashMap<String, String>();
		internals.put("id", "Test");
		bug.put("internals", internals);
		
		bug.put("product", "Test");
		bug.put("component", "Test");
		bug.put("summary", "Testing the search method");
		bug.put("version", "1.0");
		bug.put("product", "Test");
		
		bug.put("alias", "Test");
		bug.put("priority", "Test");
		bug.put("status", "Test");
		bug.put("resolution", "Test");
		bug.put("op_sys", "Test");
		bug.put("platform", "Test");
		bug.put("severity", "Test");
		bug.put("alias", "Test");
		
		bug.put(BugzillaConstants.BUG_ID,"Test");
		bug.put(BugzillaConstants.ASSIGNED_TO,"Test");
		bug.put(BugzillaConstants.BUG_FILE_LOC,"Test");
		bug.put(BugzillaConstants.BUG_SEVERITY,"Test");
		bug.put(BugzillaConstants.BUG_STATUS,"Test");
		bug.put(BugzillaConstants.CREATION_TS,"Test");
		bug.put(BugzillaConstants.DELTA_TS,"Test");
		bug.put(BugzillaConstants.SHORT_DESC,"Test");
		bug.put(BugzillaConstants.OP_SYS,"Test");
		bug.put(BugzillaConstants.PRIORITY,"Test");
		bug.put(BugzillaConstants.PRODUCT_ID,"Test");
		bug.put(BugzillaConstants.REP_PLATFORM,"Test");
		bug.put(BugzillaConstants.REPORTER,"Test");
		bug.put(BugzillaConstants.VERSION,"Test");
		bug.put(BugzillaConstants.COMPONENT_ID,"Test");
		bug.put(BugzillaConstants.RESOLUTION,"Test");
		bug.put(BugzillaConstants.TARGET_MILESTONE,"Test");
		bug.put(BugzillaConstants.QA_CONTACT,"Test");
		bug.put(BugzillaConstants.STATUS_WHITEBOARD,"Test");
		bug.put(BugzillaConstants.VOTES,"Test");
		bug.put(BugzillaConstants.LASTDIFFED,"Test");
		bug.put(BugzillaConstants.EVERCONFIRMED,"Test");
		bug.put(BugzillaConstants.REPORTER_ACCESSIBLE,"Test");
		bug.put(BugzillaConstants.CCLIST_ACCESSIBLE,"Test");
		bug.put(BugzillaConstants.ESTIMATED_TIME,"Test");
		bug.put(BugzillaConstants.REMAINING_TIME,"Test");
		bug.put(BugzillaConstants.DEADLINE,"Test");
		bug.put(BugzillaConstants.ALIAS,"Test");
		bug.put(BugzillaConstants.CF_AGENTS,"Test");
		bug.put(BugzillaConstants.CF_SUMINFO,"Test");
		bug.put(BugzillaConstants.CF_MEMITEM,"Test");
		bug.put(BugzillaConstants.CF_ERRORCODE,"Test");
		bug.put(BugzillaConstants.CF_BUILD_FOUND,"Test");
		bug.put(BugzillaConstants.CF_ENVIRONMENT,"Test");
		bug.put(BugzillaConstants.CF_AGENT_TYPE,"Test");
		bug.put(BugzillaConstants.CF_CUSTOMER_NOTE,"Test");
		bug.put(BugzillaConstants.CF_CUSTOMER,"Test");
		bug.put(BugzillaConstants.CF_REMEDY_ID,"Test");
		bug.put(BugzillaConstants.CF_CHANGELIST_NO,"Test");
		bug.put(BugzillaConstants.CF_ROOT_CAUSE,"Test");
		bug.put(BugzillaConstants.CF_BUILD_FIXED,"Test");
		bug.put(BugzillaConstants.CF_RELEASE_NOTES,"Test");
		bug.put(BugzillaConstants.CF_COMMENTS_RELEASE_NOTES,"Test");
		bug.put(BugzillaConstants.CF_AGENT_STATUS,"Test");
		bug.put(BugzillaConstants.CF_PUBLIC,"Test");
		bug.put(BugzillaConstants.CF_BUGTYPE,"Test");
		bug.put(BugzillaConstants.CF_RCA,"Test");
		bug.put(BugzillaConstants.CF_TEST_CASE_ID,"Test");
		bug.put(BugzillaConstants.CF_FIXED_RELEASE,"Test");
		bug.put(BugzillaConstants.CF_BACKEND_FRONTEND,"Test");
		bug.put(BugzillaConstants.CF_DEPARTMENT,"Test");
		bug.put(BugzillaConstants.CF_OLDBUGZILLA_ID,"Test");
		bug.put(BugzillaConstants.CF_TRIAGE_INDICATOR,"Test");
		bug.put(BugzillaConstants.CF_RECOMENTED_ACTION,"Test");
		bug.put(BugzillaConstants.CF_BUG_COMPLEXITY,"Test");
		bug.put(BugzillaConstants.CF_TRIAGE_INDICATOR1,"Test");
		bug.put(BugzillaConstants.CF_BUG_COMPLEXITY1,"Test");
		bug.put(BugzillaConstants.CF_ERR_PREFIX_NO,"Test");
		bug.put(BugzillaConstants.CF_ERR_POSTFIX_NO,"Test");
		bug.put(BugzillaConstants.CF_REVIEWER,"Test");
		bug.put(BugzillaConstants.CF_MENTOR,"Test");
		bug.put(BugzillaConstants.CF_ANALYST,"Test");
		bug.put(BugzillaConstants.CF_IMPACT,"Test");
		bug.put(BugzillaConstants.CF_CODE_REVIEW_COMMENTS,"Test");
		bug.put(BugzillaConstants.CF_SERVICE_REQUEST_ID,"Test");
		bug.put(BugzillaConstants.CF_AGENT_VERSION,"Test");
		bug.put(BugzillaConstants.CF_CATEGORY,"Test");
		bug.put(BugzillaConstants.CF_PLAN,"Test");
		bug.put(BugzillaConstants.CF_WORKFLOW_STATUS,"Test");
		bug.put(BugzillaConstants.CF_IDLE_TIME,"Test");
		bug.put(BugzillaConstants.CF_SHAREPOINT_LINK,"Test");
		bug.put(BugzillaConstants.CF_RESOLUTION1,"Test");
		bug.put(BugzillaConstants.CF_RESOLUTION2,"Test");
		bug.put(BugzillaConstants.CF_RESOLUTION3,"Test");
		bug.put(BugzillaConstants.CF_PATCH_BUILD_FIXED,"Test");
		bug.put(BugzillaConstants.CF_PATCHES_BUILD_FIXED,"Test");
		bug.put(BugzillaConstants.CF_BUILD_COMPONENTS,"Test");
		bug.put(BugzillaConstants.CF_CHOOSE_FROM,"Test");
		bug.put(BugzillaConstants.CF_COBRAND_BUG_ID,"Test");
		bug.put(BugzillaConstants.CF_CONFIGURATION_CHANGES,"Test");
		bug.put(BugzillaConstants.CF_DOCUMENTATION_CHANGES,"Test");
		bug.put(BugzillaConstants.CF_P4_LABEL,"Test");
		bug.put(BugzillaConstants.CF_README,"Test");
		bug.put(BugzillaConstants.CF_5X_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_COBRAND_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_901_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_90_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_811_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_89_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_91_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_92_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_93_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_94_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_95_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_96_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_97_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_QE_RCA,"Test");
		bug.put(BugzillaConstants.CF_BUILD_FOUND_NEW,"Test");
		bug.put(BugzillaConstants.CF_BUILD_FIXED_NEW,"Test");
		bug.put(BugzillaConstants.CF_91_CHANGELIST_1,"Test");
		bug.put(BugzillaConstants.CF_CF_902_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_902_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_903_CHANGELIST,"Test");
		bug.put(BugzillaConstants.CF_FIDELITY_ASP,"Test");
		bug.put(BugzillaConstants.CF_STORY_ID,"Test");
		bug.put(BugzillaConstants.CF_RCA_QE,"Test");
		bug.put(BugzillaConstants.CF_RCA_DEV,"Test");
		bug.put(BugzillaConstants.CF_RCA_REOPENED,"Test");
		bug.put(BugzillaConstants.CF_RCA_WID,"Test");
		bug.put(BugzillaConstants.CF_RCA_REOPENED_DEV,"Test");
		bug.put(BugzillaConstants.CF_RCA_REOPENED_QE,"Test");
		bug.put(BugzillaConstants.CF_TEA_EVALUATION,"Test");
		bug.put(BugzillaConstants.CF_TEA_CAUSE,"Test");
		bug.put(BugzillaConstants.CF_TEA_DISPOSITION,"Test");
		bug.put(BugzillaConstants.CF_TEA__DISPOSITION,"Test");
		bug.put(BugzillaConstants.CF_TEA_MANAGER,"Test");
		bug.put(BugzillaConstants.CF_QA_BROWSER_DETAILS,"Test");
		bug.put(BugzillaConstants.CF_BRANCH_FOUND,"Test");
		bug.put(BugzillaConstants.CF_PORTFOLIO,"Test");
		bug.put(BugzillaConstants.CF_RELEASE_FOUND,"Test");
		bug.put(BugzillaConstants.CF_BUG_FIXED_ON,"Test");
		bug.put(BugzillaConstants.CF_WORKFLOW,"Test");
		bug.put(BugzillaConstants.CF_BUSINESS,"Test");
		bug.put(BugzillaConstants.CF_SUB_BRAND,"Test");
		bug.put(BugzillaConstants.CF_USERNAME,"Test");
		bug.put(BugzillaConstants.CF_SLA_TYPE,"Test");
		bug.put(BugzillaConstants.CF_CODE_REVIEWER,"Test");
		bug.put(BugzillaConstants.CF_CODE_REVIEW_COMMENTS_GEN,"Test");
		bug.put(BugzillaConstants.CF_IS_REGRESSION,"Test");
		bug.put(BugzillaConstants.CF_FEATURE,"Test");
		bug.put(BugzillaConstants.CF_SITE_ID,"Test");
		bug.put(BugzillaConstants.CF_MEM_SITE_ACC_ID,"Test");
		bug.put(BugzillaConstants.CF_IS_AUTOMATION,"Test");
		bug.put(BugzillaConstants.CF_ETA,"Test");
		bug.put(BugzillaConstants.CF_RCA_CATEGORIES,"Test");
		bug.put(BugzillaConstants.CF_RCA_SUB_CATEGORIES,"Test");
		bug.put(BugzillaConstants.CF_ERRCATEGORY,"Test");
		bug.put(BugzillaConstants.CF_JSON_OUT,"Test");
		bug.put(BugzillaConstants.CF_ESCALATION_STATUS,"Test");
		
		bugArray[0] = bug;
		hash.put("bugs", bugArray);
		
		updatebug.setResultMap(hash);
		
		updatebug.getParameterMap();
	}

}