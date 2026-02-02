package com.mpcz.deposit_scheme.backend.api.controller;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ManualPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/manualpayments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ManualPaymentController {

	@Autowired
	private ManualPaymentService manualPaymentService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
	
	@GetMapping("/getAllManualPayments")
	public List<ManualPayment> getAllManualPayments() {
		return manualPaymentService.getAllManualPayments();
	}

	@PostMapping("/createManualPayment")
	public ResponseEntity<?> createManualPayment(@RequestBody ManualPayment manualPayment) {
		return manualPaymentService.createManualPayment(manualPayment);
	}

	@GetMapping("/getPaymentByApplication/{consumerAppNo}")
	public Response getPaymentByApplication(@PathVariable String consumerAppNo) {

	return	manualPaymentService.getPaymentByApplication(consumerAppNo);
	}
	
	@GetMapping("/getManualPaymentByBillRefNo/{billRefNo}")
	public Response getManualPaymentByBillRefNo(@PathVariable String billRefNo) {
		Response response = new Response();

		ManualPayment paymentByApplication = manualPaymentService.getManualPaymentByBillRefNo(billRefNo);
		if (paymentByApplication == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application not found for Manual Payment");
			return response;
		}
		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository.findByConsumerApplicationNumber(paymentByApplication.getConAppNo());
		paymentByApplication.setConsumerName(findByConsumerApplicationNumber.getConsumerName());
		response.setCode(HttpCode.OK);
		response.setMessage("Data Retrive Successfully ");
		response.setList((Arrays.asList(paymentByApplication)));
		return response;

	}

	
	
	
}
