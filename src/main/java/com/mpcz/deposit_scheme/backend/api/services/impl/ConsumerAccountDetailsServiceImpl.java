package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAccountDetails;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.DuplicateRefund;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.RefundProcessData;
import com.mpcz.deposit_scheme.backend.api.domain.RefundRejectedRemark;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.dto.DemandRefundDto;
import com.mpcz.deposit_scheme.backend.api.dto.DuplicatePaymentRefundDto;
import com.mpcz.deposit_scheme.backend.api.dto.RegistrationRefundDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAccountDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DuplicateRefundRepository;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerAccountDetailsService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;
import com.mpcz.deposit_scheme.backend.api.response.Response;

@Service
public class ConsumerAccountDetailsServiceImpl implements ConsumerAccountDetailsService {

	@Autowired
	private UploadService uploadService;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ConsumerAccountDetailsRepository consumerAccountDetailsRepository;

	@Autowired
	private DryUtility dryUtility;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private ConsumerAccountDetailsServiceImpl consumerAccountDetailsService; // Injected Spring Bean

	@Autowired
	private DuplicateRefundRepository duplicateRefundRepository;

	@Transactional
	@Override
	public ConsumerAccountDetails save(String consumerAccountDetails, MultipartFile chequeBookOrPaasbook,
			MultipartFile docPanNo, MultipartFile docAddressProof, MultipartFile docNotry,
			MultipartFile docAuthorizedLetter, MultipartFile docRequestLetter)
			throws DocumentTypeException, ConsumerApplicationDetailException {

		ConsumerAccountDetails consumerAccJson = stringToJson(consumerAccountDetails);
		List<String> missingFields = new ArrayList<>();

		if (consumerAccJson.getAccountHolderName() == null || consumerAccJson.getAccountHolderName().trim().isEmpty()) {
			missingFields.add("accountHolderName");
		}
		if (consumerAccJson.getAccountNo() == null || consumerAccJson.getAccountNo().trim().isEmpty()) {
			missingFields.add("accountNo");
		}
		if (consumerAccJson.getBankName() == null || consumerAccJson.getBankName().trim().isEmpty()) {
			missingFields.add("bankName");
		}
		if (consumerAccJson.getIfscCode() == null || consumerAccJson.getIfscCode().trim().isEmpty()) {
			missingFields.add("ifscCode");
		}
		if (consumerAccJson.getPanNo() == null || consumerAccJson.getPanNo().trim().isEmpty()) {
			missingFields.add("panNo");
		}
		if (consumerAccJson.getConsumerApplicationNo() == null
				|| consumerAccJson.getConsumerApplicationNo().trim().isEmpty()) {
			missingFields.add("consumerApplicationNo");
		}

		if (!missingFields.isEmpty()) {
			String errorMsg = "Missing or blank fields: " + String.join(", ", missingFields);
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, errorMsg));
		}

		ConsumerApplicationDetail consumerApplicationNumberDB = dryUtility
				.validateConsumerApplication(consumerAccJson.getConsumerApplicationNo());

		ConsumerAccountDetails consumerAccountDetailsDB = consumerAccountDetailsRepository
				.findByConsumerApplicationNoIsActive(consumerAccJson.getConsumerApplicationNo());

		if (!Objects.isNull(consumerAccountDetailsDB)) {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
					"Consumer Account Details Already Submitted for this application no. : "
							+ consumerAccJson.getConsumerApplicationNo()));
		}

		uploadAllDocument(consumerApplicationNumberDB, chequeBookOrPaasbook, docPanNo, docAddressProof, docNotry,
				docAuthorizedLetter, docRequestLetter);

		return consumerAccountDetailsRepository.save(consumerAccJson);
	}

	public void uploadAllDocument(ConsumerApplicationDetail consumerApplicationNumberDB,
			MultipartFile chequeBookOrPaasbook, MultipartFile docPanNo, MultipartFile docAddressProof,
			MultipartFile docNotry, MultipartFile docAuthorizedLetter, MultipartFile docRequestLetter)
			throws ConsumerApplicationDetailException, DocumentTypeException {

		Upload chequeBookOrPaasbookUpload = null;
		Upload docPanNoUpload = null;
		Upload docAddressProofUpload = null;
		Upload docNotryUpload = null;
		Upload docAuthorizedLetterUpload = null;
		Upload docRequestLetteUpload = null;

		if (chequeBookOrPaasbook != null) {
			chequeBookOrPaasbookUpload = uploadService.uploadFile(chequeBookOrPaasbook, "CHECK_OR_PASSBOOK");
			if (chequeBookOrPaasbookUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document CHECK_OR_PASSBOOK not uploaded"));
			}
		}
		if (docPanNo != null) {
			docPanNoUpload = uploadService.uploadFile(docPanNo, "PAN_NO_FILE");
			if (docPanNoUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document PAN_NO not uploaded"));
			}
		}

		if (docNotry != null) {
			docNotryUpload = uploadService.uploadFile(docNotry, "DOC_NOTRY");
			if (docNotryUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Notry Not Uploaded"));
			}

		}
		if (docAuthorizedLetter != null) {
			docAuthorizedLetterUpload = uploadService.uploadFile(docAuthorizedLetter, "DOC_AUTHORIZED_LETTER");
			if (docAuthorizedLetterUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Authorized Letter Not Uploaded"));
			}
		}

		if (docAddressProof != null) {
			docAddressProofUpload = uploadService.uploadFile(docAddressProof, "DOC_ADDRESS_PROOF");
			if (docAddressProofUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Address Proof Not Uploaded"));
			}
		}

		if (docRequestLetter != null) {
			docRequestLetteUpload = uploadService.uploadFile(docRequestLetter, "DOC_REQUEST_LETTER");
			if (docRequestLetteUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Request Letter Not Uploaded"));
			}
		}

		List<ApplicationDocument> allDataByConsumerApplicationId = applicationDocumentRepository
				.findAllDataByConsumerApplicationId(consumerApplicationNumberDB.getConsumerApplicationId());
		if (!allDataByConsumerApplicationId.isEmpty()) {
			for (ApplicationDocument doc : allDataByConsumerApplicationId) {
				doc.setDocCheckOrPassBookFile(chequeBookOrPaasbookUpload);
				doc.setDocPanNoFile(docPanNoUpload);
				if (docNotry != null)
					doc.setDocNotry(docNotryUpload);
				if (docAuthorizedLetter != null)
					doc.setDocGovAuthorizedLetterFile(docAuthorizedLetterUpload);
				if (docAddressProof != null)
					doc.setDocAddressProofFile(docAddressProofUpload);
				if (docRequestLetter != null)
					doc.setDocRequestLetter(docRequestLetteUpload);
				applicationDocumentRepository.save(doc);
			}
		} else {
			ApplicationDocument appDoc = new ApplicationDocument();
			appDoc.setDocCheckOrPassBookFile(chequeBookOrPaasbookUpload);
			appDoc.setDocPanNoFile(docPanNoUpload);
			if (docNotry != null)
				appDoc.setDocNotry(docNotryUpload);
			if (docAuthorizedLetter != null)
				appDoc.setDocGovAuthorizedLetterFile(docAuthorizedLetterUpload);
			if (docAddressProof != null)
				appDoc.setDocAddressProofFile(docAddressProofUpload);
			if (docRequestLetter != null)
				appDoc.setDocRequestLetter(docRequestLetteUpload);
			appDoc.setConsumerApplicationDetail(consumerApplicationNumberDB);
			applicationDocumentRepository.save(appDoc);
		}

	}

	public static ConsumerAccountDetails stringToJson(String consumerAccountDetails)
			throws ConsumerApplicationDetailException {
		ConsumerAccountDetails data = new ConsumerAccountDetails();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(consumerAccountDetails, ConsumerAccountDetails.class);
		} catch (IOException e) {
			throw new ConsumerApplicationDetailException(new Response("400", "Invalid JSON format: " + e.getMessage()));
		}
	}

	@Override
	public DuplicatePaymentRefundDto getConsumerApplicationDuplicateRefundDetails(String consumerApplicationNo)
			throws ConsumerApplicationDetailException {

		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(consumerApplicationNo);

		DuplicatePaymentRefundDto dto = new DuplicatePaymentRefundDto();
		dto.setTotalPaymentOfRegistration(getPaymentCount(
				billPaymentResponseeeeeeeRepository.getTotalPaymentOfRegistration(consumerApplicationNo)));
		dto.setTotalPaymentOfDemand(
				getPaymentCount(billPaymentResponseeeeeeeRepository.getTotalPaymentOfDemand(consumerApplicationNo)));
		dto.setRegistationRefundCompleted(
				getPaymentCount(billPaymentResponseeeeeeeRepository.registationRefundCompleted(consumerApplicationNo)));
		dto.setDemandRefundCompleted(
				getPaymentCount(billPaymentResponseeeeeeeRepository.demandRefundCompleted(consumerApplicationNo)));
		dto.setRemainingRegistrationRefund(getPaymentCount(
				billPaymentResponseeeeeeeRepository.remainingRegistrationRefund(consumerApplicationNo)));
		dto.setRemainingDemandRefund(
				getPaymentCount(billPaymentResponseeeeeeeRepository.remainingDemandRefund(consumerApplicationNo)));

		List<RegistrationRefundDto> registrationRemaninigRefundList = convertToRegistrationRefundDto(
				billPaymentResponseeeeeeeRepository.remainingRegistrationRefund(consumerApplicationNo));
		List<DemandRefundDto> demandRemainingRefundList = convertToDemandRefundDto(
				billPaymentResponseeeeeeeRepository.remainingDemandRefund(consumerApplicationNo),
				validateConsumerApplication, consumerAccountDetailsService);

		dto.setRegistrationDto(registrationRemaninigRefundList);
		dto.setDemandDto(demandRemainingRefundList);

		return dto;
	}

	private String getPaymentCount(List<BillDeskPaymentResponse> payments) {
		return String.valueOf(payments.size());
	}

	public static List<RegistrationRefundDto> convertToRegistrationRefundDto(
			List<BillDeskPaymentResponse> remaningRegRefundDB) {
		List<RegistrationRefundDto> registrationRemaninigRefundList = new ArrayList<>();
		remaningRegRefundDB.stream().forEach(registration -> {
			RegistrationRefundDto registrationDto = new RegistrationRefundDto();
			registrationDto.setApplicationNo(registration.getConsumerApplicationNo());
			registrationDto.setRegistrationAmount(new BigDecimal(registration.getAmount()));
			registrationDto.setRegistrationTransactionId(registration.getTranId());
			registrationDto.setRegistrationRefundableAmount(new BigDecimal(1000));
			registrationRemaninigRefundList.add(registrationDto);
		});

		return registrationRemaninigRefundList;
	}

	public static List<DemandRefundDto> convertToDemandRefundDto(List<BillDeskPaymentResponse> remaningDemandRefundDB,
			ConsumerApplicationDetail consumerApplicationDetail,
			ConsumerAccountDetailsServiceImpl consumerAccountDetailsService) {
		List<DemandRefundDto> demandRemainingRefundList = new ArrayList<>();
		remaningDemandRefundDB.stream().forEach(demand -> {
			DemandRefundDto demandDto = new DemandRefundDto();
			demandDto.setApplicationNo(demand.getConsumerApplicationNo());
			demandDto.setDemandAmount(new BigDecimal(demand.getAmount()));
			demandDto.setDemandTransactionId(demand.getTranId());
			try {

				BigDecimal demandRefundableAmount = consumerAccountDetailsService.calculateDemandRefundableAmount(
						consumerApplicationDetail.getErpWorkFlowNumber(), new BigDecimal(demand.getAmount()),
						consumerApplicationDetail.getSchemeType().getSchemeTypeId());
				demandDto.setDemandRefundableAmount(demandRefundableAmount);
			} catch (ErpEstimateAmountException e) {
				e.printStackTrace();
			}
			demandRemainingRefundList.add(demandDto);
		});

		return demandRemainingRefundList;
	}

	public BigDecimal calculateDemandRefundableAmount(String erpNo, BigDecimal demandAmount, Long schemeTypeId)
			throws ErpEstimateAmountException {
		if (dryUtility == null) {
			throw new NullPointerException("dryUtility is not initialized.");
		}

		ErpEstimateAmountData validateErpDataExistOrNot = dryUtility.validateErpDataExistOrNot(erpNo);
		BigDecimal refundableDemandAmount = new BigDecimal(0.0);

		if (schemeTypeId == 1L) {
			if (demandAmount.compareTo(validateErpDataExistOrNot.getTotalBalanceSupervisionAmount()) == 0) {

				refundableDemandAmount = demandAmount.subtract(validateErpDataExistOrNot.getCgst())
						.subtract(validateErpDataExistOrNot.getCgst());
			}
		} else {
			if (demandAmount.compareTo(validateErpDataExistOrNot.getTotalBalanceDepositAmount()) == 0) {
				refundableDemandAmount = demandAmount.subtract(validateErpDataExistOrNot.getCgst())
						.subtract(validateErpDataExistOrNot.getCgst());
			}
		}
		return refundableDemandAmount;
	}

	@Override
	public DuplicateRefund saveDuplicateRefundDetails(DuplicateRefund duplicateRefund)
			throws ConsumerApplicationDetailException, ErpEstimateAmountException {

		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(duplicateRefund.getConsumerApplicationNo());
		Optional<DuplicateRefund> transactionIdCheckDB = duplicateRefundRepository
				.findByTransactionId(duplicateRefund.getTransactionId());

		if (transactionIdCheckDB.isPresent()) {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
					"Refund Already Initiated for this Transaction Id : " + duplicateRefund.getTransactionId()));
		}
		BillDeskPaymentResponse billdeskResponse = billPaymentResponseeeeeeeRepository
				.findByTranId(duplicateRefund.getTransactionId());

		if (Objects.isNull(billdeskResponse)) {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
					"Transaction Id Not Found in Billdesk Payment: " + duplicateRefund.getTransactionId()));
		}

		if ("0300".equals(billdeskResponse.getAuth_status())) {
			String info = billdeskResponse.getAdditionalInfo();

			if ("Registration_Fees".equals(info)) {
				duplicateRefund.setRefundTypeRegistration(info);
				duplicateRefund.setRefundAmount(new BigDecimal(1000));
			} else if ("Demand_fees".equals(info)) {
				duplicateRefund.setRefundAmount(
						calculateDemandRefundableAmount(validateConsumerApplication.getErpWorkFlowNumber(),
								new BigDecimal(billdeskResponse.getAmount()),
								validateConsumerApplication.getSchemeType().getSchemeTypeId()));
				duplicateRefund.setRefundTypeDemand("Demand_fees");
			}
		}

		return duplicateRefundRepository.save(duplicateRefund);

	}

	@Override
	public ConsumerAccountDetails getConsumerAccountDetails(String consumerApplicationNo) {
		return consumerAccountDetailsRepository.findByConsumerApplicationNoIsActive(consumerApplicationNo);
	}

	@Override
	public DuplicateRefund dgmApprovalForDuplicateRefund(String consumerApplicationNo, boolean dgmApproval,
			String dgmId, String dgmName) throws ConsumerApplicationDetailException {
		DuplicateRefund applicationDuplicateRefDB = duplicateRefundRepository
				.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Consumer Application No Not Found In Duplicate Refund Table")));
		applicationDuplicateRefDB.setDgmApproval(String.valueOf(dgmApproval));
		applicationDuplicateRefDB.setDgmApprovedId(dgmId);
		applicationDuplicateRefDB.setDgmName(dgmName);
		applicationDuplicateRefDB.setDgmApprovedDate(Date.valueOf(LocalDate.now()).toString());

		return duplicateRefundRepository.save(applicationDuplicateRefDB);

	}

	@Override
	public DuplicateRefund gmApprovalForDuplicateRefund(String consumerApplicationNo, boolean gmApproval, String gmId,
			String gmName) throws ConsumerApplicationDetailException {
		DuplicateRefund applicationDuplicateRefDB = duplicateRefundRepository
				.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Consumer Application No Not Found In Duplicate Refund Table")));

		applicationDuplicateRefDB.setGmApproval(String.valueOf(gmApproval));
		applicationDuplicateRefDB.setGmApprovedId(gmId);
		applicationDuplicateRefDB.setGmName(gmName);
		applicationDuplicateRefDB.setGmApprovedDate(Date.valueOf(LocalDate.now()).toString());
		return duplicateRefundRepository.save(applicationDuplicateRefDB);

	}

	@Override
	public DuplicateRefund financeAoDuplicateRefund(String consumerApplicationNo, boolean financeApproval,
			String financeAoId, String financeName) throws ConsumerApplicationDetailException {
		DuplicateRefund applicationDuplicateRefDB = duplicateRefundRepository
				.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Consumer Application No Not Found In Duplicate Refund Table")));
		applicationDuplicateRefDB.setFinanceApproval(String.valueOf(financeApproval));
		applicationDuplicateRefDB.setFinanceId(financeAoId);
		applicationDuplicateRefDB.setFinanceName(financeName);
		applicationDuplicateRefDB.setFinanceApprovedDate(Date.valueOf(LocalDate.now()).toString());

		return duplicateRefundRepository.save(applicationDuplicateRefDB);

	}
}