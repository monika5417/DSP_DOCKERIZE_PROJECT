package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="CONSUMER_ACCOUNT_DETAILS")
public class ConsumerAccountDetails extends Auditable<Long>{
	@Id
	@SequenceGenerator(name = "CONSUMER_ACCOUNT_DETAILS_SEQ", sequenceName = "CONSUMER_ACCOUNT_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_ACCOUNT_DETAILS_SEQ")
	@Column(name="ID")
	private Long id;
	
	@Column(name="ACCOUNT_HOLDER_NAME")
	private String accountHolderName;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="ACCOUNT_NO")
	private String accountNo;
	
	@Column(name="IFSC_CODE")
	private String ifscCode;
	
	@Column(name="PAN_NO")
	private String panNo;

	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;
	
	@Column(name = "ADDRESS_PROOF_NO")
	private String addressProofNo;
	
	
	
	public String getAddressProofNo() {
		return addressProofNo;
	}

	public void setAddressProofNo(String addressProofNo) {
		this.addressProofNo = addressProofNo;
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
	
	
	
	
}
