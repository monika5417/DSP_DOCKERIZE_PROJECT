package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.LTLine;

public interface LTLineRepository extends JpaRepository<LTLine, Long> {

	@Query(value = "select * from LT_LINE_35SQMM", nativeQuery = true)
	List<Map<String, Object>> findAllData();

}
