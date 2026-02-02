package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public @Data class ApplicationDemandApprovalForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Conusmer application id", required = true)
	@NotNull
	private Long consumerApplicationId;

	@ApiModelProperty(notes = "please select approval status")
	@NotNull
	private Boolean isRejected;

	@ApiModelProperty(notes = "rejected reason")
	private String rejectedRemark;

	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}

	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public String getRejectedRemark() {
		return rejectedRemark;
	}

	public void setRejectedRemark(String rejectedRemark) {
		this.rejectedRemark = rejectedRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
