package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginToken;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.repository.UserLoginTokenRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.UserLoginTokenService;

@Service
public class UserLoginTokenServiceImpl implements UserLoginTokenService {

	@Autowired
	UserLoginTokenRepository userLoginTokenRepository;

	@Override
	public UserLoginToken save(UserLoginToken userLoginToken) {
		return userLoginTokenRepository.save(userLoginToken);
	}

	@Override
	public String isLoginTokenValid(Long userLoginTokenId) throws InvalidAuthenticationException {

		Response<User> response = new Response<>();
		Optional<UserLoginToken> userLoginToken = userLoginTokenRepository.findById(userLoginTokenId);
		if (userLoginToken.isPresent()) {
			UserLoginToken dbUserLoginToken = userLoginToken.get();
			Date currentTime = new Date();
			Date tokenTime = dbUserLoginToken.getTokenExpiredTime();
			if (tokenTime.compareTo(currentTime) < 0) {
				response.setMessage("Login time Expired , Please Try again !!");
				response.setCode(ResponseCode.LOGIN_TIME_EXPIRED);
				throw new InvalidAuthenticationException(response);
			}

			return dbUserLoginToken.getTokenText();
		} else {
			response.setMessage("Login time Expired , Please Try again !!");
			response.setCode(ResponseCode.LOGIN_TIME_EXPIRED);
			throw new InvalidAuthenticationException(response);
		}

	}

}
