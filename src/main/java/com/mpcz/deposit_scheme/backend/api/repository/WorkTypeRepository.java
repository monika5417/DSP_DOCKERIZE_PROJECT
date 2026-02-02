package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.WorkType;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {

	@Query(value = "SELECT wt.* FROM work_type wt WHERE wt.IS_ACTIVE= 1 ORDER BY wt.work_type_name ASC", nativeQuery = true)
	public List<WorkType> findAllActiveWorkType();

}
