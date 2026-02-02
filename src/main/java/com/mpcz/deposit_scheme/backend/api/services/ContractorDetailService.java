package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorDetail;
import com.mpcz.deposit_scheme.backend.api.exception.ContractorDetailException;

public interface ContractorDetailService {

	public ContractorDetail saveContractorDetail(final ContractorDetail contractorDetail)
			throws ContractorDetailException;

	public ContractorDetail findByContractorUserId(final Long contractorUserId) throws ContractorDetailException;

}
