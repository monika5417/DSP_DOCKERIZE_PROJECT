package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Consumer Type Model Form.")
public @Data class ConsumerTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Consumer Type Name must not be null", required = true)
	@NotEmpty
	private String consumerTypeName;

	public String getConsumerTypeName() {
		return consumerTypeName;
	}

	public void setConsumerTypeName(String consumerTypeName) {
		this.consumerTypeName = consumerTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
