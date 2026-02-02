package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public @Data class ApplicationStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "id  must be int and not null", required = true)
	private long id;

	@ApiModelProperty(notes = "application Status Name  must be string and not null", required = true)
	@NotEmpty
	private String applicationStatusName;

	@ApiModelProperty(notes = "application Status Description  must be string and not null", required = true)
	@NotEmpty
	private String applicationStatusDescription;

}
