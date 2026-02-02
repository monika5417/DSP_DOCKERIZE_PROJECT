package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "contractors_details")
public class ContractorsDetails {

	@Id
	@SequenceGenerator(name = "CONTRACTORS_DETAILS_SEQ", sequenceName = "CONTRACTORS_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTORS_DETAILS_SEQ")
	@Column(name = "sr_no")
	private Long srNo;

	@Column(name = "consumer_application_no")
	private String consumerApplicationNo;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "authorised_person_e")
	private String authorisedPerson;

	@Column(name = "company_name_e")
	private String companyName;

	@Column(name = "user_type")
	private String userType;

	@Column(name = "authentication_id")
	private String authenticationId;

	@Column(name = "oyt")
	private String oyt;

	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "company_add_1")
	private String companyAddress1;

	@Column(name = "company_add_2")
	private String companyAddress2;

	@Column(name = "company_pin_code")
	private String companyPinCode;

	@Column(name = "company_t_dist")
	private String companyTDistrict;

	@Column(name = "company_t_city")
	private String companyTCity;

	@Column(name = "company_t_state")
	private String companyTState;
	
	@Column(name = "oyt_name")
	private String oytName;
	
	@Column(name = "creation_date")
	private String creationDate;

	public Long getSrNo() {
		return srNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAuthorisedPerson() {
		return authorisedPerson;
	}

	public void setAuthorisedPerson(String authorisedPerson) {
		this.authorisedPerson = authorisedPerson;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getOyt() {
		return oyt;
	}

	public void setOyt(String oyt) {
		this.oyt = oyt;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyAddress1() {
		return companyAddress1;
	}

	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}

	public String getCompanyAddress2() {
		return companyAddress2;
	}

	public void setCompanyAddress2(String companyAddress2) {
		this.companyAddress2 = companyAddress2;
	}

	public String getCompanyPinCode() {
		return companyPinCode;
	}

	public void setCompanyPinCode(String companyPinCode) {
		this.companyPinCode = companyPinCode;
	}

	public String getCompanyTDistrict() {
		return companyTDistrict;
	}

	public void setCompanyTDistrict(String companyTDistrict) {
		this.companyTDistrict = companyTDistrict;
	}

	public String getCompanyTCity() {
		return companyTCity;
	}

	public void setCompanyTCity(String companyTCity) {
		this.companyTCity = companyTCity;
	}

	public String getCompanyTState() {
		return companyTState;
	}

	public void setCompanyTState(String companyTState) {
		this.companyTState = companyTState;
	}

	public String getOytName() {
		return oytName;
	}

	public void setOytName(String oytName) {
		this.oytName = oytName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}
	
	

}
