package com.mpcz.deposit_scheme.backend.api.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.builddesk.CustomerBillDTO;
import com.mpcz.deposit_scheme.backend.api.constant.ConstantProperty;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentRequest1;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBid;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.dto.AuthorizationResponseDto;
import com.mpcz.deposit_scheme.backend.api.dto.BilldeskPaymentRetriveProcess;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.jose.JoseHelper;
import com.mpcz.deposit_scheme.backend.api.jose.VerifyJWS;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BllDeskPaymentRequestRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ManualPaymentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyCalculationRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.BillDeskPaymentService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(RestApiUrl.BILL_DESK_TYPE_API)
public class BillPaymentProcessController {

	static Logger logger = LoggerFactory.getLogger(BillPaymentProcessController.class);

	@Autowired(required = false)
	BillDeskPaymentService billPaymentService;

	@Autowired
	BllDeskPaymentRequestRepository billDeskPaymentRequestRepository;

	@Autowired
	BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	MmkyCalculationRepository mmkyCalculationRepository;

	@Autowired
	EstimateAmountRepository estimateAmountRepository;

	@Autowired
	MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	ManualPaymentRepository manualPaymentRepository;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDataRepository;

	@Autowired
	ConsumerRepository consumerRepositorys;

	@Autowired
	private ContractorForBidRepository contractorForBidRepository;

//	Bill desk payment configuration with properties file

	@Value("${spring.profiles.active}")
	private String envVariable;

	@Value("${client.id}")
	private String clientId;

	@Value("${return.url}")
	private String returnUrl;

	@Value("${hash.key}")
	private String hashKey;

	@Value("${client.key}")
	private String clientKey;

	@Value("${create.order}")
	private String createOrder;

	@Value("${ravindra.send.data.after.payment}")
	private String ravindraSendDataAfterPayment;

	@Value("${transaction.url}")
	private String transactionUrl;

	@Value("${refund.create}")
	private String refundCreate;

	@PostMapping("/billPaymentProcess")
	public ResponseEntity<Response<Object>> BillPaymentProcessController(@RequestBody CustomerBillDTO billDto,
			HttpServletRequest request) throws Exception {
		Response<Object> response = new Response<Object>();

		List<Object> list = new ArrayList<>();

		if (!billDto.getIsTdsTaken()) {
			String saveBillRequestResponse = saveBillRequest(billDto.getConsumerAppllicationNo());
			if (!"local".equals(envVariable) && !"dev".equals(envVariable)) {
				if ("Data cannot be saved in request table".equals(saveBillRequestResponse)) {
					response.setCode("406");
					response.setMessage(
							"Payment cannot be processed as you have already made a request within the last 5 minutes.");
					return ResponseEntity.badRequest().header(ConstantProperty.APPLICATION_JSON).body(response);
				}
			}
		}
		response = billPaymentService.prePaymentProcessingBillDesk(billDto, list, response, request);

		return ResponseEntity.ok().header(ConstantProperty.APPLICATION_JSON).body(response);
	}

	@GetMapping("/billPaymentsProcess/{id}")
	public Response<Object> BillPaymentSaveController(@PathVariable Long id, HttpServletRequest request)
			throws Exception {
		final String method = "PaymentGatewayController : billPaymentProcess2() method";
		Response<Object> response = new Response<Object>();
		AuthorizationResponseDto authorizationResponseDto = new AuthorizationResponseDto();
		Map<String, Object> dataForBilldesk;
		logger.info("/billPaymentsProcess/{id} ", " /billPaymentsProcess/{id} ");
		try {
			Optional<BillDeskPaymentRequest1> findById = billDeskPaymentRequestRepository.findById(id);

			System.err.println("BillDeskPaymentRequest1  " + findById.get().toString());
			BillDeskPaymentRequest1 billDeskPaymentRequest1 = findById.get();

			JSONObject map = new JSONObject();
			JSONObject deviceMap = new JSONObject();
			deviceMap.put("accept_header", "text/html");
			deviceMap.put("init_channel", "internet");
			deviceMap.put("ip", "172.24.200.191");
			deviceMap.put("fingerprintid", "61b12c18b5d0cf901be34a23ca64bb19");

			JSONObject additionalinfoMap = new JSONObject();

//			if (billDeskPaymentRequest1.getConsumerName().length() > 10) {
//				additionalinfoMap.put("additional_info1", billDeskPaymentRequest1.getConsumerName().substring(0, 10));
//			} else {
			additionalinfoMap.put("additional_info1", billDeskPaymentRequest1.getConsumerName());
//			}

			additionalinfoMap.put("additional_info2", billDeskPaymentRequest1.getConsumerMobileNo());
			additionalinfoMap.put("additional_info3", billDeskPaymentRequest1.getConsumerAppliNo());
			additionalinfoMap.put("additional_info7", billDeskPaymentRequest1.getAdditionalInfo7());

			System.out.println(billDeskPaymentRequest1.getAdditionalInfo7());
			LocalDateTime now = java.time.LocalDateTime.now();

			String orderId = billDeskPaymentRequest1.getOrderId();

			map.put("amount", billDeskPaymentRequest1.getTxnAmount());

			BigDecimal roundedAmount = billDeskPaymentRequest1.getTxnAmount().setScale(0, RoundingMode.FLOOR);

			BigDecimal remainingPaise = billDeskPaymentRequest1.getTxnAmount().subtract(roundedAmount);
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(billDeskPaymentRequest1.getConsumerAppliNo());

			if (remainingPaise.compareTo(new BigDecimal(0.01)) >= 0) {
				response.setMessage("Decimal Value Not Allowed For Payment.");
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				return response;

			}

			String localDateTime = LocalDateTime.now().toString();
			int indexOf = LocalDateTime.now().toString().indexOf(".");
			localDateTime = localDateTime.substring(0, indexOf) + "+05:30";
			map.put("order_date", localDateTime);

//			    uat marchent Id /client id
			map.put("mercid", clientId);

//			    production marchent Id /client id
//			map.put("mercid", "MPMKDSPV2");

//			    uat return url***********			
			map.put("ru", returnUrl);

//		     	prodcution return url  *****
//			map.put("ru", "https://dsp.mpcz.in:8888/deposit_scheme/api/consumer/bill-desk/save_payment_response");
			map.put("currency", "356");
			map.put("itemcode", "DIRECT");
			map.put("orderid", orderId);

			map.put("additional_info", additionalinfoMap);
			map.put("device", deviceMap);

			System.err.println("------------------------------" + localDateTime);

			System.out.println("hash key " + hashKey + "           client key   " + clientKey);

//			uat hash key****************************
			String encryptAndSignJWSWithHMAC = encryptAndSignJWSWithHMAC(map.toString(), hashKey, clientKey);

			System.err.println("BillPaymentSaveController : " + new Gson().toJson(map));

//			peoduction Hash key ***********************			
//			String encryptAndSignJWSWithHMAC = encryptAndSignJWSWithHMAC(map.toString(),
//					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6", "mpmkdspv2");

			SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
			if ("local".equals(envVariable) || "prod".equals(envVariable)) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
				clientHttpReq.setProxy(proxy);
				System.err.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa : " + proxy);
			}

			RestTemplate restTemplate = new RestTemplate(clientHttpReq);
			HttpHeaders headers = new HttpHeaders();

			Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
			System.out.println("valueOf--->" + time.toString());
			headers.set("content-type", "application/jose");
			headers.set("bd-timestamp", time.toString());
			headers.set("accept", "application/jose");
			headers.set("bd-traceid", time.toString() + "123");

//				uat url
			String url = createOrder;

			System.out.println("createOrder: " + createOrder);
//				prod url
//			String url = "https://api.billdesk.com/payments/ve1_2/orders/create";

			HttpEntity httpEntity = new HttpEntity<>(encryptAndSignJWSWithHMAC, headers);
			System.err.println("httpEntity-->" + httpEntity);

			InetAddress localHost = InetAddress.getLocalHost();
			String ipAddress = localHost.getHostAddress();
			logger.info("System IP Address: {}", ipAddress);
			System.err.println("System IP Address: " + ipAddress);

			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);

			System.err.println("postForEntity-->" + postForEntity);
			String forObject = postForEntity.getBody();
			System.out.println("forObject-------------" + forObject);
			if (forObject == null && forObject.length() <= 0) {
				response.setMessage("response body is null");
				response.setCode("200");
				return response;
			}
// 			uat hash keycl   ****

			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(postForEntity.getBody(), hashKey);// IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6

//			prod  hash key
//			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(postForEntity.getBody(),
//					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6");

