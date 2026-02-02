package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.OytCalculationTempOrParma;
import com.mpcz.deposit_scheme.backend.api.domain.OytCalculationTempOrParma1;

@Repository
public interface OytCalculationTempOrParmaRepository1 extends JpaRepository<OytCalculationTempOrParma1, Long>{

	public OytCalculationTempOrParma findByTarifAndPhase(String tarif , String phase);
	
	
	public OytCalculationTempOrParma1 
	findByTarifAndPhaseAndLoadBetween(String tarif, String phase, long minLoad, long maxLoad);
	
	
	public OytCalculationTempOrParma1 
	findByTarifAndPhaseAndLoadGreaterThanEqual(String tarif, String phase, long load);
	
	
	@Query(
		    value = "SELECT * FROM application_payment_type_1 " +
		            "WHERE LOAD = ( " +
		            "    CASE  " +
		            "        WHEN :inputLoad BETWEEN 0 AND 3 THEN 3 " +
		            "        WHEN :inputLoad BETWEEN 4 AND 10 THEN 10 " +
		            "        WHEN :inputLoad BETWEEN 11 AND 1000 THEN 1000 " +
		            "    END " +
		            ") " +
		            "AND TARIF = :inputTarif " +
		            "AND PHASE = :inputPhase",
		    nativeQuery = true
		)
	public OytCalculationTempOrParma1 findSlabData(
		        @Param("inputLoad") long inputLoad,
		        @Param("inputTarif") String inputTarif,
		        @Param("inputPhase") String inputPhase
		);

	
}
