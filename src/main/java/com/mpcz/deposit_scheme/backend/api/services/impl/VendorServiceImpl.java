package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.Vendor;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByApplicationIdException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByVendorIdException;
import com.mpcz.deposit_scheme.backend.api.exception.VendorException;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorRepository;
import com.mpcz.deposit_scheme.backend.api.request.VendorRejectionForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.services.VendorService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;

@Service
public class VendorServiceImpl implements VendorService {

	Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private VendorRepository repository;

//	charitra code start here
	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	private SMSDirectService smsDirectService;

	@Autowired
	ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

	@Autowired
	ContractorForBidRepository contractorForBidRepository;

	@Autowired
	BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;
//	charitra code end here

	public List<ConsumerApplicationDetail> findAllConsumerApplicationByVendorId(Long id)
			throws ConsumerNotFoundByVendorIdException {
		final String method = "VendorServiceImpl : findAllConsumerApplicationByVendorId()";
		logger.info(method);
		final Response<ConsumerApplicationDetail> response = new Response<>();
		List<ConsumerApplicationDetail> listOfConsumerApplication = consumerApplictionDetailRepository
				.findAllConsumerApplicationsByVendorId(id);
		if (Objects.isNull(listOfConsumerApplication) || listOfConsumerApplication.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ConsumerNotFoundByVendorIdException(response);
		} else {
			logger.info("Response is returning successfully");
			return listOfConsumerApplication;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ConsumerApplicationDetail updateConsumerApplicationDetailsByApplicationId(
			VendorRejectionForm vendorRejectionForm)
			throws ConsumerNotFoundByApplicationIdException, ConsumerApplicationDetailException {
		System.err.println("inside updateConsumerDetailsByApplicationId() service");
		final String method = "VendorServiceImpl : updateConsumerApplicationDetailsByApplicationId()";
		logger.info(method);
		final Response<ConsumerApplicationDetail> response = new Response<>();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findConsumerApplicationDetailByApplicationNo(vendorRejectionForm.getConsumerApplicationNo());

		logger.info("Response is returning successfully");

		if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() != 22 &&
				consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() != 24) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("You are not authorize to call this api at this stage of application status");
			throw new ConsumerApplicationDetailException(response);
		}
		ApplicationStatus appStatusDb = null;
		if (vendorRejectionForm.getIsRejected()) {
			if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 22) {
				deleteContractorForBid(vendorRejectionForm.getConsumerApplicationNo());
				consumerApplicationDetail.setConReselectionDate(LocalDate.now().toString());
				consumerApplicationDetail.setConReselectionRemark("Contractor rejected application from QC Portal");
				if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1l)) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
				} else {
					appStatusDb = applicationStatusService.findById(
							ApplicationStatusEnum.PENDING_FOR_CONTRACTOR_SELECTION_AFTER_TENDER_BY_DGM_STC.getId());
				}
				consumerApplicationDetail.setApplicationStatus(appStatusDb);
			}
		} else {

			if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 22
					&& (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)
							|| consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(2L))) {

				ContractorForBidWorkStatus contractorForBidWorkStatus = new ContractorForBidWorkStatus();
				contractorForBidWorkStatus.setConsumerApplicationNumber(vendorRejectionForm.getConsumerApplicationNo());
				contractorForBidWorkStatus
						.setMaterialHandoverSiteDate(vendorRejectionForm.getMaterialHandoverSiteDate());
				contractorForBidWorkStatus
						.setMaterialInstallFinishDate(vendorRejectionForm.getMaterialInstallFinishDate());
				contractorForBidWorkStatus
						.setMaterialInstallStartDate(vendorRejectionForm.getMaterialInstallStartDate());
				contractorForBidWorkStatus.setConWorkStartedDate(vendorRejectionForm.getConWorkStartedDate());
				contractorForBidWorkStatus.setConWorkCompleteDate(vendorRejectionForm.getConWorkCompleteDate());
				contractorForBidWorkStatus.setUserId(vendorRejectionForm.getContractorId());
				ContractorForBidWorkStatus save = contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);

				// Monika code end

				// consumerApplicationDetail.setIsRejected(Boolean.FALSE);

				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.PENDING_FOR_WORK_COMPLETION.getId());
				consumerApplicationDetail.setApplicationStatus(appStatusDb);
			} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 22
					&& consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {
				ContractorForBidWorkStatus contractorForBidWorkStatus = new ContractorForBidWorkStatus();
				contractorForBidWorkStatus.setConsumerApplicationNumber(vendorRejectionForm.getConsumerApplicationNo());
				contractorForBidWorkStatus
						.setMaterialHandoverSiteDate(vendorRejectionForm.getMaterialHandoverSiteDate());
				contractorForBidWorkStatus
						.setMaterialInstallFinishDate(vendorRejectionForm.getMaterialInstallFinishDate());
				contractorForBidWorkStatus
						.setMaterialInstallStartDate(vendorRejectionForm.getMaterialInstallStartDate());
				contractorForBidWorkStatus.setConWorkStartedDate(vendorRejectionForm.getConWorkStartedDate());
				contractorForBidWorkStatus.setConWorkCompleteDate(vendorRejectionForm.getConWorkCompleteDate());
				contractorForBidWorkStatus.setUserId(vendorRejectionForm.getContractorId());
				ContractorForBidWorkStatus save = contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);

				LinkedList<BillDeskPaymentResponse> findByConsumerApplicationNoDemand = billPaymentResponseeeeeeeRepository
						.findByConsumerApplicationNoDemand(vendorRejectionForm.getConsumerApplicationNo());

				if (!findByConsumerApplicationNoDemand.isEmpty()) {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
				} else {
					appStatusDb = applicationStatusService
							.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId());

				}

				consumerApplicationDetail.setApplicationStatus(appStatusDb);

			} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 22) {

				// Monika code start
				ContractorForBidWorkStatus contractorForBidWorkStatus = new ContractorForBidWorkStatus();
				contractorForBidWorkStatus.setConsumerApplicationNumber(vendorRejectionForm.getConsumerApplicationNo());
				contractorForBidWorkStatus
						.setMaterialHandoverSiteDate(vendorRejectionForm.getMaterialHandoverSiteDate());
				contractorForBidWorkStatus
						.setMaterialInstallFinishDate(vendorRejectionForm.getMaterialInstallFinishDate());
				contractorForBidWorkStatus
						.setMaterialInstallStartDate(vendorRejectionForm.getMaterialInstallStartDate());
				contractorForBidWorkStatus.setConWorkStartedDate(vendorRejectionForm.getConWorkStartedDate());
				contractorForBidWorkStatus.setConWorkCompleteDate(vendorRejectionForm.getConWorkCompleteDate());
				contractorForBidWorkStatus.setUserId(vendorRejectionForm.getContractorId());
				ContractorForBidWorkStatus save = contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);

				// Monika code end

				// consumerApplicationDetail.setIsRejected(Boolean.FALSE);

				appStatusDb = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
				consumerApplicationDetail.setApplicationStatus(appStatusDb);

