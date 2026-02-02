package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorCategoryData;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBid;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorsDetails;
import com.mpcz.deposit_scheme.backend.api.domain.DynamicQuery;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.dto.ContractorDetailsDto;
import com.mpcz.deposit_scheme.backend.api.dto.ContractorParticipateAndNotPartiDto;
import com.mpcz.deposit_scheme.backend.api.dto.ParticipantAndNotParticipantDto;
import com.mpcz.deposit_scheme.backend.api.dto.QcResponseDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByApplicationIdException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.VendorException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorCategoryDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForQcRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorsDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DynamicQueryRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.ValidationRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorWorkOrderRepository;
import com.mpcz.deposit_scheme.backend.api.request.ContractorForBidForm;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.request.VendorRejectionForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.VendorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "ContractorDetailForBidController", description = "Rest api for ContractorDetailForBidController.")
@RestController
@RequestMapping(RestApiUrl.CONTRACTOR_DETAIL_API)
public class ContractorDetailForBidController {

	private static final Logger logger = LoggerFactory.getLogger(ContractorDetailForBidController.class);

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	VendorService vendorService;

	@Autowired
	ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	ContractorForQcRepository contractorForQcRepository;

	@Autowired
	ContractorForBidRepository contractorForBidRepository;

	@Autowired
	ContractorsDetailsRepository contractorsDetailsRepository;

	@Autowired
	EstimateAmountRepository estimateAmountRepository;

	@Autowired
	MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	VendorWorkOrderRepository vendorWorkOrderRepository;

	@Autowired
	private ValidationRepository validationRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Value("${ravindra.senddata.for.contractor.selection}")
	private String RavindraSendDataForContractorSelection;

	@Value("${ravindra.getfiledetails.send.to.samshad}")
	private String ravindraGetFileDetailsSendToSamshad;

	@Value("${ravindra.api.for.contractor.selection}")
	private String ravindraApiForContractorSelection;

	@Autowired
	private ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private ConsumerApplictionDetailRepository ConsumerApplictionDetailRepository;

	@Autowired
	private ContractorCategoryDataRepository contractorCategoryDataRepository;

