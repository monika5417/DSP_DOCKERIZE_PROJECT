package com.mpcz.deposit_scheme.backend.api.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.MMKYParentChild;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyCalculation;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.OytSamagraListData;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationUpdateDto;
import com.mpcz.deposit_scheme.backend.api.dto.ErpEstimateCalculatedDto;
import com.mpcz.deposit_scheme.backend.api.dto.MMKYParentChildDTO;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.MmkyCalculationException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.VendorAddMaterialException;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MMKYParentChildRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyCalculationRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.ErpEstimateResponse;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;
import com.mpcz.deposit_scheme.backend.api.services.MMKYParentChildService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

import io.swagger.annotations.Api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "MMKYParentChildController", description = "Rest api for MMKT Application Detail.")
@RestController
@RequestMapping("/api/mmky")
public class MMKYParentChildController {

	@Autowired
	MMKYParentChildService mmkyParentChildService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	ErpEstimateAmountService erpEstimateAmountService;

	@Autowired
	private MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	private SMSDirectService smsDirectService;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private MmkyCalculationRepository mmkyCalculationRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	DryUtility dryUtility;

	@PostMapping("/saveMMKYParentChild")
	public ResponseEntity<?> saveMMKYParentChild(@RequestParam("applicationNumber") String applicationNumber,
			@RequestParam("mmkyParentChildData") String mmkyParentChildData, @RequestParam("MmkyLoad") double mmkyLoad,
			@RequestParam("KvDistance") Long KvDistance, @RequestParam("dtr") Long dtr,
			@RequestParam("cutPoint") String cutPoint) throws MmkyCalculationException {
		Response response = new Response();

		if (applicationNumber == null || applicationNumber.equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill Consumer Application Number");
			throw new MmkyCalculationException(response);

		}

		if (mmkyParentChildData == null || mmkyParentChildData.equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill MMKY Parent Child Data");
			throw new MmkyCalculationException(response);

		}

		if (KvDistance == null) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill 11 KV Distance");
			throw new MmkyCalculationException(response);

		}

		if (dtr == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill DTR Capacity");
			throw new MmkyCalculationException(response);
		}

