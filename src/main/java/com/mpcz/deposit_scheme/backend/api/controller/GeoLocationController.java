package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.GeoLocation;
import com.mpcz.deposit_scheme.backend.api.dto.GeoPendingStatusConsmersListResponse;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SubstationException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.GeoLocationForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.GeoLocationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "GeoLocationController", description = "Rest api for geo  location")
@RestController
@RequestMapping(RestApiUrl.GEO_TYPE_API)
public class GeoLocationController {

	Logger LOG = LoggerFactory.getLogger(GeoLocationController.class);

	@Autowired
	private GeoLocationService geoLocationService;
	
	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;
	
	@Autowired
	private ConsumerService consumerService;

	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addLocation(@ModelAttribute  GeoLocationForm geoLocationForm ,
			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws Exception {

		final String method = RestApiUrl.GEO_TYPE_API + RestApiUrl.ADD_URL + " : addLocation()";
		System.out.println(geoLocationForm.toString());
		System.out.println(geoLocationForm.getFile().getOriginalFilename());
		
		
		LOG.info(method);
		 Response<GeoLocation> response = new Response<>();

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
		
		GeoLocation geoLocation =new GeoLocation();
		ModelMapper modelMapper =new ModelMapper();
		modelMapper.map(geoLocationForm, geoLocation);
		
		
		GeoLocation geoLocation1 = geoLocationService.savelocation(geoLocation);
		List l=new ArrayList<>();
		l.add("data successful submit");

		response.setList(l);
			response.setCode(HttpCode.OK);
		response.setMessage("Record Inserted Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	
	@ApiOperation(value = "Retrieve Pending GeoLocation Status ConsumerApplicationList", notes = "Pass substation id", response = Response.class, responseContainer = "List"//, tags = RestApiUrl.SUBSTATION_TAGS
			)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping("/getCaptcheredDataGstNumber/{mobileNumber}")
	public ResponseEntity<Response<?>> retrieveGeoPendingStatusConsumerApplications(@PathVariable String mobileNumber,
			HttpServletResponse httpServletResponse)
					throws FormValidationException, InvalidAuthenticationException, SubstationException, ConsumerApplicationDetailException, GeoLocationException, ConsumerException {

		final String method = RestApiUrl.SUB_STATION_API + RestApiUrl.GET_URL + " : retrieveGeoPendingStatusConsumerApplications()";
		
		LOG.info(method);
		Response<GeoPendingStatusConsmersListResponse> response = new Response<>();
		
		if(mobileNumber==null) {
			
			throw new GeoLocationException(response);
		}
		Consumer consumer = consumerService.findByMobileNo(mobileNumber);
		if(consumer.getConsumerId()==null) {
			
			throw new ConsumerException(response);
			
		}
		List<GeoPendingStatusConsmersListResponse> geoPendingStatusConsmersListResponse = consumerApplicationDetailService.findConsumerApplicationDetailByConsumerId(consumer.getConsumerId());
		if(geoPendingStatusConsmersListResponse.isEmpty()) {
			response.setList(geoPendingStatusConsmersListResponse);
			response.setCode("201");
			response.setMessage("Record not found");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setList(geoPendingStatusConsmersListResponse);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Fetch Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		
	}
	
}