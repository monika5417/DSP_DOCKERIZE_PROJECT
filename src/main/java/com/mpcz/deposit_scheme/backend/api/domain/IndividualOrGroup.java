package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name="IndividualOrGroup")
@Table(name="INDIVIDUAL_OR_GROUPWISE")
public @Data class IndividualOrGroup extends Auditable<Long> {
	
	
	@Id
	@SequenceGenerator(name = "INDIVIDUAL_OR_GROUPWISE_SEQ",sequenceName = "INDIVIDUAL_OR_GROUPWISE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INDIVIDUAL_OR_GROUPWISE_SEQ")
	@Column(name = "INDIVIDUAL_OR_GROUPWISE_ID", columnDefinition = "serial", updatable = false)
	private Long individualOrGroupId;
	

	@Column(name = "NAME")
	private String name;


	public Long getIndividualOrGroupId() {
		return individualOrGroupId;
	}


	public void setIndividualOrGroupId(Long individualOrGroupId) {
		this.individualOrGroupId = individualOrGroupId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	

}