			System.out.println("-------------------------+++++++++++++++" + verifyAndDecryptJWSWithHMAC);
			JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);

			authorizationResponseDto.setBdorderid(jobObject.getString("bdorderid"));
			authorizationResponseDto.setMercid(jobObject.getString("mercid"));
			dataForBilldesk = new LinkedHashMap<>();
			dataForBilldesk.put("UAT or Production", envVariable);
			dataForBilldesk.put("MerchantID", clientId);
			dataForBilldesk.put("CLIENTID", clientKey);
			dataForBilldesk.put("Full Request Headers for Create Order API", httpEntity);
			dataForBilldesk.put("URL on which the request was sent", url);
			dataForBilldesk.put("Encrypted Response", forObject);
			JSONArray jsonArray = jobObject.getJSONArray("links");

			for (int i = 0; i < jsonArray.length(); i++) {
				if (jsonArray.getJSONObject(i).toString().contains("headers")
						&& jsonArray.getJSONObject(i).getJSONObject("headers").toString().contains("authorization")) {

					authorizationResponseDto.setAuthorizationToken(
							jsonArray.getJSONObject(i).getJSONObject("headers").getString("authorization"));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage(e.getMessage());
			System.out.println(e);
			return response;
		}

		response.setList(Arrays.asList(authorizationResponseDto));
		response.setMessage("Data found successfully");
		response.setCode("200");
		response.setMap2(dataForBilldesk);
		return response;
	}

	private String encryptAndSignJWSWithHMAC(String reqStr, String secretKey, String clientid) throws JOSEException {

		JWSSigner signer = new MACSigner(secretKey);
		HashMap<String, Object> customParams = new HashMap<String, Object>();
		customParams.put("clientid", clientid);
		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256, null, null, null, null, null, null, null, null, null,
				null, customParams, null);
		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(reqStr)); // Apply the HMAC
		jwsObject.sign(signer);
		return jwsObject.serialize();
	}

	public String verifyAndDecryptJWSWithHMAC(String encryptedSignedMessage, String verificationKey) throws Exception {

		JWSObject jwsObject = JWSObject.parse(encryptedSignedMessage);

		String clientId = jwsObject.getHeader().getCustomParam("clientid").toString();
		System.out.println("clientId = " + clientId);
		JWSVerifier verifier = new MACVerifier(verificationKey);
		boolean isVerified = jwsObject.verify(verifier);
		System.out.println("is valid " + isVerified);
		String message = jwsObject.getPayload().toString();
		return message;
	}

	@PostMapping(value = "/save_payment_response")
	public void savePaymentResponse(HttpServletRequest request, HttpServletResponse response1) throws IOException {

		System.out.println(request);
		Map<String, String[]> map = request.getParameterMap();
		HttpSession session = request.getSession(true);
		String respString = new Gson().toJson(map.toString());
		response1.setContentType("text/html");
		response1.getWriter().write(respString.toString());
		System.out.println("Response-" + respString.toString());

		Response<BillDeskPaymentResponse> response = new Response<>();

		BillDeskPaymentResponse payres = null;

		TreeMap<String, String> paytmParams = new TreeMap<String, String>();

		String paytmChecksum = null;

		try {
			// Create a tree map from the form post param
			// Request is HttpServletRequest

			Set set = map.entrySet();

			TreeMap<String, String> tree_map = new TreeMap<String, String>();

			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
				// System.err.println("map :"+entry);

				paytmParams.put(entry.getKey(), entry.getValue()[0]);

				tree_map.put(entry.getKey(), entry.getValue()[0]);

			}
			payres = new BillDeskPaymentResponse();
			String string = tree_map.get("transaction_response");
//			String string = "eyJhbGciOiJIUzI1NiIsImNsaWVudGlkIjoidW1wbWt2dnYyIiwia2lkIjoiSE1BQyJ9.eyJtZXJjaWQiOiJVTVBNS1ZWVjIiLCJ0cmFuc2FjdGlvbl9kYXRlIjoiMjAyNS0xMi0wMlQxNjoxOToyNCswNTozMCIsInN1cmNoYXJnZSI6IjAuMDAiLCJwYXltZW50X21ldGhvZF90eXBlIjoiY2FyZCIsImFtb3VudCI6IjExODAuMDAiLCJydSI6Imh0dHBzOi8vdWF0ZHNwLm1wY3ouaW4vZGVwb3NpdF9zY2hlbWUvYXBpL2NvbnN1bWVyL2JpbGwtZGVzay9zYXZlX3BheW1lbnRfcmVzcG9uc2UiLCJvcmRlcmlkIjoiRFMyMDI1MTIwMjE2MTg0NiIsInRyYW5zYWN0aW9uX2Vycm9yX3R5cGUiOiJzdWNjZXNzIiwiZGlzY291bnQiOiIwLjAwIiwicGF5bWVudF9jYXRlZ29yeSI6IjAyIiwiYmFua19yZWZfbm8iOiJDTU5UQU9OWTNDIiwidHJhbnNhY3Rpb25pZCI6IlVITVBJTzkwMDIzM0JHIiwidHhuX3Byb2Nlc3NfdHlwZSI6IjNkcyIsImF1dGhjb2RlIjoiOTY3MTgyIiwiYmFua2lkIjoiSE1QIiwiYWRkaXRpb25hbF9pbmZvIjp7ImFkZGl0aW9uYWxfaW5mbzciOiJSZWdpc3RyYXRpb25fRmVlcyIsImFkZGl0aW9uYWxfaW5mbzEiOiJQUk9EIiwiYWRkaXRpb25hbF9pbmZvMyI6IlNWMTc0MjMwNDEyMTY2NSIsImFkZGl0aW9uYWxfaW5mbzIiOiI5ODA2MjA0NzM3In0sIml0ZW1jb2RlIjoiTUNESVJFQ1QiLCJ0cmFuc2FjdGlvbl9lcnJvcl9jb2RlIjoiVFJTMDAwMCIsImN1cnJlbmN5IjoiMzU2IiwiYXV0aF9zdGF0dXMiOiIwMzAwIiwidHJhbnNhY3Rpb25fZXJyb3JfZGVzYyI6IlRyYW5zYWN0aW9uIFN1Y2Nlc3NmdWwiLCJvYmplY3RpZCI6InRyYW5zYWN0aW9uIiwiY2hhcmdlX2Ftb3VudCI6IjExODAuMDAifQ.WcYGXjMqi4qoMpqc4zZpSxz4SceH_aSU3aXS2rEPNUA";
			System.out.println(string);

//			payres.setMercid(tree_map.get("mercid"));

			System.out.println(tree_map);
//			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(string,
//					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6");
			String verifyAndDecryptJWSWithHMAC = null;
			try {
				verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(string, hashKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(verifyAndDecryptJWSWithHMAC);

			JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);

			//
			payres.setMercid(jobObject.getString("mercid"));
			payres.setAmount(jobObject.getString("amount"));
			payres.setAuth_status(jobObject.getString("auth_status"));
			payres.setBankId(jobObject.getString("bankid"));
			payres.setBankRefNo(jobObject.getString("bank_ref_no"));
			payres.setChargeAmount(jobObject.getString("charge_amount"));

			payres.setCurrency(jobObject.getString("currency"));
			// payres.setMercid(jobObject.getString("transactionid"));
			payres.setObjectId(jobObject.getString("objectid"));
			payres.setOrderid(jobObject.getString("orderid"));
			payres.setPaymentMethodType(jobObject.getString("payment_method_type"));
			payres.setRu(jobObject.getString("ru"));
			payres.setSurcharge(jobObject.getString("surcharge"));
			payres.setTranErrorCode(jobObject.getString("transaction_error_code"));
			payres.setTranErrorDesc(jobObject.getString("transaction_error_desc"));
			payres.setTranId(jobObject.getString("transactionid"));
			payres.setTranProcessType(jobObject.getString("txn_process_type"));
			payres.setTransactionDate(jobObject.getString("transaction_date"));
			payres.setConsumerApplicationNo(jobObject.getJSONObject("additional_info").getString("additional_info3"));

			ConsumerApplicationDetail findByConsumerApplicationNumberDb = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(
							jobObject.getJSONObject("additional_info").getString("additional_info3"));
			Consumer consumerIdDB = null;
			if (Objects.nonNull(findByConsumerApplicationNumberDb)) {
				consumerIdDB = consumerRepositorys
						.findByConsumerId(findByConsumerApplicationNumberDb.getConsumers().getConsumerId());
			}

			payres.setConsumerName(jobObject.getJSONObject("additional_info").getString("additional_info1"));
//			payres.setConsumerName(consumerIdDB.getConsumerName());
//			payres.setConsumerName(findByConsumerApplicationNumberDb.getConsumerName());
			payres.setMobileNo(jobObject.getJSONObject("additional_info").getString("additional_info2"));
			payres.setAdditionalInfo(jobObject.getJSONObject("additional_info").getString("additional_info7"));
			String consumerApp = jobObject.getJSONObject("additional_info").getString("additional_info3");
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(consumerApp);
			ApplicationStatus appStatusDb = null;

//			Date currentDate = new Date();
//			// Create a calendar instance and set it to the current date
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(currentDate);
//
//			// Add three days to the current date
//			calendar.add(Calendar.DAY_OF_MONTH, 3);
//
//			// Get the new date
//			Date newDate = calendar.getTime();
//
//			// Create a formatter for the desired date format
//			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//
//			// Format the dates using the formatter
//			String currentDateFormatted = formatter.format(currentDate);
//			String newDateFormatted = formatter.format(newDate);
//
//			// Display the current date and the new date
//			System.out.println("Current Date: " + currentDateFormatted);
//			System.out.println("New Date: " + newDateFormatted);

			if (jobObject.getString("auth_status").equals("0300")) {
				if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(5L)) {
//					if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {
//						payres.setOytTempAmount(
//								jobObject.getJSONObject("additional_info").getString("additional_info5"));
//					}
					if (findByConsumerApplicationNumber.getAdminStatusChangedTo() != null
							&& findByConsumerApplicationNumber.getAdminStatusChangedTo().equals(5l)) { // added this
																										// check for
						// government case
						// 17-12-2025

						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId());
					} else {
						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
					}
				} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
						.equals(12L)) {
					if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName().equalsIgnoreCase("Deposit")
							|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L
							|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {

						if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {
							ContractorForBid findByApplicationNo = contractorForBidRepository
									.findByApplicationNo(consumerApp);

							if (findByApplicationNo != null) {
								appStatusDb = applicationStatusService
										.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
							} else {
								appStatusDb = applicationStatusService
										.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
							}

						} else {
							appStatusDb = applicationStatusService
									.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
						}

					} else if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName()
							.equalsIgnoreCase("Supervision")) {

						if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() != 5L) {
							appStatusDb = applicationStatusService
									.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
						}

					}

				}

				else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(30L)) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
				} else {
					appStatusDb = applicationStatusService
							.findById(findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId());
					findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
				}
			} else {
				if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(5L)) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId());
				} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
						.equals(12L)) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());

				} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
						.equals(30L)) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.REMAING_DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());
				}
			}

			findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);

			Integer count = billPaymentResponseeeeeeeRepository.getByTransactionId(payres.getTranId());
			BillDeskPaymentResponse save = null;
			if (count == null || count == 0) {

				findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);

				save = billPaymentResponseeeeeeeRepository.save(payres);

				if (save == null) {
//					return "data not save";
				} else {
					ConsumerApplicationDetail save2 = consumerApplictionDetailRepository
							.save(findByConsumerApplicationNumber);
					// Code for sending data to QC portal when payment done
					if ((save2.getApplicationStatus().getApplicationStatusId().equals(23L)
							&& save2.getErpVersion() == null)
							|| save2.getApplicationStatus().getApplicationStatusId().equals(21L)
							|| save2.getApplicationStatus().getApplicationStatusId().equals(20L)) {
						Map<String, String> requestBody = new HashMap<>();

						if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
							MmkyPayAmount findByConsumer = mmkyPayAmountRespository.findByConsumerApplicationNumber(
									findByConsumerApplicationNumber.getConsumerApplicationNo());
							requestBody.put("consumerApplicationNo",
									findByConsumerApplicationNumber.getConsumerApplicationNo());
							requestBody.put("schema", findByConsumer.getSchemeCode());

						} else {

							Optional<ErpEstimateAmountData> findById = estimateAmountRepository
									.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
							if (findById.isPresent()) {
								ErpEstimateAmountData erpEstimateAmountData = findById.get();
								String kwLoad = String.valueOf(erpEstimateAmountData.getKwLoad());
								requestBody.put("kwload", kwLoad);

								String kvaLoad = String.valueOf(erpEstimateAmountData.getKvaLoad());
								requestBody.put("kvaload", kvaLoad);

								String depositAmount = String.valueOf(erpEstimateAmountData.getDepositAmount());
								requestBody.put("deposit_amount", depositAmount);

								String superVisionAmt = String.valueOf(erpEstimateAmountData.getSupervisionAmount());
								requestBody.put("supervision_amount", superVisionAmt);

								String sgst = String.valueOf(erpEstimateAmountData.getSgst());
								requestBody.put("sgst", sgst);

								String cgst = String.valueOf(erpEstimateAmountData.getCgst());
								requestBody.put("cgst", cgst);

								requestBody.put("estimate_name", erpEstimateAmountData.getEstimateName());
								requestBody.put("schema", erpEstimateAmountData.getSchemeCode());

							}
						}

						requestBody.put("consumer_mobile_no",
								findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
						requestBody.put("consumerApplicationNo",
								findByConsumerApplicationNumber.getConsumerApplicationNo());
						requestBody.put("consumer_email_id",
								findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

						requestBody.put("address", findByConsumerApplicationNumber.getAddress());

						requestBody.put("shortDescriptionOfWork",
								findByConsumerApplicationNumber.getShortDescriptionOfWork());
						requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
						requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
						requestBody.put("is_bid_submitted", "false");
						requestBody.put("nature_of_work_name",
								findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkName());
						if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
								&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
							requestBody.put("sspTotalAmount",
									findByConsumerApplicationNumber.getSspTotalAmount().toString());
							requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

						}
						RestTemplate restTemplate = new RestTemplate();

//			         UAT Code
						ResponseEntity<Map> postForEntity = restTemplate.postForEntity(ravindraSendDataAfterPayment,
								requestBody, Map.class);

//			        Production Code
//						ResponseEntity<Map> postForEntity = restTemplate
//								.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/", requestBody, Map.class);
						System.out.println("The result of Post api is :" + postForEntity.getBody());
					}
				}
			} else {
//				return "data not save duplicate transaction id "+payres.getTranId();
			}
			if (save != null) {

				String redirectUrl = "sendReciept" + "?accountId=" + payres.getConsumerApplicationNo() + "&custName="
						+ payres.getConsumerName() + "&orderId=" + payres.getOrderid() + "&txnId=" + payres.getTranId()
						+ "&txnAmount=" + payres.getAmount() + "&txnDate=" + payres.getTransactionDate() + "&status="
						+ payres.getTranErrorDesc() + "&isFailed=" + Boolean.FALSE;

				response1.sendRedirect(redirectUrl);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response1.sendRedirect("sendReciept?msg=" + paytmParams.get("RESPMSG") + "&isFailed=" + Boolean.TRUE
					+ "&isFailedParam=" + Boolean.FALSE);
		}

	}

	@GetMapping(value = "/sendReciept")
	public String sendReciept(@RequestParam(value = "accountId", required = false) String accountId,
			@RequestParam(value = "custName", required = false) String custName,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "txnId", required = false) String txnId,
			@RequestParam(value = "txnAmount", required = false) String txnAmount,
			@RequestParam(value = "txnDate", required = false) String txnDate,
			@RequestParam(value = "status", required = false) String status, @RequestParam("isFailed") boolean isFailed,
			@RequestParam(value = "isFailedParam", required = false) boolean isFailedParam,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) throws Exception {

		String reciept = "";
		HttpSession session = request.getSession(true);
		if (isFailed) {

			if (isFailedParam) {
				reciept = generateFailRecipt(accountId, custName, orderId, txnId, txnAmount, txnDate, status);
			} else {
				reciept = generateFailRecipt(msg);
			}

		} else {
			reciept = generateRecipt(accountId, custName, orderId, txnId, txnAmount, txnDate, status);
		}
		// attribute=">>>>>>>>>>>>>>>>>>>>>>inside send recipt";
		return reciept;
	}

	/**
	 * @param paytmParams
	 * 
	 *                    Generating payment recipt
	 * @throws ParseException
	 */

	private String generateFailRecipt(String msg) throws ParseException, Exception {
		StringBuilder recipt = new StringBuilder();
		recipt.append("<html><head>");
		recipt.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
		recipt.append("<style>table, td, th {border: 1px solid black;}</style>");
		recipt.append(
				"<script type=\"text/javascript\">$(document).ready(function(){$(\"#btn\").click(function () {$(\"#printarea\").print();window.print();});});</script></head><body>\r\n");
		recipt.append(
				"<div id=\"printableArea\"><table align=\"center\" width=\"60%\" id=\"printarea\" cellpadding=\"10\" cellspacing=\"0\" style=\" border-collapse: collapse; border: 1px solid black;\"><thead>\r\n");
//		recipt.append(
//				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>"
//						+ "");
		// Adding the header image
		recipt.append("<tr><th colspan='2' width=\"50%\">"
				+ "<img src='/deposit_scheme/images/header_image.png' alt='Header Image' height='120 px' style='width:100%; display:block; margin:auto;'/>"
				+ "</th></tr>");

		recipt.append("<tr><th colspan=\"2\" style=' height: 50px;'><b>Transaction Fail Kindly retry!!</b>" + msg
				+ "</th></tr></thead><tbody>" + "</tbody></table><br><br></div>\r\n" + "");
		recipt.append(
				"<center><button name=\"print\" onclick=\"printDiv('printableArea')\" id=\"btn\" >Print Receipt</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name='close' id='btn'  onclick='window.close();return false;'>Close Window</button></center>\r\n"
						+ "</td></tr></tfoot></div></body>\r\n"
						+ "<script>function printDiv(divName) {var printContents = document.getElementById(divName).innerHTML;var originalContents = document.body.innerHTML;document.body.innerHTML = printContents;window.print();document.body.innerHTML = originalContents;}</script>"
						+ "<script type = \"text/javascript\"> $(document).on(\"keydown\", function (e) {\r\n"
						+ "        if (e.key == \"F5\" || e.key == \"F11\" || \r\n"
						+ "            (e.ctrlKey == true && (e.key == 'r' || e.key == 'R')) || \r\n"
						+ "            e.keyCode == 116 || e.keyCode == 82) {\r\n"
						+ "                   e.preventDefault();\r\n" + "        }\r\n"
						+ "    $(window).bind('beforeunload', function(e) {  \r\n"
						+ "	   return \\\"Unloading this page may lose data. What do you want to do... \r\n"
						+ "	   e.preventDefault(); \r\n" + "	});" + " });</script></html>");

		return recipt.toString();
	}

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

	private String generateRecipt(String accountId, String custName, String orderId, String txnId, String txnAmount,
			String txnDate, String status) throws ParseException, Exception {
		StringBuilder recipt = new StringBuilder();
		recipt.append("<html><head>");
		recipt.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
		recipt.append("<style>table, td, th {border: 1px solid black;}</style>");
		recipt.append(
				"<script type=\"text/javascript\">$(document).ready(function(){$(\"#btn\").click(function () {$(\"#printarea\").print();window.print();});});</script></head><body>\r\n");
		recipt.append(
				"<div id=\"printableArea\"><table align=\"center\" width=\"60%\" id=\"printarea\" cellpadding=\"10\" cellspacing=\"0\" style=\" border-collapse: collapse; border: 1px solid black;\"><thead>\r\n");

		// Adding the header image
		recipt.append("<tr><th colspan='2' width=\"50%\">"
				+ "<img src='/deposit_scheme/images/header_image.png' alt='Header Image' height='120 px' style='width:100%; display:block; margin:auto;'/>"
				+ "</th></tr>");

		recipt.append("<tr><th colspan=\"2\" style='height: 50px;'><b>DSP PAYMENT RECEIPT</b></th></tr>");
		recipt.append("</thead><tbody>");

		recipt.append("<tr><td style='font-weight:bold'>APPLICATION No.</td><td style='color:blue'>" + accountId
				+ "</td></tr>");
		recipt.append("<tr><td style='font-weight:bold'>APPLICANT NAME</td><td style='color:blue'>" + custName
				+ "</td></tr>\r\n");
		recipt.append("<tr><td style='font-weight:bold'>TRANSACTION NO</td><td style='color:blue'>" + txnId
				+ "</td></tr>\r\n");
		recipt.append(
				"<tr><td style='font-weight:bold'>AMOUNT</td><td style='color:blue'>" + txnAmount + "</td></tr>\r\n");
		recipt.append("<tr><td style='font-weight:bold'>DATE</td><td style='color:blue'>" + txnDate + "</td></tr>\r\n");
		recipt.append(
				"<tr><td style='font-weight:bold'>Payment received towards against your application</td><td style='color:blue'>"
						+ status + "</td></tr>\r\n");
		recipt.append("</tbody></table><br><br></div>\r\n");
		recipt.append(
				"<center><button name=\"print\" onclick=\"printDiv('printableArea')\" id=\"btn\" >Print Receipt</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name='close' id='btn'  onclick='window.close();return false;'>Close Window</button></center>\r\n");
		recipt.append("</td></tr></tfoot></div></body>\r\n");
		recipt.append(
				"<script>function printDiv(divName) {var printContents = document.getElementById(divName).innerHTML;var originalContents = document.body.innerHTML;document.body.innerHTML = printContents;window.print();document.body.innerHTML = originalContents;}</script>\r\n");
		recipt.append("<script type = \"text/javascript\"> $(document).on(\"keydown\", function (e) {\r\n"
				+ "        if (e.key == \"F5\" || e.key == \"F11\" || \r\n"
				+ "            (e.ctrlKey == true && (e.key == 'r' || e.key == 'R')) || \r\n"
				+ "            e.keyCode == 116 || e.keyCode == 82) {\r\n"
				+ "                   e.preventDefault();\r\n" + "    $(window).bind('beforeunload', function(e) { \r\n"
				+ "    return \\\"Unloading this page may lose data. What do you want to do... \r\n"
				+ "    e.preventDefault(); \r\n" + "    });" + "});" + "</script></html>");
		return recipt.toString();
	}

	private String generateFailRecipt(String accountId, String custName, String orderId, String txnId, String txnAmount,
			String txnDate, String status) throws ParseException, Exception {
		StringBuilder recipt = new StringBuilder();
//		Optional<Invoice> invoice = invoiceService.findByCustomerId(accountId,);

//		Invoice invoice = invoiceService.findByConsumerApplicationNumberAndTranscationNo(accountId, txnId);
		recipt.append("<html><head>");
		recipt.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
		recipt.append("<style>table, td, th {border: 1px solid black;}</style>");
		recipt.append(
				"<script type=\"text/javascript\">$(document).ready(function(){$(\"#btn\").click(function () {$(\"#printarea\").print();window.print();});});</script></head><body>\r\n");
		recipt.append(
				"<div id=\"printableArea\"><table align=\"center\" width=\"60%\" id=\"printarea\" cellpadding=\"10\" cellspacing=\"0\" style=\" border-collapse: collapse; border: 1px solid black;\"><thead>\r\n");
//		recipt.append(
//				"<tr><th colspan='2' width=\"50%\"><img src=\"https://rooftop.mpcz.in/rooftop/resources/images/mpmkvvcl1_logo.png\" height='120' alt='MADHYA PRADESH MADHYA KSHETRA VIDYUT VITARAN CO. LTD' width='100%'/></th></tr>");

		// Adding the header image
		recipt.append("<tr><th colspan='2' width=\"50%\">"
				+ "<img src='/deposit_scheme/images/header_image.png' alt='Header Image' height='120 px' style='width:100%; display:block; margin:auto;'/>"
				+ "</th></tr>");

		recipt.append(
				"<tr><th colspan=\"2\" style=' height: 50px;'><b>Transaction Fail Kindly Retry !!</b></th></tr></thead><tbody>");
		recipt.append("<tr><td style='font-weight:bold'>APPLICATION No.</td><td style='color:blue'>" + accountId
				+ "</td></tr>");

//		if (Objects.nonNull(invoice)) {
//			recipt.append("<tr><td style='font-weight:bold'>APPLICANT NAME</td><td style='color:blue'>"
//					+ invoice.getConsumerName() + "</td></tr>\r\n" + "");
//		}
		recipt.append("</td></tr>\r\n" + "<tr><td style='font-weight:bold'>TRANSACTION NO</td><td style='color:blue'>"
				+ txnId + "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>AMOUNT</td><td style='color:blue'>"
				+ txnAmount + "</td></tr>\r\n" + "<tr><td style='font-weight:bold'>DATE</td><td style='color:blue'>"
				+ txnDate + "</td></tr>\r\n"
				+ "<tr><td style='font-weight:bold'>Payment received towards  against your application</td><td style='color:blue'>"
				+ status + "</td></tr>\r\n" + "</tbody></table><br><br></div>\r\n" + "");
		recipt.append(
				"<center><button name=\"print\" onclick=\"printDiv('printableArea')\" id=\"btn\" >Print Receipt</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name='close' id='btn'  onclick='window.close();return false;'>Close Window</button></center>\r\n"
						+ "</td></tr></tfoot></div></body>\r\n"
						+ "<script>function printDiv(divName) {var printContents = document.getElementById(divName).innerHTML;var originalContents = document.body.innerHTML;document.body.innerHTML = printContents;window.print();document.body.innerHTML = originalContents;}</script>\r\n"
						+ "<script type = \"text/javascript\"> $(document).on(\"keydown\", function (e) {\r\n"
						+ "        if (e.key == \"F5\" || e.key == \"F11\" || \r\n"
						+ "            (e.ctrlKey == true && (e.key == 'r' || e.key == 'R')) || \r\n"
						+ "            e.keyCode == 116 || e.keyCode == 82) {\r\n"
						+ "                   e.preventDefault();\r\n" + "        }\r\n"
						+ "   $(window).bind('beforeunload', function(e) { \r\n"
						+ "		 return \\\"Unloading this page may lose data. What do you want to do... \r\n"
						+ "		  e.preventDefault();\r\n" + "		});" + " });" + "</script></html>");

		return recipt.toString();
	}

