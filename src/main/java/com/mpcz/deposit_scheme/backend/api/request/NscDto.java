package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


public @Data class NscDto implements Serializable {

	private static final long serialVersionUID = 1L;


	private String applicationNumber;

	
	private String appliedLoad;

	
	private String category;
	
	
	private String colonyType;
	
	
	private String consumerAddress;
	
	
	private String consumerName;
	
	
	private Date dateOfApply;
	
	
	private Date dateOfPostDeposit;
	
	
	private String dcCode;
	
	
	private String distanceFromLtPole;
	
	
	private String dtrCode;
	
	
	private String dtrName;
	
	
	private String feederCode;
	
	
	private String feederName;
	
	
	private String floorCount;
	
	
	private String floorNo;
	
	

	
	private String iSamparkDcCode;
	
	
	private String isMultiFloorComplex;
	
	
	private String loadUnit;
	
	
	private String meterCost;
	
	
	private String purpose;
	
	
	private String reason;
	
	
	private String registrationFees;
	
	
	private String remarkOfDcUser;
	
	private String nscId;
	
	

	private String remarkOfSurvayor;
	
	
	private String securityDepositCharge;
	
	
	private String supplyAffordingCharge;


	public String getApplicationNumber() {
		return applicationNumber;
	}


	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}


	public String getAppliedLoad() {
		return appliedLoad;
	}


	public void setAppliedLoad(String appliedLoad) {
		this.appliedLoad = appliedLoad;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getColonyType() {
		return colonyType;
	}


	public void setColonyType(String colonyType) {
		this.colonyType = colonyType;
	}


	public String getConsumerAddress() {
		return consumerAddress;
	}


	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}


	public String getConsumerName() {
		return consumerName;
	}


	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}


	public Date getDateOfApply() {
		return dateOfApply;
	}


	public void setDateOfApply(Date dateOfApply) {
		this.dateOfApply = dateOfApply;
	}


	public Date getDateOfPostDeposit() {
		return dateOfPostDeposit;
	}


	public void setDateOfPostDeposit(Date dateOfPostDeposit) {
		this.dateOfPostDeposit = dateOfPostDeposit;
	}


	public String getDcCode() {
		return dcCode;
	}


	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}


	public String getDistanceFromLtPole() {
		return distanceFromLtPole;
	}


	public void setDistanceFromLtPole(String distanceFromLtPole) {
		this.distanceFromLtPole = distanceFromLtPole;
	}


	public String getDtrCode() {
		return dtrCode;
	}


	public void setDtrCode(String dtrCode) {
		this.dtrCode = dtrCode;
	}


	public String getDtrName() {
		return dtrName;
	}


	public void setDtrName(String dtrName) {
		this.dtrName = dtrName;
	}


	public String getFeederCode() {
		return feederCode;
	}


	public void setFeederCode(String feederCode) {
		this.feederCode = feederCode;
	}


	public String getFeederName() {
		return feederName;
	}


	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}


	public String getFloorCount() {
		return floorCount;
	}


	public void setFloorCount(String floorCount) {
		this.floorCount = floorCount;
	}


	public String getFloorNo() {
		return floorNo;
	}


	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}


	public String getiSamparkDcCode() {
		return iSamparkDcCode;
	}


	public void setiSamparkDcCode(String iSamparkDcCode) {
		this.iSamparkDcCode = iSamparkDcCode;
	}


	public String getIsMultiFloorComplex() {
		return isMultiFloorComplex;
	}


	public void setIsMultiFloorComplex(String isMultiFloorComplex) {
		this.isMultiFloorComplex = isMultiFloorComplex;
	}


	public String getLoadUnit() {
		return loadUnit;
	}


	public void setLoadUnit(String loadUnit) {
		this.loadUnit = loadUnit;
	}


	public String getMeterCost() {
		return meterCost;
	}


	public void setMeterCost(String meterCost) {
		this.meterCost = meterCost;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getRegistrationFees() {
		return registrationFees;
	}


	public void setRegistrationFees(String registrationFees) {
		this.registrationFees = registrationFees;
	}


	public String getRemarkOfDcUser() {
		return remarkOfDcUser;
	}


	public void setRemarkOfDcUser(String remarkOfDcUser) {
		this.remarkOfDcUser = remarkOfDcUser;
	}


	public String getNscId() {
		return nscId;
	}


	public void setNscId(String nscId) {
		this.nscId = nscId;
	}


	public String getRemarkOfSurvayor() {
		return remarkOfSurvayor;
	}


	public void setRemarkOfSurvayor(String remarkOfSurvayor) {
		this.remarkOfSurvayor = remarkOfSurvayor;
	}


	public String getSecurityDepositCharge() {
		return securityDepositCharge;
	}


	public void setSecurityDepositCharge(String securityDepositCharge) {
		this.securityDepositCharge = securityDepositCharge;
	}


	public String getSupplyAffordingCharge() {
		return supplyAffordingCharge;
	}


	public void setSupplyAffordingCharge(String supplyAffordingCharge) {
		this.supplyAffordingCharge = supplyAffordingCharge;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}

