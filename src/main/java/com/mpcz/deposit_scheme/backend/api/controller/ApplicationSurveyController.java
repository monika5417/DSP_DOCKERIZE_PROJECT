package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailSurveyForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationSurveyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ApplicationSurveyController", description = "Rest api for Consumer Application Detail.")
@RestController
@RequestMapping(RestApiUrl.USER_APPLICATION_SURVEY_DETAIL_API)
public class ApplicationSurveyController {

	Logger logger = LoggerFactory.getLogger(ApplicationSurveyController.class);

	@Autowired
	ConsumerApplicationSurveyService consumerApplicationSurveyService;

	@ApiOperation(value = "Save Consumer Application Survey", notes = "Pass Consumer Application Survey Details", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(value = RestApiUrl.SUBMIT_SURVEY_URL, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })

	public ResponseEntity<Response<?>> submitConsumerApplicationSurveyDetail(
			@RequestPart("surveyForm") String consumerApplicationDetailSurveyFormString,
			@RequestPart("docSurvey") MultipartFile docSurvey,

			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException,
			DocumentTypeException, ConsumerApplicationSurveyException {

		System.out.println("addConsumerApplicationDetail !!!!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.SUBMIT_SURVEY_URL
				+ " : addConsumerApplicationDetail()";
		logger.info(method);

		Response<ConsumerApplicationSurvey> response = new Response<>();

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
		ConsumerApplicationSurvey ConsumerApplicationSurvey = null;

		ConsumerApplicationDetailSurveyForm consumerApplicationDetailForm = ConsumerApplicationDetailSurveyForm
				.stringToJson(consumerApplicationDetailSurveyFormString);

		ConsumerApplicationSurvey = consumerApplicationSurveyService
				.saveConsumerApplicationSurvey(consumerApplicationDetailForm, docSurvey);

		System.out.println(consumerApplicationDetailForm);

		response.setList(Arrays.asList(ConsumerApplicationSurvey));
		response.setCode(HttpCode.CREATED);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "update Consumer Application Survey", notes = "Pass Consumer Application Survey Details", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PutMapping(value = RestApiUrl.UPDATE_SURVEY_URL, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })

	public ResponseEntity<Response<?>> updateConsumerApplicationSurveyDetail(@PathVariable("id") Long id,
			@RequestPart("surveyForm") String consumerApplicationDetailSurveyFormString,
			@RequestPart("docSurvey") Optional<MultipartFile> docSurveyOptional,

			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException,
			DocumentTypeException, ConsumerApplicationSurveyException, PreviousStageDetailException {

		System.out.println("addConsumerApplicationDetail !!!!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.SUBMIT_SURVEY_URL
				+ " : addConsumerApplicationDetail()";
		logger.info(method);

		Response<ConsumerApplicationSurvey> response = new Response<>();

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
		ConsumerApplicationSurvey ConsumerApplicationSurvey = null;

		ConsumerApplicationDetailSurveyForm consumerApplicationDetailForm = ConsumerApplicationDetailSurveyForm
				.stringToJson(consumerApplicationDetailSurveyFormString);

		MultipartFile docSurvey = null;
		if (docSurveyOptional.isPresent()) {
			docSurvey = docSurveyOptional.get();
		}

		ConsumerApplicationSurvey = consumerApplicationSurveyService.updateConsumerApplicationSurvey(id,
				consumerApplicationDetailForm, docSurvey);

		System.out.println(consumerApplicationDetailForm);

		response.setList(Arrays.asList(ConsumerApplicationSurvey));
		response.setCode(HttpCode.CREATED);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve specific consumerApplication", notes = "Pass consumer Application  id", response = Response.class, responseContainer = "List", tags = RestApiUrl.USER_APPLICATION_SURVEY_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveSurveyDataBySurveyId(@PathVariable long id,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			ConsumerApplicationDetailException, ConsumerApplicationSurveyException {

		final String method = RestApiUrl.SIGNUP_TAGS + RestApiUrl.GET_URL + " : retrieveUser()";
		logger.info(method);
		Response<ConsumerApplicationSurvey> response = new Response();

		ConsumerApplicationSurvey consumerApplicationSurvey = consumerApplicationSurveyService
				.findByConsumersApplicationDetailConsumerApplicationId(id);

		response.setList(Arrays.asList(consumerApplicationSurvey));
		response.setCode(HttpCode.OK);
		response.setMessage("Records retrieved Successfully !");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

}
