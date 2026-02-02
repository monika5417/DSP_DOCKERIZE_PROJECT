package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByApplicationIdException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByVendorIdException;
import com.mpcz.deposit_scheme.projection.CadProjection;
import com.mpcz.deposit_scheme.projection.ConsummerAppDetails;

@Repository
public interface ConsumerApplictionDetailRepository extends JpaRepository<ConsumerApplicationDetail, Long> {

	@Query(value = " SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
			+ " ", countQuery = "  SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad  ", nativeQuery = true)
	public Page<ConsumerApplicationDetail> findByConsumerApplicationDetailsPaginate(Pageable pageable);

	@Query(value = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
			+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
			+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
			+ "left join division div on div.div_id = sub_div.division_id "
			+ "left join circle cir  on cir.circle_id =  div.circle_id "
			+ "left join region reg  on reg.region_id =  cir.region_id "
			+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
			+ " (:discomId =0 or disc.discom_id =:discomId ) " + "and (:regionId =0 or reg.region_id =:regionId ) "
			+ "and  (:circleId =0 or cir.circle_id =:circleId ) " + "and  (:divisionId =0 or div.div_id =:divisionId ) "
			+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) " + "and  (:dcId =0 or dc.dc_id =:dcId ) "
			+ "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) " + "and  (cad.is_active= 1 ) "
			+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
			+ " ", countQuery = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
					+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
					+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
					+ "left join division div on div.div_id = sub_div.division_id "
					+ "left join circle cir  on cir.circle_id =  div.circle_id "
					+ "left join region reg  on reg.region_id =  cir.region_id "
					+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
					+ " (:discomId =0 or disc.discom_id =:discomId ) "
					+ "and (:regionId =0 or reg.region_id =:regionId ) "
					+ "and  (:circleId =0 or cir.circle_id =:circleId ) "
					+ "and  (:divisionId =0 or div.div_id =:divisionId ) "
					+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) "
					+ "and  (:dcId =0 or dc.dc_id =:dcId ) " + "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
					+ "and  (cad.is_active= 1 ) " + "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
					+ " ", nativeQuery = true)
	public Page<ConsumerApplicationDetail> findByConsumerApplicationDetailsUserWisePaginate(
			@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
			@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId,
			@Param("consumerId") Long consumerId, Pageable pageable);

	@Query(value = " SELECT * from CONSUMER_APPLICATION_DETAIL where VENDOR_ID =:id and IS_ACTIVE = 1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findAllConsumerApplicationsByVendorId(@Param("id") Long id)
			throws ConsumerNotFoundByVendorIdException;

	@Query(value = " SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_ID =:consumerAppId", nativeQuery = true)
	public ConsumerApplicationDetail findConsumerApplicationDetailsByConsumerApplicationId(
			@Param("consumerAppId") Long consumerApplicationId) throws ConsumerNotFoundByApplicationIdException;

	// charitra start
	@Query(value = "SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNO and IS_ACTIVE = 1", nativeQuery = true)
	public ConsumerApplicationDetail findByConsumerApplicationNumber(String consumerApplicationNO);

	Optional<ConsumerApplicationDetail> findByConsumerApplicationId(Long consumerApplicationId);

//	@Query(value = "select Max(consumer_application_number) from  consumer_application_detail where consumer_application_number like :codeFormat", nativeQuery = true)
//	public String findMaxApplicationNo(@Param("codeFormat") String codeFormat);

	@Query(value = "select CONCAT('SV',To_CHAR(MAX(TO_NUMBER(SUBSTR(consumer_application_number,3,LENGTH(consumer_application_number)))))) from consumer_application_detail where consumer_application_number like :codeFormat", nativeQuery = true)
	public String findMaxApplicationNo(@Param("codeFormat") String codeFormat);

	@Query(value = "select CONCAT('DS',To_CHAR(MAX(TO_NUMBER(SUBSTR(consumer_application_number,3,LENGTH(consumer_application_number)))))) from consumer_application_detail where consumer_application_number like :codeFormat", nativeQuery = true)
	public String findMaxApplicationNo1(@Param("codeFormat") String codeFormat);

	@Query(value = "select CONCAT('DP',To_CHAR(MAX(TO_NUMBER(SUBSTR(consumer_application_number,3,LENGTH(consumer_application_number)))))) from consumer_application_detail where consumer_application_number like :codeFormat", nativeQuery = true)
	public String findMaxApplicationNo2(@Param("codeFormat") String codeFormat);

	public List<ConsumerApplicationDetail> findConsumerApplicationDetailsByDistributionCenterDcIdAndApplicationStatusApplicationStatusId(
			Long dcId, Long ApplicationStatus);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update CONSUMER_APPLICATION_DETAIL  set DISTRICT_ID = :district  where CONSUMER_APPLICATION_NUMBER = :consumerApplicationNumber", nativeQuery = true)
	public void updatedistrict(@Param("district") Long district,
			@Param("consumerApplicationNumber") String consumerApplicationNumber);

	@Query(value = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
			+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
			+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
			+ "left join division div on div.div_id = sub_div.division_id "
			+ "left join circle cir  on cir.circle_id =  div.circle_id "
			+ "left join region reg  on reg.region_id =  cir.region_id "
			+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
			+ " (:discomId =0 or disc.discom_id =:discomId ) " + "and (:regionId =0 or reg.region_id =:regionId ) "
			+ "and  (:circleId =0 or cir.circle_id =:circleId ) " + "and  (:divisionId =0 or div.div_id =:divisionId ) "
			+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) " + "and  (:dcId =0 or dc.dc_id =:dcId ) "
			+ "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) " + "and  (APPLICATION_STATUS > 5 ) "
			+ "and  (cad.is_active= 1 ) " + "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
			+ " ", countQuery = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
					+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
					+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
					+ "left join division div on div.div_id = sub_div.division_id "
					+ "left join circle cir  on cir.circle_id =  div.circle_id "
					+ "left join region reg  on reg.region_id =  cir.region_id "
					+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
					+ " (:discomId =0 or disc.discom_id =:discomId ) "
					+ "and (:regionId =0 or reg.region_id =:regionId ) "
					+ "and  (:circleId =0 or cir.circle_id =:circleId ) "
					+ "and  (:divisionId =0 or div.div_id =:divisionId ) "
					+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) "
					+ "and  (:dcId =0 or dc.dc_id =:dcId ) " + "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
					+ "and  (APPLICATION_STATUS > 5) " + "and  (cad.is_active= 1 ) "
					+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC " + " ", nativeQuery = true)
	public Page<ConsumerApplicationDetail> findByConsumerApplicationDetailsByApplicationStatusPaginate(
			@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
			@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId,
			@Param("consumerId") Long consumerId, Pageable pageable);

	@Query(value = "SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_ID =:consumerId and IS_ACTIVE = 1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerId(Long consumerId);

//	@Query(value = "select  to_char(sysdate,'DD-MON-YYYY HH:MM') ,cad.short_description_of_work,cad.consumer_application_number,cad.address,c.consumer_name,c.consumer_mobile_no,c.consumer_email_id,ebwa.erp_no,ebwa.estimate_name,ebwa.cgst,ebwa.sgst,ebwa.schema,ebwa.approved_by,ebwa.deposit_amount,ebwa.supervision_amount,ebwa.estimate_date,ebwa.kwload,ebwa.kvaload from consumer_application_detail cad join erp_budget_workflow_amount ebwa on ebwa.erp_budget_workflow_number=cad.erp_workflow_number join consumer c on c.consumer_id=cad.consumer_id and cad.IS_ACTIVE = 1", nativeQuery = true)
//	public List<Map<String,String>> findAllConsumerDetails();

	@Query(value = "SELECT \r\n" + "    TO_CHAR(SYSDATE, 'DD-MON-YYYY HH:MM') AS current_date,\r\n"
			+ "    cad.short_description_of_work,\r\n" + "    cad.consumer_application_number,\r\n"
			+ "    cad.address,\r\n" + "    c.consumer_name,\r\n" + "    c.consumer_mobile_no,\r\n"
			+ "    c.consumer_email_id,\r\n" + "    ebwa.erp_no,\r\n" + "    ebwa.estimate_name,\r\n"
			+ "    ebwa.cgst,\r\n" + "    ebwa.sgst,\r\n" + "    ebwa.schema,\r\n" + "    ebwa.approved_by,\r\n"
			+ "    ebwa.deposit_amount,\r\n" + "    ebwa.supervision_amount,\r\n" + "    ebwa.estimate_date,\r\n"
			+ "    ebwa.kwload,\r\n" + "    ebwa.kvaload \r\n" + "FROM \r\n"
			+ "    consumer_application_detail cad \r\n" + "left JOIN \r\n"
			+ "    erp_budget_workflow_amount ebwa ON ebwa.erp_budget_workflow_number = cad.erp_workflow_number \r\n"
			+ "    left join MKY_PAY_AMNT mpa on cad.consumer_application_number = mpa.CON_APP_NUM\r\n" + "    \r\n"
			+ "JOIN \r\n" + "    consumer c ON c.consumer_id = cad.consumer_id \r\n" + "WHERE \r\n"
			+ "    cad.application_status IN (20, 21)", nativeQuery = true)
	public List<Map<String, String>> findAllConsumerDetails();

	@Query(value = "select to_char(updated+3,'DD/MM/YYYY') from CONSUMER_APPLICATION_DETAIL  where CONSUMER_APPLICATION_NUMBER =:consumerAppNo", nativeQuery = true)
	public String findConsumerDetails(String consumerAppNo);

	@Query(value = "select * from consumer_application_detail cad where cad.Application_Status=20 and IS_ACTIVE = 1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findConsumerStatus();

	public List<ConsumerApplicationDetail> findByHt11KvOrHt33Kv(long idsix, long idseven);

	public List<ConsumerApplicationDetail> findByHt11KvIsNullOrHt33KvIsNull();

	public List<ConsumerApplicationDetail> findByLtIsNotNull();

	public List<ConsumerApplicationDetail> findByLtIsNull();

	@Query(value = "SELECT r.region,r.region_code,\r\n" + "    c.circle,\r\n" + "    c.circle_code,\r\n"
			+ "    d.division,\r\n" + "    d.division_code,\r\n" + "    dc.dc_name,\r\n" + "    dc.dc_code,\r\n"
			+ "    co.consumer_name,\r\n" + "    cad.consumer_application_number,\r\n"
			+ "    co.consumer_mobile_no,\r\n" + "    TO_CHAR(cad.created, 'DD-MM-YY') AS application_date,\r\n"
			+ "    cad.nature_of_work_id,\r\n" + "    n.nature_of_work_name,\r\n" + "    cad.scheme_type_id,\r\n"
			+ "    st.scheme_type_name,\r\n" + "    a.application_status_name,\r\n" + "    a.application_status_id\r\n"
			+ "FROM\r\n" + "     circle c \r\n" + "JOIN region r ON c.region_id = r.region_id\r\n"
			+ "JOIN division d ON c.circle_id = d.circle_id\r\n"
			+ "JOIN sub_division sd ON d.div_id = sd.division_id\r\n"
			+ "JOIN distribution_center dc ON sd.subdiv_id = dc.subdiv_id\r\n"
			+ "JOIN consumer_application_detail cad ON dc.dc_id = cad.dc_id\r\n"
			+ "JOIN application_status a ON cad.application_status = a.application_status_id\r\n"
			+ "JOIN consumer co ON co.consumer_id = cad.consumer_id\r\n"
			+ "JOIN nature_of_work n ON cad.nature_of_work_id = n.nature_of_work_id\r\n"
			+ "JOIN scheme_type st ON cad.scheme_type_id = st.scheme_type_id\r\n"
			+ "WHERE    c.circle_code=:circleCode or d.division_code=:divisionCode or r.region_code=:regionCode or dc.dc_code =:dcCode \r\n"
			+ "\r\n" + "ORDER BY c.circle", nativeQuery = true)
	List<Map<String, ?>> getDetailsByDcCodeForMis(@Param("dcCode") String dcCode,
			@Param("circleCode") String circleCode, @Param("divisionCode") String divisionCode,
			@Param("regionCode") String regionCode);

	public List<ConsumerApplicationDetail> findByNatureOfWorkType_NatureOfWorkTypeId(long idsix);

//	o&M
//	public List<ConsumerApplicationDetail> findByLtIsNotNullAndNatureOfWorkType_NatureOfWorkTypeIdOrNatureOfWorkType_NatureOfWorkTypeId(long six,long one);

	// o&M
	public List<ConsumerApplicationDetail> findByNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNotNullOrNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNotNull(
			long six, long one);

//	HTM
//	public List<ConsumerApplicationDetail> findByLtIsNullAndNatureOfWorkType_NatureOfWorkTypeIdOrNatureOfWorkType_NatureOfWorkTypeId(long six,long one);

	// HTM
	public List<ConsumerApplicationDetail> findByNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNullOrNatureOfWorkType_NatureOfWorkTypeIdAndLtIsNull(
			long six, long one);

	@Query(value = "select * from consumer_application_detail\r\n"
			+ "where nature_of_work_id in (1,6)", nativeQuery = true)
	public List<ConsumerApplicationDetail> findConsumerApplicationDetails();

	@Query(value = "select * from consumer_application_detail where samagra_id=:id", nativeQuery = true)
	public ConsumerApplicationDetail getConsumerApplicationDetailsBySamagraId(@Param("id") String samagraId);

	@Query(value = "select * from consumer_application_detail where district_id =:id", nativeQuery = true)
	public List<ConsumerApplicationDetail> getConsumerApplicationDetailsByDistrictId(@Param("id") String districtId);

//	@Query(value = "select * from consumer_application_detail where natureOfWorkType_NATURE_OF_WORK_ID =:i ", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByNatureOfWorkType_NatureOfWorkTypeId(Long i);

	public List<ConsumerApplicationDetail> findByNatureOfWorkType_NatureOfWorkTypeIdAndDistributionCenter_dcCode(Long i,
			String dcCode);

	@Query(value = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
			+ " left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
			+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
			+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
			+ "left join division div on div.div_id = sub_div.division_id "
			+ "left join circle cir  on cir.circle_id =  div.circle_id "
			+ "left join region reg  on reg.region_id =  cir.region_id "
			+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
			+ "(:discomId =0 or disc.discom_id =:discomId ) AND" + "(:regionId =0 or reg.region_id =:regionId ) "
			+ "and  (:circleId =0 or cir.circle_id =:circleId ) " + "and  (:divisionId =0 or div.div_id =:divisionId ) "
			+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) " + "and  (:dcId =0 or dc.dc_id =:dcId ) "
			+ "and  (APPLICATION_STATUS > 3 ) " + "and  (cad.is_active= 1 )"
			+ "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
			+ "AND UPPER(cad.CONSUMER_NAME) LIKE UPPER(CONCAT(CONCAT('%',:filterValue),'%') )"
			+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
			+ " ", countQuery = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
					+ " left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
					+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
					+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
					+ "left join division div on div.div_id = sub_div.division_id "
					+ "left join circle cir  on cir.circle_id =  div.circle_id "
					+ "left join region reg  on reg.region_id =  cir.region_id "
					+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
					+ "(:discomId =0 or disc.discom_id =:discomId ) AND"
					+ "(:regionId =0 or reg.region_id =:regionId ) "
					+ "and  (:circleId =0 or cir.circle_id =:circleId ) "
					+ "and  (:divisionId =0 or div.div_id =:divisionId ) "
					+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) "
					+ "and  (:dcId =0 or dc.dc_id =:dcId ) " + "and  (APPLICATION_STATUS > 3 ) "
					+ "and  (cad.is_active= 1 )" + "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
					+ "AND UPPER(cad.CONSUMER_NAME) LIKE UPPER(CONCAT(CONCAT('%',:filterValue),'%') )"
					+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC " + " ", nativeQuery = true)
	public Page<ConsumerApplicationDetail> findByConsumerApplicationDetailsUserWiseApplicantNamePaginate(
			@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
			@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId,
			@Param("consumerId") Long consumerId, @Param("filterValue") String filterValue, Pageable pageable);

	@Query(value = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
			+ " left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
			+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
			+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
			+ "left join division div on div.div_id = sub_div.division_id "
			+ "left join circle cir  on cir.circle_id =  div.circle_id "
			+ "left join region reg  on reg.region_id =  cir.region_id "
			+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
			+ "(:discomId =0 or disc.discom_id =:discomId ) AND" + "(:regionId =0 or reg.region_id =:regionId ) "
			+ "and  (:circleId =0 or cir.circle_id =:circleId ) " + "and  (:divisionId =0 or div.div_id =:divisionId ) "
			+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) " + "and  (:dcId =0 or dc.dc_id =:dcId ) "
			+ "and  (APPLICATION_STATUS > 5 ) " + "and  (cad.is_active= 1 )"
			+ "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
			+ "AND UPPER(cad.CONSUMER_APPLICATION_NUMBER) LIKE UPPER(CONCAT(CONCAT('%',:filterValue),'%') )"
			+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
			+ " ", countQuery = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
					+ " left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
					+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
					+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
					+ "left join division div on div.div_id = sub_div.division_id "
					+ "left join circle cir  on cir.circle_id =  div.circle_id "
					+ "left join region reg  on reg.region_id =  cir.region_id "
					+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
					+ "(:discomId =0 or disc.discom_id =:discomId ) AND "
					+ "(:regionId =0 or reg.region_id =:regionId ) "
					+ "and  (:circleId =0 or cir.circle_id =:circleId ) "
					+ "and  (:divisionId =0 or div.div_id =:divisionId ) "
					+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) "
					+ "and  (:dcId =0 or dc.dc_id =:dcId ) " + "and  (APPLICATION_STATUS > 5 ) "
					+ "and  (cad.is_active= 1 )" + "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
					+ "AND UPPER(cad.CONSUMER_APPLICATION_NUMBER) LIKE UPPER(CONCAT(CONCAT('%',:filterValue),'%') )"
					+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC " + " ", nativeQuery = true)
	public Page<ConsumerApplicationDetail> findByConsumerApplicationDetailsUserWiseApplicationNumberPaginate(

			@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
			@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId,
			@Param("consumerId") Long consumerId, @Param("filterValue") String filterValue, Pageable pageable);

