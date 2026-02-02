package com.mpcz.deposit_scheme.backend.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "WorkTypeController", description = "Rest api for Work Type.")
@RestController
@RequestMapping(RestApiUrl.CHANGE_STATUS_API)
public class ChangeTheApplicationStatusController {
	
	@Autowired
	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
	
	@Autowired
	ApplicationStatusService applicationStatusService;

	//Logger LOG = LoggerFactory.getLogger(ChangeTheApplicationStatusController.class);

	@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@GetMapping("/get")
	public ResponseEntity<Response<?>> addWorkType( HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, WorkTypeException {

		System.out.println("addWorkType !!!!!");

		final String method = RestApiUrl.WORK_TYPE_API + RestApiUrl.ADD_URL + " : addWorkType()";
		//LOG.info(method);

		Response<WorkType> response = new Response<>();
		
		  ApplicationStatus appStatusDb=null;
		
		String paymentDate=null;
		List<ConsumerApplicationDetail> cad = consumerApplictionDetailRepository.findConsumerStatus();
		for(ConsumerApplicationDetail cadd:cad) {
			
			paymentDate = cadd.getPaymentDate();

	
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currDate = currentDate.format(formatter1);
        System.out.println("Current date: " + currDate);

		//String paymentDate="23-05-2023";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		
		
        LocalDate localDate1 = LocalDate.parse(currDate, formatter);
        LocalDate localDate2;
        if(paymentDate!=null) {
         localDate2 = LocalDate.parse(paymentDate, formatter);
        }else {
        	paymentDate="23-05-2024";
        	  localDate2 = LocalDate.parse(paymentDate, formatter);
        }
        if (localDate1.isBefore(localDate2)) {
            System.out.println(localDate1 + " is before " + localDate2);
        } else if (localDate1.isAfter(localDate2)) {
            System.out.println(localDate1 + " is after " + localDate2);
            
             appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
             cadd.setApplicationStatus(appStatusDb);
            
             consumerApplictionDetailRepository.save(cadd);
        } else {
            System.out.println(localDate1 + " is the same as " + localDate2);
            
            appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
             cadd.setApplicationStatus(appStatusDb);
             
             consumerApplictionDetailRepository.save(cadd);
        }
    
		}

		//response.setList(Arrays.asList());
		response.setCode(HttpCode.OK);
		response.setMessage("Record Inserted Successfully");
	

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
}
