package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxDtr;

@Repository
public interface CheckBoxDtrRepositroy extends JpaRepository<CheckBoxDtr, Long>{

	@Query(value="select * from Check_Box_Dtr where consumer_Application_Id =:id",nativeQuery=true)
	CheckBoxDtr findByConsumerApplicationId(long id);

}
