package com.tescotills.order.exception;

public class Error {

	protected final String errorId;
	protected final String errorText;

	public Error(String errorId, String errorText) {
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
