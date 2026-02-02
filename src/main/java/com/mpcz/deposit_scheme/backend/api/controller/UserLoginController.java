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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ConstantProperty;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.Captcha;
import com.mpcz.deposit_scheme.backend.api.domain.CheckConsumer;
import com.mpcz.deposit_scheme.backend.api.domain.CheckUser;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginToken;
import com.mpcz.deposit_scheme.backend.api.domain.Otp;
import com.mpcz.deposit_scheme.backend.api.domain.OtpId;
import com.mpcz.deposit_scheme.backend.api.domain.Role;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.UserHistory;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginAttempts;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginHistory;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginToken;
import com.mpcz.deposit_scheme.backend.api.domain.UserRole;
import com.mpcz.deposit_scheme.backend.api.dto.UserDTO;
import com.mpcz.deposit_scheme.backend.api.dto.UserStatusDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ChangePasswordException;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DesignationException;
import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.LoginDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.OtpInvalidException;
import com.mpcz.deposit_scheme.backend.api.exception.RegionException;
import com.mpcz.deposit_scheme.backend.api.exception.RoleException;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtConsumer;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtTokenUtil;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtUser;
import com.mpcz.deposit_scheme.backend.api.jwt.security.util.PasswordUtil;
import com.mpcz.deposit_scheme.backend.api.repository.CheckUserRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRoleRepository;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.OtpForm;
import com.mpcz.deposit_scheme.backend.api.request.PasswordVerificationForm;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.request.UserLoginForm;
import com.mpcz.deposit_scheme.backend.api.request.UserLoginFormCaptcha;
import com.mpcz.deposit_scheme.backend.api.request.UserLoginOtpForm;
import com.mpcz.deposit_scheme.backend.api.request.UserSignUpForm;
import com.mpcz.deposit_scheme.backend.api.response.CaptchaResponse;
import com.mpcz.deposit_scheme.backend.api.response.OTPResponse;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.CaptchaService;
import com.mpcz.deposit_scheme.backend.api.services.CircleService;
//import com.mpcz.deposit_scheme.backend.api.response.UserDTO;
//import com.mpcz.deposit_scheme.backend.api.request.SignUpForm;
//import com.mpcz.deposit_scheme.backend.api.request.UserStatusDTO;
import com.mpcz.deposit_scheme.backend.api.services.DesignationService;
import com.mpcz.deposit_scheme.backend.api.services.DiscomService;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.services.DivisionService;
import com.mpcz.deposit_scheme.backend.api.services.FeederService;
//import com.mpcz.deposit_scheme.backend.api.services.LoginHistoryService;
//import com.mpcz.deposit_scheme.backend.api.services.LoginTokenService;
import com.mpcz.deposit_scheme.backend.api.services.OtpService;
import com.mpcz.deposit_scheme.backend.api.services.RegionService;
import com.mpcz.deposit_scheme.backend.api.services.RoleServices;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.services.SubDivisionService;
import com.mpcz.deposit_scheme.backend.api.services.SubstationService;
import com.mpcz.deposit_scheme.backend.api.services.UserHistoryService;
import com.mpcz.deposit_scheme.backend.api.services.UserLoginAttemptsService;
import com.mpcz.deposit_scheme.backend.api.services.UserLoginHistoryService;
import com.mpcz.deposit_scheme.backend.api.services.UserLoginTokenService;
import com.mpcz.deposit_scheme.backend.api.services.UserService;
import com.mpcz.deposit_scheme.backend.api.util.RandomStringGenerator;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
import com.mpcz.deposit_scheme.backend.api.utility.Utility;
import com.mpcz.deposit_scheme.backend.api.validation.AccessLevelFieldValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "LoginController", description = "Rest Api retated to User registeration,login,update profile etc..!!")
@RestController
@RequestMapping(RestApiUrl.USER_LOGIN_API)
public class UserLoginController {
	Logger LOG = LoggerFactory.getLogger(UserLoginController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CircleService circleService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private RoleServices roleService;

	@Autowired
	private UserLoginHistoryService userloginHistoryService;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserHistoryService userHistoryService;

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	private UserLoginAttemptsService loginAttemptService;

	@Autowired
	SMSDirectService smsDirectService;

	@Autowired
	OtpService otpService;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	CaptchaService captchaservice;

	@Autowired
	UserLoginTokenService loginTokenService;

	@Autowired
	DiscomService discomService;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private SubDivisionService subDivisionService;

	@Autowired
	private DistributionCenterService distributionCenterService;

	@Autowired
	private SubstationService substationService;

	@Autowired
	private FeederService feederService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private CheckUserRepository checkUserRepository;
	
	@Autowired
	private UserUpdatePassword userUpdatePassword;
	
	

	// @Value("${discom.name}")
	// private String discomName;

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
		Timestamp today = new java.sql.Timestamp(new java.util.Date().getTime());
		Response<?> response1 = new Response();
		try {
			String imgpath = resourceLoader.getResource("classpath:/static/images/FFFFFF.png").getURI().getPath();

			String fontpath = resourceLoader.getResource("classpath:/static/fonts/lucida.ttf").getURI().getPath();
			// String path = sc.getRealPath("resources");
			File file = new File(imgpath);
			File fontfile = new File(fontpath);
			InputStream inputStream = new FileInputStream(file);
			String captcha = RandomStringGenerator.getRandomString(5);

			Captcha c = new Captcha();
			c.setCaptchaText(captcha);
			System.out.println("captcha*** " + captcha);
			Long t = today.getTime();
			Date afterAdding10Mins = new Date(t + (5 * 60 * 1000));
			c.setCaptchaExpiredTime(afterAdding10Mins);
			c = captchaservice.save(c);

			byte[] img = mergeImageAndText(inputStream, captcha, new Point(16, 30), fontfile);

			CaptchaResponse r = new CaptchaResponse();

			// r.setCaptchaImg(response.getOutputStream().write(img));
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
		BufferedImage im = ImageIO.read(imageFilePath);
		Graphics2D g = (Graphics2D) im.getGraphics();
		g.setFont(g.getFont().deriveFont(35f));
		// Color cl = new Color(55, 143, 208);
		Color cl = new Color(208, 146, 55);
		g.setColor(cl);
		g.setFont(Font.getFont("lucida"));
		g.drawString(text, textPosition.x, textPosition.y);
		g.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, "png", baos);
		return baos.toByteArray();
	}

