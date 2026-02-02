package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationTypeWiseDoc;

@Repository
public interface ApplicationTypeWiseDocRepository extends JpaRepository<ApplicationTypeWiseDoc, Long> {
	

	@Query(value = "SELECT atwd.* FROM APPLICATION_TYPE_WISE_DOC atwd WHERE atwd.IS_ACTIVE= 1", nativeQuery = true)
	public List<ApplicationTypeWiseDoc> findAllActiveApplicationTypeWiseDoc();

}
