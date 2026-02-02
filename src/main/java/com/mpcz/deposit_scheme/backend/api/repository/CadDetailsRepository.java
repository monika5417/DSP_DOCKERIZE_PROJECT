package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.CadDetails;

@Repository
public interface CadDetailsRepository extends JpaRepository<CadDetails, String>{
	
	@Query(value = "select * from CAD_DETAILS ", nativeQuery = true)
	public List<CadDetails> cadDetailsReturn(String consumerApplicationNo);


}
