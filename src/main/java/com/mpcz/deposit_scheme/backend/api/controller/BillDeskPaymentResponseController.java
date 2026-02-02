//package com.mpcz.deposit_scheme.backend.api.controller;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
//import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
//import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
//import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
//import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
//import com.nimbusds.jose.JWSObject;
//import com.nimbusds.jose.JWSVerifier;
//import com.nimbusds.jose.crypto.MACVerifier;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@Api(value = "BillDeskPaymentResponseController", description = "Rest api for payment Type.")
//@RestController
//@RequestMapping(RestApiUrl.BILLDESK_PAYMENT_RESPONSE_API)
//public class BillDeskPaymentResponseController {
//
//	Logger LOG = LoggerFactory.getLogger(BillDeskPaymentResponseController.class);
//
//	@Autowired
//	BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;
//
//	@Autowired
//	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
//
//	@Autowired
//	ApplicationStatusService applicationStatusService;
//
//	@ApiOperation(value = "Save Work Type details", notes = "Pass Work Type Name", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
//	@PostMapping(value = "/save", consumes = { "application/x-www-form-urlencoded;charset=UTF-8" })
//	public void addBillDeskResponse(HttpServletRequest request, HttpServletResponse response1) throws Exception {
//
//		System.out.println("Inside addBillDeskResponse Controller !!!!!");
//
//		final String method = RestApiUrl.BILLDESK_PAYMENT_RESPONSE_API + RestApiUrl.ADD_PAYMENT_URL
//				+ " : addWorkType()";
//		LOG.info(method);
//
//		Response<BillDeskPaymentResponse> response = new Response<>();
//
//		BillDeskPaymentResponse payres = null;
//
//		TreeMap<String, String> paytmParams = new TreeMap<String, String>();
//
//		String paytmChecksum = null;
//		// session------add on 30-03-2021
//		HttpSession session = request.getSession(true);
//
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
//				LOG.error("param::" + entry.getKey() + " , val =" + entry.getValue()[0]);
//				paytmParams.put(entry.getKey(), entry.getValue()[0]);
//
//				tree_map.put(entry.getKey(), entry.getValue()[0]);
//
//			}
//			payres = new BillDeskPaymentResponse();
//			String string = tree_map.get("transaction_response");
//			System.out.println(string);
////			
////			payres.setMercid(tree_map.get("mercid"));
//
//			System.out.println(tree_map);
//			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(string,
//					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6");
//			System.out.println(verifyAndDecryptJWSWithHMAC);
//
//			JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
//
//			//
//
//			payres.setAmount(jobObject.getString("amount"));
//			payres.setAuth_status(jobObject.getString("auth_status"));
//			payres.setBankId(jobObject.getString("bankid"));
//			payres.setBankRefNo(jobObject.getString("bank_ref_no"));
//			payres.setChargeAmount(jobObject.getString("charge_amount"));
//
//			payres.setCurrency(jobObject.getString("currency"));
//			// payres.setMercid(jobObject.getString("transactionid"));
//			payres.setObjectId(jobObject.getString("objectid"));
//			payres.setOrderid(jobObject.getString("orderid"));
//			payres.setPaymentMethodType(jobObject.getString("payment_method_type"));
//			payres.setRu(jobObject.getString("ru"));
//			payres.setSurcharge(jobObject.getString("surcharge"));
//			payres.setTranErrorCode(jobObject.getString("transaction_error_code"));
//			payres.setTranErrorDesc(jobObject.getString("transaction_error_desc"));
//			payres.setTranId(jobObject.getString("transactionid"));
//			payres.setTranProcessType(jobObject.getString("txn_process_type"));
//			payres.setTransactionDate(jobObject.getString("transaction_date"));
//			payres.setConsumerApplicationNo(jobObject.getJSONObject("additional_info").getString("additional_info3"));
//			payres.setConsumerName(jobObject.getJSONObject("additional_info").getString("additional_info1"));
//			payres.setMobileNo(jobObject.getJSONObject("additional_info").getString("additional_info2"));
//			String consumerApp = jobObject.getJSONObject("additional_info").getString("additional_info3");
//			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
//					.findByConsumerApplicationNumber(consumerApp);
//			ApplicationStatus appStatusDb = null;
//
//			// findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
//
//			consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
//			// payres.setTransactionType(jobObject.getString("transactionid"));
//			System.out.println(payres + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
//
//			BillDeskPaymentResponse save = billPaymentResponseeeeeeeRepository.save(payres);
//
//			if (save.getTranErrorCode().equals("Transaction Successful") && save.getAuth_status().equals("0300")) {
//
//				if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() == 5l) {
//
//					appStatusDb = applicationStatusService
//							.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
//	
//				} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() == 12l) {
//
//					if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName()
//							.equalsIgnoreCase("Supervision")) {
//						appStatusDb = applicationStatusService
//								.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
//						findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
//						Date currentDate = new Date();
//
//						// Create a calendar instance and set it to the current date
//						Calendar calendar = Calendar.getInstance();
//						calendar.setTime(currentDate);
//
//						// Add three days to the current date
//						// -1 kiya hai 3 ki jagh or if me code past kiya hai
//						calendar.add(Calendar.DAY_OF_MONTH, -1);
//
//						// Get the new date
//						Date newDate = calendar.getTime();
//
//						// Create a formatter for the desired date format
//						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//
//						// Format the dates using the formatter
//						String currentDateFormatted = formatter.format(currentDate);
//						String newDateFormatted = formatter.format(newDate);
//
//						// Display the current date and the new date
//						System.out.println("Current Date: " + currentDateFormatted);
//						System.out.println("New Date: " + newDateFormatted);
//
//						findByConsumerApplicationNumber.setPaymentDate(newDateFormatted);
//					} else {
//						appStatusDb = applicationStatusService
//								.findById(ApplicationStatusEnum.WAITING_FOR_72_HOURS.getId());
//						Date currentDate = new Date();
//
//						// Create a calendar instance and set it to the current date
//						Calendar calendar = Calendar.getInstance();
//						calendar.setTime(currentDate);
//
//						// Add three days to the current date
//						calendar.add(Calendar.DAY_OF_MONTH, 3);
//
//						// Get the new date
//						Date newDate = calendar.getTime();
//
//						// Create a formatter for the desired date format
//						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//
//						// Format the dates using the formatter
//						String currentDateFormatted = formatter.format(currentDate);
//						String newDateFormatted = formatter.format(newDate);
//
//						// Display the current date and the new date
//						System.out.println("Current Date: " + currentDateFormatted);
//						System.out.println("New Date: " + newDateFormatted);
//
//						findByConsumerApplicationNumber.setPaymentDate(newDateFormatted);
//					}
//
//				}
//			} else {
//				appStatusDb = applicationStatusService
//						.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId());
//				findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
//			}
//			if (save != null) {
//				String redirectUrl = "sendReciept" + "?accountId=" + payres.getConsumerApplicationNo() + "&custName="
//						+ payres.getConsumerName() + "&orderId=" + payres.getOrderid() + "&txnId=" + payres.getTranId()
//						+ "&txnAmount=" + payres.getAmount() + "&txnDate=" + payres.getTransactionDate() + "&status="
//						+ payres.getTranErrorDesc() + "&isFailed=" + Boolean.FALSE;
//
//				response1.sendRedirect(redirectUrl);
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			response1.sendRedirect("sendReciept?msg=" + paytmParams.get("RESPMSG") + "&isFailed=" + Boolean.TRUE
//					+ "&isFailedParam=" + Boolean.FALSE);
//		}
//
//	}
//
//	public String verifyAndDecryptJWSWithHMAC(String encryptedSignedMessage, String verificationKey) throws Exception {
//
//		JWSObject jwsObject = JWSObject.parse(encryptedSignedMessage);
//
//		String clientId = jwsObject.getHeader().getCustomParam("clientid").toString();
//		System.out.println("clientId = " + clientId);
//		JWSVerifier verifier = new MACVerifier(verificationKey);
//		boolean isVerified = jwsObject.verify(verifier);
//		System.out.println("is valid " + isVerified);
//		String message = jwsObject.getPayload().toString();
//		return message;
//	}
//
//	@GetMapping(value = "/sendReciept1")
//	public String sendReciept1(@RequestParam(value = "accountId", required = false) String accountId,
//			@RequestParam(value = "custName", required = false) String custName,
//			@RequestParam(value = "orderId", required = false) String orderId,
//			@RequestParam(value = "txnId", required = false) String txnId,
//			@RequestParam(value = "txnAmount", required = false) String txnAmount,
//			@RequestParam(value = "txnDate", required = false) String txnDate,
//			@RequestParam(value = "status", required = false) String status, @RequestParam("isFailed") boolean isFailed,
//			@RequestParam(value = "isFailedParam", required = false) boolean isFailedParam,
//			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) throws Exception {
//		LOG.error(">>>>>>>>>>>>>>>>>>>>>>inside send recipt" + custName + " ,orderId:" + orderId);
//		String reciept = "";
//		HttpSession session = request.getSession(true);
//		if (isFailed) {
//
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
////		recipt.append(
////				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>"
////						+ "");
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
////		recipt.append(
////				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>"
////						+ "");
//		recipt.append("<tr><th colspan=\"2\" style=' height: 50px;'><b>PAYMENT RECEIPT</b></th></tr></thead><tbody>");
//		recipt.append("<tr><td style='font-weight:bold'>APPLICATION No.</td><td style='color:blue'>" + accountId
//				+ "</td></tr>");
//
//		// if (invoice.isPresent()) {
//		recipt.append("<tr><td style='font-weight:bold'>APPLICANT NAME</td><td style='color:blue'>" + custName
//				+ "</td></tr>\r\n" + "");// }
//		recipt.append("</td></tr>\r\n" + "<tr><td style='font-weight:bold'>TRANSACTION NO</td><td style='color:blue'>"
//				+ txnId + "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>AMOUNT</td><td style='color:blue'>"
//				+ txnAmount + "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>DATE</td><td style='color:blue'>"
//				+ txnDate + "</td></tr>\r\n"
//				+ "<tr><td style='font-weight:bold'>Payment received towards against your application</td><td style='color:blue'>"
//				+ status + "</td></tr>\r\n" + "</tbody></table><br><br></div>\r\n" + "");
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
////		Invoice invoice = invoiceService.findByConsumerApplicationNumberAndTranscationNo(accountId, txnId);
//		recipt.append("<html><head>");
//		recipt.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
//		recipt.append("<style>table, td, th {border: 1px solid black;}</style>");
//		recipt.append(
//				"<script type=\"text/javascript\">$(document).ready(function(){$(\"#btn\").click(function () {$(\"#printarea\").print();window.print();});});</script></head><body>\r\n");
//		recipt.append(
//				"<div id=\"printableArea\"><table align=\"center\" width=\"60%\" id=\"printarea\" cellpadding=\"10\" cellspacing=\"0\" style=\" border-collapse: collapse; border: 1px solid black;\"><thead>\r\n");
////		recipt.append(
////				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>");
//		recipt.append(
//				"<tr><th colspan=\"2\" style=' height: 50px;'><b>Transaction Fail Kindly Retry !!</b></th></tr></thead><tbody>");
//		recipt.append("<tr><td style='font-weight:bold'>APPLICATION No.</td><td style='color:blue'>" + accountId
//				+ "</td></tr>");
//
////		if (Objects.nonNull(invoice)) {
////			recipt.append("<tr><td style='font-weight:bold'>APPLICANT NAME</td><td style='color:blue'>"
////					+ invoice.getConsumerName() + "</td></tr>\r\n" + "");
////		}
//		recipt.append("</td></tr>\r\n" + "<tr><td style='font-weight:bold'>TRANSACTION NO</td><td style='color:blue'>"
//				+ txnId + "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>AMOUNT</td><td style='color:blue'>"
//				+ txnAmount + "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>DATE</td><td style='color:blue'>"
//				+ txnDate + "</td></tr>\r\n"
//				+ "<tr><td style='font-weight:bold'>Payment received towards  against your application</td><td style='color:blue'>"
//				+ status + "</td></tr>\r\n" + "</tbody></table><br><br></div>\r\n" + "");
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
//
//	// hello this is vivek khatri
//}