//					appStatusDb = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
//					consumerApplicationDetail.setApplicationStatus(appStatusDb);

			} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 24) {
				ContractorForBidWorkStatus findByConsumerApplicationNumber = contractorForBidWorkStatusRepository
						.findByConsumerApplicationNo(vendorRejectionForm.getConsumerApplicationNo());

				findByConsumerApplicationNumber
						.setActualWorkCompletionDate(vendorRejectionForm.getActualWorkCompletionDate());

				contractorForBidWorkStatusRepository.save(findByConsumerApplicationNumber);
				if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)
						|| consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(2L)) {

					appStatusDb = applicationStatusService.findById(
							ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
					consumerApplicationDetail.setApplicationStatus(appStatusDb);

//					appStatusDb = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
//					consumerApplicationDetail.setApplicationStatus(appStatusDb);
				}

			}

//			else {
//				ContractorForBidWorkStatus contractorForBidWorkStatus =	contractorForBidWorkStatusRepository.findByConsumerApplicationNo(vendorRejectionForm.getConsumerApplicationNo());
//				if(contractorForBidWorkStatus.getActualWorkCompletionDate()!=null || !contractorForBidWorkStatus.getActualWorkCompletionDate().equalsIgnoreCase(""))
//				{
//					contractorForBidWorkStatus.setActualWorkCompletionDate(contractorForBidWorkStatus.getActualWorkCompletionDate());
//					appStatusDb = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
//					consumerApplicationDetail.setApplicationStatus(appStatusDb);
//					
//				}
//			
//			}
//			charitra code start here 

