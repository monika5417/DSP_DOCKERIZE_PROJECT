package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "User Login Model Form . ")
public @Data class UserLoginForm {

	@ApiModelProperty(notes = "userLoginId should be must be betwwen 6 to 8 digits and not null", required = true)
	@NotEmpty
	@Size(max = 10, min = 6)
	private String userLoginId;

	@ApiModelProperty(notes = "userPwd should be min 3 characters to max 40 characters and not null", required = true)
	@NotNull
	@Size(max = 40, min = 3)
	private String userPwd;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	
	
}
