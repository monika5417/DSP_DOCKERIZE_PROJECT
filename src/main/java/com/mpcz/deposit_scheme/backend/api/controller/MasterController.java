package com.mpcz.deposit_scheme.backend.api.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.builddesk.config.CacheStore;
import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationHeadCharges;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationType;
import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBid;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.domain.Designation;
import com.mpcz.deposit_scheme.backend.api.domain.Discom;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.District;
import com.mpcz.deposit_scheme.backend.api.domain.DocumentType;
import com.mpcz.deposit_scheme.backend.api.domain.Feeder;
import com.mpcz.deposit_scheme.backend.api.domain.GeoLocation;
import com.mpcz.deposit_scheme.backend.api.domain.GovDepartment;
import com.mpcz.deposit_scheme.backend.api.domain.IndividualOrGroup;
import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.domain.LoadRequested;
import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;
import com.mpcz.deposit_scheme.backend.api.domain.Region;
import com.mpcz.deposit_scheme.backend.api.domain.Role;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.Substation;
import com.mpcz.deposit_scheme.backend.api.domain.SupplyVoltage;
import com.mpcz.deposit_scheme.backend.api.domain.TariffCategory;
import com.mpcz.deposit_scheme.backend.api.domain.TaskType;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.VendorWorkOrder;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.dto.AccessLevelDTO;
import com.mpcz.deposit_scheme.backend.api.dto.ApplicationHeadChargesDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDocumentException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationStatusException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DesignationException;
import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FeederException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.LoadRequestedException;
import com.mpcz.deposit_scheme.backend.api.exception.MasterException;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWorkException;
import com.mpcz.deposit_scheme.backend.api.exception.RegionException;
import com.mpcz.deposit_scheme.backend.api.exception.RoleException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.SubstationException;
import com.mpcz.deposit_scheme.backend.api.exception.SupplyVoltageException;
import com.mpcz.deposit_scheme.backend.api.exception.TariffCategoryException;
import com.mpcz.deposit_scheme.backend.api.exception.TaskTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.GovDepartmentRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.DistributionCenterDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationDocumentService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationHeadChargesService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationTypeService;
import com.mpcz.deposit_scheme.backend.api.services.CircleService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationSurveyService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.DemandService;
import com.mpcz.deposit_scheme.backend.api.services.DesignationService;
import com.mpcz.deposit_scheme.backend.api.services.DiscomService;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.services.DistrictService;
import com.mpcz.deposit_scheme.backend.api.services.DocumentTypeService;
import com.mpcz.deposit_scheme.backend.api.services.FeederService;
import com.mpcz.deposit_scheme.backend.api.services.GeoLocationService;
import com.mpcz.deposit_scheme.backend.api.services.IndividualOrGroupService;
import com.mpcz.deposit_scheme.backend.api.services.LandAreaUnitService;
import com.mpcz.deposit_scheme.backend.api.services.LoadRequestedService;
import com.mpcz.deposit_scheme.backend.api.services.MasterService;
import com.mpcz.deposit_scheme.backend.api.services.NatureOfWorkService;
import com.mpcz.deposit_scheme.backend.api.services.RegionService;
import com.mpcz.deposit_scheme.backend.api.services.RoleServices;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;
import com.mpcz.deposit_scheme.backend.api.services.SubstationService;
import com.mpcz.deposit_scheme.backend.api.services.SupplyVoltageService;
import com.mpcz.deposit_scheme.backend.api.services.TariffCategoryService;
import com.mpcz.deposit_scheme.backend.api.services.TaskTypeService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.services.UserService;
import com.mpcz.deposit_scheme.backend.api.services.VendorWorkOrderService;
import com.mpcz.deposit_scheme.backend.api.services.WorkTypeService;
import com.mpcz.deposit_scheme.backend.api.util.AccessLevelUtil;
import com.mpcz.deposit_scheme.backend.api.util.DateUtil;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "MasterController", description = "Rest api for Master's API.")
@RestController
@RequestMapping(RestApiUrl.MASTER_API)
public class MasterController {

	Logger log = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	private SchemeTypeService schemeTypeService;

	@Autowired
	private DocumentTypeService documentTypeService;

	@Autowired
	private DiscomService discomService;

	@Autowired
	private WorkTypeService workTypeService;

	@Autowired
	private DistributionCenterService distributionCenterService;

	@Autowired
	private SubstationService substationService;

	@Autowired
	private FeederService feederService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private MasterService masterService;

	@Autowired
	private RoleServices roleServices;

	@Autowired
	private UserService userService;

	@Autowired
	private RegionService regionServices;

	@Autowired
	private CircleService circleService;

	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private VendorWorkOrderService vendorWorkOrderService;

	@Autowired
	private ConsumerApplicationSurveyService consumerApplicationSurveyService;
	
	@Autowired
	private ContractorForBidRepository contractorForBidRepository;

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private DemandService demandService;

	@Autowired
	GeoLocationService geoLocationService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private ApplicationHeadChargesService ApplicationHeadChargesService;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private ApplicationTypeService applicationTypeService;

	@Autowired
	private ApplicationDocumentService applicationDocumentService;

	@Autowired
	private SupplyVoltageService supplyVoltageService;

