package com.mpcz.deposit_scheme.backend.api.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

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
import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ReSampling;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.VendorAddMaterial;
import com.mpcz.deposit_scheme.backend.api.domain.VendorWorkOrder;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ReSamplingRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorAddMaterialRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorWorkOrderRepository;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;

@RestController
@RequestMapping("/ReSampling")
@CrossOrigin(value = "*", maxAge = 3600)
public class ReSamplinController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private VendorAddMaterialRepository vendorAddMaterialRepository;

	@Autowired
	private ReSamplingRepository reSamplingRepository;

	@Autowired
	UploadService uploadService;

	@Autowired
	ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	VendorWorkOrderRepository vendorWorkOrderRepository;
	
	
	@Autowired
	private MessageProperties messageProperties;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	SMSDirectService smsDirectService;

	@GetMapping("/getSampleData/{flagNo}")
	public ResponseEntity<?> getConsumerApplicationDataFromView1(@PathVariable Long flagNo) {

		List<Map<String, ?>> reSampleData = consumerApplicationDetailRepository.getReSampleData(flagNo);

		return ResponseEntity.ok(Objects.isNull(reSampleData)
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", reSampleData));

	}

//	@GetMapping("/getReSamplingDataByContractorAuthentication/{authenticationId}")
//	public ResponseEntity<?> getReSamplingDataByContractorAuthentication(@PathVariable String authenticationId) {
//
//		List<Map<String, ?>> reSampleData = consumerApplicationDetailRepository
//				.findReSamplingDataByContractorAuthentication(authenticationId);
//
//		int totalSize = reSampleData.size(); // 25
//		int groupSize = 5; // 5 items per group
//
//		for (int outer = 0; outer < totalSize; outer += groupSize) {
//			Map<String, ?> row = reSampleData.get(outer);
//			
//			List<VendorAddMaterial> findByConsumerApplicationNumber = VendorAddMaterialRepository.findByConsumerApplicationNumber(String.valueOf(row.get("CONSUMER_NAME")));
//			
//			System.out.println( Math.random() * 1000000 + Math.random() * 1000000);
//			
//			System.out.println("  -> Consumer Name oute r: " + String.valueOf(row.get("CONSUMER_NAME")));
//			for (int inner = outer; inner < outer + groupSize && inner < totalSize; inner++) {
//				Map<String, ?> row1 = reSampleData.get(inner);
//
//			
//				System.out.println("  -> Consumer Name oinner :" + String.valueOf(row1.get("CONSUMER_NAME")));
//			}
//
//
//			return ResponseEntity.ok(Objects.isNull(reSampleData)
//					? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
//					: new Response<>(HttpCode.OK, "Data Retrived successfully ", reSampleData));
//
//		}
//		return null;
//	}

	@PostMapping("/post-resample-data")
	public ResponseEntity<?> postResampleData(@RequestBody List<ReSampling> sample) {

		List<ReSampling> saveAll = reSamplingRepository.saveAll(sample);

		List<VendorAddMaterial> findByConsumerApplicationNumber = vendorAddMaterialRepository
				.findByConsumerApplicationNumber(sample.get(0).getConAppNo());

		findByConsumerApplicationNumber.stream().forEach(updateVendorAddMaterial -> {
			updateVendorAddMaterial.setResamplingFlag(1l);
			updateVendorAddMaterial.setPostFlag(1l);
			vendorAddMaterialRepository.save(updateVendorAddMaterial);

		});

		return ResponseEntity
				.ok(saveAll.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
						: new Response<>(HttpCode.OK, "Data Retrived successfully ", saveAll));

	}

	@GetMapping("/safling-data-by-autenotication-ID/{authenticationId}")
	public ResponseEntity<?> getSaflingData(@PathVariable String authenticationId) {

		Response res = new Response<>();
		List<ReSampling> reSamplinglistDb = reSamplingRepository.findByAuticationIdAndShufflingFlag(authenticationId,
				0l);

		long uniqueCurrentTimeMS = uniqueCurrentTimeMS();
		String applictionfullname = "PC" + uniqueCurrentTimeMS;

		int totalSize = reSamplinglistDb.size(); // 6
		int groupSize = 5; // 5 items per group

		for (int outer = 0; outer < totalSize; outer += groupSize) {

			for (int inner = outer; inner < outer + groupSize && inner < totalSize; inner++) {

				if (inner == 0 || inner == 5) {
					ReSampling reSampling1 = reSamplinglistDb.get(inner);
					ReSampling findByConAppNoDB = reSamplingRepository.findByConAppNoAndId(reSampling1.getConAppNo(),
							reSampling1.getId());

					findByConAppNoDB.setShuffling("Data Suffling");
					findByConAppNoDB.setParantApplicationNo(applictionfullname);
					findByConAppNoDB.setChildApplicationNo(applictionfullname);
					findByConAppNoDB.setShufflingFlag(Long.valueOf(1));
					ReSampling save = reSamplingRepository.save(findByConAppNoDB);

					System.out.println(save);

				} else {

					ReSampling reSampling1 = reSamplinglistDb.get(inner);
					ReSampling findByConAppNoDB = reSamplingRepository.findByConAppNoAndId(reSampling1.getConAppNo(),
							reSampling1.getId());

					findByConAppNoDB.setShuffling("Data Suffling");
					findByConAppNoDB.setChildApplicationNo(applictionfullname);
					findByConAppNoDB.setShufflingFlag(Long.valueOf(2));
					reSamplingRepository.save(findByConAppNoDB);

				}

			}

		}

		res.setCode("200");
		res.setMessage("data update");
		res.setList(reSamplinglistDb);
		return ResponseEntity.ok(res);

	}

	private static final AtomicLong LAST_TIME_MS = new AtomicLong();

	public static long uniqueCurrentTimeMS() {
		long now = System.currentTimeMillis();
		while (true) {
			long lastTime = LAST_TIME_MS.get();
			if (lastTime >= now)
				now = lastTime + 1;
			if (LAST_TIME_MS.compareAndSet(lastTime, now))
				return now;
		}
	}

	@GetMapping("/getPtrValue/{consumerApplicationNo}")
	public ResponseEntity<?> getPtrValue(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		reSamplingRepository.findByConAppNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(
						new Response<>(HttpCode.NULL_OBJECT, "Application not found in Re-sampling table")));

		List<ReSampling> conAppNoAndTotalNoOfPtrIsNotNullDB = reSamplingRepository
				.findByConAppNoAndCapacityOfPtrIsNotNull(consumerApplicationNo);

		return ResponseEntity.ok(Objects.isNull(conAppNoAndTotalNoOfPtrIsNotNullDB)
				? new Response<>(HttpCode.NULL_OBJECT, "Ptr values not found for this application no.")
				: new Response<>(HttpCode.OK, "Ptr values found successfully", conAppNoAndTotalNoOfPtrIsNotNullDB));

	}

	@GetMapping("/getDtrValue/{consumerApplicationNo}")
	public ResponseEntity<?> getDtrValue(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		reSamplingRepository.findByConAppNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(
						new Response<>(HttpCode.NULL_OBJECT, "Application not found in Re-sampling table")));

		List<ReSampling> conAppNoAndTotalNoOfPtrIsNotNullDB = reSamplingRepository
				.findByConAppNoAndCapacityOfPtrIsNotNull(consumerApplicationNo);

		return ResponseEntity.ok(Objects.isNull(conAppNoAndTotalNoOfPtrIsNotNullDB)
				? new Response<>(HttpCode.NULL_OBJECT, "Ptr values not found for this application no.")
				: new Response<>(HttpCode.OK, "Ptr values found successfully", conAppNoAndTotalNoOfPtrIsNotNullDB));

	}

	@GetMapping("/safling-data-by-Vendor-table-autenotication-ID/{authenticationId}")
	public ResponseEntity<?> getSaflingDataOfVendorTable(@PathVariable String authenticationId) {

		Response res = new Response<>();
//		List<ReSampling> reSamplinglistDb = reSamplingRepository.findByAuticationId(authenticationId);

		List<VendorAddMaterial> reSamplinglistDb = vendorAddMaterialRepository
				.getReSampleDataByVendorTable(authenticationId);

		int totalSize = reSamplinglistDb.size(); // 6
		int groupSize = 5; // 5 items per group

		for (int outer = 0; outer < totalSize; outer += groupSize) {
			long uniqueCurrentTimeMS = uniqueCurrentTimeMS();
			String applictionfullname = "PC" + uniqueCurrentTimeMS;

			for (int inner = outer; inner < outer + groupSize && inner < totalSize; inner++) {

				if (inner == 0 || inner == 5) {

					VendorAddMaterial vendorAddMaterial = reSamplinglistDb.get(inner);
					VendorAddMaterial findByIdAndConsumerApplicationNumber = vendorAddMaterialRepository
							.findByIdAndConsumerApplicationNumber(vendorAddMaterial.getId(),
									vendorAddMaterial.getConsumerApplicationNumber())
							.get();

					findByIdAndConsumerApplicationNumber.setParantApplicationNo(applictionfullname);
					findByIdAndConsumerApplicationNumber.setPostFlag(Long.valueOf(1));
					findByIdAndConsumerApplicationNumber.setShufflingFlag(3L);
					findByIdAndConsumerApplicationNumber.setParticipateForTestingAndSampling("DONE");
					findByIdAndConsumerApplicationNumber.setTestingsampleSelected("DONE");
					findByIdAndConsumerApplicationNumber.setReSamplingData("1l");
					VendorAddMaterial save2 = vendorAddMaterialRepository.save(findByIdAndConsumerApplicationNumber);

				} else {
					VendorAddMaterial vendorAddMaterial = reSamplinglistDb.get(inner);
					VendorAddMaterial findByIdAndConsumerApplicationNumber = vendorAddMaterialRepository
							.findByIdAndConsumerApplicationNumber(vendorAddMaterial.getId(),
									vendorAddMaterial.getConsumerApplicationNumber())
							.get();
					findByIdAndConsumerApplicationNumber.setReSamplingData("1l");
					findByIdAndConsumerApplicationNumber.setChildApplicationNo(applictionfullname);
					findByIdAndConsumerApplicationNumber.setPostFlag(Long.valueOf(2));
					findByIdAndConsumerApplicationNumber.setParticipateForTestingAndSampling("DONE");

					VendorAddMaterial save2 = vendorAddMaterialRepository.save(findByIdAndConsumerApplicationNumber);

				}

			}

		}

		res.setCode("200");
		res.setMessage("data update");
		res.setList(reSamplinglistDb);
		return ResponseEntity.ok(res);

	}

	@GetMapping("/getsempleSelectedAndTestingDoneData")
	public ResponseEntity<?> getsempleSelectedAndTestingDoneData() {

		List<Long> l = Arrays.asList(1L, 2L);

		List<VendorAddMaterial> findByPostFlagIN = vendorAddMaterialRepository.findDistinctByPostFlagIn(l);

		return ResponseEntity.ok(findByPostFlagIN.isEmpty()
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", findByPostFlagIN));

	}

	@GetMapping("/getselectTestingData")
	public ResponseEntity<?> getselectTestingData() {

		List<VendorAddMaterial> findByPostFlagIN = vendorAddMaterialRepository.findByShufflingFlag(3L);

		return ResponseEntity.ok(findByPostFlagIN.isEmpty()
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", findByPostFlagIN));

	}

//	-------------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------------

//	@GetMapping("/getSampleDataForConsuemrAppliationDetailTable/{flagNo}")
//	public ResponseEntity<?> getSampleDataForConsuemrAppliationDetailTable(@PathVariable Long flagNo) {
//
//		ArrayList<Long> l = new ArrayList<Long>();
//		l.add(1l);
//		l.add(2l);
//
//		List<Map<String, ?>> reSampleData = consumerApplicationDetailRepository
//				.getReSampleDataForConsuemrApplicationDetails(l);
//
//		return ResponseEntity.ok(Objects.isNull(reSampleData)
//				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
//				: new Response<>(HttpCode.OK, "Data Retrived successfully ", reSampleData));
//
//	}

////	applicaiton ke againts me post data
	@PostMapping("/post-resample-data1")
	public ResponseEntity<?> postResampleData1(@RequestBody List<ReSampling> sample) {

		List<ReSampling> saveAll = reSamplingRepository.saveAll(sample);

//		List<VendorAddMaterial> findByConsumerApplicationNumber = vendorAddMaterialRepository
//				.findByConsumerApplicationNumber(sample.get(0).getConAppNo());

		ConsumerApplicationDetail consumerDetails = consumerApplicationDetailRepository
				.findByConsumerApplicationNo(sample.get(0).getConAppNo()).get();
		
		saveAll.stream().forEach(s->{
			ReSampling findByConAppNo = reSamplingRepository.findByConAppNo(s.getConAppNo()).get();
			
			Circle circleDb = consumerDetails.getDistributionCenter().getDcSubdivision()
					.getSubdivisionDivision().getDivisionCircle();
			

			findByConAppNo.setCircleName(circleDb.getCircle());
			findByConAppNo.setCircleId(circleDb.getCircleId());

			findByConAppNo.setDivisionName(consumerDetails.getDistributionCenter()
					.getDcSubdivision().getSubdivisionDivision().getDivision());
			findByConAppNo.setDC_NAME(consumerDetails.getDistributionCenter().getDcName());
			findByConAppNo.setConsumerName(consumerDetails.getConsumerName());
			findByConAppNo.setAddress(consumerDetails.getAddress());
			findByConAppNo.setDivisionId(consumerDetails.getDistributionCenter()
					.getDcSubdivision().getSubdivisionDivision().getDivisionId());
			reSamplingRepository.save(findByConAppNo);
			
		});
		consumerDetails.setReSamplingFlag(1l);
		ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(consumerDetails);

		return ResponseEntity
				.ok(saveAll.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
						: new Response<>(HttpCode.OK, "Data Retrived successfully ", Arrays.asList(saveAll)));

	}

//	 suffing hui api
//	@GetMapping("/safling-data-by-autenotication-ID1/{authenticationId}/{userID}")
//	public ResponseEntity<?> getSaflingData1(@PathVariable String authenticationId,@PathVariable String userID) {
//
//		Response res = new Response<>();
//		List<ReSampling> reSamplinglistDb = reSamplingRepository.findByAuticationIdAndShufflingFlag(authenticationId,
//				0L);
//
//		if (reSamplinglistDb.size() < 4) {
//			res.setCode("400");
//			res.setMessage("data suffling is not possible because contractor doesn't have applicaion greaterthan 4");
//			return ResponseEntity.ok(res);
//		}
//
//		long uniqueCurrentTimeMS = uniqueCurrentTimeMS();
//		String applictionfullname = "PC" + uniqueCurrentTimeMS;
//
//		int totalSize = reSamplinglistDb.size(); // 6
//		int groupSize = 5; // 5 items per group
//
//		for (int outer = 0; outer < totalSize; outer += groupSize) {
//
//			for (int inner = outer; inner < outer + groupSize && inner < totalSize; inner++) {
//
//				if (inner == 0 || inner == 5) {
//					ReSampling reSampling1 = reSamplinglistDb.get(inner);
//					ReSampling findByConAppNoDB = reSamplingRepository.findByConAppNoAndId(reSampling1.getConAppNo(),
//							reSampling1.getId());
//
//					findByConAppNoDB.setShuffling("Data Suffling");
//					findByConAppNoDB.setParantApplicationNo(applictionfullname);
//					findByConAppNoDB.setShufflingFlag(Long.valueOf(1));
//			
//
//					ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
//							.findByConsumerApplicationNo(reSampling1.getConAppNo()).get();
//					consumerApplicationDetail.setReSamplingFlag(2l);
//					consumerApplicationDetailRepository.save(consumerApplicationDetail);
//					
//
//					Circle circleDb = consumerApplicationDetail.getDistributionCenter().getDcSubdivision()
//							.getSubdivisionDivision().getDivisionCircle();
//					
//
//					findByConAppNoDB.setCircleName(circleDb.getCircle());
//					findByConAppNoDB.setCircleId(circleDb.getCircleId());
//
//					findByConAppNoDB.setDivisionName(consumerApplicationDetail.getDistributionCenter()
//							.getDcSubdivision().getSubdivisionDivision().getDivision());
//					findByConAppNoDB.setDC_NAME(consumerApplicationDetail.getDistributionCenter().getDcName());
//					findByConAppNoDB.setConsumerName(consumerApplicationDetail.getConsumerName());
//					findByConAppNoDB.setAddress(consumerApplicationDetail.getAddress());
//
//					VendorWorkOrder findByApplicationNo = vendorWorkOrderRepository
//							.findByApplicationNo(reSampling1.getConAppNo());
//					findByConAppNoDB.setRegionId(circleDb.getCircleRegion().getRegionId());
//					findByConAppNoDB.setRegionName(circleDb.getCircleRegion().getRegion());
//					findByConAppNoDB.setWorkOrderNumber(findByApplicationNo.getWorkOrderNo());
//					findByConAppNoDB.setApplicationId(consumerApplicationDetail.getConsumerApplicationId());
//					
//					
//					ReSampling save = reSamplingRepository.save(findByConAppNoDB);
//
//					System.out.println(save);
//					
//					User user = userRepository.findByUserId(userID).get();
//					final SMSRequest smsRequest = new SMSRequest();
//
//
//					String message = "";
//
//					
//					message = MessageFormat.format(messageProperties.getSendMsgReSamplingForAe(),
//							new Object[] { reSampling1.getConAppNo(), save.getDtrLeftingDate().toString() });
//
//					smsRequest.setTemplateId(messageProperties.getSendMsgReSamplingForAeTamplateId());
//					smsRequest.setText(message);
//					smsRequest.setHinglish(1l);
//					smsRequest.setMobileNo(user.getMobileNo());
//					
//					try {
//						String sendMessage = smsDirectService.sendMessage(smsRequest);
//						System.err.println("sendMessage" + sendMessage);
//					} catch (Exception e) {
//					
//						e.printStackTrace();
//					}
//				
//
//				} else {
//
//					ReSampling reSampling1 = reSamplinglistDb.get(inner);
//					ReSampling findByConAppNoDB = reSamplingRepository.findByConAppNoAndId(reSampling1.getConAppNo(),
//							reSampling1.getId());
//
//					findByConAppNoDB.setShuffling("Data Suffling");
//					findByConAppNoDB.setChildApplicationNo(applictionfullname);
//					findByConAppNoDB.setShufflingFlag(Long.valueOf(2));
//
//					ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
//							.findByConsumerApplicationNo(reSampling1.getConAppNo()).get();
//					consumerApplicationDetail.setReSamplingFlag(2l);
//					consumerApplicationDetailRepository.save(consumerApplicationDetail);
//
//					Circle circleDb = consumerApplicationDetail.getDistributionCenter().getDcSubdivision()
//							.getSubdivisionDivision().getDivisionCircle();
//
//					findByConAppNoDB.setCircleName(circleDb.getCircle());
//					findByConAppNoDB.setCircleId(circleDb.getCircleId());
//
//					findByConAppNoDB.setDivisionName(consumerApplicationDetail.getDistributionCenter()
//							.getDcSubdivision().getSubdivisionDivision().getDivision());
//					findByConAppNoDB.setDC_NAME(consumerApplicationDetail.getDistributionCenter().getDcName());
//					findByConAppNoDB.setConsumerName(consumerApplicationDetail.getConsumerName());
//					findByConAppNoDB.setAddress(consumerApplicationDetail.getAddress());
//					findByConAppNoDB.setRegionId(circleDb.getCircleRegion().getRegionId());
//					findByConAppNoDB.setRegionName(circleDb.getCircleRegion().getRegion());
//					VendorWorkOrder findByApplicationNo = vendorWorkOrderRepository
//							.findByApplicationNo(reSampling1.getConAppNo());
//
//					findByConAppNoDB.setWorkOrderNumber(findByApplicationNo.getWorkOrderNo());
//					findByConAppNoDB.setApplicationId(consumerApplicationDetail.getConsumerApplicationId());
//
//					reSamplingRepository.save(findByConAppNoDB);
//
//				}
//
//			}
//
//		}
////		User user = userRepository.findByUserId(userID).get();
////		final SMSRequest smsRequest = new SMSRequest();
////
////
////		String message = "";
////
////		
////		message = MessageFormat.format(messageProperties.getSendMsgReSamplingForAe(),
////				new Object[] { "SV989898", "03-11-2025"});
////
////		smsRequest.setTemplateId(messageProperties.getSendMsgReSamplingForAeTamplateId());
////		smsRequest.setText(message);
////		smsRequest.setHinglish(1l);
////		smsRequest.setMobileNo("9340302532");
////		
////		try {
////			String sendMessage = smsDirectService.sendMessage(smsRequest);
////			System.err.println("sendMessage" + sendMessage);
////		} catch (Exception e) {
////		
////			e.printStackTrace();
////		}
//	
//		res.setCode("200");
//		res.setMessage("data update");
//		res.setList(reSamplinglistDb);
//		return ResponseEntity.ok(res);
//
//	}

//	suffling hone ke bad de hui api 
//	@GetMapping("/getSampleDataForReSamplingTable/{flagNo}")
//	public ResponseEntity<?> getSampleDataForReSamplingTable(@PathVariable Long flagNo) {
//
//		List<ReSampling> findByShufflingFlag = reSamplingRepository.findByShufflingFlag(flagNo);
//
//		return ResponseEntity.ok(findByShufflingFlag.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data not found")
//				: new Response<>(HttpCode.OK, "Data Retrived successfully ", findByShufflingFlag));
//
//	}

// dtr ke photo upload karga (jo seel kiye hai)
	@PostMapping("/uploadPhotoDtr")
	public ResponseEntity<?> saveAddressProofData(@RequestPart String consumerApplicationNo,
			@RequestPart(required = true) MultipartFile photo1, @RequestPart(required = true) MultipartFile photo2)
			throws DocumentTypeException, ConsumerApplicationDetailException {

		if (photo1.isEmpty() || photo1 == null || photo2.isEmpty() || photo2 == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "Doc DTR File is null or empty"));
		}
		if (consumerApplicationNo.isEmpty() || consumerApplicationNo == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));

		}

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo).get();
		if (consumerApplicationDetail == null) {
			return ResponseEntity.ok(new Response(HttpCode.NOT_ACCEPTABLE, "application is null or empty"));

		}

		ApplicationDocument appDoc = new ApplicationDocument();

		Upload docDtr1 = null;
		Upload docDtr2 = null;

		if (photo1 != null && photo2 != null) {
			docDtr1 = uploadService.uploadFile(photo1, "DTR_PIC");
			docDtr2 = uploadService.uploadFile(photo2, "DTR_PIC");
			if (docDtr1 == null || docDtr2 == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Notry Not Uploaded"));
			}

		}
		appDoc.setDocDtrFile1(docDtr1);
		appDoc.setDocDtrFile2(docDtr2);
		appDoc.setConsumerApplicationDetail(consumerApplicationDetail);
		ApplicationDocument save = applicationDocumentRepository.save(appDoc);

		return ResponseEntity
				.ok(Objects.isNull(save) ? new Response(HttpCode.NULL_OBJECT, "Data not saved successfully")
						: new Response(HttpCode.UPDATED, "Data Updated successfully", Arrays.asList(save)));

	}

