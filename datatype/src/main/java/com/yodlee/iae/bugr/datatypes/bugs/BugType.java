package com.yodlee.iae.bugr.datatypes.bugs;

/**
 * @author KChandrarajan
 *
 */
public enum BugType {
	BUGZILLA(0), SYNTHETIC(1);

	private int id;

	private BugType(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}

}
