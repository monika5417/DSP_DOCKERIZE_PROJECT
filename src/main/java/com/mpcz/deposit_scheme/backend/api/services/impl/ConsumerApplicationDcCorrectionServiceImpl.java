package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.text.MessageFormat;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDcCorrection;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.dto.JeRejectFileDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.enums.StatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDcCorrectionException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplicationDcCorrectionRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplicationSurveyRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UploadRepository;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDcCorrectionForm;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDcCorrectionService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationSurveyService;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;

@Service
public class ConsumerApplicationDcCorrectionServiceImpl implements ConsumerApplicationDcCorrectionService {

	@Autowired
	ConsumerApplicationDcCorrectionRepository consumerApplicationDcCorrectionRepository;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private DistributionCenterService distributionCenterService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private ConsumerApplicationSurveyService consumerApplicationSurveyService;

	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	private SMSDirectService smsDirectService;

	@Autowired
	ConsumerApplicationSurveyRepository consumerApplicationSurveyRepository;
	
	@Autowired
	private UploadRepository uploadRepository;
	
	@Override
	public List<ConsumerApplicationDcCorrection> findByConsumerApplicationId(Long ConsumerApplicationId)
			throws ConsumerApplicationDcCorrectionException {

		return consumerApplicationDcCorrectionRepository
				.findByConsumersApplicationDetailConsumerApplicationId(ConsumerApplicationId)
				.orElseThrow(() -> new ConsumerApplicationDcCorrectionException(
						new Response<String>(ResponseCode.DATA_NOT_FOUND, ResponseMessage.CONSUMER_DETAILS_NOT_FOUND)));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ConsumerApplicationDcCorrection save(ConsumerApplicationDcCorrection consumerApplicationDcCorrection)
			throws ConsumerApplicationDcCorrectionException {
		// TODO Auto-generated method stub
		Response<ConsumerApplicationDcCorrection> response = new Response<>();
		ConsumerApplicationDcCorrection consumerApplicationDcCorrectionDb = null;
		try {
			consumerApplicationDcCorrectionDb = consumerApplicationDcCorrectionRepository
					.save(consumerApplicationDcCorrection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (consumerApplicationDcCorrectionDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Error Saving dc Correction Data !");
			throw new ConsumerApplicationDcCorrectionException(response);

		}
		return consumerApplicationDcCorrectionDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ConsumerApplicationDcCorrection saveForm(
			ConsumerApplicationDcCorrectionForm consumerApplicationDcCorrectionForm)
			throws ConsumerApplicationDcCorrectionException, ConsumerApplicationDetailException,
			DistributionCenterException, ConsumerApplicationSurveyException {

		Response<ConsumerApplicationDcCorrection> response = new Response<>();
		ConsumerApplicationDcCorrection ConsumerApplicationDcCorrectionDb= null;
		
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findByConsumerApplicationId(consumerApplicationDcCorrectionForm.getConsumerApplicationId());

		if(consumerApplicationDcCorrectionForm.getRejectFile()!=null) {
			
			for (JeRejectFileDto rejectFile : consumerApplicationDcCorrectionForm.getRejectFile()) {
				Upload findByUploadId = uploadRepository.findByUploadId(rejectFile.getDocFileId());
				findByUploadId.setDocumentStatus(rejectFile.getDocStatus());
				uploadRepository.save(findByUploadId);
		    }
			consumerApplicationDetail.setApplicationStatus(applicationStatusService
					.findById(ApplicationStatusEnum.APPLICATION_REJECTED_BY_DC_USER_DUE_TO_UPLOADED_WRONG_DOCUMENT_BY_CONSUMER.getId()));	
			consumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);
			ConsumerApplicationDcCorrection ConsumerApplicationDcCorrection = new ConsumerApplicationDcCorrection();
			response.setCode(HttpCode.CREATED);
			response.setMessage("Application Revert Back to 36 Status");
			ConsumerApplicationDcCorrection.setDcChangedReason("Application Revert Back to 36 Status");
			 return ConsumerApplicationDcCorrection;
		}else {
		ConsumerApplicationDcCorrection consumerApplicationDcCorrection = new ConsumerApplicationDcCorrection();

		consumerApplicationDcCorrection.setDcChanged(consumerApplicationDcCorrectionForm.getDcChanged());

		DistributionCenter changedDc = null;
		if (consumerApplicationDcCorrectionForm.getDcChanged()) {

			Response<DistributionCenter> newDcResponse = distributionCenterService
					.findById(consumerApplicationDcCorrectionForm.getChangedDcId());

			changedDc = newDcResponse.getList().get(0);

			if (consumerApplicationDetail.getDistributionCenter().getDcId().compareTo(changedDc.getDcId()) == 0) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Dc Not Change !");
				throw new ConsumerApplicationDcCorrectionException(response);

			}

			consumerApplicationDcCorrection
					.setDcChangedReason(consumerApplicationDcCorrectionForm.getDcChangedReason());
			consumerApplicationDcCorrection.setOldDc(consumerApplicationDetail.getDistributionCenter());
			consumerApplicationDcCorrection.setChangedDc(changedDc);

		}

		ConsumerApplicationDetail consumerApplicationDetailDb = null;
		Boolean dcAccepted = Boolean.FALSE;
		if (changedDc != null) {
			consumerApplicationDetail.setDistributionCenter(changedDc);
			consumerApplicationDetailDb = consumerApplicationDetailService
					.saveConsumerApplication(consumerApplicationDetail);
			consumerApplicationDcCorrection.setConsumersApplicationDetail(consumerApplicationDetailDb);
		} else {

			dcAccepted = Boolean.TRUE;

			ApplicationStatus pendingForSurveyStatus = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_SURVEY_AT_DC.getId());
//			ApplicationStatus pendingForSurveyStatus = applicationStatusService
//					.findById(ApplicationStatusEnum.PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM.getId());
			
			consumerApplicationDetail.setApplicationStatus(pendingForSurveyStatus);

			consumerApplicationDetailDb = consumerApplicationDetailService
					.saveConsumerApplication(consumerApplicationDetail);

		}

		

		 ConsumerApplicationDcCorrectionDb = save(consumerApplicationDcCorrection);
		Optional<ConsumerApplicationSurvey> findByConsumersApplicationDetailConsumerApplicationId = consumerApplicationSurveyRepository.findByConsumersApplicationDetailConsumerApplicationId(consumerApplicationDcCorrectionForm.getConsumerApplicationId());
//		ConsumerApplicationSurvey applicationSurvey = findByConsumersApplicationDetailConsumerApplicationId.get();
		/********* insert survey details and send sms start ********/
		if (dcAccepted && !findByConsumersApplicationDetailConsumerApplicationId.isPresent()) {
			ConsumerApplicationSurvey consumerApplicationSurvey = new ConsumerApplicationSurvey();

			consumerApplicationSurvey.setConsumersApplicationDetail(consumerApplicationDetailDb);
			consumerApplicationSurvey.setSurveyStatus(StatusEnum.PENDING.getValue());
			
			ConsumerApplicationSurvey consumerApplicationSurveyDb = consumerApplicationSurveyService
					.saveConsumerApplicationSurvey(consumerApplicationSurvey);

			
			consumerApplicationDcCorrection.setDcChangedReason("dc change nai karna");
//			charitra code start here
//			send msg of consumer

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String strDate = formatter.format(date);

			if (consumerApplicationDcCorrectionForm.getScheduleSurveyTime() != null) {
				strDate = strDate.concat(" at " + consumerApplicationDcCorrectionForm.getScheduleSurveyTime().trim());
			}

			final SMSRequest smsRequest = new SMSRequest();
			smsRequest.setText(MessageFormat.format(messageProperties.getMessageConsumerAccepetByDC(),
					new Object[] { consumerApplicationDetail.getConsumerApplicationNo(),
							consumerApplicationDcCorrectionForm.getSurveyorName(),
							consumerApplicationDcCorrectionForm.getSurveyorMobile(), strDate }));
//				smsRequest.setMobileNo(consumerApplicationDcCorrectionForm.getSurveyorName().trim());
//			smsRequest.setMobileNo("");
			smsRequest.setTemplateId(messageProperties.getMessageConsumerAccepetByDcTemplatedId());

			try {
				smsDirectService.sendMessage(smsRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}

//				send msg of surveyor

			final SMSRequest smsRequest1 = new SMSRequest();
			smsRequest1.setText(MessageFormat.format(messageProperties.getMessageServeyouAccepetByDc(),
					new Object[] { consumerApplicationDetail.getConsumerApplicationNo(), strDate }));
//			smsRequest1.setMobileNo(consumerApplicationDcCorrectionForm.getSurveyorName().trim());
			smsRequest1.setMobileNo(consumerApplicationDcCorrectionForm.getSurveyorMobile());
			smsRequest1.setTemplateId(messageProperties.getMessageServeyorAccepetByDcTemplatedId());

			try {
				smsDirectService.sendMessage(smsRequest1);
			} catch (Exception e) {
				e.printStackTrace();
			}

//			charitra code end here

		}else if(!dcAccepted) {
			System.out.println("DC changed");
			response.setCode(HttpCode.OK);
			response.setMessage("DC changed");
			 ConsumerApplicationDcCorrectionDb.setDcChangedReason("DC changed");
			 return ConsumerApplicationDcCorrectionDb;
		}
		else {
			System.out.println("An existing survey entry already exists for this Consumer Application Detail.");
			response.setCode(HttpCode.NOT_ACCEPTABLE);
			response.setMessage("An existing survey entry already exists for this Consumer Application Detail.");
			 ConsumerApplicationDcCorrectionDb.setDcChangedReason("An existing survey entry already exists for this Consumer Application Detail");
			 return ConsumerApplicationDcCorrectionDb;
			
		}
		}
		/********* insert survey details and send sms ends ********/

		return ConsumerApplicationDcCorrectionDb;
	}
	

}
