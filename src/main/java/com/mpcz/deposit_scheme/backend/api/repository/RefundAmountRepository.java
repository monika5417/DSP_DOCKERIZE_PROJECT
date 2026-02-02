package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;

public interface RefundAmountRepository extends JpaRepository<RefundAmount, Long> {

	RefundAmount findByConsumerApplicationNo(String consumerApplicationNo);

	
	
	@Query(value="select * from refund_amount where consumer_application_no=:consumerApplicationNo and is_active=1",nativeQuery = true)
	RefundAmount findByConsumerApplicationNoIsActive(String consumerApplicationNo);
	
	@Query(value="select * from refund_amount where finance_approval='true' and supplier_data='SEND'",nativeQuery = true)
	List<RefundAmount> getSupplierSendApplication();
}
