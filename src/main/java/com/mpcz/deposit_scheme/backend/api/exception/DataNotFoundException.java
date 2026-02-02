/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.exception;

import com.mpcz.deposit_scheme.backend.api.response.Response;

/**
 * @author Amit
 *
 * 31-Aug-2020
 */
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Response<String> response;
	
	
	
	public DataNotFoundException(Response<String> response) {
		super();
		this.response = response;
	}
	
	
	public Response<String> getResponse() {
		return response;
	}
	public void setResponse(Response<String> response) {
		this.response = response;
	}
}
