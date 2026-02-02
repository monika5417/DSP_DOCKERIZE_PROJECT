package com.mpcz.deposit_scheme.backend.api.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentRequest1;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;

@Repository
public interface BllDeskPaymentRequestRepository extends JpaRepository<BillDeskPaymentRequest1, Long> {

	
	@Query(value = "SELECT sysdate from BILLDESK_PAYMENT_REQUEST where BILLDESK_PAYMENT_REQUEST_ID=:id", nativeQuery = true)
	public String findByDateId(Long id);

	@Query(value = "SELECT * from BILLDESK_PAYMENT_REQUEST where created like :date%", nativeQuery = true)
	public List<BillDeskPaymentRequest1> findByCreated(@Param("date")  String date);

	@Query(value = "SELECT * from BILLDESK_PAYMENT_REQUEST where PAYMNENT_UPDATE_DATE =:s", nativeQuery = true)
	public List<BillDeskPaymentRequest1> findbyPaymentUpdateDate(String s);

	@Query(value = "SELECT * from BILLDESK_PAYMENT_REQUEST where ORDER_ID =:orderId", nativeQuery = true)
	public BillDeskPaymentRequest1 findByOrderid(String orderId);
	
	@Query(value = "SELECT * FROM BILLDESK_PAYMENT_REQUEST WHERE created BETWEEN TO_TIMESTAMP(:endDate, 'DD-MM-YY HH:MI:SS AM') AND TO_TIMESTAMP(:startDate, 'DD-MM-YY HH:MI:SS AM')", nativeQuery = true)
	List<BillDeskPaymentRequest1> findBillRequestsBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);



	
	
}
