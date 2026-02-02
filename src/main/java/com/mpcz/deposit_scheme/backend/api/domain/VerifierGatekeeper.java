package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VERIFIER_GATEKEEPER")
public class VerifierGatekeeper {
	
	@Id
	@SequenceGenerator(name = "VERIFIER_GATEKEEPER_SEQ", sequenceName = "VERIFIER_GATEKEEPER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERIFIER_GATEKEEPER_SEQ")
	@Column(name = "VERIFIER_GATEKEEPER_ID")
    private Long verifierGatekeeperId;

    @Column(name = "GATEKEEPER_NAME")
    private String name;

    @Column(name = "GATEKEEPER_DESIGNATION")
    private String designation;
    
    
    @Column(name = "CONSUEMR_APPLICATION_NUMBER")
    private String consumerApplicationNumber;  
    
    
    

    public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public Long getVerifierGatekeeperId() {
        return verifierGatekeeperId;
    }

    public void setVerifierGatekeeperId(Long verifierGatekeeperId) {
        this.verifierGatekeeperId = verifierGatekeeperId;
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
