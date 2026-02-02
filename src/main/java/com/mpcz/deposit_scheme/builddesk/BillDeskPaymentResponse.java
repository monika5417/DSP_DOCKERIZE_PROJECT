//package com.mpcz.deposit_scheme.builddesk;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import lombok.Data;
//
///**
// * 
// * @author Amit
// *
// * 20-Aug-2020
// */
//@Data
//@Entity
//@Table(name="BILLDESK_PAYMENT_RESPONSE")
//@SequenceGenerator(name="billdesk_payment_res_seq_gen", sequenceName="BILLDESK_PAYMENT_RESPONSE_SEQ" , allocationSize = 1)
//public class BillDeskPaymentResponse {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billdesk_payment_res_seq_gen")
//	@Column(name = "BILLDESK_PAYMENT_RESPONSE_ID")
//	private Long billdeskPaymentResponseId;
//	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "CREATED")
//	private Date created;
//	
//	@Column(name = "CREATED_BY")
//	private String createdBy;
//	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "UPDATED")
//	private Date updated;
//	
//	@Column(name = "UPDATED_BY")
//	private String updatedBy;
//	
//	@Column(name = "IS_ACTIVE")
//	private String isActive;
//	
//	@Column(name = "MERCHANT_ID")
//	private String merchantId;
//	
//	@Column(name = "CUSTOMER_ID")
//	private String customerId;
//	
//	@Column(name = "TXN_REFERENCE_NO")
//	private String txnReferenceNo;
//	
//	@Column(name = "BANK_REFERENCE_NO")
//	private String BankReferenceNo;
//	
//	@Column(name = "TXN_AMOUNT")
//	private BigDecimal txnAmount;
//	
//	@Column(name = "BANKID")
//	private String bankid;	
//	
//	@Column(name = "BANK_MERCHANT_ID")
//	private String bankMerchantId;
//	
//	@Column(name = "TXN_TYPE")
//	private String txnType;
//	
//	@Column(name = "CURRENCY_NAME")
//	private String currencyName;
//	
//	@Column(name = "ITEM_CODE")
//	private String itemCode;
//	
//	@Column(name = "SECURITY_TYPE")
//	private String securityType;
//	
//	@Column(name = "SECURITY_ID")
//	private String securityId;	
//	
//	@Column(name = "SECURITY_PASSWORD")
//	private String securityPassword;
//	
//	@Column(name = "TXN_DATE")
//	private String txnDate;
//	
//	@Column(name = "AUTH_STATUS")
//	private String authStatus;
//	
//	@Column(name = "SETTLEMENT_TYPE")
//	private String settlementType;
//	
//	@Column(name = "ADDITIONAL_INFO1")
//	private String additionalInfo1;
//	
//	@Column(name = "ADDITIONAL_INFO2")
//	private String additionalInfo2;
//	
//	@Column(name = "ADDITIONAL_INFO3")
//	private String additionalInfo3;
//	
//	@Column(name = "ADDITIONAL_INFO4")
//	private String additionalInfo4;
//	
//	@Column(name = "ADDITIONAL_INFO5")
//	private String additionalInfo5;
//	
//	@Column(name = "ADDITIONAL_INFO6")
//	private String additionalInfo6;
//	
//	@Column(name = "ADDITIONAL_INFO7")
//	private String additionalInfo7;
//	
//	@Column(name = "ERROR_STATUS")
//	private String errorStatus;
//	
//	@Column(name = "ERROR_DESCRIPTION")
//	private String errorDescription;
//	
//	@Column(name = "CHECKSUM")
//	private String checksum;
//
//}
//
