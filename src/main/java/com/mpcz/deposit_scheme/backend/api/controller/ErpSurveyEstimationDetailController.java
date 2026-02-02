package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ErpSurveyEstimationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Kva25Dtr;
import com.mpcz.deposit_scheme.backend.api.domain.Kva63Dtr;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpSurveyEstimationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.repository.ErpSurveyEstimationDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.KV11_LINERepository;
import com.mpcz.deposit_scheme.backend.api.repository.Kva25DtrRepository;
import com.mpcz.deposit_scheme.backend.api.repository.Kva63DtrRepository;
import com.mpcz.deposit_scheme.backend.api.repository.LTLineRepository;
import com.mpcz.deposit_scheme.backend.api.repository.TappingDpRepository;
import com.mpcz.deposit_scheme.backend.api.response.ErpSurveyResponse;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

/**
 * @author Monika Rajpoot
 * @since 02-Jan-2026
 * @description ErpSurveyEstimationDetailRepository.java class description
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/erp-estimate")
public class ErpSurveyEstimationDetailController {

	@Autowired
	private ErpSurveyEstimationDetailRepository erpSurveyEstimationDetailRepository;

	@Autowired
	private DryUtility dryUtility;

	@Autowired
	private Kva25DtrRepository kva25DtrRepository;

	@Autowired
	private Kva63DtrRepository kva63DtrRepository;

	@Autowired
	private KV11_LINERepository kV11_LINERepository;

	@Autowired
	private LTLineRepository lTLineRepository;

	@Autowired
	private TappingDpRepository tappingDpRepository;

	@PostMapping("/save")
	public ResponseEntity<?> saveErpSurveyDetails(@RequestBody ErpSurveyEstimationDetail erpSurveyEstimationDetail,
			BindingResult bindingResult) throws ConsumerApplicationDetailException, FormValidationException {

		dryUtility.checkValidationError(bindingResult);
		dryUtility.validateConsumerApplication(erpSurveyEstimationDetail.getConsumerApplicationNo());

		ErpSurveyEstimationDetail save = erpSurveyEstimationDetailRepository.save(erpSurveyEstimationDetail);
		return ResponseEntity.ok(save == null ? new Response<>(HttpCode.NULL_OBJECT, "Data Not Saved")
				: new Response<>(HttpCode.CREATED, "Data Saved Successfully", Arrays.asList(save)));
	}

	@GetMapping("/getEstimateQty/{consumerApplicationNo}")
	public ResponseEntity<?> getEstimateQty(@PathVariable String consumerApplicationNo)
			throws ErpSurveyEstimationDetailException {
		ErpSurveyResponse ErpSurveyResponse = new ErpSurveyResponse();
		List<Kva25Dtr> all25Kva = null;
		List<Kva63Dtr> all63Kva = null;
		List<Map<String, Object>> ltLineQtyMap = null;
		ErpSurveyEstimationDetail erpSurvey = erpSurveyEstimationDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ErpSurveyEstimationDetailException(new Response<>(HttpCode.NULL_OBJECT,
						"Consumer Application Not Found For Estimate Generation")));
		if (erpSurvey.getProposedOnline().equals("true")) {
			if ("25 KVA".equals(erpSurvey.getCapacity())
					&& (erpSurvey.getLtLineDistance() != null && erpSurvey.getLtLineDistance() > 0)) {
				all25Kva = kva25DtrRepository.findAll();
				ErpSurveyResponse.setKva25DtrList(all25Kva);

				ltLineQtyMap = prepareLTLineQtyMap(erpSurvey.getLtLineDistance());
				ErpSurveyResponse.setLtLINE(ltLineQtyMap);

			} else if ("63 KVA".equals(erpSurvey.getCapacity())
					&& (erpSurvey.getLtLineDistance() != null && erpSurvey.getLtLineDistance() > 0)) {
				all63Kva = kva63DtrRepository.findAll();
				ErpSurveyResponse.setKva63DtrList(all63Kva);

				ltLineQtyMap = prepareLTLineQtyMap(erpSurvey.getLtLineDistance());
				ErpSurveyResponse.setLtLINE(ltLineQtyMap);

			} else if ("25 KVA".equals(erpSurvey.getCapacity())) {
				all25Kva = kva25DtrRepository.findAll();
				ErpSurveyResponse.setKva25DtrList(all25Kva);

			} else if ("63 KVA".equals(erpSurvey.getCapacity())) {
				all63Kva = kva63DtrRepository.findAll();
				ErpSurveyResponse.setKva63DtrList(all63Kva);

			}
		} else {

		}
		return ResponseEntity.ok(ErpSurveyResponse == null ? new Response<>(HttpCode.NULL_OBJECT, "Data Not Found")
				: new Response<>(HttpCode.OK, "Data Retrieved Successfully", Arrays.asList(ErpSurveyResponse)));
	}

	public List<Map<String, Object>> prepareLTLineQtyMap(long distance) {
		long poleCount = (long) Math.ceil(distance / 50.0);
		Map<String, Object> qtyMap = new HashMap<>();
		qtyMap.put("M-0501004", poleCount);
		qtyMap.put("M-0404205", (long) Math.ceil(poleCount * 0.5));
		qtyMap.put("M-0404219", (long) Math.ceil(poleCount * 0.833333));
		qtyMap.put("M-0404223", poleCount);
		qtyMap.put("M-0404282", poleCount * 3);
		qtyMap.put("M-0404283", poleCount * 2);
		qtyMap.put("M-0402107", Math.ceil(poleCount * 0.0525));
		qtyMap.put("M-0502230", (long) Math.ceil(poleCount * 0.666667));
		qtyMap.put("M-0403019", (long) Math.ceil(poleCount * 3.666667));
		qtyMap.put("M-0502091", (long) Math.ceil(poleCount * 1.333333));
		qtyMap.put("M-0601036", (long) Math.ceil(poleCount * 0.666667));
		qtyMap.put("M-0001001", poleCount);
		qtyMap.put("P-0702056", poleCount);
		qtyMap.put("P-0001037", (long) Math.ceil(poleCount * 0.2));
		qtyMap.put("M-0403208", (long) Math.ceil(poleCount * 1.5));
		qtyMap.put("M-0403096", poleCount);
		qtyMap.put("M-0403173", (long) Math.ceil(poleCount * 0.8));
		qtyMap.put("M-0304076", poleCount);

		List<Map<String, Object>> allData = lTLineRepository.findAllData();
		List<Map<String, Object>> updatedLtLineList = new ArrayList<>();

		System.err.println("qtyMap : " + new Gson().toJson(qtyMap));
		for (Map<String, Object> dbMap : allData) {

			Map<String, Object> modifiableMap = new HashMap<>(dbMap);

			String itemCode = (String) modifiableMap.get("ITEM_CODE");

			if (qtyMap.containsKey(itemCode)) {
				modifiableMap.put("QTY", qtyMap.get(itemCode));
			}

			updatedLtLineList.add(modifiableMap);
		}
		System.err.println("dbMap : " + new Gson().toJson(updatedLtLineList));
		return updatedLtLineList;

	}

	public static long roundUpFormula(long am2) {
		return (long) Math.ceil(am2 / 70.0) - 1;
	}

	public static long roundUp(double value) {
		return (long) Math.ceil(value);
	}

	private double roundTo3Decimal(double value) {
		return Math.round(value * 1000.0) / 1000.0;
	}

}
