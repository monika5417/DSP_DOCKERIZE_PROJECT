//package com.mpcz.deposit_scheme.backend.api.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.mpcz.deposit_scheme.backend.api.domain.InvoiceDetail;
//
//public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long>{
//
//	void update(InvoiceDetail invoice);
//
//	List<InvoiceDetail> findByProperty(String property, String value);
//
//	List<InvoiceDetail> findByInvoice(String invoiceId);
//
//	InvoiceDetail findByProperties(String property1, String value1, Integer chargeId);
//
//	void updateByChargesId(InvoiceDetail invoice);
//
//}
