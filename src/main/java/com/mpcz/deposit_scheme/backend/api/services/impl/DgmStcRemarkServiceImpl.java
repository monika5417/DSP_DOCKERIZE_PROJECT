package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorRemark;
import com.mpcz.deposit_scheme.backend.api.domain.DgmStcReason;
import com.mpcz.deposit_scheme.backend.api.domain.DgmStcRemark;
import com.mpcz.deposit_scheme.backend.api.dto.DgmStcRemarkDTO;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DgmStcRemarkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DgmStcResponseRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.DgmStcRemarkService;

@Service
public class DgmStcRemarkServiceImpl implements DgmStcRemarkService {

	@Autowired
	private DgmStcRemarkRepository dgmStcRemarkRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

	@Autowired
	DgmStcResponseRepository dgmStcResponseRepository;

	@Value("${dgm.stc.response}")
	private String dgmStcResponse;

	@Override
	public ResponseEntity<?> saveDgmStcRemark(DgmStcRemarkDTO dgmStcRemarkDTO) {
		Response response = new Response();
		DgmStcRemark save = null;

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(dgmStcRemarkDTO.getConsumerAppNo());
		if (findByConsumerApplicationNumber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application not found in database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}

		DgmStcRemark dgmStcRemark = new DgmStcRemark();
		dgmStcRemark.setConsumerAppNo(dgmStcRemarkDTO.getConsumerAppNo());
		dgmStcRemark.setRemark(dgmStcRemarkDTO.getRemark());
		dgmStcRemark.setStcId(dgmStcRemarkDTO.getStcId());
		dgmStcRemark.setStcName(dgmStcRemarkDTO.getStcName());
		dgmStcRemark.setCreatedDate(LocalDate.now().toString());
		if (dgmStcRemarkDTO.getUserRole() != null) {
			dgmStcRemark.setUserRole(dgmStcRemarkDTO.getUserRole());
		}
		List<DgmStcRemark> findAll = dgmStcRemarkRepository.findByConsumerAppNo(dgmStcRemarkDTO.getConsumerAppNo());

		dgmStcRemark.setRemarkCount(findAll.size() + 1);
//		"reason":["Wrong Document Uploaded","Work Not Compleated"]
		try {
			List<DgmStcReason> reasons = dgmStcRemarkDTO.getReasons();

			for (int i = 0; i < reasons.size(); i++) {
				DgmStcReason dgmStcReason = new DgmStcReason();
				dgmStcReason.setConsumerAppNo(dgmStcRemarkDTO.getConsumerAppNo());
				dgmStcReason.setReason(dgmStcRemarkDTO.getReasons().get(i).getReason());
				dgmStcResponseRepository.save(dgmStcReason);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Objects.isNull(dgmStcRemarkDTO.getUserRole()) || (Objects.equals(dgmStcRemarkDTO.getUserRole(), "AE STC")
				&& Objects.equals(dgmStcRemarkDTO.getApproved(), false) || Objects.equals(dgmStcRemarkDTO.getUserRole(), "DGM STC") )) {
			ContractorForBidWorkStatus findByConsumerApplicationNo = contractorForBidWorkStatusRepository
					.findByConsumerApplicationNo(dgmStcRemarkDTO.getConsumerAppNo());

			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("consumer_application_no", dgmStcRemarkDTO.getConsumerAppNo());
			requestBody.put("dgm_stc_name", dgmStcRemarkDTO.getStcName());
			requestBody.put("revert_date", LocalDate.now().toString());
			requestBody.put("dgm_stc_revert_remark", dgmStcRemarkDTO.getRemark());
			requestBody.put("is_dgm_accepted_wc", false);
			requestBody.put("User_Id", findByConsumerApplicationNo.getUserId());
			requestBody.put("consumers", null);

			RestTemplate restTemplate = new RestTemplate();

			String url = dgmStcResponse + "/tkc/dgm_stc_response";
			System.out.println("url :" + url);

			// UAT Code
			ResponseEntity<Map> postForEntity = restTemplate.postForEntity(url, requestBody, Map.class);

			// Production Code
//    ResponseEntity<Map> postForEntity = restTemplate.postForEntity("https://qcportal.mpcz.in/tkc/dgm_stc_response", requestBody, Map.class);
			System.out.println("The result of Post api is :" + postForEntity.getBody());
			if (postForEntity != null) {
				save = dgmStcRemarkRepository.save(dgmStcRemark);
			}
		}
		ApplicationStatus appStatusDb = null;
		if (save != null) {
			if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(27L)
					|| findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(48L)) {
				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.NOTICE_TO_CONTRACTOR_FOR_SHORTCOMING_BY_DGM_STC.getId());
			} else {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("This Application Status is not eligible to call this Api : "
						+ findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId());
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
			consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
			response.setList(Arrays.asList(dgmStcRemarkDTO));

		} else {
			if ((Objects.equals(dgmStcRemarkDTO.getUserRole(), "AE STC")
					&& Objects.equals(dgmStcRemarkDTO.getApproved(), true)
					&& findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().equals(48L))) {
				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
			} else {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("This Application Status is not eligible to call this Api : "
						+ findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId());
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
			consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
			response.setList(Arrays.asList(dgmStcRemarkDTO));
		}
		response.setCode(HttpCode.OK);
		response.setMessage("Data saved Successfully");
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@Override
	public ResponseEntity<Response> getAllDgmStcRemark(String consumerAppNo) {
		Response response = new Response();

		List<DgmStcRemark> findAllByConsumerAppNo = dgmStcRemarkRepository.findAllByConsumerAppNo(consumerAppNo);
		if (findAllByConsumerAppNo.isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application no. not found");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode("200");
		response.setMessage("Data Retrieved successfully");
		response.setList(Arrays.asList(findAllByConsumerAppNo));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
