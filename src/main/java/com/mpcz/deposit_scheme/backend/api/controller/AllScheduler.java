package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.jose.JoseHelper;
import com.mpcz.deposit_scheme.backend.api.jose.VerifyJWS;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;

@RequestMapping("/scheduler")
@RestController
public class AllScheduler {

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	private SMSDirectService smsDirectService;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDate;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

	private static final Logger logger = LoggerFactory.getLogger(AllScheduler.class);

//	@GetMapping("/demandFees")
//	@Scheduled(fixedRate = 240000)         //  1 Minute (60000)
////	@Scheduled(cron = "0 50 12 * * ?")
//	public void AdditionalInfoSetDemandFees() {
//
//		billPaymentResponseeeeeeeRepository.AdditionalInfoSetDemandFees().stream().forEach(bill -> {
//			bill.setAdditionalInfo("Demand_fees");
//			System.out.println(bill.getConsumerApplicationNo());
//			logger.error("AdditionalInfoSetDemandFees : " +bill.toString());
//			billPaymentResponseeeeeeeRepository.save(bill);
//		});
//	}
//
//	@GetMapping("/RegistrationFees")
//	@Scheduled(fixedRate = 240000)     //4 minute
//	public void AdditionalInfoSetRegistrationFees() {
//
//		billPaymentResponseeeeeeeRepository.AdditionalInfoSetRegistrationFees().stream().forEach(bill -> {
//			bill.setAdditionalInfo("Registration_Fees");
//			System.out.println(bill.getConsumerApplicationNo());
//			logger.error("AdditionalInfoSetRegistrationFees : " +bill.toString());
//			billPaymentResponseeeeeeeRepository.save(bill);
//		});
//	}
//
//	// Monika code start
//
////	 @GetMapping("/deActiveAppAfter30Days")
//	@Scheduled(fixedRate = 86400000) // 24 Hour
////	@Scheduled(cron="0 0 */12 * * *")    //every 12 hours
//	public void deActiveAppAfter30Days() {
//		Response<Object> response = new Response<Object>();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//		String s = formatter.format(date);
//
//		System.out.println(s);
//
//		List<MmkyPayAmount> findAll = mmkyPayAmountRespository.findAll();
//		for (MmkyPayAmount M : findAll) {
//
//			LocalDate dbDate = LocalDate.parse(M.getCreated());
//
//			LocalDate currentDate1 = dbDate.plusDays(30);
//			LocalDate date2 = LocalDate.parse(s);
//
//			if (currentDate1.compareTo(date2) < 0) {
//				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
//						.findByConsumerApplicationNoDemand(M.getConsumerApplicationNumber());
//				if (findByConsumerApplicationNo.isEmpty()) {
//					M.setActive(false);
//					M.setDeleted(true);
//					System.out.println(M.getConsumerApplicationNumber());
//					ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
//							.findByConsumerApplicationNumber(M.getConsumerApplicationNumber());
//					if (findByConsumerApplicationNumber != null) {
//						findByConsumerApplicationNumber.setActive(false);
//						findByConsumerApplicationNumber.setDeleted(true);
//						ApplicationStatus applicationStatus = applicationStatusService.findById(
//								ApplicationStatusEnum.APPLICATION_REJECTED_DEMAND_NOTE_PAYMENT_NOT_DONE_WITHIN_30_DAYS
//										.getId());
//						findByConsumerApplicationNumber.setApplicationStatus(applicationStatus);
//						consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
//					}
//				}
//
//				mmkyPayAmountRespository.save(M);
//			}
//		}
//
//	}

//	 @GetMapping("/mkmyMsgAfter25Days")
//	@Scheduled(fixedRate = 86400000) // 24 Hour
////	@Scheduled(cron="0 0 */12 * * *")   //every 12 hours
//	public void mkmyMsgAfter25Days() {
//		Response<Object> response = new Response<Object>();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//		String s = formatter.format(date);
//
//		System.out.println(s);
//
//		List<MmkyPayAmount> findAll = mmkyPayAmountRespository.findAllUnsendMsg();
//		for (MmkyPayAmount M : findAll) {
//
//			ConsumerApplicationDetail consumerapplication = consumerApplictionDetailRepository
//					.findByConsumerApplicationNumber(M.getConsumerApplicationNumber());
//
//			LocalDate dbDate = LocalDate.parse(M.getCreated());
//
//			LocalDate currentDate1 = dbDate.plusDays(25);
//			LocalDate date2 = LocalDate.parse(s);
//
//			if (currentDate1.compareTo(date2) == 0) {
//				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
//						.findByConsumerApplicationNoDemand(M.getConsumerApplicationNumber());
//
//				System.out.println("consumer_application" + M.getConsumerApplicationNumber());
//				System.out.println("consumer_demand date" + currentDate1);
//				if (findByConsumerApplicationNo.isEmpty()) {
//					final SMSRequest smsRequest = new SMSRequest();
//
//					smsRequest.setText(MessageFormat.format(messageProperties.getMkmyPaymentRemainFiveDay(),
//							new Object[] { M.getConsumerApplicationNumber() }));
//					smsRequest.setMobileNo(consumerapplication.getConsumers().getConsumerLoginId());
//					smsRequest.setTemplateId(messageProperties.getMkmyPaymentRemainTempId());
////					code added by monika on 30-August-2024
//					smsRequest.setHinglish(1L);
////					code end
//
//					try {
//						String sendMessage = smsDirectService.sendMessage(smsRequest);
//						if (!sendMessage.equalsIgnoreCase(null)) {
//							M.setMsgSend("Send");
//							mmkyPayAmountRespository.save(M);
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//
//	}

//	@GetMapping("/getAllOytPaymentDatePendingApplication")
//	@Scheduled(fixedRate = 300000)                            // 24 Hour
//	public ResponseEntity<?> getAllOytPaymentDatePendingApplication() {
//		Response response = new Response();
//
//		List<ConsumerApplicationDetail> findAllOytApplication = consumerApplictionDetailRepository
//				.findAllOytApplication();
//		if (findAllOytApplication.isEmpty()) {
//			response.setCode(ResponseMessage.NOT_FOUND);
//			response.setMessage("No Oyt application found for filling payment date ");
//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//		}
//
//		for (ConsumerApplicationDetail consumerApplicationDetail : findAllOytApplication) {
//
//			List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
//					.findByConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo());
//			findByConsumerApplicationNo.stream().filter(a -> a.getAuth_status().equalsIgnoreCase("0300")
//					&& a.getAdditionalInfo().equalsIgnoreCase("Demand_fees")).forEach(b -> {
//						String transactionDate = b.getTransactionDate();
//						System.out.println("Dateeeeeeeeee   " + transactionDate);
//
//						String dateString = transactionDate;
//
//						// Parse the string to LocalDateTime
//						LocalDateTime dateTime = LocalDateTime.parse(dateString,
//								DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//
//						// Format the LocalDateTime to desired format
//						String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//						// Print the formatted date
//						System.out.println("Formatted Date: " + formattedDate);
//
//						LocalDateTime modifiedDateTime = dateTime.plusDays(3);
//
//						// Format the modified date
//						String modifiedFormattedDate = modifiedDateTime
//								.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//						consumerApplicationDetail.setPaymentDate(modifiedFormattedDate);
//						ConsumerApplicationDetail save = consumerApplictionDetailRepository
//								.save(consumerApplicationDetail);
//
//					});
//
//		}
//
//		return null;
//	}
//
//	@GetMapping("/RegistrationFeesNotDoneRevertApp")
//	@Scheduled(fixedRate = 86400000)       // 24 Hour
//	public void RegistrationFeesNotDoneRevertApp() {
//		List<ConsumerApplicationDetail> registrationFeesNotDoneRevertApp = consumerApplictionDetailRepository
//				.RegistrationFeesNotDoneRevertApp();
//		registrationFeesNotDoneRevertApp.stream().forEach(n -> n.setApplicationStatus(applicationStatusRepository
//				.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId()).get()));
//		consumerApplictionDetailRepository.saveAll(registrationFeesNotDoneRevertApp);
//	}

