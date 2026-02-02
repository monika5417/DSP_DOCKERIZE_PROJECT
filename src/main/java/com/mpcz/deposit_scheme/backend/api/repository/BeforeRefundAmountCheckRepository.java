package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.BeforeRefundAmountCheck;

public interface BeforeRefundAmountCheckRepository extends JpaRepository<BeforeRefundAmountCheck, Long> {

	
	@Query(value="select * from before_ref_amnt_check where application_no=:consumerApplicationNo and is_active=1", nativeQuery = true)
	BeforeRefundAmountCheck findByApplicationNo(String consumerApplicationNo);

	
	@Query(value="select * from before_ref_amnt_check where application_no=:consumerApplicationNo and is_active=1", nativeQuery = true)
	List<BeforeRefundAmountCheck> findByApplicationNoIsActive(String consumerApplicationNo);

}
