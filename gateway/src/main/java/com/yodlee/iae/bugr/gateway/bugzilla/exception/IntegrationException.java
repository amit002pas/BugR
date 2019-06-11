package com.yodlee.iae.bugr.gateway.bugzilla.exception;

/**
 * @author KChandrarajan
 *
 */
public class IntegrationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntegrationException() {
		super();
	}

	public IntegrationException(Throwable e) {
		super(e);
	}

	public IntegrationException(String message) {
		super(message);
	}

	public IntegrationException(String message, Throwable e) {
		super(message, e);
	}

}
