package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDetailsSummary;

public interface ApplicationDetailsRepository extends JpaRepository<ApplicationDetailsSummary, Long> {

	List<ApplicationDetailsSummary> findByDiscomId(Long discomId);

	
	
}
