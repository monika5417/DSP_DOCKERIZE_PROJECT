package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "BillDeskPaymentResponse")
@Table(name = "BILLDESK_PAYMENT_RES")
public @Data class BillDeskPaymentResponse  {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BILL_DESK_RES_SEQ", sequenceName = "BILL_DESK_RES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILL_DESK_RES_SEQ")
	@Column(name = "BILL_DESK_RES_ID", columnDefinition = "serial", updatable = false)
	private Long billDeskId;

	@Column(name = "MERCID")
	private String mercid;
	
	@Column(name = "TRANSACTION_DATE")
	private String transactionDate;
	
	@Column(name = "SURCHARGE")
	private String surcharge;
	
	@Column(name = "PAYMENT_METHOD_TYPE")
	private String paymentMethodType;
	
	@Column(name = "AMOUNT")
	private String amount;
	
	@Column(name = "RU")
	private String ru;
	
	@Column(name = "ORDER_ID")
	private String orderid;
	
	@Column(name = "TRANSACTION_ERROR_TYPE")
	private String transactionType;
	
	@Column(name = "BANK_REF_NO")
	private String bankRefNo;
	
	@Column(name = "TRANSACTION_ID")
	private String tranId;
	
	@Column(name = "TXN_PROCESS_TYPE")
	private String tranProcessType;
	
	@Column(name = "BANK_ID")
	private String bankId;
	
	@Column(name = "ADDITIONAL_INFO1")
	private String additionalInfo;
	
	@Column(name = "TRANSACTION_ERROR_CODE")
	private String tranErrorCode;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "AUTH_STATUS")
	private String auth_status;
	
	@Column(name = "TRANSACTION_ERROR_DESC")
	private String tranErrorDesc;
	
	@Column(name = "OBJECT_ID")
	private String objectId;
	
	@Column(name = "CHARGE_AMOUNT")
	private String chargeAmount;
	
	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;
	
	@Column(name = "CONSUMER_NAME")
	private String consumerName;
	
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	
	
//	PAYMENT REFUND CODE 
	
	// Refund
    @Column(name = "REFUND_ID")
    private String refundId;

    @Column(name = "REFUND_AMOUNT")
    private String refundAmount;

    @Column(name = "TXN_AMOUNT")
    private String txnAmount;

    @Column(name = "REFUND_DATE")
    private String refundDate;

    @Column(name = "MERC_REFUND_REF_NO")
    private String mercRefundRefNo;

    @Column(name = "REFUND_STATUS")
    private String refundStatus;
    

    @Column(name ="REGESTRATION_CGST")
    private String registrationCgst;
    
    @Column(name ="REGESTRATION_SGST")
    private String registrationSgst;
    
    @Column(name ="SETTLEMENT_DATE")
    private String settlementDate;
    
    @Column(name= "UTR_NO" )
    private String utrNo;
    
    
    @Column(name= "OYT_TEMP_AMOUNT" )
    private String oytTempAmount;
    
    
    
    
    
    
	public String getOytTempAmount() {
		return oytTempAmount;
	}

	public void setOytTempAmount(String oytTempAmount) {
		this.oytTempAmount = oytTempAmount;
	}

	public String getUtrNo() {
		return utrNo;
	}

	public void setUtrNo(String utrNo) {
		this.utrNo = utrNo;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getRegistrationCgst() {
		return registrationCgst;
	}

	public void setRegistrationCgst(String registrationCgst) {
		this.registrationCgst = registrationCgst;
	}

	public String getRegistrationSgst() {
		return registrationSgst;
	}

	public void setRegistrationSgst(String registrationSgst) {
		this.registrationSgst = registrationSgst;
	}

	public Long getBillDeskId() {
		return billDeskId;
	}

	public void setBillDeskId(Long billDeskId) {
		this.billDeskId = billDeskId;
	}

	public String getMercid() {
		return mercid;
	}

	public void setMercid(String mercid) {
		this.mercid = mercid;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public String getPaymentMethodType() {
		return paymentMethodType;
	}

	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public String getTranProcessType() {
		return tranProcessType;
	}

	public void setTranProcessType(String tranProcessType) {
		this.tranProcessType = tranProcessType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getTranErrorCode() {
		return tranErrorCode;
	}

	public void setTranErrorCode(String tranErrorCode) {
		this.tranErrorCode = tranErrorCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAuth_status() {
		return auth_status;
	}

	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
	}

	public String getTranErrorDesc() {
		return tranErrorDesc;
	}

	public void setTranErrorDesc(String tranErrorDesc) {
		this.tranErrorDesc = tranErrorDesc;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getMercRefundRefNo() {
		return mercRefundRefNo;
	}

	public void setMercRefundRefNo(String mercRefundRefNo) {
		this.mercRefundRefNo = mercRefundRefNo;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BillDeskPaymentResponse [billDeskId=" + billDeskId + ", mercid=" + mercid + ", transactionDate="
				+ transactionDate + ", surcharge=" + surcharge + ", paymentMethodType=" + paymentMethodType
				+ ", amount=" + amount + ", ru=" + ru + ", orderid=" + orderid + ", transactionType=" + transactionType
				+ ", bankRefNo=" + bankRefNo + ", tranId=" + tranId + ", tranProcessType=" + tranProcessType
				+ ", bankId=" + bankId + ", additionalInfo=" + additionalInfo + ", tranErrorCode=" + tranErrorCode
				+ ", currency=" + currency + ", auth_status=" + auth_status + ", tranErrorDesc=" + tranErrorDesc
				+ ", objectId=" + objectId + ", chargeAmount=" + chargeAmount + ", consumerApplicationNo="
				+ consumerApplicationNo + ", consumerName=" + consumerName + ", mobileNo=" + mobileNo + ", refundId="
				+ refundId + ", refundAmount=" + refundAmount + ", txnAmount=" + txnAmount + ", refundDate="
				+ refundDate + ", mercRefundRefNo=" + mercRefundRefNo + ", refundStatus=" + refundStatus
				+ ", registrationCgst=" + registrationCgst + ", registrationSgst=" + registrationSgst + "]";
	}
	
		
}
