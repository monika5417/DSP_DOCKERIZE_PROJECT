package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.domain.SanchayPaymentDetails;
import com.mpcz.deposit_scheme.backend.api.dto.SanchayAoRejectedDto;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationStatusException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.PoseMachinePostDataService;

@RestController
@RequestMapping("/poseMachine")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PoseMachinePostDataController {

	@Autowired
	private PoseMachinePostDataService poseMachinePostDataService;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDataRepository;

	@PostMapping("/savedata")
	public Response savePoseData(@RequestBody PoseMachinePostData poseMachinePostData) {
		Response response = new Response();
		PoseMachinePostData byApplicationNumber = null;
//		code added by monika 6-august-2024
		if (Objects.isNull(poseMachinePostData.getPaymentType())) {
			byApplicationNumber = poseMachinePostDataRepository
					.findByApplicationNumber(poseMachinePostData.getApplicationNumber());
		} else {
			byApplicationNumber = poseMachinePostDataRepository.findDataByApplicationNumber(
					poseMachinePostData.getApplicationNumber(), poseMachinePostData.getPaymentType());
		}
		if (byApplicationNumber != null) {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Application number already submitted");
			return response;
		}

		if (poseMachinePostData.getApplicationNumber().isEmpty()
				|| poseMachinePostData.getApplicationNumber() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application number is empty ");
			return response;
		}
		int compareTo = poseMachinePostData.getTxnAmount().compareTo(new BigDecimal(0.0));
		if (0 == compareTo) {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Amount  is zero");
			return response;
		}
		if (0 >= compareTo) {
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("Amount is nagitive ");
			return response;
		}

		try {
			String poseMachinePostDatadb = poseMachinePostDataService.save(poseMachinePostData);
			if (poseMachinePostDatadb.equals("data update succefully")) {
				response.setCode(HttpCode.OK);
				response.setMessage("Data Saved Successfully ");
				response.setList(Arrays.asList(poseMachinePostDatadb));
				return response;

			} else {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Data Not Updated ");
				return response;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/getDemandFeesPaymentDetails/{consumerApplicationNo}")
	public Object getDemandFeesPaymentDetails(@PathVariable String consumerApplicationNo) {
		return poseMachinePostDataService.getDemandFeesPaymentDetails(consumerApplicationNo);
	}

	@PostMapping("/sanchayPaymentData")
	public ResponseEntity<?> sanchayPaymentData(@Valid @RequestBody SanchayPaymentDetails SanchayPaymentDetails,
			BindingResult bindingResult)
			throws ConsumerApplicationDetailException, FormValidationException, ApplicationStatusException {
		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});

			throw new FormValidationException(
					new Response(ResponseMessage.FORM_VALIDATION_ERROR, ResponseCode.FORM_VALIDATION_ERROR, errorList));
		}
		SanchayPaymentDetails recordOfSanchayPostDataDB = poseMachinePostDataService
				.recordOfSanchayPostData(SanchayPaymentDetails);
		return ResponseEntity
				.ok(Objects.isNull(recordOfSanchayPostDataDB) ? new Response(HttpCode.NULL_OBJECT, "Data Not saved")
						: new Response<>(HttpCode.CREATED, "Data Saved Successfully",
								Arrays.asList(recordOfSanchayPostDataDB)));
	}

	@PutMapping("/rejectApplicationByAO")
	public ResponseEntity<?> rejectApplicationByAO(@Valid @RequestBody SanchayAoRejectedDto sanchayAoRejectedDto,
			BindingResult bindingResult)
			throws ConsumerApplicationDetailException, ApplicationStatusException, FormValidationException {

		if (bindingResult.hasErrors()) {
			List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();

			bindingResult.getFieldErrors().stream().forEach(f -> {
				ErrorDetails error = new ErrorDetails(new Date(), f.getDefaultMessage(),
						f.getField() + ":" + f.getDefaultMessage());
				errorList.add(error);
			});

			throw new FormValidationException(
					new Response(ResponseMessage.FORM_VALIDATION_ERROR, ResponseCode.FORM_VALIDATION_ERROR, errorList));
		}

		SanchayPaymentDetails aoRejectedSanchayApplication = poseMachinePostDataService
				.aoRejectedSanchayApplication(sanchayAoRejectedDto);
		return ResponseEntity.ok(
				Objects.isNull(aoRejectedSanchayApplication) ? new Response<>(HttpCode.NULL_OBJECT, "Data Not Updated ")
						: new Response<>(HttpCode.UPDATED, "Data updated successfully"));
	}

}
