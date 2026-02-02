package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBid;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.domain.SanchayPaymentDetails;
import com.mpcz.deposit_scheme.backend.api.dto.SanchayAoRejectedDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationStatusException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.SanchayPaymentException;
import com.mpcz.deposit_scheme.backend.api.exception.TransactionAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ManualPaymentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SanchayPaymentDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.PoseMachinePostDataService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

@Service
public class PoseMachinePostDataServiceIMP implements PoseMachinePostDataService {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDataRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	EstimateAmountRepository estimateAmountRepository;

	@Autowired
	ManualPaymentRepository manualPaymentRepository;

	@Autowired
	private ContractorForBidRepository contractorForBidRepository;

	@Override
	public String save(PoseMachinePostData pose) {

		Response<?> response = new Response<>();
		PoseMachinePostData poseMachinePostData = new PoseMachinePostData();
		String conAppNo = pose.getApplicationNumber();
		BigDecimal txnAmount = pose.getTxnAmount();

		ApplicationStatus appStatusDb = null;

		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(conAppNo);

			if (findByConsumerApplicationNumber == null) {
				response.setCode("404");
				response.setMessage("application not found");
				throw new TransactionAmountException(response);
			}
			poseMachinePostData.setApplicationNumber(conAppNo);
			poseMachinePostData.setBankName(pose.getBankName());
			poseMachinePostData.setCashAcceptedDate(pose.getCashAcceptedDate());
			poseMachinePostData.setDateOfPayment(pose.getDateOfPayment());
			poseMachinePostData.setDeviceId(pose.getDeviceId());
			poseMachinePostData.setInstrumentDate(pose.getInstrumentDate());
			poseMachinePostData.setInstrumentNo(pose.getInstrumentNo());
			poseMachinePostData.setLocationCode(pose.getLocationCode());
			poseMachinePostData.setMrNo(pose.getMrNo());
			poseMachinePostData.setPaymentMode(pose.getPaymentMode());
			poseMachinePostData.setTxnAmount(pose.getTxnAmount());
			if (Objects.nonNull(pose.getPaymentType())) {
				poseMachinePostData.setPaymentType(pose.getPaymentType());
			}

			PoseMachinePostData save = poseMachinePostDataRepository.save(poseMachinePostData);
			if (save != null) {

				if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(5L)
						|| (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(47L)
								&& save.getPaymentType().equals("Registration_Fees"))) {

					if (findByConsumerApplicationNumber.getAdminStatusChangedTo() != null
							&& findByConsumerApplicationNumber.getAdminStatusChangedTo().equals(5l)) { // added this check for
																								// government case
																								// 17-12-2025
						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId());
					} else {
						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
					}
				} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(12L)
						|| (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(47L)
								&& save.getPaymentType().equals("Demand_fees"))) {
					if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName().equalsIgnoreCase("Deposit")
							|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L
							|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {

						if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {
							ContractorForBid findByApplicationNo = contractorForBidRepository
									.findByApplicationNo(conAppNo);

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

				} else if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(30L)
						|| (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(47L)
								&& save.getPaymentType().equals("Revised_Demand_fees"))) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
				} else {
					appStatusDb = applicationStatusService
							.findById(findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId());
				}

				findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
				ConsumerApplicationDetail save2 = consumerApplictionDetailRepository
						.save(findByConsumerApplicationNumber);
				if (save2 != null) {

					// ConsumerApplicationDetail findByConsumerApplicationNumber1 =
					// consumerApplictionDetailRepository.findByConsumerApplicationNumber(bill.getApplicationNumber());
//				ErpEstimateAmountData findById = estimateAmountRepository.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber()).get();

					Map<String, String> requestBody = new HashMap();

					if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
						MmkyPayAmount findByConsumer = mmkyPayAmountRespository.findByConsumerApplicationNumber(
								findByConsumerApplicationNumber.getConsumerApplicationNo());
						requestBody.put("consumerApplicationNo",
								findByConsumerApplicationNumber.getConsumerApplicationNo());
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

//	        Production Code
					try {
						ResponseEntity<Map> postForEntity = restTemplate
								.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/", requestBody, Map.class);
						System.out.println("The result of Post api is :" + postForEntity.getBody());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return "data update succefully";
				} else {
					return "data not update";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "data not update ";
	}

	@Override
	public Object getDemandFeesPaymentDetails(String consumerApplicationNo) {
		Response response = new Response();
		ConsumerApplicationDetail byConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (byConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application not found in database");
		}
		PoseMachinePostData byApplicationNumber = poseMachinePostDataRepository
				.findByApplicationNumber(consumerApplicationNo);
		if (byApplicationNumber != null) {
			response.setCode(HttpCode.OK);
			response.setMessage("Data found in Pose Machine");
			response.setList(Arrays.asList(byApplicationNumber));
		} else {
			ManualPayment demandDataFromManualPayment = manualPaymentRepository
					.getDemandDataFromManualPayment(consumerApplicationNo);
			if (demandDataFromManualPayment != null) {
				response.setCode(HttpCode.OK);
				response.setMessage("Data found in Manual Payment");
				response.setList(Arrays.asList(demandDataFromManualPayment));
			}

		}
		if (response.getList() == null || response.getList().isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Demand Payment not found in Pose Machine and Manual Payment");
			return response;
		} else {
			return response;
		}
	}

	@Autowired
	private SanchayPaymentDetailsRepository sanchayPaymentDetailsRepository;

	@Autowired
	private DryUtility dryUtility;

	@Transactional
	public SanchayPaymentDetails recordOfSanchayPostData(SanchayPaymentDetails sanchayPaymentDetails)
			throws ConsumerApplicationDetailException, ApplicationStatusException {

		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(sanchayPaymentDetails.getConsumerApplicationNo());
		SanchayPaymentDetails existingSanchayRecord = sanchayPaymentDetailsRepository
				.findByConsumerApplicationNo(sanchayPaymentDetails.getConsumerApplicationNo());

		if (existingSanchayRecord != null) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NOT_ACCEPTABLE, "Consumer Application already exist"));
		}
		if (validateConsumerApplication.getApplicationStatus().getApplicationStatusId().equals(5l)) {
			sanchayPaymentDetails.setPaymentType("Registration_Fees");
		} else if (validateConsumerApplication.getApplicationStatus().getApplicationStatusId().equals(12l)) {
			sanchayPaymentDetails.setPaymentType("Demand_fees");
		} else if (validateConsumerApplication.getApplicationStatus().getApplicationStatusId().equals(30l)) {
			sanchayPaymentDetails.setPaymentType("Revised_Demand_fees");
		}
		SanchayPaymentDetails savedDb = sanchayPaymentDetailsRepository.save(sanchayPaymentDetails);
		if (Objects.isNull(savedDb)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Sanchay post data not saved in database"));
		}

		validateConsumerApplication.setApplicationStatus(applicationStatusRepository
				.findById(ApplicationStatusEnum.PENDING_AT_AO_END_FOR_PAYMENT_VERIFICATION.getId())
				.orElseThrow(() -> new ApplicationStatusException(
						new Response(HttpCode.NULL_OBJECT, "Application status not found"))));

		consumerApplictionDetailRepository.save(validateConsumerApplication);

		return savedDb;

	}

	public SanchayPaymentDetails aoRejectedSanchayApplication(SanchayAoRejectedDto sanchayAoRejectedDto)
			throws ConsumerApplicationDetailException, ApplicationStatusException {

		SanchayPaymentDetails existingSanchayRecord = sanchayPaymentDetailsRepository
				.findByTrxIdAndIsActive(sanchayAoRejectedDto.getTxnId(), true)
				.orElseThrow(() -> new SanchayPaymentException(
						new Response("404", "Given Transaction Id Not found in DSP")));

		if (!sanchayAoRejectedDto.isAoRejected()) {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
					"AO rejection flag is false for txnId: " + sanchayAoRejectedDto.getTxnId()));
		}
		existingSanchayRecord.setAoDetail(sanchayAoRejectedDto.getAoDetails());
		existingSanchayRecord.setAoRemark(sanchayAoRejectedDto.getAoRemark());
		existingSanchayRecord.setRejectDate(sanchayAoRejectedDto.getAoRejectedDate());
		existingSanchayRecord.setAoRejected("true");
		existingSanchayRecord.setActive(false);
		existingSanchayRecord.setDeleted(true);

		SanchayPaymentDetails updatedInactiveRecord = sanchayPaymentDetailsRepository.save(existingSanchayRecord);

		if (updatedInactiveRecord == null) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Failed to update Sanchay record as rejected"));
		}
		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(updatedInactiveRecord.getConsumerApplicationNo());
		if (Objects.equals(existingSanchayRecord.getPaymentType(), "Registration_Fees")) {
			validateConsumerApplication.setApplicationStatus(
					applicationStatusRepository.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId())
							.orElseThrow(() -> new ApplicationStatusException(
									new Response(HttpCode.NULL_OBJECT, "Application status not found"))));
			consumerApplictionDetailRepository.save(validateConsumerApplication);
		} else if ((Objects.equals(existingSanchayRecord.getPaymentType(), "Demand_fees"))) {
			validateConsumerApplication.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId())
					.orElseThrow(() -> new ApplicationStatusException(
							new Response(HttpCode.NULL_OBJECT, "Application status not found"))));
			consumerApplictionDetailRepository.save(validateConsumerApplication);
		} else if ((Objects.equals(existingSanchayRecord.getPaymentType(), "Revised_Demand_fees"))) {
			validateConsumerApplication.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId())
					.orElseThrow(() -> new ApplicationStatusException(
							new Response(HttpCode.NULL_OBJECT, "Application status not found"))));
			consumerApplictionDetailRepository.save(validateConsumerApplication);
		}

		return updatedInactiveRecord;

	}

}