	@ApiOperation(value = "Get verification captcha login of User in the Application", notes = "pass user id and password", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.LOGIN_SUCCESSFULLY),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@PostMapping(RestApiUrl.LOGIN_VERIFICATION_CAPTCHA_URL)
	public ResponseEntity<Response<?>> verificationCaptcha(@RequestBody @Valid UserLoginFormCaptcha loginForm,
			BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest request)
			throws FormValidationException, InvalidAuthenticationException, LoginDetailException, DataNotFoundException,
			UserException, OtpInvalidException {
		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.LOGIN_VERIFICATION_CAPTCHA_URL
				+ " : consumerVerificationCaptcha()";
		LOG.info(method);
		// Response<User> response = new Response<>();

		Response<UserLoginOtpForm> response = new Response<>();
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
			// response=userService.getUserAuthentication(loginForm);
		Timestamp today = new java.sql.Timestamp(new java.util.Date().getTime());
		User user = null;

//		if (loginForm.getCaptcha() == null || loginForm.getCaptcha().equals("") || loginForm.getCaptchaId() == null
//				|| loginForm.getCaptchaId().compareTo(0l) <= 0) {
//			response.setMessage("Invalid Captcha .!!");
//			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//			throw new InvalidAuthenticationException(response);
//		} else {
//			if (!captchaservice.isCaptchaValid(loginForm.getCaptchaId(), loginForm.getCaptcha())) {
//				response.setMessage("Invalid Captcha .!!");
//				response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//				throw new InvalidAuthenticationException(response);
//			}
//		}

		// if (loginForm.getCaptcha() == null || loginForm.getCaptcha().equals("") ||
		// loginForm.getCaptchaId() == null
		// || loginForm.getCaptchaId().compareTo(0l) <= 0) {
		// response.setMessage("Invalid Captcha .!!");
		// response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
		// throw new InvalidAuthenticationException(response);
		// } else {
		// if (!captchaservice.isCaptchaValid(loginForm.getCaptchaId(),
		// loginForm.getCaptcha())) {
		// response.setMessage("Invalid Captcha .!!");
		// response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
		// throw new InvalidAuthenticationException(response);
		// }
		// }

		try {
			user = userService.findByUserId(loginForm.getUserLoginId());
		} catch (DataNotFoundException d) {
			LOG.error("Invalid UserId : " + d);
			response = new Response<>();
			response.setCode(ResponseCode.INVALID_USER_ID);
			response.setMessage(ResponseMessage.INVALID_USER_ID);
			throw new InvalidAuthenticationException(response);
		}

		// check account non lock condition
		userService.findByUserIdAndAccountNonLocked(loginForm.getUserLoginId(), ConstantProperty.ACCOUNT_LOCKED);
		// check account non expired condition
		userService.findByUserIdAndAccountNonExpired(loginForm.getUserLoginId(), ConstantProperty.ACCOUNT_LOCKED);
		UserLoginToken loginToken = new UserLoginToken();
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginForm.getUserLoginId() + ":USER", loginForm.getUserLoginPwd()));
			final JwtUser userDetails = (JwtUser) authentication.getPrincipal();

			if (userDetails == null) {

			} else {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(null, userDetails, request);

				final UserLoginHistory loginHistory = new UserLoginHistory();

				loginHistory.setUserId(loginForm.getUserLoginId());

				userloginHistoryService.save(loginHistory, request);

				loginToken.setTokenText(token);
				Long t = today.getTime();
				Date afterAdding10Mins = new Date(t + (5 * 60 * 1000));
				loginToken.setTokenExpiredTime(afterAdding10Mins);
				loginToken = loginTokenService.save(loginToken);

				loginAttemptService.delete(user);

			}
		} catch (BadCredentialsException be) {

			response = new Response();
			LOG.error("BadCredentialsException ::" + be);
			extractLoginAttemptFailure(response, user);

		} catch (AuthenticationException e) {
			response = new Response();
			LOG.error("AuthenticationException ::", e);
			extractLoginAttemptFailure(response, user);

		}
		Response<OTPResponse> responseNew = otpService.getUserLoginOtp(loginForm.getUserLoginId(),
				loginToken.getLoginTokenId());

		String mobileHash = "";
		if (responseNew != null && responseNew.getList() != null && responseNew.getList().size() > 0) {
			mobileHash = responseNew.getList().get(0).getMobileNo();
		}

		UserLoginOtpForm loginOtpResponse = new UserLoginOtpForm();
		loginOtpResponse.setTokenId(loginToken.getLoginTokenId());
		loginOtpResponse.setUserLoginId(loginForm.getUserLoginId());
		loginOtpResponse.setUserMobileNo(mobileHash);

		List<UserLoginOtpForm> responseList = new ArrayList();
		responseList.add(loginOtpResponse);
		response.setList(responseList);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	private Integer getMaxLoginAttempts() {
		Integer maxLoginAttempts = null;
		maxLoginAttempts = ConstantProperty.MAX_LOGIN_ATTEMPTS_BHOPAL;
		return maxLoginAttempts;
	}

	/**
	 * @param consumer
	 * @param attempt
	 * @return
	 */
	private UserLoginAttempts getUserLoginAttempt(User user, UserLoginAttempts attempt) {

		Integer maxLoginAttempts = getMaxLoginAttempts();

		if (attempt != null && attempt.getAttempts() <= maxLoginAttempts) {
			Long b = attempt.getAttempts() + 1;
			attempt.setAttempts(b);
			attempt.setUserLoginAttemptId(attempt.getUserLoginAttemptId());
		} else {
			attempt = new UserLoginAttempts();
			Long b = 1L;
			attempt.setAttempts(b);
		}

		attempt.setUser(user);
		attempt.setLastAttemptTime(new Date());
		attempt.setUpdated(new Date());
		attempt.setUpdatedBy(user.getAdUserId());
		return attempt;
	}

	@ApiOperation(value = "For user registration the application", notes = "For user registration the application", response = Response.class, responseContainer = "List", tags = RestApiUrl.SIGNUP_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.SIGNUP_FORM_VIEW_SUCCESS),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.SIGNUP_URL)
	public ResponseEntity<Response<?>> signUp(@RequestBody @Valid UserSignUpForm signUpForm,
			BindingResult bindingResult) throws Exception {

		Response<User> response = null;
		// if (bindingResult.hasErrors()) {
		// List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		//
		// bindingResult.getFieldErrors().stream().forEach(f -> {
		//
		// LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " +
		// f.getDefaultMessage());
		//
		// ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
		// f.getField() + ":" + f.getDefaultMessage());
		//
		// errorList.add(error);
		// });
		// response = new Response<>();
		// response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
		// response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
		// response.setError(errorList);
		// throw new FormValidationException(response);
		// } // end of form validation

		final SMSRequest smsRequest = new SMSRequest();
		final User user = new User();
		final ModelMapper mapper = new ModelMapper();

		mapper.map(signUpForm, user);
		List<Role> userRoles = new ArrayList<>();
		for (Long role : signUpForm.getUserRoles()) {

			Role r = roleService.findRoleById(role);
			userRoles.add(r);

		}

		user.setUserRoles(userRoles);

		final String defaultPassword = Utility.getPassword(8);
		smsRequest.setText(MessageFormat.format(messageProperties.getDefaultPasswordMessage(),
				new Object[] { signUpForm.getUserName(), defaultPassword }));
		smsRequest.setMobileNo(signUpForm.getMobileNo());
		smsRequest.setTemplateId(messageProperties.getPasswordTemplateId());

		final String password = PasswordUtil.getPasswordHash(defaultPassword);

		user.setUserCredentials(password);
		CheckUser findByUserId = checkUserRepository.findByUserId(signUpForm.getUserId());
		if (findByUserId == null) {
			CheckUser checkUser = new CheckUser();
			checkUser.setUserId(signUpForm.getUserId());
			checkUser.setCheckUser(defaultPassword);
			checkUserRepository.save(checkUser);
		}
		// sandeep, starts

		// if (AccessLevelFieldValue.CIRCLE_LEVEL.equals(signUpForm.getAccessLevel())) {
		// user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
		// }
		// if (AccessLevelFieldValue.REGION_LEVEL.equals(signUpForm.getAccessLevel())
		// || AccessLevelFieldValue.CIRCLE_LEVEL.equals(signUpForm.getAccessLevel())) {
		// user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
		// }

		if (AccessLevelFieldValue.DISCOM_LEVEL.equals(signUpForm.getAccessLevel())) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
		} else if (AccessLevelFieldValue.DISCOM_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.REGION_LEVEL.equals(signUpForm.getAccessLevel())) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
		} else if (AccessLevelFieldValue.DISCOM_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.REGION_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.CIRCLE_LEVEL.equals(signUpForm.getAccessLevel())) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
		} else if (AccessLevelFieldValue.DISCOM_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.REGION_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.CIRCLE_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.DIVISION_LEVEL.equals(signUpForm.getAccessLevel())) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
			user.setUserDivision(divisionService.findDivisionById(signUpForm.getDivisionId()));
		} else if (AccessLevelFieldValue.DISCOM_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.REGION_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.CIRCLE_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.DIVISION_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.SUBDIVISION_LEVEL.equals(signUpForm.getAccessLevel())) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
			user.setUserDivision(divisionService.findDivisionById(signUpForm.getDivisionId()));
			user.setUserSubDivision(subDivisionService.findSubDivisionById(signUpForm.getSubDivisionId()));
		} else if (AccessLevelFieldValue.DISCOM_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.REGION_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.CIRCLE_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.DIVISION_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.SUBDIVISION_LEVEL.equals(signUpForm.getAccessLevel())
				|| AccessLevelFieldValue.DISTRIBUTION_LEVEL.equals(signUpForm.getAccessLevel())) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
			user.setUserDivision(divisionService.findDivisionById(signUpForm.getDivisionId()));
			user.setUserSubDivision(subDivisionService.findSubDivisionById(signUpForm.getSubDivisionId()));
			user.setUserDc(distributionCenterService.findDistributionCenterById(signUpForm.getDcId()));
			if (signUpForm.getSubstationId() != null)
				user.setUserSubstation(substationService.findSubstationById(signUpForm.getSubstationId()));
			if (signUpForm.getFeederId() != null)
				user.setUserFeeder(feederService.findFeederById(signUpForm.getFeederId()));
		}
		// sandeep, ends
		// user.setUserDesignation(designationService.findDesignationById(signUpForm.getDesignationId()));

		response = userService.save(user);
		smsDirectService.sendMessage(smsRequest);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "For update user details", notes = "Pass user id", response = Response.class, responseContainer = "List", tags = RestApiUrl.SIGNUP_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.SIGNUP_FORM_VIEW_SUCCESS),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PutMapping(RestApiUrl.UPDATE_USER_URL)
	public ResponseEntity<Response<?>> updateUser(@PathVariable Long id, @RequestBody @Valid UserSignUpForm signUpForm,
			BindingResult bindingResult) throws FormValidationException, InvalidAuthenticationException, UserException,
			RoleException, CircleException, DesignationException, RegionException, DiscomException,
			SubDivisionException, DivisionException, DistributionCenterException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.UPDATE_USER_URL + " : updateUser()";
		LOG.info(method);
		Response<User> response = null;
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
		}

		final User user = userService.findById(id).getList().get(0);
		// final ModelMapper mapper = new ModelMapper();
		// mapper.map(signUpForm, user);

		if (signUpForm.getUserName() != null && !signUpForm.getUserName().equals("")) {
			user.setUserName(signUpForm.getUserName().trim());

		}

		if (signUpForm.getUserEmailId() != null && !signUpForm.getUserEmailId().equals("")) {
			user.setUserEmailId(signUpForm.getUserEmailId().trim());
		}

		if (signUpForm.getMobileNo() != null && !signUpForm.getMobileNo().equals("")) {
			user.setMobileNo(signUpForm.getMobileNo().trim());
		}

		if (signUpForm.getAccessLevel() != null && !signUpForm.getAccessLevel().equals("")) {
			user.setAccessLevel(signUpForm.getAccessLevel());
		}

		// if (signUpForm.getAadharNo() != null && !signUpForm.getAadharNo().equals(""))
		// {
		// user.setAadharNo(signUpForm.getAadharNo());
		// }

		List<Role> userRoles = new ArrayList<>();
		for (Long role : signUpForm.getUserRoles()) {

			Role r = roleService.findRoleById(role);
			userRoles.add(r);
		}

		user.setUserRoles(userRoles);

		// sandeep, stats

		// if (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.DISCOM_LEVEL)) {
		//// Discom tempregion = discomService.findByDiscomId(id);
		//
		// user.setUserRegion(null);
		// user.setUserCircle(null);
		//
		// } else if
		// (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.REGION_LEVEL)) {
		// Region tempregion = regionService.findRegionById(signUpForm.getRegionId());
		// System.out.println(tempregion);
		// user.setUserRegion(tempregion);
		// user.setUserCircle(null);
		// } else if
		// (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.CIRCLE_LEVEL)) {
		//
		// Region tempregion = regionService.findRegionById(signUpForm.getRegionId());
		// System.out.println(tempregion);
		// user.setUserRegion(tempregion);
		//
		// Circle tempcircle = circleService.findCircleById(signUpForm.getCircleId());
		// System.out.println(tempcircle);
		// user.setUserCircle(tempcircle);
		// }

		if (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.DISCOM_LEVEL)) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(null);
			user.setUserCircle(null);
			user.setUserDivision(null);
			user.setUserSubDivision(null);
			user.setUserDc(null);
		} else if (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.REGION_LEVEL)) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(null);
			user.setUserDivision(null);
			user.setUserSubDivision(null);
			user.setUserDc(null);
		} else if (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.CIRCLE_LEVEL)) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
			user.setUserDivision(null);
			user.setUserSubDivision(null);
			user.setUserDc(null);
		} else if (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.DIVISION_LEVEL)) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
			user.setUserDivision(divisionService.findDivisionById(signUpForm.getDivisionId()));
			user.setUserSubDivision(null);
			user.setUserDc(null);
		} else if (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.SUBDIVISION_LEVEL)) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
			user.setUserDivision(divisionService.findDivisionById(signUpForm.getDivisionId()));
			user.setUserSubDivision(subDivisionService.findSubDivisionById(signUpForm.getSubDivisionId()));
			user.setUserDc(null);
		} else if (signUpForm.getAccessLevel().equals(AccessLevelFieldValue.DISTRIBUTION_LEVEL)) {
			user.setUserDiscom(discomService.findByDiscomId(signUpForm.getDiscomId()));
			user.setUserRegion(regionService.findRegionById(signUpForm.getRegionId()));
			user.setUserCircle(circleService.findCircleById(signUpForm.getCircleId()));
			user.setUserDivision(divisionService.findDivisionById(signUpForm.getDivisionId()));
			user.setUserSubDivision(subDivisionService.findSubDivisionById(signUpForm.getSubDivisionId()));
			user.setUserDc(distributionCenterService.findDistributionCenterById(signUpForm.getDcId()));
		}
		// sandeep, ends

		// Designation tempdesignation =
		// designationService.findDesignationById(signUpForm.getDesignationId());
		// System.out.println(tempdesignation);
		// user.setUserDesignation(tempdesignation);

		response = userService.save(user);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "For view user registration form", notes = "For view user registration form", response = Response.class, responseContainer = "List", tags = RestApiUrl.SIGNUP_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.SIGNUP_FORM_VIEW_SUCCESS),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@GetMapping(RestApiUrl.VIEW_SIGNUP_URL)
	public ResponseEntity<Response<?>> viewsignUp() throws InvalidAuthenticationException, RegionException,
			CircleException, DesignationException, RoleException, DivisionException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.VIEW_SIGNUP_URL + " : viewsignUp()";
		LOG.info(method);

		Response<Object> response = new Response<>();
		response.setCode(ResponseCode.OK);
		response.setMessage(ResponseMessage.SUCCESS);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve specific user", notes = "Pass user id", response = Response.class, responseContainer = "List", tags = RestApiUrl.SIGNUP_TAGS)
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
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.SIGNUP_TAGS + RestApiUrl.GET_URL + " : retrieveUser()";
		LOG.info(method);
		Response<User> response = userService.findById(id);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	// @ApiOperation(value = "Retrieve specific user for view only", notes = "Pass
	// user id", response = Response.class, responseContainer = "List", tags =
	// RestApiUrl.USER_TAGS)
	// @ApiResponses(value = {
	// @ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message =
	// ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
	// @ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message =
	// ResponseMessage.FORBIDDEN),
	// @ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message =
	// ResponseMessage.NOT_FOUND),
	// @ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message =
	// ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
	// @ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message =
	// ResponseMessage.BAD_REQUEST),
	// @ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message =
	// ResponseMessage.UNAUTHORIZED),
	// @ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message =
	// ResponseMessage.INTERNAL_SERVER_ERROR) })
	// @GetMapping(RestApiUrl.VIEW_USER_URL)
	// public ResponseEntity<Response<?>> retrieveUserForView(@PathVariable long id,
	// HttpServletResponse httpServletResponse)
	// throws FormValidationException, InvalidAuthenticationException, UserException
	// {
	//
	// final String method = RestApiUrl.USER_TAGS + RestApiUrl.VIEW_USER_URL + " :
	// retrieveUserForView()";
	// LOG.info(method);
	// Response<User> response = userService.findById(id);
	//
	//
	// UserDTO userDTO = new UserDTO();
	//
	// User user = response.getList().get(0);
	//
	// final ModelMapper mapper = new ModelMapper();
	// mapper.map(user, userDTO);
	//
	// userDTO.setDiscomName("");
	//
	// if ("1".equals(user.getAccessLevel())) {
	// userDTO.setAccessLevel("Discom Level");
	// } else if ("2".equals(user.getAccessLevel())) {
	// userDTO.setAccessLevel("Region Level");
	// } else if ("3".equals(user.getAccessLevel())) {
	// userDTO.setAccessLevel("Circle Level");
	// }
	//
	// if ("2".equals(user.getAccessLevel()) || "3".equals(user.getAccessLevel())) {
	// userDTO.setRegionName(user.getUserRegion().getRegionName());
	// }
	//
	// if ("3".equals(user.getAccessLevel())) {
	// userDTO.setCircleName(user.getUserCircle().getCircleName());
	// }
	// userDTO.setDesignationName(user.getUserDesignation().getDesignationName());
	//
	// Response<UserDTO> response2 = new Response<UserDTO>();
	// List<UserDTO> list = new ArrayList<UserDTO>();
	// list.add(userDTO);
	// response2.setList(list);
	//
	// return
	// ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response2);
	//
	// }

	@ApiOperation(value = "Retrieve all users", notes = "Fetach all users", response = Response.class, responseContainer = "List", tags = RestApiUrl.SIGNUP_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> retrieveAllUsers(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.SIGNUP_TAGS + RestApiUrl.GET_ALL_URL + " : retrieveAllUsers()";
		LOG.info(method);
		Response<List<User>> response = userService.findAll();
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Change user login status", notes = "Pass userid & status", response = Response.class, responseContainer = "List", tags = RestApiUrl.UPDATE_USER_STATUS_URL)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PutMapping(RestApiUrl.UPDATE_USER_STATUS_URL)
	public ResponseEntity<Response<?>> changeUserStatus(@RequestBody @Valid UserStatusDTO userStatusDTO,
			@PathVariable long id, BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.UPDATE_USER_STATUS_URL + " : changeUserStatus()";

		Response<User> response = null;

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				// LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " +
				// f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});
			response = new Response<>();
			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			throw new FormValidationException(response);
		}

		Response<User> userResponse = userService.findById(userStatusDTO.getAdUserId());

		User u = userResponse.getList().get(0);
		u.setAccountNonExpired(userStatusDTO.getIsAccountNonExpired());
		u.setAccountNonLocked(userStatusDTO.getIsAccountNonLocked());
		u.setAdUserId(userStatusDTO.getAdUserId());
		u.setActive(userStatusDTO.getIsActive());
		u.setLoginStatus("active");
		u.setLoginAttemp(0L);
		u.setLastLoggedInDate(new Date());

		userResponse = userService.save(u);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(userResponse);
	}

	@ApiOperation(value = "Change user password", notes = "Required UserId,OldPassword & NewPassword", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
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
	public ResponseEntity<Response<String>> changePassword(@RequestBody @Valid PasswordVerificationForm form,
			BindingResult bindingResult, HttpServletRequest request) throws FormValidationException,
			ChangePasswordException, NumberFormatException, UserException, DataNotFoundException {
		final String method = RestApiUrl.LOGIN_TAGS + RestApiUrl.CHANGE_PASSWORD_URL + " : changePassword()";
		LOG.info(method);
		final String requestData = "Data : [" + form + "]";
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

		final User user = userService.findByUserId(form.getLoginId());

		if (user != null && PasswordUtil.encoder.matches(form.getOldPassword(), user.getUserCredentials())) {
			final UserHistory history = new UserHistory();
			final String userId = (String) request.getAttribute("idValue");
			history.setCreated(new Date());
//			history.setCreatedBy(Long.valueOf(userId));
			history.setPropertyName("Password");
			final String oldPassword = PasswordUtil.getPasswordHash(form.getOldPassword());
			history.setPropertyValue(oldPassword);
			history.setUserName(user.getUserId());
			userHistoryService.saveUserHistory(history);
			user.setLastLoggedInDate(new Date());
			userService.save(user);
			final String password = PasswordUtil.getPasswordHash(form.getNewPassword());
			CheckUser findByUserId = checkUserRepository.findByUserId(form.getLoginId());
			if (findByUserId == null) {
				CheckUser checkUser = new CheckUser();
				checkUser.setUserId(form.getLoginId());
				checkUser.setCheckUser(form.getNewPassword());
				checkUserRepository.save(checkUser);
			}
			user.setUserCredentials(password);
			user.setIsFirstLogin(false);
			userService.update(user);
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

	@ApiOperation(value = "Forgot password", notes = "Required mobile", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
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
		final String method = RestApiUrl.LOGIN_TAGS + RestApiUrl.FORGOT_PASSWORD_URL + " : forgotPassword()";
		LOG.info(method);
		final String requestData = "Data : [ " + otpForm + "]";
		LOG.info(requestData);

		final Response<String> response = new Response<String>();
		OtpId OtpIdSearch = new OtpId(otpForm.getMobileNo(), otpForm.getSource());
		Optional<Otp> otpDb = otpService.findByOtpId(OtpIdSearch);

		if (otpDb.isPresent() && otpDb.get().getOtpVal() != null && otpDb.get().getOtpVal().equals(otpForm.getOtp())) {

			final User user = userService.findByMobileNo(otpForm.getMobileNo());

			// Save User Password change history Details
			final UserHistory history = new UserHistory();

			history.setCreated(new Date());
			history.setCreatedBy(Long.valueOf(user.getUserId()));
			history.setPropertyName("Password");

			final String oldPassword = PasswordUtil.getPasswordHash(user.getUserCredentials());
			history.setPropertyValue(oldPassword);
			history.setUserName(user.getUserId());
			userHistoryService.saveUserHistory(history);

			final SMSRequest smsRequest = new SMSRequest();

			final String defaultPassword = Utility.getPassword(8);

			String message = "";

			message = MessageFormat.format(messageProperties.getForgotPasswordMessage(),
					new Object[] { user.getUserName(), defaultPassword });

			smsRequest.setTemplateId(messageProperties.getForgotPasswordTemplateId());
			smsRequest.setText(message);
			smsRequest.setMobileNo(otpForm.getMobileNo());
			System.out.println("defaultPassword:::" + defaultPassword);
			final String password = PasswordUtil.getPasswordHash(defaultPassword);
			user.setIsFirstLogin(true);
			user.setUserCredentials(password);
			userService.update(user);
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

	private void extractLoginAttemptFailure(Response response, User user)
			throws InvalidAuthenticationException, DataNotFoundException, UserException {
		Optional<UserLoginAttempts> attemptOptional = loginAttemptService.findByUser(user);

		Integer maxLoginAttempts = getMaxLoginAttempts();

		if (attemptOptional.isPresent()) {
			UserLoginAttempts attempt = attemptOptional.get();

			if (attempt.getAttempts() + 1 >= maxLoginAttempts) {

				Boolean b1 = true;
				user.setAccountNonLocked(b1);
				userService.update(user);
				response.setCode(ResponseCode.MAX_LOGIN_ATTEMPTS_FAIL);
				response.setMessage(ResponseMessage.ACCOUNT_LOCKED_MESSAGE);
				throw new InvalidAuthenticationException(response);
			} else {
				UserLoginAttempts attempts = getUserLoginAttempt(user, attempt);
				loginAttemptService.update(attempts);
				response.setCode(ResponseCode.LOGIN_ATTEMPTS_FAIL);
				response.setMessage("Password is wrong, \n Only " + (maxLoginAttempts - attempts.getAttempts())
						+ " login attempt remaining ");
				throw new InvalidAuthenticationException(response);
			}

		} else {

			UserLoginAttempts attempt = null;
			UserLoginAttempts attempts = getUserLoginAttempt(user, attempt);
			loginAttemptService.save(attempts);
			response.setCode(ResponseCode.LOGIN_ATTEMPTS_FAIL);
			response.setMessage("Password is wrong,\n Only " + (maxLoginAttempts - attempts.getAttempts())
					+ " login attempt remaining ");
			throw new InvalidAuthenticationException(response);
		}
	}

	@ApiOperation(value = "Get verification of User in the Application", notes = "pass user id and password", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.LOGIN_SUCCESSFULLY),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@PostMapping(RestApiUrl.LOGIN_VERIFICATION_URL)
	public ResponseEntity<Response<?>> verification(@RequestBody @Valid UserLoginForm loginForm,
			BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest request)
			throws FormValidationException, InvalidAuthenticationException, LoginDetailException, DataNotFoundException,
			UserException {

		// LOG.info(method);
		Response<User> response = new Response<>();
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
		}

		User user = null;

		try {
			user = userService.findByUserId(loginForm.getUserLoginId());
		} catch (DataNotFoundException d) {
			LOG.error("Invalid UserId : " + d);
			response = new Response<User>();
			response.setCode(ResponseCode.INVALID_USER_ID);
			response.setMessage(ResponseMessage.INVALID_USER_ID);
			throw new InvalidAuthenticationException(response);
		}

		userService.findByUserIdAndAccountNonLocked(loginForm.getUserLoginId(), ConstantProperty.ACCOUNT_LOCKED);

		userService.findByUserIdAndAccountNonExpired(loginForm.getUserLoginId(), ConstantProperty.ACCOUNT_LOCKED);

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginForm.getUserLoginId() + ":USER", loginForm.getUserPwd()));
			final JwtUser userDetails = (JwtUser) authentication.getPrincipal();

			if (userDetails == null) {

			} else {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(null, userDetails, request);

				final UserLoginHistory loginHistory = new UserLoginHistory();

				loginHistory.setUserId(loginForm.getUserLoginId());

				userloginHistoryService.save(loginHistory, request);

				response = new Response<>();
				response.setCode(ResponseCode.OK);
				response.setMessage(ResponseMessage.LOGIN_SUCCESSFULLY);

				List<User> userList = new ArrayList<>();
				userList.add(userDetails.getUser());

				loginAttemptService.delete(user);

				headers.set(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON);
				headers.set("authorization", token);
				headers.set("Access-Control-Expose-Headers", "authorization");

			}
		} catch (BadCredentialsException be) {
			response = new Response<User>();
			LOG.error("BadCredentialsException ::" + be);
			extractLoginAttemptFailure(response, user);

		} catch (AuthenticationException e) {
			response = new Response<User>();

			LOG.error("AuthenticationException ::", e);
			extractLoginAttemptFailure(response, user);

		}

		return ResponseEntity.ok().headers(headers).body(response);
	}

	@ApiOperation(value = "Retrieve specific User", notes = "Pass User Login Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_USER_BY_LOGIN_ID_URL)
	public ResponseEntity<Response<?>> retrieveSpecificUserByLoginId(@PathVariable String id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.GET_USER_BY_LOGIN_ID_URL
				+ " : retrieveSpecificUserByLoginId()";
		LOG.info(method);

		Response<User> response = new Response<>();

		User user = userService.findByUserId(id);

		response.setList(Arrays.asList(user));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve Users by Circle", notes = "Pass Circle Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_USERS_BY_CIRCLE_ID_URL)
	public ResponseEntity<Response<?>> retrieveUsersByCircleId(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.GET_USERS_BY_CIRCLE_ID_URL
				+ " : retrieveUsersByCircleId()";
		LOG.info(method);

		Response<User> response = new Response<>();

		List<User> user = userService.findUsersByCircleId(id);

		response.setList(user);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve specific User for view only", notes = "Pass User Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.LOGIN_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.VIEW_USER_URL)
	public ResponseEntity<Response<?>> retrieveUserForView(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.VIEW_USER_URL + " : retrieveUserForView()";
		LOG.info(method);
		Response<User> response = userService.findById(id);

		// DTO Class to return User data.
		UserDTO userDTO = new UserDTO();

		User user = response.getList().get(0);

		final ModelMapper mapper = new ModelMapper();
		mapper.map(user, userDTO);

		userDTO.setDiscomName("");

		if ("1".equals(user.getAccessLevel())) {
			userDTO.setAccessLevel("Discom Level");
		} else if ("2".equals(user.getAccessLevel())) {
			userDTO.setAccessLevel("Region Level");
		} else if ("3".equals(user.getAccessLevel())) {
			userDTO.setAccessLevel("Circle Level");
		} else if ("4".equals(user.getAccessLevel())) {
			userDTO.setAccessLevel("Division Level");
		} else if ("5".equals(user.getAccessLevel())) {
			userDTO.setAccessLevel("SubDivision Level");
		} else if ("6".equals(user.getAccessLevel())) {
			userDTO.setAccessLevel("DC Level");
		}

		// sandeep, start

		// // Access Level is Region Level or Circle Level
		// if ("2".equals(user.getAccessLevel()) || "3".equals(user.getAccessLevel())) {
		// userDTO.setRegionName(user.getUserRegion().getRegion());
		// }
		//
		// // Access Level is Circle Level
		// if ("3".equals(user.getAccessLevel())) {
		// userDTO.setCircleName(user.getUserCircle().getCircle());
		// }

		if ("1".equals(user.getAccessLevel())) {
			userDTO.setDiscomName(user.getUserDiscom().getDiscomName().trim());
		} else if ("2".equals(user.getAccessLevel())) {
			userDTO.setDiscomName(user.getUserDiscom().getDiscomName().trim());
			userDTO.setRegionName(user.getUserRegion().getRegion().trim());
		} else if ("3".equals(user.getAccessLevel())) {
			userDTO.setDiscomName(user.getUserDiscom().getDiscomName().trim());
			userDTO.setRegionName(user.getUserRegion().getRegion().trim());
			userDTO.setCircleName(user.getUserCircle().getCircle().trim());
		} else if ("4".equals(user.getAccessLevel())) {
			userDTO.setDiscomName(user.getUserDiscom().getDiscomName().trim());
			userDTO.setRegionName(user.getUserRegion().getRegion().trim());
			userDTO.setCircleName(user.getUserCircle().getCircle().trim());
			userDTO.setDivisionName(user.getUserDivision().getDivision().trim());
		} else if ("5".equals(user.getAccessLevel())) {
			userDTO.setDiscomName(user.getUserDiscom().getDiscomName().trim());
			userDTO.setRegionName(user.getUserRegion().getRegion().trim());
			userDTO.setCircleName(user.getUserCircle().getCircle().trim());
			userDTO.setDivisionName(user.getUserDivision().getDivision().trim());
			userDTO.setSubDivisionName(user.getUserSubDivision().getSubDivision().trim());
		} else if ("6".equals(user.getAccessLevel())) {
			userDTO.setDiscomName(user.getUserDiscom().getDiscomName().trim());
			userDTO.setRegionName(user.getUserRegion().getRegion().trim());
			userDTO.setCircleName(user.getUserCircle().getCircle().trim());
			userDTO.setDivisionName(user.getUserDivision().getDivision().trim());
			userDTO.setSubDivisionName(user.getUserSubDivision().getSubDivision().trim());
			userDTO.setDcName(user.getUserDc().getDcName().trim());
			userDTO.setSubstationName(user.getUserSubstation().getSubStationName().trim());
			userDTO.setFeederName(user.getUserFeeder().getFeederName().trim());
		}
		// sandeep, end

		// userDTO.setDesignationName(user.getUserDesignation().getDesignation());

		Response<UserDTO> response2 = new Response<UserDTO>();
		List<UserDTO> list = new ArrayList<UserDTO>();
		list.add(userDTO);
		response2.setList(list);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response2);

	}

	@PostMapping("/user_login")
	public ResponseEntity<Response> userLogin(@RequestBody @Valid UserLoginOtpForm userLoginOtpForm,
			BindingResult bindingResult, HttpServletRequest request) throws Exception {

		Response response = new Response();

		String userId = userLoginOtpForm.getUserLoginId();
		String userPwd = userLoginOtpForm.getUserPwd();

		if (userId == null || userId.isEmpty()) {
			response.setCode("400");
			response.setMessage("User Id should not be null");
			return ResponseEntity.ok().body(response);
		}

		if (userPwd == null || userPwd.isEmpty()) {
			response.setCode("400");
			response.setMessage("Password should not be null");
			return ResponseEntity.ok().body(response);
		}

		System.err.println("Before Entering database : " + LocalDateTime.now());
		Optional<User> user = userRepository.findByUserId1(userLoginOtpForm.getUserLoginId());
		if (!user.isPresent()) {
			response.setCode("404");
			response.setMessage(ResponseMessage.USER_DETAILS_NOT_FOUND);
			return ResponseEntity.ok().body(response);
		}
		System.err.println("After getting data back from  database : " + LocalDateTime.now());
		if (BCrypt.checkpw(userPwd, user.get().getUserCredentials())) {
			System.out.println("Password matches!");
			userUpdatePassword.saveUpdatedPasswordInCheckUser(userId, userPwd);
			System.err.println("Checking for authentication : " + LocalDateTime.now());
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userId + ":USER", userPwd));
			final JwtUser userDetails = (JwtUser) authentication.getPrincipal();

			System.err.println("Checking for token : " + LocalDateTime.now());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(null, userDetails, request);

			System.err.println("Token get : " + token + "Time : " + LocalDateTime.now());
			final HttpHeaders headers = new HttpHeaders();
			headers.set(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON);
			headers.set("authorization", token);
			headers.set("Access-Control-Expose-Headers", "authorization");

			response.setCode("200");
			response.setMessage("Login Successfully");
			response.setToken(token);
			response.setList(Arrays.asList(user));

			return ResponseEntity.ok().headers(headers).body(response);

		} else {
			response.setCode("404");
			response.setMessage("Invalid credentials");
			return ResponseEntity.ok().body(response);
		}

	}

	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<Response<?>> getUserById(@PathVariable String userId, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.GET_USERS_BY_CIRCLE_ID_URL
				+ " : retrieveUsersByCircleId()";
		LOG.info(method);

		Response<User> response = new Response<>();

		Optional<User> findByUserId = userRepository.findByUserId(userId);

		if (!findByUserId.isPresent()) {
			response.setCode("404");
			response.setMessage("User Not Found With This UserId");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}

		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(findByUserId.get()));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getUserRoleByCricleId/{circleId}")
	public ResponseEntity<Response<?>> getUserBycircleId(@PathVariable Long circleId,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.GET_USERS_BY_CIRCLE_ID_URL
				+ " : retrieveUsersByCircleId()";
		LOG.info(method);

		Response response = new Response<>();

		Map<String, String> findRoleByCricleId = userRoleRepository.findRoleByCricleId(circleId);

		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(findRoleByCricleId));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getUserDetailsById/{userId}")
	public ResponseEntity<?> getUserDetailsById(@PathVariable String userId) throws UserException {
		User userDetailsById = userRepository.findByUserId(userId).orElseThrow(
				() -> new UserException(new Response(HttpCode.NULL_OBJECT, "User Not found for this userId")));

		return ResponseEntity.ok(Objects.isNull(userDetailsById) ? new Response(HttpCode.NULL_OBJECT, "Data Not Found")
				: new Response(HttpCode.OK, "Data Retreived Successfully", Arrays.asList(userDetailsById)));

	}
	
