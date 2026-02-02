package com.mpcz.deposit_scheme.backend.api.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ConstantProperty;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.Captcha;
import com.mpcz.deposit_scheme.backend.api.domain.CheckConsumer;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerHistory;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginAttempts;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginHistory;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginToken;
import com.mpcz.deposit_scheme.backend.api.domain.Otp;
import com.mpcz.deposit_scheme.backend.api.domain.OtpId;
import com.mpcz.deposit_scheme.backend.api.exception.ChangePasswordException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.LoginDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.OtpInvalidException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtConsumer;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtTokenUtil;
import com.mpcz.deposit_scheme.backend.api.jwt.security.util.PasswordUtil;
import com.mpcz.deposit_scheme.backend.api.repository.CheckConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerLoginForm;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerLoginFormCaptcha;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerLoginOtpForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.OtpForm;
import com.mpcz.deposit_scheme.backend.api.request.PasswordVerificationForm;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.CaptchaResponse;
import com.mpcz.deposit_scheme.backend.api.response.OTPResponse;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.CaptchaService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerHistoryService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerLoginAttemptsService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerLoginHistoryService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerLoginTokenService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.OtpService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.util.RandomStringGenerator;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
import com.mpcz.deposit_scheme.backend.api.utility.Utility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ConsumerLoginController", description = "Rest Api retated to Consumer login related api's..!!")
@RestController
@RequestMapping(RestApiUrl.CONSUMER_LOGIN_API)
public class ConsumerLoginController {

	Logger LOG = LoggerFactory.getLogger(ConsumerLoginController.class);

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private CaptchaService captchaservice;

	@Autowired
	private ConsumerLoginHistoryService consumerLoginHistoryService;

	@Autowired
	private ConsumerLoginTokenService consumerLoginTokenService;

	@Autowired
	private ConsumerLoginAttemptsService consumerLoginAttemptsService;

	@Autowired
	private ConsumerHistoryService consumerHistoryService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	private SMSDirectService smsDirectService;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private CheckConsumerRepository checkConsumerRepository;

	@ApiOperation(value = "Get Captcha", notes = "Get Captcha", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@GetMapping(value = RestApiUrl.LOGIN_GET_CAPTCHA_URL)
	public ResponseEntity<Response> getImage() {

		System.out.println("Get Captcha - getImage() method calling!!!");

		Timestamp today = new java.sql.Timestamp(new java.util.Date().getTime());
		Response<?> response1 = new Response();
		try {
			String imgpath = resourceLoader.getResource("classpath:/static/images/FFFFFF.png").getURI().getPath();

			String fontpath = resourceLoader.getResource("classpath:/static/fonts/lucida.ttf").getURI().getPath();
			File file = new File(imgpath);
			File fontfile = new File(fontpath);
			InputStream inputStream = new FileInputStream(file);
			String captcha = RandomStringGenerator.getRandomString(5);

			Captcha c = new Captcha();
			c.setCaptchaText(captcha);
			System.out.println("captcha*** " + captcha);
			Long t = today.getTime();
			Date afterAdding10Mins = new Date(t + (9 * 60 * 1000));
			c.setCaptchaExpiredTime(afterAdding10Mins);
			c = captchaservice.save(c);

			byte[] img = mergeImageAndText(inputStream, captcha, new Point(16, 30), fontfile);

			CaptchaResponse r = new CaptchaResponse();

			String str = new String(Base64.encodeBase64(img), "UTF-8");
			r.setCaptchaId(c.getCaptchaId());
			r.setCaptchaImgStr(str);

			List responseList = new ArrayList<CaptchaResponse>();
			responseList.add(r);
			response1.setList(responseList);
			response1.setMessage("Captcha Generated Successfully");

		} catch (Exception ex) {
			LOG.error("Some error to show Captcha", ex);
		}
		return ResponseEntity.ok().header("Content-Type", "application/json").body(response1);

	}

	public static byte[] mergeImageAndText(InputStream imageFilePath, String text, Point textPosition, File fontfile)
			throws IOException, FontFormatException {

		System.out.println("mergeImageAndText method calling!!!");

		BufferedImage im = ImageIO.read(imageFilePath);
		Graphics2D g = (Graphics2D) im.getGraphics();
		g.setFont(g.getFont().deriveFont(35f));
		Color cl = new Color(208, 146, 55);
		g.setColor(cl);
		g.setFont(Font.getFont("lucida"));
		g.drawString(text, textPosition.x, textPosition.y);
		g.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, "png", baos);
		return baos.toByteArray();
	}

