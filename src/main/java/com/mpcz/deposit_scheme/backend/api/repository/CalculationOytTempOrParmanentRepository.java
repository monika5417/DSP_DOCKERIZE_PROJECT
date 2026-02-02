package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.CalculationOytTempOrParmanent;

public interface CalculationOytTempOrParmanentRepository extends JpaRepository<CalculationOytTempOrParmanent, Long>{

	
	public CalculationOytTempOrParmanent findByTarifAndPhaseAndLoad(String traif , String phase, String load);
	
}
