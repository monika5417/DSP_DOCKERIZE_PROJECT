package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.mpcz.services.domain.ConsumerLogin;

import lombok.Data;

/**
 * 
 * The persistent class for the INVOICE database table.
 * 
 * @author Amit Pateriya
 * @date 2019-01-03
 * @version 1.0
 */   
@Data
@Entity
//@NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i")
@Table(name = "INVOICE")
@SequenceGenerator(name = "INVOICE_SEQ", sequenceName = "INVOICE_SEQ", allocationSize = 1)
public class Invoice extends Auditable<Long> {
	
	
private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "INVOICE_SEQ", sequenceName = "INVOICE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVOICE_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	

	@Column( name = "CONSUMER_ID")
	private Long consumerId ;
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	

	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicatinoNumber;
	
	
	@Column(name="CONSUMER_NAME")
	private String consumerName;
	
	@Column(name = "DATE_OF_PAYMENT")
	private Date dateOfPayment;
	
	@Column(name = "TRANSACTION_NO")
	private String tranasactionNo;
	
	@Column(name = "PAYMENT_STATUS")
	private Integer paymentStatus;

	@Column(name = "AMOUNT")
	private Double totalAmount;


	@Column(name = "TYPE_OF_INVOICE")
	private String typeOfInvoice;
	
	@Column(name = "REMARK")
	private String remark;

	@Column(name = "INVOICE_ID")
	private String invoiceId;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "PAY_BY")
	private String payBy;
	
	@Column(name = "APP_MODE")
	private String AppMode;
	
	@Column(name = "PAYEE_ID")
	private String payeeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getConsumerApplicatinoNumber() {
		return consumerApplicatinoNumber;
	}

	public void setConsumerApplicatinoNumber(String consumerApplicatinoNumber) {
		this.consumerApplicatinoNumber = consumerApplicatinoNumber;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public Date getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public String getTranasactionNo() {
		return tranasactionNo;
	}

	public void setTranasactionNo(String tranasactionNo) {
		this.tranasactionNo = tranasactionNo;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTypeOfInvoice() {
		return typeOfInvoice;
	}

	public void setTypeOfInvoice(String typeOfInvoice) {
		this.typeOfInvoice = typeOfInvoice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPayBy() {
		return payBy;
	}

	public void setPayBy(String payBy) {
		this.payBy = payBy;
	}

	public String getAppMode() {
		return AppMode;
	}

	public void setAppMode(String appMode) {
		AppMode = appMode;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

	
}