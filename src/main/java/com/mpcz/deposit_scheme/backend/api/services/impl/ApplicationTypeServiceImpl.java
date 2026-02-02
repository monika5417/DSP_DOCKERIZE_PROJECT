package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationType;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.ApplicationTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationTypeService;

@Service
public class ApplicationTypeServiceImpl implements ApplicationTypeService {

	Logger logger = LoggerFactory.getLogger(ApplicationTypeServiceImpl.class);

	@Autowired
	private ApplicationTypeRepository applicationTypeRepository;

	@Override
	public ApplicationType saveApplicationType(ApplicationTypeForm applicationTypeForm)
			throws ApplicationTypeException {

		final String method = "ApplicationTypeServiceImpl : saveApplicationType()";

		logger.info(method);

		Response<ApplicationType> response = new Response<>();

		ApplicationType applicationTypeResponse = null;

		if (Objects.isNull(applicationTypeForm)) {
			logger.error("Application Type object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ApplicationTypeException(response);
		} else {

			ApplicationType applicationType = new ApplicationType();

			applicationType.setApplicationTypeName(applicationTypeForm.getApplicationTypeName().trim());

			applicationTypeResponse = applicationTypeRepository.save(applicationType);

			if (Objects.isNull(applicationTypeResponse)) {
				logger.error("applicationTypeRepository.save(applicationType) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ApplicationTypeException(response);
			} else {
				return applicationTypeResponse;
			}

		}
	}

	@Override
	public ApplicationType findByApplicationTypeId(Long applicationTypeId) throws ApplicationTypeException {

		final String method = "ApplicationTypeServiceImpl : findByApplicationTypeId(Long applicationTypeId)";

		logger.info(method);

		final Optional<ApplicationType> applicationTypeOptional = applicationTypeRepository.findById(applicationTypeId);

		final Response<ApplicationType> response = new Response<>();

		if (Objects.isNull(applicationTypeOptional) || !applicationTypeOptional.isPresent()) {
			logger.error("applicationTypeRepository.findById is returning Null when findByApplicationTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ApplicationTypeException(response);
		} else {
			ApplicationType applicationType = applicationTypeOptional.get();
			final List<ApplicationType> list = new ArrayList<>();
			list.add(applicationType);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return applicationType;
		}
	}

	@Override
	public List<ApplicationType> findAllApplicationType() throws ApplicationTypeException {

		final String method = "ApplicationTypeServiceImpl : findAllApplicationType()";
		logger.info(method);

		List<ApplicationType> applicationType = applicationTypeRepository.findAllActiveApplicationType();

		final Response<ApplicationType> response = new Response<>();
		if (Objects.isNull(applicationType) || applicationType.isEmpty()) {
			logger.error(
					"applicationTypeRepository.findAllActiveApplicationType is returning Null when findAllApplicationType call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ApplicationTypeException(response);
		} else {
			logger.info("Response is returning successfully");
			return applicationType;
		}
	}

}
