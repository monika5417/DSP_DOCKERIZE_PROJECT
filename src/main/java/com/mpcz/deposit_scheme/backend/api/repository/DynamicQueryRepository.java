package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.DynamicQuery;

@Repository
public interface DynamicQueryRepository extends JpaRepository<DynamicQuery, Long> {
	
    Optional<DynamicQuery> findByQueryNameAndIsActive(String queryName, Integer isActive);

    @Query(value="SELECT * FROM dynamic_queries WHERE query_name = :queryName AND is_active = 1", nativeQuery = true)
    DynamicQuery findByQueryName(@Param("queryName") String queryName);

}
