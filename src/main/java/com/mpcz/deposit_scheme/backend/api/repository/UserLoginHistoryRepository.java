package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.UserLoginHistory;

/*
 * This LoginHistoryRepository is used to manage user login history.
 * 
 * 
 * @author Aditya Vays
 * @version 1.0
 */

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long>{
	
	
	@Query(value = "select * from login_history where user_id = :userId", nativeQuery = true)
	public List<UserLoginHistory> findByUserId(@Param("userId") String userId);

}
