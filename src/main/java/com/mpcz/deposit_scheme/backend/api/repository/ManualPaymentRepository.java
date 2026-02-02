package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;

@Repository
public interface ManualPaymentRepository extends JpaRepository<ManualPayment, Long> {

	List<ManualPayment> findByConAppNo(String consumerAppNo);

	ManualPayment findByBillRefNo(String billRefNo);

	@Query(value = "select * from MANUAL_PAYMENT where CON_APP_NO =:consumerApplicationNo", nativeQuery = true)
	List<ManualPayment> findByConsumerApplicationNumber(String consumerApplicationNo);

	@Query(value = "select * from manual_payment where con_app_no=:consumerApplicationNo", nativeQuery = true)
	ManualPayment getDemandDataFromManualPayment(String consumerApplicationNo);

}
