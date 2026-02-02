package com.mpcz.deposit_scheme.backend.api.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAppCancellationRefundAmount;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;
import com.mpcz.deposit_scheme.backend.api.dto.TestDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAppCancellationRefundAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAppReturnMaterialRefundAmntRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.RefundAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.SSPService;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Testing Controller", description = "Rest api for ContractorDetailForBidController.")
@RestController
@RequestMapping("/api/user")
public class TestingController {

	@GetMapping("/getsp/{circleId}")
	// ParticipantAndNotParticipantDto getQcConsumerbid() throws Exception {
	ResponseEntity<Response<?>> getQcConsumerbid(@PathVariable Long circleId) throws Exception {

		Response<TestDto> response = new Response<>();

		TestDto dto = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			String url = "https://rooftop-uat.mpcz.in:8443/security-deposit/circlemaster/circle/" + circleId;

			HttpEntity<String> entity = new HttpEntity<>(url, headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			String body = responseEntity.getBody();

			if (body != null) {

				JSONObject job = new JSONObject(body);

				if (job.getString("code").equals("200")) {

					String string = job.getString("message");
					System.out.println(string);

					JSONObject jsonObject = job.getJSONObject("object");
					System.out.println(jsonObject.getString("circleId"));
					System.out.println(jsonObject.getString("circleName"));
					System.out.println(jsonObject.getString("gmName"));
					System.out.println(jsonObject.getString("dgmStcName"));
					// System.out.println(jsonObject.getString("circleCode));

					dto = new TestDto();

					dto.setMessage(string);
					// dto.setCircleCode(string);
					dto.setCircleId(circleId);
					dto.setCircleName(jsonObject.getString("circleName"));
					dto.setGmName(jsonObject.getString("gmName"));
					dto.setDgmStcName(jsonObject.getString("dgmStcName"));

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		response.setList(Arrays.asList(dto));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

//	@GetMapping("/sendDataToSSp")
//	public  void sendDataToSSp() {
//		Response response = new Response();
//		
//		
//		String url = "https://survey.mpcz.in:8080/ssp-web/department/post/work/status/ssp";
//		RestTemplate restTemplate = new RestTemplate();
//		
//		Map<String,Object> map = new HashMap<>();
//		map.put("nscApplicationNo", "CZNSCT645");
//		map.put("workStatus", 33);
//		
//		try {
//		ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, map, String.class);
//		System.out.println(postForEntity.getBody() + "aaaaaaaaaaaaaaaaaaa");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	
//	}

	@Autowired
	private SSPService sSPService;

//	@GetMapping("/sendDataToSSp")
//	public void dspFronEndUrl(HttpServletResponse response1) throws IOException {
//
//	    RestTemplate restTemplate = new RestTemplate(); // Use the RestTemplate with logging interceptor
//	    ResponseEntity<String> postForEntity = null;
//	    Map<String, Object> requestMap = new HashMap<>();
//	    requestMap.put("consumerName", "fjdhjkhkjhdjfhj");
//	    requestMap.put("mobileNumber", "8770672335");
//	    requestMap.put("emailId", "testaaaaaaaaaaaa@gmail.com");
//	    requestMap.put("address", "aaaaaaaaaaaaaaaaaa");
//	    requestMap.put("nscApplicationNo", "trttryr1223324323rw");  // Set NSC Application No
//	    requestMap.put("schemeType", 2L);
//	    requestMap.put("natureOfWork", 2L);
//	    requestMap.put("dcCode", "2304402");
//
//	    System.err.println("requestMap : " +new Gson().toJson(requestMap));
//	    String url = "https://rooftop-uat.mpcz.in:8888/deposit_scheme/api/ssp/sspSignUp";
//	    try {
//	        postForEntity = restTemplate.postForEntity(url, requestMap, String.class);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//
//	    System.err.println("postForEntity : " +new Gson().toJson(postForEntity));
//	    
//	    if (postForEntity != null) {
//	        // Parse the response from the 3rd party API
//	        String responseBody = postForEntity.getBody();
//	        JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
//	        String token = jsonResponse.get("token").getAsString();
//	        JsonObject consumerObject = jsonResponse.getAsJsonArray("list").get(0).getAsJsonObject();
//	        
//	        String redirectUrl = "https://rooftop-uat.mpcz.in:8888/deposit-scheme/#/consumer/new-application" +
//	                "?token=" + URLEncoder.encode(token, "UTF-8") +
//	                "&consumer=" + URLEncoder.encode(consumerObject.toString(), "UTF-8");
//
//	        // Perform the redirection
//	        response1.sendRedirect(redirectUrl);
//	      
//	        // Prepare the data for redirection
////	        Map<String, Object> responseMap = new HashMap<>();
////	        responseMap.put("token", token);
////	        responseMap.put("consumer", consumerObject);
////	        responseMap.put("redirectUrl", "https://rooftop-uat.mpcz.in:8888/deposit-scheme/#/consumer/new-application");
////
////	        System.err.println("token  : " +token.toString());
////	        System.err.println("consumer  : " +consumerObject);
////	        System.err.println("responseMap : " + new Gson().toJson(responseMap));
//	         // Return the token and consumer object to the frontend
//	    } else {
//	    	 response1.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process the request");
//	    }
//	}

	@GetMapping("/sendDataToSSp")
	public void dspFronEndUrl(HttpServletResponse response1, HttpServletRequest request) throws IOException {

		// Instead of calling the 3rd party API, use predefined values
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4NzcwNjcyMzM1IiwiaXNGaXJzdExvZ2luIjp0cnVlLCJjcmVhdGVkIjoxNzI5MDc0MDg0MDMwLCJpcCI6IjA6MDowOjA6MDowOjA6MSIsImNvbnN1bWVyZnVsbG5hbWUiOiJNb25pa2EiLCJ1YSI6IlBvc3RtYW5SdW50aW1lLzcuNDIuMCIsImV4cCI6MTcyOTEwNDA4NCwiaWF0IjoxNzI5MDc0MDg0fQ.7tJSFQkHPgWxqx6DSGDg6Y0iXIkpzy-l_mU0UC81Lu5rK9rLVzcclt_38xePJCTGjQV4SvsZ7Q_d__tH3C9ZIA";

		// Predefined consumerObject
		JsonObject consumerObject = new JsonObject();
		consumerObject.addProperty("created", "2023-09-21T10:06:22.256+00:00");
		consumerObject.addProperty("consumerId", 5223);
		consumerObject.addProperty("consumerName", "Monika");
		consumerObject.addProperty("consumerEmailId", "dufysdchukdychchhc@gmail.com");
		consumerObject.addProperty("consumerMobileNo", "8770672335");
		consumerObject.addProperty("accountNonExpired", false);
		consumerObject.addProperty("accountNonLocked", false);
		consumerObject.addProperty("isFirstLogin", true);
		consumerObject.addProperty("consumerLoginId", "8770672335");
		consumerObject.addProperty("loginAttemp", 0);
		consumerObject.addProperty("address", "e'prfuk2tduelidlifoewitdedoudyedyt3d;iuewfufo;erkterEPOf67ero;j");
		consumerObject.addProperty("active", true);
		consumerObject.addProperty("deleted", false);

		// Build the redirect URL using the predefined token and consumerObject
		String redirectUrl = "https://rooftop-uat.mpcz.in:8888/deposit-scheme/#/consumer/new-application";

		// Perform the redirection
		response1.sendRedirect(redirectUrl);
		response1.setHeader("Authorization",
				"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4NzcwNjcyMzM1IiwiaXNGaXJzdExvZ2luIjp0cnVlLCJjcmVhdGVkIjoxNzI5MDc0MDg0MDMwLCJpcCI6IjA6MDowOjA6MDowOjA6MSIsImNvbnN1bWVyZnVsbG5hbWUiOiJNb25pa2EiLCJ1YSI6IlBvc3RtYW5SdW50aW1lLzcuNDIuMCIsImV4cCI6MTcyOTEwNDA4NCwiaWF0IjoxNzI5MDc0MDg0fQ.7tJSFQkHPgWxqx6DSGDg6Y0iXIkpzy-l_mU0UC81Lu5rK9rLVzcclt_38xePJCTGjQV4SvsZ7Q_d__tH3C9ZIA");
		// Optionally, log the redirect URL and objects for debugging purposes

		System.err.println("Token: " + token);
		String header = response1.getHeader("Authorization");
		System.err.println("heasdddddd : " + header);

		System.err.println("Consumer Object: " + consumerObject.toString());
		System.err.println("Redirect URL: " + redirectUrl);
	}

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ErpRevRepository erpRevRepository;

	@Autowired
	private RefundAmountRepository refundAmountRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ConsumerAppCancellationRefundAmountRepository consumerAppCancellationRefundAmountRepository;

	@Autowired
	private ConsumerAppReturnMaterialRefundAmntRepository consumerAppReturnMaterialRefundAmntRepository;

	@Autowired
	private UserRepository userRepository;

	public RefundAmount saveConsumerApplicationCancel(RefundAmount refundAmount)
			throws ConsumerApplicationDetailException {

//		BigDecimal refundableAmount = BigDecimal.ZERO;
		AtomicReference<BigDecimal> totalWapaskrneWalaPaisa = new AtomicReference<>(BigDecimal.ZERO);

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {
			List<BillDeskPaymentResponse> allPaymentDetails = billPaymentResponseeeeeeeRepository
					.getAllPaymentDetails(refundAmount.getConsumerApplicationNo());

			allPaymentDetails.stream().forEach(bill -> {
				ConsumerAppCancellationRefundAmount conAppCancel = new ConsumerAppCancellationRefundAmount();
				conAppCancel.setApplicationNo(refundAmount.getConsumerApplicationNo());
				conAppCancel.setMerchantId(bill.getMercid());
				conAppCancel.setOrderId(bill.getOrderid());
				conAppCancel.setPaymentType(bill.getAdditionalInfo());
				conAppCancel.setTxnAmount(new BigDecimal(bill.getAmount()));
				conAppCancel.setTxnId(bill.getTranId());
				conAppCancel.setTransactionDate(bill.getTransactionDate());
				consumerAppCancellationRefundAmountRepository.save(conAppCancel);
			});
			List<ErpRev> erpRevDB = erpRevRepository.findByConsumerAppNo(refundAmount.getConsumerApplicationNo());
			AtomicReference<BigDecimal> refundableAmount = new AtomicReference<>(BigDecimal.ZERO);
			erpRevDB.stream().forEach(erpRev -> {
				ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
						.findByApplicationNoAndTxnAmount(refundAmount.getConsumerApplicationNo(), erpRev.getPayAmt());
				BigDecimal remCgst = Optional.ofNullable(erpRev.getRemCgst()).orElse(BigDecimal.ZERO);
				BigDecimal remSgst = Optional.ofNullable(erpRev.getRemSgst()).orElse(BigDecimal.ZERO);
				BigDecimal totalCgstSgst = remCgst.add(remSgst);

				refundableAmount.set(applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst));
				applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount.get());
				consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);

				totalWapaskrneWalaPaisa.set(totalWapaskrneWalaPaisa.get().add(refundableAmount.get()));
			});

			ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
					.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());

			ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = null;
			applicationNoAndTxnAmountDB = consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)
					? consumerAppCancellationRefundAmountRepository.findByApplicationNoAndTxnAmount(
							refundAmount.getConsumerApplicationNo(), erpEstimateDB.getTotalBalanceSupervisionAmount())
					: consumerAppCancellationRefundAmountRepository.findByApplicationNoAndTxnAmount(
							refundAmount.getConsumerApplicationNo(), erpEstimateDB.getTotalBalanceDepositAmount());

			BigDecimal cgst = Optional.ofNullable(erpEstimateDB.getCgst()).orElse(BigDecimal.ZERO);
			BigDecimal sgst = Optional.ofNullable(erpEstimateDB.getSgst()).orElse(BigDecimal.ZERO);
			BigDecimal totalCgstSgst = cgst.add(sgst);

			refundableAmount.set(applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst));

			applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount.get());
			consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);
			totalWapaskrneWalaPaisa.set(totalWapaskrneWalaPaisa.get().add(refundableAmount.get()));

		}
		refundAmount.setRefundAmount(totalWapaskrneWalaPaisa.get());
		refundAmount.setConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo());
		refundAmount.setConsumerAppId(consumerApplicationDetail.getConsumerApplicationId());
		refundAmount.setRefundType("Cancellation_Amount");
		double digit = Math.random();
		double digit1 = Math.random();
		String valueOf = String.valueOf(digit);
		String valueOf1 = String.valueOf(digit1);
		String substring = valueOf.substring(2, 8);
		String substring1 = valueOf1.substring(2, 8);
		refundAmount.setRefundVoucherNo(substring + substring1);
		RefundAmount refundAmntDB = refundAmountRepository.save(refundAmount);
		if (Objects.isNull(refundAmntDB)) {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
		} else {
			consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
			consumerApplictionDetailRepository.save(consumerApplicationDetail);
			return refundAmntDB;
		}

	}

	@Autowired
	private ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

//	@GetMapping("/saveContractorActualWorkCompletionDates")
//	public ResponseEntity<String> saveContractorActualWorkCompletionDates() {
//		RestTemplate restTemplate = new RestTemplate();
//
//		// PROD URL
//		String url = "https://qcportal.mpcz.in/tkc/bid_not_participated_docs";
//
//		try {
//			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//			String responseBody = response.getBody();
//			if (responseBody != null) {
//				List<ConsumerApplicationDetail> saveContractorExpectedDates = consumerApplictionDetailRepository
//						.saveContractorActualWorkCompletionDates();
//
//				System.err.println(
//						"Total application count jinki work completion date fill krna hai "
//								+ saveContractorExpectedDates.size());
//
//				JSONArray dataArray = new JSONObject(responseBody).optJSONArray("data");
//
//				if (dataArray != null) {
//					for (ConsumerApplicationDetail consumerApp : saveContractorExpectedDates) {
//						String consumerApplicationNo = consumerApp.getConsumerApplicationNo();
//						boolean applicationFound = false;
//
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							String consumerApplicationNumber = item.optString("consumer_application_no", null);
//
//							if (consumerApplicationNo.equals(consumerApplicationNumber)) {
//								applicationFound = true;
//
//								String actualWorkCompletionDate = item.optString("actual_work_completion_date", null);
//								System.err.println("QC Portal se aai application" +consumerApplicationNumber);
//								System.err.println("actualWorkCompletionDate : " + actualWorkCompletionDate);
//								if (actualWorkCompletionDate != null && !actualWorkCompletionDate.isEmpty() && 
//								        !"null".equalsIgnoreCase(actualWorkCompletionDate)) {
//									System.err.println(
//											"QC se actual work completion date aai for y application "
//													+ consumerApplicationNo);
//
//									ContractorForBidWorkStatus contractorForBidStatusDB = contractorForBidWorkStatusRepository
//											.findByConsumerApplicationNo(consumerApplicationNo);
//									if (contractorForBidStatusDB == null) {
//										String workStartDate = item.optString("contractor_work_started", null);
//										String materialHandover = item.optString("material_handover_site", null);
//										String materialInstallationStart = item.optString("material_installation_start",
//												null);
//										String materialInstallationFinish = item
//												.optString("material_installation_finished", null);
//										String workCompletion = item.optString("expected_work_completion_date", null);
//										String userId = item.optString("User_Id", null);
//
//										if (workStartDate != null && materialHandover != null
//												&& materialInstallationStart != null
//												&& materialInstallationFinish != null && workCompletion != null
//												&& userId != null) {
//											System.err.println("Work details available for consumer application: "
//													+ consumerApplicationNo);
//											System.err.println("The application is Accepted by contractor : "
//													+ consumerApplicationNo);
//											System.err.println("workStartDate : " + workStartDate);
//											System.err.println("materialHandover : " + materialHandover);
//											System.err.println(
//													"materialInstallationStart : " + materialInstallationStart);
//											System.err.println(
//													"materialInstallationFinish : " + materialInstallationFinish);
//											System.err.println("expected workCompletion : " + workCompletion);
//											System.err.println("User_Id : " + userId);
//											System.err
//													.println("actualWorkCompletionDate : " + actualWorkCompletionDate);
//											ContractorForBidWorkStatus contractorForBidWorkStatus = new ContractorForBidWorkStatus();
//											contractorForBidWorkStatus
//													.setConsumerApplicationNumber(consumerApplicationNo);
//											contractorForBidWorkStatus.setMaterialHandoverSiteDate(materialHandover);
//											contractorForBidWorkStatus
//													.setMaterialInstallFinishDate(materialInstallationFinish);
//											contractorForBidWorkStatus
//													.setMaterialInstallStartDate(materialInstallationStart);
//											contractorForBidWorkStatus.setConWorkStartedDate(workStartDate);
//											contractorForBidWorkStatus.setConWorkCompleteDate(workCompletion);
//											contractorForBidWorkStatus.setUserId(Long.parseLong(userId));
//											contractorForBidWorkStatus
//													.setActualWorkCompletionDate(actualWorkCompletionDate);
//
//											contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);
//										}
//									} else {
//										contractorForBidStatusDB.setActualWorkCompletionDate(actualWorkCompletionDate);
//									}
//
////									contractorForBidWorkStatusRepository.save(contractorForBidStatusDB);
//
//									// Update consumer application status based on scheme type
//									if (consumerApp.getSchemeType().getSchemeTypeId().equals(2L)) {
//										consumerApp.setApplicationStatus(applicationStatusRepository.findById(
//												ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC
//														.getId())
//												.get());
//										System.err.println("applcation status is for application no. : "
//												+ consumerApplicationNo + "is : "
//												+ applicationStatusRepository.findById(
//														ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC
//																.getId())
//														.get());
//									}
//
////									consumerApplictionDetailRepository.save(consumerApp);
//								} 
//
//								break; // No need to continue searching if application found
//							}
//						}
//
//						if (!applicationFound) {
//							System.err.println(
//									"Application not found in tkc/bid_not_participated_docs: " + consumerApplicationNo);
//						}
//					}
//				} else {
//					System.err.println("No data found in the external response.");
//				}
//
//				System.err
//						.println("Total applications fetched from DSP Database: " + saveContractorExpectedDates.size());
////				System.out.println("Total applications where contractor did not take action: " + notActionedCount);
////				System.out.println("Total applications rejected by contractor: " + rejectedCount);
////				System.out.println("Total applications accepted by contractor: " + acceptedCount);
//
//				return ResponseEntity.ok("Processing completed successfully.");
//			} else {
//				return ResponseEntity.status(500).body("Error: Received empty response from the external API.");
//			}
//		} catch (HttpClientErrorException.NotAcceptable ex) {
//			return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
//		} catch (Exception ex) {
//			ex.printStackTrace(); // Print stack trace for debugging
//			return ResponseEntity.status(500).body("Internal Server Error occurred: " + ex.getMessage());
//		}
//	}

