package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.controller.ContractorDetailForBidController;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.domain.GeoLocation;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.GeoLocationRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UploadRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.GeoLocationService;
import com.mpcz.deposit_scheme.backend.api.services.InvoiceService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;

@Service
public class GeoLocationServiceImp implements GeoLocationService {

	Logger logger = LoggerFactory.getLogger(GeoLocationServiceImp.class);

	@Autowired
	private ConsumerApplicationDetailService applicationDetailService;

	@Autowired
	private GeoLocationRepository geoLocationRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private UploadRepository uploadRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	private GeoLocationService GeoLocationService;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailsService;

	@Autowired
	ConsumerService consumerServie;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Autowired
	private ContractorDetailForBidController contractorDetailForBidController;

	@Override
	public GeoLocation savelocation(GeoLocation geoLocation)
			throws GeoLocationException, ConsumerApplicationDetailException, DocumentTypeException {

		final String method = "Geo location : savelocation()";

		logger.info(method);

		Response<GeoLocation> response = new Response<>();

		ConsumerApplicationDetail consumerApplicationDetail = applicationDetailService
				.findConsumerApplicationDetailByApplicationNo(geoLocation.getConsumerApplicationNo());

		if (!(consumerApplicationDetail.getApplicationStatus().getApplicationStatusId()
				.compareTo(ApplicationStatusEnum.PENDING_FOR_GIS_LOCATION.getId()) == 0)) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Invalid application status OR you have a already submit location !");
			throw new GeoLocationException(response);
		}

		if (Objects.isNull(geoLocation) || geoLocation.getConsumerApplicationNo() == null
				|| geoLocation.getConsumerApplicationNo().trim().equals("")) {
			logger.error("geoLocation  object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new GeoLocationException(response);
		}

		ConsumerApplicationDetail applicationDetail = applicationDetailService
				.findConsumerApplicationDetailByApplicationNo(geoLocation.getConsumerApplicationNo().trim());

		GeoLocation geoLocationDb = geoLocationRepository
				.findByConsumerApplicationNo(geoLocation.getConsumerApplicationNo());

		MultipartFile f = geoLocation.getFile();
		MultipartFile f1 = geoLocation.getFile1();

		Upload startDoc = null;
		Upload endDoc = null;

		if (f.getSize() <= 0 || f1.getSize() <= 0) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please upload image");
			throw new GeoLocationException(response);

		}

		if (!f.getContentType().equalsIgnoreCase("image/jpeg") || !f1.getContentType().equalsIgnoreCase("image/jpeg")) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please upload image");
			throw new GeoLocationException(response);

		}

		startDoc = uploadService.uploadFile(geoLocation.getFile(), "LATLONG_IMAGE");
		endDoc = uploadService.uploadFile(geoLocation.getFile1(), "LATLONG_IMAGE");

		ApplicationDocument findByConsumerApplicationDetailId = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationDetail.getConsumerApplicationId());
		if (findByConsumerApplicationDetailId != null) {

			findByConsumerApplicationDetailId.setConsumerApplicationDetail(consumerApplicationDetail);
			findByConsumerApplicationDetailId.setDocStartGeoFile(startDoc);
			findByConsumerApplicationDetailId.setDocEndGeoFile(endDoc);
			applicationDocumentRepository.save(findByConsumerApplicationDetailId);
		} else {
			ApplicationDocument applicationDocument = new ApplicationDocument();
			applicationDocument.setConsumerApplicationDetail(consumerApplicationDetail);
			applicationDocument.setDocStartGeoFile(startDoc);
			applicationDocument.setDocEndGeoFile(endDoc);
			applicationDocumentRepository.save(applicationDocument);
		}

		if (startDoc == null || endDoc == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Eror in uploading image");
			throw new GeoLocationException(response);
		}

//		= new ContractorDetailForBidController();
		if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)) {
			contractorDetailForBidController.postDataProd(geoLocation.getConsumerApplicationNo().trim());

		}

		geoLocation.setStartDoc(startDoc);
		geoLocation.setEndDoc(endDoc);
		ApplicationStatus appStatusDb = null;
		if (applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 8l
				|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 12l) {

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
		} else if (applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 5l) {
			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
		} else if ("Government".equals(applicationDetail.getAvedakKaPrakar())
				&& (applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 1l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 2l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 3l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 4l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 6l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 7l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 9l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 10l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 11l
						|| applicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId() == 12l)) { // 12-12-2025 added this for Government type
			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
		} else {
			if (applicationDetail.getSchemeType().getSchemeTypeId() == 1l
					|| applicationDetail.getSchemeType().getSchemeTypeId() == 2l) {
				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId());
			} else {

				appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
			}

		}

		consumerApplicationDetail.setApplicationStatus(appStatusDb);
		consumerApplicationDetailsService.saveConsumerApplication(consumerApplicationDetail);

		GeoLocation geoLocationResponse = geoLocationRepository.save(geoLocation);

		if (Objects.isNull(geoLocationResponse)) {
			logger.error("repository.save(consumerType) is returning Null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
			throw new GeoLocationException(response);
		} else {
			return geoLocationResponse;
		}
//		}

	}

	@Override
	public GeoLocation findbyCosumerApplicationNumber(String consumerApplicationNumber) throws GeoLocationException {
		Response<Demand> response = new Response<>();

		GeoLocation geoLocationDb = null;

		try {
			geoLocationDb = geoLocationRepository.findByConsumerApplicationNo(consumerApplicationNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (geoLocationDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error fetching  Demand Record !  ");
			throw new GeoLocationException(response);
		}

		return geoLocationDb;

	}

//	@Override
//	public void fetchPendingGeoLocationConsumerApplications(GeoPendingStausRequestForm geoPendingStausRequestForm)
//			throws GeoLocationException {
//		
//		final String method = "FeederServiceImpl : findAllFeedersBySubstation()";
//		logger.info(method);
//		Consumer consumer = consumerServie.findByMobileNo(geoPendingStausRequestForm.getConsumerMobileNumber());
//		
//		consumerApplicationDetailsService.findConsumerApplicationDetailByConsumerId(consumer.getConsumerId());
//		
//		
//		List<Feeder> feeders = repository.findBySubstation(substationId);
//		Response response = new Response<>();
//		if (Objects.isNull(feeders) || feeders.isEmpty()) {
//			logger.error("repository.findAll is returning Null when findAllFeedersBySubstation call");
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_BY_SUB_STATION_ID_FAILED_MESSAGE);
//			throw new FeederException(response);
//		} else {
//			logger.info("Response is returning successfully");
//			return feeders;
//		}
//	}
//		
//		
//	}

}
