package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBid;

@Repository
public interface ContractorForBidRepository extends JpaRepository<ContractorForBid, Long> {

	@Query(value = "SELECT * from CONTRACTOR_FOR_BID where CONSUMER_APPLICATION_NUMBER =:applicationNo", nativeQuery = true)
	ContractorForBid findByApplicationNo(String applicationNo);

	@Query(value = "SELECT cfb.*,cfbws.USER_ID from CONTRACTOR_FOR_BID cfb left join CONTRACTOR_FOR_BID_WORK_STATUS  cfbws on cfb.CONSUMER_APPLICATION_NUMBER = cfbws.CONSUMER_APPLICATION_NUMBER \r\n"
			+ "where cfb.CONSUMER_APPLICATION_NUMBER =:applicationNo", nativeQuery = true)
	Map<String, String> findByApplicationNo1(String applicationNo);

	@Modifying
	@Transactional
	@Query(value = "delete from contractor_for_bid where consumer_application_number=:consumerApplicationNumber", nativeQuery = true)
	void deleteByConsumerApplicationNo(String consumerApplicationNumber);

	@Query(value = "select * from contractor_for_bid where contact_no is null order by  CONTRACTOR_NAME", nativeQuery = true)
	List<ContractorForBid> findAllApplication();

	@Query(value = "select * from contractor_for_bid where contractor_name = :contractorName and company_pincode = :pincode and contact_no is not null", nativeQuery = true)
	List<ContractorForBid> findAllContractorNameAndPincode(String contractorName, String pincode);

	@Query(value = "SELECT DISTINCT CONSUMER_APPLICATION_NO\r\n" + "FROM VENDOR_WORK_ORDER\r\n"
			+ "WHERE CONSUMER_APPLICATION_NO IN (\r\n" + "    SELECT CONSUMER_APPLICATION_NUMBER\r\n"
			+ "    FROM CONSUMER_APPLICATION_DETAIL\r\n" + "    WHERE CONSUMER_APPLICATION_NUMBER IN (\r\n"
			+ "        SELECT CONSUMER_APPLICATION_NUMBER\r\n" + "        FROM CONTRACTOR_FOR_BID\r\n"
			+ "        WHERE CONTRACTOR_AUTHANTICATION_ID IS NOT NULL\r\n"
			+ "          AND CONTRACTOR_AUTHANTICATION_ID = :authenticationId\r\n" + "    )\r\n"
			+ "      AND NATURE_OF_WORK_ID = 5\r\n" + "      AND APPLICATION_STATUS NOT IN (25,32,33)\r\n" + ")\r\n"
			+ "AND REGEXP_LIKE(WORK_ORDER_DATE, '^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$')\r\n"
			+ "AND TO_DATE(SUBSTR(WORK_ORDER_DATE, 1, 19), 'YYYY-MM-DD\"T\"HH24:MI:SS') < TO_DATE(:oneMonthBackDate, 'YYYY-MM-DD')", nativeQuery = true)
	List<Map<String, Object>> findByDistinctContractorAuthaticationId(String authenticationId, String oneMonthBackDate);

	@Query(value = "SELECT * from CONTRACTOR_FOR_BID where CONSUMER_APPLICATION_NUMBER =:applicationNo", nativeQuery = true)
	List<ContractorForBid> findByApplicationNumber(String applicationNo);

	@Query(value = "SELECT\r\n"
			+ "   :authenticationId AS CONTRACTOR_AUTHANTICATION_ID,consumer_application_number,consumer_name,application_status,ap.application_status_name\r\n"
			+ "FROM consumer_application_detail cad left join application_status ap on ap.application_status_id=cad.application_status where consumer_application_number in (\r\n"
			+ "SELECT DISTINCT CONSUMER_APPLICATION_NO\r\n" + "FROM VENDOR_WORK_ORDER\r\n"
			+ "WHERE CONSUMER_APPLICATION_NO IN (\r\n" + "    SELECT CONSUMER_APPLICATION_NUMBER\r\n"
			+ "    FROM CONSUMER_APPLICATION_DETAIL\r\n" + "    WHERE CONSUMER_APPLICATION_NUMBER IN (\r\n"
			+ "        SELECT CONSUMER_APPLICATION_NUMBER\r\n" + "        FROM CONTRACTOR_FOR_BID cfb\r\n"
			+ "        WHERE CONTRACTOR_AUTHANTICATION_ID IS NOT NULL\r\n"
			+ "          AND CONTRACTOR_AUTHANTICATION_ID = :authenticationId\r\n" + "    )\r\n"
			+ "      AND NATURE_OF_WORK_ID = 5\r\n" + "     AND APPLICATION_STATUS NOT IN (25,32,33)\r\n" + ")\r\n"
			+ "AND REGEXP_LIKE(WORK_ORDER_DATE, '^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$')\r\n"
			+ "AND TO_DATE(SUBSTR(WORK_ORDER_DATE, 1, 19), 'YYYY-MM-DD\"T\"HH24:MI:SS') < TO_DATE(:oneMonthBackDate, 'YYYY-MM-DD'))", nativeQuery = true)
	List<Map<String, Object>> oytContractorPendency(String authenticationId, String oneMonthBackDate);

	@Query(value = "select * from contractor_for_bid where contractor_authantication_id = :authenticationId and rownum <= 1", nativeQuery = true)
	ContractorForBid findByContractorAuthaticationId(String authenticationId);
	
	@Query(value = "select * from contractor_for_bid where contractor_authantication_id = :authenticationId", nativeQuery = true)
	List<ContractorForBid> findByContractorAuthaticationId1(String authenticationId);
}