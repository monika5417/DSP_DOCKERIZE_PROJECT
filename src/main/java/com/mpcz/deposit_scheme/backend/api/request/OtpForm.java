package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class OtpForm {

	@Pattern(regexp = "(^$|[0-9]{10})")
	@NotEmpty
	private String mobileNo;

	@NotEmpty
	private String source;

	
	private String otp;

	public OtpForm() {

	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	@Override
	public String toString() {
		return "OtpForm [mobileNo=" + mobileNo + ", source=" + source + ", otp=" + otp + "]";
	}

}
