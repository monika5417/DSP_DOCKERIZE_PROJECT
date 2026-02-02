package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
import com.mpcz.deposit_scheme.backend.api.domain.DynamicQuery;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.repository.DynamicQueryRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "DistributionCenterController", description = "Rest api for distribution center master")
@RestController
@RequestMapping(RestApiUrl.DC_API)
public class DistributionCenterController {

	Logger LOG = LoggerFactory.getLogger(DistributionCenterController.class);

	@Autowired
	private DistributionCenterService dcService;

	@Autowired
	private DynamicQueryRepository dynamicQueryRepository;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//	@Autowired
//	private SubDivisionService subDivisionService;

//	@ApiOperation(value = "Save distribution center detaiils", notes = "Pass sub-division id, dc name &  code", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
//	@PostMapping(RestApiUrl.ADD_URL)
//	public ResponseEntity<Response<?>> addDistributionCenter(
//			@RequestBody @Valid DistributionCenterForm distributionCenterForm, BindingResult bindingResult,
//			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
//			DistributionCenterException, SubDivisionException {
//
//		final String method = RestApiUrl.DC_API + RestApiUrl.ADD_URL + " : addDistributionCenter()";
//		LOG.info(method);
//		Response<DistributionCenter> response = null;
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
//		final DistributionCenter distributionCenter = new DistributionCenter();
//
//		distributionCenter.setDcName(distributionCenterForm.getDcName());
//		distributionCenter.setDcCode(distributionCenterForm.getDcCode());
//
//		SubDivision subDivision = subDivisionService.findSubDivisionById(distributionCenterForm.getSubDivisionId());
//		distributionCenter.setDcSubdivision(subDivision);
//
//		response = dcService.save(distributionCenter);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}
//
//	@ApiOperation(value = "Delete distribution center information", notes = "Pass dc id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_DELETION_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@DeleteMapping(RestApiUrl.DELETE_URL)
//	public ResponseEntity<Response<?>> removeDistributionCenter(@PathVariable long id,
//			HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {
//
//		final String method = RestApiUrl.DC_API + RestApiUrl.DELETE_URL + " : removeDistributionCenter()";
//		LOG.info(method);
//		Response<DistributionCenter> response = null;
//
//		response = dcService.delete(id);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

//	@ApiOperation(value = "Update distribution center information", notes = "Pass subdivison id, dc name, code & Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
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
//	public ResponseEntity<Response<?>> updateDistributionCenter(
//			@RequestBody @Valid DistributionCenterForm distributionCenterForm, BindingResult bindingResult,
//			@PathVariable long id, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException, SubDivisionException {
//
//		final String method = RestApiUrl.DC_API + RestApiUrl.UPDATE_URL + " : updateDistributionCenter()";
//		LOG.info(method);
//		Response<DistributionCenter> response = null;
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
//		final DistributionCenter distributionCenter = dcService.findDistributionCenterById(id);
//		distributionCenter.setDcName(distributionCenterForm.getDcName());
//		distributionCenter.setDcCode(distributionCenterForm.getDcCode());
//
//		SubDivision subDivision = subDivisionService.findSubDivisionById(distributionCenterForm.getSubDivisionId());
//		distributionCenter.setDcSubdivision(subDivision);
//
//		response = dcService.update(distributionCenter);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

	@ApiOperation(value = "Retrieve specific distribution center", notes = "Pass dc id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveDistributionCenter(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {

		final String method = RestApiUrl.DC_API + RestApiUrl.GET_URL + " : retrieveDistributionCenterById()";
		LOG.info(method);
		Response<DistributionCenter> response = dcService.findById(id);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

//	@ApiOperation(value = "Retrieve all distribution denters", notes = "Fetach all distribution denters", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@GetMapping(RestApiUrl.GET_ALL_URL)
//	public ResponseEntity<Response<?>> retrieveAllDistributionCenters(HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {
//
//		final String method = RestApiUrl.DC_API + RestApiUrl.GET_ALL_URL + " : retrieveAllDistributionCenters()";
//		LOG.info(method);
//		Response<List<DistributionCenter>> response = dcService.findAll();
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

	@ApiOperation(value = "Retrieve all distribution center by subdivison", notes = "Pass subdivison id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DC_BY_SUBDIVISION_URL)
	public ResponseEntity<Response<?>> retrieveAllDCBySubdivison(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {

		final String method = RestApiUrl.DC_API + RestApiUrl.GET_ALL_DC_BY_SUBDIVISION_URL
				+ " : retrieveAllDCBySubdivison()";
		LOG.info(method);
		Response<DistributionCenter> response = new Response<DistributionCenter>();
		List<DistributionCenter> distributionCenters = dcService.findAllDistributionCentersBySubDivision(id);
		response.setList(distributionCenters);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
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
	@GetMapping(RestApiUrl.GET_ALL_DC_BY_DISTRICT_URL)
	public ResponseEntity<Response<?>> retrieveAllDCByDistrict(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {

		final String method = RestApiUrl.DC_API + RestApiUrl.GET_ALL_DC_BY_DISTRICT_URL
				+ " : retrieveAllDCByDistrict()";
		LOG.info(method);
		Response<DistributionCenter> response = new Response<DistributionCenter>();
		List<DistributionCenter> distributionCenters = dcService.findAllDistributionCentersByDistrict(id);
		response.setList(distributionCenters);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Dc by division Id", notes = "Pass division  id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping("/findAllDistributionCentersBydcIdAnddcSubdivision_subDivisionIdAndSubdivisionDivision_divisionId/{id}")
	public ResponseEntity<Response<?>> findAllDistributionCentersBydcIdAnddcSubdivision_subDivisionIdAndSubdivisionDivision_divisionId(
			@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {

		final String method = RestApiUrl.DC_API
				+ "/findAllDistributionCentersBydcIdAnddcSubdivision_subDivisionIdAndSubdivisionDivision_divisionId";
		LOG.info(method);
		Response<DistributionCenter> response = new Response<DistributionCenter>();
		List<DistributionCenter> distributionCenters = dcService
				.findAllDistributionCentersBydcIdAnddcSubdivision_subDivisionIdAndSubdivisionDivision_divisionId(id);
		response.setList(distributionCenters);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getDcDataByNgbDCCode/{ngbDcCode}")
	public ResponseEntity<?> getDcDataByNgbDCCode(@PathVariable String ngbDcCode) {

		DynamicQuery byQueryName = dynamicQueryRepository.findByQueryName("DC_DATA_BY_NGB_DC_CODE");
		if (byQueryName == null) {
			return ResponseEntity.ok(new Response<>(HttpCode.NULL_OBJECT, "Query Not Found In Database"));
		}

		String queryText = byQueryName.getQueryText();
		Map<String, String> map = new HashedMap();
		map.put("ngbDcCode", ngbDcCode);
		List<Map<String, Object>> queryForList = namedParameterJdbcTemplate.queryForList(queryText, map);
		return ResponseEntity.ok(queryForList == null || queryForList.isEmpty()
				? new Response<>(HttpCode.NULL_OBJECT, "Data Not Found For this Ngb Id : " + ngbDcCode)
				: new Response<>(HttpCode.OK, "Data Retrieved Successfully", queryForList));
	}

}