//	This api is created for Animesh Sir portal on 04-09-2025 Monika Rajpoot
	@GetMapping("/getUserByUserId/{userId}")
	public ResponseEntity<Response<?>> getUserByMobileNo(@PathVariable String userId,
			HttpServletResponse httpServletResponse, HttpServletRequest request)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.GET_USERS_BY_CIRCLE_ID_URL
				+ " : retrieveUsersByCircleId()";
		LOG.info(method);

		Response<User> response = new Response<>();
		
		System.err.println("aaaaaaaaaa : " +userId);

		Optional<User> findByConusmerMobileNo = userRepository.findByUserId(userId);

		if (!findByConusmerMobileNo.isPresent()) {
			response.setCode("100");
			response.setMessage("User Not Found With This UserId");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}
		CheckUser findByConsumerNumber2 = checkUserRepository.findByUserId(userId);
		String token = null;
		if (!Objects.isNull(findByConsumerNumber2)) {
			if (BCrypt.checkpw(findByConsumerNumber2.getCheckUser(),
					findByConusmerMobileNo.get().getUserCredentials())) {
//				ConsumerLoginToken consumerLoginToken = new ConsumerLoginToken();

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(findByConsumerNumber2.getUserId() + ":USER",
								findByConsumerNumber2.getCheckUser()));
				final JwtUser userDetails = (JwtUser) authentication.getPrincipal();

				SecurityContextHolder.getContext().setAuthentication(authentication);
				token = jwtTokenUtil.generateToken(null, userDetails, request);
			}
		}
		response.setCode("200");
		response.setMessage("User found");
		response.setList(Arrays.asList(findByConusmerMobileNo.get()));
		response.setToken(token);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}
}
