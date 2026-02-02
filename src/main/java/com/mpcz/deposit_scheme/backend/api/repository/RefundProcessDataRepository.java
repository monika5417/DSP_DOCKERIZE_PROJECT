package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.RefundProcessData;

public interface RefundProcessDataRepository extends JpaRepository<RefundProcessData, Long> {

	RefundProcessData findByConsumerApplicationNo(String consumerApplicationNo);

}
