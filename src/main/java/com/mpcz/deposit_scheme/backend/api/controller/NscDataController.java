package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;
import com.mpcz.deposit_scheme.backend.api.domain.NscData;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.dto.NscAndSchemaDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.NatureOfWorkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.NscRepository;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.NscDto;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;
import com.mpcz.deposit_scheme.backend.api.services.UserService;
import com.mpcz.deposit_scheme.backend.api.util.SequenceGeneratorUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "NscDataController", description = "Rest api for NSC DATA.")
@RestController
@RequestMapping(RestApiUrl.NSC_DATA_API)
public class NscDataController {

	Logger LOG = LoggerFactory.getLogger(NscDataController.class);

	@Autowired
	private NscRepository nscRepository;
	
	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DistributionCenterService distributionCenterService;
	
	@Autowired
	private NatureOfWorkRepository natureOfWorkRepository;
	
	@Autowired
	private SchemeTypeService schemeTypeService;
	
	@Autowired
	private ApplicationStatusService applicationStatusService;

	@ApiOperation(value = "Save NSC details", notes = "Get Nsc Detail", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_TYPE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping(RestApiUrl.ADD_URL)
	public ResponseEntity<Response<?>> addNscDetail(@RequestBody NscDto nscdto ,BindingResult bindingResult,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, WorkTypeException {

		System.out.println("addNScDetail !!!!!");

		final String method = RestApiUrl.NSC_DATA_API + RestApiUrl.ADD_URL + " : addNscDetail()";
		LOG.info(method);

		Response<NscData> response = new Response<>();

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
		
		NscData nsc = new NscData();
		nsc.setApplicationNumber(nscdto.getApplicationNumber());
		nsc.setAppliedLoad(nscdto.getAppliedLoad());
		nsc.setCategory(nscdto.getCategory());
		nsc.setColonyType(nscdto.getColonyType());
		nsc.setConsumerAddress(nscdto.getConsumerAddress());
		nsc.setConsumerName(nscdto.getConsumerName());
		nsc.setDateOfApply(nscdto.getDateOfApply());
		nsc.setDateOfPostDeposit(nscdto.getDateOfPostDeposit());
		nsc.setDcCode(nscdto.getDcCode());
		nsc.setDistanceFromLtPole(nscdto.getDistanceFromLtPole());
		nsc.setDtrCode(nscdto.getDtrCode());
		nsc.setDtrName(nscdto.getDtrName());
		nsc.setFeederCode(nscdto.getFeederCode());
		nsc.setFeederName(nscdto.getFeederName());
		nsc.setFloorCount(nscdto.getFloorCount());
		nsc.setFloorNo(nscdto.getFloorNo());
		nsc.setNscId(nscdto.getNscId());
		nsc.setPurpose(nscdto.getPurpose());
		nsc.setReason(nscdto.getReason());
		nsc.setRegistrationFees(nscdto.getRegistrationFees());
		nsc.setRemarkOfDcUser(nscdto.getRemarkOfDcUser());
		nsc.setRemarkOfSurvayor(nscdto.getRemarkOfDcUser());
		nsc.setSecurityDepositCharge(nscdto.getSecurityDepositCharge());
		nsc.setSupplyAffordingCharge(nscdto.getSupplyAffordingCharge());
		nsc.setiSamparkDcCode(nscdto.getiSamparkDcCode());
		nsc.setIsMultiFloorComplex(nscdto.getIsMultiFloorComplex());
		nsc.setLoadUnit(nscdto.getLoadUnit());
		nsc.setMeterCost(nscdto.getMeterCost());
		
		
		nscRepository.save(nsc);
		
		response.setList(Arrays.asList(nsc));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}
	
	@ApiOperation(value = "Find All NSC Details", response = NscData.class)
	@GetMapping("/findall")
	public ResponseEntity<Response> findAll() {
		 List<Map<String, String>> findNscData = nscRepository.findNscData();
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(findNscData));
		return ResponseEntity.ok().body(response);

	}
	
	@ApiOperation(value = "find by User Id  NSC DATA  information", response = NscData.class)
	@GetMapping("/nsc/{userid}")
	public ResponseEntity<Response> findById(@PathVariable("userid") String userId) throws UserException {
		  User findByUserId = userService.findByUserId(userId);
//		 List<User> list = findById.getList();
//		 String dcCode=null;
//		 
//		 if(list.get(0)!=null && list.get(0).getUserDc()!=null && list.get(0).getUserDc().getDcCode()!=null)
		  
		  String dcCode=null;
		  if(findByUserId.getUserDc()!=null && findByUserId.getUserDc().getDcCode()!=null )
		   dcCode = findByUserId.getUserDc().getDcCode();
		
			// dcCode = list.get(0).getUserDc().getDcCode();
		
		List<NscData>nscData = nscRepository.findDcCode(dcCode);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(nscData));
		return ResponseEntity.ok().body(response);
	}
	
	
	@ApiOperation(value = "find by User Id NSC DATA  information", response = NscData.class)
	@GetMapping("/nscdata/{nscid}")
	public ResponseEntity<Response> findByNscId(@PathVariable("nscid") Long nscId) {
		NscData findByNscId = nscRepository.findByNscId(nscId);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(findByNscId));
		return ResponseEntity.ok().body(response);
	}
	
	@ApiOperation(value = "find by Dc Code NSC DATA  information", response = NscData.class)
	@PostMapping("/save/nscdata")
	public Response saveNscDataInConsumerApplication(@RequestBody NscAndSchemaDto dto) throws DistributionCenterException, SchemeTypeException {
		
		NscData findByNscId = nscRepository.findByNscId(dto.getNscId());
		
		ConsumerApplicationDetail cad= new ConsumerApplicationDetail();
		
				
		
		NatureOfWork nature= new NatureOfWork();
		SchemeType schema = schemeTypeService.findBySchemeTypeId(dto.getSchemaTypeId());
		
		NatureOfWork natureOfWork=null;
		if(findByNscId.getReason().contains("Extension") || findByNscId.getReason().contains("Illegal ")) {
			
			Optional<NatureOfWork> natureOfWorkOptional = natureOfWorkRepository.findById(2l);
			 natureOfWork = natureOfWorkOptional.get();
			
		}
		
		
		
		
		DistributionCenter distribution = distributionCenterService.findDistributionCenterByDcCode(findByNscId.getiSamparkDcCode());
		//Division division = distribution.getDcSubdivision().getSubdivisionDivision();
		cad.setConsumerName(findByNscId.getConsumerName());
		cad.setAddress(findByNscId.getConsumerAddress());
		cad.setLoadRequested(findByNscId.getLoadUnit());
		//cad.setNatureOfWorkType(findByNscId.getReason());
		//nature.setNatureOfWorkName(findByNscId.getReason());
		//NatureOfWork save = natureOfWorkRepository.save(nature);
		cad.setNatureOfWorkType(natureOfWork);
		cad.setDistributionCenter(distribution);
		cad.setSchemeType(schema);
		


		String maxApplicationNo = null;
		//generating consumer Application Number

		maxApplicationNo = consumerApplictionDetailRepository
				.findMaxApplicationNo(SequenceGeneratorUtil.getConsumerApplicationNoFormat(schema));

		String ApplicationNo = "";

		if (maxApplicationNo != null && !maxApplicationNo.equals("null")) {
			ApplicationNo = SequenceGeneratorUtil.getApplicationNo(maxApplicationNo, schema);
		} else {
			ApplicationNo = SequenceGeneratorUtil.getApplicationNo("", schema);
		}

		cad.setConsumerApplicationNo(ApplicationNo.toUpperCase().trim());
		
		//End of generating consumer Application Number
		
		ApplicationStatus appStatusDb = applicationStatusService
				.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
		cad.setApplicationStatus(appStatusDb);
		
		
		
		ConsumerApplicationDetail cadresponse = consumerApplictionDetailRepository.save(cad);
		
		if(!Objects.isNull(cadresponse)) {
			
			findByNscId.setActive(false);
			findByNscId.setDeleted(true);
			nscRepository.save(findByNscId);
			
		}
		
		Response response = new Response();
		response.setCode(HttpCode.CREATED);
		response.setMessage("record inseted successfully");
		response.setList(Arrays.asList(cadresponse));
		return response;
	
	}
	
	@ApiOperation(value = "find by Consumer Application NumberNSC DATA  information", response = NscData.class)
	@GetMapping("/nscdetail/{consumerno}")
	public ResponseEntity<Response> findByConsumerNo(@PathVariable("consumerno") String consumerno) {
		NscData findByNscId = nscRepository.findByConsumerNo(consumerno);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(findByNscId));
		return ResponseEntity.ok().body(response);
	}
	
	@ApiOperation(value = "find by Dc Code NSC DATA  information", response = NscData.class)
	@GetMapping("/nscdatadetails/{dccode}")
	public ResponseEntity<Response> findByDcCode(@PathVariable("dccode") String dccode) {
		List<NscData> findByNscId = nscRepository.findDcCode(dccode);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(findByNscId));
		return ResponseEntity.ok().body(response);
	}
}
