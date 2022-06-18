package com.tescotills.order.exception;

public class InvalidOrderException extends Exception{

	private static final long serialVersionUID = 1L;

	protected final String errorId;
	protected final String errorText;
	
	public InvalidOrderException(String errorId, String errorText) {
		this.errorId= errorId;
		this.errorText= errorText;
	}

	public String getErrorId() {
		return errorId;
	}

	public String getErrorText() {
		return errorText;
	}
	
}
