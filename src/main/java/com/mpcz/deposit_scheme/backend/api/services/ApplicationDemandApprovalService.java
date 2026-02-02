package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDemandApproval;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDemandApprovalException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.request.ApplicationDemandApprovalForm;

public interface ApplicationDemandApprovalService {

	List<ApplicationDemandApproval> findByConsumerApplicationId(Long consumerApplicationId)
			throws ApplicationDemandApprovalException;

	ApplicationDemandApproval save(ApplicationDemandApproval applicationDemandApproval)
			throws ApplicationDemandApprovalException;

	ApplicationDemandApproval saveForm(ApplicationDemandApprovalForm applicationDemandApprovalForm)
			throws ApplicationDemandApprovalException, ConsumerApplicationDetailException, PreviousStageDetailException;

}
