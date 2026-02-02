package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ConnectionPraday;

@Repository
public interface ConnectionPradayRepository extends JpaRepository<ConnectionPraday, Long> {

	ConnectionPraday findByIvrs(String ivrs);

	ConnectionPraday findByConsumerApplicationNo(String consAppNo);
}
