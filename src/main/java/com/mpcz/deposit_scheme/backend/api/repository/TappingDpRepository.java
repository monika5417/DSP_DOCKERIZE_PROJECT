package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.TappingDp;

@Repository
public interface TappingDpRepository extends JpaRepository<TappingDp, Long> {

	@Query(value = "select * from TAPPING_DP", nativeQuery = true)
	List<Map<String, Object>> findAllData();

}
