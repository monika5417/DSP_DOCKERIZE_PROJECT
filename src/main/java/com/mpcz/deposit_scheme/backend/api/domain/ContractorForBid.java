package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "ContractorForBid")
@Table(name = "CONTRACTOR_FOR_BID")
public @Data class ContractorForBid  {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTRACTOR_FOR_BID_S", sequenceName = "CONTRACTOR_FOR_BID_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_FOR_BID_S")
	@Column(name = "CONTRACTOR_ID", columnDefinition = "serial", updatable = false)
	private Long contractorId;


	@Column(name = "BID_ORDER_AT")
	private String bidOrderAt;
	
	@Column(name = "BID_AMOUNT")
	private String bidAmount;
	
	@Column(name = "CONTRACTOR_NAME")
	private String contractorName;
	
	@Column(name = "CONTRACTOR_STATE")
	private String companyState;
	
	
	@Column(name = "COMPANY_CITY")
	private String companyCity;
	
	
	@Column(name = "COMPANY_PINCODE")
	private String companyPinCode;
	
	@Column(name = "COMPANY_ADD2")
	private String companyadd2;
	
	@Column(name = "CONTRACTOR_CATEGORY")
	private String contractorCategory;
	
	
	@Column(name = "CONSUMER_APPLICATION_NUMBER")
	private String consumerApplicationNo;

	@Column(name="CONTACT_NO")
	private String contactNo;
	
	@Column(name="CONTRACTOR_AUTHANTICATION_ID")
	private String contractorAuthaticationId;
	
	
	
	

	public String getContractorAuthaticationId() {
		return contractorAuthaticationId;
	}


	public void setContractorAuthaticationId(String contractorAuthaticationId) {
		this.contractorAuthaticationId = contractorAuthaticationId;
	}


	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public Long getContractorId() {
		return contractorId;
	}


	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}


	public String getBidOrderAt() {
		return bidOrderAt;
	}


	public void setBidOrderAt(String bidOrderAt) {
		this.bidOrderAt = bidOrderAt;
	}


	public String getBidAmount() {
		return bidAmount;
	}


	public void setBidAmount(String bidAmount) {
		this.bidAmount = bidAmount;
	}


	public String getContractorName() {
		return contractorName;
	}


	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}


	public String getCompanyState() {
		return companyState;
	}


	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}


	public String getCompanyCity() {
		return companyCity;
	}


	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}


	public String getCompanyPinCode() {
		return companyPinCode;
	}


	public void setCompanyPinCode(String companyPinCode) {
		this.companyPinCode = companyPinCode;
	}


	public String getCompanyadd2() {
		return companyadd2;
	}


	public void setCompanyadd2(String companyadd2) {
		this.companyadd2 = companyadd2;
	}


	public String getContractorCategory() {
		return contractorCategory;
	}


	public void setContractorCategory(String contractorCategory) {
		this.contractorCategory = contractorCategory;
	}


	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}


	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

	
	
	
	
}
