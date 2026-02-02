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
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerType;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerTypeService;

@Service
public class ConsumerTypeServiceImpl implements ConsumerTypeService {

	Logger logger = LoggerFactory.getLogger(ConsumerTypeServiceImpl.class);

	@Autowired
	private ConsumerTypeRepository consumerTypeRepository;

	@Override
	public ConsumerType saveConsumerType(ConsumerTypeForm consumerTypeForm) throws ConsumerTypeException {

		final String method = "ConsumerTypeServiceImpl : saveConsumerType()";
		logger.info(method);

		Response<ConsumerType> response = new Response<>();

		ConsumerType consumerTypeResponse = null;

		if (Objects.isNull(consumerTypeForm)) {
			logger.error("Consumer Type object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ConsumerTypeException(response);
		} else {

			ConsumerType consumerType = new ConsumerType();
			consumerType.setConsumerTypeName(consumerTypeForm.getConsumerTypeName().trim());

			consumerTypeResponse = consumerTypeRepository.save(consumerType);

			if (Objects.isNull(consumerTypeResponse)) {
				logger.error("repository.save(consumerType) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ConsumerTypeException(response);
			} else {
				return consumerTypeResponse;
			}

		}
	}

	@Override
	public ConsumerType findByConsumerTypeId(Long Id) throws ConsumerTypeException {

		final String method = "ConsumerTypeServiceImpl : findById(Long Id)";
		logger.info(method);

		final Optional<ConsumerType> consumerTypeOptional = consumerTypeRepository.findById(Id);

		final Response<ConsumerType> response = new Response<>();

		if (Objects.isNull(consumerTypeOptional) || !consumerTypeOptional.isPresent()) {
			logger.error("consumerTypeRepository.findById is returning Null when findByConsumerTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CONSUMER_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ConsumerTypeException(response);
		} else {
			ConsumerType consumerType = consumerTypeOptional.get();
			final List<ConsumerType> list = new ArrayList<>();
			list.add(consumerType);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return consumerType;
		}

	}

}
