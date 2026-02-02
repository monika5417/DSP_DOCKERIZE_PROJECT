package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;

public class UserLoginOtpForm {

	@NotEmpty
	private String userLoginId;
	
	@NotEmpty
	private String userPwd;

	private String source;

	private String otp;

	private Long tokenId;

	private String userMobileNo;

	public UserLoginOtpForm() {
		 
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	
	

}
