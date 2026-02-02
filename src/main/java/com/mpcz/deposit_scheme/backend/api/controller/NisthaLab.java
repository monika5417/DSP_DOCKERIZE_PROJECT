package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.GatePassChallanByNistraLab;
import com.mpcz.deposit_scheme.backend.api.domain.ReSampling;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.GatePassChallanByNistraLabRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ReSamplingRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/nistha-lab")
public class NisthaLab {

	@Autowired
	private UploadService uploadService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private ReSamplingRepository reSamplingRepository;

	@Autowired
	private GatePassChallanByNistraLabRepository gatePassChallanByNistraLabRepository;

//	nistha lab DGM  testing-report-upload
	@PostMapping("/testing-report-upload")
	public ResponseEntity<?> saveGetPassPdf(@RequestPart String consumerApplicationNo,
			@RequestPart(required = true) MultipartFile testingReport,
			@RequestPart(required = true) String dtrPassOrFail, @RequestPart(required = true) String remarkDGM)
			throws DocumentTypeException, ConsumerApplicationDetailException {

		if (testingReport.isEmpty() || testingReport == null) {
			return ResponseEntity
					.ok(new Response(HttpCode.NOT_ACCEPTABLE, "Doc Testing Reposrt  File is null or empty"));
		}

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo).get();

		if (consumerApplicationDetail == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));

		}
		
		ApplicationDocument appDoc = null;
		ApplicationDocument save = null;
		appDoc = applicationDocumentRepository.findByconsumerApplicationDetail_consumerApplicationId(
				consumerApplicationDetail.getConsumerApplicationId());

		if (appDoc == null) {
			appDoc = new ApplicationDocument();
		}

		Upload testReport = null;

		testReport = uploadService.uploadFile(testingReport, "TEST_REPORT");
		if (testReport == null || testReport == null) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Document Test Not Uploaded"));
		}

		appDoc.setTestReportFile(testReport);
		appDoc.setConsumerApplicationDetail(consumerApplicationDetail);

		

		save = applicationDocumentRepository.save(appDoc);
		ReSampling findByConAppNo = reSamplingRepository.findByConAppNo(consumerApplicationNo).get();
		
		findByConAppNo.setDtrPassOrFail(dtrPassOrFail);
		reSamplingRepository.save(findByConAppNo);
		
		List<ReSampling> findByChildApplicationNo = reSamplingRepository
				.findByChildApplicationNo(findByConAppNo.getParantApplicationNo());

		findByChildApplicationNo.stream().forEach(re -> {
			
			re.setRemarkDGM(remarkDGM);
			if (dtrPassOrFail.equals("reject")) {
				re.setDtrPassOrFail("Fail");
			}else {
				re.setDtrPassOrFail("Pass");
			}
			reSamplingRepository.save(re);
		});

		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}

	@PostMapping("accepteMaterial")
	public ResponseEntity<?> accepteMaterial(@RequestPart String consumerApplicationNo,
			@RequestPart String materialrecived) {
		ReSampling findByConAppNo = reSamplingRepository.findByConAppNo(consumerApplicationNo).get();
		findByConAppNo.setMaterialRecivedInLab(materialrecived);
		ReSampling save = reSamplingRepository.save(findByConAppNo);
		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}

	@PostMapping("nistha-lab-genreated-get-pass")
	public ResponseEntity<?> nisthaLabGenratedGetPass(
			@RequestBody GatePassChallanByNistraLab gatePassChallanByNistraLab) {
		GatePassChallanByNistraLab save = gatePassChallanByNistraLabRepository.save(gatePassChallanByNistraLab);
		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}

	@GetMapping("nistha-lab-get-Getpass-Details")
	public ResponseEntity<?> nisthaLabGenratedGetPass(@RequestPart String consumerApplicationNumver) {
		GatePassChallanByNistraLab save = gatePassChallanByNistraLabRepository
				.findByConsumerApplicationNumber(consumerApplicationNumver);

		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}

}
