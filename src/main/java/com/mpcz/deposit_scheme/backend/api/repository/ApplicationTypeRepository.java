package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationType;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;

@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Long> {

	@Query(value = "SELECT at.* FROM application_type at WHERE at.IS_ACTIVE= 1 ORDER BY at.application_type_name ASC", nativeQuery = true)
	public List<ApplicationType> findAllActiveApplicationType();

}
