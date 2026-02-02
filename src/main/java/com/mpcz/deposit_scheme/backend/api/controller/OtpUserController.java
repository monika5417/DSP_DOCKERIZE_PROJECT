package com.mpcz.deposit_scheme.backend.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ConstantProperty;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.Otp;
import com.mpcz.deposit_scheme.backend.api.domain.OtpId;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.OtpInvalidException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerLoginOtpForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.LoginOtpForm;
import com.mpcz.deposit_scheme.backend.api.request.OtpForm;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.request.UserLoginOtpForm;
import com.mpcz.deposit_scheme.backend.api.response.OTPResponse;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerLoginTokenService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.OtpService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.services.UserLoginTokenService;
import com.mpcz.deposit_scheme.backend.api.services.UserService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
import com.mpcz.deposit_scheme.backend.api.utility.Utility;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/user/otp")
@Api(value = "OtpController", description = "Rest Api retated to otp,sms,message etc..!!")
public class OtpUserController {

	static final Logger LOG = LoggerFactory.getLogger(OtpUserController.class);

	@Autowired
	UserService userService;

	@Autowired
	OtpService otpService;

	@Autowired
	UserLoginTokenService userLoginTokenService;

	@Autowired
	SMSDirectService smsDirectService;

	@Autowired
	MessageProperties messageProperties;

	// Generation OTP with Mobile No. verification

