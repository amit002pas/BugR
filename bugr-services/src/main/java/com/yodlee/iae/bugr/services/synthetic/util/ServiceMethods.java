/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.util;

import java.util.HashMap;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.StatusProgress;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.validation.ServiceValidationConstants;
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

public class ServiceMethods {

	public static HashMap<Boolean, String> validateUpdate(BugEntity bugEntity, SyntheticBug sBug) {
		StatusProgress sBugSP = sBug.getSyntheticFields().getStatusProgress();
		StatusProgress bugEntitySP = bugEntity.getProgressStatus();
		HashMap<Boolean, String> map = new HashMap<>();

		if (!ValidationUtility.isNullOrEmpty(bugEntitySP)) {
			/*if (sBugSP == bugEntitySP) {
				map.put(false, ServiceValidationConstants.UPDATE_SAME_STATE);
			}*/
			if (!ValidationUtility.isNullOrEmpty(sBugSP)) {
				if (sBugSP.equals(StatusProgress.SUCCESS) && bugEntitySP.equals(StatusProgress.INPROGRESS)) {
					map.put(false, ServiceValidationConstants.UPDATE_SUCCESS_TO_INPROGRESS);
				}
				if (sBugSP.equals(StatusProgress.SUCCESS) && bugEntitySP.equals(StatusProgress.FAILURE)) {
					map.put(false, ServiceValidationConstants.UPDATE_SUCCESS_TO_FAILURE);
				}
				if (sBugSP.equals(StatusProgress.FAILURE) && bugEntitySP.equals(StatusProgress.SUCCESS)) {
					map.put(false, ServiceValidationConstants.UPDATE_FAILURE_TO_SUCCESS);
				}
			}
			else if(ValidationUtility.isNullOrEmpty(sBugSP) && (bugEntitySP.equals(StatusProgress.SUCCESS)||bugEntitySP.equals(StatusProgress.FAILURE))) {
					map.put(false, ServiceValidationConstants.UPDATE_EMPTY_TO_SUCCESS_OR_FAILURE);
				}
		}
		return map;
	}
}
