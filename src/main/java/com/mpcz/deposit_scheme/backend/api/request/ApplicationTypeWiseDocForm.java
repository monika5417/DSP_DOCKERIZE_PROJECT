package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Application Type Wise Doc Model Form.")
public @Data class ApplicationTypeWiseDocForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Application Type Id must not be null", required = true)
	@NotNull
	private Long applicationTypeId;

	@ApiModelProperty(notes = "Document Type Id must not be null", required = true)
	@NotNull
	private Long documentTypeId;

	public Long getApplicationTypeId() {
		return applicationTypeId;
	}

	public void setApplicationTypeId(Long applicationTypeId) {
		this.applicationTypeId = applicationTypeId;
	}

	public Long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
