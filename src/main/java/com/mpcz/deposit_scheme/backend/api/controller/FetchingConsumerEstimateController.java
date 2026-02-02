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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.DocumentType;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerSupervisionResponseDto;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.SubstationException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerSupervisionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "FetchingConsumerEstimateController", description = "Rest api for FetchingSupervisionConsumerEstimate")
@RestController
@RequestMapping(RestApiUrl.COSNUMER_ESTIMATION_API)
public class FetchingConsumerEstimateController {

	Logger LOG = LoggerFactory.getLogger(FetchingConsumerEstimateController.class);

	@Autowired
	private ConsumerSupervisionService consumerSupervisionService;

	@ApiOperation(value = "Retrieve all ConsumerEstimate", notes = "Fetach all ConsumerEstimate", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_ESTIMATION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<ConsumerSupervisionResponseDto>> retrieveConsumerEstimate(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SubstationException, DemandDetailException, SchemeTypeException {

		final String method = RestApiUrl.COSNUMER_ESTIMATION_API + RestApiUrl.GET_ALL_URL + " : retrieveAllConsumerEstimate()";
		LOG.info(method);
		
		Response<ConsumerSupervisionResponseDto> response = new Response<>();
//	 List<Demand> consumerApplicationEstimateResponse = demandService.findConsumerApplicationEstimate();
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(consumerApplicationEstimateResponse);
		List<ConsumerSupervisionResponseDto> consumerApplicationEstimateResponse =consumerSupervisionService.findConsumerApplicationEstimate();
		
	
		response.setList(consumerApplicationEstimateResponse);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Fetch Successfully");

		//return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	}
