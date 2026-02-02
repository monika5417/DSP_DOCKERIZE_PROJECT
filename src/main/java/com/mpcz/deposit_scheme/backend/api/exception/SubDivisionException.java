package com.mpcz.deposit_scheme.backend.api.exception;

import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * 
 * @author Ashish Tiwari
 * @version 1.0
 * @since 27-08-2019
 */

public class SubDivisionException extends Exception{

	private static final long serialVersionUID = 1L;
	private Response response;
	
	
	
	public SubDivisionException(Response response) {
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
