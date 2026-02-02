package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {

	@Query(value = "SELECT asr.* FROM APPLICATION_STATUS asr WHERE asr.IS_ACTIVE= 1 ORDER BY asr.APPLICATION_STATUS_ID ASC", nativeQuery = true)
	public List<ApplicationStatus> findAllActiveApplicationStatus();
	

	
	

}
