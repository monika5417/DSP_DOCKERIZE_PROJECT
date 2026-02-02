package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerHistory;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerHistoryRepository;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerHistoryService;

@Service
public class ConsumerHistoryServiceImpl implements ConsumerHistoryService {

	Logger logger = LoggerFactory.getLogger(ConsumerHistoryServiceImpl.class);

	private ConsumerHistoryRepository consumerHistoryRepository;

	@Autowired
	public ConsumerHistoryServiceImpl(ConsumerHistoryRepository consumerHistoryRepository) {
		super();
		this.consumerHistoryRepository = consumerHistoryRepository;
	}

	@Override
	public ConsumerHistory saveConsumerHistory(ConsumerHistory consumerHistory) {
		final String method = "consumerHistoryServiceImpl : saveConsumerHistory()";
		logger.info(method);
		return this.consumerHistoryRepository.saveAndFlush(consumerHistory);
	}

}
