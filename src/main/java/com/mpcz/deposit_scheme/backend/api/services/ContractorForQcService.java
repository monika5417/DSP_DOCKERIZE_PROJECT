package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorQc;
import com.mpcz.deposit_scheme.backend.api.exception.ContractorForQcException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;

public interface ContractorForQcService {
	
	public ContractorQc findByContractorId(Long contractorId) throws WorkTypeException, ContractorForQcException;

}
