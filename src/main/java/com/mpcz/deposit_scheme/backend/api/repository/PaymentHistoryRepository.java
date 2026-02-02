package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.PaymentHistory;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long>{
	
	
	public PaymentHistory findByApplicationNo(String applicationNo);

}
