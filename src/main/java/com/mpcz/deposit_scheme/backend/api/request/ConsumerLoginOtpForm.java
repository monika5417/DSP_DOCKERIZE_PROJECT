package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;

public class ConsumerLoginOtpForm {

	@NotEmpty
	private String consumerLoginId;

	private String source;

	private String otp;

	private Long tokenId;

	private String consumerMobileNo;

	public ConsumerLoginOtpForm() {
		 
	}

	public String getConsumerLoginId() {
		return consumerLoginId;
	}

	public void setConsumerLoginId(String consumerLoginId) {
		this.consumerLoginId = consumerLoginId;
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

	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}

	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}

	
	

}
