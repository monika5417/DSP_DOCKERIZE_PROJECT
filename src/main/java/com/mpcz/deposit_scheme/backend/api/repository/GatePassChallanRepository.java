package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.GatePassChallan;

@Repository
public interface GatePassChallanRepository extends JpaRepository<GatePassChallan, Long> {
	
	
	@Query(value = "SELECT \r\n"
			+ "    gpc.GATE_PASS_CHALLAN_ID,\r\n"
			+ "    gpc.MANUAL_DI_NO,\r\n"
			+ "    gpc.NAME_OF_ENTITY,\r\n"
			+ "    gpc.LOA_ORDER_NO,\r\n"
			+ "    gpc.NAME_OF_ITEM,\r\n"
			+ "    gpc.MANUFACTURER_FIRM_NAME,\r\n"
			+ "    gpc.DESCRIPTION_OF_ITEM,\r\n"
			+ "    gpc.QUANTITY,\r\n"
			+ "    gpc.UOM,\r\n"
			+ "    gpc.VEHICLE_NAME_NUMBER,\r\n"
			+ "    gpc.DRIVER_NAME,\r\n"
			+ "    gpc.DI_NO,\r\n"
			+ "    gpc.DRIVER_CONTACT_NO,\r\n"
			+ "    gpc.ISSUED_TO,\r\n"
			+ "    gpc.RECEIVER_ENTITY_NAME,\r\n"
			+ "    gpc.RECEIVER_DETAILS,\r\n"
			+ "    gpc.CONTACT_PERSON,\r\n"
			+ "    gpc.CONTACT_NO,\r\n"
			+ "    gpc.CREATED_BY,\r\n"
			+ "    gpc.CREATED_DATE,\r\n"
			+ "    gpc.UPDATED_BY,\r\n"
			+ "    gpc.UPDATED_DATE,\r\n"
			+ "    gpc.CONSUMER_APPLICATION_NUMBER,\r\n"
			+ "    gpc.OUTWARD_QUANTITY,\r\n"
			+ "    gpc.LOA_ORDER_DATE,\r\n"
			+ "    gpc.ISSUE_DATE,\r\n"
			+ "    gpc.DIVISION_NAME,\r\n"
			+ "    gpc.DC_NAME,\r\n"
			+ "    gpc.CONSUMER_NAME,\r\n"
			+ "    gpc.ADDRESS,\r\n"
			+ "    gpc.CON_AUTHEN_NO,\r\n"
			+ "    gpc.WORK_ORDER_NO,\r\n"
			+ "    gpc.CIRCLE,\r\n"
			+ "    \r\n"
			+ "    md.MATERIAL_DETAILS_ID,\r\n"
			+ "    md.SERIAL_NO,\r\n"
			+ "    md.MATERIAL_SERIAL_NO,\r\n"
			+ "    md.BATCH_NO,\r\n"
			+ "    md.FINAL_REMARK,\r\n"
			+ "    md.CONSUMEMR_APPLICATION_NUMBER AS MD_CONS_APP_NO,\r\n"
			+ "    \r\n"
			+ "    vgk.VERIFIER_GATEKEEPER_ID,\r\n"
			+ "    vgk.GATEKEEPER_NAME,\r\n"
			+ "    vgk.GATEKEEPER_DESIGNATION,\r\n"
			+ "    vgk.CONSUEMR_APPLICATION_NUMBER AS VGK_CONS_APP_NO,\r\n"
			+ "    \r\n"
			+ "    via.VERIFIER_ISSUING_AUTHRITY_ID,\r\n"
			+ "    via.ISSUING_AUTHORITY_NAME,\r\n"
			+ "    via.ISSUING_AUTHORITY_DESIGNATION,\r\n"
			+ "    via.CONSUEMR_APPLICATION_NUMBER AS VIA_CONS_APP_NO,\r\n"
			+ "    \r\n"
			+ "    vb.VERIFIER_BY_ID,\r\n"
			+ "    vb.VERIFIED_BY_NAME,\r\n"
			+ "    vb.VERIFIED_BY_DESIGNATION,\r\n"
			+ "    vb.CONSUEMR_APPLICATION_NUMBER AS VB_CONS_APP_NO\r\n"
			+ "    \r\n"
			+ "FROM GATE_PASS_CHALLAN gpc\r\n"
			+ "LEFT JOIN MATERIAL_DETAILS md \r\n"
			+ "    ON gpc.CONSUMER_APPLICATION_NUMBER = md.CONSUMEMR_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VERIFIER_GATEKEEPER vgk\r\n"
			+ "    ON gpc.CONSUMER_APPLICATION_NUMBER = vgk.CONSUEMR_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VERIFIER_ISSUING_AUTHRITY via\r\n"
			+ "    ON gpc.CONSUMER_APPLICATION_NUMBER = via.CONSUEMR_APPLICATION_NUMBER\r\n"
			+ "LEFT JOIN VERIFIER_BY vb\r\n"
			+ "    ON gpc.CONSUMER_APPLICATION_NUMBER = vb.CONSUEMR_APPLICATION_NUMBER "
			+ "where gpc.CONSUMER_APPLICATION_NUMBER =:consumerApplicationNumberr\r\n"
			+ "", nativeQuery = true)
	public List<Map<String ,String>> getdata(String consumerApplicationNumberr);

}
