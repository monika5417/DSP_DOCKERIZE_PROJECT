
package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.dto.ApplicationStatusDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationStatusException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ApplicationStatusController", description = "Rest Api retated to Application Status related api's..!!")
@RestController
@RequestMapping(RestApiUrl.APPLICATION_STATUS_API)
public class ApplicationStatusController {

	Logger LOG = LoggerFactory.getLogger(ApplicationStatusController.class);

	@Autowired
	private ApplicationStatusService appliactionStatusService;

//	**************************charitra ,21-09-2022, start*******************************888
	@ApiOperation(value = "Save Application Status details", notes = "Pass Application Status Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.APPLICATION_STATUS_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addApplicationStatus(@RequestBody ApplicationStatusDTO applicationStatusDto,
			BindingResult bindingResult) throws FormValidationException, ApplicationStatusException {

		System.out.println("addApplicationStatus !!!!!");

		final String method = RestApiUrl.APPLICATION_STATUS_API + RestApiUrl.ADD_URL + " : addApplicationStatus()";
		LOG.info(method);

		Response<ApplicationStatus> response = new Response<>();

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

		final ApplicationStatus applicationStatus = new ApplicationStatus();
		final ModelMapper mapper = new ModelMapper();
		mapper.map(applicationStatusDto, applicationStatus);
		Response<ApplicationStatus> appStatus = appliactionStatusService.save(applicationStatus);

//	    response.setList(Arrays.asList(appStatus));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Update Application Status details", notes = "Pass Application Status Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.APPLICATION_STATUS_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@PutMapping(RestApiUrl.UPDATE_URL)
	public ResponseEntity<Response<?>> updateApplicationStatus(@RequestBody ApplicationStatusDTO applicationStatusDto,
			@PathVariable long id, BindingResult bindingResult)
			throws FormValidationException, ApplicationStatusException {

		System.out.println("updateApplicationStatus !!!!!");

		final String method = RestApiUrl.APPLICATION_STATUS_API + RestApiUrl.ADD_URL + " : updateApplicationStatus()";
		LOG.info(method);

		Response<ApplicationStatus> response = new Response<>();

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

		Response<ApplicationStatus> appStatus = appliactionStatusService.update(applicationStatusDto, id);

//		appliactionStatusService.update(applicationStatus);

//		response.setList(Arrays.asList(appStatus));
//		response.setCode(HttpCode.OK);
//		response.setMessage("Record Updated Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(appStatus);
	}

//	**************************charitra ,21-09-2022, end*******************************888

	@ApiOperation(value = "find applicationStatus by Id", response = ApplicationStatus.class)
	@GetMapping("/{applicationStatusId}")
	public ResponseEntity<Response<?>> findById(@PathVariable("applicationStatusId") Long applicationStatusId,
			BindingResult bindingResult) throws FormValidationException {

		Response<ApplicationStatus> response = new Response<>();

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

		ApplicationStatus appStatus = appliactionStatusService.findById(applicationStatusId);

		response.setList(Arrays.asList(appStatus));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Find Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}
//	**************************charitra ,21-09-2022, start*******************************888

	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> findAll() throws FormValidationException, ApplicationStatusException {

		System.out.println("findAll !!!!!");

		final String method = RestApiUrl.APPLICATION_STATUS_API + RestApiUrl.ADD_URL + " : findAll()";
		LOG.info(method);
		Response<List<ApplicationStatus>> response = new Response<>();
		Response<List<ApplicationStatus>> findAll = appliactionStatusService.findAll();

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findAll);
	}

//	**************************charitra ,21-09-2022, end*******************************888
}