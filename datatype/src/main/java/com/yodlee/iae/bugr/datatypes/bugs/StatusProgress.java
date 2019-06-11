/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.datatypes.bugs;

public enum StatusProgress {

	INPROGRESS(0), SUCCESS(1), FAILURE(2),EMPTY(3);

	private int id;

	private StatusProgress(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}
}
