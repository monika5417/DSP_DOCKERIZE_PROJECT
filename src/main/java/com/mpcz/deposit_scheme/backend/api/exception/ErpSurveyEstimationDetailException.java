/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.exception;

import com.mpcz.deposit_scheme.backend.api.response.Response;

/**
 * @author Monika Rajpoot
 * @since 05-Jan-2026
 * @description ErpSurveyEstimationDetailException.java class description
 */

public class ErpSurveyEstimationDetailException extends Exception {

	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public ErpSurveyEstimationDetailException(final Response response) {
		super(response.getMessage());
		this.response = response;
	}

}