//	ResponseEntity<Response<T>>
	@PostMapping("/Billdesk_payment_retrive_api")
	public Response<Object> billdeskPaymentRetriveProcess(
			@RequestBody BilldeskPaymentRetriveProcess billdeskPaymentRetriveProcess) {
		Response<Object> response = new Response<Object>();
		try {

//			BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
//					.findByOrderid(billdeskPaymentRetriveProcess.getOrderid());

			BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
					.findByTranId(billdeskPaymentRetriveProcess.getTransactionid());
			if (billDeskPaymentResponse != null) {
				response.setMessage("data already exist.");
				return response;
			}

			JSONObject createObjectForbillDesk = new JSONObject();

//			uat code 
//			createObjectForbillDesk.put("mercid", clientId);
//			createObjectForbillDesk.put("orderid", billdeskPaymentRetriveProcess.getOrderid());
//			String convertObjectInString = encryptAndSignJWSWithHMAC(createObjectForbillDesk.toString(),
//					hashKey, clientKey);

//			producation code		
			createObjectForbillDesk.put("orderid", billdeskPaymentRetriveProcess.getOrderid());
//
			createObjectForbillDesk.put("mercid", clientId);
			createObjectForbillDesk.put("transactionid", billdeskPaymentRetriveProcess.getTransactionid());
			String convertObjectInString = encryptAndSignJWSWithHMAC(createObjectForbillDesk.toString(), hashKey,
					clientKey);

			SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
			clientHttpReq.setProxy(proxy);

			RestTemplate restTemplate = new RestTemplate(clientHttpReq);

			HttpHeaders headers = new HttpHeaders();

			Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
			System.out.println("valueOf--->" + time.toString());
			headers.set("Content-Type", "application/jose");
			headers.set("BD-timestamp", time.toString());
			headers.set("Accept", "application/jose");
			headers.set("BD-traceid", time.toString() + "123");

//			uat url
			String url = transactionUrl;

//			production url
//			String url = "https://api.billdesk.com/payments/ve1_2/transactions/get";

			HttpEntity httpEntity = new HttpEntity<>(convertObjectInString, headers);
			System.err.println("httpEntity-->" + httpEntity);

			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);

			System.err.println("postForEntity-->" + postForEntity);
			String forObject = postForEntity.getBody();
			System.out.println("forObject-------------" + forObject);
			if (forObject == null && forObject.length() <= 0) {
				response.setMessage("response body is null");
				response.setCode("200");
				return response;
			}

//			uat And Production code properties file se  
			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject, hashKey);

