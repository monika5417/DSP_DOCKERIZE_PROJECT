package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "REFUND_PROCESS_DATA")
public class RefundProcessData extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "REFUND_PROCESS_DATA_SEQ", sequenceName = "REFUND_PROCESS_DATA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFUND_PROCESS_DATA_SEQ")
	@Column(name = "R_ID")
	private Long rId;

	@Column(name = "CREDITOR_NAME ")
	private String creditorName;
	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;
	@Column(name = "BANK_NAME")
	private String bankName;
	@Column(name = "IFSC_CODE")
	private String ifscCode;
	@Column(name = "REFUNDABLE_AMNT")
	private BigDecimal refundableAmnt;
	@Column(name = "REFUND_TYPE")
	private String refundType;
	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;
	@Column(name = "REMARK")
	private String remark;
	@Transient
	private Long value;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Long getrId() {
		return rId;
	}

	public void setrId(Long rId) {
		this.rId = rId;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
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

	public BigDecimal getRefundableAmnt() {
		return refundableAmnt;
	}

	public void setRefundableAmnt(BigDecimal refundableAmnt) {
		this.refundableAmnt = refundableAmnt;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
