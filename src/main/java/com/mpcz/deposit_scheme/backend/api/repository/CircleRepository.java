package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;

@Repository
public interface CircleRepository extends JpaRepository<Circle, Long> {

	@Query(value = "select * from circle where region_id = :regionId and is_deleted=0", nativeQuery = true)
	public List<Circle> findByRegion(@Param("regionId") Long regionId);

	@Query(value = "select * from(select circle.*,rownum as c from circle circle) as t where  c >=:minValue and c<:maxValue", nativeQuery = true)
	public List<Circle> findCircleWithPagination(@Param("minValue") int minValue, @Param("maxValue") int maxValue);

	@Query(value = "select * from circle where circle=:circle", nativeQuery = true)
	List<Circle> checkCircle(@Param("circle") String circle);

	@Query(value = "select * from circle where circle_code=:circleCode", nativeQuery = true)
	List<Circle> checkCircleCode(@Param("circleCode") String circleCode);

	@Query(value = "select * from circle where circle=:circle and circle_id!=:id", nativeQuery = true)
	List<Circle> checkCircleForUpdate(@Param("circle") String circle, @Param("id") Long id);

	@Query(value = "select * from circle where circle_code=:circleCode and circle_id!=:id", nativeQuery = true)
	List<Circle> checkCircleCodeForUpdate(@Param("circleCode") String circleCode, @Param("id") Long id);

//	******************sandeep, 05-08-2022, starts*****************************
	@Query(value = "select * from circle where is_active = '1' and is_deleted = '0' order by circle asc ", nativeQuery = true)
	List<Circle> findAllOrderByAsc();
//	******************sandeep, 05-08-2022, ends*******************************
	
	
	@Query(value = "select * from circle where is_deleted=0", nativeQuery = true)
	public List<Circle> findAllCircleByRegion();

}
