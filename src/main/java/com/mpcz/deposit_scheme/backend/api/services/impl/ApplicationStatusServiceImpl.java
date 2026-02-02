package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.dto.ApplicationStatusDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationStatusException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

	private static final Logger logger = LogManager.getLogger(ApplicationStatusServiceImpl.class);

	@Autowired
	ApplicationStatusRepository applicationStatusRepository;

	@Override
	public ApplicationStatus findById(Long applicationStatusId) {

//		return applicationStatusRepository.findById(applicationStatusId).orElseThrow(null);

		return applicationStatusRepository.findById(applicationStatusId).orElseThrow(() -> new DataNotFoundException(
				new Response<String>(ResponseCode.DATA_NOT_FOUND, ResponseMessage.CONSUMER_APPLICATION_NOT_FOUND)));
	}

	@Override
	public List<ApplicationStatus> findAllApplicationStatus() throws ApplicationStatusException {

		final String method = "ApplicationStatusServiceImpl : findAllApplicationStatus()";
		logger.info(method);

		List<ApplicationStatus> applicationStatus = applicationStatusRepository.findAllActiveApplicationStatus();

		final Response<ApplicationStatus> response = new Response<>();
		if (Objects.isNull(applicationStatus) || applicationStatus.isEmpty()) {
			logger.error(
					"applicationStatusRepository.findAllActiveApplicationStatus is returning Null when findAllApplicationStatus call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ApplicationStatusException(response);
		} else {
			logger.info("Response is returning successfully");
			return applicationStatus;
		}
	}

	@Override
	public Response<ApplicationStatus> save(ApplicationStatus applicationStatus) {
		return null;
	}

	@Override
	public Response<ApplicationStatus> update(ApplicationStatusDTO applicationStatusDto, long id) {
		return null;
	}

	// Monika start
	@Override
	public Response<List<ApplicationStatus>> findAll() throws ApplicationStatusException {
		Response response = new Response<>();
		List<ApplicationStatus> findAll = applicationStatusRepository.findAllActiveApplicationStatus();
		
		if (Objects.isNull(findAll) || findAll.isEmpty()) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application status is not found!!!!");
			throw new ApplicationStatusException(response);
		} else {
			response.setList(findAll);
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
		
	}

	//end
}
