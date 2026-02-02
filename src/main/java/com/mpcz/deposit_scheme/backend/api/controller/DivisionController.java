package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DivisionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "DivisionController", description = "Rest api for division master")
@RestController
@RequestMapping(RestApiUrl.DIVISION_API)
public class DivisionController {
	
	Logger LOG = LoggerFactory.getLogger(DivisionController.class);
	
	
	@Autowired
	private DivisionService divisionService;
	/*
	@Autowired
	private CircleService circleService;

 
	
	@ApiOperation(value = "Save division detaiils", notes = "Pass division name & Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addDivision(@RequestBody @Valid DivisionForm divisionForm,
			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DivisionException, CircleException {

		final String method = RestApiUrl.DIVISION_API + RestApiUrl.ADD_URL + " : addDivision()";
		LOG.info(method);
		Response<Division> response = null;

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

		final Division division = new Division();

		Circle circle = circleService.findCircleById(divisionForm.getCircleId());
		division.setDivisionCircle(circle);
		
		
		division.setDivision(divisionForm.getDivision());
		division.setDivisionCode(divisionForm.getDivisionCode());
		division.setBillCycCode("");
		
		division.setGroupAStatus(divisionForm.isGroupA());
		division.setGroupBStatus(divisionForm.isGroupB());
		division.setGroupCStatus(divisionForm.isGroupC());
		division.setGroupDStatus(divisionForm.isGroupD());
		division.setGroupEStatus(divisionForm.isGroupE());
		if (divisionForm.isGroupA()) {
			division.setGroupA("A");
		}
		if (divisionForm.isGroupB()) {
			division.setGroupA("B");
		}
		if (divisionForm.isGroupC()) {
			division.setGroupA("C");
		}
		if (divisionForm.isGroupD()) {
			division.setGroupA("D");
		}
		if (divisionForm.isGroupE()) {
			division.setGroupA("E");
		}
		

		response = divisionService.save(division);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Delete division information", notes = "Pass division Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_DELETION_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@DeleteMapping(RestApiUrl.DELETE_URL)
	public ResponseEntity<Response<?>> removeDivision(@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DivisionException {

		final String method = RestApiUrl.DIVISION_API + RestApiUrl.DELETE_URL + " : removeDivision()";
		LOG.info(method);
		Response<Division> response = null;

		response = divisionService.delete(id);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Update division information", notes = "Pass division name & id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_UPDATED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PutMapping(RestApiUrl.UPDATE_URL)
	public ResponseEntity<Response<?>> updateDivision(@RequestBody @Valid DivisionForm divisionForm,
			BindingResult bindingResult, @PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DivisionException, CircleException {

		final String method = RestApiUrl.DIVISION_API + RestApiUrl.UPDATE_URL + " : updateDivision()";
		LOG.info(method);
		Response<Division> response = null;

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

		final Division division = divisionService.findDivisionById(id);

		Circle circle = circleService.findCircleById(divisionForm.getCircleId());
		division.setDivisionCircle(circle);
		division.setDivision(divisionForm.getDivision());
		division.setDivisionCode(divisionForm.getDivisionCode());
		division.setBillCycCode("");
		division.setGroupAStatus(divisionForm.isGroupA());
		division.setGroupBStatus(divisionForm.isGroupB());
		division.setGroupCStatus(divisionForm.isGroupC());
		division.setGroupDStatus(divisionForm.isGroupD());
		division.setGroupEStatus(divisionForm.isGroupE());
		
		if (divisionForm.isGroupA()) {
			division.setGroupA("Group A");
		}
		if (divisionForm.isGroupB()) {
			division.setGroupA("Group B");
		}
		if (divisionForm.isGroupC()) {
			division.setGroupA("Group C");
		}
		if (divisionForm.isGroupD()) {
			division.setGroupA("Group D");
		}
		if (divisionForm.isGroupE()) {
			division.setGroupA("Group E");
		}

		response = divisionService.update(division);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve specific division", notes = "Pass division id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveDivision(@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DivisionException {

		final String method = RestApiUrl.DIVISION_API + RestApiUrl.GET_URL + " : retrieveDivision()";
		LOG.info(method);
		Response<Division> response = divisionService.findById(id);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve all divisions", notes = "Fetach all divisions", response = Response.class, responseContainer = "List", tags = RestApiUrl.DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> retrieveAllDivisions(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DivisionException {

		final String method = RestApiUrl.DIVISION_API + RestApiUrl.GET_ALL_URL + " : retrieveAllDivisions()";
		LOG.info(method);
		Response<List<Division>> response = divisionService.findAll();
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}*/

	@ApiOperation(value = "Retrieve All divison by circle", notes = "Pass circle id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DIVISION_BY_CIRCLE_URL)
	public ResponseEntity<Response<?>> retrieveAllDivisionByCircle(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DivisionException {

		final String method = RestApiUrl.DIVISION_API + RestApiUrl.GET_ALL_DIVISION_BY_CIRCLE_URL
				+ " : retrieveAllDivisionByCircle()";
		LOG.info(method);
		Response<Division> response = new Response<Division>();
		List<Division> divisions = divisionService.findAllDivisionsByCircle(id);
		response.setList(divisions);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	/*
	@ApiOperation(value = "Retrieve all group number by division", notes = "Pass division id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_GROUP_NO_BY_DIVISION_URL)
	public ResponseEntity<Response<?>> retrieveAllGroupsByDivision(@PathVariable long id,
			HttpServletResponse httpServletResponse) throws InvalidAuthenticationException, DivisionException, GroupNotFoundException {

		final String method = RestApiUrl.DIVISION_API + RestApiUrl.GET_ALL_GROUP_NO_BY_DIVISION_URL
				+ " : retrieveAllGroupsByDivision()";
		LOG.info(method);
		Response<GroupNumberDTO> response = new Response<>();
		
		List<GroupNumberDTO> groupList = divisionService.findAllGroupNumbersByDivisionId(id);
		response.setList(groupList);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}*/
}
