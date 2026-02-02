package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Consumer Login Model Form . ")
public @Data class ConsumerLoginForm {

	@ApiModelProperty(notes = "consumerLoginId should be must be betwwen 6 to 8 digits and not null", required = true)
	@NotEmpty
	@Size(max = 10, min = 6)
	private String consumerLoginId;

	@ApiModelProperty(notes = "consumerPwd should be min 3 characters to max 40 characters and not null", required = true)
	@NotNull
	@Size(max = 40, min = 3)
	private String consumerPwd;

	public String getConsumerLoginId() {
		return consumerLoginId;
	}

	public void setConsumerLoginId(String consumerLoginId) {
		this.consumerLoginId = consumerLoginId;
	}

	public String getConsumerPwd() {
		return consumerPwd;
	}

	public void setConsumerPwd(String consumerPwd) {
		this.consumerPwd = consumerPwd;
	}
	
	
	
}
