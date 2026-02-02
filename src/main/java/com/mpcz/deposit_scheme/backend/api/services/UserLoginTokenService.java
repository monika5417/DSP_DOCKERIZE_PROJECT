package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.UserLoginToken;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;

public interface UserLoginTokenService {

	public UserLoginToken save(UserLoginToken userLoginToken);

	public String isLoginTokenValid(Long loginTokenId) throws InvalidAuthenticationException;
}
