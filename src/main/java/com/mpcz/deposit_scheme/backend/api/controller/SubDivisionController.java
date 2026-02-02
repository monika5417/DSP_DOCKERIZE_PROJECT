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
import com.mpcz.deposit_scheme.backend.api.domain.SubDivision;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.SubDivisionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "SubDivisionController", description = "Rest api for SubDivision master")
@RestController
@RequestMapping(RestApiUrl.SUB_DIVISION_API)
public class SubDivisionController {
	Logger LOG = LoggerFactory.getLogger(SubDivisionController.class);
	
	@Autowired
	private SubDivisionService subDivisionService;

//	@Autowired
//	private DivisionService divisionService;

	 

//	@ApiOperation(value = "Save sub division detaiils", notes = "Pass sub division name", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUB_DIVISION_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
//	@PostMapping(RestApiUrl.ADD_URL)
//	public ResponseEntity<Response<?>> addSubDivision(@RequestBody @Valid SubDivisionForm subDivisionForm,
//			BindingResult bindingResult, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, SubDivisionException, DivisionException {
//
//		final String method = RestApiUrl.SUB_DIVISION_API + RestApiUrl.ADD_URL + " : addSubDivision()";
//		LOG.info(method);
//		Response<SubDivision> response = null;
//
//		if (bindingResult.hasErrors()) {
//			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
//
//			bindingResult.getFieldErrors().stream().forEach(f -> {
//				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
//				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
//						f.getField() + ":" + f.getDefaultMessage());
//				errorList.add(error);
//			});
//			response = new Response<>();
//			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
//			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//			response.setError(errorList);
//			throw new FormValidationException(response);
//		} // end of form validation
//
//		final SubDivision subDivision = new SubDivision();
//		subDivision.setSubDivision(subDivisionForm.getSubDivision());
//		subDivision.setSubDivisionCode(subDivisionForm.getSubDivisionCode());
//
//		Division division = divisionService.findDivisionById(subDivisionForm.getDivisionId());
//		subDivision.setSubdivisionDivision(division);
//
//		response = subDivisionService.save(subDivision);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

//	@ApiOperation(value = "Delete sub division information", notes = "Pass sub division id", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUB_DIVISION_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_DELETION_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@DeleteMapping(RestApiUrl.DELETE_URL)
//	public ResponseEntity<Response<?>> removeSubDivision(@PathVariable long id, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, SubDivisionException {
//
//		final String method = RestApiUrl.SUB_DIVISION_API + RestApiUrl.DELETE_URL + " : removeSubDivision()";
//		LOG.info(method);
//		Response<SubDivision> response = null;
//
//		response = subDivisionService.delete(id);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

//	@ApiOperation(value = "Update sub division information", notes = "Pass sub division name & Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUB_DIVISION_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_UPDATED_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@PutMapping(RestApiUrl.UPDATE_URL)
//	public ResponseEntity<Response<?>> updateSubDivision(@RequestBody @Valid SubDivisionForm subDivisionForm,
//			BindingResult bindingResult, @PathVariable long id, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, SubDivisionException, DivisionException {
//
//		final String method = RestApiUrl.SUB_DIVISION_API + RestApiUrl.UPDATE_URL + " : updateSubDivision()";
//		LOG.info(method);
//		Response<SubDivision> response = null;
//
//		if (bindingResult.hasErrors()) {
//			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
//
//			bindingResult.getFieldErrors().stream().forEach(f -> {
//				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
//				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
//						f.getField() + ":" + f.getDefaultMessage());
//				errorList.add(error);
//			});
//			response = new Response<>();
//			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
//			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//			response.setError(errorList);
//			throw new FormValidationException(response);
//		} // end of form validation
//
//		final SubDivision subDivision = subDivisionService.findSubDivisionById(id);
//		subDivision.setSubDivision(subDivisionForm.getSubDivision());
//		subDivision.setSubDivisionCode(subDivisionForm.getSubDivisionCode());
//
//		Division division = divisionService.findDivisionById(subDivisionForm.getDivisionId());
//		subDivision.setSubdivisionDivision(division);
//
//		subDivision.setSubDivisionId(id);
//		response = subDivisionService.update(subDivision);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

//	@ApiOperation(value = "Retrieve specific sub division", notes = "Pass sub division id", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUB_DIVISION_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@GetMapping(RestApiUrl.GET_URL)
//	public ResponseEntity<Response<?>> retrieveSubDivision(@PathVariable long id,
//			HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, SubDivisionException {
//
//		final String method = RestApiUrl.SUB_DIVISION_API + RestApiUrl.GET_URL + " : retrieveSubDivision()";
//		LOG.info(method);
//		Response<SubDivision> response = subDivisionService.findById(id);
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//
//	}

//	@ApiOperation(value = "Retrieve all sub divisions", notes = "Fetach all sub divisions", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUB_DIVISION_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@GetMapping(RestApiUrl.GET_ALL_URL)
//	public ResponseEntity<Response<?>> retrieveAllSubDivisions(HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, SubDivisionException {
//
//		final String method = RestApiUrl.SUB_DIVISION_API + RestApiUrl.GET_ALL_URL + " : retrieveAllSubDivisions()";
//		LOG.info(method);
//		Response<List<SubDivision>> response = subDivisionService.findAll();
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

	@ApiOperation(value = "Retrieve All SubDivision by division", notes = "Pass division id", response = Response.class, responseContainer = "List", tags = RestApiUrl.SUB_DIVISION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_SUB_DIVISION_BY_DIVISION_URL)
	public ResponseEntity<Response<?>> retrieveAllSubDivisionByDivision(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SubDivisionException {

		final String method = RestApiUrl.SUB_DIVISION_API + RestApiUrl.GET_ALL_SUB_DIVISION_BY_DIVISION_URL
				+ " : retrieveAllSubDivisionByDivision()";
		LOG.info(method);
		Response<SubDivision> response = new Response<SubDivision>();
		List<SubDivision> subDivisions = subDivisionService.findAllSubDivisionByDivision(id);
		response.setList(subDivisions);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
