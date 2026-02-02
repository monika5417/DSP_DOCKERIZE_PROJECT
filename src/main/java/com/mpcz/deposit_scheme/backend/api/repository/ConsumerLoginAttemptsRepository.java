package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginAttempts;

@Repository
public interface ConsumerLoginAttemptsRepository extends JpaRepository<ConsumerLoginAttempts, Long> {

	public Optional<ConsumerLoginAttempts> findByConsumer(final Consumer consumer);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM CONSUMER_LOGIN_ATTEMPTS WHERE CONSUMER_ID=:CONSUMER_ID", nativeQuery = true)
	public void deleteConsumerId(final @Param("CONSUMER_ID") Long CONSUMER_ID);
}
