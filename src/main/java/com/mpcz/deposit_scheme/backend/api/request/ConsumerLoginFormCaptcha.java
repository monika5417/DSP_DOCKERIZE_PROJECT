package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Consumer Login Model Form . ")
public @Data class ConsumerLoginFormCaptcha {

	@ApiModelProperty(notes = "consumerLoginId should be must be 10 digits and not null", required = true)
	@NotEmpty
	@Size(max = 10, min = 10)
	private String consumerLoginId;
//	private String userId;

	@ApiModelProperty(notes = "consumerLoginPwd should be min 3 characters to max 40 characters and not null", required = true)
	@NotNull
	@Size(max = 40, min = 3)
	private String consumerLoginPwd;
//	private String userPwd;

	@NotNull
	@Size(max = 5, min = 5)
	private String captcha;

	@NotNull
	private Long captchaId;

	public String getConsumerLoginId() {
		return consumerLoginId;
	}

	public void setConsumerLoginId(String consumerLoginId) {
		this.consumerLoginId = consumerLoginId;
	}

	public String getConsumerLoginPwd() {
		return consumerLoginPwd;
	}

	public void setConsumerLoginPwd(String consumerLoginPwd) {
		this.consumerLoginPwd = consumerLoginPwd;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Long getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(Long captchaId) {
		this.captchaId = captchaId;
	}

	
	
}
