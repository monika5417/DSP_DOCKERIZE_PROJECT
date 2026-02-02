package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.SanchayPaymentDetails;

public interface SanchayPaymentDetailsRepository extends JpaRepository<SanchayPaymentDetails, Long> {

	@Query(value = "select * from sanchay_payment_detail where consumer_application_number = :consumerApplicationNo and is_active=1", nativeQuery = true)
	SanchayPaymentDetails findByConsumerApplicationNo(String consumerApplicationNo);
	
	
	Optional<SanchayPaymentDetails> findByTrxIdAndIsActive(String txnId, Boolean isActive);

}
