package com.mpcz.deposit_scheme.backend.api.services;

import java.util.Optional;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginAttempts;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;

public interface ConsumerLoginAttemptsService {

	public Optional<ConsumerLoginAttempts> findByConsumer(Consumer Consumer) throws DataNotFoundException;

	public void save(final ConsumerLoginAttempts consumerLoginAttempts);

	public void update(final ConsumerLoginAttempts consumerLoginAttempts);

	public void delete(final Consumer Consumer);

}
