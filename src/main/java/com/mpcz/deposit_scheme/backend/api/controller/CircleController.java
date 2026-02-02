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
import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.CircleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "CircleController", description = "Rest api for circle master")
@RestController
@RequestMapping(RestApiUrl.CIRCLE_API)
public class CircleController {
	
	Logger LOG = LoggerFactory.getLogger(CircleController.class);
	
	@Autowired
	private CircleService circleService;
//	@Autowired
//	private RegionService regionService;
//	@Autowired
//	private EntityManager entityManager;

//	@Autowired
//	ActivityLogService activityLogService;

//	 

//	@ApiOperation(value = "Save circle detaiils", notes = "Pass circle name & region id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
//	@PostMapping(RestApiUrl.ADD_URL)
//	public ResponseEntity<Response<?>> addCircle(@RequestBody @Valid CircleForm circleForm, BindingResult bindingResult,
//			HttpServletResponse httpServletResponse, HttpServletRequest request) throws FormValidationException,
//			InvalidAuthenticationException, ActivityLogException, CircleException, RegionException {
//
//		final String method = RestApiUrl.CIRCLE_API + RestApiUrl.ADD_URL + " : addCircle()";
//		LOG.info(method);
//		Response<Circle> response = null;
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
//		final Circle circle = new Circle();
//		
//		String n=SecurityContextHolder.getContext().getAuthentication().getName();
//		
//		System.out.println("___________________________________________________________"+n);
////		
////		System.out.println("Value: "+request.getAttribute("idValue"));
////		circle.setCreatedBy(Long.parseLong(request.getAttribute("idValue").toString()));
////		circle.setUpdatedBy(Long.parseLong(request.getAttribute("idValue").toString()));
////		
////		System.out.println("___________________________________________________________");
//
//		circle.setCircle(circleForm.getCircle());
//		circle.setCircleCode(circleForm.getCircleCode());
//
//		Region region = regionService.findRegionById(circleForm.getRegionId());
//		circle.setCircleRegion(region);
//
//		response = circleService.save(circle);
//		
//
//		ActivityLog activityLog = new ActivityLog();
//		activityLog.setActivityName("City");
//		activityLog.setActivityDescription("City Master Save data");
//		activityLog.setDbTableName(JPATableUtil.getTableName(entityManager, City.class));
//		activityLog.setBeforeUpdateData("NA");
//		activityLog.setAfterUpdateData("NA");
//
//		activityLogService.saveActivityLog(request, activityLog);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

//	@ApiOperation(value = "Delete circle information", notes = "Pass circle id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_DELETION_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@DeleteMapping(RestApiUrl.DELETE_URL)
//	public ResponseEntity<Response<?>> removeCircle(@PathVariable long id, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, CircleException {
//
//		final String method = RestApiUrl.CIRCLE_API + RestApiUrl.DELETE_URL + " : removeCircle()";
//		LOG.info(method);
//		Response<Circle> response = circleService.delete(id);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

//	@ApiOperation(value = "Update circle information", notes = "Pass circle name, circleId & regionId", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
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
//	public ResponseEntity<Response<?>> updateCircle(@RequestBody @Valid CircleForm circleForm,
//			BindingResult bindingResult, @PathVariable long id, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, CircleException, RegionException {
//
//		final String method = RestApiUrl.CIRCLE_API + RestApiUrl.UPDATE_URL + " : updateCircle()";
//		LOG.info(method);
//		Response<Circle> response = null;
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
//		Circle circle = circleService.findCircleById(id);
//
//		circle.setCircle(circleForm.getCircle());
//		circle.setCircleCode(circleForm.getCircleCode());
//
//		Region region = regionService.findRegionById(circleForm.getRegionId());
//		circle.setCircleRegion(region);
//
//		circle.setCircleId(id);
//
//		response = circleService.update(circle);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}
//
//	@ApiOperation(value = "Retrieve specific circle", notes = "Pass circle id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@GetMapping(RestApiUrl.GET_URL)
//	public ResponseEntity<Response<?>> retrieveCircle(@PathVariable long id, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, CircleException {
//
//		final String method = RestApiUrl.CIRCLE_API + RestApiUrl.GET_URL + " : retrieveCircle()";
//		LOG.info(method);
//		Response<Circle> response = circleService.findById(id);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//
//	}

//	@ApiOperation(value = "Retrieve all circles", notes = "Fetach all circles", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@GetMapping(RestApiUrl.GET_ALL_URL)
//	public ResponseEntity<Response<?>> retrieveAllCircles(HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, CircleException {
//
//		final String method = RestApiUrl.CIRCLE_API + RestApiUrl.GET_ALL_URL + " : retrieveAllCircles()";
//		LOG.info(method);
//		Response<List<Circle>> response =new Response<>();
//		try {
//			response = circleService.findAll();
//		} catch (JpaObjectRetrievalFailureException e) {
//			System.out.println("500 Error code: "+e.getMessage());
//			LOG.info("Error in Response: "+e.getMessage());
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage("Error: ");
//		}
//		
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

	@ApiOperation(value = "Retrieve All circles by region", notes = "Pass region id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_CIRCLE_BY_REGION_URL)
	public ResponseEntity<Response<?>> retrieveAllCirclesByRegion(@PathVariable long id,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException, CircleException {

		final String method = RestApiUrl.CIRCLE_API + RestApiUrl.GET_ALL_CIRCLE_BY_REGION_URL
				+ " : retrieveAllCirclesByRegion()";
		LOG.info(method);
		Response<Circle> response = new Response<Circle>();
		List<Circle> circles = circleService.findAllCirclesByRegion(id);
		response.setList(circles);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
}
