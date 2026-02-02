package com.mpcz.deposit_scheme.backend.api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.InvoiceException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.request.InvoiceRequestForm;


public interface InvoiceService  {


	public Invoice save(Invoice invoice);
	
	
	public Invoice saveInvoiceWithConsumerApplicationNmber(InvoiceRequestForm invoice)throws InvoiceException, PaymentHistoryException, ConsumerApplicationDetailException, ApplicationHeadChargesException;

	public Optional<List<Invoice>> findPaymentTransactionAlreadyInitiated(String consumerApplicationNumber,
			BigDecimal bigDecimal);
	
	public Invoice findByConsumerApplicationNumberAndTranscationNo(String applicationNo, String transcationNo);


	



}
