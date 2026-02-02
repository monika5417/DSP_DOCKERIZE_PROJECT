package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginAttempts;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerLoginAttemptsRepository;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerLoginAttemptsService;

@Service
public class ConsumerLoginAttemptsServiceImpl implements ConsumerLoginAttemptsService {

	Logger logger = LoggerFactory.getLogger(ConsumerLoginAttemptsServiceImpl.class);

	@Autowired
	private ConsumerLoginAttemptsRepository consumerLoginAttemptsRepository;

	@Override
	public Optional<ConsumerLoginAttempts> findByConsumer(Consumer Consumer) throws DataNotFoundException {
		final String method = "ConsumerLoginAttemptsServiceImpl/findByConsumer()";
		logger.info(method);
		return consumerLoginAttemptsRepository.findByConsumer(Consumer);
	}

	@Override
	public void save(ConsumerLoginAttempts consumerLoginAttempts) {
		final String method = "ConsumerLoginAttemptsServiceImpl/save()";
		logger.info(method);

		consumerLoginAttemptsRepository.save(consumerLoginAttempts);
	}

	@Override
	public void update(ConsumerLoginAttempts consumerLoginAttempts) {
		final String method = "ConsumerLoginAttemptsServiceImpl/update()";
		logger.info(method);

		consumerLoginAttemptsRepository.save(consumerLoginAttempts);
	}

	@Override
	public void delete(Consumer Consumer) {
		final String method = "ConsumerLoginAttemptsServiceImpl/delete()";
		logger.info(method);
		Optional<ConsumerLoginAttempts> optional = consumerLoginAttemptsRepository.findByConsumer(Consumer);
		if (optional.isPresent())
			consumerLoginAttemptsRepository.deleteConsumerId(Consumer.getConsumerId());

	}

}
