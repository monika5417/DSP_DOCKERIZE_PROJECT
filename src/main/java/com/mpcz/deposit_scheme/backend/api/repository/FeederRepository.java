package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Feeder;
import com.mpcz.deposit_scheme.backend.api.domain.Substation;

@Repository
public interface FeederRepository extends JpaRepository<Feeder, Long> {

	@Query(value = "select * from feeder where substation_id = :substationId and is_active=1", nativeQuery = true)
	public List<Feeder> findBySubstation(@Param("substationId") Long substationId);

	@Query(value = "select * from feeder where feeder_name=:feeder and is_deleted=false and is_active is true", nativeQuery = true)
	List<Feeder> checkFeeder(@Param("feeder") String feeder);

	@Query(value = "select * from feeder where feeder_code=:feederCode and is_deleted=false and is_active is true", nativeQuery = true)
	List<Feeder> checkFeederCode(@Param("feederCode") String feederCode);

	@Query(value = "select * from feeder where feeder_name=:feeder and feeder_id!=:id", nativeQuery = true)
	List<Feeder> checkFeederForUpdate(@Param("feeder") String feeder, @Param("id") Long id);

	@Query(value = "select * from feeder where feeder_code=:feederCode and feeder_id!=:id", nativeQuery = true)
	List<Feeder> checkFeederCodeForUpdate(@Param("feederCode") String feederCode, @Param("id") Long id);

//	sandeep, starts
	@Query(value = "SELECT f.* FROM feeder f WHERE f.is_active= 1 ORDER BY f.feeder_name ASC", nativeQuery = true)
	public List<Feeder> findAllActiveFeeder();
//	sandeep, ends

//	@Query(value = "select * from feeder where is_deleted=0", nativeQuery = true)
//	public List<Feeder> findAllFeedersBySubstation();
}