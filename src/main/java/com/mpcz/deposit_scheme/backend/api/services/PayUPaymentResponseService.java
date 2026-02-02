package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface PayUPaymentResponseService {

	Response<PayUPaymentResponse> saveResponse(PayUPaymentResponse payres)throws ConsumerApplicationDetailException;
	
	public PayUPaymentResponse save(PayUPaymentResponse t);

}
