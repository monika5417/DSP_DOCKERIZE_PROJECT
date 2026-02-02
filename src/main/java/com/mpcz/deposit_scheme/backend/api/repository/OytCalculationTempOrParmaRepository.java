package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.OytCalculationTempOrParma;

@Repository
public interface OytCalculationTempOrParmaRepository extends JpaRepository<OytCalculationTempOrParma, Long>{

	public OytCalculationTempOrParma findByTarifAndPhase(String tarif , String phase);
}
