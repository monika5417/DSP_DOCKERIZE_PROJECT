package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

public class RegistrationRefundDto {

	private BigDecimal registrationAmount;
	private String applicationNo;
	private String registrationTransactionId;
	private BigDecimal registrationRefundableAmount;
	
	
	public BigDecimal getRegistrationAmount() {
		return registrationAmount;
	}
	public void setRegistrationAmount(BigDecimal registrationAmount) {
		this.registrationAmount = registrationAmount;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRegistrationTransactionId() {
		return registrationTransactionId;
	}
	public void setRegistrationTransactionId(String registrationTransactionId) {
		this.registrationTransactionId = registrationTransactionId;
	}
	public BigDecimal getRegistrationRefundableAmount() {
		return registrationRefundableAmount;
	}
	public void setRegistrationRefundableAmount(BigDecimal registrationRefundableAmount) {
		this.registrationRefundableAmount = registrationRefundableAmount;
	}
	
	
	
	
	
	
	
}
