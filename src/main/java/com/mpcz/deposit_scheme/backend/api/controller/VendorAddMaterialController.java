package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.domain.VendorAddMaterial;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.VendorAddMaterialException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorAddMaterialRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

@RestController
@RequestMapping("/vendor_add_materials")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VendorAddMaterialController {

	@Autowired
	private VendorAddMaterialRepository vendorAddMaterialRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;

//	 @PersistenceContext
//	  private EntityManager entityManager;

	@PostMapping("/addMaterial")
//	@Transactional
	public ResponseEntity<?> addMaterial(@RequestBody VendorAddMaterial vendorAddMaterial)
			throws VendorAddMaterialException, ConsumerApplicationDetailException {
		Response response = new Response();
		VendorAddMaterial savedVendor = null;
		if (vendorAddMaterial.getConsumerApplicationNumber() == null
				|| vendorAddMaterial.getConsumerApplicationNumber().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill Consumer Application Number");
			throw new VendorAddMaterialException(response);

		}

		if (vendorAddMaterial.getVendorName() == null || vendorAddMaterial.getVendorName().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill Vendor Name");
			throw new VendorAddMaterialException(response);

		}

		if (vendorAddMaterial.getUserId() == null || vendorAddMaterial.getUserId().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill User Id");
			throw new VendorAddMaterialException(response);

		}

		if (vendorAddMaterial.getVendorMaterialSpecification() == null
				|| vendorAddMaterial.getVendorMaterialSpecification().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill Vendor Material Specification");
			throw new VendorAddMaterialException(response);

		}

		if (vendorAddMaterial.getTransformerSerialNo() == null
				|| vendorAddMaterial.getTransformerSerialNo().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill Transformer Serial No. ");
			throw new VendorAddMaterialException(response);

		}

		if (vendorAddMaterial.getMaterialInstallationDate() == null
				|| vendorAddMaterial.getMaterialInstallationDate().equals("")) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please Fill Material Installation Date");
			throw new VendorAddMaterialException(response);

		}

		ConsumerApplicationDetail consumerAppObject = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(vendorAddMaterial.getConsumerApplicationNumber());

		if (consumerAppObject == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(
					"Consumer Application Number not found in database. Kindly provide the correct application number");
			throw new ConsumerApplicationDetailException(response);
		}
		if (!consumerAppObject.getApplicationStatus().getApplicationStatusId().equals(24l)) {

			return ResponseEntity.ok(new Response<>(HttpCode.NOT_ACCEPTABLE,
					"You are not authorize to use this api at this stage of application status"));

		}

		if (consumerAppObject.getSchemeType().getSchemeTypeId().equals(1L)) {
			ContractorForBidWorkStatus findByConsumerApplicationNo = contractorForBidWorkStatusRepository
					.findByUniqueConsumerApplicationNo(vendorAddMaterial.getConsumerApplicationNumber());
			if (findByConsumerApplicationNo == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application Number not found in Contractor For Bid Work Status");
				throw new ConsumerApplicationDetailException(response);
			}

			if (findByConsumerApplicationNo.getActualWorkCompletionDate() != null
					&& !findByConsumerApplicationNo.getActualWorkCompletionDate().equals("None")) {
//			 consumerAppObject = entityManager.merge(consumerAppObject);
				savedVendor = vendorAddMaterialRepository.save(vendorAddMaterial);

				if (savedVendor.getMaterialInstallationDate() != null) {

					if (consumerAppObject.getApplicationStatus().getApplicationStatusId() == 24) {

						ApplicationStatus appStatusDb = applicationStatusService.findById(
								ApplicationStatusEnum.PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC.getId());
						consumerAppObject.setApplicationStatus(appStatusDb);

						consumerApplictionDetailRepository.save(consumerAppObject);
					}

				}
			} else {
				response.setCode("404");
				response.setMessage("Actual Date Not Submitted By Contractor");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			response.setCode("200");
			response.setMessage("Application Saved Successfully");
			response.setList(Arrays.asList(savedVendor));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} else {
			response.setCode("406");
			response.setMessage("Data can not be submitted because the application not belongs to Supervision cases");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
	}

	@GetMapping("/getAllVendorMaterial/{consAppNo}")
	public ResponseEntity<?> getAllVendorMaterial(@PathVariable("consAppNo") String consAppNo) {
		Response response = new Response();
		List<VendorAddMaterial> listOfVendorMaterial = vendorAddMaterialRepository
				.findByConsumerApplicationNumber(consAppNo);
		if (listOfVendorMaterial.isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application not found in database");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("Data Retrive Successfully !!!!!!!");
		response.setList(Arrays.asList(listOfVendorMaterial));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@Autowired
	private DryUtility dryUtility;

	@Autowired
	private ConsumerRepository consumerRepository;

	@PatchMapping("/consumer/updateVendorMaterialData")
	public ResponseEntity<?> updateVendorMaterialData(@RequestBody VendorAddMaterial vendorAddMaterial,
			HttpServletResponse httpServletResponse)
			throws ConsumerApplicationDetailException, VendorAddMaterialException {
		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(vendorAddMaterial.getConsumerApplicationNumber());

//		System.err.println("authorization :" + httpServletResponse.getHeader("authorization"));
//
//		System.out.println(
//				SecurityContextHolder.getContext().getAuthentication().getName() + "----------------->>>>>>>>>");
//		String LoginId = SecurityContextHolder.getContext().getAuthentication().getName();
//		Consumer consumerDB = consumerRepository.findByConsumerLoginId(LoginId).orElseThrow(
//				() -> new VendorAddMaterialException(new Response(HttpCode.NULL_OBJECT, "Consumer Id not exist")));
//
//		if (consumerDB.getConsumerId().equals(validateConsumerApplication.getConsumers().getConsumerId())) {

		VendorAddMaterial vendorMaterialDB = vendorAddMaterialRepository
				.findByIdAndConsumerApplicationNumber(vendorAddMaterial.getId(),
						vendorAddMaterial.getConsumerApplicationNumber())
				.orElseThrow(() -> new VendorAddMaterialException(
						new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")));

		if (vendorAddMaterial.getBill_date() != null)
			vendorMaterialDB.setBill_date(vendorAddMaterial.getBill_date());
		if (vendorAddMaterial.getBill_number() != null)
			vendorMaterialDB.setBill_number(vendorAddMaterial.getBill_number());
		if (vendorAddMaterial.getItem_serial_no() != null)
			vendorMaterialDB.setItem_serial_no(vendorAddMaterial.getItem_serial_no());
		if (vendorAddMaterial.getMaterialInstallationDate() != null)
			vendorMaterialDB.setMaterialInstallationDate(vendorAddMaterial.getMaterialInstallationDate());
		if (vendorAddMaterial.getMonth_year_of_item_manufacture() != null)
			vendorMaterialDB.setMonth_year_of_item_manufacture(vendorAddMaterial.getMonth_year_of_item_manufacture());
		if (vendorAddMaterial.getTransformerSerialNo() != null)
			vendorMaterialDB.setTransformerSerialNo(vendorAddMaterial.getTransformerSerialNo());
		if (vendorAddMaterial.getVendorMaterialSpecification() != null)
			vendorMaterialDB.setVendorMaterialSpecification(vendorAddMaterial.getVendorMaterialSpecification());
		if (vendorAddMaterial.getVendorName() != null)
			vendorMaterialDB.setVendorName(vendorAddMaterial.getVendorName());
		if (vendorAddMaterial.getUserId() != null)
			vendorMaterialDB.setUserId(vendorAddMaterial.getUserId());

		VendorAddMaterial updatedVendorData = vendorAddMaterialRepository.save(vendorMaterialDB);
		return ResponseEntity.ok(Objects.isNull(updatedVendorData)
				? new Response<>(HttpCode.NULL_OBJECT, "Vendor Material Data Not Updated.")
				: new Response<>(HttpCode.UPDATED, "Vendor Material Data Updated Successfully.",
						Arrays.asList(updatedVendorData)));
//		} else {
//			return ResponseEntity.ok(new Response<>(HttpCode.NULL_OBJECT,
//					"You are not authorized person to update this applicaiton thus this applicaiton does not belongs to you"));
//		}
	}

	@GetMapping("/getAllVendorMaterial1/{consAppNo}")
	public ResponseEntity<?> getAllVendorMaterial1(@PathVariable("consAppNo") String consAppNo) {
		Response response = new Response();
		List<VendorAddMaterial> listOfVendorMaterial = vendorAddMaterialRepository
				.findByConsumerApplicationNumber2(consAppNo);
		if (listOfVendorMaterial.isEmpty()) {
			response.setCode("400");
			response.setMessage("DTR Not Found This Application Number");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("Data Retrive Successfully !!!!!!!");
		response.setList(Arrays.asList(listOfVendorMaterial));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}
	
	
}