//			production code
//			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject,
//					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6");

			JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
			System.out.println(jobObject.getString("auth_status").equalsIgnoreCase("0300"));

			if (jobObject.getString("auth_status").equalsIgnoreCase("0300")) {
				String saveResponseBillDeskTable = saveResponseBillDeskTable(jobObject);
				if (saveResponseBillDeskTable.equals("data save")) {
					response.setMessage("data save payment Response class");
				}
			} else {
				response.setMessage("payment not successfull");
				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

//	@PostMapping("/Billdesk_payment_refundDemandAmount")
//	public Response<Object> refundDemandAmount(
//			@RequestBody BilldeskPaymentRetriveProcess billdeskPaymentRetriveProcess) {
//		Response<Object> response = new Response<Object>();
//		try {
//			BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
//					.findByOrderidAndTranId(billdeskPaymentRetriveProcess.getOrderid(),
//							billdeskPaymentRetriveProcess.getTransactionid());
//
//			if (billDeskPaymentResponse == null) {
//				response.setMessage("data not found please enter a vaild order id and transaction id");
//				return response;
//			}
//
////		create refund numner
//			Random random = new Random();
//			int digit = random.nextInt(900);
//			int digit1 = random.nextInt(90000000);
//
//			String refundId = "REF" + digit + "D" + digit1;
//
//			System.out.println(refundId);
//			JSONObject sendDataBillDesk = new JSONObject();
//
//			sendDataBillDesk.put("transactionid", billDeskPaymentResponse.getTranId());
//			sendDataBillDesk.put("orderid", billDeskPaymentResponse.getOrderid());
//
////		Uat code 	
//			sendDataBillDesk.put("mercid", clientId);
//
////		prod code
////			sendDataBillDesk.put("mercid", "MPMKDSPV2");
//
//			sendDataBillDesk.put("transaction_date", billDeskPaymentResponse.getTransactionDate());
//			sendDataBillDesk.put("txn_amount", billDeskPaymentResponse.getAmount());
//			sendDataBillDesk.put("refund_amount", billDeskPaymentResponse.getAmount());
//			sendDataBillDesk.put("currency", "356");
//			sendDataBillDesk.put("merc_refund_ref_no", refundId);
//
//			JSONObject deviceMap = new JSONObject();
//			deviceMap.put("accept_header", "text/html");
//			deviceMap.put("init_channel", "internet");
//			deviceMap.put("ip", "172.24.200.191");
//			deviceMap.put("fingerprintid", "61b12c18b5d0cf901be34a23ca64bb19");
//
//			sendDataBillDesk.put("device", deviceMap);
//
//			HttpHeaders headers = new HttpHeaders();
//
//			Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
//			headers.set("Content-Type", "application/jose");
//			headers.set("BD-timestamp", time.toString());
//			headers.set("Accept", "application/jose");
//			headers.set("BD-traceid", time.toString() + "123");
//
////		uat code 
//			String convertObjectInString = encryptAndSignJWSWithHMAC(sendDataBillDesk.toString(), hashKey, clientKey);
//
////		producation code		
////			String convertObjectInString = encryptAndSignJWSWithHMAC(sendDataBillDesk.toString(),
////					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6", "mpmkdspv2");
//
//			SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
//			clientHttpReq.setProxy(proxy);
//
//			RestTemplate restTemplate = new RestTemplate(clientHttpReq);
//
////			uat code
//			String url = refundCreate;
//
////			producation code
////			String url = "https://api.billdesk.com/payments/ve1_2/refunds/create";
//
//			HttpEntity httpEntity = new HttpEntity<>(convertObjectInString, headers);
//			System.err.println("httpEntity-->" + httpEntity);
//
//			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);
//
//			System.err.println("postForEntity-->" + postForEntity);
//			String forObject = postForEntity.getBody();
//			System.out.println("forObject-------------" + forObject);
//			if (forObject == null && forObject.length() <= 0) {
//				response.setMessage("response body is null");
//				response.setCode("200");
//				return response;
//			}
//
/////	       uat code
//			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject, hashKey);
//
////			prod  hash key
////			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject,
////					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6");
//
//			JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
//			billDeskPaymentResponse.setRefundId(jobObject.getString("refundid"));
////			billDeskPaymentResponse.setAmount(jobObject.getString("refund_amount"));
//			billDeskPaymentResponse.setTxnAmount(jobObject.getString("txn_amount"));
//			billDeskPaymentResponse.setRefundDate(jobObject.getString("refund_date"));
//			billDeskPaymentResponse.setMercRefundRefNo(jobObject.getString("transactionid"));
//			billDeskPaymentResponse.setRefundStatus(jobObject.getString("refund_status"));
//			billDeskPaymentResponse.setRefundAmount(jobObject.getString("refund_amount"));
//
//			billPaymentResponseeeeeeeRepository.save(billDeskPaymentResponse);
//
//			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
//					.findByConsumerApplicationNumber(billDeskPaymentResponse.getConsumerApplicationNo());
//			String consumerdetails = consumerApplicationStatusBack(findByConsumerApplicationNumber);
//			if (consumerdetails.equals("data save")) {
//				response.setMessage("Amount refund successfull");
//				return response;
//			} else {
//				response.setMessage("Amount refund successfull but applicaiton status not Update");
//				return response;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		response.setMessage("Amount allready refund ");
//		return response;
//	}

	public String consumerApplicationStatusBack(ConsumerApplicationDetail consumerApplicationDetail) {

		ApplicationStatus appStatusDb = consumerApplicationDetail.getApplicationStatus();
		try {

			if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 20
					|| consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 21
					|| consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 23) {
				if (consumerApplicationDetail.getSchemeType().getSchemeTypeName().equalsIgnoreCase("Deposit")) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());
				} else if (consumerApplicationDetail.getSchemeType().getSchemeTypeName().equalsIgnoreCase("Supervision")
						&& consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() != 5l) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());
				} else {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());
				}
				Date currentDate = new Date();

				// Create a calendar instance and set it to the current date
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);

				// Add three days to the current date
				calendar.add(Calendar.DAY_OF_MONTH, 3);

				// Get the new date
				Date newDate = calendar.getTime();

				// Create a formatter for the desired date format
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

				// Format the dates using the formatter
				String currentDateFormatted = formatter.format(currentDate);
				String newDateFormatted = formatter.format(newDate);

				// Display the current date and the new date
				System.out.println("Current Date: " + currentDateFormatted);
				System.out.println("New Date: " + newDateFormatted);

				if (consumerApplicationDetail.getSchemeType().getSchemeTypeName().equalsIgnoreCase("Supervision")) {
					consumerApplicationDetail.setPaymentDate(newDateFormatted);
				}
			} else {
				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId());
			}
			consumerApplicationDetail.setApplicationStatus(appStatusDb);
			consumerApplictionDetailRepository.save(consumerApplicationDetail);

			return "data save";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/fees_genreted_recipt/{consumerApplicationNumber}/{slipGenretatedId}")
	public void genretedRecipt(@PathVariable String consumerApplicationNumber, @PathVariable long slipGenretatedId,
			HttpServletResponse response1) {

		try {
//			List<BillDeskPaymentResponse> payres1 = billPaymentResponseeeeeeeRepository
//					.findByConsumerApplicationNo(consumerApplicationNumber);

			List<BillDeskPaymentResponse> findByConsumerApplicationNoRegistration = billPaymentResponseeeeeeeRepository
					.findByConsumerApplicationNorRegistration(consumerApplicationNumber);

			List<BillDeskPaymentResponse> findByConsumerApplicationDemand = billPaymentResponseeeeeeeRepository
					.findByConsumerApplicationNoDemand(consumerApplicationNumber);

			List<BillDeskPaymentResponse> findByConsumerApplicationRevDemand = billPaymentResponseeeeeeeRepository
					.findByConsumerApplicationNoRevDemand(consumerApplicationNumber);

			List<BillDeskPaymentResponse> allPayments = new LinkedList<>();
			if (!findByConsumerApplicationNoRegistration.isEmpty()) {
				allPayments.add(findByConsumerApplicationNoRegistration.get(0));
			}
			if (!findByConsumerApplicationDemand.isEmpty()) {
				allPayments.add(findByConsumerApplicationDemand.get(0));
			}
			if (!findByConsumerApplicationRevDemand.isEmpty()) {
				allPayments.add(findByConsumerApplicationRevDemand.get(0));
			}

			for (BillDeskPaymentResponse payres : allPayments) {
				double Amount = Double.parseDouble(payres.getAmount());

				if (payres != null && slipGenretatedId == 1l && Amount < 1200) {

					if (payres.getAuth_status().equals("0300")) {
						String redirectUrl = "/deposit_scheme/api/consumer/bill-desk/sendReciept" + "?accountId="
								+ payres.getConsumerApplicationNo() + "&custName=" + payres.getConsumerName()
								+ "&orderId=" + payres.getOrderid() + "&txnId=" + payres.getTranId() + "&txnAmount="
								+ payres.getAmount() + "&txnDate=" + payres.getTransactionDate() + "&status="
								+ "Success" + "&isFailed=" + Boolean.FALSE;

						response1.sendRedirect(redirectUrl);

					}
				}
				if (payres != null && slipGenretatedId == 2l && payres.getAdditionalInfo().equals("Demand_fees")) {
					if (payres.getAuth_status().equals("0300")) {
						String redirectUrl = "/deposit_scheme/api/consumer/bill-desk/sendReciept" + "?accountId="
								+ payres.getConsumerApplicationNo() + "&custName=" + payres.getConsumerName()
								+ "&orderId=" + payres.getOrderid() + "&txnId=" + payres.getTranId() + "&txnAmount="
								+ payres.getAmount() + "&txnDate=" + payres.getTransactionDate() + "&status="
								+ "Success" + "&isFailed=" + Boolean.FALSE;

						response1.sendRedirect(redirectUrl);
					}
				}

//				monika code start
				if (payres != null && slipGenretatedId == 3l
						&& payres.getAdditionalInfo().equals("Revised_Demand_fees")) {
					if (payres.getAuth_status().equals("0300")) {
						String redirectUrl = "/deposit_scheme/api/consumer/bill-desk/sendReciept" + "?accountId="
								+ payres.getConsumerApplicationNo() + "&custName=" + payres.getConsumerName()
								+ "&orderId=" + payres.getOrderid() + "&txnId=" + payres.getTranId() + "&txnAmount="
								+ payres.getAmount() + "&txnDate=" + payres.getTransactionDate() + "&status="
								+ "Success" + "&isFailed=" + Boolean.FALSE;

						response1.sendRedirect(redirectUrl);
					}
				}
//				end

			}

		} catch (Exception e) {
//			response1.sendRedirect("sendReciept?msg=" + paytmParams.get("RESPMSG") + "&isFailed=" + Boolean.TRUE
//					+ "&isFailedParam=" + Boolean.FALSE);
			e.printStackTrace();

		}

	}

