package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.NscData;

@Repository
public interface NscRepository extends JpaRepository<NscData, Long> {

	@Query(value = "SELECT * from NSCDATA where IS_ACTIVE = 1", nativeQuery = true)
	List<Map<String,String>> findNscData();
	
	

	@Query(value = "SELECT * from NSCDATA where DC_CODE =:dccode AND IS_ACTIVE =1", nativeQuery = true)
	List<NscData> findDcCode(String dccode);


	@Query(value = "SELECT * from NSCDATA where NSC_ID =:nscid", nativeQuery = true)
	 NscData findByNscId(Long nscid);
	
	
//	public Page<ConsumerApplicationDetail> findNscDataPaginate(
//			@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
//			@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId,
//			@Param("consumerId") Long consumerId, Pageable pageable);

	
	@Query(value = "SELECT * from NSCDATA where APPLICATION_NUMBER =:consumerno", nativeQuery = true)
	 NscData findByConsumerNo(String consumerno);

}

