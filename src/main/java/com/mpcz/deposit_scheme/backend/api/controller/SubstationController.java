package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
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
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.SubDivision;
import com.mpcz.deposit_scheme.backend.api.domain.Substation;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.SubstationException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.SubstationForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.services.SubstationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "SubstationController", description = "Rest api for substation master")
@RestController
@RequestMapping(RestApiUrl.SUB_STATION_API)
public class SubstationController {

	Logger LOG = LoggerFactory.getLogger(SubstationController.class);

	@Autowired
	private SubstationService substationService;
	@Autowired
	private DistributionCenterService distributionCenterService;

	@ApiOperation(value = "Save substation detaiils", notes = "Pass substation name, code & DC id", response = Response.class, responseContainer = "List"
			, tags = RestApiUrl.SUB_STATION_API
			)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addSubstation(@RequestBody @Valid SubstationForm substationForm,
			BindingResult bindingResult, HttpServletResponse httpServletResponse) throws FormValidationException,
	InvalidAuthenticationException, SubstationException, DistributionCenterException {

		final String method = RestApiUrl.SUB_STATION_API + RestApiUrl.ADD_URL + " : addSubstation()";
		LOG.info(method);
		Response<Substation> response = null;

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

		final Substation substation = new Substation();

		substation.setSubStationName(substationForm.getSubStationName());
		substation.setSubStationCode(substationForm.getSubStationCode());

		DistributionCenter dc = distributionCenterService.findDistributionCenterById(substationForm.getDcId());
		substation.setSubstationDistributionCenter(dc);

		response = substationService.save(substation);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve specific substation", notes = "Pass substation id", response = Response.class, responseContainer = "List"//, tags = RestApiUrl.SUBSTATION_TAGS
			)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveSubstation(@PathVariable long id,
			HttpServletResponse httpServletResponse)
					throws FormValidationException, InvalidAuthenticationException, SubstationException {

		final String method = RestApiUrl.SUB_STATION_API + RestApiUrl.GET_URL + " : retrieveSubstation()";
		LOG.info(method);
		Response<Substation> response = substationService.findById(id);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}
	
	@ApiOperation(value = "Retrieve all sub substation", notes = "Fetach all sub substation", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUB_DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> retrieveAllSubDivisions(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SubstationException {

		final String method = RestApiUrl.SUB_DIVISION_API + RestApiUrl.GET_ALL_URL + " : retrieveAllSubDivisions()";
		LOG.info(method);
		Response<List<Substation>> response = substationService.findAll();
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	
	@ApiOperation(value = "Retrieve all Dc by District Id", notes = "Pass district id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_SUBSTAION_BY_DC_URL)
	public ResponseEntity<Response<?>> retrieveAllDCByDistrict(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException, SubstationException {

		final String method = RestApiUrl.SUB_STATION_API + RestApiUrl.GET_ALL_SUBSTAION_BY_DC_URL
				+ " : retrieveAllDCByDistrict()";
		LOG.info(method);
		Response<Substation> response = new Response<Substation>();
	List<Substation> substationList = substationService.findAllSubstationsByDC(id);
		response.setList(substationList);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}