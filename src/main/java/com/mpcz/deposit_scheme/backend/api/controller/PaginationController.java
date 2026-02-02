package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.domain.DynamicQuery;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationDetailsFilterDTO;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DynamicQueryRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user/pagination")
public class PaginationController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private UserService userService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/getAllApplicationByStatusCount")
	public Response getAllDataFromView() {
		Response response = new Response();
		System.err.println("Before entering database: " + LocalDateTime.now());

		ConsumerApplicationDetailsFilterDTO filterDTO = checkUser();
		List<Map<String, Object>> allDataCount = consumerApplicationDetailRepository.getAllDataCount1(
				filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
				filterDTO.getSubDivisionId(), filterDTO.getDcId());

		System.err.println("After fetching data from database: " + LocalDateTime.now());

		if (allDataCount.isEmpty()) {
			response.setCode("404");
			response.setMessage("Data not found");
		} else {
			response.setCode("200");
			response.setMessage("Data found successfully");
			response.setList(allDataCount);
		}
		return response;
	}

	public ConsumerApplicationDetailsFilterDTO checkUser() {
		String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

		ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
		filterDTO.setUserLoginId(userLoginId);

		if (userLoginId != null) {
			User user = userService.findByUserId(userLoginId);
			String accessLevel = user.getAccessLevel();

			switch (accessLevel) {
			case "1":
				// Discom level
				filterDTO.setDiscomId(user.getUserDiscom().getDiscomId());
				break;
			case "2":
				// Region level
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				break;
			case "3":
				// Circle level
				if (Optional.ofNullable(user.getUserRegion()).isPresent()) {
					filterDTO.setRegionId(user.getUserRegion().getRegionId());
				}
				if (Optional.ofNullable(user.getUserCircle()).isPresent()) {
					filterDTO.setCircleId(user.getUserCircle().getCircleId());
				}
				break;
			case "4":
				// Division level
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
				// Subdivision level
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
				// DC level
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
			}
		}
		return filterDTO;
	}

	@GetMapping("/getApplicationData/{applicationStatusId}")
	public Response getAllApplicationByApplicationStatus(@PathVariable String applicationStatusId) {
		Response response = new Response();
		List<String> statusIdList = Arrays.asList(applicationStatusId.split(","));
		System.err.println("Before entering database: " + LocalDateTime.now());

		ConsumerApplicationDetailsFilterDTO filterDTO = checkUser();
		List<Map<String, Object>> allApplicationByApplicationStatus = consumerApplicationDetailRepository
				.getAllApplicationByApplicationStatus1(statusIdList, filterDTO.getDiscomId(), filterDTO.getRegionId(),
						filterDTO.getCircleId(), filterDTO.getDivisionId(), filterDTO.getSubDivisionId(),
						filterDTO.getDcId());

		System.err.println("After fetching data from database: " + LocalDateTime.now());

		if (allApplicationByApplicationStatus.isEmpty()) {
			response.setCode("404");
			response.setMessage("Data not found");
		} else {
			response.setCode("200");
			response.setMessage("Data found successfully");
			response.setList(allApplicationByApplicationStatus);
		}
		return response;
	}

	@GetMapping("/getAllDataFromViewViaAccessLevel/{userId}")
	public Response getAllDataFromViewViaAccessLevel(@PathVariable String userId) {
		Response response = new Response();
		System.err.println("Before entering database: " + LocalDateTime.now());
		Map<String, Object> usersByUserID = userRepository.findUsersByUserID(userId);
		System.err.println(new Gson().toJson(usersByUserID));
		Object accessLevel = usersByUserID.get("ACCESS_LEVEL");

		List<Map<String, Object>> allDataCount = null;

		if (accessLevel.equals("1")) {

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllDataCount1(1l, filterDTO.getRegionId(),
					filterDTO.getCircleId(), filterDTO.getDivisionId(), filterDTO.getSubDivisionId(),
					filterDTO.getDcId());
		} else if (accessLevel.equals("2")) {

			Object object1 = usersByUserID.get("REGION_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
					Long.valueOf(object1.toString()), filterDTO.getCircleId(), filterDTO.getDivisionId(),
					filterDTO.getSubDivisionId(), filterDTO.getDcId());
		} else if (accessLevel.equals("3")) {

			Object object1 = usersByUserID.get("CIRCLE_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
					filterDTO.getRegionId(), Long.valueOf(object1.toString()), filterDTO.getDivisionId(),
					filterDTO.getSubDivisionId(), filterDTO.getDcId());
		} else if (accessLevel.equals("4")) {

			Object object1 = usersByUserID.get("DIV_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
					filterDTO.getRegionId(), filterDTO.getCircleId(), Long.valueOf(object1.toString()),
					filterDTO.getSubDivisionId(), filterDTO.getDcId());
		} else if (accessLevel.equals("5")) {

			Object object1 = usersByUserID.get("SUB_DIVISION_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
					filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
					Long.valueOf(object1.toString()), filterDTO.getDcId());
		} else if (accessLevel.equals("6")) {

			Object object1 = usersByUserID.get("DISTRIBUTION_CENTER_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
					filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
					filterDTO.getSubDivisionId(), Long.valueOf(object1.toString()));
		}

		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(allDataCount);

		return response;
	}

	@GetMapping("/getAllApplicationByApplicationStatusByUserId/{applicationStatusId}/{userId}")
	public Response getAllApplicationByApplicationStatusByUserId(@PathVariable String applicationStatusId,
			@PathVariable String userId) {
		Response response = new Response();
		List<String> statusIdList = Arrays.asList(applicationStatusId.split(","));
		System.err.println("Before entering database: " + LocalDateTime.now());

		System.err.println("Before entering database: " + LocalDateTime.now());
		Map<String, Object> usersByUserID = userRepository.findUsersByUserID(userId);
		System.err.println(new Gson().toJson(usersByUserID));
		Object accessLevel = usersByUserID.get("ACCESS_LEVEL");

		List<Map<String, Object>> allDataCount = null;

		if (accessLevel.equals("1")) {

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList, 1l,
					filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
					filterDTO.getSubDivisionId(), filterDTO.getDcId());
		} else if (accessLevel.equals("2")) {

			Object object1 = usersByUserID.get("REGION_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
					filterDTO.getDiscomId(), Long.valueOf(object1.toString()), filterDTO.getCircleId(),
					filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), filterDTO.getDcId());
		} else if (accessLevel.equals("3")) {

			Object object1 = usersByUserID.get("CIRCLE_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
					filterDTO.getDiscomId(), filterDTO.getRegionId(), Long.valueOf(object1.toString()),
					filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), filterDTO.getDcId());
		} else if (accessLevel.equals("4")) {

			Object object1 = usersByUserID.get("DIV_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
					filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
					Long.valueOf(object1.toString()), filterDTO.getSubDivisionId(), filterDTO.getDcId());
		} else if (accessLevel.equals("5")) {

			Object object1 = usersByUserID.get("SUB_DIVISION_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
					filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
					filterDTO.getDivisionId(), Long.valueOf(object1.toString()), filterDTO.getDcId());
		} else if (accessLevel.equals("6")) {

			Object object1 = usersByUserID.get("DISTRIBUTION_CENTER_ID");

			ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
			allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
					filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
					filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), Long.valueOf(object1.toString()));
		}

		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(allDataCount);

		return response;
	}

