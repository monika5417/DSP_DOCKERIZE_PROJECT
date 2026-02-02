package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import javax.validation.Valid;

import com.mpcz.deposit_scheme.backend.api.domain.TaskType;
import com.mpcz.deposit_scheme.backend.api.exception.TaskTypeException;
import com.mpcz.deposit_scheme.backend.api.request.TaskTypeForm;

public interface TaskTypeService {
	
	public TaskType saveTaskType(@Valid TaskTypeForm taskTypeForm) throws TaskTypeException;

	public TaskType findByTaskTypeId(final Long taskTypeId) throws TaskTypeException;

	public List<TaskType> findAllTaskType() throws TaskTypeException;

}
