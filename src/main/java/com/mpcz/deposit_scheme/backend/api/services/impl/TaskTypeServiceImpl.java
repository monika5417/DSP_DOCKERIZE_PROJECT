package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.TaskType;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.exception.TaskTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.TaskTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.TaskTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.TaskTypeService;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

	Logger logger = LoggerFactory.getLogger(TaskTypeServiceImpl.class);

	@Autowired
	private TaskTypeRepository taskTypeRepository;

	@Override
	public TaskType saveTaskType(@Valid TaskTypeForm taskTypeForm) throws TaskTypeException {

		final String method = "TaskTypeServiceImpl : saveTaskType()";
		logger.info(method);

		Response<TaskType> response = new Response<>();

		TaskType taskTypeResponse = null;

		if (Objects.isNull(taskTypeForm)) {
			logger.error("Task Type object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new TaskTypeException(response);
		} else {

			TaskType taskType = new TaskType();
			taskType.setTaskTypeName(taskTypeForm.getTaskTypeName().trim());

			taskTypeResponse = taskTypeRepository.save(taskType);

			if (Objects.isNull(taskTypeResponse)) {
				logger.error("repository.save(taskType) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new TaskTypeException(response);
			} else {
				return taskTypeResponse;
			}

		}
	}

	@Override
	public TaskType findByTaskTypeId(Long taskTypeId) throws TaskTypeException {

		final String method = "TaskTypeServiceImpl : findByTaskTypeId(Long taskTypeId)";

		logger.info(method);

		final Optional<TaskType> taskTypeOptional = taskTypeRepository.findById(taskTypeId);

		final Response<TaskType> response = new Response<>();

		if (Objects.isNull(taskTypeOptional) || !taskTypeOptional.isPresent()) {
			logger.error("taskTypeRepository.findById is returning Null when findByTaskTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.WORK_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new TaskTypeException(response);
		} else {
			TaskType taskType = taskTypeOptional.get();
			final List<TaskType> list = new ArrayList<>();
			list.add(taskType);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return taskType;
		}
	}

	@Override
	public List<TaskType> findAllTaskType() throws TaskTypeException {

		final String method = "TaskTypeServiceImpl : findAllTaskType()";
		logger.info(method);

		List<TaskType> taskType = taskTypeRepository.findAllActiveTaskType();

		final Response<TaskType> response = new Response<>();
		if (Objects.isNull(taskType) || taskType.isEmpty()) {
			logger.error("taskTypeRepository.findAllActiveTaskType is returning Null when findAllTaskType call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new TaskTypeException(response);
		} else {
			logger.info("Response is returning successfully");
			return taskType;
		}
	}
}
