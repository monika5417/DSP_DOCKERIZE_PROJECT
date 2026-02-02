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

import lombok.Data;

@Data
@Entity(name = "BillDeskPaymentRequest1")
@Table(name = "BILLDESK_PAYMENT_REQUEST")
public class BillDeskPaymentRequest1 {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BILLDESK_PAYMENT_REQUEST_SEQ", sequenceName = "BILLDESK_PAYMENT_REQUEST_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILLDESK_PAYMENT_REQUEST_SEQ")
	@Column(name = "BILLDESK_PAYMENT_REQUEST_ID", columnDefinition = "serial", updatable = false)
	private Long billdeskPaymentRequestId;

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED")
	private Date created;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public BigDecimal getTxnAmount() {
		return txnAmount;
	}

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED")
	private Date updated;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "MERCHANT_ID")
	private String merchantId;

	@Column(name = "CUSTOMER_ID")
	private String customerId;

	@Column(name = "FILLER1")
	private String filler1;

	@Column(name = "TXN_AMOUNT")
	private BigDecimal txnAmount;

	@Column(name = "BANKID")
	private String bankid;

	@Column(name = "FILLER2")
	private String filler2;

	@Column(name = "FILLER3")
	private String filler3;

	@Column(name = "CURRENCY_TYPE")
	private String currencyType;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "TYPE_FIELD_1")
	private String typeField1;

	@Column(name = "SECURITY_ID")
	private String securityId;

	@Column(name = "FILLER4")
	private String filler4;

	@Column(name = "FILLER5")
	private String filler5;

	@Column(name = "TYPE_FIELD2")
	private String typeField2;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "ADDITIONAL_INFO2")
	private String additionalInfo2;

	@Column(name = "ADDITIONAL_INFO3")
	private String additionalInfo3;

	@Column(name = "ADDITIONAL_INFO4")
	private String tokenId;

	@Column(name = "ADDITIONAL_INFO5")
	private String additionalInfo5;

	@Column(name = "ADDITIONAL_INFO6")
	private String additionalInfo6;

	@Column(name = "ADDITIONAL_INFO7")
	private String additionalInfo7;

	@Column(name = "RU")
	private String ru;

	@Column(name = "CHECKSUM")
	private String checksum;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	@Column(name = "CONSUMER_EMAILID")
	private String emailId;

	@Column(name = "CONSUMER_MOBILENO")
	private String consumerMobileNo;

	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerAppliNo;

    @Column(name ="PAYMNENT_UPDATE_DATE")
    private String paymentUpdateDate;
    
    
    
    
    
	public String getPaymentUpdateDate() {
		return paymentUpdateDate;
	}


	public void setPaymentUpdateDate(String paymentUpdateDate) {
		this.paymentUpdateDate = paymentUpdateDate;
	}


	public Long getBilldeskPaymentRequestId() {
		return billdeskPaymentRequestId;
	}


	public void setBilldeskPaymentRequestId(Long billdeskPaymentRequestId) {
		this.billdeskPaymentRequestId = billdeskPaymentRequestId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFiller1() {
		return filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}



	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getFiller2() {
		return filler2;
	}

	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}

	public String getFiller3() {
		return filler3;
	}

	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getTypeField1() {
		return typeField1;
	}

	public void setTypeField1(String typeField1) {
		this.typeField1 = typeField1;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getFiller4() {
		return filler4;
	}

	public void setFiller4(String filler4) {
		this.filler4 = filler4;
	}

	public String getFiller5() {
		return filler5;
	}

	public void setFiller5(String filler5) {
		this.filler5 = filler5;
	}

	public String getTypeField2() {
		return typeField2;
	}

	public void setTypeField2(String typeField2) {
		this.typeField2 = typeField2;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAdditionalInfo2() {
		return additionalInfo2;
	}

	public void setAdditionalInfo2(String additionalInfo2) {
		this.additionalInfo2 = additionalInfo2;
	}

	public String getAdditionalInfo3() {
		return additionalInfo3;
	}

	public void setAdditionalInfo3(String additionalInfo3) {
		this.additionalInfo3 = additionalInfo3;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getAdditionalInfo5() {
		return additionalInfo5;
	}

	public void setAdditionalInfo5(String additionalInfo5) {
		this.additionalInfo5 = additionalInfo5;
	}

	public String getAdditionalInfo6() {
		return additionalInfo6;
	}

	public void setAdditionalInfo6(String additionalInfo6) {
		this.additionalInfo6 = additionalInfo6;
	}

	public String getAdditionalInfo7() {
		return additionalInfo7;
	}

	public void setAdditionalInfo7(String additionalInfo7) {
		this.additionalInfo7 = additionalInfo7;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}

	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}

	public String getConsumerAppliNo() {
		return consumerAppliNo;
	}

	public void setConsumerAppliNo(String consumerAppliNo) {
		this.consumerAppliNo = consumerAppliNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
