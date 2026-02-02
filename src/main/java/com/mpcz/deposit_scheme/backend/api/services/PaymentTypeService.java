package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.PaymentType;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;

public interface PaymentTypeService {

	public PaymentType findById(Long payId) throws PaymentTypeException;

}
