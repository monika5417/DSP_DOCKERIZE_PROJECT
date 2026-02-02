package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Scheme Type Model Form.")
public @Data class SchemeTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Scheme Type Name must not be null", required = true)
	@NotEmpty
	private String schemeTypeName;

	public String getSchemeTypeName() {
		return schemeTypeName;
	}

	public void setSchemeTypeName(String schemeTypeName) {
		this.schemeTypeName = schemeTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
