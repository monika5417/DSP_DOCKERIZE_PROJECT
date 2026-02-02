package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorsDetails;

public interface ContractorsDetailsRepository extends JpaRepository<ContractorsDetails, Long> {

}
