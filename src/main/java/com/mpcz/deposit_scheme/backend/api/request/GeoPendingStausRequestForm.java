package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "GeoPendingStausRequest Form.")
public @Data class GeoPendingStausRequestForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Consumer Mobile No", required = true)
	private String consumerMobileNumber;
}