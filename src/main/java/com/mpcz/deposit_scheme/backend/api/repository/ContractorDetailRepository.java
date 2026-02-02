package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorDetail;

@Repository
public interface ContractorDetailRepository extends JpaRepository<ContractorDetail, Long> {

	@Query(value = "SELECT * FROM contractor_detail WHERE user_id=:contractorUserId", nativeQuery = true)
	Optional<ContractorDetail> findByContractorUserId(@Param("contractorUserId") Long contractorUserId);
}
