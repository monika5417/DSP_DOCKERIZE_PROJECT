package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.UserHistory;
import com.mpcz.deposit_scheme.backend.api.repository.UserHistoryRepository;
import com.mpcz.deposit_scheme.backend.api.services.UserHistoryService;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

	Logger logger = LoggerFactory.getLogger(UserHistoryServiceImpl.class);

	private UserHistoryRepository userHistoryRepository;

	@Autowired
	public UserHistoryServiceImpl(UserHistoryRepository userHistoryRepository) {
		super();
		this.userHistoryRepository = userHistoryRepository;
	}

	@Override
	public UserHistory saveUserHistory(UserHistory userHistory) {
		final String method = "userHistoryServiceImpl : saveUserHistory()";
		logger.info(method);
		return this.userHistoryRepository.saveAndFlush(userHistory);
	}

}
