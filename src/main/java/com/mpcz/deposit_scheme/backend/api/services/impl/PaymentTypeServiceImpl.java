/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentType;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.repository.PaymentTypeRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.PaymentTypeService;

/**
 *
 * @author
 */
@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {

	@Autowired
	private PaymentTypeRepository paymentTyeRepository;

	@Override
	public PaymentType findById(Long payId) throws PaymentTypeException {
		final Response<PaymentType> response = new Response<>();

		Optional<PaymentType> paymentTypeOptional = Optional.empty();
		try {

			paymentTypeOptional = paymentTyeRepository.findById(payId);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (!paymentTypeOptional.isPresent()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Data Not Found !");
			throw new PaymentTypeException(response);
		}
		return paymentTypeOptional.get();

	}

}
