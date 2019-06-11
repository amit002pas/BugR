package com.yodlee.iae.bugr.datatypes.bugs;

/**
 * @author KChandrarajan
 *
 */
public enum ResponseStatus {
	SUCCESS(0), FAILURE(1);

	private int id;

	private ResponseStatus(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}

}
