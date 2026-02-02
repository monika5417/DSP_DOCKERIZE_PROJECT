package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.AppUpdationRemark;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicantFeedbackDSP;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicantWorkIssues;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBid;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.District;
import com.mpcz.deposit_scheme.backend.api.domain.DynamicQuery;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.IndividualOrGroup;
import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.domain.LoadRequested;
import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;
import com.mpcz.deposit_scheme.backend.api.domain.OytSamagraListData;
import com.mpcz.deposit_scheme.backend.api.domain.PurposeType;
import com.mpcz.deposit_scheme.backend.api.domain.SamagraList;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.UserRole;
import com.mpcz.deposit_scheme.backend.api.dto.ApprovalDto;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationDetailsFilterDTO;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationSearch;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationUpdateDto;
import com.mpcz.deposit_scheme.backend.api.dto.MkmyIndividualDtrCapacityDto;
import com.mpcz.deposit_scheme.backend.api.dto.OAndMLoadDetailsReviseDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDocumentException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.repository.AppUpdationRemarkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicantFeedbackForWorkIssuesRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicantFeedbackRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.CadDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DistributionCenterRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DistrictRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DynamicQueryRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.IndividualOrGroupRepository;
import com.mpcz.deposit_scheme.backend.api.repository.LandAreaUnitRepository;
import com.mpcz.deposit_scheme.backend.api.repository.LoadRequestedRepository;
import com.mpcz.deposit_scheme.backend.api.repository.NatureOfWorkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.OytSamagraListDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.PurposeTypeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SamagraListRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SchemeTypeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UploadRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRoleRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorWorkOrderRepository;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.PageConsumerApplicationDetailDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.services.SSPService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.services.impl.WorkCompletionChangStautsByDgmOAndMServiceIMp;
import com.mpcz.deposit_scheme.backend.api.util.SequenceGeneratorUtil;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;
import com.mpcz.deposit_scheme.feignClient.sendDataMeterReaderVijlanceClient;
import com.mpcz.deposit_scheme.projection.CadProjection;
import com.mpcz.deposit_scheme.projection.ConsummerAppDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ConsumerApplicationDetailController", description = "Rest api for Consumer Application Detail.")
@RestController
@RequestMapping(RestApiUrl.CONSUMER_APPLICATION_DETAIL_API)
public class ConsumerApplicationDetailController {

	Logger LOG = LoggerFactory.getLogger(ConsumerApplicationDetailController.class);

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	UploadService uploadService;

	@Autowired
	ConsumerService consumerService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	private DistributionCenterRepository distributionCenterRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	LoadRequestedRepository loadRequestedRepository;

	@Autowired
	LandAreaUnitRepository landAreaUnitRepository;

	@Autowired
	NatureOfWorkRepository natureOfWorkRepository;

	@Autowired
	SchemeTypeRepository schemeTypeRepository;

	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	SMSDirectService smsDirectService;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	SamagraListRepository samagraListRepository;

	@Autowired
	UploadRepository uploadRepository;

	@Autowired
	private IndividualOrGroupRepository individualOrGroupRepository;

	@Autowired
	private OytSamagraListDataRepository oytSamagraListDataRepository;

	@Autowired
	private CadDetailsRepository cadDetailsRepository;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

	@Autowired
	private AppUpdationRemarkRepository appUpdationRemarkRepository;

	@Autowired
	private ApplicantFeedbackRepository applicantFeedbackRepository;

	@Autowired
	private ApplicantFeedbackForWorkIssuesRepository applicantFeedbackForWorkIssuesRepository;

	@Autowired
	ContractorForBidRepository contractorForBidRepository;

	@Autowired
	VendorWorkOrderRepository vendorWorkOrderRepository;

	@Autowired
	DryUtility dryUtility;

	@Autowired
	ErpEstimateAmountService erpEstimateAmountService;

	@Autowired
	private PurposeTypeRepository purposeTypeRepository;

	@Autowired
	private SSPService sSPService;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private DynamicQueryRepository dynamicQueryRepository;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//	private Response findConsumerApplicationDetailsByCityCricleAndBhopal;

	@ApiOperation(value = "Save Consumer Application Details", notes = "Pass Consumer Application Details", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(value = RestApiUrl.ADD_URL, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })

	public ResponseEntity<Response<?>> addConsumerApplicationDetail(
			@RequestPart("applicationForm") String consumerApplicationDetailFormString,
//			@RequestPart("docAadhar") MultipartFile docAadhar, 
//			@RequestPart("docPan") MultipartFile docPan,
//			@RequestPart("docEnergyBill") Optional<MultipartFile> docEnergyBillOptional,
//			@RequestPart("docRegistoryOrLease") Optional<MultipartFile> docRegistoryOrLeaseOptional,
//			@RequestPart("docResidentialProof") Optional<MultipartFile> docResidentialProofOptional,
//			@RequestPart("docBullidingPermissonOrTncpApproval") Optional<MultipartFile> docBullidingPermissonOrTncpApprovalOptional,

			@RequestPart("docRow") Optional<MultipartFile> docRowOptional,
			@RequestPart("docRegistry") Optional<MultipartFile> docRegistryOptional,
			@RequestPart("docNagarNigamPermission") Optional<MultipartFile> docNagarNigamPermissionOptional,
			@RequestPart("docDicOrGumasta") Optional<MultipartFile> docDicOrGumastaOptional,
			@RequestPart("docShapathPatra") Optional<MultipartFile> docShapathPatraOptional,
			@RequestPart("docTestReport") Optional<MultipartFile> docTestReportOptional,
			@RequestPart("docT$cpPermission") Optional<MultipartFile> docT$cpPermissionOptional,
			@RequestPart("docReraPermission") Optional<MultipartFile> docReraPermissionOptional,
			@RequestPart("docDrawingNotarized") Optional<MultipartFile> docDrawingNotarizedOptional,
			@RequestPart("docColonyPrakoshth") Optional<MultipartFile> docColonyPrakoshthOptional,
			@RequestPart("docColonyLicence") Optional<MultipartFile> docColonyLicenceOptional,
			@RequestPart("docLoadSheet") Optional<MultipartFile> docLoadSheetOptional,
			@RequestPart("docNoc") Optional<MultipartFile> docNocOptional,
			@RequestPart("docAllPaperNotarized03Set") Optional<MultipartFile> docAllPaperNotarized03SetOptional,
			@RequestPart("docCommittee") Optional<MultipartFile> docCommitteeOptional,
			@RequestPart("docDiversion") Optional<MultipartFile> docDiversionOptional,
			@RequestPart("docNazul") Optional<MultipartFile> docNazulOptional,
			@RequestPart("docMap") Optional<MultipartFile> docMapOptional,
			@RequestPart("docKhasraKhatoni") Optional<MultipartFile> docKhasraKhatoniOptional,
			@RequestPart("docMapCivilEngineer") Optional<MultipartFile> docMapCivilEngineerOptional,
			@RequestPart("docApplicationConsent") Optional<MultipartFile> docApplicationConsentOptional,
			@RequestPart("docPerforma5A") Optional<MultipartFile> docPerforma5AOptional,
			@RequestPart("docPerforma5B") Optional<MultipartFile> docPerforma5BOptional,
			@RequestPart("docPlotAreaDetailsWithOwner") Optional<MultipartFile> docPlotAreaDetailsWithOwnerOptional,
			@RequestPart("docIndividualOrGroup") Optional<MultipartFile> docIndividualOrGroupOptional,
			@RequestPart("docAdministrative") Optional<MultipartFile> docAdministrativeOptional,
			@RequestPart("docGst") Optional<MultipartFile> docGstOptional,
			@RequestPart("energyBillDoc") Optional<MultipartFile> energyBillDocOptinal,
			@RequestPart("docSamagraFile") Optional<MultipartFile> docSamagraFileOptional,

			BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest// ,
																														// Principal
																														// authentication
	) throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException,
			DocumentTypeException {

		System.out.println("addConsumerApplicationDetail !!!!!");
		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.ADD_URL
				+ " : addConsumerApplicationDetail()";
		LOG.info(method);

		System.out.println(">>>>>>>>" + httpServletRequest.getHeader("authorization"));
//		System.out.println(">>>>>>>>"+authentication.getName());

		System.out.println(
				SecurityContextHolder.getContext().getAuthentication().getName() + "----------------->>>>>>>>>");
		Response<ConsumerApplicationDetail> response = new Response<>();

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});
			response = new Response<>();
			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			throw new FormValidationException(response);
		} // end of form validation
		ConsumerApplicationDetail consumerApplicationDetail = null;
		try {
			System.err.println(new Gson().toJson(consumerApplicationDetailFormString));
			ConsumerApplicationDetailForm consumerApplicationDetailForm = ConsumerApplicationDetailForm
					.stringToJson(consumerApplicationDetailFormString);

//			MultipartFile docEnergyBill = null;
//			MultipartFile docRegistoryOrLease = null;
//			MultipartFile docResidentialProof = null;
//			MultipartFile docBullidingPermissonOrTncpApproval = null;
			MultipartFile docRow = null;
			MultipartFile docRegistry = null;
			MultipartFile docNagarNigamPermission = null;
			MultipartFile docDicOrGumasta = null;
			MultipartFile docShapathPatra = null;
			MultipartFile docTestReport = null;
			MultipartFile docT$cpPermission = null;
			MultipartFile docReraPermission = null;
			MultipartFile docDrawingNotarized = null;
			MultipartFile docColonyPrakoshth = null;
			MultipartFile docColonyLicence = null;
			MultipartFile docLoadSheet = null;
			MultipartFile docNoc = null;
			MultipartFile docAllPaperNotarized03Set = null;
			MultipartFile docCommittee = null;
			MultipartFile docDiversion = null;
			MultipartFile docNazul = null;
			MultipartFile docMap = null;
			MultipartFile docKhasraKhatoni = null;
			MultipartFile docMapCivilEngineer = null;
			MultipartFile docApplicationConsent = null;
			MultipartFile docPerforma5A = null;
			MultipartFile docPerforma5B = null;
			MultipartFile docPlotAreaDetailsWithOwner = null;
			MultipartFile docIndividualOrGroup = null;
			MultipartFile docAdministrative = null;
			MultipartFile docGst = null;
			MultipartFile energyBillDoc = null;
			MultipartFile docSamagraFile = null;

//			if (docEnergyBillOptional.isPresent()) {
//				docEnergyBill = docEnergyBillOptional.get();
//			}
//			if (docRegistoryOrLeaseOptional.isPresent()) {
//				docRegistoryOrLease = docRegistoryOrLeaseOptional.get();
//			}
//			if (docResidentialProofOptional.isPresent()) {
//				docResidentialProof = docResidentialProofOptional.get();
//			}
//			if (docBullidingPermissonOrTncpApprovalOptional.isPresent()) {
//				docBullidingPermissonOrTncpApproval = docBullidingPermissonOrTncpApprovalOptional.get();
//			}
			if (docRowOptional.isPresent()) {
				docRow = docRowOptional.get();
			}
			if (docRegistryOptional.isPresent()) {
				docRegistry = docRegistryOptional.get();
			}
			if (docNagarNigamPermissionOptional.isPresent()) {
				docNagarNigamPermission = docNagarNigamPermissionOptional.get();
			}
			if (docDicOrGumastaOptional.isPresent()) {
				docDicOrGumasta = docDicOrGumastaOptional.get();
			}
			if (docShapathPatraOptional.isPresent()) {
				docShapathPatra = docShapathPatraOptional.get();
			}
			if (docTestReportOptional.isPresent()) {
				docTestReport = docTestReportOptional.get();
			}
			if (docT$cpPermissionOptional.isPresent()) {
				docT$cpPermission = docT$cpPermissionOptional.get();
			}
			if (docReraPermissionOptional.isPresent()) {
				docReraPermission = docReraPermissionOptional.get();
			}
			if (docDrawingNotarizedOptional.isPresent()) {
				docDrawingNotarized = docDrawingNotarizedOptional.get();
			}
			if (docColonyPrakoshthOptional.isPresent()) {
				docColonyPrakoshth = docColonyPrakoshthOptional.get();
			}
			if (docColonyLicenceOptional.isPresent()) {
				docColonyLicence = docColonyLicenceOptional.get();
			}
			if (docLoadSheetOptional.isPresent()) {
				docLoadSheet = docLoadSheetOptional.get();
			}
			if (docNocOptional.isPresent()) {
				docNoc = docNocOptional.get();
			}
			if (docAllPaperNotarized03SetOptional.isPresent()) {
				docAllPaperNotarized03Set = docAllPaperNotarized03SetOptional.get();
			}
			if (docCommitteeOptional.isPresent()) {
				docCommittee = docCommitteeOptional.get();
			}
			if (docDiversionOptional.isPresent()) {
				docDiversion = docDiversionOptional.get();
			}
			if (docNazulOptional.isPresent()) {
				docNazul = docNazulOptional.get();
			}
			if (docMapOptional.isPresent()) {
				docMap = docMapOptional.get();
			}
			if (docKhasraKhatoniOptional.isPresent()) {
				docKhasraKhatoni = docKhasraKhatoniOptional.get();
			}
			if (docMapCivilEngineerOptional.isPresent()) {
				docMapCivilEngineer = docMapCivilEngineerOptional.get();
			}
			if (docApplicationConsentOptional.isPresent()) {
				docApplicationConsent = docApplicationConsentOptional.get();
			}
			if (docPerforma5AOptional.isPresent()) {
				docPerforma5A = docPerforma5AOptional.get();
			}
			if (docPerforma5BOptional.isPresent()) {
				docPerforma5B = docPerforma5BOptional.get();
			}
			if (docPlotAreaDetailsWithOwnerOptional.isPresent()) {
				docPlotAreaDetailsWithOwner = docPlotAreaDetailsWithOwnerOptional.get();
			}
			if (docIndividualOrGroupOptional.isPresent()) {
				docIndividualOrGroup = docIndividualOrGroupOptional.get();
			}
			if (docAdministrativeOptional.isPresent()) {
				docAdministrative = docAdministrativeOptional.get();
			}
			if (docGstOptional.isPresent()) {
				docGst = docGstOptional.get();
			}
			if (energyBillDocOptinal.isPresent()) {
				energyBillDoc = energyBillDocOptinal.get();
			}
			if (docSamagraFileOptional.isPresent()) {
				docSamagraFile = docSamagraFileOptional.get();
			}

			consumerApplicationDetail = consumerApplicationDetailService.saveConsumerApplicationDetail(
					consumerApplicationDetailForm, /*
													 * docAadhar, docPan, docEnergyBill, docRegistoryOrLease,
													 * docResidentialProof, docBullidingPermissonOrTncpApproval,
													 */ docRow, docRegistry, docNagarNigamPermission, docDicOrGumasta,
					docShapathPatra, docTestReport, docT$cpPermission, docReraPermission, docDrawingNotarized,
					docColonyPrakoshth, docColonyLicence, docLoadSheet, docNoc, docAllPaperNotarized03Set, docCommittee,
					docDiversion, docNazul, docMap, docKhasraKhatoni, docMapCivilEngineer, docApplicationConsent,
					docPerforma5A, docPerforma5B, docPlotAreaDetailsWithOwner, docIndividualOrGroup, docAdministrative,
					docGst, energyBillDoc, docSamagraFile);
			System.err.println(">>>>>>>>>>>>>" + consumerApplicationDetail);

		} catch (Exception e) {
			e.printStackTrace();
			response.setList(null);
			response.setCode("404");
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}

		response.setList(Arrays.asList(consumerApplicationDetail));
		response.setCode(HttpCode.CREATED);
		response.setMessage("Record Inserted Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve Consumer Application List with Pagination", notes = "Fetch all Consumer Application List", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_PAGINATE_URL)
	public ResponseEntity<Response<?>> getAllConsumerApplicationPagination(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException {

		System.out.println("getAllConsumerApplicationPagination !!!!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.GET_ALL_PAGINATE_URL
				+ " : getAllConsumerApplicationPagination()";
		LOG.info(method);

		Response<PageConsumerApplicationDetailDTO> response = new Response<>();

		String consumerLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println(consumerLoginId);
		ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();

		filterDTO.setConsumerLoginId(consumerLoginId);

		response = consumerApplicationDetailService.findAllConsumerApplicationDetailPagination(page, size, filterDTO);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve specific Consumer Application Detail", notes = "Pass Consumer Application  Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_URL)
	public ResponseEntity<Response<?>> retrieveSpecificConsumerApplicationDetail(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException {

		System.out.println("retrieveSpecificConsumerApplicationDetail !!!!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.GET_URL
				+ " : retrieveSpecificConsumerApplicationDetail()";
		LOG.info(method);
		Response<ConsumerApplicationDetail> response = new Response();

		ConsumerApplicationDetail ConsumerApplicationDetail = consumerApplicationDetailService
				.findByConsumerApplicationId(id);

		System.err.println(new Gson().toJson(ConsumerApplicationDetail));

		response.setList(Arrays.asList(ConsumerApplicationDetail));
		response.setCode(HttpCode.OK);
		response.setMessage("Records retrieved Successfully !");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve specific Consumer", notes = "Pass Consumer Login Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_LOGIN_URL)
	public ResponseEntity<Response<?>> retrieveSpecificConsumer(@PathVariable String loginId,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerException {

		System.out.println("retrieveSpecificConsumer method is calling !!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.GET_LOGIN_URL
				+ " : retrieveSpecificConsumer()";
		LOG.info(method);

		Response<Consumer> response = new Response<>();

		Consumer consumer = consumerService.findByConsumerLoginId(loginId);

		response.setList(Arrays.asList(consumer));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Update Consumer Application Details", notes = "Pass Consumer Application Details", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PutMapping(value = RestApiUrl.UPDATE_URL, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })

	public ResponseEntity<Response<?>> updateConsumerApplicationDetail(
			@RequestPart("applicationForm") String consumerApplicationDetailFormString,
//				@RequestPart("docAadhar") MultipartFile docAadhar, 
//				@RequestPart("docPan") MultipartFile docPan,
//				@RequestPart("docEnergyBill") Optional<MultipartFile> docEnergyBillOptional,
//				@RequestPart("docRegistoryOrLease") Optional<MultipartFile> docRegistoryOrLeaseOptional,
//				@RequestPart("docResidentialProof") Optional<MultipartFile> docResidentialProofOptional,
//				@RequestPart("docBullidingPermissonOrTncpApproval") Optional<MultipartFile> docBullidingPermissonOrTncpApprovalOptional,

			@RequestPart("docRow") Optional<MultipartFile> docRowOptional,
			@RequestPart("docRegistry") Optional<MultipartFile> docRegistryOptional,
			@RequestPart("docNagarNigamPermission") Optional<MultipartFile> docNagarNigamPermissionOptional,
			@RequestPart("docDicOrGumasta") Optional<MultipartFile> docDicOrGumastaOptional,
			@RequestPart("docShapathPatra") Optional<MultipartFile> docShapathPatraOptional,
			@RequestPart("docTestReport") Optional<MultipartFile> docTestReportOptional,
			@RequestPart("docT$cpPermission") Optional<MultipartFile> docT$cpPermissionOptional,
			@RequestPart("docReraPermission") Optional<MultipartFile> docReraPermissionOptional,
			@RequestPart("docDrawingNotarized") Optional<MultipartFile> docDrawingNotarizedOptional,
			@RequestPart("docColonyPrakoshth") Optional<MultipartFile> docColonyPrakoshthOptional,
			@RequestPart("docColonyLicence") Optional<MultipartFile> docColonyLicenceOptional,
			@RequestPart("docLoadSheet") Optional<MultipartFile> docLoadSheetOptional,
			@RequestPart("docNoc") Optional<MultipartFile> docNocOptional,
			@RequestPart("docAllPaperNotarized03Set") Optional<MultipartFile> docAllPaperNotarized03SetOptional,
			@RequestPart("docCommittee") Optional<MultipartFile> docCommitteeOptional,
			@RequestPart("docDiversion") Optional<MultipartFile> docDiversionOptional,
			@RequestPart("docNazul") Optional<MultipartFile> docNazulOptional,
			@RequestPart("docMap") Optional<MultipartFile> docMapOptional,
			@RequestPart("docKhasraKhatoni") Optional<MultipartFile> docKhasraKhatoniOptional,
			@RequestPart("docMapCivilEngineer") Optional<MultipartFile> docMapCivilEngineerOptional,
			@RequestPart("docApplicationConsent") Optional<MultipartFile> docApplicationConsentOptional,
			@RequestPart("docPerforma5A") Optional<MultipartFile> docPerforma5AOptional,
			@RequestPart("docPerforma5B") Optional<MultipartFile> docPerforma5BOptional,
			@RequestPart("docPlotAreaDetailsWithOwner") Optional<MultipartFile> docPlotAreaDetailsWithOwnerOptional,
			@RequestPart("docIndividualOrGroup") Optional<MultipartFile> docIndividualOrGroupOptional,
			@RequestPart("docAdministrative") Optional<MultipartFile> docAdministrativeOptional,
			@RequestPart("docGst") Optional<MultipartFile> docGstOptional,
			@RequestPart("consentletter") Optional<MultipartFile> consentletterOptional, @PathVariable("id") Long id,

			BindingResult bindingResult, HttpServletResponse httpServletResponse) throws FormValidationException,
			InvalidAuthenticationException, ConsumerApplicationDetailException, DocumentTypeException {

		System.out.println("updateConsumerApplicationDetail !!!!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.UPDATE_URL
				+ " : updateConsumerApplicationDetail()";
		LOG.info(method);

		Response<ConsumerApplicationDetail> response = new Response<>();

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});
			response = new Response<>();
			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			throw new FormValidationException(response);
		} // end of form validation
		ConsumerApplicationDetail consumerApplicationDetail = null;
		try {
			ConsumerApplicationDetailForm consumerApplicationDetailForm = ConsumerApplicationDetailForm
					.stringToJson(consumerApplicationDetailFormString);

//				MultipartFile docEnergyBill = null;
//				MultipartFile docRegistoryOrLease = null;
//				MultipartFile docResidentialProof = null;
//				MultipartFile docBullidingPermissonOrTncpApproval = null;
			MultipartFile docRow = null;
			MultipartFile docRegistry = null;
			MultipartFile docNagarNigamPermission = null;
			MultipartFile docDicOrGumasta = null;
			MultipartFile docShapathPatra = null;
			MultipartFile docTestReport = null;
			MultipartFile docT$cpPermission = null;
			MultipartFile docReraPermission = null;
			MultipartFile docDrawingNotarized = null;
			MultipartFile docColonyPrakoshth = null;
			MultipartFile docColonyLicence = null;
			MultipartFile docLoadSheet = null;
			MultipartFile docNoc = null;
			MultipartFile docAllPaperNotarized03Set = null;
			MultipartFile docCommittee = null;
			MultipartFile docDiversion = null;
			MultipartFile docNazul = null;
			MultipartFile docMap = null;
			MultipartFile docKhasraKhatoni = null;
			MultipartFile docMapCivilEngineer = null;
			MultipartFile docApplicationConsent = null;
			MultipartFile docPerforma5A = null;
			MultipartFile docPerforma5B = null;
			MultipartFile docPlotAreaDetailsWithOwner = null;
			MultipartFile docIndividualOrGroup = null;
			MultipartFile docAdministrative = null;
			MultipartFile docGst = null;
			MultipartFile consentletter = null;

//				if (docEnergyBillOptional.isPresent()) {
//					docEnergyBill = docEnergyBillOptional.get();
//				}
//				if (docRegistoryOrLeaseOptional.isPresent()) {
//					docRegistoryOrLease = docRegistoryOrLeaseOptional.get();
//				}
//				if (docResidentialProofOptional.isPresent()) {
//					docResidentialProof = docResidentialProofOptional.get();
//				}
//				if (docBullidingPermissonOrTncpApprovalOptional.isPresent()) {
//					docBullidingPermissonOrTncpApproval = docBullidingPermissonOrTncpApprovalOptional.get();
//				}
			if (docRowOptional.isPresent()) {
				docRow = docRowOptional.get();
			}
			if (docRegistryOptional.isPresent()) {
				docRegistry = docRegistryOptional.get();
			}
			if (docNagarNigamPermissionOptional.isPresent()) {
				docNagarNigamPermission = docNagarNigamPermissionOptional.get();
			}
			if (docDicOrGumastaOptional.isPresent()) {
				docDicOrGumasta = docDicOrGumastaOptional.get();
			}
			if (docShapathPatraOptional.isPresent()) {
				docShapathPatra = docShapathPatraOptional.get();
			}
			if (docTestReportOptional.isPresent()) {
				docTestReport = docTestReportOptional.get();
			}
			if (docT$cpPermissionOptional.isPresent()) {
				docT$cpPermission = docT$cpPermissionOptional.get();
			}
			if (docReraPermissionOptional.isPresent()) {
				docReraPermission = docReraPermissionOptional.get();
			}
			if (docDrawingNotarizedOptional.isPresent()) {
				docDrawingNotarized = docDrawingNotarizedOptional.get();
			}
			if (docColonyPrakoshthOptional.isPresent()) {
				docColonyPrakoshth = docColonyPrakoshthOptional.get();
			}
			if (docColonyLicenceOptional.isPresent()) {
				docColonyLicence = docColonyLicenceOptional.get();
			}
			if (docLoadSheetOptional.isPresent()) {
				docLoadSheet = docLoadSheetOptional.get();
			}
			if (docNocOptional.isPresent()) {
				docNoc = docNocOptional.get();
			}
			if (docAllPaperNotarized03SetOptional.isPresent()) {
				docAllPaperNotarized03Set = docAllPaperNotarized03SetOptional.get();
			}
			if (docCommitteeOptional.isPresent()) {
				docCommittee = docCommitteeOptional.get();
			}
			if (docDiversionOptional.isPresent()) {
				docDiversion = docDiversionOptional.get();
			}
			if (docNazulOptional.isPresent()) {
				docNazul = docNazulOptional.get();
			}
			if (docMapOptional.isPresent()) {
				docMap = docMapOptional.get();
			}
			if (docKhasraKhatoniOptional.isPresent()) {
				docKhasraKhatoni = docKhasraKhatoniOptional.get();
			}
			if (docMapCivilEngineerOptional.isPresent()) {
				docMapCivilEngineer = docMapCivilEngineerOptional.get();
			}
			if (docApplicationConsentOptional.isPresent()) {
				docApplicationConsent = docApplicationConsentOptional.get();
			}
			if (docPerforma5AOptional.isPresent()) {
				docPerforma5A = docPerforma5AOptional.get();
			}
			if (docPerforma5BOptional.isPresent()) {
				docPerforma5B = docPerforma5BOptional.get();
			}
			if (docPlotAreaDetailsWithOwnerOptional.isPresent()) {
				docPlotAreaDetailsWithOwner = docPlotAreaDetailsWithOwnerOptional.get();
			}
			if (docIndividualOrGroupOptional.isPresent()) {
				docIndividualOrGroup = docIndividualOrGroupOptional.get();
			}
			if (docAdministrativeOptional.isPresent()) {
				docAdministrative = docAdministrativeOptional.get();

			}
			if (docGstOptional.isPresent()) {
				docGst = docGstOptional.get();
			}
			if (consentletterOptional.isPresent()) {
				consentletter = consentletterOptional.get();
			}
			consumerApplicationDetail = consumerApplicationDetailService.updateConsumerApplicationDetail(
					consumerApplicationDetailForm, /*
													 * docAadhar, docPan, docEnergyBill, docRegistoryOrLease,
													 * docResidentialProof, docBullidingPermissonOrTncpApproval,
													 */ docRow, docRegistry, docNagarNigamPermission, docDicOrGumasta,
					docShapathPatra, docTestReport, docT$cpPermission, docReraPermission, docDrawingNotarized,
					docColonyPrakoshth, docColonyLicence, docLoadSheet, docNoc, docAllPaperNotarized03Set, docCommittee,
					docDiversion, docNazul, docMap, docKhasraKhatoni, docMapCivilEngineer, docApplicationConsent,
					docPerforma5A, docPerforma5B, docPlotAreaDetailsWithOwner, docIndividualOrGroup, docAdministrative,
					id, docGst, consentletter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setList(Arrays.asList(consumerApplicationDetail));
		response.setCode(HttpCode.UPDATED);
		response.setMessage("Record Updated Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

//		O&M  dikhana hai  bhopal or gwalior ka
	@ApiOperation(value = "Retrieve data based on LT is Not null city cricle bhopal and cricle gwalior", notes = "no value pass", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.CONSUMER_APPLICATION_DETAILS_BASED_ON_CITY_CRICLE_BHOPAL_AND_GWALIOR_O_AND_M)
	public Response<?> retrieveSpecificConsumerBasedOnCityCricleBhopalAndGwalior(@PathVariable long circleid) {

		System.out.println("retrieveSpecificConsumerBasedOnCityCricleBhopalAndGwalior method is calling !!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API
				+ RestApiUrl.CONSUMER_APPLICATION_DETAILS_BASED_ON_CITY_CRICLE_BHOPAL_AND_GWALIOR_O_AND_M
				+ " : retrieveSpecificConsumer()";
		LOG.info(method);

		return consumerApplicationDetailService.findConsumerApplicationDetailsByCityCricleAndBhopal(circleid);

//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findConsumerApplicationDetailsByCityCricleAndBhopal);

	}

//		HTM ko dikhna hai
	@ApiOperation(value = "Retrieve data based LT is null  city cricle bhopal and cricle gwalior Based On Null Value", notes = "no value pass", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.CONSUMER_APPLICATION_DETAILS_BASED_ON_CITY_CRICLE_BHOPAL_AND_GWALIOR_BASED_ON_LT_NULL_VALVE_htm)
	public Response<?> retrieveSpecificConsumerBasedOnCityCricleBhopalAndGwaliorBasedOnNullValue(
			@PathVariable long circleid) {

		System.out.println(
				"retrieveSpecificConsumerBasedOnCityCricleBhopalAndGwaliorBasedOnNullValue method is calling !!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API
				+ RestApiUrl.CONSUMER_APPLICATION_DETAILS_BASED_ON_CITY_CRICLE_BHOPAL_AND_GWALIOR_BASED_ON_LT_NULL_VALVE_htm
				+ " : retrieveSpecificConsumer()";
		LOG.info(method);

		return consumerApplicationDetailService
				.findConsumerApplicationDetailsByCityCricleAndBhopalBasedOnNullValue(circleid);

//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findConsumerApplicationDetailsByCityCricleAndBhopal);

	}

	// Monika code start

	@ApiOperation(value = "Updating the application Rejected By GM", notes = "no value pass", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PutMapping("/rejectApplicationByGM")
	public Response<?> rejectApplicationByGM(@RequestParam String consumerApplicationNumber,
			@RequestParam String rejectedApplicationDate, @RequestParam String rejectedRemark,
			@RequestParam String rejectApplicationGMName, @RequestParam Boolean gmAccepted) {

		System.out.println("rejectApplicationByGM method is calling !!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + "rejectApplication"
				+ " : retrieveSpecificConsumer()";
		LOG.info(method);

		return consumerApplicationDetailService.rejectApplication(consumerApplicationNumber, rejectedApplicationDate,
				rejectedRemark, rejectApplicationGMName, gmAccepted);

//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findConsumerApplicationDetailsByCityCricleAndBhopal);

	}

	@ApiOperation(value = "Update JE Load Application", notes = "no value pass", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PutMapping("/jeLoad")
	public Response<?> jeLoadApplication(@RequestParam String consumerApplicationNumber, @RequestParam String jeLoad,
			@RequestParam String jeLoadUnitKwYaKva, @RequestParam int goodMaterialOrNot) {

		System.out.println("jeLoadApplication method is calling !!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + "rejectApplication"
				+ " : retrieveSpecificConsumer()";
		LOG.info(method);

		return consumerApplicationDetailService.jeLoadApplication(consumerApplicationNumber, jeLoad, jeLoadUnitKwYaKva,
				goodMaterialOrNot);

//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findConsumerApplicationDetailsByCityCricleAndBhopal);

	}

//	@ApiOperation(value = "Get chalan with POS", notes = "no value pass", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@GetMapping("/getChalan")
//	public ResponseEntity<?> getChalan(@RequestParam String consumerApplicationNumber)
//			throws ConsumerApplicationDetailException, SchemeTypeException, Exception {
//
//		System.out.println(
//				"retrieveSpecificConsumerBasedOnCityCricleBhopalAndGwaliorBasedOnNullValue method is calling !!!");
//
//		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + "rejectApplication"
//				+ " : retrieveSpecificConsumer()";
//		LOG.info(method);
//
//		ErpEstimateCalculatedDto consumerChalan = consumerApplicationDetailService
//				.getConsumerChalan(consumerApplicationNumber);
//		if (consumerChalan == null) {
//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON)
//					.body("massage: " + "No Payment Pending");
//
//		} else {
//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(consumerChalan);
//		}
//	}

	@GetMapping("/getConsumerApplicationDetailsBySamagraId/{id}")
	public ResponseEntity<?> getConsumerApplicationDetailsBySamagraId(@PathVariable("id") String samagraId) {
		Response response = new Response();

		ConsumerApplicationDetail consumerApplicationDetailsBySamagraId = consumerApplicationDetailRepository
				.getConsumerApplicationDetailsBySamagraId(samagraId);
		if (consumerApplicationDetailsBySamagraId == null) {
			response.setCode("404");
			response.setMessage("Application not found on this samagra id");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}
		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(consumerApplicationDetailsBySamagraId));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	// this api for submit of MKMY

	@PostMapping("/consumerApplicationUpdateByMKMY")
	public ResponseEntity<?> consumerApplicationUpdateByMKMY(
			@RequestPart("consumerApplicattionMmky") String consumerApplicationUpdateDto,
			@RequestPart("docKhasraKhatoni") Optional<MultipartFile> docKhasraKhatoniOptional,
			@RequestPart("docSamagraFile") Optional<MultipartFile> docSamagraFileOptional) {
		Response response = new Response();

//		Long consumerApplicationId = consumerApplicationUpdateDto.getConsumerApplicationId();
//		if(consumerApplicationId==null ) {
//			response.setCode("400");
//			response.setMessage("Consumer application id should not be null");
//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//		}

//	

		try {
			ConsumerApplicationUpdateDto consumerApplicationUpdate = ConsumerApplicationUpdateDto
					.stringToJson(consumerApplicationUpdateDto);

			// code took from controller

			Map<String, Object> map = new HashMap<String, Object>();

			MultipartFile docKhasraKhatoni = null;

			MultipartFile docSamagraFile = null;

			if (docKhasraKhatoniOptional.isPresent()) {
				docKhasraKhatoni = docKhasraKhatoniOptional.get();
			}

			if (docSamagraFileOptional.isPresent()) {
				docSamagraFile = docSamagraFileOptional.get();
			}

			// code took from serviceImpl

			ConsumerApplicationUpdateDto consumerApplicationUpdateDtos = new ConsumerApplicationUpdateDto();

			ApplicationDocument applicationDocument = new ApplicationDocument();

			Upload khasraKhatoniUpload = null;
			Upload SamagraFileUpload = null;

			if ((consumerApplicationUpdate.getNatureOfWorkTypeId().compareTo(8l)) == 0) {

				if (docKhasraKhatoni == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Doc Khasar-Khatoni document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (docSamagraFile == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Doc Samagra File document");
					throw new ConsumerApplicationDetailException(response);
				}
			}

			if (docKhasraKhatoni != null) {
				khasraKhatoniUpload = uploadService.uploadFile(docKhasraKhatoni, "KHASRA_KHATONI");
				if (khasraKhatoniUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Khasar-Khatoni not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocKhasraKhatoni(khasraKhatoniUpload);
			}

			if (docSamagraFile != null) {
				SamagraFileUpload = uploadService.uploadFile(docSamagraFile, "SAMAGRA_FILE");
				if (SamagraFileUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Samagra File not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocSamagraFile(SamagraFileUpload);
			}

			if (consumerApplicationUpdate.getKhasra() != null) {
				consumerApplicationUpdateDtos.setKhasra(consumerApplicationUpdate.getKhasra());
			}

			ApplicationStatus app = null;
			District district = null;
			SchemeType schemeType = null;
			LoadRequested loadRequested2 = null;
			LandAreaUnit landAreaUnit = null;
			NatureOfWork natureOfWork = null;
			String maxApplicationNo = null;
			DistributionCenter dis = null;

			if (consumerApplicationUpdate.getAddress() == null || consumerApplicationUpdate.getAddress().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Address");
				throw new ConsumerApplicationDetailException(response);

			}

			if (consumerApplicationUpdate.getArea() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Area");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getConsumerName() == null
					|| consumerApplicationUpdate.getConsumerName().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Consumer Name");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getDcId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Distribution Center");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getDistrictId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill District");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getGuardianName() == null
					|| consumerApplicationUpdate.getGuardianName().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Guardian Name");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getKhasra() == null || consumerApplicationUpdate.getKhasra().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Khasra");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getLandAreaUnitId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Land Area Unit Id");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getLoadRequestedId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Load Requested Id");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getNatureOfWorkTypeId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Nature Of Work Type ");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getPinCode() == null || consumerApplicationUpdate.getPinCode().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Pincode");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getSchemeTypeId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Scheme Type ");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getShortDescriptionOfWork() == null
					|| consumerApplicationUpdate.getShortDescriptionOfWork().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill ShortDescription Of Work");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getCastCategory() == null
					|| consumerApplicationUpdate.getCastCategory().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Caste Category");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getMobilNo() == null || consumerApplicationUpdate.getMobilNo().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Mobile No.");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getSamgraId() == null || consumerApplicationUpdate.getSamgraId().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Samgra Id");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getLoadRequested() == null
					|| consumerApplicationUpdate.getLoadRequested().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Load Requested Bhar");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getAadharNo() == null || consumerApplicationUpdate.getAadharNo().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Aadhar No.");
				throw new ConsumerApplicationDetailException(response);
			}
			if (consumerApplicationUpdate.getIndividualOrGroupId() == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill individual or group");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getAvedakKaPrakar() == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Fill Avedan Ka Prakar");
				throw new ConsumerApplicationDetailException(response);
			}

			String address = consumerApplicationUpdate.getAddress();
			Double area = consumerApplicationUpdate.getArea();
			String consumerName = consumerApplicationUpdate.getConsumerName();
			String avedakKaPrakar = consumerApplicationUpdate.getAvedakKaPrakar();
			String avedakRemark = consumerApplicationUpdate.getAvedakRemark();
			Long individualOrGroupId = consumerApplicationUpdate.getIndividualOrGroupId();
			Optional<IndividualOrGroup> findById2 = individualOrGroupRepository.findById(individualOrGroupId);
			IndividualOrGroup individualOrGroup = findById2.get();

			Long dcId = consumerApplicationUpdate.getDcId();
			if (dcId != null) {
				Optional<DistributionCenter> dis1 = distributionCenterRepository.findById(dcId);
				dis = dis1.get();
			} else {
				response.setCode("404");
				response.setMessage("select DC ID");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			Long districtId = consumerApplicationUpdate.getDistrictId();
			if (districtId != null) {
				Optional<District> findById = districtRepository.findById(districtId);
				district = findById.get();
			} else {
				response.setCode("404");
				response.setMessage("select district");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

			}
			String guardianName = consumerApplicationUpdate.getGuardianName();
			String khasra = consumerApplicationUpdate.getKhasra();
//		String khatoni = consumerApplicationUpdateDto.getKhatoni();

			Long landAreaUnitId = consumerApplicationUpdate.getLandAreaUnitId();
			if (landAreaUnitId != null) {
				Optional<LandAreaUnit> landArea = landAreaUnitRepository.findById(landAreaUnitId);
				landAreaUnit = landArea.get();
			} else {
				response.setCode("404");
				response.setMessage("select Hectare And Square feet ");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			Long loadRequestedId = consumerApplicationUpdate.getLoadRequestedId();
			if (loadRequestedId != null) {
				Optional<LoadRequested> loadRequested = loadRequestedRepository.findById(loadRequestedId);
				loadRequested2 = loadRequested.get();
			} else {
				response.setCode("404");
				response.setMessage("select loadRequested ");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

			}

			Long natureOfWorkTypeId = consumerApplicationUpdate.getNatureOfWorkTypeId();
			if (natureOfWorkTypeId != null) {
				Optional<NatureOfWork> findById = natureOfWorkRepository.findById(natureOfWorkTypeId);
				natureOfWork = findById.get();
			}
			String pinCode = consumerApplicationUpdate.getPinCode();

			Long schemeTypeId = consumerApplicationUpdate.getSchemeTypeId();
			if (schemeTypeId != null) {
				Optional<SchemeType> findById = schemeTypeRepository.findById(schemeTypeId);
				schemeType = findById.get();
			} else {
				response.setCode("404");
				response.setMessage("select schemeType ");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

			}

			String shortDescriptionOfWork = consumerApplicationUpdate.getShortDescriptionOfWork();
			String castCategory = consumerApplicationUpdate.getCastCategory();
			String mobileNumber = consumerApplicationUpdate.getMobilNo();
			String samgraId = consumerApplicationUpdate.getSamgraId();
			String aadharNo = consumerApplicationUpdate.getAadharNo();
			ConsumerApplicationDetail consumerApplication = new ConsumerApplicationDetail();

			consumerApplication.setAddress(address);
			consumerApplication.setArea(area);
			consumerApplication.setConsumerName(consumerName);
			consumerApplication.setApplicationStatus(app);
			consumerApplication.setDistrict(district);
			consumerApplication.setGuardianName(guardianName);
			consumerApplication.setKhasra(khasra);
			consumerApplication.setLandAreaUnit(landAreaUnit);
			consumerApplication.setLoadRequestedId(loadRequested2);
			consumerApplication.setNatureOfWorkType(natureOfWork);
			consumerApplication.setPincode(pinCode);
			consumerApplication.setSchemeType(schemeType);
			consumerApplication.setShortDescriptionOfWork(shortDescriptionOfWork);
			consumerApplication.setCastCategory(castCategory);
			consumerApplication.setConsumerphonNumber(mobileNumber);
			consumerApplication.setSamagraId(samgraId);
			consumerApplication.setLoadRequested(consumerApplicationUpdate.getLoadRequested());
			consumerApplication.setDistributionCenter(dis);
			consumerApplication.setAadharNo(aadharNo);
			consumerApplication.setIndividualOrGroup(individualOrGroup);
			consumerApplication.setActive(false);
			consumerApplication.setDeleted(true);
			consumerApplication.setAvedakKaPrakar(avedakKaPrakar);
			consumerApplication.setAvedakRemark(avedakRemark);
			if (schemeType.getSchemeTypeId() == 1) {
				maxApplicationNo = consumerApplicationDetailRepository
						.findMaxApplicationNo(SequenceGeneratorUtil.getConsumerApplicationNoFormat(schemeType));
			} else if (schemeType.getSchemeTypeId() == 2) {
				maxApplicationNo = consumerApplicationDetailRepository
						.findMaxApplicationNo1(SequenceGeneratorUtil.getConsumerApplicationNoFormat(schemeType));
			} else {
				maxApplicationNo = consumerApplicationDetailRepository
						.findMaxApplicationNo2(SequenceGeneratorUtil.getConsumerApplicationNoFormat(schemeType));
			}
			String ApplicationNo = "";

//		if (maxApplicationNo != null && !maxApplicationNo.equals("null")) {
//			ApplicationNo = SequenceGeneratorUtil.getApplicationNo(maxApplicationNo, schemeType);
//		} else {
//			ApplicationNo = SequenceGeneratorUtil.getApplicationNo("", schemeType);
//		}

			String applicationfirst = maxApplicationNo.substring(0, 2);
//			String applicationSecond = maxApplicationNo.substring(2);
//			Long num1 = Long.parseLong(applicationSecond);
//			Long incrementedNum = num1 + 1;
//			String applictionfullname = applicationfirst + incrementedNum;

			long uniqueCurrentTimeMS = uniqueCurrentTimeMS();
			String applictionfullname = applicationfirst + uniqueCurrentTimeMS;

			consumerApplication.setConsumerApplicationNo(applictionfullname.toUpperCase().trim());

			Consumer consumer = null;
			consumer = consumerService.getCurrentLoginConsumerByLoginId();
			if (consumer != null) {
				consumerApplication.setConsumers(consumer);
			}

			consumerApplication.setApplicationStatus(applicationStatusRepository.findById(4l).get());
			consumerApplication.setSamagraId(consumerApplicationUpdate.getSamgraId());
			ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(consumerApplication);

			Optional<Upload> findById = uploadRepository.findById(SamagraFileUpload.getUploadId());
			Upload upload1 = findById.get();
			upload1.setConsuemrApplicatonID(save.getConsumerApplicationId());

			Optional<Upload> findById1 = uploadRepository.findById(khasraKhatoniUpload.getUploadId());
			Upload upload2 = findById1.get();
			upload2.setConsuemrApplicatonID(save.getConsumerApplicationId());
			applicationDocument.setConsumerApplicationDetail(save);
			applicationDocumentRepository.save(applicationDocument);
			if (consumerApplicationUpdate.getIndividualOrGroupId() == 2l) {

				List<SamagraList> samagraLists = new ArrayList<>();

				consumerApplicationUpdate.getSamagraListDto().forEach(a -> {

					SamagraList samagraList = new SamagraList();
					samagraList.setConsumerApplicationNo(save.getConsumerApplicationNo());
					samagraList.setConsumerName(a.getConsumerName());
					samagraList.setGuardianName(a.getGuardianName());
					samagraList.setKhasraNo(a.getKhasraNo());
					samagraList.setSamagraId(a.getSamagraId());
					samagraList.setLoadRequested(a.getLoadRequested());
					samagraList.setCreatedDate(LocalDateTime.now().toString());
					samagraLists.add(samagraList);

				});

				List<SamagraList> saveAll = samagraListRepository.saveAll(samagraLists);
				map.put("listData", saveAll);
			}

			map.put("formData", save);

			final SMSRequest smsRequest = new SMSRequest();
			smsRequest.setText(MessageFormat.format(messageProperties.getSendsmsConsumerMsgfromMMKYsms(),
					new Object[] { save.getConsumerApplicationNo() }));
			smsRequest.setMobileNo(mobileNumber);
			smsRequest.setTemplateId(messageProperties.getTamplateIdsendsmsConsumerMsgfromMMKY());
//			code added by monika on 30-August-2024
			smsRequest.setHinglish(1L);
//			code end

			try {
				smsDirectService.sendMessage(smsRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}

			response.setCode("200");
			response.setMessage("Application update successfully");
			response.setList(Arrays.asList(map));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getConsumerApplicationDetailsByDistrictId/{id}")
	public ResponseEntity<?> getConsumerApplicationDetailsByDistrictId(@PathVariable("id") String districtId) {
		Response response = new Response();

		List<ConsumerApplicationDetail> consumerApplicationDetailsByDistrictId = consumerApplicationDetailRepository
				.getConsumerApplicationDetailsByDistrictId(districtId);
		if (consumerApplicationDetailsByDistrictId == null) {
			response.setCode("404");
			response.setMessage("Application not found on this district id");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(consumerApplicationDetailsByDistrictId));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getConsumerApplicationDetailsByConsumerApplicationNo/{consumerApplicationNo}")
	public ResponseEntity<?> getConsumerApplicationDetailsByConsumerApplicationNo(
			@PathVariable("consumerApplicationNo") String consumerApplicationNo) {
		Response response = new Response();

		ConsumerApplicationDetail consumerApplicationDetailsByConsumerAppNO = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (consumerApplicationDetailsByConsumerAppNO == null) {
			response.setCode("404");
			response.setMessage("Application not found on this district id");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}

		List<Upload> findByConsuemrApplicatonID = uploadRepository
				.findByConsuemrApplicatonID(consumerApplicationDetailsByConsumerAppNO.getConsumerApplicationId());

		findByConsuemrApplicatonID.forEach(a -> {

			if (a.getDocumentType().getDocumentTypeId() == 44L) {

				consumerApplicationDetailsByConsumerAppNO.setRejectionProrposalFilePath(a.getDocumentPath());
			}
		});
		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(consumerApplicationDetailsByConsumerAppNO));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@PutMapping("/consumerApplicationUpdateByApplication")
	public ResponseEntity<?> consumerApplicationUpdateByApplication(
			@RequestPart("consumerApplicattionMmky") String consumerApplicationUpdateDto,
			@RequestPart("docKhasraKhatoni") Optional<MultipartFile> docKhasraKhatoniOptional,
			@RequestPart("docSamagraFile") Optional<MultipartFile> docSamagraFileOptional) {
		Response response = new Response();

		try {
			ConsumerApplicationUpdateDto consumerApplicationUpdate = ConsumerApplicationUpdateDto
					.stringToJson(consumerApplicationUpdateDto);

			ConsumerApplicationDetail consumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationUpdate.getConsumerApplicationNo());
			// code took from controller

			Map<String, Object> map = new HashMap<String, Object>();

			MultipartFile docKhasraKhatoni = null;

			MultipartFile docSamagraFile = null;

			if (docKhasraKhatoniOptional.isPresent()) {
				docKhasraKhatoni = docKhasraKhatoniOptional.get();
			}

			if (docSamagraFileOptional.isPresent()) {
				docSamagraFile = docSamagraFileOptional.get();
			}

			ApplicationDocument applicationDocument = new ApplicationDocument();

			Upload khasraKhatoniUpload = null;
			Upload SamagraFileUpload = null;

			if (docKhasraKhatoni != null) {
				khasraKhatoniUpload = uploadService.uploadFile(docKhasraKhatoni, "KHASRA_KHATONI");
				if (khasraKhatoniUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Khasar-Khatoni not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocKhasraKhatoni(khasraKhatoniUpload);
			}

			if (docSamagraFile != null) {
				SamagraFileUpload = uploadService.uploadFile(docSamagraFile, "SAMAGRA_FILE");
				if (SamagraFileUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Samagra File not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocSamagraFile(SamagraFileUpload);
			}

			DistributionCenter dis = null;
			if (consumerApplicationUpdate.getDcId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Distribution Center");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getGuardianName() == null
					|| consumerApplicationUpdate.getGuardianName().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Guardian Name");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getCastCategory() == null
					|| consumerApplicationUpdate.getCastCategory().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Caste Category");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getMobilNo() == null || consumerApplicationUpdate.getMobilNo().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Mobile No.");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getSamgraId() == null || consumerApplicationUpdate.getSamgraId().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Samgra Id");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getAadharNo() == null || consumerApplicationUpdate.getAadharNo().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Aadhar No.");
				throw new ConsumerApplicationDetailException(response);
			}

			Long dcId = consumerApplicationUpdate.getDcId();
			if (dcId != null) {
				Optional<DistributionCenter> dis1 = distributionCenterRepository.findById(dcId);
				dis = dis1.get();
			} else {
				response.setCode("404");
				response.setMessage("select DC ID");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			String guardianName = consumerApplicationUpdate.getGuardianName();
			String mobileNumber = consumerApplicationUpdate.getMobilNo();
			String samgraId = consumerApplicationUpdate.getSamgraId();
			String aadharNo = consumerApplicationUpdate.getAadharNo();
			String cast = consumerApplicationUpdate.getCastCategory();

			Long individualOrGroupId = consumerApplicationUpdate.getIndividualOrGroupId();
			Optional<IndividualOrGroup> findById2 = individualOrGroupRepository.findById(individualOrGroupId);
			IndividualOrGroup individualOrGroup = findById2.get();

			consumerApplicationNumber.setGuardianName(guardianName);
			consumerApplicationNumber.setCastCategory(cast);
			consumerApplicationNumber.setConsumerphonNumber(mobileNumber);
			consumerApplicationNumber.setSamagraId(samgraId);
			consumerApplicationNumber.setDistributionCenter(dis);
			consumerApplicationNumber.setAadharNo(aadharNo);
			consumerApplicationNumber.setIndividualOrGroup(individualOrGroup);

			Consumer consumer = null;
			consumer = consumerService.getCurrentLoginConsumerByLoginId();
			if (consumer != null) {
				consumerApplicationNumber.setConsumers(consumer);
			}

			consumerApplicationNumber.setSamagraId(consumerApplicationUpdate.getSamgraId());
			ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(consumerApplicationNumber);

			Optional<Upload> findById = uploadRepository.findById(SamagraFileUpload.getUploadId());
			Upload upload1 = findById.get();
			upload1.setConsuemrApplicatonID(save.getConsumerApplicationId());

			Optional<Upload> findById1 = uploadRepository.findById(khasraKhatoniUpload.getUploadId());
			Upload upload2 = findById1.get();
			upload2.setConsuemrApplicatonID(save.getConsumerApplicationId());

			if (consumerApplicationUpdate.getIndividualOrGroupId() == 2l) {

				List<SamagraList> samagraLists = new ArrayList<>();

				consumerApplicationUpdate.getSamagraListDto().forEach(a -> {

					SamagraList samagraList = new SamagraList();
					samagraList.setConsumerApplicationNo(save.getConsumerApplicationNo());
					samagraList.setConsumerName(a.getConsumerName());
					samagraList.setGuardianName(a.getGuardianName());
					samagraList.setKhasraNo(a.getKhasraNo());
					samagraList.setSamagraId(a.getSamagraId());
					samagraList.setLoadRequested(a.getLoadRequested());
					samagraList.setCreatedDate(LocalDateTime.now().toString());
					samagraLists.add(samagraList);

				});

				List<SamagraList> saveAll = samagraListRepository.saveAll(samagraLists);
				map.put("listData", saveAll);
			}

			map.put("formData", save);

			final SMSRequest smsRequest = new SMSRequest();
			smsRequest.setText(MessageFormat.format(messageProperties.getSendsmsConsumerMsgfromMMKYsms(),
					new Object[] { save.getConsumerApplicationNo() }));
			smsRequest.setMobileNo(mobileNumber);
			smsRequest.setTemplateId(messageProperties.getTamplateIdsendsmsConsumerMsgfromMMKY());
//			code added by monika on 30-August-2024
			smsRequest.setHinglish(1L);
//			code end

			try {
				smsDirectService.sendMessage(smsRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}

			response.setCode("200");
			response.setMessage("Application update successfully");
			response.setList(Arrays.asList(map));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getAllConsumerApplicationByNatureOfwork8/{dcCode}")
	public ResponseEntity<?> getAllConsumerApplicationByNatureOfwork8(@PathVariable String dcCode) {
		Response response = new Response();

		List<ConsumerApplicationDetail> getAllConsumerApplicationByNatureOfwork8 = consumerApplicationDetailRepository
				.findByNatureOfWorkType_NatureOfWorkTypeIdAndDistributionCenter_dcCode(8l, dcCode);

		List<Map<String, String>> list = new ArrayList();

		for (ConsumerApplicationDetail cad : getAllConsumerApplicationByNatureOfwork8) {

			if (cad.getDistributionCenter() != null) {
				Map<String, String> map = new HashMap<String, String>();

				map.put("consumerApplicationNumber", cad.getConsumerApplicationNo());
				map.put("DistributionCenter", cad.getDistributionCenter().getDcCode());
				list.add(map);
			}
		}

		if (list.isEmpty()) {
			response.setCode("404");
			response.setMessage("Application not found on for MKMY");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}
		response.setCode("200");
		response.setMessage("Application found");
		response.setList(list);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

//	dgm o and m rejection
	@PutMapping("/rejectProposalApplication")
	public Response<?> rejectProposalApplication(@RequestPart String consumerApplicationNumber,
			@RequestPart String rejectionProposal, @RequestPart String rejectionRemark, @RequestPart String userid,
			@RequestPart String userName, @RequestPart String role,
			@RequestPart("docRejectProposal") Optional<MultipartFile> docRejectProposalFile)
			throws ConsumerApplicationDetailException, DocumentTypeException {

		System.out.println(
				"retrieveSpecificConsumerBasedOnCityCricleBhopalAndGwaliorBasedOnNullValue method is calling !!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + "rejectApplication"
				+ " : retrieveSpecificConsumer()";
		LOG.info(method);
		Response response = new Response();

		MultipartFile docRejectProposal = null;

		if (docRejectProposalFile.isPresent()) {
			docRejectProposal = docRejectProposalFile.get();
		}

		return consumerApplicationDetailService.rejectProposalApplication(consumerApplicationNumber, rejectionProposal,
				rejectionRemark, docRejectProposal, userid, userName, role);

//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findConsumerApplicationDetailsByCityCricleAndBhopal);

	}

	private static final AtomicLong LAST_TIME_MS = new AtomicLong();

	public static long uniqueCurrentTimeMS() {
		long now = System.currentTimeMillis();
		while (true) {
			long lastTime = LAST_TIME_MS.get();
			if (lastTime >= now)
				now = lastTime + 1;
			if (LAST_TIME_MS.compareAndSet(lastTime, now))
				return now;
		}
	}

	@GetMapping("/getConsumerApplicationNumber/{consumerApplicationNumber}")
	public ResponseEntity<?> getConsumerApplicationNumber(
			@PathVariable("consumerApplicationNumber") String consumerApplicationNumber) {
		Response response = new Response();
		try {
			ConsumerApplicationDetail consumerApplication = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNumber);

			if (consumerApplication.getApplicationStatus().getApplicationStatusId() == 24) {
				response.setCode("200");
				response.setMessage("Date should be fill by contractor ");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

			}
			response.setCode("100");
			response.setMessage("Date should not be fill by contractor ");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("404");
			response.setMessage("Application Not found");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

	}

	// end
	@ApiOperation(value = "Retrieve Consumer Application List with Pagination", notes = "Fetch all Consumer Application List", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_PAGINATE_NEW_URL)
	public ResponseEntity<Response<?>> getAllConsumerApplicationNewPagination(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam("filterType") String filterType, @RequestParam("filterValue") String filterValue,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException {

		System.out.println("getAllConsumerApplicationNewPagination !!!!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + RestApiUrl.GET_ALL_PAGINATE_URL
				+ " : getAllConsumerApplicationNewPagination()";
		LOG.info(method);

		Response<PageConsumerApplicationDetailDTO> response = new Response<>();

		String consumerLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println(consumerLoginId);
		ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();

		filterDTO.setConsumerLoginId(consumerLoginId);

		response = consumerApplicationDetailService.findAllConsumerApplicationDetailNewPagination(page, size, filterDTO,
				filterType, filterValue);

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@PostMapping("/oytSamgraDataApplication")
	public ResponseEntity<?> oytSamgraDataApplication(
			@RequestPart("consumerApplicattionOyt") String consumerApplicationUpdateDto,
			@RequestPart String consumerApplicationNO,
			@RequestPart("docGroupPeopleCommonFile") Optional<MultipartFile> docGroupPeopleCommonFileOptional) {
		Response response = new Response();

		try {
			ConsumerApplicationUpdateDto consumerApplicationUpdate = ConsumerApplicationUpdateDto
					.stringToJson(consumerApplicationUpdateDto);

			ConsumerApplicationDetail consumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNO);

			if (consumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application No. Not found!!!!");
				throw new ConsumerApplicationDetailException(response);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			MultipartFile docGroupPeopleCommonFile = null;

			if (docGroupPeopleCommonFileOptional.isPresent()) {
				docGroupPeopleCommonFile = docGroupPeopleCommonFileOptional.get();
			}

			ApplicationDocument applicationDocument = new ApplicationDocument();

			Upload groupPeopleCommonFile = null;

			if (docGroupPeopleCommonFile != null) {
				groupPeopleCommonFile = uploadService.uploadFile(docGroupPeopleCommonFile, "GROUP_PEOPLE_COMMON_FILE");
				if (groupPeopleCommonFile == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document GROUP PEOPLE COMMON FILE not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocGroupPeopleCommonFile(groupPeopleCommonFile);
			}

			Optional<Upload> findById1 = uploadRepository.findById(groupPeopleCommonFile.getUploadId());
			Upload upload2 = findById1.get();
			upload2.setConsuemrApplicatonID(consumerApplicationNumber.getConsumerApplicationId());

			if (consumerApplicationUpdate.getIndividualOrGroupId() == 2l) {

				List<OytSamagraListData> samagraLists = new ArrayList<>();

				consumerApplicationUpdate.getSamagraListDto().forEach(a -> {

					OytSamagraListData samagraList = new OytSamagraListData();
					samagraList.setConsumerApplicationNo(consumerApplicationNumber.getConsumerApplicationNo());
					samagraList.setConsumerName(a.getConsumerName());
					samagraList.setGuardianName(a.getGuardianName());
					samagraList.setKhasraNo(a.getKhasraNo());
					samagraList.setSamagraId(a.getSamagraId());
					samagraList.setLoadRequested(a.getLoadRequested());
					samagraList.setCreatedDate(LocalDateTime.now().toString());
					samagraLists.add(samagraList);

				});

				List<OytSamagraListData> saveAll = oytSamagraListDataRepository.saveAll(samagraLists);
				map.put("listData", saveAll);
			}

			response.setCode("200");
			response.setMessage("Application update successfully");
			response.setList(Arrays.asList(map));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Update JE Load Application", notes = "no value pass", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PutMapping("/jeReturnAmount")
	public Response<?> jeLoadApplication(@RequestParam String consumerApplicationNumber,
			@RequestParam BigDecimal jeReturnAmount) {

		System.out.println("jeLoadApplication method is calling !!!");

		final String method = RestApiUrl.CONSUMER_APPLICATION_DETAIL_API + "rejectApplication"
				+ " : retrieveSpecificConsumer()";
		LOG.info(method);

		return consumerApplicationDetailService.jeReturnAmountApplication(consumerApplicationNumber, jeReturnAmount);

//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(findConsumerApplicationDetailsByCityCricleAndBhopal);

	}

	@GetMapping("/getMkmyConsumerDetailsByApplicationNo/{consumerApplicationNo}")
	public ResponseEntity<?> getMkmyConsumerDetailsByApplicationNo(
			@PathVariable("consumerApplicationNo") String consumerApplicationNo) {
		Response response = new Response();

		CadProjection findByConsumerApplicationNO = consumerApplicationDetailRepository
				.findByConsumerApplicationNO(consumerApplicationNo);
		if (findByConsumerApplicationNO == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application No. Not found in Database for MKMY");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(findByConsumerApplicationNO));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getMkmySamagraListDataByApplicationNo/{consumerApplicationNo}")
	public ResponseEntity<?> getMkmySamagraListDataByApplicationNo(
			@PathVariable("consumerApplicationNo") String consumerApplicationNo) {
		Response response = new Response();
		List<SamagraList> samagraEntry = samagraListRepository.findByConsumerApplicationNo(consumerApplicationNo);
		if (samagraEntry == null || samagraEntry.isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application No. Not found in Database for MKMY");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		response.setCode("200");
		response.setMessage("Application found in SamagraList");
		response.setList(Arrays.asList(samagraEntry));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@PutMapping("/updateMkmyConsumerDetails")
	public ResponseEntity<?> updateMkmyConsumerDetails(
			@RequestPart("updateMkmyConsumer") String consumerApplicationUpdateDto) {

		Response response = new Response();

		try {

			ConsumerApplicationUpdateDto consumerApplicationUpdate = ConsumerApplicationUpdateDto
					.stringToJson(consumerApplicationUpdateDto);

			ConsumerApplicationDetail consumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationUpdate.getConsumerApplicationNo());

			if (consumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application Not Found In Database");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			DistributionCenter dis = null;
			if (consumerApplicationUpdate.getDcId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Distribution Center");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getConsumerName() == null
					|| consumerApplicationUpdate.getConsumerName().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Consumer Name");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getGuardianName() == null
					|| consumerApplicationUpdate.getGuardianName().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Guardian Name");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getCastCategory() == null
					|| consumerApplicationUpdate.getCastCategory().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Caste Category");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getMobilNo() == null || consumerApplicationUpdate.getMobilNo().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Mobile No.");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getSamgraId() == null || consumerApplicationUpdate.getSamgraId().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Samgra Id");
				throw new ConsumerApplicationDetailException(response);
			}

			if (consumerApplicationUpdate.getAadharNo() == null || consumerApplicationUpdate.getAadharNo().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Aadhar No.");
				throw new ConsumerApplicationDetailException(response);
			}

			Long dcId = consumerApplicationUpdate.getDcId();

			Optional<DistributionCenter> dis1 = distributionCenterRepository.findById(dcId);
			dis = dis1.get();

			Long individualOrGroupId = consumerApplicationUpdate.getIndividualOrGroupId();

			Optional<IndividualOrGroup> findById = individualOrGroupRepository.findById(individualOrGroupId);
			IndividualOrGroup individualOrGroup1 = findById.get();

			Long natureOfWorkTypeId = consumerApplicationUpdate.getNatureOfWorkTypeId();

			Optional<NatureOfWork> findByNatureOfWork = natureOfWorkRepository.findById(natureOfWorkTypeId);
			NatureOfWork natureOfWork = findByNatureOfWork.get();

			Long districtId = consumerApplicationUpdate.getDistrictId();

			Optional<District> findByDistrict = districtRepository.findById(districtId);
			District district = findByDistrict.get();

			Long loadRequestedId = consumerApplicationUpdate.getLoadRequestedId();

			Optional<LoadRequested> findByLoadRequestedId = loadRequestedRepository.findById(loadRequestedId);
			LoadRequested loadRequested1 = findByLoadRequestedId.get();

			String consumerName = consumerApplicationUpdate.getConsumerName();
			String guardianName = consumerApplicationUpdate.getGuardianName();
			String mobileNumber = consumerApplicationUpdate.getMobilNo();
			String samgraId = consumerApplicationUpdate.getSamgraId();
			String aadharNo = consumerApplicationUpdate.getAadharNo();
			String cast = consumerApplicationUpdate.getCastCategory();
			Double area = consumerApplicationUpdate.getArea();
			String address = consumerApplicationUpdate.getAddress();
			String shortDescriptionOfWork = consumerApplicationUpdate.getShortDescriptionOfWork();
			String pinCode = consumerApplicationUpdate.getPinCode();
			String loadRequested = consumerApplicationUpdate.getLoadRequested();
			String khasra = consumerApplicationUpdate.getKhasra();

			consumerApplicationNumber.setSamagraId(samgraId);
			consumerApplicationNumber.setConsumerName(consumerName);
			consumerApplicationNumber.setGuardianName(guardianName);
			consumerApplicationNumber.setAddress(address);
			consumerApplicationNumber.setArea(area);
			consumerApplicationNumber.setAadharNo(aadharNo);
			consumerApplicationNumber.setShortDescriptionOfWork(shortDescriptionOfWork);
			consumerApplicationNumber.setPincode(pinCode);
			consumerApplicationNumber.setLoadRequested(loadRequested);
			consumerApplicationNumber.setKhasra(khasra);
			consumerApplicationNumber.setCastCategory(cast);
			consumerApplicationNumber.setConsumerphonNumber(mobileNumber);
			consumerApplicationNumber.setDistributionCenter(dis);
			consumerApplicationNumber.setIndividualOrGroup(individualOrGroup1);
			consumerApplicationNumber.setNatureOfWorkType(natureOfWork);
			consumerApplicationNumber.setDistrict(district);
			consumerApplicationNumber.setLoadRequestedId(loadRequested1);
			consumerApplicationNumber.setDistributionCenter(dis);

			ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(consumerApplicationNumber);

			response.setCode("200");
			response.setMessage("Application update successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		} catch (Exception e) {
			// TODO: handle exce
			e.printStackTrace();
		}
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getConsumerDetailsfullDetailsByApplicationNo/{consumerApplicationNo}")
	public ResponseEntity<?> getConsumerDetailsByApplicationNo(
			@PathVariable("consumerApplicationNo") String consumerApplicationNo) {
		Response response = new Response();

		List<Map<String, Object>> cadDetailsReturn = consumerApplicationDetailRepository
				.cadDetailsReturn(consumerApplicationNo);
		if (cadDetailsReturn.isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application No. Not found in View");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(cadDetailsReturn));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getDcByDistrictId/{districtId}")
	public ResponseEntity<?> getDcByDistrictId(@PathVariable("districtId") Long districtId) {
		Response response = new Response();
		List<DistributionCenter> findByDistrict = distributionCenterRepository.findByDistrict(districtId);

		if (findByDistrict == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Distribution Center not found for this district id");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode("200");
		response.setMessage("Distribution Center found");
		response.setList(Arrays.asList(findByDistrict));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@PutMapping("/deactivateApplication/{consumerApp}")
	public ResponseEntity<?> deactivateApplication(@PathVariable("consumerApp") String consumerApp) {
		Response response = new Response();

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApp);
		findByConsumerApplicationNumber.setActive(false);
		findByConsumerApplicationNumber.setDeleted(true);

		ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
		response.setCode("200");
		response.setMessage("Application Deactivated Successfully");
		response.setList(Arrays.asList(save));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getConsumerDetailsfullDetailsByApplicationNo1/{consumerApplicationNo}")
	public ResponseEntity<?> getConsumerDetailsByApplicationNo1(
			@PathVariable("consumerApplicationNo") String consumerApplicationNo) {
		Response response = new Response();

		List<Map<String, Object>> cadDetailsReturn = consumerApplicationDetailRepository
				.cadDetailsReturn(consumerApplicationNo);
		if (cadDetailsReturn == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application No. Not found in View");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode("200");
		response.setMessage("Application found");
		response.setList(Arrays.asList(cadDetailsReturn));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getConsumerDetailsForTrackApplication")
	public ResponseEntity<?> getConsumerDetailsForTrackApplication(@RequestParam String mobileNo,
			@RequestParam String consumerAppNo, @RequestParam String nscApplicationNo) {
		Response response = new Response();

		if ((consumerAppNo == null || consumerAppNo.isEmpty()) && (mobileNo == null || mobileNo.isEmpty())
				&& (nscApplicationNo == null || nscApplicationNo.isEmpty())) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please provide either DSP ConsumerAppNo , MobileNo or SSP Application No.");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		if (!consumerAppNo.isEmpty() && !consumerAppNo.equalsIgnoreCase("null")) {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found in database");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			response.setCode("200");
			response.setMessage("Application Found Successfully");
			response.setList(Arrays.asList(findByConsumerApplicationNumber));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		} else if (!mobileNo.isEmpty() && !mobileNo.equalsIgnoreCase("null")) {
			Optional<Consumer> findByConsumerMobileNo = consumerRepository.findByConsumerMobileNo(mobileNo);
			if (findByConsumerMobileNo.isPresent()) {
				Consumer consumer = findByConsumerMobileNo.get();
				List<ConsumerApplicationDetail> findByConsumerId = consumerApplicationDetailRepository
						.findByConsumerId(consumer.getConsumerId());
				if (findByConsumerId.isEmpty()) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Consumer Mobile Number not found in database");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
				response.setCode("200");
				response.setMessage("Applications Found Successfully");
				response.setList(Arrays.asList(findByConsumerId));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Number not found in database kindly provide correct mobile no.");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		} else if (StringUtils.isNotBlank(nscApplicationNo) && !nscApplicationNo.equalsIgnoreCase("null")) {
			ConsumerApplicationDetail nscApplicationNoDB = consumerApplicationDetailRepository
					.findByNscApplicationNo(nscApplicationNo);
			if (nscApplicationNoDB == null) {
				return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "SSP Application Not found in database"));
			}
			return ResponseEntity.ok(new Response(HttpCode.OK, "SSP Application Retrived Successfully",
					Arrays.asList(nscApplicationNoDB)));
		} else {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Invalid input parameters.");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
	}

	@PostMapping("/saveMkmyDtrForIndividual")
	public ResponseEntity<?> saveMkmyDtrForIndividual(@RequestBody MkmyIndividualDtrCapacityDto dto)
			throws ConsumerApplicationDetailException {
		Response response = new Response();

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(dto.getConsumerAppNo());
		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application not found in database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		if (dto.getMkmyCutPoint() == null || dto.getMkmyCutPoint().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill MKMY Cut Point ");
			throw new ConsumerApplicationDetailException(response);
		}

		if (dto.getMkmyDistance() == null || dto.getMkmyDistance().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill MKMY Distance ");
			throw new ConsumerApplicationDetailException(response);
		}

		if (dto.getMkmyDtrCapacity() == null || dto.getMkmyDtrCapacity().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill MKMY DTR Capacity ");
			throw new ConsumerApplicationDetailException(response);
		}

		findByConsumerApplicationNumber.setMkmyCutPoint(dto.getMkmyCutPoint());
		findByConsumerApplicationNumber.setMkmyDistance(dto.getMkmyDistance());
		findByConsumerApplicationNumber.setMkmyDtrCapacity(dto.getMkmyDtrCapacity());
		ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
		response.setCode("200");
		response.setMessage("Data Saved Successfully");
		response.setList(Arrays.asList(save));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@PutMapping("/saveOAndMLoadDetailsForRevise")
	public ResponseEntity<?> saveOAndMLoadDetailsForRevise(@RequestBody OAndMLoadDetailsReviseDto dto) {
		Response response = new Response();
		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(dto.getConsumerAppNo());
		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application Not Found In Database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		findByConsumerApplicationNumber.setoAndMLoad(dto.getoAndMLoad());
		findByConsumerApplicationNumber.setoAndMLoadUnit(dto.getoAndMLoadUnit());
		findByConsumerApplicationNumber.setoAndMReturnAmount(dto.getoAndMReturnAmt());
		ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
		if (save == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("O & M Revise Data Not Saved");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("O & M Revise Data Updated Successfully");
		response.setList(Arrays.asList(save));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/revertAppToDemandNote/{consumerAppNo}")
	public ResponseEntity<?> revertAppToDemandNote(@PathVariable String consumerAppNo) {
		Response response = new Response();
		ConsumerApplicationDetail save = null;

		BillDeskPaymentResponse collect = billPaymentResponseeeeeeeRepository.getApplicationByDemandFees(consumerAppNo);
		if (collect == null) {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId()).get());
			save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
		}

//		if (save == null) {
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage("Consumer Paid Demand Fees So Status Can Not Revert ");
//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//		}

		response.setCode(HttpCode.OK);
		response.setMessage("Application Revert To Demand Note Payment");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@PutMapping(value = "/updateJERejectedDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response<?>> updateJERejectedDocument(
			@RequestPart("docRegistry") Optional<MultipartFile> docRegistryOptional,
			@RequestPart("docT$cpPermission") Optional<MultipartFile> docT$cpPermissionOptional,
			@RequestPart("docReraPermission") Optional<MultipartFile> docReraPermissionOptional,
			@RequestPart("docKhasraKhatoni") Optional<MultipartFile> docKhasraKhatoniOptional,
			@RequestPart("docAdministrative") Optional<MultipartFile> docAdministrativeOptional,
			@RequestPart("docGst") Optional<MultipartFile> docGstOptional,
			@RequestPart("energyBillDoc") Optional<MultipartFile> energyBillDocOptinal,
			@RequestPart("docSamagraFile") Optional<MultipartFile> docSamagraFileOptional,
			@RequestPart("docNoc") Optional<MultipartFile> docNocOptional,
			@RequestPart("docIndividualOrGroup") Optional<MultipartFile> docIndividualOrGroupOptional,
			@RequestParam("oldDocRegistryId") Long oldDocRegistryId,
			@RequestParam("oldDocTcpPermissionId") Long oldDocTcpPermissionId,
			@RequestParam("oldDocReraPermissionId") Long oldDocReraPermissionId,
			@RequestParam("oldDocKhasraKhatoniId") Long oldDocKhasraKhatoniId,
			@RequestParam("oldDocAdministrativeId") Long oldDocAdministrativeId,
			@RequestParam("oldDocGstId") Long oldDocGstId, @RequestParam("oldDocEnergyBillId") Long oldDocEnergyBillId,
			@RequestParam("oldDocSamagraFileId") Long oldDocSamagraFileId,
			@RequestParam("oldDocIndividualOrGroupFileId") Long oldDocIndividualOrGroupFileId,
			@RequestParam("oldDocNocId") Long oldDocNocId,
			@RequestPart("docMap") Optional<MultipartFile> docMapOptional,
			@RequestPart("docLoadSheet") Optional<MultipartFile> docLoadSheetOptional,
			@RequestParam("oldDocMapId") Long oldDocMapId, @RequestParam("oldDocLoadSheetId") Long oldDocLoadSheetId,
			@RequestParam("consumerAppNo") String consumerAppNo) throws FormValidationException,
			InvalidAuthenticationException, ConsumerApplicationDetailException, DocumentTypeException {
		Response response = new Response();
		MultipartFile docRegistry = null;
		MultipartFile docT$cpPermission = null;
		MultipartFile docReraPermission = null;
		MultipartFile docNoc = null;
		MultipartFile docKhasraKhatoni = null;
		MultipartFile docAdministrative = null;
		MultipartFile docGst = null;
		MultipartFile energyBillDoc = null;
		MultipartFile docSamagraFile = null;
		MultipartFile docIndividualOrGroup = null;
		MultipartFile docLoadSheet = null;
		MultipartFile docMap = null;

		String updateFile = null;

		if (docRegistryOptional.isPresent()) {
			docRegistry = docRegistryOptional.get();
			updateFile = updateFile(consumerAppNo, docRegistry, oldDocRegistryId, "REGISTRY");
		}

		if (docT$cpPermissionOptional.isPresent()) {
			docT$cpPermission = docT$cpPermissionOptional.get();
			updateFile = updateFile(consumerAppNo, docT$cpPermission, oldDocTcpPermissionId, "T$CP_PERMISSION");
		}
		if (docReraPermissionOptional.isPresent()) {
			docReraPermission = docReraPermissionOptional.get();
			updateFile = updateFile(consumerAppNo, docReraPermission, oldDocReraPermissionId, "RERA_PERMISSION");
		}

		if (docNocOptional.isPresent()) {
			docNoc = docNocOptional.get();
			updateFile = updateFile(consumerAppNo, docNoc, oldDocNocId, "NOC");
		}

		if (docKhasraKhatoniOptional.isPresent()) {
			docKhasraKhatoni = docKhasraKhatoniOptional.get();
			updateFile = updateFile(consumerAppNo, docKhasraKhatoni, oldDocKhasraKhatoniId, "KHASRA_KHATONI");
		}

		if (docAdministrativeOptional.isPresent()) {
			docAdministrative = docAdministrativeOptional.get();
			updateFile = updateFile(consumerAppNo, docAdministrative, oldDocAdministrativeId, "Administrative");
		}
		if (docGstOptional.isPresent()) {
			docGst = docGstOptional.get();
			updateFile = updateFile(consumerAppNo, docGst, oldDocGstId, "GST");
		}
		if (energyBillDocOptinal.isPresent()) {
			energyBillDoc = energyBillDocOptinal.get();
			updateFile = updateFile(consumerAppNo, energyBillDoc, oldDocEnergyBillId, "energyBillDoc");
		}
		if (docSamagraFileOptional.isPresent()) {
			docSamagraFile = docSamagraFileOptional.get();
			updateFile = updateFile(consumerAppNo, docSamagraFile, oldDocSamagraFileId, "SAMAGRA_FILE");
		}

		if (docIndividualOrGroupOptional.isPresent()) {
			docIndividualOrGroup = docIndividualOrGroupOptional.get();
			updateFile = updateFile(consumerAppNo, docIndividualOrGroup, oldDocIndividualOrGroupFileId, "Group_File");
		}

//		code added 6-june-2025
		if (docLoadSheetOptional.isPresent()) {
			docLoadSheet = docLoadSheetOptional.get();
			updateFile = updateFile(consumerAppNo, docLoadSheet, oldDocLoadSheetId, "LOAD_SHEET");
		}
		if (docMapOptional.isPresent()) {
			docMap = docMapOptional.get();
			updateFile = updateFile(consumerAppNo, docMap, oldDocMapId, "MAP");
		}

		if (updateFile.equals("Data Updated Succsfully")) {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId()).get());
			ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
			response.setList(Arrays.asList(save));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Updated Successfully");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} else {
			response.setCode(HttpCode.UPDATED);
			response.setMessage("Record Not Updated Successfully");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

	}

	public String updateFile(String consumerAppNo, MultipartFile file, Long fileId, String fileName)
			throws DocumentTypeException {

		try {
			ApplicationDocument applicationDoc = null;
//		uploadRepository.deleteById(fileId);

			Upload uploadFile = uploadService.uploadFile(file, fileName);
			System.out.println("Document Path " + uploadFile.getDocumentPath());
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			uploadFile.setConsuemrApplicatonID(findByConsumerApplicationNumber.getConsumerApplicationId());
			uploadFile.setDocumentStatus(1L);
			Upload save = uploadRepository.save(uploadFile);
			System.out.println(save.getUploadId() + " Upload Id issssssss");

			if (save != null) {
				ApplicationDocument applicationDocument = null;
				try {
					applicationDocument = applicationDocumentRepository.findByConsumerApplicationDetailId(
							findByConsumerApplicationNumber.getConsumerApplicationId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (fileName.equals("REGISTRY")) {
					applicationDocument.setDocRegistry(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("REGISTRY Saved " + applicationDoc);
				} else if (fileName.equals("T$CP_PERMISSION")) {
					applicationDocument.setDocT$cpPermission(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("CP_PERMISSION Saved " + applicationDoc);
				} else if (fileName.equals("RERA_PERMISSION")) {
					applicationDocument.setDocReraPermission(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("RERA_PERMISSION Saved" + applicationDoc);
				} else if (fileName.equals("NOC")) {
					applicationDocument.setDocNoc(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("NOC Saved " + applicationDoc);
				} else if (fileName.equals("KHASRA_KHATONI")) {
					applicationDocument.setDocKhasraKhatoni(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("KHASRA_KHATONI Saved" + applicationDoc);
				} else if (fileName.equals("Administrative")) {
					applicationDocument.setDocAdministrative(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("Administrative Saved " + applicationDoc);
				} else if (fileName.equals("GST")) {
					applicationDocument.setDocGst(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("GST Saved" + applicationDoc);
				} else if (fileName.equals("energyBillDoc")) {
					applicationDocument.setDocEnergyBillFile(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("energyBillDoc Saved " + applicationDoc);
				} else if (fileName.equals("SAMAGRA_FILE")) {
					applicationDocument.setDocSamagraFile(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("SAMAGRA_FILE Saved" + applicationDoc);
				} else if (fileName.equals("Group_File")) {
					applicationDocument.setDocGroup(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("Group_File Saved" + applicationDoc);
				} else if (fileName.equals("LOAD_SHEET")) {
					applicationDocument.setDocLoadSheet(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("LOAD_SHEET Saved" + applicationDoc);
				} else if (fileName.equals("MAP")) {
					applicationDocument.setDocMap(save);
					applicationDoc = applicationDocumentRepository.save(applicationDocument);
					System.out.println("MAP Saved" + applicationDoc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		uploadRepository.deleteById(fileId);
		return "Data Updated Succsfully";
	}

	@GetMapping("/jePutErpNumberAndChangeApplicationStatus/{consumerAppNo}/{erpNumber}")
	public ResponseEntity<?> jePutErpNumberAndChangeApplicationStatus(@PathVariable String consumerAppNo,
			@PathVariable String erpNumber) {
		Response response = new Response();
		ConsumerApplicationDetail findByConsumerApplicationNumber = null;
		try {

			findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);

			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

//			code start 3 April 2025 with updated check for erp no. ki same erp no. kisi bhi application se tagged na ho 
//			ResponseEntity<Response> checkErpExistOrNot = dryUtility.checkErpExistOrNot(erpNumber);
//			if (checkErpExistOrNot != null && checkErpExistOrNot.getBody() != null) {
//				Response responseBody = checkErpExistOrNot.getBody(); // Extract response body
//
//				if ("This ERP Number Is Already Associated With Another Application Number."
//						.equals(responseBody.getMessage())) {
//					return checkErpExistOrNot;
//				}
//			}

			if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(7l)) {
//			code changed on 24-09-2025
				ResponseEntity<Response> checkErpExistOrNot = dryUtility.checkErpExistOrNot2(erpNumber, consumerAppNo);
				if (checkErpExistOrNot != null && checkErpExistOrNot.getBody() != null) {
					Response responseBody = checkErpExistOrNot.getBody();

					// Agar ERP already system me kahin bhi present hai
					if (HttpCode.NOT_ACCEPTABLE.equals(responseBody.getCode())) {
						return checkErpExistOrNot;
					}
				}

//			code end
				findByConsumerApplicationNumber.setErpWorkFlowNumber(erpNumber);
				findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId()).get());
				consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
				response.setCode(HttpCode.OK);
				response.setMessage("Consumer Application Update");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
						"This API is not allowed for the current application status :  "
								+ findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusName()
								+ " (ID: "
								+ findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
								+ ")"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setCode(HttpCode.OK);
		response.setMessage("Application Revert To Demand Note Payment");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

// Nature Of work change krkr data update krne k liye created 22-05-2024
	@PutMapping("/updateConsumerApplicationDetailToChangeScheme/{id}")
	public ResponseEntity<Response<?>> updateConsumerApplicationDetailToChangeScheme(
			@RequestPart("applicationForm") String consumerApplicationDetailFormString,
			@RequestPart("docRegistry") Optional<MultipartFile> docRegistryOptional,
			@RequestPart("docT$cpPermission") Optional<MultipartFile> docT$cpPermissionOptional,
			@RequestPart("docReraPermission") Optional<MultipartFile> docReraPermissionOptional,
			@RequestPart("docNoc") Optional<MultipartFile> docNocOptional,
			@RequestPart("docKhasraKhatoni") Optional<MultipartFile> docKhasraKhatoniOptional,
			@RequestPart("docIndividualOrGroup") Optional<MultipartFile> docIndividualOrGroupOptional,
			@RequestPart("docAdministrative") Optional<MultipartFile> docAdministrativeOptional,
			@RequestPart("docGst") Optional<MultipartFile> docGstOptional,
			@RequestPart("energyBillDoc") Optional<MultipartFile> energyBillDocOptinal,
			@RequestPart("docSamagraFile") Optional<MultipartFile> docSamagraFileOptional,
			@RequestPart("docMap") Optional<MultipartFile> docMapOptional,
			@RequestPart("docLoadSheet") Optional<MultipartFile> docLoadSheetOptional,
			@RequestPart("docconsentletter") Optional<MultipartFile> docconsentletterOptional,
			@PathVariable("id") Long id,

			BindingResult bindingResult, HttpServletResponse httpServletResponse) throws FormValidationException,
			InvalidAuthenticationException, ConsumerApplicationDetailException, DocumentTypeException {
		Response<ConsumerApplicationDetail> response = new Response<>();

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});
			response = new Response<>();
			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			throw new FormValidationException(response);
		} // end of form validation
		ConsumerApplicationDetail updateConsumerApplicationDetail = null;
		try {
			ConsumerApplicationDetailForm form = ConsumerApplicationDetailForm
					.stringToJson(consumerApplicationDetailFormString);

			MultipartFile docRegistry = null;
			MultipartFile docT$cpPermission = null;
			MultipartFile docReraPermission = null;
			MultipartFile docNoc = null;
			MultipartFile docKhasraKhatoni = null;
			MultipartFile docIndividualOrGroup = null;
			MultipartFile docAdministrative = null;
			MultipartFile docGst = null;
			MultipartFile docEnergyBill = null;
			MultipartFile docSamagraFile = null;
			MultipartFile docMap = null;
			MultipartFile docLoadSheet = null;
			MultipartFile docconsentletter = null;

			if (docconsentletterOptional.isPresent()) {
				docconsentletter = docconsentletterOptional.get();
			}
			if (docRegistryOptional.isPresent()) {
				docRegistry = docRegistryOptional.get();
			}
			if (docT$cpPermissionOptional.isPresent()) {
				docT$cpPermission = docT$cpPermissionOptional.get();
			}
			if (docReraPermissionOptional.isPresent()) {
				docReraPermission = docReraPermissionOptional.get();
			}
			if (docNocOptional.isPresent()) {
				docNoc = docNocOptional.get();
			}
			if (docKhasraKhatoniOptional.isPresent()) {
				docKhasraKhatoni = docKhasraKhatoniOptional.get();
			}
			if (docIndividualOrGroupOptional.isPresent()) {
				docIndividualOrGroup = docIndividualOrGroupOptional.get();
			}
			if (docAdministrativeOptional.isPresent()) {
				docAdministrative = docAdministrativeOptional.get();

			}
			if (docGstOptional.isPresent()) {
				docGst = docGstOptional.get();
			}
			if (energyBillDocOptinal.isPresent()) {
				docEnergyBill = energyBillDocOptinal.get();
			}
			if (docSamagraFileOptional.isPresent()) {
				docSamagraFile = docSamagraFileOptional.get();
			}

			if (docLoadSheetOptional.isPresent()) {
				docLoadSheet = docLoadSheetOptional.get();
			}

			if (docMapOptional.isPresent()) {
				docMap = docMapOptional.get();
			}

			updateConsumerApplicationDetail = consumerApplicationDetailService
					.updateConsumerApplicationDetailChangeSchemeType(form, docRegistry, docT$cpPermission,
							docReraPermission, docNoc, docKhasraKhatoni, docIndividualOrGroup, docAdministrative, id,
							docGst, docEnergyBill, docSamagraFile, docLoadSheet, docMap, docconsentletter);
		} catch (Exception e) {
			e.printStackTrace();
			response.setList(null);
			response.setCode("404");
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}
		response.setList(Arrays.asList(updateConsumerApplicationDetail));
		response.setCode(HttpCode.UPDATED);
		response.setMessage("Record Updated Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getAllJeRemarkForUpdation/{consumerAppNo}")
	public ResponseEntity<?> getAllJeRemarkForUpdation(@PathVariable String consumerAppNo) {
		Response<AppUpdationRemark> response = new Response<AppUpdationRemark>();
		try {
			List<AppUpdationRemark> findByConsumerAppNo = appUpdationRemarkRepository
					.findByConsumerAppNo(consumerAppNo);
			if (findByConsumerAppNo.isEmpty() || findByConsumerAppNo == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Je Remark Not Found");
				return ResponseEntity.ok(response);
			}
			response.setCode(HttpCode.OK);
			response.setMessage("Data Retrieved Successfully");
			response.setList(findByConsumerAppNo);
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PostMapping("/saveApplicantFeedback")
	public ResponseEntity<?> saveApplicantFeedback(@RequestBody ApplicantFeedbackDSP feedback) {
		Response<ApplicantFeedbackDSP> response = new Response<ApplicantFeedbackDSP>();
		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(feedback.getConsumerApplicationNo());
		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("consumer application number not found in database");
			return ResponseEntity.ok(response);
		}

		List<ApplicantFeedbackDSP> findAllByConsumerApplicationNo = applicantFeedbackRepository
				.findAllByConsumerApplicationNo(feedback.getConsumerApplicationNo());
		if (!findAllByConsumerApplicationNo.isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("You have already submitted the Feedback Thanks for your feedback.");
			return ResponseEntity.ok(response);
		}
		ApplicantFeedbackDSP save = applicantFeedbackRepository.save(feedback);
		if (save == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Data Not saved in database");

		} else {
			findByConsumerApplicationNumber.setApplicantFeedbackDSP(save);
			consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
			response.setCode(HttpCode.CREATED);
			response.setMessage("data successfully saved in database");
			response.setList(Arrays.asList(save));
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getAllApplicantFeedback/{consumerApplicationNo}")
	public ResponseEntity<?> getAllApplicantFeedback(@PathVariable String consumerApplicationNo) {
		Response<ApplicantFeedbackDSP> response = new Response<ApplicantFeedbackDSP>();
		List<ApplicantFeedbackDSP> findAllByConsumerApplicationNo = applicantFeedbackRepository
				.findAllByConsumerApplicationNo(consumerApplicationNo);
		if (findAllByConsumerApplicationNo.isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("consumer application no. not found in database");
		} else {
			response.setCode(HttpCode.CREATED);
			response.setMessage("data retrived successfully ");
			response.setList(findAllByConsumerApplicationNo);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/saveApplicantWorkIssues")
	public ResponseEntity<?> saveApplicantWorkIssues(@RequestBody ApplicantWorkIssues workIssues) {
		Response<ApplicantWorkIssues> response = new Response<ApplicantWorkIssues>();

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(workIssues.getConsumerApplicationNo());
		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application Number Not Found In Database");
			return ResponseEntity.ok(response);
		}
		ApplicantWorkIssues save = applicantFeedbackForWorkIssuesRepository.save(workIssues);
		if (save == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Data Not saved in database");
		} else {
			response.setCode(HttpCode.CREATED);
			response.setMessage("data successfully saved in database");
			response.setList(Arrays.asList(save));
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getAllApplicantWorkIssues/{consumerApplicationNo}")
	public ResponseEntity<?> getAllApplicantWorkIssues(@PathVariable String consumerApplicationNo) {
		Response<ApplicantWorkIssues> response = new Response<ApplicantWorkIssues>();
		List<ApplicantWorkIssues> findAllByConsumerApplicationNo = applicantFeedbackForWorkIssuesRepository
				.findAllByConsumerApplicationNo(consumerApplicationNo);
		if (!findAllByConsumerApplicationNo.isEmpty()) {
			response.setCode(HttpCode.OK);
			response.setMessage("data retriseteved successfully");
			response.setList(findAllByConsumerApplicationNo);
		} else {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("consumer application no. not found in database");
		}

		return ResponseEntity.ok(response);
	}

//	api for reselection of contractor before work completion

	@PutMapping("/user/reselectionOfContractor")
	public ResponseEntity<Response> reselectionOfContractor(@RequestParam String consumerApplicationNumber,
			@RequestParam String conReselectionDate, @RequestParam String conReselectionRemark,
			@RequestParam String conReselectionDGMOANDMName, @RequestParam Boolean gmAccepted,
			@RequestPart("docContractorReselctionFile") Optional<MultipartFile> docContractorReselctionFile)
			throws DocumentTypeException {

		Response response = new Response();
		ConsumerApplicationDetail applicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNumber);

		if (applicationDetail == null) {
			response.setCode("404");
			response.setMessage("Application not found in database");
			return ResponseEntity.ok(response);
		}

		Long statusId = applicationDetail.getApplicationStatus().getApplicationStatusId();
		if (statusId == 22 || statusId == 23 || statusId == 24) {
			if (gmAccepted) {
				ContractorForBid byApplicationNo = contractorForBidRepository
						.findByApplicationNo(consumerApplicationNumber);
				if (byApplicationNo != null) {
					contractorForBidRepository.delete(byApplicationNo);
				}

				ContractorForBidWorkStatus byConsumerApplicationNo = contractorForBidWorkStatusRepository
						.findByConsumerApplicationNo(consumerApplicationNumber);
				if (byConsumerApplicationNo != null) {
					contractorForBidWorkStatusRepository.delete(byConsumerApplicationNo);
				}
//				List<VendorWorkOrder> workOrder = vendorWorkOrderRepository
//						.findByConsumerApplicationNo(consumerApplicationNumber);
//				if (workOrder != null) {
//					for (VendorWorkOrder wOrder : workOrder)
//						vendorWorkOrderRepository.delete(wOrder);
//				}
				vendorWorkOrderRepository.findByConsumerApplicationNo(consumerApplicationNumber)
						.forEach(vendorWorkOrderRepository::delete);
				Long newStatusId = applicationDetail.getSchemeType().getSchemeTypeId().equals(1L)
						? ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId()
						: ApplicationStatusEnum.PENDING_FOR_CONTRACTOR_SELECTION_AFTER_TENDER_BY_DGM_STC.getId();
				applicationDetail.setApplicationStatus(applicationStatusRepository.findById(newStatusId).orElse(null));

//	            file uploading code
				MultipartFile contractorReselctionFile = docContractorReselctionFile.orElse(null);
				Upload contractorResUpload = null;
				if (contractorReselctionFile != null) {
					contractorResUpload = uploadService.uploadFile(contractorReselctionFile,
							"CONTRACTOR_RESELECTION_FILE");
					if (contractorResUpload == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Contractor Reselection File Not Uploaded");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}
				}

				ApplicationDocument byConsumerApplicationDetailId = applicationDocumentRepository
						.findByConsumerApplicationDetailId(applicationDetail.getConsumerApplicationId());
				if (byConsumerApplicationDetailId != null) {
					byConsumerApplicationDetailId.setDocContractorReselctionFile(contractorResUpload);
					applicationDocumentRepository.save(byConsumerApplicationDetailId);
				} else {
					ApplicationDocument applicationDocument = new ApplicationDocument();
					applicationDocument.setConsumerApplicationDetail(applicationDetail);
					applicationDocument.setDocContractorReselctionFile(contractorResUpload);
					applicationDocumentRepository.save(applicationDocument);
				}

				applicationDetail.setConReselectionDate(conReselectionDate);
				applicationDetail.setConReselectionDgmAccepted("true");
				applicationDetail.setConReselectionDGMOANDMName(conReselectionDGMOANDMName);
				applicationDetail.setConReselectionRemark(conReselectionRemark);

				ConsumerApplicationDetail savedDetail = consumerApplicationDetailRepository.save(applicationDetail);
				response.setCode("200");
				response.setMessage("Application updated successfully");
				response.setList(Arrays.asList(savedDetail));
				return ResponseEntity.ok(response);
			}
		} else {
			response.setCode("404");
			response.setMessage("Contractor Reselection is not possible at this stage of application");
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(null);
	}

	@GetMapping("/getBilldeskDemandPaymentExistOrNot/{consumerApplicationNo}")
	public ResponseEntity<?> getBilldeskDemandPaymentExistOrNot(@PathVariable String consumerApplicationNo) {
		Response response = new Response();
		ConsumerApplicationDetail applicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);

		if (applicationDetail == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application not found in database");
			return ResponseEntity.ok(response);

		}
		BillDeskPaymentResponse demandDataFromBilldesk = billPaymentResponseeeeeeeRepository
				.getDemandDataFromBilldesk(consumerApplicationNo);
		response.setCode(HttpCode.NULL_OBJECT);
		response.setMessage((demandDataFromBilldesk != null) ? "Demand Payment Found In Billdesk Table"
				: "Demand Payment Not Found In Billdesk Table");
		return ResponseEntity.ok(response);

	}

	@PostMapping("/consumerApplicationSearch")
	public ResponseEntity<?> consumerApplicationSearch(@RequestBody ConsumerApplicationSearch consumerApplicationSearch)
			throws ConsumerApplicationDetailException {

		if (Objects.isNull(consumerApplicationSearch)) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Payload is null", null));
		}

		if (consumerApplicationSearch.getConsumerId() == null) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Consumer ID is required", null));
		}

		// Search by ApplicationNo
		if (consumerApplicationSearch.getApplicationNo() != null) {
			ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
					.findByConsumerIdAndApplicationNo(consumerApplicationSearch.getConsumerId(),
							consumerApplicationSearch.getApplicationNo())
					.orElseThrow(() -> new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
							"Consumer Application No. not found for this consumer Id")));
			return ResponseEntity.ok(new Response(HttpCode.OK, "Consumer Application retrieved successfully",
					Arrays.asList(consumerApplicationDetail)));
		}

		// Search by ApplicationStatusId
		if (consumerApplicationSearch.getApplicationStatusId() != null) {
			List<ConsumerApplicationDetail> consumerApplicationDetail = consumerApplicationDetailRepository
					.findByConsumerIdAndApplicationStatusId(consumerApplicationSearch.getConsumerId(),
							consumerApplicationSearch.getApplicationStatusId());
			if (consumerApplicationDetail.isEmpty()) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "No application found for given status id"));
			}
			return ResponseEntity.ok(new Response(HttpCode.OK, "Consumer Application retrieved successfully",
					consumerApplicationDetail));
		}

		// Search by ConsumerName
		if (consumerApplicationSearch.getConsumerName() != null) {
			List<ConsumerApplicationDetail> consumerApplicationDetail = consumerApplicationDetailRepository
					.findByConsumerIdAndConsumerName(consumerApplicationSearch.getConsumerId(),
							consumerApplicationSearch.getConsumerName());
			if (consumerApplicationDetail.isEmpty()) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "No application found for given consumer name"));
			}
			return ResponseEntity.ok(new Response(HttpCode.OK, "Consumer Application retrieved successfully",
					consumerApplicationDetail));
		}

		// Search by NatureOfWorkId
		if (consumerApplicationSearch.getNatureOfWorkId() != null) {
			List<ConsumerApplicationDetail> consumerApplicationDetail = consumerApplicationDetailRepository
					.findByConsumerIdAndNatureOfWorkId(consumerApplicationSearch.getConsumerId(),
							consumerApplicationSearch.getNatureOfWorkId());
			if (consumerApplicationDetail.isEmpty()) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "No application found for given Nature Of Work type id"));
			}
			return ResponseEntity.ok(new Response(HttpCode.OK, "Consumer Application retrieved successfully",
					consumerApplicationDetail));
		}

		// Search by SchemeTypeId
		if (consumerApplicationSearch.getSchemeTypeId() != null) {
			List<ConsumerApplicationDetail> consumerApplicationDetail = consumerApplicationDetailRepository
					.findByConsumerIdAndSchemeTypeId(consumerApplicationSearch.getConsumerId(),
							consumerApplicationSearch.getSchemeTypeId());
			if (consumerApplicationDetail.isEmpty()) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "No application found for given scheme type id"));
			}
			return ResponseEntity.ok(new Response(HttpCode.OK, "Consumer Application retrieved successfully",
					consumerApplicationDetail));
		}

		return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Invalid search criteria", null));
	}

	@GetMapping("/getUserIdApplicationCountPendency/{userId}")
	public ResponseEntity<?> getUserIdApplicationCountPendency(@PathVariable String userId) {
		List<Map<String, ?>> userIdApplicationCountPendency = consumerApplicationDetailRepository
				.getUserIdApplicationCountPendency(userId);

		return ResponseEntity.ok(userIdApplicationCountPendency.isEmpty()
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this userId")
				: new Response(HttpCode.OK, "Data Retrieved successfully",
						Arrays.asList(userIdApplicationCountPendency)));

	}

	@Autowired
	UserRepository userRepository;

//	@GetMapping("/getDSPApplicationPendencyOnDiscomUser/{userId}")
//	public ResponseEntity<?> getDSPApplicationPendencyOnDiscomUser(@PathVariable String userId)
//			throws ConsumerApplicationDetailException {
//
//		List<Map<String, ?>> dspApplicationPendencyOnDiscomUser = null;
//		User userDB = userRepository.findByUserId(userId).orElseThrow(() -> new ConsumerApplicationDetailException(
//				new Response(HttpCode.NULL_OBJECT, "User Id not found in database : " + userId)));
//		if (!Objects.isNull(userDB)) {
//			dspApplicationPendencyOnDiscomUser = consumerApplicationDetailRepository
//					.getDSPApplicationPendencyOnDiscomUser(userId);
//
//		}
//		return ResponseEntity.ok(dspApplicationPendencyOnDiscomUser.isEmpty()
//				? new Response(HttpCode.NULL_OBJECT, "Data not found for this userId")
//				: new Response(HttpCode.OK, "Data Retrieved successfully",
//						Arrays.asList(dspApplicationPendencyOnDiscomUser)));
//	}
//	08-12-2025 commented code bcoz used the dynamic query in below api

	@GetMapping("/getDSPApplicationPendencyOnDiscomUser/{userId}")
	public ResponseEntity<?> getDSPApplicationPendencyOnDiscomUser(@PathVariable String userId)
			throws ConsumerApplicationDetailException {

		List<Map<String, Object>> dspApplicationPendencyOnDiscomUser = null;
		User userDB = userRepository.findByUserId(userId).orElseThrow(() -> new ConsumerApplicationDetailException(
				new Response(HttpCode.NULL_OBJECT, "User Id not found in database : " + userId)));
		if (!Objects.isNull(userDB)) {
			DynamicQuery financeGstApplicationList = dynamicQueryRepository.findByQueryName("ANIMESH_WFM_QUERY");

			if (Objects.isNull(financeGstApplicationList)) {
				throw new IllegalArgumentException("No dynamic query found for: ANIMESH_WFM_QUERY");
			}
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("userId", userId);

			dspApplicationPendencyOnDiscomUser = namedParameterJdbcTemplate
					.queryForList(financeGstApplicationList.getQueryText(), hashMap);

		}
		return ResponseEntity.ok(dspApplicationPendencyOnDiscomUser.isEmpty()
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this userId")
				: new Response(HttpCode.OK, "Data Retrieved successfully",
						Arrays.asList(dspApplicationPendencyOnDiscomUser)));
	}

	@GetMapping("/getByDcId/{dcId}")
	public ResponseEntity<?> getByDcId(@PathVariable Long dcId) {
		Response response = new Response();
		List<ConsumerApplicationDetail> findByDistributionCenter = consumerApplicationDetailRepository
				.findByDistributionCenter_DcId(dcId);

		if (findByDistributionCenter.size() <= 0) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application not found in database");
			return ResponseEntity.ok(response);

		}

		response.setCode(HttpCode.OK);
		response.setMessage("data Successfully return...");
		response.setList(findByDistributionCenter);
		return ResponseEntity.ok(response);

	}

	@PatchMapping("/isAvedakReallyGovernment")
	public ResponseEntity<?> isAvedakReallyGovernment(@RequestParam(required = false) String isAvedakGovernmentERP,
			@RequestParam(required = false) String isAvedakGovernmentRevise, @RequestParam String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(consumerApplicationNo);

		System.err.println("isAvedakGovernmentERP : " + isAvedakGovernmentERP);
		if (isAvedakGovernmentERP != null) {
			validateConsumerApplication.setIsAvedakGovernmentErp(isAvedakGovernmentERP);
		}
		if (isAvedakGovernmentRevise != null) {
			validateConsumerApplication.setIsAvedakGovernmentRevise(isAvedakGovernmentRevise);
		}
		ConsumerApplicationDetail savedData = consumerApplicationDetailRepository.save(validateConsumerApplication);

		return ResponseEntity.ok(Objects.isNull(savedData) ? new Response(HttpCode.NULL_OBJECT, "Data not saved")
				: new Response(HttpCode.UPDATED, "Data Updated Successfully", Arrays.asList(savedData)));

	}

	@GetMapping("/getConsumerApplicationDetailsByApplicationNumber/{consumerApplicationNo}")
	public ResponseEntity<?> getConsumerApplicationDetailsByApplicationNumber(
			@PathVariable String consumerApplicationNo) {
		Response response = new Response();
		ConsummerAppDetails findByConsumerAppDetails = consumerApplicationDetailRepository
				.findByConsumerAppDetails(consumerApplicationNo);

		if (findByConsumerAppDetails == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application not found in database");
			return ResponseEntity.ok(response);

		}

		response.setCode(HttpCode.OK);
		response.setMessage("data Successfully return...");
		response.setList(Arrays.asList(findByConsumerAppDetails));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/changeConsumerMobileNumber/{oldLoginId}/{newMobileNo}")
	public ResponseEntity<?> changeConsumerMobileNumber(@PathVariable String oldLoginId,
			@PathVariable String newMobileNo) {
		Response<Consumer> response = new Response<>();
		try {

			Consumer consumer1 = consumerRepository.findByConsumerLoginId(newMobileNo).orElse(null);
			if (consumer1 != null) {
				response.setCode("406");
				response.setMessage("This mobile no is already registered");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			Consumer consumer = consumerRepository.findByConsumerLoginId(oldLoginId).orElse(null);
			if (consumer == null) {
				response.setCode("100");
				response.setMessage("Consuer Not Found With This Login Id");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			consumer.setConsumerLoginId(newMobileNo);
			consumer.setConsumerMobileNo(newMobileNo);

			List<ConsumerApplicationDetail> findByConsumerId = consumerApplicationDetailRepository
					.findByConsumerId(consumer.getConsumerId());
			for (ConsumerApplicationDetail consumerDetail : findByConsumerId) {
				consumerDetail.setConsumerphonNumber(newMobileNo);
				consumerApplicationDetailRepository.save(consumerDetail);
			}

			Consumer save = consumerRepository.save(consumer);
			response.setCode("200");
			response.setMessage("Consumer Mobile Number Updated Successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("404");
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/getConsumerApplicationDataFromView/{consumerApplicationNo}")
	public ResponseEntity<?> getConsumerApplicationDataFromView(@PathVariable String consumerApplicationNo) {
		List<Map<String, ?>> dataFromView = consumerApplicationDetailRepository.getDataFromView(consumerApplicationNo);
		return ResponseEntity.ok(Objects.isNull(dataFromView)
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", dataFromView));

	}

	@GetMapping("/getAllPurposeType")
	public ResponseEntity<?> getAllPurposeType() {
		List<PurposeType> listData = purposeTypeRepository.findAll();
		return ResponseEntity.ok(listData.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data Not Found In Table")
				: new Response(HttpCode.OK, "Data Retrieved Successfully", listData));

	}

	@PatchMapping("/uploadInspectionFile")
	public ResponseEntity<?> uploadInspectionFile(@RequestPart String consumerApplicationNo,
			@RequestPart("docInspectionFile") MultipartFile docInspectionFile)
			throws ConsumerApplicationDetailException, ApplicationDocumentException, DocumentTypeException {
		ConsumerApplicationDetail validateConsumerApplicationDB = dryUtility
				.validateConsumerApplication(consumerApplicationNo);

		Upload docInspectionFileUpload = null;
		if (docInspectionFile == null)
			throw new ApplicationDocumentException(
					new Response(HttpCode.NOT_ACCEPTABLE, "Inspection file can not be null"));

		if (Objects.nonNull(docInspectionFile)) {
			docInspectionFileUpload = uploadService.uploadFile(docInspectionFile, "DOC_INSPECTION_FILE");
			if (docInspectionFileUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Inspection File Not Uploaded"));
			}
		}
		List<ApplicationDocument> allDataByConsumerApplicationId = applicationDocumentRepository
				.findAllDataByConsumerApplicationId(validateConsumerApplicationDB.getConsumerApplicationId());
		if (Objects.isNull(allDataByConsumerApplicationId) || allDataByConsumerApplicationId.isEmpty()) {
			ApplicationDocument applicationDocument = new ApplicationDocument();
			applicationDocument.setConsumerApplicationDetail(validateConsumerApplicationDB);
			applicationDocument.setDocInspectionFile(docInspectionFileUpload);
			applicationDocumentRepository.save(applicationDocument);
		} else {
			Upload finalDocInspectionFileUpload = docInspectionFileUpload;
			allDataByConsumerApplicationId.forEach(doc -> {
				doc.setDocInspectionFile(finalDocInspectionFileUpload);
			});
			applicationDocumentRepository.saveAll(allDataByConsumerApplicationId);
		}

		return ResponseEntity.ok(new Response<>(HttpCode.OK, "Data Saved Successfully "));
	}

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMServiceIMp workCompletionChangStautsByDgmOAndMServiceIMp;

	@PatchMapping("/rejectLoadChangeApplication/{consumerApplicationNo}")
	public ResponseEntity<?> rejectLoadChangeApplication(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		ConsumerApplicationDetail saveConsumerApplicationDetail = null;
		String sendDataToSSp = null;
		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(consumerApplicationNo);
		if (validateConsumerApplication.getApplicationStatus().getApplicationStatusId() < 6l) {

//			String sendDataToSspAfterWorkOrder = sSPService.sendDataToSspAfterWorkOrder(consumerApplicationNo, 2L);
			sendDataToSSp = workCompletionChangStautsByDgmOAndMServiceIMp.rejectLoadChangeApplication(
					validateConsumerApplication.getNscApplicationNo(), 2L, consumerApplicationNo);
			if ("Success".equals(sendDataToSSp)) {
				validateConsumerApplication.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.UNPAID_APPLICATION_PENDING_FOR_VENDOR_APPROVAL.getId()).get());
				validateConsumerApplication.setActive(false);
				validateConsumerApplication.setDeleted(true);
				saveConsumerApplicationDetail = dryUtility.saveConsumerApplicationDetail(validateConsumerApplication);

			}
		} else {
			return ResponseEntity.ok(
					new Response(HttpCode.NOT_ACCEPTABLE, "You can not call this at this stage of application status : "
							+ validateConsumerApplication.getApplicationStatus().getApplicationStatusId()));
		}
		return ResponseEntity
				.ok(Objects.isNull(saveConsumerApplicationDetail) ? new Response(HttpCode.NULL_OBJECT, sendDataToSSp)
						: new Response(HttpCode.UPDATED, "Data Updated Successfully",
								Arrays.asList(saveConsumerApplicationDetail)));
	}

	@Autowired
	private UserRoleRepository userRoleRepository;

	@GetMapping("/getUserDetailsById/{consumerApplicationNo}")
	public ResponseEntity<?> getUserDetailsById(@PathVariable String consumerApplicationNo) throws UserException {

		List<Map<String, ?>> applicationDataByMainDataView = consumerApplicationDetailRepository
				.getApplicationDataByMainDataView(consumerApplicationNo);
		if (applicationDataByMainDataView.isEmpty()) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data Not Found for this userId "));
		}

		String cirId = String.valueOf(applicationDataByMainDataView.get(0).get("CIR_ID"));

		List<User> byCircleId = userRepository.findDataByCircleId(cirId);
		List<Long> list = new ArrayList<>();

		for (User user : byCircleId) {
			UserRole userRoleDB = userRoleRepository.findByAdUserIdAndRoleId(user.getAdUserId());
			if (Objects.nonNull(userRoleDB)) {
				list.add(userRoleDB.getUser());
			}
		}

		List<User> totalAeStcList = new ArrayList<>();
		for (Long adUserId : list) {
			User userDetailsById = userRepository.findByAdUserId(adUserId).orElseThrow(() -> new UserException(
					new Response(HttpCode.NULL_OBJECT, "User Not found for this userId" + adUserId.toString())));
			totalAeStcList.add(userDetailsById);
		}

		return ResponseEntity.ok(Objects.isNull(totalAeStcList) ? new Response(HttpCode.NULL_OBJECT, "Data Not Found")
				: new Response(HttpCode.OK, "Data Retreived Successfully", Arrays.asList(totalAeStcList)));

	}

	@ApiOperation(value = "Update Erp table By sortContratoreListByKvLine  ", notes = "no value pass", response = Response.class, responseContainer = "List", tags = RestApiUrl.CONSUMER_APPLICATION_DETAIL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PutMapping("/sortContratoreListByKvLine")
	public ResponseEntity<Response<?>> jeLoadApplication(@RequestParam String consumerApplicationNumber,
			@RequestParam Long sortContratoreListByKvLine) {

		System.out.println("sortContratoreListByKvLine method is calling !!!");

		Response<ErpEstimateAmountData> response = new Response();

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNumber);
		ErpEstimateAmountData findByErpNo = estimateAmountRepository
				.findByErpNo(findByConsumerApplicationNumber.getErpWorkFlowNumber());
		findByErpNo.setSortContratoreListByKvLine(sortContratoreListByKvLine);
		ErpEstimateAmountData save = estimateAmountRepository.save(findByErpNo);
		response.setCode("200");
		response.setMessage("Data Update Successfully");
		response.setList(Arrays.asList(save));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getSampleData1/{flagNo}")
	public ResponseEntity<?> getConsumerApplicationDataFromView1(@PathVariable Long flagNo) {

		List<Map<String, ?>> reSampleData = consumerApplicationDetailRepository.getReSampleData(flagNo);

		return ResponseEntity.ok(Objects.isNull(reSampleData)
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", reSampleData));

	}

	@PutMapping("/changeConsumerMobileNumber1")
	public ResponseEntity<?> changeConsumerMobileNumber1(@RequestPart String oldLoginId,
			@RequestPart String newMobileNo, @RequestPart MultipartFile file) {
		Response<Consumer> response = new Response<>();
		try {

			Consumer consumer1 = consumerRepository.findByConsumerLoginId(newMobileNo).orElse(null);
			if (consumer1 != null) {
				response.setCode("406");
				response.setMessage("This mobile no is already registered");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			Consumer consumer = consumerRepository.findByConsumerLoginId(oldLoginId).orElse(null);
			if (consumer == null) {
				response.setCode("100");
				response.setMessage("Consuer Not Found With This Login Id");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			ApplicationDocument applicationDocument = null;
			applicationDocument = applicationDocumentRepository
					.findByconsumerApplicationDetail_consumer(consumer.getConsumerId());

//			 applicationDocument = applicationDocumentRepository.findByConsumer(consumer);

			if (applicationDocument == null) {
				applicationDocument = new ApplicationDocument();
			}
			if (file != null) {

				Upload uploadFile = uploadService.uploadFile(file, "CHANGE_MOBILE_NUMBER_FILE");
				if (uploadFile == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Registry not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setMobileNumberChangeFile(uploadFile);
				applicationDocument.setConsumer(consumer);
				applicationDocumentRepository.save(applicationDocument);
			}

			consumer.setConsumerLoginId(newMobileNo);
			consumer.setConsumerMobileNo(newMobileNo);

			List<ConsumerApplicationDetail> findByConsumerId = consumerApplicationDetailRepository
					.findByConsumerId(consumer.getConsumerId());
			for (ConsumerApplicationDetail consumerDetail : findByConsumerId) {
				consumerDetail.setConsumerphonNumber(newMobileNo);
				consumerApplicationDetailRepository.save(consumerDetail);
			}

			Consumer save = consumerRepository.save(consumer);
			response.setCode("200");
			response.setMessage("Consumer Mobile Number Updated Successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();

			response.setCode("404");
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/jePutErpNumberAndChangeApplicationStatusAndSchemeCode/{consumerAppNo}/{erpNumber}/{SchemeCode}")
	public ResponseEntity<?> jePutErpNumberAndChangeApplicationStatusAndSchemeCode(@PathVariable String consumerAppNo,
			@PathVariable String erpNumber, @PathVariable String SchemeCode) {
		Response response = new Response();
		ConsumerApplicationDetail findByConsumerApplicationNumber = null;
		try {

			System.err.println("aaaaaaaaaaaaaa : " + consumerAppNo);
			findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);

			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

//			code start 3 April 2025 with updated check for erp no. ki same erp no. kisi bhi application se tagged na ho 
//			ResponseEntity<Response> checkErpExistOrNot = dryUtility.checkErpExistOrNot(erpNumber);
//			if (checkErpExistOrNot != null && checkErpExistOrNot.getBody() != null) {
//				Response responseBody = checkErpExistOrNot.getBody(); // Extract response body
//
//				if ("This ERP Number Is Already Associated With Another Application Number."
//						.equals(responseBody.getMessage())) {
//					return checkErpExistOrNot;
//				}
//			}

			if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(7l)) {

				if ((SchemeCode.equals("SCCW")
						&& !findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L))

						|| (SchemeCode.equals("DEPOSITE")
								&& !findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(2L))
						|| (SchemeCode.equals("KMY") && !findByConsumerApplicationNumber.getNatureOfWorkType()
								.getNatureOfWorkName().equals("MKMY"))
						|| (SchemeCode.equals("OYT") && !findByConsumerApplicationNumber.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(5L))
						|| (SchemeCode.equals("RRTD") && (findByConsumerApplicationNumber.getSchemeType()
								.getSchemeTypeId().equals(1L)
								|| findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(2L)))
						|| (SchemeCode.equals("DEPOSITE")
								&& findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8l)

						|| (SchemeCode.equals("SCCW") && findByConsumerApplicationNumber.getNatureOfWorkType()
								.getNatureOfWorkTypeId() == 5l)) {

					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Scheme code not match");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
//			code changed on 24-09-2025
				ResponseEntity<Response> checkErpExistOrNot = dryUtility.checkErpExistOrNot2(erpNumber, consumerAppNo);
				if (checkErpExistOrNot != null && checkErpExistOrNot.getBody() != null) {
					Response responseBody = checkErpExistOrNot.getBody();

					// Agar ERP already system me kahin bhi present hai
					if (HttpCode.NOT_ACCEPTABLE.equals(responseBody.getCode())) {
						return checkErpExistOrNot;
					}
				}

//			code end
				findByConsumerApplicationNumber.setErpWorkFlowNumber(erpNumber);
				findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId()).get());
				consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
				response.setCode(HttpCode.OK);
				response.setMessage("Consumer Application Update");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
						"This API is not allowed for the current application status :  "
								+ findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusName()
								+ " (ID: "
								+ findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
								+ ")"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setCode(HttpCode.OK);
		response.setMessage("Application Revert To Demand Note Payment");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/checkConsumerHaveAnyGovermentApplicationOrNot/{mobileNo}")
	public Response<?> checkConsumerHaveAnyGovermentApplicationOrNot(@PathVariable String mobileNo) {

		Response<ConsumerApplicationDetail> response = new Response();

		try {
			List<ConsumerApplicationDetail> changeMobileNOByApplicationisGOV = consumerApplicationDetailRepository
					.changeMobileNOByApplicationisGOV(mobileNo);

			if (changeMobileNOByApplicationisGOV.size() == 0) {
				response.setCode("404");
				response.setMessage("data not found");
				return response;
			} else {
				response.setCode("200");
				response.setMessage("data found");
				response.setList(changeMobileNOByApplicationisGOV);
				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@GetMapping("/changeMobileNOByApplicationisGOV/{oldMobileNo}/{newMobileNo}")
	public Response<?> changeMobileNOByApplicationisGOV(@PathVariable String oldMobileNo,
			@PathVariable String newMobileNo) {

		Response<ConsumerApplicationDetail> response = new Response();

		try {
			Optional<Consumer> findByConsumerLoginId = consumerRepository.findByConsumerLoginId(newMobileNo);

			if (findByConsumerLoginId.isPresent()) {
				response.setCode("404");
				response.setMessage("Mobile Number All Ready Present");
				return response;
			} else {

				Consumer consumer = consumerRepository.findByConsumerLoginId(oldMobileNo).get();

				Consumer consumer1 = new Consumer();

//				String consumerPassword = generatePassword();
//				consumer.setConsumerCredentials(PasswordUtil.getPasswordHash(consumerPassword));
//
//				final String password = PasswordUtil.getPasswordHash(consumerPassword);

				consumer1.setConsumerCredentials(consumer.getConsumerCredentials());
				consumer1.setConsumerName(consumer.getConsumerName().trim());
				consumer1.setConsumerLoginId(newMobileNo.trim());
				consumer1.setConsumerMobileNo(newMobileNo.trim());
				consumer1.setConsumerEmailId(consumer.getConsumerEmailId().trim());
				consumer1.setActive(Boolean.TRUE);
				consumer1.setAccountNonExpired(Boolean.FALSE);
				consumer1.setAccountNonLocked(Boolean.FALSE);
				consumer1.setIsFirstLogin(Boolean.TRUE);
				consumer1.setCredentialsNonExpired(null);
				consumer1.setLoginAttemp(0L);
				consumer1.setCreated(new Date());
				consumer1.setCreatedBy(1L);
				consumer1.setUpdated(new Date());
				consumer1.setUpdatedBy(1L);
				consumer1.setLoginStatus("NEW REGISTRATION");
				consumer1.setCredentialsNonExpired(Boolean.FALSE);

				consumer1.setAddress(consumer.getAddress().trim());

				Consumer savedConsumer = consumerRepository.save(consumer1);
				List<ConsumerApplicationDetail> changeMobileNOByApplicationisGOV = null;
				if (savedConsumer != null) {
					changeMobileNOByApplicationisGOV = consumerApplicationDetailRepository
							.changeMobileNOByApplicationisGOV(oldMobileNo);

					for (ConsumerApplicationDetail cad : changeMobileNOByApplicationisGOV) {

						ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
								.findByConsumerApplicationNumber(cad.getConsumerApplicationNo());
						consumerApplicationDetail.setConsumers(savedConsumer);
						ConsumerApplicationDetail save = consumerApplicationDetailRepository
								.save(consumerApplicationDetail);

					}

					response.setCode("200");
					response.setMessage("data sucessfully update");
					response.setList(changeMobileNOByApplicationisGOV);
					return response;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Autowired
	private sendDataMeterReaderVijlanceClient sendDataMeterReaderVijlanceClient;

//	@GetMapping("/sendDataMeterReaderVijlance")
//	public Response<?> sendDataMeterReaderVijlance() {
//
//		Response<ConsumerApplicationDetail> response = new Response();
//
//		try {
//			List<ConsumerApplicationDetail> sendDataMeterReaderVijlance = consumerApplicationDetailRepository
//					.sendDataMeterReaderVijlance();
//			sendDataMeterReaderVijlance.stream().forEach(cad -> {
//
//				String inputDate = cad.getCreated().toString();
//
//				// Step 1: Define the input pattern
//				DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy HH.mm.ss.SSSSSSSSS",
//						Locale.ENGLISH);
//
//				// Step 2: Parse the string into LocalDateTime
//				LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
//
//				// Step 3: Define output pattern (YYYYMM)
//				DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMM");
//
//				// Step 4: Format the date
//				String formattedDate = dateTime.format(outputFormatter);
//
//				sendDataMeterReaderVijlanceClient.sendDataMeterReaderVigilance(cad.getConsumerApplicationNo(),
//						formattedDate, cad.getDistributionCenter().getNgbDcCd(),
//						"OYT CONNECTION IS PANDING FOR PUNCHING", cad.getCreated().toString(), cad.getConsumerName(),
//						cad.getAddress(), null, null, null, null, null, null, cad.getConsumerphonNumber(), null, null,
//						null, null, "OYT की एप्लीकेशन में IVRS पंच नहीं किया गया हैं");
//
//			});
//
//			response.setCode("200");
//			response.setList(sendDataMeterReaderVijlance);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}

	@GetMapping("/changeMobileNOByApplicationisGOV1")
	public Response<?> changeMobileNOByApplicationisGOV1(@RequestPart String oldMobileNo,
			@RequestPart String newMobileNo, @RequestPart MultipartFile file) {

		Response<ConsumerApplicationDetail> response = new Response();

		try {
			Optional<Consumer> findByConsumerLoginId = consumerRepository.findByConsumerLoginId(newMobileNo);

			if (findByConsumerLoginId.isPresent()) {
				response.setCode("404");
				response.setMessage("Mobile Number All Ready Present");
				return response;
			} else {

				Consumer consumer = consumerRepository.findByConsumerLoginId(oldMobileNo).get();

				Consumer consumer1 = new Consumer();

//				String consumerPassword = generatePassword();
//				consumer.setConsumerCredentials(PasswordUtil.getPasswordHash(consumerPassword));
//
//				final String password = PasswordUtil.getPasswordHash(consumerPassword);

				consumer1.setConsumerCredentials(consumer.getConsumerCredentials());
				consumer1.setConsumerName(consumer.getConsumerName().trim());
				consumer1.setConsumerLoginId(newMobileNo.trim());
				consumer1.setConsumerMobileNo(newMobileNo.trim());
				consumer1.setConsumerEmailId(consumer.getConsumerEmailId().trim());
				consumer1.setActive(Boolean.TRUE);
				consumer1.setAccountNonExpired(Boolean.FALSE);
				consumer1.setAccountNonLocked(Boolean.FALSE);
				consumer1.setIsFirstLogin(Boolean.TRUE);
				consumer1.setCredentialsNonExpired(null);
				consumer1.setLoginAttemp(0L);
				consumer1.setCreated(new Date());
				consumer1.setCreatedBy(1L);
				consumer1.setUpdated(new Date());
				consumer1.setUpdatedBy(1L);
				consumer1.setLoginStatus("NEW REGISTRATION");
				consumer1.setCredentialsNonExpired(Boolean.FALSE);

				consumer1.setAddress(consumer.getAddress().trim());

				ApplicationDocument applicationDocument = null;
				applicationDocument = applicationDocumentRepository
						.findByconsumerApplicationDetail_consumer(consumer.getConsumerId());

//				 applicationDocument = applicationDocumentRepository.findByConsumer(consumer);

				if (applicationDocument == null) {
					applicationDocument = new ApplicationDocument();
				}
				if (file != null) {

					Upload uploadFile = uploadService.uploadFile(file, "CHANGE_MOBILE_NUMBER_FILE");
					if (uploadFile == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Document Registry not uploaded.");
						throw new ConsumerApplicationDetailException(response);
					}
					applicationDocument.setMobileNumberChangeFile(uploadFile);
					applicationDocument.setConsumer(consumer);
					applicationDocumentRepository.save(applicationDocument);
				}

				Consumer savedConsumer = consumerRepository.save(consumer1);
				List<ConsumerApplicationDetail> changeMobileNOByApplicationisGOV = null;
				if (savedConsumer != null) {
					changeMobileNOByApplicationisGOV = consumerApplicationDetailRepository
							.changeMobileNOByApplicationisGOV(oldMobileNo);

					for (ConsumerApplicationDetail cad : changeMobileNOByApplicationisGOV) {

						ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
								.findByConsumerApplicationNumber(cad.getConsumerApplicationNo());
						consumerApplicationDetail.setConsumers(savedConsumer);
						ConsumerApplicationDetail save = consumerApplicationDetailRepository
								.save(consumerApplicationDetail);

					}

					response.setCode("200");
					response.setMessage("data sucessfully update");
					response.setList(changeMobileNOByApplicationisGOV);
					return response;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@GetMapping("/sendDataMeterReadervigilanceGet1/{ByNgbDcCode}")
	public Response<?> sendDataMeterReaderVijlance1(@PathVariable String ByNgbDcCode) {

		Response<ConsumerApplicationDetail> response = new Response();

		try {
			DistributionCenter distributionCenter = distributionCenterRepository
					.findDistributionCenterByNgbDcCd(ByNgbDcCode);

			List<ConsumerApplicationDetail> sendDataMeterReaderVijlance = consumerApplicationDetailRepository
					.sendDataMeterReaderVijlance(distributionCenter.getDcId());
			if (sendDataMeterReaderVijlance.size() > 0) {
				response.setCode("200");
				response.setList(sendDataMeterReaderVijlance);
				return response;
			}
			response.setCode("400");
			response.setList(sendDataMeterReaderVijlance);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@PostMapping("/punchNamaDoneApi/{consumerApplicatonNO}/{punchNamaReferemceNo}/{punchNamaReferenceDate}")
	public Response<?> punchNamaDoneApi(@PathVariable String consumerApplicatonNO,
			@PathVariable String punchNamaReferemceNo, @PathVariable String punchNamaReferenceDate) {

		Response<ConsumerApplicationDetail> response = new Response();

		try {
			ConsumerApplicationDetail changeMobileNOByApplicationisGOV = consumerApplicationDetailRepository
					.findByConsumerApplicationNo_11(consumerApplicatonNO);

			if (changeMobileNOByApplicationisGOV == null) {
				response.setCode("404");
				response.setMessage("data not found");
				return response;
			} else {

				changeMobileNOByApplicationisGOV.setPunchNamaCreatedDate(punchNamaReferenceDate);
				changeMobileNOByApplicationisGOV.setPunchNamaReferenceNo(punchNamaReferemceNo);
				ConsumerApplicationDetail save = consumerApplicationDetailRepository
						.save(changeMobileNOByApplicationisGOV);

				response.setCode("200");
				response.setMessage("data Update");
				response.setList(Arrays.asList(save));

				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@GetMapping("/sendDataMeterReadervigilanceGet2/{ByNgbDcCode}")
	public Response<?> sendDataMeterReaderVijlance2(@PathVariable String ByNgbDcCode) {

		Response<Map<String, String>> response = new Response();

		try {
			DistributionCenter distributionCenter = distributionCenterRepository
					.findDistributionCenterByNgbDcCd(ByNgbDcCode);
			if (distributionCenter == null) {
				response.setCode("400");
				response.setMessage("Please Submit Correct NGB DC code");
				return response;
			}
			List<Map<String, String>> sendDataMeterReaderVijlance = consumerApplicationDetailRepository
					.sendDataMeterReaderVijlance1(distributionCenter.getDcId());
			if (sendDataMeterReaderVijlance.size() > 0) {
				response.setCode("200");
				response.setMessage("data successfully returned");
				response.setList(sendDataMeterReaderVijlance);
				return response;
			}
			response.setCode("400");
			response.setMessage("data not found");
			response.setList(sendDataMeterReaderVijlance);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

//	 ye api temporary wala code chalega

	@PostMapping("/changCalulationForOyt/{applicationNumber}/{OytTepOrParma}")
	public Response changCalulationForOyt(@PathVariable String applicationNumber, @PathVariable String OytTepOrParma) {

		Response<ConsumerApplicationDetail> response = new Response<ConsumerApplicationDetail>();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(applicationNumber);
		consumerApplicationDetail.setOytTepOrParma(OytTepOrParma);
		if (OytTepOrParma.equals("temporary")) {
			consumerApplicationDetail.setOytTempApplicationNo(applicationNumber + "T");
		}
		consumerApplicationDetailRepository.save(consumerApplicationDetail);
		response.setCode("200");
		response.setMessage("data update successfully update");

		return response;

	}

	@PostMapping("/demandNoteApprovalByGM")
	public ResponseEntity<?> demandNoteApprovalByGM(@RequestBody ApprovalDto dto, BindingResult bindingResult)
			throws FormValidationException, ConsumerApplicationDetailException {
		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});
			Response response = new Response<>();
			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
			response.setError(errorList);
			throw new FormValidationException(response);
		}
		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(dto.getConsumerApplicationNO());

		System.err.println("validateConsumerApplication : " + validateConsumerApplication);
		validateConsumerApplication.setApplicationStatus(applicationStatusRepository
				.findById(dto.getApprovedOrNo() ? ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId()
						: ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId())
				.get());
		validateConsumerApplication.setGmDemandAppDate(LocalDateTime.now().toString());
		validateConsumerApplication.setGmDemandApproved(dto.getApprovedOrNo().toString());
		validateConsumerApplication.setGmDemandUserId(dto.getUserId());
		validateConsumerApplication.setGmDemandRemark(dto.getUserRemark());
		ConsumerApplicationDetail saveConsumerApplicationDetail = dryUtility
				.saveConsumerApplicationDetail(validateConsumerApplication);
		return ResponseEntity.ok(new Response<>(HttpCode.UPDATED, "Data Updated Successfully",
				Arrays.asList(saveConsumerApplicationDetail)));
	}

}