//	bhanu sir 
	@Query(value = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
			+ "left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
			+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
			+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
			+ "left join division div on div.div_id = sub_div.division_id "
			+ "left join circle cir  on cir.circle_id =  div.circle_id "
			+ "left join region reg  on reg.region_id =  cir.region_id "
			+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
			+ "(:discomId =0 or disc.discom_id =:discomId ) AND " + "(:regionId =0 or reg.region_id =:regionId ) "
			+ "and  (:circleId =0 or cir.circle_id =:circleId ) " + "and  (:divisionId =0 or div.div_id =:divisionId ) "
			+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) " + "and  (:dcId =0 or dc.dc_id =:dcId ) "
			+ "and  (APPLICATION_STATUS > 5 ) " + "and  (cad.is_active= 1 ) "
			+ "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) " + "AND APPLICATION_STATUS = :filterValue "
			+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
			+ " ", countQuery = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
					+ "left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
					+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
					+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
					+ "left join division div on div.div_id = sub_div.division_id "
					+ "left join circle cir  on cir.circle_id =  div.circle_id "
					+ "left join region reg  on reg.region_id =  cir.region_id "
					+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
					+ "(:discomId =0 or disc.discom_id =:discomId ) AND "
					+ "(:regionId =0 or reg.region_id =:regionId ) "
					+ "and  (:circleId =0 or cir.circle_id =:circleId ) "
					+ "and  (:divisionId =0 or div.div_id =:divisionId ) "
					+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) "
					+ "and  (:dcId =0 or dc.dc_id =:dcId ) " + "and  (APPLICATION_STATUS > 5 ) "
					+ "and  (cad.is_active= 1 ) " + "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
					+ "AND APPLICATION_STATUS = :filterValue" + " ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
					+ " ", nativeQuery = true)
	public Page<ConsumerApplicationDetail> findByConsumerApplicationDetailsUserWiseApplicationStatusPaginate(
			@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
			@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId,
			@Param("consumerId") Long consumerId, @Param("filterValue") String filterValue, Pageable pageable);

	@Query(value = "  SELECT\r\n" + "    sub.transaction_date,\r\n" + "    sub.transaction_id,\r\n"
			+ "    sub.amount BILLDESK_AMOUNT,\r\n" + "    sub.ORDER_ID,\r\n" + "    erp.supervision_amount,\r\n"
			+ "    erp.cgst,\r\n" + "    erp.sgst,\r\n" + "     sub.BANK_REF_NO,\r\n" + "        sub.MERCID,\r\n"
			+ "        erp.DEPOSIT_AMOUNT,\r\n" + "        erp.KVALOAD SUPPLY_AFFORDING_CHARGE,\r\n"
			+ "        erp.KWLOAD SYSTEM_DEVELOPEMENT_CHARGE\r\n" + "FROM\r\n"
			+ "    erp_budget_workflow_amount erp\r\n" + "JOIN (\r\n" + "    SELECT\r\n"
			+ "        cad.consumer_application_number,\r\n" + "        cad.ERP_WORKFLOW_NUMBER,\r\n"
			+ "        bps.transaction_date,\r\n" + "        bps.transaction_id,\r\n" + "        bps.amount,\r\n"
			+ "        bps.ORDER_ID,\r\n" + "        bps.BANK_REF_NO,\r\n" + "        bps.MERCID\r\n" + "    FROM\r\n"
			+ "        billdesk_payment_res bps\r\n" + "    JOIN\r\n"
			+ "        consumer_application_detail cad ON bps.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "    WHERE\r\n"
			+ "  --      TO_DATE(SUBSTR(bps.transaction_date, 0, INSTR(bps.transaction_date, 'T') - 1), 'YYYY-MM-DD') = TO_DATE(:input_date, 'YYYY-MM-DD')\r\n"
			+ "     TO_DATE(substr(bps.transaction_date,0,instr(bps.transaction_date,'T') - 1),'YYYY-MM-DD') \r\n"
			+ "    BETWEEN  TO_DATE(:toDate,'YYYY-MM-DD') AND TO_DATE(:fromDate,'YYYY-MM-DD')\r\n"
			+ "        AND bps.auth_status = '0300'\r\n"
			+ ") sub ON erp.ERP_NO = sub.ERP_WORKFLOW_NUMBER", nativeQuery = true)
	List<Map<String, ?>> getBillDeskMisByDate(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

//	@Query(value = "SELECT \r\n" + "    NGB_DC_CD,\r\n" + "     COUNT(*) AS transaction_count,\r\n"
//			+ "   SUM(amount) AS total_sum,\r\n"
//			+ "    TO_CHAR(TO_DATE(SUBSTR(transaction_date, 0, INSTR(transaction_date, 'T') - 1), 'YYYY-MM-DD'),'YYYY-MM-DD')  AS transaction_date \r\n"
//			+ "FROM \r\n" + "    BILLDESK_PAYMENT_RES bps\r\n" + "JOIN \r\n"
//			+ "    CONSUMER_APPLICATION_DETAIL cad ON bps.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
//			+ "JOIN\r\n" + "    DISTRIBUTION_CENTER  dc on cad.dc_id = dc.dc_id  \r\n" + "WHERE \r\n"
//			+ "    AUTH_STATUS = '0300'\r\n"
//			+ "    AND TO_DATE(SUBSTR(transaction_date, 0, INSTR(transaction_date, 'T') - 1), 'YYYY-MM-DD') \r\n"
//			+ "        BETWEEN TO_DATE(:toDate, 'YYYY-MM-DD') AND TO_DATE(:fromDate, 'YYYY-MM-DD')\r\n"
//			+ "GROUP BY \r\n" + "    NGB_DC_CD, \r\n" + "    transaction_date", nativeQuery = true)
//	List<Map<String, ?>> getBillDesk(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

	@Query(value = "select * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo", nativeQuery = true)
	public CadProjection findByConsumerApplicationNO(@Param("consumerApplicationNo") String consumerApplicationNo);

//	@Query(value = "SELECT cad.CONSUMER_APPLICATION_ID FROM CONSUMER_APPLICATION_DETAIL cad "
//	        + "WHERE CONSUMER_APPLICATION_NUMBER = :consumerApplicationNo", nativeQuery = true)
//	public ConsumerApplicationDetail findByConsumerApplicationNO(@Param("consumerApplicationNo") String consumerApplicationNo);

//	@Query(value = "SELECT cad.CONSUMER_APPLICATION_ID from CONSUMER_APPLICATION_DETAIL cad "
//			+ "left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
//			+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
//			+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
//			+ "left join division div on div.div_id = sub_div.division_id "
//			+ "left join circle cir  on cir.circle_id =  div.circle_id "
//			+ "left join region reg  on reg.region_id =  cir.region_id "
//			+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "  (cad.is_active= 1 ) "
//			 + "and  (cad.CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo ) ", nativeQuery = true)
//	public ConsumerApplicationDetail findByConsumerApplicationNO(@Param("consumerApplicationNo") String consumerApplicationNo);

	@Query(value = "SELECT \r\n" + "    CIR.CIRCLE AS NAME_OF_CIRCLE,\r\n" + "    DIV.DIVISION AS NAME_OF_DIVISION,\r\n"
			+ "    DC.DC_NAME AS NAME_OF_DC,\r\n" + "    ASA.APPLICATION_STATUS_NAME AS APPLICATION_STATUS,\r\n"
			+ "    CAD.CONSUMER_APPLICATION_NUMBER AS APPLICATION_NO,\r\n"
			+ "    CAD.CONSUMER_NAME AS NAME_OF_APPLICANT,\r\n" + "    ST.SCHEME_TYPE_NAME AS NAME_OF_SCHEME,\r\n"
			+ "    NOW.NATURE_OF_WORK_NAME AS NATURE_OF_WORK,\r\n"
			+ "    TO_CHAR(CAD.CREATED, 'DD-MM-YY') AS DATE_OF_CREATION,\r\n"
			+ "    CAD.CREATED_BY AS CREATED_BY_LOGIN,\r\n"
			+ "    BPR.TRANSACTION_ID AS REGISTRATION_TRANSACTION_ID,\r\n" + "    BPR.AMOUNT AS PAYMENT_AMOUNT,\r\n"
			+ "    TO_DATE(SUBSTR(BPR.TRANSACTION_DATE, 1, 10), 'YYYY-MM-DD') AS DATE_OF_REGISTRATION,\r\n"
			+ "    BPR1.TRANSACTION_ID AS DEMAND_TRANSACTION_ID,\r\n"
			+ "    BPR1.AMOUNT AS SUPERVISION_PAYMENT_AMOUNT,\r\n"
			+ "    TO_DATE(SUBSTR(BPR1.TRANSACTION_DATE, 1, 10), 'YYYY-MM-DD') AS DATE_OF_SUPERVISION_PAYMENT,\r\n"
			+ "    VWO.WORK_ORDER_NO AS WORK_ORDER_NO,\r\n" + "    VWO.WORK_ORDER_DATE AS WORK_ORDER_DATE,\r\n"
			+ "    CAD.WORK_COMPLETION_DATE AS WORK_COMP_DATE_BY_CONTRA,\r\n" + "    ERP.CGST,\r\n"
			+ "    ERP.SGST,\r\n" + "    ERP.ESTIMATE_SANCTION_NO,\r\n" + "    ERP.ESTIMATE_NAME,\r\n"
			+ "    ERP.LOCATION,\r\n" + "    ERP.SCHEMA,\r\n" + "    ERP.ESTIMATE_AMOUNT,\r\n"
			+ "    ERP.APPROVED_BY,\r\n" + "    ERP.ESTIMATE_STATUS,\r\n"
			+ "    ERP.ERP_NO AS KMY_ERP_ESTIMATE_NUMBER,\r\n" + "    MKY.ABEDAN_SULK,\r\n"
			+ "    MKY.TOTAL_AMOUNT,\r\n" + "    MKY.SECURITY_DEPOSIT,\r\n" + "    MKY.SCHEME_CODE,\r\n"
			+ "    MKY.PAYALE_AMOUNT,\r\n" + "    MKY.MPMK_MAF_BILL,\r\n" + "    MKY.GOV_MAF_BILL,\r\n"
			+ "    MKY.CREATED,\r\n" + "    MKY.CARRY_AMOUNT_BY_APPLICANT,\r\n"
			+ "    CFB.CONTRACTOR_NAME AS NAME_OF_CONTRACTOR,\r\n" + "    CFB.CONTRACTOR_STATE,\r\n"
			+ "    CFB.CONTRACTOR_ID\r\n" + "    ,\r\n" + "    BPR2.AMOUNT AS REVISE_PAYMENT_AMOUNT,\r\n"
			+ "    BPR2.TRANSACTION_ID AS REVISE_TRANSACTION_ID\r\n" + "FROM \r\n"
			+ "    DEPOSITE_SCHEMA.CONSUMER_APPLICATION_DETAIL CAD\r\n" + "    LEFT JOIN CONTRACTOR_FOR_BID CFB \r\n"
			+ "        ON CFB.CONSUMER_APPLICATION_NUMBER = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "    JOIN APPLICATION_STATUS ASA \r\n"
			+ "        ON ASA.APPLICATION_STATUS_ID = CAD.APPLICATION_STATUS\r\n" + "    JOIN NATURE_OF_WORK NOW \r\n"
			+ "        ON NOW.NATURE_OF_WORK_ID = CAD.NATURE_OF_WORK_ID\r\n" + "    JOIN SCHEME_TYPE ST \r\n"
			+ "        ON ST.SCHEME_TYPE_ID = CAD.SCHEME_TYPE_ID\r\n" + "    LEFT JOIN (\r\n" + "        SELECT * \r\n"
			+ "        FROM BILLDESK_PAYMENT_RES \r\n" + "        WHERE BILL_DESK_RES_ID IN (\r\n"
			+ "            SELECT MAX(BILL_DESK_RES_ID)\r\n" + "            FROM BILLDESK_PAYMENT_RES\r\n"
			+ "            WHERE AMOUNT = 1180 \r\n"
			+ "              AND TRANSACTION_ERROR_DESC = 'Transaction Successful'\r\n"
			+ "            GROUP BY CONSUMER_APPLICATION_NO\r\n" + "        )\r\n" + "    ) BPR \r\n"
			+ "        ON BPR.CONSUMER_APPLICATION_NO = CAD.CONSUMER_APPLICATION_NUMBER\r\n" + "    LEFT JOIN (\r\n"
			+ "        SELECT * \r\n" + "        FROM BILLDESK_PAYMENT_RES \r\n"
			+ "        WHERE BILL_DESK_RES_ID IN (\r\n" + "            SELECT MAX(BILL_DESK_RES_ID)\r\n"
			+ "            FROM BILLDESK_PAYMENT_RES\r\n" + "           WHERE AUTH_STATUS='0300'\r\n"
			+ "            and ADDITIONAL_INFO1 = 'Demand_fees'\r\n"
			+ "            GROUP BY CONSUMER_APPLICATION_NO\r\n" + "        )\r\n" + "    ) BPR1 \r\n"
			+ "        ON BPR1.CONSUMER_APPLICATION_NO = CAD.CONSUMER_APPLICATION_NUMBER\r\n" + "    LEFT JOIN (\r\n"
			+ "        SELECT * \r\n" + "        FROM BILLDESK_PAYMENT_RES \r\n"
			+ "        WHERE BILL_DESK_RES_ID IN (\r\n" + "            SELECT MAX(BILL_DESK_RES_ID)\r\n"
			+ "            FROM BILLDESK_PAYMENT_RES\r\n" + "            WHERE AUTH_STATUS='0300'\r\n"
			+ "            and ADDITIONAL_INFO1 = 'Revised_Demand_fees'\r\n"
			+ "            GROUP BY CONSUMER_APPLICATION_NO\r\n" + "        )\r\n"
			+ "    ) BPR2  ON BPR2.CONSUMER_APPLICATION_NO = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "    LEFT JOIN VENDOR_WORK_ORDER VWO \r\n"
			+ "        ON VWO.CONSUMER_APPLICATION_NO = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "    JOIN DISTRIBUTION_CENTER DC \r\n" + "        ON DC.DC_ID = CAD.DC_ID\r\n"
			+ "    JOIN SUB_DIVISION SD \r\n" + "        ON SD.SUBDIV_ID = DC.SUBDIV_ID\r\n"
			+ "    JOIN DIVISION DIV \r\n" + "        ON DIV.DIV_ID = SD.DIVISION_ID\r\n" + "    JOIN CIRCLE CIR \r\n"
			+ "        ON CIR.CIRCLE_ID = DIV.CIRCLE_ID\r\n" + "    LEFT JOIN MKY_PAY_AMNT MKY \r\n"
			+ "        ON MKY.CON_APP_NUM = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "    LEFT JOIN ERP_BUDGET_WORKFLOW_AMOUNT ERP \r\n"
			+ "        ON ERP.ERP_NO = CAD.ERP_WORKFLOW_NUMBER\r\n" + "WHERE \r\n"
			+ "    CAD.NATURE_OF_WORK_ID IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)\r\n"
			+ "    AND CAD.CONSUMER_APPLICATION_NUMBER = :consumerApplicationNo\r\n" + "    AND CAD.IS_ACTIVE = 1\r\n"
			+ "    AND CAD.IS_DELETED = 0\r\n" + "ORDER BY \r\n" + "    APPLICATION_STATUS", nativeQuery = true)
	public List<Map<String, Object>> cadDetailsReturn(@Param("consumerApplicationNo") String consumerApplicationNo);

	@Query(value = "select consumer_application_number from "
			+ "(select consumer_application_number,count(*) from consumer_application_detail group by consumer_application_number,application_status having count(*)>1)", nativeQuery = true)
	public List<Map<String, String>> settlementNautreOfWork();

	@Query(value = " SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_NUMBER =:consumerAppNo", nativeQuery = true)
	public List<ConsumerApplicationDetail> UpadateConsumerApplicationDetailsByDuplicateAppNumber(
			@Param("consumerAppNo") String consumerAppNo) throws ConsumerNotFoundByApplicationIdException;

	@Query(value = "SELECT\r\n" + "    c.circle,\r\n" + "    dc.dc_name,\r\n"
			+ "  TO_CHAR(TO_DATE(SUBSTR(transaction_date, 0, INSTR(transaction_date, 'T') - 1), 'YYYY-MM-DD'), 'YYYY-MM-DD') AS transaction_date,\r\n"
			+ "    SUM(erp.supervision_amount) AS supervision_amount,\r\n"
			+ "    SUM(erp.deposit_amount) AS deposit_amount,\r\n" + "    SUM(erp.cgst) AS cgst,\r\n"
			+ "    SUM(erp.sgst) AS sgst,\r\n" + "    erp.kwload AS system_development_charge,\r\n"
			+ "    erp.kvaload AS supply_affording_charge,\r\n" + "    erp.colony_or_slum,\r\n"
			+ "    erp.je_return_amount,\r\n" + "    mky.total_amount,\r\n" + "    mky.gov_maf_bill,\r\n"
			+ "    mky.mpmk_maf_bill,\r\n" + "    mky.payale_amount,\r\n" + "    mky.abedan_sulk,\r\n"
			+ "    mky.security_deposit,\r\n" + "    mky.carry_amount_by_applicant\r\n"
			+ "FROM billdesk_payment_res bps\r\n"
			+ "JOIN consumer_application_detail cad ON bps.consumer_application_no = cad.consumer_application_number\r\n"
			+ "JOIN distribution_center dc ON dc.dc_id = cad.dc_id\r\n"
			+ "JOIN sub_division sd ON sd.subdiv_id = dc.subdiv_id\r\n"
			+ "JOIN division d ON sd.division_id = d.div_id\r\n" + "JOIN circle c ON c.circle_id = d.circle_id\r\n"
			+ "LEFT JOIN erp_budget_workflow_amount erp ON cad.erp_workflow_number = erp.erp_no\r\n"
			+ "LEFT JOIN mky_pay_amnt mky ON mky.erp_num = erp.erp_no\r\n"
			+ "WHERE TO_DATE(SUBSTR(bps.transaction_date, 0, INSTR(bps.transaction_date, 'T') - 1), 'YYYY-MM-DD') = TO_DATE('2023-11-14', 'YYYY-MM-DD')\r\n"
			+ "GROUP BY dc.dc_name, c.circle, TO_DATE(SUBSTR(bps.transaction_date, 0, INSTR(bps.transaction_date, 'T') - 1), 'YYYY-MM-DD'),\r\n"
			+ "    erp.supervision_amount, erp.deposit_amount, erp.cgst, erp.sgst, erp.kwload, erp.kvaload, erp.colony_or_slum, erp.je_return_amount,\r\n"
			+ "    mky.total_amount, mky.gov_maf_bill, mky.mpmk_maf_bill, mky.payale_amount, mky.abedan_sulk, mky.security_deposit, mky.carry_amount_by_applicant", nativeQuery = true)
	public List<Map<String, Object>> getCraData(@Param("date") String date);

	@Query(value = "select consumer_application_number,count(*),nature_of_work_id,application_status from consumer_application_detail  where\r\n"
			+ "consumer_application_detail.nature_of_work_id<>8 group by consumer_application_number ,nature_of_work_id,application_status having count(*)>1", nativeQuery = true)
	public List<Map<String, String>> findListByConsumerApplicaation();

	@Query(value = "select *  from consumer_application_detail where CONSUMER_APPLICATION_NUMBER =:consumerAppNo", nativeQuery = true)
	public List<ConsumerApplicationDetail> findListByConsumerApplicatonNumber(
			@Param("consumerAppNo") String consumerAppNo);

	@Query(value = "select *  from consumer_application_detail where SCHEME_TYPE_ID=1 and APPLICATION_STATUS>=12", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByScemetypeId();

	@Query(value = "SELECT *\r\n" + "FROM CONSUMER_APPLICATION_DETAIL\r\n" + "WHERE nature_of_work_id = 5\r\n"
			+ "  AND application_status = 21\r\n" + "  AND payment_date IS NULL", nativeQuery = true)
	public List<ConsumerApplicationDetail> findAllOytApplication();

	@Query(value = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
			+ "left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
			+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
			+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
			+ "left join division div on div.div_id = sub_div.division_id "
			+ "left join circle cir  on cir.circle_id =  div.circle_id "
			+ "left join region reg  on reg.region_id =  cir.region_id "
			+ "left join nature_of_work now  on now.nature_of_work_id =  cad.nature_of_work_id "
			+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
			+ "(:discomId =0 or disc.discom_id =:discomId ) AND " + "(:regionId =0 or reg.region_id =:regionId ) "
			+ "and  (:circleId =0 or cir.circle_id =:circleId ) " + "and  (:divisionId =0 or div.div_id =:divisionId ) "
			+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) " + "and  (:dcId =0 or dc.dc_id =:dcId ) "
			+ "and  (APPLICATION_STATUS > 5 ) " + "and  (cad.is_active= 1 ) "
			+ "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) " + " AND now.nature_of_work_id = :filterValue "
			+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
			+ " ", countQuery = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
					+ "left join consumer con on cad.CONSUMER_ID = con.CONSUMER_ID "
					+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
					+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
					+ "left join division div on div.div_id = sub_div.division_id "
					+ "left join circle cir  on cir.circle_id =  div.circle_id "
					+ "left join region reg  on reg.region_id =  cir.region_id "
					+ "left join nature_of_work now  on now.nature_of_work_id =  cad.nature_of_work_id "
					+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
					+ "(:discomId =0 or disc.discom_id =:discomId ) AND "
					+ "(:regionId =0 or reg.region_id =:regionId ) "
					+ "and  (:circleId =0 or cir.circle_id =:circleId ) "
					+ "and  (:divisionId =0 or div.div_id =:divisionId ) "
					+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) "
					+ "and  (:dcId =0 or dc.dc_id =:dcId ) " + "and  (APPLICATION_STATUS > 5 ) "
					+ "and  (cad.is_active= 1 ) " + "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
					+ "AND now.nature_of_work_id = :filterValue" + " ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
					+ " ", nativeQuery = true)
	public Page<ConsumerApplicationDetail> findByConsumerApplicationDetailsUserWiseNatureOfWorkPaginate(Long discomId,
			Long regionId, Long circleId, Long divisionId, Long subDivisionId, Long dcId, Long consumerId,
			String filterValue, Pageable paging);

	@Query(value = "SELECT *\r\n" + "FROM CONSUMER_APPLICATION_DETAIL CAD\r\n"
			+ "WHERE CAD.APPLICATION_STATUS in (6,7,12)\r\n" + "AND CAD.NATURE_OF_WORK_ID!=8\r\n"
			+ "AND NOT EXISTS (\r\n" + "    SELECT 1\r\n" + "    FROM BILLDESK_PAYMENT_RES BPR\r\n"
			+ "    WHERE CAD.CONSUMER_APPLICATION_NUMBER = BPR.CONSUMER_APPLICATION_NO\r\n"
			+ "    AND BPR.ADDITIONAL_INFO1 LIKE '%Registration_Fees%' and BPR.AUTH_STATUS='0300'\r\n" + ")\r\n"
			+ "AND NOT EXISTS (\r\n" + "    SELECT 1\r\n" + "    FROM MANUAL_PAYMENT MP\r\n"
			+ "    WHERE CAD.CONSUMER_APPLICATION_NUMBER = MP.CON_APP_NO\r\n"
			+ "    AND MP.PAYMENT_TYPE LIKE '%Registration_Fees%' and MP.AUTH_STATUS='0300'\r\n"
			+ ")", nativeQuery = true)
	List<ConsumerApplicationDetail> RegistrationFeesNotDoneRevertApp();

	@Query(value = "select * from consumer_application_detail where erp_workflow_number = :erpNo and is_active=1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByErpNo(String erpNo);

	@Query(value = "select * from \r\n" + "consumer_application_detail cad \r\n"
			+ "left join  CONTRACTOR_FOR_BID_WORK_STATUS cfbs on \r\n"
			+ "cad.CONSUMER_APPLICATION_NUMBER = cfbs.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "where application_status=22 and cfbs.CONSUMER_APPLICATION_NUMBER is null", nativeQuery = true)
	public List<ConsumerApplicationDetail> saveContractorExpectedDates();

//	 @Query(value = "SELECT cad.name, sub_div.div_name from CONSUMER_APPLICATION_DETAIL cad "
//				+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
//				+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
//				+ "left join division div on div.div_id = sub_div.division_id "
//				+ "left join circle cir  on cir.circle_id =  div.circle_id "
//				+ "left join region reg  on reg.region_id =  cir.region_id "
//				+ "left join discom disc  on disc.discom_id =  reg.discom_id "
//				+ "where "
//				+ " (:discomId =0 or disc.discom_id =:discomId ) " + "and (:regionId =0 or reg.region_id =:regionId ) "
//				+ "and  (:circleId =0 or cir.circle_id =:circleId ) " + "and  (:divisionId =0 or div.div_id =:divisionId ) "
//				+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) " + "and  (:dcId =0 or dc.dc_id =:dcId ) "
//				+ "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) " + "and  (APPLICATION_STATUS > 5 ) "
//				+ "and  (cad.is_active= 1 ) " + "ORDER BY cad.CONSUMER_APPLICATION_ID DESC "
//				+ " ", countQuery = "SELECT cad.* from CONSUMER_APPLICATION_DETAIL cad "
//						+ "left join distribution_center  dc on dc.dc_id = cad.dc_id "
//						+ "left join sub_division sub_div on sub_div.SUBDIV_ID  = dc.subdiv_id "
//						+ "left join division div on div.div_id = sub_div.division_id "
//						+ "left join circle cir  on cir.circle_id =  div.circle_id "
//						+ "left join region reg  on reg.region_id =  cir.region_id "
//						+ "left join discom disc  on disc.discom_id =  reg.discom_id " + "where "
//						+ " (:discomId =0 or disc.discom_id =:discomId ) "
//						+ "and (:regionId =0 or reg.region_id =:regionId ) "
//						+ "and  (:circleId =0 or cir.circle_id =:circleId ) "
//						+ "and  (:divisionId =0 or div.div_id =:divisionId ) "
//						+ "and  (:subDivisionId =0 or sub_div.SUBDIV_ID =:subDivisionId ) "
//						+ "and  (:dcId =0 or dc.dc_id =:dcId ) " + "and  (:consumerId =0 or cad.CONSUMER_ID =:consumerId ) "
//						+ "and  (APPLICATION_STATUS > 5) " + "and  (cad.is_active= 1 ) "
//						+ "ORDER BY cad.CONSUMER_APPLICATION_ID DESC " + " ", nativeQuery = true)
//		public List<ConsumerApplicationDetail> findByConsumerApplicationDetailsByApplicationStatusPaginate1(
//				@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
//				@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId,
//				@Param("consumerId") Long consumerId);

	@Query(value = "select * from consumer_application_detail where application_status>5 and is_active=1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findApplication();

	public ConsumerApplicationDetail findByNscApplicationNo(String sspApplicationNo);

	@Query(value = "SELECT * \r\n" + "FROM CONSUMER_APPLICATION_DETAIL \r\n" + "WHERE \r\n"
			+ "APPLICATION_STATUS > 5\r\n" + "AND is_active = 1 AND ROWNUM<11", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerApplicationDetailsByApplicationStatusPaginate2();

	@Query(value = "select * from consumer_application_detail where application_status>5 and is_active=1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerApplicationDetailsByApplicationStatusPaginate1();

	@Query(value = "SELECT\r\n" + "    CIR.CIRCLE AS \"NAME_OF_CIRCLE\",\r\n" + "    CIR.CIRCLE_ID AS \"CIR_ID\",\r\n"
			+ "    DIV.DIVISION AS \"NAME_OF_DIVISION\",\r\n" + "    DIV.DIV_ID AS \"DIV_ID\",\r\n"
			+ "    D.DISTRICT_NAME AS \"DISTRICT\",\r\n" + "    D.DISTRICT_ID AS \"DIST_ID\",\r\n"
			+ "    DC.DC_NAME AS \"dcName\",\r\n" + "    DC.DC_ID AS \"dcId\",\r\n"
			+ "    ASA.APPLICATION_STATUS_NAME AS \"applicationStatusName\",\r\n"
			+ "    ASA.APPLICATION_STATUS_ID AS \"applicationStatusId\",\r\n"
			+ "    CAD.CONSUMER_NAME AS \"consumerName\",\r\n" + "    CAD.GUARDIAN_NAME AS \"guardianName\",\r\n"
			+ "    ST.SCHEME_TYPE_NAME AS \"schemeTypeName\",\r\n" + "		ST.SCHEME_TYPE_ID AS \"schemeTypeId\",\r\n"
			+ "    NOW.NATURE_OF_WORK_NAME AS \"natureOfWorkName\",\r\n"
			+ "		NOW.NATURE_OF_WORK_ID AS \"natureOfWorkTypeId\",\r\n"
			+ "    TO_CHAR(CAD.CREATED, 'DD-MM-YY') AS \"DATE_OF_CREATION\",\r\n"
			+ "    C.CONSUMER_MOBILE_NO AS \"consumerphonNumber\",\r\n"
			+ "    CAD.CONSUMER_APPLICATION_NUMBER AS \"consumerApplicationNo\",\r\n"
			+ "    CAD.WORK_ALLOCATION_ADDRESS AS \"address\",\r\n" + "    CAD.JE_LOAD AS \"loadRequested\",\r\n"
			+ "    CAD.JE_LOAD_UNIT_KW_KVA  AS \"loadRequestedName\",\r\n"
			+ "    CAD.ERP_WORKFLOW_NUMBER AS \"erpWorkFlowNumber\",\r\n"
			+ "    TO_DATE(SUBSTR(BPR.TRANSACTION_DATE, 1, 10), 'YYYY-MM-DD') AS \"REG_PAY_DATE\",\r\n"
			+ "    COALESCE(BPR3.DATE_OF_PAYMENT, TO_DATE(SUBSTR(BPR1.TRANSACTION_DATE, 1, 10), 'YYYY-MM-DD')) AS \"DEMAND_PAY_DATE\",\r\n"
			+ "    CFB.CONTRACTOR_NAME AS \"NAME_OF_CONTRACTOR\",\r\n"
			+ "    VWO.WORK_ORDER_NO AS \"WORK_ORDER_NO\",\r\n" + "    CAD.DATE_OF_DGM_O_M AS \"dateOfDgmOandM\",\r\n"
			+ "    CAD.IS_ACTIVE AS \"ISACTIVE\",\r\n"
			+ "		CAD.CONSUMER_APPLICATION_ID AS \"consumerApplicationId\",\r\n"
			+ "		CAD.IS_REJECTED AS \"isRejected\",\r\n" + "		CAD.PREVIOUS_STAGE AS \"previousStage\",\r\n"
			+ "		CAD.REV_ERP AS \"revisedErpNumber\",\r\n"
			+ "		CAD.RETURN_MATERIAL_DATA AS \"returnMaterialData\",\r\n"
			+ "		CAD.REJECTION_PROPOSAL AS \"rejectionProposal\"\r\n" + "FROM \r\n"
			+ "    CONSUMER_APPLICATION_DETAIL CAD \r\n"
			+ "LEFT JOIN MKY_PAY_AMNT MKY ON MKY.CON_APP_NUM = CAD.CONSUMER_APPLICATION_NUMBER \r\n"
			+ "LEFT JOIN ERP_BUDGET_WORKFLOW_AMOUNT ERP ON ERP.ERP_NO = CAD.ERP_WORKFLOW_NUMBER\r\n"
			+ "LEFT JOIN DISTRICT D ON D.DISTRICT_ID = CAD.DISTRICT_ID\r\n"
			+ "LEFT JOIN CONSUMER C ON CAD.CONSUMER_ID = C.CONSUMER_ID\r\n"
			+ "LEFT JOIN CONTRACTOR_FOR_BID CFB ON CFB.CONSUMER_APPLICATION_NUMBER = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "JOIN APPLICATION_STATUS ASA ON ASA.APPLICATION_STATUS_ID = CAD.APPLICATION_STATUS\r\n"
			+ "JOIN NATURE_OF_WORK NOW ON NOW.NATURE_OF_WORK_ID = CAD.NATURE_OF_WORK_ID\r\n"
			+ "JOIN SCHEME_TYPE ST ON ST.SCHEME_TYPE_ID = CAD.SCHEME_TYPE_ID\r\n" + "LEFT JOIN (\r\n"
			+ "    SELECT *\r\n" + "    FROM BILLDESK_PAYMENT_RES\r\n" + "    WHERE BILL_DESK_RES_ID IN (\r\n"
			+ "        SELECT MAX(BILL_DESK_RES_ID)\r\n" + "        FROM BILLDESK_PAYMENT_RES\r\n"
			+ "        WHERE AMOUNT = 1180 AND TRANSACTION_ERROR_DESC = 'Transaction Successful'\r\n"
			+ "        GROUP BY CONSUMER_APPLICATION_NO\r\n" + "    )\r\n"
			+ ") BPR ON BPR.CONSUMER_APPLICATION_NO = CAD.CONSUMER_APPLICATION_NUMBER\r\n" + "LEFT JOIN (\r\n"
			+ "    SELECT *\r\n" + "    FROM BILLDESK_PAYMENT_RES\r\n" + "    WHERE BILL_DESK_RES_ID IN (\r\n"
			+ "        SELECT MAX(BILL_DESK_RES_ID)\r\n" + "        FROM BILLDESK_PAYMENT_RES\r\n"
			+ "        WHERE AMOUNT <> 1180 AND TRANSACTION_ERROR_DESC = 'Transaction Successful'\r\n"
			+ "        GROUP BY CONSUMER_APPLICATION_NO\r\n" + "    )\r\n"
			+ ") BPR1 ON BPR1.CONSUMER_APPLICATION_NO = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN Pose_machine_D BPR3 ON BPR3.APPLICATION_NUMBWER = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VENDOR_WORK_ORDER VWO ON VWO.CONSUMER_APPLICATION_NO = CAD.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "JOIN DISTRIBUTION_CENTER DC ON DC.DC_ID = CAD.DC_ID\r\n"
			+ "JOIN SUB_DIVISION SD ON SD.SUBDIV_ID = DC.SUBDIV_ID\r\n"
			+ "JOIN DIVISION DIV ON DIV.DIV_ID = SD.DIVISION_ID\r\n"
			+ "JOIN CIRCLE CIR ON CIR.CIRCLE_ID = DIV.CIRCLE_ID\r\n" + "WHERE \r\n"
			+ "    ASA.APPLICATION_STATUS_ID > 5\r\n" + "    AND CAD.IS_ACTIVE = 1", nativeQuery = true)
	public List<Map<String, Object>> getAllDataFromView();

	@Query(value = "select * from main_data", nativeQuery = true)
	public List<Map<String, Object>> getAllDataFromView1();

	@Query(value = "select * from (\r\n"
			+ "select '12,21,30,38' AS statusId,'6' AS Sno, sum(appl_count) as count FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (12,21,30,38) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status) UNION ALL\r\n"
			+ "select '6, 7, 36' AS statusId,'3' AS Sno, sum(appl_count) as count FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (6, 7, 36) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)\r\n" + "UNION ALL\r\n"
			+ "select '9, 23, 25' AS statusId,'4' AS Sno, sum(appl_count) as count  FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (9, 23, 25) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)\r\n" + "UNION ALL\r\n"
			+ "select '27,34' AS statusId,'5' AS Sno, sum(appl_count) as count FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (27,34) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)\r\n" + "UNION ALL\r\n"
			+ "select '35' AS statusId,'2' AS Sno, sum(appl_count) FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (35) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)\r\n" + "UNION ALL\r\n"
			+ "select '20,22,24,31' AS statusId,'8' AS Sno, sum(appl_count) as count FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (20,22,24,31) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)\r\n" + "UNION ALL\r\n"
			+ "select '32' AS statusId,'1' AS Sno, sum(appl_count) as count FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (32) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)\r\n" + "UNION ALL\r\n"
			+ "select '37,29,35' AS statusId,'7' AS Sno, sum(appl_count) as count FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (37,29,35) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)\r\n" + "UNION ALL\r\n"
			+ "select '28,33' AS statusId,'9' AS Sno, sum(appl_count) as count FROM (\r\n"
			+ "select application_status, count(consumer_application_id) as appl_count from consumer_application_detail\r\n"
			+ "where application_status in (28,33) and IS_ACTIVE=1\r\n"
			+ "group by application_status order by application_status)) order by Sno", nativeQuery = true)
	public List<Map<String, Object>> getAllDataCount();

//	@Query(value="SELECT * FROM main_data WHERE \"applicationStatusId\" IN (:applicationStatusId)",nativeQuery = true)
//	public List<Map<String, Object>> getAllApplicationByApplicationStatus(@Param("applicationStatusId") String applicationStatusId);

	@Query(value = "SELECT * FROM main_data WHERE \"applicationStatusId\" IN (:applicationStatusIds)", nativeQuery = true)
	public List<Map<String, Object>> getAllApplicationByApplicationStatus(
			@Param("applicationStatusIds") List<String> applicationStatusIds);

	@Query(value = "SELECT * FROM (\r\n" + "    SELECT '12,21,30,38' statusId, '6' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (12, 21, 30, 38)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '6,7,36' statusId, '3' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (6, 7, 36)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '9,23,25,39,44' statusId, '4' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (9, 23, 25, 39, 44)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '27,34,43' statusId, '5' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (27, 34, 43)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '37,40' statusId, '2' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (37, 40)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '20,22,24,31' statusId, '8' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (20, 22, 24, 31)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '32' statusId, '1' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (32)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '29,35' statusId, '7' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN ( 29, 35)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '28,33' statusId, '9' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (28, 33)\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT '6,7,9,12,20,21,22,23,24,25,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,2' statusId, '10' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n"
			+ "        APPLICATION_STATUS IN (6,7,9,12,20,21,22,23,24,25,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,2)\r\n"
			+ "    UNION ALL\r\n" + "    SELECT '41,42' statusId, '11' Sno, SUM(appl_count) count\r\n"
			+ "    FROM application_details_summary\r\n" + "    WHERE \r\n"
			+ "        (:discomId = 0 OR discom_id = :discomId) AND\r\n"
			+ "        (:regionId = 0 OR region_id = :regionId) AND\r\n"
			+ "        (:circleId = 0 OR circle_id = :circleId) AND\r\n"
			+ "        (:divisionId = 0 OR div_id = :divisionId) AND\r\n"
			+ "        (:subDivisionId = 0 OR SUBDIV_ID = :subDivisionId) AND\r\n"
			+ "        (:dcId = 0 OR dc_id = :dcId) AND\r\n" + "        APPLICATION_STATUS IN (41,42)\r\n"
			+ ")", nativeQuery = true)
	public List<Map<String, Object>> getAllDataCount1(@Param("discomId") Long discomId,
			@Param("regionId") Long regionId, @Param("circleId") Long circleId, @Param("divisionId") Long divisionId,
			@Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId);

//	@Query(value = "select * from main_data\r\n"
//			+ "where\r\n"
//			+ "(:discomId =0 or discomid =:discomId ) AND (:regionId =0 or regionid =:regionId ) and  \r\n"
//			+ "(:circleId =0 or CIR_ID =:circleId ) and  (:divisionId =0 or DIV_ID =:divisionId )\r\n"
//			+ "and  (:subDivisionId =0 or subdivid =:subDivisionId ) \r\n"
//			+ "and  (:dcId =0 or \"dcId\" =:dcId )\r\n"
//			+ "and  (applicationStatusId IN(:applicationStatusIds))", nativeQuery = true)
//	public List<Map<String, Object>> getAllApplicationByApplicationStatus1(@Param("applicationStatusIds") List<String> applicationStatusIds,@Param("discomId") Long discomId, @Param("regionId") Long regionId, @Param("circleId") Long circleId,
//			@Param("divisionId") Long divisionId, @Param("subDivisionId") Long subDivisionId, @Param("dcId") Long dcId);

	@Query(value = "select * from main_data\r\n" + "where\r\n"
			+ "(:discomid =0 or \"discomid\" =:discomid ) AND (:regionid =0 or \"regionid\" =:regionid ) and  \r\n"
			+ "(:CIR_ID =0 or \"CIR_ID\" =:CIR_ID ) and  (:DIV_ID =0 or \"DIV_ID\" =:DIV_ID )\r\n"
			+ "and  (:subdivid =0 or \"subdivid\" =:subdivid ) \r\n" + "and  (:dcId =0 or \"dcId\" =:dcId )\r\n"
			+ "and  (\"applicationStatusId\" IN(:applicationStatusIds))", nativeQuery = true)
	public List<Map<String, Object>> getAllApplicationByApplicationStatus1(
			@Param("applicationStatusIds") List<String> applicationStatusIds, @Param("discomid") Long discomId,
			@Param("regionid") Long regionId, @Param("CIR_ID") Long circleId, @Param("DIV_ID") Long divisionId,
			@Param("subdivid") Long subDivisionId, @Param("dcId") Long dcId);

	@Query(value = "select * from consumer_application_detail where je_return_amount>0 and is_active=1 and application_status in(28,33,46) and consumer_id=:consumerId", nativeQuery = true)
	public List<ConsumerApplicationDetail> findAllJeReturnAmountApp(@Param("consumerId") Long consumerId);

	@Query(value = "SELECT \"schemeTypeId\" ,\"schemeTypeName\",\"natureOfWorkTypeId\", \"natureOfWorkName\",\"discomid\",\"discomName\",\"regionid\",\"regionName\" , \"CIR_ID\",\"NAME_OF_CIRCLE\",\"DIV_ID\",\"NAME_OF_DIVISION\", \"subdivid\",\"subdivName\"  ,\"dcId\",\"dcName\", \"consumerApplicationId\", \"consumerApplicationNo\",\"applicationStatusId\",\"applicationStatusName\"  FROM main_data where \"consumerApplicationNo\"= :consumerApplicationNo", nativeQuery = true)
	public List<Map<String, Object>> getSchemeAndNatureByApplicationNo(
			@Param("consumerApplicationNo") String consumerApplicationNo);

	@Query(value = "select * from consumer_application_detail where consumer_id=:consumerId and consumer_application_number=:applicationNo", nativeQuery = true)
	public Optional<ConsumerApplicationDetail> findByConsumerIdAndApplicationNo(Long consumerId, String applicationNo);

	@Query(value = "select * from consumer_application_detail where consumer_id=:consumerId and application_status=:applicationStatusId", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerIdAndApplicationStatusId(Long consumerId,
			Long applicationStatusId);

	@Query(value = "select * from consumer_application_detail where consumer_id=:consumerId and consumer_name=:consumerName", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerIdAndConsumerName(Long consumerId, String consumerName);

	@Query(value = "select * from consumer_application_detail where consumer_id=:consumerId and nature_of_work_id=:natureOfWorkId", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerIdAndNatureOfWorkId(Long consumerId, Long natureOfWorkId);

	@Query(value = "select * from consumer_application_detail where consumer_id=:consumerId and scheme_type_id=:schemeTypeId", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerIdAndSchemeTypeId(Long consumerId, Long schemeTypeId);

	@Query(value = "SELECT\r\n" + "    dc.NGB_DC_CD,\r\n" + "    COUNT(*) AS transaction_count,\r\n"
			+ "    SUM(bps.amount) AS total_sum,\r\n"
			+ "    TO_CHAR(TO_DATE(SUBSTR(bps.transaction_date, 0, INSTR(bps.transaction_date, 'T') - 1), 'YYYY-MM-DD'), 'YYYY-MM-DD') AS transaction_date,\r\n"
			+ "    bps.settlement_date\r\n" + "FROM\r\n" + "    BILLDESK_PAYMENT_RES bps\r\n" + "JOIN\r\n"
			+ "    CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "    ON bps.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n" + "JOIN\r\n"
			+ "    DISTRIBUTION_CENTER dc\r\n" + "    ON cad.dc_id = dc.dc_id\r\n" + "WHERE\r\n"
			+ "    bps.AUTH_STATUS = '0300'\r\n"
			+ "    AND TO_DATE(SUBSTR(bps.transaction_date, 0, INSTR(bps.transaction_date, 'T') - 1), 'YYYY-MM-DD')\r\n"
			+ "        BETWEEN TO_DATE(:toDate, 'YYYY-MM-DD') AND TO_DATE(:fromDate, 'YYYY-MM-DD')\r\n" + "GROUP BY\r\n"
			+ "    dc.NGB_DC_CD,\r\n"
			+ "    TO_CHAR(TO_DATE(SUBSTR(bps.transaction_date, 0, INSTR(bps.transaction_date, 'T') - 1), 'YYYY-MM-DD'), 'YYYY-MM-DD'),\r\n"
			+ "    bps.settlement_date", nativeQuery = true)
	List<Map<String, ?>> getBillDesk(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

	@Query(value = "select * from consumer_application_detail cad left join contractor_for_bid_work_status cfbws on cad.CONSUMER_APPLICATION_NUMBER=cfbws.CONSUMER_APPLICATION_NUMBER where cad.APPLICATION_STATUS=24 and cfbws.ACTUAL_WORK_COMPLETION_DATE is null", nativeQuery = true)
	public List<ConsumerApplicationDetail> saveContractorActualWorkCompletionDates();

	@Query(value = "SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo and IS_ACTIVE = 1", nativeQuery = true)
	public Optional<ConsumerApplicationDetail> findByConsumerApplicationNo(String consumerApplicationNo);

	@Query(value = "update consumer_application_detail set dc_id=183 where consumer_application_number in(\r\n"
			+ "select consumer_application_number from consumer_application_detail where dc_id=372 and ssp_application_no is not null)", nativeQuery = true)
	public void sspApplicationDCUpates();

	@Query(value = "SELECT \r\n" + "    'DSP PORTAL' AS \"Transaction Source\",\r\n" + "    CIR.CIRCLE AS \"OU\",\r\n"
			+ "    CIR.CIRCLE AS \"CIRCLE\",\r\n" + "    DIV.DIVISION AS \"DIVISION\",\r\n"
			+ "    DC.DC_NAME AS \"DC\",\r\n" + "    '' AS \"Class\",\r\n" + "    '' AS \"Transaction Type\",\r\n"
			+ "    '' AS \"Reference No/Sales Order No\",\r\n" + "    BPR.TRANSACTION_DATE AS \"Invoice Date\",\r\n"
			+ "    '' AS \"GL Date\",\r\n" + "    NVL(BPR.Amount, 0) AS \"Amount\",\r\n"
			+ "    'INR' AS \"Currency\",\r\n" + "    BPR.CONSUMER_NAME AS \"Customer Name\",\r\n"
			+ "    BPR.CONSUMER_APPLICATION_NO AS \"Number (Address)\",		\r\n" + "    '' AS \"Payment Term\",\r\n"
			+ "    '' AS \"Item Description\",\r\n" + "    '' AS \"Quantity\",	\r\n"
			+ "    '' AS \"Unit Price\",	\r\n" + "    '' AS \"Comments (Legacy System)\",\r\n"
			+ "    '' AS \"GL Account Combination\",\r\n" + "    '' AS \"Tax Category\",\r\n" + "    NVL(\r\n"
			+ "        CASE \r\n" + "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 1000 \r\n"
			+ "            ELSE 0 \r\n" + "        END, 0\r\n" + "    ) AS \"Reg Amount(62.932)\",	\r\n"
			+ "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 90 \r\n"
			+ "            ELSE B.CGST \r\n" + "        END, 0\r\n" + "    ) AS \"CGST(46.948)\",	\r\n" + "\r\n"
			+ "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 90 \r\n"
			+ "            ELSE B.SGST \r\n" + "        END, 0\r\n" + "    ) AS \"SGST(46.949)\",	\r\n" + "  \r\n"
			+ "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE B.DEPOSIT_AMOUNT \r\n" + "        END, 0\r\n" + "    ) AS \"DEPOSIT(47.320)\",	\r\n"
			+ "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE C.ABEDAN_SULK \r\n" + "        END, 0\r\n"
			+ "    ) AS \"AVEDAN SHULK(62.936) @RS 2495\",	\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE C.AVEDAN_SHULK_FIVE_RUPEE \r\n" + "        END, 0\r\n"
			+ "    ) AS \"AVEDAN SHULK  (47.310)@RS 5\",	\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE C.SECURITY_DEPOSIT \r\n" + "        END, 0\r\n"
			+ "    ) AS \"SECURITY DEPOSIT(48.150)\",\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE C.PAYALE_AMOUNT \r\n" + "        END, 0\r\n"
			+ "    ) AS \"PAID BY APPLICANT(47.345)\",\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE B.COLONY_OR_SLUM \r\n" + "        END, 0\r\n" + "    ) AS \"Slump (47.337)\",\r\n"
			+ "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE B.U_SEC_194J_TDS2 \r\n" + "        END, 0\r\n"
			+ "    ) AS \"TDS at 2%(27.425)\",	\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN '0' \r\n"
			+ "            ELSE A.GST_NUMBER \r\n" + "        END, '0'\r\n" + "    ) AS \"GST_NUMBER()\",\r\n" + "\r\n"
			+ "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE B.SUPERVISION_AMOUNT \r\n" + "        END, 0\r\n"
			+ "    ) AS \"SUPERVISION CHARGES(62.925)\",	\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE B.KVALOAD \r\n" + "        END, 0\r\n"
			+ "    ) AS \"Supply Afford Charge(55.150)\",	\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE B.KWLOAD \r\n" + "        END, 0\r\n"
			+ "    ) AS \"Sys Development Charge(55.160)\",	\r\n" + "\r\n" + "    NVL(\r\n" + "        CASE \r\n"
			+ "            WHEN BPR.ADDITIONAL_INFO1 = 'Registration_Fees' THEN 0 \r\n"
			+ "            ELSE B.JE_RETURN_AMOUNT \r\n" + "        END, 0\r\n"
			+ "    ) AS \"JE_RETURN_AMOUNT(46.115)\"\r\n" + "\r\n" + "FROM \r\n"
			+ "    CONSUMER_APPLICATION_DETAIL A\r\n" + "LEFT JOIN \r\n"
			+ "    ERP_BUDGET_WORKFLOW_AMOUNT B ON A.ERP_WORKFLOW_NUMBER = B.ERP_NO\r\n" + "LEFT JOIN \r\n"
			+ "    MKY_PAY_AMNT C ON A.CONSUMER_APPLICATION_NUMBER = C.CON_APP_NUM\r\n" + "LEFT JOIN \r\n"
			+ "    ERP_REV D ON A.CONSUMER_APPLICATION_NUMBER = D.CONS_APP_NO\r\n" + "LEFT JOIN \r\n"
			+ "    BILLDESK_PAYMENT_RES BPR ON A.CONSUMER_APPLICATION_NUMBER = BPR.CONSUMER_APPLICATION_NO\r\n"
			+ "JOIN \r\n" + "    DISTRIBUTION_CENTER DC ON DC.DC_ID = A.DC_ID\r\n" + "JOIN \r\n"
			+ "    SUB_DIVISION SD ON SD.SUBDIV_ID = DC.SUBDIV_ID\r\n" + "JOIN \r\n"
			+ "    DIVISION DIV ON DIV.DIV_ID = SD.DIVISION_ID\r\n" + "JOIN \r\n"
			+ "    CIRCLE CIR ON CIR.CIRCLE_ID = DIV.CIRCLE_ID\r\n" + "JOIN \r\n"
			+ "    REGION REG ON CIR.REGION_ID = REG.REGION_ID\r\n" + "JOIN \r\n"
			+ "    DISCOM DIS ON REG.DISCOM_ID = DIS.DISCOM_ID\r\n" + "WHERE \r\n"
			+ "    TO_CHAR(TO_TIMESTAMP(BPR.SETTLEMENT_DATE, 'YYYY-MM-DD\"T\"HH24:MI:SS\"+05:30\"'), 'YYYY-MM-DD') \r\n"
			+ "        BETWEEN :fromDate AND :toDate\r\n" + "    AND \r\n"
			+ "    BPR.auth_status = '0300'", nativeQuery = true)
	public List<Map<String, ?>> getErpBillDeskMis(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

	@Query(value = "SELECT\r\n" + "    au.userid,\r\n" + "    au.distribution_center_id,\r\n"
			+ "    ads.application_status,\r\n" + "    aps.application_status_name,\r\n" + "    ads.appl_count,\r\n"
			+ "    case when ads.application_status in(6,7,36,32) then 'JE'\r\n"
			+ "         when ads.application_status in(9,23,25,39,44) then 'DGM(O&M)/HTM'\r\n"
			+ "         when ads.application_status in(27,34,43) then 'DGM STC/AE(O&M)'\r\n"
			+ "         when ads.application_status in(37,40) then 'GM'\r\n" + "END PENDENCY\r\n" + "FROM\r\n"
			+ "    ad_user au\r\n"
			+ "    LEFT JOIN application_details_summary ads ON au.distribution_center_id = ads.dc_id\r\n"
			+ "    LEFT JOIN application_status          aps ON ads.application_status = aps.application_status_id\r\n"
			+ "WHERE\r\n" + "    access_level = 6\r\n" + "    AND ads.dc_id IS NOT NULL\r\n"
			+ "    AND ads.application_status NOT IN(12,21,30,38,20,22,24,31,29,28,33)\r\n"
			+ "    AND au.userid = :userId", nativeQuery = true)
	public List<Map<String, ?>> getUserIdApplicationCountPendency(String userId);

//	@Procedure("data_to_erp")
//	public Integer syncWithModification(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

//	@Procedure("data_to_erp(todate, fromDate)")
//	public Integer syncWithModification(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

//	@Modifying
//	@Transactional
//	@Query(value = "CALL data_to_erp(:toDate, :fromDate)", nativeQuery = true)
//	Integer syncWithModification(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

	@Procedure("data_to_erp")
	public Integer executeStoredProc(@Param("toDate") String toDate, @Param("fromDate") String fromDate);

	public List<ConsumerApplicationDetail> findByDistributionCenter_DcId(Long dcId);

	@Query(value = "SELECT\r\n" + "    ads.nature_of_work_name,\r\n" + "    au.userid,\r\n"
			+ "    aps.application_status_name,\r\n"
			+ "    SUM(ads.appl_count) AS total_appl_count, -- Sab counts ko add karne ke liye\r\n" + "    CASE \r\n"
			+ "        WHEN au.access_level = 6 AND ads.application_status IN (6, 7, 36, 32) THEN 'JE'\r\n"
			+ "        WHEN au.access_level = 5 AND ads.application_status IN (27, 34, 43) THEN 'DGM STC/AE(O & M)'\r\n"
			+ "        WHEN au.access_level = 4 AND ads.application_status IN (9, 23, 25, 39, 44) THEN 'DGM(O & M)/HTM'\r\n"
			+ "        WHEN au.access_level = 3 AND ads.application_status IN (37, 40) THEN 'GM'\r\n"
			+ "        ELSE NULL -- Agar koi bhi condition match nahi hui toh NULL\r\n" + "    END AS pendency\r\n"
			+ "FROM\r\n" + "    ad_user au\r\n" + "    LEFT JOIN animesh_wfm ads \r\n" + "        ON (\r\n"
			+ "            (au.access_level = 6 AND au.distribution_center_id = ads.dc_id) OR\r\n"
			+ "            (au.access_level = 5 AND au.sub_division_id = ads.subdiv_id) OR\r\n"
			+ "            (au.access_level = 4 AND au.div_id = ads.div_id) OR\r\n"
			+ "            (au.access_level = 3 AND au.circle_id = ads.circle_id) OR\r\n"
			+ "            (au.access_level = 2 AND au.region_id = ads.region_id) OR\r\n"
			+ "            (au.access_level = 1 AND au.discom_id = ads.discom_id)\r\n" + "        )\r\n"
			+ "    LEFT JOIN application_status aps \r\n"
			+ "        ON ads.application_status = aps.application_status_id\r\n" + "WHERE \r\n"
			+ "    au.userid = :userId -- Specific userId ka filter\r\n"
			+ "    AND ads.application_status NOT IN (12, 21, 30, 38, 20, 22, 24, 31, 29, 28, 33)\r\n" + "    AND (\r\n"
			+ "        (au.access_level = 6 AND ads.application_status IN (6, 7, 36, 32)) OR\r\n"
			+ "        (au.access_level = 5 AND ads.application_status IN (27, 34, 43)) OR\r\n"
			+ "        (au.access_level = 4 AND ads.application_status IN (9, 23, 25, 39, 44)) OR\r\n"
			+ "        (au.access_level = 3 AND ads.application_status IN (37, 40))\r\n" + "    )\r\n" + "GROUP BY\r\n"
			+ "    ads.nature_of_work_name,\r\n" + "    au.userid,\r\n" + "    aps.application_status_name,\r\n"
			+ "    CASE \r\n"
			+ "        WHEN au.access_level = 6 AND ads.application_status IN (6, 7, 36, 32) THEN 'JE'\r\n"
			+ "        WHEN au.access_level = 5 AND ads.application_status IN (27, 34, 43) THEN 'DGM STC/AE(O & M)'\r\n"
			+ "        WHEN au.access_level = 4 AND ads.application_status IN (9, 23, 25, 39, 44) THEN 'DGM(O & M)/HTM'\r\n"
			+ "        WHEN au.access_level = 3 AND ads.application_status IN (37, 40) THEN 'GM'\r\n"
			+ "        ELSE NULL\r\n" + "    END", nativeQuery = true)
	public List<Map<String, ?>> getDSPApplicationPendencyOnDiscomUser(String userId);

	@Query(value = "SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo and IS_ACTIVE = 1", nativeQuery = true)
	public ConsummerAppDetails findByConsumerAppDetails(String consumerApplicationNo);

//	@Query(value = "select * from consumer_application_detail where erp_workflow_number = :erpNo and is_active=1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByerpWorkFlowNumberOrRevisedErpNumber(String erpNo, String erpNumber);

	public List<ConsumerApplicationDetail> findByerpWorkFlowNumber(String erpNo);

	@Query(value = "select * from main_data where \"consumerApplicationNo\"=:consumerApplicationNo", nativeQuery = true)
	public List<Map<String, ?>> getDataFromView(String consumerApplicationNo);

	@Query(value = "SELECT DISTINCT\r\n"
			+ "    vam.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.ERP_WORKFLOW_NUMBER,\r\n"
			+ "    vam.TRANSFORMER_SERIAL_NO,\r\n"
			+ "    cfb.CONTRACTOR_NAME,\r\n"
			+ "    vam.VENDOR_MATERIAL_SPECIFICATION,\r\n"
			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n"
			+ "    now.NATURE_OF_WORK_NAME,\r\n"
			+ "    vam.VENDOR_NAME,\r\n"
			+ "    cir.CIRCLE,\r\n"
			+ "    div.DIVISION,\r\n"
			+ "    dc.DC_NAME,\r\n"
			+ "    cad.CONSUMER_NAME,\r\n"
			+ "    cad.ADDRESS,\r\n"
			+ "    cad.PHONE_NUMBER,\r\n"
			+ "    cd.CONSUMER_MOBILE_NO,\r\n"
			+ "    vwo.WORK_ORDER_NO,\r\n"
			+ "    vwo.WORK_ORDER_DATE,\r\n"
			+ "    vam.POST_FLAG,\r\n"
			+ "    cad.WORK_COMPLETION_DATE\r\n"
			+ "FROM VENDOR_ADD_MATERIAL vam\r\n"
			+ "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN CONSUMER_APPLICATION_DETAIL cad \r\n"
			+ "    ON cad.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN DISTRIBUTION_CENTER dc \r\n"
			+ "    ON dc.DC_ID = cad.DC_ID\r\n"
			+ "LEFT JOIN SUB_DIVISION sub_div \r\n"
			+ "    ON sub_div.SUBDIV_ID = dc.SUBDIV_ID\r\n"
			+ "LEFT JOIN DIVISION div \r\n"
			+ "    ON div.DIV_ID = sub_div.DIVISION_ID\r\n"
			+ "LEFT JOIN CIRCLE cir \r\n"
			+ "    ON cir.CIRCLE_ID = div.CIRCLE_ID\r\n"
			+ "LEFT JOIN REGION reg \r\n"
			+ "    ON reg.REGION_ID = cir.REGION_ID\r\n"
			+ "LEFT JOIN DISCOM disc \r\n"
			+ "    ON disc.DISCOM_ID = reg.DISCOM_ID\r\n"
			+ "LEFT JOIN CONSUMER cd \r\n"
			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n"
			+ "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN NATURE_OF_WORK now \r\n"
			+ "    ON now.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
			+ "WHERE cad.NATURE_OF_WORK_ID = 2\r\n"
			+ "  AND vam.RESAMPLING_FLAG = :flagNo\r\n"
			+ "  AND cfb.CONTRACTOR_AUTHANTICATION_ID IN (\r\n"
			+ "        SELECT cfb_inner.CONTRACTOR_AUTHANTICATION_ID\r\n"
			+ "        FROM CONTRACTOR_FOR_BID cfb_inner\r\n"
			+ "        GROUP BY cfb_inner.CONTRACTOR_AUTHANTICATION_ID\r\n"
			+ "        HAVING COUNT(*) >= 5\r\n"
			+ "    )\r\n"
			+ "  AND cad.CREATED >= ADD_MONTHS(SYSDATE, -6) "
			+ "ORDER BY cfb.CONTRACTOR_AUTHANTICATION_ID" , nativeQuery = true)
	public List<Map<String, ?>> getReSampleData(Long flagNo);

	@Query(value = "SELECT DISTINCT\r\n" + "    vam.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.ERP_WORKFLOW_NUMBER,\r\n" + "    vam.TRANSFORMER_SERIAL_NO,\r\n" + "    cfb.CONTRACTOR_NAME,\r\n"
			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n" + "    now.NATURE_OF_WORK_NAME,\r\n"
			+ "    vam.VENDOR_NAME,\r\n" + "    cir.CIRCLE,\r\n" + "    div.DIVISION,\r\n" + "    dc.DC_NAME,\r\n"
			+ "    cad.CONSUMER_NAME,\r\n" + "    cad.ADDRESS,\r\n" + "    cad.PHONE_NUMBER,\r\n"
			+ "    cd.CONSUMER_MOBILE_NO,\r\n" + "    vwo.WORK_ORDER_NO,\r\n" + "    vwo.WORK_ORDER_DATE,\r\n"
			+ "    cad.WORK_COMPLETION_DATE\r\n" + "FROM VENDOR_ADD_MATERIAL vam\r\n"
			+ "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN CONSUMER_APPLICATION_DETAIL cad \r\n"
			+ "    ON cad.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN DISTRIBUTION_CENTER dc \r\n" + "    ON dc.DC_ID = cad.DC_ID\r\n"
			+ "LEFT JOIN SUB_DIVISION sub_div \r\n" + "    ON sub_div.SUBDIV_ID = dc.SUBDIV_ID\r\n"
			+ "LEFT JOIN DIVISION div \r\n" + "    ON div.DIV_ID = sub_div.DIVISION_ID\r\n"
			+ "LEFT JOIN CIRCLE cir \r\n" + "    ON cir.CIRCLE_ID = div.CIRCLE_ID\r\n" + "LEFT JOIN REGION reg \r\n"
			+ "    ON reg.REGION_ID = cir.REGION_ID\r\n" + "LEFT JOIN DISCOM disc \r\n"
			+ "    ON disc.DISCOM_ID = reg.DISCOM_ID\r\n" + "LEFT JOIN CONSUMER cd \r\n"
			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n" + "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN NATURE_OF_WORK now \r\n" + "    ON now.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
			+ "WHERE cad.NATURE_OF_WORK_ID IN (2) and cfb.CONTRACTOR_AUTHANTICATION_ID =:authenticationId\r\n"
			+ "ORDER BY cfb.CONTRACTOR_AUTHANTICATION_ID ASC \r\n" + "", nativeQuery = true)
	public List<Map<String, ?>> findReSamplingDataByContractorAuthentication(
			@Param("authenticationId") String authenticationId);

//	procedure call method
	@Procedure("CREATE_INVOICE_22")
	public void createInvoice();

//	@Query(value = "SELECT DISTINCT\r\n" + "    cad.CONSUMER_APPLICATION_NUMBER,\r\n"
//			+ "    cad.erp_workflow_number,\r\n" + "  \r\n" + "    cfb.CONTRACTOR_NAME,\r\n" + " \r\n"
//			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n" + "    now_tbl.NATURE_OF_WORK_NAME,\r\n" + "   \r\n"
//			+ "    cir.circle,\r\n" + "    div.division,\r\n" + "    dc.dc_name,\r\n" + "    cad.CONSUMER_NAME,\r\n"
//			+ "    cad.ADDRESS,\r\n" + "    cad.PHONE_NUMBER,\r\n" + "    cd.CONSUMER_MOBILE_NO,\r\n"
//			+ "    vwo.WORK_ORDER_NO,\r\n" + "    vwo.WORK_ORDER_DATE,\r\n" + "    cad.WORK_COMPLETION_DATE\r\n"
//			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n" + "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
//			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = cad.CONSUMER_APPLICATION_NUMBER\r\n"
//			+ "LEFT JOIN VENDOR_ADD_MATERIAL vam \r\n"
//			+ "    ON cad.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
//			+ "LEFT JOIN DISTRIBUTION_CENTER dc \r\n" + "    ON dc.DC_ID = cad.DC_ID\r\n"
//			+ "LEFT JOIN SUB_DIVISION sub_div \r\n" + "    ON sub_div.SUBDIV_ID = dc.SUBDIV_ID\r\n"
//			+ "LEFT JOIN DIVISION div \r\n" + "    ON div.DIV_ID = sub_div.DIVISION_ID\r\n"
//			+ "LEFT JOIN CIRCLE cir \r\n" + "    ON cir.CIRCLE_ID = div.CIRCLE_ID\r\n" + "LEFT JOIN REGION reg \r\n"
//			+ "    ON reg.REGION_ID = cir.REGION_ID\r\n" + "LEFT JOIN DISCOM disc \r\n"
//			+ "    ON disc.DISCOM_ID = reg.DISCOM_ID\r\n" + "LEFT JOIN CONSUMER cd \r\n"
//			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n" + "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
//			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
//			+ "LEFT JOIN NATURE_OF_WORK now_tbl \r\n" + "    ON now_tbl.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
//			+ "WHERE cad.NATURE_OF_WORK_ID IN (2)\r\n" + "  AND cad.APPLICATION_STATUS IN (27, 25, 28, 32, 33)\r\n"
//			+ "  AND cad.RESAMPLING_FLAG IN (0, 1) and cfb.CONTRACTOR_AUTHANTICATION_ID is not null and  cad.CREATED >= TO_DATE('01-01-2025', 'DD-MM-YYYY') and cad.SCHEME_TYPE_ID = 1"
//			+ " AND cir.CIRCLE_ID = :circleId", nativeQuery = true)
//	public List<Map<String, ?>> getReSampleDataForConsuemrApplicationDetails(Long circleId);

//	  AND cfb.CONTRACTOR_AUTHANTICATION_ID IN (\r\n"
//				+ "        SELECT cfb_inner.CONTRACTOR_AUTHANTICATION_ID\r\n"
//				+ "        FROM CONTRACTOR_FOR_BID cfb_inner\r\n"
//				+ "        GROUP BY cfb_inner.CONTRACTOR_AUTHANTICATION_ID\r\n"
//				+ "        HAVING COUNT(*) >= 5\r\n"
//				+ "    )\r\n"
//				+ "ORDER BY cfb.CONTRACTOR_AUTHANTICATION_ID ASC\r\n"
//				+ "

	@Query(value = "SELECT DISTINCT\r\n" + "    cad.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.erp_workflow_number,\r\n" + "    cfb.CONTRACTOR_NAME,\r\n"
			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n" + "    now_tbl.NATURE_OF_WORK_NAME,\r\n"
			+ "    cir.circle,\r\n" + "    div.division,\r\n" + "    dc.dc_name,\r\n" + "    cad.CONSUMER_NAME,\r\n"
			+ "    cad.ADDRESS,\r\n" + "    cad.PHONE_NUMBER,\r\n" + "    cd.CONSUMER_MOBILE_NO,\r\n"
			+ "    vwo.WORK_ORDER_NO,\r\n" + "    vwo.WORK_ORDER_DATE,\r\n" + "    cad.WORK_COMPLETION_DATE,\r\n"
			+ "    res.TOTAL_NO_OF_DTR,\r\n" + "    res.VENDOR_NAME,\r\n" + "    res.SERIAL_NO,\r\n"
			+ "    res.INVOICE_NO,\r\n" + "    res.YEAR_OF_MANUFACTURE,\r\n" + "    res.CAPACITY_OF_DTR,\r\n"
			+ "    res.PARANT_APPLICATION_NO,\r\n" + "    res.Material_Name,\r\n"
			+ "    res.Material_Specification,\r\n" + "    sub_div.SUB_DIVISION,\r\n" + "    res.ITEM_NO, "

			+ "    gpc.MANUAL_DI_NO , " + "    gpc.NAME_OF_ENTITY , " + "    gpc.LOA_ORDER_NO , "
			+ "    gpc.QUANTITY , " + "    gpc.UOM , " + "    gpc.DRIVER_NAME , " + "    gpc.DI_NO , "
			+ "    gpc.DRIVER_CONTACT_NO , " + "    gpc.ISSUED_TO , " + "    gpc.RECEIVER_DETAILS , "
			+ "    gpc.CONTACT_PERSON , " + "    gpc.OUTWARD_QUANTITY , " + "    gpc.LOA_ORDER_DATE , "
			+ "    gpc.ISSUE_DATE , " + "    gpc.MATERIAL_TYPE , "

			+ "    gpc.DTR_CAPACITY , " + "    gpc.Y_O_M , "

			+ "    gpc.VEHICLE_NAME , " + "    gpc.VEHICLE_NUMER , "

			+ "     reg.REGION," + " vb.VERIFIER_CODE , "

			+ "     vb.VERIFIED_BY_NAME," + "     vb.VERIFIED_BY_DESIGNATION," + "     vb.CONSUEMR_APPLICATION_NUMBER,"

			+ "    'Nistha Lab' as nistha_lab \r\n" + "FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VENDOR_ADD_MATERIAL vam \r\n"
			+ "    ON cad.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN DISTRIBUTION_CENTER dc \r\n" + "    ON dc.DC_ID = cad.DC_ID\r\n"
			+ "LEFT JOIN SUB_DIVISION sub_div \r\n" + "    ON sub_div.SUBDIV_ID = dc.SUBDIV_ID\r\n"
			+ "LEFT JOIN DIVISION div \r\n" + "    ON div.DIV_ID = sub_div.DIVISION_ID\r\n"
			+ "LEFT JOIN CIRCLE cir \r\n" + "    ON cir.CIRCLE_ID = div.CIRCLE_ID\r\n" + "LEFT JOIN REGION reg \r\n"
			+ "    ON reg.REGION_ID = cir.REGION_ID\r\n" + "LEFT JOIN DISCOM disc \r\n"
			+ "    ON disc.DISCOM_ID = reg.DISCOM_ID\r\n" + "LEFT JOIN CONSUMER cd \r\n"
			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n" + "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN NATURE_OF_WORK now_tbl \r\n" + "    ON now_tbl.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
			+ "LEFT JOIN RE_SAMPLING res \r\n" + "    ON res.CON_APP_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN GATE_PASS_CHALLAN gpc \r\n"
			+ "    ON gpc.CONSUMER_APPLICATION_NUMBER = cad.CONSUMER_APPLICATION_NUMBER\r\n"

			+ "LEFT JOIN VERIFIER_BY vb \r\n"
			+ "    ON vb.CONSUEMR_APPLICATION_NUMBER = cad.CONSUMER_APPLICATION_NUMBER\r\n"

			+ "WHERE cad.NATURE_OF_WORK_ID IN (2)\r\n" + "  AND cad.CONSUMER_APPLICATION_NUMBER =:consumerAppNo\r\n"
			+ "" + "", nativeQuery = true)
	public List<Map<String, ?>> getReSampleDataForConsuemrApplicationDetailsByApplicationNo(String consumerAppNo);

	@Query(value = "select * from main_data where \"consumerApplicationNo\" = :consumerApplicationNo", nativeQuery = true)
	public List<Map<String, ?>> getApplicationDataByMainDataView(String consumerApplicationNo);

	
	
	
	
	@Query(value = "SELECT cir.circle_id\r\n"
			+ "	FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "	LEFT JOIN distribution_center dc ON dc.dc_id = cad.dc_id\r\n"
			+ "	LEFT JOIN sub_division sub_div ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "	LEFT JOIN division div ON div.div_id = sub_div.division_id\r\n"
			+ "	LEFT JOIN circle cir ON cir.circle_id = div.circle_id\r\n"
			+ "	LEFT JOIN region reg ON reg.region_id = cir.region_id\r\n"
			+ "	LEFT JOIN discom disc ON disc.discom_id = reg.discom_id\r\n"
			+ "	WHERE cad.dc_id =:dcId  and  CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo", nativeQuery = true)
	public List<Map<String, ?>> getCircleDataByApplicationNO(Long dcId, String consumerApplicationNo);

	
	@Query(value="SELECT \r\n"
			+ "    CAD.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    :monthyear AS monthyear,\r\n"
			+ "    CAD.GST_NUMBER AS gst_no,\r\n"
			+ "    CAD.CONSUMER_NAME AS name,\r\n"
			+ "    'Madhya Pradesh' AS state,\r\n"
			+ "    di.district_name AS district,\r\n"
			+ "\r\n"
			+ "    -- ✅ Description based on Billdesk or Pose\r\n"
			+ "    CASE\r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Revised_Demand_fees%' OR PMD.PAYMENT_TYPE LIKE '%Revised_Demand_fees%') THEN 'Revised_Demand_fees'\r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Demand_fees%' OR PMD.PAYMENT_TYPE LIKE '%Demand_fees%') THEN 'Demand_fees'\r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Registration_Fees%' OR PMD.PAYMENT_TYPE LIKE '%Registration_Fees%') THEN 'Registration_Fees'\r\n"
			+ "    END AS description,\r\n"
			+ "\r\n"
			+ "    '9' AS cgst_rate,\r\n"
			+ "\r\n"
			+ "    -- ✅ CGST Amount logic\r\n"
			+ "    CASE \r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Registration_Fees%' OR PMD.PAYMENT_TYPE LIKE '%Registration_Fees%')\r\n"
			+ "            THEN 90\r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Revised_Demand_fees%' OR PMD.PAYMENT_TYPE LIKE '%Revised_Demand_fees%')\r\n"
			+ "            THEN NVL(ERPR.REM_CGST, 0)\r\n"
			+ "        ELSE NVL(EBWA.CGST, 0)\r\n"
			+ "    END AS cgst_amount,\r\n"
			+ "\r\n"
			+ "    '9' AS sgst_rate,\r\n"
			+ "\r\n"
			+ "    -- ✅ SGST Amount logic\r\n"
			+ "    CASE \r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Registration_Fees%' OR PMD.PAYMENT_TYPE LIKE '%Registration_Fees%')\r\n"
			+ "            THEN 90\r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Revised_Demand_fees%' OR PMD.PAYMENT_TYPE LIKE '%Revised_Demand_fees%')\r\n"
			+ "            THEN NVL(ERPR.REM_SGST, 0)\r\n"
			+ "        ELSE NVL(EBWA.SGST, 0)\r\n"
			+ "    END AS sgst_amount,\r\n"
			+ "\r\n"
			+ "    0 AS cgst_interest,\r\n"
			+ "    0 AS sgst_interest,\r\n"
			+ "\r\n"
			+ "    -- ✅ total_invoice_value = Paid Amount (Billdesk or Pose)\r\n"
			+ "    NVL(\r\n"
			+ "        TO_NUMBER(NULLIF(BPR.AMOUNT, '')), \r\n"
			+ "        NVL(PMD.TXN_AMOUNT, 0)\r\n"
			+ "    ) AS total_invoice_value,\r\n"
			+ "\r\n"
			+ "    -- ✅ taxable_value logic\r\n"
			+ "    CASE \r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Registration_Fees%' OR PMD.PAYMENT_TYPE LIKE '%Registration_Fees%')\r\n"
			+ "            THEN 1000\r\n"
			+ "        WHEN (BPR.ADDITIONAL_INFO1 LIKE '%Revised_Demand_fees%' OR PMD.PAYMENT_TYPE LIKE '%Revised_Demand_fees%')\r\n"
			+ "            THEN NVL(ERPR.PAY_AMT, 0) - (NVL(ERPR.REM_CGST, 0) + NVL(ERPR.REM_SGST, 0))\r\n"
			+ "        WHEN CAD.SCHEME_TYPE_ID = 1 THEN \r\n"
			+ "            NVL(EBWA.TOTAL_BALANCE_SUPER_AMOUNT, 0) - (NVL(EBWA.CGST, 0) + NVL(EBWA.SGST, 0))\r\n"
			+ "        WHEN CAD.SCHEME_TYPE_ID = 2 THEN \r\n"
			+ "            NVL(EBWA.TOTAL_BALANCE_DEPO_AMOUNT, 0) - (NVL(EBWA.CGST, 0) + NVL(EBWA.SGST, 0))\r\n"
			+ "        ELSE 0\r\n"
			+ "    END AS taxable_value,\r\n"
			+ "\r\n"
			+ "    CAD.PIN_CODE AS pincode,\r\n"
			+ "\r\n"
			+ "    -- ✅ Payment Date column\r\n"
			+ "    CASE\r\n"
			+ "        WHEN BPR.CONSUMER_APPLICATION_NO IS NOT NULL THEN \r\n"
			+ "            TO_DATE(SUBSTR(BPR.TRANSACTION_DATE, 1, 10), 'YYYY-MM-DD')\r\n"
			+ "        WHEN PMD.APPLICATION_NUMBWER IS NOT NULL THEN \r\n"
			+ "            CAST(PMD.DATE_OF_PAYMENT AS DATE)\r\n"
			+ "    END AS payment_date,\r\n"
			+ "\r\n"
			+ "    CASE \r\n"
			+ "        WHEN BPR.CONSUMER_APPLICATION_NO IS NOT NULL THEN 'BILLDESK'\r\n"
			+ "        WHEN PMD.APPLICATION_NUMBWER IS NOT NULL THEN 'POSE MACHINE'\r\n"
			+ "        ELSE 'NOT PAID'\r\n"
			+ "    END AS payment_source,\r\n"
			+ "\r\n"
			+ "    -- ✅ Unique Reference Number (new column)\r\n"
			+ "    CAD.CONSUMER_APPLICATION_NUMBER || '_' || \r\n"
			+ "    NVL(\r\n"
			+ "        TO_CHAR(NVL(TO_NUMBER(NULLIF(BPR.AMOUNT, '')), NVL(PMD.TXN_AMOUNT, 0))),\r\n"
			+ "        '0'\r\n"
			+ "    ) AS unique_reference_no\r\n"
			+ "\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL CAD\r\n"
			+ "\r\n"
			+ "-- ✅ Billdesk Payment Join\r\n"
			+ "LEFT JOIN BILLDESK_PAYMENT_RES BPR \r\n"
			+ "    ON CAD.CONSUMER_APPLICATION_NUMBER = BPR.CONSUMER_APPLICATION_NO\r\n"
			+ "    AND TO_DATE(SUBSTR(BPR.TRANSACTION_DATE, 1, 7), 'YYYY-MM') = TO_DATE(:monthyear, 'YYYY-MM')\r\n"
			+ "    AND BPR.AUTH_STATUS = '0300'\r\n"
			+ "\r\n"
			+ "-- ✅ Pose Machine Payment Join\r\n"
			+ "LEFT JOIN POSE_MACHINE_D PMD \r\n"
			+ "    ON CAD.CONSUMER_APPLICATION_NUMBER = PMD.APPLICATION_NUMBWER\r\n"
			+ "    AND TO_CHAR(PMD.DATE_OF_PAYMENT, 'YYYY-MM') = :monthyear\r\n"
			+ "\r\n"
			+ "-- ✅ ERP Workflow Amount Join\r\n"
			+ "LEFT JOIN ERP_BUDGET_WORKFLOW_AMOUNT EBWA \r\n"
			+ "    ON CAD.CONSUMER_APPLICATION_NUMBER = EBWA.CONSUMER_APPLICATION_NO\r\n"
			+ "\r\n"
			+ "-- ✅ ERP Revision Table Join (for Revised Demand Fees)\r\n"
			+ "LEFT JOIN ERP_REV ERPR\r\n"
			+ "    ON CAD.REV_ERP = ERPR.ERP_NO\r\n"
			+ "    AND NVL(ERPR.PAY_AMT, 0) > 0\r\n"
			+ "    AND (BPR.ADDITIONAL_INFO1 LIKE '%Revised_Demand_fees%' OR PMD.PAYMENT_TYPE LIKE '%Revised_Demand_fees%')\r\n"
			+ "\r\n"
			+ "LEFT JOIN DISTRICT DI ON CAD.DISTRICT_ID = DI.DISTRICT_ID\r\n"
			+ "\r\n"
			+ "WHERE \r\n"
			+ "    CAD.GST_NUMBER IS NOT NULL \r\n"
			+ "    AND (BPR.CONSUMER_APPLICATION_NO IS NOT NULL OR PMD.APPLICATION_NUMBWER IS NOT NULL)",nativeQuery = true)
	public List<Map<String, ?>> getFinancePaymentDataForGSTApplication(String monthyear);
	
	
	@Query(value = "select * from consumer_application_detail where is_avedak_government_erp ='Yes' and  CONSUMER_ID = (select CONSUMER_ID from consumer where CONSUMER_LOGIN_ID =:loginId)", nativeQuery = true)
    public List<ConsumerApplicationDetail> changeMobileNOByApplicationisGOV(@Param("loginId") String loginId);

	
	
	@Query(value = "SELECT * from consumer_application_detail cad left join MAIN_DATA m on m.\"consumerApplicationNo\" = cad.CONSUMER_APPLICATION_NUMBER \r\n"
			+ "left join distribution_center dis on cad.DC_ID  = dis.DC_ID WHERE \"natureOfWorkTypeId\"=5  and cad.IS_ACTIVE = 1\r\n"
			+ "AND REG_PAY_DATE IS NOT NULL and IVRSNO IS NULL and cad.DC_ID =:DcCode and cad.CREATED<(SYSDATE-90) ORDER BY cad.CREATED DESC", nativeQuery = true)
    public List<ConsumerApplicationDetail> sendDataMeterReaderVijlance(@Param("DcCode") Long  DcCode);
	
	
	
	@Query(value = "SELECT \r\n"
			+ "    cad.CONSUMER_APPLICATION_ID,\r\n"
			+ "    cad.SCHEME_TYPE_ID,\r\n"
			+ "    cad.CONSUMER_ID,\r\n"
			+ "    cad.CONSUMER_NAME,\r\n"
			+ "    cad.GUARDIAN_NAME,\r\n"
			+ "    cad.ADDRESS,\r\n"
			+ "    cad.AADHAR_NO,\r\n"
			+ "    cad.CREATED,\r\n"
			+ "    cad.CREATED_BY,\r\n"
			+ "    cad.UPDATED,\r\n"
			+ "    cad.UPDATED_BY,\r\n"
			+ "    cad.APPLICATION_STATUS,\r\n"
			+ "    cad.DC_ID,\r\n"
			+ "    cad.SUBSTATION_ID,\r\n"
			+ "    cad.FEEDER_ID,\r\n"
			+ "    cad.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.SHORT_DESCRIPTION_OF_WORK,\r\n"
			+ "    cad.PIN_CODE,\r\n"
			+ "    cad.NATURE_OF_WORK_ID,\r\n"
			+ "    cad.DISTRICT_ID,\r\n"
			+ "    cad.IVRSNO,\r\n"
			+ "    cad.WORK_ALLOCATION_ADDRESS,\r\n"
			+ "    cad.PAYMENT_DATE,\r\n"
			+ "    cad.CREATED_BY AS PHONE_NUMBER,\r\n"
			+ "    geo.STARTING_LONGITUDE,\r\n"
			+ "    geo.STARTING_LATITUDE,\r\n"
			+ "    geo.ENDING_LATITUDE,\r\n"
			+ "    geo.ENDING_LONGITUDE,\r\n"
			+ "    dis.NGB_DC_CD,\r\n"
			+ "    'LV-5 Unmetered Agriculture' as TRAIFF_CODE,\r\n"
			+ "    TO_CHAR(cad.CREATED, 'YYYYMM') AS BILL_MONTH,\r\n"
			+ "    'NA' as ACCT_ID,\r\n"
			+ "    'Connection number not genrate' as REQUIRED_ACTION\r\n"
			+ "FROM consumer_application_detail cad\r\n"
			+ "LEFT JOIN MAIN_DATA m \r\n"
			+ "       ON m.\"consumerApplicationNo\" = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN distribution_center dis \r\n"
			+ "       ON cad.DC_ID = dis.DC_ID\r\n"
			+ "LEFT JOIN GEO_LOCATION geo \r\n"
			+ "       ON geo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "WHERE m.\"natureOfWorkTypeId\" = 5\r\n"
			+ "  AND cad.IS_ACTIVE = 1\r\n"
			+ "  AND cad.PUNCH_NAMA_REFERENCE_NO IS NULL\r\n"
			+ "  AND m.\"REG_PAY_DATE\" IS NOT NULL\r\n"
			+ "  AND cad.IVRSNO IS NULL\r\n"
			+ "AND cad.DC_ID = :DcCode\r\n"
			+ "  AND cad.CREATED < (SYSDATE - 90)\r\n"
			+ "ORDER BY cad.CREATED DESC\r\n"
			+ "", nativeQuery = true)
    public List<Map<String,String>> sendDataMeterReaderVijlance1(@Param("DcCode") Long  DcCode);

	
	
	@Query(value = "SELECT * from consumer_application_detail cad left join MAIN_DATA m on m.\"consumerApplicationNo\" = cad.CONSUMER_APPLICATION_NUMBER \r\n"
			+ "left join distribution_center dis on cad.DC_ID  = dis.DC_ID WHERE \"natureOfWorkTypeId\"=5  and cad.IS_ACTIVE = 1\r\n"
			+ "AND REG_PAY_DATE IS NOT NULL and IVRSNO IS NULL  and cad.CREATED<(SYSDATE-90) ORDER BY cad.CREATED DESC", nativeQuery = true)
    public List<ConsumerApplicationDetail> sendDataMeterReaderVijlance3();
	
	
	@Query(value = "SELECT * from CONSUMER_APPLICATION_DETAIL where OYT_TEMP_APPLICATION_NO =:consumerApplicationNO and IS_ACTIVE = 1", nativeQuery = true)
	public ConsumerApplicationDetail findByConsumerApplicationNumberTemporary(String consumerApplicationNO);
	
	
	
	
	@Query(value = "SELECT DISTINCT\r\n" + "    cad.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.erp_workflow_number,\r\n" + "  \r\n" + "    cfb.CONTRACTOR_NAME,\r\n" + " \r\n"
			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n" + "    now_tbl.NATURE_OF_WORK_NAME,\r\n" + "   \r\n"
			+ "    cir.circle,\r\n" + "    div.division,\r\n" + "    dc.dc_name,\r\n" + "    cad.CONSUMER_NAME,\r\n"
			+ "    cad.ADDRESS,\r\n" + "    cad.PHONE_NUMBER,\r\n" + "    cd.CONSUMER_MOBILE_NO,\r\n"
			+ "    vwo.WORK_ORDER_NO,\r\n" + "    vwo.WORK_ORDER_DATE,\r\n" + "    cad.WORK_COMPLETION_DATE\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n" + "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VENDOR_ADD_MATERIAL vam \r\n"
			+ "    ON cad.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN DISTRIBUTION_CENTER dc \r\n" + "    ON dc.DC_ID = cad.DC_ID\r\n"
			+ "LEFT JOIN SUB_DIVISION sub_div \r\n" + "    ON sub_div.SUBDIV_ID = dc.SUBDIV_ID\r\n"
			+ "LEFT JOIN DIVISION div \r\n" + "    ON div.DIV_ID = sub_div.DIVISION_ID\r\n"
			+ "LEFT JOIN CIRCLE cir \r\n" + "    ON cir.CIRCLE_ID = div.CIRCLE_ID\r\n" + "LEFT JOIN REGION reg \r\n"
			+ "    ON reg.REGION_ID = cir.REGION_ID\r\n" + "LEFT JOIN DISCOM disc \r\n"
			+ "    ON disc.DISCOM_ID = reg.DISCOM_ID\r\n" + "LEFT JOIN CONSUMER cd \r\n"
			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n" + "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN NATURE_OF_WORK now_tbl \r\n" + "    ON now_tbl.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
			+ "WHERE cad.NATURE_OF_WORK_ID IN (1,3,4,5,6,7,8,9,10,11)\r\n" + "  AND cad.APPLICATION_STATUS IN (27, 25, 28, 32, 33)\r\n"
			+ "  AND cad.RESAMPLING_FLAG IN (0, 1) and cfb.CONTRACTOR_AUTHANTICATION_ID is not null and  cad.CREATED >= TO_DATE('01-01-2025', 'DD-MM-YYYY')"
			+ "", nativeQuery = true)
	public List<Map<String, ?>> getReSampleDataForConsuemrApplicationDetailsNotNsc(ArrayList<Long> flagNo);

	@Query(value = "SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo and IS_ACTIVE = 1", nativeQuery = true)
	public List<ConsumerApplicationDetail> findByConsumerApplicationNo_1(String consumerApplicationNo);

	
	
	@Query(value = "SELECT DISTINCT\r\n" + "    cad.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.erp_workflow_number,\r\n" + "  \r\n" + "    cfb.CONTRACTOR_NAME,\r\n" + " \r\n"
			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n" + "    now_tbl.NATURE_OF_WORK_NAME,\r\n" + "   \r\n"
			+ "    cir.circle,\r\n" + "    div.division,\r\n" + "    dc.dc_name,\r\n" + "    cad.CONSUMER_NAME,\r\n"
			+ "    cad.ADDRESS,\r\n" + "    cad.PHONE_NUMBER,\r\n" + "    cd.CONSUMER_MOBILE_NO,\r\n"
			+ "    vwo.WORK_ORDER_NO,\r\n" + "    vwo.WORK_ORDER_DATE,\r\n" + "    cad.WORK_COMPLETION_DATE\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n" + "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VENDOR_ADD_MATERIAL vam \r\n"
			+ "    ON cad.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN DISTRIBUTION_CENTER dc \r\n" + "    ON dc.DC_ID = cad.DC_ID\r\n"
			+ "LEFT JOIN SUB_DIVISION sub_div \r\n" + "    ON sub_div.SUBDIV_ID = dc.SUBDIV_ID\r\n"
			+ "LEFT JOIN DIVISION div \r\n" + "    ON div.DIV_ID = sub_div.DIVISION_ID\r\n"
			+ "LEFT JOIN CIRCLE cir \r\n" + "    ON cir.CIRCLE_ID = div.CIRCLE_ID\r\n" + "LEFT JOIN REGION reg \r\n"
			+ "    ON reg.REGION_ID = cir.REGION_ID\r\n" + "LEFT JOIN DISCOM disc \r\n"
			+ "    ON disc.DISCOM_ID = reg.DISCOM_ID\r\n" + "LEFT JOIN CONSUMER cd \r\n"
			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n" + "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN NATURE_OF_WORK now_tbl \r\n" + "    ON now_tbl.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
			+ "WHERE cad.NATURE_OF_WORK_ID IN (2)\r\n" + "  AND cad.APPLICATION_STATUS IN (27, 25, 28, 32, 33)\r\n"
			+ "  AND cad.RESAMPLING_FLAG IN (0, 1) and cfb.CONTRACTOR_AUTHANTICATION_ID is not null and  cad.CREATED >= TO_DATE('01-01-2025', 'DD-MM-YYYY') and cad.SCHEME_TYPE_ID = 1", nativeQuery = true)
	public List<Map<String, ?>> getReSampleDataForConsuemrApplicationDetails(ArrayList<Long> l);
	
	
	@Query(value = "SELECT DISTINCT\r\n" + "    cad.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.erp_workflow_number,\r\n" + "  \r\n" + "    cfb.CONTRACTOR_NAME,\r\n" + " \r\n"
			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n" + "    now_tbl.NATURE_OF_WORK_NAME,\r\n" + "   \r\n"
			+ "    cir.circle,\r\n" + "    div.division,\r\n" + "    dc.dc_name,\r\n" + "    cad.CONSUMER_NAME,\r\n"
			+ "    cad.ADDRESS,\r\n" + "    cad.PHONE_NUMBER,\r\n" + "    cd.CONSUMER_MOBILE_NO,\r\n"
			+ "    vwo.WORK_ORDER_NO,\r\n" + "   cad.DC_ID,\r\n" + "    vwo.WORK_ORDER_DATE,\r\n" + "    cad.WORK_COMPLETION_DATE\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n" + "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VENDOR_ADD_MATERIAL vam \r\n"
			+ "    ON cad.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN DISTRIBUTION_CENTER dc \r\n" + "    ON dc.DC_ID = cad.DC_ID\r\n"
			+ "LEFT JOIN SUB_DIVISION sub_div \r\n" + "    ON sub_div.SUBDIV_ID = dc.SUBDIV_ID\r\n"
			+ "LEFT JOIN DIVISION div \r\n" + "    ON div.DIV_ID = sub_div.DIVISION_ID\r\n"
			+ "LEFT JOIN CIRCLE cir \r\n" + "    ON cir.CIRCLE_ID = div.CIRCLE_ID\r\n" + "LEFT JOIN REGION reg \r\n"
			+ "    ON reg.REGION_ID = cir.REGION_ID\r\n" + "LEFT JOIN DISCOM disc \r\n"
			+ "    ON disc.DISCOM_ID = reg.DISCOM_ID\r\n" + "LEFT JOIN CONSUMER cd \r\n"
			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n" + "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN NATURE_OF_WORK now_tbl \r\n" + "    ON now_tbl.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
			+ "WHERE cad.NATURE_OF_WORK_ID IN (2)\r\n" + "  AND cad.APPLICATION_STATUS IN (27, 25, 28, 32, 33)\r\n"
			+ "  AND cad.RESAMPLING_FLAG IN (0, 1) and cfb.CONTRACTOR_AUTHANTICATION_ID is not null and  cad.CREATED >= TO_DATE('01-01-2025', 'DD-MM-YYYY') and cad.SCHEME_TYPE_ID = 1"
			+ "  AND cad.DC_ID IN (:dcId)", nativeQuery = true)
	public List<Map<String, ?>> getReSampleDataForConsuemrApplicationDetailsByDcId(ArrayList<Integer> dcId);
	
	
	
	@Query(value = "SELECT distinct dc.DC_ID\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "LEFT JOIN distribution_center dc \r\n"
			+ "       ON dc.dc_id = cad.dc_id\r\n"
			+ "LEFT JOIN sub_division sub_div \r\n"
			+ "       ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "LEFT JOIN division div \r\n"
			+ "       ON div.div_id = sub_div.division_id\r\n"
			+ "LEFT JOIN circle cir  \r\n"
			+ "       ON cir.circle_id = div.circle_id\r\n"
			+ "LEFT JOIN region reg  \r\n"
			+ "       ON reg.region_id = cir.region_id\r\n"
			+ "LEFT JOIN discom disc  \r\n"
			+ "       ON disc.discom_id = reg.discom_id\r\n"
			+ "WHERE\r\n"
			+ "    disc.discom_id =:dicomId",nativeQuery = true)
	public ArrayList<Integer> getDcIdByDiscomId(Integer dicomId);
	
	@Query(value = "SELECT distinct dc.DC_ID\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "LEFT JOIN distribution_center dc \r\n"
			+ "       ON dc.dc_id = cad.dc_id\r\n"
			+ "LEFT JOIN sub_division sub_div \r\n"
			+ "       ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "LEFT JOIN division div \r\n"
			+ "       ON div.div_id = sub_div.division_id\r\n"
			+ "LEFT JOIN circle cir  \r\n"
			+ "       ON cir.circle_id = div.circle_id\r\n"
			+ "LEFT JOIN region reg  \r\n"
			+ "       ON reg.region_id = cir.region_id\r\n"
			+ "LEFT JOIN discom disc  \r\n"
			+ "       ON disc.discom_id = reg.discom_id\r\n"
			+ "WHERE\r\n"
			+ "    reg.region_id =:regionId",nativeQuery = true)
	public ArrayList<Integer> getDcIdByRegionId(Integer regionId);
	
	
	@Query(value = "SELECT distinct dc.DC_ID\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "LEFT JOIN distribution_center dc \r\n"
			+ "       ON dc.dc_id = cad.dc_id\r\n"
			+ "LEFT JOIN sub_division sub_div \r\n"
			+ "       ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "LEFT JOIN division div \r\n"
			+ "       ON div.div_id = sub_div.division_id\r\n"
			+ "LEFT JOIN circle cir  \r\n"
			+ "       ON cir.circle_id = div.circle_id\r\n"
			+ "LEFT JOIN region reg  \r\n"
			+ "       ON reg.region_id = cir.region_id\r\n"
			+ "LEFT JOIN discom disc  \r\n"
			+ "       ON disc.discom_id = reg.discom_id\r\n"
			+ "WHERE\r\n"
			+ "    cir.circle_id =:circleId",nativeQuery = true)
	public ArrayList<Integer> getDcIdByCircleId(Integer circleId);
	
	
	@Query(value = "SELECT distinct dc.DC_ID\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "LEFT JOIN distribution_center dc \r\n"
			+ "       ON dc.dc_id = cad.dc_id\r\n"
			+ "LEFT JOIN sub_division sub_div \r\n"
			+ "       ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "LEFT JOIN division div \r\n"
			+ "       ON div.div_id = sub_div.division_id\r\n"
			+ "LEFT JOIN circle cir  \r\n"
			+ "       ON cir.circle_id = div.circle_id\r\n"
			+ "LEFT JOIN region reg  \r\n"
			+ "       ON reg.region_id = cir.region_id\r\n"
			+ "LEFT JOIN discom disc  \r\n"
			+ "       ON disc.discom_id = reg.discom_id\r\n"
			+ "WHERE\r\n"
			+ "    div.div_id =:dividionId",nativeQuery = true)
	public ArrayList<Integer> getDcIdByDivisionId(Integer dividionId);
	
	@Query(value = "SELECT distinct dc.DC_ID\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "LEFT JOIN distribution_center dc \r\n"
			+ "       ON dc.dc_id = cad.dc_id\r\n"
			+ "LEFT JOIN sub_division sub_div \r\n"
			+ "       ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "LEFT JOIN division div \r\n"
			+ "       ON div.div_id = sub_div.division_id\r\n"
			+ "LEFT JOIN circle cir  \r\n"
			+ "       ON cir.circle_id = div.circle_id\r\n"
			+ "LEFT JOIN region reg  \r\n"
			+ "       ON reg.region_id = cir.region_id\r\n"
			+ "LEFT JOIN discom disc  \r\n"
			+ "       ON disc.discom_id = reg.discom_id\r\n"
			+ "WHERE\r\n"
			+ "    sub_div.SUBDIV_ID =:subDividionId",nativeQuery = true)
	public ArrayList<Integer> getDcIdBysubDivisionId(Integer subDividionId);
	
	
	@Query(value = "SELECT distinct dc.DC_ID\r\n"
			+ "FROM CONSUMER_APPLICATION_DETAIL cad\r\n"
			+ "LEFT JOIN distribution_center dc \r\n"
			+ "       ON dc.dc_id = cad.dc_id\r\n"
			+ "LEFT JOIN sub_division sub_div \r\n"
			+ "       ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "LEFT JOIN division div \r\n"
			+ "       ON div.div_id = sub_div.division_id\r\n"
			+ "LEFT JOIN circle cir  \r\n"
			+ "       ON cir.circle_id = div.circle_id\r\n"
			+ "LEFT JOIN region reg  \r\n"
			+ "       ON reg.region_id = cir.region_id\r\n"
			+ "LEFT JOIN discom disc  \r\n"
			+ "       ON disc.discom_id = reg.discom_id\r\n"
			+ "WHERE\r\n"
			+ "    dc.dc_id =:dcId",nativeQuery = true)
	public ArrayList<Integer> getDcIdByDcId(Integer dcId);
	
	@Query(value = "SELECT * from CONSUMER_APPLICATION_DETAIL where CONSUMER_APPLICATION_NUMBER =:consumerApplicationNo and IS_ACTIVE = 1", nativeQuery = true)
	public ConsumerApplicationDetail findByConsumerApplicationNo_11(String consumerApplicationNo);

}