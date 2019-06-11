/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */ 
package com.yodlee.iae.bugr.datatypes.bugs;

/**
 * @author KChandrarajan
 *
 */
public enum BugSortBy {
	FAILURE(0), AGENT_NAME(1), ERROR_CODE(2),LAST_CHANGE_TIME(3);

	private int id;

	private BugSortBy(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}

}
