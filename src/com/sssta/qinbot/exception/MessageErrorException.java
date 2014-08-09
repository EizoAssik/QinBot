package com.sssta.qinbot.exception;

public class MessageErrorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6748585258755425620L;
	private int errorCode;
	public MessageErrorException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
}
