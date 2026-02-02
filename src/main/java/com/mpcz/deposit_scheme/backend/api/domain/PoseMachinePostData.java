package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "POSE_MACHINE_D")
public class PoseMachinePostData {
	
	@Id
	@SequenceGenerator(name = "POSE_MACHINE_D_SEQ", sequenceName = "POSE_MACHINE_D_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSE_MACHINE_D_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private long id; 
	
	@Column(name="APPLICATION_NUMBWER")
	private String applicationNumber;
	
	@Column(name="LOCATION_CODE")
    private String locationCode;
	
	@Column(name="TXN_AMOUNT")
    private BigDecimal txnAmount; // Transaction Amount
	
	@Column(name="MR_NO")
    private String mrNo; // Money Receipt No
	
	@Column(name="DEVICE_ID")
    private String deviceId; // 20 Char
	
//	@Column(name="")
//    private String userId; // MobileNo ,15 Char
	
	@Column(name="DATE_OF_PAYMENT")
    private Date dateOfPayment; // 1 Now Push this Date in NGB
	
	@Column(name="CASH_ACCEPTED_DATE")
    private Date cashAcceptedDate;// 2 Ignore this Date to push in NGB
	
	@Column(name="PAYMENT_MODE")
    private String paymentMode; // Cash or DD
	
	@Column(name="BANK_NAME")
    private String bankName; // Bank Name if( paymentMode = DD )
	
	@Column(name="INSTRUMENT_NO")
    private String instrumentNo; // DDNo if( paymentMode = DD )
	
	@Column(name="INSTRUNENT_DATE")
    private Date instrumentDate; // DD Date if( paymentMode = DD )
	
	@Column(name="PAYMENT_TYPE")
	private String paymentType;

	
	
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public BigDecimal getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getMrNo() {
		return mrNo;
	}

	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}



	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public Date getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public Date getCashAcceptedDate() {
		return cashAcceptedDate;
	}

	public void setCashAcceptedDate(Date cashAcceptedDate) {
		this.cashAcceptedDate = cashAcceptedDate;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	

}
