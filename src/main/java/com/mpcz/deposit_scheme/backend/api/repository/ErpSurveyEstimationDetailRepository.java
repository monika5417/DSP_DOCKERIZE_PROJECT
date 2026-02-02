/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ErpSurveyEstimationDetail;

/**
 * @author Monika Rajpoot
 * @since 02-Jan-2026
 * @description ErpSurveyEstimationDetailRepository.java class description
 */

public interface ErpSurveyEstimationDetailRepository extends JpaRepository<ErpSurveyEstimationDetail, Long>{

	Optional<ErpSurveyEstimationDetail> findByConsumerApplicationNo(String consumerApplicationNo);

}
