package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class ParticipantAndNotParticipantDto {

	private List<ContractorParticipateAndNotPartiDto> listOfParticipatedContractors;
	
	private List<ContractorParticipateAndNotPartiDto> listOfNotParticipatedContractors;
	
	private List<ContractorParticipateAndNotPartiDto> listOfParticipantedAndNotParticipated;

	public List<ContractorParticipateAndNotPartiDto> getListOfParticipatedContractors() {
		return listOfParticipatedContractors;
	}

	public void setListOfParticipatedContractors(List<ContractorParticipateAndNotPartiDto> listOfParticipatedContractors) {
		this.listOfParticipatedContractors = listOfParticipatedContractors;
	}

	public List<ContractorParticipateAndNotPartiDto> getListOfNotParticipatedContractors() {
		return listOfNotParticipatedContractors;
	}

	public void setListOfNotParticipatedContractors(
			List<ContractorParticipateAndNotPartiDto> listOfNotParticipatedContractors) {
		this.listOfNotParticipatedContractors = listOfNotParticipatedContractors;
	}

	public List<ContractorParticipateAndNotPartiDto> getListOfParticipantedAndNotParticipated() {
		return listOfParticipantedAndNotParticipated;
	}

	public void setListOfParticipantedAndNotParticipated(
			List<ContractorParticipateAndNotPartiDto> listOfParticipantedAndNotParticipated) {
		this.listOfParticipantedAndNotParticipated = listOfParticipantedAndNotParticipated;
	}
		
	
   
}
