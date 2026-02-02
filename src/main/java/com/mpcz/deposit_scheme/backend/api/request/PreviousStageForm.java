package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Application Type Model Form.")
public @Data class PreviousStageForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ApplicationId is required", required = true)
	@NotNull
	private Long consumersApplicationDetailId;
	
	@ApiModelProperty(notes = "Remark is required ", required = true)
	@NotEmpty
	private String remark;

	public Long getConsumersApplicationDetailId() {
		return consumersApplicationDetailId;
	}

	public void setConsumersApplicationDetailId(Long consumersApplicationDetailId) {
		this.consumersApplicationDetailId = consumersApplicationDetailId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
