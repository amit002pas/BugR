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
public enum Criteria {
	JUGGERNAUT_HARDERROR(0), JUGGERNAUT_DQERROR(1), JUGGERNAUT_NONSIMILAR(2);

	private int id;

	private Criteria(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}

}
