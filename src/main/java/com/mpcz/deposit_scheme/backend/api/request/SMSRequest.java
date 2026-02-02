package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class SMSRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String mobileNo;
	String accountId;
	String templateId;
	String messageType;
	String text;
	String text2;
	String text3;
	String text4;
	Long hinglish;
	
	
	
	
	public String getMobileNo() {
		return mobileNo;
	}
	public Long getHinglish() {
		return hinglish;
	}
	public void setHinglish(Long hinglish) {
		this.hinglish = hinglish;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	public String getText3() {
		return text3;
	}
	public void setText3(String text3) {
		this.text3 = text3;
	}
	public String getText4() {
		return text4;
	}
	public void setText4(String text4) {
		this.text4 = text4;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
