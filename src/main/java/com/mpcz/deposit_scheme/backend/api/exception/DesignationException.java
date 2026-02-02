package com.mpcz.deposit_scheme.backend.api.exception;

import com.mpcz.deposit_scheme.backend.api.response.Response;

/**
 * 
 * 
 * @author Aditya
 * @version 1.0
 * @since 2019-08-21
 *
 */

public class DesignationException extends Exception {

	private static final long serialVersionUID = 1L;
	private Response response;
	
	public DesignationException(Response response) {
		super(response.getMessage());
		this.response = response;
	}
	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	
}
