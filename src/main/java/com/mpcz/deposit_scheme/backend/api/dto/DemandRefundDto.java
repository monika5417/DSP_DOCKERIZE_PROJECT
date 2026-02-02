package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

public class DemandRefundDto {

	
	private BigDecimal demandAmount;
	private String applicationNo;
	private String demandTransactionId;
	private BigDecimal demandRefundableAmount;
	
	public BigDecimal getDemandAmount() {
		return demandAmount;
	}
	public void setDemandAmount(BigDecimal demandAmount) {
		this.demandAmount = demandAmount;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getDemandTransactionId() {
		return demandTransactionId;
	}
	public void setDemandTransactionId(String demandTransactionId) {
		this.demandTransactionId = demandTransactionId;
	}
	public BigDecimal getDemandRefundableAmount() {
		return demandRefundableAmount;
	}
	public void setDemandRefundableAmount(BigDecimal demandRefundableAmount) {
		this.demandRefundableAmount = demandRefundableAmount;
	}
	
	
	
	
	
}
