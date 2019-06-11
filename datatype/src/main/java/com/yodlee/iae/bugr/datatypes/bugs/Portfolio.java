package com.yodlee.iae.bugr.datatypes.bugs;

/**
 * @author KChandrarajan
 *
 */
public enum Portfolio {
	PRE_SR(0), RECON_SERVICES(1), RECON_HOLDING(2), PRE_SR_JUGGERNAUT(3), PRE_SR_TTR(4);

	private int id;

	private Portfolio(int num) {
		this.id = num;
	}

	public int getId() {
		return id;
	}

}
