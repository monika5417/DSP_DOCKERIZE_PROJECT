package com.mpcz.deposit_scheme.dryprincipalutility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;

@Component
public class DryUtility {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ErpEstimateAmountService erpEstimateAmountService;

	@Autowired
	private MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	private ErpRevRepository erpRevRepository;

	public ConsumerApplicationDetail validateConsumerApplication(String consumerApplicationNo)
			throws ConsumerApplicationDetailException {

		System.err.println("consumerApplictionDetailRepository : " + consumerApplictionDetailRepository);
		return consumerApplictionDetailRepository.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Consumer Application No. Not Found For : " + consumerApplicationNo)));
	}

	public ApplicationDocument validateApplicationDocumentExistOrNot(Long consumerApplicationId) {
		ApplicationDocument applicationDocDB = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationId);
		if (Objects.isNull(applicationDocDB)) {
			applicationDocDB = new ApplicationDocument();
		}
		return applicationDocDB;
	}

//	public List<ApplicationDocument> validateApplicationDocumentExistOrNotInList(Long consumerApplicationId) {
//		List<ApplicationDocument> applicationDocDB = applicationDocumentRepository
//				.findAllDataByConsumerApplicationId(consumerApplicationId);
//		if (Objects.isNull(applicationDocDB) || applicationDocDB.isEmpty()) {
//			applicationDocDB = new ApplicationDocument();
//		}
//		return applicationDocDB;
//	}

	public ErpEstimateAmountData validateErpDataExistOrNot(String erpNo) throws ErpEstimateAmountException {
		return estimateAmountRepository.findById(erpNo).orElseThrow(() -> new ErpEstimateAmountException(
				new Response(HttpCode.NULL_OBJECT, "Erp no. not found for the above application no.")));

	}

	public ResponseEntity<Response> checkErpExistOrNot(String erpNumber) throws ErpEstimateAmountException {

		ErpEstimateAmountData erpDemandDB = erpEstimateAmountService.findByEstimateNumber(erpNumber);
		Optional<MmkyPayAmount> mmkyPayAmountERPDB = mmkyPayAmountRespository.findByErpNumber(erpNumber);
		ErpRev RevErpNoDB = erpRevRepository.findByNewErpNo(erpNumber);

		System.out.println("erpDemandDB: " + erpDemandDB);
		System.out.println("mmkyPayAmountERPDB is present: " + mmkyPayAmountERPDB.isPresent());
		System.out.println("RevErpNoDB: " + RevErpNoDB);

		if (erpDemandDB != null || mmkyPayAmountERPDB.isPresent() || RevErpNoDB != null) {

			List<ConsumerApplicationDetail> findByErpNo = consumerApplictionDetailRepository
					.findByerpWorkFlowNumberOrRevisedErpNumber(erpNumber, erpNumber);

			System.out.println("findByErpNo size: " + findByErpNo.size());

			List<String> applicationNumbers = new ArrayList<>();

			for (ConsumerApplicationDetail detail : findByErpNo) {
				System.out.println("ConsumerApplication no: " + detail.getConsumerApplicationNo());
				applicationNumbers.add(detail.getConsumerApplicationNo());
			}

			System.out.println("applicationNumbers size: " + applicationNumbers.size());

			if (!applicationNumbers.isEmpty()) {
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
						.body(new Response(HttpCode.NOT_ACCEPTABLE,
								"This ERP Number Is Already Associated With Another Application Number.",
								applicationNumbers));
			}
		}

		// If list is empty, return this
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
				.body(new Response(HttpCode.OK, "You can enroll Erp no. now ", null));
	}

