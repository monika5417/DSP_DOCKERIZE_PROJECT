package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.DynamicQuery;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DynamicQueryRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "MisController", description = "Rest api for Mis Controller ")
@RestController
@RequestMapping("/mis_details")
public class MisController {

	@Autowired
	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private DynamicQueryRepository dynamicQueryRepository;

	@GetMapping("/mis")
	Response getMisDetailsByDcCode(@RequestParam(value = "dcCode", required = false) String dcCode,
			@RequestParam(value = "circleCode", required = false) String circleCode,
			@RequestParam(value = "divisionCode", required = false) String divisionCode,
			@RequestParam(value = "regionCode", required = false) String regionCode) {
		Response response = new Response();

		List<Map<String, ?>> detailsByDcCodeForMis = consumerApplictionDetailRepository.getDetailsByDcCodeForMis(dcCode,
				circleCode, divisionCode, regionCode);
		if (detailsByDcCodeForMis.isEmpty()) {
			response.setCode("404");
			response.setMessage("Data not found");
			return response;
		}
		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(detailsByDcCodeForMis);
		return response;
	}

	@GetMapping("/bill_desk_mis/{toDate}/{fromDate}")
	Response getBillDeskMis(@PathVariable("toDate") String toDate, @PathVariable("fromDate") String fromDate) {
		Response response = new Response();

		List<Map<String, ?>> detailsMisDate = consumerApplictionDetailRepository.getBillDeskMisByDate(toDate, fromDate);
		if (detailsMisDate.isEmpty()) {
			response.setCode("404");
			response.setMessage("Data not found");
			return response;
		}
		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(detailsMisDate);
		return response;
	}

	@GetMapping("/bill_desk/{toDate}/{fromDate}")
	Response getBillDesk(@PathVariable("toDate") String toDate, @PathVariable("fromDate") String fromDate) {
		Response response = new Response();

		List<Map<String, ?>> detailsMisDate = consumerApplictionDetailRepository.getBillDesk(toDate, fromDate);
		if (detailsMisDate.isEmpty()) {
			response.setCode("404");
			response.setMessage("Data not found");
			return response;
		}
		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(detailsMisDate);
		return response;
	}

	@GetMapping("/getCraData/{date}")
	Response getCraData(@PathVariable("date") String date) {

		Response response = new Response();

		List<Map<String, Object>> craData = consumerApplictionDetailRepository.getCraData(date);
		if (craData.isEmpty()) {
			response.setCode("404");
			response.setMessage("Data not found");
			return response;
		}
		response.setCode("200");
		response.setMessage("Data found successfully");
		response.setList(craData);
		return response;
	}

// api created on 17-Feb-2025
	@GetMapping("/erp_bill_desk_mis/{toDate}/{fromDate}")
	public ResponseEntity<?> getErpBillDeskMis(@PathVariable String toDate, @PathVariable String fromDate) {
		System.err.println("toDate : " + toDate);
		System.err.println("fromDate : " + fromDate);

		if (toDate == null || toDate.trim().isEmpty()) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "toDate should not be null"));
		}
		if (fromDate == null || fromDate.trim().isEmpty()) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "fromDate should not be null"));
		}
		List<Map<String, ?>> result = consumerApplictionDetailRepository.getErpBillDeskMis(toDate, fromDate);

		// Convert HashMap to LinkedHashMap to maintain order
		List<Map<String, Object>> orderedList = new ArrayList<>();

		for (Map<String, ?> map : result) {
			Map<String, Object> orderedMap = new LinkedHashMap<>();

			// Define the correct order of keys
			List<String> orderedKeys = Arrays.asList("Transaction Source", "OU", "CIRCLE", "DIVISION", "DC", "Class",
					"Transaction Type", "Reference No/Sales Order No", "Invoice Date", "GL Date", "Amount", "Currency",
					"Customer Name", "Number (Address)", "Payment Term", "Item Description", "Quantity", "Unit Price",
					"Comments (Legacy System)", "GL Account Combination", "Tax Category", "Reg Amount(62.932)",
					"SUPERVISION CHARGES(62.925)", "Supply Afford Charge(55.150)", "Sys Development Charge(55.160)",
					"JE_RETURN_AMOUNT(46.115)", "CGST(46.948)", "SGST(46.949)", "DEPOSIT(47.320)",
					"AVEDAN SHULK(62.936) @RS 2495", "AVEDAN SHULK  (47.310)@RS 5", "SECURITY DEPOSIT(48.150)",
					"PAID BY APPLICANT(47.345)", "Slump (47.337)", "TDS at 2%(27.425)", "GST_NUMBER()");

			// Insert values in correct order
			for (String key : orderedKeys) {
				orderedMap.put(key, map.get(key));
			}

			orderedList.add(orderedMap);
		}
		System.err.println("aaaaaaaaaaaa : " + new Gson().toJson(orderedList));
		return ResponseEntity
				.ok(orderedList.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data not found for these dates")
						: new Response(HttpCode.OK, "Data retrieved successfully", orderedList));
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@GetMapping("/ErpStagingData")
	public void callDataToErpProcedure() {
		jdbcTemplate.execute("BEGIN DATA_TO_ERP(); END;");
	}

