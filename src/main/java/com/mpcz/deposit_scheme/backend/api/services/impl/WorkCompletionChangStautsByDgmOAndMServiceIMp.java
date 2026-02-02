package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.domain.WorkCompletionChangStautsByDgmOAndM;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.WorkCompletionChangStautsByDgmOAndMRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.WorkCompletionChangStautsByDgmOAndMService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WorkCompletionChangStautsByDgmOAndMServiceIMp implements WorkCompletionChangStautsByDgmOAndMService {

	@Value("${post.nsc.oyt.ssp}")
	private String postNscOytSSP;

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMRepository workCompletionChangStautsByDgmOAndMService;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDataRepository;

	private static final Logger log = LoggerFactory.getLogger(WorkCompletionChangStautsByDgmOAndMServiceIMp.class);

	@Override
//	@Transactional(readOnly = true)
	public WorkCompletionChangStautsByDgmOAndM saveAndChange(
			WorkCompletionChangStautsByDgmOAndM workCompletionChangStautsByDgmOAndM)
			throws ConsumerApplicationDetailException, ErpEstimateAmountException {

		ApplicationStatus applicationStatus = null;
		try {

			BigDecimal securityDeposit = BigDecimal.ZERO;

			ConsumerApplicationDetail applicationDetail = consumerApplicationDetailService
					.findByConsumerApplicationId(workCompletionChangStautsByDgmOAndM.getConsumerApplicationId());

			if (applicationDetail.getNscApplicationNo() != null) {
				if (workCompletionChangStautsByDgmOAndM.getWorkComplationChangedByDgmOrendum()
						.equalsIgnoreCase("false")) {
					if (applicationDetail.getIvrsNo() == null) {
						applicationStatus = applicationStatusService.findById(
								ApplicationStatusEnum.WORK_FINISHED_NOW_PENDING_FOR_CONNECTION_PUNCHING.getId());
					} else {
						applicationStatus = applicationStatusService
								.findById(ApplicationStatusEnum.CONNECTION_GRANTED_SUCCESSFULLY.getId());
					}
					applicationDetail.setDateOfDgmOandM(workCompletionChangStautsByDgmOAndM.getDateOfDgmOandM());
					applicationDetail.setApplicationStatus(applicationStatus);
//					ConsumerApplicationDetail saveConsumerApplicationDB = consumerApplicationDetailService
//							.saveConsumerApplication(applicationDetail);
//					02-05-2025
					ErpEstimateAmountData erpDataDB = estimateAmountRepository
							.findById(applicationDetail.getErpWorkFlowNumber())
							.orElseThrow(() -> new ErpEstimateAmountException(
									new Response(HttpCode.NULL_OBJECT, "Erp data not found in table")));

					securityDeposit = erpDataDB.getSecurityDeposit() != null ? erpDataDB.getSecurityDeposit()
							: BigDecimal.ZERO;

					System.err.println("bbbbbbbbbbbbbbb : " + securityDeposit);

					if (applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 2L
							&& Objects.nonNull(erpDataDB.getSspTotalAmount())
							&& erpDataDB.getSspTotalAmount().compareTo(BigDecimal.ZERO) >= 0) {
						String sendDataToSSp = sendDataToSSp1(applicationDetail);
						System.err.println("sendDataToSSp : " + sendDataToSSp);
//			end
						if ("Success".equals(sendDataToSSp)) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							String formattedDate = LocalDateTime.now().format(formatter);
							applicationDetail.setSspApplicationCompleted("true");
							applicationDetail.setSspApplicationCompleteDate(formattedDate);
							consumerApplictionDetailRepository.save(applicationDetail);
						} else {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							String formattedDate = LocalDateTime.now().format(formatter);
							applicationDetail.setSspApplicationCompleteDate(formattedDate);
							applicationDetail.setSspApplicationCompleted(sendDataToSSp);
							consumerApplictionDetailRepository.save(applicationDetail);
						}
					} else {
//					02-05-2025 added the above check for oyt application
						if (applicationDetail.getSspApplicationCompleteDate() == null
								&& securityDeposit.compareTo(BigDecimal.ZERO) <= 0 && !applicationDetail
										.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {
//				String sendDataToSSp = sendDataToSSp(saveConsumerApplicationDB.getNscApplicationNo(), 32L,
//								applicationDetail.getConsumerApplicationNo());

//						//		given line is new code 19-05-2025 commented due to production early deployment
							String sendDataToSSp = sendDataToSSp1(applicationDetail);
							System.err.println("sendDataToSSp : " + sendDataToSSp);
//				end
							if ("Success".equals(sendDataToSSp)) {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
								String formattedDate = LocalDateTime.now().format(formatter);
								applicationDetail.setSspApplicationCompleted("true");
								applicationDetail.setSspApplicationCompleteDate(formattedDate);
								consumerApplictionDetailRepository.save(applicationDetail);
							} else {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
								String formattedDate = LocalDateTime.now().format(formatter);
								applicationDetail.setSspApplicationCompleteDate(formattedDate);
								applicationDetail.setSspApplicationCompleted(sendDataToSSp);
								consumerApplictionDetailRepository.save(applicationDetail);
							}
						}
					}
				} else {
					applicationStatus = applicationStatusService.findById(
							ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
					applicationDetail.setDateOfDgmOandM(workCompletionChangStautsByDgmOAndM.getDateOfDgmOandM());
					applicationDetail.setApplicationStatus(applicationStatus);
					consumerApplicationDetailService.saveConsumerApplication(applicationDetail);
				}
			}
			if (applicationDetail.getNscApplicationNo() == null) {
				if (workCompletionChangStautsByDgmOAndM.getWorkComplationChangedByDgmOrendum()
						.equalsIgnoreCase("false")) {

					if (applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 2L
							|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
						applicationStatus = applicationStatusService.findById(
								ApplicationStatusEnum.WORK_FINISHED_NOW_PENDING_FOR_CONNECTION_PUNCHING.getId());
					} else if (applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) { // added this
																										// check for oyt
																										// 18-dec
						applicationStatus = applicationStatusService.findById(applicationDetail.getIvrsNo() == null
								? ApplicationStatusEnum.WORK_FINISHED_NOW_PENDING_FOR_CONNECTION_PUNCHING.getId()
								: ApplicationStatusEnum.CONNECTION_GRANTED_SUCCESSFULLY.getId());
					} else { // added this check for oyt 18-dec
						applicationStatus = applicationStatusService
								.findById(ApplicationStatusEnum.WORK_FINISED.getId());
					}
					applicationDetail.setDateOfDgmOandM(workCompletionChangStautsByDgmOAndM.getDateOfDgmOandM());
					applicationDetail.setApplicationStatus(applicationStatus);
					consumerApplicationDetailService.saveConsumerApplication(applicationDetail);
				} else {
					applicationStatus = applicationStatusService.findById(
							ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
					applicationDetail.setDateOfDgmOandM(workCompletionChangStautsByDgmOAndM.getDateOfDgmOandM());
					applicationDetail.setApplicationStatus(applicationStatus);
					consumerApplicationDetailService.saveConsumerApplication(applicationDetail);
				}
			}
		} catch (ConsumerApplicationDetailException e) {

			e.printStackTrace();
		}

		return workCompletionChangStautsByDgmOAndMService.save(workCompletionChangStautsByDgmOAndM);
	}

//	public String sendDataToSSp(String nscApplicationNo, Long applicationStatusId, String consumerApplicationNo) {
//		Response response = new Response();
//
//		String url = postNscOytSSP;
////		String url = "https://survey.mpcz.in:8080/ssp-web/department/post/work/status/ssp";
//		System.err.println("url : " + url);
//		RestTemplate restTemplate = new RestTemplate();
//
//		Map<String, Object> map = new HashMap<>();
//		map.put("nscApplicationNo", nscApplicationNo);
//		map.put("workStatus", applicationStatusId);
//		map.put("dspApplicationNo", consumerApplicationNo);
//		map.put("applicationType", "NSC");
//		System.out.println("map Data : " + map.toString());
//
//		try {
//			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, map, String.class);
//			System.out.println(postForEntity.getBody() + "aadadddddddddddddd");
//			JSONObject responseJson = new JSONObject(postForEntity.getBody());
//
//			// Extract the message and statusCode
//			String message = responseJson.getString("message");
//			int statusCode = responseJson.getInt("statusCode");
//
//			// Check if statusCode is 208 and handle accordingly
//			if (statusCode == 208) {
//				System.out.println("Work Status already updated: " + message);
//				return "Work Status already updated In Saral Sanyojan Portal !!!";
//			} else if (statusCode == 200) {
//				System.out.println("Request was successful.");
//				return "Success";
//			} else {
//				System.out.println("Request failed with status: " + statusCode);
//				return "Request failed";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Internal Server Error";
//		}
//
//	}

	public String sendDataToSSp1(ConsumerApplicationDetail consumerApplicationDetail) {

		log.info("[START] sendDataToSSp1 in WorkCompletionChangStautsByDgmOAndMServiceIMp| consumerApplicationNo: {}",
				consumerApplicationDetail.getConsumerApplicationNo());
		Response response = new Response();

		String url = postNscOytSSP;

		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> map = new HashMap<>();
		map.put("nscApplicationNo", consumerApplicationDetail.getNscApplicationNo());

		Long workStatus = (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 25L) ? 32L
				: consumerApplicationDetail.getApplicationStatus().getApplicationStatusId();

		map.put("workStatus", workStatus);
//		code added for sending the data to ssp for cancel application also 10-10-2025
		if (workStatus == 29L) {
			map.put("message", "Application Rejected Before Demand Payment on DSP");
		} else if (workStatus == 42L) {
			map.put("message", "Amount Refunded To Applicant Successfully. So Application can not move forward");
		} else {
			map.put("message", "Work Completed On DSP");
		}
//		code added for sending the data to ssp for cancel application also 10-10-2025
		map.put("dspApplicationNo", consumerApplicationDetail.getConsumerApplicationNo());
		String applicationType = (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 7L) ? "LC"
				: "NSC";
		map.put("applicationType", applicationType);

		System.err.println("ssp data sending before payment : " + new Gson().toJson(map));
//		Added this code for sending the data of SSP application for which payment is taken in DSP Portal 11-09-2025 start
//		|| consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L ANKIT SIR SE puch kr ise bhi add krna hai 07-11-2025
		if ((consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 2L)
				&& Objects.nonNull(consumerApplicationDetail.getSspTotalAmount()) && workStatus != 29L
				&& workStatus != 42L) {
			String transactionDate;
			String transactionId;
			BillDeskPaymentResponse checkDemandFeesExistOrNot = billPaymentResponseeeeeeeRepository
					.checkDemandFeesExistOrNot(consumerApplicationDetail.getConsumerApplicationNo());
			if (Objects.isNull(checkDemandFeesExistOrNot)) {
				PoseMachinePostData dataByApplicationNumber = poseMachinePostDataRepository.findDataByApplicationNumber(
						consumerApplicationDetail.getConsumerApplicationNo(), "Demand_fees");
				transactionDate = getPosPaymentDate(dataByApplicationNumber.getDateOfPayment());
				transactionId = dataByApplicationNumber.getMrNo();

			} else {
				transactionDate = checkDemandFeesExistOrNot.getTransactionDate();
				transactionId = checkDemandFeesExistOrNot.getTranId();

			}

			if (Objects.nonNull(transactionDate) && Objects.nonNull(transactionId)) {
				map.put("dateOfPayment", transactionDate);
				map.put("tranasactionNumber", transactionId);
			}
		}

//		Added this code for sending the data of SSP application for which payment is taken in DSP Portal 11-09-2025 end
		try {
			System.err.println("ssp data sending before payment : " + new Gson().toJson(map));
			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, map, String.class);

			JSONObject responseJson = new JSONObject(postForEntity.getBody());

			// Extract the message and statusCode
			String message = responseJson.getString("message");
			int statusCode = responseJson.getInt("statusCode");

			if (statusCode == 208) {

				System.out.println("Work Status already updated: " + message);
				return "Work Status already updated In Saral Sanyojan Portal !!!";
			} else if (statusCode == 200) {

				System.out.println("Request was successful.");
				return "Success";
			} else {
				return "Request failed";
			}
		} catch (Exception e) {
			e.printStackTrace();

			return "Internal Server Error";
		}

	}

	public String rejectLoadChangeApplication(String nscApplicationNo, Long applicationStatusId,
			String consumerApplicationNo) {
		Response response = new Response();

		String url = postNscOytSSP;
//		String url = "https://survey.mpcz.in:8080/ssp-web/department/post/work/status/ssp";
		System.err.println("url : " + url);
		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> map = new HashMap<>();
		map.put("nscApplicationNo", nscApplicationNo);
		map.put("workStatus", applicationStatusId);
		map.put("dspApplicationNo", consumerApplicationNo);
		map.put("applicationType", "LC");
		System.out.println("map Data : " + map.toString());

		try {
			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, map, String.class);
			System.out.println(postForEntity.getBody() + "aadadddddddddddddd");
			JSONObject responseJson = new JSONObject(postForEntity.getBody());

			// Extract the message and statusCode
			String message = responseJson.getString("message");
			int statusCode = responseJson.getInt("statusCode");

			// Check if statusCode is 208 and handle accordingly
			if (statusCode == 208) {
				System.out.println("Work Status already updated: " + message);
				return "Work Status already updated In Saral Sanyojan Portal !!!";
			} else if (statusCode == 200) {
				System.out.println("Request was successful.");
				return "Success";
			} else {
				System.out.println("Request failed with status: " + statusCode);
				return "Request failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Internal Server Error";
		}

	}

	public static String getPosPaymentDate(Date dateOfPayment) {
		String localDateTime = dateOfPayment.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+05:30"));
		return localDateTime;
	}

}
