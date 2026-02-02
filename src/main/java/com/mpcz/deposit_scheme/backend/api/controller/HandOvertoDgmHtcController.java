package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.DgmHtcDtr;
import com.mpcz.deposit_scheme.backend.api.domain.DgmHtcHt11Kv;
import com.mpcz.deposit_scheme.backend.api.domain.DgmHtcHt132Kv;
import com.mpcz.deposit_scheme.backend.api.domain.DgmHtcHt33Kv;
import com.mpcz.deposit_scheme.backend.api.domain.DgmHtcLt;
import com.mpcz.deposit_scheme.backend.api.domain.DgmHtcPtr;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.repository.DgmHtcDtrRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DgmHtcHt11KvReopository;
import com.mpcz.deposit_scheme.backend.api.repository.DgmHtcHt132KvRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DgmHtcHt33KvRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DgmHtcLtRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DgmHtcPtrRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ApplicationSurveyController", description = "Rest api for Consumer Application Detail.")
@RestController
@RequestMapping(RestApiUrl.HANDOVER_DGMHTC_TAG_API)
public class HandOvertoDgmHtcController {

		Logger logger = LoggerFactory.getLogger(ApplicationSurveyController.class);
		
		
		@Autowired
		private DgmHtcDtrRepository dgmHtcDtrRepository;
		
		@Autowired
		private DgmHtcPtrRepository dgmHtcPtrRepository;
		
		@Autowired
		private DgmHtcLtRepository dgmHtcLtRepository;
		
		@Autowired
		private DgmHtcHt11KvReopository dgmHtcHt11KvReopository;
		
		@Autowired
		private DgmHtcHt33KvRepository dgmHtcHt33KvReopository;
		
		@Autowired
		private DgmHtcHt132KvRepository dgmHtcHt132KvReopository;

		
		
		

		@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
		@ApiResponses(value = {
				@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
				@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
				@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
				@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
				@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
				@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
				@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
		@PostMapping(RestApiUrl.ADD_URL_DTR)
		public ResponseEntity<Response<?>> addDtr(@RequestBody  DgmHtcDtr dgmHtcForm,
				BindingResult bindingResult, HttpServletResponse httpServletResponse)
				throws FormValidationException, InvalidAuthenticationException {

			Response<DgmHtcDtr> response = new Response<>();
			
			
			DgmHtcDtr save = dgmHtcDtrRepository.save(dgmHtcForm);

			response.setList(Arrays.asList(save));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		
		@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
		@ApiResponses(value = {
				@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
				@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
				@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
				@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
				@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
				@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
				@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
		@PostMapping(RestApiUrl.ADD_URL_PTR)
		public ResponseEntity<Response<?>> addPtr(@RequestBody @Valid DgmHtcPtr dgmHtcForm,
				BindingResult bindingResult, HttpServletResponse httpServletResponse)
				throws FormValidationException, InvalidAuthenticationException {

			Response<DgmHtcPtr> response = new Response<>();
			
			
			DgmHtcPtr save = dgmHtcPtrRepository.save(dgmHtcForm);

			response.setList(Arrays.asList(save));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
		@ApiResponses(value = {
				@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
				@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
				@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
				@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
				@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
				@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
				@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
		@PostMapping(RestApiUrl.ADD_URL_Lt)
		public ResponseEntity<Response<?>> addLt(@RequestBody @Valid DgmHtcLt dgmHtcForm,
				BindingResult bindingResult, HttpServletResponse httpServletResponse)
				throws FormValidationException, InvalidAuthenticationException {

			Response<DgmHtcLt> response = new Response<>();
			
			
			DgmHtcLt save = dgmHtcLtRepository.save(dgmHtcForm);

			response.setList(Arrays.asList(save));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		
		@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
		@ApiResponses(value = {
				@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
				@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
				@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
				@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
				@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
				@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
				@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
		@PostMapping(RestApiUrl.ADD_URL_11Kv)
		public ResponseEntity<Response<?>> addHt11Kv(@RequestBody @Valid DgmHtcHt11Kv dgmHtcForm,
				BindingResult bindingResult, HttpServletResponse httpServletResponse)
				throws FormValidationException, InvalidAuthenticationException {

			Response<DgmHtcHt11Kv> response = new Response<>();
			
			
			DgmHtcHt11Kv save = dgmHtcHt11KvReopository.save(dgmHtcForm);

			response.setList(Arrays.asList(save));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		
		@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
		@ApiResponses(value = {
				@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
				@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
				@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
				@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
				@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
				@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
				@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
		@PostMapping(RestApiUrl.ADD_URL_33Kv)
		public ResponseEntity<Response<?>> addHt33Kv(@RequestBody  DgmHtcHt33Kv dgmHtcForm,
				BindingResult bindingResult, HttpServletResponse httpServletResponse)
				throws FormValidationException, InvalidAuthenticationException {

			Response<DgmHtcHt33Kv> response = new Response<>();
			
			
			DgmHtcHt33Kv save = dgmHtcHt33KvReopository.save(dgmHtcForm);

			response.setList(Arrays.asList(save));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		
		@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
		@ApiResponses(value = {
				@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
				@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
				@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
				@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
				@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
				@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
				@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
		@PostMapping(RestApiUrl.ADD_URL_132Kv)
		public ResponseEntity<Response<?>> addHt132Kv(@RequestBody @Valid DgmHtcHt132Kv dgmHtcForm,
				BindingResult bindingResult, HttpServletResponse httpServletResponse)
				throws FormValidationException, InvalidAuthenticationException {

			Response<DgmHtcHt132Kv> response = new Response<>();
			
			
			
			
			DgmHtcHt132Kv save = dgmHtcHt132KvReopository.save(dgmHtcForm);

			response.setList(Arrays.asList(save));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}


		

}
