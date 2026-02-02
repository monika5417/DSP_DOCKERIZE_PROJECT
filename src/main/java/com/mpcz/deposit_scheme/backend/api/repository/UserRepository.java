package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByuserIdAndUserCredentials(String userId, String userCredentials);

	public Optional<User> findByUserId(String userId);

	@Query(value = "select * from ad_user where user_email_id=:email", nativeQuery = true)
	List<User> checkEmail(@Param("email") String email);

	@Query(value = "select * from ad_user where mobile_no=:mobile", nativeQuery = true)
	List<User> checkMobileNumber(@Param("mobile") String mobile);

	@Query(value = "select * from ad_user where aadhar_number=:aadhar", nativeQuery = true)
	List<User> checkAadharNumber(@Param("aadhar") String aadhar);

	@Query(value = "select * from ad_user where userid=:userId", nativeQuery = true)
	List<User> checkUserId(@Param("userId") String userId);

	@Query(value = "select * from ad_user where user_email_id=:email and ad_user_id!=:id", nativeQuery = true)
	List<User> checkEmailForUpdate(@Param("email") String email, @Param("id") Long id);

	@Query(value = "select * from ad_user where mobile_no=:mobile and ad_user_id!=:id", nativeQuery = true)
	List<User> checkMobileNumberForUpdate(@Param("mobile") String mobile, @Param("id") Long id);

	@Query(value = "select * from ad_user where aadhar_number=:aadhar and ad_user_id!=:id", nativeQuery = true)
	List<User> checkAadharNumberForUpdate(@Param("aadhar") String aadhar, @Param("id") Long id);

	@Query(value = "select * from ad_user where userid=:userId and ad_user_id!=:id", nativeQuery = true)
	List<User> checkUserIdForUpdate(@Param("userId") String userId, @Param("id") Long id);

	@Query(value = "SELECT * FROM public.ad_user where designation_id =2 and ad_user_id IN(select ad_user_id from user_role ur where ur.role_id in (select r.role_id from role r where r.role ='Amount Adjustment'))", nativeQuery = true)
	List<User> findGMHTBillingUser();

	@Query(value = "SELECT * FROM public.ad_user where designation_id =4 and ad_user_id IN(select ad_user_id from user_role ur where ur.role_id in (select r.role_id from role r where r.role ='Amount Adjustment'))", nativeQuery = true)
	List<User> findAOBillingUser();

	Optional<User> findByUserIdAndAccountNonLocked(String userId, Boolean accountNonLocked);

	Optional<User> findByUserIdAndAccountNonExpired(String userId, Boolean accountNonExpired);

	public Optional<User> findByMobileNo(String mobileNo);

	public Optional<User> findByUserEmailId(String emailId);

	@Query(value = "select * from ad_user where circle_id=:circleId", nativeQuery = true)
	List<User> findUsersByCircleId(@Param("circleId") Long circleId);
	
	
	@Query(value = "select discom_id,REGION_ID,circle_id,div_id,sub_division_id,distribution_center_id,access_level from ad_user where userid =:ID", nativeQuery = true)
	Map<String,Object> findUsersByUserID(@Param("ID") String ID);
	
	@Query(value = "select * from AD_USER au left join USER_ROLE ur on au.AD_USER_ID = ur.AD_USER_ID where au.USERID =:userId",nativeQuery = true)
	public Optional<User> findByUserId1(String userId);
	
	@Query(value="select * from USER_DATA_ACESSLEVEL where USERID =:userId", nativeQuery= true)
	Map<String,Object> findByUserIdInView(@Param("userId") String userId);

	@Query(value="select * from AD_USER where CIRCLE_ID=:cirId and ACCESS_LEVEL=3",nativeQuery = true)
	public List<User> findDataByCircleId(Object cirId);

	public Optional<User> findByAdUserId(Long adUserId);

	
}
