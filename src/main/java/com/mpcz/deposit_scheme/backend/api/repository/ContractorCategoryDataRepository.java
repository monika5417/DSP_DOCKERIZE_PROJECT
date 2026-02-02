/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorCategoryData;

/**
 * @author Monika Rajpoot
 * @since 11-Sep-2025
 * @description ContractorCategoryDataRepository.java class description
 */

public interface ContractorCategoryDataRepository extends JpaRepository<ContractorCategoryData, Long> {

	
	@Query("SELECT c FROM ContractorCategoryData c " +
		       "WHERE c.dgmSelectedPreference = :selectedReference " +
		       "AND :amount BETWEEN c.startAmount AND c.endAmount")
		ContractorCategoryData findCategoryByRange(@Param("selectedReference") Long selectedReference,
		                                           @Param("amount") BigDecimal amount);

	
	ContractorCategoryData findByDgmSelectedPreference(Long selectedrefernce);
}
