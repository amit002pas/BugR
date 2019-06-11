package com.yodlee.iae.bugr.services.utilities;

/**
 * @author Sanyam Jain
 */
public final class EncryptionUtils {

	private final static String BUG_ANALYSER_ID_PREFIX = "BAID-";
	private final static String BUG_ANALYSER_ID_SUFFIX = "-BGCR"; 

	public static String decryptBugAnalyserId(String bugAnalyserId) {
		if (!ValidationUtility.isNullOrEmpty(bugAnalyserId)) {
			bugAnalyserId = bugAnalyserId.replace(BUG_ANALYSER_ID_PREFIX, "");
			bugAnalyserId = bugAnalyserId.replace(BUG_ANALYSER_ID_SUFFIX, ""); 
		}
		return bugAnalyserId;
	}

	public static String encryptBugAnalyserId(String bugAnalyserId) {
		if (!ValidationUtility.isNullOrEmpty(bugAnalyserId)) {
			bugAnalyserId = BUG_ANALYSER_ID_PREFIX + bugAnalyserId + BUG_ANALYSER_ID_SUFFIX;
		}
		return bugAnalyserId;
	}
}
