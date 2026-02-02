package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.dto.ApplicationStatusDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationStatusException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface ApplicationStatusService {

	ApplicationStatus findById(Long applicationStatusId);

	public List<ApplicationStatus> findAllApplicationStatus() throws ApplicationStatusException;

	Response<ApplicationStatus> save(ApplicationStatus applicationStatus);

	Response<ApplicationStatus> update(ApplicationStatusDTO applicationStatusDto, long id);

	Response<List<ApplicationStatus>> findAll() throws ApplicationStatusException;
}
