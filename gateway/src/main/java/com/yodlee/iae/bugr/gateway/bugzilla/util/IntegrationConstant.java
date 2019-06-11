package com.yodlee.iae.bugr.gateway.bugzilla.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author KChandrarajan
 *
 */
public class IntegrationConstant {

	/**
	 * 
	 */
	private IntegrationConstant() {
	}

	public static final String BUGZILLA_SERVICE_DOWN = "Bugzilla Service Is Down !";
	public static final String BUGZILLA_HOST = "https://blrbugzilla.yodlee.com";
	public static final String BUGZILLA_BUG_URL = "https://blrbugzilla.yodlee.com/show_bug.cgi?id=";
	public static final String BUGZILLA_LOGIN_KEY = "Bugzilla_login";
	public static final String BUGZILLA_PASSWORD_KEY = "Bugzilla_password";
	public static final String BUGZILLA_LOGIN_SUMBIT = "log_in";

	public static final String COMMENT = "comment";
	public static String BUG_ID = "id";

	public static final String BUG_SUMMARY_FIELD = "summary";
	public static final String BUG_STATUS = "bug_status";
	public static final String BUG_STATUS_RESOLVED = "RESOLVED";
	public static final String BUG_RESOLUTION = "resolution";
	public static final String BUG_RESOLUTION_INVALID = "INVALID";
	public static final String BUG_STATUS_WHILTE_BOARD = "status_whiteboard";
	public static final String BUG_STATUS_WHILTE_BOARD_IAT_TESTING_INVALID = "IAT, IAT_TESTING_INVALID";
	public static final String BUG_SUMMARY_IAT_TESTING_INVALID = "IAT_TESTING_INVALID";
	public static final String BUG_IAT_TESTING_COMMENT = "Invalidating the bug for IAT testing purposes";
	public static final String BUG_SUMMARY = "IAT_TESTING_INVALID";
	public static final String BUG_WORKFLOW_STATUS = "cf_workflow_status";
	public static final String BUG_WORKFLOW_STATUS_CLOSED = "Closed";
	public static final String BUG_AGENT_STATUS = "cf_agent_status";
	public static final String BUG_AGENT_STATUS_ALREADY_SUPPORTED = "Already Supported";
	public static final String BUG_AGENT_TYPE = "cf_agent_type";
	public static final String BUG_AGENT_TYPE_FULL_AGENT = "Full Agent";
	public static final String BUG_RCA_CATEGORIES = "cf_rca_categories";
	public static final String BUG_RCA_CATEGORIES_IAE_NONE_OF_THE_ABOVE = "IAE - None Of The Above";
	public static final String BUG_IAE_RESOLUTION = "cf_iae_resolution";
	public static final String BUG_IAE_RESOLUTION_NO_FIX_FROM_IAE = "No Fix from IAE";
	public static final String BUG_COMMENT_TEXTAREA = "#blankText textarea";
	public static final String BUG_SUMBIT = "submitComments";
	public static final String JS_DISABLE_ALERT = "window.confirm = function(msg) { return true; }";
	protected static final Map<String, String> BUG_INVALIDATE_MAP = new LinkedHashMap<>();
	public static final String BUG_COMMENT = "comment";
	public static final String BUG_XMLRPC_SERVER_URL = "https://blrbugzilla.yodlee.com/xmlrpc.cgi";
	public static final String BUGZILLA_REMEMBER = "Bugzilla_remember";
	public static final String REMEMBERLOGIN = "rememberlogin";
	public static final String BUG_LOGIN_XMLRPC_KEY = "login";
	public static final String BUG_PASSWORD_XMLRPC_KEY = "password";
	public static final String BUG_METHOD_LOGIN = "User.login";
	public static final String BUG_METHOD_UPDATE = "Bug.update";
	public static final String QUERY_CODE_INS_ACCTS = "insuranceAccts";
	public static final String QUERY_CODE_CACHERUN_DISABLED_CHECK = "mtdSIIDet";
	public static final String QUERY_CODE_AGENT_NAME_FROM_SUMINFO = "mtdSiteDet";
	public static final String BUG_COMMENT_BODY = "body";
	public static final String BUG_COMMENT_IS_PRIVATE = "is_private";
	public static final String BUG_IDS = "ids";

}
