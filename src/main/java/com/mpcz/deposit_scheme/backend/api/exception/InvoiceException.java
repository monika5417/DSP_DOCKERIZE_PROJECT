package com.mpcz.deposit_scheme.backend.api.exception;

import com.mpcz.deposit_scheme.backend.api.response.Response;

public class InvoiceException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private Response response;
	
	
	
	public InvoiceException(Response response) {
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
