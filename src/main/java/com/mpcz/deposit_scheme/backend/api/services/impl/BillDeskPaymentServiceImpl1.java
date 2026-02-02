package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.builddesk.CustomerBillDTO;
import com.mpcz.deposit_scheme.backend.api.builddesk.PaymentProperties;
import com.mpcz.deposit_scheme.backend.api.builddesk.ViewBillUtility;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentRequest1;
import com.mpcz.deposit_scheme.backend.api.domain.CalculationOytTempOrParmanent;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.OytCalculationTempOrParma1;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentType;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.repository.BllDeskPaymentRequestRepository;
import com.mpcz.deposit_scheme.backend.api.repository.CalculationOytTempOrParmanentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.OytCalculationTempOrParmaRepository;
import com.mpcz.deposit_scheme.backend.api.repository.OytCalculationTempOrParmaRepository1;
import com.mpcz.deposit_scheme.backend.api.repository.PaymentTypeRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.BillDeskPaymentService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;

@Service
public class BillDeskPaymentServiceImpl1 implements BillDeskPaymentService {
	//

	@Autowired(required = false)
	PaymentProperties paymentProperties;

	@Autowired
	ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	EstimateAmountRepository estimateAmountRepository;

	@Autowired
	ConsumerRepository consumerRepository;

	@Autowired
	PaymentTypeRepository paymentTypeRepository;

	@Autowired
	ViewBillUtility viewBillUtility;

	@Autowired(required = false)
	BllDeskPaymentRequestRepository bllDeskPaymentRequestRepository;

	@Autowired
	BillDeskPaymentService billDeskPaymentService;

	@Autowired
	ConsumerRepository consumerRepositorys;

	@Autowired
	MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	private ErpRevRepository erpRevRepository;

//  BillDesk Payment Configuration
	@Value("${client.id}")
	private String clientId;

	@Value("${hash.key}")
	private String hashKey;

	@Value("${client.key}")
	private String clientKey;

	@Autowired
	private OytCalculationTempOrParmaRepository oytCalculationTempOrParmaRepository;

	@Autowired
	private CalculationOytTempOrParmanentRepository calculatiocOytTempOrParmanentRepository;

	@Autowired
	private OytCalculationTempOrParmaRepository1 oytCalculationTempOrParmaRepository1;
	
	@Override
	public Response<Object> prePaymentProcessingBillDesk(CustomerBillDTO billDto, List<Object> list,
			Response<Object> response, HttpServletRequest request) throws Exception {
		final String method = "BillPaymentServiceImpl : prePaymentProcessingBillDesk() method";
		// logger.error(method);
		// Invoice invoice = billDetailToInvoice(billDto, request);
		BillDeskPaymentRequest1 invoiceToBillDeskPaymentRequest = invoiceToBillDeskPaymentRequest(billDto);

		// invoice.setRaoCode(Long.parseLong(payRequest.getAdditionalInfo3()));
		// logger.error("Invoice Object " + invoice);
		// invoiceService.save(invoice);
		System.out.println(invoiceToBillDeskPaymentRequest.toString()
				+ "--------------------------------------------------------");
		BillDeskPaymentRequest1 save = bllDeskPaymentRequestRepository.save(invoiceToBillDeskPaymentRequest);
		// list.add(save.getChecksum());
		list.add(save);
		// list.add(Arrays.asList)
		response.setMessage(ResponseMessage.SUCCESS);
		response.setCode(ResponseCode.OK);
		response.setList(list);
		return response;
	}

