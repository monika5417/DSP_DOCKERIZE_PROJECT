package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxPtr;

@Repository
public interface CheckBoxPtrRepositroy extends JpaRepository<CheckBoxPtr, Long>{

	@Query(value="select * from Check_Box_Ptr where consumer_Application_Id =:id",nativeQuery=true)
	CheckBoxPtr findByConsumerApplicationId(long id);
}