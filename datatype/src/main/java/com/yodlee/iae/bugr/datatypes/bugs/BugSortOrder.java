package com.yodlee.iae.bugr.datatypes.bugs;

/**
 * @author KChandrarajan
 *
 */
public enum BugSortOrder {
	ASCENDING(0), DESCENDING(1);

	private int id;

	private BugSortOrder(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}

}