//	Billdesk payment service impl me bhi 240 or 241 line pe uncomment or comment karna padta  hai

//	ResponseEntity<Response<T>>
//	@Scheduled(fixedRate = 3600000)
	public void billdeskPaymentRetriveProcessByDate() {
		Response<Object> response = new Response<Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String s = formatter.format(date);

		System.out.println(s + "okjikj");

		List<BillDeskPaymentRequest1> billdesk = null;
		try {
			billdesk = billDeskPaymentRequestRepository.findbyPaymentUpdateDate(s);

			for (BillDeskPaymentRequest1 bill : billdesk) {
				try {

					BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
							.findByOrderid(bill.getOrderId());

					if (billDeskPaymentResponse == null) {

						JSONObject createObjectForbillDesk = new JSONObject();

//			uat and production  code by properties file 
						createObjectForbillDesk.put("mercid", clientId);
						createObjectForbillDesk.put("orderid", bill.getOrderId());
						String convertObjectInString = encryptAndSignJWSWithHMAC(createObjectForbillDesk.toString(),
								hashKey, clientKey);

//			producation code		
//						createObjectForbillDesk.put("mercid", "MPMKDSPV2");
//						createObjectForbillDesk.put("orderid", bill.getOrderId());
//						String convertObjectInString = encryptAndSignJWSWithHMAC(createObjectForbillDesk.toString(),
//								"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6", "mpmkdspv2");

						SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
						Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
						clientHttpReq.setProxy(proxy);

						RestTemplate restTemplate = new RestTemplate(clientHttpReq);

						HttpHeaders headers = new HttpHeaders();

						Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
						System.out.println("valueOf--->" + time.toString());
						headers.set("Content-Type", "application/jose");
						headers.set("BD-timestamp", time.toString());
						headers.set("Accept", "application/jose");
						headers.set("BD-traceid", time.toString() + "123");

//			uat url
						String url = transactionUrl;

//			production url
//						String url = "https://api.billdesk.com/payments/ve1_2/transactions/get";

						HttpEntity httpEntity = new HttpEntity<>(convertObjectInString, headers);
						System.err.println("httpEntity-->" + httpEntity);

						ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, httpEntity,
								String.class);

						System.err.println("postForEntity-->" + postForEntity);
						String forObject = postForEntity.getBody();
						System.out.println("forObject-------------" + forObject);
						if (forObject == null && forObject.length() <= 0) {
							response.setMessage("response body is null");
							response.setCode("200");
							System.out.println("response body is null");
//					return response;
						}

//			uat  code
						String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject, hashKey);

//			production code
//						String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject,
//								"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6");

						JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
						System.out.println(jobObject.getString("auth_status").equalsIgnoreCase("0300"));
						if (jobObject.getString("auth_status").equalsIgnoreCase("0300")) {
							String saveResponseBillDeskTable = saveResponseBillDeskTable(jobObject);
							if (saveResponseBillDeskTable.equals("data save")) {
								response.setMessage("data save payment Response class");
								System.out.println("data save payment Response class");
								System.out.println("data save payment Response class");
							}
						} else {
							response.setMessage("payment not successfull");
							System.out.println("payment not successfull");
//					return response;
						}
					} else {
						System.out.println("payment allready exits hai data base me");
					}
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("is order id pr koi payment nai hua hai");
					System.out.println("is order id pr koi payment nai hua hai");
//				return response;
				}
			}
