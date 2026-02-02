package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="MKY_PAY_AMNT")
public class MmkyPayAmount {
	
	@Id
	@SequenceGenerator(name = "MKY_PAY_AMNT_SEQ", sequenceName = "MKY_PAY_AMNT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MKY_PAY_AMNT_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
	@Column(name="CON_APP_NUM")
	private String consumerApplicationNumber;
	
	@Column(name="ERP_NUM")
	private String ErpNumber;
	
	@Column(name="TOTAL_AMOUNT")
	private BigDecimal totalAmount;
	
	@Column(name="GOV_MAF_BILL")
	private BigDecimal GovMafBill;
	
	@Column(name="MPMK_MAF_BILL")
	private BigDecimal MpmkMafBill;
	
	@Column(name="PAYALE_AMOUNT")
	private BigDecimal payableAmount;
	
	@Column(name="SCHEME_CODE")
	private String SchemeCode; 
	
	@Column(name="ABEDAN_SULK")
	private BigDecimal avedanShulk; 
	
	@Column(name="SECURITY_DEPOSIT")
	private BigDecimal securityDeposit;
	
	@Column(name="CARRY_AMOUNT_BY_APPLICANT")
	private BigDecimal carryAmountByApplicant;
	
	@Column(name="CREATED")
	private String created;
	
	@Column(name = "IS_ACTIVE")
	protected boolean isActive;

	@Column(name = "IS_DELETED")
	private boolean isDeleted ;
	
	@Column(name = "MSG_SEND")
	private String msgSend;

	@Column(name = "EST_SANCTION_NO")
	private String estSanctionNo;
	
	@Column(name = "EST_NAME")
	private String estName;
	
	@Column(name = "EST_APPROVED_BY")
	private String estApprovedBy;
	
	@Column(name="AVEDAN_SHULK_FIVE_RUPEE")
	private BigDecimal avedanShulkFiveRupee; 

	
	
	public BigDecimal getAvedanShulkFiveRupee() {
		return avedanShulkFiveRupee;
	}

	public void setAvedanShulkFiveRupee(BigDecimal avedanShulkFiveRupee) {
		this.avedanShulkFiveRupee = avedanShulkFiveRupee;
	}

	public String getEstSanctionNo() {
		return estSanctionNo;
	}

	public void setEstSanctionNo(String estSanctionNo) {
		this.estSanctionNo = estSanctionNo;
	}

	public String getEstName() {
		return estName;
	}

	public void setEstName(String estName) {
		this.estName = estName;
	}

	public String getEstApprovedBy() {
		return estApprovedBy;
	}

	public void setEstApprovedBy(String estApprovedBy) {
		this.estApprovedBy = estApprovedBy;
	}

	public String getMsgSend() {
		return msgSend;
	}

	public void setMsgSend(String msgSend) {
		this.msgSend = msgSend;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public BigDecimal getCarryAmountByApplicant() {
		return carryAmountByApplicant;
	}

	public void setCarryAmountByApplicant(BigDecimal carryAmountByApplicant) {
		this.carryAmountByApplicant = carryAmountByApplicant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getErpNumber() {
		return ErpNumber;
	}

	public void setErpNumber(String erpNumber) {
		ErpNumber = erpNumber;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getGovMafBill() {
		return GovMafBill;
	}

	public void setGovMafBill(BigDecimal govMafBill) {
		GovMafBill = govMafBill;
	}

	public BigDecimal getMpmkMafBill() {
		return MpmkMafBill;
	}

	public void setMpmkMafBill(BigDecimal mpmkMafBill) {
		MpmkMafBill = mpmkMafBill;
	}

	public BigDecimal getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getSchemeCode() {
		return SchemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		SchemeCode = schemeCode;
	}

	public BigDecimal getAvedanShulk() {
		return avedanShulk;
	}

	public void setAvedanShulk(BigDecimal avedanShulk) {
		this.avedanShulk = avedanShulk;
	}

	public BigDecimal getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(BigDecimal securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	

}