//	@GetMapping("/getAllDataFromViewViaAccessLevelAndDc/{userId}/{dcId}/{divisionId}")
//	public Response getAllDataFromViewViaAccessLevelAndDc(@PathVariable String userId, @PathVariable int dcId,
//			@PathVariable int divisionId) {
//		Response response = new Response();
//		System.err.println("Before entering database: " + LocalDateTime.now());
//		Map<String, Object> usersByUserID = userRepository.findUsersByUserID(userId);
//		System.err.println(new Gson().toJson(usersByUserID));
//		Object accessLevel = usersByUserID.get("ACCESS_LEVEL");
//
//		List<Map<String, Object>> allDataCount = null;
//		if (dcId == 0 && divisionId == 0) {
//			if (accessLevel.equals("1")) {
//
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(1l, filterDTO.getRegionId(),
//						filterDTO.getCircleId(), filterDTO.getDivisionId(), filterDTO.getSubDivisionId(),
//						filterDTO.getDcId());
//			} else if (accessLevel.equals("2")) {
//
//				Object object1 = usersByUserID.get("REGION_ID");
//
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
//						Long.valueOf(object1.toString()), filterDTO.getCircleId(), filterDTO.getDivisionId(),
//						filterDTO.getSubDivisionId(), filterDTO.getDcId());
//			} else if (accessLevel.equals("3")) {
//
//				Object object1 = usersByUserID.get("CIRCLE_ID");
//
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
//						filterDTO.getRegionId(), Long.valueOf(object1.toString()), filterDTO.getDivisionId(),
//						filterDTO.getSubDivisionId(), filterDTO.getDcId());
//			} else if (accessLevel.equals("4")) {
//
//				Object object1 = usersByUserID.get("DIV_ID");
//
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
//						filterDTO.getRegionId(), filterDTO.getCircleId(), Long.valueOf(object1.toString()),
//						filterDTO.getSubDivisionId(), filterDTO.getDcId());
//			} else if (accessLevel.equals("5")) {
//
//				Object object1 = usersByUserID.get("SUB_DIVISION_ID");
//
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
//						filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
//						Long.valueOf(object1.toString()), filterDTO.getDcId());
//			} else if (accessLevel.equals("6")) {
//
//				Object object1 = usersByUserID.get("DISTRIBUTION_CENTER_ID");
//
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
//						filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
//						filterDTO.getSubDivisionId(), Long.valueOf(object1.toString()));
//			}
//		} else {
//
//			if (dcId != 0) {
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
//						filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
//						filterDTO.getSubDivisionId(), Long.valueOf(dcId));
//			} else {
//				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
//				allDataCount = consumerApplicationDetailRepository.getAllDataCount1(filterDTO.getDiscomId(),
//						filterDTO.getRegionId(), filterDTO.getCircleId(), Long.valueOf(divisionId),
//						filterDTO.getSubDivisionId(), filterDTO.getDcId());
//			}
//
//		}
//		response.setCode("200");
//		response.setMessage("Data found successfully");
//		response.setList(allDataCount);
//
//		return response;
//	}

	@GetMapping("/getAllApplicationByApplicationStatusByUserIdAndDC/{applicationStatusId}/{userId}/{dcId}/{divisionId}")
	public Response getAllApplicationByApplicationStatusByUserIdAndDC(@PathVariable String applicationStatusId,
			@PathVariable String userId, @PathVariable int dcId, @PathVariable int divisionId) {
		Response response = new Response();
		List<String> statusIdList = Arrays.asList(applicationStatusId.split(","));
		System.err.println("Before entering database: " + LocalDateTime.now());

		System.err.println("Before entering database: " + LocalDateTime.now());
		Map<String, Object> usersByUserID = userRepository.findUsersByUserID(userId);
		System.err.println(new Gson().toJson(usersByUserID));
		Object accessLevel = usersByUserID.get("ACCESS_LEVEL");

		List<Map<String, Object>> allDataCount = null;

		if (dcId == 0 && divisionId == 0) {
			if (accessLevel.equals("1")) {

				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						1l, filterDTO.getRegionId(), filterDTO.getCircleId(), filterDTO.getDivisionId(),
						filterDTO.getSubDivisionId(), filterDTO.getDcId());
			} else if (accessLevel.equals("2")) {

				Object object1 = usersByUserID.get("REGION_ID");

				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						filterDTO.getDiscomId(), Long.valueOf(object1.toString()), filterDTO.getCircleId(),
						filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), filterDTO.getDcId());
			} else if (accessLevel.equals("3")) {

				Object object1 = usersByUserID.get("CIRCLE_ID");

				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						filterDTO.getDiscomId(), filterDTO.getRegionId(), Long.valueOf(object1.toString()),
						filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), filterDTO.getDcId());
			} else if (accessLevel.equals("4")) {

				Object object1 = usersByUserID.get("DIV_ID");

				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
						Long.valueOf(object1.toString()), filterDTO.getSubDivisionId(), filterDTO.getDcId());
				System.out.println("" + new Gson().toJson(allDataCount));
			} else if (accessLevel.equals("5")) {

				Object object1 = usersByUserID.get("SUB_DIVISION_ID");

				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
						filterDTO.getDivisionId(), Long.valueOf(object1.toString()), filterDTO.getDcId());
			} else if (accessLevel.equals("6")) {

				Object object1 = usersByUserID.get("DISTRIBUTION_CENTER_ID");

				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
						filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), Long.valueOf(object1.toString()));
			}
		} else {

			if (dcId != 0) {
				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
						filterDTO.getDivisionId(), filterDTO.getSubDivisionId(), Long.valueOf(dcId));
			} else {
				ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
				allDataCount = consumerApplicationDetailRepository.getAllApplicationByApplicationStatus1(statusIdList,
						filterDTO.getDiscomId(), filterDTO.getRegionId(), filterDTO.getCircleId(),
						Long.valueOf(divisionId), filterDTO.getSubDivisionId(), filterDTO.getDcId());
			}

		}
		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(allDataCount);

		return response;
	}

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private DynamicQueryRepository dynamicQueryRepository;

