package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.CheckConsumer;

public interface CheckConsumerRepository extends JpaRepository<CheckConsumer, Integer> {
	
	
     public CheckConsumer findByConsumerNumber(String consumernumber);

}
