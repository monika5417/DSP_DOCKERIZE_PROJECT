package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UploadRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ErpRevService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

@RestController
@RequestMapping("/ErpRev")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ErpRevController {

	@Autowired
	private ErpRevService erpRevService;

	@Autowired
	private ConsumerApplicationDetailService ConsumerApplicationDetailService;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private UploadRepository uploadRepository;

	@Autowired
	ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;
	@Autowired
	private ErpRevRepository erpRevRepository;

	@Autowired
	private EstimateAmountRepository EstimateAmountRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private DryUtility dryUtility;

	private static final List<String> EXCEPTION_APPS = Arrays.asList("DS1699426487567", "DS1699253588016",
			"DS1702298555321", "DS1701246539098", "DS1704454926731", "DS1706857590152", "DS1706004532398",
			"DS1706691627804", "DS1708945257354", "DS1710229519315", "SV1711795690451", "DS1716359048844",
			"DS1728465537213", "DS1730795058219", "DS1721631072059", "DS1729579231286", "DS1724392623868",
			"DS1716973729863", "DS1718866089982", "DS1727336589325", "DS1715358880541", "DS1715680035277",
			"DS1736226610759", "DS1732716032314", "DS1706337259323", "DS1733473796731", "DS1736236226757");

	@GetMapping("/RevErpDataNew/{erpNo}/{applicationNo}/{value}")
	public ResponseEntity<Response<ErpRev>> save(@PathVariable String erpNo, @PathVariable String applicationNo,
			@PathVariable Long value) {
		Response<ErpRev> res = new Response<>();
		try {

//			code added for certain application kyuki inka minus cost and je return amount leliya gaya hai check ankit sir ne lagwaya hai 
//			code statrt 5-march-2025
			if (EXCEPTION_APPS.contains(applicationNo)) {
				throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
						"Revise Estimate can not be generated for given application: " + applicationNo
								+ " Please contact DSP Team"));
			}
//			code end 5-march-2025
			ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo = ConsumerApplicationDetailService
					.findConsumerApplicationDetailByApplicationNo(applicationNo);
			
			if(findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals("3L") &&
					findConsumerApplicationDetailByApplicationNo.getJeLoadUnitKwYaKva().equals("KW")) {
				res.setCode("404");
				res.setMessage("Applicationn je load unit not correct Please connect it department ");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);	
			}
			if(findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(4L) 
					&& findConsumerApplicationDetailByApplicationNo.getColonyIllegalSelectionType().equals("2") &&
					findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup().getIndividualOrGroupId().equals(1L) 
					&& (findConsumerApplicationDetailByApplicationNo.getoAndMLoad().compareTo(new BigDecimal(400))	> 0	)) {
				res.setCode("404");
				res.setMessage("Thsi application amoutn can not be calculate above 400 load ");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);	
			}
			
			if (findConsumerApplicationDetailByApplicationNo == null) {

				res.setCode("404");
				res.setMessage("consumer applicationn not found");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);
			}
			if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {

				res.setCode("404");
				res.setMessage("Scheme closed");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);
			}
			if (value == 2L) {
				ErpRev byConsAppNoDB = erpRevRepository.findByConsAppNo(applicationNo);
				if (byConsAppNoDB != null) {
					res.setCode("406");
					res.setMessage("Data already submitted for this application : " + applicationNo);
					return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);
				}

				ErpRev newErpNoDB = erpRevRepository.findByNewErpNo(erpNo);
				if (newErpNoDB != null) {
					return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
							"This erp no. is already associate with application no. : " + applicationNo));
				}
			}

			Optional<ErpEstimateAmountData> findByIdAndConsumerApplicationNoOptinaol = EstimateAmountRepository
					.findByIdAndConsumerApplicationNo(erpNo, applicationNo);
			if (!findByIdAndConsumerApplicationNoOptinaol.isPresent()) {

				ResponseEntity<Response> checkErpExistOrNotForRevDemand = dryUtility
						.checkErpExistOrNot1(erpNo,applicationNo);
				
				if (checkErpExistOrNotForRevDemand.getBody().getCode().equals("406")) {
					return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE,
							"This erp no. is already associate with application no. : ",
							checkErpExistOrNotForRevDemand.getBody().getList()));
				}

			}
