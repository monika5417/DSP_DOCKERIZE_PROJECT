package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.VendorWorkOrder;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.VendorWorkOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Work OrderController", description = "Rest api for Vendor Work Order Controller.")
@RestController
@RequestMapping(RestApiUrl.WORK_ORDEER_SAVE_API)
public class VendorWorkOrderController {

	@Autowired
	private VendorWorkOrderService vendorWorkOrderService;

	@PostMapping("/save")
	public ResponseEntity<Response> save(@RequestBody VendorWorkOrder venderWorkOrder) {

		VendorWorkOrder workOrder = vendorWorkOrderService.save(venderWorkOrder);

		// logger.info("TableController response" + dataResponse);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("Work Order save successfully ");
		response.setList(Arrays.asList(workOrder));

		return ResponseEntity.ok().body(response);
	}

	@ApiOperation(value = "update Work Order", response = VendorWorkOrder.class)
	@PutMapping("/update")
	public ResponseEntity<Response> update(@RequestBody VendorWorkOrder vendorWorkOrder) {

		VendorWorkOrder workOrder = vendorWorkOrderService.update(vendorWorkOrder);
		// logger.info("TableController response" + dataResponse);

		Response response = new Response();
		response.setCode("200");
		response.setMessage("Contractor Information update successfully");
		response.setList(Arrays.asList(workOrder));

		return ResponseEntity.ok().body(response);
	}

	@ApiOperation(value = "Find All Work Order Information", response = VendorWorkOrder.class)
	@GetMapping("/")
	public ResponseEntity<Response> findAll() {
		List<VendorWorkOrder> workOrder = vendorWorkOrderService.findAll();
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(workOrder));
		return ResponseEntity.ok().body(response);

	}

	@ApiOperation(value = "find by work Order Id Vendor Work Order information", response = VendorWorkOrder.class)
	@GetMapping("workorder/{workOrderId}")
	public ResponseEntity<Response> findById(@PathVariable("workOrderId") Long workOrderId) {
		VendorWorkOrder workOrder = vendorWorkOrderService.findById(workOrderId);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(workOrder));
		return ResponseEntity.ok().body(response);
	}

	@ApiOperation(value = "find by work Order Id Vendor Work Order information", response = VendorWorkOrder.class)
	@GetMapping("workorders/{applicationNo}")
	public ResponseEntity<Response> findByApplicationNo(@PathVariable("applicationNo") String applicationNo) {
		VendorWorkOrder workOrder = vendorWorkOrderService.findByApplicationNo(applicationNo);
		Response response = new Response();
		response.setCode("200");
		response.setMessage("success");
		response.setList(Arrays.asList(workOrder));
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/uploadWorkOrderFile")
	public ResponseEntity<Response> uploadWorkOrderFile(@RequestPart String consumerApplicationNo,
			@RequestPart("docWorkOrder") MultipartFile docWorkOrder)
			throws ConsumerApplicationDetailException, DocumentTypeException {

		System.err.println("docWorkOrder.getSize() " + docWorkOrder.getSize());
		if (docWorkOrder.isEmpty()) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new Response(HttpCode.NOT_ACCEPTABLE, "Please upload Work Order File"));
		}
		ApplicationDocument uploadWorkOrderFile = vendorWorkOrderService.uploadWorkOrderFile(consumerApplicationNo,
				docWorkOrder);
		if (Objects.isNull(uploadWorkOrderFile)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body(new Response(HttpCode.NULL_OBJECT, "Application Document Not saved in database."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpCode.OK,
					"Application Document saved successfully in database.", Arrays.asList(uploadWorkOrderFile)));
		}

	}

	@PostMapping("/uploadDemandNoteFile")
	public ResponseEntity<Response> uploadDemandNoteFile(@RequestPart String consumerApplicationNo,
			@RequestPart("docDemandNote") MultipartFile docDemandNote)
			throws ConsumerApplicationDetailException, DocumentTypeException {

		System.err.println("docWorkOrder.getSize() " + docDemandNote.getSize());
		if (docDemandNote.isEmpty()) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new Response(HttpCode.NOT_ACCEPTABLE, "Please upload Demand Note File"));
		}
		ApplicationDocument uploadDemandNoteFile = vendorWorkOrderService.uploadDemandNoteFile(consumerApplicationNo,
				docDemandNote);
		if (Objects.isNull(uploadDemandNoteFile)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body(new Response(HttpCode.NULL_OBJECT, "Application Document Not saved in database."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpCode.OK,
					"Application Document saved successfully in database.", Arrays.asList(uploadDemandNoteFile)));
		}

	}
	
	@PostMapping("/uploadDraftAgreement")
	public ResponseEntity<Response> uploadDraftAgreement(@RequestPart String consumerApplicationNo,
			@RequestPart("docDraftAgreement") MultipartFile docDraftAgreement)
			throws ConsumerApplicationDetailException, DocumentTypeException {

		System.err.println("docDraftAgreement.getSize() " + docDraftAgreement.getSize());
		if (docDraftAgreement.isEmpty()) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new Response(HttpCode.NOT_ACCEPTABLE, "Please upload Draft Agreement File"));
		}
		ApplicationDocument uploadDraftAgreement = vendorWorkOrderService.uploadDraftAgreement(consumerApplicationNo,
				docDraftAgreement);
		if (Objects.isNull(uploadDraftAgreement)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body(new Response(HttpCode.NULL_OBJECT, "Application Document Not saved in database."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpCode.OK,
					"Application Document saved successfully in database.", Arrays.asList(uploadDraftAgreement)));
		}

	}

}
