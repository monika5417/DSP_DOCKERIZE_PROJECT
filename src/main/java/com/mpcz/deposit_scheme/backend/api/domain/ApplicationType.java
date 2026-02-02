package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "ApplicationType")
@Table(name = "APPLICATION_TYPE")
public @Data class ApplicationType extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APPLICATION_TYPE_SEQ", sequenceName = "APPLICATION_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_TYPE_SEQ")
	@Column(name = "APPLICATION_TYPE_ID", columnDefinition = "serial", updatable = false)
	private Long ApplicationTypeId;

	@Column(name = "APPLICATION_TYPE_NAME")
	private String ApplicationTypeName;

	public Long getApplicationTypeId() {
		return ApplicationTypeId;
	}

	public void setApplicationTypeId(Long applicationTypeId) {
		ApplicationTypeId = applicationTypeId;
	}

	public String getApplicationTypeName() {
		return ApplicationTypeName;
	}

	public void setApplicationTypeName(String applicationTypeName) {
		ApplicationTypeName = applicationTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
