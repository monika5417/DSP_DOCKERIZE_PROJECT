package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "User Login Model Form . ")
public @Data class UserLoginFormCaptcha {

	@ApiModelProperty(notes = "userLoginId should be must be 10 digits and not null", required = true)
	@NotEmpty
	@Size(max = 10, min = 3)
	private String userLoginId;
//	private String userId;

	@ApiModelProperty(notes = "userLoginPwd should be min 3 characters to max 40 characters and not null", required = true)
	@NotNull
	@Size(max = 40, min = 3)
	private String userLoginPwd;
//	private String userPwd;

	@NotNull
	@Size(max = 5, min = 5)
	private String captcha;

	@NotNull
	private Long captchaId;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserLoginPwd() {
		return userLoginPwd;
	}

	public void setUserLoginPwd(String userLoginPwd) {
		this.userLoginPwd = userLoginPwd;
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
