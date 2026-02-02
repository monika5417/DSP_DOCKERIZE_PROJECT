package com.mpcz.deposit_scheme.backend.api.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.GatePassChallan;
import com.mpcz.deposit_scheme.backend.api.domain.MaterialDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ReSampling;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.VerifierBy;
import com.mpcz.deposit_scheme.backend.api.domain.VerifierGatekeeper;
import com.mpcz.deposit_scheme.backend.api.domain.VerifierIssuingAuthority;
import com.mpcz.deposit_scheme.backend.api.dto.GatePassChallanDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.GatePassChallanRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MaterialDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ReSamplingRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VerifierByRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VerifierGatekeeperRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VerifierIssuingAuthorityRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;

@RestController
@RequestMapping("/GatePassChallan")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GatePassChallanController {

	@Autowired
	private GatePassChallanRepository gatePassChallanRepository;

	@Autowired
	private MaterialDetailRepository materialDetailRepository;

	@Autowired
	private VerifierGatekeeperRepository verifierGatekeeperRepository;

	@Autowired
	private VerifierIssuingAuthorityRepository verifierIssuingAuthorityRepository;

	@Autowired
	private VerifierByRepository verifierByRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	UploadService uploadService;

	@Autowired
	ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private ReSamplingRepository reSamplingRepository;

	@PostMapping("/save")
	public Response<GatePassChallanDTO> save(@RequestBody GatePassChallanDTO gatePassChallanDTO) {

		Response<GatePassChallanDTO> response = new Response<GatePassChallanDTO>();

		GatePassChallan gatePassChallan = gatePassChallanDTO.getGatePassChallan();

		List<MaterialDetail> materialDetail = gatePassChallanDTO.getMaterialDetail();

		VerifierBy verifierBy = gatePassChallanDTO.getVerifierBy();

		VerifierGatekeeper verifierGatekeeper = gatePassChallanDTO.getVerifierGatekeeper();

		VerifierIssuingAuthority verifierIssuingAuthority = gatePassChallanDTO.getVerifierIssuingAuthority();
		GatePassChallan save = null;
		try {
			 save = gatePassChallanRepository.save(gatePassChallan);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (save != null) {
			List<MaterialDetail> saveAll = materialDetailRepository.saveAll(materialDetail);

//			verifierGatekeeperRepository.save(verifierGatekeeper);
//			verifierIssuingAuthorityRepository.save(verifierIssuingAuthority);
			try {
				verifierByRepository.save(verifierBy);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			response.setCode("200");
			response.setMessage("data  save");
			response.setList(Arrays.asList(gatePassChallanDTO));
			return response;

		} else {
			response.setCode("400");
			response.setMessage("data not save");
			return response;

		}

	}

	@GetMapping("getPassChallanDetailsByconsumerApplicationNumber/{consuemrApplicationNumber}")
	public List<Map<String, String>> get(@PathVariable String consuemrApplicationNumber) {

		Response<?> response = new Response();
		List<Map<String, String>> getdata2 = gatePassChallanRepository.getdata(consuemrApplicationNumber);

		return getdata2;

	}

	@PostMapping("/saveGetPassPdfaAndtrfUploadFile")
	public ResponseEntity<?> saveGetPassPdf(@RequestPart String consumerApplicationNo,
			@RequestPart(required = false) MultipartFile getPassFilee,
			@RequestPart(required = false) MultipartFile trfFile)
			throws DocumentTypeException, ConsumerApplicationDetailException {

		ReSampling findByConAppNo = reSamplingRepository.findByConAppNo(consumerApplicationNo).get();

		if (findByConAppNo == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application not found "));
		}

		findByConAppNo.setDate(LocalDateTime.now() + "");
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo).get();
		if (consumerApplicationDetail == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));

		}
		ApplicationDocument appDoc = null;
		appDoc = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());
		if (appDoc == null) {
			appDoc = new ApplicationDocument();
		}

		Upload getPassFile = null;
		Upload trfFile1 = null;

		if (getPassFilee != null) {
			getPassFile = uploadService.uploadFile(getPassFilee, "GET_PASS_PIC");
			appDoc.setGetPassfile(getPassFile);
		}
		if (trfFile != null) {
			trfFile1 = uploadService.uploadFile(trfFile, "TRF_FILE");
			appDoc.setTrffile(trfFile1);
		}

		appDoc.setConsumerApplicationDetail(consumerApplicationDetail);
		ApplicationDocument save = applicationDocumentRepository.save(appDoc);

		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}

	@PostMapping("nistha-lab-TA-submit-data")
	public Response<ReSampling> saveNisthaLabTAsubmitData(@RequestPart String consumerApplicationNo,
			@RequestPart String dtrAcceptOrNot,
			@RequestPart String  remark) throws ConsumerApplicationDetailException {

		ReSampling reSample = reSamplingRepository.findByConAppNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(
						new Response<>(HttpCode.NULL_OBJECT, "Application not found in Re-sampling table")));
		reSample.setDtrAcceptOrNot(dtrAcceptOrNot);
		reSample.setTaAcceptDtrOrNotDate(LocalDateTime.now() + "");
	
		reSample.setRemark(remark);
	if(dtrAcceptOrNot.equals("reject")) {
		reSample.setDtrPassOrFail(dtrAcceptOrNot);
	}
	
		ReSampling save = reSamplingRepository.save(reSample);

		Response res = new Response();

		if (dtrAcceptOrNot.equals("reject")) {
			reSample.setDtrAcceptOrNot(dtrAcceptOrNot);
			ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
					.findByConsumerApplicationNo(consumerApplicationNo).get();
			
			if (consumerApplicationDetail == null) {
				res.setMessage("data not foun");
				res.setCode("404");
				return res;
			}
			ApplicationDocument appDoc = null;
			appDoc = applicationDocumentRepository
					.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());
			appDoc.setTrffile(null);
			appDoc.setGetPassfile(null);		
			applicationDocumentRepository.save(appDoc);
			
			res.setMessage("Data not accepet by Nista lab");
			res.setCode("200");
			return res;
		}
		else {
			res.setList(Arrays.asList(save));
			res.setMessage("Data Accepet by Nista lab");
			res.setCode("200");
			return res;
		}

	
	} 
	
	@PostMapping("/saveReversiveGetPassPdfaAndtrfUploadFile")
	public ResponseEntity<?> saveReversiveGetPassPdfaAndtrfUploadFile(@RequestPart String consumerApplicationNo,
			@RequestPart(required = false) MultipartFile getRivicePassFilee
	)
			throws DocumentTypeException, ConsumerApplicationDetailException {

		ReSampling findByConAppNo = reSamplingRepository.findByConAppNo(consumerApplicationNo).get();

		if (findByConAppNo == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application not found "));
		}

		findByConAppNo.setGatPassDate(LocalDateTime.now() + "");
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo).get();
		if (consumerApplicationDetail == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));

		}
		ApplicationDocument appDoc = null;
		appDoc = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());
		if (appDoc == null) {
			appDoc = new ApplicationDocument();
		}

		Upload REVgetPassFile = null;


		if (getRivicePassFilee != null) {
			REVgetPassFile = uploadService.uploadFile(getRivicePassFilee, "REV_GET_PASS_PIC");
			appDoc.setRevGetPassfile(REVgetPassFile);
		}
		
		appDoc.setConsumerApplicationDetail(consumerApplicationDetail);
		ApplicationDocument save = applicationDocumentRepository.save(appDoc);

		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}
	
	
