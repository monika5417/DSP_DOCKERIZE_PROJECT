package com.mpcz.deposit_scheme.backend.api.builddesk;

import lombok.Data;

@Data
public class CustomerBillDTO {
	
	private String consumerAppllicationNo;

	private Boolean isTdsTaken;
	
	
	public Boolean getIsTdsTaken() {
		return isTdsTaken;
	}

	public void setIsTdsTaken(Boolean isTdsTaken) {
		this.isTdsTaken = isTdsTaken;
	}

	public String getConsumerAppllicationNo() {
		return consumerAppllicationNo;
	}

	public void setConsumerAppllicationNo(String consumerAppllicationNo) {
		this.consumerAppllicationNo = consumerAppllicationNo;
	}
	
	
	
	
}
