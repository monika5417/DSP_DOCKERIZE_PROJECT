package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.FeederException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ConsumerSignupController", description = "Rest api for Consumer Sign-up.")
@RestController
@RequestMapping(RestApiUrl.CONSUMER_FETCHING_API)
//@RequestMapping(RestApiUrl.CONSUMER_ACC_BASE_API)
public class consumerFetchingSupervisionDetaiForQclController {

	Logger LOG = LoggerFactory.getLogger(consumerFetchingSupervisionDetaiForQclController.class);
	
	@Autowired
	ConsumerApplicationDetailService consumerApplicationDetailService;
	
	@ApiOperation(value = "Retrieve all feeders", notes = "Fetach all feeders", response = Response.class, responseContainer = "List"
			 , tags = RestApiUrl.FEEDER_TAGS
			)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_CONSUMER_URL)
	public ResponseEntity<List<Map<String, String>>> retrieveAllFeeder(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, FeederException, ConsumerApplicationDetailException {

		// final String method = RestApiUrl.FEEDER_API + RestApiUrl.GET_ALL_URL + " : retrieveAllFeeder()";
		// LOG.info(method);
		List<Map<String, String>> findAllConsumerDetail = consumerApplicationDetailService.findAllConsumerDetail();
		
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findAllConsumerDetail);
	}

}
