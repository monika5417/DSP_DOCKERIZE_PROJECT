package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginToken;

@Repository
public interface ConsumerLoginTokenRepository extends JpaRepository<ConsumerLoginToken, Long> {

	@Query(value = "SELECT * FROM CONSUMER_LOGIN_TOKEN WHERE CONSUMER_LOGIN_TOKEN_ID = :consumerLoginTokenId and IS_ACTIVE = 1 ", nativeQuery = true)
	Optional<ConsumerLoginToken> findById(@Param("consumerLoginTokenId") Long consumerLoginTokenId);

}
