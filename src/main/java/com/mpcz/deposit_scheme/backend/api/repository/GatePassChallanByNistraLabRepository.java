package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.GatePassChallanByNistraLab;

public interface GatePassChallanByNistraLabRepository extends JpaRepository<GatePassChallanByNistraLab, Long> {

	
	public  GatePassChallanByNistraLab findByConsumerApplicationNumber(String consumerApplicationNumber);
}
