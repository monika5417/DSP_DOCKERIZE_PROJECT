package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.ReSampling;

public interface ReSamplingRepository extends JpaRepository<ReSampling, Long> {

	
	public List<ReSampling> findByAuticationIdAndShufflingFlag(String auticationId,Long flag);
	
	public ReSampling findByConAppNoAndId(String auticationId,Long Id);

	public List<ReSampling> findByConAppNoAndShufflingFlag(String consumerApplicationNo, int i);

	
	@Query(value="select * from re_sampling where con_app_no=:consumerApplicationNo and capacity_of_ptr is not null", nativeQuery=true)
	public List<ReSampling> findByConAppNoAndCapacityOfPtrIsNotNull(String consumerApplicationNo);

	public Optional<ReSampling> findByConAppNo(String consumerApplicationNo);

	@Query(value="select * from re_sampling where con_app_no=:consumerApplicationNo and capacity_of_dtr is not null", nativeQuery=true)
	public List<ReSampling> findByConAppNoAndCapacityOfDtrIsNotNull(String consumerApplicationNo);

	
	public List<ReSampling> findByShufflingFlag(Long i);
	
	public List<ReSampling> findByChildApplicationNo(String chaildApplicationNo);
	
	
	@Query(value="select * from re_sampling where con_app_no=:consumerApplicationNo", nativeQuery=true)
	public List<ReSampling> findByConAppNo_1(String consumerApplicationNo);
	
	
	public List<ReSampling> findByAuticationIdAndShufflingFlagAndCircleId(String auticationId,Long flag,Long circleId);
	
	public List<ReSampling> findByAuticationIdAndShufflingFlagAndDivisionId(String auticationId,Long flag,Long circleId);

	public List<ReSampling> findByShufflingFlagAndCircleId(Long flagNo, Long circleId);

	public List<ReSampling> findByShufflingFlagAndDivisionId(Long flagNo, Long divisionId);

	public List<ReSampling> findByShufflingFlagAndRegionId(Long flagNo, Long regionId);

	
//	public Optional<ReSampling> findByConAppNoAndId(String consumerApplicationNo,Long id);
}
