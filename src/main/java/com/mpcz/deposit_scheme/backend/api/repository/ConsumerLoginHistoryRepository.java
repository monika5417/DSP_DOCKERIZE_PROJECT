package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginHistory;

@Repository
public interface ConsumerLoginHistoryRepository extends JpaRepository<ConsumerLoginHistory, Long> {

	@Query(value = "SELECT * FROM CONSUMER_LOGIN_HISTORY WHERE CONSUMER_ID = :consumerLoginId", nativeQuery = true)
	public List<ConsumerLoginHistory> findByConsumerLoginId(@Param("consumerLoginId") String consumerLoginId);

}
