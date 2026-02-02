/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginAttempts;

/**
 * @author Amit
 *
 * 07-Sep-2020
 */
@Repository
public interface UserLoginAttemptsRepository extends JpaRepository<UserLoginAttempts, Long> {

	public Optional<UserLoginAttempts> findByUser(final User user);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="delete from user_login_attempts where ad_user_id=:ad_user_id",nativeQuery=true)
	public void deleteUserId(final @Param("ad_user_id") Long ad_user_id);
}
