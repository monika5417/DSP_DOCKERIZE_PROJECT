package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerSignUpForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ConsumerSignupController", description = "Rest api for Consumer Sign-up.")
@RestController
@RequestMapping(RestApiUrl.CONSUMER_SIGNUP_API)
//@RequestMapping(RestApiUrl.CONSUMER_ACC_BASE_API)
public class ConsumerSignupController {

	Logger LOG = LoggerFactory.getLogger(ConsumerSignupController.class);

//	@Autowired
//	MessageProperties messageProperties;

	@Autowired
	ConsumerService consumerService;

//	@Autowired
//	SMSDirectService smsDirectService;

	@ApiOperation(value = "For Consumer Registration.", notes = "Pass Consumer Basic details.", response = Response.class, responseContainer = "List", tags = RestApiUrl.SIGNUP_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(value = RestApiUrl.SIGNUP_URL, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })

	public ResponseEntity<Response<?>> consumerSignUp(
			@RequestBody @Valid ConsumerSignUpForm consumerSignUpForm,
			//@RequestPart("consumerSignUpForm") String consumerSignUpFormString,
//			@RequestPart("docElectricityBill") MultipartFile docElectricityBill,
//			@RequestPart("docResidentialProof") MultipartFile docResidentialProof,
			//@RequestPart("docAadhar") MultipartFile docAadhar, @RequestPart("docPan") MultipartFile docPan,
			BindingResult bindingResult) throws Exception {

		System.out.println("consumerSignUp !!!!!");

		final String method = RestApiUrl.CONSUMER_SIGNUP_API + RestApiUrl.SIGNUP_URL + " : consumerSignUp()";
		LOG.info(method);

		Response<Consumer> response = new Response<>();

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {

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

		

		Consumer consumer = null;
		try {
			//ConsumerSignUpForm consumerSignUpForm = ConsumerSignUpForm.stringToJson(consumerSignUpFormString);

			consumer = consumerService.saveConsumer(consumerSignUpForm /* docElectricityBill, docResidentialProof, */
					/*docAadhar, docPan*/);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setList(Arrays.asList(consumer));
		response.setCode(HttpCode.CREATED);
		response.setMessage("Record Inserted Successfully");

//		final SMSRequest smsRequest = new SMSRequest();
//
//		final String defaultConsumerPassword = Utility.getPassword(8);
//		smsRequest.setText(MessageFormat.format(messageProperties.getDefaultPasswordMessage(),
//				new Object[] { consumerSignUpForm.getConsumerName(), defaultConsumerPassword }));
//		smsRequest.setMobileNo(consumerSignUpForm.getConsumerMobileNo());
//		smsRequest.setTemplateId(messageProperties.getPasswordTemplateId());

//		final String password = PasswordUtil.getPasswordHash(defaultConsumerPassword);

//		consumer.setConsumerCredentials(password);

//		smsDirectService.sendMessage(smsRequest);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