//	 new method for multipal dtr 
	
//	@PostMapping("/saveGetPassPdfaAndtrfUploadFile")
//	public ResponseEntity<?> saveGetPassPdf1(@RequestPart String consumerApplicationNo,
//			@RequestPart(required = false) MultipartFile getPassFilee,
//			@RequestPart(required = false) MultipartFile trfFile,
//			@RequestPart(required = true) Long id )
//			throws DocumentTypeException, ConsumerApplicationDetailException {
//
//		ReSampling findByConAppNo = reSamplingRepository.findByConAppNoAndId(consumerApplicationNo,id);
//
//		if (findByConAppNo == null) {
//			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application not found "));
//		}
//
//		findByConAppNo.setDate(LocalDateTime.now() + "");
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
//				.findByConsumerApplicationNo(consumerApplicationNo).get();
//		if (consumerApplicationDetail == null) {
//			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));
//
//		}
//		ApplicationDocument appDoc = null;
//		appDoc = applicationDocumentRepository
//				.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());
//		if (appDoc == null) {
//			appDoc = new ApplicationDocument();
//		}
//
//		Upload getPassFile = null;
//		Upload trfFile1 = null;
//
//		if (getPassFilee != null) {
//			getPassFile = uploadService.uploadFile(getPassFilee, "GET_PASS_PIC");
//			appDoc.setGetPassfile(getPassFile);
//		}
//		if (trfFile != null) {
//			trfFile1 = uploadService.uploadFile(trfFile, "TRF_FILE");
//			appDoc.setTrffile(trfFile1);
//		}
//
//		appDoc.setConsumerApplicationDetail(consumerApplicationDetail);
//		ApplicationDocument save = applicationDocumentRepository.save(appDoc);
//
//		return ResponseEntity
//				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
//						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));
//
//	}
	
	
//	@PostMapping("/saveReversiveGetPassPdfaAndtrfUploadFile")
//	public ResponseEntity<?> saveReversiveGetPassPdfaAndtrfUploadFile1(@RequestPart String consumerApplicationNo,
//			@RequestPart(required = false) MultipartFile getRivicePassFilee,
//			@RequestPart Long id
//	)
//			throws DocumentTypeException, ConsumerApplicationDetailException {
//
//		ReSampling findByConAppNo = reSamplingRepository.findByConAppNo(consumerApplicationNo,id);
//
//		if (findByConAppNo == null) {
//			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application not found "));
//		}
//
//		findByConAppNo.setGatPassDate(LocalDateTime.now() + "");
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
//				.findByConsumerApplicationNo(consumerApplicationNo).get();
//		if (consumerApplicationDetail == null) {
//			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));
//
//		}
//		ApplicationDocument appDoc = null;
//		appDoc = applicationDocumentRepository
//				.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());
//		if (appDoc == null) {
//			appDoc = new ApplicationDocument();
//		}
//
//		Upload REVgetPassFile = null;
//
//
//		if (getRivicePassFilee != null) {
//			REVgetPassFile = uploadService.uploadFile(getRivicePassFilee, "REV_GET_PASS_PIC");
//			appDoc.setRevGetPassfile(REVgetPassFile);
//		}
//		
//		appDoc.setConsumerApplicationDetail(consumerApplicationDetail);
//		ApplicationDocument save = applicationDocumentRepository.save(appDoc);
//
//		return ResponseEntity
//				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
//						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));
//
//	}

}