	private BillDeskPaymentRequest1 invoiceToBillDeskPaymentRequest(CustomerBillDTO invoice) throws Exception {
		// TODO Auto-generated method stub
		// logger.debug("email" +
		// dcDetailService.findByDcCode(invoice.getDcCode()).getMid());

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(invoice.getConsumerAppllicationNo());
		Consumer consumer = consumerRepositorys
				.findByConsumerId(findByConsumerApplicationNumber.getConsumers().getConsumerId());
		// consumerRepository.findByConsumeId(findByConsumerApplicationNumber.getCons());
		// String
		// orderId=findByConsumerApplicationNumber.getConsumerApplicationNo()+""+new
		// Date().getTime();

		BillDeskPaymentRequest1 payRequest = new BillDeskPaymentRequest1();
		// logger.error("invoiceToBillDeskPaymentRequest" + invoice.getPayBy() + "=check
		// web or wap=");

		// payRequest.setRu(paymentProperties.getBillDeskCallbackUrl());
		// payRequest.setRu(paymentProperties.getBillDeskCallbackUrl());

		payRequest.setCreated(new Date());
		payRequest.setCreatedBy("System");
		payRequest.setUpdated(new Date());
		payRequest.setUpdatedBy("System");
		payRequest.setIsActive("Y");
		payRequest.setConsumerAppliNo(invoice.getConsumerAppllicationNo());

//	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String s = formatter.format(date);
		payRequest.setPaymentUpdateDate(s);

		payRequest.setCustomerId(findByConsumerApplicationNumber.getConsumerApplicationNo());
		String orderId = "DS-" + LocalDate.now().toString() + LocalTime.now().getHour() + LocalTime.now().getMinute()
				+ LocalTime.now().getSecond();
		orderId = orderId.replaceAll("\\-|\\:|\\.", "");
		if (consumer != null) {
			payRequest.setConsumerMobileNo(consumer.getConsumerMobileNo());
			payRequest.setConsumerName(findByConsumerApplicationNumber.getConsumerName());
			payRequest.setEmailId(consumer.getConsumerEmailId());
		}

		payRequest.setOrderId(orderId);
		//
//			if (Objects.nonNull(invoice.getEmailId()))
//				payRequest.setAdditionalInfo2(invoice.getEmailId());

		// payRequest.setRu(paymentProperties.getBillDeskCallbackUrl());
		// payRequest.setSecurityId(paymentProperties.getBillDeskSecurityId());

		// logger.debug("DCCode>>>>>" + invoice.getDcCode());
		Long dcId = findByConsumerApplicationNumber.getDistributionCenter().getDcId();

		if (dcId != null) {
			// String mid = paymentProperties.getBillDeskMerchantId();
//			String mid = "MPMKDSPV2";

			String mid = clientId;
//			String mid = "UATMPMKNSC";
			// Long raoCode = dcId.getRaoCode();
			// payRequest.setAdditionalInfo3(raoCode.toString());
			payRequest.setMerchantId(mid);
		} else {
			Response<String> response = new Response<String>();
			response.setCode(ResponseCode.NOT_FOUND);
			response.setMessage(ResponseMessage.NOT_FOUND);
			throw new DistributionCenterException(response);
		}

		// payRequest.setAdditionalInfo4(invoice.getCustomerId().trim());
		// payRequest.setAdditionalInfo5(invoice.getCustomerName());

		StringBuilder sbMercUniqueRef = new StringBuilder("");
		sbMercUniqueRef.append(findByConsumerApplicationNumber.getConsumerApplicationNo().trim()).append("_")
				.append(orderId).append("_")
				// .append(payRequest.getAdditionalInfo3())// RAO_CODE
				.append("_").append(dcId);
		// .append("_").append(invoice.getChannel());

		payRequest.setAdditionalInfo6(sbMercUniqueRef.toString());
		// channel and due date added on 27112020 start
		// payRequest.setAdditionalInfo7(invoice.getDueDate().toString());
		// end

		if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId()
				.compareTo(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId()) == 0) {

			Optional<PaymentType> paymentType = paymentTypeRepository.findById(1l);
			PaymentType paymentType2 = paymentType.get();
			BigDecimal bd = paymentType2.getAmount();
			BigDecimal double1 = bd;
			BigDecimal cgst = new BigDecimal(90);
			BigDecimal sgst = new BigDecimal(90);

			BigDecimal registration = double1.add(cgst).add(sgst);
			payRequest.setAdditionalInfo7("Registration_Fees");

//			in case of OYT new Flow (ssp application ke liye)

			// BigDecimal.doubleValue(payment);
			payRequest.setTxnAmount(registration);

//         ye code ankit sir ke khene pr band kiya gya hai
//			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)
//					&& findByConsumerApplicationNumber.getOytTepOrParma().equals("temporary")) {
////				htrewertvg
//				try {
//					
//					
//					
//					CalculationOytTempOrParmanent findByTarifAndPhaseAndLoad = calculatiocOytTempOrParmanentRepository
//							.findByTarifAndPhaseAndLoad(findByConsumerApplicationNumber.getPremiseAreaType(),
//									findByConsumerApplicationNumber.getPhase(),
//									findByConsumerApplicationNumber.getLoadRequested().toString());
//					if (findByTarifAndPhaseAndLoad != null) {
//
//						payRequest
//								.setTxnAmount(registration.add(new BigDecimal(findByTarifAndPhaseAndLoad.getAmount())));
//						payRequest.setAdditionalInfo5(new BigDecimal(findByTarifAndPhaseAndLoad.getAmount()) + "");
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			
			
			
			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(5l)
					&& findByConsumerApplicationNumber.getOytTepOrParma().equals("temporary")) {
//				htrewertvg
				try {
					long it =(long) Double.parseDouble(findByConsumerApplicationNumber.getLoadRequested());
					System.err.println("it " + it);
				OytCalculationTempOrParma1 findByTarifAndPhaseAndLoadBetween = oytCalculationTempOrParmaRepository1
							.findSlabData((long) Double.parseDouble(findByConsumerApplicationNumber.getLoadRequested()),findByConsumerApplicationNumber.getPremiseAreaType(),
									findByConsumerApplicationNumber.getPhase());
				
//					OytCalculationTempOrParma1 findByTarifAndPhaseAndLoadBetween = oytCalculationTempOrParmaRepository1
//							.findByTarifAndPhaseAndLoadGreaterThanEqual(findByConsumerApplicationNumber.getPremiseAreaType(),
//									findByConsumerApplicationNumber.getPhase(),
//									(long) Double.parseDouble(findByConsumerApplicationNumber.getLoadRequested()));
					
					
					if (findByTarifAndPhaseAndLoadBetween != null) {

						BigDecimal multiply = new BigDecimal((long) Double.parseDouble(findByConsumerApplicationNumber.getLoadRequested())).multiply(new BigDecimal(findByTarifAndPhaseAndLoadBetween.getAmount()));
						
						BigDecimal add = multiply.add(new BigDecimal( findByTarifAndPhaseAndLoadBetween.getAddinalAmount()));
						
							
						payRequest
								.setTxnAmount(registration.add(add));
						payRequest.setAdditionalInfo5(add + "");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId() == 8) {
				MmkyPayAmount findByConsumerApplicationNumber1 = mmkyPayAmountRespository
						.findByConsumerApplicationNumber(findByConsumerApplicationNumber.getConsumerApplicationNo());
				if (findByConsumerApplicationNumber.getErpVersion() == null) {
					payRequest.setTxnAmount(findByConsumerApplicationNumber1.getPayableAmount());
					payRequest.setAdditionalInfo7("Demand_fees");
				} else {
					ErpRev findByConsAppNo = erpRevRepository
							.findByConsAppNo(findByConsumerApplicationNumber.getConsumerApplicationNo());
					payRequest.setTxnAmount(findByConsAppNo.getPayAmt());
					payRequest.setAdditionalInfo7("Revised_Demand_fees");
				}
			} else {
				if ((findByConsumerApplicationNumber.getErpVersion() != null)
						&& (findByConsumerApplicationNumber.getErpVersion().equals("V2"))
						&& (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId().compareTo(
								ApplicationStatusEnum.REMAING_DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId()) == 0)) {

					ErpRev findByConsAppNo = erpRevRepository
							.findByConsAppNo(findByConsumerApplicationNumber.getConsumerApplicationNo());
					payRequest.setTxnAmount(findByConsAppNo.getPayAmt());
					payRequest.setAdditionalInfo7("Revised_Demand_fees");

				} else {

					Optional<ErpEstimateAmountData> findById = estimateAmountRepository
							.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
					ErpEstimateAmountData erpEstimateAmountData = findById.get();

					String schemeTypeName = findByConsumerApplicationNumber.getSchemeType().getSchemeTypeName();
					if (schemeTypeName.equalsIgnoreCase("Supervision")) {
						if (erpEstimateAmountData.getSchema().equalsIgnoreCase("OYT")) {
							payRequest.setTxnAmount(erpEstimateAmountData.getTotalBalanceSupervisionAmount());
							payRequest.setAdditionalInfo7("Demand_fees");
						} else {
							payRequest.setTxnAmount(erpEstimateAmountData.getTotalBalanceSupervisionAmount());
							payRequest.setAdditionalInfo7("Demand_fees");
//				payRequest.setTxnAmount(Double.parseDouble(erpEstimateAmountData.getSupervisionAmount())+erpEstimateAmountData.getCgst()+erpEstimateAmountData.getSgst());
							if (findByConsumerApplicationNumber.getConsumerApplicationNo().equals("SV1719207823064")) {
								payRequest.setTxnAmount(erpEstimateAmountData.getTotalBalanceSupervisionAmount());
								payRequest.setAdditionalInfo7("Second_Revised_Demand_fees");
							}

						}
					} else {
						payRequest.setTxnAmount(erpEstimateAmountData.getTotalBalanceDepositAmount());

//			monika code start check added for application minus cost not taken application 23-august-2024
						if (findByConsumerApplicationNumber.getConsumerApplicationNo().equals("DS1720455056974")) {
							payRequest.setTxnAmount(new BigDecimal(39529));
						}
						if (findByConsumerApplicationNumber.getConsumerApplicationNo().equals("DS1721295247685")) {
							payRequest.setTxnAmount(new BigDecimal(52090));
						}
//					end 23-august-2024

						payRequest.setAdditionalInfo7("Demand_fees");
					}

				}
			}

			if (findByConsumerApplicationNumber.getConsumerApplicationNo().equals("DS1726920706365")) {
				payRequest.setTxnAmount(new BigDecimal(504325));
			}

//			is application ka CGST or SGST ka payment lena hai bs
			if (findByConsumerApplicationNumber.getConsumerApplicationNo().equals("DS1723432888995")) {

				payRequest.setTxnAmount(new BigDecimal(14540));
				payRequest.setAdditionalInfo7("Second_Revised_Demand_fees");
			}
		}

//			String msgString = viewBillUtility.getBillDeskCheckSum(payRequest);
//			System.out.println(msgString+"-------------------------------------------------------");
//			payRequest.setChecksum(msgString);
//			

		String str = payRequest.toString();

//		production scretkey **************************Prod*****************************************//

//		String scretKey = "IJLmg3QiLBnDwb3IoeK13eXcYcvHVdP6";
//		String clientId = "mpmkdspv2";	

//		new wale jo 27 june se lagi hai
//		String scretKey = "NSvy7RuiqXgf3szo5CDdtmCzTXYKGGbh";
//		String clientId = "mpmkdsplt";
//		

//		String scretKey = "TnxSx3Gtz3YG4KGnvLchRfHHllJ2ZUEy";
//		String clientId = "mpmkdsplt2";

//		new key added 20-january-2025
		String scretKey = hashKey;
		String clientId = clientKey;

//		uat scretkey*************************************************************************//

//		String scretKey = "IZq29RHYY3J0mzd9vJVvF3WyokYzfbhD";
//		String clientId = "uatmpmknsc";
//		String scretKey = hashKey;
//		String clientId = clientKey;

		System.err.println(new Gson().toJson(str));
		String encryptAndSignJWSWithHMAC = encryptAndSignJWSWithHMAC(str, scretKey, clientId);

		System.out.println(
				encryptAndSignJWSWithHMAC + "---------------------------------------------------------------------->");
//	            
//			byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
//			System.out.println("encoded value is " + new String(bytesEncoded));
		payRequest.setTokenId(encryptAndSignJWSWithHMAC);

		return payRequest;
	}

	private static String encryptAndSignJWSWithHMAC(String reqStr, String secretKey, String clientid)
			throws JOSEException {

		JWSSigner signer = new MACSigner(secretKey);
		HashMap<String, Object> customParams = new HashMap<String, Object>();
		customParams.put("clientid", clientid);
		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256, null, null, null, null, null, null, null, null, null,
				null, customParams, null);
		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(reqStr)); // Apply the HMAC
		jwsObject.sign(signer);
		return jwsObject.serialize();
	}

}

//
