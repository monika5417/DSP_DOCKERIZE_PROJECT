package com.mpcz.deposit_scheme.backend.api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
import com.mpcz.deposit_scheme.backend.api.dto.InvoiceReportDto;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

//	void update(Invoice invoice);
//
//	Invoice findByProperty(String property, String value);
//
	@Query(value="select INVOICE_SEQ.NEXTVAl  FROM DUAL",nativeQuery = true)
	String getInvoiceNo();
	
	
	@Query(value="SELECT * FROM INVOICE  WHERE CONSUMER_APPLICATION_NO = :cunsumerApplicationNumber AND AMOUNT = :amount AND PAYMENT_STATUS = 1 " , nativeQuery = true)
	public Optional<List<Invoice>> findPaymentTransactionAlreadyInitiated (@Param("cunsumerApplicationNumber")String cunsumerApplicationNumber  , @Param("amount")BigDecimal amount);


	@Query(value="SELECT * FROM INVOICE WHERE CONSUMER_APPLICATION_NO =:consumerApplicationNumber AND TRANSACTION_NO=:tranasactionNumber AND PAYMENT_STATUS=1",nativeQuery = true)
	public Invoice findByConsumerApplicatioNoAndTranasactionNO(String consumerApplicationNumber,
			String tranasactionNumber) ;


	


	 
 
//
////	Invoice findPendingApplication(String applicationNo, String paymentType);
//	
//	Invoice findByConsumerApplicatinoNumberAndPaymentType(String applicationNo, String paymentType);
//
//
//	List<Invoice> findInvoicedApplication(String applicationNo);
//
//	String getTransactionNo();
//
//	List<Invoice> findPaidInvoicedApplication(String applicationNo);
//
//	InvoiceReportDto printRecipient(String applicationNo, String invoiceId, String customerId);
//
//	List<Invoice> findById(String applicationNumber);
//	
////	Invoice findById(String application);
//
//	
////	Invoice findPendingApplication(String applicationNo);
//	
//	Invoice findByConsumerApplicatinoNumber(String applicationNo);


}
