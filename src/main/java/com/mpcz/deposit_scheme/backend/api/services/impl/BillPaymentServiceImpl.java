//package com.mpcz.deposit_scheme.backend.api.services.impl;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
//import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
//import com.mpcz.deposit_scheme.backend.api.domain.PayUPaymentRequest;
//import com.mpcz.deposit_scheme.backend.api.dto.CustomerBillDTO;
//import com.mpcz.deposit_scheme.backend.api.enums.PaymentStatusEnum;
//import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
//import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
//import com.mpcz.deposit_scheme.backend.api.exception.InvoiceException;
//import com.mpcz.deposit_scheme.backend.api.exception.PayUpaymentRequestException;
//import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
//import com.mpcz.deposit_scheme.backend.api.repository.InvoiceRepository;
//import com.mpcz.deposit_scheme.backend.api.request.InvoiceRequestForm;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.BillPaymentService;
//import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
//import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
//import com.mpcz.deposit_scheme.backend.api.services.InvoiceService;
//import com.mpcz.deposit_scheme.backend.api.services.PayUPaymentRequestService;
//import com.mpcz.deposit_scheme.backend.api.utility.DateTimeUtility;
//import com.mpcz.deposit_scheme.backend.api.utility.Utility;
//
//@Service
////@Transactional(propagation = Propagation.REQUIRED)
//public class BillPaymentServiceImpl implements BillPaymentService {
//
//	@Autowired
//	private InvoiceService invoiceService;
//
//	@Autowired
//	private ConsumerService consumerService;
//
//	@Autowired
//	private InvoiceRepository invoiceRepository;
//
//	@Autowired
//	private ConsumerApplicationDetailService applicationDetailService;
//
//	@Autowired
//	PayUPaymentRequestService payUPaymentRequestService;
//
////	@Value("${spring.profiles.active}")
////	private String activeProfile;
////
////	@Value("${mpcz.account_no}")
////	private String accountNo;
////
////	@Value("${mpcz.ifcs}")
////	private String ifcsCode;
//
//	// PAY U payment gateway
//	@Value("${payu.key}")
//	private String payuKey;
//
//	@Value("${payu.salt}")
//	private String payuSalt;
//
//	@Value("${payu.surl}")
//	private String payuSurl;
//
//	@Value("${payu.furl}")
//	private String payuFurl;
//
//	@Value("${payu.merchantId}")
//	private String merchantId;
//
//	@Value("${payu.securityId}")
//	private String securityId;
//
//
//
//	Logger logger = LoggerFactory.getLogger(BillPaymentServiceImpl.class);
//
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public Response<PayUPaymentRequest> prePaymentProcessingPayU(CustomerBillDTO billDto, List<PayUPaymentRequest> list,
//			Response<PayUPaymentRequest> response, HttpServletRequest request) throws InvoiceException,
//			ConsumerApplicationDetailException, PaymentHistoryException, ConsumerApplicationDetailException, ApplicationHeadChargesException {
//		final String method = "BillPaymentServiceImpl : payUPaymentRequestService() method";
//		logger.error(method);
//		
//
//		InvoiceRequestForm irf = new InvoiceRequestForm();
//		irf.setConsumerApplicatinoNumber(billDto.getConsumerApplicationNumber());
//		Invoice invoice1 = invoiceService.saveInvoiceWithConsumerApplicationNmber(irf);
//
////		Invoice invoice = billDetailToInvoice(billDto, request);
//
//		PayUPaymentRequest payRequest = invoiceToPayUPaymentRequest(invoice1);
//
//		PayUPaymentRequest payRequest1 = payUPaymentRequestService.save(payRequest);
//
//		list.add(payRequest1);
//		response.setMessage(ResponseMessage.SUCCESS);
//		response.setCode(ResponseCode.OK);
//		response.setList(list);
//		return response;
//	}
//
//	private void applicationdetailToInvoice(ConsumerApplicationDetail applicationDetail) {
//
//	}
//
//	private Invoice billDetailToInvoice(final CustomerBillDTO billDto, HttpServletRequest request)
//			throws ParseException {
//		final String method = "BillPaymentServiceImpl : billDetailToInvoice() method";
//		logger.error(method);
//		Invoice invoicedb = invoiceRepository.findByConsumerApplicatioNoAndTranasactionNO(
//				billDto.getConsumerApplicationNumber(), billDto.getTranasactionNumber());
//		Invoice invoice = new Invoice();
//
//		// System.out.println("C1--");
//		invoice.setConsumerApplicatinoNumber(invoicedb.getConsumerApplicatinoNumber());
//		invoice.setTotalAmount(invoicedb.getTotalAmount());
//		invoice.setConsumerId(invoicedb.getConsumerId());
//		invoice.setDateOfPayment(new Date());
//		invoice.setMobileNumber(invoicedb.getMobileNumber());
//		invoice.setMobileNumber(invoicedb.getMobileNumber());
//		invoice.setEmail(invoicedb.getEmail());
//		invoice.setPaymentStatus(PaymentStatusEnum.PENDING.getId().intValue());
//		invoice.setRemark("");
//		invoice.setConsumerName(invoicedb.getConsumerName());
//		invoice.setPayBy(invoicedb.getPayBy());
//		invoice.setAppMode(invoicedb.getAppMode());
//		invoice.setPayeeId(invoicedb.getPayeeId());
//		invoice.setTranasactionNo(invoicedb.getTranasactionNo());
//		invoice.setConsumerId(invoicedb.getConsumerId());
//		invoice.setInvoiceId(invoicedb.getInvoiceId());
//
////		final String clientIp = Utility.getClientIp(request);
////		invoice.setRemoteIP(clientIp);
//		return invoice;
//	}
//
//	public PayUPaymentRequest invoiceToPayUPaymentRequest(final Invoice invoice)  {
//		// TODO Auto-generated method stub
//		// logger.debug("email" +
//		// dcDetailService.findByDcCode(invoice.getDcCode()).getMid());
//		final String method = "BillPaymentServiceImpl : invoiceToPayUPaymentRequest() method";
//		logger.error(method);
//
//		PayUPaymentRequest payRequest = new PayUPaymentRequest();
//
//		payRequest.setTxnId(invoice.getTranasactionNo());
//		payRequest.setAmount(Float.valueOf(invoice.getTotalAmount().toString()).toString().trim());
//		payRequest.setPrductInfo(invoice.getPaymentType());
//		String name[] = invoice.getConsumerName().split(" ");
//		payRequest.setFirstName(name[0]);
//		try {
//			payRequest.setLastName(name[1]);
//		} catch (Exception e) {
//		}
//		payRequest.setEmail(invoice.getEmail());
//		payRequest.setMobileNumber(invoice.getMobileNumber());
//		payRequest.setPaymentStatus(PaymentStatusEnum.PENDING.getValue().toString());
//		payRequest.setPaymentStatusCode(PaymentStatusEnum.PENDING.getId().toString());
//		payRequest.setCustomerId(invoice.getConsumerId().toString());
//		payRequest.setPrductInfo(invoice.getPaymentType());
////		payRequest.setSecurityId(securityId);
//		payRequest.setUdf1(invoice.getConsumerName());
//		payRequest.setUdf2(Long.toString(invoice.getConsumerId()));
////		payRequest.setUdf2(invoice.getConsumerId());
//		payRequest.setUdf3(invoice.getConsumerApplicatinoNumber());
//		payRequest.setUdf4(invoice.getTranasactionNo());
//		payRequest.setUdf5(invoice.getInvoiceId());
//		payRequest.setAmount(Float.valueOf(invoice.getTotalAmount().toString()).toString().trim());
//		System.err.println("amount in float : " + Float.valueOf(invoice.getTotalAmount().toString()));
//		payRequest.setSurl(payuSurl);
//		payRequest.setFurl(payuFurl);
//		payRequest.setSecurityId(securityId);
//		payRequest.setPrductInfo(invoice.getPaymentType());
//		payRequest.setMerchantId(merchantId);
//
////		  udf1 me consumer name ja raha hai
////	      udf2 me consumer ki id ja rahi hai 
////	      udf3 me consumer ki application number ja rahi
////		  udf4 me consumer ki trancation id ja rahi hai jo humne gernerate ki hai invoice table wali transcation id
////		  udf me invoice id ja rahai hai invoice table se nikal kar consumer application ke bihaf par
//
//		String joiner2 = payuKey + "|" + payRequest.getTxnId() + "|" + payRequest.getAmount().toString().trim() + "|"
//				+ payRequest.getPrductInfo() + "|" + payRequest.getFirstName() + "|" + payRequest.getEmail() + "|"
//				+ payRequest.getUdf1() + "|" + payRequest.getUdf2() + "|" + payRequest.getUdf3() + "|"
//				+ payRequest.getUdf4() + "|" + payRequest.getUdf5() + "||||||" + payuSalt.toString();
//
//		System.err.println("hash : " + joiner2);
//
//		String msgString = getPayUCheckSum(joiner2, "");
//
//		payRequest.setHash(msgString);
//
//		System.err.println("Payu generated Hash" + msgString);
//		return payRequest;
//
//	}
//
//	public String getPayUCheckSum(String passwordToHash, String salt) {
//		final String method = "BillPaymentServiceImpl : getPayUCheckSum() method";
//		logger.error(method);
//		String generatedPassword = null;
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-512");
//			// md.update(salt.getBytes(StandardCharsets.UTF_8));
//			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < bytes.length; i++) {
//				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//			}
//			generatedPassword = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return generatedPassword.toString();
//	}
//
//}