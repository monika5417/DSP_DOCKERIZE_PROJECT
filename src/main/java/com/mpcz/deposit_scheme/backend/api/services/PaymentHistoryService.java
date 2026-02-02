package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentHistory;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface PaymentHistoryService {
	
	
	public Response<PaymentHistory> save(PaymentHistory history) throws PaymentHistoryException;
	
	public PaymentHistory findByApplicationNo(String applicationNo)  throws PaymentHistoryException;

	List<PaymentHistory> findAllApplication() throws PaymentHistoryException;

}
