package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "ContractorDetail")
@Table(name = "CONTRACTOR_DETAIL")
public @Data class ContractorDetail extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTRACTOR_DETAIL_SEQ", sequenceName = "CONTRACTOR_DETAIL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_DETAIL_SEQ")
	@Column(name = "CONTRACTOR_ID", columnDefinition = "serial", updatable = false)
	private Long contractorId;

	@Column(name = "USER_ID")
	public Long contractorUserId;

	@Column(name = "AUTHENTICATION_ID")
	public String contractorAuthenticationId;

	@Column(name = "USER_ZONE")
	public String contractorUserZone;

	@Column(name = "USER_CLASS")
	public String contractorUserClass;

	@Column(name = "COMPANY_NAME")
	public String contractorCompanyName;

	@Column(name = "MOBILE")
	public String contractorMobile;

	@Column(name = "EMAIL")
	public String contractorEmail;

	@Column(name = "ADDRESS")
	public String contractorAddress;

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public Long getContractorUserId() {
		return contractorUserId;
	}

	public void setContractorUserId(Long contractorUserId) {
		this.contractorUserId = contractorUserId;
	}

	public String getContractorAuthenticationId() {
		return contractorAuthenticationId;
	}

	public void setContractorAuthenticationId(String contractorAuthenticationId) {
		this.contractorAuthenticationId = contractorAuthenticationId;
	}

	public String getContractorUserZone() {
		return contractorUserZone;
	}

	public void setContractorUserZone(String contractorUserZone) {
		this.contractorUserZone = contractorUserZone;
	}

	public String getContractorUserClass() {
		return contractorUserClass;
	}

	public void setContractorUserClass(String contractorUserClass) {
		this.contractorUserClass = contractorUserClass;
	}

	public String getContractorCompanyName() {
		return contractorCompanyName;
	}

	public void setContractorCompanyName(String contractorCompanyName) {
		this.contractorCompanyName = contractorCompanyName;
	}

	public String getContractorMobile() {
		return contractorMobile;
	}

	public void setContractorMobile(String contractorMobile) {
		this.contractorMobile = contractorMobile;
	}

	public String getContractorEmail() {
		return contractorEmail;
	}

	public void setContractorEmail(String contractorEmail) {
		this.contractorEmail = contractorEmail;
	}

	public String getContractorAddress() {
		return contractorAddress;
	}

	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
