package com.mpcz.deposit_scheme.backend.api.domain;

import lombok.Data;

@Data
public class Payment {
	
	
	private String key;                //mandatory
	private String txnId;              //mandatory
	private String amount;             //mandatory
	private String productInfo;        //mandatory
	private String firstName;          //mandatory
	private String email;              //mandatory
	private String phone;              //mandatory
	private String lastname;           //mandatory
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf15;
	private String surl;
	private String furl;
	private String curl;
	private String hash;   //mandatory
	private String pg;
	private String codurl;
	private String drop_category;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public String getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public String getUdf15() {
		return udf15;
	}
	public void setUdf15(String udf15) {
		this.udf15 = udf15;
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
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getPg() {
		return pg;
	}
	public void setPg(String pg) {
		this.pg = pg;
	}
	public String getCodurl() {
		return codurl;
	}
	public void setCodurl(String codurl) {
		this.codurl = codurl;
	}
	public String getDrop_category() {
		return drop_category;
	}
	public void setDrop_category(String drop_category) {
		this.drop_category = drop_category;
	}
	
	
	
}
