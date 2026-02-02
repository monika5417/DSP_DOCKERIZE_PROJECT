package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Role;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(value = "select * from role where role_id in (select role_id from user_role ur where ad_user_id in (SELECT ad_user_id FROM public.ad_user where designation_id =2))", nativeQuery = true)
	List<Role> findRoleForHTBillingGM();

	@Query(value = "select * from role where role_id in (select role_id from user_role ur where ad_user_id in (SELECT ad_user_id FROM public.ad_user where designation_id =4))", nativeQuery = true)
	List<Role> findRoleForHTBillingAO();

	@Query(value = "SELECT * FROM role WHERE is_active = 1 ORDER BY role ASC", nativeQuery = true)
	List<Role> findAllOrderByASC();
	
	List<Role> findByRoleId(Long roleId);
}
