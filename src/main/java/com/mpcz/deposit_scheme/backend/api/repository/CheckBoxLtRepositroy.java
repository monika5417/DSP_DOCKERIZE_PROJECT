package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt;

@Repository
public interface CheckBoxLtRepositroy extends JpaRepository<CheckBoxLt, Long>{

	@Query(value="select * from Check_Box_Lt where consumer_Application_Id =:id",nativeQuery=true)
	CheckBoxLt findByConsumerApplicationId(long id);
}