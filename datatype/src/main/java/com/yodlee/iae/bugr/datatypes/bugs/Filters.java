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
 * @author KChandrarajan
 *
 */
@Named
public @Data class Filters {
	private String keyword;
	private String whiteboard;
	private String summary;
	private String siteid;
	private Portfolio sourceProduct;
	private Criteria criteria;
	private List<String> agentNames;
	private List<String> errorCodes;
}
