package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@EqualsAndHashCode(callSuper = false)

@ToString
@Entity
@Table(name = "PAYMENT_HISTORY")
public @Data class PaymentHistory {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PAYMENT_HISTORY_SEQ", sequenceName = "PAYMENT_HISTORY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_HISTORY_SEQ")
	@Column(name = "PAYMENT_HISTORY_id", columnDefinition = "serial")
	private Long paymentHistoryId;

	@Column(name = "INVOICE_NO")
	private String invoiceNo;

	@Column(name = "APPLICATION_NO")
	private String applicationNo;

	@Column(name = "TRANSACTION_NO")
	private String transactionNo;

	@Column(name = "REQUEST_STATUS")
	private String requestStatus;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	@Column(name = "CONSUMER_MOB_NO")
	private String consumerMobNo;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "REFERENCE")
	private String reference;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "PORTAL")
	private String protal;

	public Long getPaymentHistoryId() {
		return paymentHistoryId;
	}

	public void setPaymentHistoryId(Long paymentHistoryId) {
		this.paymentHistoryId = paymentHistoryId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerMobNo() {
		return consumerMobNo;
	}

	public void setConsumerMobNo(String consumerMobNo) {
		this.consumerMobNo = consumerMobNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProtal() {
		return protal;
	}

	public void setProtal(String protal) {
		this.protal = protal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
