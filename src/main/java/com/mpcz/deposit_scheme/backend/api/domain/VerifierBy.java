package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "VERIFIER_BY")
@Entity
public class VerifierBy {

	@Id
	@SequenceGenerator(name = "VERIFIER_BY_SEQ", sequenceName = "VERIFIER_BY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERIFIER_BY_SEQ")
	@Column(name = "VERIFIER_BY_ID")
	private Long verifierById;

	@Column(name = "VERIFIED_BY_NAME")
	private String name;

	@Column(name = "VERIFIED_BY_DESIGNATION")
	private String designation;
	
	@Column(name = "VERIFIER_CODE")
	private String veriferCode;
	
	@Column(name = "CONSUEMR_APPLICATION_NUMBER")
	private String consumerApplicationNumber;
	
	public Long getVerifierById() {
		return verifierById;
	}

	public void setVerifierById(Long verifierById) {
		this.verifierById = verifierById;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getVeriferCode() {
		return veriferCode;
	}

	public void setVeriferCode(String veriferCode) {
		this.veriferCode = veriferCode;
	}

	
	
}
