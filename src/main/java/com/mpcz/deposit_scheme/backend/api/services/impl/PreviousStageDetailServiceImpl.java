package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.PreviousStageDetail;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.enums.StatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.PreviousStageDetailRepository;
import com.mpcz.deposit_scheme.backend.api.request.PreviousStageForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.PreviousStageDetailService;

@Service
public class PreviousStageDetailServiceImpl implements PreviousStageDetailService {

	@Autowired
	PreviousStageDetailRepository previousStageDetailRepository;

	@Autowired
	ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveBackToPreviousStage(PreviousStageForm previousStageForm)
			throws PreviousStageDetailException, ConsumerApplicationDetailException {

		Response<PreviousStageDetail> response = new Response<>();

		ApplicationStatus toStage = null;

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findByConsumerApplicationId(previousStageForm.getConsumersApplicationDetailId());

		String applicationStatusId = consumerApplicationDetail.getApplicationStatus().getApplicationStatusId()
				.toString();

		switch (applicationStatusId) {
		case "8":
			// PENDING_FOR_DEMAND_GENERATION_AT_DE
			toStage = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_SURVEY_AT_DC.getId());
			break;
		case "9":
		case "10":
		case "11":
//			PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_SE
//			PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_CE
//			toStage = applicationStatusService
//					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_GENERATION_AT_DE.getId());
			
			toStage = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId());
			break;
		}

		if (toStage == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" application stage not set for this appliaction status !  ");
			throw new PreviousStageDetailException(response);
		}

		PreviousStageDetail previousStageDetail = new PreviousStageDetail();

		previousStageDetail.setConsumersApplicationDetail(consumerApplicationDetail);

		previousStageDetail.setFromStage(consumerApplicationDetail.getApplicationStatus());
		previousStageDetail.setToStage(toStage);
		previousStageDetail.setRemark(previousStageForm.getRemark());

		save(previousStageDetail);

		consumerApplicationDetail.setApplicationStatus(toStage);
		consumerApplicationDetail.setPreviousStage(Boolean.TRUE);
		consumerApplicationDetail.setPreviousStageRemark(previousStageForm.getRemark());

		consumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PreviousStageDetail save(PreviousStageDetail previousStageDetail) throws PreviousStageDetailException {

		Response<PreviousStageDetail> response = new Response<>();
		PreviousStageDetail previousStageDetailDb = null;
		try {
			previousStageDetailDb = previousStageDetailRepository.save(previousStageDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (previousStageDetailDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error fetching  Survey Record !  ");
			throw new PreviousStageDetailException(response);
		}
		return previousStageDetailDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updatePreviousStageDataByConsumerApplicationDetailId(Long consumerApplicationDetailId)
			throws ConsumerApplicationDetailException, PreviousStageDetailException {
		PreviousStageDetail previousStageDetail = null;
		try {
	        previousStageDetail = findPreviousStageDetailByConsumerApplicationDetailId(
					consumerApplicationDetailId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(previousStageDetail == null) {
			return;
		}

		LocalDate backwordDate = previousStageDetail.getBackwardDate().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate forwardDate = LocalDate.now();

		Long daysBetween = ChronoUnit.DAYS.between(backwordDate, forwardDate);

		// update ...

		previousStageDetail.setDays(new Integer(daysBetween.toString()));
		previousStageDetail.setForwardDate(new Date());
		previousStageDetail.setStatus(StatusEnum.COMPLETED.getValue());

		save(previousStageDetail);

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findByConsumerApplicationId(consumerApplicationDetailId);

		consumerApplicationDetailService.cleanPreviousStageDataInConsumerApplicationDetail(consumerApplicationDetail);

	}

	@Override
	public PreviousStageDetail findPreviousStageDetailByConsumerApplicationDetailId(Long consumerApplicationDetailId)
			throws PreviousStageDetailException {

		Response<PreviousStageDetail> response = new Response<>();

		PreviousStageDetail previousStageDetailDb = null;
		try {
			Optional<PreviousStageDetail> previousStageDetailOptional = previousStageDetailRepository
					.findByConsumersApplicationDetailConsumerApplicationIdAndForwardDateIsNull(
							consumerApplicationDetailId);

			if (previousStageDetailOptional.isPresent()) {
				previousStageDetailDb = previousStageDetailOptional.get();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (previousStageDetailDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error fetching  Survey Record !  ");
			throw new PreviousStageDetailException(response);
		}
		return previousStageDetailDb;

	}

	public Date convertLocalDateToDate(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

}
