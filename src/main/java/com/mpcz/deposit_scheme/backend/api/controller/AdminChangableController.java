package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.AppUpdationRemark;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.domain.IndividualOrGroup;
import com.mpcz.deposit_scheme.backend.api.domain.ListDistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.ListDivision;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.dto.UpdateUser;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.repository.AppUpdationRemarkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplicationSurveyRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DistributionCenterRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DivisionRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.IndividualOrGroupRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ListDistributionCenterRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ListDivisionRepository;
import com.mpcz.deposit_scheme.backend.api.repository.NatureOfWorkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SchemeTypeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationDocumentService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/adminChangable/user")
public class AdminChangableController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	NatureOfWorkRepository natureOfWorkRepository;

	@Autowired
	SchemeTypeRepository schemeTypeRepository;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private IndividualOrGroupRepository individualOrGroupRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private ApplicationDocumentService applicationDocumentService;

	@Autowired
	private AppUpdationRemarkRepository appUpdationRemarkRepository;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ConsumerApplicationSurveyRepository consumerApplicationSurveyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ListDistributionCenterRepository listDistributionCenterRepository;

	@Autowired
	private ListDivisionRepository listDivisionRepository;

	@Autowired
	private DistributionCenterRepository distributionCenterRepository;

	@Autowired
	private DivisionRepository divisionRepository;

	@GetMapping("/getConsumerByLoginId/{loginId}")
	public ResponseEntity<Response<?>> getConsumerByLoginId(@PathVariable String loginId,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, UserException {

		Response<Consumer> response = new Response<>();

		Optional<Consumer> findByConusmerMobileNo = consumerRepository.findByConsumerLoginId(loginId);

		if (!findByConusmerMobileNo.isPresent()) {
			response.setCode("100");
			response.setMessage("Consumer Not Found With This Login Id");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		}

		response.setCode("200");
		response.setMessage("Consumer found");
		response.setList(Arrays.asList(findByConusmerMobileNo.get()));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

	@PutMapping("/changeConsumerMobileNo/{oldLoginId}/{newMobileNo}")
	public ResponseEntity<?> changeConsumerMobileNo(@PathVariable String oldLoginId, @PathVariable String newMobileNo) {
		Response<Consumer> response = new Response<>();
		try {

			Consumer consumer1 = consumerRepository.findByConsumerLoginId(newMobileNo).orElse(null);
			if (consumer1 != null) {
				response.setCode("406");
				response.setMessage("This mobile no is already registered");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			Consumer consumer = consumerRepository.findByConsumerLoginId(oldLoginId).orElse(null);
			if (consumer == null) {
				response.setCode("100");
				response.setMessage("Consuer Not Found With This Login Id");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			consumer.setConsumerLoginId(newMobileNo);
			consumer.setConsumerMobileNo(newMobileNo);

			List<ConsumerApplicationDetail> findByConsumerId = consumerApplicationDetailRepository
					.findByConsumerId(consumer.getConsumerId());
			for (ConsumerApplicationDetail consumerDetail : findByConsumerId) {
				consumerDetail.setConsumerphonNumber(newMobileNo);
				consumerApplicationDetailRepository.save(consumerDetail);
			}

			Consumer save = consumerRepository.save(consumer);
			response.setCode("200");
			response.setMessage("Consumer Mobile Number Updated Successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("404");
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/changeConsumerNameAndAddress/{consumerApplicationNo}/{newConsumerName}/{newAddress}")
	public ResponseEntity<?> changeConsumerNameAndAddress(@PathVariable String consumerApplicationNo,
			@PathVariable String newConsumerName, @PathVariable String newAddress) {
		Response response = new Response();
		ConsumerApplicationDetail save = null;
		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode("404");
				response.setMessage("Consumer Application No Not Found In Database");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (findByConsumerApplicationNumber.getConsumerName().equals(newConsumerName)
					&& findByConsumerApplicationNumber.getAddress().equals(newAddress)) {
				response.setCode("202");
				response.setMessage("No changes needed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (!findByConsumerApplicationNumber.getConsumerName().equals(newConsumerName)) {
				findByConsumerApplicationNumber.setConsumerName(newConsumerName);
			}
			if (!findByConsumerApplicationNumber.getAddress().equals(newAddress)) {
				findByConsumerApplicationNumber.setAddress(newAddress);
			}

			save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
			if (save != null) {
				response.setCode("202");
				response.setMessage("Changes saved successfully");
				response.setList(Arrays.asList(save));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				response.setCode("500");
				response.setMessage("Failed to save changes");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/changeConsumerNameAndAddressByConsumerMobileNo/{consumerMobileNo}/{newConsumerName}/{newAddress}")
	public ResponseEntity<?> changeConsumerNameAndAddress1(@PathVariable String consumerMobileNo,
			@PathVariable String newConsumerName, @PathVariable String newAddress) {
		Response response = new Response();
		Consumer save = null;
		try {
			Consumer findByConsumerLoginId = consumerRepository.findByConsumerLoginId(consumerMobileNo).orElse(null);

			if (findByConsumerLoginId == null) {
				response.setCode("404");
				response.setMessage("Consumer Application No Not Found In Database");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (findByConsumerLoginId.getConsumerName().equals(newConsumerName)
					&& findByConsumerLoginId.getAddress().equals(newAddress)) {
				response.setCode("202");
				response.setMessage("No changes needed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			if (!findByConsumerLoginId.getConsumerName().equals(newConsumerName)) {
				findByConsumerLoginId.setConsumerName(newConsumerName);

			}
			if (!findByConsumerLoginId.getAddress().equals(newAddress)) {
				findByConsumerLoginId.setAddress(newAddress);
			}

			save = consumerRepository.save(findByConsumerLoginId);

			if (save != null) {
				response.setCode("202");
				response.setMessage("Changes saved successfully");
				response.setList(Arrays.asList(save));
			} else {
				response.setCode("500");
				response.setMessage("Failed to save changes");
			}

			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/changeApplicationStatus")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseEntity<?> changeApplicationStatus(@RequestParam String consumerApplicationNo,
			@RequestParam Long applicationStatusNo, @RequestParam String userAdminId,
			@RequestParam String adminRemark) {
		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNo);
			if (Objects.isNull(findByConsumerApplicationNumber)) {
				return ResponseEntity
						.ok(new Response(HttpCode.NULL_OBJECT, "Consumer Application No Not Found In Database", null));
			} else {
				if ((findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() > 12
						|| findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() < 4)
						|| (applicationStatusNo > 12l || applicationStatusNo < 4l)) {
					return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
							"Application Status can not be change Because applicaiton status is : "
									+ findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
									+ "  and you are changing status to : " + applicationStatusNo,
							null));
				} else {
					List<ConsumerApplicationDetail> consumerDetailList = consumerApplicationDetailRepository
							.findByErpNo(findByConsumerApplicationNumber.getErpWorkFlowNumber());
					System.out.println("aaaaaaaaaaa : " + consumerDetailList.size());
					if (consumerDetailList.size() > 1) {
						return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
								"Application can not revert back because this erp is tagged with multiple applications kindly contact DSP Team",
								null));
					}
					if (applicationStatusNo == 9l) {
						consumerDetailList.stream().forEach(con -> {
							deletErp(findByConsumerApplicationNumber.getErpWorkFlowNumber());

						});

						findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId()).get());
					} else { // application status 6 k liye h ye
						if (consumerDetailList != null && !consumerDetailList.isEmpty()) {
							consumerDetailList.forEach(con -> {
								deletErp(findByConsumerApplicationNumber.getErpWorkFlowNumber());
							});
						}
						if (applicationStatusNo == 7l) {
							findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
									.findById(ApplicationStatusEnum.PENDING_FOR_SURVEY_AT_DC.getId())
									.orElseThrow(() -> new RuntimeException("Application status not found")));
						} else if (applicationStatusNo == 6l) {
							// Empty ya null list hone par bhi common code chalega
							deleSurvey(findByConsumerApplicationNumber.getConsumerApplicationId());
							findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
									.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId())
									.orElseThrow(() -> new RuntimeException("Application status not found")));
						} else if (applicationStatusNo == 5l) {
//							deleSurvey(findByConsumerApplicationNumber.getConsumerApplicationId());
							findByConsumerApplicationNumber.setAdminStatusChangedTo(5l);
							findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
									.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId())
									.orElseThrow(() -> new RuntimeException("Application status not found")));
						}
						findByConsumerApplicationNumber.setErpWorkFlowNumber(null);
					}

					User user = userRepository.findByUserId(userAdminId).orElseThrow(
							() -> new UserException(new Response(HttpCode.NULL_OBJECT, "User Id Not Found")));
					findByConsumerApplicationNumber.setAdminId(userAdminId);
					findByConsumerApplicationNumber.setAdminName(user.getUserName());
					findByConsumerApplicationNumber.setAdminUpdateDate(LocalDate.now().toString());
					findByConsumerApplicationNumber.setAdminRemark(adminRemark);
					ConsumerApplicationDetail save = consumerApplicationDetailRepository
							.save(findByConsumerApplicationNumber);
					if (Objects.isNull(save)) {
						return ResponseEntity.ok(new Response(HttpCode.NULL_OBJECT, "Data Not Saved", null));
					} else {
						return ResponseEntity.ok(new Response(HttpCode.UPDATED,
								"Application Status Updated successfully", Arrays.asList(save)));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

	@PutMapping("/changeSchemeType/{consumerApplicationNo}/{schemeTypeId}")
	public ResponseEntity<?> changeSchemeType(@PathVariable String consumerApplicationNo,
			@PathVariable long schemeTypeId) {
		Response response = new Response();
		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5L)
					|| findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("Can not change the scheme type for OYT Or MKMY Application");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			SchemeType schemeType = null;
			ConsumerApplicationDetail save = null;
			try {
				schemeType = schemeTypeRepository.findById(schemeTypeId).get();

				if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == schemeTypeId) {
					response.setCode("202");
					response.setMessage("No changes needed");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}

				if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() != schemeTypeId) {
					findByConsumerApplicationNumber.setSchemeType(schemeType);
				}

				save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (save != null) {
				response.setCode("202");
				response.setMessage("Changes saved successfully");
				response.setList(Arrays.asList(save));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				response.setCode("500");
				response.setMessage("Failed to save changes");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/changeIndividualOrGroup/{consumerApplicationNo}/{individualOrGroupId}")
	public ResponseEntity<?> changeIndividualOrGroup(@PathVariable String consumerApplicationNo,
			@PathVariable Long individualOrGroupId,
			@RequestPart("docIndividualOrGroup") Optional<MultipartFile> docIndividualOrGroupOptional) {

		Response response = new Response();
		try {
			ConsumerApplicationDetail applicationDetail = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNo);
			if (applicationDetail == null) {
				response.setCode("404");
				response.setMessage("Consumer Application No Not Found In Database");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			Long natureOfWorkTypeId = applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId();
			if (!(natureOfWorkTypeId.equals(4L) || natureOfWorkTypeId.equals(8L) || natureOfWorkTypeId.equals(2L))) {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage(
						"Individual And Group Is not allowed for this Nature Of Work : " + individualOrGroupId);
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			IndividualOrGroup individualOrGroup = individualOrGroupRepository.findById(individualOrGroupId)
					.orElse(null);
			if (individualOrGroup == null) {
				response.setCode("404");
				response.setMessage("IndividualOrGroup ID not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (applicationDetail.getIndividualOrGroup() == null
					|| !individualOrGroupId.equals(applicationDetail.getIndividualOrGroup().getIndividualOrGroupId())) {

				MultipartFile docIndividualOrGroup = docIndividualOrGroupOptional.orElse(null);
				if (individualOrGroupId.equals(2L) && docIndividualOrGroup == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Please upload Group File document");
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
				}

				Upload individualGroupUpload = null;
				if (docIndividualOrGroup != null) {
					individualGroupUpload = uploadService.uploadFile(docIndividualOrGroup, "Group_File");
					if (individualGroupUpload == null) {
						response.setCode(HttpCode.NULL_OBJECT);
						response.setMessage("Document Groupfile not uploaded.");
						return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
					}
				}

				applicationDetail.setIndividualOrGroup(individualOrGroup);
				ConsumerApplicationDetail savedDetail = consumerApplicationDetailRepository.save(applicationDetail);

				if (individualOrGroupId.equals(2L)) {
					ApplicationDocument appDocument = applicationDocumentRepository
							.findByconsumerApplicationDetail_consumerApplicationId(
									savedDetail.getConsumerApplicationId());
					if (individualGroupUpload != null) {
						appDocument.setDocGroup(individualGroupUpload);
						applicationDocumentRepository.save(appDocument);
					}
				}
				response.setCode("200");
				response.setMessage("Changes saved successfully");
				response.setList(Arrays.asList(savedDetail));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			} else {
				response.setCode("202");
				response.setMessage("No changes needed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("404");
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
	}

	@PostMapping("/saveJeRemarkForApplicationUpdation")
	public ResponseEntity<?> saveJeRemarkForApplicationUpdation(@RequestBody AppUpdationRemark remark) {
		try {
			Response response = new Response();

			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(remark.getConsumerAppNo());
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			AppUpdationRemark save = appUpdationRemarkRepository.save(remark);
			response.setCode("200");
			response.setMessage("Data Saved Successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

	@PutMapping("/revertApplcaicationForNatureOfWork/{consumerApplicationNo}/{natrueOfWorkChange}")
	public ResponseEntity<?> revertApplcaicationForNatureOfWork(@PathVariable String consumerApplicationNo,
			@PathVariable Boolean natrueOfWorkChange) {
		try {
			Response response = new Response();

			ConsumerApplicationDetail save = null;
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(consumerApplicationNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (natrueOfWorkChange) {
				findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.APPLIACATION_BACK_TO_CONSUMER_END_FOR_UPDATION.getId()).get());
				save = consumerApplicationDetailRepository.save(findByConsumerApplicationNumber);
			} else {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("No need to change status");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			response.setCode("200");
			response.setMessage("Application Return Back To 38 Status Successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

//	14- Oct-2024 MKMY Document Update

	@PutMapping("/updateMKMYDocument")
	public ResponseEntity<?> updateMKMYDocument(
			@RequestPart("docSamagraFile") Optional<MultipartFile> docSamagraFileOptional,
			@RequestPart("docKhasraKhatoni") Optional<MultipartFile> docKhasraKhatoniOptional,
			@RequestPart("consumerApplicationNo") String consumerApplicationNo) throws DocumentTypeException {

		MultipartFile samagraFile = docSamagraFileOptional.orElse(null);
		MultipartFile khasraFile = docKhasraKhatoniOptional.orElse(null);

		Response updateResponse = consumerApplicationDetailService.updateMKMYDocument(samagraFile, khasraFile,
				consumerApplicationNo);

		return ResponseEntity.ok(updateResponse);
	}

	@Transactional
	public void deletErp(String erp) {
		estimateAmountRepository.deleteByErpNo(erp);

	}

	@Transactional
	public void deleSurvey(Long applicationId) {

		consumerApplicationSurveyRepository.deleteByConsumerApplicationId(applicationId);
	}

	@PutMapping("/updataAddUserTable")
	public ResponseEntity<?> updataAddUserTable(@RequestBody UpdateUser updarUser) {
		Response<?> response = new Response<>();
		if (updarUser.getListdistributionCenter().size() > 0) {
			List<ListDistributionCenter> findByuserId = listDistributionCenterRepository
					.findByuserId(updarUser.getListdistributionCenter().get(0).getUserId());
			if (findByuserId.size() > 0) {
				findByuserId.stream().forEach(l -> {
					listDistributionCenterRepository.deleteById(l.getId());
				});
			}
		}
		if (updarUser.getListDivision().size() > 0) {
			List<ListDivision> findByuserId = listDivisionRepository
					.findByuserId(updarUser.getListDivision().get(0).getUserId());
			if (findByuserId.size() > 0) {
				findByuserId.stream().forEach(l -> {
					listDivisionRepository.deleteById(l.getId());
				});
			}
		}

		try {
			if (updarUser.getListdistributionCenter().size() > 0) {
				updarUser.getListdistributionCenter().stream().forEach(dis -> {
					System.err.println(dis);
					listDistributionCenterRepository.save(dis);

				});
			}
			if (updarUser.getListDivision().size() > 0) {
				updarUser.getListDivision().stream().forEach(division -> {
					listDivisionRepository.save(division);
				});
			}
			response.setCode("200");
			response.setMessage("successfull done");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	@GetMapping("/getUserDcAndDivion/{userId}")
//	public Map<String, List<?>> getUserDcAndDivion(@PathVariable String userId) {
//		Response<?> response = new Response<>();
//
//		Map<String, List<?>> map = new HashMap<String, List<?>>();
//
//		try {
////			User user = userRepository.findByUserId(userId).get();
//
//			Map<String, Object> findByUserIdInView = userRepository.findByUserIdInView(userId);
//			System.err.println("ssssss : " +new Gson().toJson(findByUserIdInView));
//			
////			String name = findByUserIdInView.get("DISTRIBUTION_CENTER_ID");
////			System.out.println(name); 
////			Long valueOf = Long.parseLong(findByUserIdInView.get("DISTRIBUTION_CENTER_ID"));
//			
//	
//			
//			List<ListDistributionCenter> findByuserId = listDistributionCenterRepository.findByuserId(userId);
//			
//			List<ListDivision> findByuserId2 = listDivisionRepository.findByuserId(userId);
//
//			if (findByuserId.size() > 0) {
//				if (findByUserIdInView.get("ACCESS_LEVEL").equals("5") || findByUserIdInView.get("ACCESS_LEVEL").equals("6")) {
//					
//					DistributionCenter findByDistrict = distributionCenterRepository.findByDcId(valueOf);
//		
//					ListDistributionCenter list = new ListDistributionCenter();
//					list.setId(1);
//					list.setUserId(userId);
//					list.setDistributionCenterId(findByDistrict.getDcId().intValue());
//					list.setDistributionCenterName(findByDistrict.getDcName());
//					findByuserId.add(list);
//				}
//				map.put("DistributionCenter", findByuserId);
//			}
////			if (findByuserId2.size() > 0) {
////				if (user.getAccessLevel().equals("3") || user.getAccessLevel().equals("4")) {
////					ListDivision listDivision = new ListDivision();
////					listDivision.setId(1);
////					listDivision.setUserId(userId);
////					listDivision.setDivisionId(user.getUserDivision().getDivisionId().intValue());
////					listDivision.setDivisionName(user.getUserDivision().getDivision());
////					findByuserId2.add(listDivision);
////				}
////				map.put("division", findByuserId2);
////			}
//
//			response.setCode("200");
//			response.setMessage("successsfull..");
//			response.setMap1(map);
//
//			return map;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@PutMapping("/userDeActiveAccount/{oldLoginId}/{oneactive2Deactive}")
	public ResponseEntity<?> deActiveAccount(@PathVariable String oldLoginId, @PathVariable int oneactive2Deactive) {
		Response<User> response = new Response<>();
		try {

			User user = userRepository.findByUserId(oldLoginId).get();

			if (user == null) {
				response.setCode("100");
				response.setMessage("Consuer Not Found With This Login Id");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			if (oneactive2Deactive == 1) {
				user.setActive(false);
				user.setDeleted(true);

			} else {
				user.setActive(true);
				user.setDeleted(false);

			}
			User save = userRepository.save(user);

			response.setCode("200");
			response.setMessage("Consumer Mobile Number Updated Successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("404");
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/getUserDcAndDivion/{userId}")
	public Response<Map<String, List<?>>> getUserDcAndDivion(@PathVariable String userId) {
		Response<Map<String, List<?>>> response = new Response<>();

		Map<String, List<?>> map = new HashMap<String, List<?>>();

		try {

			Map<String, Object> findByUserIdInView = userRepository.findByUserIdInView(userId);

			for (Map.Entry<String, Object> entry : findByUserIdInView.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}

			// Optional conversion to Long
			Object value = findByUserIdInView.get("DISTRIBUTION_CENTER_ID");
			Object value1 = findByUserIdInView.get("DIV_ID");

			Long valueOf = Optional.ofNullable(value).map(val -> {
				if (val instanceof BigDecimal) {
					return ((BigDecimal) val).longValue();
				} else if (val instanceof Number) {
					return ((Number) val).longValue();
				} else if (val instanceof String) {
					return Long.parseLong((String) val);
				} else {
					return null;
				}
			}).orElse(null);

			Long valueOf1 = Optional.ofNullable(value1).map(val -> {
				if (val instanceof BigDecimal) {
					return ((BigDecimal) val).longValue();
				} else if (val instanceof Number) {
					return ((Number) val).longValue();
				} else if (val instanceof String) {
					return Long.parseLong((String) val);
				} else {
					return null;
				}
			}).orElse(null);

			List<ListDistributionCenter> findByuserId = listDistributionCenterRepository.findByuserId(userId);
			List<ListDivision> findByuserId2 = listDivisionRepository.findByuserId(userId);

			if (findByuserId.size() > 0) {
				if (findByUserIdInView.get("ACCESS_LEVEL").equals("5")
						|| findByUserIdInView.get("ACCESS_LEVEL").equals("6")) {

					DistributionCenter findByDistribution = distributionCenterRepository.findByDcId(valueOf);

					if (findByDistribution != null) {
						ListDistributionCenter list = new ListDistributionCenter();
						list.setId(1);
						list.setUserId(userId);
						list.setDistributionCenterId(findByDistribution.getDcId().intValue());
						list.setDistributionCenterName(findByDistribution.getDcName());
						findByuserId.add(list);
					}
				}
				map.put("DistributionCenter", findByuserId);
				map.put("division", null);
			}
			if (findByuserId2.size() > 0) {
				if (findByUserIdInView.get("ACCESS_LEVEL").equals("3")
						|| findByUserIdInView.get("ACCESS_LEVEL").equals("4")) {

					Division findByDivision = divisionRepository.findByDivisionId(valueOf1);
					if (findByDivision != null) {
						ListDivision listDivision = new ListDivision();
						listDivision.setId(1);
						listDivision.setUserId(userId);
						listDivision.setDivisionId(findByDivision.getDivisionId().intValue());
						listDivision.setDivisionName(findByDivision.getDivision());
						findByuserId2.add(listDivision);
					}
				}
				map.put("DistributionCenter", null);
				map.put("division", findByuserId2);
			}

			if (map.size() > 0) {
				response.setCode("200");
				response.setMessage("successsfull..");
				response.setMap1(map);
			} else {
				response.setCode("404");
				response.setMessage("data not found...");
				response.setMap1(map);
			}

			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
