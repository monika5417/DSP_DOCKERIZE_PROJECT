package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

public class ReviseDemandRefundDto {

	private BigDecimal reviseDemandAmount;
	private String applicationNo;
	private String reviseDemandTransactionId;
	public BigDecimal getReviseDemandAmount() {
		return reviseDemandAmount;
	}
	public void setReviseDemandAmount(BigDecimal reviseDemandAmount) {
		this.reviseDemandAmount = reviseDemandAmount;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getReviseDemandTransactionId() {
		return reviseDemandTransactionId;
	}
	public void setReviseDemandTransactionId(String reviseDemandTransactionId) {
		this.reviseDemandTransactionId = reviseDemandTransactionId;
	}
	
	
	

}
