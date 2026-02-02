package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAccountDetails;

public interface ConsumerAccountDetailsRepository extends JpaRepository<ConsumerAccountDetails, Long> {

	ConsumerAccountDetails findByConsumerApplicationNo(String consumerApplicationNo);
	
	@Query(value="select * from consumer_account_details where consumer_application_no=:consumerApplicationNo and is_active=1",nativeQuery = true)
	ConsumerAccountDetails findByConsumerApplicationNoIsActive(String consumerApplicationNo);

}