//		return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/getPaymentDetailsByApplicationNo/{consumerApplicationNo}")
	public ResponseEntity<?> getPaymentDetailsByApplicationNo(
			@PathVariable("consumerApplicationNo") String consumerApplicationNo) {
		Response response = new Response();
		List<ManualPayment> manualPaymentdb = null;
		List<PoseMachinePostData> pose = null;
		Map<String, String> map = new LinkedHashMap<String, String>();

		List<BillDeskPaymentResponse> findByConsumerApplicationNoRegistration = billPaymentResponseeeeeeeRepository
				.findByConsumerApplicationNorRegistration(consumerApplicationNo);

		List<BillDeskPaymentResponse> findByConsumerApplicationDemand = billPaymentResponseeeeeeeRepository
				.findByConsumerApplicationNoDemand(consumerApplicationNo);

		List<BillDeskPaymentResponse> findByConsumerApplicationRevDemand = billPaymentResponseeeeeeeRepository
				.findByConsumerApplicationNoRevDemand(consumerApplicationNo);

		for (BillDeskPaymentResponse bill : findByConsumerApplicationNoRegistration) {
			if (bill.getAuth_status().equals("0300")) {
				map.put(bill.getAdditionalInfo(), bill.getAmount());
				map.put(bill.getAdditionalInfo() + "_Transaction_Id", bill.getTranId());
				map.put(bill.getAdditionalInfo() + "_Transaction_Date", bill.getTransactionDate());
			}
		}
//		 
		if (!findByConsumerApplicationDemand.isEmpty()) {
			for (BillDeskPaymentResponse bill : findByConsumerApplicationDemand) {
				if (bill.getAuth_status().equals("0300")) {
					map.put(bill.getAdditionalInfo(), bill.getAmount());
					map.put(bill.getAdditionalInfo() + "_Transaction_Id", bill.getTranId());
					map.put(bill.getAdditionalInfo() + "_Transaction_Date", bill.getTransactionDate());
				}
			}
		}
		if (!findByConsumerApplicationRevDemand.isEmpty()) {
			for (BillDeskPaymentResponse bill : findByConsumerApplicationRevDemand) {
				if (bill.getAuth_status().equals("0300")) {
					map.put(bill.getAdditionalInfo(), bill.getAmount());
					map.put(bill.getAdditionalInfo() + "_Transaction_Id", bill.getTranId());
					map.put(bill.getAdditionalInfo() + "_Transaction_Date", bill.getTransactionDate());
				}
			}
		}

		// Check if Demand_fees exists in the map
		if (!map.containsKey("Demand_fees")) {
			manualPaymentdb = manualPaymentRepository.findByConAppNo(consumerApplicationNo);
			for (ManualPayment manualPayment : manualPaymentdb) {
				if (manualPayment.getAuthStatus().equals("0300")) {
					map.put(manualPayment.getPaymentType(), manualPayment.getAmount());
					map.put(manualPayment.getPaymentType() + "_Transaction_Id", manualPayment.getBillRefNo());
					map.put(manualPayment.getPaymentType() + "_Transaction_Date", manualPayment.getVerificationDate());
				}
			}

			// Check if Demand_fees still not found and manualPaymentdb is empty
			if ((!map.containsKey("Demand_fees") && (manualPaymentdb == null || manualPaymentdb.isEmpty()))
					|| !map.containsKey("Revised_Demand_fees")) {
				pose = poseMachinePostDataRepository.findByApplicationNumber1(consumerApplicationNo);
				for (PoseMachinePostData p : pose) {
					if (pose != null) {
						map.put(p.getPaymentType(), p.getTxnAmount().toString());
						map.put(p.getPaymentType() + "_Transaction_Id", p.getMrNo());
						map.put(p.getPaymentType() + "_Transaction_Date", p.getDateOfPayment().toString());
					}
				}

			}

			if (map.containsKey("Demand_fees")) {

				BigDecimal tdsDataForDemandFees = getTdsDataForDemandFees(consumerApplicationNo);
				if (tdsDataForDemandFees.compareTo(BigDecimal.ZERO) > 0) {
					map.put("tdsDeducted", tdsDataForDemandFees.toString());
				}
				response.setCode("200");
				response.setMessage("Payment Received");
				response.setList(Arrays.asList(map));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				response.setCode("200");
				response.setMessage("Payment Not Received");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		}
		BigDecimal tdsDataForDemandFees = getTdsDataForDemandFees(consumerApplicationNo);
		if (tdsDataForDemandFees.compareTo(BigDecimal.ZERO) > 0) {
			map.put("tdsDeducted", tdsDataForDemandFees.toString());
		}
		response.setCode("200");
		response.setMessage("Payment Received");
		response.setList(Arrays.asList(map));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	public BigDecimal getTdsDataForDemandFees(String consumerApplicationNo) {
		BigDecimal tdsDeducted = BigDecimal.ZERO;

		ConsumerApplicationDetail byConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.nonNull(byConsumerApplicationNumber)) {
			ErpEstimateAmountData byErpNo = estimateAmountRepository
					.findByErpNo(byConsumerApplicationNumber.getErpWorkFlowNumber());

			System.err.println("aaaaaaaaaaaaaaaa : " + new Gson().toJson(byErpNo));
			if (Objects.nonNull(byErpNo)) {
				if (byConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1l)
						&& (Objects.nonNull(byErpNo.getU_sec_194J_tds2())
								&& byErpNo.getU_sec_194J_tds2().compareTo(BigDecimal.ZERO) >= 0)) {
					tdsDeducted = byErpNo.getU_sec_194J_tds2();
				} else if (byConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(2l)
						&& (Objects.nonNull(byErpNo.getU_194C_tds2_fSupDep())
								&& byErpNo.getU_194C_tds2_fSupDep().compareTo(BigDecimal.ZERO) >= 0)) {
					tdsDeducted = byErpNo.getU_194C_tds2_fSupDep();
				}
			}

		}
		return tdsDeducted;
	}

