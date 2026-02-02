package com.mpcz.deposit_scheme.backend.api.exception;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public class SubstationException extends Exception{

	private static final long serialVersionUID = 1L;
	private Response response;

	public SubstationException(final Response response) {

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