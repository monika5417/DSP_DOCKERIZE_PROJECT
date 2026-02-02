package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;
import java.util.Optional;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDcCorrection;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDcCorrectionException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDcCorrectionForm;

public interface ConsumerApplicationDcCorrectionService {

	List<ConsumerApplicationDcCorrection> findByConsumerApplicationId(Long consumerApplicationId)
			throws ConsumerApplicationDcCorrectionException;

	ConsumerApplicationDcCorrection save(ConsumerApplicationDcCorrection consumerApplicationDcCorrection)
			throws ConsumerApplicationDcCorrectionException;

	ConsumerApplicationDcCorrection saveForm(ConsumerApplicationDcCorrectionForm consumerApplicationDcCorrectionForm)
			throws ConsumerApplicationDcCorrectionException, ConsumerApplicationDetailException,
			DistributionCenterException, ConsumerApplicationSurveyException;

}
