package com.mpcz.deposit_scheme.backend.api.services;

import java.util.Optional;

import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginAttempts;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;

public interface UserLoginAttemptsService {

	public Optional<UserLoginAttempts> findByUser(User User) throws DataNotFoundException;

	public void save(final UserLoginAttempts userLoginAttempts);

	public void update(final UserLoginAttempts userLoginAttempts);

	public void delete(final User User);

}
