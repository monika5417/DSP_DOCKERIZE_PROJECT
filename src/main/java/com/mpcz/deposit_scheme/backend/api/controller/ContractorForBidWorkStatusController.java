package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.dto.ContractorForBidWorkStatusDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ContractorForBidWorkStatusService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Contractor For Bid Work Status Controller", description = "Rest api forWork Status .")
@RestController
@RequestMapping(RestApiUrl.CONTRACTOR_BID_WORK_STATUS_API)
public class ContractorForBidWorkStatusController {

	@Autowired
	private ContractorForBidWorkStatusService contractorForBidWorkStatusService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	private DryUtility dryUtility;

	@PostMapping("/save")
	public ResponseEntity<Response> save(@RequestBody ContractorForBidWorkStatus contractorForBidWorkStatus) {

		ContractorForBidWorkStatus workStatusResponse = contractorForBidWorkStatusService
				.save(contractorForBidWorkStatus);
		// logger.info("TableController response" + dataResponse);

		ConsumerApplicationDetail consumerAppObject = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(contractorForBidWorkStatus.getConsumerApplicationNumber());

		ApplicationStatus appStatusDb = null;
		if (workStatusResponse.getConWorkCompleteDate() != null) {

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
			consumerAppObject.setApplicationStatus(appStatusDb);
			consumerAppObject.setWorkCompletionDate(contractorForBidWorkStatus.getConWorkCompleteDate());
		}

		consumerApplictionDetailRepository.save(consumerAppObject);

		Response response = new Response();

		// if (workStatusResponse != null) {
		response.setCode("200");
		response.setMessage("Contractor Work Status Save Successfully");
		response.setList(Arrays.asList(workStatusResponse));

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	public ResponseEntity<Response> save(@RequestBody ContractorForBidWorkStatusDto contractorForBidWorkStatusDto) {

		ContractorForBidWorkStatus dataResponse = contractorForBidWorkStatusService
				.update(contractorForBidWorkStatusDto);
		if (dataResponse != null) {

			// logger.info("TableController response" + dataResponse);
			Response response = new Response();
			response.setCode("200");
			response.setMessage("Contractor Work Status Update Successfully");
			response.setList(Arrays.asList(dataResponse));

			return ResponseEntity.ok().body(response);
		}
		Response response = new Response();
		response.setCode("404");
		response.setMessage("Contractor Work Status Not Found");
		response.setList(Arrays.asList(dataResponse));

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/get/{consumerApplicationNo}")
	public ContractorForBidWorkStatus get(@PathVariable String consumerApplicationNo) {
		ContractorForBidWorkStatus consumerApplicationStatu = contractorForBidWorkStatusRepository
				.findByConsumerApplicationNo(consumerApplicationNo);

		System.out.println(consumerApplicationStatu + "------------------------------------------");
		return consumerApplicationStatu;
	}

	@PutMapping("/updatee")
	public ResponseEntity<Response> saveContractor(
			@RequestBody ContractorForBidWorkStatusDto contractorForBidWorkStatusDto) {

		List<ContractorForBidWorkStatus> contractorforBid = contractorForBidWorkStatusRepository
				.findByConsumerApplicationNumber(contractorForBidWorkStatusDto.getConsumerApplicationNumber());
		ConsumerApplicationDetail cad = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(contractorForBidWorkStatusDto.getConsumerApplicationNumber());

		for (ContractorForBidWorkStatus contractorForBidWorkStatus : contractorforBid) {

			contractorForBidWorkStatus.setDgmStcDate(contractorForBidWorkStatusDto.getDateOfDgmStc());
			contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);
		}
		ApplicationStatus appStatusDb = applicationStatusService
				.findById(ApplicationStatusEnum.WORK_COMPLETION_DONE.getId());
		cad.setDgmStcDate(contractorForBidWorkStatusDto.getDateOfDgmStc());
		cad.setApplicationStatus(appStatusDb);

		consumerApplictionDetailRepository.save(cad);

		Response response = new Response();
		// if (workStatusResponse != null) {
		response.setCode("200");
		response.setMessage("Contractor Work Status Update Successfully");
		response.setList(Arrays.asList(cad));
		return ResponseEntity.ok().body(response);
	}

	

//	@ApiOperation(value = "find by Application No Contractor  information", response = ContractorForBid.class);
//	@GetMapping("/conforbids-get/{applicationNo}")
//	public ResponseEntity<Response> findById(@PathVariable("applicationNo") String applicationNo) {
//		ContractorForBidWorkStatus conForBid = ContractorForBidWorkStatusRepository.findByConsumerApplicationNumber(applicationNo);
//		Response response = new Response();
//		response.setCode("200");
//		response.setMessage("success");
//		response.setList(Arrays.asList(conForBid));
//		return ResponseEntity.ok().body(response);
//	}

//	@GetMapping("/get")
//	public List<ContractorForBidWorkStatus> get(@PathVariable) {
//		return contractorForBidWorkStatusRepository.findAll();
//	}

//	@GetMapping("/get/{consumerApplicationNo}")
//	public ContractorForBidWorkStatus get(@PathVariable String consumerApplicationNo) {
//		 ContractorForBidWorkStatus consumerApplicationStatu = contractorForBidWorkStatusRepository.findByConsumerApplicationNo(consumerApplicationNo);
//		return consumerApplicationStatu;
//	}

	
//	Ae stc api's 11-08-2025 created by Monika Rajpoot
	@PatchMapping("/forwardApplicationToAEStc/{consumerApplicationNo}")
	public ResponseEntity<?> forwardApplicationToAEStc(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {

		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(consumerApplicationNo);
		if (validateConsumerApplication.getApplicationStatus().getApplicationStatusId().equals(27L)) {
			validateConsumerApplication.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.PENDING_FOR_INSPECTION_OF_WORK_BY_AE_STC.getId())
					.orElseThrow(() -> new ConsumerApplicationDetailException(
							new Response(HttpCode.NULL_OBJECT, "Application Status not found in database"))));
			ConsumerApplicationDetail updatedDataDB = consumerApplictionDetailRepository
					.save(validateConsumerApplication);
			return ResponseEntity
					.ok(Objects.isNull(updatedDataDB) ? new Response<>(HttpCode.NULL_OBJECT, "Data Not Updated.....")
							: new Response<>(HttpCode.UPDATED, "Application Status Updated Successfully....",
									Arrays.asList(updatedDataDB)));
		} else {
			return ResponseEntity.ok(
					new Response(HttpCode.NOT_ACCEPTABLE, "This Application Status is not eligible to call this Api : "
							+ validateConsumerApplication.getApplicationStatus().getApplicationStatusId()));
		}

	}
	

}
