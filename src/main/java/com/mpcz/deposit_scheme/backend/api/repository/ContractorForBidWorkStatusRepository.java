package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBidWorkStatus;
import com.mpcz.deposit_scheme.backend.api.dto.ContractorForBidWorkStatusDto;
import java.util.List;

@Repository
public interface ContractorForBidWorkStatusRepository extends JpaRepository<ContractorForBidWorkStatus,Long>{

	ContractorForBidWorkStatus save(ContractorForBidWorkStatusDto contractorForBidWorkStatusDto);

	
	@Query(value = "SELECT * from CONTRACTOR_FOR_BID_WORK_STATUS where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNumber", nativeQuery = true)
	List <ContractorForBidWorkStatus> findByConsumerApplicationNumber(String consumerApplicationNumber);
	
	
	@Query(value = "SELECT * from CONTRACTOR_FOR_BID_WORK_STATUS where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo", nativeQuery = true)
	ContractorForBidWorkStatus findByConsumerApplicationNo(String consumerApplicationNo);

	@Query(value = "select * from (\r\n"
			+ "SELECT * from CONTRACTOR_FOR_BID_WORK_STATUS where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo \r\n"
			+ "order by WORK_STATUS_ID desc ) where rownum=1", nativeQuery = true)
	ContractorForBidWorkStatus findByUniqueConsumerApplicationNo(String consumerApplicationNo);

	Optional<ContractorForBidWorkStatus> findByUserId(Long userId);



	

}
