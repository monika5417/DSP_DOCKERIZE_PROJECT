package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorDetail;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.District;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.IndividualOrGroup;
import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.domain.LoadRequested;
import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;
import com.mpcz.deposit_scheme.backend.api.domain.RejectProposalApplicationStatusHis;
import com.mpcz.deposit_scheme.backend.api.domain.Role;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.SupplyVoltage;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationDetailsFilterDTO;
import com.mpcz.deposit_scheme.backend.api.dto.ErpEstimateCalculatedDto;
import com.mpcz.deposit_scheme.backend.api.dto.GeoPendingStatusConsmersListResponse;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.enums.VendorEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDocumentException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWorkException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.SMSDirectServiceException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.SupplyVoltageException;
import com.mpcz.deposit_scheme.backend.api.exception.TariffCategoryException;
import com.mpcz.deposit_scheme.backend.api.exception.TaskTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplicationSurveyRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DivisionRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.RejectProposalApplicationStatusHisRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SubDivisionRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UploadRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailForm;
import com.mpcz.deposit_scheme.backend.api.response.Meta;
import com.mpcz.deposit_scheme.backend.api.response.PageConsumerApplicationDetailDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationDocumentService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationHeadChargesService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationTypeService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationSurveyService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.ContractorDetailService;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.services.DistrictService;
import com.mpcz.deposit_scheme.backend.api.services.DocumentTypeService;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;
import com.mpcz.deposit_scheme.backend.api.services.FeederService;
import com.mpcz.deposit_scheme.backend.api.services.GeoLocationService;
import com.mpcz.deposit_scheme.backend.api.services.IndividualOrGroupService;
import com.mpcz.deposit_scheme.backend.api.services.LandAreaUnitService;
import com.mpcz.deposit_scheme.backend.api.services.LoadRequestedService;
import com.mpcz.deposit_scheme.backend.api.services.NatureOfWorkService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;
import com.mpcz.deposit_scheme.backend.api.services.SubstationService;
import com.mpcz.deposit_scheme.backend.api.services.SupplyVoltageService;
import com.mpcz.deposit_scheme.backend.api.services.TariffCategoryService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.services.UserService;
import com.mpcz.deposit_scheme.backend.api.services.VendorService;
import com.mpcz.deposit_scheme.backend.api.services.WorkTypeService;
import com.mpcz.deposit_scheme.backend.api.util.SequenceGeneratorUtil;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;

@Service
public class ConsumerApplicationDetailServiceImpl implements ConsumerApplicationDetailService {

	Logger logger = LoggerFactory.getLogger(ConsumerApplicationDetailServiceImpl.class);

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private SchemeTypeService schemeTypeService;

	@Autowired
	private DocumentTypeService documentTypeService;

	@Autowired
	private WorkTypeService workTypeService;

	@Autowired
	private DistributionCenterService distributionCenterService;

	@Autowired
	private SubstationService substationService;

	@Autowired
	private FeederService feederService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private UserService userService;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	ConsumerApplicationSurveyService consumerApplicationSurveyService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	private SMSDirectService smsDirectService;

	@Autowired
	private ContractorDetailService contractorDetailService;

	@Autowired
	private ApplicationHeadChargesService applicationHeadChargesService;

	@Autowired
	private ApplicationTypeService applicationTypeService;

	@Autowired
	private ApplicationDocumentService applicationDocumentService;

	@Autowired
	private SupplyVoltageService supplyVoltageService;

	@Autowired
	private TariffCategoryService tariffCategoryService;

	@Autowired
	private DistrictService districtService;

//	@Autowired
//	private TaskTypeService taskTypeService;

	@Autowired
	private NatureOfWorkService natureOfWorkService;

	@Autowired
	private DivisionRepository divisionRepository;

	@Autowired
	SubDivisionRepository subDivisionRepository;

	@Autowired
	GeoLocationService geoLocationService;

	@Autowired
	private LoadRequestedService loadRequestedService;

	@Autowired
	private LandAreaUnitService landAreaUnitService;

	@Autowired
	private IndividualOrGroupService individualOrGroupService;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ErpEstimateAmountService erpEstimateAmountService;

	@Autowired
	private UploadRepository uploadRepository;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	ConsumerApplicationSurveyRepository consumerApplicationSurveyRepository;

	@Autowired
	RejectProposalApplicationStatusHisRepository rejectProposalApplicationStatusHisRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ConsumerApplicationDetail saveConsumerApplicationDetail(
			ConsumerApplicationDetailForm consumerApplicationDetailForm,
			/*
			 * MultipartFile docAadhar, MultipartFile docPan, MultipartFile docEnergyBill,
			 * MultipartFile docRegistoryOrLease, MultipartFile docResidentialProof,
			 * MultipartFile docBullidingPermissonOrTncpApproval,
			 */ MultipartFile docRow, MultipartFile docRegistry, MultipartFile docNagarNigamPermission,
			MultipartFile docDicOrGumasta, MultipartFile docShapathPatra, MultipartFile docTestReport,
			MultipartFile docT$cpPermission, MultipartFile docReraPermission, MultipartFile docDrawingNotarized,
			MultipartFile docColonyPrakoshth, MultipartFile docColonyLicence, MultipartFile docLoadSheet,
			MultipartFile docNoc, MultipartFile docAllPaperNotarized03Set, MultipartFile docCommittee,
			MultipartFile docDiversion, MultipartFile docNazul, MultipartFile docMap, MultipartFile docKhasraKhatoni,
			MultipartFile docMapCivilEngineer, MultipartFile docApplicationConsent, MultipartFile docPerforma5A,
			MultipartFile docPerforma5B, MultipartFile docPlotAreaDetailsWithOwner, MultipartFile docIndividualOrGroup,
			MultipartFile docAdministrative, MultipartFile docGst, MultipartFile energyBillDoc,
			MultipartFile docSamagraFile) throws ConsumerApplicationDetailException, DocumentTypeException,
			ConsumerApplicationSurveyException, SMSDirectServiceException, PaymentTypeException,
			ApplicationHeadChargesException, ApplicationDocumentException, SupplyVoltageException,
			TariffCategoryException, TaskTypeException, NatureOfWorkException {

		final String method = "ConsumerApplicationDetailServiceImpl : saveConsumerApplicationDetail()";

		logger.info(method);

		Response<ConsumerApplicationDetail> response = new Response<>();

		ConsumerApplicationDetail consumerApplicationDetailResponse = null;

		if (Objects.isNull(consumerApplicationDetailForm)) {
			logger.error("Consumer Application Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ConsumerApplicationDetailException(response);
		} else {

			ConsumerApplicationDetail consumerApplicationDetail = new ConsumerApplicationDetail();

			Upload rowUpload = null;
			Upload registryUpload = null;
			Upload nagarNigamPermissionUpload = null;
			Upload dicOrGumastaUpload = null;
			Upload shapathPatraUpload = null;
			Upload testReportUpload = null;
			Upload t$cpPermissionUpload = null;
			Upload reraPermissionUpload = null;
			Upload drawingNotarizedUpload = null;
			Upload colonyPrakoshthUpload = null;
			Upload colonyLicenceUpload = null;
			Upload loadSheetUpload = null;
			Upload nocUpload = null;
			Upload allPaperNotarized03SetUpload = null;
			Upload committeeUpload = null;
			Upload diversionUpload = null;
			Upload nazulUpload = null;
			Upload mapUpload = null;
			Upload khasraKhatoniUpload = null;
			Upload mapCivilEngineerUpload = null;
			Upload applicationConsentUpload = null;
			Upload performa5AUpload = null;
			Upload performa5BUpload = null;
			Upload plotAreaDetailsWithOwnerUpload = null;
			Upload docAdministrativeUpload = null;
			Upload docGstUpload = null;
			Upload docGroupUpload = null;
			Upload energyBillDocUpload = null;
			Upload SamagraFileUpload = null;

			/*********** validations start ***********************/

			if (consumerApplicationDetailForm.getConsumerName() == null
					|| consumerApplicationDetailForm.getConsumerName().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Consumer Name");
				throw new ConsumerApplicationDetailException(response);

			}

			if (consumerApplicationDetailForm.getGuardianName() == null
					|| consumerApplicationDetailForm.getGuardianName().equals("")) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Father/Husband Name");
				throw new ConsumerApplicationDetailException(response);

			}

			if (consumerApplicationDetailForm.getAddress() == null
					|| consumerApplicationDetailForm.getAddress().equals("")) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Address");
				throw new ConsumerApplicationDetailException(response);

			}

			consumerApplicationDetail.setAvedakKaPrakar(consumerApplicationDetailForm.getAvedakKaPrakar());
			consumerApplicationDetail.setAvedakRemark(consumerApplicationDetailForm.getAvedakRemark());
			consumerApplicationDetail.setVoltageLevel(consumerApplicationDetailForm.getVoltageLevel());
			NatureOfWork natureOfWork = null;
			try {
				natureOfWork = natureOfWorkService
						.findByNatureOfWorkId(consumerApplicationDetailForm.getNatureOfWorkTypeId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!natureOfWork.getNatureOfWorkName().equals("Line Shifting (Non Government)")
					&& !natureOfWork.getNatureOfWorkName().equals("Line Shifting (Goverment)")) {

			}

			if (consumerApplicationDetailForm.getShortDescriptionOfWork() == null
					|| consumerApplicationDetailForm.getShortDescriptionOfWork().equals("")) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Short description of work");
				throw new ConsumerApplicationDetailException(response);

			}

			if (consumerApplicationDetailForm.getSchemeTypeId() != null) {

				if (consumerApplicationDetailForm.getSchemeTypeId() == null
						|| consumerApplicationDetailForm.getSchemeTypeId().compareTo(0l) <= 0) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please Select Scheme Type ");
					throw new ConsumerApplicationDetailException(response);
				}
			}

			if (consumerApplicationDetailForm.getAddress() == null
					|| consumerApplicationDetailForm.getAddress().equals("")) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Address");
				throw new ConsumerApplicationDetailException(response);

			}

			if (consumerApplicationDetailForm.getDistrictId() == null
					|| consumerApplicationDetailForm.getDistrictId().compareTo(0l) <= 0) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Select District Id ");
				throw new ConsumerApplicationDetailException(response);

			}

			ApplicationDocument applicationDocument = new ApplicationDocument();

			NatureOfWork natureOfWorkdb = null;

			if (consumerApplicationDetailForm.getNatureOfWorkTypeId() != null) {

				try {

					natureOfWorkdb = natureOfWorkService
							.findByNatureOfWorkId(consumerApplicationDetailForm.getNatureOfWorkTypeId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (natureOfWorkdb != null) {
//					this is for Line Shifting
				if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(1l) == 0)) {

				}
//					this is for NSC eccetion
				else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(2l) == 0)) {

				}

				else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(8l) == 0)) {

				}
//					this is for colony eletrifacation  legal
				else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(3l) == 0)) {

					if (consumerApplicationDetailForm.getColonyLegalSelectionType() == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please Choose Building Type");
						throw new ConsumerApplicationDetailException(response);
					} else {
						consumerApplicationDetail.setColonyLegalSelectionType(
								consumerApplicationDetailForm.getColonyLegalSelectionType());
					}
					if (docReraPermission == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload docReraPermission document");
						throw new ConsumerApplicationDetailException(response);
					}

					if (docT$cpPermission == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload docT$cpPermission With Owner document");
						throw new ConsumerApplicationDetailException(response);
					}

				}
