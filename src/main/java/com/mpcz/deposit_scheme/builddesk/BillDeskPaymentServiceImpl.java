//package com.mpcz.deposit_scheme.builddesk;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
//import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
//import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//
//@Service
//public class BillDeskPaymentServiceImpl implements BillDeskPaymentService {
//	
//
//	@Autowired(required=false)
//	PaymentProperties paymentProperties;
//	
//	
//	@Autowired
//	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
//	
//	@Autowired
//	EstimateAmountRepository   estimateAmountRepository;
//
//	@Autowired
//	ConsumerRepository consumerRepository;
//	
//	
//	@Autowired
//	ViewBillUtility viewBillUtility;
//	
//	@Autowired(required=false)
//	BllDeskPaymentRequestRepository bllDeskPaymentRequestRepository;
//	
//	@Value("{payment.merchantId}")
//	private String billDeskMerchantId;
//	
//	@Value("{payment.keyForPgiCheckSum}")
//	private String keyForPgiCheckSum;
//	
//	@Value("{billDesk.checksumKey}")
//	private String billDeskChecksumKey;
//	
//	@Value("{billDesk.callbackURL}")
//	private String billDeskCallbackUrl;
//	
//	@Value("{billDesk.securityId}")
//	private String billDeskSecurityId;
//	
//	
//	@Override
//	public Response<String> prePaymentProcessingBillDesk(CustomerBillDTO billDto, List<String> list,
//			Response<String> response, HttpServletRequest request) throws Exception {
//		// TODO Auto-generated method stub
//		final String method = "BillPaymentServiceImpl : prePaymentProcessingBillDesk() method";
//		//logger.error(method);
//		//Invoice invoice = billDetailToInvoice(billDto, request);
//		BillDeskPaymentRequest payRequest = invoiceToBillDeskPaymentRequest(billDto);
//	
//		//invoice.setRaoCode(Long.parseLong(payRequest.getAdditionalInfo3()));
//		//logger.error("Invoice Object " + invoice);
//		//invoiceService.save(invoice);
//System.out.println(payRequest.toString()+"--------------------------------------------------------");
//		payRequest = bllDeskPaymentRequestRepository.save(payRequest);
//		list.add(payRequest.getChecksum());
//		response.setMessage(ResponseMessage.SUCCESS);
//		response.setCode(ResponseCode.OK);
//		response.setList(list);
//		return response;
//	}
//	
//	private BillDeskPaymentRequest invoiceToBillDeskPaymentRequest(final CustomerBillDTO invoice)
//			throws  Exception {
//		// TODO Auto-generated method stub
//		// logger.debug("email" +
//		// dcDetailService.findByDcCode(invoice.getDcCode()).getMid());
//		
//		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository.findByConsumerApplicationNumber(invoice.getConsumerAppllicationNo());
//		
//	//	consumerRepository.findByConsumeId(findByConsumerApplicationNumber.getCons());
//		//String orderId=findByConsumerApplicationNumber.getConsumerApplicationNo()+""+new Date().getTime();
//		
//		BillDeskPaymentRequest payRequest = new BillDeskPaymentRequest();
//		//logger.error("invoiceToBillDeskPaymentRequest" + invoice.getPayBy() + "=check web or wap=");
//		
//			//payRequest.setRu(paymentProperties.getBillDeskCallbackUrl());
//			payRequest.setRu("localhost");
//
//
//		payRequest.setCreated(new Date());
//		payRequest.setCreatedBy("System");
//		payRequest.setUpdated(new Date());
//		payRequest.setUpdatedBy("System");
//		payRequest.setIsActive("Y");
//
//		payRequest.setCustomerId(findByConsumerApplicationNumber.getConsumerApplicationNo());
//		String orderId="DS-"+LocalDate.now().toString() +  LocalTime.now().getHour()+LocalTime.now().getMinute()+LocalTime.now().getSecond();
//		orderId=orderId.replaceAll("\\-|\\:|\\.", "");
//		
//		
//		
//			payRequest.setAdditionalInfo1(orderId);
////
////		if (Objects.nonNull(invoice.getEmailId()))
////			payRequest.setAdditionalInfo2(invoice.getEmailId());
//
//		// payRequest.setRu(paymentProperties.getBillDeskCallbackUrl());
//		payRequest.setSecurityId(billDeskSecurityId);
//
//		//logger.debug("DCCode>>>>>" + invoice.getDcCode());
//		Long dcId = findByConsumerApplicationNumber.getDistributionCenter().getDcId();
//		
//		if (dcId != null) {
//			//String mid = paymentProperties.getBillDeskMerchantId();
//			String mid = billDeskMerchantId;
//		//	Long raoCode = dcId.getRaoCode();
//			//payRequest.setAdditionalInfo3(raoCode.toString());
//			payRequest.setMerchantId(mid);
//		} else {
//			Response<String> response = new Response<String>();
//			response.setCode(ResponseCode.NOT_FOUND);
//			response.setMessage(ResponseMessage.NOT_FOUND);
//			throw new DistributionCenterException(response);
//		}
//
//		//payRequest.setAdditionalInfo4(invoice.getCustomerId().trim());
//	//	payRequest.setAdditionalInfo5(invoice.getCustomerName());
//
//		StringBuilder sbMercUniqueRef = new StringBuilder("");
//		sbMercUniqueRef.append(findByConsumerApplicationNumber.getConsumerApplicationNo().trim()).append("_")
//				.append(orderId).append("_")
//				//.append(payRequest.getAdditionalInfo3())// RAO_CODE
//				.append("_").append(dcId);
//				//.append("_").append(invoice.getChannel());
//
//		payRequest.setAdditionalInfo6(sbMercUniqueRef.toString());
//		// channel and due date added on 27112020 start
//		//payRequest.setAdditionalInfo7(invoice.getDueDate().toString());
//		// end
//		
//		Optional<ErpEstimateAmountData> findById = estimateAmountRepository.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
//		ErpEstimateAmountData erpEstimateAmountData = findById.get();
//		String schemeTypeName = findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName();
//		if(schemeTypeName.equalsIgnoreCase("Supervision"))
//		payRequest.setTxnAmount(erpEstimateAmountData.getTotalBalanceSupervisionAmount());
//		else
//			payRequest.setTxnAmount(erpEstimateAmountData.getBalanceDepositAmount());
//
//		payRequest.setBankid("NA");
//		payRequest.setCurrencyType("INR");
//		payRequest.setFiller1("NA");
//		
//		payRequest.setFiller2("NA");
//		payRequest.setFiller3("NA");
//		payRequest.setFiller4("NA");
//		payRequest.setFiller5("NA");
//		payRequest.setItemCode("NA");
//		payRequest.setTypeField1("R");
//		payRequest.setTypeField2("F");
//
//		String msgString = viewBillUtility.getBillDeskCheckSum(payRequest);
//		payRequest.setChecksum(msgString);
//
//		return payRequest;
//	}
//
//}
