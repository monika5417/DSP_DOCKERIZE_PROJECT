package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.Validation;

public interface ValidationRepository extends JpaRepository<Validation, Long> {
	
	
    @Query(value="select Days from validation where pendency_name=:pendencyData",nativeQuery = true)
	Long findByPendencyName(String pendencyData);

}