		return mmkyParentChildService.submit(applicationNumber, mmkyParentChildData, mmkyLoad, KvDistance, dtr,
				cutPoint);
	}

	@GetMapping("/erpMkmypApi/{erpNumber}/{applicationNumber}")
	public ResponseEntity<ErpEstimateResponse> getMkmyAmountByErp(@PathVariable("erpNumber") long erpNumber,
			@PathVariable("applicationNumber") String applicationNumber, HttpServletRequest request) throws Exception {

		System.out.println("value of id is :- " + erpNumber);

		final String method = RestApiUrl.URJAS_PORTAL_API + RestApiUrl.GET_ERP_ESTIMATE_AMOUNT_URL
				+ " : retrieveErpEstimate()";

		RestTemplate restTemplate = new RestTemplate();
		ErpEstimateResponse response = new ErpEstimateResponse();

		String url = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNumber;

		URI uri = new URI(url);

		HttpHeaders headers = new HttpHeaders();

		headers.set("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		headers.set("Content-Type", "application/json");

		String token = null;

		token = request.getHeader("Authorization");
		headers.set("Authorization", token);

		ConsumerApplicationDetail consumerapplication = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(applicationNumber);

		String s = String.valueOf(erpNumber);
		System.out.println("consumerapplication--->" + consumerapplication);
		consumerapplication.setErpWorkFlowNumber(s);

//		check added for same erp no. application
//		Optional<MmkyPayAmount> mmkyPayAmountDB = mmkyPayAmountRespository.findByErpNumber(s);
//		if (mmkyPayAmountDB.isPresent()) {
//			List<ConsumerApplicationDetail> findByErpNo = consumerApplictionDetailRepository.findByErpNo(s);
//
//			List<String> applicationNumbers = new ArrayList<>();
//
//			for (ConsumerApplicationDetail detail : findByErpNo) {
//
//				System.out.println("ConsumerApplication no: " + detail.getConsumerphonNumber());
//				applicationNumbers.add(detail.getConsumerApplicationNo());
//			}
//
//			response.setCode(HttpCode.NOT_ACCEPTABLE);
//			response.setMessage("This ERP Number Is Already Associated With Another Application Number.");
//			response.setList(applicationNumbers);
//			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//
//		}

//		code start 3 April 2025 with updated check for erp no. ki same erp no. kisi bhi application se tagged na ho 
		ResponseEntity<Response> checkErpExistOrNot = dryUtility.checkErpExistOrNot(Long.toString(erpNumber));
		if (checkErpExistOrNot != null && checkErpExistOrNot.getBody() != null) {

			Response responseBody = checkErpExistOrNot.getBody(); // Extract response body

			if ("This ERP Number Is Already Associated With Another Application Number."
					.equals(responseBody.getMessage())) {
				ErpEstimateResponse erpResponse = new ErpEstimateResponse();
				erpResponse.setCode(responseBody.getCode());
				erpResponse.setMessage(responseBody.getMessage());
				erpResponse.setList(responseBody.getList()); // Assuming both have list field

				return ResponseEntity.status(checkErpExistOrNot.getStatusCode()) // Preserve original status code
						.headers(checkErpExistOrNot.getHeaders()) // Preserve headers
						.body(erpResponse);
			}
		}
//		code end

		HttpStatus status = null;
		Integer statusCode = null;

		HttpEntity<ErpEstimateAmountData> entity = new HttpEntity<>(headers);

		ResponseEntity<String> result = null;

		MmkyPayAmount MmkyPayAmount = new MmkyPayAmount();
		List<ErpEstimateAmountData> erpEstimateList = new ArrayList<ErpEstimateAmountData>();

		ErpEstimateAmountData erpEstimateDataResponse = null;

		BigDecimal sgst = null;
		BigDecimal cgst = null;
		BigDecimal jeLoadAmnt = BigDecimal.ZERO;
		String schemaCode = null;

		Response<ErpEstimateAmountData> returnResult = new Response();
		try {

			result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

			JSONObject jsonObject = new JSONObject(result.getBody());

			System.out.println(
					"jsonObject.get(\"statusCode\")---->" + jsonObject.get("statusCode").toString().equals("200"));
			MmkyPayAmount.setConsumerApplicationNumber(applicationNumber);
			if (jsonObject.get("statusCode").toString().equals("200")) {

				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
							&& !jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("KMY")) {
						response.setCode("500");
						response.setMessage("Scheme code not match");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}

					if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null) {
						MmkyPayAmount.setErpNumber(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
					}
					if (jsonArray.getJSONObject(i).getString("ESTIMATED_COST") != null) {

						MmkyPayAmount.setTotalAmount(roundAmountUperSide(
								new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))));
					}
					if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
							&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {
						MmkyPayAmount.setSchemeCode(jsonArray.getJSONObject(i).getString("SCHEMECODE"));
					}
					if (jsonArray.getJSONObject(i).getString("ESTIMATE_NO") != null) {
						MmkyPayAmount.setEstSanctionNo(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));
					}
					if (jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME") != null) {
						MmkyPayAmount.setEstApprovedBy(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME"));

					}
					if (jsonArray.getJSONObject(i).getString("LONG_NAME") != null) {
						MmkyPayAmount.setEstName(jsonArray.getJSONObject(i).getString("LONG_NAME"));

					}
					MmkyPayAmount.setCarryAmountByApplicant(
							roundAmountUperSide(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
									.multiply(new BigDecimal(50)).divide(new BigDecimal(100))));
					MmkyPayAmount.setGovMafBill(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
							.multiply(new BigDecimal(40)).divide(new BigDecimal(100)));

					MmkyPayAmount.setMpmkMafBill(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
							.multiply(new BigDecimal(10)).divide(new BigDecimal(100)));

					MmkyPayAmount.setAvedanShulk(new BigDecimal(2495));
					MmkyPayAmount.setAvedanShulkFiveRupee(new BigDecimal(5));

//					MmkyPayAmount.setSecurityDeposit(
//							round11(new BigDecimal(consumerapplication.getJeLoad())).multiply(new BigDecimal(600)));
//
//					MmkyPayAmount.setPayableAmount(
//							roundAmountUperSide(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
//									.multiply(new BigDecimal(50).divide(new BigDecimal(100)))
//									.add(round11(new BigDecimal(consumerapplication.getJeLoad()))
//											.multiply(new BigDecimal(600)))
//									.add(new BigDecimal(2500))));
//					code start		7 - April -2025 if je load is less than or equal to 10 than 200 per hp and above 10 so 400 pr hp 
					double jeLoad = Double.parseDouble(consumerapplication.getJeLoad());
					if (jeLoad <= 10) {
						jeLoadAmnt = round11(new BigDecimal(consumerapplication.getJeLoad()))
								.multiply(new BigDecimal(200));

					} else {
						jeLoadAmnt = round11(new BigDecimal(consumerapplication.getJeLoad()))
								.multiply(new BigDecimal(400));

					}
					MmkyPayAmount.setSecurityDeposit(jeLoadAmnt);
					MmkyPayAmount.setPayableAmount(
							roundAmountUperSide(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
									.multiply(new BigDecimal(50).divide(new BigDecimal(100))).add(jeLoadAmnt)
									.add(new BigDecimal(2500))));

//					code end
					MmkyPayAmount.setMsgSend("Unsend");

					Date currentDate = new Date();

					// Format the date using SimpleDateFormat (optional)
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = formatter.format(currentDate);

					// Print the current date
					System.out.println("Current Date: " + formattedDate);

					MmkyPayAmount.setCreated(formattedDate);
					MmkyPayAmount.setActive(true);
					MmkyPayAmount.setDeleted(false);
				}

			} else {

				String uri1 = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNumber;

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
						if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
								&& !jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("KMY")) {
							response.setCode("500");
							response.setMessage("Scheme code not match");
							return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
						}

						if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null) {
							MmkyPayAmount.setErpNumber(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
						}
						if (jsonArray.getJSONObject(i).getString("ESTIMATED_COST") != null) {

							MmkyPayAmount.setTotalAmount(roundAmountUperSide(
									new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))));
						}
						if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
								&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {
							MmkyPayAmount.setSchemeCode(jsonArray.getJSONObject(i).getString("SCHEMECODE"));
						}
						if (jsonArray.getJSONObject(i).getString("ESTIMATE_NO") != null) {
							MmkyPayAmount.setEstSanctionNo(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));
						}
						if (jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME") != null) {
							MmkyPayAmount.setEstApprovedBy(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME"));

						}
						if (jsonArray.getJSONObject(i).getString("LONG_NAME") != null) {
							MmkyPayAmount.setEstName(jsonArray.getJSONObject(i).getString("LONG_NAME"));

						}

						MmkyPayAmount.setCarryAmountByApplicant(roundAmountUperSide(
								new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
										.multiply(new BigDecimal(50)).divide(new BigDecimal(100))));
						MmkyPayAmount
								.setGovMafBill(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
										.multiply(new BigDecimal(40)).divide(new BigDecimal(100)));

						MmkyPayAmount
								.setMpmkMafBill(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
										.multiply(new BigDecimal(10)).divide(new BigDecimal(100)));

						MmkyPayAmount.setAvedanShulk(new BigDecimal(2495));
						MmkyPayAmount.setAvedanShulkFiveRupee(new BigDecimal(5));

						MmkyPayAmount.setMsgSend("Unsend");

						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date currentDate = new Date();
						String formattedDate = formatter.format(currentDate);

						// Print the current date
						System.out.println("Current Date: " + formattedDate);

						MmkyPayAmount.setCreated(formattedDate);
						MmkyPayAmount.setActive(true);
						MmkyPayAmount.setDeleted(false);

						if (consumerapplication.getJeLoad() != null) {
//							MmkyPayAmount.setSecurityDeposit(round11(new BigDecimal(consumerapplication.getJeLoad()))
//									.multiply(new BigDecimal(600)));

							double jeLoad = Double.parseDouble(consumerapplication.getJeLoad());
							if (jeLoad <= 10) {
								jeLoadAmnt = round11(new BigDecimal(consumerapplication.getJeLoad()))
										.multiply(new BigDecimal(200));

							} else {
								jeLoadAmnt = round11(new BigDecimal(consumerapplication.getJeLoad()))
										.multiply(new BigDecimal(400));

							}
							MmkyPayAmount.setSecurityDeposit(jeLoadAmnt);

						} else {
							response.setCode("500");
							response.setMessage("please fill je load after enter erp number");
							return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
						}
//						MmkyPayAmount
//								.setPayableAmount(
//										roundAmountUperSide(
//												new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
//														.multiply(new BigDecimal(50).divide(new BigDecimal(100)))
//														.add(round11(new BigDecimal(consumerapplication.getJeLoad()))
//																.multiply(new BigDecimal(600)))
//														.add(new BigDecimal(2500))));
						MmkyPayAmount.setPayableAmount(roundAmountUperSide(
								new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST"))
										.multiply(new BigDecimal(50).divide(new BigDecimal(100))).add(jeLoadAmnt)
										.add(new BigDecimal(2500))));

					}

				}
			}
		} catch (Exception e) {
			System.out.println("exception block------------------>" + e);
			response.setCode("500");
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		if ((consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 8
//				&& schemaCode.equalsIgnoreCase("MKY")
		)) {

			ConsumerApplicationDetail saveConsumerApplication = consumerApplicationDetailService
					.saveConsumerApplication(consumerapplication);
			MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
					.findByConsumerApplicationNumber(applicationNumber);
// start added this check for existing application entry at this stage 21-jan-2025
			if (findByConsumerApplicationNumber != null) {
				deleteMkyPayAmountErpData(applicationNumber);
			}
// end
			if (saveConsumerApplication.getIndividualOrGroup().getIndividualOrGroupId() == 1L) {
				if (saveConsumerApplication.getMkmyDtrCapacity().equals("25")) {
					if (MmkyPayAmount.getPayableAmount().compareTo(new BigDecimal(195972)) > 0) {
						response.setCode("307");
						response.setMessage("Amount more than the sanction estimate amount 195972 for for 25 DTR");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}

				} else if (saveConsumerApplication.getMkmyDtrCapacity().equals("63")) {
					if (MmkyPayAmount.getPayableAmount().compareTo(new BigDecimal(337173)) > 0) {
						response.setCode("307");
						response.setMessage("Amount more than the sanction estimate amount 337173 for 63 DTR");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}
				}
			} else if (saveConsumerApplication.getIndividualOrGroup().getIndividualOrGroupId() == 2L) {
				MmkyCalculation findByParentApplicationNo = mmkyCalculationRepository
						.findByParentApplicationNo(applicationNumber);
				if (findByParentApplicationNo.getDtrCapacity() == 25) {
					if (MmkyPayAmount.getPayableAmount().compareTo(new BigDecimal(195972)) > 0) {
						response.setCode("307");
						response.setMessage("Amount more than the sanction estimate amount 195972 for for 25 DTR");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}

				} else if (findByParentApplicationNo.getDtrCapacity() == 63) {
					if (MmkyPayAmount.getPayableAmount().compareTo(new BigDecimal(337173)) > 0) {
						response.setCode("307");
						response.setMessage("Amount more than the sanction estimate amount 337173 for 63 DTR");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}
				}
			}

			mmkyPayAmountRespository.save(MmkyPayAmount);

			final SMSRequest smsRequest = new SMSRequest();

			smsRequest.setText(MessageFormat.format(messageProperties.getMkmyPaymentUnderThirty(),
					new Object[] { consumerapplication.getConsumerApplicationNo() }));
			smsRequest.setMobileNo(consumerapplication.getConsumers().getConsumerLoginId());
			smsRequest.setTemplateId(messageProperties.getMkmyPaymenttemplateId());
//				code added by monika on 30-August-2024
			smsRequest.setHinglish(1L);
//				code end

			try {
				smsDirectService.sendMessage(smsRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}

			response.setList(Arrays.asList(MmkyPayAmount));
			response.setCode(HttpCode.OK);
			response.setMessage("Record Retrieve Successfully");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} else {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Please enter a valid ERP number");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@GetMapping("/getMkmyAmountByConsumerApplicationNo/{consumerAppNo}")
	public ResponseEntity<Response> getMkmyAmountByConsumerApplicationNo(
			@PathVariable("consumerAppNo") String consumerAppNo) throws ConsumerApplicationDetailException {
		Response response = new Response();

		MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
				.findByConsumerApplicationNumber(consumerAppNo);
		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Consumer Application No. Not found!!!!");
			throw new ConsumerApplicationDetailException(response);
		}
		response.setCode("200");
		response.setMessage("Application Saved successfully");
		response.setList(Arrays.asList(findByConsumerApplicationNumber));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	public static BigDecimal round11(BigDecimal value) {

		BigDecimal bd = value;
		bd = bd.setScale(0, RoundingMode.CEILING);
		return bd;
	}

	// Monika code start

//	 @GetMapping("/deActiveAppAfter30Days")
//	@Scheduled(fixedRate = 86400000)
//	public void deActiveAppAfter30Days() {
//		Response<Object> response = new Response<Object>();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//		String s = formatter.format(date);
//
//		System.out.println(s);
//
//		List<MmkyPayAmount> findAll = mmkyPayAmountRespository.findAll();
//		for (MmkyPayAmount M : findAll) {
//
//			LocalDate dbDate = LocalDate.parse(M.getCreated());
//
//			LocalDate currentDate1 = dbDate.plusDays(30);
//			LocalDate date2 = LocalDate.parse(s);
//
//			if (currentDate1.compareTo(date2) < 0) {
//				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
//						.findByConsumerApplicationNo(M.getConsumerApplicationNumber());
//				if (findByConsumerApplicationNo.isEmpty()) {
//					M.setActive(false);
//					M.setDeleted(true);
//					System.out.println(M.getConsumerApplicationNumber());
//					ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
//							.findByConsumerApplicationNumber(M.getConsumerApplicationNumber());
//					if (findByConsumerApplicationNumber != null) {
//						findByConsumerApplicationNumber.setActive(false);
//						findByConsumerApplicationNumber.setDeleted(true);
//						ApplicationStatus applicationStatus = applicationStatusService.findById(
//								ApplicationStatusEnum.APPLICATION_REJECTED_DEMAND_NOTE_PAYMENT_NOT_DONE_WITHIN_30_DAYS
//										.getId());
//						findByConsumerApplicationNumber.setApplicationStatus(applicationStatus);
//						consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
//					}
//				}
//
//				mmkyPayAmountRespository.save(M);
//			}
//		}
//
//	}

//	@GetMapping("/mkmyMsgAfter25Days")
////	@Scheduled(fixedRate = 86400000)
//	public void mkmyMsgAfter25Days() {
//		Response<Object> response = new Response<Object>();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//		String s = formatter.format(date);
//
//		System.out.println(s);
//
//		List<MmkyPayAmount> findAll = mmkyPayAmountRespository.findAllUnsendMsg();
//		for (MmkyPayAmount M : findAll) {
//
//			ConsumerApplicationDetail consumerapplication = consumerApplictionDetailRepository
//					.findByConsumerApplicationNumber(M.getConsumerApplicationNumber());
//
//			LocalDate dbDate = LocalDate.parse(M.getCreated());
//
//			LocalDate currentDate1 = dbDate.plusDays(25);
//			LocalDate date2 = LocalDate.parse(s);
//
//			if (currentDate1.compareTo(date2) == 0) {
//				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
//						.findByConsumerApplicationNo(M.getConsumerApplicationNumber());
//
//				System.out.println("consumer application" + M.getConsumerApplicationNumber());
//				System.out.println("consumer demand date" + currentDate1);
//				if (findByConsumerApplicationNo.isEmpty()) {
//					final SMSRequest smsRequest = new SMSRequest();
//
//					System.out.println("Application_no" + M.getConsumerApplicationNumber());
//					System.out.println("Mobile_no" + consumerapplication.getConsumers().getConsumerLoginId());
//					smsRequest.setText(MessageFormat.format(messageProperties.getMkmyPaymentRemainFiveDay(),
//							new Object[] { M.getConsumerApplicationNumber() }));
//					smsRequest.setMobileNo(consumerapplication.getConsumers().getConsumerLoginId());
//					smsRequest.setTemplateId(messageProperties.getMkmyPaymentRemainTempId());
////				code added by monika on 30-August-2024
//					smsRequest.setHinglish(1L);
////				code end
//
//					try {
//						String sendMessage = smsDirectService.sendMessage(smsRequest);
//						if (!sendMessage.equalsIgnoreCase(null)) {
//							M.setMsgSend("Send");
//							mmkyPayAmountRespository.save(M);
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//
//	}

	// Monika code end
//	 public static BigDecimal roundAmountUperSide(BigDecimal amount) {
//			BigDecimal roundedAmount = amount.setScale(0, RoundingMode.FLOOR); // Get the integer part
//
//			BigDecimal remainingPaise = amount.subtract(roundedAmount); // Get the decimal part (paise)
//
//			if (remainingPaise.compareTo(new BigDecimal(0.5)) >= 0) {
//				roundedAmount = roundedAmount.add(BigDecimal.ONE); // Round up if paise is 50 or more
//			}
//			return roundedAmount;
//		}

	public static BigDecimal roundAmountUperSide(BigDecimal amount) {
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

//	 for new sms sending testing

	@GetMapping("/sendSms")
	public ResponseEntity<?> sendSms() {
		final SMSRequest smsRequest = new SMSRequest();

		String application = "SV32948982349";
//			System.out.println("Application_no" +M.getConsumerApplicationNumber());
//			System.out.println("Mobile_no" +consumerapplication.getConsumers().getConsumerLoginId());

//		 For hindi message
//			smsRequest.setText(MessageFormat.format(messageProperties.getMkmyPaymentRemainFiveDay(),
//					new Object[] { application}));
//			smsRequest.setMobileNo("8770672335");
//			smsRequest.setTemplateId(messageProperties.getMkmyPaymentRemainTempId());
//			smsRequest.setHinglish(1L);			

//			For English Message
		smsRequest.setText(MessageFormat.format(messageProperties.getMessageServeyouAccepetByDc(),
				new Object[] { application, application }));
		smsRequest.setMobileNo("8770672335");
		smsRequest.setTemplateId(messageProperties.getMessageServeyorAccepetByDcTemplatedId());

		try {
			String sendMessage = smsDirectService.sendMessage(smsRequest);
			System.out.println("sendMessage  : " + sendMessage);
//				if (!sendMessage.equalsIgnoreCase(null)) {
//					M.setMsgSend("Send");
//					mmkyPayAmountRespository.save(M);
//				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	public void deleteMkyPayAmountErpData(String consumerApplicationNo) {
		mmkyPayAmountRespository.deleteByConsumerApplicationNo(consumerApplicationNo);
	}

}
