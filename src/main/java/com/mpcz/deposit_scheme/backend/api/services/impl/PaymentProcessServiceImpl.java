package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
import com.mpcz.deposit_scheme.backend.api.domain.Payment;
import com.mpcz.deposit_scheme.backend.api.enums.PaymentStatusEnum;
import com.mpcz.deposit_scheme.backend.api.services.PaymentProcessService;

@Service 
public class PaymentProcessServiceImpl  implements PaymentProcessService{

	@Override
	public Payment payment(Invoice invoice) {
		
		Payment payment=new Payment();
		payment.setKey(null);
		payment.setTxnId(null);
		payment.setAmount(null);
		payment.setFirstName(null);
		payment.setEmail(null);
		payment.setPhone(null);
		payment.setLastname(null);
		payment.setHash(null);
		
		return null;
	}
	
	

}
