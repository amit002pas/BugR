package com.yodlee.iae.bugr.gateway.bugzilla.resource;

import com.j2bugzilla.base.BugzillaConnector;

/**
 * @author KChandrarajan
 *
 */
public abstract class BaseRequest {

	private String username;

	private String password;

	private BugzillaConnector connector;

	BaseRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BugzillaConnector getConnector() {
		return connector;
	}

	public void setConnector(BugzillaConnector connector) {
		this.connector = connector;
	}

}
