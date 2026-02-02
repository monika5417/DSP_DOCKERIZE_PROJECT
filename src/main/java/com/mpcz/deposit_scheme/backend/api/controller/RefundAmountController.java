package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAccountDetails;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.DynamicQuery;
import com.mpcz.deposit_scheme.backend.api.domain.ProjectItemForZeroOneCase;
import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.dto.ApiResponseForZeroOrOnecaseDTO;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.BillDeskPaymentResponseException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.RefundAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAccountDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DynamicQueryRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ProjectItemForZeroOneCaseRepository;
import com.mpcz.deposit_scheme.backend.api.repository.RefundAmountRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.RefundAmountService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.services.impl.WorkCompletionChangStautsByDgmOAndMServiceIMp;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;
import com.mpcz.deposit_scheme.feignClient.SaveDataZeroOneCaseClient;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/refundAmount")
public class RefundAmountController {

	@Autowired
	private RefundAmountService refundAmountService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private DryUtility dryUtility;

	@Autowired
	private ConsumerAccountDetailsRepository consumerAccountDetailsRepository;

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMServiceIMp workCompletionChangStautsByDgmOAndMServiceIMp;

	@Autowired
	private RefundAmountRepository refundAmountRepository;

	@Autowired
	private DynamicQueryRepository dynamicQueryRepository;

