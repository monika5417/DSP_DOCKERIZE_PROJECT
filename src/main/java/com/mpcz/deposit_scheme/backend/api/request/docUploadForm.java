package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Document Upload Model Form ")
public @Data class docUploadForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@ApiModelProperty(notes = "Document Name must be string and not null", required = true)
//	@NotEmpty
	private Long id;

//	@ApiModelProperty(notes = "Document Name must be string and not null", required = true)
//	@NotEmpty
	private String docTypeName;

//	@ApiModelProperty(notes = "Document Name must be string and not null", required = true)
//	@NotEmpty
	private String documentNumber;

//	@ApiModelProperty(notes = "Document Name must be string and not null", required = true)
//	@NotEmpty
	private MultipartFile documentFile;

//	@ApiModelProperty(notes = "Document Name must be string and not null", required = true)
//	@NotEmpty
	private String documentUploadPath;
}
