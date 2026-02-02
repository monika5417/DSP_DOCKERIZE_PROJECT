//
//package com.mpcz.deposit_scheme.backend.api.controller;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mpcz.deposit_scheme.backend.api.builddesk.CustomerBillDTO;
//import com.mpcz.deposit_scheme.backend.api.constant.ConstantProperty;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
//import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
//import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentRequest;
//import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentResponse;
//import com.mpcz.deposit_scheme.backend.api.dto.PayUPaymentRequestDto;
//import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
//import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
//import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
//import com.mpcz.deposit_scheme.backend.api.exception.InvoiceException;
//import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
//import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.InvoiceService;
//import com.mpcz.deposit_scheme.backend.api.services.PayUPaymentResponseService;
//import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
//import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
//
///**
// * @author charitra
// *
// *         3-sep-2021
// */
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping(RestApiUrl.PAYMENT_API)
//public class PaymentGatewayController {
//
//	@Autowired
////	@Qualifier("invoiceService")
//	private InvoiceService invoiceService;
//
////	@Autowired
////	BillPaymentService billPaymentService;
//
//	@Autowired
//	MessageProperties messageProperties;
//
//	@Autowired
//	SMSDirectService smsDirectService;
//
//	@Autowired
//	PayUPaymentResponseService payUPaymentResponseService;
//
//	// PAY U payment gateway
//	@Value("${payu.key}")
//	private String payuKey;
//
//	@Value("${payu.salt}")
//	private String payuSalt;
//
//	@Value("${payu.surl}")
//	private String payuSurl;
//
//	@Value("${payu.furl}")
//	private String payuFurl;
//
//	@Value("${payu.merchantId}")
//	private String merchantId;
//
//	@Value("${payu.securityId}")
//	private String securityId;
//
//	Logger logger = LoggerFactory.getLogger(PaymentGatewayController.class);
//
//	// payu api code start here
//
//	@PostMapping(RestApiUrl.INITIA_PAY_SUCCESS)
//	public ResponseEntity<Response<PayUPaymentRequestDto>> consumerPaymentProcess(
//			@RequestBody @Valid CustomerBillDTO billDto, BindingResult bindingResult, HttpServletRequest request)
//			throws FormValidationException, InvoiceException, PaymentHistoryException,
//			ConsumerApplicationDetailException, ApplicationHeadChargesException {
//		final String method = "PaymentGatewayController : consumerPaymentProcess() method";
//		logger.error(method);
//		logger.error("consumer  PreProcessing Dto Response " + billDto);
//		Response<PayUPaymentRequest> response = new Response<PayUPaymentRequest>();
//		Response<PayUPaymentRequestDto> response2 = new Response<PayUPaymentRequestDto>();
//		List<PayUPaymentRequest> list = new ArrayList<>();
//
//		if (bindingResult.hasErrors()) {
//			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
//			logger.error("Error in validation");
//
//			bindingResult.getFieldErrors().stream().forEach(f -> {
//				logger.error("Error in validation" + f.getField() + ": " + f.getDefaultMessage());
//				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
//						f.getField() + ":" + f.getDefaultMessage());
//				errorList.add(error);
//
//			});
//			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
//			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//			response.setError(errorList);
//			logger.error(ResponseMessage.FORM_VALIDATION_ERROR + response);
//			throw new FormValidationException(response);
//		}
//
//		if (Objects.nonNull(billDto)) {
//
////				Optional<List<Invoice>> invoices = invoiceService.findPaymentTransactionAlreadyInitiated(
////						billDto.getConsumerApplicationNumber(), new BigDecimal(billDto.getTotalAmount()));
////
////				if (invoices.get().size() > 1) {
////					logger.error(ResponseMessage.DUPLICATE_TRANSACTION_MESSAGE + " : Bill DTO : " + billDto);
////					response.setCode(ResponseCode.DUPLICATE_TRANSACTION);
////					response.setMessage(ResponseMessage.DUPLICATE_TRANSACTION_MESSAGE);
////					throw new DuplicateTransactionException(response);
////				}
//
////			response = billPaymentService.prePaymentProcessingPayU(billDto, list, response, request);
//
//			System.err.println("inside controller pay u : " + response.getList());
//
//			PayUPaymentRequestDto dto = new PayUPaymentRequestDto();
//			dto.setAmount(response.getList().get(0).getAmount());
////			dto.setAmount(new BigDecimal(response.getList().get(0).getAmount()).toString());
//			dto.setEmail(response.getList().get(0).getEmail());
//			dto.setFirstName(response.getList().get(0).getFirstName());
//
//			dto.setFurl(payuFurl);
//			dto.setHash(response.getList().get(0).getHash());
//			dto.setKey(payuKey);
//			dto.setMobileNumber(response.getList().get(0).getMobileNumber());
//			dto.setLastName(response.getList().get(0).getLastName());
//			dto.setPrductInfo(response.getList().get(0).getPrductInfo());
//			dto.setSurl(payuSurl);
////				transcation id =transcation number
//
//			dto.setTxnId(response.getList().get(0).getTxnId());
//			dto.setUdf1(response.getList().get(0).getUdf1());
//			dto.setPaymentStatus(response.getList().get(0).getPaymentStatus());
//			dto.setPaymentStatusCode(response.getList().get(0).getPaymentStatusCode());
//
////				  udf1 me consumer name ja raha hai
////			      udf2 me consumer ki id ja rahi hai 
////			      udf3 me consumer ki application number ja rahi
////				  udf4 me consumer ki trancation id ja rahi hai jo humne gernerate ki hai invoice table wali transcation id
////				  udf me invoice id ja rahai hai invoice table se nikal kar consumer application ke bihaf par
//
//			dto.setUdf2(response.getList().get(0).getUdf2());
//			dto.setUdf2(response.getList().get(0).getUdf2());
//			dto.setUdf3(response.getList().get(0).getUdf3());
//			dto.setUdf4(response.getList().get(0).getUdf4());
//			dto.setUdf5(response.getList().get(0).getUdf5());
//			dto.setMerchantId(response.getList().get(0).getMerchantId());
//
//			ArrayList<PayUPaymentRequestDto> lst = new ArrayList<PayUPaymentRequestDto>();
//			lst.add(dto);
//			response2.setCode(response.getCode());
//			response2.setError(response.getError());
//			response2.setMessage(response.getMessage());
//			response2.setList(lst);
//			return ResponseEntity.ok().header(ConstantProperty.APPLICATION_JSON).body(response2);
//		}
//
//		return ResponseEntity.ok().header(ConstantProperty.APPLICATION_JSON).body(response2);
//	}
//
//	@PostMapping(RestApiUrl.RESPONSE_PAYMENTS)
//	public void response_payment(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		final String method = "PaymentGatewayController : response_payment() method";
//		logger.error(method);
//
//		TreeMap<String, String> paytmParams = new TreeMap<String, String>();
//
//		String paytmChecksum = null;
//		// session------add on 30-03-2021
//		HttpSession session = request.getSession(true);
//		// ------------
//		try {
//			// Create a tree map from the form post param
//			// Request is HttpServletRequest
//
//			Map<String, String[]> map = request.getParameterMap();
//			Set set = map.entrySet();
//
//			TreeMap<String, String> tree_map = new TreeMap<String, String>();
//
//			Iterator it = set.iterator();
//			while (it.hasNext()) {
//				Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
//				// System.err.println("map :"+entry);
//				if ("CHECKSUMHASH".equalsIgnoreCase(entry.getKey())) {
//					paytmChecksum = entry.getValue()[0];
//					logger.error("CHECKSUMHASH : " + entry.getValue()[0]);
//				}
//				logger.error("param::" + entry.getKey() + " , val =" + entry.getValue()[0]);
//				paytmParams.put(entry.getKey(), entry.getValue()[0]);
//
//				tree_map.put(entry.getKey(), entry.getValue()[0]);
//
//			}
//
//			System.err.println("paytmParams : " + tree_map.get("key"));
//
//			PayUPaymentResponse payres = new PayUPaymentResponse();
//
//			payres.setAddedon(tree_map.get("addedon"));
//			payres.setMihpayid(tree_map.get("mihpayid"));
//			payres.setAmount(tree_map.get("amount"));
//			payres.setBankcode(tree_map.get("bankcode"));
//			payres.setBankRefNum(tree_map.get("bank_ref_num"));
//			payres.setCardcategory(tree_map.get("cardCategory"));
//			payres.setCardhash(tree_map.get("cardhash"));
//			payres.setCardnum(tree_map.get("cardnum"));
//			payres.setCreatedBy1(tree_map.get(tree_map.get("addedon"))); // ......
//			payres.setDiscount(tree_map.get("discount"));
//			payres.setEmail(tree_map.get("email"));
//			payres.setError(tree_map.get("error"));
//			payres.setErrorMessage(tree_map.get("error_Message"));
//			payres.setField1(tree_map.get("field1"));
//			payres.setField2(tree_map.get("field2"));
//			payres.setField3(tree_map.get("field3"));
//			payres.setField4(tree_map.get("field4"));
//			payres.setField5(tree_map.get("field5"));
//			payres.setField6(tree_map.get("field6"));
//			payres.setField7(tree_map.get("field7"));
//			payres.setField8(tree_map.get("field8"));
//			payres.setField9(tree_map.get("field9"));
//
//			payres.setFirstname(tree_map.get("firstname"));
//			payres.setHash(tree_map.get("hash"));
//			payres.setIsActive1(true); //
//			payres.setKey(tree_map.get("key"));
//			payres.setLastname(tree_map.get("lastname"));
//			payres.setMode(tree_map.get("mode"));
//			payres.setNetAmountDebit(tree_map.get("net_amount_debit"));
//			payres.setPaymentSource(tree_map.get("payment_source"));
//
//			payres.setPgType(tree_map.get("PG_TYPE"));
//			payres.setPhone(tree_map.get("phone"));
//			payres.setProductinfo(tree_map.get("productinfo"));
//			payres.setStatus(tree_map.get("status"));
//			payres.setTxnid(tree_map.get("txnid"));
//			payres.setUdf1(tree_map.get("udf1"));
//			payres.setUdf2(tree_map.get("udf2"));
//			payres.setUdf3(tree_map.get("udf3"));
//			payres.setUdf4(tree_map.get("udf4"));
//			payres.setUdf5(tree_map.get("udf5"));
//			payres.setUdf6(tree_map.get("udf6"));
//			payres.setUdf7(tree_map.get("udf7"));
//			payres.setUdf8(tree_map.get("udf8"));
//			payres.setUnmappedStatus(tree_map.get("unmappedstatus"));
////			String fullNameConsumer=tree_map.get("firstname")+" "+tree_map.get("lastname");
//
//			Response<PayUPaymentResponse> response3 = payUPaymentResponseService.saveResponse(payres);
//
////			account id=consumer applicationnumer
////				parabhat sir wala hai ye
//			String redirectUrl = "sendReciept" + "?accountId=" + tree_map.get("udf3") + "&custName="
//					+ tree_map.get("firstname") + " " + tree_map.get("lastname") + "&orderId=" + tree_map.get("txnid")
//					+ "&txnId=" + tree_map.get("txnid") + "&txnAmount=" + tree_map.get("amount") + "&txnDate="
//					+ tree_map.get("addedon") + "&status=" + tree_map.get("status") + "&isFailed=" + Boolean.FALSE;
//
////			  udf1 me consumer name ja raha hai
////		      udf2 me consumer ki id ja rahi hai 
////		      udf3 me consumer ki application number ja rahi
////			  udf4 me consumer ki trancation id ja rahi hai jo humne gernerate ki hai invoice table wali transcation id
////			  udf me invoice id ja rahai hai invoice table se nikal kar consumer application ke bihaf par
//
////			String redirectUrl = "sendReciept" + "?"
////					+ "consumerId=" + tree_map.get("udf2") +
////					"&consumerName="+ tree_map.get("udf1")  +
////					"&txnId=" + tree_map.get("txnid") + 
////					"&txnAmount=" + tree_map.get("amount") + 
////					"&txnDate=" + tree_map.get("addedon") + "&status="
////					+ tree_map.get("status") + "&isFailed=" + Boolean.FALSE;
//			response.sendRedirect(redirectUrl);
//
//		} catch (Exception ex) {
//			logger.error("Exception in response_payment ::", ex);
//			String msg = paytmParams.get("RESPMSG");
//			logger.error("Error in ...................................12 :: " + msg);
//			// start>>>>>>>>---add on 30-03-2021
//			/*
//			 * session.setAttribute("isFailed", Boolean.TRUE);
//			 * session.setAttribute("isFailedParam", Boolean.FALSE);
//			 * session.setAttribute("msg", paytmParams.get("RESPMSG")); String redirectUrl =
//			 * "sendReciept"; response.sendRedirect(redirectUrl);
//			 */
//			// End
//
//			response.sendRedirect("sendReciept?msg=" + paytmParams.get("RESPMSG") + "&isFailed=" + Boolean.TRUE
//					+ "&isFailedParam=" + Boolean.FALSE);
//
//		}
//	}
//
//	@GetMapping(value = "/sendReciept")
//	public String sendReciept(@RequestParam(value = "accountId", required = false) String accountId,
//			@RequestParam(value = "custName", required = false) String custName,
//			@RequestParam(value = "orderId", required = false) String orderId,
//			@RequestParam(value = "txnId", required = false) String txnId,
//			@RequestParam(value = "txnAmount", required = false) String txnAmount,
//			@RequestParam(value = "txnDate", required = false) String txnDate,
//			@RequestParam(value = "status", required = false) String status, @RequestParam("isFailed") boolean isFailed,
//			@RequestParam(value = "isFailedParam", required = false) boolean isFailedParam,
//			@RequestParam(value = "msg", required = false) String msg) throws Exception {
//		logger.error(">>>>>>>>>>>>>>>>>>>>>>inside send recipt" + custName + " ,orderId:" + orderId);
//		String reciept = "";
//		if (isFailed) {
//			if (isFailedParam) {
//				reciept = generateFailRecipt(accountId, custName, orderId, txnId, txnAmount, txnDate, status);
//			} else {
//				reciept = generateFailRecipt(msg);
//			}
//
//		} else {
//			reciept = generateRecipt(accountId, custName, orderId, txnId, txnAmount, txnDate, status);
//		}
//		// attribute=">>>>>>>>>>>>>>>>>>>>>>inside send recipt";
//		return reciept;
//	}
//
//	/**
//	 * @param paytmParams
//	 * 
//	 *                    Generating payment recipt
//	 * @throws ParseException
//	 */
//
//	private String generateFailRecipt(String msg) throws ParseException, Exception {
//		StringBuilder recipt = new StringBuilder();
//		recipt.append("<html><head>");
//		recipt.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
//		recipt.append("<style>table, td, th {border: 1px solid black;}</style>");
//		recipt.append(
//				"<script type=\"text/javascript\">$(document).ready(function(){$(\"#btn\").click(function () {$(\"#printarea\").print();window.print();});});</script></head><body>\r\n");
//		recipt.append(
//				"<div id=\"printableArea\"><table align=\"center\" width=\"60%\" id=\"printarea\" cellpadding=\"10\" cellspacing=\"0\" style=\" border-collapse: collapse; border: 1px solid black;\"><thead>\r\n");
//		recipt.append(
//				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>"
//						+ "");
//		recipt.append("<tr><th colspan=\"2\" style=' height: 50px;'><b>Transaction Fail Kindly retry!!</b>" + msg
//				+ "</th></tr></thead><tbody>" + "</tbody></table><br><br></div>\r\n" + "");
//		recipt.append(
//				"<center><button name=\"print\" onclick=\"printDiv('printableArea')\" id=\"btn\" >Print Receipt</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name='close' id='btn'  onclick='window.close();return false;'>Close Window</button></center>\r\n"
//						+ "</td></tr></tfoot></div></body>\r\n"
//						+ "<script>function printDiv(divName) {var printContents = document.getElementById(divName).innerHTML;var originalContents = document.body.innerHTML;document.body.innerHTML = printContents;window.print();document.body.innerHTML = originalContents;}</script>"
//						+ "<script type = \"text/javascript\"> $(document).on(\"keydown\", function (e) {\r\n"
//						+ "        if (e.key == \"F5\" || e.key == \"F11\" || \r\n"
//						+ "            (e.ctrlKey == true && (e.key == 'r' || e.key == 'R')) || \r\n"
//						+ "            e.keyCode == 116 || e.keyCode == 82) {\r\n"
//						+ "                   e.preventDefault();\r\n" + "        }\r\n"
//						+ "    $(window).bind('beforeunload', function(e) {  \r\n"
//						+ "	   return \\\"Unloading this page may lose data. What do you want to do... \r\n"
//						+ "	   e.preventDefault(); \r\n" + "	});" + " });</script></html>");
//
//		return recipt.toString();
//	}
//
//	private String generateRecipt(String accountId, String custName, String orderId, String txnId, String txnAmount,
//			String txnDate, String status) throws ParseException, Exception {
//		StringBuilder recipt = new StringBuilder();
//		// Optional<Invoice> invoice =
//		// invoiceService.findByCustomerId(paytmParams.get("MERC_UNQ_REF"));
//		recipt.append("<html><head>");
//		recipt.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
//		recipt.append("<style>table, td, th {border: 1px solid black;}</style>");
//		recipt.append(
//				"<script type=\"text/javascript\">$(document).ready(function(){$(\"#btn\").click(function () {$(\"#printarea\").print();window.print();});});</script></head><body>\r\n");
//		recipt.append(
//				"<div id=\"printableArea\"><table align=\"center\" width=\"60%\" id=\"printarea\" cellpadding=\"10\" cellspacing=\"0\" style=\" border-collapse: collapse; border: 1px solid black;\"><thead>\r\n");
//		recipt.append(
//				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>"
//						+ "");
//		recipt.append("<tr><th colspan=\"2\" style=' height: 50px;'><b>PAYMENT RECEIPT</b></th></tr></thead><tbody>");
//		recipt.append(
//				"<tr><td style='font-weight:bold'>APPLICATION No.</td><td style='color:blue'>" + accountId + "</td></tr>");
//
//		// if (invoice.isPresent()) {
//		recipt.append("<tr><td style='font-weight:bold'>APPLICANT NAME</td><td style='color:blue'>" + custName
//				+ "</td></tr>\r\n" + "");// }
//		recipt.append("</td></tr>\r\n" + "<tr><td style='font-weight:bold'>TRANSACTION NO</td><td style='color:blue'>" + txnId
//				+ "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>AMOUNT</td><td style='color:blue'>" + txnAmount
//				+ "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>DATE</td><td style='color:blue'>" + txnDate
//				+ "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>Payment received against your application</td><td style='color:blue'>" + status 
//				+ "</td></tr>\r\n" + "</tbody></table><br><br></div>\r\n" + "");
//		recipt.append(
//				"<center><button name=\"print\" onclick=\"printDiv('printableArea')\" id=\"btn\" >Print Receipt</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name='close' id='btn'  onclick='window.close();return false;'>Close Window</button></center>\r\n"
//						+ "</td></tr></tfoot></div></body>\r\n"
//						+ "<script>function printDiv(divName) {var printContents = document.getElementById(divName).innerHTML;var originalContents = document.body.innerHTML;document.body.innerHTML = printContents;window.print();document.body.innerHTML = originalContents;}</script>\r\n"
//						+ "<script type = \"text/javascript\"> $(document).on(\"keydown\", function (e) {\r\n"
//						+ "        if (e.key == \"F5\" || e.key == \"F11\" || \r\n"
//						+ "            (e.ctrlKey == true && (e.key == 'r' || e.key == 'R')) || \r\n"
//						+ "            e.keyCode == 116 || e.keyCode == 82) {\r\n"
//						+ "                   e.preventDefault();\r\n"
//						+ "    $(window).bind('beforeunload', function(e) { \r\n"
//						+ "	   return \\\"Unloading this page may lose data. What do you want to do... \r\n"
//						+ "	   e.preventDefault(); \r\n" + "	});" + "});" + "</script></html>");
//		return recipt.toString();
//	}
//
//	private String generateFailRecipt(String accountId, String custName, String orderId, String txnId, String txnAmount,
//			String txnDate, String status) throws ParseException, Exception {
//		StringBuilder recipt = new StringBuilder();
////		Optional<Invoice> invoice = invoiceService.findByCustomerId(accountId,);
//
//		Invoice invoice = invoiceService.findByConsumerApplicationNumberAndTranscationNo(accountId, txnId);
//		recipt.append("<html><head>");
//		recipt.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
//		recipt.append("<style>table, td, th {border: 1px solid black;}</style>");
//		recipt.append(
//				"<script type=\"text/javascript\">$(document).ready(function(){$(\"#btn\").click(function () {$(\"#printarea\").print();window.print();});});</script></head><body>\r\n");
//		recipt.append(
//				"<div id=\"printableArea\"><table align=\"center\" width=\"60%\" id=\"printarea\" cellpadding=\"10\" cellspacing=\"0\" style=\" border-collapse: collapse; border: 1px solid black;\"><thead>\r\n");
//		recipt.append(
//				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>");
//		recipt.append(
//				"<tr><th colspan=\"2\" style=' height: 50px;'><b>Transaction Fail Kindly Retry !!</b></th></tr></thead><tbody>");
//		recipt.append(
//				"<tr><td style='font-weight:bold'>APPLICATION No.</td><td style='color:blue'>" + accountId + "</td></tr>");
//
//		if (Objects.nonNull(invoice)) {
//			recipt.append("<tr><td style='font-weight:bold'>APPLICANT NAME</td><td style='color:blue'>"
//					+ invoice.getConsumerName() + "</td></tr>\r\n" + "");
//		}
//		recipt.append("</td></tr>\r\n" + "<tr><td style='font-weight:bold'>TRANSACTION NO</td><td style='color:blue'>" + txnId
//				+ "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>AMOUNT</td><td style='color:blue'>" + txnAmount
//				+ "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>DATE</td><td style='color:blue'>" + txnDate
//				+ "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>Payment received against your application</td><td style='color:blue'>" + status 
//				+ "</td></tr>\r\n" + "</tbody></table><br><br></div>\r\n" + "");
//		recipt.append(
//				"<center><button name=\"print\" onclick=\"printDiv('printableArea')\" id=\"btn\" >Print Receipt</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name='close' id='btn'  onclick='window.close();return false;'>Close Window</button></center>\r\n"
//						+ "</td></tr></tfoot></div></body>\r\n"
//						+ "<script>function printDiv(divName) {var printContents = document.getElementById(divName).innerHTML;var originalContents = document.body.innerHTML;document.body.innerHTML = printContents;window.print();document.body.innerHTML = originalContents;}</script>\r\n"
//						+ "<script type = \"text/javascript\"> $(document).on(\"keydown\", function (e) {\r\n"
//						+ "        if (e.key == \"F5\" || e.key == \"F11\" || \r\n"
//						+ "            (e.ctrlKey == true && (e.key == 'r' || e.key == 'R')) || \r\n"
//						+ "            e.keyCode == 116 || e.keyCode == 82) {\r\n"
//						+ "                   e.preventDefault();\r\n" + "        }\r\n"
//						+ "   $(window).bind('beforeunload', function(e) { \r\n"
//						+ "		 return \\\"Unloading this page may lose data. What do you want to do... \r\n"
//						+ "		  e.preventDefault();\r\n" + "		});" + " });" + "</script></html>");
//
//		return recipt.toString();
//	}
////	+ "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>Payment received towards registration fees against your application</td><td style='color:blue'>" + status 
//
//}
