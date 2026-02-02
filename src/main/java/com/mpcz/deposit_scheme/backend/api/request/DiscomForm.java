package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Discom Model Form.")
public @Data class DiscomForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Discom Name must not be null", required = true)
	@NotEmpty
	private String discomName;
	
	@ApiModelProperty(notes = "Discom Code must not be null", required = true)
	@NotEmpty
	private String discomCode;

	public String getDiscomName() {
		return discomName;
	}

	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}

	public String getDiscomCode() {
		return discomCode;
	}

	public void setDiscomCode(String discomCode) {
		this.discomCode = discomCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
