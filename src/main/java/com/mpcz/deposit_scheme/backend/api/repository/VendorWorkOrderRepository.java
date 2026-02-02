package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.VendorWorkOrder;

@Repository
public interface VendorWorkOrderRepository extends JpaRepository<VendorWorkOrder, Long> {
	
//	@Query(value = "SELECT * from VENDOR_WORK_ORDER where CONSUMER_APPLICATION_NO =:applicationNo", nativeQuery = true)
	
	@Query(value = "SELECT * from VENDOR_WORK_ORDER WHERE WORK_ORDER_ID=(SELECT max(WORK_ORDER_ID) from VENDOR_WORK_ORDER where CONSUMER_APPLICATION_NO =:applicationNo)", nativeQuery = true)
	VendorWorkOrder findByApplicationNo(String applicationNo);

	List<VendorWorkOrder> findByConsumerApplicationNo(String applicationNo);
	
}
