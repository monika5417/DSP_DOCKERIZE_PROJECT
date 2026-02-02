package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicantWorkIssues;

public interface ApplicantFeedbackForWorkIssuesRepository extends JpaRepository<ApplicantWorkIssues, Long> {

	List<ApplicantWorkIssues> findAllByConsumerApplicationNo(String consumerApplicationNo);

}
