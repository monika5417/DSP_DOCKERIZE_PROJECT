package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;

@Repository
public interface DistributionCenterRepository extends JpaRepository<DistributionCenter, Long> {

	@Query(value = "select * from distribution_center where subdiv_id = :subdivId and is_deleted=0", nativeQuery = true)
	public List<DistributionCenter> findBySubdivision(@Param("subdivId") Long subdivId);

	@Query(value = "select * from distribution_center where dc_name=:dcName", nativeQuery = true)
	List<DistributionCenter> checkDistributionCenter(@Param("dcName") String dcName);

	@Query(value = "select * from distribution_center where dc_code=:dcCode", nativeQuery = true)
	List<DistributionCenter> checkDistributionCenterCode(@Param("dcCode") String dcCode);

	@Query(value = "select * from distribution_center where dc_name=:dcName and dc_id!=:id", nativeQuery = true)
	List<DistributionCenter> checkDistributionCenterForUpdate(@Param("dcName") String dcName, @Param("id") Long id);

	@Query(value = "select * from distribution_center where dc_code=:dcCode and dc_id!=:id", nativeQuery = true)
	List<DistributionCenter> checkDistributionCenterCodeForUpdate(@Param("dcCode") String dcCode, @Param("id") Long id);

//	sandeep, starts
	@Query(value = "SELECT dc.* FROM distribution_center dc WHERE dc.is_active= 1 ORDER BY dc.dc_name ASC", nativeQuery = true)
	public List<DistributionCenter> findAllActiveDistributionCenter();
//	sandeep, ends

	public Optional<DistributionCenter> findDistributionCenterByDcCode(String dcCode);
	
	@Query(value = "select * from distribution_center where DISTRICT_ID = :districtId and is_deleted=0", nativeQuery = true)
	public List<DistributionCenter> findByDistrict(Long districtId);
	
//	@Query(value = "select * from distribution_center where is_deleted=0", nativeQuery = true)
//	public List<DistributionCenter> findAllDistrictDistributionCenter();

	
	@Query(value = "select * from distribution_center where subdiv_id=:subDivisionId and is_deleted=0", nativeQuery = true)
	public List<DistributionCenter> findAllDistrictDistributionCenter(Long subDivisionId);
	
	public List<DistributionCenter> findByDistrict(String districtId);

	@Query(value="select * from distribution_center where ngb_dc_cd=:ngbDcCode and is_deleted=0",nativeQuery=true)
	public DistributionCenter findDistributionCenterByNgbDcCd(String ngbDcCode);

	@Query(value="select * from distribution_center where subdiv_id in (:subDivisionId)",nativeQuery=true)
	public List<DistributionCenter> findByDcSubdivisionIn(List<Long> subDivisionId);
	
	public DistributionCenter findByDcId(Long dcid);
	
	public DistributionCenter findByDcCode(String dcCode);
}