//	@GetMapping("/dbQueryCheck")
	public void dbQueryCheck() {
		List<ConsumerApplicationDetail> saveContractorExpectedDates = consumerApplictionDetailRepository
				.saveContractorActualWorkCompletionDates();

		System.err.println("data retrieved successfully ");
	}

	@GetMapping("/saveContractorActualWorkCompletionDates")
	public ResponseEntity<String> saveContractorActualWorkCompletionDates() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://qcportal.mpcz.in/tkc/bid_not_participated_docs";

		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			String responseBody = response.getBody();

			if (responseBody == null) {
				return ResponseEntity.status(500).body("Error: Received empty response from the external API.");
			}

			List<ConsumerApplicationDetail> applicationsToUpdate = consumerApplictionDetailRepository
					.saveContractorActualWorkCompletionDates();
			System.err.println(
					"Total applications requiring work completion date update: " + applicationsToUpdate.size());

			JSONArray dataArray = new JSONObject(responseBody).optJSONArray("data");

			if (dataArray == null) {
				System.err.println("No data found in the external response.");
				return ResponseEntity.ok("No data found to process.");
			}

			int onlyDateUpdatedCount = 0;
			int newObjectCreatedCount = 0;
			int notFoundCount = 0;
			int applicationWithNullActualDateCount = 0;

			List<String> onlyDateUpdatedList = new ArrayList<>();
			List<String> newObjectCreatedList = new ArrayList<>();
			List<String> notFoundList = new ArrayList<>();
			List<String> applicationWithNullActualDateList = new ArrayList<>();

			for (ConsumerApplicationDetail consumerApp : applicationsToUpdate) {
				String consumerApplicationNo = consumerApp.getConsumerApplicationNo();
				boolean applicationFound = false;

				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject item = dataArray.getJSONObject(i);
					String consumerApplicationNumber = item.optString("consumer_application_no", null);

					if (consumerApplicationNo.equals(consumerApplicationNumber)) {
						applicationFound = true;

						String actualWorkCompletionDate = item.optString("actual_work_completion_date", null);
						System.err.println("QC Portal application found: " + consumerApplicationNumber);
						System.err.println("actualWorkCompletionDate: " + actualWorkCompletionDate);

						if (actualWorkCompletionDate != null && !actualWorkCompletionDate.isEmpty()
								&& !"null".equalsIgnoreCase(actualWorkCompletionDate)) {
							List<ContractorForBidWorkStatus> contractorForBidStatusDB = contractorForBidWorkStatusRepository
									.findByConsumerApplicationNumber(consumerApplicationNo);
							if (!contractorForBidStatusDB.isEmpty()) {
								for (ContractorForBidWorkStatus contractorForBidStatus : contractorForBidStatusDB) {
									contractorForBidStatus.setActualWorkCompletionDate(actualWorkCompletionDate);
									contractorForBidWorkStatusRepository.save(contractorForBidStatus);
									onlyDateUpdatedCount++;
									onlyDateUpdatedList.add(consumerApplicationNo);
								}
							} else {
								ContractorForBidWorkStatus contractorForBidStatus = new ContractorForBidWorkStatus();
								contractorForBidStatus.setConsumerApplicationNumber(consumerApplicationNo);
								contractorForBidStatus
										.setMaterialHandoverSiteDate(item.optString("material_handover_site", null));
								contractorForBidStatus.setMaterialInstallFinishDate(
										item.optString("material_installation_finished", null));
								contractorForBidStatus.setMaterialInstallStartDate(
										item.optString("material_installation_start", null));
								contractorForBidStatus
										.setConWorkStartedDate(item.optString("contractor_work_started", null));
								contractorForBidStatus
										.setConWorkCompleteDate(item.optString("expected_work_completion_date", null));
								contractorForBidStatus.setUserId(Long.valueOf(item.optString("User_Id", null)));
								contractorForBidStatus.setActualWorkCompletionDate(actualWorkCompletionDate);

//								contractorForBidWorkStatusRepository.save(contractorForBidStatus);
								newObjectCreatedCount++;
								newObjectCreatedList.add(consumerApplicationNo);
							}
							if (consumerApp.getSchemeType().getSchemeTypeId().equals(2L)) {
								consumerApp.setApplicationStatus(applicationStatusRepository.findById(
										ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC
												.getId())
										.orElse(null));
								System.err.println("Application status updated for application: "
										+ consumerApplicationNo + " Application status : "
										+ ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC
												.getId());
//								consumerApplictionDetailRepository.save(consumerApp);
							}
						} else {
							applicationWithNullActualDateCount++;
							applicationWithNullActualDateList.add(consumerApplicationNo);
						}
						break; // Exit inner loop once application is processed
					}
				}
				if (!applicationFound) {
					notFoundCount++;
					notFoundList.add(consumerApplicationNo);
				}
			}

			System.err.println("Processing Summary:");
			System.err.println("Applications with only actual work completion date updated: " + onlyDateUpdatedCount);
			System.err.println("List: " + onlyDateUpdatedList);
			System.err.println("Applications with new object created: " + newObjectCreatedCount);
			System.err.println("List: " + newObjectCreatedList);
			System.err.println("Applications Actual date is null : " + applicationWithNullActualDateCount);
			System.err.println("List : " + applicationWithNullActualDateList);
			System.err.println("Applications not found in 3rd party API response: " + notFoundCount);
			System.err.println("List: " + notFoundList);

			String summary = String.format(
					"Processing completed.\n" + "Only Date Updated: %d\n" + "List: %s\n" + "New Object Created: %d\n"
							+ "List: %s\n" + "Null Actual Date: %d\n" + "List: %s\n" + "Not Found: %d\n" + "List: %s",
					onlyDateUpdatedCount, onlyDateUpdatedList, newObjectCreatedCount, newObjectCreatedList,
					applicationWithNullActualDateCount, applicationWithNullActualDateCount, notFoundCount,
					notFoundList);

			return ResponseEntity.ok(summary);
		} catch (HttpClientErrorException.NotAcceptable ex) {
			return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(500).body("Internal Server Error occurred: " + ex.getMessage());
		}
	}

	@GetMapping("/saveContractorExpectedDates")
	public ResponseEntity<String> getBidNotParticipatedDocs() {
		RestTemplate restTemplate = new RestTemplate();

		// PROD URL
		String url = "https://qcportal.mpcz.in/tkc/bid_not_participated_docs";

		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			String responseBody = response.getBody();

			int notActionedCount = 0;
			int rejectedCount = 0;
			int acceptedCount = 0;

			if (responseBody != null) {
				List<ConsumerApplicationDetail> saveContractorExpectedDates = consumerApplictionDetailRepository
						.saveContractorExpectedDates();

				JSONArray dataArray = new JSONObject(responseBody).optJSONArray("data");

				if (dataArray != null) {
					for (ConsumerApplicationDetail consumerApp : saveContractorExpectedDates) {
						String consumerApplicationNo = consumerApp.getConsumerApplicationNo();
						boolean applicationFound = false;

						for (int i = 0; i < dataArray.length(); i++) {
							JSONObject item = dataArray.getJSONObject(i);
							String consumerApplicationNumber = item.optString("consumer_application_no", null);

							if (consumerApplicationNo.equals(consumerApplicationNumber)) {
								applicationFound = true;

								String isRejected = item.optString("is_rejected", "null");

								// Handle different contractor statuses
								if ("null".equals(isRejected)) {
									System.out.println("Contractor did not take the action on application: "
											+ consumerApplicationNo);
									notActionedCount++;
								} else if ("true".equals(isRejected)) {
									System.out.println(
											"The application is rejected by contractor: " + consumerApplicationNo);
									rejectedCount++;
								} else if ("false".equals(isRejected)) {
									acceptedCount++;
									System.out.println(
											"The application is accepted by contractor: " + consumerApplicationNo);

									String workStartDate = item.optString("contractor_work_started", null);
									String materialHandover = item.optString("material_handover_site", null);
									String materialInstallationStart = item.optString("material_installation_start",
											null);
									String materialInstallationFinish = item.optString("material_installation_finished",
											null);
									String workCompletion = item.optString("expected_work_completion_date", null);
									String userId = item.optString("User_Id", null);

									if (workStartDate != null && materialHandover != null
											&& materialInstallationStart != null && materialInstallationFinish != null
											&& workCompletion != null && userId != null) {
										System.out.println("Work details available for consumer application: "
												+ consumerApplicationNo);
										System.out.println(
												"The application is Accepted by contractor : " + consumerApplicationNo);
										System.out.println("workStartDate : " + workStartDate);
										System.out.println("materialHandover : " + materialHandover);
										System.out.println("materialInstallationStart : " + materialInstallationStart);
										System.out
												.println("materialInstallationFinish : " + materialInstallationFinish);
										System.out.println("expected workCompletion : " + workCompletion);
										System.out.println("User_Id : " + userId);
										ContractorForBidWorkStatus contractorForBidWorkStatus = new ContractorForBidWorkStatus();
										contractorForBidWorkStatus.setConsumerApplicationNumber(consumerApplicationNo);
										contractorForBidWorkStatus.setMaterialHandoverSiteDate(materialHandover);
										contractorForBidWorkStatus
												.setMaterialInstallFinishDate(materialInstallationFinish);
										contractorForBidWorkStatus
												.setMaterialInstallStartDate(materialInstallationStart);
										contractorForBidWorkStatus.setConWorkStartedDate(workStartDate);
										contractorForBidWorkStatus.setConWorkCompleteDate(workCompletion);
										contractorForBidWorkStatus.setUserId(Long.parseLong(userId));

										contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);

										// Update consumer application status based on scheme type
										if (consumerApp.getSchemeType().getSchemeTypeId().equals(1L)) {
											consumerApp.setApplicationStatus(applicationStatusRepository
													.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId())
													.get());
										} else {
											consumerApp.setApplicationStatus(applicationStatusRepository
													.findById(ApplicationStatusEnum.PENDING_FOR_WORK_COMPLETION.getId())
													.get());
										}

										consumerApplictionDetailRepository.save(consumerApp);
									} else {
										System.out.println(
												"Accepted application has missing dates for consumerApplicationNo: "
														+ consumerApplicationNo);
									}
								}
								break; // No need to continue searching if application found
							}
						}

						if (!applicationFound) {
							System.out.println(
									"Application not found in tkc/bid_not_participated_docs: " + consumerApplicationNo);
						}
					}
				} else {
					System.out.println("No data found in the external response.");
				}

				System.out
						.println("Total applications fetched from DSP Database: " + saveContractorExpectedDates.size());
				System.out.println("Total applications where contractor did not take action: " + notActionedCount);
				System.out.println("Total applications rejected by contractor: " + rejectedCount);
				System.out.println("Total applications accepted by contractor: " + acceptedCount);

				return ResponseEntity.ok("Processing completed successfully.");
			} else {
				return ResponseEntity.status(500).body("Error: Received empty response from the external API.");
			}
		} catch (HttpClientErrorException.NotAcceptable ex) {
			return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
		} catch (Exception ex) {
			ex.printStackTrace(); // Print stack trace for debugging
			return ResponseEntity.status(500).body("Internal Server Error occurred: " + ex.getMessage());
		}
	}

	@GetMapping("/dbQueryCheck")
	public void sspApplicationDCUpates() {
		try {
//			ConsumerApplictionDetailRepository consumerApplictiony = new ConsumerApplictionDetailRepository();
			System.err.println("consumerApplictionDetailRepository : " + consumerApplictionDetailRepository);
			consumerApplictionDetailRepository.sspApplicationDCUpates();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@PostConstruct
//	public void init() {
//	    System.out.println("poseMachinePostDataRepository injected? " + (poseMachinePostDataRepository != null));
//	}
	@Autowired
	PoseMachinePostDataRepository poseMachinePostDataRepository;

	@GetMapping("/refNoGenerator")
	private String refNoGenerator() {

		String refNo = null;

		Calendar calendar = Calendar.getInstance();

		refNo = getAlphaCorrospondingNumber(calendar.get(Calendar.YEAR) - 2021)

				+ getAlphaCorrospondingNumber(calendar.get(Calendar.MONTH))

				+ (calendar.get(Calendar.DAY_OF_MONTH) <= 9 ? calendar.get(Calendar.DAY_OF_MONTH)
						: getAlphaCorrospondingNumber(calendar.get(Calendar.DAY_OF_MONTH) - 10))

				+ getAlphaCorrospondingNumber(calendar.get(Calendar.HOUR_OF_DAY))

				+ (String.valueOf(calendar.get(Calendar.MINUTE)).length() == 1 ? "0" + calendar.get(Calendar.MINUTE)
						: calendar.get(Calendar.MINUTE))

				+ (String.valueOf(calendar.get(Calendar.SECOND)).length() == 1 ? "0" + calendar.get(Calendar.SECOND)
						: calendar.get(Calendar.SECOND));

//		String nextRefSequence = poseMachinePostDataRepository.getNextRefSequence();
//        String formattedSeq = String.format("%02d", nextRefSequence);
//
//        System.err.println("generated txn id : " + "DSP" + refNo + formattedSeq);
		return "DSP" + refNo;
	}

	private String getAlphaCorrospondingNumber(int number) {
		return String.valueOf((char) ('A' + number % 26));
	}

//	public static void main(String[] args) {
//		TestingController testingController = new TestingController();
//		String refNoGenerator = testingController.refNoGenerator();
//		System.err.println("refNoGenerator : " + refNoGenerator);
//	}

//	public static void main(String args[]) throws Exception{
//
//	Collections.sort(null);
//
//		ArrayList<Integer>  array = new ArrayList<>(); 
//
//		Thread t1 = new Thread(() -> {
//
//			for (int i = 0; i < 1000; i++) {
//				array.add(i);
//			}
//		});
//
//		Thread t2 = new Thread(() -> {
//
//			for (int i = 0; i < 1000; i++) {
//				array.add(i);
//			}
//		});
//		
//		t1.start();
//		t2.start();
//		
//			t1.run();
//			t2.run();
//		
//		
//		System.out.println(array.size());
//
////		TreeSet<A> treeset = new TreeSet(new A());
////		treeset.add(a);
////		treeset.add(a1);
////		treeset.add(a2);
////		treeset.add(a3);
////		treeset.add(a4);
//
////		treeset.stream().forEach(x->System.out.println(x.getName()));
//	}

//	@GetMapping("/redirect-to-frontend")
//	public ResponseEntity<Void> redirectToFrontend() {
//		URI redirectUri = URI.create("https://saralsanyojan.mpcz.in:8888/fill_connection_details/CZNSC1818155");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(redirectUri);
//		return new ResponseEntity<>(headers, HttpStatus.FOUND); // HTTP 302 Redirect
//	}

	@GetMapping("/redirect-to-frontend")
	public ResponseEntity<Void> redirectToDSPFrontendForLogin() {
		URI redirectUri = URI.create("https://rooftop-uat.mpcz.in:8888/deposit-scheme/#/consumer/login");
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(redirectUri);
		return new ResponseEntity<>(headers, HttpStatus.FOUND);

	}

//	public static void main(String[] args) {
//		Date date = new Date();
//		System.err.println("date : " + date);
//		getPosPaymentDate(date);
//
//		System.err.println("aaaaaaaaaaa : " + getPosPaymentDate(date));
//	}

	public static String getPosPaymentDate(Date dateOfPayment) {
		String localDateTime = dateOfPayment.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+05:30"));
		return localDateTime;
	}

	public static void main(String[] args) {
//	nput: S = { “the”, “quick”, “brown”, “fox”, “quick”}, word1 = “the”, word2 = “fox”
//		Output: 3
//		String[] str = { "the", "quick","the", "brown", "fox", "quick" };
//		int i = 0;
//		int j = 0;
//		int l;
//		for (int k = 0; k < str.length; k++) {
//			System.out.println("Index " + k + " -> " + str[k]);
//			if (str[k].equals("the")) {
//				i = k;
//				System.err.println("the value of i = " + i);
//			}
//			if (str[k].equals("fox")) {
//				j = k;
//				System.err.println("the value of j = " + j);
//			}
//
//		}
//		l = j - i;
//		System.err.println("the minimum distance is  : " + l);

//		Remove duplicate from int array
//		int[] intArray = { 1, 9, 3, 5, 3, 6, 4, 2, 8, 9, 5, 8 };
//		Set<Integer> uniqueData = new HashSet<>();
//		Set<Integer> notAddedData = new HashSet<>();
//		for (int i : intArray) {
//			if (!uniqueData.add(i)) {
//				notAddedData.add(i); // duplicate element
//			}
//		}
//
//		System.out.println("Unique Data: " + uniqueData);
//		System.out.println("Not Added Data (Duplicates): " + notAddedData);

//		int[] tempArray = {};
//		for (int i = 0; i < intArray.length; i++) {
//			System.err.println(intArray[i]);
//		}

		int x = 10;
		int y = 25;
		int z = x + y;

		System.out.println("Sum of x+y = " + z);

		

	}

}
