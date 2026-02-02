package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Invoice generate  request from")
public class InvoiceRequestForm {
	
	@NotNull
	@NotEmpty
	@ApiModelProperty("consumerApplicatinoNumber must be string not null")
	private String consumerApplicatinoNumber;

	public String getConsumerApplicatinoNumber() {
		return consumerApplicatinoNumber;
	}

	public void setConsumerApplicatinoNumber(String consumerApplicatinoNumber) {
		this.consumerApplicatinoNumber = consumerApplicatinoNumber;
	}
	
	

}
