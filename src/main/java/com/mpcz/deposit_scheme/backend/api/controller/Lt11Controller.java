package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt;
import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt11;
import com.mpcz.deposit_scheme.backend.api.dto.CheckBoxHLt11DTO;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.Lt11Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Lt11Controller", description = "Rest api for Lt11Controller .")
@RestController
@RequestMapping(RestApiUrl.LT_11_API)
public class Lt11Controller {
	
	@Autowired
	private Lt11Service lt11Service;
	
	Logger LOG = LoggerFactory.getLogger(Lt11Controller.class);
	
	@ApiOperation(value = "save lt11 check box details", notes = "save lt11 check box details",response = Response.class,responseContainer = "List", tags = RestApiUrl.LT_11_API )
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> save(@RequestBody CheckBoxHLt11DTO boxLt11DTO,
			BindingResult bindingResult,HttpServletResponse httpServletResponse) throws FormValidationException{
		final String method = RestApiUrl.DTR_API + RestApiUrl.ADD_URL + "  : save()";
		
		Response<CheckBoxLt11> response =new Response();
		
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
		CheckBoxLt11 lt11 =new CheckBoxLt11();
		
		ModelMapper convetLt11DtoToLt11Demoain = new ModelMapper();
		convetLt11DtoToLt11Demoain.map(boxLt11DTO, lt11);
		
		CheckBoxLt11 lt11db=	lt11Service.save(lt11);
			
		
		if(lt11db!=null) {
			response = new Response<>();
			response.setMessage(ResponseMessage.SUCCESS);
			response.setCode(ResponseCode.OK);
			response.setList(Arrays.asList(lt11db));
			return  ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		
		
		return null;
	}
	
	
	
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveDtr(@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException {

		 final String method = RestApiUrl.DTR_API + RestApiUrl.GET_URL + " : retrieveDtr()";
		 LOG.info(method);
		 CheckBoxLt11 dtrdb=	lt11Service.getlt11Details(id);
		 Response<CheckBoxLt11> response =new Response<CheckBoxLt11>();
		 response = new Response<>();
			response.setMessage(ResponseMessage.SUCCESS);
			response.setCode(ResponseCode.OK);
			response.setList(Arrays.asList(dtrdb));
			return  ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		
	}
}