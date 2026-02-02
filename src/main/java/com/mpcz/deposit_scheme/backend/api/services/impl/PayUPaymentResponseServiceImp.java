//package com.mpcz.deposit_scheme.backend.api.services.impl;
//
//import java.util.Objects;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
//import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
//import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentRequest;
//import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentResponse;
//import com.mpcz.deposit_scheme.backend.api.domain.PaymentHistory;
//import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
//import com.mpcz.deposit_scheme.backend.api.enums.PaymentStatusEnum;
//import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.InvoiceRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.PayUPaymentResponseRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.PaymentHistoryRepository;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
//import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
//import com.mpcz.deposit_scheme.backend.api.services.InvoiceService;
//import com.mpcz.deposit_scheme.backend.api.services.PayUPaymentRequestService;
//import com.mpcz.deposit_scheme.backend.api.services.PayUPaymentResponseService;
//
//@Service
//public class PayUPaymentResponseServiceImp implements PayUPaymentResponseService {
//	
//	Logger logger = LoggerFactory.getLogger(PayUPaymentResponseServiceImp.class);
//
//	@Autowired
//	private PayUPaymentResponseRepository paymentResponseRepository;
//
//	@Autowired
//	private PayUPaymentRequestService payUPaymentRequestService;
//
//	@Autowired
//	private InvoiceService invoiceService;
//
//	@Autowired
//	private InvoiceRepository invoiceRepository;
//	
//	@Autowired
//	PaymentHistoryRepository paymentHistoryRepository;
//	
//	@Autowired
//	ApplicationStatusService applicationStatusService;
//	
//	@Autowired
//	private ConsumerApplicationDetailService consumerApplicationDetailService;
//	
//	@Autowired
//	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
//
//	@Override
//	public PayUPaymentResponse save(PayUPaymentResponse t) {
//		// TODO Auto-generated method stub
//		return paymentResponseRepository.save(t);
//	}
//
//	@Override
//	public Response<PayUPaymentResponse> saveResponse(PayUPaymentResponse response) throws ConsumerApplicationDetailException {
//		System.out.println("inside response service " + response);
//		
////		  udf1 me consumer ka full name ja raha hai
////	      udf2 me consumer ki id ja rahi hai 
////	      udf3 me consumer ki application number ja rahi
////		  udf4 me consumer ki trancation id ja rahi hai jo humne gernerate ki hai invoice table wali transcation id
////		  udf me invoice id ja rahai hai invoice table se nikal kar consumer application ke bihaf par
//
//
//		Response<PayUPaymentResponse> response2 = new Response<PayUPaymentResponse>();
////		response.setIsActive(true);
//		PayUPaymentResponse payUPaymentResponse = this.save(response);
//
//		PayUPaymentRequest payUPaymentRequestDb = payUPaymentRequestService.findByTxnIdAndCustomerId(response.getTxnid(),
//				response.getUdf2());
//		
//         ConsumerApplicationDetail consumerApplicationDetail=
//        		 consumerApplicationDetailService.findConsumerApplicationDetailByApplicationNo(response.getUdf3());
//         
//		if (Objects.nonNull(payUPaymentRequestDb) && response.getStatus().equals("success")) {
//			System.err.println("inside success");
//
//			payUPaymentRequestDb.setPaymentStatus(PaymentStatusEnum.PAID.getValue());
//			payUPaymentRequestDb.setPaymentStatusCode(PaymentStatusEnum.PAID.getId().toString());
//			Invoice invoiceData = invoiceService.findByConsumerApplicationNumberAndTranscationNo(payUPaymentRequestDb.getUdf3(),
//					payUPaymentRequestDb.getUdf4());
//			if (Objects.nonNull(invoiceData)) {
//				invoiceData.setPaymentStatus(PaymentStatusEnum.PAID.getId().intValue());
//				invoiceData.setTypeOfInvoice(PaymentStatusEnum.PAID.getValue());
//				if(consumerApplicationDetail.getApplicationStatus().getApplicationStatusId().compareTo(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId()) == 0) {
//					
//					ApplicationStatus appStatusDb = applicationStatusService.findById(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId());
//					
//					consumerApplicationDetail.setApplicationStatus(appStatusDb);
//					
//				}else if(consumerApplicationDetail.getApplicationStatus().getApplicationStatusId().compareTo(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId()) == 0) {
//					
//	                 ApplicationStatus appStatusDb = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_SELECTING_CONTRACTOR.getId());
//					
//					consumerApplicationDetail.setApplicationStatus(appStatusDb);
//					
//					String contractSelectDate = consumerApplictionDetailRepository.findConsumerDetails(consumerApplicationDetail.getConsumerApplicationNo());
//					consumerApplicationDetail.setUpdatedDateForContractor(contractSelectDate);
//				}
//				consumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);
//				invoiceRepository.save(invoiceData);
//				payUPaymentRequestService.save(payUPaymentRequestDb);
//			} else {
//				response2.setCode(ResponseCode.NOT_FOUND);
//				response2.setMessage("INVOICE " + ResponseMessage.NOT_FOUND);
//			}
//			response2.setCode("200");
//			response2.setMessage("Success");
////				
//
//		}
//
//		else if (Objects.nonNull(payUPaymentRequestDb) && response.getStatus().equals("failure")) {
//
//			payUPaymentRequestDb.setPaymentStatus(PaymentStatusEnum.FAILURE.getValue());
//			payUPaymentRequestDb.setPaymentStatusCode(PaymentStatusEnum.FAILURE.getId().toString());
//			System.err.println(" inside failure");
//			Invoice invoiceData = invoiceService.findByConsumerApplicationNumberAndTranscationNo(payUPaymentRequestDb.getUdf3(),
//					payUPaymentRequestDb.getCustomerId());
//			if (Objects.nonNull(invoiceData)) {
//				invoiceData.setPaymentStatus(PaymentStatusEnum.FAILURE.getId().intValue());
//				invoiceData.setTypeOfInvoice(PaymentStatusEnum.FAILURE.getValue());
//				invoiceRepository.save(invoiceData);
//				payUPaymentRequestService.save(payUPaymentRequestDb);
//			}
//
//			else {
//				response2.setCode(ResponseCode.NOT_FOUND);
//				response2.setMessage("INVOICE " + ResponseMessage.NOT_FOUND);
//			}
//			response2.setCode("600");
//			response2.setMessage("Failure");
//
//		} else if (Objects.nonNull(payUPaymentRequestDb) && !response.getStatus().equals("failure")
//				&& !response.getStatus().equals("success")) {
////			if(response.getHash().equals(payUPaymentRequestDb.getHash())) {
//			payUPaymentRequestDb.setPaymentStatus(PaymentStatusEnum.FAILURE.getValue());
//			payUPaymentRequestDb.setPaymentStatusCode(PaymentStatusEnum.FAILURE.getId().toString());
//			Invoice invoiceData = invoiceService.findByConsumerApplicationNumberAndTranscationNo(payUPaymentRequestDb.getUdf3(),
//					payUPaymentRequestDb.getCustomerId());
//			if (Objects.nonNull(invoiceData)) {
//				invoiceData.setPaymentStatus(PaymentStatusEnum.FAILURE.getId().intValue());
//				invoiceData.setTypeOfInvoice(PaymentStatusEnum.FAILURE.getValue());
//				invoiceRepository.save(invoiceData);
//				payUPaymentRequestService.save(payUPaymentRequestDb);
//			} else {
//				response2.setCode(ResponseCode.NOT_FOUND);
//				response2.setMessage("INVOICE " + ResponseMessage.NOT_FOUND);
//			}
//			response2.setCode("600");
//			response2.setMessage("Failure");
//
//		}
//		
////		payent history code yaha likhna hai
//
////		  udf1 me consumer name aa raha hai
////		  udf2 me consumer ki id aa rahi hai 
////	      udf3 me consumer ki application number aa raha
////		  udf4 me consumer ki trancation id aa rahi hai jo humne gernerate ki hai invoice table wali transcation id
////		  udf5 me invoice id ja rahai hai invoice table se nikal kar consumer application ke bihaf par
//		
//		PaymentHistory paymentHistory=new PaymentHistory();
//		
//		paymentHistory.setAmount(response.getNetAmountDebit());
//		paymentHistory.setApplicationNo(response.getUdf3());
//		paymentHistory.setBankName(response.getBankcode());
//		paymentHistory.setConsumerMobNo(response.getPhone());
//		paymentHistory.setConsumerName(response.getUdf1());
//		paymentHistory.setInvoiceNo(response.getUdf5());
//		paymentHistory.setPaymentType(response.getProductinfo());
////		paymentHistory.setProtal(response);
////		paymentHistory.setReference(response);
////		paymentHistory.setRemark(response.);
//		paymentHistory.setRequestStatus(response.getStatus());
//		paymentHistory.setTransactionNo(response.getTxnid());
//		//paymentHistory.setCreated(new Date());
//		//paymentHistory.setUpdated(new Date());
//	
//		paymentHistoryRepository.save(paymentHistory);
//
//		return response2;
//	}
//
//}
