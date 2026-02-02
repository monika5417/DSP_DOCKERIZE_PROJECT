package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;


public class LoginOtpForm {

	@NotEmpty
	private String userId;

	private String source;

	private String otp;

	private Long tokenId;

	private String mobile;

	public LoginOtpForm() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
