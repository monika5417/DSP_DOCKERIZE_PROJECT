package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginToken;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerLoginTokenRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerLoginTokenService;

@Service
public class ConsumerLoginTokenServiceImpl implements ConsumerLoginTokenService {

	@Autowired
	ConsumerLoginTokenRepository consumerLoginTokenRepository;

	@Override
	public ConsumerLoginToken save(ConsumerLoginToken consumerLoginToken) {
		return consumerLoginTokenRepository.save(consumerLoginToken);
	}

	@Override
	public String isLoginTokenValid(Long consumerLoginTokenId) throws InvalidAuthenticationException {

		Response<Consumer> response = new Response<>();
		Optional<ConsumerLoginToken> consumerLoginToken = consumerLoginTokenRepository.findById(consumerLoginTokenId);
		if (consumerLoginToken.isPresent()) {
			ConsumerLoginToken dbConsumerLoginToken = consumerLoginToken.get();
//			Date currentTime = new Date();
//			Date tokenTime = dbConsumerLoginToken.getConsumerTokenExpiredTime();
//			if (tokenTime.compareTo(currentTime) < 0) {
//				response.setMessage("Login time Expired , Please Try again !!");
//				response.setCode(ResponseCode.LOGIN_TIME_EXPIRED);
//				throw new InvalidAuthenticationException(response);
//			}

			return dbConsumerLoginToken.getConsumerTokenText();
		} else {
			response.setMessage("Login time Expired , Please Try again !!");
			response.setCode(ResponseCode.LOGIN_TIME_EXPIRED);
			throw new InvalidAuthenticationException(response);
		}

	}

}
