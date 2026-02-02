package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ChargeRateMaster;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationDetailsFilterDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailForm;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailSurveyForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.PageConsumerApplicationDetailDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationHeadChargesService;
import com.mpcz.deposit_scheme.backend.api.services.ChargeRateMasterService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationSurveyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "UserApplicationDetailController", description = "Rest api for User Application Detail.")
@RestController
@RequestMapping(RestApiUrl.USER_APPLICATION_DETAIL_API)
public class UserApplicationDetailController {

	Logger LOG = LoggerFactory.getLogger(UserApplicationDetailController.class);

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	ChargeRateMasterService chargeRateMasterService;

	@Autowired
	ApplicationHeadChargesService applicationHeadChargesService; 

	@Autowired
	private ConsumerApplicationSurveyService consumerApplicationSurveyService;

	@ApiOperation(value = "Retrieve User Application List with Pagination", notes = "Fetch all User Application List", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_PAGINATE_URL)
	public ResponseEntity<Response<?>> getAllUserWiseConsumerApplicationPagination(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			ConsumerApplicationDetailException, PaymentTypeException {

		System.out.println("getAllUserWiseConsumerApplicationPagination !!!!!");

		final String method = RestApiUrl.USER_APPLICATION_DETAIL_API + RestApiUrl.GET_ALL_PAGINATE_URL
				+ " : getAllUserWiseConsumerApplicationPagination()";
		LOG.info(method);

		Response<PageConsumerApplicationDetailDTO> response = new Response<>();

		String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

		ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();

		filterDTO.setUserLoginId(userLoginId);

		response = consumerApplicationDetailService.findAllConsumerApplicationDetailByApplicationStatusPagination(page,
				size, filterDTO);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve specific consumerApplication", notes = "Pass consumer Application  id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveUser(@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException {

		final String method = RestApiUrl.SIGNUP_TAGS + RestApiUrl.GET_URL + " : retrieveUser()";
		LOG.info(method);
		Response<ConsumerApplicationDetail> response = new Response();

		ConsumerApplicationDetail ConsumerApplicationDetail = consumerApplicationDetailService
				.findByConsumerApplicationId(id);

		response.setList(Arrays.asList(ConsumerApplicationDetail));
		response.setCode(HttpCode.OK);
		response.setMessage("Records retrieved Successfully !");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	
	@ApiOperation(value = "Retrieve Consumer Application List with Pagination", notes = "Fetch all Consumer Application List", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_PAGINATE_NEW_URL)
	public ResponseEntity<Response<?>> getAllConsumerApplicationNewPagination(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam("filterType") String filterType,
			@RequestParam("filterValue") String filterValue,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException {

		System.out.println("getAllConsumerApplicationNewPagination !!!!!");

		final String method = RestApiUrl.USER_APPLICATION_DETAIL_API+ RestApiUrl.GET_ALL_PAGINATE_URL
				+ " : getAllConsumerApplicationNewPagination()";
		LOG.info(method);

		Response<PageConsumerApplicationDetailDTO> response = new Response<>();

		String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println(userLoginId);
		ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();

		filterDTO.setUserLoginId(userLoginId);

		response = consumerApplicationDetailService.findAllConsumerApplicationDetailNewPagination(page, size, filterDTO, filterType, filterValue);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}
}
