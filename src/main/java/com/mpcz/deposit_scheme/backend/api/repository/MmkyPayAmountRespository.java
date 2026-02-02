package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;

@Repository
public interface MmkyPayAmountRespository extends JpaRepository<MmkyPayAmount, Long> {

	MmkyPayAmount findByConsumerApplicationNumber(String consumerAppNo);

	@Query(value = "select * from MKY_PAY_AMNT where MSG_SEND='Unsend'", nativeQuery = true)
	List<MmkyPayAmount> findAllUnsendMsg();

	@Query(value = "select * from MKY_PAY_AMNT where erp_num=:erpNumber", nativeQuery = true)
	Optional<MmkyPayAmount> findByErpNumber(String erpNumber);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from mky_pay_amnt where con_app_num=:consumerApplicationNo", nativeQuery = true)
	void deleteByConsumerApplicationNo(String consumerApplicationNo);

	@Query(value = "select * from MKY_PAY_AMNT where con_app_num=:consumerApplicationNumber AND erp_num=:erpNumber ", nativeQuery = true)
	MmkyPayAmount findByConsumerApplicationNumberAndErpNumber(String consumerApplicationNumber, String erpNumber);

}
