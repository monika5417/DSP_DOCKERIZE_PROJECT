package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentRequest;

public interface PayUPaymentRequestService {

	PayUPaymentRequest save(PayUPaymentRequest payRequest);

	PayUPaymentRequest findByTxnIdAndCustomerId(String txnid, String udf4);

}
