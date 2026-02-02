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
//import com.mpcz.deposit_scheme.backend.api.domain.Auditable;
//
//import lombok.Data;
//
//@Data
//@Entity
////@NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i")
//@Table(name = "INVOICE1")
//@SequenceGenerator(name = "INVOICE_SEQ", sequenceName = "INVOICE_SEQ", allocationSize = 1)
//public class Invoice1 extends Auditable<Long> {
//	
//	
//private static final long serialVersionUID = 1L;
//	
//	@Id
//	@SequenceGenerator(name = "INVOICE_SEQ", sequenceName = "INVOICE_SEQ", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVOICE_SEQ")
//	@Column(name = "ID", columnDefinition = "serial", updatable = false)
//	private Long id;
//	
//
//	@Column( name = "CONSUMER_ID")
//	private Long consumerId ;
//	
//	@Column(name = "PAYMENT_TYPE")
//	private String paymentType;
//	
//	
//
//	@Column(name = "CONSUMER_APPLICATION_NO")
//	private String consumerApplicatinoNumber;
//	
//	
//	@Column(name="CONSUMER_NAME")
//	private String consumerName;
//	
//	@Column(name = "DATE_OF_PAYMENT")
//	private Date dateOfPayment;
//	
//	@Column(name = "TRANSACTION_NO")
//	private String tranasactionNo;
//	
//	@Column(name = "PAYMENT_STATUS")
//	private Integer paymentStatus;
//
//	@Column(name = "AMOUNT")
//	private Double totalAmount;
//
//
//	@Column(name = "TYPE_OF_INVOICE")
//	private String typeOfInvoice;
//	
//	@Column(name = "REMARK")
//	private String remark;
//
//	@Column(name = "INVOICE_ID")
//	private String invoiceId;
//	
//	@Column(name = "MOBILE_NUMBER")
//	private String mobileNumber;
//	
//	@Column(name = "Email")
//	private String email;
//	
//	@Column(name = "PAY_BY")
//	private String payBy;
//	
//	@Column(name = "APP_MODE")
//	private String AppMode;
//	
//	@Column(name = "PAYEE_ID")
//	private String payeeId;
//	
//
//	
//}