//	@GetMapping("/getAllDataFromViewViaAccessLevel/{userId}")
	public Response getAllDataFromViewViaAccessLevel1(@PathVariable String userId) {
		Response response = new Response();
		System.err.println("Before entering database: " + LocalDateTime.now());
		Map<String, Object> usersByUserID = userRepository.findUsersByUserID(userId);
		System.err.println(new Gson().toJson(usersByUserID));
		Object accessLevel = usersByUserID.get("ACCESS_LEVEL");

		List<Map<String, Object>> allDataCount = null;

		Optional<DynamicQuery> optionalQuery = dynamicQueryRepository.findByQueryNameAndIsActive("GET_ALL_COUNT", 1);

		if (!optionalQuery.isPresent()) {
			throw new IllegalArgumentException("No dynamic query found for: GET_ALL_DATA");
		}
		DynamicQuery dynamicQuery = optionalQuery.get();

		String query = dynamicQuery.getQueryText();

		if (dynamicQuery == null || dynamicQuery.getQueryText() == null) {
			throw new IllegalArgumentException("No dynamic query found for: GET_ALL_DATA");
		}

		System.err.println("aaaaaaaaaaaa : " + dynamicQuery);
		ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
		Map<String, Object> params = new HashMap<>();
		params.put("discomId", filterDTO.getDiscomId());
		params.put("regionId", filterDTO.getRegionId());
		params.put("circleId", filterDTO.getCircleId());
		params.put("divisionId", filterDTO.getDivisionId());
		params.put("subDivisionId", filterDTO.getSubDivisionId());
		params.put("dcId", filterDTO.getDcId());
		if (accessLevel.equals("1")) {
			params.put("discomId", 1l);
			allDataCount = namedParameterJdbcTemplate.queryForList(query, params);

		} else if (accessLevel.equals("2")) {
			Object object1 = usersByUserID.get("REGION_ID");
			params.put("regionId", Long.valueOf(object1.toString()));
			allDataCount = namedParameterJdbcTemplate.queryForList(query, params);

		} else if (accessLevel.equals("3")) {
			Object object1 = usersByUserID.get("CIRCLE_ID");
			params.put("circleId", Long.valueOf(object1.toString()));
			allDataCount = namedParameterJdbcTemplate.queryForList(query, params);
		} else if (accessLevel.equals("4")) {
			Object object1 = usersByUserID.get("DIV_ID");
			params.put("divisionId", Long.valueOf(object1.toString()));
			allDataCount = namedParameterJdbcTemplate.queryForList(query, params);
		} else if (accessLevel.equals("5")) {
			Object object1 = usersByUserID.get("SUB_DIVISION_ID");
			params.put("subDivisionId", Long.valueOf(object1.toString()));

			allDataCount = namedParameterJdbcTemplate.queryForList(query, params);
		} else if (accessLevel.equals("6")) {
			Object object1 = usersByUserID.get("DISTRIBUTION_CENTER_ID");
			params.put("dcId", Long.valueOf(object1.toString()));
			allDataCount = namedParameterJdbcTemplate.queryForList(query, params);
		}

		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(allDataCount);

		return response;
	}

	@GetMapping("/getAllDataFromViewViaAccessLevelAndDc/{userId}/{dcId}/{divisionId}")
	public Response getAllDataFromViewViaAccessLevelAndDc(@PathVariable String userId, @PathVariable int dcId,
			@PathVariable int divisionId) {
		Response response = new Response();
		Map<String, Object> usersByUserID = userRepository.findUsersByUserID(userId);
		Object accessLevel = usersByUserID.get("ACCESS_LEVEL");
		List<Map<String, Object>> allDataCount = null;
		DynamicQuery byQueryName = dynamicQueryRepository.findByQueryName("GET_ALL_COUNT");
		if (Objects.isNull(byQueryName)) {
			throw new IllegalArgumentException("No dynamic query found for: GET_ALL_COUNT");
		}

		String query = byQueryName.getQueryText(); // Actual SQL Query
		System.err.println("Query to execute: " + query);
		ConsumerApplicationDetailsFilterDTO filterDTO = new ConsumerApplicationDetailsFilterDTO();
		System.err.println("filterDTO : " + new Gson().toJson(filterDTO));

		HashMap<String, Object> params = new HashMap<>();
		params.put("discomId", filterDTO.getDiscomId());
		params.put("regionId", filterDTO.getRegionId());
		params.put("circleId", filterDTO.getCircleId());
		params.put("divisionId", filterDTO.getDivisionId());
		params.put("subDivisionId", filterDTO.getSubDivisionId());
		params.put("dcId", filterDTO.getDcId());

		if (dcId == 0 && divisionId == 0) {
			if (accessLevel.equals("1")) {
				params.put("discomId", 1l);
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			} else if (accessLevel.equals("2")) {

				Object object1 = usersByUserID.get("REGION_ID");
				params.put("regionId", Long.valueOf(object1.toString()));
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			} else if (accessLevel.equals("3")) {

				Object object1 = usersByUserID.get("CIRCLE_ID");
				params.put("circleId", Long.valueOf(object1.toString()));
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			} else if (accessLevel.equals("4")) {

				Object object1 = usersByUserID.get("DIV_ID");
				params.put("divisionId", Long.valueOf(object1.toString()));
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			} else if (accessLevel.equals("5")) {

				Object object1 = usersByUserID.get("SUB_DIVISION_ID");
				params.put("subDivisionId", Long.valueOf(object1.toString()));
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			} else if (accessLevel.equals("6")) {

				Object object1 = usersByUserID.get("DISTRIBUTION_CENTER_ID");
				params.put("dcId", Long.valueOf(object1.toString()));
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			}
		} else {

			if (dcId != 0) {
				params.put("dcId", Long.valueOf(dcId));
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			} else {
				params.put("divisionId", Long.valueOf(divisionId));
				allDataCount = namedParameterJdbcTemplate.queryForList(byQueryName.getQueryText(), params);
			}

		}
		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(allDataCount);

		return response;
	}

}
