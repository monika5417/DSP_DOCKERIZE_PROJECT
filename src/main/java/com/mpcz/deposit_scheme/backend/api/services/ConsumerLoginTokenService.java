package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginToken;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;

public interface ConsumerLoginTokenService {

	public ConsumerLoginToken save(ConsumerLoginToken consumerLoginToken);

	public String isLoginTokenValid(Long loginTokenId) throws InvalidAuthenticationException;
}
