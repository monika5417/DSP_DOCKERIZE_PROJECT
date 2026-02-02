package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "NatureOfWork")
@Table(name = "NATURE_OF_WORK")
public @Data class NatureOfWork extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NATURE_OF_WORK_SEQ", sequenceName = "NATURE_OF_WORK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NATURE_OF_WORK_SEQ")
	@Column(name = "NATURE_OF_WORK_ID", columnDefinition = "serial", updatable = false)
	private Long natureOfWorkTypeId;

	@Column(name = "NATURE_OF_WORK_NAME")
	private String natureOfWorkName;

	public Long getNatureOfWorkTypeId() {
		return natureOfWorkTypeId;
	}

	public void setNatureOfWorkTypeId(Long natureOfWorkTypeId) {
		this.natureOfWorkTypeId = natureOfWorkTypeId;
	}

	public String getNatureOfWorkName() {
		return natureOfWorkName;
	}

	public void setNatureOfWorkName(String natureOfWorkName) {
		this.natureOfWorkName = natureOfWorkName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}