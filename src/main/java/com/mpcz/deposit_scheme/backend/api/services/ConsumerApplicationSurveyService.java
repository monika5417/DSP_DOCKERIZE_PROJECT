package com.mpcz.deposit_scheme.backend.api.services;

import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailSurveyForm;

public interface ConsumerApplicationSurveyService {

	ConsumerApplicationSurvey saveConsumerApplicationSurvey(
			ConsumerApplicationDetailSurveyForm consumerApplicationDetailForm, MultipartFile docSurvey)
			throws ConsumerApplicationSurveyException, DocumentTypeException, ConsumerApplicationDetailException;

	ConsumerApplicationSurvey updateConsumerApplicationSurvey(Long id,
			ConsumerApplicationDetailSurveyForm consumerApplicationDetailForm, MultipartFile docSurvey)
			throws ConsumerApplicationSurveyException, DocumentTypeException, ConsumerApplicationDetailException,
			PreviousStageDetailException;

	ConsumerApplicationSurvey saveConsumerApplicationSurvey(ConsumerApplicationSurvey ConsumerApplicationSurvey)
			throws ConsumerApplicationSurveyException;

	ConsumerApplicationSurvey findByConsumersApplicationDetailConsumerApplicationId(Long consumerApplicationId)
			throws ConsumerApplicationSurveyException;

	ConsumerApplicationSurvey findByConsumerApplicationSurveyById(Long consumerApplicationSurveyId)
			throws ConsumerApplicationSurveyException;

}
