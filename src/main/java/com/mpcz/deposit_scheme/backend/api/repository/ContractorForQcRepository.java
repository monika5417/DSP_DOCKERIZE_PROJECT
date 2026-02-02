package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorQc;

@Repository
public interface ContractorForQcRepository extends JpaRepository<ContractorQc, Long> {

	
	public Optional<ContractorQc> findById(Long contractorId);

}