//	@GetMapping("/ErpStagingData")
//	@Transactional
//	public void pushDataToErp() {
//		erpStagingDataRepository.callDataToErpProcedure();
//		System.out.println("Data successfully pushed to ERP!");
//	}

	@Transactional(readOnly = true)
	@GetMapping("/refundAmountMIS")
	public ResponseEntity<?> refundAmountMIS() {
		DynamicQuery byQueryName = dynamicQueryRepository.findByQueryName("REFUND_AMOUNT_MIS");
		if (Objects.isNull(byQueryName)) {
			throw new IllegalArgumentException("No dynamic query found for: REFUND_AMOUNT_MIS");
		}

		String query = byQueryName.getQueryText(); // Actual SQL Query
		System.err.println("Query to execute: " + query);

		List<Map<String, Object>> resultList = namedParameterJdbcTemplate.queryForList(query, new HashMap<>());
		return ResponseEntity
				.ok(Objects.isNull(resultList) ? new Response<>(HttpCode.NULL_OBJECT, "No data found for refund mis")
						: new Response<>(HttpCode.OK, "Data Retrieved Successfully", resultList));
	}

	@GetMapping("/getFinancePaymentDataForGSTApplication/{yearMonth}")
	Response getFinancePaymentDataForGSTApplication(@PathVariable("yearMonth") String yearMonth) {

		DynamicQuery financeGstApplicationList = dynamicQueryRepository
				.findByQueryName("FINANCE_GST_ALL_APPLICATION_QUERY");

		if (Objects.isNull(financeGstApplicationList)) {
			throw new IllegalArgumentException("No dynamic query found for: FINANCE_GST_ALL_APPLICATION_QUERY");
		}

		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("monthyear", yearMonth);
		List<Map<String, Object>> financeGstList = namedParameterJdbcTemplate
				.queryForList(financeGstApplicationList.getQueryText(), hashMap);

		return (Objects.isNull(financeGstList) || financeGstList.isEmpty())
				? new Response(HttpCode.NULL_OBJECT, "No data found for Finance GST List")
				: new Response(HttpCode.OK, "Data Retrieved Successfully", financeGstList);

	}
	
	
	@PostMapping("/execute-query")
	public ResponseEntity<?> executeDynamicQuery(
	        @RequestParam String queryName,
	        @RequestBody(required = false) Map<String, Object> params) {

	    DynamicQuery dq =
	        dynamicQueryRepository.findByQueryName(queryName);

	    if (dq == null) {
	        return ResponseEntity.ok(
	            new Response<>(HttpCode.NULL_OBJECT,
	                "Query not found or inactive")
	        );
	    }

	    // ---------- PARAM VALIDATION ----------
	    if (dq.getParamKeys() != null && !dq.getParamKeys().isEmpty()) {

	    	Set<String> requiredParams = Arrays.stream(dq.getParamKeys().split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());


	        if (params == null || !params.keySet().containsAll(requiredParams)) {
	            return ResponseEntity.badRequest().body(
	                new Response<>(HttpCode.NOT_ACCEPTABLE,
	                    "Required params: " + requiredParams)
	            );
	        }
	    }

	    // ---------- EXECUTION ----------
	    List<Map<String, Object>> data =
	    	    namedParameterJdbcTemplate.queryForList(
	    	        dq.getQueryText(),
	    	        params == null ? Collections.emptyMap() : params
	    	    );

	    if (data.isEmpty()) {
	        return ResponseEntity.ok(
	            new Response<>(HttpCode.NULL_OBJECT, "No data found")
	        );
	    }

	    return ResponseEntity.ok(
	        new Response<>(HttpCode.OK, "Success", data)
	    );
	}


}
