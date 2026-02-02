package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.enums.StatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.DemandRepository;
import com.mpcz.deposit_scheme.backend.api.request.DemandTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.DemandService;
import com.mpcz.deposit_scheme.backend.api.services.PreviousStageDetailService;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;

@Service
public class DemandServiceImpl implements DemandService {

	Logger logger = LoggerFactory.getLogger(DemandServiceImpl.class);

	@Autowired
	private DemandRepository demandRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	ConsumerApplicationDetailService ConsumerApplicationDetailService;

	@Autowired
	PreviousStageDetailService previousStageDetailService;
	
	@Autowired
	SchemeTypeService schemeTypeService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Demand saveDemandDetail(DemandTypeForm demandTypeForm, MultipartFile docDemand)
			throws DemandDetailException, DocumentTypeException, ConsumerApplicationDetailException {

		Response<Demand> response = new Response<>();

		if (Objects.isNull(demandTypeForm)) {
			logger.error("Demand Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DemandDetailException(response);
		}

		Upload demandUpload = null;

		if (demandTypeForm.getConsumerApplicationId() == null
				|| demandTypeForm.getConsumerApplicationId().compareTo(0l) <= 0) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application Id not found  ");
			throw new DemandDetailException(response);

		}

		if (demandTypeForm.getDemandGenerationDate() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please select demand generation date !  ");
			throw new DemandDetailException(response);

		}

		if (docDemand == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please upload  demand doc ");
			throw new DemandDetailException(response);

		}
		Demand demand = null;

		try {
			demand = findByConsumersApplicationDetailConsumerApplicationId(demandTypeForm.getConsumerApplicationId());
		} catch (DemandDetailException e) {
			e.printStackTrace();
		}

		if (demand != null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application demand already submitted !  ");
			throw new DemandDetailException(response);
		}

		demandUpload = uploadService.uploadFile(docDemand, "DEMAND");

		if (demandUpload == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error Uploding Demand Document !  ");
			throw new DemandDetailException(response);

		}

		if (demandTypeForm.getDemandAmount() == null
				|| demandTypeForm.getDemandAmount().compareTo(new BigDecimal(0)) == 0) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Demand amount cannot be zero !  ");
			throw new DemandDetailException(response);
		}

		ApplicationStatus appStatusDb = null;

		if (demandTypeForm.getDemandAmount().compareTo(new BigDecimal("3000000")) == 0
				|| demandTypeForm.getDemandAmount().compareTo(new BigDecimal("3000000")) < 0) {
			// Pending for demand note approval at DGM

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId());

		} else if (demandTypeForm.getDemandAmount().compareTo(new BigDecimal("10000000")) == 0
				|| demandTypeForm.getDemandAmount().compareTo(new BigDecimal("10000000")) < 0) {
			// Pending for demand note approval at GM

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_GM.getId());

		} else {
			// Pending for demand note approval at CGM

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_CGM.getId());

		}

		ConsumerApplicationDetail consumerApplicationDetail = ConsumerApplicationDetailService
				.findByConsumerApplicationId(demandTypeForm.getConsumerApplicationId());

		demand = new Demand();

		demand.setConsumersApplicationDetail(consumerApplicationDetail);
		demand.setDemandGenerationDate(demandTypeForm.getDemandGenerationDate());
//		sandeep, start
		demand.setSupervisionChargesOnCostOfMaterial(demandTypeForm.getSupervisionChargesOnCostOfMaterial());
		demand.setCgst(demandTypeForm.getCgst());
		demand.setSgst(demandTypeForm.getSgst());
		demand.setKrishiCess(demandTypeForm.getKrishiCess());
		demand.setEducationCess(demandTypeForm.getEducationCess());
		demand.setEecondaryHigherEduCess(demandTypeForm.getEecondaryHigherEduCess());
		demand.setSystemDevelopmentCharges(demandTypeForm.getSystemDevelopmentCharges());
		demand.setCostOfEstimate(demandTypeForm.getCostOfEstimate());
		demand.setOtherInfraStrucRelatedCost(demandTypeForm.getOtherInfraStrucRelatedCost());
		demand.setSupplyAffordingCharges(demandTypeForm.getSupplyAffordingCharges());
