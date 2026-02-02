package com.mpcz.deposit_scheme.backend.api.constant;

/*
 * @author Sandeep Namdeo
 * @version 1.0
 * @since 1.0
 * 
 * Interface to manage Rest API Response Code.
 */

public interface ApiResponseCode {

	/*
	 * The request has succeeded.
	 */
	final int SUCCESS_CODE = 200;
	/*
	 * Form validation errors.
	 */
	final int FORM_VALIDATION_ERROR = 600;
	/*
	 * The server understood the request, but is refusing to fulfill it.
	 */
	final int FORBIDDEN_CODE = 403;
	/*
	 * The server has not found anything matching the Request-URI.
	 */
	final int NOT_FOUND_CODE = 404;
	/*
	 * The server is refusing to service the request because the entity of the
	 * request is in a format not supported by the requested resource for the
	 * requested method.
	 */
	final int UNSUPPORTED_MEDIA_TYPE_CODE = 415;
	/*
	 * The request could not be understood by the server due to malformed syntax.
	 * The client SHOULD NOT repeat the request without modifications.
	 */
	final int BAD_REQUEST_CODE = 400;
	/*
	 * The request requires user authentication.
	 */
	final int UNAUTHORIZED_CODE = 401;
	/*
	 * The server encountered an unexpected condition which prevented it from
	 * fulfilling the request.
	 */
	final int INTERNAL_SERVER_ERROR_CODE = 500;

	/*
	 * The server encountered an conflict or already exits record condition which
	 * prevented it from fulfilling the request.
	 */
	final int CONFLICT_CODE = 409;

}
