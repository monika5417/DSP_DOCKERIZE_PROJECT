package com.mpcz.deposit_scheme.backend.api.dto;

public class ConsumerUpdatePasswordDTO {

	
	private String consumerMobileNo;
	private String password;
	private String confirmPassword;
	
	
	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}
	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
