package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountDataGet;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateDomain;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.OytMaterialCharges;
import com.mpcz.deposit_scheme.backend.api.domain.OytProjectDetails;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.domain.SanchayPaymentDetails;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.SupervisionOnlyButNotForOyt;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.dto.ApiResponseOYT;
import com.mpcz.deposit_scheme.backend.api.dto.ErpEstimateCalculatedDto;
import com.mpcz.deposit_scheme.backend.api.dto.ErpRevRefundDto;
import com.mpcz.deposit_scheme.backend.api.dto.SanchayPortalDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplicationSurveyRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DemandNoteAllowedStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpEstimateAmountDataGetRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ManualPaymentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.OytMaterialChargesRepository;
import com.mpcz.deposit_scheme.backend.api.repository.OytProjectDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ReSamplingRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SanchayPaymentDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SupervisionOnlyButNotForOytRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UploadRepository;
import com.mpcz.deposit_scheme.backend.api.response.ErpEstimateResponse;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateService;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.services.impl.ErpRevServiceIMP;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;
import com.mpcz.deposit_scheme.feignClient.OytMaterialChargesClient;
import com.mpcz.deposit_scheme.feignClient.OytMaterialChargesClient1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ERPEstimateController", description = "Rest api for ERP Estimate Details.")
@RestController
@RequestMapping(RestApiUrl.URJAS_PORTAL_API)
public class ERPEstimateController {

	Logger logger = LoggerFactory.getLogger(ERPEstimateController.class);

	@Autowired
	ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	ErpEstimateService erpEstimateService;

	@Autowired
	ErpEstimateAmountService erpEstimateAmountService;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	SchemeTypeService schemeTypeService;

	@Autowired
	UploadService uploadService;

	@Autowired
	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	UploadRepository uploadRepository;

	@Autowired
	BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	ConsumerApplicationSurveyRepository consumerApplicationSurveyRepository;

	@Autowired
	MmkyPayAmountRespository mmkyPayAmountRespository;
	@Autowired
	EstimateAmountRepository estimateAmountRepository;

	@Autowired
	ErpRevRepository erpRevRepository;

	@Autowired
	ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	DryUtility dryUtility;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private OytMaterialChargesClient1 oytMaterialChargesClient1;

	@Autowired
	ErpRevServiceIMP erpRevServiceIMP;

	@Autowired
	ErpEstimateAmountDataGetRepository erpEstimateAmountDataGetRepository;
	
	@Autowired
	SupervisionOnlyButNotForOytRepository supervisionOnlyButNotForOytRepository;

	@Autowired
	private DemandNoteAllowedStatusRepository demandNoteAllowedStatusRepository;


	public Long getNextVal() {
		return jdbcTemplate.queryForObject("SELECT deposite_schema.DSP_REF_NO_SEQ.nextval FROM dual", Long.class);
	}