//	@GetMapping("/getAllOytPaymentDatePendingApplication")
//	@Scheduled(fixedRate = 86400000)
	public ResponseEntity<?> getAllOytPaymentDatePendingApplication() {
		Response response = new Response();

		List<ConsumerApplicationDetail> findAllOytApplication = consumerApplictionDetailRepository
				.findAllOytApplication();
		if (findAllOytApplication.isEmpty()) {
			response.setCode(ResponseMessage.NOT_FOUND);
			response.setMessage("No Oyt application found for filling payment date ");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		for (ConsumerApplicationDetail consumerApplicationDetail : findAllOytApplication) {

			List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
					.findByConsumerApplicationNoDemand(consumerApplicationDetail.getConsumerApplicationNo());
			findByConsumerApplicationNo.stream().filter(a -> a.getAuth_status().equalsIgnoreCase("0300")
					&& a.getAdditionalInfo().equalsIgnoreCase("Demand_fees")).forEach(b -> {
						String transactionDate = b.getTransactionDate();
						System.out.println("Dateeeeeeeeee   " + transactionDate);

						String dateString = transactionDate;

						// Parse the string to LocalDateTime
						LocalDateTime dateTime = LocalDateTime.parse(dateString,
								DateTimeFormatter.ISO_OFFSET_DATE_TIME);

						// Format the LocalDateTime to desired format
						String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

						// Print the formatted date
						System.out.println("Formatted Date: " + formattedDate);

						LocalDateTime modifiedDateTime = dateTime.plusDays(3);

						// Format the modified date
						String modifiedFormattedDate = modifiedDateTime
								.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

						consumerApplicationDetail.setPaymentDate(modifiedFormattedDate);
						ConsumerApplicationDetail save = consumerApplictionDetailRepository
								.save(consumerApplicationDetail);
					});

		}

		return null;
	}

//	public static BigDecimal roundAmountCgstAndSgst(BigDecimal amount) {
//		BigDecimal roundedAmount = amount.setScale(0, RoundingMode.FLOOR); // Get the integer part
//
//		BigDecimal remainingPaise = amount.subtract(roundedAmount); // Get the decimal part (paise)
//
//		if (remainingPaise.compareTo(new BigDecimal(0.5)) >= 0) {
//			roundedAmount = roundedAmount.add(BigDecimal.ONE); // Round up if paise is 50 or more
//		}
//		return roundedAmount;
//	}

	public static BigDecimal roundAmountCgstAndSgst(BigDecimal amount) {
		// Round the amount to 2 decimal places to avoid precision issues
		BigDecimal preciseAmount = amount.setScale(2, RoundingMode.HALF_UP);

		// Get the integer part
		BigDecimal roundedAmount = preciseAmount.setScale(0, RoundingMode.FLOOR);

		// Get the fractional part (paise)
		BigDecimal remainingPaise = preciseAmount.subtract(roundedAmount);

		// Round up if paise is 0.5 or more
		if (remainingPaise.compareTo(new BigDecimal("0.5")) >= 0) {
			roundedAmount = roundedAmount.add(BigDecimal.ONE);
		}

		return roundedAmount;
	}

//	@GetMapping("/saveBillRequest")
	public String saveBillRequest(String consumerNo) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss a");
		Date currentDate = new Date();
		String currentFormattedDate = formatter.format(currentDate).toUpperCase();
		System.out.println("Current Date: " + currentFormattedDate);

		// Create a Calendar instance and set it to current date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Subtract 5 minutes from current date
		calendar.add(Calendar.MINUTE, -6);
		Date fiveMinutesBack = calendar.getTime();
		String fiveMinutesBackFormatted = formatter.format(fiveMinutesBack).toUpperCase();
		System.out.println("5 minutes back Date: " + fiveMinutesBackFormatted);

		// Retrieve bill requests within the last five minutes
		List<BillDeskPaymentRequest1> saveBillRequest = billDeskPaymentRequestRepository
				.findBillRequestsBetween(currentFormattedDate, fiveMinutesBackFormatted);
		System.out.println("Saved Bill Requests: " + saveBillRequest);

		// Check if any request matches the given consumer number
		boolean consumerFound = false;
		for (BillDeskPaymentRequest1 request : saveBillRequest) {
			System.out.println("Checking request: " + request);
			if (request.getConsumerAppliNo().equals(consumerNo)) {
				System.out.println("Found matching record: " + request);
				consumerFound = true;
				break;
			}
		}

		if (consumerFound) {
			return "Data cannot be saved in request table";
		} else {
			return "Data can be saved in request table";
		}
	}

	public String saveResponseBillDeskTable(JSONObject jobObject) {
		BillDeskPaymentResponse payres = new BillDeskPaymentResponse();
		try {
//			uat ki mercId
			payres.setMercid(clientId);

//			prod ka mercId
//			payres.setMercid("MPMKDSPV2");

			payres.setAmount(jobObject.getString("amount"));
			payres.setAuth_status(jobObject.getString("auth_status"));
			payres.setBankId(jobObject.getString("bankid"));
			payres.setBankRefNo(jobObject.getString("bank_ref_no"));
			payres.setChargeAmount(jobObject.getString("charge_amount"));
			payres.setCurrency(jobObject.getString("currency"));
			payres.setObjectId(jobObject.getString("objectid"));
			payres.setOrderid(jobObject.getString("orderid"));
			payres.setPaymentMethodType(jobObject.getString("payment_method_type"));
			payres.setRu(jobObject.getString("ru"));
			payres.setSurcharge(jobObject.getString("surcharge"));
			payres.setTranErrorCode(jobObject.getString("transaction_error_code"));
			payres.setTranErrorDesc(jobObject.getString("transaction_error_desc"));
			payres.setTranId(jobObject.getString("transactionid"));
			payres.setTranProcessType(jobObject.getString("txn_process_type"));
			payres.setTransactionDate(jobObject.getString("transaction_date"));
			payres.setConsumerApplicationNo(jobObject.getJSONObject("additional_info").getString("additional_info3"));
			ConsumerApplicationDetail findByConsumerApplicationNumberDb = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(
							jobObject.getJSONObject("additional_info").getString("additional_info3"));
			Consumer consumerIdDB = null;
			if (Objects.nonNull(findByConsumerApplicationNumberDb)) {
				consumerIdDB = consumerRepositorys
						.findByConsumerId(findByConsumerApplicationNumberDb.getConsumers().getConsumerId());
			}

//			payres.setConsumerName(jobObject.getJSONObject("additional_info").getString("additional_info1"));
			payres.setConsumerName(findByConsumerApplicationNumberDb.getConsumerName());
			payres.setMobileNo(jobObject.getJSONObject("additional_info").getString("additional_info2"));
			payres.setAdditionalInfo(jobObject.getJSONObject("additional_info").getString("additional_info7"));
			String consumerApp = jobObject.getJSONObject("additional_info").getString("additional_info3");

			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(consumerApp);
			ApplicationStatus appStatusDb = null;

			if (jobObject.getString("auth_status").equals("0300")) {
				if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(5L)) {

					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
				} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
						.equals(12L)) {
					if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName().equalsIgnoreCase("Deposit")
							|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L
							|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {

						if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {
							ContractorForBid findByApplicationNo = contractorForBidRepository
									.findByApplicationNo(consumerApp);

							if (findByApplicationNo != null) {
								appStatusDb = applicationStatusService
										.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
							} else {
								appStatusDb = applicationStatusService
										.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
							}

						} else {
							appStatusDb = applicationStatusService
									.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
						}

					} else if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName()
							.equalsIgnoreCase("Supervision")) {

						if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() != 5L) {
							appStatusDb = applicationStatusService
									.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
						}

					}

				}

				else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(30L)) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
				} else {
					appStatusDb = applicationStatusService
							.findById(findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId());
					findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
				}
			} else {
				if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() == 5l) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId());
				} else {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());
				}
			}
			findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
			consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);

			BillDeskPaymentResponse save = billPaymentResponseeeeeeeRepository.save(payres);
			if (save == null) {
//				return "data not save";
			} else {
				ConsumerApplicationDetail save2 = consumerApplictionDetailRepository
						.save(findByConsumerApplicationNumber);
				// Code for sending data to QC portal when payment done
				if ((save2.getApplicationStatus().getApplicationStatusId().equals(23L) && save2.getErpVersion() == null)
						|| save2.getApplicationStatus().getApplicationStatusId().equals(21L)
						|| save2.getApplicationStatus().getApplicationStatusId().equals(20L)) {
					Map<String, String> requestBody = new HashMap<>();

					if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
						MmkyPayAmount findByConsumer = mmkyPayAmountRespository.findByConsumerApplicationNumber(
								findByConsumerApplicationNumber.getConsumerApplicationNo());
						requestBody.put("consumerApplicationNo",
								findByConsumerApplicationNumber.getConsumerApplicationNo());
						requestBody.put("schema", findByConsumer.getSchemeCode());

					} else {

						Optional<ErpEstimateAmountData> findById = estimateAmountRepository
								.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
						if (findById.isPresent()) {
							ErpEstimateAmountData erpEstimateAmountData = findById.get();
							String kwLoad = String.valueOf(erpEstimateAmountData.getKwLoad());
							requestBody.put("kwload", kwLoad);

							String kvaLoad = String.valueOf(erpEstimateAmountData.getKvaLoad());
							requestBody.put("kvaload", kvaLoad);

							String depositAmount = String.valueOf(erpEstimateAmountData.getDepositAmount());
							requestBody.put("deposit_amount", depositAmount);

							String superVisionAmt = String.valueOf(erpEstimateAmountData.getSupervisionAmount());
							requestBody.put("supervision_amount", superVisionAmt);

							String sgst = String.valueOf(erpEstimateAmountData.getSgst());
							requestBody.put("sgst", sgst);

							String cgst = String.valueOf(erpEstimateAmountData.getCgst());
							requestBody.put("cgst", cgst);

							requestBody.put("estimate_name", erpEstimateAmountData.getEstimateName());
							requestBody.put("schema", erpEstimateAmountData.getSchemeCode());

						}
					}

					requestBody.put("consumer_mobile_no",
							findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
					requestBody.put("consumerApplicationNo",
							findByConsumerApplicationNumber.getConsumerApplicationNo());
					requestBody.put("consumer_email_id",
							findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

					requestBody.put("address", findByConsumerApplicationNumber.getAddress());

					requestBody.put("shortDescriptionOfWork",
							findByConsumerApplicationNumber.getShortDescriptionOfWork());
					requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
					requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
					requestBody.put("is_bid_submitted", "false");
					if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
							&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
						requestBody.put("sspTotalAmount",
								findByConsumerApplicationNumber.getSspTotalAmount().toString());
						requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

					}

					RestTemplate restTemplate = new RestTemplate();

//		         UAT Code
					ResponseEntity<Map> postForEntity = restTemplate.postForEntity(ravindraSendDataAfterPayment,
							requestBody, Map.class);

//		        Production Code
//					ResponseEntity<Map> postForEntity = restTemplate
//							.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/", requestBody, Map.class);
					System.out.println("The result of Post api is :" + postForEntity.getBody());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "data save";
	}

	@GetMapping("/settlementDate")
	public Response<Object> settlementDate(@RequestParam String from_date) {
		Response<Object> response = new Response<>();
		try {
			// Log method entry
			logger.info("Entering settlementDate method");

			LocalDate currentDate = LocalDate.now();
			System.err.println("currentDate : " + currentDate);

			String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			System.err.println("Formatted Date : " + formattedCurrentDate);

			String formattedminusOneDayDate = currentDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			System.err.println("minusOneDayDate : " + formattedminusOneDayDate);

			// Log system IP address
			InetAddress localHost = InetAddress.getLocalHost();
			String ipAddress = localHost.getHostAddress();
			logger.info("System IP Address: {}", ipAddress);
			System.err.println("System IP Address: {}" + ipAddress);
//
//			JSONObject sendDataBillDesk = new JSONObject();
//			sendDataBillDesk.put("mercid", "MPMKDSPLT");
//			sendDataBillDesk.put("from_date", formattedminusOneDayDate);
//			sendDataBillDesk.put("to_date", formattedCurrentDate);

			JSONObject sendDataBillDesk = new JSONObject();
			sendDataBillDesk.put("mercid", "MPMKDSPLT");

			sendDataBillDesk.put("from_date", from_date);
			sendDataBillDesk.put("to_date", from_date);

			HttpHeaders headers = createHeaders();

			String convertObjectInString = encryptBilldeskData(sendDataBillDesk.toString());
			if (convertObjectInString == null) {
				throw new RuntimeException("Payload encryption failed");
			}

			// Configure proxy for HTTP request
			SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
			clientHttpReq.setProxy(proxy);
			logger.info("Proxy configured: {}", proxy.toString());

			// Set up RestTemplate and make the API call
			RestTemplate restTemplate = new RestTemplate(clientHttpReq);
			String url = "https://api.billdesk.com/pasettlements/ve1_2/settlements/get";
			logger.info("Sending request to URL: {}", url);

			HttpEntity<String> httpEntity = new HttpEntity<>(convertObjectInString, headers);

			logger.info("httpEntity: {}", httpEntity);

			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);

			logger.info("Response received: {}", postForEntity.getBody());

			// Process the response
			if (postForEntity.getBody() == null || postForEntity.getBody().isEmpty()) {
				response.setMessage("Response body is null");
				response.setCode(HttpCode.NULL_OBJECT);
				logger.warn("Response body is null or empty");
				return response;
			} else {
				String pvNumber = null;
				String utrNumber = null;
				String responseBody = postForEntity.getBody();
				String verifyAndDecrypt = decryptBilldeskData(responseBody);
				System.err.println("verifyAndDecrypt : " + new Gson().toJson(verifyAndDecrypt));

				JSONObject json = new JSONObject(verifyAndDecrypt);
				JSONArray recordsArray = json.getJSONArray("records");
				// Iterate through the array and get "pv_number"
				for (int i = 0; i < recordsArray.length(); i++) {
					JSONObject record = recordsArray.getJSONObject(i);
					pvNumber = record.getString("pv_number");
					System.err.println("pvNumber : " + pvNumber);
					utrNumber = record.getString("utr");
					System.err.println("UTR : " + utrNumber);
				}
				HttpHeaders headers1 = createHeaders();

				System.err.println("headers1 : " + headers1);
				logger.info("headers1 : " + headers1);

				SimpleClientHttpRequestFactory clientHttpReq1 = new SimpleClientHttpRequestFactory();
				Proxy proxy1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
				clientHttpReq1.setProxy(proxy1);
				logger.info("Proxy configured: {}", proxy1.toString());

				RestTemplate restTemplate1 = new RestTemplate(clientHttpReq1);

				JSONObject settlementDetails = new JSONObject();
				settlementDetails.put("mercid", "MPMKDSPLT");
				settlementDetails.put("pv_number", pvNumber);

				System.err.println("settlementDetails : " + settlementDetails.toString());
				logger.info("settlementDetails : " + settlementDetails.toString());

				String encryptBilldeskData = encryptBilldeskData(settlementDetails.toString());

				String url1 = "https://api.billdesk.com/pasettlements/ve1_2/settlements/getSettlementDetails";
				logger.info("Sending request to URL: {}", url1);
				HttpEntity<String> httpEntity1 = new HttpEntity<>(encryptBilldeskData, headers1);
				ResponseEntity<String> postForEntity1 = restTemplate1.postForEntity(url1, httpEntity1, String.class);
				logger.info("Response receivedaaaaaaaaaa: {}", postForEntity1.getBody());
				String encryptedResponse = postForEntity1.getBody();
				String decryptBilldeskData = decryptBilldeskData(encryptedResponse);
				System.err.println("decryptBilldeskData : " + decryptBilldeskData);
				if (decryptBilldeskData == null) {
					throw new RuntimeException("Decrypted Payload failed");
				} else {
					JSONObject jsonObj = new JSONObject(decryptBilldeskData);
					JSONArray recordsArr = jsonObj.getJSONArray("records");

					System.err.println("jsonObj : " + jsonObj);

					for (int i = 0; i < recordsArr.length(); i++) {
						JSONObject record = recordsArr.getJSONObject(i);

						// Check if "billdesk_id" exists
						if (record.has("billdesk_id")) {
							String billdeskTransactionId = record.getString("billdesk_id");
							System.err.println("billdeskTransactionId : " + billdeskTransactionId);

							BillDeskPaymentResponse transactionIdDB = billPaymentResponseeeeeeeRepository
									.findByTranId(billdeskTransactionId);

							if (transactionIdDB != null) {
								System.err.println("Settlement Date : " + record.getString("settlement_date"));

								System.err.println("UTR NO. FROM BILLDESK : " + utrNumber);
								if (utrNumber != null && !utrNumber.trim().isEmpty()) {
									transactionIdDB.setUtrNo(utrNumber);
								}

								transactionIdDB.setSettlementDate(record.getString("settlement_date"));
								billPaymentResponseeeeeeeRepository.save(transactionIdDB);
							}

						} else {
							System.err.println("Skipping record at index " + i + ": 'billdesk_id' is missing");
							System.err.println("Record details: " + record.toString());

							logger.info("Skipping record at index " + i + ": 'billdesk_id' is missing");
							logger.info("Record details: " + record.toString());
						}
					}
				}
			}
			response.setMessage("Success");
			response.setCode("200");

		} catch (Exception e) {
			logger.error("Exception occurred: ", e);
			response.setMessage(e.getMessage());
			response.setCode("500");
		}

		logger.info("Exiting settlementDate method");
		return response;
	}

	private HttpHeaders createHeaders() {
		Long timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/jose");
		headers.set("BD-timestamp", timestamp.toString());
		headers.set("Accept", "application/jose");
		headers.set("BD-traceid", timestamp.toString() + "123");
		return headers;
	}

	public static String encryptBilldeskData(String payload) throws Exception {

//		jo certificate billdesk walo ne diya hai,       uska path chaiye wo bhi public certificate ka
		RSAPublicKey publicKey = VerifyJWS.getPublicKey();

//   	 discom walo ka public certificate jo billdesk ko diya hai
//        RSAPrivateKey privateKey = (RSAPrivateKey) VerifyJWS.loadPrivateKey();

		String keyStorePath = "D:\\jks\\mpcz.jks"; // Provide the correct path to the uploaded JKS file

//		String keyStorePath = "C:\\Users\\M.M\\Desktop\\jose fram work\\mpcz.jks"; // Provide the correct path to the uploaded JKS file

		String alias = "mpcz_alias"; // Replace with the alias for your key or certificate
		String keyStorePassword = "changeit"; // Replace with the password for the keystore
		String keyPassword = "changeit"; // Replace with the password for the private key
		String convertObjectInString = null;
		RSAPrivateKey privateKey = null;
		try {
			logger.info("Attempting to retrieve public and private keys");
			privateKey = VerifyJWS.getPrivateKeyFromKeyStore(keyStorePath, alias, keyStorePassword, keyPassword);
			logger.info("Successfully retrieved keys");

			String thumbPrintSigningKeyFingerPrint = VerifyJWS.signingKeyFingerPrint();// apna certificate
			String thumbPrintEncryptionKeyFingerPrint = VerifyJWS.encryptionKeyFingerPrint();// unka diya

			logger.info("Fingerprints generated successfully");
			logger.info("sendDataBillDesk : " + payload);
			logger.info("publicKey " + publicKey);
			logger.info("privateKey " + privateKey);
			logger.info("thumbPrintSigningKeyFingerPrint " + thumbPrintSigningKeyFingerPrint);
			logger.info("thumbPrintEncryptionKeyFingerPrint " + thumbPrintEncryptionKeyFingerPrint);

			convertObjectInString = JoseHelper.encryptAndSign(payload, publicKey, privateKey,
					thumbPrintSigningKeyFingerPrint, thumbPrintEncryptionKeyFingerPrint, "mpmkcert");

			logger.info("convertObjectInString " + convertObjectInString);
			System.err.println("convertObjectInString " + convertObjectInString);
			logger.info("Payload encrypted and signed successfully");

//			return convertObjectInString;
		} catch (Exception e) {
			logger.error("Error in processing: ", e); // Stack trace will show details
			e.printStackTrace();
		}
		return convertObjectInString;
	}

	public static String decryptBilldeskData(String encryptedResponse) throws Exception {

		String keyStorePath = "D:\\jks\\mpcz.jks"; // Provide the correct path to the uploaded JKS file

//		String keyStorePath = "C:\\Users\\M.M\\Desktop\\jose fram work\\mpcz.jks"; // Provide the correct path to the uploaded JKS file

		String alias = "mpcz_alias"; // Replace with the alias for your key or certificate
		String keyStorePassword = "changeit"; // Replace with the password for the keystore
		String keyPassword = "changeit"; // Replace with the password for the private key
		String convertObjectInString = null;
		RSAPrivateKey privateKey = null;
		String verifyAndDecrypt = null;
		logger.info("Attempting to retrieve public and private keys");
//			RSAPublicKey publicKey = VerifyJWS.getPublicKeyFromKeyStore(keyStorePath, alias, keyStorePassword);
		privateKey = VerifyJWS.getPrivateKeyFromKeyStore(keyStorePath, alias, keyStorePassword, keyPassword);
		logger.info("Successfully retrieved keys");

		RSAPublicKey publicKeyForDecryption = VerifyJWS.getPublicKeyForDecryption();
		System.err.println("publicKeyForDecryption : " + publicKeyForDecryption);

		boolean verifyJWSSignature = VerifyJWS.verifyJWSSignature(encryptedResponse, publicKeyForDecryption);
		System.err.println("verifyJWSSignature : " + verifyJWSSignature);
		String pvNumber = null;
		if (verifyJWSSignature) {
			verifyAndDecrypt = JoseHelper.verifyAndDecrypt(encryptedResponse, publicKeyForDecryption, privateKey);
			System.err.println("verifyAndDecrypt : " + new Gson().toJson(verifyAndDecrypt));

		} else {
			System.err.println("verifyJWSSignature is false");
		}
		return verifyAndDecrypt;

	}

