package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorRemark;

public interface ContractorRemarkRepository extends JpaRepository<ContractorRemark, Long>{

	List<ContractorRemark> findAllByConsumerAppNo(String consumerAppNo);

}
