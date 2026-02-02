package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.Substation;

@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long> {

	@Query(value = "select * from substation where dc_id = :dcId and is_active= 1", nativeQuery = true)
	public List<Substation> findByDC(@Param("dcId") Long dcId);

	@Query(value = "select * from substation where substation_name=:subStation", nativeQuery = true)
	List<Substation> checkSubstation(@Param("subStation") String subStation);

	@Query(value = "select * from substation where substation_code=:substationCode", nativeQuery = true)
	List<Substation> checkSubstationCode(@Param("substationCode") String substationCode);

	@Query(value = "select * from substation where substation_name=:subStation and substation_id!=:id", nativeQuery = true)
	List<Substation> checkSubstationForUpdate(@Param("subStation") String subStation, @Param("id") Long id);

	@Query(value = "select * from substation where substation_code=:substationCode and substation_id!=:id", nativeQuery = true)
	List<Substation> checkSubstationCodeForUpdate(@Param("substationCode") String substationCode, @Param("id") Long id);

	List<Substation> findByIsActiveAndIsDeletedOrderBySubStationNameAsc(Boolean isActive, Boolean isDeleted);

//	sandeep, starts
	@Query(value = "SELECT s.* FROM substation s WHERE s.is_active= 1 ORDER BY s.substation_name ASC", nativeQuery = true)
	public List<Substation> findAllActiveSubstation();
//	sandeep, ends
}
