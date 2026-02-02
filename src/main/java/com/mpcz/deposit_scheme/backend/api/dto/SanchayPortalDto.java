package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

public class SanchayPortalDto {

	private String applicationNumber;
	private String address1;
	private String consumerName;
	private String mobileNumber;
	private String city;
	private String dc;
	private String dcCode;
	private BigDecimal cgst;
	private BigDecimal sgst;
	private BigDecimal superVisionAmount;
	private BigDecimal depositAmount;
	private BigDecimal systemDevelopmentCharge;
	private BigDecimal supplyAffordingCharge;
	private BigDecimal colonyOrSlum;
	private BigDecimal jeReturnAmount;
	private BigDecimal totalBalanceSupervisionAmount;
	private BigDecimal totalBalanceDepositAmount;

	private BigDecimal mkmyTotalAmount;
	private BigDecimal govMafBill;
	private BigDecimal mpmkMafBill;
	private BigDecimal payableAmount;
	private BigDecimal AvedanShulk;

	private BigDecimal DsSvMkPayAmount;
	private String applicationType;
	private String ngbDcCode;

	private BigDecimal mkmySecurityDeposit;
	private BigDecimal avedanShulkFiveRupee;

//	key added for gst no. (10-june-2024)
	private String gstNumber;

	private BigDecimal registrationFees;;

	private String paymentType;
	private String transactionId;
	private BigDecimal sspMeterCost;
	private BigDecimal sspRegistrationCharge;
	
	private BigDecimal securityDepositAmnt;
	
	
	
	
	

	public BigDecimal getSecurityDepositAmnt() {
		return securityDepositAmnt;
	}

	public void setSecurityDepositAmnt(BigDecimal securityDepositAmnt) {
		this.securityDepositAmnt = securityDepositAmnt;
	}

	public BigDecimal getSspMeterCost() {
		return sspMeterCost;
	}

	public void setSspMeterCost(BigDecimal sspMeterCost) {
		this.sspMeterCost = sspMeterCost;
	}

	public BigDecimal getSspRegistrationCharge() {
		return sspRegistrationCharge;
	}

	public void setSspRegistrationCharge(BigDecimal sspRegistrationCharge) {
		this.sspRegistrationCharge = sspRegistrationCharge;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public BigDecimal getRegistrationFees() {
		return registrationFees;
	}

	public void setRegistrationFees(BigDecimal registrationFees) {
		this.registrationFees = registrationFees;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public BigDecimal getMkmySecurityDeposit() {
		return mkmySecurityDeposit;
	}

	public void setMkmySecurityDeposit(BigDecimal mkmySecurityDeposit) {
		this.mkmySecurityDeposit = mkmySecurityDeposit;
	}

	public BigDecimal getAvedanShulkFiveRupee() {
		return avedanShulkFiveRupee;
	}

	public void setAvedanShulkFiveRupee(BigDecimal avedanShulkFiveRupee) {
		this.avedanShulkFiveRupee = avedanShulkFiveRupee;
	}

	public String getNgbDcCode() {
		return ngbDcCode;
	}

	public void setNgbDcCode(String ngbDcCode) {
		this.ngbDcCode = ngbDcCode;
	}

	public BigDecimal getTotalBalanceDepositAmount() {
		return totalBalanceDepositAmount;
	}

	public void setTotalBalanceDepositAmount(BigDecimal totalBalanceDepositAmount) {
		this.totalBalanceDepositAmount = totalBalanceDepositAmount;
	}

	public String getDcCode() {
		return dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public BigDecimal getTotalBalanceSupervisionAmount() {
		return totalBalanceSupervisionAmount;
	}

	public void setTotalBalanceSupervisionAmount(BigDecimal totalBalanceSupervisionAmount) {
		this.totalBalanceSupervisionAmount = totalBalanceSupervisionAmount;
	}

	public BigDecimal getColonyOrSlum() {
		return colonyOrSlum;
	}

	public void setColonyOrSlum(BigDecimal colonyOrSlum) {
		this.colonyOrSlum = colonyOrSlum;
	}

	public BigDecimal getJeReturnAmount() {
		return jeReturnAmount;
	}

	public void setJeReturnAmount(BigDecimal jeReturnAmount) {
		this.jeReturnAmount = jeReturnAmount;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public BigDecimal getSuperVisionAmount() {
		return superVisionAmount;
	}

	public void setSuperVisionAmount(BigDecimal superVisionAmount) {
		this.superVisionAmount = superVisionAmount;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst(BigDecimal bigDecimal) {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getSystemDevelopmentCharge() {
		return systemDevelopmentCharge;
	}

	public void setSystemDevelopmentCharge(BigDecimal systemDevelopmentCharge) {
		this.systemDevelopmentCharge = systemDevelopmentCharge;
	}

	public BigDecimal getSupplyAffordingCharge() {
		return supplyAffordingCharge;
	}

	public void setSupplyAffordingCharge(BigDecimal supplyAffordingCharge) {
		this.supplyAffordingCharge = supplyAffordingCharge;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public BigDecimal getMkmyTotalAmount() {
		return mkmyTotalAmount;
	}

	public void setMkmyTotalAmount(BigDecimal mkmyTotalAmount) {
		this.mkmyTotalAmount = mkmyTotalAmount;
	}

	public BigDecimal getGovMafBill() {
		return govMafBill;
	}

	public void setGovMafBill(BigDecimal govMafBill) {
		this.govMafBill = govMafBill;
	}

	public BigDecimal getMpmkMafBill() {
		return mpmkMafBill;
	}

	public void setMpmkMafBill(BigDecimal mpmkMafBill) {
		this.mpmkMafBill = mpmkMafBill;
	}

	public BigDecimal getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}

	public BigDecimal getAvedanShulk() {
		return AvedanShulk;
	}

	public void setAvedanShulk(BigDecimal avedanShulk) {
		AvedanShulk = avedanShulk;
	}

	public BigDecimal getDsSvMkPayAmount() {
		return DsSvMkPayAmount;
	}

	public void setDsSvMkPayAmount(BigDecimal dsSvMkPayAmount) {
		DsSvMkPayAmount = dsSvMkPayAmount;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

}