	@PostMapping("/user/saveRefundDetails")
	public ResponseEntity<?> saveRefundDetails(@RequestBody RefundAmount refund)
			throws ConsumerApplicationDetailException {
		Response<RefundAmount> response = new Response<RefundAmount>();

		if (refund == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Payload is null");
			return ResponseEntity.ok(response);
		}
		ConsumerAccountDetails findByConsumerApplicationNoIsActive = consumerAccountDetailsRepository
				.findByConsumerApplicationNoIsActive(refund.getConsumerApplicationNo());
		if (findByConsumerApplicationNoIsActive == null) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "First Fill Consumer Account Details"));
		}

		RefundAmount saveRefundDetails = refundAmountService.saveRefundDetails(refund);
		if (saveRefundDetails == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Data not saved");
			return ResponseEntity.ok(response);
		}
		response.setCode(HttpCode.CREATED);
		response.setMessage("Data  saved");
		response.setList(Arrays.asList(saveRefundDetails));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/user/getRefundApplication/{consumerApplicationNo}")
	public ResponseEntity<?> getRefundApplication(@PathVariable String consumerApplicationNo) {
		Response<RefundAmount> response = new Response<RefundAmount>();
		RefundAmount refundApplicationDetails = refundAmountService.getRefundApplicationDetails(consumerApplicationNo);
		if (Objects.isNull(refundApplicationDetails)) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Data not found in database");
			return ResponseEntity.ok(response);
		} else {
			response.setCode(HttpCode.OK);
			response.setMessage("Data  Retrived Successfully");
			response.setList(Arrays.asList(refundApplicationDetails));
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/user/getAllData")
	public ResponseEntity<?> getAllData() {
		List<RefundAmount> allRefundApplication = refundAmountService.getAllRefundApplication();
		if (Objects.isNull(allRefundApplication)) {
			return ResponseEntity.ok(new Response<Object>(HttpCode.NULL_OBJECT, "Data not found in database"));
		} else {
			return ResponseEntity
					.ok(new Response<RefundAmount>(HttpCode.OK, "Data  Retrived Successfully", allRefundApplication));
		}
	}

	@PostMapping("/consumer/saveApplicationCancelletion")
	public ResponseEntity<?> saveApplicationCancelletion(@RequestBody RefundAmount refundAmount)
			throws ConsumerApplicationDetailException, BillDeskPaymentResponseException {

		if (Objects.isNull(refundAmount)) {
			return ResponseEntity.ok(new Response<RefundAmount>(HttpCode.NULL_OBJECT, "Payload is null"));
		} else {
			ConsumerAccountDetails findByConsumerApplicationNoIsActive = consumerAccountDetailsRepository
					.findByConsumerApplicationNoIsActive(refundAmount.getConsumerApplicationNo());
			if (findByConsumerApplicationNoIsActive == null) {
				return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "First Fill Consumer Account Details"));
			}

			RefundAmount saved = refundAmountService.saveConsumerApplicationCancel(refundAmount);
			if (Objects.isNull(saved)) {
				return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
			}
			return ResponseEntity.ok(new Response(HttpCode.CREATED, "Data Saved Successfully ", Arrays.asList(saved)));
		}
	}

	@PutMapping("/user/dgmApprovalForRefund")
	public ResponseEntity<?> dgmApprovalForRefund(@RequestParam String consumerApplicationNo,
			@RequestParam boolean dgmApproval, @RequestParam String dgmId, @RequestParam String dgmName,
			@RequestParam String dgmRemark) throws ConsumerApplicationDetailException {

		RefundAmount dgmApprovalForRefund = refundAmountService.dgmApprovalForRefund(consumerApplicationNo, dgmApproval,
				dgmId, dgmName, dgmRemark);
		if (Objects.isNull(dgmApprovalForRefund)) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data not updated"));
		} else {
			return ResponseEntity.ok(new Response(HttpCode.UPDATED, "Record Updated Successfully ",
					Arrays.asList(dgmApprovalForRefund)));
		}
	}

	@PutMapping("/user/gmApprovalForRefund")
	public ResponseEntity<?> gmApprovalForRefund(@RequestParam String consumerApplicationNo,
			@RequestParam boolean gmApproval, @RequestParam String gmId, @RequestParam String gmName,
			@RequestParam String gmRemark) throws ConsumerApplicationDetailException {

		RefundAmount gmApprovalForRefund = refundAmountService.gmApprovalForRefund(consumerApplicationNo, gmApproval,
				gmId, gmName, gmRemark);
		if (Objects.isNull(gmApprovalForRefund)) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data not updated"));
		} else {
			return ResponseEntity.ok(
					new Response(HttpCode.UPDATED, "Record Updated Successfully ", Arrays.asList(gmApprovalForRefund)));
		}
	}

	@GetMapping("/getAllApplicationByConsumerId/{consumerId}")
	public ResponseEntity<?> getAllApplicationByConsumerId(@PathVariable Long consumerId) {
		List<ConsumerApplicationDetail> allApplicationByConsumerId = refundAmountService
				.getAllApplicationByConsumerId(consumerId);
		if (allApplicationByConsumerId.isEmpty()) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Application Not Found For This ConsumerId"));
		} else {
			return ResponseEntity
					.ok(new Response(HttpCode.OK, "Data Retrieved Successfully", allApplicationByConsumerId));
		}
	}

	@GetMapping("/getAllJeReturnAmountApp/{consumerId}")
	public ResponseEntity<?> getAllJeReturnAmountApp(@PathVariable Long consumerId) {
		List<ConsumerApplicationDetail> allJeReturnAmountApp = refundAmountService.getAllJeReturnAmountApp(consumerId);
		return ResponseEntity.ok(allJeReturnAmountApp.isEmpty()
				? new Response(HttpCode.NULL_OBJECT, "Application Not Found For This ConsumerId")
				: new Response(HttpCode.OK, "Data Retrieved Successfully", allJeReturnAmountApp));
	}

	@PutMapping("/user/dgmStcRefundApproval")
	public ResponseEntity<?> dgmStcRefundApproval(@RequestParam String consumerApplicationNo,
			@RequestParam boolean dgmStcApproval, @RequestParam String dgmStcId, @RequestParam String dgmStcName,
			@RequestParam String dgmStcRemark) throws RefundAmountException, ConsumerApplicationDetailException {

		RefundAmount dgmStcApprovalForRefund = refundAmountService.dgmStcApprovalForRefund(consumerApplicationNo,
				dgmStcApproval, dgmStcId, dgmStcName, dgmStcRemark);
		return Objects.isNull(dgmStcApprovalForRefund)
				? ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data not updated"))
				: ResponseEntity.ok(new Response(HttpCode.UPDATED, "Data Updated Successfully",
						Arrays.asList(dgmStcApprovalForRefund)));
	}

	@GetMapping("/getPaymentRefundDetails/{consumerApplicationNo}/{value}")
	public ResponseEntity<?> getPaymentRefundDetails(@PathVariable String consumerApplicationNo,
			@PathVariable Long value) throws ConsumerApplicationDetailException {
		Map<String, BigDecimal> paymentDetailForRefund = refundAmountService
				.getPaymentDetailForRefund(consumerApplicationNo, value);
		return ResponseEntity
				.ok(new Response(HttpCode.OK, "Data Retrived Successfully ", Arrays.asList(paymentDetailForRefund)));
	}

	@PostMapping("/consumer/saveReturnAmntApplication")
	public ResponseEntity<?> saveReturnAmntApplication(@RequestBody RefundAmount refundAmount)
			throws ConsumerApplicationDetailException, BillDeskPaymentResponseException {

		if (Objects.isNull(refundAmount)) {
			return ResponseEntity.ok(new Response<RefundAmount>(HttpCode.NULL_OBJECT, "Payload is null"));
		} else {
			ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
			if (consumerApplicationDetail.getSchemeType().getSchemeTypeId() == 2l) {
				return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
						"Deposit Scheme Type is not acceptable for Return material refund"));
			}
			ConsumerAccountDetails findByConsumerApplicationNoIsActive = consumerAccountDetailsRepository
					.findByConsumerApplicationNoIsActive(refundAmount.getConsumerApplicationNo());
			if (findByConsumerApplicationNoIsActive == null) {
				return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "First Fill Consumer Account Details"));
			}

			RefundAmount saved = refundAmountService.saveReturnAmntApplication(refundAmount);
			if (Objects.isNull(saved)) {
				return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
			}
			return ResponseEntity.ok(new Response(HttpCode.CREATED, "Data Saved Successfully ", Arrays.asList(saved)));
		}
	}

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//	@GetMapping("/getSchemeAndNatureByApplicationNo/{consumerApplicationNo}")
//	public ResponseEntity<?> getSchemeAndNatureByApplicationNo(@PathVariable String consumerApplicationNo) {
//		Response response = new Response();
//		System.err.println("aaaaaaaaaaaaaaa :  " + consumerApplicationNo);
//
//		List<Map<String, Object>> schemeAndNatureByApplicationNo = consumerApplictionDetailRepository
//				.getSchemeAndNatureByApplicationNo(consumerApplicationNo);
//		if (schemeAndNatureByApplicationNo.isEmpty()) {
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage("Data Not Found");
//		} else {
//			response.setCode(HttpCode.OK);
//			response.setMessage("Data Retrived Successfully");
//			response.setList(schemeAndNatureByApplicationNo);
//		}
//		return ResponseEntity.ok(response);
//
//	}