	@Autowired
	private TariffCategoryService tariffCategoryService;

	@Autowired
	private TaskTypeService taskTypeService;

	@Autowired
	private ApplicationStatusService applicationStatusService;
	
	@Autowired
	NatureOfWorkService natureOfWorkService;
	
	@Autowired
	DistrictService districtService;
	
	@Autowired
	LoadRequestedService loadRequestedService;
	
	
	@Autowired
	LandAreaUnitService landAreaUnitServicer;
	
	@Autowired
	IndividualOrGroupService individualOrGroupService;
	
	@Autowired
	private DistributionCenterService dcService;
	
	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
	
	@Autowired
	private ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;
	
	@Autowired
	private CacheStore<ApplicationType> applicationTypeCache;
	
	@Autowired
	private CacheStore<Discom> discomTypeCache;
	
	@Autowired
	private CacheStore<Region> regionTypeCache;
	
	@Autowired
	private GovDepartmentRepository govDepartmentRepository;

	@ApiOperation(value = "Retrieve all Scheme Type", notes = "Fetch all Scheme Type", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_SCHEME_TYPE_URL)
	public ResponseEntity<Response<?>> retrieveAllSchemeType(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SchemeTypeException {

		System.out.println("retrieveAllSchemeType !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_SCHEME_TYPE_URL + " : retrieveAllSchemeType()";
		log.info(method);

		Response<SchemeType> response = new Response<>();

		List<SchemeType> schemeType = schemeTypeService.findAllSchemeType();

		response.setList(schemeType);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Document Type", notes = "Fetch all Document Type", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DOCUMENT_TYPE_URL)
	public ResponseEntity<Response<?>> retrieveAllDocumentType(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DocumentTypeException {

		System.out.println("retrieveAllDocumentType !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_DOCUMENT_TYPE_URL
				+ " : retrieveAllDocumentType()";
		log.info(method);

		Response<DocumentType> response = new Response<>();

		List<DocumentType> documentType = documentTypeService.findAllDocumentType();

		response.setList(documentType);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Discom", notes = "Fetch all Discom", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DISCOM_URL)
	public ResponseEntity<Response<?>> retrieveAllDiscom(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DiscomException {

		System.out.println("retrieveAllDiscom !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_DISCOM_URL + " : retrieveAllDiscom()";
		log.info(method);

		Response<Discom> response = new Response<>();

		List<Discom> mis =  discomTypeCache.get("fromDb");
		if(mis!= null) {
			response.setList(mis);
			System.out.println("From Cache-"+mis.toString());
		} else {
			List<Discom> discom = discomService.findAllDiscom();
			System.out.println("From DB-"+discom.toString());
			discomTypeCache.add("fromDb",discom);
			response.setList(discom);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Work Type", notes = "Fetch all Work Type", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_WORK_TYPE_URL)
	public ResponseEntity<Response<?>> retrieveAllWorkType(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, WorkTypeException {

		System.out.println("retrieveAllWorkType !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_WORK_TYPE_URL + " : retrieveAllWorkType()";
		log.info(method);

		Response<WorkType> response = new Response<>();

		List<WorkType> workType = workTypeService.findAllWorkType();

		response.setList(workType);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Distribution Center", notes = "Fetch all Distribution Center", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DISTRIBUTION_CENTER_URL)
	public ResponseEntity<Response<?>> retrieveAllDistributionCenter(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {

		System.out.println("retrieveAllDistributionCenter !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_DISTRIBUTION_CENTER_URL
				+ " : retrieveAllDistributionCenter()";
		log.info(method);

		Response<DistributionCenter> response = new Response<>();

		List<DistributionCenter> distributionCenter = distributionCenterService.findAllActiveDistributionCenter();

		response.setList(distributionCenter);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve All Active Substations by DC", notes = "Pass DC id", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_SUBSTATION_BY_DC_URL)
	public ResponseEntity<Response<?>> retrieveAllSubstationsByDC(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SubstationException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_SUBSTATION_BY_DC_URL
				+ " : retrieveAllSubstationsByDC()";
		log.info(method);
		Response<Substation> response = new Response<Substation>();
		List<Substation> substations = substationService.findAllSubstationsByDC(id);
		response.setList(substations);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Substation", notes = "Fetch all Substation", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_SUBSTATION_URL)
	public ResponseEntity<Response<?>> retrieveAllSubstation(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SubstationException {

		System.out.println("retrieveAllSubstation !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_SUBSTATION_URL + " : retrieveAllSubstation()";
		log.info(method);

		Response<Substation> response = new Response<>();

		List<Substation> substation = substationService.findAllActiveSubstation();

		response.setList(substation);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Active Feeders by Substation", notes = "Pass Substation id", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_FEEDER_BY_SUBSTATION_URL)
	public ResponseEntity<Response<?>> retrieveAllFeedersBySubstation(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, FeederException {

		System.out.println("retrieveAllFeedersBySubstation !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_FEEDER_BY_SUBSTATION_URL
				+ " : retrieveAllFeedersBySubstation()";
		log.info(method);
		Response<Feeder> response = new Response<Feeder>();
		List<Feeder> feeders = feederService.findAllFeedersBySubstation(id);
		response.setList(feeders);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve All Designations", notes = "Fetch all designations", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DESIGNATIONL_URL)
	public ResponseEntity<Response<?>> retrieveAllDesignations(HttpServletResponse httpServletResponse)
			throws InvalidAuthenticationException, DesignationException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_DESIGNATIONL_URL
				+ " : retrieveAllDesignations()";
		log.info(method);
		Response<List<Designation>> response = designationService.findAll();
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all access levels", notes = "Fetach all access levels", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_ACCESS_LEVEL_URL)
	public ResponseEntity<Response<?>> retrieveAllAccessLevel(HttpServletResponse httpServletResponse)
			throws InvalidAuthenticationException, MasterException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_ACCESS_LEVEL_URL
				+ " : retrieveAllAccessLevel()";
		log.info(method);
//		Response<Map<Long, String>> response = masterService.findAllAccessLevel();
		Response<AccessLevelDTO> response = new Response();
		response.setList(AccessLevelUtil.getAccessLevelList());
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all roles", notes = "Fetch all roles", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_ROLE_URL)
	public ResponseEntity<Response<?>> retrieveAllRoles(HttpServletResponse httpServletResponse)
			throws InvalidAuthenticationException, RoleException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_ROLE_URL + " : retrieveAllRoles()";
		log.info(method);
		Response<List<Role>> response = roleServices.findAll();
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all regions", notes = "fetch all region", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_REGION_URL)
	public ResponseEntity<Response<?>> retrieveAllRegion(HttpServletResponse httpServletResponse)
			throws InvalidAuthenticationException, RegionException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_REGION_URL + "  : retrieveAllRegion()";
		log.info(method);
//		Response<List<Region>> response = regionServices.findAll();
		Response<Region> response = new Response<>();
		
		List<Region> mis =  regionTypeCache.get("fromDb");
		if(mis!= null) {
			response.setList(mis);
			System.out.println("From Cache-"+mis.toString());
		} else {
			List<Region> region = regionServices.findAllRegions();
			System.out.println("From DB-"+region.toString());
			regionTypeCache.add("fromDb",region);
			response.setList(region);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Check data is already exists or not", notes = "Pass data & master name", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PostMapping(RestApiUrl.IS_DATA_ALREADY_EXISTS)
	public ResponseEntity<Response<?>> isDataAlreadyExists(@RequestBody @Valid DataStatusDTO dataStatusDTO,
			BindingResult bindingResult, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ConsumerException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.IS_DATA_ALREADY_EXISTS + " : isDataAlreadyExists()";
		log.info(method);

		Response<?> response = new Response<>();
		List<ErrorDetails> errorList = null;

		String status = "";

		switch (dataStatusDTO.getMaster()) {

		case "user":
			if (dataStatusDTO.getEmail() != null || dataStatusDTO.getMobile() != null
					|| dataStatusDTO.getAadharNumber() != null || dataStatusDTO.getUserId() != null) {

				String value = "";
				if (dataStatusDTO.getEmail() != null) {
					value = "Email: " + dataStatusDTO.getEmail();
				} else if (dataStatusDTO.getMobile() != null) {
					value = "Mobile Number: " + dataStatusDTO.getMobile();
				} else if (dataStatusDTO.getAadharNumber() != null) {
					value = "Aadhar Number: " + dataStatusDTO.getAadharNumber();
				} else if (dataStatusDTO.getUserId() != null) {
					value = "User id: " + dataStatusDTO.getUserId();
				}
				try {
					status = userService.isUserRecordExist(dataStatusDTO);
					if (MasterDataStatus.EXISTS.equals(status)) {
						errorList = new ArrayList<ErrorDetails>();

						log.error(value + " is " + ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE + ": for master "
								+ dataStatusDTO.getMaster());

						ErrorDetails error = new ErrorDetails(new Date(), ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE,
								value + " is " + ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE);
						errorList.add(error);
						response.setMessage(ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE);
						response.setCode(ResponseCode.CONFLICT);
						response.setError(errorList);

						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					} else {
						response.setMessage(ResponseMessage.NEW_RECORD_MESSAGE);
						response.setCode(ResponseCode.OK);

						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}

				} catch (UserException e) {
					log.error("Exception while checking user " + value + " is alredady exists or not" + e.getMessage());
				}
			}

			break;
		case "holiday":

			break;
		case "role":

			break;

		case "tariffschedule":

		case "tariffcategory":

			break;
		case "tariffsubcategory":

			break;

		case "purposerevenuecategory":

			break;

		case "department":

			break;
		case "designation":

		case "district":

			break;
		case "tehsil":

			break;

		case "city":

			break;

		case "region":

			break;
		case "circle":

			break;
		case "division":

			break;
		case "subdivision":

			break;
		case "distributioncenter":

			break;
		case "substation":

			break;
		case "feeder":

			break;
		case "meter":

			break;

		case "submeter":

			break;

		case "nsc":

			break;
		case "consumer":
//			******************sandeep, starts**************************
			if (dataStatusDTO.getConsumerEmailId() != null || dataStatusDTO.getConsumerMobileNo() != null
					|| dataStatusDTO.getAadharNo() != null || dataStatusDTO.getPanNo() != null) {

				String value = "";
				if (dataStatusDTO.getConsumerEmailId() != null) {
					value = "Email: " + dataStatusDTO.getConsumerEmailId();
				} else if (dataStatusDTO.getConsumerMobileNo() != null) {
					value = "Mobile Number: " + dataStatusDTO.getConsumerMobileNo();
				} else if (dataStatusDTO.getAadharNo() != null) {
					value = "Aadhar Number: " + dataStatusDTO.getAadharNo();
				} else if (dataStatusDTO.getPanNo() != null) {
					value = "PAN Number: " + dataStatusDTO.getPanNo();
				}
				try {
					status = consumerService.isConsumerRecordExist(dataStatusDTO);
					if (MasterDataStatus.EXISTS.equals(status)) {
						errorList = new ArrayList<ErrorDetails>();

						log.error(value + " is " + ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE + ": for master "
								+ dataStatusDTO.getMaster());

						ErrorDetails error = new ErrorDetails(new Date(), ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE,
								value + " is " + ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE);
						errorList.add(error);
						response.setMessage(ResponseMessage.RECORD_ALREADY_EXISTS_MESSAGE);
						response.setCode(ResponseCode.CONFLICT);
						response.setError(errorList);

						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					} else {
						response.setMessage(ResponseMessage.NEW_RECORD_MESSAGE);
						response.setCode(ResponseCode.OK);

						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}

				} catch (ConsumerException e) {
					log.error("Exception while checking user " + value + " is alredady exists or not" + e.getMessage());
				}
			}

//		******************sandeep, ends**************************

			break;
		default:
			break;
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve All circles by region", notes = "Pass region id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_CIRCLE_BY_REGION_URL)
	public ResponseEntity<Response<?>> retrieveAllCirclesByRegion(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, CircleException {

		final String method = RestApiUrl.CIRCLE_API + RestApiUrl.GET_ALL_CIRCLE_BY_REGION_URL
				+ " : retrieveAllCirclesByRegion()";
		log.info(method);
		Response<Circle> response = new Response<Circle>();
		List<Circle> circles = circleService.findAllCirclesByRegion(id);
		response.setList(circles);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "pass upload id", notes = "Pass region id", response = Response.class, responseContainer = "List", tags = RestApiUrl.CIRCLE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_FILE)
	public void retrieveFileById(@PathVariable long id, HttpServletResponse response)
			throws FormValidationException, InvalidAuthenticationException, CircleException {

//		String fileTypePath = "";
//		fileTypePath = DocTypeEnum.getEnumByValue(fileType).getValue();

		final Upload uploadDoc = uploadService.findUpload(id);

		File file = null;
		String pdfName = "";

		if (uploadDoc != null && uploadDoc.getDocumentPath() != null
				&& !uploadDoc.getDocumentPath().trim().equals("")) {

			String fileExtension = null;
			String fileType = null;

			String separator = "\\";
			String[] docPathArray = uploadDoc.getDocumentPath().split(Pattern.quote("."));
			String[] fileTypeArray = uploadDoc.getDocumentPath().split(Pattern.quote(separator));

			if (docPathArray.length > 1) {
				fileExtension = "." + docPathArray[docPathArray.length - 1];
				fileType = fileTypeArray[0];
			}

			pdfName = fileType + DateUtil.getCurrentTimeStamp() + fileExtension;

			file = new File(uploadPath + File.separator + uploadDoc.getDocumentPath());

		}

		if (file != null && file.exists()) {

			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + pdfName));

			response.setContentLength((int) file.length());

			try {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				FileCopyUtils.copy(inputStream, response.getOutputStream());

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	@ApiOperation(value = "Retrieve Specific Consumer Application Survey Datails", notes = "Pass Consumer Application Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_APPLICATION_SURVEY_BY_APPLICATION_ID_URL)
	public ResponseEntity<Response<?>> retrieveApplicationSurveyByApplicationId(@PathVariable long id,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			ConsumerApplicationDetailException, ConsumerApplicationSurveyException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_APPLICATION_SURVEY_BY_APPLICATION_ID_URL
				+ " : retrieveApplicationSurveyByApplicationId()";
		log.info(method);
		Response<ConsumerApplicationSurvey> response = new Response();

		ConsumerApplicationSurvey consumerApplicationSurvey = consumerApplicationSurveyService
				.findByConsumersApplicationDetailConsumerApplicationId(id);

		response.setList(Arrays.asList(consumerApplicationSurvey));
		response.setCode(HttpCode.OK);
		response.setMessage("Records retrieved Successfully !");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve Specific Consumer Application Demand Datails", notes = "Pass Consumer Application  id", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_APPLICATION_DEMAND_BY_APPLICATION_ID_URL)
	public ResponseEntity<Response<?>> retrieveApplicationDemandByApplicationId(@PathVariable long id,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			ConsumerApplicationDetailException, DemandDetailException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_APPLICATION_DEMAND_BY_APPLICATION_ID_URL
				+ " : retrieveApplicationDemandByApplicationId()";

		Response<Demand> response = new Response();

		Demand demand = demandService.findByConsumersApplicationDetailConsumerApplicationId(id);

		response.setList(Arrays.asList(demand));
		response.setCode(HttpCode.OK);
		response.setMessage("Records retrieved Successfully !");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve Specific Geo Location  Details", notes = "Pass Consumer Application  number", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_GEO_lOCATION_DETAILS)
	public ResponseEntity<Response<?>> retrieveGeoLocationByApplicationNUmber(
			@PathVariable String consumerApplicationNumber, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, GeoLocationException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_GEO_lOCATION_DETAILS
				+ " : retrieveGeoLocationByApplicationNUmber()";

		Response<GeoLocation> response = new Response();

//		Demand demand = demandService.findByConsumersApplicationDetailConsumerApplicationId(id);
		GeoLocation geoLocation = geoLocationService.findbyCosumerApplicationNumber(consumerApplicationNumber);

		response.setList(Arrays.asList(geoLocation));
		response.setCode(HttpCode.OK);
		response.setMessage("Records retrieved Successfully !");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Application Type", notes = "Fetch all Application Type", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_APPLICATION_TYPE_URL)
	public ResponseEntity<Response<?>> retrieveAllApplicationType(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationTypeException {

		System.out.println("retrieveAllApplicationType !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_APPLICATION_TYPE_URL
				+ " : retrieveAllApplicationType()";
		log.info(method);

		Response<ApplicationType> response = new Response<>();

		List<ApplicationType> mis =  applicationTypeCache.get("fromDb");
		if(mis!= null) {
			response.setList(mis);
			System.out.println("From Cache-"+mis.toString());
		} else {
			List<ApplicationType> applicationType = applicationTypeService.findAllApplicationType();
			System.out.println("From DB-"+applicationType.toString());
			applicationTypeCache.add("fromDb",applicationType);
			response.setList(applicationType);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Application headWise Charges", notes = "Fetch all Application Type Charges", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PostMapping(RestApiUrl.GET_ALL_APPLICATION_CHARGES_BY_APPLICATIONID_PAYMENTTYPE_URL)
	public ResponseEntity<Response<?>> retrieveAllApplicationHeadWiseCharges(
			@Valid @RequestBody ApplicationHeadChargesDTO applicationHeadChargesDTO, BindingResult bindingResult,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			ApplicationTypeException, ApplicationHeadChargesException {

		System.out.println("retrieveAllApplicationType !!!!!");

		final String method = RestApiUrl.MASTER_API
				+ RestApiUrl.GET_ALL_APPLICATION_CHARGES_BY_APPLICATIONID_PAYMENTTYPE_URL
				+ " : retrieveAllApplicationType()";
		log.info(method);

		Response<ApplicationHeadCharges> response = new Response<>();

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {

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

		List<ApplicationHeadCharges> applicationHeadCharges = ApplicationHeadChargesService
				.findByApplicationIdPaymentTypeId(applicationHeadChargesDTO.getConsumerApplicationId(),
						applicationHeadChargesDTO.getPaymentTypeId());

		response.setList(applicationHeadCharges);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve Specific Consumer Application  Datails By Application Number", notes = "Pass Consumer Application  id", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_APPLICATION_DETAIL_BY_APPLICATION_NUMBER_URL)
	public ResponseEntity<Response<?>> retrieveApplicationDetailByApplicationNumber(@PathVariable String appNumber,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			ConsumerApplicationDetailException, DemandDetailException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_APPLICATION_DETAIL_BY_APPLICATION_NUMBER_URL
				+ " : retrieveApplicationDetailByApplicationNumber()";

		Response<ConsumerApplicationDetail> response = new Response();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findConsumerApplicationDetailByApplicationNo(appNumber);

		response.setList(Arrays.asList(consumerApplicationDetail));
		response.setCode(HttpCode.OK);
		response.setMessage("Records retrieved Successfully !");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Application Document On the basis Consumer Application Details Id", notes = "Pass Consumer Application Details Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_APPLICATION_DOCUMENT_BY_CONSUMER_APPLICATION_ID_URL)
	public ResponseEntity<Response<?>> retrieveAllApplicationDocumentByConsumerApplicationDetailsId(
			@PathVariable long id, HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationDocumentException {

		final String method = RestApiUrl.MASTER_API
				+ RestApiUrl.GET_ALL_APPLICATION_DOCUMENT_BY_CONSUMER_APPLICATION_ID_URL
				+ " : retrieveAllApplicationDocumentByConsumerApplicationDetailsId()";
		log.info(method);

		Response<ApplicationDocument> response = new Response<ApplicationDocument>();
		ApplicationDocument applicationDocument = null;

		applicationDocument = applicationDocumentService.findByConsumerApplicationDetailId(id);

		response.setList(Arrays.asList(applicationDocument));
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Supply Voltage", notes = "Fetch all Active SupplyVoltage", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_SUPPLY_VOLTAGE_URL)
	public ResponseEntity<Response<?>> retrieveAllSupplyVoltage(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, SupplyVoltageException {

		System.out.println("retrieveAllSupplyVoltage !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_SUPPLY_VOLTAGE_URL
				+ " : retrieveAllSupplyVoltage()";
		log.info(method);

		Response<SupplyVoltage> response = new Response<>();

		List<SupplyVoltage> supplyVoltage = supplyVoltageService.findAllSupplyVoltage();

		response.setList(supplyVoltage);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

//	@ApiOperation(value = "Retrieve specific Demand details by Consumer Application Id", notes = "Pass Consumer Application Id", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@GetMapping(RestApiUrl.GET_DEMAND_DETAIL_BY_CONSUMER_APPLICATION_ID_URL)
//	public ResponseEntity<Response<?>> retrieveSpecificDemandDetailsByConsumerApplicationId(@PathVariable long id,
//			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
//			ConsumerApplicationDetailException, DemandDetailException {
//
//		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_DEMAND_DETAIL_BY_CONSUMER_APPLICATION_ID_URL
//				+ " : retrieveSpecificDemandDetailsByConsumerApplicationId()";
//
//		Response<Demand> response = new Response();
//
//		Demand demand = demandService.findByConsumersApplicationDetailConsumerApplicationId(id);
//
//		response.setList(Arrays.asList(demand));
//		response.setCode(HttpCode.OK);
//		response.setMessage("Records retrieved Successfully !");
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//	}

//	charitra code start here

	@ApiOperation(value = "Retrieve   ConsuerApplication number and DC name and DC code and short discrption By Consumer Application Number", notes = "Fetch ConsuerApplication number and DC name and DC code and short discrption By Consumer Application Number", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_CONSUMER_APPLICATION_DC_DETAILS_BY_APPLICATION_NO_URL)
	public ResponseEntity<HashMap<String, String>> retrieveConsumerApplicationNumberAndDcNameAndDcCodeAndShortDistription(
			@PathVariable String ConsumerApplicationNumber)
			throws FormValidationException, InvalidAuthenticationException, ConsumerApplicationDetailException {

		final String method = RestApiUrl.MASTER_API
				+ RestApiUrl.GET_CONSUMER_APPLICATION_DC_DETAILS_BY_APPLICATION_NO_URL
				+ " : retrieveConsumerApplicationNumberAndDcNameAndDcCodeAndShortDistription()";
		log.info(method);

		Response<HashMap> response = new Response<>();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findConsumerApplicationDetailByApplicationNo(ConsumerApplicationNumber);

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("consumerApplication Number", consumerApplicationDetail.getConsumerApplicationNo());
		hashMap.put("Dc name", consumerApplicationDetail.getDistributionCenter().getDcName());
		hashMap.put("Dc code", consumerApplicationDetail.getDistributionCenter().getDcCode());
		hashMap.put("short discription", consumerApplicationDetail.getShortDescriptionOfWork());

//		response.setMap(1,hashMap);
//		response.setCode(HttpCode.OK);
//		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(hashMap);
	}

	@ApiOperation(value = "Retrieve   ConsuerApplication number and DC name and DC code and short discrption By Dc Code", notes = "Fetch ConsuerApplication number and DC name and DC code and short discrption By Dc Code", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_CONSUMER_APPLICATION_DC_DETAILS_BY_DC_CODE_URL)
	public ResponseEntity<ArrayList<DistributionCenterDetails>> retrieveConsumerApplicationNumberAndDcNameAndDcCodeAndShortDistriptionByDcCode(
			@PathVariable String dcCode) throws FormValidationException, InvalidAuthenticationException,
			ConsumerApplicationDetailException, DistributionCenterException {

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_CONSUMER_APPLICATION_DC_DETAILS_BY_DC_CODE_URL
				+ " : retrieveConsumerApplicationNumberAndDcNameAndDcCodeAndShortDistriptionByDcCode()";
		log.info(method);

		Response<HashMap> response = new Response<>();

		List<ConsumerApplicationDetail> consumerApplicationDetail = consumerApplicationDetailService
				.findConsumerApplicationDetailByDcCode(dcCode);
		ArrayList<DistributionCenterDetails> arrayList = new ArrayList();
		DistributionCenterDetails distributionCenterDetails;
		for (ConsumerApplicationDetail p : consumerApplicationDetail) {

			distributionCenterDetails = new DistributionCenterDetails();
			distributionCenterDetails.setConsumerApplicationNumber(p.getConsumerApplicationNo());
			distributionCenterDetails.setDcCode(p.getDistributionCenter().getDcCode());
			distributionCenterDetails.setDcName(p.getDistributionCenter().getDcName());
			distributionCenterDetails.setShortDiscription(p.getShortDescriptionOfWork());
			arrayList.add(distributionCenterDetails);

		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(arrayList);
	}

	@ApiOperation(value = "Retrieve all Tariff Category", notes = "Fetch all Tariff Category", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_TARIFF_CATEGORY_URL)
	public ResponseEntity<Response<?>> retrieveAllTariffCategory(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, TariffCategoryException {

		System.out.println("retrieveAllTariffCategory !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_TARIFF_CATEGORY_URL
				+ " : retrieveAllTariffCategory()";
		log.info(method);

		Response<TariffCategory> response = new Response<>();

		List<TariffCategory> tariffCategory = tariffCategoryService.findAllTariffCategory();

		response.setList(tariffCategory);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Task Type", notes = "Fetch all Task Type", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_TASK_TYPE_URL)
	public ResponseEntity<Response<?>> retrieveAllTaskType(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, TaskTypeException {

		System.out.println("retrieveAllTaskType !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_TASK_TYPE_URL + " : retrieveAllTaskType()";
		log.info(method);

		Response<TaskType> response = new Response<>();

		List<TaskType> taskType = taskTypeService.findAllTaskType();

		response.setList(taskType);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve all Application Status", notes = "Fetch all Application Status", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_APPLICATION_STATUS_URL)
	public ResponseEntity<Response<?>> retrieveAllApplicationStatus(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationStatusException {

		System.out.println("retrieveAllApplicationStatus !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_APPLICATION_STATUS_URL
				+ " : retrieveAllApplicationStatus()";
		log.info(method);

		Response<ApplicationStatus> response = new Response<>();

		List<ApplicationStatus> applicationStatus = applicationStatusService.findAllApplicationStatus();

		response.setList(applicationStatus);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	
	
	@ApiOperation(value = "Retrieve all Nature Of Work Type", notes = "Fetch all Nature Of Work Type", response = Response.class, responseContainer = "List", tags = RestApiUrl.NATURE_OF_WORK_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_NATURE_OF_WORK_STATUS_URL)
	public ResponseEntity<Response<?>> retrieveAllNatureOfWork(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationTypeException, NatureOfWorkException {

		System.out.println("retrieveAllNatureOfWorkType !!!!!");

		final String method = RestApiUrl.NATURE_OF_WORK_API + RestApiUrl.GET_ALL_NATURE_OF_WORK_STATUS_URL
				+ " : retrieveAllNatureOfWork()";
	
		Response<NatureOfWork> response = new Response<>();

		List<NatureOfWork> natureOfWork = natureOfWorkService.findAllNatureOfWorkDocumentType();

		response.setList(natureOfWork);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	
	@ApiOperation(value = "Retrieve all District", notes = "Fetch all District", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DISTRICT_URL)
	public ResponseEntity<Response<?>> retrieveAllDistrict(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, ApplicationTypeException, NatureOfWorkException, DistrictException {

		System.out.println("retrieveAllDistrict !!!!!");

		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_DISTRICT_URL
				+ " : retrieveAllDistrict()";
	
		Response<District> response = new Response<>();

		List<District> districtList = districtService.findAllDistrict();

		response.setList(districtList);
		response.setCode(HttpCode.OK);
		response.setMessage("All Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	
	
	@ApiOperation(value = "Retrieve All LoadRequested", notes = "Fetach all LoadRequested", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_LOAD_REQUESTED_URL)
	public ResponseEntity<Response<?>> retrieveAllLoadRequested(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DocumentTypeException,LoadRequestedException {
		Response<LoadRequested> response = new Response<>();
		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_LOAD_REQUESTED_URL + " : retrieveAllLoadRequested()";
	
		List<LoadRequested> loadRequestedResponse = loadRequestedService.findAllLoadRequested();
		
		response.setList(loadRequestedResponse);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");	
		
		
		Iterator itr=loadRequestedResponse.iterator();  
		while(itr.hasNext()){  
		System.out.println(itr.next());  
		} 
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Retrieve All LandArea Unit", notes = "Fetach all Land area unit", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_LAND_AERA_UNIT_URL)
	public ResponseEntity<Response<?>> retrieveAllLandAreaUnit(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DocumentTypeException,LoadRequestedException {
		Response<LandAreaUnit> response = new Response<>();
		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_LAND_AERA_UNIT_URL + " : retrieveAllLandAreaUnit()";
	
		List<LandAreaUnit> landAreaUnit = landAreaUnitServicer.findAllLandAreaUnit();
		
		response.setList(landAreaUnit);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");	

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

    @ApiOperation(value = "Retrieve All LandArea Unit find hectare", notes = "Fetach one Land area unit", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_LAND_AERA_UNIT_HE_URL)
	public ResponseEntity<Response<?>> retrieveAllLandAreaUnitHe(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DocumentTypeException,LoadRequestedException {
		Response<LandAreaUnit> response = new Response<>();
		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_LAND_AERA_UNIT_HE_URL + " : retrieveAllLandAreaUnitHe()";
	
		List<LandAreaUnit> landAreaUnitDB = landAreaUnitServicer.findAllLandAreaUnit();
		
		List<LandAreaUnit> landAreaUnitDbOne = new ArrayList<>();

		
		for (LandAreaUnit landAreaUnit : landAreaUnitDB) {
	        if (landAreaUnit.getLandAreaUnitName().equals("Hectare")) {
	        	landAreaUnitDbOne.add(landAreaUnit);
	        }
	    }
		response.setList(landAreaUnitDbOne);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
    
    
    @ApiOperation(value = "Retrieve All individual or group type", notes = "Fetach individual or group type", response = Response.class, responseContainer = "List", tags = RestApiUrl.MASTER_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_INDIVIDUAL_OR_GROUP_URL)
	public ResponseEntity<Response<?>> retrieveAllIndividualOrGroup(HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DocumentTypeException,LoadRequestedException {
		Response<IndividualOrGroup> response = new Response<>();
		final String method = RestApiUrl.MASTER_API + RestApiUrl.GET_ALL_INDIVIDUAL_OR_GROUP_URL + " : retrieveAllIndividualOrGroup()";
	
		List<IndividualOrGroup> individualOrGroup = individualOrGroupService.findAllIndividualOrGroup();
		
		
		response.setList(individualOrGroup);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
    
    @ApiOperation(value = "Retrieve all distribution center by subdivison", notes = "Pass subdivison id", response = Response.class, responseContainer = "List", tags = RestApiUrl.DC_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping(RestApiUrl.GET_ALL_DC_BY_SUBDIVISION_URL)
	public ResponseEntity<Response<?>> retrieveAllDCBySubdivison(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {

		final String method = RestApiUrl.DC_API + RestApiUrl.GET_ALL_DC_BY_SUBDIVISION_URL
				+ " : retrieveAllDCBySubdivison()";
		
		Response<DistributionCenter> response = new Response<DistributionCenter>();
		List<DistributionCenter> distributionCenters = dcService.findAllDistributionCentersBySubDivision(id);
		response.setList(distributionCenters);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
    
    @GetMapping("/get/{id}")
	public ResponseEntity<Response<?>> retrieveConsumerApplicationDetail(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, DistributionCenterException {
Response response=new Response();
		final String method = RestApiUrl.DC_API + RestApiUrl.GET_ALL_DC_BY_SUBDIVISION_URL
				+ " : retrieveAllDCBySubdivison()";
		
		Optional<ConsumerApplicationDetail> consumerAppDetail = consumerApplictionDetailRepository.findByConsumerApplicationId(id);
		ConsumerApplicationDetail consumerApplicationDetail = consumerAppDetail.get();
		response.setList(Arrays.asList(consumerApplicationDetail));
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		response.setCode(ResponseCode.OK);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
    

    @ApiOperation(value = "find by Application No Contractor  information", response = ContractorForBid.class)
	@GetMapping("/conforbid/{applicationNo}")
	public ResponseEntity<Response> findById(@PathVariable("applicationNo") String applicationNo) {
		ContractorForBid conForBid = contractorForBidRepository.findByApplicationNo(applicationNo);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(conForBid));
		return ResponseEntity.ok().body(response);
	}
    
    @GetMapping("/gets/{consumerApplicationNo}")
	public ContractorForBidWorkStatus get(@PathVariable String consumerApplicationNo) {
		 ContractorForBidWorkStatus consumerApplicationStatu = contractorForBidWorkStatusRepository.findByConsumerApplicationNo(consumerApplicationNo);
		return consumerApplicationStatu;
	}
    

    @ApiOperation(value = "find by work Order Id Vendor Work Order information", response = VendorWorkOrder.class)
	@GetMapping("workorders/{applicationNo}")
	public ResponseEntity<Response> findByApplicationNo(@PathVariable("applicationNo") String applicationNo) {
		VendorWorkOrder workOrder = vendorWorkOrderService.findByApplicationNo(applicationNo);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(workOrder));
		return ResponseEntity.ok().body(response);
	}

    

//    @ApiOperation(value = "Change user login status", notes = "Pass userid & status", response = Response.class, responseContainer = "List", tags = RestApiUrl.UPDATE_USER_STATUS_URL)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_ALL_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
//	@PutMapping(RestApiUrl.UPDATE_USER_STATUS_URL)
//	public ResponseEntity<Response<?>> changeUserStatus(@RequestBody @Valid UserStatusDTO userStatusDTO,@PathVariable long id,
//			BindingResult bindingResult, HttpServletResponse httpServletResponse)
//			throws FormValidationException, InvalidAuthenticationException, UserException {
//
//		final String method = RestApiUrl.USER_LOGIN_API + RestApiUrl.UPDATE_USER_STATUS_URL + " : changeUserStatus()";
//	
//		Response<User> response = null;
//
//		if (bindingResult.hasErrors()) {
//			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
//
//			bindingResult.getFieldErrors().stream().forEach(f -> {
//				//LOG.error(ResponseMessage.FORM_VALIDATION_ERROR + f.getField() + ": " + f.getDefaultMessage());
//				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
//						f.getField() + ":" + f.getDefaultMessage());
//				errorList.add(error);
//			});
//			response = new Response<>();
//			response.setMessage(ResponseMessage.FORM_VALIDATION_ERROR);
//			response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
//			response.setError(errorList);
//			throw new FormValidationException(response);
//		}
//
//		Response<User> userResponse = userService.findById(userStatusDTO.getAdUserId());
//
//		User u = userResponse.getList().get(0);
//		u.setAccountNonExpired(userStatusDTO.getIsAccountNonExpired());
//		u.setAccountNonLocked(userStatusDTO.getIsAccountNonLocked());
//		u.setAdUserId(userStatusDTO.getAdUserId());
//		u.setActive(userStatusDTO.getIsActive());
//		u.setLoginStatus("active");
//		u.setLoginAttemp(0L);
//		u.setLastLoggedInDate(new Date());
//
//		userResponse = userService.save(u);
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(userResponse);
//	}

    @GetMapping("/getAllGovDepartment")
    public ResponseEntity<?> getAllGovDepartment(){
    	
    	List<GovDepartment> findAll = govDepartmentRepository.findAll();
    	Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(findAll));
		return ResponseEntity.ok().body(response);
    }
    
}
