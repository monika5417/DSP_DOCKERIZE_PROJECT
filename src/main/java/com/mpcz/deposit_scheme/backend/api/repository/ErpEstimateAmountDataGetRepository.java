package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountDataGet;

public interface ErpEstimateAmountDataGetRepository extends JpaRepository<ErpEstimateAmountDataGet, Long> {

	
     public ErpEstimateAmountDataGet findByErpNo(String erpNo);
}