//		final SMSRequest smsRequest = new SMSRequest();
//		smsRequest.setText(MessageFormat.format(messageProperties.getSendMsgConsumer(),
//				new Object[] { consumerApplicationDetail.getConsumerName() }));
//		smsRequest.setMobileNo(consumerApplicationDetail.getConsumers().getConsumerMobileNo());
//
//		smsRequest.setTemplateId(messageProperties.getConsumerTemplateId());
//
//		try {
//			smsDirectService.sendMessage(smsRequest);
//		} catch (Exception e) {
//			// TODO Auto-generated catch blocks
//			e.printStackTrace();
//		}
//	charitra code end here	

		}

		ConsumerApplicationDetail consumerApplicationDetailDb = consumerApplicationDetailService
				.saveConsumerApplication(consumerApplicationDetail);

		return consumerApplicationDetailDb;

	}

	@Override
	public Vendor findByVendorCode(String contractorId) {
		final String method = "VendorServiceImpl : updateConsumerApplicationDetailsByApplicationId()";
		logger.info(method);

		return repository.findByVendorCode(contractorId);
	}

//	public ConsumerApplicationDetail updateConsumerApplicationDetailsByApplicationId(
//			VendorRejectionForm vendorRejectionForm)
//			throws ConsumerNotFoundByApplicationIdException, ConsumerApplicationDetailException {
//		System.err.println("inside updateConsumerDetailsByApplicationId() service");
//		final String method = "VendorServiceImpl : updateConsumerApplicationDetailsByApplicationId()";
//		logger.info(method);
//		final Response<ConsumerApplicationDetail> response = new Response<>();
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
//				.findConsumerApplicationDetailByApplicationNo(vendorRejectionForm.getConsumerApplicationNo());
//
//		logger.info("Response is returning successfully");
//
//		Predicate<ConsumerApplicationDetail> cadr = cad -> {
//			if (vendorRejectionForm.getIsRejected().equals("ture")) {
//				return true;
//			} else {
//				return false;
//			}
//
//		};
//
//		Predicate<ConsumerApplicationDetail> cada = cadaa -> {
//			if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 22) {
//				return true;
//			}
//			return false;
//		};
//
//		if (cadr.and(cada).test(consumerApplicationDetail)) {
//			consumerApplicationDetail.setApplicationStatus(
//					applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId()));
//		} else {
//			if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 22) {
//
//				ContractorForBidWorkStatus contractorForBidWorkStatus = new ContractorForBidWorkStatus();
//				contractorForBidWorkStatus.setConsumerApplicationNumber(vendorRejectionForm.getConsumerApplicationNo());
//				contractorForBidWorkStatus
//						.setMaterialHandoverSiteDate(vendorRejectionForm.getMaterialHandoverSiteDate());
//				contractorForBidWorkStatus
//						.setMaterialInstallFinishDate(vendorRejectionForm.getMaterialInstallFinishDate());
//				contractorForBidWorkStatus
//						.setMaterialInstallStartDate(vendorRejectionForm.getMaterialInstallStartDate());
//				contractorForBidWorkStatus.setConWorkStartedDate(vendorRejectionForm.getConWorkStartedDate());
//				contractorForBidWorkStatus.setConWorkCompleteDate(vendorRejectionForm.getConWorkCompleteDate());
//				contractorForBidWorkStatus.setUserId(vendorRejectionForm.getContractorId());
//				ContractorForBidWorkStatus save = contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);
//
//				consumerApplicationDetail.setApplicationStatus(
//						applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId()));
//
//			} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 24) {
//				ContractorForBidWorkStatus findByConsumerApplicationNumber = contractorForBidWorkStatusRepository
//						.findByConsumerApplicationNo(vendorRejectionForm.getConsumerApplicationNo());
//
//				findByConsumerApplicationNumber
//						.setActualWorkCompletionDate(vendorRejectionForm.getActualWorkCompletionDate());
//
//				contractorForBidWorkStatusRepository.save(findByConsumerApplicationNumber);
//
//				consumerApplicationDetail.setApplicationStatus(applicationStatusService.findById(
//						ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId()));
//
//			}
//		}
//		ConsumerApplicationDetail consumerApplicationDetailDb = consumerApplicationDetailService
//				.saveConsumerApplication(consumerApplicationDetail);
//
//		return consumerApplicationDetailDb;
//
//	}

	@Transactional
	public void deleteContractorForBid(String consumerApplicationNo) {
		contractorForBidRepository.deleteByConsumerApplicationNo(consumerApplicationNo);
	}

}
