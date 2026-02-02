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
@Entity(name = "TaskType")
@Table(name = "TASK_TYPE")
public @Data class TaskType extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TASK_TYPE_SEQ", sequenceName = "TASK_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_TYPE_SEQ")
	@Column(name = "TASK_TYPE_ID", columnDefinition = "serial", updatable = false)
	private Long taskTypeId;

	@Column(name = "TASK_TYPE_NAME")
	private String taskTypeName;

	public Long getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
