package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "Discom")
@Table(name = "DISCOM")
public @Data class Discom extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DISCOM_SEQ", sequenceName = "DISCOM_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCOM_SEQ")
	@Column(name = "DISCOM_ID", columnDefinition = "serial", updatable = false)
	private Long discomId;

	@Column(name = "DISCOM_NAME")
	private String discomName;

	@Column(name = "DISCOM_CODE")
	private String discomCode;

	public Long getDiscomId() {
		return discomId;
	}

	public void setDiscomId(Long discomId) {
		this.discomId = discomId;
	}

	public String getDiscomName() {
		return discomName;
	}

	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}

	public String getDiscomCode() {
		return discomCode;
	}

	public void setDiscomCode(String discomCode) {
		this.discomCode = discomCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
