package com.mpcz.deposit_scheme.backend.api.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SanchayAoRejectedDto {

//	@NotBlank(message = "Consumer Application No cannot be blank")
//	private String consumerApplicationNo;

	@NotBlank(message = "AO Details cannot be blank")
	private String aoDetails;

	@NotBlank(message = "AO Remark cannot be blank")
	private String aoRemark;

	@NotNull(message = "AO Rejected flag must be provided")
	private Boolean aoRejected;

	@NotNull(message = "AO Rejected Date must not be null")
	private String aoRejectedDate;

	@NotBlank(message = "TxnId cannot be blank")
	private String txnId;
	
	

	public Boolean getAoRejected() {
		return aoRejected;
	}

	public void setAoRejected(Boolean aoRejected) {
		this.aoRejected = aoRejected;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getAoDetails() {
		return aoDetails;
	}

	public void setAoDetails(String aoDetails) {
		this.aoDetails = aoDetails;
	}

	public String getAoRemark() {
		return aoRemark;
	}

	public void setAoRemark(String aoRemark) {
		this.aoRemark = aoRemark;
	}

	public boolean isAoRejected() {
		return aoRejected;
	}

	public void setAoRejected(boolean aoRejected) {
		this.aoRejected = aoRejected;
	}

	public String getAoRejectedDate() {
		return aoRejectedDate;
	}

	public void setAoRejectedDate(String aoRejectedDate) {
		this.aoRejectedDate = aoRejectedDate;
	}

	

}
