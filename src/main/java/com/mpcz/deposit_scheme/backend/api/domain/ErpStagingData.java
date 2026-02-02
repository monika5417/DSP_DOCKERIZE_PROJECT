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

@Entity
@Table(name = "ERP_STAGING_DATA")
public class ErpStagingData {

	@Id
	@SequenceGenerator(name = "ERP_STAGING_DATA_SEQ", sequenceName = "ERP_STAGING_DATA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERP_STAGING_DATA_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
	@Column(name = "TRANSACTION_SOURCE")
	private String transactionSource;

	@Column(name = "OU")
	private String ou;

	@Column(name = "CIRCLE")
	private String circle;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "DC")
	private String dc;

	@Column(name = "CLASS")
	private String classType;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "REF_SALES_NO")
	private String refSalesNo;

	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;

	@Column(name = "GL_DATE")
	private Date glDate;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "CUST_NUMBER")
	private String custNumber;

	@Column(name = "PAYMENT_TERM")
	private String paymentTerm;

	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;

	@Column(name = "QUANTITY")
	private String quantity;

	@Column(name = "UNIT_PRICE")
	private BigDecimal unitPrice;

	@Column(name = "COM_LEGACY_SYS")
	private String comLegacySys;

	@Column(name = "GL_ACC_COMBI")
	private String glAccCombi;

	@Column(name = "TAX_RATE")
	private String taxRate;

	@Column(name = "TAX_CATEGORY")
	private String taxCategory;

	@Column(name = "REG_AMOUNT")
	private BigDecimal regAmount;

	@Column(name = "SUPE_CHARGES")
	private BigDecimal supeCharges;

	@Column(name = "SUPP_AFFO_CHARGES")
	private BigDecimal suppAffoCharges;

	@Column(name = "SYS_DEVE_CHARGES")
	private BigDecimal sysDeveCharges;

	@Column(name = "JE_RETURN_AMOUNT")
	private BigDecimal jeReturnAmount;

	@Column(name = "CGST")
	private BigDecimal cgst;

	@Column(name = "SGST")
	private BigDecimal sgst;

	@Column(name = "DEPOSIT")
	private BigDecimal deposit;

	@Column(name = "AVEDAN_SHULK_62")
	private BigDecimal avedanShulk62;

	@Column(name = "AVEDAN_SHULK_47")
	private BigDecimal avedanShulk47;

	@Column(name = "SECURITY_DEPOSIT")
	private BigDecimal securityDeposit;

	@Column(name = "PAID_BY_APPLICANT")
	private BigDecimal paidByApplicant;

	@Column(name = "DEPO_FOR_ELE_ILLEGAL")
	private String depoForEleIllegal;

	@Column(name = "TDS")
	private BigDecimal tds;

	@Column(name = "GST_NUMBER")
	private String gstNumber;

	@Column(name = "RECEIP_NUM")
	private String receipNum;

	@Column(name = "RECEIP_STATUS")
	private String receipStatus;

	@Column(name = "INVOICE_NUM")
	private String invoiceNum;

	@Column(name = "INVOICE_STATUS")
	private String invoiceStatus;

	@Column(name = "CUSTOMER_NUM")
	private String customerNum;

	@Column(name = "CUSTOMER_STATUS")
	private String customerStatus;

	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	@Column(name = "CREATED_BY")
	private BigDecimal createdBy;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "LAST_UPDATED_BY")
	private BigDecimal lastUpdatedBy;

	@Column(name = "LAST_UPDATE_LOGIN")
	private BigDecimal lastUpdateLogin;

	@Column(name = "STATUS")
	private String status = "PENDING";

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getRefSalesNo() {
		return refSalesNo;
	}

	public void setRefSalesNo(String refSalesNo) {
		this.refSalesNo = refSalesNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getGlDate() {
		return glDate;
	}

	public void setGlDate(Date glDate) {
		this.glDate = glDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getComLegacySys() {
		return comLegacySys;
	}

	public void setComLegacySys(String comLegacySys) {
		this.comLegacySys = comLegacySys;
	}

	public String getGlAccCombi() {
		return glAccCombi;
	}

	public void setGlAccCombi(String glAccCombi) {
		this.glAccCombi = glAccCombi;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(String taxCategory) {
		this.taxCategory = taxCategory;
	}

	public BigDecimal getRegAmount() {
		return regAmount;
	}

	public void setRegAmount(BigDecimal regAmount) {
		this.regAmount = regAmount;
	}

	public BigDecimal getSupeCharges() {
		return supeCharges;
	}

	public void setSupeCharges(BigDecimal supeCharges) {
		this.supeCharges = supeCharges;
	}

	public BigDecimal getSuppAffoCharges() {
		return suppAffoCharges;
	}

	public void setSuppAffoCharges(BigDecimal suppAffoCharges) {
		this.suppAffoCharges = suppAffoCharges;
	}

	public BigDecimal getSysDeveCharges() {
		return sysDeveCharges;
	}

	public void setSysDeveCharges(BigDecimal sysDeveCharges) {
		this.sysDeveCharges = sysDeveCharges;
	}

	public BigDecimal getJeReturnAmount() {
		return jeReturnAmount;
	}

	public void setJeReturnAmount(BigDecimal jeReturnAmount) {
		this.jeReturnAmount = jeReturnAmount;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getAvedanShulk62() {
		return avedanShulk62;
	}

	public void setAvedanShulk62(BigDecimal avedanShulk62) {
		this.avedanShulk62 = avedanShulk62;
	}

	public BigDecimal getAvedanShulk47() {
		return avedanShulk47;
	}

	public void setAvedanShulk47(BigDecimal avedanShulk47) {
		this.avedanShulk47 = avedanShulk47;
	}

	public BigDecimal getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(BigDecimal securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public BigDecimal getPaidByApplicant() {
		return paidByApplicant;
	}

	public void setPaidByApplicant(BigDecimal paidByApplicant) {
		this.paidByApplicant = paidByApplicant;
	}

	public String getDepoForEleIllegal() {
		return depoForEleIllegal;
	}

	public void setDepoForEleIllegal(String depoForEleIllegal) {
		this.depoForEleIllegal = depoForEleIllegal;
	}

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getReceipNum() {
		return receipNum;
	}

	public void setReceipNum(String receipNum) {
		this.receipNum = receipNum;
	}

	public String getReceipStatus() {
		return receipStatus;
	}

	public void setReceipStatus(String receipStatus) {
		this.receipStatus = receipStatus;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public BigDecimal getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(BigDecimal lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}