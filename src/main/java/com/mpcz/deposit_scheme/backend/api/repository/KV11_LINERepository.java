package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.KV11_LINE;

public interface KV11_LINERepository extends JpaRepository<KV11_LINE, String> {

	@Query(value="select * from KV11_LINE", nativeQuery = true)
	List<Map<String,Object>> findAllData();
	
	
}
