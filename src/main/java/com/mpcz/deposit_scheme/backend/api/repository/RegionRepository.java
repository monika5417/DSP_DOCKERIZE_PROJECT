package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>  {

	@Query(value = "select * from region where discom_id = :dicomId and is_deleted=0 ", nativeQuery = true)
	public List<Region> findByDiscom(@Param("dicomId") Long regionId);
	
	@Query(value="select * from region where region = :region", nativeQuery=true)
	public List<Region> checkRegion(@Param("region") String region);
	
	@Query(value="select * from region where region_code=:regionCode", nativeQuery=true)
	List<Region> checkRegionCode(@Param("regionCode") String regionCode);
	
	@Query(value="select * from region where region = :region and region_id!=:id", nativeQuery=true)
	public List<Region> checkRegionForUpdate(@Param("region") String region, @Param("id") Long id);
	
	@Query(value="select * from region where region_code=:regionCode and region_id!=:id", nativeQuery=true)
	List<Region> checkRegionCodeForUpdate(@Param("regionCode") String regionCode, @Param("id") Long id);
	
}
