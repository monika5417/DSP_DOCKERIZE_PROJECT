package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class IpTracking implements Serializable{
	private BigDecimal id;
	
	private String ipAddress;
	private String tableName;
	private String operation;
	private String recordId;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	

}
