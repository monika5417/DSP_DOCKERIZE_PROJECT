package com.mpcz.deposit_scheme.backend.api.dto;

public class ConsumerApplicationPayDetailsDto {
	
	private String consumerApplicationNumber;
	
	private String ammount;

	public ConsumerApplicationPayDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsumerApplicationPayDetailsDto(String consumerApplicationNumber, String ammount) {
		super();
		this.consumerApplicationNumber = consumerApplicationNumber;
		this.ammount = ammount;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getAmmount() {
		return ammount;
	}

	public void setAmmount(String ammount) {
		this.ammount = ammount;
	}
	
	

}
