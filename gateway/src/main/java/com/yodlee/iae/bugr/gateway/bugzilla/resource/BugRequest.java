package com.yodlee.iae.bugr.gateway.bugzilla.resource;

import com.j2bugzilla.base.BugzillaMethod;

/**
 * @author KChandrarajan
 *
 */
public class BugRequest extends BaseRequest {

	private BugzillaMethod method;

	/**
	 * Get bug Request
	 * 
	 * @param method
	 * @param username
	 * @param password
	 */
	public BugRequest(BugzillaMethod method, String username, String password) {
		super(username, password);
		this.method = method;
	}

	/**
	 * Get Bugzilla method
	 * 
	 * @return
	 */
	public BugzillaMethod getMethod() {
		return method;
	}
}
