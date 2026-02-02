package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import javax.validation.Valid;

import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
import com.mpcz.deposit_scheme.backend.api.request.WorkTypeForm;

public interface WorkTypeService {

	public WorkType saveWorkType(@Valid WorkTypeForm workTypeForm) throws WorkTypeException;

	public WorkType findByWorkTypeId(final Long workTypeId) throws WorkTypeException;

	public List<WorkType> findAllWorkType() throws WorkTypeException;

}
