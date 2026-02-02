package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ReturnProjectItem;

public interface ReturnProjectItemRepository extends JpaRepository<ReturnProjectItem, Integer> {

	
	
	public List<ReturnProjectItem> findByProjectNumber(String projectNumber);
}