	@GetMapping("/getQcConsumerbid/{consumerAppNo}")
	// ParticipantAndNotParticipantDto getQcConsumerbid() throws Exception {
	ResponseEntity<Response<?>> getQcConsumerbid(@PathVariable String consumerAppNo) throws Exception {
		List<ContractorParticipateAndNotPartiDto> listOfContractorParticipateAndNotPartiDto = new ArrayList<>();
		List<ContractorParticipateAndNotPartiDto> listOfNotContractorParticipateAndNotPartiDto = new ArrayList<>();

		ContractorParticipateAndNotPartiDto contractorParticipateAndNotPartiDto = new ContractorParticipateAndNotPartiDto();
		ParticipantAndNotParticipantDto participantAndNotParticipantDto = new ParticipantAndNotParticipantDto();

		Response<ParticipantAndNotParticipantDto> response = new Response<>();

		String consumerAppliNo = null;
		String consumerAppliId = null;

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");

			// ********************* based on properties file
			// *******************************//
			String url = ravindraApiForContractorSelection;

			HttpEntity<String> entity = new HttpEntity<>(url, headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			System.out.println("url--->" + url);
			if (responseEntity != null) {
				String forObject = responseEntity.getBody();
				System.out.println("forObject-------------" + forObject);
				if (forObject != null && forObject.length() > 0) {
					JSONObject job = new JSONObject(forObject);
					if (job.getBoolean("status")) {

						JSONArray jsonArray = job.getJSONArray("participated_contractors");

						for (int i = 0; i < jsonArray.length(); i++) {
							String consumerAppDetailForBid = jsonArray.getJSONObject(i).getJSONObject("consumers")
									.getString("consumerApplicationNo");
							if (consumerAppDetailForBid.equals(consumerAppNo)) {

								ContractorParticipateAndNotPartiDto ContractorParticipateAndNotPartiDto = new ContractorParticipateAndNotPartiDto();
								ContractorParticipateAndNotPartiDto.setAuthenticationId(jsonArray.getJSONObject(i)
										.getJSONObject("User_Id").getString("Authentication_id"));
								ContractorParticipateAndNotPartiDto.setAuthorisedPersonE(jsonArray.getJSONObject(i)
										.getJSONObject("User_Id").getString("Authorised_person_E"));

								double double1 = jsonArray.getJSONObject(i).getDouble("bid_amount");
								String valueOf = String.valueOf(double1);

								if (valueOf != null)
									ContractorParticipateAndNotPartiDto
											.setBigAmount(jsonArray.getJSONObject(i).getDouble("bid_amount"));
								ContractorParticipateAndNotPartiDto
										.setBigOrderAt(jsonArray.getJSONObject(i).getString("bid_order_at"));
//								ContractorParticipateAndNotPartiDto.setCompanyAdd1(jsonArray.getJSONObject(i)
//										.getJSONObject("User_Id").getJSONArray("user_company_data").getJSONObject(0)
//										.getString("Company_add_1"));
//								ContractorParticipateAndNotPartiDto.setCompanyAdd2(jsonArray.getJSONObject(i)
//										.getJSONObject("User_Id").getJSONArray("user_company_data").getJSONObject(0)
//										.getString("Company_add_2"));
//								ContractorParticipateAndNotPartiDto.setCompanyCity(jsonArray.getJSONObject(i)
//										.getJSONObject("User_Id").getJSONArray("user_company_data").getJSONObject(0)
//										.getString("Company_city"));
//								ContractorParticipateAndNotPartiDto.setCompanyDist(jsonArray.getJSONObject(i)
//										.getJSONObject("User_Id").getJSONArray("user_company_data").getJSONObject(0)
//										.getString("Company_dist"));
//								ContractorParticipateAndNotPartiDto.setCompanyId(jsonArray.getJSONObject(i)
//										.getJSONObject("User_Id").getJSONArray("user_company_data").getJSONObject(0)
//										.getLong("Company_Id"));
								ContractorParticipateAndNotPartiDto.setCompanyNameE(
										jsonArray.getJSONObject(i).getJSONObject("User_Id").getString("CompanyName_E"));
//								ContractorParticipateAndNotPartiDto.setCompanyPinCode(jsonArray.getJSONObject(i)
//										.getJSONObject("User_Id").getJSONArray("user_company_data").getJSONObject(0)
//										.getLong("Company_pin_code"));
//								ContractorParticipateAndNotPartiDto.setCompanyState(jsonArray.getJSONObject(i)
//										.getJSONObject("User_Id").getJSONArray("user_company_data").getJSONObject(0)
//										.getString("Company_state"));
								consumerAppliNo = jsonArray.getJSONObject(i).getJSONObject("consumers")
										.getString("consumerApplicationNo");
								ContractorParticipateAndNotPartiDto.setConsumerApplicationNo(jsonArray.getJSONObject(i)
										.getJSONObject("consumers").getString("consumerApplicationNo"));

								consumerAppliId = jsonArray.getJSONObject(i).getJSONObject("consumers").getString("id");

								ContractorParticipateAndNotPartiDto.setConsumerId(
										jsonArray.getJSONObject(i).getJSONObject("consumers").getString("id"));
								ContractorParticipateAndNotPartiDto.setContractorCategory(
										jsonArray.getJSONObject(i).getString("contractor_category"));
								ContractorParticipateAndNotPartiDto.setUserId(
										jsonArray.getJSONObject(i).getJSONObject("User_Id").getLong("User_Id"));
								ContractorParticipateAndNotPartiDto.setUserType(
										jsonArray.getJSONObject(i).getJSONObject("User_Id").getString("User_type"));
								ContractorParticipateAndNotPartiDto.setIsParticipated(true);
								listOfContractorParticipateAndNotPartiDto.add(ContractorParticipateAndNotPartiDto);
							}

						}

						// saveAllParticipated =
						// iContractorParticipateAndNotPartiDto.saveAll(listOfContractorParticipateAndNotPartiDto);

						JSONArray notParticipantArray = job.getJSONArray("not_participated_contractors");

						for (int i = 0; i < notParticipantArray.length(); i++) {
							ContractorParticipateAndNotPartiDto notContractorParticipateAndNotPartiDto = new ContractorParticipateAndNotPartiDto();
							notContractorParticipateAndNotPartiDto.setAuthenticationId(notParticipantArray
									.getJSONObject(i).getJSONObject("user_id_id").getString("Authentication_id"));
							notContractorParticipateAndNotPartiDto.setAuthorisedPersonE(notParticipantArray
									.getJSONObject(i).getJSONObject("user_id_id").getString("Authorised_person_E"));

							if (notParticipantArray.getJSONObject(i).getJSONObject("user_id_id").getString("Oyt")
									.equals("8")) {

								notContractorParticipateAndNotPartiDto.setOyt("A5");

							} else if (notParticipantArray.getJSONObject(i).getJSONObject("user_id_id").getString("Oyt")
									.equals("7")) {
								notContractorParticipateAndNotPartiDto.setOyt("A4");
							} else if (notParticipantArray.getJSONObject(i).getJSONObject("user_id_id").getString("Oyt")
									.equals("6")) {
								notContractorParticipateAndNotPartiDto.setOyt("A3");
							} else if (notParticipantArray.getJSONObject(i).getJSONObject("user_id_id").getString("Oyt")
									.equals("5")) {
								notContractorParticipateAndNotPartiDto.setOyt("A2");
							} else if (notParticipantArray.getJSONObject(i).getJSONObject("user_id_id").getString("Oyt")
									.equals("3")) {
								notContractorParticipateAndNotPartiDto.setOyt("A1");
							} else if (notParticipantArray.getJSONObject(i).getJSONObject("user_id_id").getString("Oyt")
									.equals("1")) {
								notContractorParticipateAndNotPartiDto.setOyt("B");
							}

//							notContractorParticipateAndNotPartiDto
//									.setCompanyAdd1(notParticipantArray.getJSONObject(i).getString("Company_add_1"));
//							notContractorParticipateAndNotPartiDto
//									.setCompanyAdd2(notParticipantArray.getJSONObject(i).getString("Company_add_2"));
//							notContractorParticipateAndNotPartiDto
//									.setCompanyCity(notParticipantArray.getJSONObject(i).getString("Company_city"));
//							notContractorParticipateAndNotPartiDto
//									.setCompanyDist(notParticipantArray.getJSONObject(i).getString("Company_dist"));
//							notContractorParticipateAndNotPartiDto
//									.setCompanyId(notParticipantArray.getJSONObject(i).getLong("Company_Id"));
							notContractorParticipateAndNotPartiDto.setCompanyNameE(notParticipantArray.getJSONObject(i)
									.getJSONObject("user_id_id").getString("CompanyName_E"));
//							notContractorParticipateAndNotPartiDto.setCompanyPinCode(
//									notParticipantArray.getJSONObject(i).getLong("Company_pin_code"));

							System.out.println("askldflas" + notParticipantArray.getJSONObject(i)
									.getJSONObject("user_id_id").getString("contractor_category"));
//							add this line
							notContractorParticipateAndNotPartiDto.setContractorCategory(notParticipantArray
									.getJSONObject(i).getJSONObject("user_id_id").getString("contractor_category"));

							notContractorParticipateAndNotPartiDto
									.setCompanyAdd1(notParticipantArray.getJSONObject(i).getString("Company_add_1"));
//							notContractorParticipateAndNotPartiDto
//									.setCompanyState(notParticipantArray.getJSONObject(i).getString("Company_state"));
							notContractorParticipateAndNotPartiDto.setUserId(notParticipantArray.getJSONObject(i)
									.getJSONObject("user_id_id").getLong("User_Id"));
							notContractorParticipateAndNotPartiDto.setUserType(notParticipantArray.getJSONObject(i)
									.getJSONObject("user_id_id").getString("User_type"));
							notContractorParticipateAndNotPartiDto.setIsParticipated(false);
							notContractorParticipateAndNotPartiDto.setConsumerId(consumerAppliId);
							notContractorParticipateAndNotPartiDto.setConsumerApplicationNo(consumerAppliNo);

							listOfNotContractorParticipateAndNotPartiDto.add(notContractorParticipateAndNotPartiDto);
						}
						// saveAllNotParticipated =
						// iContractorParticipateAndNotPartiDto.saveAll(listOfNotContractorParticipateAndNotPartiDto);

					} else {
						// your status is false
					}

				}

				participantAndNotParticipantDto
						.setListOfParticipatedContractors(listOfContractorParticipateAndNotPartiDto);
				participantAndNotParticipantDto
						.setListOfNotParticipatedContractors(listOfNotContractorParticipateAndNotPartiDto);
				List<ContractorParticipateAndNotPartiDto> mergeList = new ArrayList<ContractorParticipateAndNotPartiDto>();
				mergeList.addAll(listOfContractorParticipateAndNotPartiDto);
				mergeList.addAll(listOfNotContractorParticipateAndNotPartiDto);

				ConsumerApplicationDetail findByConsumerApplicationNumber = ConsumerApplictionDetailRepository
						.findByConsumerApplicationNumber(consumerAppNo);
				List<Map<String, ?>> circleDataByApplicationNO = ConsumerApplictionDetailRepository
						.getCircleDataByApplicationNO(findByConsumerApplicationNumber.getDistributionCenter().getDcId(),
								consumerAppNo);
				Object object = circleDataByApplicationNO.get(0).get("circle_id");
				Long valueOf = Optional.ofNullable(object).map(val -> {
					if (val instanceof BigDecimal) {
						return ((BigDecimal) val).longValue();
					} else if (val instanceof Number) {
						return ((Number) val).longValue();
					} else if (val instanceof String) {
						return Long.parseLong((String) val);
					} else {
						return null;
					}
				}).orElse(null);
				System.out.println(valueOf + " valueOf");
				participantAndNotParticipantDto.setListOfParticipantedAndNotParticipated(mergeList);
				if (!findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {
//					if (valueOf == 3 || valueOf == 10) {

						ErpEstimateAmountData findByErpNo = estimateAmountRepository
								.findByErpNo(findByConsumerApplicationNumber.getErpWorkFlowNumber());

//			
						if (findByErpNo != null) {
							if (findByErpNo.getSortContratoreListByKvLine() != null) {
								BigDecimal payableAmount = findByErpNo.getEstimateAmount();
//					BigDecimal payableAmount = new BigDecimal(999999);

								ContractorCategoryData findByDgmSelectedPreference = null;
								if (findByErpNo.getSortContratoreListByKvLine() == 1l) {
									findByDgmSelectedPreference = contractorCategoryDataRepository.findCategoryByRange(
											findByErpNo.getSortContratoreListByKvLine(), payableAmount);
								}
								if (findByErpNo.getSortContratoreListByKvLine() == 2l
										|| findByErpNo.getSortContratoreListByKvLine() == 3l
										|| findByErpNo.getSortContratoreListByKvLine() == 4l) {
									findByDgmSelectedPreference = contractorCategoryDataRepository
											.findByDgmSelectedPreference(findByErpNo.getSortContratoreListByKvLine());
								}
								
								System.out.println(findByDgmSelectedPreference + " findByDgmSelectedPreference");

								// पहले सिर्फ non-null categories collect करो
								List<String> categories = new ArrayList<>();
								if (findByDgmSelectedPreference.getCategoryA1() != null
										&& !findByDgmSelectedPreference.getCategoryA1().trim().isEmpty()) {
									categories.add(findByDgmSelectedPreference.getCategoryA1());
								}
								if (findByDgmSelectedPreference.getCategoryA2() != null
										&& !findByDgmSelectedPreference.getCategoryA2().trim().isEmpty()) {
									categories.add(findByDgmSelectedPreference.getCategoryA2());
								}
								if (findByDgmSelectedPreference.getCategoryA3() != null
										&& !findByDgmSelectedPreference.getCategoryA3().trim().isEmpty()) {
									categories.add(findByDgmSelectedPreference.getCategoryA3());
								}
								if (findByDgmSelectedPreference.getCategoryA4() != null
										&& !findByDgmSelectedPreference.getCategoryA4().trim().isEmpty()) {
									categories.add(findByDgmSelectedPreference.getCategoryA4());
								}
								if (findByDgmSelectedPreference.getCategoryA5() != null
										&& !findByDgmSelectedPreference.getCategoryA5().trim().isEmpty()) {
									categories.add(findByDgmSelectedPreference.getCategoryA5());
								}

								// अब सिर्फ वही filter होगा जो exact match है
								List<ContractorParticipateAndNotPartiDto> filteredList = mergeList.stream()
										.filter(m -> categories.contains(m.getContractorCategory()))
										.collect(Collectors.toList());

								filteredList.stream().forEach(System.out::println);
								participantAndNotParticipantDto.setListOfParticipantedAndNotParticipated(filteredList);

							}
						}

//					}
				}

				if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {
					
					List<ContractorParticipateAndNotPartiDto> listOfParticipantedAndNotParticipated = participantAndNotParticipantDto.getListOfParticipantedAndNotParticipated();
					List<ContractorParticipateAndNotPartiDto> collect = listOfParticipantedAndNotParticipated.stream().filter(a->!a.getContractorCategory().equalsIgnoreCase("B")).collect(Collectors.toList()) ;
					participantAndNotParticipantDto.setListOfParticipantedAndNotParticipated(collect);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		response.setList(Arrays.asList(participantAndNotParticipantDto));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "Save addQcContractorDetail ", notes = "Pass ConsumerApplicationId,   ", response = Response.class, responseContainer = "List", tags = RestApiUrl.WORK_ORDER_TYPE_TAGS)
	@ApiResponses(value = {
			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
	@PostMapping("/saveForQcPortal")
	public ResponseEntity<Response<?>> addQcContractorDetail(@RequestBody ContractorForBidForm contractorForBidForm,
			HttpServletResponse httpServletResponse) {
		System.out.println("addWorkOrderDetails !!!!!");
		final String method = RestApiUrl.WORK_ORDER_TYPE_API + RestApiUrl.ADD_URL + " : addWorkOrderDetail()";

		logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		Response response = new Response<>();
		RestTemplate restTemplate = new RestTemplate();
		try {
			String oytApplicationGreaterFiveOnSameContractor = oytApplicationGreaterFiveOnSameContractor(
					contractorForBidForm.getContractorAuthenticationId(),
					contractorForBidForm.getConsumerApplicationId());
			if (oytApplicationGreaterFiveOnSameContractor.equals(
					"Consumer can not select contractor application because there is alot pendency on contractor. ")) {
				return ResponseEntity
						.ok(new Response(HttpCode.NOT_ACCEPTABLE, oytApplicationGreaterFiveOnSameContractor));
			}

			ConsumerApplicationDetail findByConsumerApplicationId = consumerApplicationDetailService
					.findByConsumerApplicationId(contractorForBidForm.getConsumerApplicationId());
			if (findByConsumerApplicationId != null) {
				List<ContractorForBid> contractorApplicationDB = contractorForBidRepository
						.findByApplicationNumber(findByConsumerApplicationId.getConsumerApplicationNo());
				if (!contractorApplicationDB.isEmpty()) {
					return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
							"Contractor selection already done for this application no. "));
				}
			}
			ContractorForBid contractorForBid = getToken(restTemplate, contractorForBidForm);
			logger.info("contractorForBid" + contractorForBid + " ");

			response.setList(Arrays.asList(contractorForBid));
			response.setCode(HttpCode.CREATED);
			response.setMessage("Record Inserted Successfully");

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			logger.info("		e.getMessage()" + e.getMessage() + " ");

			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
	}

	public ContractorForBid getToken(RestTemplate restTemplate, ContractorForBidForm contractorForBidForm)
			throws Exception {
		ContractorForBid save = null;

		ConsumerApplicationDetail findByConsumerApplicationId = consumerApplicationDetailService
				.findByConsumerApplicationId(contractorForBidForm.getConsumerApplicationId());

		List<Map<String, ?>> dataFromView = consumerApplictionDetailRepository
				.getDataFromView(findByConsumerApplicationId.getConsumerApplicationNo());
		Response response = null;

		ApplicationStatus appStatusDb = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		Map<String, String> map = new HashMap();
		map.put("User_Id", contractorForBidForm.getUserId());
		map.put("consumers", contractorForBidForm.getConsumers());
		map.put("consumerTask", findByConsumerApplicationId.getShortDescriptionOfWork());
		map.put("consumerName", findByConsumerApplicationId.getConsumerName());
		map.put("consumerApplicationNo", findByConsumerApplicationId.getConsumerApplicationNo());
		if (findByConsumerApplicationId.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
			map.put("SchemeCode", "MKMY");

		} else {
			map.put("SchemeCode", findByConsumerApplicationId.getSchemeType().getSchemeTypeName());
		}
		Map<String, ?> mapData = dataFromView.get(0);
		System.err.println("aaaaaaaaaaaaaaaaaa : " + new Gson().toJson(mapData));
		map.put("discomName", mapData.get("discomName").toString());
		map.put("regionName", mapData.get("regionName").toString());
		map.put("circleName", mapData.get("NAME_OF_CIRCLE").toString());
		map.put("divisionName", mapData.get("NAME_OF_DIVISION").toString());
		map.put("subDivisionName", mapData.get("subdivName").toString());
		map.put("dcName", mapData.get("dcName").toString());

//		*************this is url based on properties file ****************
		System.err.println("tttttttttttt url : " + RavindraSendDataForContractorSelection);

		System.err.println("vvvvvvvvvvvvvvvvvvvvvvvv : " + new Gson().toJson(map));
		// listOfMap.add(map);
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(map, headers);
		System.out.println("httpEntity------------->" + httpEntity);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(RavindraSendDataForContractorSelection,
				httpEntity, String.class);
		System.out.println("responseEntity------------->" + responseEntity);

		String consumerApplicationNo = findByConsumerApplicationId.getConsumerApplicationNo();

		logger.info("api response " + responseEntity + " ");

		if (responseEntity != null) {
			String forObject = responseEntity.getBody();
			System.out.println("forObject-------------" + forObject);

			logger.info("api coll hone ke bad api ka responese" + forObject + " ");

			if (forObject != null && forObject.length() > 0) {
				JSONObject job = new JSONObject(forObject);
				if (job.getBoolean("status")) {

					Iterator keys = job.keys();

					while (keys.hasNext()) {

						String next = (String) keys.next();

						if (next.equals("contractor_details")) {
							logger.info("contractor_details " + next.equals("contractor_details") + " ");

							JSONArray jsonArray = job.getJSONArray("contractor_address");
							JSONArray jsonArray2 = job.getJSONArray("contractor_selection_bid_details");

							System.out.println(jsonArray2);

							System.out.println(jsonArray);

							for (int i = 0; i < jsonArray.length(); i++) {

								String userId = job.getJSONObject("contractor_details").getString("User_Id");
								logger.info("contractor details user id" + userId + " ");

								String consumers = job.getJSONObject("contractor_details").getString("consumers");
								logger.info("contractor details  consumers" + consumers + " ");

								String companyadd2 = jsonArray.getJSONObject(i).getString("Company_add_2");
								logger.info("contractor details user companyadd2" + companyadd2 + " ");

//								String companyPinCode = jsonArray.getJSONObject(i).getString("Company_pin_code");
//								logger.info("contractor details user companyPinCode" + companyPinCode + " ");

								String companyCity = jsonArray.getJSONObject(i).getString("Company_city");
								logger.info("contractor details user companyCity" + companyCity + " ");

								String companyState = jsonArray.getJSONObject(i).getString("Company_state");
								logger.info("contractor details user companyState" + companyState + " ");

								String authori = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
										.getString("Authorised_person_E");
								logger.info("contractor details user authori" + authori + " ");

								String bidOrderDate = jsonArray2.getJSONObject(i).getString("bid_order_at");
								logger.info("contractor details user bidOrderDate" + bidOrderDate + " ");

								String bidAmount = jsonArray2.getJSONObject(i).getString("bid_amount");
								logger.info("contractor details user bidAmount" + bidAmount + " ");

								String contractorCategory = jsonArray2.getJSONObject(i)
										.getString("contractor_category");
								logger.info("contractor details user contractorCategory" + contractorCategory + " ");

								String contactNo = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
										.getString("ContactNo");

								String contactAutanticationId = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
										.getString("Authentication_id");

								System.err.println("contactAutanticationId     : " + contactAutanticationId);

								logger.info("contractor details for Contact No" + contactNo + " ");

								ContractorForBid contractorForBid = new ContractorForBid();

								contractorForBid.setContractorCategory(contractorCategory);
								if (bidAmount != null)
									contractorForBid.setBidAmount(bidAmount);
								contractorForBid.setBidOrderAt(bidOrderDate);
								contractorForBid.setCompanyadd2(companyadd2);
//								contractorForBid.setCompanyPinCode(companyPinCode);
								contractorForBid.setCompanyCity(companyCity);
								contractorForBid.setCompanyState(companyState);
								contractorForBid.setContractorId(Long.getLong(userId));
								contractorForBid.setContractorCategory(contractorCategory);
								contractorForBid.setContactNo(contactNo);
								contractorForBid.setContractorAuthaticationId(contactAutanticationId);

								//
								contractorForBid.setContractorName(authori);

								contractorForBid.setConsumerApplicationNo(consumerApplicationNo);

								save = contractorForBidRepository.save(contractorForBid);

								appStatusDb = applicationStatusService
										.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());

								ConsumerApplicationDetail consumerApplicationDetil = consumerApplicationDetailService
										.findByConsumerApplicationId(contractorForBidForm.getConsumerApplicationId());

								consumerApplicationDetil.setApplicationStatus(appStatusDb);

								consumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetil);
								logger.info("contractor details user save" + save + " ");

								return save;

							}

						}

					}

					JSONArray jsonArray = job.getJSONArray("contractor_address");

					for (int i = 0; i <= jsonArray.length(); i++) {
//charitra start code
						String userId = jsonArray.getJSONObject(i).getJSONObject("user_id_id").getString("User_Id");
						logger.info("contractor details user userId" + userId + " ");

						String authorisedPersonE = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
								.getString("Authorised_person_E");
						logger.info("contractor details user authorisedPersonE" + authorisedPersonE + " ");

						String AuthenticationID = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
								.getString("Authentication_id");
						logger.info("contractor details user AuthenticationID" + AuthenticationID + " ");

						String contractorename = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
								.getString("CompanyName_E");
						logger.info("contractor details user contractorename" + contractorename + " ");

						String userType = jsonArray.getJSONObject(i).getJSONObject("user_id_id").getString("User_type");
						logger.info("contractor details user userType" + userType + " ");

						String oyt = jsonArray.getJSONObject(i).getJSONObject("user_id_id").getString("Oyt");
						logger.info("contractor details  oyt" + oyt + " ");

						String contactNo = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
								.getString("ContactNo");
						logger.info("contractor details for Contact No" + contactNo + " ");

						String contactAutanticationId = jsonArray.getJSONObject(i).getJSONObject("user_id_id")
								.getString("Authentication_id");

						System.err.println("contactAutanticationId     : " + contactAutanticationId);
//charitra end code	 
						String companyId = jsonArray.getJSONObject(i).getString("Company_Id");
						String companyadd1 = jsonArray.getJSONObject(i).getString("Company_add_1");
						String companyname = jsonArray.getJSONObject(i).getString("CompanyName_E");
						String companyadd2 = jsonArray.getJSONObject(i).getString("Company_add_2");
						String companyPinCode = jsonArray.getJSONObject(i).getString("Company_pin_code");
						String companydist = jsonArray.getJSONObject(i).getString("Company_dist");
						String companyCity = jsonArray.getJSONObject(i).getString("Company_city");
						String companyState = jsonArray.getJSONObject(i).getString("Company_state");

						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.PENDING_FOR_ACCEPTANCE_CONTRACTOR.getId());

						ConsumerApplicationDetail consumerApplicationDetil = consumerApplicationDetailService
								.findByConsumerApplicationId(contractorForBidForm.getConsumerApplicationId());

						consumerApplicationDetil.setApplicationStatus(appStatusDb);

						consumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetil);

						ContractorForBid contractorForBid = new ContractorForBid();

						contractorForBid.setContractorId(Long.getLong(userId));
						contractorForBid.setContractorName(authorisedPersonE);
						contractorForBid.setCompanyadd2(companyadd2);
						contractorForBid.setCompanyPinCode(companyPinCode);
						contractorForBid.setCompanyCity(companyCity);
						contractorForBid.setCompanyState(companyState);
						contractorForBid.setContactNo(contactNo);
						contractorForBid.setConsumerApplicationNo(consumerApplicationNo);
						contractorForBid.setContractorAuthaticationId(contactAutanticationId);

						if (oyt.equals("8")) {

							contractorForBid.setContractorCategory("A5");

						} else if (oyt.equals("7")) {

							contractorForBid.setContractorCategory("A4");
						} else if (oyt.equals("6")) {

							contractorForBid.setContractorCategory("A3");
						} else if (oyt.equals("5")) {

							contractorForBid.setContractorCategory("A2");
						} else if (oyt.equals("3")) {

							contractorForBid.setContractorCategory("A1");
						} else if (oyt.equals("2")) {

							contractorForBid.setContractorCategory("B");
						}

						save = contractorForBidRepository.save(contractorForBid);
						logger.info("value save" + save + " ");

						return save;
					}

				}
			}

		}

		logger.info("contractor details user save" + save + " ");

		return save;
	}

	@PostMapping("/acceptanceOfConsumer")
	public ResponseEntity<?> addConsumerApplicationAcceptanceOrRejectionDetail(
			@RequestBody @Valid VendorRejectionForm vendorRejectionForm, BindingResult bindingResult,
			HttpServletResponse httpServletResponse) throws FormValidationException, InvalidAuthenticationException,
			ConsumerNotFoundByApplicationIdException, ConsumerApplicationDetailException, VendorException {

		final String method = RestApiUrl.CONTRACTOR_DETAIL_API + RestApiUrl.ACCEPTANCE_FOR_CONTRACTORFORQC
				+ " :  addConsumerApplicationAcceptanceOrRejectionDetail() ";

		Response<ConsumerApplicationDetail> response = new Response<>();

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
		if (!vendorRejectionForm.getIsRejected()) {

			if (vendorRejectionForm.getContractorId() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Fill Expected Contractor Id");
				throw new VendorException(response);
			}
			if (vendorRejectionForm.getConWorkCompleteDate() == null
					|| vendorRejectionForm.getConWorkCompleteDate().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Fill Expected Contractor Work Complete Date");
				throw new VendorException(response);
			}

			if (vendorRejectionForm.getConWorkStartedDate() == null
					|| vendorRejectionForm.getConWorkStartedDate().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Fill Expected Contractor Work start Date");
				throw new VendorException(response);
			}

			if (vendorRejectionForm.getMaterialInstallStartDate() == null
					|| vendorRejectionForm.getMaterialInstallStartDate().equals("")) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Fill Expected Material Installation Start Date");
				throw new VendorException(response);
			}

			if (vendorRejectionForm.getMaterialInstallFinishDate() == null
					|| vendorRejectionForm.getMaterialInstallFinishDate().equals("")) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Fill Expected Material Installation Finish Date");
				throw new VendorException(response);
			}

			if (vendorRejectionForm.getMaterialHandoverSiteDate() == null
					|| vendorRejectionForm.getMaterialHandoverSiteDate().equals("")) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please Fill Material Handover Site Date");
				throw new VendorException(response);
			}
		}
		ConsumerApplicationDetail consumerApplicationDetail = vendorService
				.updateConsumerApplicationDetailsByApplicationId(vendorRejectionForm);

		if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 21) {

			response.setCode(HttpCode.OK);
			response.setMessage("Application Return back to consumer side please select new contractor");
		} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 23) {

			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully Now Application is Pending for generation of work order");
			response.setList(Arrays.asList(consumerApplicationDetail));
			return ResponseEntity.ok().body(response);
		}

		else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 27) {

			response.setCode(HttpCode.OK);
			response.setMessage("Record Inserted Successfully Now Application is Pending for DGM STC");
			response.setList(Arrays.asList(consumerApplicationDetail));
			return ResponseEntity.ok().body(response);
		} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 24) {
			response.setCode(HttpCode.OK);
			response.setMessage(
					"Record Inserted Successfully Now Application is Pending for Work completion by Contractor at QC Portal");
			response.setList(Arrays.asList(consumerApplicationDetail));
			return ResponseEntity.ok().body(response);
		} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() == 5) {
			response.setCode(HttpCode.OK);
			response.setMessage("Pending for Registration Fees");
			response.setList(Arrays.asList(consumerApplicationDetail));
			return ResponseEntity.ok().body(response);
		} else {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage(
					"Record can not be inserted because the application is not on Pending for Acceptance of contractor");
		}
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@ApiOperation(value = "find by Application No Contractor  information", response = ContractorForBid.class)
	@GetMapping("/conforbid/{applicationNo}")
	public ResponseEntity<Response> findById(@PathVariable("applicationNo") String applicationNo) {
		Map<String, String> findByApplicationNo = contractorForBidRepository.findByApplicationNo1(applicationNo);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(findByApplicationNo));
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/contractor_selection_old_dsp_save")
	public Response contractorSelectionSave(@RequestBody ContractorDetailsDto contractorDetailsDto) {

		Response response = new Response();
		RestTemplate restTemplate = new RestTemplate();

		String url = "https://rooftop-uat.mpcz.in:8443/qcp/contractor_selection_old_dsp_save";
//		 String url = "http://localhost:8081/qcp/contractor_selection_save";

		ContractorsDetails saveContractorsDetails = new ContractorsDetails();
		ContractorsDetails contractorDetailsWithAppNo = new ContractorsDetails();
		int dspDataCheck = 0;
		int qcDataCheck = 0;

		if (contractorDetailsDto.getUserId() != null && contractorDetailsDto.getConsumerApplicationNo() != null) {
			try {

				contractorDetailsWithAppNo.setConsumerApplicationNo(contractorDetailsDto.getConsumerApplicationNo());
				contractorDetailsWithAppNo.setAuthenticationId(contractorDetailsDto.getAuthenticationId());
				contractorDetailsWithAppNo.setAuthorisedPerson(contractorDetailsDto.getAuthorisedPerson());
				contractorDetailsWithAppNo.setCompanyAddress1(contractorDetailsDto.getCompanyAddress1());
				contractorDetailsWithAppNo.setCompanyAddress2(contractorDetailsDto.getCompanyAddress2());
				contractorDetailsWithAppNo.setCompanyId(contractorDetailsDto.getCompanyId());
				contractorDetailsWithAppNo.setCompanyName(contractorDetailsDto.getCompanyName());
				contractorDetailsWithAppNo.setCompanyPinCode(contractorDetailsDto.getCompanyPinCode());
				contractorDetailsWithAppNo.setCompanyTCity(contractorDetailsDto.getCompanyTCity());
				contractorDetailsWithAppNo.setCompanyTDistrict(contractorDetailsDto.getCompanyTDistrict());
				contractorDetailsWithAppNo.setCompanyTState(contractorDetailsDto.getCompanyTState());
				contractorDetailsWithAppNo.setCreationDate(LocalDateTime.now().toString());
				contractorDetailsWithAppNo.setOyt(contractorDetailsDto.getOyt());
				contractorDetailsWithAppNo.setOytName(contractorDetailsDto.getOytName());
				contractorDetailsWithAppNo.setUserId(contractorDetailsDto.getUserId());
				contractorDetailsWithAppNo.setUserType(contractorDetailsDto.getUserType());

				saveContractorsDetails = contractorsDetailsRepository.save(contractorDetailsWithAppNo);
				dspDataCheck = 1;
				if (saveContractorsDetails == null) {
					response.setCode("404");
					response.setMessage("Data not save in dsp");
					return response;
				}

				HttpHeaders headers = new HttpHeaders();
				headers.set("Content-Type", "application/json");

				HttpEntity<ContractorsDetails> httpEntity = new HttpEntity<>(saveContractorsDetails, headers);

				ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
				System.out.println("responseEntity------------->" + responseEntity);

				String body = responseEntity.getBody();

				ObjectMapper objectMapper = new ObjectMapper();

				QcResponseDto qcResponseDto = objectMapper.readValue(body, QcResponseDto.class);

				if (qcResponseDto.getStatus() == 200) {
					qcDataCheck = 1;
					response.setCode("200");
					response.setMessage("Data save in dsp and qc");
					return response;
				} else {
					if (dspDataCheck == 1) {
						contractorsDetailsRepository.deleteById(saveContractorsDetails.getSrNo());
						response.setCode("200");
						response.setMessage("Data not save in qc portal So delete from dsp");
						return response;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(dspDataCheck);
				if (dspDataCheck == 1 && qcDataCheck == 0) {
					contractorsDetailsRepository.deleteById(saveContractorsDetails.getSrNo());
				}
				response.setCode("500");
				response.setError(Arrays.asList(e.getMessage()));
				return response;
				// return Response.response("", HttpStatus.INTERNAL_SERVER_ERROR, null,
				// e.getMessage());

			}
		}
		response.setCode("404");
		response.setMessage("user id and application no. should not be null");
		return response;
	}

	@Autowired
	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@GetMapping("/postDataProd/{consApp}")
	public Response postDataProd(@PathVariable String consApp) {

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consApp);

		Map<String, String> requestBody = new HashMap<>();

		if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
			MmkyPayAmount findByConsumer = mmkyPayAmountRespository
					.findByConsumerApplicationNumber(findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("schema", findByConsumer.getSchemeCode());
		} else if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 5L) {

			requestBody.put("schema", "OYT");

		} else {

			Optional<ErpEstimateAmountData> findById = estimateAmountRepository
					.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
			if (findById.isPresent()) {
				ErpEstimateAmountData erpEstimateAmountData = findById.get();
				String kwLoad = String.valueOf(erpEstimateAmountData.getKwLoad());
				requestBody.put("kwload", kwLoad);

				String kvaLoad = String.valueOf(erpEstimateAmountData.getKvaLoad());
				requestBody.put("kvaload", kvaLoad);

				String depositAmount = String.valueOf(erpEstimateAmountData.getDepositAmount());
				requestBody.put("deposit_amount", depositAmount);

				String superVisionAmt = String.valueOf(erpEstimateAmountData.getSupervisionAmount());
				requestBody.put("supervision_amount", superVisionAmt);

				String sgst = String.valueOf(erpEstimateAmountData.getSgst());
				requestBody.put("sgst", sgst);

				String cgst = String.valueOf(erpEstimateAmountData.getCgst());
				requestBody.put("cgst", cgst);

				requestBody.put("estimate_name", erpEstimateAmountData.getEstimateName());
				requestBody.put("schema", erpEstimateAmountData.getSchemeCode());

			}
		}

		requestBody.put("consumer_mobile_no", findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
		requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
		requestBody.put("consumer_email_id", findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

		requestBody.put("address", findByConsumerApplicationNumber.getAddress());

		requestBody.put("shortDescriptionOfWork", findByConsumerApplicationNumber.getShortDescriptionOfWork());
		requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
		requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
		requestBody.put("is_bid_submitted", "false");
		requestBody.put("nature_of_work_name", findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkName());
		if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
				&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
			requestBody.put("sspTotalAmount", findByConsumerApplicationNumber.getSspTotalAmount().toString());
			requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

		}
		RestTemplate restTemplate = new RestTemplate();

		// Production Code
		ResponseEntity<Map> postForEntity = restTemplate.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/",
				requestBody, Map.class);
		System.out.println("The result of Post api is :" + postForEntity.getBody());
		return null;

	}

	@GetMapping("/postDataUat/{consApp}")
	public Response postData(@PathVariable String consApp) {

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consApp);

		Map<String, String> requestBody = new HashMap<>();

		if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
			MmkyPayAmount findByConsumer = mmkyPayAmountRespository
					.findByConsumerApplicationNumber(findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
			requestBody.put("schema", findByConsumer.getSchemeCode());
		} else {

			Optional<ErpEstimateAmountData> findById = estimateAmountRepository
					.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
			if (findById.isPresent()) {
				ErpEstimateAmountData erpEstimateAmountData = findById.get();
				String kwLoad = String.valueOf(erpEstimateAmountData.getKwLoad());
				requestBody.put("kwload", kwLoad);

				String kvaLoad = String.valueOf(erpEstimateAmountData.getKvaLoad());
				requestBody.put("kvaload", kvaLoad);

				String depositAmount = String.valueOf(erpEstimateAmountData.getDepositAmount());
				requestBody.put("deposit_amount", depositAmount);

				String superVisionAmt = String.valueOf(erpEstimateAmountData.getSupervisionAmount());
				requestBody.put("supervision_amount", superVisionAmt);

				String sgst = String.valueOf(erpEstimateAmountData.getSgst());
				requestBody.put("sgst", sgst);

				String cgst = String.valueOf(erpEstimateAmountData.getCgst());
				requestBody.put("cgst", cgst);

				requestBody.put("estimate_name", erpEstimateAmountData.getEstimateName());
				requestBody.put("schema", erpEstimateAmountData.getSchemeCode());

			}
		}

		requestBody.put("consumer_mobile_no", findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
		requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
		requestBody.put("consumer_email_id", findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

		requestBody.put("address", findByConsumerApplicationNumber.getAddress());

		requestBody.put("shortDescriptionOfWork", findByConsumerApplicationNumber.getShortDescriptionOfWork());
		requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
		requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
		requestBody.put("is_bid_submitted", "false");
		requestBody.put("nature_of_work_name", findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkName());
		if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
				&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
			requestBody.put("sspTotalAmount", findByConsumerApplicationNumber.getSspTotalAmount().toString());
			requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

		}

		System.err.println("aaaaaaaaaaaa : " + new Gson().toJson(requestBody));
		RestTemplate restTemplate = new RestTemplate();

		// UAT Code
		ResponseEntity<Map> postForEntity = restTemplate
				.postForEntity("https://qualitycheckdev.mpcz.in:8081/tkc/get_consumer/", requestBody, Map.class);

		System.out.println("The result of Post api is :" + postForEntity.getBody());
		return null;

	}

	@GetMapping("/getQcPortalData")
	public String getQcPortalData(@RequestParam String consumerApp, @RequestParam Long userId) {

		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" + consumerApp);
		Response response = new Response();
		RestTemplate rest = new RestTemplate();
		try {

			String url = ravindraGetFileDetailsSendToSamshad + consumerApp + "&User_Id=" + userId;

			System.out.println("print url " + url);
			HttpEntity<String> httpEntity = new HttpEntity<>(null);
			System.out.println(url);
			ResponseEntity<String> exchange = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);
			System.out.println(exchange.getBody());
			return exchange.getBody();
		} catch (Exception e) {

		}

		return null;

	}

	@GetMapping("/postDataProdAll")
	public void postDataProdAll() {

		List<ConsumerApplicationDetail> findByConsumerApplicationNumber1 = consumerApplictionDetailRepository.findAll();
		for (ConsumerApplicationDetail findByConsumerApplicationNumber : findByConsumerApplicationNumber1) {
			Map<String, String> requestBody = new HashMap<>();

			if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() > 7) {
				if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8L) {
					MmkyPayAmount findByConsumer = mmkyPayAmountRespository.findByConsumerApplicationNumber(
							findByConsumerApplicationNumber.getConsumerApplicationNo());
					requestBody.put("consumerApplicationNo",
							findByConsumerApplicationNumber.getConsumerApplicationNo());
					requestBody.put("schema", findByConsumer.getSchemeCode());
				} else {

					Optional<ErpEstimateAmountData> findById = estimateAmountRepository
							.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
					if (findById.isPresent()) {
						ErpEstimateAmountData erpEstimateAmountData = findById.get();
						String kwLoad = String.valueOf(erpEstimateAmountData.getKwLoad());
						requestBody.put("kwload", kwLoad);

						String kvaLoad = String.valueOf(erpEstimateAmountData.getKvaLoad());
						requestBody.put("kvaload", kvaLoad);

						String depositAmount = String.valueOf(erpEstimateAmountData.getDepositAmount());
						requestBody.put("deposit_amount", depositAmount);

						String superVisionAmt = String.valueOf(erpEstimateAmountData.getSupervisionAmount());
						requestBody.put("supervision_amount", superVisionAmt);

						String sgst = String.valueOf(erpEstimateAmountData.getSgst());
						requestBody.put("sgst", sgst);

						String cgst = String.valueOf(erpEstimateAmountData.getCgst());
						requestBody.put("cgst", cgst);

						requestBody.put("estimate_name", erpEstimateAmountData.getEstimateName());
						requestBody.put("schema", erpEstimateAmountData.getSchemeCode());

					}
				}

				requestBody.put("consumer_mobile_no",
						findByConsumerApplicationNumber.getConsumers().getConsumerMobileNo());
				requestBody.put("consumerApplicationNo", findByConsumerApplicationNumber.getConsumerApplicationNo());
				requestBody.put("consumer_email_id",
						findByConsumerApplicationNumber.getConsumers().getConsumerEmailId());

				requestBody.put("address", findByConsumerApplicationNumber.getAddress());

				requestBody.put("shortDescriptionOfWork", findByConsumerApplicationNumber.getShortDescriptionOfWork());
				requestBody.put("erp_no", findByConsumerApplicationNumber.getErpWorkFlowNumber());
				requestBody.put("consumerName", findByConsumerApplicationNumber.getConsumerName());
				requestBody.put("is_bid_submitted", "false");
				if (Objects.nonNull(findByConsumerApplicationNumber.getSspTotalAmount())
						&& findByConsumerApplicationNumber.getSspTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
					requestBody.put("sspTotalAmount", findByConsumerApplicationNumber.getSspTotalAmount().toString());
					requestBody.put("sspApplicationNo", findByConsumerApplicationNumber.getNscApplicationNo());

				}

				RestTemplate restTemplate = new RestTemplate();

				// Production Code
				ResponseEntity<Map> postForEntity = restTemplate
						.postForEntity("https://qcportal.mpcz.in/tkc/get_consumer/", requestBody, Map.class);
				System.out.println("The result of Post api is :" + postForEntity.getBody());

			}
		}

	}