	@ApiOperation(value = "Get Consumer Verification Captcha at the time of Consumer Login", notes = "Pass Consumer Login Id, Password and Captcha", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.LOGIN_SUCCESSFULLY),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@PostMapping(RestApiUrl.LOGIN_VERIFICATION_CAPTCHA_URL)
	public ResponseEntity<Response<?>> consumerVerificationCaptcha(
			@RequestBody @Valid ConsumerLoginFormCaptcha consumerLoginFormCaptcha, BindingResult bindingResult,
			HttpServletResponse httpServletResponse, HttpServletRequest request)
			throws FormValidationException, InvalidAuthenticationException, LoginDetailException, DataNotFoundException,
			ConsumerException, OtpInvalidException {

		System.out.println("consumerVerificationCaptcha method calling!!!");

		final String method = RestApiUrl.CONSUMER_LOGIN_API + RestApiUrl.LOGIN_VERIFICATION_CAPTCHA_URL
				+ " : consumerVerificationCaptcha()";
		LOG.info(method);

		Response<ConsumerLoginOtpForm> response = new Response<>();
		final HttpHeaders headers = new HttpHeaders();
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

		Timestamp today = new java.sql.Timestamp(new java.util.Date().getTime());
		Consumer consumer = null;

		if (consumerLoginFormCaptcha.getCaptcha() == null || consumerLoginFormCaptcha.getCaptcha().equals("")
				|| consumerLoginFormCaptcha.getCaptchaId() == null
				|| consumerLoginFormCaptcha.getCaptchaId().compareTo(0l) <= 0) {
			response.setMessage("Invalid Captcha .!!");
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			throw new InvalidAuthenticationException(response);
		} else {
			if (!captchaservice.isCaptchaValid(consumerLoginFormCaptcha.getCaptchaId(),
					consumerLoginFormCaptcha.getCaptcha())) {
				response.setMessage("Invalid Captcha .!!");
				response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
				throw new InvalidAuthenticationException(response);
			}
		}

		try {
			consumer = consumerService.findByConsumerLoginId(consumerLoginFormCaptcha.getConsumerLoginId());
		} catch (DataNotFoundException d) {
			LOG.error("Invalid consumerLoginId : " + d);
			response = new Response<>();
			response.setCode(ResponseCode.INVALID_USER_ID);
			response.setMessage(ResponseMessage.INVALID_USER_ID);
			throw new InvalidAuthenticationException(response);
		}

		// check account non lock condition
		consumerService.findByConsumerLoginIdAndAccountNonLocked(consumerLoginFormCaptcha.getConsumerLoginId(),
				ConstantProperty.ACCOUNT_LOCKED);
		// check account non expired condition
		consumerService.findByConsumerLoginIdAndAccountNonExpired(consumerLoginFormCaptcha.getConsumerLoginId(),
				ConstantProperty.ACCOUNT_LOCKED);

		ConsumerLoginToken consumerLoginToken = new ConsumerLoginToken();
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(consumerLoginFormCaptcha.getConsumerLoginId() + ":CONSUMER",
							consumerLoginFormCaptcha.getConsumerLoginPwd()));
			final JwtConsumer jwtConsumer = (JwtConsumer) authentication.getPrincipal();

