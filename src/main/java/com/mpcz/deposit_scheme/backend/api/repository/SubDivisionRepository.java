package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.SubDivision;

/*
 * This SubDivisionRepository is used to manage SubDivisions
 * 
 * 
 * @author Aditya Vays
 * @version 1.0
 */


@Repository
public interface SubDivisionRepository extends JpaRepository<SubDivision, Long> {
	
	@Query(value = "select * from sub_division where division_id = :divisionId and is_deleted=0", nativeQuery = true)
	public List<SubDivision> findByDivision(@Param("divisionId") Long divisionId);
	
	@Query(value="select * from sub_division where sub_division=:subDivision", nativeQuery=true)
	List<SubDivision> checkSubDivision(@Param("subDivision") String subDivision);
	
	@Query(value="select * from sub_division where sub_div_code=:subDivisionCode", nativeQuery=true)
	List<SubDivision> checkSubDivisionCode(@Param("subDivisionCode") String subDivisionCode);
	
	@Query(value="select * from sub_division where sub_division=:subDivision and subdiv_id!=:id", nativeQuery=true)
	List<SubDivision> checkSubDivisionForUpdate(@Param("subDivision") String subDivision, @Param("id")Long id);
	
	@Query(value="select * from sub_division where sub_div_code=:subDivisionCode and subdiv_id!=:id", nativeQuery=true)
	List<SubDivision> checkSubDivisionCodeForUpdate(@Param("subDivisionCode") String subDivisionCode, @Param("id")Long id);
	
	@Query(value="select subdiv_id from sub_division where division_id=:divisionId", nativeQuery=true)
	List<Long> findByDivisionId(@Param("divisionId") Long divisionId);
}









