package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxHt;

@Repository
public interface CheckBoxHtRepositroy extends JpaRepository<CheckBoxHt, Long>{

	@Query(value="select * from Check_Box_Ht where consumer_Application_Id =:id",nativeQuery=true)
	CheckBoxHt findByConsumerApplicationId(long id);
}