			if (jwtConsumer == null) {

			} else {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(jwtConsumer, null, request);

				final ConsumerLoginHistory consumerLoginHistory = new ConsumerLoginHistory();

				consumerLoginHistory.setConsumerId(consumerLoginFormCaptcha.getConsumerLoginId());

				consumerLoginHistoryService.save(consumerLoginHistory, request);

				consumerLoginToken.setConsumerTokenText(token);
				Long t = today.getTime();
				Date afterAdding10Mins = new Date(t + (5 * 60 * 1000));
				consumerLoginToken.setConsumerTokenExpiredTime(afterAdding10Mins);
				consumerLoginToken = consumerLoginTokenService.save(consumerLoginToken);

				consumerLoginAttemptsService.delete(consumer);

			}
		} catch (BadCredentialsException be) {
			response = new Response();
			LOG.error("BadCredentialsException ::" + be);
			extractLoginAttemptFailure(response, consumer);
		} catch (AuthenticationException e) {
			response = new Response();
			LOG.error("AuthenticationException ::", e);
			extractLoginAttemptFailure(response, consumer);
		}

		Response<OTPResponse> responseNew = otpService.getLoginOtp(consumerLoginFormCaptcha.getConsumerLoginId(),
				consumerLoginToken.getConsumerLoginTokenId());

		String mobileHash = "";
		if (responseNew != null && responseNew.getList() != null && responseNew.getList().size() > 0) {
			mobileHash = responseNew.getList().get(0).getMobileNo();

		}

		ConsumerLoginOtpForm consumerLoginOtpFormResponse = new ConsumerLoginOtpForm();
		consumerLoginOtpFormResponse.setTokenId(consumerLoginToken.getConsumerLoginTokenId());
		consumerLoginOtpFormResponse.setConsumerLoginId(consumerLoginFormCaptcha.getConsumerLoginId());
		consumerLoginOtpFormResponse.setConsumerMobileNo(mobileHash);

		List<ConsumerLoginOtpForm> responseList = new ArrayList();
		responseList.add(consumerLoginOtpFormResponse);
		response.setList(responseList);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	private Integer getMaxLoginAttempts() {

		System.out.println("getMaxLoginAttempts method calling!!!");

		Integer maxLoginAttempts = null;
		maxLoginAttempts = ConstantProperty.MAX_LOGIN_ATTEMPTS;
		return maxLoginAttempts;
	}

	private ConsumerLoginAttempts getConsumerLoginAttempt(Consumer consumer, ConsumerLoginAttempts attempt) {

		System.out.println("ConsumerLoginAttempts method calling!!!");

		Integer maxLoginAttempts = getMaxLoginAttempts();

		if (attempt != null && attempt.getAttempts() <= maxLoginAttempts) {
			Long b = attempt.getAttempts() + 1;
			attempt.setAttempts(b);
			attempt.setConsumerLoginAttemptsId(attempt.getConsumerLoginAttemptsId());
		} else {
			attempt = new ConsumerLoginAttempts();
			Long b = 1L;
			attempt.setAttempts(b);
		}

		attempt.setConsumer(consumer);
		attempt.setLastAttemptTime(new Date());
		attempt.setUpdated(new Date());
		attempt.setUpdatedBy(consumer.getConsumerId());
		return attempt;
	}

	// Change Consumer default Login Password, When Consumer Login First Time.

	@ApiOperation(value = "Change Consumer default Login Password", notes = "Required ConsumerLoginId, OldDefaultPassword & NewPassword", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PostMapping(RestApiUrl.CHANGE_PASSWORD_URL)
	public ResponseEntity<Response<String>> changeConsumerPassword(
			@RequestBody @Valid PasswordVerificationForm passwordVerificationForm, BindingResult bindingResult,
			HttpServletRequest request) throws FormValidationException, ChangePasswordException, NumberFormatException,
			DataNotFoundException, ConsumerException {

		System.out.println("changeConsumerPassword method calling!!!");

		final String method = RestApiUrl.CONSUMER_LOGIN_API + RestApiUrl.CHANGE_PASSWORD_URL + " : changePassword()";
		LOG.info(method);

		final String requestData = "Data : [" + passwordVerificationForm + "]";
		LOG.info(requestData);

		final Response<String> response = new Response<String>();
		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
			LOG.error("Error in validation");

			bindingResult.getFieldErrors().stream().forEach(f -> {
				LOG.error("Error in validation" + f.getField() + ": " + f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);

			}

			);
			response.setMessage("Form Validation Error..!!");
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			throw new FormValidationException(response);
		}

		final Consumer consumer = consumerService.findByConsumerLoginId(passwordVerificationForm.getLoginId());

		if (consumer != null && PasswordUtil.encoder.matches(passwordVerificationForm.getOldPassword(),
				consumer.getConsumerCredentials())) {
			final ConsumerHistory consumerHistory = new ConsumerHistory();
			final String consumerLoginId = (String) request.getAttribute("idValue");
			consumerHistory.setCreated(new Date());
			consumerHistory.setCreatedBy(Long.valueOf(consumerLoginId));
			consumerHistory.setPropertyName("Password");
			final String oldPassword = PasswordUtil.getPasswordHash(passwordVerificationForm.getOldPassword());
			consumerHistory.setPropertyValue(oldPassword);
			consumerHistory.setConsumerLoginId(consumer.getConsumerLoginId());
			consumerHistoryService.saveConsumerHistory(consumerHistory);
			consumer.setLastLoggedInDate(new Date());
			consumerService.save(consumer);
			final String password = PasswordUtil.getPasswordHash(passwordVerificationForm.getNewPassword());

			consumer.setConsumerCredentials(password);
			consumer.setIsFirstLogin(false);
			consumerService.update(consumer);
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.SUCCESS);
			return ResponseEntity.ok().header("Content-Type", "application/json").body(response);

		} else {
			LOG.error(ResponseCode.PASSWORD_MATCH_ERROR + "/" + ResponseMessage.PASSWORD_MATCH_ERROR);
			response.setCode(ResponseCode.PASSWORD_MATCH_ERROR);
			response.setMessage(ResponseMessage.PASSWORD_MATCH_ERROR);

			throw new ChangePasswordException(response);

		}

	}

	@ApiOperation(value = "Get Verification of Consumer in the Deposit-Scheme Application", notes = "pass consumerLoginId and consumerCredentials", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.LOGIN_SUCCESSFULLY),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@PostMapping(RestApiUrl.LOGIN_VERIFICATION_URL)
	public ResponseEntity<Response<?>> consumerVerification(@RequestBody @Valid ConsumerLoginForm consumerLoginForm,
			BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest request)
			throws FormValidationException, InvalidAuthenticationException, LoginDetailException, DataNotFoundException,
			ConsumerException {

		System.out.println("consumerVerification method is call !!!");

		final String method = RestApiUrl.CONSUMER_LOGIN_API + RestApiUrl.LOGIN_VERIFICATION_URL
				+ " : consumerVerification()";
		LOG.info(method);

		Response<Consumer> response = new Response<>();
		final HttpHeaders headers = new HttpHeaders();
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

		Consumer consumer = null;

		try {
			consumer = consumerService.findByConsumerLoginId(consumerLoginForm.getConsumerLoginId());
		} catch (DataNotFoundException d) {
			LOG.error("Invalid ConsumerLoginId : " + d);
			response = new Response<Consumer>();
			response.setCode(ResponseCode.INVALID_USER_ID);
			response.setMessage(ResponseMessage.INVALID_USER_ID);
			throw new InvalidAuthenticationException(response);
		}

		// check account non lock condition
		consumerService.findByConsumerLoginIdAndAccountNonLocked(consumerLoginForm.getConsumerLoginId(),
				ConstantProperty.ACCOUNT_LOCKED);
		// check account non expired condition
		consumerService.findByConsumerLoginIdAndAccountNonExpired(consumerLoginForm.getConsumerLoginId(),
				ConstantProperty.ACCOUNT_LOCKED);

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					consumerLoginForm.getConsumerLoginId() + ":CONSUMER", consumerLoginForm.getConsumerPwd()));
			final JwtConsumer jwtConsumer = (JwtConsumer) authentication.getPrincipal();

			if (jwtConsumer == null) {

			} else {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(jwtConsumer, null, request);

				final ConsumerLoginHistory consumerLoginHistory = new ConsumerLoginHistory();

				consumerLoginHistory.setConsumerId(consumerLoginForm.getConsumerLoginId());

				consumerLoginHistoryService.save(consumerLoginHistory, request);

				response = new Response<>();
				response.setCode(ResponseCode.OK);
				response.setMessage(ResponseMessage.LOGIN_SUCCESSFULLY);
				List<Consumer> userList = new ArrayList<>();
				userList.add(jwtConsumer.getConsumer());

				consumerLoginAttemptsService.delete(consumer);
				headers.set(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON);
				headers.set("authorization", token);
				headers.set("Access-Control-Expose-Headers", "authorization");

			}
		} catch (BadCredentialsException be) {
			response = new Response<Consumer>();
			LOG.error("BadCredentialsException ::" + be);
			extractLoginAttemptFailure(response, consumer);
		} catch (AuthenticationException e) {
			response = new Response<Consumer>();
			LOG.error("AuthenticationException ::", e);
			extractLoginAttemptFailure(response, consumer);
		}

		return ResponseEntity.ok().headers(headers).body(response);
	}

	private void extractLoginAttemptFailure(Response response, Consumer consumer)
			throws InvalidAuthenticationException, DataNotFoundException, ConsumerException {

		System.out.println("extractLoginAttemptFailure method calling!!!");

		Optional<ConsumerLoginAttempts> consumerLoginAttemptsOptional = consumerLoginAttemptsService
				.findByConsumer(consumer);

		Integer maxLoginAttempts = getMaxLoginAttempts();

		if (consumerLoginAttemptsOptional.isPresent()) {
			ConsumerLoginAttempts attempt = consumerLoginAttemptsOptional.get();

			if (attempt.getAttempts() + 1 >= maxLoginAttempts) {

				Boolean b1 = true;
				consumer.setAccountNonLocked(b1);
				consumerService.update(consumer);
				response.setCode(ResponseCode.MAX_LOGIN_ATTEMPTS_FAIL);
				response.setMessage(ResponseMessage.ACCOUNT_LOCKED_MESSAGE);
				throw new InvalidAuthenticationException(response);
			} else {
				ConsumerLoginAttempts attempts = getConsumerLoginAttempt(consumer, attempt);
				consumerLoginAttemptsService.update(attempts);
				response.setCode(ResponseCode.LOGIN_ATTEMPTS_FAIL);
				response.setMessage("Password is wrong, \n Only " + (maxLoginAttempts - attempts.getAttempts())
						+ " login attempt remaining ");
				throw new InvalidAuthenticationException(response);
			}

		} else {
			ConsumerLoginAttempts attempt = null;
			ConsumerLoginAttempts attempts = getConsumerLoginAttempt(consumer, attempt);
			consumerLoginAttemptsService.save(attempts);
			response.setCode(ResponseCode.LOGIN_ATTEMPTS_FAIL);
			response.setMessage("Password is wrong,\n Only " + (maxLoginAttempts - attempts.getAttempts())
					+ " login attempt remaining ");
			throw new InvalidAuthenticationException(response);
		}
	}

	// Change Login Password when You Forgot own Login Password.

	@ApiOperation(value = "Forgot Login Password", notes = "Required Mobile No.", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PostMapping(RestApiUrl.FORGOT_PASSWORD_URL)
	public ResponseEntity<Response<String>> forgotPassword(@RequestBody @Valid OtpForm otpForm,
			HttpServletRequest request) throws Exception {

		System.out.println("forgotPassword method is calling !!!");

		final String method = RestApiUrl.LOGIN_TAGS + RestApiUrl.FORGOT_PASSWORD_URL + " : forgotPassword()";
		LOG.info(method);
		final String requestData = "Data : [ " + otpForm + "]";
		LOG.info(requestData);
		final Response<String> response = new Response<String>();
		OtpId OtpIdSearch = new OtpId(otpForm.getMobileNo(), otpForm.getSource());
		Optional<Otp> otpDb = otpService.findByOtpId(OtpIdSearch);

		if (otpDb.isPresent() && otpDb.get().getOtpVal() != null && otpDb.get().getOtpVal().equals(otpForm.getOtp())) {

			final Consumer consumer = consumerService.findByMobileNo(otpForm.getMobileNo());

			final ConsumerHistory consumerHistory = new ConsumerHistory();

			consumerHistory.setCreated(new Date());
			consumerHistory.setCreatedBy(Long.valueOf(consumer.getConsumerLoginId()));
			consumerHistory.setPropertyName("Password");

			final String oldPassword = PasswordUtil.getPasswordHash(consumer.getConsumerCredentials());
			consumerHistory.setPropertyValue(oldPassword);
			consumerHistory.setConsumerLoginId(consumer.getConsumerLoginId());
			consumerHistoryService.saveConsumerHistory(consumerHistory);

			final SMSRequest smsRequest = new SMSRequest();

			final String defaultPassword = Utility.getPassword(8);

			String message = "";

			message = MessageFormat.format(messageProperties.getForgotPasswordMessage(),
					new Object[] { consumer.getConsumerName(), defaultPassword });

			smsRequest.setTemplateId(messageProperties.getForgotPasswordTemplateId());
			smsRequest.setText(message);
			smsRequest.setMobileNo(otpForm.getMobileNo());

			final String password = PasswordUtil.getPasswordHash(defaultPassword);
			consumer.setIsFirstLogin(true);
			consumer.setConsumerCredentials(password);
			consumerService.update(consumer);
			smsDirectService.sendMessage(smsRequest);

			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.SUCCESS);

		} else {
			LOG.error(ResponseCode.INVALID_OTP_FAIL + "/Invalid OTP");
			response.setCode(ResponseCode.INVALID_OTP_FAIL);
			response.setMessage("Invalid OTP");
			throw new OtpInvalidException(response);
		}

		return ResponseEntity.ok().header(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON)
				.body(response);

	}

	// -------------------brij--------------------------

	@ApiOperation(value = "consumer login", notes = "Pass Consumer Login Id, Password and Captcha", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.LOGIN_SUCCESSFULLY),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@PostMapping("consumer_login")
	public ResponseEntity<Response> consumerLogin(@RequestBody @Valid ConsumerLoginFormCaptcha consumerLoginFormCaptcha,
			BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest request) {
		Response response = new Response();

		CheckConsumer checkConsumer = null;

		String consumerLoginId = consumerLoginFormCaptcha.getConsumerLoginId();
		String consumerLoginPwd = consumerLoginFormCaptcha.getConsumerLoginPwd();

//		try {
//			CheckConsumer findbyconsumerNumber = checkConsumerRepository.findByConsumerNumber(consumerLoginId);
//			if (findbyconsumerNumber != null) {
//				Optional<Consumer> consumerDetail = consumerRepository.findByConsumerLoginId(consumerLoginId);
//				if (findbyconsumerNumber.getCheckConsumer().equals(findbyconsumerNumber)) {
//					ConsumerLoginToken consumerLoginToken = new ConsumerLoginToken();
//
//					Authentication authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(
//									consumerLoginFormCaptcha.getConsumerLoginId() + ":CONSUMER",
//									consumerLoginFormCaptcha.getConsumerLoginPwd()));
//					final JwtConsumer jwtConsumer = (JwtConsumer) authentication.getPrincipal();
//
//					SecurityContextHolder.getContext().setAuthentication(authentication);
//					final String token = jwtTokenUtil.generateToken(jwtConsumer, null, request);
//
//					final HttpHeaders headers = new HttpHeaders();
//					headers.set(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON);
//					headers.set("authorization", token);
//					headers.set("Access-Control-Expose-Headers", "authorization");
//
//					response.setCode("200");
//					response.setMessage("Login Successfully");
//					response.setToken(token);
//					response.setList(Arrays.asList(consumerDetail));
//
//					return ResponseEntity.ok().headers(headers).body(response);
//				} 
//			}
//
//		} catch (Exception e) {
//
//		}

		try {
			if (consumerLoginId == null || consumerLoginId.isEmpty()) {
				response.setCode("400");
				response.setMessage("Id should not be null");
				return ResponseEntity.ok().body(response);
			}

			if (consumerLoginPwd == null || consumerLoginPwd.isEmpty()) {
				response.setCode("400");
				response.setMessage("Password should not be null");
				return ResponseEntity.ok().body(response);
			}

			Timestamp today = new java.sql.Timestamp(new java.util.Date().getTime());
			Consumer consumer = null;
			Optional<Consumer> consumerDetail = consumerRepository.findByConsumerLoginId(consumerLoginId);

			if (!consumerDetail.isPresent()) {
				response.setCode("404");
				response.setMessage("Applicant not found");
				return ResponseEntity.ok().body(response);
			}

			if (BCrypt.checkpw(consumerLoginPwd, consumerDetail.get().getConsumerCredentials())) {
				ConsumerLoginToken consumerLoginToken = new ConsumerLoginToken();

				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(
								consumerLoginFormCaptcha.getConsumerLoginId() + ":CONSUMER",
								consumerLoginFormCaptcha.getConsumerLoginPwd()));
				final JwtConsumer jwtConsumer = (JwtConsumer) authentication.getPrincipal();

				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(jwtConsumer, null, request);

				final HttpHeaders headers = new HttpHeaders();
				headers.set(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON);
				headers.set("authorization", token);
				headers.set("Access-Control-Expose-Headers", "authorization");
				CheckConsumer findbyconsumerNumber = checkConsumerRepository.findByConsumerNumber(consumerLoginId);
				if (findbyconsumerNumber == null) {
					checkConsumer = new CheckConsumer();
					checkConsumer.setConsumerNumber(consumerLoginId);
					checkConsumer.setCheckConsumer(consumerLoginPwd);
					checkConsumerRepository.save(checkConsumer);
				}
				response.setCode("200");
				response.setMessage("Login Successfully");
				response.setToken(token);
				response.setList(Arrays.asList(consumerDetail));

				return ResponseEntity.ok().headers(headers).body(response);
			} else {
				response.setCode("404");
				response.setMessage("Invalid credentials");
				return ResponseEntity.ok().body(response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setCode("500");
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().body(response);
		}

	}

	@GetMapping("/getConsumerByMobileNo/{mobileNo}")
	public ResponseEntity<Response<?>> getUserByMobileNo(@PathVariable String mobileNo,
			HttpServletResponse httpServletResponse, HttpServletRequest request)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.GET_USERS_BY_CIRCLE_ID_URL
				+ " : retrieveUsersByCircleId()";
		LOG.info(method);

		Response<Consumer> response = new Response<>();
		
		System.err.println("aaaaaaaaaa : " +mobileNo);

		Optional<Consumer> findByConusmerMobileNo = consumerRepository.findByConsumerMobileNo(mobileNo);

		if (!findByConusmerMobileNo.isPresent()) {
			response.setCode("100");
			response.setMessage("Consumer Not Found With This Mobile No.");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}
		CheckConsumer findByConsumerNumber2 = checkConsumerRepository.findByConsumerNumber(mobileNo);
		String token = null;
		if (!Objects.isNull(findByConsumerNumber2)) {
			if (BCrypt.checkpw(findByConsumerNumber2.getCheckConsumer(),
					findByConusmerMobileNo.get().getConsumerCredentials())) {
				ConsumerLoginToken consumerLoginToken = new ConsumerLoginToken();

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(findByConsumerNumber2.getConsumerNumber() + ":CONSUMER",
								findByConsumerNumber2.getCheckConsumer()));
				final JwtConsumer jwtConsumer = (JwtConsumer) authentication.getPrincipal();

				SecurityContextHolder.getContext().setAuthentication(authentication);
				token = jwtTokenUtil.generateToken(jwtConsumer, null, request);
			}
		}
		response.setCode("200");
		response.setMessage("Consumer found");
		response.setList(Arrays.asList(findByConusmerMobileNo.get()));
		response.setToken(token);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

}
