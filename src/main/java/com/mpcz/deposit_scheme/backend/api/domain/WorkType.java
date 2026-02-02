package com.mpcz.deposit_scheme.backend.api.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "WorkType")
@Table(name = "WORK_TYPE")
public @Data class WorkType extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "WORK_TYPE_SEQ", sequenceName = "WORK_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORK_TYPE_SEQ")
	@Column(name = "WORK_TYPE_ID", columnDefinition = "serial", updatable = false)
	private Long workTypeId;

	@Column(name = "WORK_TYPE_NAME")
	private String workTypeName;

	public Long getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}