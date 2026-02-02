package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DUPLICATE_REFUND")
public class DuplicateRefund extends Auditable<Long> {
	@Id
	@SequenceGenerator(name = "DUPLICATE_REFUND_SEQ", sequenceName = "DUPLICATE_REFUND_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DUPLICATE_REFUND_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ACCOUNT_HOLDER_NAME")
	private String accountHolderName;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "ACCOUNT_NO")
	private String accountNo;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@Column(name = "PAN_NO")
	private String panNo;

	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;
	
	@Column(name="DGM_APPROVED_ID")
	private String dgmApprovedId; 
	
	@Column(name="DGM_NAME")
	private String dgmName;
	
	@Column(name="DGM_APPROAL")
	private String dgmApproval;
	
	@Column(name="DGM_APPROVED_DATE")
	private String dgmApprovedDate;
	
	@Column(name="GM_APPROVED_ID")
	private String gmApprovedId;
	
	@Column(name="GM_NAME")
	private String gmName;
	
	@Column(name="GM_APPROVAL")
	private String gmApproval;
	
	@Column(name="GM_APPROVED_DATE")
	private String gmApprovedDate;
	
	@Column(name ="FINANCE_ID")
	private String financeId;
	
	@Column(name="FINANCE_NAME")
	private String financeName;
	
	@Column(name="FINANCE_APPROVAL")
	private String financeApproval;
	
	@Column(name="FINANCE_APPROVED_DATE")
	private String financeApprovedDate;
	
	@Column(name="TRANSACTION_ID")
	private String transactionId;
	
	@Column(name="REFUND_AMOUNT")
	private BigDecimal refundAmount;
	
	@Column(name="REFUND_TYPE_REGISTRATION")
	private String refundTypeRegistration;

	@Column(name="REFUND_TYPE_DEMAND")
	private String refundTypeDemand;
	
	

	public String getRefundTypeRegistration() {
		return refundTypeRegistration;
	}

	public void setRefundTypeRegistration(String refundTypeRegistration) {
		this.refundTypeRegistration = refundTypeRegistration;
	}

	public String getRefundTypeDemand() {
		return refundTypeDemand;
	}

	public void setRefundTypeDemand(String refundTypeDemand) {
		this.refundTypeDemand = refundTypeDemand;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public String getDgmApprovedId() {
		return dgmApprovedId;
	}

	public void setDgmApprovedId(String dgmApprovedId) {
		this.dgmApprovedId = dgmApprovedId;
	}

	public String getDgmApproval() {
		return dgmApproval;
	}

	public void setDgmApproval(String dgmApproval) {
		this.dgmApproval = dgmApproval;
	}

	public String getGmApprovedId() {
		return gmApprovedId;
	}

	public void setGmApprovedId(String gmApprovedId) {
		this.gmApprovedId = gmApprovedId;
	}

	public String getGmApproval() {
		return gmApproval;
	}

	public void setGmApproval(String gmApproval) {
		this.gmApproval = gmApproval;
	}

	public String getFinanceId() {
		return financeId;
	}

	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}

	public String getFinanceApproval() {
		return financeApproval;
	}

	public void setFinanceApproval(String financeApproval) {
		this.financeApproval = financeApproval;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}



	public String getDgmName() {
		return dgmName;
	}

	public void setDgmName(String dgmName) {
		this.dgmName = dgmName;
	}

	public String getGmName() {
		return gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}

	public String getFinanceName() {
		return financeName;
	}

	public void setFinanceName(String financeName) {
		this.financeName = financeName;
	}

	public String getDgmApprovedDate() {
		return dgmApprovedDate;
	}

	public void setDgmApprovedDate(String dgmApprovedDate) {
		this.dgmApprovedDate = dgmApprovedDate;
	}

	public String getGmApprovedDate() {
		return gmApprovedDate;
	}

	public void setGmApprovedDate(String gmApprovedDate) {
		this.gmApprovedDate = gmApprovedDate;
	}

	public String getFinanceApprovedDate() {
		return financeApprovedDate;
	}

	public void setFinanceApprovedDate(String financeApprovedDate) {
		this.financeApprovedDate = financeApprovedDate;
	}
	
	
	
	
	
}
