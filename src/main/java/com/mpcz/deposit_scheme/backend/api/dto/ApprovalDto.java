package com.mpcz.deposit_scheme.backend.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ApprovalDto {

	@NotEmpty(message = "Please provide application no.")
	private String consumerApplicationNO;
	@NotEmpty(message = "Please provide userId")
	private String userId;
	
	@NotEmpty(message = "Please provide userRemark")
	private String userRemark;
	
	@NotEmpty(message = "Please provide approvedOrNo")
	private Boolean approvedOrNo;
	
	

	public String getConsumerApplicationNO() {
		return consumerApplicationNO;
	}

	public void setConsumerApplicationNO(String consumerApplicationNO) {
		this.consumerApplicationNO = consumerApplicationNO;
	}

	public Boolean getApprovedOrNo() {
		return approvedOrNo;
	}

	public void setApprovedOrNo(Boolean approvedOrNo) {
		this.approvedOrNo = approvedOrNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

}