//	@Value("${client.id1}")
//	private String clientId1;
//
//	
//
//	@Value("${hash.key1}")
//	private String hashKey1;
//
//	@Value("${client.key1}")
//	private String clientKey1;

	@PostMapping("/Billdesk_payment_retrive_api1")
	public Response<Object> billdeskPaymentRetriveProcess1(
			@RequestBody BilldeskPaymentRetriveProcess billdeskPaymentRetriveProcess) {
		Response<Object> response = new Response<Object>();
		try {

//			BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
//					.findByOrderid(billdeskPaymentRetriveProcess.getOrderid());

			BillDeskPaymentResponse billDeskPaymentResponse = billPaymentResponseeeeeeeRepository
					.findByTranId(billdeskPaymentRetriveProcess.getTransactionid());
			if (billDeskPaymentResponse != null) {
				response.setMessage("data already exist.");
				return response;
			}

			JSONObject createObjectForbillDesk = new JSONObject();

//			uat code 
//			createObjectForbillDesk.put("mercid", clientId);
//			createObjectForbillDesk.put("orderid", billdeskPaymentRetriveProcess.getOrderid());
//			String convertObjectInString = encryptAndSignJWSWithHMAC(createObjectForbillDesk.toString(),
//					hashKey, clientKey);

//			producation code		
//			createObjectForbillDesk.put("orderid", billdeskPaymentRetriveProcess.getOrderid());		
//
			createObjectForbillDesk.put("mercid", "MPMKDSPV2");
			createObjectForbillDesk.put("transactionid", billdeskPaymentRetriveProcess.getTransactionid());
			String convertObjectInString = encryptAndSignJWSWithHMAC(createObjectForbillDesk.toString(),
					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6", "mpmkdspv2");

			SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
			clientHttpReq.setProxy(proxy);

			RestTemplate restTemplate = new RestTemplate(clientHttpReq);

			HttpHeaders headers = new HttpHeaders();

			Long time = Timestamp.valueOf(LocalDateTime.now()).getTime();
			System.out.println("valueOf--->" + time.toString());
			headers.set("Content-Type", "application/jose");
			headers.set("BD-timestamp", time.toString());
			headers.set("Accept", "application/jose");
			headers.set("BD-traceid", time.toString() + "123");

//			uat url
			String url = transactionUrl;

//			production url
//			String url = "https://api.billdesk.com/payments/ve1_2/transactions/get";

			HttpEntity httpEntity = new HttpEntity<>(convertObjectInString, headers);
			System.err.println("httpEntity-->" + httpEntity);

			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);

			System.err.println("postForEntity-->" + postForEntity);
			String forObject = postForEntity.getBody();
			System.out.println("forObject-------------" + forObject);
			if (forObject == null && forObject.length() <= 0) {
				response.setMessage("response body is null");
				response.setCode("200");
				return response;
			}

//			uat  code
			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject, hashKey);

//			production code
//			String verifyAndDecryptJWSWithHMAC = verifyAndDecryptJWSWithHMAC(forObject,
//					"IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6");

			JSONObject jobObject = new JSONObject(verifyAndDecryptJWSWithHMAC);
			System.out.println(jobObject.getString("auth_status").equalsIgnoreCase("0300"));

			if (jobObject.getString("auth_status").equalsIgnoreCase("0300")) {
				String saveResponseBillDeskTable = saveResponseBillDeskTable(jobObject);
				if (saveResponseBillDeskTable.equals("data save")) {
					response.setMessage("data save payment Response class");
				}
			} else {
				response.setMessage("payment not successfull");
				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

}