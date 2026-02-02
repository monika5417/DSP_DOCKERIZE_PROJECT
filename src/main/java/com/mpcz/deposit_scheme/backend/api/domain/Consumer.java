package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity(name = "Consumer")
@Table(name = "CONSUMER")
public @Data class Consumer extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONSUMER_SEQ", sequenceName = "CONSUMER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_SEQ")
	@Column(name = "CONSUMER_ID", columnDefinition = "serial", updatable = false)
	private Long consumerId;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	@Column(name = "CONSUMER_EMAIL_ID")
	private String consumerEmailId;

	@Column(name = "CONSUMER_MOBILE_NO")
	private String consumerMobileNo;
	

	@Column(name = "IVRS_NO")
	private String ivrsNo;

	@OneToOne
	@JoinColumn(name = "DOC_ELECTRICITY_BILL_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docElectricityBill;

	@JsonIgnore
	@Column(name = "CONSUMER_CREDENTIALS")
	private String consumerCredentials;

	@Column(name = "ACCOUNT_NON_EXPIRED")
	private Boolean accountNonExpired;

	@Column(name = "ACCOUNT_NON_LOCKED")
	private Boolean accountNonLocked;

	@JsonIgnore
	@Column(name = "CREDENTIALS_NON_EXPIRED")
	private Boolean credentialsNonExpired;

	@Column(name = "IS_FIRST_LOGIN")
	private Boolean isFirstLogin;

	@Column(name = "CONSUMER_LOGIN_ID")
	private String consumerLoginId;

	@Column(name = "LOGIN_ATTEMP")
	private Long loginAttemp;

	@JsonIgnore
	@Column(name = "LOGIN_STATUS")
	private String loginStatus;

	@ManyToOne
	@JoinColumn(name = "CONSUMER_TYPE_ID", referencedColumnName = "CONSUMER_TYPE_ID")
	private ConsumerType consumerType;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOGGED_IN_DATE")
	private Date lastLoggedInDate;

	@Column(name = "ADDRESS")
	private String address;

	@OneToOne
	@JoinColumn(name = "DOC_RESIDENTIAL_PROOF_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docResidentialProof;

	@Column(name = "AADHAR_NO")
	private String aadharNo;

	@OneToOne
	@JoinColumn(name = "DOC_AADHAR_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docAadhar;

	@Column(name = "PAN_NO")
	private String panNo;

	@OneToOne
	@JoinColumn(name = "DOC_PAN_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docPan;
	
	@Transient
	private String dspApplicationNo;
	
	@Transient
	private String sspApplicationNo;
	
	

	public String getDspApplicationNo() {
		return dspApplicationNo;
	}

	public void setDspApplicationNo(String dspApplicationNo) {
		this.dspApplicationNo = dspApplicationNo;
	}

	public String getSspApplicationNo() {
		return sspApplicationNo;
	}

	public void setSspApplicationNo(String sspApplicationNo) {
		this.sspApplicationNo = sspApplicationNo;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerEmailId() {
		return consumerEmailId;
	}

	public void setConsumerEmailId(String consumerEmailId) {
		this.consumerEmailId = consumerEmailId;
	}

	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}

	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}

	public String getIvrsNo() {
		return ivrsNo;
	}

	public void setIvrsNo(String ivrsNo) {
		this.ivrsNo = ivrsNo;
	}

	public Upload getDocElectricityBill() {
		return docElectricityBill;
	}

	public void setDocElectricityBill(Upload docElectricityBill) {
		this.docElectricityBill = docElectricityBill;
	}

	public String getConsumerCredentials() {
		return consumerCredentials;
	}

	public void setConsumerCredentials(String consumerCredentials) {
		this.consumerCredentials = consumerCredentials;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public String getConsumerLoginId() {
		return consumerLoginId;
	}

	public void setConsumerLoginId(String consumerLoginId) {
		this.consumerLoginId = consumerLoginId;
	}

	public Long getLoginAttemp() {
		return loginAttemp;
	}

	public void setLoginAttemp(Long loginAttemp) {
		this.loginAttemp = loginAttemp;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public ConsumerType getConsumerType() {
		return consumerType;
	}

	public void setConsumerType(ConsumerType consumerType) {
		this.consumerType = consumerType;
	}

	public Date getLastLoggedInDate() {
		return lastLoggedInDate;
	}

	public void setLastLoggedInDate(Date lastLoggedInDate) {
		this.lastLoggedInDate = lastLoggedInDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Upload getDocResidentialProof() {
		return docResidentialProof;
	}

	public void setDocResidentialProof(Upload docResidentialProof) {
		this.docResidentialProof = docResidentialProof;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public Upload getDocAadhar() {
		return docAadhar;
	}

	public void setDocAadhar(Upload docAadhar) {
		this.docAadhar = docAadhar;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public Upload getDocPan() {
		return docPan;
	}

	public void setDocPan(Upload docPan) {
		this.docPan = docPan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
