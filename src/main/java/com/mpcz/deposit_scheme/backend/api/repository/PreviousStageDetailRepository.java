package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.PreviousStageDetail;

@Repository
public interface PreviousStageDetailRepository extends JpaRepository<PreviousStageDetail, Long> {
	
	
	Optional<PreviousStageDetail> findByConsumersApplicationDetailConsumerApplicationIdAndForwardDateIsNull(Long Id);

}
