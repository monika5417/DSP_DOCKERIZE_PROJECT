package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
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
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.LoadRequested;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.LoadRequestedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "LoadRequestedController", description = "Rest api for LoadRequested Type.")
@RestController
@RequestMapping(RestApiUrl.LOAD_REQUESTED_API)
public class LoadRequestedController {

	Logger LOG = LoggerFactory.getLogger(ConsumerTypeController.class);

	@Autowired
	private LoadRequestedService loadRequestedService;

	@ApiOperation(value = "Retrieve All LoadRequested", notes = "Fetach all LoadRequested", response = Response.class, responseContainer = "List", tags = RestApiUrl.DOCUMENT_TYPE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<List<?>> retrieveAllDepartments(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DocumentTypeException {
		Response<LoadRequested> response = new Response<>();
		final String method = RestApiUrl.DOCUMENT_TYPE_API + RestApiUrl.GET_ALL_URL + " : retrieveAllDocuments()";
		LOG.info(method);
		List<LoadRequested> loadRequestedResponse = loadRequestedService.findAllLoadRequested();
		
		response.setList(loadRequestedResponse);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Inserted Successfully");	
		
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(loadRequestedResponse);
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
	@GetMapping(RestApiUrl.GET_lOAD_REQUESTED_BY_ID_URL)
	public ResponseEntity<Response<?>> retrieveAllDCByDistrict(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {

		final String method = RestApiUrl.DC_API + RestApiUrl.GET_ALL_DC_BY_DISTRICT_URL
				+ " : retrieveAllDCByDistrict()";
		LOG.info(method);
		Response<LoadRequested> response = new Response<LoadRequested>();
		LoadRequested distributionCenters = loadRequestedService.findById(id);
		response.setList(Arrays.asList(distributionCenters));
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	
}