//	@GetMapping("/oytApplicationGreaterFiveOnSameContractor")
	public String oytApplicationGreaterFiveOnSameContractor(String contractorAuthenticationId,
			Long consumerApplicationId) throws ConsumerApplicationDetailException {
		// code start 13-12-2024 for OYT Application pending on contractor greater than
		// 5
		ConsumerApplicationDetail findByConsumerApplicationId = consumerApplicationDetailService
				.findByConsumerApplicationId(consumerApplicationId);
		if (findByConsumerApplicationId.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {

			if (contractorAuthenticationId != null && !contractorAuthenticationId.trim().isEmpty()) {
//							String authenticationId = "CZC201809044994";
//							String oneMonthBackDate = "2023-12-13";
				LocalDate currentDate = LocalDate.now();
				String oneMonthBackDate = currentDate.minusMonths(1).toString();
				System.err.println("oneMonthBackDate :  " + oneMonthBackDate);
				List<Map<String, Object>> contractorListDB = contractorForBidRepository
						.findByDistinctContractorAuthaticationId(contractorAuthenticationId, oneMonthBackDate);
				System.err.println("contractorListDB : " + new Gson().toJson(contractorListDB));

				Long contractorPendecyDB = validationRepository.findByPendencyName("CONTRACTOR_APPLICATION_PENDENCY");
				System.err.println("contractorPendecyDB : " + contractorPendecyDB);

				if (contractorListDB.size() > contractorPendecyDB) {
					System.err.println(
							"you can not select contractor application because there is alot pendency on contractor : "
									+ contractorListDB.size());

					return "Consumer can not select contractor application because there is alot pendency on contractor. ";

				}
			}
		}
		// code end 13-12-2024 for OYT Application pending on contractor greater than 5
		return "Consumer can select contractor because there is less pendency on this contractor for OYT Applicaiton.";
	}

	@PutMapping("/oytContractorSelectionByDGM/{consumerApplicationNo}/{discomUserId}/{userType}/{contractorUserId}")
	public ResponseEntity<?> oytContractorSelectionByDGM(@PathVariable String consumerApplicationNo,
			@PathVariable String discomUserId, @PathVariable String userType, @PathVariable Long contractorUserId) {
		ConsumerApplicationDetail consumerApplicationNumberDB = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationNumberDB)) {
			return ResponseEntity.status(404)
					.body(new Response<>(HttpCode.NULL_OBJECT, "Consumer Application Not Found in database"));
		}
		if (userType.equals("DGM")) {
			LocalDate tenPlusDays = LocalDate.now().plusDays(10);
			LocalDate fourteenPlusDays = LocalDate.now().plusDays(14);
			LocalDate eighteenPlusDays = LocalDate.now().plusDays(18);
			LocalDate twentyPlusDays = LocalDate.now().plusDays(20);
			LocalDate nextPlusDays = LocalDate.now().plusDays(22);

			ContractorForBidWorkStatus consumerApplicationNoDB = contractorForBidWorkStatusRepository
					.findByConsumerApplicationNo(consumerApplicationNo);
			if (Objects.isNull(consumerApplicationNoDB)) {
				ContractorForBidWorkStatus conForBid = new ContractorForBidWorkStatus();
				conForBid.setConWorkStartedDate(tenPlusDays.toString());
				conForBid.setMaterialInstallStartDate(fourteenPlusDays.toString());
				conForBid.setMaterialInstallFinishDate(eighteenPlusDays.toString());
				conForBid.setConWorkCompleteDate(twentyPlusDays.toString());
				conForBid.setMaterialHandoverSiteDate(nextPlusDays.toString());
				conForBid.setConsumerApplicationNumber(consumerApplicationNo);
				conForBid.setUserId(contractorUserId);
				contractorForBidWorkStatusRepository.save(conForBid);
			} else {
				consumerApplicationNoDB.setConWorkStartedDate(tenPlusDays.toString());
				consumerApplicationNoDB.setMaterialInstallStartDate(fourteenPlusDays.toString());
				consumerApplicationNoDB.setMaterialInstallFinishDate(eighteenPlusDays.toString());
				consumerApplicationNoDB.setConWorkCompleteDate(twentyPlusDays.toString());
				consumerApplicationNoDB.setMaterialHandoverSiteDate(nextPlusDays.toString());
				consumerApplicationNoDB.setConsumerApplicationNumber(consumerApplicationNo);
				consumerApplicationNoDB.setUserId(contractorUserId);
				contractorForBidWorkStatusRepository.save(consumerApplicationNoDB);
			}

			List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
					.getTotalPaymentDetails(consumerApplicationNo);

			boolean demandFeesDB = findByConsumerApplicationNo.stream()
					.anyMatch(a -> a.getAdditionalInfo().equals("Demand_fees"));
			boolean registrationFeesDB = findByConsumerApplicationNo.stream()
					.anyMatch(b -> b.getAdditionalInfo().equals("Registration_Fees"));

			if (demandFeesDB && registrationFeesDB) {

				consumerApplicationNumberDB.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId()).get());
			} else {
				consumerApplicationNumberDB.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId()).get());
			}
		}
		consumerApplicationNumberDB.setOytContractorSelection(discomUserId);
		ConsumerApplicationDetail savedData = consumerApplictionDetailRepository.save(consumerApplicationNumberDB);

		return ResponseEntity.status(200)
				.body(new Response<>(HttpCode.UPDATED, "Data Updated Successfully", Arrays.asList(savedData)));

	}

	@GetMapping("/oytContractorPendency/{authenticationId}")
	public ResponseEntity<?> oytContractorPendency(@PathVariable String authenticationId) {
		String minusOneMonthDate = LocalDate.now().minusMonths(1).toString();

		List<Map<String, Object>> oytContractorPendencyRaw = contractorForBidRepository
				.oytContractorPendency(authenticationId, minusOneMonthDate);

		ContractorForBid contractor = contractorForBidRepository.findByContractorAuthaticationId(authenticationId);

		// Create a new modifiable list
		List<Map<String, Object>> oytContractorPendency = new ArrayList<>();

		for (Map<String, Object> rawRow : oytContractorPendencyRaw) {
			Map<String, Object> modifiableRow = new HashMap<>(rawRow); // make it modifiable
			modifiableRow.put("CONTRACTOR_NAME", contractor.getContractorName());
			oytContractorPendency.add(modifiableRow);
		}

		return ResponseEntity.ok(oytContractorPendency.isEmpty()
				? new Response<>(HttpCode.NULL_OBJECT, "No Pendency at authenticationId : " + authenticationId)
				: new Response<>(HttpCode.OK, "Pendency at authenticationId : " + authenticationId,
						oytContractorPendency));
	}

