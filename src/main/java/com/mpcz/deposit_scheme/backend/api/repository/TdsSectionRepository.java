package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.TdsSection;

public interface TdsSectionRepository extends JpaRepository<TdsSection, Long> {

	TdsSection findByConsumerAppNo(String consumerAppNo);

}
