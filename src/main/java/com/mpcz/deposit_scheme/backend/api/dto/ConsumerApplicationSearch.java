package com.mpcz.deposit_scheme.backend.api.dto;

public class ConsumerApplicationSearch {
	
	
private Long consumerId;
private String consumerName;
private Long natureOfWorkId;
private Long schemeTypeId;
private Long applicationStatusId;
private String applicationNo;

public Long getConsumerId() {
	return consumerId;
}
public void setConsumerId(Long consumerId) {
	this.consumerId = consumerId;
}
public String getConsumerName() {
	return consumerName;
}
public void setConsumerName(String consumerName) {
	this.consumerName = consumerName;
}
public Long getNatureOfWorkId() {
	return natureOfWorkId;
}
public void setNatureOfWorkId(Long natureOfWorkId) {
	this.natureOfWorkId = natureOfWorkId;
}
public Long getSchemeTypeId() {
	return schemeTypeId;
}
public void setSchemeTypeId(Long schemeTypeId) {
	this.schemeTypeId = schemeTypeId;
}
public Long getApplicationStatusId() {
	return applicationStatusId;
}
public void setApplicationStatusId(Long applicationStatusId) {
	this.applicationStatusId = applicationStatusId;
}
public String getApplicationNo() {
	return applicationNo;
}
public void setApplicationNo(String applicationNo) {
	this.applicationNo = applicationNo;
}


	
	
	
}
