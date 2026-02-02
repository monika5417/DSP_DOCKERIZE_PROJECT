package com.mpcz.deposit_scheme.backend.api.services;

import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.domain.SanchayPaymentDetails;
import com.mpcz.deposit_scheme.backend.api.dto.SanchayAoRejectedDto;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationStatusException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.TransactionAmountException;

public interface PoseMachinePostDataService {

	String save(PoseMachinePostData poseMachinePostData)
			throws ConsumerApplicationDetailException, TransactionAmountException;

	Object getDemandFeesPaymentDetails(String consumerApplicationNo);

	public SanchayPaymentDetails recordOfSanchayPostData(SanchayPaymentDetails sanchayPaymentDetails) throws ConsumerApplicationDetailException, ApplicationStatusException;

	public SanchayPaymentDetails aoRejectedSanchayApplication(SanchayAoRejectedDto sanchayAoRejectedDto) throws ConsumerApplicationDetailException, ApplicationStatusException;
}
