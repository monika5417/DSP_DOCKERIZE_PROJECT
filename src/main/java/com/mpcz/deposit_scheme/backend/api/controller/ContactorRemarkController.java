package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorRemark;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorRemarkRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;

@RestController
@RequestMapping("/api/contractor_remark")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContactorRemarkController {

	@Autowired
	private ContractorRemarkRepository contractorRemarkRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@PostMapping("/addContractorRemark")
	public ResponseEntity<Response> addContractorRemark(@RequestBody ContractorRemark contractorRemark) {
		Response response = new Response();
		ApplicationStatus appStatusDb = null;
		try {

			ConsumerApplicationDetail consumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(contractorRemark.getConsumerAppNo());
			if (consumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Application no. not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			if (consumerApplicationNumber.getApplicationStatus().getApplicationStatusId() != 31) {
				return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
						"You are not authorize to call this api at this stage of application status"));
			}
			ContractorRemark conRemark = new ContractorRemark();
			conRemark.setConName(contractorRemark.getConName());
			conRemark.setConRemark(contractorRemark.getConRemark());
			conRemark.setConId(contractorRemark.getConId());
			conRemark.setConsumerAppNo(contractorRemark.getConsumerAppNo());

			ContractorRemark save = contractorRemarkRepository.save(conRemark);
			if (save != null) {
				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
				consumerApplicationNumber.setApplicationStatus(appStatusDb);
				consumerApplictionDetailRepository.save(consumerApplicationNumber);

			}
			response.setCode("200");
			response.setMessage("Data Saved successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@GetMapping("/getAllConRemark/{consumerAppNo}")
	public ResponseEntity<Response> getAllConRemark(@PathVariable String consumerAppNo) {
		Response response = new Response();

		List<ContractorRemark> findAllByConsumerAppNo = contractorRemarkRepository
				.findAllByConsumerAppNo(consumerAppNo);
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
