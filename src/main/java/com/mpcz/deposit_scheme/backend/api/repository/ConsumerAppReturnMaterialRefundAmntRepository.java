package com.mpcz.deposit_scheme.backend.api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAppReturnMaterialRefundAmnt;

public interface ConsumerAppReturnMaterialRefundAmntRepository
		extends JpaRepository<ConsumerAppReturnMaterialRefundAmnt, Long> {

	List<ConsumerAppReturnMaterialRefundAmnt> findByApplicationNo(String consumerApplicaitonNo);

	ConsumerAppReturnMaterialRefundAmnt findByTxnId(String txnId);

	@Query(value = "select * from CONS_RET_MAT_REFU_AMT where application_no=:consumerApplicaitonNo and is_active=1", nativeQuery = true)
	List<ConsumerAppReturnMaterialRefundAmnt> findByApplicationNoIsActive(String consumerApplicaitonNo);

//	29-04-2025
	@Query(value = "select * from cons_ret_mat_refu_amt where txn_id=:txnId and is_active=1", nativeQuery = true)
	ConsumerAppReturnMaterialRefundAmnt findByTxnIdAndIsActive(String txnId);

	@Query(value = "select * from cons_ret_mat_refu_amt where application_no=:consuemerApplicationNo and TXN_AMOUNT=:totalZamaPaisa and REFUNDABLE_AMOUNT=:totalWapasKrneWalaPaisa and is_active=1", nativeQuery = true)
	ConsumerAppReturnMaterialRefundAmnt checkApplicationExistOrNot(String consuemerApplicationNo, String totalZamaPaisa,
			BigDecimal totalWapasKrneWalaPaisa);
}
