package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.TariffCategory;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.TariffCategoryException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.TariffCategoryForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.TariffCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "TariffCategoryController", description = "Rest api for TariffCategory.")
@RestController
@RequestMapping(RestApiUrl.TARIFF_CATEGORY_API)
public class TariffCategoryController {

	Logger LOG = LoggerFactory.getLogger(TariffCategoryController.class);

	@Autowired
	private TariffCategoryService tariffCategoryService;

	@ApiOperation(value = "Tariff Category details", notes = "Pass Tariff Category Name, Code & Description", response = Response.class, responseContainer = "List", tags = RestApiUrl.TARIFF_CATEGORY_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addTariffCategory(@RequestBody @Valid TariffCategoryForm tariffCategoryForm,

			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, TariffCategoryException {

		System.out.println("addTariffCategory !!!!!");

		final String method = RestApiUrl.TARIFF_CATEGORY_API + RestApiUrl.ADD_URL + " : addTariffCategory()";
		LOG.info(method);

		Response<TariffCategory> response = new Response<>();

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

		TariffCategory tariffCategory = tariffCategoryService.saveTariffCategory(tariffCategoryForm);

		response.setList(Arrays.asList(tariffCategory));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve Specific Tariff Category", notes = "Pass Tariff Category Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.TARIFF_CATEGORY_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveTariffCategory(@PathVariable Long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, TariffCategoryException {

		System.out.println("retrieveTariffCategory !!!!!");

		final String method = RestApiUrl.TARIFF_CATEGORY_API + RestApiUrl.GET_URL + " : retrieveTariffCategory()";
		LOG.info(method);

		Response<TariffCategory> response = new Response<>();

		TariffCategory tariffCategory = tariffCategoryService.findByTariffCategoryId(id);

		response.setList(Arrays.asList(tariffCategory));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve all Tariff Category", notes = "Fetch all Tariff Category", response = Response.class, responseContainer = "List", tags = RestApiUrl.TARIFF_CATEGORY_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_URL)
	public ResponseEntity<Response<?>> retrieveAllTariffCategory(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, TariffCategoryException {

		System.out.println("retrieveAllTariffCategory !!!!!");

		final String method = RestApiUrl.TARIFF_CATEGORY_API + RestApiUrl.GET_ALL_URL
				+ " : retrieveAllTariffCategory()";
		LOG.info(method);

		Response<TariffCategory> response = new Response<>();

		List<TariffCategory> tariffCategory = tariffCategoryService.findAllTariffCategory();

		response.setList(tariffCategory);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}