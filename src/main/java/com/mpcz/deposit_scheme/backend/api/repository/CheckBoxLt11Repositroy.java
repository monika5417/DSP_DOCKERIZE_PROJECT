package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt11;

@Repository
public interface CheckBoxLt11Repositroy extends JpaRepository<CheckBoxLt11, Long>{

	
	@Query(value="select * from Check_Box_Lt_11 where consumer_Application_Id =:id",nativeQuery=true)
	CheckBoxLt11 findByConsumerApplicationId(long id);
}