//	this code is change on 24-09-2025
//	public ResponseEntity<Response> checkErpExistOrNot(String erpNumber) throws ErpEstimateAmountException {
//
//		Set<String> applicationNumbers = new HashSet<>();
//
//		// 1. ERP Demand Table check
//		ErpEstimateAmountData erpDemandDB = erpEstimateAmountService.findByEstimateNumber(erpNumber);
//		if (erpDemandDB != null) {
//			consumerApplictionDetailRepository.findByerpWorkFlowNumber(erpNumber).forEach(erp -> {
//				applicationNumbers.add(erp.getConsumerApplicationNo());
//			});
//
//		}
//
//		// 2. MMKY Table check
//		Optional<MmkyPayAmount> mmkyPayAmountERPDB = mmkyPayAmountRespository.findByErpNumber(erpNumber);
//		if (mmkyPayAmountERPDB.isPresent() && mmkyPayAmountERPDB.get().getConsumerApplicationNumber() != null) {
//			applicationNumbers.add(mmkyPayAmountERPDB.get().getConsumerApplicationNumber());
//		}
//
//		// 3. Revised ERP Table check
//		ErpRev RevErpNoDB = erpRevRepository.findByNewErpNo(erpNumber);
//		if (RevErpNoDB != null && RevErpNoDB.getConsAppNo() != null) {
//			applicationNumbers.add(RevErpNoDB.getConsAppNo());
//		}
//
//		// 4. Consumer Application Detail Table check
//		List<ConsumerApplicationDetail> findByErpNo = consumerApplictionDetailRepository
//				.findByerpWorkFlowNumberOrRevisedErpNumber(erpNumber, erpNumber);
//
//		if (findByErpNo != null && !findByErpNo.isEmpty()) {
//			applicationNumbers.addAll(findByErpNo.stream().map(ConsumerApplicationDetail::getConsumerApplicationNo).distinct()
//					.collect(Collectors.toList()));
//		}
//		List<String> uniqueApplications = new ArrayList<>(applicationNumbers);
//
//		// If ERP is already linked anywhere
//		if (!applicationNumbers.isEmpty()) {
//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
//					.body(new Response(HttpCode.NOT_ACCEPTABLE,
//							"This ERP Number Is Already Associated With Another Application Number.",
//							uniqueApplications));
//		}
//
//		// If ERP not found anywhere
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
//				.body(new Response(HttpCode.OK, "You can enroll ERP no. now", null));
//	}

	public ResponseEntity<Response> checkErpExistOrNot2(String erpNumber, String consumerAppNo)
			throws ErpEstimateAmountException {

		Set<String> applicationNumbers = new HashSet<>();

		// 1. ERP Demand Table check
		ErpEstimateAmountData erpDemandDB = erpEstimateAmountService.findByEstimateNumber(erpNumber);
		if (erpDemandDB != null) {
			consumerApplictionDetailRepository.findByerpWorkFlowNumber(erpNumber).forEach(erp -> {
				applicationNumbers.add(erp.getConsumerApplicationNo());
			});
		}

		// 2. MMKY Table check
		Optional<MmkyPayAmount> mmkyPayAmountERPDB = mmkyPayAmountRespository.findByErpNumber(erpNumber);
		if (mmkyPayAmountERPDB.isPresent() && mmkyPayAmountERPDB.get().getConsumerApplicationNumber() != null) {
			applicationNumbers.add(mmkyPayAmountERPDB.get().getConsumerApplicationNumber());
		}

		// 3. Revised ERP Table check
		ErpRev RevErpNoDB = erpRevRepository.findByNewErpNo(erpNumber);
		if (RevErpNoDB != null && RevErpNoDB.getConsAppNo() != null) {
			applicationNumbers.add(RevErpNoDB.getConsAppNo());
		}

		// 4. Consumer Application Detail Table check
		List<ConsumerApplicationDetail> findByErpNo = consumerApplictionDetailRepository
				.findByerpWorkFlowNumberOrRevisedErpNumber(erpNumber, erpNumber);

		if (findByErpNo != null && !findByErpNo.isEmpty()) {
			applicationNumbers.addAll(findByErpNo.stream().map(ConsumerApplicationDetail::getConsumerApplicationNo)
					.collect(Collectors.toSet())); // set already unique
		}

		// ⚡ Ignore current application from the check
		applicationNumbers.remove(consumerAppNo);

		if (!applicationNumbers.isEmpty()) {
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
					.body(new Response(HttpCode.NOT_ACCEPTABLE,
							"This ERP Number Is Already Associated With Another Application Number.",
							new ArrayList<>(applicationNumbers)));
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
				.body(new Response(HttpCode.OK, "You can enroll ERP no. now", null));
	}

//	=======================================
	public ResponseEntity<Response> checkErpExistOrNotForRevDemand(String consumerApplicationNumber, String erpNumber)
			throws ErpEstimateAmountException {

		MmkyPayAmount mmkyPayAmountERPDB = mmkyPayAmountRespository
				.findByConsumerApplicationNumberAndErpNumber(consumerApplicationNumber, erpNumber);

		ErpRev RevErpNoDB = erpRevRepository.findByConsAppNoAndNewErpNo(consumerApplicationNumber, erpNumber);

		if (mmkyPayAmountERPDB != null || RevErpNoDB != null) {

			List<ConsumerApplicationDetail> findByErpNo = consumerApplictionDetailRepository
					.findByerpWorkFlowNumberOrRevisedErpNumber(erpNumber, erpNumber);

			System.out.println("findByErpNo size: " + findByErpNo.size());

			List<String> applicationNumbers = new ArrayList<>();

			for (ConsumerApplicationDetail detail : findByErpNo) {
				System.out.println("ConsumerApplication no: " + detail.getConsumerphonNumber());
				applicationNumbers.add(detail.getConsumerApplicationNo());
			}

			System.out.println("applicationNumbers size: " + applicationNumbers.size());

			if (!applicationNumbers.isEmpty()) {
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
						.body(new Response(HttpCode.NOT_ACCEPTABLE,
								"This ERP Number Is Already Associated With Another Application Number.",
								applicationNumbers));
			}
		}

		// If list is empty, return this
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
				.body(new Response(HttpCode.OK, "You can enroll Erp no. now ", null));
	}

	public ResponseEntity<Response> checkErpExistOrNotForReviseDemand(String erpNumber)
			throws ErpEstimateAmountException {

		Optional<MmkyPayAmount> mmkyPayAmountERPDB = mmkyPayAmountRespository.findByErpNumber(erpNumber);
		ErpRev RevErpNoDB = erpRevRepository.findByNewErpNo(erpNumber);

		System.out.println("mmkyPayAmountERPDB is present: " + mmkyPayAmountERPDB.isPresent());
		System.out.println("RevErpNoDB: " + RevErpNoDB);

		if (mmkyPayAmountERPDB.isPresent() || RevErpNoDB != null) {

			List<ConsumerApplicationDetail> findByErpNo = consumerApplictionDetailRepository
					.findByerpWorkFlowNumberOrRevisedErpNumber(erpNumber, erpNumber);

			System.out.println("findByErpNo size: " + findByErpNo.size());

			List<String> applicationNumbers = new ArrayList<>();

			for (ConsumerApplicationDetail detail : findByErpNo) {
				System.out.println("ConsumerApplication no: " + detail.getConsumerphonNumber());
				applicationNumbers.add(detail.getConsumerApplicationNo());
			}

			System.out.println("applicationNumbers size: " + applicationNumbers.size());

			if (!applicationNumbers.isEmpty()) {
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
						.body(new Response(HttpCode.NOT_ACCEPTABLE,
								"This ERP Number Is Already Associated With Another Application Number.",
								applicationNumbers));
			}
		}

		// If list is empty, return this
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
				.body(new Response(HttpCode.OK, "You can enroll Erp no. now ", null));
	}

	public ResponseEntity<Response> checkErpExistOrNot1(String erpNumber, String consumerApplicationNo)
			throws ErpEstimateAmountException, ConsumerApplicationDetailException {

		ErpRev RevErpNoDB = null;
		ConsumerApplicationDetail validateConsumerApplication = validateConsumerApplication(consumerApplicationNo);

		ErpEstimateAmountData erpDemandDB = erpEstimateAmountService.findByEstimateNumber(erpNumber);
		Optional<MmkyPayAmount> mmkyPayAmountERPDB = mmkyPayAmountRespository.findByErpNumber(erpNumber);
		if (validateConsumerApplication.getApplicationStatus().getApplicationStatusId() != 30L
				&& validateConsumerApplication.getApplicationStatus().getApplicationStatusId() != 23L
				&& validateConsumerApplication.getApplicationStatus().getApplicationStatusId() != 28L) {
			RevErpNoDB = erpRevRepository.findByNewErpNo(erpNumber);
		}

		System.out.println("erpDemandDB: " + erpDemandDB);
		System.out.println("mmkyPayAmountERPDB is present: " + mmkyPayAmountERPDB.isPresent());
		System.out.println("RevErpNoDB: " + RevErpNoDB);

		if (erpDemandDB != null || mmkyPayAmountERPDB.isPresent() || RevErpNoDB != null) {

			List<ConsumerApplicationDetail> findByErpNo = consumerApplictionDetailRepository
					.findByerpWorkFlowNumberOrRevisedErpNumber(erpNumber, erpNumber);

			System.out.println("findByErpNo size: " + findByErpNo.size());

			List<String> applicationNumbers = new ArrayList<>();

			for (ConsumerApplicationDetail detail : findByErpNo) {
				System.out.println("ConsumerApplication no: " + detail.getConsumerphonNumber());
				applicationNumbers.add(detail.getConsumerApplicationNo());
			}

			System.out.println("applicationNumbers size: " + applicationNumbers.size());

			if (!applicationNumbers.isEmpty()) {
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
						.body(new Response(HttpCode.NOT_ACCEPTABLE,
								"This ERP Number Is Already Associated With Another Application Number.",
								applicationNumbers));
			}
		}

		// If list is empty, return this
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
				.body(new Response(HttpCode.OK, "You can enroll Erp no. now ", null));
	}

	public ConsumerApplicationDetail saveConsumerApplicationDetail(ConsumerApplicationDetail consumerApplicationDetail)
			throws ConsumerApplicationDetailException {
		consumerApplictionDetailRepository
				.findByConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo())
				.orElseThrow(() -> new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Consumer Application No. Not Found For : "
								+ consumerApplicationDetail.getConsumerApplicationNo())));

		ConsumerApplicationDetail save = consumerApplictionDetailRepository.save(consumerApplicationDetail);
		if (Objects.nonNull(save)) {
			return save;
		} else {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Consumer Application Data not saved"));
		}
	}

	public void checkValidationError(BindingResult bindingResult) throws FormValidationException {
		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});
			throw new FormValidationException(
					new Response<>(ResponseCode.FORM_VALIDATION_ERROR, ResponseMessage.FORM_VALIDATION_ERROR, errorList));
		}
	}

	
	
}
