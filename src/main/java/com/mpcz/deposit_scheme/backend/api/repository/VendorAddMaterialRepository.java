package com.mpcz.deposit_scheme.backend.api.repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.VendorAddMaterial;

public interface VendorAddMaterialRepository extends JpaRepository<VendorAddMaterial, String> {

	List<VendorAddMaterial> findByConsumerApplicationNumber(String consAppNo);
   
	@Query(value = "SELECT DISTINCT * FROM VENDOR_ADD_MATERIAL vam LEFT JOIN consumer_application_detail cad ON cad.consumer_application_number = vam.CONSUMER_APPLICATION_NUMBER where CONSUMER_APPLICATION_NUMBER =:consAppNo", nativeQuery = true)
	VendorAddMaterial findByConsumerApplicationNumber1(String consAppNo);
	
	
	
	
	
	
	
	@Query(value = "SELECT DISTINCT\r\n"
			+ "    vam.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    cad.erp_workflow_number,\r\n"
			+ "    vam.TRANSFORMER_SERIAL_NO,\r\n"
			+ "    cfb.CONTRACTOR_NAME,\r\n"
			+ "    cfb.CONTRACTOR_AUTHANTICATION_ID,\r\n"
			+ "    now.NATURE_OF_WORK_NAME,\r\n"
			+ "    vam.VENDOR_NAME,\r\n"
			+ "    cir.circle,\r\n"
			+ "    div.division,\r\n"
			+ "    dc.dc_name,\r\n"
			+ "    cad.CONSUMER_NAME,\r\n"
			+ "    cad.ADDRESS,\r\n"
			+ "    cad.PHONE_NUMBER,\r\n"
			+ "    cd.CONSUMER_MOBILE_NO,\r\n"
			+ "    vwo.WORK_ORDER_NO,\r\n"
			+ "    vwo.WORK_ORDER_DATE,\r\n"
			+ "    cad.WORK_COMPLETION_DATE\r\n"
			+ "FROM VENDOR_ADD_MATERIAL vam\r\n"
			+ "LEFT JOIN CONTRACTOR_FOR_BID cfb \r\n"
			+ "    ON cfb.CONSUMER_APPLICATION_NUMBER = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN consumer_application_detail cad \r\n"
			+ "    ON cad.consumer_application_number = vam.CONSUMER_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN distribution_center dc \r\n"
			+ "    ON dc.dc_id = cad.dc_id\r\n"
			+ "LEFT JOIN sub_division sub_div \r\n"
			+ "    ON sub_div.SUBDIV_ID = dc.subdiv_id\r\n"
			+ "LEFT JOIN division div \r\n"
			+ "    ON div.div_id = sub_div.division_id\r\n"
			+ "LEFT JOIN circle cir \r\n"
			+ "    ON cir.circle_id = div.circle_id\r\n"
			+ "LEFT JOIN region reg \r\n"
			+ "    ON reg.region_id = cir.region_id\r\n"
			+ "LEFT JOIN discom disc \r\n"
			+ "    ON disc.discom_id = reg.discom_id\r\n"
			+ "LEFT JOIN CONSUMER cd \r\n"
			+ "    ON cad.CONSUMER_ID = cd.CONSUMER_ID\r\n"
			+ "LEFT JOIN VENDOR_WORK_ORDER vwo \r\n"
			+ "    ON vwo.CONSUMER_APPLICATION_NO = cad.consumer_application_number\r\n"
			+ "LEFT JOIN nature_of_work now \r\n"
			+ "    ON now.NATURE_OF_WORK_ID = cad.NATURE_OF_WORK_ID\r\n"
			+ "WHERE cad.NATURE_OF_WORK_ID IN (2)\r\n"
			+ "ORDER BY cfb.CONTRACTOR_AUTHANTICATION_ID ASC", nativeQuery = true)
	public List<Map<String, ?>> getReSampleData1();

	Optional<VendorAddMaterial> findByIdAndConsumerApplicationNumber(Long id, String consumerApplicationNumber);
	
	@Query(value = "SELECT vam.* \r\n"
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
			+ "WHERE cad.NATURE_OF_WORK_ID IN (2)\r\n"
			+ "  AND vam.RESAMPLING_FLAG =0\r\n"
			+ "AND cfb.CONTRACTOR_AUTHANTICATION_ID =:autheticationId", nativeQuery = true)
	public List<VendorAddMaterial> getReSampleDataByVendorTable(String autheticationId);

	List<VendorAddMaterial> findDistinctByPostFlagIn(List<Long> l);

	List<VendorAddMaterial> findByShufflingFlag(Long l);

	@Query(value = "SELECT DISTINCT * FROM VENDOR_ADD_MATERIAL where CONSUMER_APPLICATION_NUMBER =:consAppNo and (VENDOR_MATERIAL_SPECIFICATION like '%25%' or VENDOR_MATERIAL_SPECIFICATION like '%63%')", nativeQuery = true)
	List<VendorAddMaterial> findByConsumerApplicationNumber2(String consAppNo);
	
}
