package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginAttempts;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.repository.UserLoginAttemptsRepository;
import com.mpcz.deposit_scheme.backend.api.services.UserLoginAttemptsService;

@Service
public class UserLoginAttemptsServiceImpl implements UserLoginAttemptsService {

	Logger logger = LoggerFactory.getLogger(UserLoginAttemptsServiceImpl.class);

	@Autowired
	private UserLoginAttemptsRepository userLoginAttemptsRepository;

	@Override
	public Optional<UserLoginAttempts> findByUser(User User) throws DataNotFoundException {
		final String method = "UserLoginAttemptsServiceImpl/findByUser()";
		logger.info(method);
		return userLoginAttemptsRepository.findByUser(User);
	}

	@Override
	public void save(UserLoginAttempts userLoginAttempts) {
		final String method = "UserLoginAttemptsServiceImpl/save()";
		logger.info(method);

		userLoginAttemptsRepository.save(userLoginAttempts);
	}

	@Override
	public void update(UserLoginAttempts userLoginAttempts) {
		final String method = "UserLoginAttemptsServiceImpl/update()";
		logger.info(method);

		userLoginAttemptsRepository.save(userLoginAttempts);
	}

	@Override
	public void delete(User User) {
		final String method = "UserLoginAttemptsServiceImpl/delete()";
		logger.info(method);
		Optional<UserLoginAttempts> optional = userLoginAttemptsRepository.findByUser(User);
		if (optional.isPresent())
			userLoginAttemptsRepository.deleteUserId(Long.parseLong(User.getUserId()));

	}

}