//					this is for colony eletrifacation  illegal
				else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(4l) == 0)) {

					if (consumerApplicationDetailForm.getColonyIllegalSelectionType() == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please Choose Colony Type");
						throw new ConsumerApplicationDetailException(response);
					} else {
						consumerApplicationDetail.setColonyIllegalSelectionType(
								consumerApplicationDetailForm.getColonyIllegalSelectionType());
					}

					if (docRegistry == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload Registry document");
						throw new ConsumerApplicationDetailException(response);
					}

					if (docNoc == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload NOC document");
						throw new ConsumerApplicationDetailException(response);
					}

				}

				// this is for LineShifting(Govermnet)
				else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(6l) == 0)
						|| (natureOfWorkdb.getNatureOfWorkTypeId().compareTo(12l) == 0)) {

					if (docAdministrative == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload Doc Administrative document");
						throw new ConsumerApplicationDetailException(response);
					}
				}

				// this is for OYT + kisan yojna
				else if (natureOfWorkdb.getNatureOfWorkTypeId().compareTo(5l) == 0) {

					if (docKhasraKhatoni == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload Doc Khasar-Khatoni document");
						throw new ConsumerApplicationDetailException(response);
					}
					if (consumerApplicationDetailForm.getSamagraId() != null) {
						if (docSamagraFile == null) {
							response.setCode(HttpCode.NULL_OBJECT);
							response.setMessage("Please upload Samagra File document");
							throw new ConsumerApplicationDetailException(response);
						}
					}
					if (consumerApplicationDetailForm.getSamagraId() != null) {

						consumerApplicationDetail.setSamagraId(consumerApplicationDetailForm.getSamagraId());
					}
				} else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(7l) == 0)) {
					if (energyBillDoc == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload Doc energyBillDoc document");
						throw new ConsumerApplicationDetailException(response);
					}
				} else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(10l) == 0)) {

					if (docLoadSheet == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload Load Sheet document");
						throw new ConsumerApplicationDetailException(response);
					}

					if (docMap == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload document Map");
						throw new ConsumerApplicationDetailException(response);
					}

				}

				consumerApplicationDetail.setNatureOfWorkType(natureOfWorkdb);

			}

			/*********** validations ends ************************/
			if (docRegistry != null) {
				registryUpload = uploadService.uploadFile(docRegistry, "REGISTRY");
				if (registryUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Registry not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocRegistry(registryUpload);
			}

			if (docRegistry != null) {
				registryUpload = uploadService.uploadFile(docRegistry, "REGISTRY");
				if (registryUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Registry not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocRegistry(registryUpload);
			}

			if (docT$cpPermission != null) {
				t$cpPermissionUpload = uploadService.uploadFile(docT$cpPermission, "T$CP_PERMISSION");
				if (t$cpPermissionUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document T&CP Permission not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocT$cpPermission(t$cpPermissionUpload);
			}
			if (docReraPermission != null) {
				reraPermissionUpload = uploadService.uploadFile(docReraPermission, "RERA_PERMISSION");
				if (reraPermissionUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Rera Permission not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocReraPermission(reraPermissionUpload);
			}
			if (docNoc != null) {
				nocUpload = uploadService.uploadFile(docNoc, "NOC");
				if (nocUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document NOC not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocNoc(nocUpload);
			}

			if (docIndividualOrGroup != null) {
				docGroupUpload = uploadService.uploadFile(docIndividualOrGroup, "Group_File");
				if (docGroupUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Groupfile not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocGroup(docGroupUpload);
			}

			if (docAdministrative != null) {
				docAdministrativeUpload = uploadService.uploadFile(docAdministrative, "Administrative");
				if (docAdministrativeUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Administrative not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocAdministrative(docAdministrativeUpload);
			}

			// 999
			if (docKhasraKhatoni != null) {
				khasraKhatoniUpload = uploadService.uploadFile(docKhasraKhatoni, "KHASRA_KHATONI");
				if (khasraKhatoniUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Khasar-Khatoni not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocKhasraKhatoni(khasraKhatoniUpload);
			}
			if (docGst != null) {
				docGstUpload = uploadService.uploadFile(docGst, "GST");
				applicationDocument.setDocGst(docGstUpload);
			}
			if (energyBillDoc != null) {
				energyBillDocUpload = uploadService.uploadFile(energyBillDoc, "energyBillDoc");
				if (energyBillDocUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document energyBillDoc not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocEnergyBillFile(energyBillDocUpload);
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

			if (docLoadSheet != null) {
				loadSheetUpload = uploadService.uploadFile(docLoadSheet, "LOAD_SHEET");
				if (loadSheetUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Load Sheet not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocLoadSheet(loadSheetUpload);
			}

			if (docMap != null) {
				mapUpload = uploadService.uploadFile(docMap, "MAP");
				if (mapUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document MAP not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				applicationDocument.setDocMap(mapUpload);
			}

			if (consumerApplicationDetailForm.getWorkTypeId() != null) {
				try {
					WorkType workTypeDb = workTypeService
							.findByWorkTypeId(consumerApplicationDetailForm.getWorkTypeId());
					if (workTypeDb != null) {
						consumerApplicationDetail.setWorkType(workTypeDb);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

//			ApplicationStatus appStatusDb = applicationStatusService.findById(1L);

			ApplicationStatus appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_GIS_LOCATION.getId());

			consumerApplicationDetail.setConsumerName(consumerApplicationDetailForm.getConsumerName().trim());
			consumerApplicationDetail.setGuardianName(consumerApplicationDetailForm.getGuardianName().trim());
			consumerApplicationDetail.setAddress(consumerApplicationDetailForm.getAddress().trim());
			consumerApplicationDetail.setArea(consumerApplicationDetailForm.getArea());

			// Written By Abhishek Start
			consumerApplicationDetail.setApplicationStatus(appStatusDb);
			// end

			if (consumerApplicationDetailForm.getBillNo() != null
					&& !consumerApplicationDetailForm.getBillNo().equals("")) {
				consumerApplicationDetail.setBillNo(consumerApplicationDetailForm.getBillNo().trim());
			}
			if (consumerApplicationDetailForm.getPanNo() != null
					&& !consumerApplicationDetailForm.getPanNo().equals("")) {
				consumerApplicationDetail.setPanNo(consumerApplicationDetailForm.getPanNo().trim());
			}

			consumerApplicationDetail
					.setShortDescriptionOfWork(consumerApplicationDetailForm.getShortDescriptionOfWork());

			consumerApplicationDetail.setPincode(consumerApplicationDetailForm.getPinCode());
			if (consumerApplicationDetailForm.getAadharNo() != null
					&& !consumerApplicationDetailForm.getAadharNo().equals("")) {
				consumerApplicationDetail.setAadharNo(consumerApplicationDetailForm.getAadharNo().trim());
			}

			if (consumerApplicationDetailForm.getIvrsNo() != null
					&& !consumerApplicationDetailForm.getIvrsNo().equals("")) {
				consumerApplicationDetail.setIvrsNo(consumerApplicationDetailForm.getIvrsNo().trim());
			}
			if (consumerApplicationDetailForm.getWorkAllocationAddress() != null
					&& !consumerApplicationDetailForm.getWorkAllocationAddress().equals("")) {
				consumerApplicationDetail
						.setWorkAllocationAddress(consumerApplicationDetailForm.getWorkAllocationAddress().trim());
			}

			if (consumerApplicationDetailForm.getWorkLocationAddresscb() == null) {
				consumerApplicationDetail.setWorkLocationAddresscb(false);
			} else {
				consumerApplicationDetail.setWorkLocationAddresscb(true);
			}
			if (consumerApplicationDetailForm.getLoadRequested() != null) {
				consumerApplicationDetail.setLoadRequested(consumerApplicationDetailForm.getLoadRequested());
			}
			if (consumerApplicationDetailForm.getLoadRequested() != null) {
				LoadRequested loadRequestedDb = loadRequestedService
						.findById(consumerApplicationDetailForm.getLoadRequestedId());
				consumerApplicationDetail.setLoadRequestedId(loadRequestedDb);
			}
			if (consumerApplicationDetailForm.getLandAreaUnitId() != null) {
				LandAreaUnit landAreaUnit = landAreaUnitService
						.findById(consumerApplicationDetailForm.getLandAreaUnitId());

				consumerApplicationDetail.setLandAreaUnit(landAreaUnit);
			}
			if (consumerApplicationDetailForm.getLandAreaUnitName() != null) {
				consumerApplicationDetail.setLandAreaUnitName(consumerApplicationDetailForm.getLandAreaUnitName());
			}
			if (consumerApplicationDetailForm.getIndividualOrGroupId() != null) {
				IndividualOrGroup individualOrGroup = individualOrGroupService
						.findById(consumerApplicationDetailForm.getIndividualOrGroupId());

				consumerApplicationDetail.setIndividualOrGroup(individualOrGroup);
			}
			if (consumerApplicationDetailForm.getNoOfPlot() != null) {
				consumerApplicationDetail.setNoOfPlot(consumerApplicationDetailForm.getNoOfPlot());
			}

			if (consumerApplicationDetailForm.getKhasra() != null) {
				consumerApplicationDetail.setKhasra(consumerApplicationDetailForm.getKhasra());
			}

			if (consumerApplicationDetailForm.getKhatoni() != null) {
				consumerApplicationDetail.setKhatoni(consumerApplicationDetailForm.getKhatoni());
			}

			if (consumerApplicationDetailForm.getGstNumber() != null) {
				consumerApplicationDetail.setGstNumber(consumerApplicationDetailForm.getGstNumber());
			}

			if (consumerApplicationDetailForm.getGstNumberProvide() != null) {
				consumerApplicationDetail.setGstNumberProvide(consumerApplicationDetailForm.getGstNumberProvide());
			}

//			consumerApplicationDetail.setGoodMaterialOrnot(0);
			SchemeType schemeTypeDb = null;
			SupplyVoltage supplyVoltageDb = null;

			if (consumerApplicationDetailForm.getSupplyVoltageId() == null) {
				consumerApplicationDetail.setDtr(null);
				consumerApplicationDetail.setPtr(null);
				consumerApplicationDetail.setLt(null);
				consumerApplicationDetail.setHt11Kv(null);
				consumerApplicationDetail.setHt33Kv(null);
				consumerApplicationDetail.setHt132Kv(null);

			} else {

				for (Long s : consumerApplicationDetailForm.getSupplyVoltageId()) {
					System.out.println("************************************************++++++ ========" + s);
					supplyVoltageDb = supplyVoltageService.findBySupplyVoltageId(s);

					if (s.equals(3l)) {
						consumerApplicationDetail.setDtr(3l);
					} else if (s.equals(4l)) {
						consumerApplicationDetail.setPtr(4l);
					} else if (s.equals(5l)) {
						consumerApplicationDetail.setLt(5l);
					} else if (s.equals(6l)) {
						consumerApplicationDetail.setHt11Kv(6l);
					} else if (s.equals(7l)) {
						consumerApplicationDetail.setHt33Kv(7l);
					} else if (s.equals(8l)) {
						consumerApplicationDetail.setHt132Kv(8l);
					}
				}
			}

			if (consumerApplicationDetailForm.getSchemeTypeId() != null) {
				try {
					schemeTypeDb = schemeTypeService
							.findBySchemeTypeId(consumerApplicationDetailForm.getSchemeTypeId());
					consumerApplicationDetail.setSchemeType(schemeTypeDb);

					if (schemeTypeDb != null
							&& !(schemeTypeDb.getSchemeTypeId().compareTo(VendorEnum.OYT.getId()) == 0)) {
						consumerApplicationDetail.setSchemeType(schemeTypeDb);

						if (schemeTypeDb.getSchemeTypeId().compareTo(VendorEnum.MPMKVVCL.getId()) == 0) {
							consumerApplicationDetail.setVendor(VendorEnum.MPMKVVCL.getValue());
							consumerApplicationDetail.setContractorId(null);
							consumerApplicationDetail.setContractorName("NA");
						} else if (schemeTypeDb.getSchemeTypeId().compareTo(VendorEnum.CONTRACTOR.getId()) == 0
								&& consumerApplicationDetailForm.getContractorUserId() != null
								&& !consumerApplicationDetailForm.getContractorUserId().equals("")) {
							consumerApplicationDetail.setVendor(VendorEnum.CONTRACTOR.getValue());

//							sandeep, starts here

							ContractorDetail contractorDetailResponse = null;

							if (consumerApplicationDetailForm.getContractorUserId() != null) {

								ContractorDetail contractorDetailDb = null;

								ContractorDetail contractorDetail = new ContractorDetail();

								contractorDetailDb = contractorDetailService
										.findByContractorUserId(consumerApplicationDetailForm.getContractorUserId());

								if (contractorDetailDb == null) {
									contractorDetail
											.setContractorUserId(consumerApplicationDetailForm.getContractorUserId());
									contractorDetail.setContractorAuthenticationId(
											consumerApplicationDetailForm.getContractorAuthenticationId().trim());
									contractorDetail.setContractorUserZone(
											consumerApplicationDetailForm.getContractorUserZone().trim());
									contractorDetail.setContractorUserClass(
											consumerApplicationDetailForm.getContractorUserClass().trim());
									contractorDetail.setContractorCompanyName(
											consumerApplicationDetailForm.getContractorCompanyName().trim());
									contractorDetail.setContractorMobile(
											consumerApplicationDetailForm.getContractorMobile().trim());
									contractorDetail.setContractorEmail(
											consumerApplicationDetailForm.getContractorEmail().trim());
									contractorDetail.setContractorAddress(
											consumerApplicationDetailForm.getContractorAddress().trim());

									contractorDetailResponse = contractorDetailService
											.saveContractorDetail(contractorDetail);
								} else {
									contractorDetailDb
											.setContractorUserId(consumerApplicationDetailForm.getContractorUserId());
									contractorDetailDb.setContractorAuthenticationId(
											consumerApplicationDetailForm.getContractorAuthenticationId().trim());
									contractorDetailDb.setContractorUserZone(
											consumerApplicationDetailForm.getContractorUserZone().trim());
									contractorDetailDb.setContractorUserClass(
											consumerApplicationDetailForm.getContractorUserClass().trim());
									contractorDetailDb.setContractorCompanyName(
											consumerApplicationDetailForm.getContractorCompanyName().trim());
									contractorDetailDb.setContractorMobile(
											consumerApplicationDetailForm.getContractorMobile().trim());
									contractorDetailDb.setContractorEmail(
											consumerApplicationDetailForm.getContractorEmail().trim());
									contractorDetailDb.setContractorAddress(
											consumerApplicationDetailForm.getContractorAddress().trim());

									Timestamp currentSystemDateAndTime = new Timestamp(System.currentTimeMillis());
									contractorDetailDb.setUpdated(currentSystemDateAndTime);

									contractorDetailResponse = contractorDetailService
											.saveContractorDetail(contractorDetailDb);
								}
							}

							if (contractorDetailResponse != null) {
								consumerApplicationDetail.setContractorId(contractorDetailResponse);
								consumerApplicationDetail
										.setContractorUserId(contractorDetailResponse.getContractorUserId());
							}

//							sandeep, ends here

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (consumerApplicationDetailForm.getSchemeTypeId() == null) {
				try {
					schemeTypeDb = schemeTypeService.findBySchemeTypeId(1l);
					consumerApplicationDetail.setSchemeType(schemeTypeDb);
					consumerApplicationDetailForm.setSchemeTypeId(1l);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			Consumer consumer = null;
			consumer = consumerService.getCurrentLoginConsumerByLoginId();
			if (consumer != null) {
				consumerApplicationDetail.setConsumers(consumer);
			}

			String maxApplicationNo = null;

			if (consumerApplicationDetailForm.getSchemeTypeId() == 1) {
				maxApplicationNo = consumerApplictionDetailRepository
						.findMaxApplicationNo(SequenceGeneratorUtil.getConsumerApplicationNoFormat(schemeTypeDb));
			} else if (consumerApplicationDetailForm.getSchemeTypeId() == 2) {
				maxApplicationNo = consumerApplictionDetailRepository
						.findMaxApplicationNo1(SequenceGeneratorUtil.getConsumerApplicationNoFormat(schemeTypeDb));
			} else {
				maxApplicationNo = consumerApplictionDetailRepository
						.findMaxApplicationNo2(SequenceGeneratorUtil.getConsumerApplicationNoFormat(schemeTypeDb));
			}
			String ApplicationNo = "";

			String applicationfirst = maxApplicationNo.substring(0, 2);
//			String applicationSecond = maxApplicationNo.substring(2);
//			Long num1 = Long.parseLong(applicationSecond);
//			Long incrementedNum = num1 + 1;
//			String applictionfullname = applicationfirst + incrementedNum;

			long uniqueCurrentTimeMS = uniqueCurrentTimeMS();
			String applictionfullname = applicationfirst + uniqueCurrentTimeMS;

//			if (maxApplicationNo != null && !maxApplicationNo.equals("null")) {
//				ApplicationNo = SequenceGeneratorUtil.getApplicationNo(maxApplicationNo, schemeTypeDb);
//			} else {
//				ApplicationNo = SequenceGeneratorUtil.getApplicationNo("", schemeTypeDb);
//			}

			consumerApplicationDetail.setConsumerApplicationNo(applictionfullname.toUpperCase().trim());

			consumerApplicationDetailResponse = consumerApplictionDetailRepository.save(consumerApplicationDetail);

			if (consumerApplicationDetailForm.getDcId() != null) {
				try {
					DistributionCenter distributionCenterDb = distributionCenterService
							.findDistributionCenterById(consumerApplicationDetailForm.getDcId());
					if (distributionCenterDb != null) {
						consumerApplicationDetail.setDistributionCenter(distributionCenterDb);
					}
					// district
					try {
						District districtDb = districtService
								.findDistrictById(consumerApplicationDetailForm.getDistrictId());
						if (districtDb != null) {
							consumerApplicationDetail.setDistrict(districtDb);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

//			sandeep, start
			if (consumerApplicationDetailResponse != null) {

				ApplicationDocument applicationDocumentResponse = null;
				applicationDocument.setConsumerApplicationDetail(consumerApplicationDetailResponse);
				applicationDocumentResponse = applicationDocumentService.saveApplicationDocument(applicationDocument);

			}
//			sandeep, ends

//			applicationHeadChargesService
//					.prepareRegistrationFeeCharges(consumerApplicationDetailResponse.getConsumerApplicationId());

//			charitra code start here

//			if (consumerApplicationDetailForm.getSchemeTypeId() != null
//					&& (consumerApplicationDetailForm.getSchemeTypeId().compareTo(1L) == 0)) {
//
//				final SMSRequest smsRequest = new SMSRequest();
//				smsRequest.setText(MessageFormat.format(messageProperties.getSendMsgVendor(),
//						new Object[] { consumerApplicationDetailForm.getContractorCompanyName().trim(),
//								schemeTypeDb.getSchemeTypeName() }));
////				smsRequest.setMobileNo(consumerApplicationDetailForm.getContractorMobile().trim());
//				smsRequest.setMobileNo(" ");
//				smsRequest.setTemplateId(messageProperties.getVendorTemplateId());
//
//				try {
//					smsDirectService.sendMessage(smsRequest);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}

//			charitra code end here

			if (Objects.isNull(consumerApplicationDetailResponse)) {
				logger.error("consumerApplictionDetailRepository.save(consumerApplicationDetail) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ConsumerApplicationDetailException(response);
			} else {
				return consumerApplicationDetailResponse;
			}

		}

	}

	@Override
	public Response<PageConsumerApplicationDetailDTO> findAllConsumerApplicationDetailPagination(Integer page,
			Integer size, ConsumerApplicationDetailsFilterDTO filterDTO) throws ConsumerApplicationDetailException {

		System.out.println("findAllConsumerApplicationDetailPagination method is calling !!!");

		Response<PageConsumerApplicationDetailDTO> response = new Response<PageConsumerApplicationDetailDTO>();
		PageConsumerApplicationDetailDTO pageConsumerApplicationDetailDTO = new PageConsumerApplicationDetailDTO();
		Page<ConsumerApplicationDetail> consumerApplicationDetails = findConsumerApplicationDetailPaginate(page, size,
				filterDTO);
		Map<String, Object> pageData = new HashMap<>();
		Meta meta = new Meta();
		meta.setCurrentPage(consumerApplicationDetails.getNumber());
		meta.setTotalItems(consumerApplicationDetails.getTotalElements());
		meta.setTotalPages(consumerApplicationDetails.getTotalPages());
		pageConsumerApplicationDetailDTO.setMeta(meta);
		List<ConsumerApplicationDetail> consumerApplicationDetailList = consumerApplicationDetails.getContent();

		pageConsumerApplicationDetailDTO.setList(consumerApplicationDetailList);
		response.setList(Arrays.asList(pageConsumerApplicationDetailDTO));
		response.setCode("200");
		response.setMessage("All record retrieved successfully");
		return response;
	}

	@Override
	public Page<ConsumerApplicationDetail> findConsumerApplicationDetailPaginate(Integer page, Integer size,
			ConsumerApplicationDetailsFilterDTO filterDTO) throws ConsumerApplicationDetailException {

		System.out.println("findConsumerApplicationDetailPaginate method is calling !!!");

		Response<ConsumerApplicationDetail> response = new Response<>();
		Pageable paging = PageRequest.of(page, size);
		Page<ConsumerApplicationDetail> pages = null;
		Integer totalRecords = 0;

		if (filterDTO.getUserLoginId() != null) {

			User user = userService.findByUserId(filterDTO.getUserLoginId());

			switch (user.getAccessLevel()) {
			case "1":
				// discomId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());

				break;
			case "2":

				// regionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());

				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}

				break;
			case "3":
				// circleId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}

				break;
			case "4":
				// divisionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}

				break;
			case "5":
				// subDivisionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}
				filterDTO.setSubDivisionId(user.getUserSubDivision().getSubDivisionId());

				break;
			case "6":
				// dcId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}
				filterDTO.setSubDivisionId(user.getUserSubDivision().getSubDivisionId());
				filterDTO.setDcId(user.getUserDc().getDcId());

				break;

			default:
				break;
			}

		}

		if (filterDTO.getConsumerLoginId() != null) {

			Consumer consumer = consumerService.findByConsumerLoginId(filterDTO.getConsumerLoginId());

			filterDTO.setConsumerId(consumer.getConsumerId());
		}

		pages = consumerApplictionDetailRepository.findByConsumerApplicationDetailsUserWisePaginate(
				filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
				filterDTO.getSubDivisionId(), filterDTO.getDcId(), filterDTO.getConsumerId(), paging);

		System.out.println("pages " + pages.getContent().get(0).getAadharNo());

		if (Objects.isNull(pages) || pages.isEmpty()) {
			logger.error(
					"consumerApplictionDetailRepository.findByConsumerApplicationDetailsPaginate is returning Null when findConsumerApplicationDetailPaginate call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CONSUMER_APPLICATION_DETAIL_RECORDS_WITH_PAGINATION_FAILED_MESSAGE);
//			throw new ConsumerApplicationDetailException(response);
			return pages;
		} else {
			logger.info("Response is returning successfully");
			return pages;
		}

	}

	@Override
	public ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo(String consumerApplicationNo)
			throws ConsumerApplicationDetailException {

		Response<GeoLocationException> response = new Response<>();

		ConsumerApplicationDetail consumerApplicationDetail = null;
		try {
			consumerApplicationDetail = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Objects.isNull(consumerApplicationDetail)) {
			response = new Response<>();
			response.setMessage(ResponseMessage.CONSUMER_APPLICATION_NOT_FOUND);
			response.setCode(ResponseCode.CONSUMER_APPLICATION_NOT_FOUND);
			throw new ConsumerApplicationDetailException(response);
		} else {
			return consumerApplicationDetail;
		}

	}

	@Override
	public ConsumerApplicationDetail findByConsumerApplicationId(Long consumerApplicationId)
			throws ConsumerApplicationDetailException {
		Response<GeoLocationException> response = new Response<>();
		return consumerApplictionDetailRepository.findByConsumerApplicationId(consumerApplicationId)
				.orElseThrow(() -> new DataNotFoundException(new Response<String>(ResponseCode.DATA_NOT_FOUND,
						ResponseMessage.CONSUMER_APPLICATION_NOT_FOUND)));

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ConsumerApplicationDetail saveConsumerApplication(ConsumerApplicationDetail consumerApplicationDetail)
			throws ConsumerApplicationDetailException {
		Response<GeoLocationException> response = new Response<>();
		ConsumerApplicationDetail consumerApplicationDetailDb = consumerApplictionDetailRepository
				.save(consumerApplicationDetail);
		if (consumerApplicationDetailDb == null) {
			response.setMessage("Error Saving Consumer Application !");
			response.setCode(ResponseCode.CONSUMER_APPLICATION_NOT_FOUND);
			throw new ConsumerApplicationDetailException(response);
		}
		return consumerApplicationDetailDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cleanPreviousStageDataInConsumerApplicationDetail(ConsumerApplicationDetail consumerApplicationDetail)
			throws ConsumerApplicationDetailException {

		Response<ConsumerApplicationDetailException> response = new Response<>();
		consumerApplicationDetail.setPreviousStageRemark(null);
		consumerApplicationDetail.setPreviousStage(Boolean.FALSE);

		ConsumerApplicationDetail consumerApplicationDetailDb = consumerApplictionDetailRepository
				.save(consumerApplicationDetail);
		if (consumerApplicationDetailDb == null) {
			response.setMessage("Error Saving Consumer Application !");
			response.setCode(ResponseCode.CONSUMER_APPLICATION_NOT_FOUND);
			throw new ConsumerApplicationDetailException(response);
		}

	}

	@Override
	public List<ConsumerApplicationDetail> findConsumerApplicationDetailByDcCode(String dcCode)
			throws ConsumerApplicationDetailException, DistributionCenterException {

		Response<ConsumerApplicationDetail> response = new Response<>();
		if (dcCode == null) {
			response.setMessage("DC Is Null !");
			response.setCode(ResponseCode.DATA_NOT_FOUND);
			throw new ConsumerApplicationDetailException(response);
		}

		DistributionCenter distributionCenter = distributionCenterService.findDistributionCenterByDcCode(dcCode);

		List<ConsumerApplicationDetail> consumerApplicationDetail = consumerApplictionDetailRepository
				.findConsumerApplicationDetailsByDistributionCenterDcIdAndApplicationStatusApplicationStatusId(
						distributionCenter.getDcId(), ApplicationStatusEnum.PENDING_FOR_SURVEY_AT_DC.getId());

		if (consumerApplicationDetail.isEmpty()) {
			response.setMessage("consumer application not found");
			response.setCode(ResponseCode.CONSUMER_APPLICATION_NOT_FOUND);
			throw new ConsumerApplicationDetailException(response);
		}

		return consumerApplicationDetail;

	}

	@Override
	public Response<PageConsumerApplicationDetailDTO> findAllConsumerApplicationDetailByApplicationStatusPagination(
			Integer page, Integer size, ConsumerApplicationDetailsFilterDTO filterDTO)
			throws ConsumerApplicationDetailException {

		System.out.println("findAllConsumerApplicationDetailByApplicationStatusPagination method is calling !!!");

		Response<PageConsumerApplicationDetailDTO> response = new Response<PageConsumerApplicationDetailDTO>();
		PageConsumerApplicationDetailDTO pageConsumerApplicationDetailDTO = new PageConsumerApplicationDetailDTO();
		Page<ConsumerApplicationDetail> consumerApplicationDetails = findConsumerApplicationDetailByApplicationStatusPaginate(
				page, size, filterDTO);
		Map<String, Object> pageData = new HashMap<>();
		Meta meta = new Meta();
		meta.setCurrentPage(consumerApplicationDetails.getNumber());
		meta.setTotalItems(consumerApplicationDetails.getTotalElements());
		meta.setTotalPages(consumerApplicationDetails.getTotalPages());
		pageConsumerApplicationDetailDTO.setMeta(meta);
		List<ConsumerApplicationDetail> consumerApplicationDetailList = consumerApplicationDetails.getContent();
		for (ConsumerApplicationDetail cad : consumerApplicationDetailList) {
			List<Map<String, String>> transactionDateByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
					.getTransactionDateByConsumerApplicationNo(cad.getConsumerApplicationNo());
			System.out.println("transactionDateByConsumerApplicationNo = " + transactionDateByConsumerApplicationNo);
			cad.setListOfTransactionDate(transactionDateByConsumerApplicationNo);
		}

		pageConsumerApplicationDetailDTO.setList(consumerApplicationDetailList);
		response.setList(Arrays.asList(pageConsumerApplicationDetailDTO));
		response.setCode("200");
		response.setMessage("All record retrieved successfully");
		return response;
	}

	@Override
	public Page<ConsumerApplicationDetail> findConsumerApplicationDetailByApplicationStatusPaginate(Integer page,
			Integer size, ConsumerApplicationDetailsFilterDTO filterDTO) throws ConsumerApplicationDetailException {

		System.out.println("findConsumerApplicationDetailPaginate method is calling !!!");

		Response<ConsumerApplicationDetail> response = new Response<>();
		Pageable paging = PageRequest.of(page, size);
		Page<ConsumerApplicationDetail> pages = null;
		Integer totalRecords = 0;

		if (filterDTO.getUserLoginId() != null) {

			User user = userService.findByUserId(filterDTO.getUserLoginId());

			switch (user.getAccessLevel()) {
			case "1":
				// discomId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());

				break;
			case "2":

				// regionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());

				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}

				break;
			case "3":
				// circleId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}

				break;
			case "4":
				// divisionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}

				break;
			case "5":
				// subDivisionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}
				filterDTO.setSubDivisionId(user.getUserSubDivision().getSubDivisionId());

				break;
			case "6":
				// dcId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}
				filterDTO.setSubDivisionId(user.getUserSubDivision().getSubDivisionId());
				filterDTO.setDcId(user.getUserDc().getDcId());

				break;

			default:
				break;
			}

		}

		if (filterDTO.getConsumerLoginId() != null) {

			Consumer consumer = consumerService.findByConsumerLoginId(filterDTO.getConsumerLoginId());

			filterDTO.setConsumerId(consumer.getConsumerId());
		}

		pages = consumerApplictionDetailRepository.findByConsumerApplicationDetailsByApplicationStatusPaginate(
				filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
				filterDTO.getSubDivisionId(), filterDTO.getDcId(), filterDTO.getConsumerId(), paging);

		List<ConsumerApplicationDetail> list = pages.get().collect(Collectors.toList());
		for (ConsumerApplicationDetail consumerApplicationDetail : list) {
			if (consumerApplicationDetail.getErpWorkFlowNumber() != null) {
				consumerApplicationDetail.setErpWorkFlowSacntion(
						estimateAmountRepository.getWorkflowNumber(consumerApplicationDetail.getErpWorkFlowNumber()));
			}
			;
		}

		if (Objects.isNull(pages) || pages.isEmpty()) {
			logger.error(
					"consumerApplictionDetailRepository.findByConsumerApplicationDetailsPaginate is returning Null when findConsumerApplicationDetailPaginate call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CONSUMER_APPLICATION_DETAIL_RECORDS_WITH_PAGINATION_FAILED_MESSAGE);
//			throw new ConsumerApplicationDetailException(response);
			return pages;
		} else {
			logger.info("Response is returning successfully");
			return pages;
		}

	}

	@Override
	public List<GeoPendingStatusConsmersListResponse> findConsumerApplicationDetailByConsumerId(Long consumerId)
			throws ConsumerApplicationDetailException, GeoLocationException {
		List<ConsumerApplicationDetail> consumerApplicationDetailList = consumerApplictionDetailRepository
				.findByConsumerId(consumerId);

		List<GeoPendingStatusConsmersListResponse> geoPendingStatusConsmersList = new ArrayList<>();

		GeoPendingStatusConsmersListResponse geoPendingStatusConsmersListResponse = null;
		for (ConsumerApplicationDetail consumerApplicationDetail : consumerApplicationDetailList) {

			if (consumerApplicationDetail != null && consumerApplicationDetail.getApplicationStatus() != null
					&& consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() != null
					&& consumerApplicationDetail.getApplicationStatus().getApplicationStatusId().equals(4l)) {
				System.out.println(consumerApplicationDetail.getConsumerApplicationNo() + "------------------------");
				geoPendingStatusConsmersListResponse = new GeoPendingStatusConsmersListResponse();
				geoPendingStatusConsmersListResponse
						.setConsumerApplicationNumber(consumerApplicationDetail.getConsumerApplicationNo());
				geoPendingStatusConsmersListResponse
						.setConsumerApplicationStatus("Unpaid applications pending for GIS location");

				// geoLocationService.findbyCosumerApplicationNumber(consumerApplicationDetail.getConsumerApplicationNo());
				geoPendingStatusConsmersList.add(geoPendingStatusConsmersListResponse);
			}

		}
//		System.out.println(geoPendingStatusConsmersList);
//	for(int i=0;i<geoPendingStatusConsmersList.size();i++)
//		System.out.println(geoPendingStatusConsmersList.get(i));
		return geoPendingStatusConsmersList;
	}

	@Override
	public ConsumerApplicationDetail updateConsumerApplicationDetail(
			ConsumerApplicationDetailForm consumerApplicationDetailForm, MultipartFile docRow,
			MultipartFile docRegistry, MultipartFile docNagarNigamPermission, MultipartFile docDicOrGumasta,
			MultipartFile docShapathPatra, MultipartFile docTestReport, MultipartFile docT$cpPermission,
			MultipartFile docReraPermission, MultipartFile docDrawingNotarized, MultipartFile docColonyPrakoshth,
			MultipartFile docColonyLicence, MultipartFile docLoadSheet, MultipartFile docNoc,
			MultipartFile docAllPaperNotarized03Set, MultipartFile docCommittee, MultipartFile docDiversion,
			MultipartFile docNazul, MultipartFile docMap, MultipartFile docKhasraKhatoni,
			MultipartFile docMapCivilEngineer, MultipartFile docApplicationConsent, MultipartFile docPerforma5A,
			MultipartFile docPerforma5B, MultipartFile docPlotAreaDetailsWithOwner, MultipartFile docIndividualOrGroup,
			MultipartFile docAdministrative, Long id, MultipartFile docGst,MultipartFile consentletter) throws ConsumerApplicationDetailException,
			DocumentTypeException, ConsumerApplicationSurveyException, SMSDirectServiceException, PaymentTypeException,
			ApplicationHeadChargesException, ApplicationDocumentException, SupplyVoltageException,
			TariffCategoryException, TaskTypeException, NatureOfWorkException {
		// TODO Auto-generated method stub

		final String method = "ConsumerApplicationDetailServiceImpl : updateConsumerApplicationDetail()";

		logger.info(method);

		Response<ConsumerApplicationDetail> response = new Response<>();

		ConsumerApplicationDetail consumerApplicationDetailResponse = null;

		if (id == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application Id not found  ");
			throw new ConsumerApplicationDetailException(response);
		}

		ConsumerApplicationDetail consumerAppdetail = consumerApplicationDetailService.findByConsumerApplicationId(id);

		if (Objects.isNull(consumerApplicationDetailForm)) {
			logger.error("Consumer Application Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ConsumerApplicationDetailException(response);
		}

		Upload rowUpload = null;
		Upload registryUpload = null;
		Upload nagarNigamPermissionUpload = null;
		Upload dicOrGumastaUpload = null;
		Upload shapathPatraUpload = null;
		Upload testReportUpload = null;
		Upload t$cpPermissionUpload = null;
		Upload reraPermissionUpload = null;
		Upload drawingNotarizedUpload = null;
		Upload colonyPrakoshthUpload = null;
		Upload colonyLicenceUpload = null;
		Upload loadSheetUpload = null;
		Upload nocUpload = null;
		Upload allPaperNotarized03SetUpload = null;
		Upload committeeUpload = null;
		Upload diversionUpload = null;
		Upload nazulUpload = null;
		Upload mapUpload = null;
		Upload khasraKhatoniUpload = null;
		Upload mapCivilEngineerUpload = null;
		Upload applicationConsentUpload = null;
		Upload performa5AUpload = null;
		Upload performa5BUpload = null;
		Upload plotAreaDetailsWithOwnerUpload = null;
		Upload docAdministrativeUpload = null;
		Upload docGstUpload = null;
		Upload docconsentletter =null;

		/*********** validations start ***********************/

		NatureOfWork natureOfWorkdb = null;
		try {
			natureOfWorkdb = natureOfWorkService
					.findByNatureOfWorkId(consumerAppdetail.getNatureOfWorkType().getNatureOfWorkTypeId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!natureOfWorkdb.getNatureOfWorkName().equals("Line Shifting (Non Government)")
				&& !natureOfWorkdb.getNatureOfWorkName().equals("Line Shifting (Goverment)")) {

		}

		ApplicationDocument applicationDocument = new ApplicationDocument();

		if (natureOfWorkdb != null) {
//				this is for Line Shr
			if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(1l) == 0)) {

			}
//				this is for NSC eccetion
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(2l) == 0)) {

			}
//				this is for colony eletrifacation  llegal
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(3l) == 0)) {

				if (docReraPermission == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload docReraPermission document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (docT$cpPermission == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload docT$cpPermission With Owner document");
					throw new ConsumerApplicationDetailException(response);
				}

			}
//				this is for colony eletrifacation  illegal
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(4l) == 0)) {

				if (docRegistry == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Registry document");
					throw new ConsumerApplicationDetailException(response);
				}

//					if (docNagarNigamPermission == null) {
//						response.setCode(HttpCode.NULL_OBJECT);
//						response.setMessage("Please upload Nagar Nigam Permission document");
//						throw new ConsumerApplicationDetailException(response);
//					}

				if (docNoc == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload NOC document");
					throw new ConsumerApplicationDetailException(response);
				}

			}
//				this is for OYT

			// this is for LineShifting(Govermnet)
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(6l) == 0)) {

				if (docAdministrative == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Doc Administrative document");
					throw new ConsumerApplicationDetailException(response);
				}
			}

			// this is for OYT
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(5l) == 0)) {

				if (docKhasraKhatoni == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Doc Khasar-Khatoni document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (consumerApplicationDetailForm.getAadharNo() == null
						|| consumerApplicationDetailForm.getAadharNo().equals("")) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill aadhar number");
					throw new ConsumerApplicationDetailException(response);

				}
			} else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(10l) == 0)) {

				if (docLoadSheet == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Load Sheet document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (docMap == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload document Map");
					throw new ConsumerApplicationDetailException(response);
				}

			}
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(12l) == 0)) {

				if (consentletter == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload consentletter document");
					throw new ConsumerApplicationDetailException(response);
				}
			}
			consumerAppdetail.setNatureOfWorkType(natureOfWorkdb);

		}

		/*********** validations ends ************************/
		
		if(consentletter!=null){
			docconsentletter = uploadService.uploadFile(consentletter, "CONSENT_LETTER");
			if (registryUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document consent letter not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setConsentletter(docconsentletter);
		}

		if (docRegistry != null) {
			registryUpload = uploadService.uploadFile(docRegistry, "REGISTRY");
			if (registryUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Registry not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocRegistry(registryUpload);
		}

		if (docT$cpPermission != null) {
			t$cpPermissionUpload = uploadService.uploadFile(docT$cpPermission, "T$CP_PERMISSION");
			if (t$cpPermissionUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document T&CP Permission not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocT$cpPermission(t$cpPermissionUpload);
		}
		if (docReraPermission != null) {
			reraPermissionUpload = uploadService.uploadFile(docReraPermission, "RERA_PERMISSION");
			if (reraPermissionUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Rera Permission not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocReraPermission(reraPermissionUpload);
		}

		if (docNoc != null) {
			nocUpload = uploadService.uploadFile(docNoc, "NOC");
			if (nocUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document NOC not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocNoc(nocUpload);
		}

		if (docIndividualOrGroup != null) {
			nocUpload = uploadService.uploadFile(docIndividualOrGroup, "Group_File");
			if (nocUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Groupfile not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocNoc(nocUpload);
		}

		if (docAdministrative != null) {
			docAdministrativeUpload = uploadService.uploadFile(docAdministrative, "Administrative");
			if (docAdministrativeUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Administrative not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocAdministrative(docAdministrativeUpload);
		}

		// 999
		if (docKhasraKhatoni != null) {
			khasraKhatoniUpload = uploadService.uploadFile(docKhasraKhatoni, "KHASRA_KHATONI");
			if (khasraKhatoniUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Khasar-Khatoni not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocKhasraKhatoni(khasraKhatoniUpload);
		}

		if (docGst != null) {
			docGstUpload = uploadService.uploadFile(docGst, "GST");
			applicationDocument.setDocGst(docGstUpload);
		}

		if (docLoadSheet != null) {
			loadSheetUpload = uploadService.uploadFile(docLoadSheet, "LOAD_SHEET");
			if (loadSheetUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Load Sheet not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocLoadSheet(loadSheetUpload);
		}

		if (docMap != null) {
			mapUpload = uploadService.uploadFile(docMap, "MAP");
			if (mapUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document MAP not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocMap(mapUpload);
		}

		if (consumerApplicationDetailForm.getWorkTypeId() != null) {
			try {
				WorkType workTypeDb = workTypeService.findByWorkTypeId(consumerApplicationDetailForm.getWorkTypeId());
				if (workTypeDb != null) {
					consumerAppdetail.setWorkType(workTypeDb);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

//		ApplicationStatus appStatusDb = applicationStatusService
//				.findById(ApplicationStatusEnum.PENDING_FOR_GIS_LOCATION.getId());
//
//		consumerAppdetail.setApplicationStatus(appStatusDb);

		consumerAppdetail.setConsumerName(consumerAppdetail.getConsumerName().trim());

		consumerAppdetail.setGuardianName(consumerApplicationDetailForm.getGuardianName().trim());

		consumerAppdetail.setAddress(consumerApplicationDetailForm.getAddress().trim());
		if (consumerApplicationDetailForm.getArea() != null) {
			consumerAppdetail.setArea(consumerApplicationDetailForm.getArea());
		}

		if (consumerApplicationDetailForm.getGstNumber() != null) {
			consumerAppdetail.setGstNumber(consumerApplicationDetailForm.getGstNumber());
		}

		if (consumerApplicationDetailForm.getBillNo() != null
				&& !consumerApplicationDetailForm.getBillNo().equals("")) {
			consumerAppdetail.setBillNo(consumerApplicationDetailForm.getBillNo().trim());
		}

		consumerAppdetail.setIsRejected(Boolean.FALSE);
		consumerAppdetail.setRejectedRemark("NA");

		consumerAppdetail.setShortDescriptionOfWork(consumerApplicationDetailForm.getShortDescriptionOfWork());

		consumerAppdetail.setPincode(consumerApplicationDetailForm.getPinCode());

		if (consumerApplicationDetailForm.getIvrsNo() != null
				&& !consumerApplicationDetailForm.getIvrsNo().equals("")) {
			consumerAppdetail.setIvrsNo(consumerApplicationDetailForm.getIvrsNo().trim());
		}
		if (consumerApplicationDetailForm.getWorkAllocationAddress() != null
				&& !consumerApplicationDetailForm.getWorkAllocationAddress().equals("")) {
			consumerAppdetail.setWorkAllocationAddress(consumerApplicationDetailForm.getWorkAllocationAddress().trim());
		}

		if (consumerApplicationDetailForm.getWorkLocationAddresscb() == null) {
			consumerAppdetail.setWorkLocationAddresscb(false);
		} else {
			consumerAppdetail.setWorkLocationAddresscb(true);
		}
		if (consumerApplicationDetailForm.getLoadRequested() != null) {
			consumerAppdetail.setLoadRequested(consumerApplicationDetailForm.getLoadRequested());
		}
		if (consumerApplicationDetailForm.getLoadRequested() != null) {
			LoadRequested loadRequestedDb = loadRequestedService
					.findById(consumerApplicationDetailForm.getLoadRequestedId());
			consumerAppdetail.setLoadRequestedId(loadRequestedDb);
		}
		if (consumerApplicationDetailForm.getLandAreaUnitId() != null) {
			LandAreaUnit landAreaUnit = landAreaUnitService.findById(consumerApplicationDetailForm.getLandAreaUnitId());

			consumerAppdetail.setLandAreaUnit(landAreaUnit);
		}
		if (consumerApplicationDetailForm.getLandAreaUnitName() != null) {
			consumerAppdetail.setLandAreaUnitName(consumerApplicationDetailForm.getLandAreaUnitName());
		}
		if (consumerApplicationDetailForm.getIndividualOrGroupId() != null) {
			IndividualOrGroup individualOrGroup = individualOrGroupService
					.findById(consumerApplicationDetailForm.getIndividualOrGroupId());

			consumerAppdetail.setIndividualOrGroup(individualOrGroup);
		}
		if (consumerApplicationDetailForm.getNoOfPlot() != null) {
			consumerAppdetail.setNoOfPlot(consumerApplicationDetailForm.getNoOfPlot());
		}

		if (consumerApplicationDetailForm.getKhasra() != null) {
			consumerAppdetail.setKhasra(consumerApplicationDetailForm.getKhasra());
		}

		if (consumerApplicationDetailForm.getKhatoni() != null) {
			consumerAppdetail.setKhatoni(consumerApplicationDetailForm.getKhatoni());
		}

		if (consumerApplicationDetailForm.getAadharNo() != null) {
			consumerAppdetail.setAadharNo(consumerApplicationDetailForm.getAadharNo());
		}

		SchemeType schemeTypeDb = null;
		SupplyVoltage supplyVoltageDb = null;

		consumerAppdetail.setDtr(null);
		consumerAppdetail.setPtr(null);
		consumerAppdetail.setLt(null);
		consumerAppdetail.setHt11Kv(null);
		consumerAppdetail.setHt33Kv(null);
		consumerAppdetail.setHt132Kv(null);

		if (consumerApplicationDetailForm.getSupplyVoltageId() != null) {
			for (Long s : consumerApplicationDetailForm.getSupplyVoltageId()) {
				System.out.println("************************************************++++++ ========" + s);
//				supplyVoltageDb = supplyVoltageService.findBySupplyVoltageId(s);

				if (s == 3l) {// 4l//5l
					consumerAppdetail.setDtr(3l);
				}

				if (s == 4l) {
					consumerAppdetail.setPtr(4l);
				}

				if (s == 5l) {
					consumerAppdetail.setLt(5l);
				}
				if (s == 6l) {
					consumerAppdetail.setHt11Kv(6l);
				}

				if (s == 7l) {
					consumerAppdetail.setHt33Kv(7l);
				}
				if (s == 8l) {
					consumerAppdetail.setHt132Kv(8l);
				}

			}
		}

		if (consumerApplicationDetailForm.getSchemeTypeId() != null) {
			try {
				schemeTypeDb = schemeTypeService.findBySchemeTypeId(consumerApplicationDetailForm.getSchemeTypeId());
				consumerAppdetail.setSchemeType(schemeTypeDb);

				if (schemeTypeDb != null && !(schemeTypeDb.getSchemeTypeId().compareTo(VendorEnum.OYT.getId()) == 0)) {
					consumerAppdetail.setSchemeType(schemeTypeDb);

					if (schemeTypeDb.getSchemeTypeId().compareTo(VendorEnum.MPMKVVCL.getId()) == 0) {
						consumerAppdetail.setVendor(VendorEnum.MPMKVVCL.getValue());
						consumerAppdetail.setContractorId(null);
						consumerAppdetail.setContractorName("NA");
					} else if (schemeTypeDb.getSchemeTypeId().compareTo(VendorEnum.CONTRACTOR.getId()) == 0
							&& consumerApplicationDetailForm.getContractorUserId() != null
							&& !consumerApplicationDetailForm.getContractorUserId().equals("")) {
						consumerAppdetail.setVendor(VendorEnum.CONTRACTOR.getValue());

//							sandeep, starts here

						ContractorDetail contractorDetailResponse = null;

						if (consumerApplicationDetailForm.getContractorUserId() != null) {

							ContractorDetail contractorDetailDb = null;

							ContractorDetail contractorDetail = new ContractorDetail();

							contractorDetailDb = contractorDetailService
									.findByContractorUserId(consumerApplicationDetailForm.getContractorUserId());

							if (contractorDetailDb == null) {
								contractorDetail
										.setContractorUserId(consumerApplicationDetailForm.getContractorUserId());
								contractorDetail.setContractorAuthenticationId(
										consumerApplicationDetailForm.getContractorAuthenticationId().trim());
								contractorDetail.setContractorUserZone(
										consumerApplicationDetailForm.getContractorUserZone().trim());
								contractorDetail.setContractorUserClass(
										consumerApplicationDetailForm.getContractorUserClass().trim());
								contractorDetail.setContractorCompanyName(
										consumerApplicationDetailForm.getContractorCompanyName().trim());
								contractorDetail.setContractorMobile(
										consumerApplicationDetailForm.getContractorMobile().trim());
								contractorDetail
										.setContractorEmail(consumerApplicationDetailForm.getContractorEmail().trim());
								contractorDetail.setContractorAddress(
										consumerApplicationDetailForm.getContractorAddress().trim());

								contractorDetailResponse = contractorDetailService
										.saveContractorDetail(contractorDetail);
							} else {
								contractorDetailDb
										.setContractorUserId(consumerApplicationDetailForm.getContractorUserId());
								contractorDetailDb.setContractorAuthenticationId(
										consumerApplicationDetailForm.getContractorAuthenticationId().trim());
								contractorDetailDb.setContractorUserZone(
										consumerApplicationDetailForm.getContractorUserZone().trim());
								contractorDetailDb.setContractorUserClass(
										consumerApplicationDetailForm.getContractorUserClass().trim());
								contractorDetailDb.setContractorCompanyName(
										consumerApplicationDetailForm.getContractorCompanyName().trim());
								contractorDetailDb.setContractorMobile(
										consumerApplicationDetailForm.getContractorMobile().trim());
								contractorDetailDb
										.setContractorEmail(consumerApplicationDetailForm.getContractorEmail().trim());
								contractorDetailDb.setContractorAddress(
										consumerApplicationDetailForm.getContractorAddress().trim());

								Timestamp currentSystemDateAndTime = new Timestamp(System.currentTimeMillis());
								contractorDetailDb.setUpdated(currentSystemDateAndTime);

								contractorDetailResponse = contractorDetailService
										.saveContractorDetail(contractorDetailDb);
							}
						}

						if (contractorDetailResponse != null) {
							consumerAppdetail.setContractorId(contractorDetailResponse);
							consumerAppdetail.setContractorUserId(contractorDetailResponse.getContractorUserId());
						}

//							sandeep, ends here

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

//		scheme type me 5 bej ya 7 be tabhi age ka ka hoga
		if (consumerApplicationDetailForm.getSchemeTypeId() == null) {
			try {
				schemeTypeDb = schemeTypeService
						.findBySchemeTypeId(consumerAppdetail.getSchemeType().getSchemeTypeId());
				consumerAppdetail.setSchemeType(schemeTypeDb);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		Consumer consumer = null;
		consumer = consumerService.getCurrentLoginConsumerByLoginId();
		if (consumer != null) {
			consumerAppdetail.setConsumers(consumer);
		}

		if (consumerApplicationDetailForm.getDcId() != null) {
			try {
				DistributionCenter distributionCenterDb = distributionCenterService
						.findDistributionCenterById(consumerApplicationDetailForm.getDcId());
				if (distributionCenterDb != null) {
					consumerAppdetail.setDistributionCenter(distributionCenterDb);
				}
				// district
				try {
					District districtDb = districtService
							.findDistrictById(consumerApplicationDetailForm.getDistrictId());
					if (districtDb != null) {
						consumerAppdetail.setDistrict(districtDb);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		consumerApplicationDetailResponse = consumerApplictionDetailRepository.save(consumerAppdetail);

//			sandeep, start
		if (consumerApplicationDetailResponse != null) {

			ApplicationDocument applicationDocumentResponse = null;
			List<ApplicationDocument> findAllDataByConsumerApplicationId = applicationDocumentRepository
					.findAllDataByConsumerApplicationId(consumerApplicationDetailResponse.getConsumerApplicationId());
			System.out.println("findAllDataByConsumerApplicationId : " + findAllDataByConsumerApplicationId);
			for (ApplicationDocument appDocument : findAllDataByConsumerApplicationId) {
				applicationDocumentRepository.deleteById(appDocument.getApplicationDocumentId());
			}

			applicationDocument.setConsumerApplicationDetail(consumerApplicationDetailResponse);
			applicationDocumentResponse = applicationDocumentService.saveApplicationDocument(applicationDocument);

		}
//			sandeep, ends

//			applicationHeadChargesService
//					.prepareRegistrationFeeCharges(consumerApplicationDetailResponse.getConsumerApplicationId());

		if (Objects.isNull(consumerApplicationDetailResponse)) {
			logger.error("consumerApplictionDetailRepository.save(consumerApplicationDetail) is returning Null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
			throw new ConsumerApplicationDetailException(response);
		} else {
			return consumerApplicationDetailResponse;
		}

	}

	@Override
	public List<Map<String, String>> findAllConsumerDetail() throws ConsumerApplicationDetailException {
		final String method = "FeederServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<Map<String, String>> findAll = consumerApplicationDetailRepository.findAllConsumerDetails();

		if (Objects.isNull(findAll) || findAll.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new ConsumerApplicationDetailException(response);
		} else {
			response.setList(findAll);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return findAll;
		}
	}

//	O&M  dikhana hai
	@Override
	public Response findConsumerApplicationDetailsByCityCricleAndBhopal(long circleid) {
		Response response = new Response();

//		List<ConsumerApplicationDetail> findAll = consumerApplicationDetailRepository.findByNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNotNullOrNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNotNull(6,1);

		List<ConsumerApplicationDetail> findAll = consumerApplicationDetailRepository.findAll();

		List<ConsumerApplicationDetail> filterdatabBasedOncityCricleAndBhopal = new ArrayList();

		for (ConsumerApplicationDetail c : findAll) {
			System.out.println(
					"c.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivisionCircle()\r\n"
							+ "					.getCircleId() " + c.getDistributionCenter().getDcSubdivision()
									.getSubdivisionDivision().getDivisionCircle().getCircleId());
			if (c.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivisionCircle()
					.getCircleId() == circleid) {

				if (c.getNatureOfWorkType().getNatureOfWorkTypeId() == 1
						|| c.getNatureOfWorkType().getNatureOfWorkTypeId() == 6) {
					if (c.getLt() != null && c.getLt() == 5 && (c.getDtr() == null || c.getDtr() != 3)
							&& (c.getPtr() == null || c.getPtr() != 4) && (c.getHt11Kv() == null || c.getHt11Kv() != 6)
							&& (c.getHt33Kv() == null || c.getHt33Kv() != 7)
							&& (c.getHt132Kv() == null || c.getHt132Kv() != 8)) {
						filterdatabBasedOncityCricleAndBhopal.add(c);
					}
				} else {
					filterdatabBasedOncityCricleAndBhopal.add(c);
				}

			}

		}
		if (Objects.isNull(filterdatabBasedOncityCricleAndBhopal) || filterdatabBasedOncityCricleAndBhopal.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		} else {
			response.setList(filterdatabBasedOncityCricleAndBhopal);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}

	}

//		HTM ko dikhna hai
	@Override
	public Response findConsumerApplicationDetailsByCityCricleAndBhopalBasedOnNullValue(long circleid) {

		Response response = new Response();

		List<ConsumerApplicationDetail> findAll = consumerApplicationDetailRepository.findConsumerApplicationDetails();

//				.findByNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNullOrNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNull(6,
//						1);

		List<ConsumerApplicationDetail> filterdatabBasedOncityCricleAndBhopal = new ArrayList();

		for (ConsumerApplicationDetail c : findAll) {

			if (c.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivisionCircle()
					.getCircleId() == circleid) {
				filterdatabBasedOncityCricleAndBhopal.add(c);
			}
		}
		if (Objects.isNull(filterdatabBasedOncityCricleAndBhopal) || filterdatabBasedOncityCricleAndBhopal.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		} else {
			response.setList(filterdatabBasedOncityCricleAndBhopal);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}

	}

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMServiceIMp workCompletionChangStautsByDgmOAndMServiceIMp;

	@Override
	public Response<?> rejectApplication(String consumerApplicationNumber, String rejectedApplicationDate,
			String rejectedRemark, String rejectApplicationGMName, Boolean gmAccepted) {
		Response response = new Response();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNumber);

		if (Objects.isNull(consumerApplicationDetail)) {
			logger.error("Consumer Application Not Found");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		} else {
			if (gmAccepted) {
				consumerApplicationDetail.setApplicationRejectedDate(rejectedApplicationDate);
				consumerApplicationDetail.setRejectedRemark(rejectedRemark);
				consumerApplicationDetail.setRejectApplicationGMName(rejectApplicationGMName);
				consumerApplicationDetail.setGmRejectedOrAccepted("true");

//				added code start for release erp no. when application is going to Reject 2-jan-2025
				String deleteRejectedApplication = deleteRejectedApplication(
						consumerApplicationDetail.getErpWorkFlowNumber(),consumerApplicationDetail.getConsumerApplicationNo());
				System.err.println("deleteRejectedApplication : " + deleteRejectedApplication);
				if (deleteRejectedApplication.equals("ERP No. can be deleted")) {
					deletErp(consumerApplicationDetail.getErpWorkFlowNumber());
					consumerApplicationDetail.setErpWorkFlowNumber(null);
				} else {
					consumerApplicationDetail.setErpWorkFlowNumber(null);
				}
//				code end
				consumerApplicationDetail.setApplicationStatus(applicationStatusService.findById(29L));

//				20-05-2025 added this check to return cancel application to SSP portal for Load Enhancement application
				if (consumerApplicationDetail.getNscApplicationNo() != null) {
					workCompletionChangStautsByDgmOAndMServiceIMp.sendDataToSSp1(consumerApplicationDetail);
				}

			} else {
				if (consumerApplicationDetail.getErpWorkFlowNumber() != null) {
					Optional<ErpEstimateAmountData> findById = estimateAmountRepository
							.findByIdAndConsumerApplicationNo(consumerApplicationDetail.getErpWorkFlowNumber(),
									consumerApplicationNumber);
					if (findById.isPresent()) {
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId()).get());
					} else {
						consumerApplicationDetail.setErpWorkFlowNumber(null);
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.PENDING_FOR_SURVEY_AT_DC.getId()).get());
					}
				} else {
					Optional<ConsumerApplicationSurvey> findByConsumersApplicationDetailConsumerApplicationId = consumerApplicationSurveyRepository
							.findByConsumersApplicationDetailConsumerApplicationId(
									consumerApplicationDetail.getConsumerApplicationId());
					if (findByConsumersApplicationDetailConsumerApplicationId.isPresent()) {
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.PENDING_FOR_SURVEY_AT_DC.getId()).get());
					} else {
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId()).get());
					}
				}
				consumerApplicationDetail.setApplicationRejectedDate(rejectedApplicationDate);
				consumerApplicationDetail.setRejectedRemark(rejectedRemark);
				consumerApplicationDetail.setRejectApplicationGMName(rejectApplicationGMName);
				consumerApplicationDetail.setGmRejectedOrAccepted("false");
				consumerApplicationDetail.setRejectionProposal(null);

			}

			ConsumerApplicationDetail updatedConsumerApplicationDetail = consumerApplicationDetailRepository
					.save(consumerApplicationDetail);

			response.setList(Arrays.asList(updatedConsumerApplicationDetail));
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;

		}

	}

	@Override
	public Response<?> jeLoadApplication(String consumerApplicationNumber, String jeLoad, String jeLoadUnitKwYaKva,
			int goodMaterialOrNot) {
		Response response = new Response();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNumber);

		if (Objects.isNull(consumerApplicationDetail)) {
			logger.error("Consumer Application Not Found");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		} else {

			consumerApplicationDetail.setJeLoad(jeLoad);
			consumerApplicationDetail.setJeLoadUnitKwYaKva(jeLoadUnitKwYaKva);
			if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1l)) {
				consumerApplicationDetail.setGoodMaterialOrnot(0);
			} else {
				consumerApplicationDetail.setGoodMaterialOrnot(goodMaterialOrNot);
			}

			ConsumerApplicationDetail updatedConsumerApplicationDetail = consumerApplicationDetailRepository
					.save(consumerApplicationDetail);
			response.setList(Arrays.asList(updatedConsumerApplicationDetail));
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;

		}

	}

//	@Override
//	public ErpEstimateCalculatedDto getConsumerChalan(String consumerApplicationNumber)
//			throws ConsumerApplicationDetailException, SchemeTypeException, Exception {
//
//		ErpEstimateCalculatedDto findByEstimateNumber = new ErpEstimateCalculatedDto();
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
//				.findByConsumerApplicationNumber(consumerApplicationNumber);
//
//		if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 5l) {
//			findByEstimateNumber.setConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo());
//			findByEstimateNumber.setSgst(new BigDecimal(90.00));
//			findByEstimateNumber.setCgst(new BigDecimal(90.00));
//			findByEstimateNumber.setRegistrationFee(new BigDecimal(1000.00));
//			findByEstimateNumber.setTotalAmount(new BigDecimal(1180.00));
//
//			return findByEstimateNumber;
//
//		} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 12l) {
//			findByEstimateNumber = erpEstimateAmountService
//					.calculateEstimateAmount(consumerApplicationDetail.getConsumerApplicationId());
//			findByEstimateNumber.setConsumerApplicationNo(consumerApplicationNumber);
//			return findByEstimateNumber;
//
//		}
//
//		return null;
//	}

	@Override
	public Response<?> rejectProposalApplication(String consumerApplicationNumber, String rejectionProposal,
			String rejectionRemark, MultipartFile docRejectProposal, String userid, String userName, String role)
			throws ConsumerApplicationDetailException, DocumentTypeException {
		Response response = new Response();

		Upload rejectProposalUpload = null;

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNumber);

		ApplicationDocument findByConsumerApplicationDetailId = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());

		if (docRejectProposal != null) {
			rejectProposalUpload = uploadService.uploadFile(docRejectProposal, "REJECTION_PROPOSAL");
			if (rejectProposalUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Rejection Proposal Not Uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			if (findByConsumerApplicationDetailId != null) {
				findByConsumerApplicationDetailId.setDocRejectProposalFilePath(rejectProposalUpload);
				applicationDocumentRepository.save(findByConsumerApplicationDetailId);
			} else {
				ApplicationDocument applicationDoc = new ApplicationDocument();
				applicationDoc.setConsumerApplicationDetail(consumerApplicationDetail);
				applicationDoc.setDocRejectProposalFilePath(rejectProposalUpload);

				ApplicationDocument save = applicationDocumentRepository.save(applicationDoc);

				System.out.println("data_saved " + save);
			}

		}

		if (Objects.isNull(consumerApplicationDetail)) {
			logger.error("Consumer Application Not Found");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		} else {

//			start this code for JE level 22-January-2025
			if (role.trim().equals("JE_IT") || role.trim().equals("AE(O&M)")) {
				RejectProposalApplicationStatusHis rejectProposalApplicationStatusHis = new RejectProposalApplicationStatusHis();
				rejectProposalApplicationStatusHis.setConsumerApplicationNumber(consumerApplicationNumber);
				rejectProposalApplicationStatusHis.setApplicationStatusId(Integer.parseInt(
						consumerApplicationDetail.getApplicationStatus().getApplicationStatusId().toString()));
				rejectProposalApplicationStatusHis.setUserName(userName);
				rejectProposalApplicationStatusHis.setUserId(userid);
				rejectProposalApplicationStatusHis.setUserRole(role);

				rejectProposalApplicationStatusHisRepository.save(rejectProposalApplicationStatusHis);

//				consumerApplicationDetail.setRejectionProposal(rejectionProposal);
//				consumerApplicationDetail.setRejectionRemarkByDGMOAndM(rejectionRemark);
				consumerApplicationDetail.setRejectionRemarkByJE(rejectionRemark);
				consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.REJECTION_PROPOSAL_BY_JE.getId()).get());
				ConsumerApplicationDetail updatedConsumerApplicationDetail = consumerApplicationDetailRepository
						.save(consumerApplicationDetail);

				Optional<Upload> findById = uploadRepository.findById(rejectProposalUpload.getUploadId());
				Upload upload1 = findById.get();
				upload1.setConsuemrApplicatonID(updatedConsumerApplicationDetail.getConsumerApplicationId());
				uploadRepository.save(upload1);

				response.setList(Arrays.asList(updatedConsumerApplicationDetail));
				response.setCode(HttpCode.OK);
				response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
				return response;

			} else {
				consumerApplicationDetail.setRejectionProposal(rejectionProposal);
				consumerApplicationDetail.setRejectionRemarkByDGMOAndM(rejectionRemark);
				if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {
					List<ConsumerApplicationDetail> consumerDetailList = consumerApplicationDetailRepository
							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
					System.err.println("consumerDetailList : " + consumerDetailList);
					if (consumerDetailList.size() > 1) {
						return new Response<>(HttpCode.NOT_ACCEPTABLE,
								"Application can not revert back because this erp is tagged with multiple applications kindly contact DSP Team",
								null);
					}
					consumerDetailList.stream().forEach(con -> {
						deletErp(consumerApplicationDetail.getErpWorkFlowNumber());

					});
					consumerApplicationDetail.setErpWorkFlowNumber(null);
					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
							.findById(ApplicationStatusEnum.APPLICATION_REJECT_BY_GM.getId()).get());
				} else {
					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
							.findById(ApplicationStatusEnum.REJECTION_PROPOSAL_BY_DGM.getId()).get());
				}
				ConsumerApplicationDetail updatedConsumerApplicationDetail = consumerApplicationDetailRepository
						.save(consumerApplicationDetail);

				Optional<Upload> findById = uploadRepository.findById(rejectProposalUpload.getUploadId());
				Upload upload1 = findById.get();
				upload1.setConsuemrApplicatonID(updatedConsumerApplicationDetail.getConsumerApplicationId());
				uploadRepository.save(upload1);

				response.setList(Arrays.asList(updatedConsumerApplicationDetail));
				response.setCode(HttpCode.OK);
				response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
				return response;
			}

		}

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

	@Override
	public Response<PageConsumerApplicationDetailDTO> findAllConsumerApplicationDetailNewPagination(Integer page,
			Integer size, ConsumerApplicationDetailsFilterDTO filterDTO, String filterType, String filterValue)
			throws ConsumerApplicationDetailException {

		System.out.println("findAllConsumerApplicationDetailPagination method is calling !!!");

		Response<PageConsumerApplicationDetailDTO> response = new Response<PageConsumerApplicationDetailDTO>();
		PageConsumerApplicationDetailDTO pageConsumerApplicationDetailDTO = new PageConsumerApplicationDetailDTO();
		Page<ConsumerApplicationDetail> consumerApplicationDetails = findConsumerApplicationDetailNewPaginate(page,
				size, filterDTO, filterType, filterValue);
		Map<String, Object> pageData = new HashMap<>();
		Meta meta = new Meta();
		meta.setCurrentPage(consumerApplicationDetails.getNumber());
		meta.setTotalItems(consumerApplicationDetails.getTotalElements());
		meta.setTotalPages(consumerApplicationDetails.getTotalPages());
		pageConsumerApplicationDetailDTO.setMeta(meta);
		List<ConsumerApplicationDetail> consumerApplicationDetailList = consumerApplicationDetails.getContent();

		pageConsumerApplicationDetailDTO.setList(consumerApplicationDetailList);
		response.setList(Arrays.asList(pageConsumerApplicationDetailDTO));
		response.setCode("200");
		response.setMessage("All record retrieved successfully");
		return response;

	}

	private Page<ConsumerApplicationDetail> findConsumerApplicationDetailNewPaginate(Integer page, Integer size,
			ConsumerApplicationDetailsFilterDTO filterDTO, String filterType, String filterValue) {

		System.out.println("findConsumerApplicationDetailNewPaginate method is calling !!!");

		Response<ConsumerApplicationDetail> response = new Response<>();
		Pageable paging = PageRequest.of(page, size);
		Page<ConsumerApplicationDetail> pages = null;
		Integer totalRecords = 0;

		if (filterDTO.getUserLoginId() != null) {

			User user = userService.findByUserId(filterDTO.getUserLoginId());

			switch (user.getAccessLevel()) {
			case "1":
				// discomId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());

				break;
			case "2":

				// regionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());

				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}

				break;
			case "3":
				// circleId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}

				break;
			case "4":
				// divisionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}

				break;
			case "5":
				// subDivisionId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}
				filterDTO.setSubDivisionId(user.getUserSubDivision().getSubDivisionId());

				break;
			case "6":
				// dcId
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				if (Optional.ofNullable(user.getUserDivision()).isPresent()) {
					filterDTO.setDivisionId(user.getUserDivision().getDivisionId());
				}
				filterDTO.setSubDivisionId(user.getUserSubDivision().getSubDivisionId());
				filterDTO.setDcId(user.getUserDc().getDcId());

				break;

			default:
				break;
			}

		}

		if (filterDTO.getConsumerLoginId() != null) {

			Consumer consumer = consumerService.findByConsumerLoginId(filterDTO.getConsumerLoginId());

			filterDTO.setConsumerId(consumer.getConsumerId());
		}

		if (filterType != null) {
			if (filterType.equalsIgnoreCase("Applicant Name")) {
				pages = consumerApplictionDetailRepository
						.findByConsumerApplicationDetailsUserWiseApplicantNamePaginate(filterDTO.getDiscomId(),
								filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
								filterDTO.getSubDivisionId(), filterDTO.getDcId(), filterDTO.getConsumerId(),
								filterValue.toUpperCase(), paging);
			} else if (filterType.equalsIgnoreCase("ApplicationNumber")) {
				pages = consumerApplictionDetailRepository
						.findByConsumerApplicationDetailsUserWiseApplicationNumberPaginate(filterDTO.getDiscomId(),
								filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
								filterDTO.getSubDivisionId(), filterDTO.getDcId(), filterDTO.getConsumerId(),
								filterValue.toUpperCase(), paging);
			} else if (filterType.equalsIgnoreCase("ApplicationStatus")) {
				pages = consumerApplictionDetailRepository
						.findByConsumerApplicationDetailsUserWiseApplicationStatusPaginate(filterDTO.getDiscomId(),
								filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
								filterDTO.getSubDivisionId(), filterDTO.getDcId(), filterDTO.getConsumerId(),
								filterValue, paging);
			} else if (filterType.equalsIgnoreCase("NatureOfWork")) {
				pages = consumerApplictionDetailRepository.findByConsumerApplicationDetailsUserWiseNatureOfWorkPaginate(
						filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
						filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), filterDTO.getDcId(),
						filterDTO.getConsumerId(), filterValue, paging);
			}
		}

		if (Objects.isNull(pages) || pages.isEmpty()) {
			logger.error(
					"consumerApplictionDetailRepository.findByConsumerApplicationDetailsPaginate is returning Null when findConsumerApplicationDetailPaginate call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CONSUMER_APPLICATION_DETAIL_RECORDS_WITH_PAGINATION_FAILED_MESSAGE);
//			throw new ConsumerApplicationDetailException(response);
			return pages;
		} else {
			logger.info("Response is returning successfully");
			return pages;
		}

	}

	@Override
	public Response<?> jeReturnAmountApplication(String consumerApplicationNumber, BigDecimal jeReturnAmount) {
		Response response = new Response();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNumber);

		if (Objects.isNull(consumerApplicationDetail)) {
			logger.error("Consumer Application Not Found");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		} else {

			consumerApplicationDetail.setJeReturnAmount(jeReturnAmount);

			ConsumerApplicationDetail updatedConsumerApplicationDetail = consumerApplicationDetailRepository
					.save(consumerApplicationDetail);
			response.setList(Arrays.asList(updatedConsumerApplicationDetail));
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;

		}
	}

	@Override
	public ConsumerApplicationDetail updateConsumerApplicationDetailChangeSchemeType(
			ConsumerApplicationDetailForm consumerApplicationDetailForm, MultipartFile docRegistry,
			MultipartFile docT$cpPermission, MultipartFile docReraPermission, MultipartFile docNoc,
			MultipartFile docKhasraKhatoni, MultipartFile docIndividualOrGroup, MultipartFile docAdministrative,
			Long id, MultipartFile docGst, MultipartFile docEnergyBill, MultipartFile docSamagraFile,
			MultipartFile docLoadSheet, MultipartFile docMap,MultipartFile docconsentletter)
			throws ConsumerApplicationDetailException, DocumentTypeException, ApplicationDocumentException {

		Response response = new Response();
		ApplicationDocument applicationDocument = new ApplicationDocument();

		if (id == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application Id not found  ");
			throw new ConsumerApplicationDetailException(response);
		}

		ConsumerApplicationDetail consumerAppdetail = consumerApplicationDetailService.findByConsumerApplicationId(id);

		if (Objects.isNull(consumerApplicationDetailForm)) {
			logger.error("Consumer Application Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ConsumerApplicationDetailException(response);
		}

		Upload registryUpload = null;
		Upload t$cpPermissionUpload = null;
		Upload reraPermissionUpload = null;
		Upload nocUpload = null;
		Upload khasraKhatoniUpload = null;
		Upload docAdministrativeUpload = null;
		Upload docGstUpload = null;
		Upload energyBillDocUpload = null;
		Upload individualGroupUpload = null;
		Upload samagraUpload = null;
		Upload loadSheetUpload = null;
		Upload mapUpload = null;
		Upload docconsentletterUpload = null;
		/*********** validations start ***********************/

		NatureOfWork natureOfWorkdb = null;
		try {
			natureOfWorkdb = natureOfWorkService
					.findByNatureOfWorkId(consumerApplicationDetailForm.getNatureOfWorkTypeId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (natureOfWorkdb != null) {
//				this is for Line Shifting
			if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(1l) == 0)) {
				if (consumerApplicationDetailForm.getIvrsNo() == null
						|| consumerApplicationDetailForm.getIvrsNo().equals("")) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill IVRS number");
					throw new ConsumerApplicationDetailException(response);
				}

				if (consumerApplicationDetailForm.getSupplyVoltageId() == null
						|| consumerApplicationDetailForm.getSupplyVoltageId().isEmpty()) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill Supply Voltage details");
					throw new ConsumerApplicationDetailException(response);
				}

			}
//				this is for NSC 
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(2l) == 0)) {

			}
//				this is for colony eletrification  legal
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(3l) == 0)) {

				if (docReraPermission == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload docReraPermission document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (docT$cpPermission == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload docT$cpPermission With Owner document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (consumerApplicationDetailForm.getColonyLegalSelectionType() == null
						|| consumerApplicationDetailForm.getColonyLegalSelectionType().equals("")) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill colony selection type ");
					throw new ConsumerApplicationDetailException(response);
				}

			}
//				this is for colony eletrifacation  illegal
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(4l) == 0)) {

				if (docRegistry == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Registry document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (docNoc == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload NOC document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (consumerApplicationDetailForm.getColonyIllegalSelectionType() == null
						|| consumerApplicationDetailForm.getColonyIllegalSelectionType().equals("")) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill colony illegal selection type ");
					throw new ConsumerApplicationDetailException(response);
				}
				if (consumerApplicationDetailForm.getColonyIllegalSelectionType().equals("2")) {
					if (consumerApplicationDetailForm.getIndividualOrGroupId() == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Individual and Group Id Can Not be null of Illegal Application");
						throw new ConsumerApplicationDetailException(response);
					}

					if (consumerApplicationDetailForm.getIndividualOrGroupId().equals(2L)
							&& docIndividualOrGroup == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Please upload Group File document");
						throw new ConsumerApplicationDetailException(response);
					}
				}

			}

			// this is for LineShifting(Govermnet)
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(6l) == 0)) {

				if (docAdministrative == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Doc Administrative document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (consumerApplicationDetailForm.getSupplyVoltageId() == null
						|| consumerApplicationDetailForm.getSupplyVoltageId().isEmpty()) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill Supply Voltage details");
					throw new ConsumerApplicationDetailException(response);
				}

			}

			// this is for OYT
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(5l) == 0) || (natureOfWorkdb.getNatureOfWorkTypeId().compareTo(13l) == 0) || (natureOfWorkdb.getNatureOfWorkTypeId().compareTo(14l) == 0) ) {

				if (docKhasraKhatoni == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Doc Khasar-Khatoni document");
					throw new ConsumerApplicationDetailException(response);
				}

//				ye field hata di hai
//				 anurag or samshad ke khane pr
//				if (consumerApplicationDetailForm.getSamagraId() != null) {
//					if (docSamagraFile == null) {
//						response.setCode(HttpCode.NULL_OBJECT);
//						response.setMessage("Please upload Doc Samagra document");
//						throw new ConsumerApplicationDetailException(response);
//					}
//				}

//				if (consumerApplicationDetailForm.getAadharNo() == null
//						|| consumerApplicationDetailForm.getAadharNo().equals("")) {
//					response.setCode(HttpCode.NULL_OBJECT);
//					response.setMessage("Please fill aadhar number");
//					throw new ConsumerApplicationDetailException(response);
//
//				}

				if (consumerApplicationDetailForm.getKhasra() == null
						|| consumerApplicationDetailForm.getKhasra().equals("")) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill khasra number");
					throw new ConsumerApplicationDetailException(response);
				}

				if (consumerApplicationDetailForm.getKhatoni() == null
						|| consumerApplicationDetailForm.getKhatoni().equals("")) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill khatoni number");
					throw new ConsumerApplicationDetailException(response);
				}

			}

//			this is for Load Inhancement
			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(7l) == 0)) {
				if (docEnergyBill == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Doc energyBillDoc document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (consumerApplicationDetailForm.getIvrsNo() == null
						|| consumerApplicationDetailForm.getIvrsNo().equals("")) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please fill IVRS number");
					throw new ConsumerApplicationDetailException(response);
				}

			}

			else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(10l) == 0)) {

				if (docLoadSheet == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Load Sheet document");
					throw new ConsumerApplicationDetailException(response);
				}

				if (docMap == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload document Map");
					throw new ConsumerApplicationDetailException(response);
				}

			}
			consumerAppdetail.setNatureOfWorkType(natureOfWorkdb);

		}

		
		else if ((natureOfWorkdb.getNatureOfWorkTypeId().compareTo(14l) == 0)) {

			if (docconsentletter == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please upload consentletter document");
				throw new ConsumerApplicationDetailException(response);
			}

		
		consumerAppdetail.setNatureOfWorkType(natureOfWorkdb);

	}
		/*********** validations ends ************************/
		
		if (docconsentletter != null) {
			docconsentletterUpload = uploadService.uploadFile(docconsentletter, "CONSENT_LETTER");
			if (registryUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document consent letter not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setConsentletter(docconsentletterUpload);
		}

		if (docRegistry != null) {
			registryUpload = uploadService.uploadFile(docRegistry, "REGISTRY");
			if (registryUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Registry not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocRegistry(registryUpload);
		}

		if (docT$cpPermission != null) {
			t$cpPermissionUpload = uploadService.uploadFile(docT$cpPermission, "T$CP_PERMISSION");
			if (t$cpPermissionUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document T&CP Permission not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocT$cpPermission(t$cpPermissionUpload);
		}
		if (docReraPermission != null) {
			reraPermissionUpload = uploadService.uploadFile(docReraPermission, "RERA_PERMISSION");
			if (reraPermissionUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Rera Permission not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocReraPermission(reraPermissionUpload);
		}

		if (docNoc != null) {
			nocUpload = uploadService.uploadFile(docNoc, "NOC");
			if (nocUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document NOC not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocNoc(nocUpload);
		}

		if (docIndividualOrGroup != null) {
			individualGroupUpload = uploadService.uploadFile(docIndividualOrGroup, "Group_File");
			if (individualGroupUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Groupfile not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocGroup(individualGroupUpload);
		}

		if (docAdministrative != null) {
			docAdministrativeUpload = uploadService.uploadFile(docAdministrative, "Administrative");
			if (docAdministrativeUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Administrative not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocAdministrative(docAdministrativeUpload);
		}

		// 999
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
			samagraUpload = uploadService.uploadFile(docSamagraFile, "SAMAGRA_FILE");
			if (samagraUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Samagra File not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocSamagraFile(samagraUpload);
		}

		if (docGst != null) {
			docGstUpload = uploadService.uploadFile(docGst, "GST");
			applicationDocument.setDocGst(docGstUpload);
		}

		if (docEnergyBill != null) {
			energyBillDocUpload = uploadService.uploadFile(docEnergyBill, "energyBillDoc");
			if (energyBillDocUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document energyBillDoc not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocEnergyBillFile(energyBillDocUpload);
		}

		if (docLoadSheet != null) {
			loadSheetUpload = uploadService.uploadFile(docLoadSheet, "LOAD_SHEET");
			if (loadSheetUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document Load Sheet not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocLoadSheet(loadSheetUpload);
		}

		if (docMap != null) {
			mapUpload = uploadService.uploadFile(docMap, "MAP");
			if (mapUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Document MAP not uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			applicationDocument.setDocMap(mapUpload);
		}

//		01-05-2025
		consumerAppdetail.setPanNo(consumerApplicationDetailForm.getPanNo());
//		01-05-2025
		consumerAppdetail.setConsumerName(consumerAppdetail.getConsumerName().trim());

		consumerAppdetail.setGuardianName(consumerApplicationDetailForm.getGuardianName().trim());

		consumerAppdetail.setAddress(consumerApplicationDetailForm.getAddress().trim());

		if (consumerApplicationDetailForm.getAvedakKaPrakar() != null) {
			consumerAppdetail.setAvedakKaPrakar(consumerApplicationDetailForm.getAvedakKaPrakar());
		}
		if (consumerApplicationDetailForm.getAvedakRemark() != null) {
			consumerAppdetail.setAvedakRemark(consumerApplicationDetailForm.getAvedakRemark());
		}

		if (consumerApplicationDetailForm.getGstNumber() != null) {
			consumerAppdetail.setGstNumber(consumerApplicationDetailForm.getGstNumber());
		}
		if (consumerApplicationDetailForm.getGstNumberProvide() != null) {
			consumerAppdetail.setGstNumberProvide(consumerApplicationDetailForm.getGstNumberProvide());
		}

		if (consumerApplicationDetailForm.getBillNo() != null
				&& !consumerApplicationDetailForm.getBillNo().equals("")) {
			consumerAppdetail.setBillNo(consumerApplicationDetailForm.getBillNo().trim());
		}

//		CHARITRA ADD KEY 13-11-2025  fOR oyt CASE 
		if (consumerApplicationDetailForm.getOytTepOrParma() != null
				&& !consumerApplicationDetailForm.getOytTepOrParma().equals("")) {
			consumerAppdetail.setOytTepOrParma(consumerApplicationDetailForm.getOytTepOrParma().trim());
			consumerAppdetail.setOytTempApplicationNo(consumerAppdetail.getConsumerApplicationNo() + "T");
		}

		consumerAppdetail.setIsRejected(Boolean.FALSE);
		consumerAppdetail.setRejectedRemark("NA");

		consumerAppdetail.setShortDescriptionOfWork(consumerApplicationDetailForm.getShortDescriptionOfWork());

		consumerAppdetail.setPincode(consumerApplicationDetailForm.getPinCode());

		if (consumerApplicationDetailForm.getAadharNo() != null) {
			consumerAppdetail.setAadharNo(consumerApplicationDetailForm.getAadharNo());
		}

		if (consumerApplicationDetailForm.getWorkAllocationAddress() != null
				&& !consumerApplicationDetailForm.getWorkAllocationAddress().equals("")) {
			consumerAppdetail.setWorkAllocationAddress(consumerApplicationDetailForm.getWorkAllocationAddress().trim());
		} else {
			consumerAppdetail.setWorkAllocationAddress(null);
		}

		if (consumerApplicationDetailForm.getWorkLocationAddresscb() == null) {
			consumerAppdetail.setWorkLocationAddresscb(false);
		} else {
			consumerAppdetail.setWorkLocationAddresscb(true);
		}

		if (consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(1L)
				|| consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(7L) ||  consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(13L) 
				|| consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(14L)) {
			if (consumerApplicationDetailForm.getIvrsNo() != null
					&& !consumerApplicationDetailForm.getIvrsNo().equals("")) {
				consumerAppdetail.setIvrsNo(consumerApplicationDetailForm.getIvrsNo().trim());
			}
		} else {
			consumerAppdetail.setIvrsNo(null);
		}

		if (!consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(1L)
				&& !consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(6L)) {
			if (consumerApplicationDetailForm.getLoadRequested() != null) {
				consumerAppdetail.setLoadRequested(consumerApplicationDetailForm.getLoadRequested());
			}
			if (consumerApplicationDetailForm.getLoadRequested() != null) {
				LoadRequested loadRequestedDb = loadRequestedService
						.findById(consumerApplicationDetailForm.getLoadRequestedId());
				consumerAppdetail.setLoadRequestedId(loadRequestedDb);
			}
			if (consumerApplicationDetailForm.getArea() != null) {
				consumerAppdetail.setArea(consumerApplicationDetailForm.getArea());
			}
			if (consumerApplicationDetailForm.getLandAreaUnitId() != null) {
				LandAreaUnit landAreaUnit = landAreaUnitService
						.findById(consumerApplicationDetailForm.getLandAreaUnitId());

				consumerAppdetail.setLandAreaUnit(landAreaUnit);
			}
			if (consumerApplicationDetailForm.getLandAreaUnitName() != null) {
				consumerAppdetail.setLandAreaUnitName(consumerApplicationDetailForm.getLandAreaUnitName());
			}
		} else {
			consumerAppdetail.setLoadRequested(null);
			consumerAppdetail.setLoadRequestedId(null);
			consumerAppdetail.setArea(null);
			consumerAppdetail.setLandAreaUnit(null);
			consumerAppdetail.setLandAreaUnitName(null);
		}

		if (consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(3L)
				|| consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(4L)) {
			if (consumerApplicationDetailForm.getNoOfPlot() != null) {
				consumerAppdetail.setNoOfPlot(consumerApplicationDetailForm.getNoOfPlot());
			}
		} else {
			consumerAppdetail.setNoOfPlot(null);
		}
		if (consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(4L)) {
			if (consumerApplicationDetailForm.getColonyIllegalSelectionType() != null) {
				consumerAppdetail
						.setColonyIllegalSelectionType(consumerApplicationDetailForm.getColonyIllegalSelectionType());
			}
			if (consumerApplicationDetailForm.getColonyIllegalSelectionType().equals("1")) {
				consumerAppdetail.setIndividualOrGroup(null);
			} else {
				if (consumerApplicationDetailForm.getIndividualOrGroupId() != null) {
					IndividualOrGroup individualOrGroup = individualOrGroupService
							.findById(consumerApplicationDetailForm.getIndividualOrGroupId());

					consumerAppdetail.setIndividualOrGroup(individualOrGroup);
				}

			}
		} else {
			consumerAppdetail.setIndividualOrGroup(null);
			consumerAppdetail.setColonyIllegalSelectionType(null);
		}

		if (consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(3L)) {
			consumerAppdetail.setColonyLegalSelectionType(consumerApplicationDetailForm.getColonyLegalSelectionType());
		} else {
			consumerAppdetail.setColonyLegalSelectionType(null);
		}

		if ((consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(5L)) || (consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(13L)) || (consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(14L))) {

			if (consumerApplicationDetailForm.getSamagraId() != null) {
				consumerAppdetail.setSamagraId(consumerApplicationDetailForm.getSamagraId());
			} else {
				consumerAppdetail.setSamagraId(null);
			}

			if (consumerApplicationDetailForm.getKhasra() != null) {
				consumerAppdetail.setKhasra(consumerApplicationDetailForm.getKhasra());
			}

			if (consumerApplicationDetailForm.getKhatoni() != null) {
				consumerAppdetail.setKhatoni(consumerApplicationDetailForm.getKhatoni());
			}
		} else {
			consumerAppdetail.setKhasra(null);
			consumerAppdetail.setKhatoni(null);
			consumerAppdetail.setSamagraId(null);
		}

		SchemeType schemeTypeDb = null;
		SupplyVoltage supplyVoltageDb = null;

		consumerAppdetail.setDtr(null);
		consumerAppdetail.setPtr(null);
		consumerAppdetail.setLt(null);
		consumerAppdetail.setHt11Kv(null);
		consumerAppdetail.setHt33Kv(null);
		consumerAppdetail.setHt132Kv(null);
//		special check start in the condition of NSC application
		if (consumerApplicationDetailForm.getVoltageLevel() != null) {
			consumerAppdetail.setVoltageLevel(consumerApplicationDetailForm.getVoltageLevel());
		}
		if (consumerApplicationDetailForm.getIndividualOrGroupId() != null) {
			IndividualOrGroup individualOrGroup = individualOrGroupService
					.findById(consumerApplicationDetailForm.getIndividualOrGroupId());

			consumerAppdetail.setIndividualOrGroup(individualOrGroup);
		}
//		special check start in the condition of NSC application
		if (consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(1L)
				|| consumerApplicationDetailForm.getNatureOfWorkTypeId().equals(6L)) {
			if (consumerApplicationDetailForm.getSupplyVoltageId() != null) {
				for (Long s : consumerApplicationDetailForm.getSupplyVoltageId()) {
					System.out.println("************************************************++++++ ========" + s);
//				supplyVoltageDb = supplyVoltageService.findBySupplyVoltageId(s);

					if (s == 3l) {// 4l//5l
						consumerAppdetail.setDtr(3l);
					}

					if (s == 4l) {
						consumerAppdetail.setPtr(4l);
					}

					if (s == 5l) {
						consumerAppdetail.setLt(5l);
					}
					if (s == 6l) {
						consumerAppdetail.setHt11Kv(6l);
					}

					if (s == 7l) {
						consumerAppdetail.setHt33Kv(7l);
					}
					if (s == 8l) {
						consumerAppdetail.setHt132Kv(8l);
					}

				}
			}
		}
		if (consumerApplicationDetailForm.getSchemeTypeId() != null) {
			try {
				schemeTypeDb = schemeTypeService.findBySchemeTypeId(consumerApplicationDetailForm.getSchemeTypeId());
				consumerAppdetail.setSchemeType(schemeTypeDb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

//		scheme type me 5 bej ya 7 be tabhi age ka ka hoga
		if (consumerApplicationDetailForm.getSchemeTypeId() == null) {
			try {
				schemeTypeDb = schemeTypeService
						.findBySchemeTypeId(consumerAppdetail.getSchemeType().getSchemeTypeId());
				consumerAppdetail.setSchemeType(schemeTypeDb);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		Consumer consumer = null;
		consumer = consumerService.getCurrentLoginConsumerByLoginId();
		if (consumer != null) {
			consumerAppdetail.setConsumers(consumer);
		}

		if (consumerApplicationDetailForm.getDcId() != null) {
			try {
				DistributionCenter distributionCenterDb = distributionCenterService
						.findDistributionCenterById(consumerApplicationDetailForm.getDcId());
				if (distributionCenterDb != null) {
					consumerAppdetail.setDistributionCenter(distributionCenterDb);
				}
				// district
				try {
					District districtDb = districtService
							.findDistrictById(consumerApplicationDetailForm.getDistrictId());
					if (districtDb != null) {
						consumerAppdetail.setDistrict(districtDb);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

//		25-september-2024
		if (consumerAppdetail.getNscApplicationNo() != null
				&& consumerAppdetail.getApplicationStatus().getApplicationStatusId().equals(3L)) {
			consumerAppdetail.setApplicationStatus(
					applicationStatusRepository.findById(ApplicationStatusEnum.PENDING_FOR_GIS_LOCATION.getId()).get());
		} else if (consumerAppdetail.getNscApplicationNo() != null
				&& consumerAppdetail.getApplicationStatus().getApplicationStatusId().equals(4L)) {
			consumerAppdetail.setApplicationStatus(
					applicationStatusRepository.findById(ApplicationStatusEnum.PENDING_FOR_GIS_LOCATION.getId()).get());
		}else if (consumerAppdetail.getNatureOfWorkType().equals(5l) && consumerAppdetail.getNscApplicationNo()==null) {
			consumerAppdetail.setApplicationStatus(
					applicationStatusRepository.findById(ApplicationStatusEnum.PENDING_FOR_GIS_LOCATION.getId()).get());
		}
		else {
			consumerAppdetail.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId()).get());

		}
//		25-september-2024
		ConsumerApplicationDetail consumerApplicationDetailResponse = consumerApplictionDetailRepository
				.save(consumerAppdetail);

		if (consumerApplicationDetailResponse != null) {

			ApplicationDocument applicationDocumentResponse = null;
			List<ApplicationDocument> findAllDataByConsumerApplicationId = applicationDocumentRepository
					.findAllDataByConsumerApplicationId(consumerApplicationDetailResponse.getConsumerApplicationId());
			System.out.println("findAllDataByConsumerApplicationId : " + findAllDataByConsumerApplicationId);
			for (ApplicationDocument appDocument : findAllDataByConsumerApplicationId) {
				applicationDocumentRepository.deleteById(appDocument.getApplicationDocumentId());
			}
			applicationDocument.setConsumerApplicationDetail(consumerApplicationDetailResponse);
			applicationDocumentResponse = applicationDocumentService.saveApplicationDocument(applicationDocument);
		}
		if (Objects.isNull(consumerApplicationDetailResponse)) {
			logger.error("consumerApplictionDetailRepository.save(consumerApplicationDetail) is returning Null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
			throw new ConsumerApplicationDetailException(response);
		} else {
			return consumerApplicationDetailResponse;
		}

	}

	@Override
	public Response updateMKMYDocument(MultipartFile samagraFile, MultipartFile khasraFile,
			String consumerApplicationNo) throws DocumentTypeException {

		ConsumerApplicationDetail applicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);

		if (applicationDetail == null) {
			return new Response(HttpCode.NULL_OBJECT, "Application not found in database");
		}

		ApplicationDocument applicationDocument = applicationDocumentRepository
				.findByConsumerApplicationDetailId(applicationDetail.getConsumerApplicationId());
		ApplicationDocument docToSave = (applicationDocument == null) ? new ApplicationDocument() : applicationDocument;

		Upload samagraUpload = null;
		Upload khasraUpload = null;

		if (applicationDocument == null) {

			if (samagraFile == null || khasraFile == null) {

				return new Response(HttpCode.NULL_OBJECT, samagraFile == null ? "Please Upload Samagra Document"
						: "Please Upload Khasra-Khatoni Document");
			}

			samagraUpload = uploadService.uploadFile(samagraFile, "SAMAGRA_FILE");
			khasraUpload = uploadService.uploadFile(khasraFile, "KHASRA_KHATONI");
			docToSave.setDocSamagraFile(samagraUpload);
			docToSave.setDocKhasraKhatoni(khasraUpload);
		} else {

			if (applicationDocument.getDocSamagraFile() == null) {
				samagraUpload = uploadService.uploadFile(samagraFile, "SAMAGRA_FILE");
				docToSave.setDocSamagraFile(samagraUpload);

			}

			if (applicationDocument.getDocKhasraKhatoni() == null) {
				khasraUpload = uploadService.uploadFile(khasraFile, "KHASRA_KHATONI");
				docToSave.setDocKhasraKhatoni(khasraUpload);
			}
		}

		if (samagraUpload == null && applicationDocument.getDocSamagraFile() == null) {
			return new Response(HttpCode.NULL_OBJECT, "Document Samagra not uploaded.");
		}

		if (khasraUpload == null && applicationDocument.getDocKhasraKhatoni() == null) {
			return new Response(HttpCode.NULL_OBJECT, "Document Khasra-Khatoni not uploaded.");
		}

		docToSave.setConsumerApplicationDetail(applicationDetail);

		ApplicationDocument savedDoc = applicationDocumentRepository.save(docToSave);

		return new Response(HttpCode.UPDATED, "Document Updated Successfully", Arrays.asList(savedDoc));
	}

	@Transactional
	public void deletErp(String erp) {
		estimateAmountRepository.deleteByErpNo(erp);

	}

	public String deleteRejectedApplication(String erpNo, String consumerApplicationNo) {
		List<String> erpNumberDemandPaymentDone = new ArrayList<>();

		List<ConsumerApplicationDetail> erpNoDB = consumerApplicationDetailRepository.findByErpNo(erpNo);
		if (erpNoDB.size() > 0) {
			for (ConsumerApplicationDetail cad : erpNoDB) {
				if (cad.getApplicationStatus().getApplicationStatusId() > 12
						&& !cad.getApplicationStatus().getApplicationStatusId().equals(37L)) {
					System.err.println("consumer applciaiotn no : " + cad.getConsumerApplicationNo());
					if(!consumerApplicationNo.equals(cad.getConsumerApplicationNo()))
					erpNumberDemandPaymentDone.add(cad.getConsumerApplicationNo());
				}
			}
		}
		if (!erpNumberDemandPaymentDone.isEmpty()) {

			System.err.println("Kindly contact DSP Team as the list size is : " + erpNumberDemandPaymentDone.size());
			return "Kindly contact DSP Team";
		} else {
			return "ERP No. can be deleted";
		}

	}
	

}
