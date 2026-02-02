package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDemandApproval;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDcCorrection;

@Repository
public interface ApplicationDemandApprovalRepository extends JpaRepository<ApplicationDemandApproval, Long> {

	Optional<List<ApplicationDemandApproval>> findByConsumersApplicationDetailConsumerApplicationId(
			Long ConsumerApplicationId);
	
	
}
