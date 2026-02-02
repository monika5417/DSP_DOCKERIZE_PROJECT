package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.MmkyCalculation;

public interface MmkyCalculationRepository  extends JpaRepository<MmkyCalculation, Long> {
	

	@Query(value = " SELECT * from MMKY_CALCULATION where P_APP_NUM =:consumerApplicationNo", nativeQuery = true)	
	MmkyCalculation findByParentApplicationNo(String consumerApplicationNo);


	
	


}
