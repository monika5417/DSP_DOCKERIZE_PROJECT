package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateDomain;

@Repository
public interface EstimateRepository extends JpaRepository<ErpEstimateDomain, Long> {

	
	@Query(value="SELECT * FROM ERP_BUDGET_WORKFLOW WHERE ERP_BUDGET_WORKFLOW_NUMBER =:estimateNumber",nativeQuery = true)
	Optional<ErpEstimateDomain> findById(String estimateNumber);

	
}

