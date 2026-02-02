package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mpcz.deposit_scheme.backend.api.domain.UserLoginToken;

public interface UserLoginTokenRepository extends JpaRepository<UserLoginToken, Long> {

	@Query(value = "Select * from user_login_token where login_token_id = :loginTokenId and is_active =1 ", nativeQuery = true)
	Optional<UserLoginToken> findById(@Param("loginTokenId") Long loginTokenId);
}
