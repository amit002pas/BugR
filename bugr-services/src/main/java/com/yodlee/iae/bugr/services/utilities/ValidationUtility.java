/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.utilities;

/**
 * @author Sanyam Jain
 *
 */
public final class ValidationUtility {

	/**
	 * 
	 */
	private ValidationUtility() {
		//
	}

	public static boolean isNullOrEmpty(Object obj) {
		return obj == null || obj.toString().isEmpty();
	}

	/*public static boolean isBugzillaError(String error) {
		boolean isBugzillaError = false;
		if (error != null && (error.contains(ResponseConstants.ERRORCODE_CONNECTIONEXCEPTION)
				|| error.contains(ResponseConstants.ERRORCODE_BUGZILLAEXCEPTION)
				|| error.contains(ResponseConstants.ERRORCODE_SQLEXCEPTION)
				|| error.contains(ResponseConstants.ERRORCODE_CLASSNOTFOUNDEXCEPTION))) {
			isBugzillaError = true;
		}
		return isBugzillaError;
	}*/
}
