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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDcCorrection;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDcCorrectionException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDcCorrectionForm;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailSurveyForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDcCorrectionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ConsumerApplicationDcCorrectionController", description = "Rest api for Consumer Application Dc change Controller.")
@RestController
@RequestMapping(RestApiUrl.USER_APPLICATION_DC_CHANGE_API)
public class ConsumerApplicationDcCorrectionController {

	Logger logger = LoggerFactory.getLogger(ConsumerApplicationDcCorrectionController.class);

	@Autowired
	ConsumerApplicationDcCorrectionService consumerApplicationDcCorrectionService;

	@ApiOperation(value = "Save Consumer Dc change ", notes = "Pass Consumer Dc Change data", response = Response.class, responseContainer = "List", tags = RestApiUrl.USER_APPLICATION_DC_CHANGE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(value = RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addConsumerApplicationDcCorrection(
			@Valid @RequestBody ConsumerApplicationDcCorrectionForm consumerApplicationDcCorrectionForm,

			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDcCorrectionException,
			ConsumerApplicationDetailException, DistributionCenterException, ConsumerApplicationSurveyException {

		final String method = RestApiUrl.USER_APPLICATION_DC_CHANGE_API + RestApiUrl.ADD_URL
				+ " : addConsumerApplicationDcCorrection()";

		Response<ConsumerApplicationDcCorrection> response = new Response<>();

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				logger.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
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
		ConsumerApplicationDcCorrection consumerApplicationDcCorrection = consumerApplicationDcCorrectionService
				.saveForm(consumerApplicationDcCorrectionForm);
	
		if (consumerApplicationDcCorrection.getDcChangedReason()
				.equals("An existing survey entry already exists for this Consumer Application Detail")) {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("An existing survey entry already exists for this Consumer Application Detail.");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		if (consumerApplicationDcCorrection.getDcChangedReason().equals("DC changed")) {
			response.setCode(HttpCode.OK);
			response.setMessage("DC changed");
			response.setList(Arrays.asList(consumerApplicationDcCorrection) );

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		if (consumerApplicationDcCorrection.getDcChangedReason().equals("Application Revert Back to 36 Status")) {
			response.setCode(HttpCode.CREATED);
			response.setMessage("Application Revert To Applicant End");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		response.setList(Arrays.asList(consumerApplicationDcCorrection));
		response.setCode(HttpCode.CREATED);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
