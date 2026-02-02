package com.mpcz.deposit_scheme.backend.api.response;

public class DistributionCenterDetails {
	
	private String dcCode;
	private String dcName;
	private String consumerApplicationNumber;
	private String shortDiscription;
	public String getDcCode() {
		return dcCode;
	}
	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}
	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}
	public String getShortDiscription() {
		return shortDiscription;
	}
	public void setShortDiscription(String shortDiscription) {
		this.shortDiscription = shortDiscription;
	}
	
	

}
