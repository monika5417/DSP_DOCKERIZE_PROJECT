package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VERIFIER_ISSUING_AUTHRITY")
public class VerifierIssuingAuthority {

	@Id
	@SequenceGenerator(name = "VERIFIER_ISSUING_AUTHRITY_SEQ", sequenceName = "VERIFIER_ISSUING_AUTHRITY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERIFIER_ISSUING_AUTHRITY_SEQ")
	@Column(name = "VERIFIER_ISSUING_AUTHRITY_ID")
    private Long VerifierIssuingAuthorityId;

    @Column(name = "ISSUING_AUTHORITY_NAME")
    private String name;

    @Column(name = "ISSUING_AUTHORITY_DESIGNATION")
    private String designation;
    
    
    @Column(name = "CONSUEMR_APPLICATION_NUMBER")
    private String consumerApplicationNumber;  
    

    public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public Long getVerifierIssuingAuthorityId() {
        return VerifierIssuingAuthorityId;
    }

    public void setVerifierIssuingAuthorityId(Long verifierIssuingAuthorityId) {
        VerifierIssuingAuthorityId = verifierIssuingAuthorityId;
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
}
