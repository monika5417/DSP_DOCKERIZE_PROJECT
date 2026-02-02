package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.NgbStagingData;

public interface NgbStagingDataRepository extends JpaRepository<NgbStagingData, Long> {

	@Query(value="select * from ngb_staging_data where ngb_consumer_no is null",nativeQuery = true)
	List<NgbStagingData> findAllDataWithEmptyConsumerId();

	NgbStagingData findByApplicationNumber(String applicationNo);

}
