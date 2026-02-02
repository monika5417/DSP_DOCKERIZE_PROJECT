package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
	
	@Query(value="select * from designation where designation=:designation", nativeQuery=true)
	List<Designation> checkDesignation(@Param("designation") String designation);
	
	@Query(value="select * from designation where designation=:designation and designation_id!=:id", nativeQuery=true)
	List<Designation> checkDesignationForUpdate(@Param("designation") String designation, @Param("id") Long id);

}
