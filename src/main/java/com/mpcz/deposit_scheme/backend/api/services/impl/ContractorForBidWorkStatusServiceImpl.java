package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.dto.ContractorForBidWorkStatusDto;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidWorkStatusRepository;
import com.mpcz.deposit_scheme.backend.api.services.ContractorForBidWorkStatusService;

@Service
public class ContractorForBidWorkStatusServiceImpl implements ContractorForBidWorkStatusService {

    @Autowired
    
    private ContractorForBidWorkStatusRepository contractorForBidWorkStatusRepository;
	
	@Override
	public ContractorForBidWorkStatus save(ContractorForBidWorkStatus contractorForBidWorkStatus) {
		// TODO Auto-generated method stub
		return contractorForBidWorkStatusRepository.save(contractorForBidWorkStatus);
	}

	@Override
	public ContractorForBidWorkStatus update(ContractorForBidWorkStatusDto contractorForBidWorkStatusDto) {
		// TODO Auto-generated method stub
		 Optional<ContractorForBidWorkStatus> contractorForBidWorkStatus = contractorForBidWorkStatusRepository.findById(contractorForBidWorkStatusDto.getWorkStatusId());
	if(contractorForBidWorkStatus.isPresent()) {
		 ContractorForBidWorkStatus conWorkStatus=contractorForBidWorkStatus.get();
		 conWorkStatus.setConWorkCompleteDate(contractorForBidWorkStatusDto.getConWorkCompleteDate());
		conWorkStatus.setConWorkStartedDate(contractorForBidWorkStatusDto.getConWorkStartedDate());
		conWorkStatus.setMaterialHandoverSiteDate(contractorForBidWorkStatusDto.getMaterialHandoverSiteDate());
		conWorkStatus.setMaterialInstallFinishDate(contractorForBidWorkStatusDto.getMaterialInstallFinishDate());
		conWorkStatus.setMaterialInstallStartDate(contractorForBidWorkStatusDto.getMaterialInstallStartDate());
		conWorkStatus.setDgmStcDate(contractorForBidWorkStatusDto.getDateOfDgmStc());
		conWorkStatus.setVendorName(contractorForBidWorkStatusDto.getVendorName());
		conWorkStatus.setVendorMaterialSpecification(contractorForBidWorkStatusDto.getVendorMaterialSpecification());
		conWorkStatus.setDtr(contractorForBidWorkStatusDto.getDtr());
		conWorkStatus.setPtr(contractorForBidWorkStatusDto.getPtr());
		conWorkStatus.setLt(contractorForBidWorkStatusDto.getLt());
		conWorkStatus.setHt11Kv(contractorForBidWorkStatusDto.getHt11Kv());
		conWorkStatus.setHt33Kv(contractorForBidWorkStatusDto.getHt33Kv());
		conWorkStatus.setHt132Kv(contractorForBidWorkStatusDto.getHt132Kv());
		
		if(contractorForBidWorkStatusDto.getActualWorkCompletionDate()!=null) {
			conWorkStatus.setActualWorkCompletionDate(contractorForBidWorkStatusDto.getActualWorkCompletionDate());
		}
		return contractorForBidWorkStatusRepository.save(conWorkStatus);
	}
	return null;
	}

	@Override
	public List<ContractorForBidWorkStatus> findByConsumerApplicationNo(String applicationNo) {
		
		 List<ContractorForBidWorkStatus> findByConsumerApplicationNumber = contractorForBidWorkStatusRepository.findByConsumerApplicationNumber(applicationNo);
	return findByConsumerApplicationNumber;
	}
	
	
	
	

}