//	getSchemeAndNatureByApplicationNo by dynamic query 18-12-2025
	@GetMapping("/getSchemeAndNatureByApplicationNo/{consumerApplicationNo}")
	public ResponseEntity<?> getSchemeAndNatureByApplicationNo(@PathVariable String consumerApplicationNo) {
		Response response = new Response();

		DynamicQuery query = dynamicQueryRepository.findByQueryName("GET_SCHEME_NOW_FOR_REFUND");
		if (query == null || query.getQueryText() == null) {
			return ResponseEntity
					.ok(new Response<>(HttpCode.NULL_OBJECT, "Query not found for GET_SCHEME_NOW_FOR_REFUND"));
		}
		String queryText = query.getQueryText();
		Map<String, Object> param = new HashMap<>();
		param.put("consumerApplicationNo", consumerApplicationNo);
		List<Map<String, Object>> queryForList = namedParameterJdbcTemplate.queryForList(queryText, param);

		return ResponseEntity.ok(queryForList.isEmpty() ? new Response<>(HttpCode.NULL_OBJECT, "Data Not Found")
				: new Response<>(HttpCode.OK, "Data Retrived Successfully", queryForList));

	}

	@PutMapping("/user/financeAoRefundReject")
	public ResponseEntity<?> financeAoRefundReject(@RequestParam String consumerApplicationNo,
			@RequestParam boolean financeAoRefundReject, @RequestParam String financeAoId,
			@RequestParam String financeRemark, @RequestParam String financeName)
			throws RefundAmountException, ConsumerApplicationDetailException {

		RefundAmount financeAoRefundRejectDB = refundAmountService.financeAoRefundReject(consumerApplicationNo,
				financeAoRefundReject, financeAoId, financeRemark, financeName);
		return Objects.isNull(financeAoRefundRejectDB)
				? ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data not updated"))
				: ResponseEntity.ok(new Response(HttpCode.UPDATED, "Data Updated Successfully",
						Arrays.asList(financeAoRefundRejectDB)));
	}

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@PutMapping("/user/changeRefundApplicationStatusToFinalStage/{consumerApplicationNo}")
	public ResponseEntity<?> changeRefundApplicationStatusToFinalStage(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		ConsumerApplicationDetail consumerApplicationDB = dryUtility.validateConsumerApplication(consumerApplicationNo);
		consumerApplicationDB.setApplicationStatus(applicationStatusRepository
				.findById(ApplicationStatusEnum.PENDING_AT_ERP_END_FOR_INVOICE_CREATION.getId()).get());
		ConsumerApplicationDetail savedData = consumerApplictionDetailRepository.save(consumerApplicationDB);
		return ResponseEntity
				.ok(Objects.isNull(savedData) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated Successfully", Arrays.asList(savedData)));
	}

	@PatchMapping("/consumer/saveAddressProofData")
	public ResponseEntity<?> saveAddressProofData(@RequestPart String consumerApplicationNo,
			@RequestPart(required = false) String idProofNo,
			@RequestPart(required = false) MultipartFile docAddressProof,
			@RequestPart(required = false) MultipartFile docNotry,
			@RequestPart(required = false) MultipartFile docAuthorizedLetter,
			@RequestPart(required = false) MultipartFile docMRA,
			@RequestPart(required = false) MultipartFile docRequestLetter,
			@RequestPart(required = false) MultipartFile docDebitSlipFile)
			throws ConsumerApplicationDetailException, DocumentTypeException {

//		if (docAddressProof.isEmpty() || docAddressProof == null) {
//			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "Doc Address Proof File is null or empty"));
//		}
//		if (idProofNo.equals(null)) {
//			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "idProofNo should not be null"));
//		}
		ConsumerAccountDetails saveAddressProofData = refundAmountService.saveAddressProofData(consumerApplicationNo,
				idProofNo, docAddressProof, docNotry, docAuthorizedLetter, docMRA, docRequestLetter, docDebitSlipFile);
		return ResponseEntity.ok(Objects.isNull(saveAddressProofData)
				? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
				: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(saveAddressProofData)));

	}

	@Autowired
	private UploadService uploadService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@PostMapping("/upload-pdf-for-refund-data")
	public ResponseEntity<?> uploadPdfForRefundData(@RequestPart String consumerApplicationNo,
			@RequestPart(required = false) MultipartFile consumerUploadPdfForRefundTime,
			@RequestPart(required = false) MultipartFile DgmUploadPdfForRefundTime,
			@RequestPart(required = false) MultipartFile StcUploadPdfForRefundTime,
			@RequestPart(required = false) MultipartFile GmUploadPdfForRefundTime,
			@RequestPart(required = false) MultipartFile FinanceUploadPdfForRefundTime)
			throws DocumentTypeException, ConsumerApplicationDetailException {

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo).get();

		if (consumerApplicationDetail == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));

		}
		ApplicationDocument appDoc = null;

		appDoc = applicationDocumentRepository.findByconsumerApplicationDetail_consumerApplicationId(
				consumerApplicationDetail.getConsumerApplicationId());
		if (appDoc == null) {
			appDoc = new ApplicationDocument();
		}

		Upload consumerUploadPdfForRefundTimeDb = null;
		Upload DgmUploadPdfForRefundTimeDb = null;
		Upload stcUploadPdfForRefundTimeDb = null;
		Upload GmUploadPdfForRefundTimeDb = null;
		Upload FinanceUploadPdfForRefundTimeDb = null;

		if (consumerUploadPdfForRefundTime != null) {
			consumerUploadPdfForRefundTimeDb = uploadService.uploadFile(consumerUploadPdfForRefundTime,
					"CONSUMER_UPLOAD_PDF_FOR_REFUND_TIME");
			if (consumerUploadPdfForRefundTimeDb == null || consumerUploadPdfForRefundTimeDb == null) {
				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Document consumer Upload Pdf For Refund Time Not Uploaded"));
			}
			appDoc.setConsumerUploadPdfForRefundTime(consumerUploadPdfForRefundTimeDb);
		}

		if (DgmUploadPdfForRefundTime != null) {
			DgmUploadPdfForRefundTimeDb = uploadService.uploadFile(DgmUploadPdfForRefundTime,
					"DGM_UPLOAD_PDF_FOR_REFUND_TIME");
			if (DgmUploadPdfForRefundTimeDb == null || DgmUploadPdfForRefundTimeDb == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Dgm Upload Pdf For Refund Time Not Uploaded"));
			}
			appDoc.setDgmUploadPdfForRefundTime(DgmUploadPdfForRefundTimeDb);
		}

		if (StcUploadPdfForRefundTime != null) {
			stcUploadPdfForRefundTimeDb = uploadService.uploadFile(StcUploadPdfForRefundTime,
					"STC_UPLOAD_PDF_FOR_REFUND_TIME");
			if (stcUploadPdfForRefundTimeDb == null || stcUploadPdfForRefundTimeDb == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document STC Upload Pdf For Refund Time  Not Uploaded"));
			}
			appDoc.setStcUploadPdfForRefundTime(stcUploadPdfForRefundTimeDb);
		}

		if (GmUploadPdfForRefundTime != null) {
			GmUploadPdfForRefundTimeDb = uploadService.uploadFile(GmUploadPdfForRefundTime,
					"GM_UPLOAD_PDF_FOR_REFUND_TIME");
			if (GmUploadPdfForRefundTimeDb == null || GmUploadPdfForRefundTimeDb == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document GM Upload Pdf For Refund Time Not Uploaded"));
			}
			appDoc.setgMUploadPdfForRefundTime(GmUploadPdfForRefundTimeDb);
		}

		if (FinanceUploadPdfForRefundTime != null) {
			FinanceUploadPdfForRefundTimeDb = uploadService.uploadFile(FinanceUploadPdfForRefundTime,
					"FINANCE_UPLOAD_PDF_FOR_REFUND_TIME");
			if (FinanceUploadPdfForRefundTimeDb == null || FinanceUploadPdfForRefundTimeDb == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Finance Upload Pdf For Refund Time Not Uploaded"));
			}
			appDoc.setFinanceUploadPdfForRefundTime(FinanceUploadPdfForRefundTimeDb);
		}

		appDoc.setConsumerApplicationDetail(consumerApplicationDetail);
		ApplicationDocument save = applicationDocumentRepository.save(appDoc);

		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}

	@Autowired
	private SaveDataZeroOneCaseClient saveDataZeroOneCaseClient;

	@Autowired
	private ProjectItemForZeroOneCaseRepository projectItemForZeroOneCaseRepository;

	@PostMapping("/saveDataForRefundMaterialZeroOneCase")
	public ResponseEntity<?> saveDataForRefundMaterialZeroOneCase(@RequestPart String consumerApplicationNo,
			@RequestPart String erpNumber) {

		ApiResponseForZeroOrOnecaseDTO apiResponseForZeroOrOnecaseDTO = saveDataZeroOneCaseClient
				.saveDataZeroOneCaseForDemandTime(erpNumber);
		System.err.println("aaaaa : " + new Gson().toJson(apiResponseForZeroOrOnecaseDTO));

		List<ProjectItemForZeroOneCase> list = apiResponseForZeroOrOnecaseDTO.getList();
		List<ProjectItemForZeroOneCase> saveAll = projectItemForZeroOneCaseRepository.saveAll(list);

		return ResponseEntity.ok(saveAll.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
				: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(saveAll)));

	}

	@PatchMapping("/user/financeRejectRefundApplicationAtDiscomOfficer")
	public ResponseEntity<?> financeRejectRefundApplicationAtDiscomOfficer(@RequestParam String consumerApplicationNo,
			@RequestParam boolean financeAoRefundReject, @RequestParam String financeAoId,
			@RequestParam String financeRemark, @RequestParam String financeName, @RequestParam String retrunDiscomName)
			throws RefundAmountException, ConsumerApplicationDetailException {

		RefundAmount financeAoRefundRejectDB = refundAmountService.financeRejectRefundApplicationAtDiscomOfficer(
				consumerApplicationNo, financeAoRefundReject, financeAoId, financeRemark, financeName,
				retrunDiscomName);
		return Objects.isNull(financeAoRefundRejectDB)
				? ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data not updated"))
				: ResponseEntity.ok(new Response(HttpCode.UPDATED, "Data Updated Successfully",
						Arrays.asList(financeAoRefundRejectDB)));
	}

	@PatchMapping("/user/submitEFileNoByDGM")
	public ResponseEntity<?> submitEFileNoByDGM(@RequestParam String consumerApplicationNo,
			@RequestParam String eFileNo) throws ConsumerApplicationDetailException {
		RefundAmount submitEFileNoByDGM = refundAmountService.submitEFileNoByDGM(consumerApplicationNo, eFileNo);
		return ResponseEntity.ok(Objects.isNull(submitEFileNoByDGM)
				? new Response<>(HttpCode.NULL_OBJECT, "Data not saved")
				: new Response<>(HttpCode.UPDATED, "Data Updated Successfully", Arrays.asList(submitEFileNoByDGM)));

	}

	@PutMapping("/user/financeAoAcceptanceAfterErpCompletion")
	public ResponseEntity<?> financeAoAcceptanceAfterErpCompletion(@RequestParam String consumerApplicationNo,

			@RequestParam String financeErpRemark, @RequestParam String financeErpPaymentDate,
			@RequestParam String erpRefundVoucherNumber)
			throws RefundAmountException, ConsumerApplicationDetailException {

		RefundAmount financeAoRefundRejectDB = refundAmountService.financeAoAcceptanceAfterErpCompletion(
				consumerApplicationNo, financeErpRemark, financeErpPaymentDate, erpRefundVoucherNumber);
		return Objects.isNull(financeAoRefundRejectDB)
				? ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data not updated"))
				: ResponseEntity.ok(new Response(HttpCode.UPDATED, "Data Updated Successfully",
						Arrays.asList(financeAoRefundRejectDB)));
	}

	@PutMapping("/user/rejected/forward-to-finance")
	public ResponseEntity<?> forwardRejectedApplicationToFinance(@RequestParam String consumerApplicationNo)
			throws ConsumerApplicationDetailException {

		ConsumerApplicationDetail consumerApplicationData = consumerApplictionDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(
						new Response<>(HttpCode.NULL_OBJECT, "Application not found in consumerApplication detail")));

		RefundAmount byConsumerApplicationNoIsActive = refundAmountRepository
				.findByConsumerApplicationNoIsActive(consumerApplicationNo);
		if (Objects.isNull(byConsumerApplicationNoIsActive))
			throw new ConsumerApplicationDetailException(
					new Response<>(HttpCode.NULL_OBJECT, "Application no. not found in refundAmount"));
		if (byConsumerApplicationNoIsActive.getFinanceRevertToDiscom() != null) {
			if ("DGM (STC)".equals(byConsumerApplicationNoIsActive.getFinanceRevertToDiscom())) {
				byConsumerApplicationNoIsActive.setDgmStcApproval("true");
			} else if ("DGM (O&M)".equals(byConsumerApplicationNoIsActive.getFinanceRevertToDiscom())) {
				byConsumerApplicationNoIsActive.setDgmApproval("true");
			} else {
				byConsumerApplicationNoIsActive.setGmApproval("true");
			}

			refundAmountRepository.save(byConsumerApplicationNoIsActive);
			consumerApplicationData.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_FINANCE_AO_FOR_REFUND.getId()).get());
		}
		ConsumerApplicationDetail updatedData = consumerApplictionDetailRepository.save(consumerApplicationData);
		return Objects.isNull(updatedData) ? ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data not updated"))
				: ResponseEntity
						.ok(new Response(HttpCode.UPDATED, "Data Updated Successfully", Arrays.asList(updatedData)));

	}

}
