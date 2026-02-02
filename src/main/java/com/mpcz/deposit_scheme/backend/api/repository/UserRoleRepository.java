package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mpcz.deposit_scheme.backend.api.domain.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	@Query(value="select user_name  from  ad_user where AD_USER_ID in  (select AD_USER_ID from user_role where ROLE_ID=26 and AD_USER_ID in (select AD_USER_ID  from ad_user where circle_id=:cricleId))",nativeQuery=true)
	public Map< String,String> findRoleByCricleId( Long cricleId);

	@Query(value="select * from user_role where ad_user_id=:adUserId and ROLE_ID=36",nativeQuery = true)
	public UserRole findByAdUserIdAndRoleId(Long adUserId);
}