//	@Autowired
//	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//	@Autowired
//	private DynamicQueryRepository dynamicQueryRepository;
//
//	@GetMapping("/oytContractorPendency/{authenticationId}")
//	public ResponseEntity<?> fetchPendingApplications(String contractorAuthenticationId) {
//		String oneMonthBackDate = LocalDate.now().minusMonths(1).toString();
//
//
//		Optional<DynamicQuery> optionalQuery = dynamicQueryRepository
//				.findByQueryNameAndIsActive("PENDING_OYT_APPLICATIONS_QUERY", 1);
//
//		if (!optionalQuery.isPresent()) {
//			throw new IllegalArgumentException("No dynamic query found for: PENDING_OYT_APPLICATIONS_QUERY");
//		}
//
//		DynamicQuery dynamicQuery = optionalQuery.get();
//
//		String query = dynamicQuery.getQueryText();
//
//		if (dynamicQuery == null || dynamicQuery.getQueryText() == null) {
//			throw new IllegalArgumentException("No dynamic query found for: PENDING_OYT_APPLICATIONS_QUERY");
//		}
//
//		System.err.println("aaaaaaaaaaaa : " + dynamicQuery);
//
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("authenticationId", contractorAuthenticationId);
//		params.put("oneMonthBackDate", oneMonthBackDate);
//
//		List<Map<String, Object>> queryForList = namedParameterJdbcTemplate.queryForList(query, params);
//
//		LinkedHashMap<String, Object> responseData = new LinkedHashMap<>();
//		responseData.put("Total_Count", queryForList.size());
//		responseData.put("Records", queryForList);
//
//		return ResponseEntity.ok(Objects.isNull(responseData)
//				? new Response<>(HttpCode.NULL_OBJECT,
//						"No Pendency at authenticationId : " + contractorAuthenticationId)
//				: new Response<>(HttpCode.OK, "Pendency at authenticationId : " + contractorAuthenticationId,
//						Arrays.asList(responseData)));
//
//	}

	@GetMapping("/listSelectContractor/{consumerapplicationNumber}")
	public ResponseEntity<?> listSelectContractor(@PathVariable String consumerapplicationNumber) {
		ContractorCategoryData findByDgmSelectedPreference = null;
		ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo = null;
		try {
			findConsumerApplicationDetailByApplicationNo = consumerApplicationDetailService
					.findConsumerApplicationDetailByApplicationNo(consumerapplicationNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ErpEstimateAmountData findByErpNo = estimateAmountRepository
				.findByErpNo(findConsumerApplicationDetailByApplicationNo.getErpWorkFlowNumber());

		if (findByErpNo != null) {
			if (findByErpNo.getSortContratoreListByKvLine() != null) {
				BigDecimal payableAmount = findByErpNo.getEstimateAmount();

				if (findByErpNo.getSortContratoreListByKvLine() == 1l) {
					findByDgmSelectedPreference = contractorCategoryDataRepository
							.findCategoryByRange(findByErpNo.getSortContratoreListByKvLine(), payableAmount);
				}
				if (findByErpNo.getSortContratoreListByKvLine() == 2l
						|| findByErpNo.getSortContratoreListByKvLine() == 3l) {
					findByDgmSelectedPreference = contractorCategoryDataRepository
							.findByDgmSelectedPreference(findByErpNo.getSortContratoreListByKvLine());
				}

			}
		}

		return ResponseEntity.status(200).body(new Response<>(HttpCode.UPDATED, "Data Updated Successfully",
				Arrays.asList(findByDgmSelectedPreference)));

	}

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private DynamicQueryRepository dynamicQueryRepository;

	@GetMapping("/getListOfActualWorkCompletionApplicationForQC")
	public ResponseEntity<?> getListOfActualWorkCompletionApplicationForQC() {

		DynamicQuery byQueryName = dynamicQueryRepository.findByQueryName("CONTRACTOR_WORK_COMPLETION_DATE_QUERY");
		if (Objects.isNull(byQueryName)) {
			throw new IllegalArgumentException("No dynamic query found for: CONTRACTOR_WORK_COMPLETION_DATE_QUERY");
		}

		String query = byQueryName.getQueryText(); // Actual SQL Query
		System.err.println("Query to execute: " + query);

		List<Map<String, Object>> resultList = namedParameterJdbcTemplate.queryForList(query, new HashMap<>());

		System.err.println("AAAAAAA : " + new Gson().toJson(resultList));

		return ResponseEntity
				.ok(Objects.isNull(resultList) ? new Response<>(HttpCode.NULL_OBJECT, "No data found for refund mis")
						: new Response<>(HttpCode.OK, "Data Retrieved Successfully", resultList));

	}

}