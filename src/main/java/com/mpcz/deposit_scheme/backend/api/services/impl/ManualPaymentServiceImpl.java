package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ManualPaymentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ManualPaymentService;

@Service
public class ManualPaymentServiceImpl implements ManualPaymentService {

	@Autowired
	private ManualPaymentRepository manualPaymentRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	EstimateAmountRepository estimateAmountRepository;

	@Autowired
	MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Value("${ravindra.send.data.after.payment}")
	private String ravindraSendDataAfterPayment;

	@Override
	public List<ManualPayment> getAllManualPayments() {
		List<ManualPayment> manualPayments = manualPaymentRepository.findAll();
		return manualPayments;
	}

	@Override
	public ResponseEntity<?> createManualPayment(ManualPayment manualPayment) {
		Response response = new Response();

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(manualPayment.getConAppNo());

		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application not found in database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		if (manualPayment.getAmount() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Amount should not be null");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		ManualPayment save = manualPaymentRepository.save(manualPayment);

		if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(12L)) {
			Map<String, String> requestBody = new HashMap<>();

			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
				MmkyPayAmount findByConsumer = mmkyPayAmountRespository
						.findByConsumerApplicationNumber(findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
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

			requestBody.put("consumer_mobile_no", findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
			requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("consumer_email_id", findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

			requestBody.put("address", findByConsumerApplicationNumber.getAddress());

			requestBody.put("shortDescriptionOfWork", findByConsumerApplicationNumber.getShortDescriptionOfWork());
			requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
			requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
			requestBody.put("is_bid_submitted", "false");
			requestBody.put("nature_of_work_name", findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkName());
			if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
					&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
				requestBody.put("sspTotalAmount", findByConsumerApplicationNumber.getSspTotalAmount().toString());
				requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

			}

			RestTemplate restTemplate = new RestTemplate();

			// UAT Code
//        ResponseEntity<Map> postForEntity = restTemplate.postForEntity("https://qcdev.mpcz.in:8080/tkc/get_consumer/", requestBody, Map.class);

			// Production Code
			ResponseEntity<Map> postForEntity = restTemplate.postForEntity(ravindraSendDataAfterPayment, requestBody,
					Map.class);
			System.out.println("The result of Post api is :" + postForEntity.getBody());
		}
		if (save != null) {
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
			ApplicationStatus appStatusDb = null;
			if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(5L)) {

				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
			} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(12L)) {
				if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName().equalsIgnoreCase("Deposit")
						|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
				} else if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName()
						.equalsIgnoreCase("Supervision")
						&& !findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5L)) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
				} else if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName()
						.equalsIgnoreCase("Supervision")
						&& findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5L)) {
					appStatusDb = applicationStatusService.findById(ApplicationStatusEnum.WAITING_FOR_72_HOURS.getId());
					findByConsumerApplicationNumber.setPaymentDate(newDateFormatted);
				}

			}
			findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
			findByConsumerApplicationNumber.setPaymentModeManual("True");
			consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
		}

		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(save));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@Override
	public Response getPaymentByApplication(String consumerAppNo) {
		Response response = new Response();
		List<ManualPayment> findByConAppNo = manualPaymentRepository.findByConAppNo(consumerAppNo);
		if (findByConAppNo.isEmpty()) {
			return new Response(HttpCode.NULL_OBJECT, "Application not found for Manual Payment");
		}

//		for(ManualPayment m : findByConAppNo) {
//			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository.findByConsumerApplicationNumber(consumerAppNo);
//			m.setConsumerName(findByConsumerApplicationNumber.getConsumerName());
//		}

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerAppNo);
		findByConAppNo.forEach(m -> m.setConsumerName(findByConsumerApplicationNumber.getConsumerName()));

		return new Response(HttpCode.OK, "Data Retrieve Successfully", Arrays.asList(findByConAppNo));

	}

	@Override
	public ManualPayment getManualPaymentByBillRefNo(String billRefNo) {
		return manualPaymentRepository.findByBillRefNo(billRefNo);
	}

}
