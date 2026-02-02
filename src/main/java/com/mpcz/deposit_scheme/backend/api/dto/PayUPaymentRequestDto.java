package com.mpcz.deposit_scheme.backend.api.dto;

import javax.persistence.Column;

public class PayUPaymentRequestDto {

	private String txnId;
	private String amount;
	private String prductInfo;
	private String firstName;
	private String email;
	private String mobileNumber;
	private String lastName;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String surl;
	private String furl;
	private String hash;
	private String paymentStatus;
	private String paymentStatusCode;
	private String merchantId;
	private String customerId;
	private String securityId;
	private String key;

	public PayUPaymentRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayUPaymentRequestDto(String txnId, String amount, String prductInfo, String firstName, String email,
			String mobileNumber, String lastName, String udf1, String udf2, String udf3, String udf4, String udf5,
			String udf6, String udf7, String udf8, String surl, String furl, String hash, String paymentStatus,
			String paymentStatusCode, String merchantId, String customerId, String securityId) {
		super();
		this.txnId = txnId;
		this.amount = amount;
		this.prductInfo = prductInfo;
		this.firstName = firstName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.lastName = lastName;
		this.udf1 = udf1;
		this.udf2 = udf2;
		this.udf3 = udf3;
		this.udf4 = udf4;
		this.udf5 = udf5;
		this.udf6 = udf6;
		this.udf7 = udf7;
		this.udf8 = udf8;
		this.surl = surl;
		this.furl = furl;
		this.hash = hash;
		this.paymentStatus = paymentStatus;
		this.paymentStatusCode = paymentStatusCode;
		this.merchantId = merchantId;
		this.customerId = customerId;
		this.securityId = securityId;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrductInfo() {
		return prductInfo;
	}

	public void setPrductInfo(String prductInfo) {
		this.prductInfo = prductInfo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String string) {
		this.udf2 = string;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getUdf6() {
		return udf6;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentStatusCode() {
		return paymentStatusCode;
	}

	public void setPaymentStatusCode(String paymentStatusCode) {
		this.paymentStatusCode = paymentStatusCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
