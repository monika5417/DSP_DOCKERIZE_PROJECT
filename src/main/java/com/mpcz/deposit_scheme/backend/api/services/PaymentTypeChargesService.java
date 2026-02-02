package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.PaymentTypeCharges;

public interface PaymentTypeChargesService {

	List<PaymentTypeCharges> findByPaymentTypeId(Long paymentTypeId);

}
