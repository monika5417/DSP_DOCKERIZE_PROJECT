package com.mpcz.deposit_scheme.backend.api.builddesk;
//package com.mpcz.deposit_scheme.builddesk;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * 
// * @author Amit
// *
// * 20-Aug-2020
// */
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name="BILLDESK_PAYMENT_REQUEST")
//public class BillDeskPaymentRequest {
//	@Id
//	@SequenceGenerator(name = "BILLDESK_PAYMENT_REQUEST_SEQ", sequenceName = "BILLDESK_PAYMENT_REQUEST_SEQ", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILLDESK_PAYMENT_REQUEST_SEQ")
//	@Column(name = "BILLDESK_PAYMENT_REQUEST_ID")
//	private Long billdeskPaymentRequestId;
//	
//	//@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "CREATED")
//	private Date created;
//	
//	@Column(name = "CREATED_BY")
//	private String createdBy;
//	
//	//@Temporal(TemporalType.TIMESTAMP)
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
//	@Column(name = "FILLER1")
//	private String filler1;
//	
//	@Column(name = "TXN_AMOUNT")
//	private Double txnAmount;
//	
//	@Column(name = "BANKID")
//	private String bankid;
//	
//	@Column(name = "FILLER2")
//	private String filler2;
//	
//	@Column(name = "FILLER3")
//	private String filler3;
//	
//	@Column(name = "CURRENCY_TYPE")
//	private String currencyType;
//	
//	@Column(name = "ITEM_CODE")
//	private String itemCode;
//	
//	@Column(name = "TYPE_FIELD_1")
//	private String typeField1;
//	
//	@Column(name = "SECURITY_ID")
//	private String securityId;
//	
//	@Column(name = "FILLER4")
//	private String filler4;
//	
//	@Column(name = "FILLER5")
//	private String filler5;
//	
//	@Column(name = "TYPE_FIELD2")
//	private String typeField2;
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
//	@Column(name = "RU")
//	private String ru;
//	
//	@Column(name = "CHECKSUM")
//	private String checksum;
//
//}
//
