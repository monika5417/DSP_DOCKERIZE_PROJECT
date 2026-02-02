package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;

@Repository
public interface SchemeTypeRepository extends JpaRepository<SchemeType, Long> {

	@Query(value = "SELECT st.* FROM scheme_type st WHERE st.IS_ACTIVE= 1 ORDER BY st.scheme_type_name ASC", nativeQuery = true)
	public List<SchemeType> findAllActiveSchemeType();
}
