package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;
import java.sql.Timestamp;
/*
 * Author:- Saurabh Kumar Gupta
 * Date:- 25-08-2022
 * 
 */
public class InvoiceReportDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String consumerName;
	private String applicationNo;
	private String invoiceId;
	private String transactionNo;
	private String typeOfInvoice;
	private String paymentType;
	private String amount;
	private Timestamp dateOfPayment;
	private String responseMessage;
	private String status;
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(Timestamp dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public String getTypeOfInvoice() {
		return typeOfInvoice;
	}
	public void setTypeOfInvoice(String typeOfInvoice) {
		this.typeOfInvoice = typeOfInvoice;
	}
	
	
}
