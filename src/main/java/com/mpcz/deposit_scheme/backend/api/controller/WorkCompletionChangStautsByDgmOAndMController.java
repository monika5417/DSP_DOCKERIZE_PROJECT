package com.mpcz.deposit_scheme.backend.api.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import com.mpcz.deposit_scheme.backend.api.domain.VendorWorkOrder;
import com.mpcz.deposit_scheme.backend.api.domain.WorkCompletionChangStautsByDgmOAndM;
import com.mpcz.deposit_scheme.backend.api.dto.WorkCompletionChangStautsByDgmOAndMDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.repository.WorkCompletionChangStautsByDgmOAndMRepository;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.WorkCompletionChangStautsByDgmOAndMService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(RestApiUrl.WORK_COMPLECATION_API)
public class WorkCompletionChangStautsByDgmOAndMController {

	Logger LOG = LoggerFactory.getLogger(WorkCompletionChangStautsByDgmOAndMController.class);

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMService sorkCompletionChangStautsByDgmOAndMService;

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMRepository workCompletionChangStautsByDgmOAndMRepo;

	@ApiOperation(value = "Save Work Completion Chang Stauts By Dgm O And M Controller", notes = "Pass Work Completion Chang Stauts By DgmO And M Controller information", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_COMPLECATION_API)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> changeWorkCompletionStatus(
			@RequestBody @Valid WorkCompletionChangStautsByDgmOAndMDto workCompletionChangStautsByDgmOAndMDto,
			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException, ErpEstimateAmountException {

		final String method = RestApiUrl.WORK_COMPLECATION_API + RestApiUrl.ADD_URL
				+ "  : changeWorkCompletionStatus()";

		Response<WorkCompletionChangStautsByDgmOAndM> response = null;

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

		// end of form validation

		final WorkCompletionChangStautsByDgmOAndM workCompletionChangStautsByDgmOAndM = new WorkCompletionChangStautsByDgmOAndM();

		ModelMapper mapper = new ModelMapper();
		mapper.map(workCompletionChangStautsByDgmOAndMDto, workCompletionChangStautsByDgmOAndM);

		WorkCompletionChangStautsByDgmOAndM WorkCompletionChangStautsByDgmOAndMDB = sorkCompletionChangStautsByDgmOAndMService
				.saveAndChange(workCompletionChangStautsByDgmOAndM);
		response = new Response<>();
		response.setMessage(ResponseMessage.SUCCESS);
		response.setCode(ResponseCode.CREATED);
		response.setList(Arrays.asList(WorkCompletionChangStautsByDgmOAndMDB));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Find All Work Order Information", response = VendorWorkOrder.class)
	@GetMapping("/get/{id}")
	public ResponseEntity<Response> findById(@PathVariable long id) {
		Optional<WorkCompletionChangStautsByDgmOAndM> findById = workCompletionChangStautsByDgmOAndMRepo.findById(id);

		WorkCompletionChangStautsByDgmOAndM workCompletionChangStautsByDgmOAndM = findById.get();

		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(workCompletionChangStautsByDgmOAndM));
		return ResponseEntity.ok().body(response);

	}
}
