package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.OytProjectDetails;

public interface OytProjectDetailsRepository extends JpaRepository<OytProjectDetails, Long> {

	List<OytProjectDetails> findDistinctByConsumerApplicationNoAndItemCodeFlag(String consumerApplicationNo, int i);

	List<OytProjectDetails> findByConsumerApplicationNoAndExpType(String expType, String consumerApplicationNo);

}
