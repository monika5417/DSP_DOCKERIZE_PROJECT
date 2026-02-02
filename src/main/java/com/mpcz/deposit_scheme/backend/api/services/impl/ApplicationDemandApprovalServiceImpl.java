package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDemandApproval;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.enums.StatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDemandApprovalException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDemandApprovalRepository;
import com.mpcz.deposit_scheme.backend.api.request.ApplicationDemandApprovalForm;
import com.mpcz.deposit_scheme.backend.api.request.PreviousStageForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationDemandApprovalService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.PreviousStageDetailService;

@Service
public class ApplicationDemandApprovalServiceImpl implements ApplicationDemandApprovalService {

	@Autowired
	ApplicationDemandApprovalRepository applicationDemandApprovalRepository;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private PreviousStageDetailService previousStageDetailService;

	@Override
	public List<ApplicationDemandApproval> findByConsumerApplicationId(Long consumerApplicationId)
			throws ApplicationDemandApprovalException {
		return applicationDemandApprovalRepository
				.findByConsumersApplicationDetailConsumerApplicationId(consumerApplicationId)
				.orElseThrow(() -> new ApplicationDemandApprovalException(
						new Response<String>(ResponseCode.DATA_NOT_FOUND, ResponseMessage.CONSUMER_DETAILS_NOT_FOUND)));

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ApplicationDemandApproval save(ApplicationDemandApproval applicationDemandApproval)
			throws ApplicationDemandApprovalException {
		// TODO Auto-generated method stub
		Response<ApplicationDemandApproval> response = new Response<>();
		ApplicationDemandApproval applicationDemandApprovalDb = null;
		try {
			applicationDemandApprovalDb = applicationDemandApprovalRepository.save(applicationDemandApproval);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (applicationDemandApprovalDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Error Saving demand approval Data !");
			throw new ApplicationDemandApprovalException(response);

		}
		return applicationDemandApprovalDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ApplicationDemandApproval saveForm(ApplicationDemandApprovalForm applicationDemandApprovalForm)
			throws ApplicationDemandApprovalException, ConsumerApplicationDetailException,
			PreviousStageDetailException {
		Response<ApplicationDemandApproval> response = new Response<>();
		
		previousStageDetailService.updatePreviousStageDataByConsumerApplicationDetailId(applicationDemandApprovalForm.getConsumerApplicationId());

		ConsumerApplicationDetail consumerApplicationDetailDb = null;
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findByConsumerApplicationId(applicationDemandApprovalForm.getConsumerApplicationId());
		ApplicationDemandApproval applicationDemandApprovalDb = null;
		if (applicationDemandApprovalForm.getIsRejected()) {
			// demand rejected

			// set application to demand generation stage
			PreviousStageForm previousStageForm = new PreviousStageForm();
			previousStageForm.setConsumersApplicationDetailId(applicationDemandApprovalForm.getConsumerApplicationId());
			previousStageForm.setRemark(applicationDemandApprovalForm.getRejectedRemark());
			previousStageDetailService.saveBackToPreviousStage(previousStageForm);

			consumerApplicationDetailDb = consumerApplicationDetailService
					.findByConsumerApplicationId(applicationDemandApprovalForm.getConsumerApplicationId());

			ApplicationDemandApproval applicationDemandApproval = new ApplicationDemandApproval();
			applicationDemandApproval.setStatus(StatusEnum.REJECTED.getValue());
			applicationDemandApproval.setRejectedReason(applicationDemandApprovalForm.getRejectedRemark());

			applicationDemandApproval.setConsumersApplicationDetail(consumerApplicationDetailDb);
			applicationDemandApprovalDb = save(applicationDemandApproval);

		} else {
			// demand approved

			// set status pending for 12

			ApplicationStatus pendingForDemandPayment = applicationStatusService
					.findById(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());

			consumerApplicationDetail.setApplicationStatus(pendingForDemandPayment);

			consumerApplicationDetailDb = consumerApplicationDetailService
					.saveConsumerApplication(consumerApplicationDetail);

			ApplicationDemandApproval applicationDemandApproval = new ApplicationDemandApproval();
			applicationDemandApproval.setStatus(StatusEnum.APPROVED.getValue());
			applicationDemandApproval.setConsumersApplicationDetail(consumerApplicationDetailDb);
			applicationDemandApprovalDb = save(applicationDemandApproval);

		}

		return applicationDemandApprovalDb;
	}

}
