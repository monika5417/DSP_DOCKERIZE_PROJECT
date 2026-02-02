package com.mpcz.deposit_scheme.backend.api.exception;

import com.mpcz.deposit_scheme.backend.api.response.Response;

public class OtpInvalidException extends Exception {
	private static final long serialVersionUID = 7718828512143293558L;

	private Response response;

	public OtpInvalidException(Response response) {

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
