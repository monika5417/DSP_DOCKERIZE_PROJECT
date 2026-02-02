package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAccountDetails;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.DuplicateRefund;
import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;
import com.mpcz.deposit_scheme.backend.api.domain.RefundRejectedRemark;
import com.mpcz.deposit_scheme.backend.api.dto.DuplicatePaymentRefundDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.exception.RefundAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DuplicateRefundRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerAccountDetailsService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/consumer_account_deatils")
public class ConsumerAccountDetailsController {

	@Autowired
	private ConsumerAccountDetailsService consumerAccountDetailsService;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;
	
	@Autowired
	private DuplicateRefundRepository duplicateRefundRepository;

	@PostMapping("/saveConsumerAccountDetails")
	public ResponseEntity<?> saveConsumerAccountDetails(@RequestPart String consumerAccountDetails,
			@RequestPart MultipartFile chequeBookOrPaasbook,
			@RequestPart MultipartFile docPanNo,
			@RequestPart(required = false) MultipartFile docAddressProof,
			@RequestPart(required = false) MultipartFile docNotry,
			@RequestPart(required = false) MultipartFile docAuthorizedLetter,
			@RequestPart(required = false) MultipartFile docRequestLetter)
			throws DocumentTypeException, ConsumerApplicationDetailException {
		if (chequeBookOrPaasbook.isEmpty()) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "chequeOrPassbookFile can not be null"));
		}
		if (docPanNo.isEmpty()) {
			return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "panNo file can not be null"));
		}
		ConsumerAccountDetails consumerAccountDetailsSaved = consumerAccountDetailsService.save(consumerAccountDetails,
				chequeBookOrPaasbook, docPanNo, docAddressProof, docNotry, docAuthorizedLetter, docRequestLetter);

		return ResponseEntity.ok(Objects.isNull(consumerAccountDetailsSaved)
				? new Response(HttpCode.NULL_OBJECT, "Consumer Account Details Not saved")
				: new Response(HttpCode.CREATED, "Consumer Account Details Saved",
						Arrays.asList(consumerAccountDetailsSaved)));
	}

	@GetMapping("/consumer/getConsumerApplicationDuplicateRefundDetails/{consumerApplicationNo}")
	public ResponseEntity<?> getConsumerApplicationDuplicateRefundDetails(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		System.err.println("consumer application number : " + consumerApplicationNo);

		DuplicatePaymentRefundDto duplicateRefundDetails = consumerAccountDetailsService
				.getConsumerApplicationDuplicateRefundDetails(consumerApplicationNo);
		return ResponseEntity.ok(Objects.isNull(duplicateRefundDetails)
				? new Response(HttpCode.NULL_OBJECT, "Details not found for given application no.")
				: new Response(HttpCode.OK, "Data Retrieved Successfully", Arrays.asList(duplicateRefundDetails)));

	}

	@PostMapping("/consumer/saveDuplicateRefundDetails")
	public ResponseEntity<?> saveDuplicateRefundDetails(@RequestBody DuplicateRefund duplicateRefund)
			throws ConsumerApplicationDetailException, ErpEstimateAmountException {
		if (Objects.isNull(duplicateRefund)) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "duplicateRefund should not be null"));
		}

		DuplicateRefund saveDuplicateRefundDetails = consumerAccountDetailsService
				.saveDuplicateRefundDetails(duplicateRefund);
		return ResponseEntity.ok(Objects.isNull(saveDuplicateRefundDetails)
				? new Response(HttpCode.NULL_OBJECT, "Data not saved")
				: new Response(HttpCode.CREATED, "Data saved successfully", Arrays.asList(saveDuplicateRefundDetails)));

	}

	@GetMapping("/getConsumerAccountDetails/{consumerApplicationNo}")
	public ResponseEntity<?> getConsumerAccountDetails(@PathVariable String consumerApplicationNo) {
		ConsumerAccountDetails consumerAccountDetailsDB = consumerAccountDetailsService
				.getConsumerAccountDetails(consumerApplicationNo);
		return ResponseEntity.ok(Objects.isNull(consumerAccountDetailsDB)
				? new Response(HttpCode.NULL_OBJECT, "consumer application not found in database")
				: new Response(HttpCode.OK, "data retrieved successfully", Arrays.asList(consumerAccountDetailsDB)));
	}

	@PutMapping("/user/dgmApprovalForDuplicateRefund")
	public ResponseEntity<?> dgmApprovalForDuplicateRefund(@RequestParam String consumerApplicationNo,
			@RequestParam boolean dgmApproval, @RequestParam String dgmId, @RequestParam String dgmName)
			throws ConsumerApplicationDetailException {

		DuplicateRefund dgmApprovalForDuplicateRefund = consumerAccountDetailsService
				.dgmApprovalForDuplicateRefund(consumerApplicationNo, dgmApproval, dgmId, dgmName);

		return ResponseEntity.ok(Objects.isNull(dgmApprovalForDuplicateRefund)
				? new Response(HttpCode.NULL_OBJECT, "data not updated the object is null")
				: new Response(HttpCode.UPDATED, "data updated successfully",
						Arrays.asList(dgmApprovalForDuplicateRefund)));
	}

	@PutMapping("/user/gmApprovalForDuplicateRefund")
	public ResponseEntity<?> gmApprovalForDuplicateRefund(@RequestParam String consumerApplicationNo,
			@RequestParam boolean gmApproval, @RequestParam String gmId, @RequestParam String gmName)
			throws ConsumerApplicationDetailException {

		DuplicateRefund dgmApprovalForDuplicateRefund = consumerAccountDetailsService
				.gmApprovalForDuplicateRefund(consumerApplicationNo, gmApproval, gmId, gmName);

		return ResponseEntity.ok(Objects.isNull(dgmApprovalForDuplicateRefund)
				? new Response(HttpCode.NULL_OBJECT, "data not updated the object is null")
				: new Response(HttpCode.UPDATED, "data updated successfully",
						Arrays.asList(dgmApprovalForDuplicateRefund)));
	}

	@PutMapping("/user/financeAoDuplicateRefund")
	public ResponseEntity<?> financeAoDuplicateRefund(@RequestParam String consumerApplicationNo,
			@RequestParam boolean financeApproval, @RequestParam String financeAoId, @RequestParam String financeName)
			throws RefundAmountException, ConsumerApplicationDetailException {

		DuplicateRefund financeAoDuplicateRefundDB = consumerAccountDetailsService
				.financeAoDuplicateRefund(consumerApplicationNo, financeApproval, financeAoId, financeName);
		return Objects.isNull(financeAoDuplicateRefundDB)
				? ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "data not updated the object is null"))
				: ResponseEntity.ok(new Response(HttpCode.UPDATED, "Data Updated Successfully",
						Arrays.asList(financeAoDuplicateRefundDB)));
	}

	@GetMapping("/getAllDuplicateRefundApplicationByApplicationNo/{consumerApplicationNo}")
	public ResponseEntity<?> getAllDuplicateRefundApplicationByApplicationNo(@PathVariable String consumerApplicationNo){
		List<DuplicateRefund> duplicateRefundListDB =  duplicateRefundRepository.getAllDuplicateRefundApplicationByApplicationNo(consumerApplicationNo);
		return ResponseEntity.ok(duplicateRefundListDB.isEmpty()?new Response(HttpCode.NULL_OBJECT,"Application no. not found in Duplicate Refund Table"):new Response(HttpCode.OK,"Data Retrieved Successfully",duplicateRefundListDB));
	}
	
	@GetMapping("/getAllDuplicateRefundApplication")
	public ResponseEntity<?> getAllDuplicateRefundApplicationByApplicationNo(){
		List<DuplicateRefund> duplicateRefundListDB =  duplicateRefundRepository.findAll();
		return ResponseEntity.ok(duplicateRefundListDB.isEmpty()?new Response(HttpCode.NULL_OBJECT,"Application no. not found in Duplicate Refund Table"):new Response(HttpCode.OK,"Data Retrieved Successfully",duplicateRefundListDB));
	}
	
}