//	consuemer ki detaisl jese (circle , dc ,divison,work order date,parent application no , etc )
	@GetMapping("/getSampleDataForConsuemrAppliationDetailTableByConsumerApplicaionNo/{consumerApplicaionNO}")
	public ResponseEntity<?> getSampleDataForConsuemrAppliationDetailTableByConsumerApplicaionNo(
			@PathVariable String consumerApplicaionNO) {

		List<Map<String, ?>> reSampleData = consumerApplicationDetailRepository
				.getReSampleDataForConsuemrApplicationDetailsByApplicationNo(consumerApplicaionNO);

		return ResponseEntity.ok(Objects.isNull(reSampleData)
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", reSampleData));

	}
	
	@GetMapping("/getReSampleData/{consumerApplicationNo}")
	public Response<ReSampling> getReSampleData(@PathVariable String consumerApplicationNo)
	        throws ConsumerApplicationDetailException {

	    ReSampling reSample = reSamplingRepository.findByConAppNo(consumerApplicationNo)
	            .orElseThrow(() -> new ConsumerApplicationDetailException(
	                    new Response<>(HttpCode.NULL_OBJECT, "Application not found in Re-sampling table")));

	    Response response = new Response();
	    if(reSample==null) {
	    	 
	 	    response.setCode("404");
	 	    response.setMessage("data not found");
	    }else {
	    	 response.setList(Arrays.asList(reSample));
	 	    response.setCode("200");	
	    }
	   
	 return response;
	}

	@PostMapping("/saveDtrLiftingDAte/{authenticationId}/{dtrLiftingDate}")
	public ResponseEntity<?> saveDtrLiftingDAte(@PathVariable String authenticationId,@PathVariable String dtrLiftingDate) {
		Response res = new Response<>();
		List<ReSampling> reSamplinglistDb = reSamplingRepository.findByAuticationIdAndShufflingFlag(authenticationId,
				0L);
      ArrayList<ReSampling> a = new ArrayList();
      
		if (reSamplinglistDb.size() < 4) {
			res.setCode("400");
			res.setMessage("DTR lifting date submission is not possible because the contractor does not have more than 4 applications.");
			return ResponseEntity.ok(res);
		}

		reSamplinglistDb.stream().forEach(m->{
			m.setDtrLeftingDate(dtrLiftingDate);
			ReSampling save = reSamplingRepository.save(m);		
			if(save!=null) {
				a.add(save);
			}
		});
		
		res.setCode("200");
		res.setMessage("Data updated successfully ");
		res.setList(a);
		return ResponseEntity.ok(res);	
	}

	@PostMapping("/saveDtrLiftingDAte1/{authenticationId}/{dtrLiftingDate}")
	public ResponseEntity<?> saveDtrLiftingDAte1(@PathVariable String authenticationId,@PathVariable String dtrLiftingDate) {
		Response res = new Response<>();
		List<ReSampling> reSamplinglistDb = reSamplingRepository.findByAuticationIdAndShufflingFlag(authenticationId,
				0L);
      ArrayList<ReSampling> a = new ArrayList();
      
		reSamplinglistDb.stream().forEach(m->{
			m.setDtrLeftingDate(dtrLiftingDate);
			ReSampling save = reSamplingRepository.save(m);		
			
			
			
			if(save!=null) {
				a.add(save);
			}
		});
		
		
		
		
		
		
		res.setCode("200");
		res.setMessage("Data updated successfully ");
		res.setList(a);
		return ResponseEntity.ok(res);	
	}
	
	@GetMapping("/getSampleDataForConsuemrAppliationDetailTableNotNsc/{flagNo}")
	public ResponseEntity<?> getSampleDataForConsuemrAppliationDetailTableNotNsc(@PathVariable Long flagNo) {

		ArrayList<Long> l = new ArrayList<Long>();
		l.add(1l);
		l.add(2l);

		List<Map<String, ?>> reSampleData = consumerApplicationDetailRepository
				.getReSampleDataForConsuemrApplicationDetailsNotNsc(l);

		return ResponseEntity.ok(Objects.isNull(reSampleData)
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", reSampleData));

	}
	
	//==========================================================================
//	multipal dtr ke liye
	
//	applicaiton ke againts me post data
//	@PostMapping("/post-resample-data1")
//	public ResponseEntity<?> postResampleData1(@RequestBody List<ReSampling> sample) {
//
//		List<ReSampling> saveAll = reSamplingRepository.saveAll(sample);
//
//
//
//		ConsumerApplicationDetail consumerDetails = consumerApplicationDetailRepository
//				.findByConsumerApplicationNo(sample.get(0).getConAppNo()).get();
//		
//		List<ReSampling> findByConApp= null;
//		try {
//			 findByConApp = reSamplingRepository.findByConAppNo_1(sample.get(0).getConAppNo());
//
//		}catch (Exception e) {
//			e.printStackTrace();b
//		}
//			
//			findByConApp.stream().forEach(s->{
//				
//				ReSampling findByConAppNo = reSamplingRepository.findByConAppNoAndId(s.getConAppNo(),s.getId());
//				
//				
//			Circle circleDb = consumerDetails.getDistributionCenter().getDcSubdivision()
//					.getSubdivisionDivision().getDivisionCircle();
//			
//
//			findByConAppNo.setCircleName(circleDb.getCircle());
//			findByConAppNo.setCircleId(circleDb.getCircleId());
//
//			findByConAppNo.setDivisionName(consumerDetails.getDistributionCenter()
//					.getDcSubdivision().getSubdivisionDivision().getDivision());
//			findByConAppNo.setDC_NAME(consumerDetails.getDistributionCenter().getDcName());
//			findByConAppNo.setConsumerName(consumerDetails.getConsumerName());
//			findByConAppNo.setAddress(consumerDetails.getAddress());
//			reSamplingRepository.save(findByConAppNo);
//		});
//		consumerDetails.setReSamplingFlag(1l);
//		ConsumerApplicationDetail save = consumerApplicationDetailRepository.save(consumerDetails);
//
//		return ResponseEntity
//				.ok(saveAll.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data not found for this application no.")
//						: new Response<>(HttpCode.OK, "Data Retrived successfully ", Arrays.asList(saveAll)));
//
//	}
//	
	
	
//	first api working
	@PostMapping("/getSampleDataForConsuemrAppliationDetailTable2")
	public ResponseEntity<?> getSampleDataForConsuemrAppliationDetailTable(@RequestBody HashMap<String,Integer> l)  {

//		{
//		    "discom" : 0,
//		    "region" : 0,
//		    "circle" : 0,
//		    "division" : 0,
//		    "subDivision" : 0,
//		    "distributionCenter" :3 
//
//		}

		ArrayList<Integer> dcid = new ArrayList<>();
		Integer discomId = l.get("discom");	
		Integer regionId = l.get("region");	
		Integer circleId = l.get("circle");	
		Integer divisionId = l.get("division");
		Integer subDivisionId = l.get("subDivision");
		Integer distributionCenterId = l.get("distributionCenter");
		ArrayList<Integer> dcIdByDiscomId =null;
		if(discomId>0) {
			 dcIdByDiscomId = consumerApplicationDetailRepository.getDcIdByDiscomId(discomId);
		}else if (regionId>0) {
			 dcIdByDiscomId = consumerApplicationDetailRepository.getDcIdByRegionId(regionId);
		}else if(circleId>0){
			 dcIdByDiscomId = consumerApplicationDetailRepository.getDcIdByCircleId(circleId);
		}else if(divisionId>0) {
			 dcIdByDiscomId = consumerApplicationDetailRepository.getDcIdByDivisionId(divisionId);
		}else if(subDivisionId>0) {
			dcIdByDiscomId = consumerApplicationDetailRepository.getDcIdBysubDivisionId(subDivisionId);
		}else if(distributionCenterId>0) {
			dcIdByDiscomId = consumerApplicationDetailRepository.getDcIdByDcId(distributionCenterId);
		}
		else {
			return ResponseEntity.ok(Objects.isNull(null)
					? new Response(HttpCode.NULL_OBJECT, "Data not found for this user id.")
					: new Response<>(HttpCode.OK, "Data Retrived successfully ", null));
		}
		
		List<Map<String, ?>> reSampleData = consumerApplicationDetailRepository
				.getReSampleDataForConsuemrApplicationDetailsByDcId(dcIdByDiscomId);

		return ResponseEntity.ok(reSampleData.isEmpty()
				? new Response(HttpCode.NULL_OBJECT, "Data not found for this user id.")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", reSampleData));

	}
	
//	thired api  for resampling
	@GetMapping("/safling-data-by-autenotication-ID1/{authenticationId}/{userID}/{circleId}/{divisionId}")
	public ResponseEntity<?> getSaflingData1(@PathVariable String authenticationId, @PathVariable String userID,
			@PathVariable Long circleId,@PathVariable Long divisionId) {
		
		
		Response res = new Response<>();
//		List<ReSampling> reSamplinglistDb = reSamplingRepository.findByAuticationIdAndShufflingFlag(authenticationId,
//				0L);
		List<ReSampling> reSamplinglistDb = null;
		if(circleId>0) {
		 reSamplinglistDb = reSamplingRepository
				.findByAuticationIdAndShufflingFlagAndCircleId(authenticationId, 0l, circleId);
		}else {
			 reSamplinglistDb = reSamplingRepository
						.findByAuticationIdAndShufflingFlagAndDivisionId(authenticationId, 0l, divisionId);
		}
		
		
		if (reSamplinglistDb.size() < 4) {
			res.setCode("400");
			res.setMessage("data suffling is not possible because contractor doesn't have applicaion greaterthan 4");
			return ResponseEntity.ok(res);
		}

		long uniqueCurrentTimeMS = uniqueCurrentTimeMS();
		String applictionfullname = "PC" + uniqueCurrentTimeMS;

		int totalSize = reSamplinglistDb.size(); // 6
		int groupSize = 5; // 5 items per group

		for (int outer = 0; outer < totalSize; outer += groupSize) {

			for (int inner = outer; inner < outer + groupSize && inner < totalSize; inner++) {

				if (inner == 0 || inner == 5) {
					ReSampling reSampling1 = reSamplinglistDb.get(inner);
					ReSampling findByConAppNoDB = reSamplingRepository.findByConAppNoAndId(reSampling1.getConAppNo(),
							reSampling1.getId());

					findByConAppNoDB.setShuffling("Data Suffling");
					findByConAppNoDB.setParantApplicationNo(applictionfullname);
					findByConAppNoDB.setShufflingFlag(Long.valueOf(1));

					ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
							.findByConsumerApplicationNo(reSampling1.getConAppNo()).get();
					consumerApplicationDetail.setReSamplingFlag(2l);
					consumerApplicationDetailRepository.save(consumerApplicationDetail);

					Circle circleDb = consumerApplicationDetail.getDistributionCenter().getDcSubdivision()
							.getSubdivisionDivision().getDivisionCircle();

					findByConAppNoDB.setCircleName(circleDb.getCircle());
					findByConAppNoDB.setCircleId(circleDb.getCircleId());

					findByConAppNoDB.setDivisionName(consumerApplicationDetail.getDistributionCenter()
							.getDcSubdivision().getSubdivisionDivision().getDivision());
					findByConAppNoDB.setDC_NAME(consumerApplicationDetail.getDistributionCenter().getDcName());
					findByConAppNoDB.setConsumerName(consumerApplicationDetail.getConsumerName());
					findByConAppNoDB.setAddress(consumerApplicationDetail.getAddress());

					VendorWorkOrder findByApplicationNo = vendorWorkOrderRepository
							.findByApplicationNo(reSampling1.getConAppNo());
					findByConAppNoDB.setRegionId(circleDb.getCircleRegion().getRegionId());
					findByConAppNoDB.setRegionName(circleDb.getCircleRegion().getRegion());
					findByConAppNoDB.setWorkOrderNumber(findByApplicationNo.getWorkOrderNo());
					findByConAppNoDB.setApplicationId(consumerApplicationDetail.getConsumerApplicationId());

					ReSampling save = reSamplingRepository.save(findByConAppNoDB);

					System.out.println(save);

					User user = userRepository.findByUserId(userID).get();
					final SMSRequest smsRequest = new SMSRequest();

					String message = "";

					message = MessageFormat.format(messageProperties.getSendMsgReSamplingForAe(),
							new Object[] { reSampling1.getConAppNo(), save.getDtrLeftingDate().toString() });

					smsRequest.setTemplateId(messageProperties.getSendMsgReSamplingForAeTamplateId());
					smsRequest.setText(message);
					smsRequest.setHinglish(1l);
					smsRequest.setMobileNo(user.getMobileNo());

					try {
						String sendMessage = smsDirectService.sendMessage(smsRequest);
						System.err.println("sendMessage" + sendMessage);
					} catch (Exception e) {

						e.printStackTrace();
					}

				} else {

					ReSampling reSampling1 = reSamplinglistDb.get(inner);
					ReSampling findByConAppNoDB = reSamplingRepository.findByConAppNoAndId(reSampling1.getConAppNo(),
							reSampling1.getId());

					findByConAppNoDB.setShuffling("Data Suffling");
					findByConAppNoDB.setChildApplicationNo(applictionfullname);
					findByConAppNoDB.setShufflingFlag(Long.valueOf(2));

					ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailRepository
							.findByConsumerApplicationNo(reSampling1.getConAppNo()).get();
					consumerApplicationDetail.setReSamplingFlag(2l);
					consumerApplicationDetailRepository.save(consumerApplicationDetail);

					Circle circleDb = consumerApplicationDetail.getDistributionCenter().getDcSubdivision()
							.getSubdivisionDivision().getDivisionCircle();

					findByConAppNoDB.setCircleName(circleDb.getCircle());
					findByConAppNoDB.setCircleId(circleDb.getCircleId());

					findByConAppNoDB.setDivisionName(consumerApplicationDetail.getDistributionCenter()
							.getDcSubdivision().getSubdivisionDivision().getDivision());
					findByConAppNoDB.setDC_NAME(consumerApplicationDetail.getDistributionCenter().getDcName());
					findByConAppNoDB.setConsumerName(consumerApplicationDetail.getConsumerName());
					findByConAppNoDB.setAddress(consumerApplicationDetail.getAddress());
					findByConAppNoDB.setRegionId(circleDb.getCircleRegion().getRegionId());
					findByConAppNoDB.setRegionName(circleDb.getCircleRegion().getRegion());
					VendorWorkOrder findByApplicationNo = vendorWorkOrderRepository
							.findByApplicationNo(reSampling1.getConAppNo());

					findByConAppNoDB.setWorkOrderNumber(findByApplicationNo.getWorkOrderNo());
					findByConAppNoDB.setApplicationId(consumerApplicationDetail.getConsumerApplicationId());

					reSamplingRepository.save(findByConAppNoDB);

				}

			}

		}


		res.setCode("200");
		res.setList(reSamplinglistDb);
		return ResponseEntity.ok(res);

	}

	@GetMapping("/getSampleDataForReSamplingTable/{flagNo}/{circleId}/{divisionId}/{regionId}")
	public ResponseEntity<?> getSampleDataForReSamplingTable(@PathVariable Long flagNo,@PathVariable Long circleId,@PathVariable Long divisionId,@PathVariable Long regionId) {
		List<ReSampling> findByShufflingFlag = null;
		if(circleId>0) {
			findByShufflingFlag = reSamplingRepository.findByShufflingFlagAndCircleId(flagNo,circleId);
		}else if(divisionId>0) {
			findByShufflingFlag = reSamplingRepository.findByShufflingFlagAndDivisionId(flagNo,divisionId);

		}else {
			findByShufflingFlag = reSamplingRepository.findByShufflingFlagAndRegionId(flagNo,regionId);

		}

		return ResponseEntity.ok(findByShufflingFlag.isEmpty() ? new Response(HttpCode.NULL_OBJECT, "Data not found")
				: new Response<>(HttpCode.OK, "Data Retrived successfully ", findByShufflingFlag));

	}
}