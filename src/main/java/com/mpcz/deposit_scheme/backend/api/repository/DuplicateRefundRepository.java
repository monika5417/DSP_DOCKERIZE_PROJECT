package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.DuplicateRefund;

public interface DuplicateRefundRepository extends JpaRepository<DuplicateRefund, Long> {

	Optional<DuplicateRefund> findByTransactionId(String transactionId);

	Optional<DuplicateRefund> findByConsumerApplicationNo(String consumerApplicationNo);
	
	
@Query(value="select * from duplicate_refund where consumer_application_no = :consumerApplicationNo", nativeQuery = true)
	List<DuplicateRefund> getAllDuplicateRefundApplicationByApplicationNo(String consumerApplicationNo);

}