//		sandeep, end		
		demand.setDemandRs(demandTypeForm.getDemandAmount());
		demand.setDocDemand(demandUpload);
		demand.setDemandStatus(StatusEnum.PENDING.getValue());

		Demand demandDb = saveDemandDetail(demand);

		consumerApplicationDetail.setApplicationStatus(appStatusDb);
		ConsumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);

		return demandDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Demand saveDemandDetail(Demand demandDoc) throws DemandDetailException {
		Demand demandDb = demandRepository.save(demandDoc);

		Response<Demand> response = new Response<>();

		if (demandDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error saving Demand !  ");
			throw new DemandDetailException(response);
		}

		return demandDb;

	}

	@Override
	public Demand findByConsumersApplicationDetailConsumerApplicationId(Long consumerApplicationId)
			throws DemandDetailException {
		Response<Demand> response = new Response<>();

		Demand demandDb = null;
		Optional<Demand> demandOptional = null;
		try {
			demandOptional = demandRepository
					.findByConsumersApplicationDetailConsumerApplicationId(consumerApplicationId);
			if (demandOptional.isPresent()) {
				demandDb = demandOptional.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (demandDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error fetching  Demand Record !  ");
			throw new DemandDetailException(response);
		}

		return demandDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Demand updateDemandDetail(Long id, DemandTypeForm demandTypeForm, MultipartFile docDemand)
			throws DemandDetailException, DocumentTypeException, ConsumerApplicationDetailException,
			PreviousStageDetailException {
		Response<Demand> response = new Response<>();

		Demand demand = findByConsumersApplicationDetailConsumerApplicationId(
				demandTypeForm.getConsumerApplicationId());

		if (Objects.isNull(demandTypeForm)) {
			logger.error("Demand Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DemandDetailException(response);
		}

		Upload demandUpload = null;

		if (demandTypeForm.getConsumerApplicationId() == null
				|| demandTypeForm.getConsumerApplicationId().compareTo(0l) <= 0) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application Id not found  ");
			throw new DemandDetailException(response);

		}

		if (demandTypeForm.getDemandGenerationDate() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please select demand generation date !  ");
			throw new DemandDetailException(response);

		}

//		if (docDemand == null) {
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage("Please upload  demand doc ");
//			throw new DemandDetailException(response);
//
//		}
//		

		if (docDemand != null) {
			demandUpload = uploadService.uploadFile(docDemand, "DEMAND");

			if (demandUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(" Error Uploding Demand Document !  ");
				throw new DemandDetailException(response);

			}
		}

		if (demandTypeForm.getDemandAmount() == null
				|| demandTypeForm.getDemandAmount().compareTo(new BigDecimal(0)) == 0) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Demand amount cannot be zero !  ");
			throw new DemandDetailException(response);
		}

		ApplicationStatus appStatusDb = null;

		if (demandTypeForm.getDemandAmount().compareTo(new BigDecimal("3000000")) == 0
				|| demandTypeForm.getDemandAmount().compareTo(new BigDecimal("3000000")) < 0) {
			// Pending for demand note approval at DGM

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId());

		} else if (demandTypeForm.getDemandAmount().compareTo(new BigDecimal("10000000")) == 0
				|| demandTypeForm.getDemandAmount().compareTo(new BigDecimal("10000000")) < 0) {
			// Pending for demand note approval at GM

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_GM.getId());

		} else {
			// Pending for demand note approval at CGM

			appStatusDb = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_CGM.getId());

		}

		ConsumerApplicationDetail consumerApplicationDetail = ConsumerApplicationDetailService
				.findByConsumerApplicationId(demandTypeForm.getConsumerApplicationId());

		demand.setConsumersApplicationDetail(consumerApplicationDetail);
		demand.setDemandGenerationDate(demandTypeForm.getDemandGenerationDate());
		demand.setDemandRs(demandTypeForm.getDemandAmount());
		if (demandUpload != null) {
			demand.setDocDemand(demandUpload);
		}

		demand.setDemandStatus(StatusEnum.PENDING.getValue());

		Demand demandDb = saveDemandDetail(demand);

		consumerApplicationDetail.setApplicationStatus(appStatusDb);
		ConsumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);

		previousStageDetailService.updatePreviousStageDataByConsumerApplicationDetailId(
				consumerApplicationDetail.getConsumerApplicationId());

		return demandDb;
	}

	@Override
	public Demand findByConsumerApplicationId(Long consumerApplicationId) {

		return demandRepository.findByConsumersApplicationDetailConsumerApplicationId(consumerApplicationId)
				.orElseThrow(() -> new DataNotFoundException(
						new Response<String>(ResponseCode.DATA_NOT_FOUND, ResponseMessage.CONSUMER_DETAILS_NOT_FOUND)));

	}
	
	@Override
	public List<Demand> findAll()throws DemandDetailException {
		final String method = "DivisionServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		 List<Demand> demandDetail = demandRepository.findAll();
		if (Objects.isNull(demandDetail) || demandDetail.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DIVISION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DemandDetailException(response);
		} else {
			response.setList(demandDetail);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return demandDetail;
		}
	}

	@Override
	public List<Demand> findConsumerApplicationEstimate() throws DemandDetailException, SchemeTypeException {
		// TODO Auto-generated method stub
		final String method = "DemandServiceImpl : findConsumerApplicationEstimate()";
		logger.info(method);
		Response response = new Response<>();
		
		List<Demand> consumerSupervisionDemand= new ArrayList<>();
		
		
		 List<Demand> demandDetail = demandRepository.findAll();
		 System.out.println("*********************************************"+demandDetail);
		 for(Demand demand:demandDetail) {
			 
	if(demand!=null && demand.getConsumersApplicationDetail()!=null && demand.getConsumersApplicationDetail().getSchemeType()!=null && demand.getConsumersApplicationDetail().getSchemeType().getSchemeTypeName()!=null && demand.getConsumersApplicationDetail().getSchemeType().getSchemeTypeName().equals("Supervision"))
					 consumerSupervisionDemand.add(demand);

			 }
		 
		 
		if (Objects.isNull(consumerSupervisionDemand) || consumerSupervisionDemand.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DEMAND_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DemandDetailException(response);
		} 
			response.setList(consumerSupervisionDemand);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return consumerSupervisionDemand;
	}

}