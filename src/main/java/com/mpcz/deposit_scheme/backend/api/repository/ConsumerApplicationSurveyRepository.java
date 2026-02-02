package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationSurvey;

@Repository
public interface ConsumerApplicationSurveyRepository extends JpaRepository<ConsumerApplicationSurvey, Long> {

	
	Optional<ConsumerApplicationSurvey>  findByConsumersApplicationDetailConsumerApplicationId(Long id);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="delete from CONSUMER_APPLICATION_SURVEY where consumer_application_id=:consumerApplicationId", nativeQuery = true)
	void deleteByConsumerApplicationId(Long consumerApplicationId);
	
 
	
}
