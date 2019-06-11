package com.yodlee.iae.bugr.services.synthetic.exception;

/**
 * @author KChandrarajan
 *
 */
public class SyntheticException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	/**
	 * Parameterized constructor to set message
	 * 
	 * @param error
	 */
	SyntheticException(String error) {
		this.message = error;
	}

	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * Sets the detail message string of the throwable.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
