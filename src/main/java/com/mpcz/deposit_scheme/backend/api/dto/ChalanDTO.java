package com.mpcz.deposit_scheme.backend.api.dto;


import lombok.Data;

@Data
public class ChalanDTO {

	private String consumerApplicationNo;
	
	private String consumerName;
	
	private double cgst;

	private double sgst;
	
	private String consumerMobileNumber;
	
	private String registrationFee;
	
	private String totalAmount;

	private String supervisionCharge;
	
	private String estimateCharge;
	
	
	private String supplyAffordingCharge;
	

}
