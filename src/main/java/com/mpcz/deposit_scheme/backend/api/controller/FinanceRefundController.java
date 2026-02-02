//package com.mpcz.deposit_scheme.backend.api.controller;
//
//import java.math.BigDecimal;
//import java.net.InetSocketAddress;
//import java.net.Proxy;
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.google.gson.Gson;
//import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
//import com.mpcz.deposit_scheme.backend.api.domain.BeforeRefundAmountCheck;
//import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAppCancellationRefundAmount;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAppReturnMaterialRefundAmnt;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
//import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
//import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;
//import com.mpcz.deposit_scheme.backend.api.domain.User;
//import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
//import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.BeforeRefundAmountCheckRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAppCancellationRefundAmountRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAppReturnMaterialRefundAmntRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.RefundAmountRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.JWSObject;
//import com.nimbusds.jose.JWSSigner;
//import com.nimbusds.jose.JWSVerifier;
//import com.nimbusds.jose.Payload;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jose.crypto.MACVerifier;
//
//@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RequestMapping("/api/user/finance_refund")
//public class FinanceRefundController {
//
//	@Value("${client.id}")
//	private String clientId;
//
//	@Value("${return.url}")
//	private String returnUrl;
//
//	@Value("${hash.key}")
//	private String hashKey;
//
//	@Value("${client.key}")
//	private String clientKey;
//
//	@Value("${create.order}")
//	private String createOrder;
//
//	@Value("${transaction.url}")
//	private String transactionUrl;
//
//	@Value("${refund.create}")
//	private String refundCreate;
//
//	@Autowired
//	private ApplicationStatusRepository applicationStatusRepository;
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
//	@Autowired
//	RefundAmountRepository refundAmountRepository;
//
//	@Autowired
//	private ConsumerAppCancellationRefundAmountRepository consumerAppCancellationRefundAmountRepository;
//
//	@Autowired
//	private BeforeRefundAmountCheckRepository beforeRefundAmountCheckRepository;
//
//	@Autowired
//	private ConsumerAppReturnMaterialRefundAmntRepository consumerAppReturnMaterialRefundAmntRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private ErpRevRepository erpRevRepository;
//
//	@PostMapping("/refundDemandAmount/{consumerApplicaitonNo}/{userId}")
//	public Response<Object> refundDemandAmount(@PathVariable String consumerApplicaitonNo,
//			@PathVariable String userId) {
//		Response<Object> response = new Response<Object>();
//		try {
//			BillDeskPaymentResponse billDeskRefund = null;
//			List<BillDeskPaymentResponse> billdeskPaymentDetails = billPaymentResponseeeeeeeRepository
//					.getAllPaymentDetails(consumerApplicaitonNo);
//			if (billdeskPaymentDetails.isEmpty()) {
//				return new Response(HttpCode.NULL_OBJECT, "Payment not found ");
//			}
////For cancellation payment .........................  consumer k dwara applicaiton ka payment wapas managa gaya hai
//			RefundAmount refundAmountDB = refundAmountRepository.findByConsumerApplicationNo(consumerApplicaitonNo);
//			if (refundAmountDB == null) {
//				response.setCode(HttpCode.NULL_OBJECT);
//				response.setMessage("Refund Data not found for this application no ");
//				return response;
//			} else {
//				if (refundAmountDB.getRefundType().equals("Cancellation_Amount")) {
//					BeforeRefundAmountCheck refundAmountCheckDB = beforeRefundAmountCheckRepository
//							.findByApplicationNo(consumerApplicaitonNo);
//					if (refundAmountCheckDB.getRefundableAmount().equals(refundAmountDB.getRefundAmount())) {
//						List<ConsumerAppCancellationRefundAmount> consumerKoReturnHoneWalaPaiseDB = consumerAppCancellationRefundAmountRepository
//								.findByApplicationNo(consumerApplicaitonNo);
//						for (ConsumerAppCancellationRefundAmount cons : consumerKoReturnHoneWalaPaiseDB) {
//							billDeskCancellationRefund(cons, refundAmountDB.getRefundAmount());
//						}
//						refundAmountDB.setRefundStatus("Refund Success");
////					Updated list ko check krne k liye ki usme Refund Ka status kya hai code start 
//						List<ConsumerAppCancellationRefundAmount> updatedListWithSuccessStatus = consumerAppCancellationRefundAmountRepository
//								.findByApplicationNo(consumerApplicaitonNo);
//
//						for (ConsumerAppCancellationRefundAmount consApp : updatedListWithSuccessStatus) {
//							if (consApp.getRefundStatus().equals("Refund Failed")) {
//								refundAmountDB.setRefundStatus("Refund Failed");
//							}
//						}
////           code start 
//						Optional<User> userIdDB = userRepository.findByUserId(userId);
//						if (userIdDB.isPresent()) {
//							refundAmountDB.setFinanceId(userId);
//							refundAmountDB.setFinanceName(userIdDB.get().getUserName());
//							refundAmountDB.setFinanceApprovedDate(Date.valueOf(LocalDate.now()).toString());
//						}
//						refundAmountRepository.save(refundAmountDB);
//
//						ConsumerApplicationDetail consumerApplication = consumerApplictionDetailRepository
//								.findByConsumerApplicationNumber(consumerApplicaitonNo);
//						consumerApplication.setApplicationStatus(applicationStatusRepository.findById(
//								ApplicationStatusEnum.CANCELLATION_AMOUNT_REFUNDED_TO_APPLICANT_SUCCESSFULLY.getId())
//								.get());
//						consumerApplictionDetailRepository.save(consumerApplication);
//
//						response.setCode("200");
//						response.setMessage("Refund Successfull");
//						return response;
//					} else {
//						System.err.println(
//								"Difference in amount from refundAmountDB.getRefundAmount() and refundAmountCheckDB.getRefundableAmount() ");
//					}
//				} else if (refundAmountDB.getRefundType().equals("Revise_Amount")) { // Consumer Ko Revise ka Negative
//																						// amount return krne k liye
//					BigDecimal consumerRefundableAmntDB = BigDecimal.ZERO;
//					ErpRev erpRevDb = erpRevRepository.findByConsAppNo(consumerApplicaitonNo);
//					if (erpRevDb != null) {
//						consumerRefundableAmntDB = Optional.ofNullable(erpRevDb.getConsumerRefundableAmnt())
//								.orElse(BigDecimal.ZERO);
//
//					}
//					if (consumerRefundableAmntDB.equals(refundAmountDB.getRefundAmount())) {
//						BillDeskPaymentResponse billDeskLatestDemand = billPaymentResponseeeeeeeRepository
//								.getBillDeskLatestDemand(consumerApplicaitonNo);
//
//						BillDeskPaymentResponse billDeskReviseRefund = billDeskReviseRefund(billDeskLatestDemand,
//								refundAmountDB.getRefundAmount().abs());
//						if (billDeskReviseRefund.getRefundStatus().equals("0799")) {
//							refundAmountDB.setRefundStatus("Refund Success");
//						} else {
//							refundAmountDB.setRefundStatus("Refund Failed");
//						}
//
//						Optional<User> userIdDB = userRepository.findByUserId(userId);
//						if (userIdDB.isPresent()) {
//							refundAmountDB.setFinanceId(userId);
//							refundAmountDB.setFinanceName(userIdDB.get().getUserName());
//							refundAmountDB.setFinanceApprovedDate(Date.valueOf(LocalDate.now()).toString());
//						}
//						refundAmountRepository.save(refundAmountDB);
//						response.setCode("200");
//						response.setMessage("Refund Successfull");
//						return response;
//					} else {
//						System.err.println(
//								"Difference in amount from refundAmountDB.getRefundAmount() and consumerRefundableAmntDB ");
//					}
//				} else if (refundAmountDB.getRefundType().equals("Return_Amount")) { // Consumer ko Return material ka
//																						// poora
//																						// paisa return krna hai
//					BeforeRefundAmountCheck refundAmountCheckDB = beforeRefundAmountCheckRepository
//							.findByApplicationNo(consumerApplicaitonNo);
//					if (refundAmountCheckDB.getRefundableAmount().equals(refundAmountDB.getRefundAmount())) {
//						List<ConsumerAppReturnMaterialRefundAmnt> consumerKoReturnHoneWalaPaiseDB = consumerAppReturnMaterialRefundAmntRepository
//								.findByApplicationNo(consumerApplicaitonNo);
//
//						for (ConsumerAppReturnMaterialRefundAmnt cons : consumerKoReturnHoneWalaPaiseDB) {
//							billDeskReturnMaterialRefund(cons, refundAmountDB.getRefundAmount());
//
//						}
//						refundAmountDB.setRefundStatus("Refund Success");
////					Updated list ko check krne k liye ki usme Refund Ka status kya hai code start 
//						List<ConsumerAppReturnMaterialRefundAmnt> updateListRefundStatusSuccess = consumerAppReturnMaterialRefundAmntRepository
//								.findByApplicationNo(consumerApplicaitonNo);
//
//						for (ConsumerAppReturnMaterialRefundAmnt returnMaterial : updateListRefundStatusSuccess) {
//							if (returnMaterial.getRefundStatus().equals("Refund Failed")) {
//								refundAmountDB.setRefundStatus("Refund Failed");
//							}
//						}
//
////     Code End
//						Optional<User> userIdDB = userRepository.findByUserId(userId);
//						if (userIdDB.isPresent()) {
//							refundAmountDB.setFinanceId(userId);
//							refundAmountDB.setFinanceName(userIdDB.get().getUserName());
//							refundAmountDB.setFinanceApprovedDate(Date.valueOf(LocalDate.now()).toString());
//						}
//						refundAmountRepository.save(refundAmountDB);
//						ConsumerApplicationDetail consumerApplication = consumerApplictionDetailRepository
//								.findByConsumerApplicationNumber(consumerApplicaitonNo);
//						consumerApplication.setApplicationStatus(applicationStatusRepository.findById(
//								ApplicationStatusEnum.CANCELLATION_AMOUNT_REFUNDED_TO_APPLICANT_SUCCESSFULLY.getId())
//								.get());
//						consumerApplictionDetailRepository.save(consumerApplication);
//
//						response.setCode("200");
//						response.setMessage("Refund Successfull");
//						return response;
//					} else {
//						System.err.println(
//								"Difference in amount from refundAmountDB.getRefundAmount() and refundAmountCheckDB.getRefundableAmount() ");
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Response("500", "Internal Server Error");
//		}
//		return response;
//	}
//
//	private String encryptAndSignJWSWithHMAC(String reqStr, String secretKey, String clientid) throws JOSEException {
//
//		JWSSigner signer = new MACSigner(secretKey);
//		HashMap<String, Object> customParams = new HashMap<String, Object>();
//		customParams.put("clientid", clientid);
//		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256, null, null, null, null, null, null, null, null, null,
//				null, customParams, null);
//		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(reqStr)); // Apply the HMAC
//		jwsObject.sign(signer);
//		return jwsObject.serialize();
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
//	public BillDeskPaymentResponse billDeskCancellationRefund(
//			ConsumerAppCancellationRefundAmount consumerAppCancellationRefundAmount, BigDecimal amount)
//			throws Exception {
//
//		Random random = new Random();
//		int digit = random.nextInt(900);
//		int digit1 = random.nextInt(90000000);
//
//		String refundId = "REF" + digit + "D" + digit1;
//
//		System.err.println(refundId);
//
//		JSONObject sendDataBillDesk = new JSONObject();
//
//		sendDataBillDesk.put("transactionid", consumerAppCancellationRefundAmount.getTxnId());
//		sendDataBillDesk.put("orderid", consumerAppCancellationRefundAmount.getOrderId());
//		sendDataBillDesk.put("mercid", consumerAppCancellationRefundAmount.getMerchantId());
//		sendDataBillDesk.put("transaction_date", consumerAppCancellationRefundAmount.getTransactionDate());
//		sendDataBillDesk.put("txn_amount", consumerAppCancellationRefundAmount.getTxnAmount());
//		sendDataBillDesk.put("refund_amount", consumerAppCancellationRefundAmount.getRefundableAmount());
//		sendDataBillDesk.put("currency", "356");
//		sendDataBillDesk.put("merc_refund_ref_no", refundId);
//
//		JSONObject deviceMap = new JSONObject();
//		deviceMap.put("accept_header", "text/html");
//		deviceMap.put("init_channel", "internet");
//		deviceMap.put("ip", "172.24.200.191");
//		deviceMap.put("fingerprintid", "61b12c18b5d0cf901be34a23ca64bb19");
//
//		sendDataBillDesk.put("device", deviceMap);
//
//		System.err.println("sendDataBillDesk : " + new Gson().toJson(sendDataBillDesk));
//		HttpHeaders headers = new HttpHeaders();
//
//		Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
//		headers.set("Content-Type", "application/jose");
//		headers.set("BD-timestamp", time.toString());
//		headers.set("Accept", "application/jose");
//		headers.set("BD-traceid", time.toString() + "123");
//
//		String convertObjectInString = encryptAndSignJWSWithHMAC(sendDataBillDesk.toString(), hashKey, clientKey);
//
//		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
//		clientHttpReq.setProxy(proxy);
//
//		RestTemplate restTemplate = new RestTemplate(clientHttpReq);
//
//		String url = refundCreate;
//
//		HttpEntity httpEntity = new HttpEntity<>(convertObjectInString, headers);
//		System.err.println("httpEntity-->" + httpEntity);
//		ResponseEntity<String> postForEntity = null;
//		try {
//			postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);
//		} catch (Exception e) {
//
//		}
//		System.err.println("postForEntity-->" + postForEntity);
//		String forObject = postForEntity.getBody();
//		System.out.println("forObject-------------" + forObject);
//		if (forObject == null && forObject.length() <= 0) {
////			response.setMessage("response body is null");
////			response.setCode("200");
////			return response;
//		}
//
//		String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject, hashKey);
//		BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository.findByOrderidAndTranId(
//				consumerAppCancellationRefundAmount.getOrderId(), consumerAppCancellationRefundAmount.getTxnId());
//
//		JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
//		billDeskPaymentResponse.setRefundId(jobObject.getString("refundid"));
//		billDeskPaymentResponse.setTxnAmount(jobObject.getString("txn_amount"));
//		billDeskPaymentResponse.setRefundDate(jobObject.getString("refund_date"));
//		billDeskPaymentResponse.setMercRefundRefNo(jobObject.getString("transactionid"));
//		billDeskPaymentResponse.setRefundStatus(jobObject.getString("refund_status"));
//		billDeskPaymentResponse.setRefundAmount(jobObject.getString("refund_amount"));
//
//		ConsumerAppCancellationRefundAmount cancellationRefundAmountDB = consumerAppCancellationRefundAmountRepository
//				.findByTxnId(consumerAppCancellationRefundAmount.getTxnId());
//
//		if (jobObject.getString("refund_status").equals("0799")) {
//			cancellationRefundAmountDB.setRefundStatus("Refund Success");
//		} else {
//			cancellationRefundAmountDB.setRefundStatus("Refund Failed");
//		}
//		consumerAppCancellationRefundAmountRepository.save(cancellationRefundAmountDB);
//
//		BillDeskPaymentResponse save = billPaymentResponseeeeeeeRepository.save(billDeskPaymentResponse);
//		return save;
//
//	}
//
//	public BillDeskPaymentResponse billDeskReviseRefund(BillDeskPaymentResponse billDeskLatestDemand, BigDecimal amount)
//			throws Exception {
//
//		Random random = new Random();
//		int digit = random.nextInt(900);
//		int digit1 = random.nextInt(90000000);
//		String refundId = "REF" + digit + "D" + digit1;
//		System.err.println(refundId);
//
//		JSONObject sendDataBillDesk = new JSONObject();
//
//		sendDataBillDesk.put("transactionid", billDeskLatestDemand.getTranId());
//		sendDataBillDesk.put("orderid", billDeskLatestDemand.getOrderid());
//		sendDataBillDesk.put("mercid", billDeskLatestDemand.getMercid());
//		sendDataBillDesk.put("transaction_date", billDeskLatestDemand.getTransactionDate());
//		sendDataBillDesk.put("txn_amount", billDeskLatestDemand.getTxnAmount());
//		sendDataBillDesk.put("refund_amount", amount);
//		sendDataBillDesk.put("currency", "356");
//		sendDataBillDesk.put("merc_refund_ref_no", refundId);
//
//		JSONObject deviceMap = new JSONObject();
//		deviceMap.put("accept_header", "text/html");
//		deviceMap.put("init_channel", "internet");
//		deviceMap.put("ip", "172.24.200.191");
//		deviceMap.put("fingerprintid", "61b12c18b5d0cf901be34a23ca64bb19");
//
//		sendDataBillDesk.put("device", deviceMap);
//
//		System.err.println("sendDataBillDesk : " + new Gson().toJson(sendDataBillDesk));
//		HttpHeaders headers = new HttpHeaders();
//
//		Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
//		headers.set("Content-Type", "application/jose");
//		headers.set("BD-timestamp", time.toString());
//		headers.set("Accept", "application/jose");
//		headers.set("BD-traceid", time.toString() + "123");
//
//		String convertObjectInString = encryptAndSignJWSWithHMAC(sendDataBillDesk.toString(), hashKey, clientKey);
//
//		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
//		clientHttpReq.setProxy(proxy);
//
//		RestTemplate restTemplate = new RestTemplate(clientHttpReq);
//
//		String url = refundCreate;
//
//		HttpEntity httpEntity = new HttpEntity<>(convertObjectInString, headers);
//		System.err.println("httpEntity-->" + httpEntity);
//		ResponseEntity<String> postForEntity = null;
//		try {
//			postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);
//		} catch (Exception e) {
//
//		}
//		System.err.println("postForEntity-->" + postForEntity);
//		String forObject = postForEntity.getBody();
//		System.out.println("forObject-------------" + forObject);
//		if (forObject == null && forObject.length() <= 0) {
////			response.setMessage("response body is null");
////			response.setCode("200");
////			return response;
//		}
//
//		String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject, hashKey);
//		BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
//				.findByTranId(billDeskLatestDemand.getTranId());
//
//		JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
//		billDeskPaymentResponse.setRefundId(jobObject.getString("refundid"));
//		billDeskPaymentResponse.setTxnAmount(jobObject.getString("txn_amount"));
//		billDeskPaymentResponse.setRefundDate(jobObject.getString("refund_date"));
//		billDeskPaymentResponse.setMercRefundRefNo(jobObject.getString("transactionid"));
//		billDeskPaymentResponse.setRefundStatus(jobObject.getString("refund_status"));
//		billDeskPaymentResponse.setRefundAmount(jobObject.getString("refund_amount"));
//
//		BillDeskPaymentResponse save = billPaymentResponseeeeeeeRepository.save(billDeskPaymentResponse);
//		return save;
//
//	}
//
//	public BillDeskPaymentResponse billDeskReturnMaterialRefund(
//			ConsumerAppReturnMaterialRefundAmnt consumerAppReturnMaterialRefundAmnt, BigDecimal amount)
//			throws Exception {
//
//		Random random = new Random();
//		int digit = random.nextInt(900);
//		int digit1 = random.nextInt(90000000);
//
//		String refundId = "REF" + digit + "D" + digit1;
//
//		System.err.println(refundId);
//
//		JSONObject sendDataBillDesk = new JSONObject();
//
//		sendDataBillDesk.put("transactionid", consumerAppReturnMaterialRefundAmnt.getTxnId());
//		sendDataBillDesk.put("orderid", consumerAppReturnMaterialRefundAmnt.getOrderId());
//		sendDataBillDesk.put("mercid", consumerAppReturnMaterialRefundAmnt.getMerchantId());
//		sendDataBillDesk.put("transaction_date", consumerAppReturnMaterialRefundAmnt.getTransactionDate());
//		sendDataBillDesk.put("txn_amount", consumerAppReturnMaterialRefundAmnt.getTxnAmount());
//		sendDataBillDesk.put("refund_amount", amount);
//		sendDataBillDesk.put("currency", "356");
//		sendDataBillDesk.put("merc_refund_ref_no", refundId);
//
//		JSONObject deviceMap = new JSONObject();
//		deviceMap.put("accept_header", "text/html");
//		deviceMap.put("init_channel", "internet");
//		deviceMap.put("ip", "172.24.200.191");
//		deviceMap.put("fingerprintid", "61b12c18b5d0cf901be34a23ca64bb19");
//
//		sendDataBillDesk.put("device", deviceMap);
//
//		System.err.println("sendDataBillDesk : " + new Gson().toJson(sendDataBillDesk));
//		HttpHeaders headers = new HttpHeaders();
//
//		Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
//		headers.set("Content-Type", "application/jose");
//		headers.set("BD-timestamp", time.toString());
//		headers.set("Accept", "application/jose");
//		headers.set("BD-traceid", time.toString() + "123");
//
//		String convertObjectInString = encryptAndSignJWSWithHMAC(sendDataBillDesk.toString(), hashKey, clientKey);
//		System.err.println("Encrypted Request Payload: " + convertObjectInString);
//		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
//		clientHttpReq.setProxy(proxy);
//
//		RestTemplate restTemplate = new RestTemplate(clientHttpReq);
//
//		String url = refundCreate;
//
//		HttpEntity httpEntity = new HttpEntity<>(convertObjectInString, headers);
//		System.err.println("httpEntity-->" + httpEntity);
//		ResponseEntity<String> postForEntity = null;
//		try {
//			postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);
//			System.out.println("Response Status Code: " + postForEntity.getStatusCode());
//			System.out.println("Response Headers: " + postForEntity.getHeaders());
//		} catch (Exception e) {
//			System.err.println("Exception during API call: " + e.getMessage());
//		}
//		System.err.println("postForEntity-->" + postForEntity);
//		String forObject = postForEntity.getBody();
//		System.out.println("forObject-------------" + forObject);
//		if (forObject == null && forObject.length() <= 0) {
////			response.setMessage("response body is null");
////			response.setCode("200");
////			return response;
//		}
//
//		String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject, hashKey);
//		BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
//				.findByTranId(consumerAppReturnMaterialRefundAmnt.getTxnId());
//
//		JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
//		billDeskPaymentResponse.setRefundId(jobObject.getString("refundid"));
////		billDeskPaymentResponse.setAmount(jobObject.getString("refund_amount"));
//		billDeskPaymentResponse.setTxnAmount(jobObject.getString("txn_amount"));
//		billDeskPaymentResponse.setRefundDate(jobObject.getString("refund_date"));
//		billDeskPaymentResponse.setMercRefundRefNo(jobObject.getString("transactionid"));
//		billDeskPaymentResponse.setRefundStatus(jobObject.getString("refund_status"));
//		billDeskPaymentResponse.setRefundAmount(jobObject.getString("refund_amount"));
//
//		ConsumerAppReturnMaterialRefundAmnt txnIdKeBasePrDataDB = consumerAppReturnMaterialRefundAmntRepository
//				.findByTxnId(consumerAppReturnMaterialRefundAmnt.getTxnId());
//		if (jobObject.getString("refund_status").equals("0799")) {
//			txnIdKeBasePrDataDB.setRefundStatus("Refund Success");
//		} else {
//			txnIdKeBasePrDataDB.setRefundStatus("Refund Failed");
//		}
//		consumerAppReturnMaterialRefundAmntRepository.save(txnIdKeBasePrDataDB);
//		BillDeskPaymentResponse save = billPaymentResponseeeeeeeRepository.save(billDeskPaymentResponse);
//		return save;
//
//	}
//
//}
