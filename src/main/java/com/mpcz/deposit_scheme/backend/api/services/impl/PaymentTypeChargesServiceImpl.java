package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.PaymentTypeCharges;
import com.mpcz.deposit_scheme.backend.api.repository.PaymentTypeChargesRepository;
import com.mpcz.deposit_scheme.backend.api.services.PaymentTypeChargesService;

@Service
public class PaymentTypeChargesServiceImpl implements PaymentTypeChargesService {

	@Autowired
	PaymentTypeChargesRepository paymentTypeChargesRepository;

	@Override
	public List<PaymentTypeCharges> findByPaymentTypeId(Long paymentTypeId) {
		List<PaymentTypeCharges> paymentTypeCharges = null;

		try {
			paymentTypeCharges = paymentTypeChargesRepository.findByPaymentTypePaymentTypeId(paymentTypeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentTypeCharges;
	}

}
