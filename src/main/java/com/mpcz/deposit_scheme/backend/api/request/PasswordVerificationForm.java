package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Password Verification Model Form . ")
public @Data class PasswordVerificationForm {

	@ApiModelProperty(notes = "oldPassword should be min 3 characters to max 40 characters and not null", required = true)
	@NotEmpty
	private String oldPassword;

	@ApiModelProperty(notes = "newPassword should be min 3 characters to max 40 characters and not null", required = true)
	@NotEmpty
	private String newPassword;

	@ApiModelProperty(notes = "loginId must not be null", required = true)
	@NotEmpty
	private String loginId;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	
}
