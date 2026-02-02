//package com.mpcz.deposit_scheme.backend.api.controller;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
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
//import com.mpcz.deposit_scheme.backend.api.domain.Discom;
//import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
//import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
//import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
//import com.mpcz.deposit_scheme.backend.api.request.DiscomForm;
//import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.DiscomService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@Api(value = "DiscomController", description = "Rest api for Discom.")
//@RestController
//@RequestMapping(RestApiUrl.DISCOM_API)
//public class DiscomController {
//
//	Logger LOG = LoggerFactory.getLogger(ApplicationTypeController.class);
//
//	@Autowired
//	private DiscomService discomService;
//
//	@ApiOperation(value = "Save Discom details", notes = "Pass Discom Name and Discom Code.", response = Response.class, responseContainer = "List", tags = RestApiUrl.DISCOM_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
//	@PostMapping(RestApiUrl.ADD_URL)
//	public ResponseEntity<Response<?>> addDiscom(@RequestBody @Valid DiscomForm discomForm,
//
//			BindingResult bindingResult, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, DiscomException {
//
//		System.out.println("addDiscom !!!!!");
//
//		final String method = RestApiUrl.DISCOM_API + RestApiUrl.ADD_URL + " : addDiscom()";
//		LOG.info(method);
//
//		Response<Discom> response = new Response<>();
//
//		if (bindingResult.hasErrors()) {
//			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
//
//			bindingResult.getFieldErrors().stream().forEach(f -> {
//				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
//				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
//						f.getField() + ":" + f.getDefaultMessage());
//				errorList.add(error);
//			});
//			response = new Response<>();
//			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
//			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//			response.setError(errorList);
//			throw new FormValidationException(response);
//		} // end of form validation
//
//		Discom discom = discomService.saveDiscom(discomForm);
//
//		response.setList(Arrays.asList(discom));
//		response.setCode(HttpCode.OK);
//		response.setMessage("Record Inserted Successfully");
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}
//
//}
