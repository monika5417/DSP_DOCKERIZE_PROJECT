package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SANCHAY_PAYMENT_DETAIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanchayPaymentDetails extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SANCHAY_PAYMENT_DETAIL_SEQ", sequenceName = "SANCHAY_PAYMENT_DETAIL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SANCHAY_PAYMENT_DETAIL_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;

	@NotBlank
	@Column(name = "CUSTOMER_ID", nullable = false)
	private String customerId;

	@NotNull
	@Column(name = "DATE_OF_PAYMENT", nullable = false)
	private String dateOfPayment;

	@NotNull
	@Column(name = "AMOUNT", nullable = false)
	private BigDecimal amount;

	@NotBlank
	@Column(name = "MOBILE_NO", nullable = false)
	private String mobileNo;

	@NotBlank
	@Column(name = "CUSTOMER_NAME", nullable = false)
	private String customerName;

	@NotBlank
	@Column(name = "DC_CODE", nullable = false)
	private String dcCode;

	@NotBlank
	@Column(name = "TRX_ID", nullable = false)
	private String trxId;

	@Column(name = "DEVICE_ID")
	private String deviceId;

	@NotBlank
	@Column(name = "PAYMENT_MODE", nullable = false)
	private String paymentMode;

	@Column(name = "INSTRUMENT_NO")
	private String instrumentNo;

	@Column(name = "INSTRUMENT_DATE")
	private String instrumentDate;

	@Column(name = "APP_VERSION")
	private String appVersion;

	@NotBlank
	@Column(name = "BANK_NAME", nullable = false)
	private String bankName;

	@NotNull
	@Column(name = "DEPOSITED_AMOUNT", nullable = false)
	private BigDecimal depositedAmount;

	@Column(name = "TDS")
	private BigDecimal tds;

	@Column(name = "GST")
	private BigDecimal gst;

	@NotBlank
	@Column(name = "BANK_ACC_NO", nullable = false)
	private String bankAccNo;

	@Column(name = "OTHER_AMT")
	private BigDecimal otherAmt;

	@Column(name = "AO_REMARK")
	private String aoRemark;

	@Column(name = "REJECT_DATE")
	private String rejectDate;

	@Column(name = "AO_DETAIL")
	private String aoDetail;
	
	@Column(name = "AO_REJECTED")
	private String aoRejected;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	@NotBlank
	@Column(name = "CONSUMER_APPLICATION_NUMBER", nullable = false)
	private String consumerApplicationNo;
	
	
	

	public String getAoRejected() {
		return aoRejected;
	}

	public void setAoRejected(String aoRejected) {
		this.aoRejected = aoRejected;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	

	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDcCode() {
		return dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
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

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}


	public String getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getDepositedAmount() {
		return depositedAmount;
	}

	public void setDepositedAmount(BigDecimal depositedAmount) {
		this.depositedAmount = depositedAmount;
	}

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public BigDecimal getGst() {
		return gst;
	}

	public void setGst(BigDecimal gst) {
		this.gst = gst;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public BigDecimal getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(BigDecimal otherAmt) {
		this.otherAmt = otherAmt;
	}

	public String getAoRemark() {
		return aoRemark;
	}

	public void setAoRemark(String aoRemark) {
		this.aoRemark = aoRemark;
	}


	public String getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}

	public String getAoDetail() {
		return aoDetail;
	}

	public void setAoDetail(String aoDetail) {
		this.aoDetail = aoDetail;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
