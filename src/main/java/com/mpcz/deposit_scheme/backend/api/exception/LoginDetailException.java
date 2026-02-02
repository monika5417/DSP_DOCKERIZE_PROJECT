package com.mpcz.deposit_scheme.backend.api.exception;

import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * 
 * @author Aditya Vays
 * @version 1.0
 */

public class LoginDetailException extends Exception{

	private static final long serialVersionUID = 1L;
	private Response response;
	
	
	
	public LoginDetailException(Response response) {
		super();
		this.response = response;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
}