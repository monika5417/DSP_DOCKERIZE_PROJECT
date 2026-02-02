package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.dto.ContractorForBidWorkStatusDto;

public interface ContractorForBidWorkStatusService {
	
	public ContractorForBidWorkStatus save (ContractorForBidWorkStatus contractorForBidWorkStatus);
	
	public ContractorForBidWorkStatus update (ContractorForBidWorkStatusDto contractorForBidWorkStatusDto);
	
	public List<ContractorForBidWorkStatus> findByConsumerApplicationNo(String applicationNo);

}
