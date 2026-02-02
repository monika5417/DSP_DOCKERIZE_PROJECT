package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.Discom;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentHistory;
import com.mpcz.deposit_scheme.backend.api.domain.Region;
import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.exception.RegionException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.PaymentHistoryForm;
import com.mpcz.deposit_scheme.backend.api.request.RegionForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.PaymentHistoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(RestApiUrl.PAYMENT_HISTORY_API)
@Api(value = "PaymentHistoryController", description = "Rest Api retated to otp,sms,message etc..!!")
public class PaymentHistoryController {
	
	Logger LOG = LoggerFactory.getLogger(PaymentHistoryController.class) ;
	
	@Autowired
	private PaymentHistoryService paymentHistoryService;
	
	
	
	@ApiOperation(value = "Save payment histrory information", notes = "Pass region information", response = Response.class, responseContainer = "List", tags = RestApiUrl.REGION_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addRegion(@RequestBody @Valid PaymentHistoryForm paymentHistoryForm, BindingResult bindingResult,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, PaymentHistoryException {

		final String method = RestApiUrl.REGION_API + RestApiUrl.ADD_URL + "  : addRegion()";
		LOG.info(method);
		Response<PaymentHistory> response = null;

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

		
		final PaymentHistory paymentHistory =new PaymentHistory();
		 ModelMapper modelMapper = new ModelMapper();
		 modelMapper.map(paymentHistoryForm, paymentHistory);
		 System.out.println(paymentHistory);
		 
		 Response<PaymentHistory> history= paymentHistoryService.save(paymentHistory);
		 
		   
		 
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(history);
	}

	

}
