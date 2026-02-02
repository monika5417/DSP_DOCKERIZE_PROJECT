//package com.mpcz.deposit_scheme.backend.api.services.impl;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.mpcz.deposit_scheme.backend.api.controller.PaymentGatewayController;
//import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentRequest;
//import com.mpcz.deposit_scheme.backend.api.repository.PayUPaymentRequestRepository;
//import com.mpcz.deposit_scheme.backend.api.services.PayUPaymentRequestService;
//
//@Service
//public class PayUPaymentRequestServiceImp implements PayUPaymentRequestService {
//	
//	Logger logger = LoggerFactory.getLogger(PayUPaymentRequestServiceImp.class);
//
//	@Autowired
//	private PayUPaymentRequestRepository payUPaymentRequestRepository;
//
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public PayUPaymentRequest save(PayUPaymentRequest payRequest) {
//		
//
//		PayUPaymentRequest PayUPaymentRequestDb = null;
//
//		try {
//			PayUPaymentRequestDb = payUPaymentRequestRepository.save(payRequest);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		return PayUPaymentRequestDb;
//
//	}
//
//	@Override
//	public PayUPaymentRequest findByTxnIdAndCustomerId(String txnid, String udf2) {
//		
//		PayUPaymentRequest PayUPaymentRequestDb = null;
//
//		try {
//			PayUPaymentRequestDb = payUPaymentRequestRepository.findByTxnIdAndCustomerId(txnid,udf2);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		return PayUPaymentRequestDb;
//
//	}
//
//}
