package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ListDivision;

public interface ListDivisionRepository extends JpaRepository<ListDivision,Integer> {

	
	public List<ListDivision> findByuserId(String userid);
}
