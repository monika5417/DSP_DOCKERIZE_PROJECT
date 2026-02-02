package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Division;

/*
 * This LoadRecordRepository is used to manage Load Record details.
 * 
 * 
 * @author Aditya Vays
 * @version 1.0
 */
@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

	@Query(value = "select * from division where circle_id = :circle_id and is_deleted=0", nativeQuery = true)
	public List<Division> findByCircle(@Param("circle_id") Long circle_id);

	@Query(value = "select * from division where division=:division", nativeQuery = true)
	List<Division> checkDivision(@Param("division") String division);

	@Query(value = "select * from division where division_code=:divisionCode", nativeQuery = true)
	List<Division> checkDivisionCode(@Param("divisionCode") String divisionCode);

	@Query(value = "select * from division where division=:division and div_id!=:id", nativeQuery = true)
	List<Division> checkDivisionForUpdate(@Param("division") String division, @Param("id") Long id);

	@Query(value = "select * from division where division_code=:divisionCode and div_id!=:id", nativeQuery = true)
	List<Division> checkDivisionCodeForUpdate(@Param("divisionCode") String divisionCode, @Param("id") Long id);
	
	@Query(value = "select * from division where is_deleted=0", nativeQuery = true)
	public List<Division> findAllCircleDivision();
	
	public Division findByDivisionId(@Param("divisionId") Long divisionId);

}
