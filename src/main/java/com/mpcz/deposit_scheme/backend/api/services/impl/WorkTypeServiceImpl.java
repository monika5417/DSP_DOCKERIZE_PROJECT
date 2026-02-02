package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.WorkTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.WorkTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.WorkTypeService;

@Service
public class WorkTypeServiceImpl implements WorkTypeService {

	Logger logger = LoggerFactory.getLogger(WorkTypeServiceImpl.class);

	@Autowired
	private WorkTypeRepository workTypeRepository;

	@Override
	public WorkType saveWorkType(WorkTypeForm workTypeForm) throws WorkTypeException {

		final String method = "WorkTypeServiceImpl : saveWorkType()";
		logger.info(method);

		Response<WorkType> response = new Response<>();

		WorkType workTypeResponse = null;

		if (Objects.isNull(workTypeForm)) {
			logger.error("Work Type object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new WorkTypeException(response);
		} else {

			WorkType workType = new WorkType();
			workType.setWorkTypeName(workTypeForm.getWorkTypeName().trim());

			workTypeResponse = workTypeRepository.save(workType);

			if (Objects.isNull(workTypeResponse)) {
				logger.error("repository.save(workType) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new WorkTypeException(response);
			} else {
				return workTypeResponse;
			}

		}
	}

	@Override
	public WorkType findByWorkTypeId(Long workTypeId) throws WorkTypeException {
		final String method = "WorkTypeServiceImpl : findByWorkTypeId(Long workTypeId)";

		logger.info(method);

		final Optional<WorkType> workTypeOptional = workTypeRepository.findById(workTypeId);

		final Response<WorkType> response = new Response<>();

		if (Objects.isNull(workTypeOptional) || !workTypeOptional.isPresent()) {
			logger.error("workTypeRepository.findById is returning Null when findByWorkTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.WORK_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new WorkTypeException(response);
		} else {
			WorkType workType = workTypeOptional.get();
			final List<WorkType> list = new ArrayList<>();
			list.add(workType);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return workType;
		}
	}

	@Override
	public List<WorkType> findAllWorkType() throws WorkTypeException {
		final String method = "WorkTypeServiceImpl : findAllWorkType()";
		logger.info(method);

		List<WorkType> workType = workTypeRepository.findAllActiveWorkType();

		final Response<WorkType> response = new Response<>();
		if (Objects.isNull(workType) || workType.isEmpty()) {
			logger.error("workTypeRepository.findAll is returning Null when findAllWorkType call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new WorkTypeException(response);
		} else {
			logger.info("Response is returning successfully");
			return workType;
		}

	}
}
