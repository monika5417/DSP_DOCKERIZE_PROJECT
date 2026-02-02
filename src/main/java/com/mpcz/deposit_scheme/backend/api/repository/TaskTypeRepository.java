package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.TaskType;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

	@Query(value = "SELECT tt.* FROM task_type tt WHERE tt.IS_ACTIVE= 1 ORDER BY tt.task_type_name ASC", nativeQuery = true)
	public List<TaskType> findAllActiveTaskType();
}