	@GetMapping("/RavindraSendDataForContractorSelectinon/{date}")
//	@Scheduled(fixedRate = 300000)
	public void RavindraSendDataForContractorSelectinon(@PathVariable String date) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//		String s = formatter.format(date);
//		System.out.println(s+"datesdfhsdyertyuwe5ue4rtuerie5rdyietdftuj");
		List<BillDeskPaymentResponse> ravindraSendDataForContractorSelectinon = billPaymentResponseeeeeeeRepository
				.RavindraSendDataForContractorSelectinon(date);

		ravindraSendDataForContractorSelectinon.stream().forEach(bill -> {

			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(bill.getConsumerApplicationNo());
//			ErpEstimateAmountData findById = estimateAmountRepository.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber()).get();

			Map<String, String> requestBody = new HashMap();

			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
				MmkyPayAmount findByConsumer = mmkyPayAmountRespository
						.findByConsumerApplicationNumber(findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("schema", findByConsumer.getSchemeCode());
			} else {

				Optional<ErpEstimateAmountData> findById1 = estimateAmountRepository
						.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
				if (findById1.isPresent()) {
					ErpEstimateAmountData erpEstimateAmountData = findById1.get();
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

			requestBody.put("consumer_mobile_no", findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
			requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("consumer_email_id", findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

			requestBody.put("address", findByConsumerApplicationNumber.getAddress());

			requestBody.put("shortDescriptionOfWork", findByConsumerApplicationNumber.getShortDescriptionOfWork());
			requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
			requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
			requestBody.put("is_bid_submitted", "false");
			if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
					&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
				requestBody.put("sspTotalAmount", findByConsumerApplicationNumber.getSspTotalAmount().toString());
				requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

			}
			RestTemplate restTemplate = new RestTemplate();

			try {
				ResponseEntity<Map> postForEntity = restTemplate
						.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/", requestBody, Map.class);
				System.out.println("The result of Post api is :" + postForEntity.getBody());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}
//monika code end

//	@GetMapping("/RavindraSendDataForContractorSelectinonBYPoseMachine")
	@Scheduled(fixedRate = 86400000)
	public void RavindraSendDataForContractorSelectinonBYPoseMachine() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String s = formatter.format(date);
		System.out.println(s + "datesdfhsdyertyuwe5ue4rtuerie5rdyietdftuj");

		List<PoseMachinePostData> ravindraSendDataForContractorSelectinon = poseMachinePostDate.findAll();
		ravindraSendDataForContractorSelectinon.stream().forEach(bill -> {

			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(bill.getApplicationNumber());
//			ErpEstimateAmountData findById = estimateAmountRepository.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber()).get();

			Map<String, String> requestBody = new HashMap();
			System.out.println(s + "datesdfhsdyertyuwe5ue4rtuerie5rdyietdftuj");
			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
				MmkyPayAmount findByConsumer = mmkyPayAmountRespository
						.findByConsumerApplicationNumber(findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("schema", findByConsumer.getSchemeCode());
			} else {

				Optional<ErpEstimateAmountData> findById1 = estimateAmountRepository
						.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
				if (findById1.isPresent()) {
					ErpEstimateAmountData erpEstimateAmountData = findById1.get();
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

			requestBody.put("consumer_mobile_no", findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
			requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("consumer_email_id", findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

			requestBody.put("address", findByConsumerApplicationNumber.getAddress());

			requestBody.put("shortDescriptionOfWork", findByConsumerApplicationNumber.getShortDescriptionOfWork());
			requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
			requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
			requestBody.put("is_bid_submitted", "false");
			if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
					&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
				requestBody.put("sspTotalAmount", findByConsumerApplicationNumber.getSspTotalAmount().toString());
				requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

			}

			RestTemplate restTemplate = new RestTemplate();

			try {
				ResponseEntity<Map> postForEntity = restTemplate
						.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/", requestBody, Map.class);
				System.out.println("The result of Post api is :" + postForEntity.getBody());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

//	@Scheduled(cron="0 0 */12 * * *")   //every 12 hours
	@Scheduled(fixedRate = 86400000)
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
				List<ConsumerApplicationDetail> saveContractorExpectedDates = consumerApplicationDetailRepository
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

										LinkedList<BillDeskPaymentResponse> byConsumerApplicationNoDemand = billPaymentResponseeeeeeeRepository
												.findByConsumerApplicationNoDemand(consumerApplicationNo);

										// Update consumer application status based on scheme type
										if (consumerApp.getSchemeType().getSchemeTypeId().equals(1L)) {
											if (consumerApp.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)
													&& byConsumerApplicationNoDemand.isEmpty()) {
												consumerApp.setApplicationStatus(applicationStatusRepository.findById(
														ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId())
														.get());
											} else {
												consumerApp.setApplicationStatus(applicationStatusRepository
														.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId())
														.get());
											}
										} else {
											consumerApp.setApplicationStatus(applicationStatusRepository
													.findById(ApplicationStatusEnum.PENDING_FOR_WORK_COMPLETION.getId())
													.get());
										}

										consumerApplicationDetailRepository.save(consumerApp);
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
			ex.printStackTrace();
			return ResponseEntity.status(500).body("Internal Server Error occurred: " + ex.getMessage());
		}
	}

//	@GetMapping("/settlementDate")
	@Scheduled(fixedRate = 86400000)
//	@Scheduled(cron = "0 0 4 * * ?")  // 4 AM
//	@Scheduled(cron ="0 30 12 * * ?") //12:30 PM
	public Response<Object> settlementDate() {
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

			JSONObject sendDataBillDesk = new JSONObject();
			sendDataBillDesk.put("mercid", "MPMKDSPLT");
			sendDataBillDesk.put("from_date", formattedminusOneDayDate);
			sendDataBillDesk.put("to_date", formattedCurrentDate);

			logger.info("Request payload for BillDesk: {}", sendDataBillDesk.toString());

			HttpHeaders headers = createHeaders();

			String convertObjectInString = encryptBilldeskData(sendDataBillDesk.toString());
			if (convertObjectInString == null) {
				throw new RuntimeException("Payload encryption failed");
			}
			logger.info("Payload encrypted and signed successfully");

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

					for (int i = 0; i < recordsArr.length(); i++) {
						JSONObject record = recordsArr.getJSONObject(i);

						// Check if "billdesk_id" exists
						if (record.has("billdesk_id")) {
							String billdeskTransactionId = record.getString("billdesk_id");

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

//	@GetMapping("/saveContractorActualWorkCompletionDates")
//	public ResponseEntity<String> saveContractorActualWorkCompletionDates() {
//		RestTemplate restTemplate = new RestTemplate();
//		String url = "https://qcportal.mpcz.in/tkc/bid_not_participated_docs";
//
//		try {
//			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//			String responseBody = response.getBody();
//
//			if (responseBody == null) {
//				return ResponseEntity.status(500).body("Error: Received empty response from the external API.");
//			}
//
//			List<ConsumerApplicationDetail> applicationsToUpdate = consumerApplictionDetailRepository
//					.saveContractorActualWorkCompletionDates();
//			System.err.println(
//					"Total applications requiring work completion date update: " + applicationsToUpdate.size());
//
//			JSONArray dataArray = new JSONObject(responseBody).optJSONArray("data");
//
//			if (dataArray == null) {
//				System.err.println("No data found in the external response.");
//				return ResponseEntity.ok("No data found to process.");
//			}
//
//			int onlyDateUpdatedCount = 0;
//			int newObjectCreatedCount = 0;
//			int notFoundCount = 0;
//			int applicationWithNullActualDateCount = 0;
//
//			List<String> onlyDateUpdatedList = new ArrayList<>();
//			List<String> newObjectCreatedList = new ArrayList<>();
//			List<String> notFoundList = new ArrayList<>();
//			List<String> applicationWithNullActualDateList = new ArrayList<>();
//
//			for (ConsumerApplicationDetail consumerApp : applicationsToUpdate) {
//				String consumerApplicationNo = consumerApp.getConsumerApplicationNo();
//				boolean applicationFound = false;
//
//				for (int i = 0; i < dataArray.length(); i++) {
//					JSONObject item = dataArray.getJSONObject(i);
//					String consumerApplicationNumber = item.optString("consumer_application_no", null);
//
//					if (consumerApplicationNo.equals(consumerApplicationNumber)) {
//						applicationFound = true;
//
//						String actualWorkCompletionDate = item.optString("actual_work_completion_date", null);
//						System.err.println("QC Portal application found: " + consumerApplicationNumber);
//						System.err.println("actualWorkCompletionDate: " + actualWorkCompletionDate);
//
//						if (actualWorkCompletionDate != null && !actualWorkCompletionDate.isEmpty()
//								&& !"null".equalsIgnoreCase(actualWorkCompletionDate)) {
//							List<ContractorForBidWorkStatus> contractorForBidStatusDB = contractorForBidWorkStatusRepository
//									.findByConsumerApplicationNumber(consumerApplicationNo);
//							if (!contractorForBidStatusDB.isEmpty()) {
//								for (ContractorForBidWorkStatus contractorForBidStatus : contractorForBidStatusDB) {
//									contractorForBidStatus.setActualWorkCompletionDate(actualWorkCompletionDate);
//									contractorForBidWorkStatusRepository.save(contractorForBidStatus);
//									onlyDateUpdatedCount++;
//									onlyDateUpdatedList.add(consumerApplicationNo);
//								}
//							} else {
//								ContractorForBidWorkStatus contractorForBidStatus = new ContractorForBidWorkStatus();
//								contractorForBidStatus.setConsumerApplicationNumber(consumerApplicationNo);
//								contractorForBidStatus
//										.setMaterialHandoverSiteDate(item.optString("material_handover_site", null));
//								contractorForBidStatus.setMaterialInstallFinishDate(
//										item.optString("material_installation_finished", null));
//								contractorForBidStatus.setMaterialInstallStartDate(
//										item.optString("material_installation_start", null));
//								contractorForBidStatus
//										.setConWorkStartedDate(item.optString("contractor_work_started", null));
//								contractorForBidStatus
//										.setConWorkCompleteDate(item.optString("expected_work_completion_date", null));
//								contractorForBidStatus.setUserId(Long.valueOf(item.optString("User_Id", null)));
//								contractorForBidStatus.setActualWorkCompletionDate(actualWorkCompletionDate);
//
//								contractorForBidWorkStatusRepository.save(contractorForBidStatus);
//								newObjectCreatedCount++;
//								newObjectCreatedList.add(consumerApplicationNo);
//							}
//							if (consumerApp.getSchemeType().getSchemeTypeId().equals(2L)) {
//								consumerApp.setApplicationStatus(applicationStatusRepository.findById(
//										ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC
//												.getId())
//										.orElse(null));
//								System.err.println("Application status updated for application: "
//										+ consumerApplicationNo + " Application status : "
//										+ ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC
//												.getId());
//								consumerApplictionDetailRepository.save(consumerApp);
//							}
//						} else {
//							applicationWithNullActualDateCount++;
//							applicationWithNullActualDateList.add(consumerApplicationNo);
//						}
//						break; // Exit inner loop once application is processed
//					}
//				}
//				if (!applicationFound) {
//					notFoundCount++;
//					notFoundList.add(consumerApplicationNo);
//				}
//			}
//
//			System.err.println("Processing Summary:");
//			System.err.println("Applications with only actual work completion date updated: " + onlyDateUpdatedCount);
//			System.err.println("List: " + onlyDateUpdatedList);
//			System.err.println("Applications with new object created: " + newObjectCreatedCount);
//			System.err.println("List: " + newObjectCreatedList);
//			System.err.println("Applications Actual date is null : " + applicationWithNullActualDateCount);
//			System.err.println("List : " + applicationWithNullActualDateList);
//			System.err.println("Applications not found in 3rd party API response: " + notFoundCount);
//			System.err.println("List: " + notFoundList);
//
//			String summary = String.format(
//					"Processing completed.\n" + "Only Date Updated: %d\n" + "List: %s\n" + "New Object Created: %d\n"
//							+ "List: %s\n" + "Null Actual Date: %d\n" + "List: %s\n" + "Not Found: %d\n" + "List: %s",
//					onlyDateUpdatedCount, onlyDateUpdatedList, newObjectCreatedCount, newObjectCreatedList,
//					applicationWithNullActualDateCount, applicationWithNullActualDateCount, notFoundCount,
//					notFoundList);
//
//			return ResponseEntity.ok(summary);
//		} catch (HttpClientErrorException.NotAcceptable ex) {
//			return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return ResponseEntity.status(500).body("Internal Server Error occurred: " + ex.getMessage());
//		}
//	}

	@Scheduled(cron = "0 30 23 * * *") // Runs every night at 11:30 PM
	public void RavindraSendDataForContractorSelectinon1() {
		YearMonth yearMonth = YearMonth.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		String formattedYearMonth = yearMonth.format(formatter) + "%";
		System.out.println(formattedYearMonth);
		List<BillDeskPaymentResponse> ravindraSendDataForContractorSelectinon = billPaymentResponseeeeeeeRepository
				.RavindraSendDataForContractorSelectinon1(formattedYearMonth);

		ravindraSendDataForContractorSelectinon.stream().forEach(bill -> {

			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(bill.getConsumerApplicationNo());
//			ErpEstimateAmountData findById = estimateAmountRepository.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber()).get();

			Map<String, String> requestBody = new HashMap();

			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
				MmkyPayAmount findByConsumer = mmkyPayAmountRespository
						.findByConsumerApplicationNumber(findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("schema", findByConsumer.getSchemeCode());
			} else {

				Optional<ErpEstimateAmountData> findById1 = estimateAmountRepository
						.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
				if (findById1.isPresent()) {
					ErpEstimateAmountData erpEstimateAmountData = findById1.get();
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

			requestBody.put("consumer_mobile_no", findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
			requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("consumer_email_id", findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

			requestBody.put("address", findByConsumerApplicationNumber.getAddress());

			requestBody.put("shortDescriptionOfWork", findByConsumerApplicationNumber.getShortDescriptionOfWork());
			requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
			requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
			requestBody.put("is_bid_submitted", "false");
			if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
					&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
				requestBody.put("sspTotalAmount", findByConsumerApplicationNumber.getSspTotalAmount().toString());
				requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

			}
			RestTemplate restTemplate = new RestTemplate();

			try {
				ResponseEntity<Map> postForEntity = restTemplate
						.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/", requestBody, Map.class);
				System.out.println("The result of Post api is :" + postForEntity.getBody());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Scheduled(cron = "0 0 * * * *") // every Hour
	public void createInvoiceProcedure() {
		try {
			consumerApplictionDetailRepository.createInvoice();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}