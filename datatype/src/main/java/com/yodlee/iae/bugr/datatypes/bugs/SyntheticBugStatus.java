package com.yodlee.iae.bugr.datatypes.bugs;

/**
 * @author KChandrarajan
 *
 */
public enum SyntheticBugStatus {
	ACTIVE(0), INACTIVE(2), INVALID(3), CLOSED(4);

	private int id;

	private SyntheticBugStatus(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}

}
