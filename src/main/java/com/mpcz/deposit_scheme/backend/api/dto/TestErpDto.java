package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

public class TestErpDto {

	private String erpNo;
	private String estimateSancNo;
	private String estimateName;
	private String location;
	private String scheme;
	private BigDecimal supervisionAmnt;
	private BigDecimal estimateAmnt;
	private BigDecimal cgst;
	private BigDecimal sgst;
	private String estimateStatus;
	private String approvedBy;

	
	
	public String getErpNo() {
		return erpNo;
	}
	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}
	public String getEstimateSancNo() {
		return estimateSancNo;
	}
	public void setEstimateSancNo(String estimateSancNo) {
		this.estimateSancNo = estimateSancNo;
	}
	public String getEstimateName() {
		return estimateName;
	}
	public void setEstimateName(String estimateName) {
		this.estimateName = estimateName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public BigDecimal getSupervisionAmnt() {
		return supervisionAmnt;
	}
	public void setSupervisionAmnt(BigDecimal supervisionAmnt) {
		this.supervisionAmnt = supervisionAmnt;
	}
	public BigDecimal getEstimateAmnt() {
		return estimateAmnt;
	}
	public void setEstimateAmnt(BigDecimal estimateAmnt) {
		this.estimateAmnt = estimateAmnt;
	}
	public BigDecimal getCgst() {
		return cgst;
	}
	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}
	public BigDecimal getSgst() {
		return sgst;
	}
	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}
	public String getEstimateStatus() {
		return estimateStatus;
	}
	public void setEstimateStatus(String estimateStatus) {
		this.estimateStatus = estimateStatus;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	
	
	
}
