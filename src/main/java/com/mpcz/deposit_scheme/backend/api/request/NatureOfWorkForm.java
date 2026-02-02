package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Nature Of Work Model Form.")
public @Data class NatureOfWorkForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = " Nature of work Name  must not be null", required = true)
	@NotEmpty
	private String natureOfWorkName;

	public String getNatureOfWorkName() {
		return natureOfWorkName;
	}

	public void setNatureOfWorkName(String natureOfWorkName) {
		this.natureOfWorkName = natureOfWorkName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
