//package com.mpcz.deposit_scheme.backend.api.controller;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
//import com.mpcz.deposit_scheme.backend.api.domain.User;
//import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
//import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
//import com.mpcz.deposit_scheme.backend.api.request.UserSignUpForm;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.UserServiceOld;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@CrossOrigin(origins ="*",maxAge = 3600 )
//@Api(value = "UserSingUpController", description = "Rest api for user sing up API.")
//@RestController
//@RequestMapping(RestApiUrl.USER_TYPE_API)
//public class UserSingUpController {
//	
//	@Autowired
//	private UserServiceOld userService;
//	
//	Logger LOG = LoggerFactory.getLogger(UserSingUpController.class);
//	
//	@ApiOperation(value = "For Consumer Registration.", notes = "Pass Consumer Basic details.", response = Response.class, responseContainer = "List", tags = RestApiUrl.SIGNUP_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.SIGNUP_FORM_VIEW_SUCCESS),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
//	@PostMapping(RestApiUrl.SIGNUP_URL)
//public ResponseEntity<Response<?>> UserSignUp(@RequestBody @Valid UserSignUpForm userSingUpForm,
//			BindingResult bindingResult) throws Exception {
//		System.out.println("UserSingUpController !!!!!");
//		final String method = RestApiUrl.CONSUMER_SIGNUP_API + RestApiUrl.SIGNUP_URL + " : UserSignUp()";
//		LOG.info(method);
//
//		Response<User> response = new Response<>();
//
//		if (bindingResult.hasErrors()) {
//			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
//
//			bindingResult.getFieldErrors().stream().forEach(f -> {
//
//				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
//
//				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
//						f.getField() + ":" + f.getDefaultMessage());
//
//				errorList.add(error);
//			});
//			response = new Response<>();
//			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
//			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//			response.setError(errorList);
//			throw new FormValidationException(response);
//		}  
//
//		User user = userService.saveUser(userSingUpForm);
//
//		response.setList(Arrays.asList(user));
//		response.setCode(HttpCode.OK);
//		response.setMessage("Record Inserted Successfully");
//
//
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}
//
//
//}
