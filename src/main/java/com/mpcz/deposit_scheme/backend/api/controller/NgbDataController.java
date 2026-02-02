package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.NgbStagingData;
import com.mpcz.deposit_scheme.backend.api.dto.NgbDto;
import com.mpcz.deposit_scheme.backend.api.dto.NgbDtoForConusmerNo;
import com.mpcz.deposit_scheme.backend.api.dto.NgbStagingDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.NgbStagingDataRepository;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/NgbStagingData")
@SuppressWarnings("unchecked")
public class NgbDataController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	private NgbStagingDataRepository ngbStagingDataRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Value("${ngb.data.url}")
	private String ngbDataUrl;

	@Value("${post.nsc.ssp}")
	private String postNscSsp;
	private static Map<String, String> tokens = new HashMap<>();

	Logger LOG = LoggerFactory.getLogger(NgbDataController.class);

// ***********************  Get Token for NGB Data Request ******************************************	
	@GetMapping("/getToken")
	public String getToken() {
		RestTemplate rest = new RestTemplate();
		try {

//			    "username": "ADMIN_CZ",
//			    "password": "ADMIN_CZ"
			String url = ngbDataUrl + "/mppkvvcl/nextgenbilling/backend/api/v1/authentication/login";
			System.out.println(" url : " + url);
			Map<String, String> requestBody = new HashMap<>();
			requestBody.put("username", "ADMIN_CZ");
			requestBody.put("password", "ADMIN_CZ");

			System.out.println("requestBody : " + requestBody);

			ResponseEntity<Map> postForEntity = rest.postForEntity(url, requestBody, Map.class);

			System.out.println("postForEntity : " + postForEntity);
			if (postForEntity.getStatusCode() == HttpStatus.OK) {
				HttpHeaders responseHeaders = postForEntity.getHeaders();
				String authToken = responseHeaders.getFirst(HttpHeaders.AUTHORIZATION);

				tokens.put("authorizationToken", authToken);

				System.out.println("Authorization Token: " + authToken);
				return authToken;
			} else {
				throw new RuntimeException("Failed to retrieve authorization token");
			}

		} catch (Exception e) {
			return null;
		}

	}

	// *************************** Fetching data by location code and sending it to
	// front end *********************
	@GetMapping("/fetchDataByLocationCode/{locationCode}")
	public ResponseEntity<?> fetchDataByLocationCode(@PathVariable Long locationCode) {
		RestTemplate restTemplate = new RestTemplate();
		Response res = new Response();
		try {

			String token = getToken();

			System.out.println("Token : " + token);
			String apiUrl = ngbDataUrl + "/mppkvvcl/nextgenbilling/backend/api/v1/group//location-code/" + locationCode
					+ "/is-deleted/false";

			System.out.println("apiUrl : " + apiUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(token);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody();

				System.out.println("Response: " + responseBody);
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.body(responseBody);

			} else {
				System.out.println("Error: " + response.getStatusCodeValue());
				return ResponseEntity.status(response.getStatusCode()).body("Error occurred while fetching data");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

	// *************************** Fetching data by Group No and sending it to front
	// end *********************
	@GetMapping("/fetchDataWithGRNo/{GrNo}")
	public ResponseEntity<?> fetchDataWithGRNo(@PathVariable String GrNo) {
		RestTemplate restTemplate = new RestTemplate();
		Response res = new Response();
		try {

			String token = getToken();
			System.out.println("Token : " + token);
//				http://172.16.17.110:8080/mppkvvcl/nextgenbilling/backend/api/v1/reading/diary/group/no/RMP21

			String apiUrl = ngbDataUrl + "/mppkvvcl/nextgenbilling/backend/api/v1/reading/diary/group/no/" + GrNo;

			System.out.println("apiUrl : " + apiUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(token);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody();

				System.out.println("Response: " + responseBody);

				return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.body(responseBody);

			} else {
				System.out.println("Error: " + response.getStatusCodeValue());
				return ResponseEntity.status(response.getStatusCode()).body("Error occurred while fetching data");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

	// *************************** Fetching data by Isampark Code and sending it to
	// front end *********************
	@GetMapping("/getFeederDataWithIsamparkCode/{iSamparkCode}")
	public ResponseEntity<?> getFeederDataWithIsamparkCode(@PathVariable Long iSamparkCode) {
		RestTemplate restTemplate = new RestTemplate();
		Response res = new Response();
		try {

			String apiUrl = "http://isampark.mpcz.in/UrjasApi/UrjasApi.aspx?api=1&var=" + iSamparkCode;

			System.out.println("apiUrl : " + apiUrl);
			HttpHeaders headers = new HttpHeaders();
//				headers.setBearerAuth(token);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody();

				System.out.println("Response: " + responseBody);

				return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.body(responseBody);

			} else {
				System.out.println("Error: " + response.getStatusCodeValue());
				return ResponseEntity.status(response.getStatusCode()).body("Error occurred while fetching data");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

	// *************************** Fetching data by Feeder Code and sending it to
	// front end *********************
	@GetMapping("/getDataWithFeederCode/{feederCode}")
	public ResponseEntity<?> getDataWithFeederCode(@PathVariable Long feederCode) {
		RestTemplate restTemplate = new RestTemplate();
		Response res = new Response();
		try {

			String apiUrl = "http://isampark.mpcz.in/UrjasApi/UrjasApi.aspx?api=0&var=" + feederCode;

			System.out.println("apiUrl : " + apiUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody();

				System.out.println("Response: " + responseBody);

				return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.body(responseBody);

			} else {
				System.out.println("Error: " + response.getStatusCodeValue());
				return ResponseEntity.status(response.getStatusCode()).body("Error occurred while fetching data");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

//		*************************** Sending data to front end which is present in Database for NGB ********************************
	@GetMapping("/getNgbData/{consumerAppNo}")
	public ResponseEntity<Response> getNgbData(@PathVariable String consumerAppNo) {
		Response response = new Response();
		NgbDto dto = new NgbDto();

		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
				Date createdDate = findByConsumerApplicationNumber.getCreated();

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify the desired date format

				String formattedDate = dateFormat.format(createdDate);
				System.out.println("formattedDate : " + formattedDate);

				dto.setConsumerName(findByConsumerApplicationNumber.getConsumerName());
				dto.setRelativeName(findByConsumerApplicationNumber.getGuardianName());
				dto.setRelation("FATHER");
				dto.setCategory(findByConsumerApplicationNumber.getCastCategory());
				dto.setAadhaarNo(findByConsumerApplicationNumber.getAadharNo());
				dto.setAddress1(findByConsumerApplicationNumber.getAddress());
				if (findByConsumerApplicationNumber.getPincode() == null) {
					dto.setAddress2(findByConsumerApplicationNumber.getAddress());
				} else {
					dto.setAddress2(findByConsumerApplicationNumber.getPincode());
				}
				dto.setAddress3(findByConsumerApplicationNumber.getDistrict().getDistrictName());
				dto.setDateOfReg(formattedDate);
				dto.setLocationCode(
						Long.parseLong(findByConsumerApplicationNumber.getDistributionCenter().getNgbDcCd()));
				dto.setPlotSize(5.0);
				dto.setPlotSizeUnit("HCT");
				dto.setPortalName("DSP");
				dto.setApplicationNumber(consumerAppNo);
				dto.setPrimaryMobileNo(findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
				dto.setConnectionType("PERMANENT");
				dto.setPurposeOfInstallation("(FLAT RATE) Permanent agricultural pump");
				dto.setPurposeOfInstallationId(101L);
				dto.setSanctionedLoad(Double.parseDouble(findByConsumerApplicationNumber.getJeLoad()));
				dto.setSanctionedLoadUnit(findByConsumerApplicationNumber.getJeLoadUnitKwYaKva());
				dto.setSubCategoryCode(512L);
				dto.setTariffCategory("LV5");
				dto.setTarrifCode("LV5.4");
				dto.setPremiseType("RURAL");
				dto.setMeteringStatus("UNMETERED");
				dto.setConsumerNameH("");
				dto.setPropertyName("khasra_no");
				dto.setPropertyValue(findByConsumerApplicationNumber.getKhasra());
				dto.setAreaStatus("AUTHORISED");
				dto.setPhase("THREE");
				dto.setIsBpl("false");
				dto.setIsEmployee("false");
				dto.setIsSeasonal("false");
				dto.setIsXray("false");
				dto.setIsWeldingTransformerSurcharge("false");
				dto.setIsCapacitorSurcharge("false");
				dto.setIsDemandside("false");
				dto.setIsiMotorType("false");
				dto.setIsBeneficiary("false");
				dto.setIsAffiliatedConsumer("false");
				dto.setIsGovernment("false");
				dto.setContractDemand(0.0);
				dto.setContractDemandUnit("KW");
				dto.setRegistrationFeeAmount(new BigDecimal(2500.00));
				dto.setiSamparkLocationCode(
						Long.parseLong(findByConsumerApplicationNumber.getDistributionCenter().getDcCode()));

				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
						.findByConsumerApplicationNoDemand(consumerAppNo);
				if (findByConsumerApplicationNo == null || findByConsumerApplicationNo.isEmpty()) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Consumer Application not found in BillDesk Payment Response");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
				for (BillDeskPaymentResponse bill : findByConsumerApplicationNo) {
					if (bill.getAdditionalInfo().equals("Demand_fees") && bill.getAuth_status().equals("0300")) {

						String tranId = bill.getTranId();
						int startIndex = Math.max(0, tranId.length() - 10);
						String lastTenChars = tranId.substring(startIndex);
						dto.setRegistrationFeeAmountMrNo(lastTenChars);
						dto.setSecurityDepositAmountMrNo(lastTenChars);

					}
				}
				MmkyPayAmount mmkyPayAmnt = mmkyPayAmountRespository.findByConsumerApplicationNumber(consumerAppNo);
				if (mmkyPayAmnt == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Consumer Application not found in MmkyPayAmount");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
				dto.setSecurityDepositAmount(mmkyPayAmnt.getSecurityDeposit());

				response.setCode(ResponseCode.OK);
				response.setMessage(ResponseMessage.SUCCESS);
				response.setList(Arrays.asList(dto));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				Date createdDate = findByConsumerApplicationNumber.getCreated();

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify the desired date format

				String formattedDate = dateFormat.format(createdDate);
				System.out.println("formattedDate : " + formattedDate);

				dto.setConsumerName(findByConsumerApplicationNumber.getConsumerName());
				dto.setRelativeName(findByConsumerApplicationNumber.getGuardianName());
				dto.setRelation("FATHER");
				dto.setCategory(findByConsumerApplicationNumber.getCastCategory());
				dto.setAadhaarNo(findByConsumerApplicationNumber.getAadharNo());
				dto.setAddress1(findByConsumerApplicationNumber.getAddress());
				if (findByConsumerApplicationNumber.getPincode() == null) {
					dto.setAddress2(findByConsumerApplicationNumber.getAddress());
				} else {
					dto.setAddress2(findByConsumerApplicationNumber.getPincode());
				}
				dto.setAddress3(findByConsumerApplicationNumber.getDistrict().getDistrictName());
				dto.setDateOfReg(formattedDate);
				dto.setLocationCode(
						Long.parseLong(findByConsumerApplicationNumber.getDistributionCenter().getNgbDcCd()));
//				dto.setPlotSize(5.0);
				dto.setPlotSize(findByConsumerApplicationNumber.getArea());
				dto.setPlotSizeUnit("HCT");
				dto.setPortalName("DSP");
				dto.setApplicationNumber(consumerAppNo);
				dto.setPrimaryMobileNo(findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
				dto.setConnectionType("PERMANENT");
				dto.setPurposeOfInstallation("(FLAT RATE) Permanent agricultural pump");
				dto.setPurposeOfInstallationId(101L);
				dto.setSanctionedLoad(Double.parseDouble(findByConsumerApplicationNumber.getJeLoad()));
				dto.setSanctionedLoadUnit(findByConsumerApplicationNumber.getJeLoadUnitKwYaKva());

//				if ("SC".equals(findByConsumerApplicationNumber.getCastCategory())
//						|| "ST".equals(findByConsumerApplicationNumber.getCastCategory())) {
//					dto.setSubCategoryCode(510L);
//				} else {
//					dto.setSubCategoryCode(512L);
//				}
				dto.setTariffCategory("LV5");
				dto.setTarrifCode("LV5.4");
				if (findByConsumerApplicationNumber.getPremiseAreaType() != null
						&& !findByConsumerApplicationNumber.getPremiseAreaType().isEmpty()) {
					dto.setPremiseType(findByConsumerApplicationNumber.getPremiseAreaType());
				} else {
					dto.setPremiseType("RURAL");

				}

				dto.setMeteringStatus((findByConsumerApplicationNumber.getMeteringStatus() != null
						&& !findByConsumerApplicationNumber.getMeteringStatus().isEmpty())
								? findByConsumerApplicationNumber.getMeteringStatus()
								: "UNMETERED");

				dto.setConsumerNameH("");
				dto.setPropertyName("khasra_no");
				dto.setPropertyValue(findByConsumerApplicationNumber.getKhasra());
				dto.setAreaStatus("AUTHORISED");
				dto.setPhase(findByConsumerApplicationNumber.getPhase() != null
						&& !findByConsumerApplicationNumber.getPhase().isEmpty()
								? findByConsumerApplicationNumber.getPhase()
								: "THREE");
//				code start 01-12-2025
				if ("SINGLE".equals(dto.getPhase()) && "URBAN".equals(dto.getPremiseType())) {
					dto.setSubCategoryCode(509L);
				}
				if ("SINGLE".equals(dto.getPhase()) && "RURAL".equals(dto.getPremiseType())) {
					dto.setSubCategoryCode(510L);
				}
				if ("THREE".equals(dto.getPhase()) && "URBAN".equals(dto.getPremiseType())) {
					dto.setSubCategoryCode(511L);
				}
				if ("THREE".equals(dto.getPhase()) && "RURAL".equals(dto.getPremiseType())) {
					dto.setSubCategoryCode(512L);
				}
//				end 01-12-2025
				dto.setIsBpl("false");
				dto.setIsEmployee("false");
				dto.setIsSeasonal("false");
				dto.setIsXray("false");
				dto.setIsWeldingTransformerSurcharge("false");
				dto.setIsCapacitorSurcharge("false");
				dto.setIsDemandside("false");
				dto.setIsiMotorType("false");
				dto.setIsBeneficiary("false");
				dto.setIsAffiliatedConsumer("false");
				dto.setIsGovernment("false");
				dto.setContractDemand(0.0);
				dto.setContractDemandUnit("KW");
				if (Objects.isNull(findByConsumerApplicationNumber.getSspTotalAmount())
						|| (findByConsumerApplicationNumber.getSspTotalAmount()).compareTo(BigDecimal.ZERO) <= 0) {
					dto.setRegistrationFeeAmount(new BigDecimal(0.0));
				} else {
					dto.setRegistrationFeeAmount(findByConsumerApplicationNumber.getSspRegistrationCharge());
				}
				dto.setiSamparkLocationCode(
						Long.parseLong(findByConsumerApplicationNumber.getDistributionCenter().getDcCode()));

				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
						.getTotalPaymentDetails(consumerAppNo);
				if (findByConsumerApplicationNo == null || findByConsumerApplicationNo.isEmpty()) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Consumer Application not found in BillDesk Payment Response");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
				for (BillDeskPaymentResponse bill : findByConsumerApplicationNo) {
					if (bill.getAdditionalInfo().equals("Demand_fees") && bill.getAuth_status().equals("0300")) {

						String tranId = bill.getTranId();
						int startIndex = Math.max(0, tranId.length() - 10);
						String lastTenChars = tranId.substring(startIndex);

						dto.setSecurityDepositAmountMrNo(lastTenChars);

					}
					if (bill.getAdditionalInfo().equals("Registration_Fees") && bill.getAuth_status().equals("0300")) {
						String tranId = bill.getTranId();
						int startIndex = Math.max(0, tranId.length() - 10);
						String lastTenChars = tranId.substring(startIndex);
						dto.setRegistrationFeeAmountMrNo(lastTenChars);
					}
				}
//				MmkyPayAmount mmkyPayAmnt = mmkyPayAmountRespository.findByConsumerApplicationNumber(consumerAppNo);

				ErpEstimateAmountData findByErpNo = estimateAmountRepository
						.findByErpNo(findByConsumerApplicationNumber.getErpWorkFlowNumber());
				if (findByErpNo == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Estimate not found in erp table");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
				dto.setSecurityDepositAmount(findByErpNo.getSecurityDeposit());

				response.setCode(ResponseCode.OK);
				response.setMessage(ResponseMessage.SUCCESS);
				response.setList(Arrays.asList(dto));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Internal Server Error");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
	}

//		Post data to SSP Portal
	@PostMapping("/saveDataToSsp")
	public ResponseEntity<?> saveDataToSsp(@Valid @RequestBody NgbStagingDto dto, BindingResult bindingResult)
			throws FormValidationException {
		Response response = new Response();

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
		}

		String connectionDate = dto.getConnectionDate();

		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		// String → LocalDate
		LocalDate connDate = LocalDate.parse(connectionDate, formatter1);
		LocalDate today = LocalDate.now();

		// Difference in days
		long daysBetween = ChronoUnit.DAYS.between(connDate, today);

		if (daysBetween > 30) {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Connection date is older than 30 days!");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> postForEntity = null;
		ConsumerApplicationDetail findByConsumerApplicationNumber = null;

		if ("TEMPORARY".equals(dto.getConnectionType())) {
			findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumberTemporary(dto.getOytTempApplicationNo());
			dto.setApplicationNumber(dto.getOytTempApplicationNo());
		} else {
			findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(dto.getApplicationNumber());
			dto.setSspApplicationNo(findByConsumerApplicationNumber.getNscApplicationNo());
		}
		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application not found");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

//		if(findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {
//			dto.setApplicationType("OYT");
//		}else {
		dto.setApplicationType("MKMY");
//		}

		LOG.info("SSP Push DATA", dto);
		System.out.println("dto : " + new Gson().toJson(dto));
		String url = postNscSsp;
		System.err.println("url : " + url);
		try {
			System.err.println(dto);

			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)
					|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
				postForEntity = restTemplate.postForEntity(url, dto, Map.class);
			} else {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("this application not OYT and MKMY");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		LOG.info("Data Returned From SSP ", postForEntity.getBody());
		System.err.println("Data Returned From SSP " + postForEntity.getBody());
		if (postForEntity != null) {
			Map<String, Object> responseBody = postForEntity.getBody();
			if (responseBody != null && responseBody.containsKey("statusCode")
					&& responseBody.get("statusCode").equals(201)) {
				NgbStagingData saveToNgbStagingData = saveToNgbStagingData(dto);
				NgbStagingData save = ngbStagingDataRepository.save(saveToNgbStagingData);

				if (save != null) {

					if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {

						if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() == 32L) {

							ApplicationStatus applicationStatus = applicationStatusService
									.findById(ApplicationStatusEnum.CONNECTION_GRANTED_SUCCESSFULLY.getId());
							findByConsumerApplicationNumber.setApplicationStatus(applicationStatus);

						} else {
							ApplicationStatus applicationStatus = applicationStatusService.findById(
									findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId());
							findByConsumerApplicationNumber.setApplicationStatus(applicationStatus);
						}

						ConsumerApplicationDetail save2 = consumerApplictionDetailRepository
								.save(findByConsumerApplicationNumber);
						response.setCode(HttpCode.OK);
						response.setMessage("Data sent to SSP Portal and saved in DSP database");
						response.setList(Arrays.asList(save2));
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					} else if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId()
							.equals(5l)) {
						if (findByConsumerApplicationNumber.getNscApplicationNo() != null) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							if ("temporary".equals(findByConsumerApplicationNumber.getOytTepOrParma())) {
								findByConsumerApplicationNumber.setTemporaryPushed("YES");
								findByConsumerApplicationNumber
										.setTemporaryPushedDate(LocalDateTime.now().format(formatter));

							} else {
								findByConsumerApplicationNumber.setSspApplicationCompleted("true");
								findByConsumerApplicationNumber
										.setSspApplicationCompleteDate(LocalDateTime.now().format(formatter));
							}
							ConsumerApplicationDetail savedOYTData = consumerApplictionDetailRepository
									.save(findByConsumerApplicationNumber);
							return ResponseEntity.ok(Objects.isNull(savedOYTData)
									? new Response(HttpCode.NULL_OBJECT, "Data Not Updated")
									: new Response(HttpCode.OK, "Data Updated Successfully"));
						}
					}

				} else {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Failed to save data in DSP database");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}
			} else if (responseBody.get("statusCode").equals(208)) {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("Already Pushed in saral sanyojan");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				response.setCode(responseBody.get("statusCode").toString());
				response.setMessage(responseBody.get("message").toString());
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		}

		response.setCode(HttpCode.NULL_OBJECT);
		response.setMessage("Failed to post data to SSP Portal");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	public NgbStagingData saveToNgbStagingData(NgbStagingDto dto) {

		NgbStagingData data = new NgbStagingData();
		data.setAadhaarNo(dto.getAadhaarNo());
		data.setAddress1(dto.getAddress1());
		data.setAddress2(dto.getAddress2());
		data.setAddress3(dto.getAddress3());
		data.setAffiliatedConsumerNo(dto.getAffiliatedConsumerNo());
		data.setApplicationNumber(dto.getApplicationNumber());
		data.setAreaStatus(dto.getAreaStatus());
		data.setCategory(dto.getCategory());

		data.setConnectionDate(dto.getConnectionDate());
		data.setConnectionType(dto.getConnectionType());
		data.setConsumerName(dto.getConsumerName());
		data.setConsumerNameH(dto.getConsumerNameH());
		data.setContractDemand(0.0);
		data.setContractDemandUnit("KW");
		data.setDateOfReg(dto.getDateOfReg());
		data.setDtrName(dto.getDtrName());
		data.setFeederName(dto.getFeederName());
		data.setGroupNo(dto.getGroupNo());
		data.setIsAffiliatedConsumer("false");
		data.setIsBeneficiary("false");
//		data.setIsBpl("false");
		data.setIsBpl(dto.getIsBpl());
		data.setIsCapacitorSurcharge("false");
		data.setIsDemandside("false");
		data.setIsEmployee("false");
		data.setIsGovernment("false");
		data.setIsiMotorType("false");
		data.setIsSeasonal("false");
		data.setIsWeldingTransformerSurcharge("false");
		data.setIsXray("false");
		data.setLocationCode(dto.getLocationCode());
		data.setMeteringStatus("UNMETERED");
		data.setPhase("THREE");
//		data.setPlotSize(5.0);
		data.setPlotSize(dto.getPlotSize());
		data.setPlotSizeUnit("HCT");
		data.setPoleDistance(dto.getPoleDistance());
		data.setPoleLatitude(dto.getPoleLatitude());
		data.setPoleLongitude(dto.getPoleLongitude());
		data.setPoleNo(dto.getPoleNo());
		data.setPortalName("DSP");
//		data.setPremiseType("RURAL");
		data.setPremiseType(dto.getPremiseType());
		data.setPrimaryMobileNo(dto.getPrimaryMobileNo());
		data.setPropertyName(dto.getPropertyName());
		data.setPropertyValue(dto.getPropertyValue());
		data.setPurposeOfInstallation(dto.getPurposeOfInstallation());
		data.setPurposeOfInstallationId(dto.getPurposeOfInstallationId());
		data.setReadingDiaryNo(dto.getReadingDiaryNo());
		data.setRegistrationFeeAmount(dto.getRegistrationFeeAmount());
		data.setRegistrationFeeAmountMrNo(dto.getRegistrationFeeAmountMrNo());
		data.setRelation("FATHER");
		data.setRelativeName(dto.getRelativeName());
		data.setSanctionedLoad(dto.getSanctionedLoad());
		data.setSanctionedLoadUnit(dto.getSanctionedLoadUnit());
		data.setSecurityDepositAmount(dto.getSecurityDepositAmount());
		data.setSecurityDepositAmountMrNo(dto.getSecurityDepositAmountMrNo());
		data.setSubCategoryCode(dto.getSubCategoryCode());
		data.setTariffCategory(dto.getTariffCategory());
		data.setTarrifCode(dto.getTarrifCode());
		data.setTcEndDate(dto.getTcEndDate());
		data.setTcStartDate(dto.getTcStartDate());

		return data;
	}

//	 Put api to update consumerNo, nscAppId, pushDate from SSP portal

	@PutMapping("/putConsumerNoFromSSP")
	public ResponseEntity<?> putConsumerNoFromSSP(@RequestBody NgbDtoForConusmerNo dto) {
		Response response = new Response();
		ConsumerApplicationDetail findByConsumerApplicationNumber = null;

		findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(dto.getApplicationNo());
		if (findByConsumerApplicationNumber == null) {
			findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumberTemporary(dto.getApplicationNo());
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		}
		NgbStagingData findByApplicationNumber = ngbStagingDataRepository
				.findByApplicationNumber(dto.getApplicationNo());
		if (findByApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application not found in NGB Staging Data Table");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} else {

			findByApplicationNumber.setNscAppId(dto.getNscAppId());
			findByApplicationNumber.setNgbPushDate(dto.getNgbPushDate());
			findByApplicationNumber.setNgbConsumerNo(dto.getNgbConsumerNo());
			NgbStagingData save = ngbStagingDataRepository.save(findByApplicationNumber);

//			added these 2 lines code to insert Ivrs no. in consumerApplicationDetail table code start 27-Jan-2025 by Monika Rajpoot
			if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() == 32L) {
				findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.CONNECTION_GRANTED_SUCCESSFULLY.getId()).get());
			}
			if ("TEMPORARY".equals(save.getConnectionType())) {
				findByConsumerApplicationNumber.setTempIvrsNo(dto.getNgbConsumerNo());
			} else {
				findByConsumerApplicationNumber.setIvrsNo(dto.getNgbConsumerNo());
			}
			consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
//			code end
			response.setCode(HttpCode.OK);
			response.setMessage("Consumer Number Inserted Successfully");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
	}

	@GetMapping("/getGroupNoByLocationCode/{locationCode}")
	public ResponseEntity<?> getGroupNoByLocationCode(@PathVariable Long locationCode) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		Long code = 512L;

//		String url = "https://survey.mpcz.in:8080/ssp-web/ngb/getGroupNo?locationCode=" + locationCode + "&code="
//				+ code;

		String url = "https://saralsanyojan.mpcz.in:8888/ngb/getGroupNo?locationCode=" + locationCode + "&code=" + code;
		System.out.println("url : " + url);
		// Prepare headers if needed
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// You might need to set a request body, in this case it's just an empty body
		HttpEntity<String> request = new HttpEntity<>(headers);
		try {
			response = restTemplate.postForEntity(url, request, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("response : " + response);
		return response;

	}

	@GetMapping("/getDiaryNoByGroupNo/{groupNo}")
	public ResponseEntity<?> getDiaryNoByGroupNo(@PathVariable String groupNo) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

//		String url = "https://survey.mpcz.in:8080/ssp-web/ngb/getReadingDiaryNo?groupNo=" + groupNo;

		String url = "https://saralsanyojan.mpcz.in:8888/ngb/getReadingDiaryNo?groupNo=" + groupNo;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(headers);

		try {
			response = restTemplate.postForEntity(url, request, String.class);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	@GetMapping("/getNgbDataByApplicationNo/{consumerAppNo}")
	public ResponseEntity<?> getNgbDataByApplicationNo(@PathVariable String consumerAppNo) {
		Response response = new Response();
		NgbStagingData findByApplicationNumber = ngbStagingDataRepository.findByApplicationNumber(consumerAppNo);
		if (findByApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Conusmer Application Number Not Found In NGB Staging Data Table");
			return ResponseEntity.ok(response);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("Application Data Retrived Successfully ");
		response.setList(Arrays.asList(findByApplicationNumber));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getOytDataForTemporaryConnection/{consumerAppNo}")
	public ResponseEntity<Response> getOytDataForTemporaryConnection(@PathVariable String consumerAppNo) {
		Response response = new Response();
		NgbDto dto = new NgbDto();

		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				Date createdDate = findByConsumerApplicationNumber.getCreated();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify the desired date format

				String formattedDate = dateFormat.format(createdDate);

				dto.setConsumerName(findByConsumerApplicationNumber.getConsumerName());
				dto.setRelativeName(findByConsumerApplicationNumber.getGuardianName());
				dto.setRelation("FATHER");
				dto.setCategory(findByConsumerApplicationNumber.getCastCategory());
				dto.setAadhaarNo(findByConsumerApplicationNumber.getAadharNo());
				dto.setAddress1(findByConsumerApplicationNumber.getAddress());
				if (findByConsumerApplicationNumber.getPincode() == null) {
					dto.setAddress2(findByConsumerApplicationNumber.getAddress());
				} else {
					dto.setAddress2(findByConsumerApplicationNumber.getPincode());
				}
				dto.setAddress3(findByConsumerApplicationNumber.getDistrict().getDistrictName());
				dto.setDateOfReg(formattedDate);
				dto.setLocationCode(
						Long.parseLong(findByConsumerApplicationNumber.getDistributionCenter().getNgbDcCd()));

				dto.setPlotSize(findByConsumerApplicationNumber.getArea());
				dto.setPlotSizeUnit("HCT");
				dto.setPortalName("DSP");
				dto.setApplicationNumber(consumerAppNo);
				dto.setPrimaryMobileNo(findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
				dto.setConnectionType("TEMPORARY");
				dto.setPurposeOfInstallation("Flat Rate Temporary");
				dto.setPurposeOfInstallationId(141L);
				dto.setSanctionedLoad(Double.parseDouble(findByConsumerApplicationNumber.getLoadRequested()));
				dto.setSanctionedLoadUnit(findByConsumerApplicationNumber.getLoadRequestedId().getLoadRequestedName());

				if (findByConsumerApplicationNumber.getPhase().equals("THREE")
						&& findByConsumerApplicationNumber.getPremiseAreaType().equals("URBAN")) {
					dto.setSubCategoryCode(517L);
				}
				if (findByConsumerApplicationNumber.getPhase().equals("THREE")
						&& findByConsumerApplicationNumber.getPremiseAreaType().equals("RURAL")) {
					dto.setSubCategoryCode(518L);
				}
				if (findByConsumerApplicationNumber.getPhase().equals("SINGLE")
						&& findByConsumerApplicationNumber.getPremiseAreaType().equals("URBAN")) {
					dto.setSubCategoryCode(519L);
				}
				if (findByConsumerApplicationNumber.getPhase().equals("SINGLE")
						&& findByConsumerApplicationNumber.getPremiseAreaType().equals("RURAL")) {
					dto.setSubCategoryCode(520L);
				}

				dto.setTariffCategory("LV5");
				dto.setTarrifCode("LV5.1BT.UM");
				dto.setPremiseType("RURAL");
				dto.setMeteringStatus("UNMETERED");
				dto.setConsumerNameH("");
				dto.setPropertyName("khasra_no");
				dto.setPropertyValue(findByConsumerApplicationNumber.getKhasra());
				dto.setAreaStatus("AUTHORISED");
				if (findByConsumerApplicationNumber.getPhase() != null) {
					dto.setPhase(findByConsumerApplicationNumber.getPhase());
				} else {
					dto.setPhase("THREE");
				}
				dto.setIsBpl("false");
				dto.setIsEmployee("false");
				dto.setIsSeasonal("false");
				dto.setIsXray("false");
				dto.setIsWeldingTransformerSurcharge("false");
				dto.setIsCapacitorSurcharge("false");
				dto.setIsDemandside("false");
				dto.setIsiMotorType("false");
				dto.setIsBeneficiary("false");
				dto.setIsAffiliatedConsumer("false");
				dto.setIsGovernment("false");
				dto.setContractDemand(0.0);
				dto.setContractDemandUnit("KW");

				dto.setiSamparkLocationCode(
						Long.parseLong(findByConsumerApplicationNumber.getDistributionCenter().getDcCode()));
				dto.setOytTempApplicationNo(findByConsumerApplicationNumber.getOytTempApplicationNo());

				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
						.getTotalPaymentDetails(consumerAppNo);
				if (findByConsumerApplicationNo == null || findByConsumerApplicationNo.isEmpty()) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Consumer Application not found in BillDesk Payment Response");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}

				findByConsumerApplicationNo.stream().filter(bill -> "Registration_Fees".equals(bill.getAdditionalInfo())
						&& "0300".equals(bill.getAuth_status())).findFirst().ifPresent(bill -> {
							String tranId = bill.getTranId();
							int startIndex = Math.max(0, tranId.length() - 10);
							String lastTenChars = tranId.substring(startIndex);
							dto.setRegistrationFeeAmount(BigDecimal.ZERO);
							dto.setRegistrationFeeAmountMrNo(lastTenChars);
							dto.setSecurityDepositAmount(new BigDecimal(bill.getOytTempAmount()));
							dto.setSecurityDepositAmountMrNo(lastTenChars);
						});

				response.setCode(ResponseCode.OK);
				response.setMessage(ResponseMessage.SUCCESS);
				response.setList(Arrays.asList(dto));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Internal Server Error");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
	}

	@PostMapping("/pdc/{ivrsNo}")
	public ResponseEntity<?> pdc(@PathVariable String ivrsNo, @RequestBody Map<String, String> map) {
		Response response = new Response();
		ResponseEntity<String> response1 = null;
		RestTemplate t = new RestTemplate();
		String url = "https://ngb.mpcz.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/master/pdc/consumer-no/"
				+ ivrsNo;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJWSUdJTElBTkNFIiwidXNlciI6eyJpZCI6MTQ5NCwidXNlcm5hbWUiOiJWSUdJTElBTkNFIiwicm9sZSI6ImFkbWluIiwibmFtZSI6IlZJR0lMSUFOQ0UiLCJkZXNpZ25hdGlvbiI6IiIsImxvY2F0aW9uQ29kZSI6IjIzMDQyMDQiLCJzdGF0dXMiOiJBQ1RJVkUiLCJtb2JpbGVObyI6IiIsIm90cE1vYmlsZU5vIjpudWxsLCJjcmVhdGVkQnkiOm51bGwsImNyZWF0ZWRPbiI6bnVsbCwidXBkYXRlZEJ5IjpudWxsLCJ1cGRhdGVkT24iOm51bGwsImlkZW50aWZpZXIiOiIiLCJ6b25lIjpudWxsfSwiaWF0IjoxNzM3OTgxOTkyLCJleHAiOjE3NjcxMTk0MDB9.WFL4-bZhnXqsXrqyXfPv_jeP8I3BxCJPKs1RlkNDRN4"); // 👈
																																																																																																																																	// JWT
																																																																																																																																	// token
		 HttpEntity<Map<String, String>> entity =
		            new HttpEntity<>(map, headers);
		
		
		 try {
			    response1 = t.exchange(url, HttpMethod.PUT, entity, String.class);

			    response.setCode(HttpCode.OK);
			    response.setMessage(response1.getBody());

			} catch (HttpClientErrorException ex) {

			    String errorBody = ex.getResponseBodyAsString();
			    String errorMessage = "Unknown error";

			    try {
			        ObjectMapper mapper = new ObjectMapper();
			        JsonNode node = mapper.readTree(errorBody);

			        if (node.has("errorMessage")) {
			            errorMessage = node.get("errorMessage").asText();
			        }

			    } catch (Exception e) {
			        e.printStackTrace();
			    }

			    response.setCode("417");
			    response.setMessage(errorMessage);
			    return ResponseEntity.ok(response);
			}

		 

		response.setCode(HttpCode.OK);
		response.setMessage(response1.getBody());
		return ResponseEntity.ok(response);
	}

}
