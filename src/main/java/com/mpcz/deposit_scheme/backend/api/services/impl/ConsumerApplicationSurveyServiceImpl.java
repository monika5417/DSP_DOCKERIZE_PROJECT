package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.enums.StatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplicationSurveyRepository;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailSurveyForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationSurveyService;
import com.mpcz.deposit_scheme.backend.api.services.PreviousStageDetailService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsumerApplicationSurveyServiceImpl implements ConsumerApplicationSurveyService {

	Logger logger = LoggerFactory.getLogger(ConsumerApplicationSurveyServiceImpl.class);

	@Autowired
	ConsumerApplicationSurveyRepository consumerApplicationSurveyRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	ConsumerApplicationDetailService ConsumerApplicationDetailService;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	PreviousStageDetailService previousStageDetailService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ConsumerApplicationSurvey saveConsumerApplicationSurvey(
			ConsumerApplicationDetailSurveyForm consumerApplicationDetailSurveyForm, MultipartFile docSurvey)
			throws ConsumerApplicationSurveyException, DocumentTypeException, ConsumerApplicationDetailException {

		Response<ConsumerApplicationSurvey> response = new Response<>();

		if (Objects.isNull(consumerApplicationDetailSurveyForm)) {
			logger.error("Consumer Application Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ConsumerApplicationSurveyException(response);
		}

		Upload surveyUpload = null;

		if (consumerApplicationDetailSurveyForm.getConsumerApplicationId() == null
				|| consumerApplicationDetailSurveyForm.getConsumerApplicationId().compareTo(0l) <= 0) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application Id not found  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (consumerApplicationDetailSurveyForm.getIsRejected() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please select approved  or reject !  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (consumerApplicationDetailSurveyForm.getIsRejected()
				&& (consumerApplicationDetailSurveyForm.getRejectedRemark() == null
						|| consumerApplicationDetailSurveyForm.getRejectedRemark().trim().equals(""))) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please add reject Reason  !  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (consumerApplicationDetailSurveyForm.getSurveyDate() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please select survey date !  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (docSurvey == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please upload survey document ");
			throw new ConsumerApplicationSurveyException(response);

		}

//		ConsumerApplicationSurvey consumerApplicationSurvey = null;

//		try {
		ConsumerApplicationSurvey	consumerApplicationSurvey = findByConsumersApplicationDetailConsumerApplicationId(
					consumerApplicationDetailSurveyForm.getConsumerApplicationId());
//		} catch (ConsumerApplicationSurveyException e) {
//			 
//			e.printStackTrace();
//		}
//
//		if (consumerApplicationSurvey != null) {
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(" Application survey already submitted ! ");
//			throw new ConsumerApplicationSurveyException(response);
//		}

 
		surveyUpload = uploadService.uploadFile(docSurvey, "SURVEY");

		if (surveyUpload == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error Uploding Survey Document !  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		ApplicationStatus appStatusDb = applicationStatusService.findById(8L);

		ConsumerApplicationDetail consumerApplicationDetail = ConsumerApplicationDetailService
				.findByConsumerApplicationId(consumerApplicationDetailSurveyForm.getConsumerApplicationId());

//		consumerApplicationSurvey = new ConsumerApplicationSurvey();

		consumerApplicationSurvey.setConsumersApplicationDetail(consumerApplicationDetail);

		if (consumerApplicationDetailSurveyForm.getIsRejected()) {
			consumerApplicationSurvey.setSurveyStatus(StatusEnum.REJECTED.getValue());
			consumerApplicationSurvey.setRejectedReason(consumerApplicationDetailSurveyForm.getRejectedRemark().trim());
		} else {
			consumerApplicationSurvey.setSurveyStatus(StatusEnum.APPROVED.getValue());
		}

		consumerApplicationSurvey.setSurveyDate(consumerApplicationDetailSurveyForm.getSurveyDate());

		consumerApplicationSurvey.setDocSurvey(surveyUpload);

		ConsumerApplicationSurvey consumerApplicationSurveyDb = saveConsumerApplicationSurvey(
				consumerApplicationSurvey);

		if (consumerApplicationDetailSurveyForm.getIsRejected()) {

			consumerApplicationDetail.setRejectedRemark(consumerApplicationDetailSurveyForm.getRejectedRemark().trim());
			consumerApplicationDetail.setIsRejected(consumerApplicationDetailSurveyForm.getIsRejected());

		} else {
			consumerApplicationDetail.setApplicationStatus(appStatusDb);
		}

		ConsumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);

		return consumerApplicationSurveyDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ConsumerApplicationSurvey saveConsumerApplicationSurvey(ConsumerApplicationSurvey ConsumerApplicationSurvey)
			throws ConsumerApplicationSurveyException {
		
		
		ConsumerApplicationSurvey consumerApplicationSurveyDb = consumerApplicationSurveyRepository
				.save(ConsumerApplicationSurvey);

		Response<ConsumerApplicationSurvey> response = new Response<>();

		if (consumerApplicationSurveyDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error saving Survey !  ");
			throw new ConsumerApplicationSurveyException(response);
		}

		return consumerApplicationSurveyDb;

	}

	@Override
	public ConsumerApplicationSurvey findByConsumersApplicationDetailConsumerApplicationId(Long consumerApplicationId)
			throws ConsumerApplicationSurveyException {

		Response<ConsumerApplicationSurvey> response = new Response<>();

		ConsumerApplicationSurvey consumerApplicationSurveyDb = null;
		Optional<ConsumerApplicationSurvey> surveyOptional = null;
		try {
			surveyOptional = consumerApplicationSurveyRepository
					.findByConsumersApplicationDetailConsumerApplicationId(consumerApplicationId);
			if (surveyOptional.isPresent()) {
				consumerApplicationSurveyDb = surveyOptional.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (consumerApplicationSurveyDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error fetching  Survey Record !  ");
			throw new ConsumerApplicationSurveyException(response);
		}

		return consumerApplicationSurveyDb;

	}

	@Override
	public ConsumerApplicationSurvey updateConsumerApplicationSurvey(Long consumerApplicationSurveyId,
			ConsumerApplicationDetailSurveyForm consumerApplicationDetailSurveyForm, MultipartFile docSurvey)
			throws ConsumerApplicationSurveyException, DocumentTypeException, ConsumerApplicationDetailException,
			PreviousStageDetailException {

		Response<ConsumerApplicationSurvey> response = new Response<>();

		ConsumerApplicationSurvey consumerApplicationSurvey = findByConsumerApplicationSurveyById(
				consumerApplicationSurveyId);

		if (Objects.isNull(consumerApplicationDetailSurveyForm)) {
			logger.error("Consumer Application Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ConsumerApplicationSurveyException(response);
		}

		Upload surveyUpload = null;

		if (consumerApplicationDetailSurveyForm.getConsumerApplicationId() == null
				|| consumerApplicationDetailSurveyForm.getConsumerApplicationId().compareTo(0l) <= 0) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application Id not found  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (consumerApplicationDetailSurveyForm.getIsRejected() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please select approved  or reject !  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (consumerApplicationDetailSurveyForm.getIsRejected()
				&& (consumerApplicationDetailSurveyForm.getRejectedRemark() == null
						|| consumerApplicationDetailSurveyForm.getRejectedRemark().trim().equals(""))) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please add reject Reason  !  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (consumerApplicationDetailSurveyForm.getSurveyDate() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Please select survey date !  ");
			throw new ConsumerApplicationSurveyException(response);

		}

		if (docSurvey != null) {

			surveyUpload = uploadService.uploadFile(docSurvey, "SURVEY");

			if (surveyUpload == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(" Error Uploding Survey Document !  ");
				throw new ConsumerApplicationSurveyException(response);

			}

		}

		ApplicationStatus appStatusDb = applicationStatusService.findById(8L);

		ConsumerApplicationDetail consumerApplicationDetail = ConsumerApplicationDetailService
				.findByConsumerApplicationId(consumerApplicationDetailSurveyForm.getConsumerApplicationId());

		consumerApplicationSurvey.setConsumersApplicationDetail(consumerApplicationDetail);

		if (consumerApplicationDetailSurveyForm.getIsRejected()) {
			consumerApplicationSurvey.setSurveyStatus(StatusEnum.REJECTED.getValue());
			consumerApplicationSurvey.setRejectedReason(consumerApplicationDetailSurveyForm.getRejectedRemark().trim());
		} else {
			consumerApplicationSurvey.setSurveyStatus(StatusEnum.APPROVED.getValue());
		}

		consumerApplicationSurvey.setSurveyDate(consumerApplicationDetailSurveyForm.getSurveyDate());

		if (surveyUpload != null) {
			consumerApplicationSurvey.setDocSurvey(surveyUpload);
		}

		ConsumerApplicationSurvey consumerApplicationSurveyDb = saveConsumerApplicationSurvey(
				consumerApplicationSurvey);

		if (consumerApplicationDetailSurveyForm.getIsRejected()) {

			consumerApplicationDetail.setRejectedRemark(consumerApplicationDetailSurveyForm.getRejectedRemark().trim());
			consumerApplicationDetail.setIsRejected(consumerApplicationDetailSurveyForm.getIsRejected());

		} else {
			consumerApplicationDetail.setApplicationStatus(appStatusDb);
		}

		ConsumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);

		previousStageDetailService.updatePreviousStageDataByConsumerApplicationDetailId(
				consumerApplicationDetail.getConsumerApplicationId());

 
		return consumerApplicationSurveyDb;

	}

	@Override
	public ConsumerApplicationSurvey findByConsumerApplicationSurveyById(Long consumerApplicationSurveyId)
			throws ConsumerApplicationSurveyException {
		Response<ConsumerApplicationSurvey> response = new Response<>();

		ConsumerApplicationSurvey consumerApplicationSurveyDb = null;
		Optional<ConsumerApplicationSurvey> surveyOptional = null;
		try {
			surveyOptional = consumerApplicationSurveyRepository.findById(consumerApplicationSurveyId);
			if (surveyOptional.isPresent()) {
				consumerApplicationSurveyDb = surveyOptional.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (consumerApplicationSurveyDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error fetching  Survey Record !  ");
			throw new ConsumerApplicationSurveyException(response);
		}

		return consumerApplicationSurveyDb;
	}

}
