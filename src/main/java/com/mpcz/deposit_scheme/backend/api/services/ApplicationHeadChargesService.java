package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationHeadCharges;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;

public interface ApplicationHeadChargesService {

	void prepareRegistrationFeeCharges(Long ConsumerApplicationId)
			throws PaymentTypeException, ConsumerApplicationDetailException, ApplicationHeadChargesException;

	void saveAll(List<ApplicationHeadCharges> applicationHeadChargesList) throws ApplicationHeadChargesException;

	public ApplicationHeadCharges findByConsumerApplicationIdChargesTypeIdPaymentTypeId(Long consumerApplicationId,
			Long chargesTypeId, Long paymentTypeId) throws ApplicationHeadChargesException;

	List<ApplicationHeadCharges> findByApplicationIdPaymentTypeId(Long consumerApplicationId, Long paymentTypeId)
			throws ApplicationHeadChargesException;

}
