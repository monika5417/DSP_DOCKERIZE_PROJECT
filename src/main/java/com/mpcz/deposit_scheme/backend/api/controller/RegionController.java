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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.Discom;
import com.mpcz.deposit_scheme.backend.api.domain.Region;
import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.RegionException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.RegionForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DiscomService;
import com.mpcz.deposit_scheme.backend.api.services.RegionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "RegionController", description = "Rest Api for Region Master")
@RestController
@RequestMapping(RestApiUrl.REGION_API)
public class RegionController {
	Logger LOG = LoggerFactory.getLogger(RegionController.class);

	@Autowired
	private RegionService regionServices;
	@Autowired
	private DiscomService discomService;

	 

	@ApiOperation(value = "Save region information", notes = "Pass region information", response = Response.class, responseContainer = "List", tags = RestApiUrl.REGION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addRegion(@RequestBody @Valid RegionForm regionForm, BindingResult bindingResult,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, RegionException, DiscomException {

		final String method = RestApiUrl.REGION_API + RestApiUrl.ADD_URL + "  : addRegion()";
		LOG.info(method);
		Response<Region> response = null;

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

		final Region region = new Region();
		region.setRegion(regionForm.getRegion());
		region.setRegionCode(regionForm.getRegionCode());

		Discom discom = discomService.findDiscomById(regionForm.getDiscomId());
		region.setRegionDiscom(discom);

		response = regionServices.save(region);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Delete region information", notes = "Pass region Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.REGION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_DELETION_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@DeleteMapping(RestApiUrl.DELETE_URL)
	public ResponseEntity<Response<?>> removeRegion(@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, RegionException {

		final String method = RestApiUrl.REGION_API + RestApiUrl.DELETE_URL + "  : removeRegion()";
		LOG.info(method);
		Response<Region> response = null;

		response = regionServices.delete(id);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Update region information", notes = "Pass region information & id ", response = Response.class, responseContainer = "List", tags = RestApiUrl.REGION_TAGS)
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
	public ResponseEntity<Response<?>> updateRegion(@RequestBody @Valid RegionForm regionForm,
			BindingResult bindingResult, @PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, RegionException, DiscomException {

		final String method = RestApiUrl.REGION_API + RestApiUrl.UPDATE_URL + "  : updateRegion()";
		LOG.info(method);
		Response<Region> response = null;

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

		final Region region = regionServices.findRegionById(id);

		region.setRegion(regionForm.getRegion());
		region.setRegionCode(regionForm.getRegionCode());

		Discom discom = discomService.findDiscomById(regionForm.getDiscomId());
		region.setRegionDiscom(discom);
		region.setRegionId(id);
		response = regionServices.update(region);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve specific region", notes = "Pass region id", response = Response.class, responseContainer = "List", tags = RestApiUrl.REGION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveRegion(@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, RegionException {

		final String method = RestApiUrl.REGION_API + RestApiUrl.GET_URL + "  : retrieveRegion()";
		LOG.info(method);
		Response<Region> response = regionServices.findById(id);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve all regions", notes = "Fetch all regions", response = Response.class, responseContainer = "List", tags = RestApiUrl.REGION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> retrieveAllRegion(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, RegionException {

		final String method = RestApiUrl.REGION_API + RestApiUrl.GET_ALL_URL + "  : retrieveAllRegion()";
		LOG.info(method);
		Response<List<Region>> response = regionServices.findAll();
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all regions by discom ", notes = "Pass discom id", response = Response.class, responseContainer = "List", tags = RestApiUrl.REGION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_REGION_BY_DISCOM_URL)
	public ResponseEntity<Response<?>> retrieveAllRegionsByDiscom(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, RegionException {

		final String method = RestApiUrl.REGION_API + RestApiUrl.GET_ALL_REGION_BY_DISCOM_URL
				+ " : retrieveAllRegionsByDiscom()";
		LOG.info(method);
		Response<Region> response = new Response<Region>();
		List<Region> regions = regionServices.findAllRegionsByDiscom(id);
		response.setList(regions);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
