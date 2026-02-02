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
@Entity(name = "SchemeType")
@Table(name = "SCHEME_TYPE")
public @Data class SchemeType extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SCHEME_TYPE_SEQ", sequenceName = "SCHEME_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEME_TYPE_SEQ")
	@Column(name = "SCHEME_TYPE_ID", columnDefinition = "serial", updatable = false)
	private Long schemeTypeId;

	@Column(name = "SCHEME_TYPE_NAME")
	private String schemeTypeName;

	public Long getSchemeTypeId() {
		return schemeTypeId;
	}

	public void setSchemeTypeId(Long schemeTypeId) {
		this.schemeTypeId = schemeTypeId;
	}

	public String getSchemeTypeName() {
		return schemeTypeName;
	}

	public void setSchemeTypeName(String schemeTypeName) {
		this.schemeTypeName = schemeTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	

}
