package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name="CON_APP_CAN_REF_AMT") // CONSUMER APPLICATION CANCELLATION REFUND AMOUNT
@Entity
public class ConsumerAppCancellationRefundAmount extends Auditable<Long> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "CON_APP_CAN_REF_AMT_SEQ", sequenceName = "CON_APP_CAN_REF_AMT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CON_APP_CAN_REF_AMT_SEQ")
	@Column(name="ID")
	private Long id;
	
	@Column(name="APPLICATION_NO")
	private String applicationNo;
	
	@Column(name="PAYMENT_TYPE")
	private String paymentType;
	
	@Column(name="TXN_ID")
	private String txnId;
	
	@Column(name="TXN_AMOUNT")
	private BigDecimal txnAmount;
	
	@Column(name="MERCHANT_ID")
	private String merchantId;
	
	@Column(name="ORDER_ID")
	private String orderId;
	
	@Column(name="REFUNDABLE_AMOUNT")
	private BigDecimal refundableAmount;
	
	@Column(name = "TRANSACTION_DATE")
	private String transactionDate;
	
	@Column(name = "REFUND_STATUS")
	private String refundStatus;
	
	

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public BigDecimal getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getRefundableAmount() {
		return refundableAmount;
	}

	public void setRefundableAmount(BigDecimal refundableAmount) {
		this.refundableAmount = refundableAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
