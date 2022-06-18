package com.tescotills.order.exception;

public class InvalidPaymentDetailsException extends Exception{

	private static final long serialVersionUID = 1L;

	protected final String errorId;
	protected final String errorText;
	
	public InvalidPaymentDetailsException(String errorId, String errorText) {
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
