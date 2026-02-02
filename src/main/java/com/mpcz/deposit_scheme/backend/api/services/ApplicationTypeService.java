package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationType;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeException;
import com.mpcz.deposit_scheme.backend.api.request.ApplicationTypeForm;

public interface ApplicationTypeService {

	public ApplicationType saveApplicationType(ApplicationTypeForm applicationTypeForm) throws ApplicationTypeException;

	public ApplicationType findByApplicationTypeId(final Long applicationTypeId) throws ApplicationTypeException;

	public List<ApplicationType> findAllApplicationType() throws ApplicationTypeException;
}
