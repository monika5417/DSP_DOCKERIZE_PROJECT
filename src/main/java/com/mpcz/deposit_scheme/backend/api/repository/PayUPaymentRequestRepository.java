package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentRequest;

public interface PayUPaymentRequestRepository extends JpaRepository<PayUPaymentRequest, Long> {
	

	PayUPaymentRequest findByTxnIdAndCustomerId(String txnid, String udf2);

}
