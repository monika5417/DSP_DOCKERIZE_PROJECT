//package com.mpcz.deposit_scheme.builddesk;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mpcz.deposit_scheme.backend.api.constant.ConstantProperty;
//import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//
//
//@RestController
//@RequestMapping(RestApiUrl.BILL_DESK_TYPE_API)
//public class BillPaymentProcessController{
//	
//	@Autowired(required=false)
//	BillDeskPaymentService billPaymentService;
//	
//@PostMapping("/billPaymentProcess")
//public ResponseEntity<Response<String>> BillPaymentProcessController(@RequestBody @Valid CustomerBillDTO billDto, HttpServletRequest request) throws Exception {
//	final String method = "PaymentGatewayController : billPaymentProcess2() method";
//	Response<String> response = new Response<String>();
//	List<String> list = new ArrayList<>();
//	//paytm disable code recommended by lakesh sir  20-01-2022
////	if (billDto.getVendorId().equals("WEB101") || billDto.getVendorId().equals("WAP101") ) {
////		response.setMessage("Currently Paytm Payment Gateway is Disabled");
////	} else {
////		if (bindingResult.hasErrors()) {
////			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
////			//logger.error("Error in validation");
////
////			bindingResult.getFieldErrors().stream().forEach(f -> {
////				//logger.error("Error in validation" + f.getField() + ": " + f.getDefaultMessage());
////				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
////						f.getField() + ":" + f.getDefaultMessage());
////				errorList.add(error);
////
////			});
////			//response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR_MESSAGE);
////			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
////			response.setError(errorList);
////		//	logger.error(ResponseMessage.FORM_VALIDATION_ERROR_MESSAGE + response);
////			throw new FormValidationException(response);
////		}
//
////		if (Objects.nonNull(billDto)) {
////
////			Optional<List<Invoice1>> invoices = invoiceService.findPaymentTransactionAlreadyInitiated(
////					billDto.getCustomerId(), billDto.getBillMonth(), new BigDecimal(billDto.getOutstandingAmt()));
//////			
//////			Optional<List<Invoice>> invoices = invoiceService.findPaymentTransactionAlreadyInitiated(
//////					billDto.getCustomer_id(), billDto.getBillMonth(), new BigDecimal(billDto.getOutstandingAmt()));
////			
////			if (invoices.isPresent()) {
////				//logger.error(ResponseMessage.DUPLICATE_TRANSACTION_MESSAGE + " : Bill DTO : " + billDto);
////				response.setCode(ResponseCode.DUPLICATE_TRANSACTION);
////				response.setMessage(ResponseMessage.DUPLICATE_TRANSACTION_MESSAGE);
////				throw new DuplicateTransactionException(response);
////			}
//
//			response = billPaymentService.prePaymentProcessingBillDesk(billDto, list, response, request);
//
//			return ResponseEntity.ok().header(ConstantProperty.APPLICATION_JSON).body(response);
//		}
//
//}// end of billPaymentProcess method
