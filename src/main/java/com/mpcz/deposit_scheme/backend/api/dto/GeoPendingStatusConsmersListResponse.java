package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GeoPendingStatusConsmersListResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String consumerApplicationNumber;

	private String consumerApplicationStatus;

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getConsumerApplicationStatus() {
		return consumerApplicationStatus;
	}

	public void setConsumerApplicationStatus(String consumerApplicationStatus) {
		this.consumerApplicationStatus = consumerApplicationStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	


	
	
	

}
