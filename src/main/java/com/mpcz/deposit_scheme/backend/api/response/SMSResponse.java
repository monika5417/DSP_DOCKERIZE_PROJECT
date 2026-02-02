package com.mpcz.deposit_scheme.backend.api.response;

public class SMSResponse {

	private static final long serialVersionUID = 1L;

	private String responseMsg;

	private String smsMessage;

	private String mobileNo;

	private String accountId;

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

	public String getMobileNo() {
		return mobileNo;
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

	@Override
	public String toString() {
		return "SMSResponse [responseMsg=" + responseMsg + ", smsMessage=" + smsMessage + ", mobileNo=" + mobileNo
				+ ", accountId=" + accountId + "]";
	}

}
