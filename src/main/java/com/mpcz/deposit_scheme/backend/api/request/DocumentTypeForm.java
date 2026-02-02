package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description = "Document Model Form ")
public @Data class DocumentTypeForm implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Document Name must be string and not null", required = true)
	@NotEmpty
	private String documentTypeName;

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}

