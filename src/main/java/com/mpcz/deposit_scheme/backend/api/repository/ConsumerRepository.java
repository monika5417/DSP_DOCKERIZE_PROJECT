package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.User;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
	

	public Optional<Consumer> findByConsumerLoginId(final String consumerLoginId);

	public Optional<Consumer> findByConsumerLoginIdAndAccountNonLocked(String consumerLoginId,
			Boolean accountNonLocked);

	public Optional<Consumer> findByConsumerLoginIdAndAccountNonExpired(String consumerLoginId,
			Boolean accountNonExpired);

	public Optional<Consumer> findByConsumerId(final String consumerId);

	public Optional<Consumer> findByConsumerMobileNo(String consumerMobileNo);

//	sandeep, starts
	@Query(value = "select * from CONSUMER where CONSUMER_EMAIL_ID=:consumerEmailId", nativeQuery = true)
	List<Consumer> checkConsumerEmailId(@Param("consumerEmailId") String consumerEmailId);

	@Query(value = "select * from CONSUMER where CONSUMER_MOBILE_NO=:consumerMobileNo", nativeQuery = true)
	List<Consumer> checkConsumerMobileNo(@Param("consumerMobileNo") String consumerMobileNo);

	@Query(value = "select * from CONSUMER where AADHAR_NO=:aadharNo", nativeQuery = true)
	List<Consumer> checkAadharNo(@Param("aadharNo") String aadharNo);

	@Query(value = "select * from CONSUMER where PAN_NO=:panNo", nativeQuery = true)
	List<Consumer> checkPanNo(@Param("panNo") String panNo);
//	sandeep, ends	

	public Consumer findByConsumerId(Long consumerId);

	

}
