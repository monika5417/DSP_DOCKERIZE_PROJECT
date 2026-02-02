package com.mpcz.deposit_scheme.backend.api.dto;

import javax.persistence.Column;

public class PayUPaymentResponseDto {

	private String mihpayid;
	private String mode;
	private String status;
	private String unmappedStatus;
	private String key;
	private String txnid;
	private String amount;
	private String cardcategory;
	private String discount;
	private String netAmountDebit;
	private String addedon;
	private String productinfo;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String hash;
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;
	private String field7;
	private String field8;
	private String field9;
	private String paymentSource;
	private String pgType;
	private String bankRefNum;
	private String bankcode;
	private String error;
	private String errorMessage;
	private String cardnum;
	private String cardhash;
	public PayUPaymentResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PayUPaymentResponseDto(String mihpayid, String mode, String status, String unmappedStatus, String key,
			String txnid, String amount, String cardcategory, String discount, String netAmountDebit, String addedon,
			String productinfo, String firstname, String lastname, String email, String phone, String udf1, String udf2,
			String udf3, String udf4, String udf5, String udf6, String udf7, String udf8, String hash, String field1,
			String field2, String field3, String field4, String field5, String field6, String field7, String field8,
			String field9, String paymentSource, String pgType, String bankRefNum, String bankcode, String error,
			String errorMessage, String cardnum, String cardhash) {
		super();
		this.mihpayid = mihpayid;
		this.mode = mode;
		this.status = status;
		this.unmappedStatus = unmappedStatus;
		this.key = key;
		this.txnid = txnid;
		this.amount = amount;
		this.cardcategory = cardcategory;
		this.discount = discount;
		this.netAmountDebit = netAmountDebit;
		this.addedon = addedon;
		this.productinfo = productinfo;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.udf1 = udf1;
		this.udf2 = udf2;
		this.udf3 = udf3;
		this.udf4 = udf4;
		this.udf5 = udf5;
		this.udf6 = udf6;
		this.udf7 = udf7;
		this.udf8 = udf8;
		this.hash = hash;
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.field6 = field6;
		this.field7 = field7;
		this.field8 = field8;
		this.field9 = field9;
		this.paymentSource = paymentSource;
		this.pgType = pgType;
		this.bankRefNum = bankRefNum;
		this.bankcode = bankcode;
		this.error = error;
		this.errorMessage = errorMessage;
		this.cardnum = cardnum;
		this.cardhash = cardhash;
	}
	public String getMihpayid() {
		return mihpayid;
	}
	public void setMihpayid(String mihpayid) {
		this.mihpayid = mihpayid;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnmappedStatus() {
		return unmappedStatus;
	}
	public void setUnmappedStatus(String unmappedStatus) {
		this.unmappedStatus = unmappedStatus;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTxnid() {
		return txnid;
	}
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCardcategory() {
		return cardcategory;
	}
	public void setCardcategory(String cardcategory) {
		this.cardcategory = cardcategory;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getNetAmountDebit() {
		return netAmountDebit;
	}
	public void setNetAmountDebit(String netAmountDebit) {
		this.netAmountDebit = netAmountDebit;
	}
	public String getAddedon() {
		return addedon;
	}
	public void setAddedon(String addedon) {
		this.addedon = addedon;
	}
	public String getProductinfo() {
		return productinfo;
	}
	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public void setUdf2(String udf2) {
		this.udf2 = udf2;
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
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	public String getField6() {
		return field6;
	}
	public void setField6(String field6) {
		this.field6 = field6;
	}
	public String getField7() {
		return field7;
	}
	public void setField7(String field7) {
		this.field7 = field7;
	}
	public String getField8() {
		return field8;
	}
	public void setField8(String field8) {
		this.field8 = field8;
	}
	public String getField9() {
		return field9;
	}
	public void setField9(String field9) {
		this.field9 = field9;
	}
	public String getPaymentSource() {
		return paymentSource;
	}
	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}
	public String getPgType() {
		return pgType;
	}
	public void setPgType(String pgType) {
		this.pgType = pgType;
	}
	public String getBankRefNum() {
		return bankRefNum;
	}
	public void setBankRefNum(String bankRefNum) {
		this.bankRefNum = bankRefNum;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getCardhash() {
		return cardhash;
	}
	public void setCardhash(String cardhash) {
		this.cardhash = cardhash;
	}
	
	

}