	@ApiOperation(value = "Retrieve all ERP Estimate details", notes = "Fetch all ERP Estimate details", response = Response.class, responseContainer = "List", tags = RestApiUrl.URJAS_PORTAL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })

	@GetMapping("/getErpEstimate/{erpNumber}/{consumerAppId}")
	public ResponseEntity<ErpEstimateResponse> retrieveErpEstimateStatus(@PathVariable String erpNumber,
			@PathVariable Long consumerAppId, HttpServletResponse httpServletResponse, HttpServletRequest request)
			throws Exception {

		System.out.println("value of id is :- " + erpNumber);

		final String method = RestApiUrl.URJAS_PORTAL_API + RestApiUrl.GET_ERP_ESTIMATE_URL
				+ " : retrieveErpEstimate()";

		RestTemplate restTemplate = new RestTemplate();

		ErpEstimateResponse response = new ErpEstimateResponse();

		ErpEstimateDomain erpEstimateDomain = null;

		// String url =
		// "https://rooftop-uat.mpcz.in:8443/budget/budget/XXPA_WF_NOTIFI_DTLS/" +
		// erpNumber;
		String url = "https://rooftop-uat.mpcz.in:8443/budget/budget/XXPA_WF_NOTIFI_DTLS/" + erpNumber;

		// String tempId = String.valueOf(erpNumber);

		URI uri = new URI(url);

		// Map<String, String> map = new HashMap<>();
		//
		// map.put("id", tempId);

		HttpHeaders headers = new HttpHeaders();

		headers.set("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		headers.set("Content-Type", "application/json");

		String token = null;

		token = request.getHeader("Authorization");
		headers.set("Authorization", token);

		// findByEstimateNumber.setErpBudgetWorkFlowNumber(erpNumber);
		ConsumerApplicationDetail consumerapplication = consumerApplicationDetailService
				.findByConsumerApplicationId(consumerAppId);

		consumerapplication.setErpWorkFlowNumber(erpNumber);

		consumerApplicationDetailService.saveConsumerApplication(consumerapplication);

		HttpStatus status = null;
		Integer statusCode = null;

		HttpEntity<ErpEstimateCalculatedDto> entity = new HttpEntity<>(headers);

		ResponseEntity<String> result = null;

		ErpEstimateCalculatedDto erpEstimateData = null;
		// List<ErpEstimateData> erpEstimateList=new ArrayList<ErpEstimateData>();

		List<ErpEstimateDomain> saveAllEstimateList = new ArrayList<ErpEstimateDomain>();

		ErpEstimateCalculatedDto erpEstimateDataResponse = null;

		Response<ErpEstimateCalculatedDto> returnResult = new Response();
		try {

			result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			System.out.println(
					"result------------------------------------------------------------------------------------------------------------------------------------------------->"
							+ result);

			// Type listOfMyClassObject = new TypeToken<ArrayList<ErpEstimateData>>() {
			// }.getType();
			// erpEstimateData = new Gson().fromJson(result.getBody(), listOfMyClassObject);
			JSONObject jsonObject = new JSONObject(result.getBody());
			if (jsonObject.get("responseCode").toString().equals("200")) {
				// ObjectMapper mapper = new ObjectMapper();
				// erpEstimateDataResponse = mapper.readValue(result.getBody(), new
				// TypeReference<ErpEstimateData>() {
				// });

				System.out.println("----------->" + jsonObject.get("responseCode"));

				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					erpEstimateDomain = new ErpEstimateDomain();
					// if(jsonArray.getJSONObject(i).getString("ESTIMATE_NO")!=null)
					// erpEstimateDomain.setErpBudgetWorkFlowNumber(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));
					if (jsonArray.getJSONObject(i).getString("STATUS") != null)
						erpEstimateDomain.setStatus(jsonArray.getJSONObject(i).getString("STATUS"));
					if (jsonArray.getJSONObject(i).getString("ACTION_PERFORMED_DATE") != null)
						erpEstimateDomain
								.setActionPerformedDate(jsonArray.getJSONObject(i).getString("ACTION_PERFORMED_DATE"));
					if (jsonArray.getJSONObject(i).getString("TO_ROLE") != null)
						erpEstimateDomain.setPendingOn(jsonArray.getJSONObject(i).getString("TO_ROLE"));
					if (jsonArray.getJSONObject(i).getString("ACTION_PERFORMED") != null)
						erpEstimateDomain.setActionPerformed(jsonArray.getJSONObject(i).getString("ACTION_PERFORMED"));
					if (jsonArray.getJSONObject(i).getString("NOTIFICATION_DATE") != null)
						erpEstimateDomain
								.setNotificationOnDate(jsonArray.getJSONObject(i).getString("NOTIFICATION_DATE"));

					erpEstimateDomain.setErpBudgetWorkFlowNumber(erpNumber);
					System.out.println(erpEstimateData);
					saveAllEstimateList.add(erpEstimateDomain);
				}
			}

		} catch (Exception e) {

		}
		ErpEstimateDomain findByEstimateNumber = erpEstimateService.findByEstimateNumber(erpNumber);
		if (findByEstimateNumber == null) {

			erpEstimateService.saveAllEstimate(saveAllEstimateList);
		}

		response.setList(saveAllEstimateList);
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getErpEstimateAmount/{erpNumber}/{consumerAppId}/{savedataId}")
	public ResponseEntity<ErpEstimateResponse> retrieveErpEstimateAmount(@PathVariable String erpNumber,
			@PathVariable Long consumerAppId, @PathVariable Long savedataId, HttpServletResponse httpServletResponse,
			HttpServletRequest request) throws Exception {

		System.out.println("value of id is :- " + erpNumber);

		final String method = RestApiUrl.URJAS_PORTAL_API + RestApiUrl.GET_ERP_ESTIMATE_AMOUNT_URL
				+ " : retrieveErpEstimate()";

		RestTemplate restTemplate = new RestTemplate();
		ErpEstimateResponse response = new ErpEstimateResponse();

//		String url = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNumber;

		String url = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNumber;
//		new wala code chalana hai
//		String	url="https://rooftop-uat.mpcz.in:8443/newerp/XXPA_PROJECTS_DSP_V/"+erpNumber;

		URI uri = new URI(url);

		HttpHeaders headers = new HttpHeaders();

		headers.set("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		headers.set("Content-Type", "application/json");

		String token = null;

		token = request.getHeader("Authorization");
		headers.set("Authorization", token);

		ConsumerApplicationDetail consumerapplication = consumerApplicationDetailService
				.findByConsumerApplicationId(consumerAppId);

		if (savedataId == 2l) {
			consumerapplication.setErpWorkFlowNumber(erpNumber);

			consumerApplicationDetailService.saveConsumerApplication(consumerapplication);
		}
		HttpStatus status = null;
		Integer statusCode = null;

		HttpEntity<ErpEstimateAmountData> entity = new HttpEntity<>(headers);

		ResponseEntity<String> result = null;
//		ResponseEntity<String> result1 = null;
		ErpEstimateAmountData erpEstimateAmountData = null;
		List<ErpEstimateAmountData> erpEstimateList = new ArrayList<ErpEstimateAmountData>();

		List<ErpEstimateAmountData> saveAllEstimateAmountList = new ArrayList<ErpEstimateAmountData>();

		ErpEstimateAmountData erpEstimateDataResponse = null;

		SchemeType schemeObject = schemeTypeService
				.findBySchemeTypeId(consumerapplication.getSchemeType().getSchemeTypeId());

		BigDecimal sgst = null;
		BigDecimal cgst = null;

		String schemaCode = null;

		Response<ErpEstimateAmountData> returnResult = new Response();
		ErpEstimateAmountData estimateAmountObject = null;
		ConsumerApplicationDetail consumerapplicationUpdatedDB = null;
		try {
//			code start 3 April 2025 with updated check for erp no. ki same erp no. kisi bhi application se tagged na ho 
//			ResponseEntity<Response> checkErpExistOrNot = dryUtility.checkErpExistOrNot(erpNumber);
//			if (checkErpExistOrNot != null && checkErpExistOrNot.getBody() != null) {
//
//				Response responseBody = checkErpExistOrNot.getBody(); // Extract response body
//
//				if ("This ERP Number Is Already Associated With Another Application Number."
//						.equals(responseBody.getMessage())) {
//					ErpEstimateResponse erpResponse = new ErpEstimateResponse();
//					erpResponse.setCode(responseBody.getCode());
//					erpResponse.setMessage(responseBody.getMessage());
//					erpResponse.setList(responseBody.getList()); // Assuming both have list field
//
//					return ResponseEntity.status(checkErpExistOrNot.getStatusCode()) // Preserve original status code
//							.headers(checkErpExistOrNot.getHeaders()) // Preserve headers
//							.body(erpResponse);
//				}
//			}
//			code changed 24-09-2025
			ResponseEntity<Response> checkErpExistOrNot = dryUtility.checkErpExistOrNot2(erpNumber,
					consumerapplication.getConsumerApplicationNo());
			if (checkErpExistOrNot != null && checkErpExistOrNot.getBody() != null) {
				Response responseBody = checkErpExistOrNot.getBody();

				// Agar ERP already system me kahin bhi present hai
				if (HttpCode.NOT_ACCEPTABLE.equals(responseBody.getCode())) {
					ErpEstimateResponse erpResponse = new ErpEstimateResponse();
					erpResponse.setCode(responseBody.getCode());
					erpResponse.setMessage(responseBody.getMessage());
					erpResponse.setList(responseBody.getList()); // Assuming both have list field

					return ResponseEntity.status(checkErpExistOrNot.getStatusCode()) // Preserve original status code
							.headers(checkErpExistOrNot.getHeaders()) // Preserve headers
							.body(erpResponse);
				}
			}

//			code end

			result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

			JSONObject jsonObject = new JSONObject(result.getBody());

			System.out.println(
					"jsonObject.get(\"statusCode\")---->" + jsonObject.get("statusCode").toString().equals("200"));
			consumerapplicationUpdatedDB = consumerApplicationDetailService.findByConsumerApplicationId(consumerAppId);
			if (jsonObject.get("statusCode").toString().equals("400")) {
				throw new Exception("Estimate not approved");
			}
			if (jsonObject.get("statusCode").toString().equals("200")) {

				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {

					if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
							&& !consumerapplication.getSchemeType().getSchemeTypeId().equals(1L))

							|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
									&& !consumerapplication.getSchemeType().getSchemeTypeId().equals(2L))
							|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("KMY")
									&& !consumerapplication.getNatureOfWorkType().getNatureOfWorkName().equals("MKMY"))
							|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("OYT")
									&& !consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5L))
							|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("RRTD")
									&& (consumerapplication.getSchemeType().getSchemeTypeId().equals(1L)
											|| consumerapplication.getSchemeType().getSchemeTypeId().equals(2L)))
							|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
									&& consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 8l)

							|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
									&& consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 5l)

					) {
						response.setCode(HttpCode.NOT_ACCEPTABLE);
						response.setMessage("Scheme code not matched");
						// throw new Exception("please provide valid erp number");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}

					erpEstimateAmountData = new ErpEstimateAmountData();
					erpEstimateAmountData.setConsumerApplicationNo(consumerapplication.getConsumerApplicationNo());
					if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null)
						erpEstimateAmountData.setErpNo(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
					// if(jsonArray.getJSONObject(i).getString("PROJECT_NAME")!=null)
					// erpEstimateAmountData.setEstimateSanctionNo(jsonArray.getJSONObject(i).getString("PROJECT_NAME"));
					if (jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME") != null)
						erpEstimateAmountData.setLocation(jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME"));
					if (jsonArray.getJSONObject(i).getString("STATUS") != null)
						erpEstimateAmountData.setEstimateStatus(jsonArray.getJSONObject(i).getString("STATUS"));
					if (jsonArray.getJSONObject(i).getString("SUPERVISION_COST") != null) {
						String string = jsonArray.getJSONObject(i).getString("SUPERVISION_COST");

						BigDecimal big = new BigDecimal(string);
						if (big.compareTo(new BigDecimal(0.0)) == 0) {
							response.setCode(HttpCode.NOT_ACCEPTABLE);
							response.setMessage("Estimate is wrongly created in ERP");
							return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
						}
						cgst = round111(big.multiply(new BigDecimal(.09)), 2);
						sgst = round111(big.multiply(new BigDecimal(.09)), 2);
						erpEstimateAmountData.setCgst(cgst);
						erpEstimateAmountData.setSgst(sgst);
						String d = jsonArray.getJSONObject(i).getString("SUPERVISION_COST");
						BigDecimal cost = new BigDecimal(d);
						erpEstimateAmountData.setSupervisionAmount(cost);
					}

					if (jsonArray.getJSONObject(i).getString("ESTIMATED_COST") != null)
						erpEstimateAmountData.setEstimateAmount(
								new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST")));
					// if(jsonArray.getJSONObject(i).getString("SUPERVISION_COST")!=null)
					// erpEstimateAmountData.setEstimateAmount(jsonArray.getJSONObject(i).getString("SUPERVISION_COST"));
					if (jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME") != null)
						erpEstimateAmountData.setApprovedBy(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME"));

					if (jsonArray.getJSONObject(i).getString("ESTIMATE_NO") != null)
						erpEstimateAmountData
								.setEstimateSanctionNo(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));

					if (jsonArray.getJSONObject(i).getString("LONG_NAME") != null)
						erpEstimateAmountData.setEstimateName(jsonArray.getJSONObject(i).getString("LONG_NAME"));

					if (jsonArray.getJSONObject(i).getString("ESTIMATE_DATE") != null)
						erpEstimateAmountData.setEstimateDate(jsonArray.getJSONObject(i).getString("ESTIMATE_DATE"));

					if (jsonArray.getJSONObject(i).getString("DESIG") != null)
						erpEstimateAmountData.setDesignation(jsonArray.getJSONObject(i).getString("DESIG"));

					if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
							&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {
						schemaCode = jsonArray.getJSONObject(i).getString("SCHEMECODE");
						String projectType = jsonArray.getJSONObject(i).getString("PROJECT_TYPE");
						String schema = schemaCode;
						erpEstimateAmountData.setSchema(schema);
						erpEstimateAmountData.setSchemeCode(schemaCode);
					}

					if (jsonArray.getJSONObject(i).getString("MINUS_COST") != null)
						erpEstimateAmountData
								.setMinusCost(new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST")));

//					y code add kiya gaya hai ankit sir k kehne pr ki ab disom user return amount nhi 
//					dalega ab return amount erp ki api se aane wale minus cost se liya jayega autmatically
//					sirf supervision k case me minus cost k amount ko ConsumerApplication Detail k Je Return amount me put krenge 
//					deposit case me je return me 0 put krenge kyuki already deposit k case me minus cost se calculation hoti hai
//					code start 21-Feb-2025
					if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
							&& consumerapplication.getSchemeType().getSchemeTypeId().equals(1L))
							&& jsonArray.getJSONObject(i).getString("MINUS_COST") != null) {
// agar goverment hai to return amount 0 liya jaayega code start 6-march-2025
						consumerapplicationUpdatedDB.setJeReturnAmount(
								consumerapplication.getIsAvedakGovernmentErp().equals("Yes") ? new BigDecimal(0)
										: new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST")).abs());
						// code end 6-march-2025
					} else {
						consumerapplicationUpdatedDB.setJeReturnAmount(new BigDecimal(0));
					}

//					code end

					erpEstimateAmountData.setErpBudgetWorkFlowNumber(erpNumber);

					System.out.println(" erpEstimateAmountData : " + erpEstimateAmountData);
					saveAllEstimateAmountList.add(erpEstimateAmountData);
				}

				
				
			} else {

//			String	uri1="https://rooftop-uat.mpcz.in:8443/newerp/XXPA_PROJECTS_DSP_V/"+erpNumber;
//				String uri1 = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNumber;

				String uri1 = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNumber;

				result = restTemplate.exchange(uri1, HttpMethod.GET, entity, String.class);

				JSONObject jsonObject1 = new JSONObject(result.getBody());
				if (!jsonObject1.get("statusCode").toString().equals("200")) {
					response.setCode(HttpCode.NOT_ACCEPTABLE);
					response.setMessage("Please enter a valid ERP number");

					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}

				if (jsonObject1.get("statusCode").toString().equals("200")) {

					JSONArray jsonArray = jsonObject1.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {

						if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
								&& !consumerapplication.getSchemeType().getSchemeTypeId().equals(1L))

								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
										&& !consumerapplication.getSchemeType().getSchemeTypeId().equals(2L))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("KMY")
										&& !consumerapplication.getNatureOfWorkType().getNatureOfWorkName()
												.equals("MKMY"))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("OYT")
										&& !consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId()
												.equals(5L))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("RRTD")
										&& (consumerapplication.getSchemeType().getSchemeTypeId().equals(1L)
												|| consumerapplication.getSchemeType().getSchemeTypeId().equals(2L)))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
										&& consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 8l)

								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
										&& consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 5l)) {
							response.setCode(HttpCode.NOT_ACCEPTABLE);
							response.setMessage("Scheme code not matched");
							// throw new Exception("please provide valid erp number");
							return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
						}
						erpEstimateAmountData = new ErpEstimateAmountData();
						erpEstimateAmountData.setConsumerApplicationNo(consumerapplication.getConsumerApplicationNo());
						if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null)
							erpEstimateAmountData.setErpNo(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
						if (jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME") != null)
							erpEstimateAmountData
									.setLocation(jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME"));
						if (jsonArray.getJSONObject(i).getString("STATUS") != null)
							erpEstimateAmountData.setEstimateStatus(jsonArray.getJSONObject(i).getString("STATUS"));
						if (jsonArray.getJSONObject(i).getString("SUPERVISION_COST") != null) {
							String string = jsonArray.getJSONObject(i).getString("SUPERVISION_COST");

							BigDecimal big = new BigDecimal(string);
							if (big.compareTo(new BigDecimal(0.0)) == 0) {
								response.setCode(HttpCode.NOT_ACCEPTABLE);
								response.setMessage("Estimate is wrongly created in ERP");
								return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
							}
							cgst = round111(big.multiply(new BigDecimal(.09)), 2);
							sgst = round111(big.multiply(new BigDecimal(.09)), 2);
							erpEstimateAmountData.setCgst(cgst);
							erpEstimateAmountData.setSgst(sgst);

							String d = jsonArray.getJSONObject(i).getString("SUPERVISION_COST");
							BigDecimal cost = new BigDecimal(d);
							erpEstimateAmountData.setSupervisionAmount(cost);
						}
						if (jsonArray.getJSONObject(i).getString("ESTIMATED_COST") != null)
							erpEstimateAmountData.setEstimateAmount(
									new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST")));
						if (jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME") != null)
							erpEstimateAmountData
									.setApprovedBy(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME"));

						if (jsonArray.getJSONObject(i).getString("ESTIMATE_NO") != null)
							erpEstimateAmountData
									.setEstimateSanctionNo(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));

						if (jsonArray.getJSONObject(i).getString("LONG_NAME") != null)
							erpEstimateAmountData.setEstimateName(jsonArray.getJSONObject(i).getString("LONG_NAME"));

						if (jsonArray.getJSONObject(i).getString("ESTIMATE_DATE") != null)
							erpEstimateAmountData
									.setEstimateDate(jsonArray.getJSONObject(i).getString("ESTIMATE_DATE"));

						if (jsonArray.getJSONObject(i).getString("DESIG") != null)
							erpEstimateAmountData.setDesignation(jsonArray.getJSONObject(i).getString("DESIG"));

						if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
								&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {
							schemaCode = jsonArray.getJSONObject(i).getString("SCHEMECODE");
							String projectType = jsonArray.getJSONObject(i).getString("PROJECT_TYPE");
							String schema = schemaCode;
							erpEstimateAmountData.setSchema(schema);
							erpEstimateAmountData.setSchemeCode(schemaCode);
						}
						if (jsonArray.getJSONObject(i).getString("MINUS_COST") != null)
							erpEstimateAmountData
									.setMinusCost(new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST")));

//						y code add kiya gaya hai ankit sir k kehne pr ki ab disom user return amount nhi 
//						dalega ab return amount erp ki api se aane wale minus cost se liya jayega autmatically
//						sirf supervision k case me minus cost k amount ko ConsumerApplication Detail k Je Return amount me put krenge 
//						deposit case me je return me 0 put krenge kyuki already deposit k case me minus cost se calculation hoti hai
//						code start 21-Feb-2025
						if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
								&& consumerapplication.getSchemeType().getSchemeTypeId().equals(1L))
								&& jsonArray.getJSONObject(i).getString("MINUS_COST") != null) {
							// agar goverment hai to return amount 0 liya jaayega code start 6-march-2025
							consumerapplicationUpdatedDB.setJeReturnAmount(
									consumerapplication.getIsAvedakGovernmentErp().equals("Yes") ? new BigDecimal(0)
											: new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST")).abs());
							// code end 6-march-2025
						} else {
							consumerapplicationUpdatedDB.setJeReturnAmount(new BigDecimal(0));
						}
// code end
						erpEstimateAmountData.setErpBudgetWorkFlowNumber(erpNumber);

						System.out.println(erpEstimateAmountData);
						saveAllEstimateAmountList.add(erpEstimateAmountData);
					}

				}

			}

		} catch (Exception e) {
			System.out.println("exception block------------------>" + e);
			response.setCode("500");
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		System.out.println("----------------------------------------------------------------------------------"
				+ estimateAmountObject);

//		Api me changes kiye h jisse y api status 7 or 9 pr call hogi  API changes start
		if (savedataId.equals(2L)) {
			if (erpEstimateAmountData.getSupervisionAmount().compareTo(BigDecimal.ZERO) < 0) {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage(" Supervision Amount Wrong. Please Contact IT Cell. ");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				consumerApplicationDetailService.saveConsumerApplication(consumerapplicationUpdatedDB);
				erpEstimateAmountService.saveAllEstimateAmount(saveAllEstimateAmountList);
			}
		}

		if ((schemeObject.getSchemeTypeName().equals("Supervision") && schemaCode.contains("SCCW"))
				|| (schemeObject.getSchemeTypeName().equals("Deposit") && schemaCode.equalsIgnoreCase("DEPOSITE"))) {
			response.setList(saveAllEstimateAmountList);
			response.setCode(HttpCode.OK);
			response.setMessage("Record Retrieve Successfully");
		} else if ((schemeObject.getSchemeTypeName().equals("Supervision") && !schemaCode.contains("SCCW"))
				&& (schemeObject.getSchemeTypeName().equals("Supervision") && schemaCode.contains("OYT"))) {
			response.setList(saveAllEstimateAmountList);
			response.setCode(HttpCode.OK);
			response.setMessage("Record Retrieve Successfully");

		} else if ((schemeObject.getSchemeTypeName().equals("Deposit") && !schemaCode.contains("KMY"))) {
			response.setList(saveAllEstimateAmountList);
			response.setCode(HttpCode.OK);
			response.setMessage("Record Retrieve Successfully");

		} else {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Please enter a valid ERP number");
			// throw new Exception("please provide valid erp number");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Update Estimate Status", notes = "Pass consumerAppId", response = Response.class, responseContainer = "List", tags = RestApiUrl.URJAS_PORTAL_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@PutMapping("update/{consumerAppId}/{scheduleSurveyDate}/{scheduleSurveyTime}/{surveyorName}/{surveyorMobile}")
	public ResponseEntity<Response<?>> updateEstimateStatus(@PathVariable long consumerAppId,
			@PathVariable String scheduleSurveyDate, @PathVariable String scheduleSurveyTime,
			@PathVariable String surveyorName, @PathVariable String surveyorMobile,
			@RequestPart("docEstimate") Optional<MultipartFile> docEstimateOptional,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			DistributionCenterException, ConsumerApplicationDetailException, SchemeTypeException {

		final String method = RestApiUrl.URJAS_PORTAL_API + RestApiUrl.UPDATE_ESTIMATE_STATUS_URL
				+ " : updateEstimateStatus()";

		ConsumerApplicationDetail consumerapplication = consumerApplicationDetailService
				.findByConsumerApplicationId(consumerAppId);
//		ApplicationDocument applicationDocument = new ApplicationDocument();

		Optional<ConsumerApplicationSurvey> consumerApplicationSurvey = consumerApplicationSurveyRepository
				.findByConsumersApplicationDetailConsumerApplicationId(consumerAppId);
		ConsumerApplicationSurvey ConsumerApplicationSurvey1 = consumerApplicationSurvey.get();

		ConsumerApplicationSurvey1.setSurveyDate(scheduleSurveyDate);

		ConsumerApplicationSurvey1.setScheduleSurveyDate(scheduleSurveyDate);

		ConsumerApplicationSurvey1.setScheduleSurveyTime(scheduleSurveyTime);
		ConsumerApplicationSurvey1.setSurveyStatus("Done");

		ConsumerApplicationSurvey1.setSurveyorName(surveyorName);
		ConsumerApplicationSurvey1.setSurveyorMobile(surveyorMobile);

		Response<ConsumerApplicationDetail> response = new Response<>();

		try {

			MultipartFile docEstimate = null;

			if (docEstimateOptional.isPresent()) {
				docEstimate = docEstimateOptional.get();
			}

			Upload docEstimateUpload = null;

			if (docEstimate != null) {
				docEstimateUpload = uploadService.uploadFile(docEstimate, "ESTIMATEPDF");
				if (docEstimateUpload == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Document Estimate not uploaded.");
					throw new ConsumerApplicationDetailException(response);
				}
				// charitra start code
				Upload upload = uploadService.findUpload(docEstimateUpload.getUploadId());
				upload.setConsuemrApplicatonID(consumerAppId);
				Upload save = uploadService.save(upload);
				// charitra end code

				ApplicationDocument applicationDocDB = applicationDocumentRepository
						.findByConsumerApplicationDetailId(consumerAppId);
				if (applicationDocDB == null) {
					ApplicationDocument applicationDocument = new ApplicationDocument();
					applicationDocument.setConsumerApplicationDetail(consumerapplication);
					applicationDocument.setDocEstimate(docEstimateUpload);
					applicationDocumentRepository.save(applicationDocument);
				} else {
					applicationDocDB.setDocEstimate(docEstimateUpload);
					applicationDocumentRepository.save(applicationDocDB);
				}
//				applicationDocument.setDocRegistry(docEstimateUpload);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		SchemeType schemeObject = schemeTypeService
				.findBySchemeTypeId(consumerapplication.getSchemeType().getSchemeTypeId());

		ApplicationStatus applicationStatus = null;

		if (schemeObject.getSchemeTypeName().equalsIgnoreCase("Departmental (MPMKVVCL)")) {
			applicationStatus = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());

		} else {
// 04-12-2025 ADDED consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(12l) CHECK MONIKA RAJPOOT
			applicationStatus = applicationStatusService
					.findById(consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(12l)
							? ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_GM.getId()
							: ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());

		}

		consumerapplication.setApplicationStatus(applicationStatus);

		Optional<ErpEstimateAmountData> findById = estimateAmountRepository
				.findById(consumerapplication.getErpWorkFlowNumber());
		MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
				.findByConsumerApplicationNumber(consumerapplication.getConsumerApplicationNo());

		consumerApplicationDetailService.saveConsumerApplication(consumerapplication);

		response.setList(Arrays.asList(consumerapplication));
		response.setCode(HttpCode.OK);
		response.setMessage("Updated successfully !!!!!!!");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@ApiOperation(value = "Retrieve Calculated Amount", notes = "Pass userId,consumerAppNo", response = Response.class, responseContainer = "List", tags = RestApiUrl.DOCUMENT_TYPE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.INTERNAL_SERVER_ERROR_CODE, message = ResponseMessage.INTERNAL_SERVER_ERROR) })
	@GetMapping("/erpEstimateCalculations/{consumerAppId}")
	public ResponseEntity<Response<?>> calculateEstimateAmountRates(@PathVariable long consumerAppId,
			HttpServletResponse httpServletResponse) throws Exception {

		final String method = RestApiUrl.URJAS_PORTAL_API + "calculateEstimateAmountRates" + " : retrieveDocument()";

		Response<ErpEstimateCalculatedDto> response;
		try {

			ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
					.findByConsumerApplicationId(consumerAppId)
					.orElseThrow(() -> new DataNotFoundException(new Response<String>(ResponseCode.DATA_NOT_FOUND,
							ResponseMessage.CONSUMER_APPLICATION_NOT_FOUND)));
			Set<Long> allowedStatuses = new HashSet<>(demandNoteAllowedStatusRepository.findActiveAllowedStatusIds());
			Long currentStatus = consumerApplicationDetail.getApplicationStatus().getApplicationStatusId();
			System.err.println("allowedStatuses  : " + allowedStatuses);
			if (!allowedStatuses.contains(currentStatus)) {
				response = new Response<>();

				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("you can not fetch demand note calculation of this application");
				return ResponseEntity.ok(response);
			}

			if (consumerApplicationDetail.getColonyIllegalSelectionType() != null
					&& consumerApplicationDetail.getColonyIllegalSelectionType().equals("2")) {
				if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(4l)
						&& consumerApplicationDetail.getIndividualOrGroup().getIndividualOrGroupId().equals(1l)
						&& Double.parseDouble(consumerApplicationDetail.getJeLoad()) > 400) {
					response = new Response<>();

					response.setCode(HttpCode.NOT_ACCEPTABLE);
					response.setMessage(
							"In the case of illigal colony application, load greater than 400 KW is not Acceptable");
					return ResponseEntity.ok(response);
				}
			}

			BillDeskPaymentResponse demandDataFromBilldesk = billPaymentResponseeeeeeeRepository
					.getDemandDataFromBilldesk(consumerApplicationDetail.getConsumerApplicationNo());
			PoseMachinePostData byApplicationNumber = poseMachinePostDataRepository
					.findByApplicationNumber(consumerApplicationDetail.getConsumerApplicationNo());

			if (demandDataFromBilldesk == null && byApplicationNumber == null) {

				ErpEstimateCalculatedDto calculateEstimateAmount = erpEstimateAmountService
						.calculateEstimateAmount(consumerAppId);
				if (calculateEstimateAmount == null) {
					response = new Response<>();

					response.setCode("404");
					response.setMessage("Estimate Scheme Code Not Mathced !!!!");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}

				response = new Response<>();
				response.setList(Arrays.asList(calculateEstimateAmount));
				response.setCode(HttpCode.OK);
				response.setMessage("Retrieve successfully !!!!!!!");
			} else {
				response = new Response<>();

				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("Payment Already done for this application now demand not can not calculate");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);

			response = new Response<>();
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(e.getMessage());
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/get_erp_estimate_doc/{applicationNo}")
	public Response getErpEstimateDocumentByApplicationNo(@PathVariable("applicationNo") String applicationNo) {

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(applicationNo);
		if (consumerApplicationDetail == null) {
			return new Response<>("404", "Consumer Application not found");
		}
		List<Upload> upload = uploadRepository
				.findByConsuemrApplicatonID(consumerApplicationDetail.getConsumerApplicationId());
		String etimateFilePath = null;
		for (Upload u : upload) {
			if (u.getDocumentType().getDocumentTypeId() == 41L) {
				etimateFilePath = u.getDocumentPath();
				break; // Assuming you only need one estimate file path, so we exit the loop.
			}
		}
		if (etimateFilePath != null) {
			return new Response<>("200", etimateFilePath);
		} else {
			return new Response<>("404", "Estimate file not found");
		}
	}

	@GetMapping("/downloadpdf")
	public ResponseEntity<byte[]> uploadFileForGallery(@RequestParam("path") String filePath) {
		byte[] readAllBytes = null;
		HttpHeaders headers = null;
		try {
			String file = filePath.replace("\\", "/");

			System.out.println(file);
			double random1 = Math.random();
			String random = String.valueOf(random1);
			System.out.println(random);
			random = random.substring(random.indexOf(".") + 1);
			Path path = Paths.get(file);
			readAllBytes = Files.readAllBytes(path);
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.set("Content-Disposition", String.format("attachment; filename=DSP_document_2022-23" + ".pdf"));
		} catch (Exception e) {

			return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

		}

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(readAllBytes, headers, HttpStatus.OK);

		return responseEntity;
	}

//	return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	@GetMapping("/getamount/{consumerApplicationid}")
	public Response<ErpEstimateCalculatedDto> getAmount(
			@PathVariable("consumerApplicationid") long consumerApplicationid)
			throws ConsumerApplicationDetailException, SchemeTypeException, Exception {

		Response<ErpEstimateCalculatedDto> response = new Response<ErpEstimateCalculatedDto>();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationId(consumerApplicationid)
				.orElseThrow(() -> new DataNotFoundException(new Response<String>(ResponseCode.DATA_NOT_FOUND,
						ResponseMessage.CONSUMER_APPLICATION_NOT_FOUND)));

		if (consumerApplicationDetail.getColonyIllegalSelectionType() != null
				&& consumerApplicationDetail.getColonyIllegalSelectionType().equals("2")) {
			if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(4l)
					&& consumerApplicationDetail.getIndividualOrGroup().getIndividualOrGroupId().equals(1l)
					&& Double.parseDouble(consumerApplicationDetail.getJeLoad()) > 400) {

				throw new DataNotFoundException(new Response<String>(ResponseCode.NOT_ACCEPTABLE,
						"In the case of illigal colony application, load greater than 400 KW is not Acceptable"));
			}
		}
		ErpEstimateCalculatedDto findByEstimateNumber = erpEstimateAmountService
				.calculateEstimateAmount1(consumerApplicationid);
		if (findByEstimateNumber == null) {
			response.setCode("404");
			response.setMessage("Erp Scheme code not matched");

			return response;

		}
		response.setCode("200");
		response.setMessage("data successfully return");
		response.setList(Arrays.asList(findByEstimateNumber));

		return response;
	}

//	posemachin
	@GetMapping("/getconsumerApplicatioNo/{consumerApplicatioNo}")
	public ResponseEntity<Response<?>> getconsumerApplicatioNo(
			@PathVariable("consumerApplicatioNo") String consumerApplicatioNo)
			throws ConsumerApplicationDetailException, SchemeTypeException, Exception {
		Response<SanchayPortalDto> response = new Response<SanchayPortalDto>();

		ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo = consumerApplicationDetailService
				.findConsumerApplicationDetailByApplicationNo(consumerApplicatioNo);
		if (findConsumerApplicationDetailByApplicationNo == null) {
			response.setCode("404");
			response.setMessage("Application not found in database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		SanchayPortalDto sanchayPortalDto = new SanchayPortalDto();
		sanchayPortalDto.setTransactionId(refNoGenerator());

		if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId().equals(5l)) {
			sanchayPortalDto.setApplicationNumber(consumerApplicatioNo);
			sanchayPortalDto.setCgst(new BigDecimal(90));
			sanchayPortalDto.setSgst(new BigDecimal(90));
			sanchayPortalDto.setRegistrationFees(new BigDecimal(1000));
			sanchayPortalDto.setPayableAmount(new BigDecimal(1180));
			sanchayPortalDto.setPaymentType("Registration_Fees");
			sanchayPortalDto
					.setNgbDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getNgbDcCd());
			sanchayPortalDto.setConsumerName(findConsumerApplicationDetailByApplicationNo.getConsumerName());
			sanchayPortalDto
					.setMobileNumber(findConsumerApplicationDetailByApplicationNo.getConsumers().getConsumerMobileNo());

			response.setCode("200");
			response.setMessage("application is Pending for payment");
			response.setList(Arrays.asList(sanchayPortalDto));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId().equals(12l)) {

			if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId() > 12) {
				response.setCode("001");
				response.setMessage("Payment already done");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId() < 12) {
				response.setCode("");
				response.setMessage("application is not Pending for payment");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {

//				MmkyPayAmount amount = mmkyPayAmountRespository.findByConsumerApplicationNumber(consumerApplicatioNo);
//
//				sanchayPortalDto.setGovMafBill(amount.getGovMafBill());
//				sanchayPortalDto.setMpmkMafBill(amount.getMpmkMafBill());
//				sanchayPortalDto.setMkmyTotalAmount(amount.getTotalAmount());
//				sanchayPortalDto.setPayableAmount(amount.getPayableAmount());
//				sanchayPortalDto.setAvedanShulk(amount.getAvedanShulk());
//				sanchayPortalDto.setMkmySecurityDeposit(amount.getSecurityDeposit());
//				sanchayPortalDto.setAvedanShulkFiveRupee(amount.getAvedanShulkFiveRupee());
//				sanchayPortalDto.setApplicationType("MMKY");
//				sanchayPortalDto.setDsSvMkPayAmount(amount.getPayableAmount());

				response.setCode("404");
				response.setMessage("Scheme Closed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				ErpEstimateCalculatedDto findByEstimateNumber = erpEstimateAmountService.calculateEstimateAmount(
						findConsumerApplicationDetailByApplicationNo.getConsumerApplicationId());
				if (findByEstimateNumber == null) {
					response.setCode("404");
					response.setMessage("Erp Scheme code not matched");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
//				added ssp payment head in this 18-11-2025
				sanchayPortalDto.setSspRegistrationCharge(
						findByEstimateNumber.getSspRegistrationCharge() == null ? BigDecimal.ZERO
								: findByEstimateNumber.getSspRegistrationCharge());
				sanchayPortalDto.setSspMeterCost(findByEstimateNumber.getSspMeterCost() == null ? BigDecimal.ZERO
						: findByEstimateNumber.getSspMeterCost());

				sanchayPortalDto
						.setSecurityDepositAmnt(findByEstimateNumber.getSecurityDeposit() == null ? BigDecimal.ZERO
								: findByEstimateNumber.getSecurityDeposit());
//				end
//				added this key for NOW=12 04-12-2025
				if (findByEstimateNumber.getRegistrationCharges() != null) {
					sanchayPortalDto.setRegistrationFees(findByEstimateNumber.getRegistrationCharges());
				}
//				end by Monika Rajpoot
				sanchayPortalDto.setCgst(findByEstimateNumber.getCgst());
				sanchayPortalDto.setSgst(findByEstimateNumber.getSgst());

				sanchayPortalDto.setSuperVisionAmount(findByEstimateNumber.getSuperVisionAmount());
				sanchayPortalDto.setSupplyAffordingCharge(findByEstimateNumber.getKvaLoadAmount());
				sanchayPortalDto.setColonyOrSlum(findByEstimateNumber.getColonyOrSlum());
				if (findByEstimateNumber.getJeReturnAmount() != null) {
					sanchayPortalDto.setJeReturnAmount(findByEstimateNumber.getJeReturnAmount());
				}
				sanchayPortalDto.setTotalBalanceSupervisionAmount(findByEstimateNumber.getTotalamountOfSupervision());

				sanchayPortalDto.setDepositAmount(findByEstimateNumber.getDepositAmount());
				sanchayPortalDto.setTotalBalanceDepositAmount(findByEstimateNumber.getTotaldepositAmount());

				sanchayPortalDto.setApplicationType(
						findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeName());
				if (findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId().equals(1L)) {
					sanchayPortalDto.setDsSvMkPayAmount(findByEstimateNumber.getTotalamountOfSupervision());
				} else {
					sanchayPortalDto.setDsSvMkPayAmount(findByEstimateNumber.getTotaldepositAmount());
				}

			}

			sanchayPortalDto
					.setNgbDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getNgbDcCd());
			sanchayPortalDto.setApplicationNumber(consumerApplicatioNo);
			sanchayPortalDto.setAddress1(findConsumerApplicationDetailByApplicationNo.getAddress());
			sanchayPortalDto.setConsumerName(findConsumerApplicationDetailByApplicationNo.getConsumerName());
			sanchayPortalDto.setDc(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcName());
			sanchayPortalDto
					.setDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcCode());
			sanchayPortalDto.setCity(findConsumerApplicationDetailByApplicationNo.getDistrict().getDistrictName());

			sanchayPortalDto
					.setMobileNumber(findConsumerApplicationDetailByApplicationNo.getConsumers().getConsumerMobileNo());
			// code start sending gst no. if exist to sanchay portal (10-june-2024) -Monika
			// Rajpoot
			if (findConsumerApplicationDetailByApplicationNo.getGstNumber() != null) {
				sanchayPortalDto.setGstNumber(findConsumerApplicationDetailByApplicationNo.getGstNumber());
			}
			sanchayPortalDto.setPaymentType("Demand_fees");

		} else {
//nature of work 30 ke liye
			if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
				response.setCode("404");
				response.setMessage("Scheme Closed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

//				ErpRev findByConsAppNo = erpRevRepository.findByConsAppNo(consumerApplicatioNo);
//
//				sanchayPortalDto.setGovMafBill(findByConsAppNo.getRemGovMafAmt());
//				sanchayPortalDto.setMpmkMafBill(findByConsAppNo.getRemMpmkMafAmt());
//				sanchayPortalDto.setMkmyTotalAmount(findByConsAppNo.getNewPayAmt());
//				sanchayPortalDto.setPayableAmount(findByConsAppNo.getPayAmt());
//				sanchayPortalDto.setApplicationType("MMKY");
//				sanchayPortalDto.setDsSvMkPayAmount(findByConsAppNo.getPayAmt());

//				sanchayPortalDto.setAvedanShulk(findByConsAppNo.);
//				sanchayPortalDto.setMkmySecurityDeposit(findByConsAppNo.);
//				sanchayPortalDto.setAvedanShulkFiveRupee(findByConsAppNo.);
			} else {
				ErpRev findByConsAppNo = erpRevRepository.findByConsAppNo(consumerApplicatioNo);

				if (findByConsAppNo == null) {
					response.setCode("404");
					response.setMessage("data not found");

					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
				if (findByConsAppNo.getRemCgst().compareTo(BigDecimal.ZERO) < 0) {
					sanchayPortalDto.setCgst(BigDecimal.ZERO);
					sanchayPortalDto.setSgst(BigDecimal.ZERO);
				} else {
					sanchayPortalDto.setCgst(findByConsAppNo.getRemCgst());
					sanchayPortalDto.setSgst(findByConsAppNo.getRemSgst());
				}

				sanchayPortalDto.setSuperVisionAmount(findByConsAppNo.getRemSupervisionAmt());
				sanchayPortalDto.setSupplyAffordingCharge(findByConsAppNo.getRemKvaAmt());
				sanchayPortalDto.setColonyOrSlum(findByConsAppNo.getRemColonyOrSlum());
				if (findByConsAppNo.getRemReturnAmt() != null) {
					sanchayPortalDto.setJeReturnAmount(findByConsAppNo.getRemReturnAmt());
				}
				sanchayPortalDto.setTotalBalanceSupervisionAmount(findByConsAppNo.getPayAmt());

				sanchayPortalDto.setDepositAmount(findByConsAppNo.getRemmDepositAmt());
				sanchayPortalDto.setTotalBalanceDepositAmount(findByConsAppNo.getPayAmt());

				sanchayPortalDto.setApplicationType(
						findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeName());

			}
			sanchayPortalDto
					.setNgbDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getNgbDcCd());
			sanchayPortalDto.setApplicationNumber(consumerApplicatioNo);
			sanchayPortalDto.setAddress1(findConsumerApplicationDetailByApplicationNo.getAddress());
			sanchayPortalDto.setConsumerName(findConsumerApplicationDetailByApplicationNo.getConsumerName());
			sanchayPortalDto.setDc(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcName());
			sanchayPortalDto
					.setDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcCode());
			sanchayPortalDto.setCity(findConsumerApplicationDetailByApplicationNo.getDistrict().getDistrictName());

			sanchayPortalDto
					.setMobileNumber(findConsumerApplicationDetailByApplicationNo.getConsumers().getConsumerMobileNo());
			// code start sending gst no. if exist to sanchay portal (10-june-2024) -Monika
			// Rajpoot
			if (findConsumerApplicationDetailByApplicationNo.getGstNumber() != null) {
				sanchayPortalDto.setGstNumber(findConsumerApplicationDetailByApplicationNo.getGstNumber());
			}
			sanchayPortalDto.setPaymentType("Revised_Demand_fees");
		}

		if (sanchayPortalDto.getTotalBalanceSupervisionAmount() != null) {
			sanchayPortalDto.setDsSvMkPayAmount(sanchayPortalDto.getTotalBalanceSupervisionAmount());
		}
		if (sanchayPortalDto.getTotalBalanceDepositAmount() != null) {
			sanchayPortalDto.setDsSvMkPayAmount(sanchayPortalDto.getTotalBalanceDepositAmount());
		}
// Monika code end
//	List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository.findByConsumerApplicationNo(consumerApplicatioNo);

		response.setCode("200");
		response.setMessage("Data Retrive Successfully");
		response.setList(Arrays.asList(sanchayPortalDto));

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

//	public static BigDecimal roundAmountCgstAndSgst(BigDecimal amount) {
//		BigDecimal roundedAmount = amount.setScale(0, RoundingMode.FLOOR); // Get the integer part
//
//		BigDecimal remainingPaise = amount.subtract(roundedAmount); // Get the decimal part (paise)
//
//		if (remainingPaise.compareTo(new BigDecimal(0.5)) >= 0) {
//			roundedAmount = roundedAmount.add(BigDecimal.ONE); // Round up if paise is 50 or more
//		}
//		return roundedAmount;
//	}

	public static BigDecimal roundAmountCgstAndSgst(BigDecimal amount) {
		// Round the amount to 2 decimal places to avoid precision issues
		BigDecimal preciseAmount = amount.setScale(2, RoundingMode.HALF_UP);

		// Get the integer part
		BigDecimal roundedAmount = preciseAmount.setScale(0, RoundingMode.FLOOR);

		// Get the fractional part (paise)
		BigDecimal remainingPaise = preciseAmount.subtract(roundedAmount);

		// Round up if paise is 0.5 or more
		if (remainingPaise.compareTo(new BigDecimal("0.5")) >= 0) {
			roundedAmount = roundedAmount.add(BigDecimal.ONE);
		}

		return roundedAmount;
	}

	public static BigDecimal round111(BigDecimal value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = value;
		bd = bd.setScale(places, RoundingMode.CEILING);
		return bd;
	}

//*********************************** New Erp API CALL *****************************************************************
//  Abhishek ne Erp data get krne k liye 2 api dii h unka integration
	@GetMapping("/newErpResponse/{erpNo}")
	public ResponseEntity<?> newErpResponse(@PathVariable Long erpNo) {

		Response response = new Response();

//	https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V_ALLDATA/' + erpNo
//	https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V_ALLDATA/' + erpNo
		try {

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> exchange = null;
			JSONObject json = null;

//			new Erp api pehle call krni hai Urjas wali api baad me call hogi  09-06-2025

//			String url = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V_ALLDATA/" + erpNo;
			String url = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V_ALLDATA/" + erpNo;
			HttpEntity<String> httpEntity = new HttpEntity<>(null);

			exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			json = new JSONObject(exchange.getBody());
			if (json.get("statusCode").toString().equals("404")) {
				String url1 = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V_ALLDATA/" + erpNo;
//				String url1 = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V_ALLDATA/" + erpNo;
				exchange = restTemplate.exchange(url1, HttpMethod.GET, httpEntity, String.class);
				json = new JSONObject(exchange.getBody());
			}

			System.out.println("json :" + json);
			if (json.length() == 0) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Enter Valid Erp Number");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			response.setCode("200");
			response.setMessage("Data Retrieved Successfully");
			response.setList(Arrays.asList(json));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/getErpData/{consumerApplicationNo}/{value}")
	public ResponseEntity<?> getErpData(@PathVariable String consumerApplicationNo, @PathVariable Long value)
			throws ErpEstimateAmountException {

		Response response = new Response();
		ConsumerApplicationDetail findByConsumerApplicationNumber = this.consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);

		if (findByConsumerApplicationNumber == null) {
			response.setMessage("Data not found for given application");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		BigDecimal demandAmount = getDemandAmount(consumerApplicationNo);
		if (demandAmount == null) {
			response.setCode("406");
			response.setMessage("No Demand Payment found for the given application number");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		switch (value.intValue()) {
		case 1:
			return handleDemandRefund(response, findByConsumerApplicationNumber, demandAmount);
		case 2:
			return handleReturnAmount(response, findByConsumerApplicationNumber);
		case 3:
			return handleReviseDemandRefund(response, findByConsumerApplicationNumber, demandAmount);
		case 4:
			return handleDemandAndReviseDemandRefund(response, findByConsumerApplicationNumber, demandAmount);
		default:
			response.setCode("406");
			response.setMessage("Invalid value parameter");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	private BigDecimal getDemandAmount(String consumerApplicationNo) {
		BillDeskPaymentResponse demandDataFromBilldesk = billPaymentResponseeeeeeeRepository
				.getDemandDataFromBilldesk(consumerApplicationNo);
		if (demandDataFromBilldesk != null) {
			return new BigDecimal(demandDataFromBilldesk.getAmount());
		}

		PoseMachinePostData demandDataFromPoseMachine = poseMachinePostDataRepository
				.getDemandDataFromPoseMachine(consumerApplicationNo);
		if (demandDataFromPoseMachine != null) {
			return demandDataFromPoseMachine.getTxnAmount();
		}

		ManualPayment demandDataFromManualPayment = manualPaymentRepository
				.getDemandDataFromManualPayment(consumerApplicationNo);
		if (demandDataFromManualPayment != null) {
			return new BigDecimal(demandDataFromManualPayment.getAmount());
		}

		return null;
	}

	private ResponseEntity<?> handleDemandRefund(Response response,
			ConsumerApplicationDetail findByConsumerApplicationNumber, BigDecimal demandAmount)
			throws ErpEstimateAmountException {
		ErpEstimateAmountData findByEstimateNumber = erpEstimateAmountService
				.findByEstimateNumber(findByConsumerApplicationNumber.getErpWorkFlowNumber());
		if (findByEstimateNumber == null) {
			response.setCode("100");
			response.setMessage("Data not found for this ERP number");
			return ResponseEntity.ok(response);
		}

		BigDecimal cgst = Optional.ofNullable(findByEstimateNumber.getCgst()).orElse(BigDecimal.ZERO);
		BigDecimal sgst = Optional.ofNullable(findByEstimateNumber.getSgst()).orElse(BigDecimal.ZERO);
		BigDecimal totalBalance = (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L))
				? findByEstimateNumber.getTotalBalanceSupervisionAmount()
				: findByEstimateNumber.getTotalBalanceDepositAmount();

		findByEstimateNumber.setRefundDemandAmnt(totalBalance.subtract(cgst).subtract(sgst));
		BigDecimal billDeskDemandAmountCheck = demandAmount.subtract(cgst).subtract(sgst);

		if (billDeskDemandAmountCheck.compareTo(findByEstimateNumber.getRefundDemandAmnt()) == 0) {
			response.setCode("200");
			response.setMessage("Data Returned Successfully");
			response.setList(Arrays.asList(findByEstimateNumber));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Difference in billdesk Received amount and refund created amount .");
			System.err.println("Difference in billdesk Received amount and refund created amount ." + "Paid Payment : "
					+ billDeskDemandAmountCheck + "Refundable Amount " + findByEstimateNumber.getRefundDemandAmnt());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	private ResponseEntity<?> handleReturnAmount(Response response,
			ConsumerApplicationDetail findByConsumerApplicationNumber) throws ErpEstimateAmountException {
		ErpEstimateAmountData findByEstimateNumber = erpEstimateAmountService
				.findByEstimateNumber(findByConsumerApplicationNumber.getErpWorkFlowNumber());
		ErpRev findByConsAppNo = erpRevRepository
				.findByConsAppNo(findByConsumerApplicationNumber.getConsumerApplicationNo());

		if (findByConsAppNo.getPayAmt().compareTo(BigDecimal.ZERO) >= 0) {
			findByEstimateNumber.setRefundJeReturnAmnt(
					findByEstimateNumber.getJeReturnAmount().add(findByConsAppNo.getRemReturnAmt()));
		} else {
			findByEstimateNumber.setRefundJeReturnAmnt(findByEstimateNumber.getJeReturnAmount());
		}

		response.setCode("200");
		response.setMessage("Data Returned Successfully");
		response.setList(Arrays.asList(findByEstimateNumber));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private ResponseEntity<?> handleReviseDemandRefund(Response response,
			ConsumerApplicationDetail findByConsumerApplicationNumber, BigDecimal demandAmount) {
		ErpRev findByConsAppNo = erpRevRepository
				.findByConsAppNo(findByConsumerApplicationNumber.getConsumerApplicationNo());
		if (findByConsAppNo == null) {
			response.setCode("100");
			response.setMessage("Data not found for this ERP Rev");
			return ResponseEntity.ok(response);
		}

		if (findByConsAppNo.getPayAmt().compareTo(BigDecimal.ZERO) >= 0) {
			response.setCode("406");
			response.setMessage("Your amount is not in negative so we cannot refund it");
			return ResponseEntity.ok(response);
		}

		BigDecimal remCgst = Optional.ofNullable(findByConsAppNo.getRemCgst()).orElse(BigDecimal.ZERO);
		BigDecimal remSgst = Optional.ofNullable(findByConsAppNo.getRemSgst()).orElse(BigDecimal.ZERO);
		BigDecimal payAmt = findByConsAppNo.getPayAmt();
		findByConsAppNo.setRefundReviseDemandAmnt(payAmt.subtract(remCgst).subtract(remSgst).abs());

		response.setCode("200");
		response.setMessage("Data Returned Successfully");
		response.setList(Arrays.asList(findByConsAppNo));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private ResponseEntity<?> handleDemandAndReviseDemandRefund(Response response,
			ConsumerApplicationDetail findByConsumerApplicationNumber, BigDecimal demandAmount)
			throws ErpEstimateAmountException {
		BillDeskPaymentResponse reviseDemandDataFromBilldesk = billPaymentResponseeeeeeeRepository
				.getReviseDemandDataFromBilldesk(findByConsumerApplicationNumber.getConsumerApplicationNo());

		if (reviseDemandDataFromBilldesk == null) {
			response.setCode("406");
			response.setMessage("You have not paid revise demand fees");
			return ResponseEntity.ok(response);
		}

		ErpEstimateAmountData findByEstimateNumber = erpEstimateAmountService
				.findByEstimateNumber(findByConsumerApplicationNumber.getErpWorkFlowNumber());
		BigDecimal totalBalanceSupervisionAmount = findByEstimateNumber.getTotalBalanceSupervisionAmount();
		BigDecimal totalBalanceDepositAmount = findByEstimateNumber.getTotalBalanceDepositAmount();
		BigDecimal cgst = Optional.ofNullable(findByEstimateNumber.getCgst()).orElse(BigDecimal.ZERO);
		BigDecimal sgst = Optional.ofNullable(findByEstimateNumber.getSgst()).orElse(BigDecimal.ZERO);
		ErpRev findByConsAppNo = erpRevRepository
				.findByConsAppNo(findByConsumerApplicationNumber.getConsumerApplicationNo());
		BigDecimal remCgst = Optional.ofNullable(findByConsAppNo.getRemCgst()).orElse(BigDecimal.ZERO);
		BigDecimal remSgst = Optional.ofNullable(findByConsAppNo.getRemSgst()).orElse(BigDecimal.ZERO);
		BigDecimal payAmt = findByConsAppNo.getPayAmt();
		BigDecimal reviseDemandAmount = new BigDecimal(reviseDemandDataFromBilldesk.getAmount());
		BigDecimal demandReviseDemandTotalAmnt = demandAmount.add(reviseDemandAmount);

		BigDecimal reviseDemandRefundableAmnt = demandReviseDemandTotalAmnt.subtract(cgst).subtract(sgst)
				.subtract(remCgst).subtract(remSgst);

		if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L)) {
			findByConsAppNo.setDemandRefundAmnt(totalBalanceSupervisionAmount.subtract(cgst).subtract(sgst));
		} else {
			findByConsAppNo.setDemandRefundAmnt(totalBalanceDepositAmount.subtract(cgst).subtract(sgst));
		}
		findByConsAppNo.setReviseRefundAmnt(payAmt.subtract(remCgst).subtract(remSgst));
		findByConsAppNo.setRefundReviseDemandAmnt(
				findByConsAppNo.getDemandRefundAmnt().add(findByConsAppNo.getReviseRefundAmnt()));

		if (reviseDemandRefundableAmnt.compareTo(findByConsAppNo.getRefundReviseDemandAmnt()) == 0) {
			response.setCode("200");
			response.setMessage("Data Returned Successfully");
			response.setList(Arrays.asList(findByConsAppNo));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Difference in billdesk sending amount and refund amount");
			System.err.println("Difference in billdesk Received amount and refund created amount ." + "Paid Payment : "
					+ reviseDemandRefundableAmnt + "Refundable Amount " + findByConsAppNo.getRefundReviseDemandAmnt());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@GetMapping({ "/getErpRevDataForRefund/{consumerApplicationNo}" })
	public ResponseEntity<?> getErpRevDataForRefund(@PathVariable String consumerApplicationNo)
			throws ErpEstimateAmountException {
		Response response = new Response();
		ConsumerApplicationDetail findByConsumerApplicationNumber = this.consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (findByConsumerApplicationNumber == null) {
			response.setMessage("Data not found for given application");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			ErpEstimateAmountData findByEstimateNumber = this.erpEstimateAmountService
					.findByEstimateNumber(findByConsumerApplicationNumber.getErpWorkFlowNumber());
			if (findByEstimateNumber == null) {
				response.setCode("100");
				response.setMessage("Data not found for this ERP number");
				return ResponseEntity.ok(response);
			} else {
				BigDecimal oldCgst = findByEstimateNumber.getCgst();
				BigDecimal oldSgst = findByEstimateNumber.getSgst();
				ErpRev findByConsAppNo = this.erpRevRepository.findByConsAppNo(consumerApplicationNo);
				if (findByConsAppNo == null) {
					response.setCode("100");
					response.setMessage("Data not found for this ERP Rev");
					return ResponseEntity.ok(response);
				} else {
					BigDecimal newSuperVisionAmnt = (BigDecimal) Optional
							.ofNullable(findByConsAppNo.getNewSupervisionAmt()).orElse(BigDecimal.ZERO);
					BigDecimal newKvaAmnt = (BigDecimal) Optional.ofNullable(findByConsAppNo.getNewKvaAmt())
							.orElse(BigDecimal.ZERO);
					BigDecimal remSuperVision = (BigDecimal) Optional.ofNullable(findByConsAppNo.getRemSupervisionAmt())
							.orElse(BigDecimal.ZERO);
					BigDecimal remKva = (BigDecimal) Optional.ofNullable(findByConsAppNo.getRemKvaAmt())
							.orElse(BigDecimal.ZERO);
					BigDecimal remColonyOrSlum = (BigDecimal) Optional.ofNullable(findByConsAppNo.getRemColonyOrSlum())
							.orElse(BigDecimal.ZERO);
					BigDecimal newDepositAmnt = (BigDecimal) Optional.ofNullable(findByConsAppNo.getNewDepositAmt())
							.orElse(BigDecimal.ZERO);
					BigDecimal remDeposit = (BigDecimal) Optional.ofNullable(findByConsAppNo.getRemmDepositAmt())
							.orElse(BigDecimal.ZERO);
					BigDecimal remSuperVisionAmnt = (BigDecimal) Optional
							.ofNullable(findByConsAppNo.getRemSupervisionAmt()).orElse(BigDecimal.ZERO);
					BigDecimal newColonySlumAmnt = (BigDecimal) Optional
							.ofNullable(findByConsAppNo.getNewColonyOrSlum()).orElse(BigDecimal.ZERO);
					if (findByConsAppNo.getPayAmt().compareTo(BigDecimal.ZERO) < 0) {
						ErpRevRefundDto dto = new ErpRevRefundDto();
						if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L)) {
							if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId()
									.equals(3L)) {
								dto.setNewSupervisionAmnt(newSuperVisionAmnt);
								dto.setOldSgst(oldSgst);
								dto.setOldCgst(oldCgst);
								dto.setNewKvaAmnt(newKvaAmnt);
								dto.setRemKvaAmnt(remKva);
								dto.setRemSupervisionAmnt(remSuperVisionAmnt);
								dto.setRefundableAmnt(remSuperVision.add(remKva).abs());
							} else if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId()
									.equals(4L)) {
								dto.setNewColonySlumAmnt(newColonySlumAmnt);
								dto.setRemColonySlumAmnt(remColonyOrSlum);
								dto.setRefundableAmnt(remColonyOrSlum.abs());
							} else {
								dto.setNewSupervisionAmnt(newSuperVisionAmnt);
								dto.setRemSupervisionAmnt(remSuperVisionAmnt);
								dto.setOldSgst(oldSgst);
								dto.setOldCgst(oldCgst);
								dto.setRefundableAmnt(remSuperVision.abs());
							}
						} else if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId()
								.equals(3L)) {
							dto.setNewSupervisionAmnt(newSuperVisionAmnt);
							dto.setOldSgst(oldSgst);
							dto.setOldCgst(oldCgst);
							dto.setNewKvaAmnt(newKvaAmnt);
							dto.setRemKvaAmnt(remKva);
							dto.setRemSupervisionAmnt(remSuperVisionAmnt);
							dto.setNewDepositAmnt(newDepositAmnt);
							dto.setRemDepositAmnt(remDeposit);
							dto.setRefundableAmnt(remSuperVision.add(remKva).add(remDeposit).abs());
						} else if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId()
								.equals(4L)) {
							dto.setNewColonySlumAmnt(newColonySlumAmnt);
							dto.setRemColonySlumAmnt(remColonyOrSlum);
							dto.setRefundableAmnt(remColonyOrSlum.abs());
						} else {
							dto.setNewSupervisionAmnt(newSuperVisionAmnt);
							dto.setOldSgst(oldSgst);
							dto.setOldCgst(oldCgst);
							dto.setRefundableAmnt(remSuperVision.add(remDeposit).abs());
						}

						response.setCode("200");
						response.setMessage("Data retrieved successfully");
						response.setList(Arrays.asList(dto));
						return ResponseEntity.ok(response);
					} else {
						response.setCode("406");
						response.setMessage("Your amount is not in negative so we can not refund it");
						return ResponseEntity.ok(response);
					}
				}
			}
		}
	}

	@Autowired
	private ManualPaymentRepository manualPaymentRepository;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDataRepository;

//	@GetMapping("/checkDemandOrReviseDemandRefund/{consumerApplicationNo}")
//	public ResponseEntity<?> checkDemandOrReviseDemandRefund(@PathVariable String consumerApplicationNo)
//			throws ErpEstimateAmountException {
//		Response response = new Response();
//		Map<String, String> map = new HashMap<>();
//
//		ConsumerApplicationDetail appDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(consumerApplicationNo);
//		if (appDetail == null) {
//			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Consumer Application not found"));
//		} else {
//			billPaymentResponseeeeeeeRepository.getAllPaymentDetails(consumerApplicationNo).forEach(bill -> {
//				if (bill.getAdditionalInfo() != null) {
//					if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
//						map.put("Payment_Type", "Revised_Demand_fees");
//					} else if (bill.getAdditionalInfo().equals("Demand_fees")) {
//						map.put("Payment_Type", "Demand_fees");
//					}
//				}
//			});
//			
//			return ResponseEntity.ok(new Response(HttpCode.OK, "Data Retrived Successfully ", Arrays.asList(map)));
//		}
//	}

	@GetMapping("/checkDemandOrReviseDemandRefund/{consumerApplicationNo}")
	public ResponseEntity<?> checkDemandOrReviseDemandRefund(@PathVariable String consumerApplicationNo)
			throws ErpEstimateAmountException {
		Response response = new Response();
		Map<String, String> map = new HashMap<>();

		ConsumerApplicationDetail appDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (appDetail == null) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Consumer Application not found"));
		} else {
			List<BillDeskPaymentResponse> paymentDetails = billPaymentResponseeeeeeeRepository
					.getAllPaymentDetails(consumerApplicationNo);

			if (paymentDetails == null || paymentDetails.isEmpty()) {
				// Fallback to another table if no data found in billPaymentResponse
				List<PoseMachinePostData> poseMachineData = poseMachinePostDataRepository
						.getDemandDataFromPoseMachineData(consumerApplicationNo);

				if (poseMachineData != null && !poseMachineData.isEmpty()) {
					poseMachineData.forEach(pose -> {
						if (pose.getPaymentType() != null) {
							if (pose.getPaymentType().equals("Revised_Demand_fees")) {
								map.put("Payment_Type", "Revised_Demand_fees");
							} else if (pose.getPaymentType().equals("Demand_fees")) {
								map.put("Payment_Type", "Demand_fees");
							}
						}
					});
				} else {
					List<ManualPayment> manualPaymentDb = manualPaymentRepository
							.findByConsumerApplicationNumber(consumerApplicationNo);
					manualPaymentDb.forEach(manual -> {
						if (manual.getPaymentType() != null) {
							if (manual.getPaymentType().equals("Revised_Demand_fees")) {
								map.put("Payment_Type", "Revised_Demand_fees");
							} else if (manual.getPaymentType().equals("Demand_fees")) {
								map.put("Payment_Type", "Demand_fees");
							}
						}
					});
				}

			} else {
				paymentDetails.forEach(bill -> {
					if (bill.getAdditionalInfo() != null) {
						if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
							map.put("Payment_Type", "Revised_Demand_fees");
						} else if (bill.getAdditionalInfo().equals("Demand_fees")) {
							map.put("Payment_Type", "Demand_fees");
						}
					}
				});
			}

			return ResponseEntity.ok(new Response(HttpCode.OK, "Data Retrived Successfully ", Arrays.asList(map)));
		}
	}

	private boolean hasDemandPayment(String consumerApplicationNo) {
		return billPaymentResponseeeeeeeRepository.getDemandDataFromBilldesk(consumerApplicationNo) != null
				|| poseMachinePostDataRepository.getDemandDataFromPoseMachine(consumerApplicationNo) != null
				|| manualPaymentRepository.getDemandDataFromManualPayment(consumerApplicationNo) != null;
	}

	private boolean hasReviseDemandPayment(String consumerApplicationNo) {
		return billPaymentResponseeeeeeeRepository.getReviseDemandDataFromBilldesk(consumerApplicationNo) != null;
	}

	private ResponseEntity<Response> buildResponse(String code, Object messageOrList, Response response) {
		response.setCode(code);
		if (messageOrList instanceof String) {
			response.setMessage((String) messageOrList);
		} else if (messageOrList instanceof List) {
			response.setList((List<?>) messageOrList);
		}
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

//	@PostConstruct
//	public void init() {
//	    System.out.println("poseMachinePostDataRepository injected? " + (poseMachinePostDataRepository != null));
//	}
//	

//	@GetMapping("/refNoGenerator")
	private String refNoGenerator() {

		String refNo = null;

		Calendar calendar = Calendar.getInstance();

		refNo = getAlphaCorrospondingNumber(calendar.get(Calendar.YEAR) - 2021)

				+ getAlphaCorrospondingNumber(calendar.get(Calendar.MONTH))

				+ (calendar.get(Calendar.DAY_OF_MONTH) <= 9 ? calendar.get(Calendar.DAY_OF_MONTH)
						: getAlphaCorrospondingNumber(calendar.get(Calendar.DAY_OF_MONTH) - 10))

				+ getAlphaCorrospondingNumber(calendar.get(Calendar.HOUR_OF_DAY))

				+ (String.valueOf(calendar.get(Calendar.MINUTE)).length() == 1 ? "0" + calendar.get(Calendar.MINUTE)
						: calendar.get(Calendar.MINUTE))

				+ (String.valueOf(calendar.get(Calendar.SECOND)).length() == 1 ? "0" + calendar.get(Calendar.SECOND)
						: calendar.get(Calendar.SECOND));

		Long nextValFromSequence = getNextVal();
		String formattedSeq = String.format("%02d", nextValFromSequence);

		System.err.println("generated txn id : " + "DSP" + refNo + formattedSeq);

		return "DSP" + refNo + formattedSeq;

	}

	private String getAlphaCorrospondingNumber(int number) {
		return String.valueOf((char) ('A' + number % 26));
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

	@GetMapping("/downloadpdf1")
	public ResponseEntity<byte[]> uploadFileForGallery1(@RequestParam("path") String filePath) {
		byte[] readAllBytes = null;
		HttpHeaders headers = null;
		try {
			String file = filePath.replace("\\", "/");

			System.out.println(file);
			double random1 = Math.random();
			String random = String.valueOf(random1);
			System.out.println(random);
			random = random.substring(random.indexOf(".") + 1);
			Path path = Paths.get(file);
			readAllBytes = Files.readAllBytes(path);
			headers = new HttpHeaders();
			headers.setContentType(MediaType.ALL);
			headers.set("Content-Disposition",
					String.format("attachment; filename=DSP_document_2022-23" + MediaType.ALL));
		} catch (Exception e) {

			return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

		}

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(readAllBytes, headers, HttpStatus.OK);

		return responseEntity;
	}

	@Autowired
	private SanchayPaymentDetailsRepository sanchayPaymentDetailsRepository;

	@GetMapping("/posePaymentReceipt/{consumerApplicatioNo}")
	public ResponseEntity<?> posePaymentReceipt(@PathVariable String consumerApplicatioNo)
			throws ConsumerApplicationDetailException, SchemeTypeException, Exception {
		Response<SanchayPortalDto> response = new Response<SanchayPortalDto>();

		ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo = consumerApplicationDetailService
				.findConsumerApplicationDetailByApplicationNo(consumerApplicatioNo);
		if (findConsumerApplicationDetailByApplicationNo == null) {
			response.setCode("404");
			response.setMessage("Application not found in database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		SanchayPaymentDetails sanchayApplicationNoDB = sanchayPaymentDetailsRepository
				.findByConsumerApplicationNo(consumerApplicatioNo);
		if (Objects.isNull(sanchayApplicationNoDB)) {
			response.setCode("404");
			response.setMessage("Application not found in sanchay database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		SanchayPortalDto sanchayPortalDto = new SanchayPortalDto();
//		sanchayPortalDto.setTransactionId(refNoGenerator());

		if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId().equals(47l)
				&& sanchayApplicationNoDB.getPaymentType().equals("Registration_Fees")) {
			sanchayPortalDto.setApplicationNumber(consumerApplicatioNo);
			sanchayPortalDto.setCgst(new BigDecimal(90));
			sanchayPortalDto.setSgst(new BigDecimal(90));
			sanchayPortalDto.setRegistrationFees(new BigDecimal(1000));
			sanchayPortalDto.setPayableAmount(new BigDecimal(1180));
			sanchayPortalDto.setPaymentType("Registration_Fees");

			response.setCode("200");
			response.setMessage("application is Pending for payment");
			response.setList(Arrays.asList(sanchayPortalDto));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} else if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId()
				.equals(47l) && sanchayApplicationNoDB.getPaymentType().equals("Demand_fees")) {

//			if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId() > 12) {
//				response.setCode("001");
//				response.setMessage("Payment already done");
//				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//			}
			if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId() < 12) {
				response.setCode("");
				response.setMessage("application is not Pending for payment");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {

//				MmkyPayAmount amount = mmkyPayAmountRespository.findByConsumerApplicationNumber(consumerApplicatioNo);
//
//				sanchayPortalDto.setGovMafBill(amount.getGovMafBill());
//				sanchayPortalDto.setMpmkMafBill(amount.getMpmkMafBill());
//				sanchayPortalDto.setMkmyTotalAmount(amount.getTotalAmount());
//				sanchayPortalDto.setPayableAmount(amount.getPayableAmount());
//				sanchayPortalDto.setAvedanShulk(amount.getAvedanShulk());
//				sanchayPortalDto.setMkmySecurityDeposit(amount.getSecurityDeposit());
//				sanchayPortalDto.setAvedanShulkFiveRupee(amount.getAvedanShulkFiveRupee());
//				sanchayPortalDto.setApplicationType("MMKY");
//				sanchayPortalDto.setDsSvMkPayAmount(amount.getPayableAmount());

				response.setCode("404");
				response.setMessage("Scheme Closed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
//				ErpEstimateCalculatedDto findByEstimateNumber = erpEstimateAmountService.calculateEstimateAmount(
//						findConsumerApplicationDetailByApplicationNo.getConsumerApplicationId());
				ErpEstimateAmountData findByEstimateNumber = estimateAmountRepository
						.findById(findConsumerApplicationDetailByApplicationNo.getErpWorkFlowNumber())
						.orElseThrow(() -> new ConsumerApplicationDetailException(
								new Response("404", "Demand ERP data not found in database")));

				if (findByEstimateNumber == null) {
					response.setCode("404");
					response.setMessage("Erp Scheme code not matched");

					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}

				sanchayPortalDto.setCgst(findByEstimateNumber.getCgst());
				sanchayPortalDto.setSgst(findByEstimateNumber.getSgst());

				sanchayPortalDto.setSuperVisionAmount(findByEstimateNumber.getSupervisionAmount());
				sanchayPortalDto.setSupplyAffordingCharge(findByEstimateNumber.getKvaLoad());
				sanchayPortalDto.setColonyOrSlum(findByEstimateNumber.getColonyOrSlum());
				if (findByEstimateNumber.getJeReturnAmount() != null) {
					sanchayPortalDto.setJeReturnAmount(findByEstimateNumber.getJeReturnAmount());
				}
				sanchayPortalDto
						.setTotalBalanceSupervisionAmount(findByEstimateNumber.getTotalBalanceSupervisionAmount());

				sanchayPortalDto.setDepositAmount(findByEstimateNumber.getDepositAmount());
				sanchayPortalDto.setTotalBalanceDepositAmount(findByEstimateNumber.getTotalBalanceDepositAmount());

				sanchayPortalDto.setApplicationType(
						findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeName());
				if (findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId().equals(1L)) {
					sanchayPortalDto.setDsSvMkPayAmount(findByEstimateNumber.getTotalBalanceSupervisionAmount());
				} else {
					sanchayPortalDto.setDsSvMkPayAmount(findByEstimateNumber.getTotalBalanceDepositAmount());
				}

			}
			sanchayPortalDto
					.setNgbDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getNgbDcCd());
			sanchayPortalDto.setApplicationNumber(consumerApplicatioNo);
			sanchayPortalDto.setAddress1(findConsumerApplicationDetailByApplicationNo.getAddress());
			sanchayPortalDto.setConsumerName(findConsumerApplicationDetailByApplicationNo.getConsumerName());
			sanchayPortalDto.setDc(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcName());
			sanchayPortalDto
					.setDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcCode());
			sanchayPortalDto.setCity(findConsumerApplicationDetailByApplicationNo.getDistrict().getDistrictName());

			sanchayPortalDto
					.setMobileNumber(findConsumerApplicationDetailByApplicationNo.getConsumers().getConsumerMobileNo());

			if (findConsumerApplicationDetailByApplicationNo.getGstNumber() != null) {
				sanchayPortalDto.setGstNumber(findConsumerApplicationDetailByApplicationNo.getGstNumber());
			}
			sanchayPortalDto.setPaymentType("Demand_fees");
		} else if (findConsumerApplicationDetailByApplicationNo.getApplicationStatus().getApplicationStatusId()
				.equals(47l) && sanchayApplicationNoDB.getPaymentType().equals("Revised_Demand_fees")) {
			if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {

				response.setCode("404");
				response.setMessage("Scheme Closed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

//				ErpRev findByConsAppNo = erpRevRepository.findByConsAppNo(consumerApplicatioNo);
//
//				sanchayPortalDto.setGovMafBill(findByConsAppNo.getRemGovMafAmt());
//				sanchayPortalDto.setMpmkMafBill(findByConsAppNo.getRemMpmkMafAmt());
//				sanchayPortalDto.setMkmyTotalAmount(findByConsAppNo.getNewPayAmt());
//				sanchayPortalDto.setPayableAmount(findByConsAppNo.getPayAmt());
//				sanchayPortalDto.setApplicationType("MMKY");
//				sanchayPortalDto.setDsSvMkPayAmount(findByConsAppNo.getPayAmt());

//				sanchayPortalDto.setAvedanShulk(findByConsAppNo.);
//				sanchayPortalDto.setMkmySecurityDeposit(findByConsAppNo.);
//				sanchayPortalDto.setAvedanShulkFiveRupee(findByConsAppNo.);
			} else {
				ErpRev findByConsAppNo = erpRevRepository.findByConsAppNo(consumerApplicatioNo);

				if (findByConsAppNo == null) {
					response.setCode("404");
					response.setMessage("data not found for revise application");

					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
				if (findByConsAppNo.getRemCgst().compareTo(BigDecimal.ZERO) < 0) {
					sanchayPortalDto.setCgst(BigDecimal.ZERO);
					sanchayPortalDto.setSgst(BigDecimal.ZERO);
				} else {
					sanchayPortalDto.setCgst(findByConsAppNo.getRemCgst());
					sanchayPortalDto.setSgst(findByConsAppNo.getRemSgst());
				}

				sanchayPortalDto.setSuperVisionAmount(findByConsAppNo.getRemSupervisionAmt());
				sanchayPortalDto.setSupplyAffordingCharge(findByConsAppNo.getRemKvaAmt());
				sanchayPortalDto.setColonyOrSlum(findByConsAppNo.getRemColonyOrSlum());
				if (findByConsAppNo.getRemReturnAmt() != null) {
					sanchayPortalDto.setJeReturnAmount(findByConsAppNo.getRemReturnAmt());
				}
				sanchayPortalDto.setTotalBalanceSupervisionAmount(findByConsAppNo.getPayAmt());

				sanchayPortalDto.setDepositAmount(findByConsAppNo.getRemmDepositAmt());
				sanchayPortalDto.setTotalBalanceDepositAmount(findByConsAppNo.getPayAmt());

				sanchayPortalDto.setApplicationType(
						findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeName());

			}
			sanchayPortalDto
					.setNgbDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getNgbDcCd());
			sanchayPortalDto.setApplicationNumber(consumerApplicatioNo);
			sanchayPortalDto.setAddress1(findConsumerApplicationDetailByApplicationNo.getAddress());
			sanchayPortalDto.setConsumerName(findConsumerApplicationDetailByApplicationNo.getConsumerName());
			sanchayPortalDto.setDc(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcName());
			sanchayPortalDto
					.setDcCode(findConsumerApplicationDetailByApplicationNo.getDistributionCenter().getDcCode());
			sanchayPortalDto.setCity(findConsumerApplicationDetailByApplicationNo.getDistrict().getDistrictName());

			sanchayPortalDto
					.setMobileNumber(findConsumerApplicationDetailByApplicationNo.getConsumers().getConsumerMobileNo());
			// code start sending gst no. if exist to sanchay portal (10-june-2024) -Monika
			// Rajpoot
			if (findConsumerApplicationDetailByApplicationNo.getGstNumber() != null) {
				sanchayPortalDto.setGstNumber(findConsumerApplicationDetailByApplicationNo.getGstNumber());
			}
			sanchayPortalDto.setPaymentType("Revised_Demand_fees");
		} else {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Application status is not compatible for API Response");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		if (sanchayPortalDto.getTotalBalanceSupervisionAmount() != null) {
			sanchayPortalDto.setDsSvMkPayAmount(sanchayPortalDto.getTotalBalanceSupervisionAmount());
		}
		if (sanchayPortalDto.getTotalBalanceDepositAmount() != null) {
			sanchayPortalDto.setDsSvMkPayAmount(sanchayPortalDto.getTotalBalanceDepositAmount());
		}

		response.setCode("200");
		response.setMessage("Data Retrive Successfully");
		response.setList(Arrays.asList(sanchayPortalDto));

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@Autowired
	private OytMaterialChargesClient oytMaterialChargesClient;

	@Autowired
	private OytMaterialChargesRepository oytMaterialChargesRepository;

	@Autowired
	private OytProjectDetailsRepository oytProjectDetailsRepository;

	@GetMapping("/oytMaterialCharges/{erpNo}/{consumerApplicationNo}")
	public Map<String, Object> oytMaterialCharges(@PathVariable String erpNo,
			@PathVariable String consumerApplicationNo) {

		ApiResponseOYT apiResponse = oytMaterialChargesClient.getOytMaterialCharges(erpNo);

		System.err.println("ccccccccccccccc : " + new Gson().toJson(apiResponse));
		if (apiResponse.getStatusCode().equals("200")) {
			System.err.println("bbbbbbbbbb :  " + new Gson().toJson(apiResponse.getData().get(0)));
			System.err.println("aaaaaaaaaa :  " + new Gson().toJson(apiResponse.getData()));

			apiResponse.getData().stream().forEach(oyt -> {
				OytMaterialCharges oytMaterialCharges = new OytMaterialCharges();
				ModelMapper mapper = new ModelMapper();

				mapper.map(oyt, oytMaterialCharges);
				if (oyt.getResourceItem().equals("M-0602002") || oyt.getResourceItem().equals("M-0602007")
						|| oyt.getResourceItem().equals("M-0201106") || oyt.getResourceItem().equals("M-0201142")) {
					oytMaterialCharges.setItemCodeFlag(1);
				}
				oytMaterialCharges.setErpNo(erpNo);
				oytMaterialCharges.setConsumerApplicationNo(consumerApplicationNo);

				System.err.println("dddddddd : " + new Gson().toJson(oytMaterialCharges));
				oytMaterialChargesRepository.save(oytMaterialCharges);

			});

		}

		return null;
	}

	@GetMapping("/oytMaterialCharges1/{erpNo}/{consumerApplicationNo}")
	public ApiResponseOYT oytMaterialCharges1(@PathVariable String erpNo, @PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {

		ApiResponseOYT apiResponse = oytMaterialChargesClient1.oytMaterialCharges1(erpNo);

		if (apiResponse.getStatusCode().equals("200")) {
			System.err.println("bbbbbbbbbb :  " + new Gson().toJson(apiResponse.getData().get(0)));
			System.err.println("aaaaaaaaaa :  " + new Gson().toJson(apiResponse.getData()));

			apiResponse.getData().stream().forEach(oyt -> {
				OytProjectDetails oytMaterialCharges = new OytProjectDetails();
				ModelMapper mapper = new ModelMapper();

				mapper.map(oyt, oytMaterialCharges);
				if (oyt.getResourceItem().equals("M-0602002") || oyt.getResourceItem().equals("M-0602007")
						|| oyt.getResourceItem().equals("M-0201106") || oyt.getResourceItem().equals("M-0201142")) {
					oytMaterialCharges.setItemCodeFlag(1);

					oytMaterialCharges.setRateWithTotalCgstAndSgst(
							erpRevServiceIMP.round111(oyt.getRate().multiply(new BigDecimal(1.18)), 0));

					oytMaterialCharges.setOytMaterialCostWithCgst(
							erpRevServiceIMP.round111(oyt.getItemCost().multiply(new BigDecimal(0.09)), 0));
					oytMaterialCharges.setOytMaterialCostWithSgst(
							erpRevServiceIMP.round111(oyt.getItemCost().multiply(new BigDecimal(0.09)), 0));

					oytMaterialCharges.setOytMaterialTotalCostWithCgstAndSgst(erpRevServiceIMP
							.round111(oytMaterialCharges.getRateWithTotalCgstAndSgst().multiply(oyt.getItemQty()), 0));
				}

				oytMaterialCharges.setErpNo(erpNo);
				oytMaterialCharges.setConsumerApplicationNo(consumerApplicationNo);

				System.err.println("dddddddd : " + new Gson().toJson(oytMaterialCharges));
				oytProjectDetailsRepository.save(oytMaterialCharges);

			});

		}

		return apiResponse;
	}

	@Autowired
	ReSamplingRepository reSamplingRepository;

	@GetMapping("/getoytMaterial/{consumerApplicationNo}")
	public Map<String, BigDecimal> getoytMaterial(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		List<OytProjectDetails> oytProjectDetails = oytProjectDetailsRepository
				.findDistinctByConsumerApplicationNoAndItemCodeFlag(consumerApplicationNo, 1);

		System.out.println("oytProjectDetails" + oytProjectDetails);
		OytProjectDetails oyt = new OytProjectDetails();

		BigDecimal d = BigDecimal.ZERO;
		Response<?> response = new Response<SanchayPortalDto>();
		for (OytProjectDetails s : oytProjectDetails) {
			if (s.getExpType().equals("M-0602002") || s.getExpType().equals("M-0602007")
					|| s.getExpType().equals("M-0201106") || s.getExpType().equals("M-0201142")) {

				List<OytProjectDetails> listDb = oytProjectDetailsRepository
						.findByConsumerApplicationNoAndExpType(s.getExpType(), consumerApplicationNo);

				d = d.add(s.getOytMaterialTotalCostWithCgstAndSgst());
			}

		}

		Map<String, BigDecimal> m = new HashMap<String, BigDecimal>();
		m.put("total_amount", d);
		return m;
	}

	@GetMapping("/getoytMaterialdataByConsumerapplicationNumber/{consumerApplicationNo}")
	public ResponseEntity<Response> getoytMaterialdataByConsumerapplicationNumber(
			@PathVariable String consumerApplicationNo) throws ConsumerApplicationDetailException {
		Response<OytProjectDetails> response = new Response<OytProjectDetails>();
		List<OytProjectDetails> oytProjectDetails = oytProjectDetailsRepository
				.findDistinctByConsumerApplicationNoAndItemCodeFlag(consumerApplicationNo, 1);
		response.setCode("200");
		response.setMessage("data successfull retrive");
		response.setList(oytProjectDetails);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/erpEstimateAmountDataGetByerp/{consumerApplicationNo}")
	public ResponseEntity<Response> erpEstimateAmountDataGetBYERP(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		Response<ErpEstimateCalculatedDto> response = new Response<ErpEstimateCalculatedDto>();
		List<ConsumerApplicationDetail> findListByConsumerApplicatonNumber = consumerApplictionDetailRepository
				.findListByConsumerApplicatonNumber(consumerApplicationNo);
		if (findListByConsumerApplicatonNumber.isEmpty()) {
			response.setCode("404");
			response.setMessage("data not ound");
			return ResponseEntity.ok(response);
		}
		ErpEstimateAmountDataGet findByErpNo = erpEstimateAmountDataGetRepository
				.findByErpNo(findListByConsumerApplicatonNumber.get(0).getErpWorkFlowNumber());

		ErpEstimateCalculatedDto dto = new ErpEstimateCalculatedDto();

//		dto.setActionPerformed(null);
//		dto.setBalanceRemaining(null);

		if (findByErpNo.getAvedanShulk() != null) {
			dto.setAvedanShulk(findByErpNo.getAvedanShulk());
		}
		if (findByErpNo.getAvedanShulkFiveRupee() != null) {
			dto.setAvedanShulkFiveRupee(findByErpNo.getAvedanShulkFiveRupee());
		}

		if (findByErpNo.getCgst() != null) {
			dto.setCgst(findByErpNo.getCgst());
		}
		if (findByErpNo.getSgst() != null) {
			dto.setSgst(findByErpNo.getSgst());
		}

		if (findByErpNo.getConsumerApplicationNo() != null) {
			dto.setConsumerApplicationNo(consumerApplicationNo);
		}
		if (findByErpNo.getDepositAmount() != null) {
			dto.setDepositAmount(findByErpNo.getDepositAmount());
		}

		if (findByErpNo.getJeReturnAmount() != null) {
			dto.setJeReturnAmount(findByErpNo.getJeReturnAmount());
		}
		if (findByErpNo.getKvaLoad() != null) {
			dto.setKvaLoadAmount(findByErpNo.getKvaLoad());
		}
		if (findByErpNo.getKwLoad()!= null) {
			dto.setKwLoadAmount(findByErpNo.getKwLoad());
		}
		if (findByErpNo.getMinusCost() != null) {
			dto.setMinusCost(findByErpNo.getMinusCost());
		}

		if (findByErpNo.getOytMaterialTotalCostWithCsgstAndSgst() != null) {
			dto.setOytMaterialtotalcostwithCgstAndSgst(null);
		}
		if (findByErpNo.getPrayakshanShulk() != null) {
			dto.setPrayakshanShulk(findByErpNo.getPrayakshanShulk());
		}
		if (findByErpNo.getRegistrationCharges() != null) {
			dto.setRegistrationCharges(findByErpNo.getRegistrationCharges());
		}

		if (findByErpNo.getSecurityDeposit() != null) {
			dto.setSecurityDeposit(findByErpNo.getSecurityDeposit());
		}
		if (findByErpNo.getColonyOrSlum() != null) {
			dto.setColonyOrSlum(findByErpNo.getColonyOrSlum());
		}
		if (findByErpNo.getSspMeterCost() != null) {
			dto.setSspMeterCost(findByErpNo.getSspMeterCost());
		}
		if (findByErpNo.getSspRegistrationCharge() != null) {
			dto.setSspRegistrationCharge(findByErpNo.getSspRegistrationCharge());
		}
		if (findByErpNo.getRegistrationCharges() != null) {
			dto.setRegistrationCharges(findByErpNo.getRegistrationCharges());
		}
		if (findByErpNo.getSspTotalAmount() != null) {
			dto.setSspTotalAmount(findByErpNo.getSspTotalAmount());
		}
		if (findByErpNo.getSupervisionAmount() != null) {
			dto.setSuperVisionAmount(findByErpNo.getSupervisionAmount());
		}

		if (findByErpNo.getTotalBalanceSupervisionAmount() != null) {
			dto.setTotalamountOfSupervision(findByErpNo.getTotalBalanceSupervisionAmount());
		}
		if (findByErpNo.getTotalBalanceDepositAmount() != null) {
			dto.setTotalBalanceDepositAmount(findByErpNo.getTotalBalanceDepositAmount());
		}
		if (findByErpNo.getTotalBalanceSupervisionAmount() != null) {
			dto.setTotalBalanceSupervisionAmount(findByErpNo.getTotalBalanceSupervisionAmount());
		}
		if (findByErpNo.getTotalBalanceDepositAmount() != null) {
			dto.setTotaldepositAmount(findByErpNo.getTotalBalanceDepositAmount());
		}
		if (findByErpNo.getU_sec_194J_tds2() != null) {
			dto.setU_sec_194J_tds2(findByErpNo.getU_sec_194J_tds2());
		}

		if (findByErpNo.getU_sec_194C_tds2() != null) {
			dto.setU_sec_194C_tds2(findByErpNo.getU_sec_194C_tds2());
		}

		if (findByErpNo.getU_194C_tds2_fSupDep() != null) {
			dto.setU_sec_194C_tds2(findByErpNo.getU_194C_tds2_fSupDep());
		}

//		if (findByErpNo.getApprovedBy() != null) {
//			dto.setFinalOyt(null);
//		}
//		if (findByErpNo.getApprovedBy() != null) {
//			dto.setGovMaffBill(null);
//		dto.setTotalAmountOfMKMY(null);
//		}
//		if (findByErpNo.getApprovedBy() != null) {
//			dto.setRegistrationFee(null);
//		}
//		if (findByErpNo.getApprovedBy() != null) {
//			dto.setParikshanCgst(null);
//		}
//		if (findByErpNo.getApprovedBy() != null) {
//			dto.setMpmkvvclMaffBill(null);
//		}
//
//		if (findByErpNo.getApprovedBy() != null) {
//			dto.setOytMaterialcostwithCgst(null);
//		}
//		if (findByErpNo.getApprovedBy() != null) {
//			dto.setOytMaterialcostwithSgst(null);
//		}

		response.setCode("200");
		response.setMessage("data successfull retrive");
		response.setList(Arrays.asList(dto));
		return ResponseEntity.ok(response);
	}

//	erpEstimateAmountDataGetRepository
	
	
	
	
	@GetMapping("/saveForSupervisionOnlyButNotForOyt/{erpNo}/{consumerApplicationNo}")
	public ApiResponseOYT saveForSupervisionOnlyButNotForOyt(@PathVariable String erpNo, @PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {

		ApiResponseOYT apiResponse = oytMaterialChargesClient1.oytMaterialCharges1(erpNo);

		if (apiResponse.getStatusCode().equals("200")) {
		

			apiResponse.getData().stream().forEach(oyt -> {
				
				SupervisionOnlyButNotForOyt SupervisionOnlyButNotForOyt = new SupervisionOnlyButNotForOyt();
				
				ModelMapper mapper = new ModelMapper();

				mapper.map(oyt, SupervisionOnlyButNotForOyt);
				

				SupervisionOnlyButNotForOyt.setErpNo(erpNo);
				SupervisionOnlyButNotForOyt.setConsumerApplicationNo(consumerApplicationNo);

				System.err.println("dddddddd : " + new Gson().toJson(SupervisionOnlyButNotForOyt));
				supervisionOnlyButNotForOytRepository.save(SupervisionOnlyButNotForOyt);

			});

		}

		return apiResponse;
	}
	
}
