package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Application Type Model Form.")
public @Data class ApplicationTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Application Type Name must not be null", required = true)
	@NotEmpty
	private String applicationTypeName;

	public String getApplicationTypeName() {
		return applicationTypeName;
	}

	public void setApplicationTypeName(String applicationTypeName) {
		this.applicationTypeName = applicationTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
