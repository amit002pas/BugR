/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.resources.mongo;

import java.util.Date;

import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.StatusProgress;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;

import lombok.Data;

public @Data class SyntheticFields {

	private Portfolio portfolio;

	private Integer bugzillaBugId;
	private boolean isBugzillaBugCreated;
	private SyntheticBugStatus syntheticBugStatus;
	private String failureMessage;

	private Date updatedAt;

	private String segmentId;
	private String action;

	private String cusip;
	private String symbol;

	private String apiKey;
	private StatusProgress statusProgress; 
	private String jnAnalysisId;

}
