package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MANUAL_PAYMENT") 
public class ManualPayment extends Auditable<Long>{

    @Id
    @SequenceGenerator(name = "MANUAL_PAYMENT_SEQ", sequenceName = "MANUAL_PAYMENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MANUAL_PAYMENT_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
    private Long id;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "AMOUNT")
    private String amount;

    //PFMS TXN ID
    @Column(name = "BILL_REF_NO")
    private String billRefNo;

    @Column(name = "CON_APP_NO")
    private String conAppNo;

    @Column(name = "PARTY_CODE")
    private String partyCode;

    @Column(name = "PAYMENT_DATE")
    private String paymentDate;

    @Column(name = "PAYMENT_MODE")
    private String paymentMode;
    
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    
    //DATE WHEN APPLICATION COMES TO DSP PORTAL
    @Column(name="VERIFICATION_DATE")
    private String verificationDate; 
    
    @Column(name="ORG_NAME")
    private String orgName;
    
    //NAME OF ACCOUNT HOLDER RELATED TO ACCOUNT
    @Column(name="BENIFICIARY_NAME")
    private String benificiaryName;
    
    @Column(name="PAYMENT_TYPE")
    private String paymentType;
    
    @Column(name="AUTH_STATUS")
    private String authStatus;
    
    @Transient
    private String consumerName;

    
    
	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBillRefNo() {
		return billRefNo;
	}

	public void setBillRefNo(String billRefNo) {
		this.billRefNo = billRefNo;
	}

	public String getConAppNo() {
		return conAppNo;
	}

	public void setConAppNo(String conAppNo) {
		this.conAppNo = conAppNo;
	}

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBenificiaryName() {
		return benificiaryName;
	}

	public void setBenificiaryName(String benificiaryName) {
		this.benificiaryName = benificiaryName;
	}
	
	

    
}
