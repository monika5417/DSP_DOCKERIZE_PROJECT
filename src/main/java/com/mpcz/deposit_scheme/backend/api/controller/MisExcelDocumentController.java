package com.mpcz.deposit_scheme.backend.api.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.dto.MisExcelData;
import com.mpcz.deposit_scheme.backend.api.dto.MisExcelHeader;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.MisExcelDataException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.MisExcelDataService;
import com.mpcz.deposit_scheme.backend.api.util.ExcelGenerator;
import com.mpcz.deposit_scheme.backend.api.util.ExcelGeneratorHeader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "MisExcelDocumentController", description = "Rest api for Creating Excel Consumer Application Detail.")
@RestController
@RequestMapping(RestApiUrl.MIS_EXCEL_DETAIL_API)
public class MisExcelDocumentController {

	Logger LOG = LoggerFactory.getLogger(MisExcelDocumentController.class);
	
	@Autowired
	MisExcelDataService misExcelDataService;
	
	@ApiOperation(value = "Get Excel Of Consumer Payment Charges Details", notes = "Get Excel Of Payment Charges Details", response = Response.class, responseContainer = "List", tags = RestApiUrl.MIS_EXCEL_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@GetMapping(RestApiUrl.GET_PDF_URL)
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException, ConsumerApplicationDetailException, MisExcelDataException, PaymentHistoryException, DistributionCenterException, SubDivisionException, DivisionException, CircleException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=consumerApplicationStatus" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
         List<MisExcelData> misExcelDetails = misExcelDataService.findAllDetails();
         Double totalOfRegistrationCharges = misExcelDataService.totalOfRegistrationCharges(misExcelDetails);
        ExcelGenerator generator = new ExcelGenerator(misExcelDetails,totalOfRegistrationCharges);
        generator.generateExcelFile(response);
        
}
	
	@ApiOperation(value = "Get Excel Of Consumer Demand  Details", notes = "Get Excel Of Demand Details", response = Response.class, responseContainer = "List", tags = RestApiUrl.MIS_EXCEL_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@GetMapping(RestApiUrl.GET_HEADER_URL)
    public void exportHeaderDetailsIntoExcelFile(HttpServletResponse response) throws IOException, ConsumerApplicationDetailException, MisExcelDataException, PaymentHistoryException, DistributionCenterException, SubDivisionException, DivisionException, CircleException, DemandDetailException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=consumerApplicationStatus" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
       
         List<MisExcelHeader> misExcelHeaderDetails = misExcelDataService.findAllHeaderDetails();
         List<Double> listOfDemandChargesDetails =  misExcelDataService.totalOfDemandChargesDetails(misExcelHeaderDetails);
         
        ExcelGeneratorHeader generator = new ExcelGeneratorHeader(misExcelHeaderDetails,listOfDemandChargesDetails);
        generator.generateExcelFile(response);
	
	
}
	
	
}	
	
	
	
	
	
	
	
