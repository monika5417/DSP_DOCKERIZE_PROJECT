package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.PreviousStageDetail;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.request.PreviousStageForm;

public interface PreviousStageDetailService {

	PreviousStageDetail save(PreviousStageDetail previousStageDetail) throws PreviousStageDetailException;

	void saveBackToPreviousStage(PreviousStageForm previousStageForm)
			throws PreviousStageDetailException, ConsumerApplicationDetailException;

	void updatePreviousStageDataByConsumerApplicationDetailId(Long ConsumerApplicationDetail)
			throws ConsumerApplicationDetailException, PreviousStageDetailException;

	PreviousStageDetail findPreviousStageDetailByConsumerApplicationDetailId(Long consumerApplicationDetailId)
			throws PreviousStageDetailException;

}
