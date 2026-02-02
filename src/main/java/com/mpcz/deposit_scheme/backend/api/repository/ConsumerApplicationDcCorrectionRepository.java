package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDcCorrection;

public interface ConsumerApplicationDcCorrectionRepository
		extends JpaRepository<ConsumerApplicationDcCorrection, Long> {

	Optional<List<ConsumerApplicationDcCorrection>> findByConsumersApplicationDetailConsumerApplicationId(
			Long ConsumerApplicationId);

}
