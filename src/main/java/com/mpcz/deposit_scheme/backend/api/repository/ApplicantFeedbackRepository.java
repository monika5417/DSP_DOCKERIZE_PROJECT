package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicantFeedbackDSP;

public interface ApplicantFeedbackRepository extends JpaRepository<ApplicantFeedbackDSP, Long> {

	List<ApplicantFeedbackDSP> findAllByConsumerApplicationNo(String consumerApplicationNo);

}
