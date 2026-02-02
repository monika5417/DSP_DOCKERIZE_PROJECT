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
import com.mpcz.deposit_scheme.backend.api.domain.SupplyVoltage;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SupplyVoltageException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.SupplyVoltageForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.SupplyVoltageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "SupplyVoltageController", description = "Rest api for Supply Voltage.")
@RestController
@RequestMapping(RestApiUrl.SUPPLY_VOLTAGE_API)
public class SupplyVoltageController {

	Logger LOG = LoggerFactory.getLogger(SupplyVoltageController.class);

	@Autowired
	private SupplyVoltageService supplyVoltageService;

	@ApiOperation(value = "Save Supply Voltage details", notes = "Pass Supply Voltage Name & Supply Voltage Code", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUPPLY_VOLTAGE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addSupplyVoltage(@RequestBody @Valid SupplyVoltageForm supplyVoltageForm,

			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SupplyVoltageException {

		System.out.println("addSupplyVoltage !!!!!");

		final String method = RestApiUrl.SUPPLY_VOLTAGE_API + RestApiUrl.ADD_URL + " : addSupplyVoltage()";
		LOG.info(method);

		Response<SupplyVoltage> response = new Response<>();

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

		SupplyVoltage supplyVoltage = supplyVoltageService.saveSupplyVoltage(supplyVoltageForm);

		response.setList(Arrays.asList(supplyVoltage));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve Specific Supply Voltage", notes = "Pass Supply Voltage Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUPPLY_VOLTAGE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveSupplyVoltage(@PathVariable Long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SupplyVoltageException {

		System.out.println("retrieveSupplyVoltage !!!!!");

		final String method = RestApiUrl.SUPPLY_VOLTAGE_API + RestApiUrl.GET_URL + " : retrieveSupplyVoltage()";
		LOG.info(method);

		Response<SupplyVoltage> response = new Response<>();

		SupplyVoltage supplyVoltage = supplyVoltageService.findBySupplyVoltageId(id);

		response.setList(Arrays.asList(supplyVoltage));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve all Supply Voltage", notes = "Fetch all Active Supply Voltage", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUPPLY_VOLTAGE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> retrieveAllActiveSupplyVoltage(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SupplyVoltageException {

		System.out.println("retrieveAllActiveSupplyVoltage !!!!!");

		final String method = RestApiUrl.SUPPLY_VOLTAGE_API + RestApiUrl.GET_ALL_URL + " : retrieveAllActiveSupplyVoltage()";
		LOG.info(method);

		Response<SupplyVoltage> response = new Response<>();

		List<SupplyVoltage> supplyVoltage = supplyVoltageService.findAllSupplyVoltage();

		response.setList(supplyVoltage);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
