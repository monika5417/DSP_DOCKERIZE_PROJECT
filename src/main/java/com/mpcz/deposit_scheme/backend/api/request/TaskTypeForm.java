package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Task Type Model Form.")
public @Data class TaskTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Task Type Name must not be null", required = true)
	@NotEmpty
	private String taskTypeName;

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