	@PostMapping("/getOtp")
	public ResponseEntity<Response<OTPResponse>> getOtp(@RequestBody @Valid OtpForm otpForm,
			BindingResult bindingResult) throws DataNotFoundException, Exception {

		System.out.println("getOtp method is calling !!!");

		final String method = "OtpController/getOtp()";
   
		LOG.info(method);

		Response<OTPResponse> response = new Response<OTPResponse>();
		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
			LOG.error("Error in validation");

			bindingResult.getFieldErrors().stream().forEach(f -> {
				LOG.error("Error in validation" + f.getField() + ": " + f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);

			});
			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + response);
			throw new FormValidationException(response);
		}

		userService.findByMobileNo(otpForm.getMobileNo());

		OtpId OtpIdSearch = new OtpId(otpForm.getMobileNo(), otpForm.getSource());
		Optional<Otp> otp = otpService.findByOtpId(OtpIdSearch);
		OTPResponse otpResponse = new OTPResponse();
		otpResponse.setMobileNo(otpForm.getMobileNo());
		if (!otp.isPresent()) {
			char[] otpVal = Utility.generateOTP(5);
			Otp otpNew = new Otp();
			OtpId otpIdNew = new OtpId(otpForm.getMobileNo(), otpForm.getSource());
			otpNew.setOtpId(otpIdNew);
			otpNew.setCreated(new Timestamp(System.currentTimeMillis()));
			otpNew.setOtpVal(String.valueOf(otpVal));
			otpService.save(otpNew);
			response = getOTPReponse(otpResponse);
			getSMSMessage(otpForm.getMobileNo(), String.valueOf(otpVal));

		} else {
			Timestamp tStamp = new Timestamp(System.currentTimeMillis());
			long diff = tStamp.getTime() - otp.get().getCreated().getTime();
			char[] otpVal = Utility.generateOTP(5);
			long diffMinutes = diff / (60 * 1000); // in minutes

			if (diffMinutes > ConstantProperty.OTP_TIME_LIMIT.intValue()) {
				Otp otpNew = new Otp();
				OtpId otpIdNew = new OtpId(otpForm.getMobileNo(), otpForm.getSource());
				otpNew.setOtpId(otpIdNew);
				otpNew.setCreated(new Timestamp(System.currentTimeMillis()));
				otpNew.setOtpVal(String.valueOf(otpVal));
				otpService.save(otpNew);
				getSMSMessage(otpForm.getMobileNo(), String.valueOf(otpVal));
				response = getOTPReponse(otpResponse);
			} else {
				getSMSMessage(otpForm.getMobileNo(), otp.get().getOtpVal());
				response = getOTPReponse(otpResponse);
			}
		}

		return ResponseEntity.ok().header("Content-Type", "application/json").body(response);
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping(value = "/verifyLoginOtp")
	public ResponseEntity<Response> verifyLoginOtp(@RequestBody @Valid UserLoginOtpForm userLoginOtpForm,
			BindingResult bindingResult) throws Exception {

		System.out.println("verifyLoginOtp method is call !!!");

		LOG.error("Inside  verifyOtpFor ..........." + userLoginOtpForm);

		Response response = new Response();
		userLoginOtpForm.setSource("Otp-login");

		// validate login time 3 minutes

		User user = userService.findByUserId(userLoginOtpForm.getUserLoginId());

		if (user == null) {
			LOG.error("Invalid UserLoginId : ");
			response = new Response<Consumer>();
			response.setCode(ResponseCode.INVALID_USER_ID);
			response.setMessage(ResponseMessage.INVALID_USER_ID);
			throw new InvalidAuthenticationException(response);
		}
		OtpId OtpIdSearch = new OtpId(user.getMobileNo(), userLoginOtpForm.getSource());
		Optional<Otp> otpDb = otpService.findByOtpId(OtpIdSearch);

		if (otpDb.isPresent() && otpDb.get().getOtpVal() != null
				&& otpDb.get().getOtpVal().equals(userLoginOtpForm.getOtp())) {

			// login otp match
			String token = userLoginTokenService.isLoginTokenValid(userLoginOtpForm.getTokenId());

			
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.LOGIN_SUCCESSFULLY);
            response.setList(Arrays.asList(user));
            
			final HttpHeaders headers = new HttpHeaders();
			headers.set(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON);
			headers.set("authorization", token);
			headers.set("Access-Control-Expose-Headers", "authorization");

			return ResponseEntity.ok().headers(headers).body(response);

		} else {
			response.setCode(ResponseCode.INVALID_OTP_FAIL);
			response.setMessage("Invalid OTP");
			throw new OtpInvalidException(response);
		}
	}

	private void getSMSMessage(String mobile, String otpVal) throws Exception {

		System.out.println("getSMSMessage method is calling !!!");

		SMSRequest smsRequest = new SMSRequest();

		smsRequest.setMessageType("OTP");
		smsRequest.setText(Integer.valueOf(otpVal).toString());

		smsRequest.setMobileNo(mobile);
		smsRequest.setTemplateId(messageProperties.getOtpTemplateId());
		smsDirectService.sendMessage(smsRequest);
	}

	private Response<OTPResponse> getOTPReponse(OTPResponse otpResponse) {
		Response<OTPResponse> response = new Response<OTPResponse>();
		List<OTPResponse> responseList = new ArrayList<OTPResponse>();
		responseList.add(otpResponse);
		response.setCode(ResponseCode.OK);
		response.setMessage(ResponseMessage.SUCCESS);
		response.setList(responseList);
		return response;
	}
	
	// Generation OTP with Mobile No. verification
			@PostMapping("/getLoginOtp")
			public ResponseEntity<Response<OTPResponse>> getLoginOtp(@RequestBody @Valid LoginOtpForm loginOtpForm,
					BindingResult bindingResult) throws DataNotFoundException, Exception {
				final String method = "OtpController/getLoginOtp()";
				LOG.info(method);
				Response<OTPResponse> response = new Response<OTPResponse>();
//				if (bindingResult.hasErrors()) {
//					List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
//					LOG.error("Error in validation");
	//
//					bindingResult.getFieldErrors().stream().forEach(f -> {
//						LOG.error("Error in validation" + f.getField() + ": " + f.getDefaultMessage());
//						ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
//								f.getField() + ":" + f.getDefaultMessage());
//						errorList.add(error);
	//
//					});
//					response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
//					response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//					response.setError(errorList);
//					LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + response);
//					throw new FormValidationException(response);
//				}

				// validate login time 3 minutes
				// if valid then increase time in user table

				/*
				 * loginOtpForm.setSource("Otp-login"); User loginUser =
				 * userService.findByUserId(loginOtpForm.getUserId());
				 * 
				 * OtpId OtpIdSearch = new OtpId(loginUser.getMobileNo(),
				 * loginOtpForm.getSource()); Optional<Otp> otp =
				 * otpService.findByOtpId(OtpIdSearch); OTPResponse otpResponse = new
				 * OTPResponse(); otpResponse.setMobile(loginUser.getMobileNo()); if
				 * (!otp.isPresent()) { char[] otpVal = Utility.generateOTP(5); Otp otpNew = new
				 * Otp(); OtpId otpIdNew = new OtpId(loginUser.getMobileNo(),
				 * loginOtpForm.getSource()); otpNew.setOtpId(otpIdNew); otpNew.setCreated(new
				 * Timestamp(System.currentTimeMillis()));
				 * otpNew.setOtpVal(String.valueOf(otpVal)); otpService.save(otpNew); response =
				 * getOTPReponse(otpResponse); getLoginOtpSMSMessage(loginUser.getMobileNo(),
				 * String.valueOf(otpVal));
				 * 
				 * } else { Timestamp tStamp = new Timestamp(System.currentTimeMillis()); long
				 * diff = tStamp.getTime() - otp.get().getCreated().getTime(); char[] otpVal =
				 * Utility.generateOTP(5); long diffMinutes = diff / (60 * 1000); // in minutes
				 * 
				 * if (diffMinutes > ConstantProperty.OTP_TIME_LIMIT.intValue()) { Otp otpNew =
				 * new Otp(); OtpId otpIdNew = new OtpId(loginUser.getMobileNo(),
				 * loginOtpForm.getSource()); otpNew.setOtpId(otpIdNew); otpNew.setCreated(new
				 * Timestamp(System.currentTimeMillis()));
				 * otpNew.setOtpVal(String.valueOf(otpVal)); otpService.save(otpNew);
				 * getLoginOtpSMSMessage(loginUser.getMobileNo(), String.valueOf(otpVal));
				 * response = getOTPReponse(otpResponse); } else {
				 * getLoginOtpSMSMessage(loginUser.getMobileNo(), otp.get().getOtpVal());
				 * response = getOTPReponse(otpResponse); } } // }
				 */
				response = otpService.getUserLoginOtp(loginOtpForm.getUserId(), loginOtpForm.getTokenId());
				return ResponseEntity.ok().header("Content-Type", "application/json").body(response);
			}


}
