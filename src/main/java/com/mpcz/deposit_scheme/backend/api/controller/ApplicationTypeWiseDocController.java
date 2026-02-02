package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationTypeWiseDoc;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeWiseDocException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.request.ApplicationTypeWiseDocForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationTypeWiseDocService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ApplicationTypeWiseDocController", description = "Rest api for Application Type Wise Doc.")
@RestController
@RequestMapping(RestApiUrl.APPLICATION_TYPE_WISE_DOC_API)
public class ApplicationTypeWiseDocController {

	Logger LOG = LoggerFactory.getLogger(ApplicationTypeWiseDocController.class);

	@Autowired
	private ApplicationTypeWiseDocService applicationTypeWiseDocService;

	@ApiOperation(value = "Save Application Type Wise Doc details", notes = "Pass Application Type Id and Document Type Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.APPLICATION_TYPE_WISE_DOC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addApplicationTypeWiseDoc(
			@RequestBody @Valid ApplicationTypeWiseDocForm applicationTypeWiseDocForm,

			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationTypeWiseDocException,
			ApplicationTypeException, DocumentTypeException {

		System.out.println("addApplicationTypeWiseDoc !!!!!");

		final String method = RestApiUrl.APPLICATION_TYPE_WISE_DOC_API + RestApiUrl.ADD_URL
				+ " : addApplicationTypeWiseDoc()";
		LOG.info(method);

		Response<ApplicationTypeWiseDoc> response = new Response<>();

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});
			response = new Response<>();
			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			throw new FormValidationException(response);
		} // end of form validation

		ApplicationTypeWiseDoc applicationTypeWiseDoc = applicationTypeWiseDocService
				.saveApplicationTypeWiseDoc(applicationTypeWiseDocForm);

		response.setList(Arrays.asList(applicationTypeWiseDoc));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve Specific Application Type Wise Doc", notes = "Pass Application Type Wise Doc Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.APPLICATION_TYPE_WISE_DOC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveApplicationTypeWiseDoc(@PathVariable Long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationTypeWiseDocException {

		System.out.println("retrieveApplicationTypeWiseDoc !!!!!");

		final String method = RestApiUrl.APPLICATION_TYPE_WISE_DOC_API + RestApiUrl.GET_URL
				+ " : retrieveApplicationTypeWiseDoc()";
		LOG.info(method);

		Response<ApplicationTypeWiseDoc> response = new Response<>();

		ApplicationTypeWiseDoc applicationTypeWiseDoc = applicationTypeWiseDocService
				.findByApplicationTypeWiseDocId(id);

		response.setList(Arrays.asList(applicationTypeWiseDoc));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve all Application Type Wise Doc", notes = "Fetch all Application Type Wise Doc", response = Response.class, responseContainer = "List", tags = RestApiUrl.APPLICATION_TYPE_WISE_DOC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> retrieveAllApplicationTypeWiseDoc(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationTypeWiseDocException {

		System.out.println("retrieveAllApplicationTypeWiseDoc !!!!!");

		final String method = RestApiUrl.APPLICATION_TYPE_WISE_DOC_API + RestApiUrl.GET_ALL_URL
				+ " : retrieveAllApplicationTypeWiseDoc()";
		LOG.info(method);

		Response<ApplicationTypeWiseDoc> response = new Response<>();

		List<ApplicationTypeWiseDoc> applicationTypeWiseDoc = applicationTypeWiseDocService
				.findAllApplicationTypeWiseDoc();

		response.setList(applicationTypeWiseDoc);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
