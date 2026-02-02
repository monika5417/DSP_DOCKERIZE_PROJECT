package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Work Type Model Form.")
public @Data class WorkTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Work Type Name must not be null", required = true)
	@NotEmpty
	private String workTypeName;

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

