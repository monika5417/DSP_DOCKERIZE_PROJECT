package com.mpcz.deposit_scheme.backend.api.exception;


public class NatureOfWork14Exception extends RuntimeException {

	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NatureOfWork14Exception(String message) {
		super();
		this.message = message;
	}
	
	
}