//			ErpRev erp  =null;
//			if(findConsumerApplicationDetailByApplicationNo.getErpVersion().equals("V2")) {
//				 erp 	= erpRevService.save(erpNo, applicationNo, value);
//			}else {
//				erp 	= erpRevService.Dynamic(erpNo, applicationNo, value);
//			}
			
			
			ErpRev	erp 	= erpRevService.save(erpNo, applicationNo, value);
			
			if (erp.getSchemeCode().equals("Scheme code not match")) {
				res.setCode("404");
				res.setMessage("Scheme code not match");
				res.setList(Arrays.asList(erp));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);
			}
			if(erp.getSchemeCode().equals("As per guidelines, dismantling cost cannot be zero or negative hence the estimate is incorrect.")){
				res.setMessage(erp.getSchemeCode());
				res.setCode("404");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);
			}
			if (!erp.getSchemeCode().equals("not data show")) {
				res.setCode("200");
				res.setMessage("data save successfully");
				res.setList(Arrays.asList(erp));
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);
			} else if (erp.getSchemeCode().equals("not data show"))
				;
			res.setCode("404");
			res.setMessage("this application of elegle type thats way amount not show");
			res.setList(Arrays.asList(erp));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(res);

		} catch (ConsumerApplicationDetailException e) {
			res.setCode(HttpCode.NOT_ACCEPTABLE.toString());
			res.setMessage(e.getMessage());
			return ResponseEntity.status(406).body(res);
		} catch (Exception e) {
			res.setCode("500");
			res.setMessage("An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(500).body(res);
		}
//        return ResponseEntity.status(500).body(res);
	}

	@GetMapping("/getConsumerApplication/{consumerApp}")
	public ResponseEntity<?> getConsumerApplication(@PathVariable("consumerApp") String consumerApp)
			throws ConsumerApplicationDetailException {
		return erpRevService.getConsumerApplication(consumerApp);

	}

	@PostMapping("/erpReviseFileSave")
	public Response<?> erpReviseFileSave(@RequestPart String consumerApplicationNumber,
			@RequestPart("docErpRevise") Optional<MultipartFile> docErpRevise)
			throws ConsumerApplicationDetailException, DocumentTypeException {

		Response response = new Response();

		MultipartFile docErpRevised = null;
		Upload uploadFile = null;

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNumber);

		if (Objects.isNull(consumerApplicationDetail)) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		}

		ApplicationDocument findByConsumerApplicationDetailId = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());
		if (Objects.isNull(findByConsumerApplicationDetailId)) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			return response;
		}

		if (docErpRevise.isPresent()) {
			docErpRevised = docErpRevise.get();
		}

		if (docErpRevised != null) {
			uploadFile = uploadService.uploadFile(docErpRevised, "ERP_REVISE");
			if (uploadFile == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Erp Revise Not Uploaded.");
				throw new ConsumerApplicationDetailException(response);
			}
			findByConsumerApplicationDetailId.setDocErpReviseFile(uploadFile);
			applicationDocumentRepository.save(findByConsumerApplicationDetailId);
			System.out.println("Uploaded file name: " + docErpRevised.getOriginalFilename());

		}

		Optional<Upload> findById = uploadRepository.findById(uploadFile.getUploadId());
		Upload upload1 = findById.get();
		upload1.setConsuemrApplicatonID(consumerApplicationDetail.getConsumerApplicationId());
		Upload save = uploadRepository.save(upload1);

		response.setList(Arrays.asList(save));
		response.setCode(HttpCode.OK);
		response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
		return response;

	}

	@GetMapping("/negativeRev/{erpNo}/{applicationNo}/{value}")
	public ResponseEntity<?> negativeRev(@PathVariable String erpNo, @PathVariable String applicationNo,
			@PathVariable Long value) {
		try {

			ResponseEntity<Response<ErpRev>> save = save(erpNo, applicationNo, value);
			Response<ErpRev> responseBody = save.getBody();
			System.out.println("Response body: " + responseBody);
			List<ErpRev> erpRevList = responseBody.getList();
			for (ErpRev erpRev : erpRevList) {
				System.out.println("ErpNo: " + erpRev.getNewErpNo());
				BigDecimal newSupervisionAmt = erpRev.getNewSupervisionAmt();
				BigDecimal oldSupervision = erpRev.getOldSupervision();
				if (newSupervisionAmt.compareTo(oldSupervision) < 0) {
//	            	System.out.println("New Supervision Amount : " +newSupervisionAmt);
					System.out.println("New Estimate Amount : " + erpRev.getNewEstimateAmt());
					System.out.println("Old Estimate Amount : " + erpRev.getOldEstimate());
					System.out.println("Remaining Estimate Amount : " + erpRev.getRemEstimateAmt());
					System.out.println("Old Total Paid Amount : " + erpRev.getOldPayableAmt());
					System.out.println("Old Cgst : " + erpRev.getOldCgst());
					System.out.println("Old Sgst : " + erpRev.getOldSgst());

					BigDecimal oldBalanceAmount = erpRev.getRemEstimateAmt().add(erpRev.getOldCgst())
							.add(erpRev.getOldSgst());
//	            	System.out.println("Total Amount with GST : " + oldBalanceAmount);
					BigDecimal returnableAmount = erpRev.getOldPayableAmt().subtract(erpRev.getNewEstimateAmt());

					System.out.println("Retunable amount : " + oldBalanceAmount);
				}

				// Print other properties as needed
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
