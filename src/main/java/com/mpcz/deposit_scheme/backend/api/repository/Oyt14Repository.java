package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.OytOf14;

public interface Oyt14Repository extends JpaRepository<OytOf14, Integer>{
	
	public List<OytOf14> findByconsumerApplciationNo(String applicationNo);

}
