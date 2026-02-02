package com.mpcz.deposit_scheme.backend.api.response;

public class OTPResponse {

	private String otp;
	private String mobileNo;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "OTPResponse [otp=" + otp + ", mobileNo=" + mobileNo + "]";
	}

}
