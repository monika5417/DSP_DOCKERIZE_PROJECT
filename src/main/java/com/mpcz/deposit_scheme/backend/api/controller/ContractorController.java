//package com.mpcz.deposit_scheme.backend.api.controller;
//
//import java.lang.reflect.Type;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.google.common.reflect.TypeToken;
//
//import com.mpcz.deposit_scheme.backend.api.constant.ApiResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
//import com.mpcz.deposit_scheme.backend.api.domain.SupplyVoltage;
//import com.mpcz.deposit_scheme.backend.api.dto.ContractorData;
//import com.mpcz.deposit_scheme.backend.api.enums.SupplyVoltageEnum;
//import com.mpcz.deposit_scheme.backend.api.response.ContractorResponse;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.SupplyVoltageService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@Api(value = "ContractorController", description = "Rest api for Contractor Details.")
//@RestController
//@RequestMapping(RestApiUrl.QC_PORTAL_API)
//public class ContractorController {
//
//	Logger logger = LoggerFactory.getLogger(ContractorController.class);
//
////	@Autowired
////	ProjectProperties projectProperties;
//
//	@Autowired
//	private SupplyVoltageService supplyVoltageService;
//
//	@ApiOperation(value = "Retrieve all contractor details", notes = "Fetch all contractor details", response = Response.class, responseContainer = "List", tags = RestApiUrl.QC_PORTAL_TAGS)
//	@ApiResponses(value = {
//			@ApiResponse(code = ApiResponseCode.SUCCESS_CODE, message = ResponseMessage.RECORD_INSERTED_MESSAGE),
//			@ApiResponse(code = ApiResponseCode.FORM_VALIDATION_ERROR, message = ResponseMessage.FORM_VALIDATION_ERROR),
//			@ApiResponse(code = ApiResponseCode.UNSUPPORTED_MEDIA_TYPE_CODE, message = ResponseMessage.UNSUPPORTED_MEDIA_TYPE),
//			@ApiResponse(code = ApiResponseCode.BAD_REQUEST_CODE, message = ResponseMessage.BAD_REQUEST),
//			@ApiResponse(code = ApiResponseCode.UNAUTHORIZED_CODE, message = ResponseMessage.UNAUTHORIZED),
//			@ApiResponse(code = ApiResponseCode.FORBIDDEN_CODE, message = ResponseMessage.FORBIDDEN),
//			@ApiResponse(code = ApiResponseCode.NOT_FOUND_CODE, message = ResponseMessage.NOT_FOUND) })
//	@GetMapping(RestApiUrl.GET_ALL_CONTRACTOR_URL)
//	public ResponseEntity<ContractorResponse> retrieveAllContractors(@PathVariable Long id,
//			HttpServletResponse httpServletResponse, HttpServletRequest request) throws Exception {
//
//		System.out.println("value of id is :- " + id);
//
//		final String method = RestApiUrl.QC_PORTAL_API + RestApiUrl.GET_ALL_CONTRACTOR_URL
//				+ " : retrieveAllContractors()";
//
//		ContractorResponse response = new ContractorResponse();
//
//		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
//
//		final String baseUrl = "https://qcportal.mpcz.in/tkc/all_active_contractors_list";
//
//		URI uri = new URI(baseUrl);
//
//		HttpHeaders headers = new HttpHeaders();
//
//		headers.set("User-Agent",
//				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
//
//		headers.set("Content-Type", "application/json");
//
//		String token = null;
//
//		token = request.getHeader("Authorization");
//		headers.set("Authorization", token);
//
//		HttpStatus status = null;
//		Integer statusCode = null;
//
//		ResponseEntity<String> result = null;
//
//		List<ContractorData> contractorList = new ArrayList();
//
//		List<ContractorData> contractorListResponse = null;
//
//		Response<ContractorData> returnResult = new Response();
//		try {
//
//			result = restTemplate.getForEntity(uri, String.class);
//			Type listOfMyClassObject = new TypeToken<ArrayList<ContractorData>>() {
//			}.getType();
//			contractorList = new Gson().fromJson(result.getBody(), listOfMyClassObject);
//
////			sandeep, start
//
//			SupplyVoltage supplyVoltageResonse = null;
//
//			if (id != null) {
//				supplyVoltageResonse = supplyVoltageService.findBySupplyVoltageId(id);
//			}
//
//			if (supplyVoltageResonse != null) {
//
//				if (supplyVoltageResonse.getSupplyVoltageId().compareTo(SupplyVoltageEnum.VOLTAGE11KV.getId()) == 0) {
//
//					contractorListResponse = contractorList.stream().filter(p -> p.getUser_class().contains("A"))
//							.collect(Collectors.toList());
//					contractorListResponse.sort((c1, c2) -> c1.getCompany_name().compareTo(c2.getCompany_name()));
//				} else if (supplyVoltageResonse.getSupplyVoltageId()
//						.compareTo(SupplyVoltageEnum.VOLTAGE33KV.getId()) == 0) {
//					contractorListResponse = contractorList.stream()
//							.filter(p -> p.getUser_class().contains("A4") || p.getUser_class().contains("A5"))
//							.collect(Collectors.toList());
//					contractorListResponse.sort((c1, c2) -> c1.getCompany_name().compareTo(c2.getCompany_name()));
//				}
//			}
//
//			System.out.println("Actual Size of List is : " + contractorList.size());
//			System.out.println("Size of List is after filtering & sorting : " + contractorListResponse.size());
//
////			sandeep, end
//
//		} catch (Exception e) {
//
//			System.out.println("QC Exception :-");
//			e.printStackTrace();	
//
//			if (e.getMessage().contains("unable to find valid certification")) {
//				System.out.println("**IF:- unable to find valid certification**");
//				System.out.println(e.getMessage());
//			} else {
//				System.out.println("**ELSE:- unable to find valid certification**");
//			}
//			return ResponseEntity.badRequest().body(null);
//
//		}
//
//		if (Objects.nonNull(contractorListResponse) && Objects.nonNull(contractorListResponse)) {
//			response.setCode(HttpCode.OK);
//			response.setList(contractorListResponse);
//			response.setMessage("Record fatching Successfully");
//		} else {
//
//			return ResponseEntity.badRequest().body(null);
//		}
//
//		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
//
//	}
//
//	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
//		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//
//		clientHttpRequestFactory.setConnectTimeout(10 * 1000);
//
//		clientHttpRequestFactory.setReadTimeout(120 * 1000);
//
//		clientHttpRequestFactory.setConnectionRequestTimeout(120 * 1000);
//
//		return clientHttpRequestFactory;
//	}
//}