package com.mpcz.deposit_scheme.backend.api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAppCancellationRefundAmount;

public interface ConsumerAppCancellationRefundAmountRepository extends JpaRepository<ConsumerAppCancellationRefundAmount, Long> {

	ConsumerAppCancellationRefundAmount findByApplicationNoAndTxnAmount(String consumerApplicationNo, BigDecimal payAmount);

	List<ConsumerAppCancellationRefundAmount> findByApplicationNo(String consumerApplicaitonNo);

	ConsumerAppCancellationRefundAmount findByTxnId(String txnId);
	
	
	
	@Query(value="select * from CON_APP_CAN_REF_AMT where application_no=:consumerApplicaitonNo and is_active=1", nativeQuery = true)
	List<ConsumerAppCancellationRefundAmount> findByApplicationNoIsActive(String consumerApplicaitonNo);
	
//	29-04-2025
	@Query(value="select * from con_app_can_ref_amt where txn_id=:txnId and is_active=1",nativeQuery = true)
	ConsumerAppCancellationRefundAmount findByTxnIdIsActive(String txnId);
	
	@Query(value="select * from con_app_can_ref_amt where txn_amount=:payAmount and application_no=:consumerApplicationNo and is_active=1",nativeQuery = true)
	ConsumerAppCancellationRefundAmount findByApplicationNoAndTxnAmountIsActive(String consumerApplicationNo, BigDecimal payAmount);


}
