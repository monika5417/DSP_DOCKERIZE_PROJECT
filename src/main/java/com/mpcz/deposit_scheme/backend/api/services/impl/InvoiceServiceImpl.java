package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationHeadCharges;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentType;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.enums.ChargesTypeEnum;
import com.mpcz.deposit_scheme.backend.api.enums.PaymentTypeEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateException;
import com.mpcz.deposit_scheme.backend.api.exception.InvoiceException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.InvoiceRepository;
import com.mpcz.deposit_scheme.backend.api.repository.PaymentTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.InvoiceRequestForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationHeadChargesService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;
import com.mpcz.deposit_scheme.backend.api.services.DemandService;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;
import com.mpcz.deposit_scheme.backend.api.services.InvoiceService;
import com.mpcz.deposit_scheme.backend.api.services.PaymentHistoryService;
import com.mpcz.deposit_scheme.backend.api.utility.DateTimeUtility;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	private PaymentTypeRepository paymentTypeRepository;

	@Autowired
	private ConsumerApplicationDetailService applicationDetailService;

	@Autowired
	private PaymentHistoryService paymentHistoryService;

	@Autowired
	InvoiceService invoiceService;

	
	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private ConsumerApplictionDetailRepository consuemrApplicationDetailRepository;

	@Autowired
	private DemandService demandService;

	@Autowired
	private ApplicationHeadChargesService applicationHeadChargesService;
	
	@Autowired
	private ErpEstimateAmountService erpEstimateAmountService;

//	@Autowired 
//	private FeeChargesService feeChargesService;

//	@Value("${mpcz.account_no}")
//	private String accountNo;

//	@Value("${mpcz.ifcs}")
//	private String ifcsCode;

	@Override
	public Invoice save(Invoice invoice) {

		final String method = RestApiUrl.INVOICE_API + RestApiUrl.ADD_URL + " : save()";
		logger.info(method);
		return invoiceRepository.save(invoice);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Invoice saveInvoiceWithConsumerApplicationNmber(InvoiceRequestForm invoice) throws InvoiceException,
			PaymentHistoryException, ConsumerApplicationDetailException, ApplicationHeadChargesException {

		Response<Invoice> response2 = new Response<Invoice>();
		Invoice invoice2 = null;
		final String method = RestApiUrl.INVOICE_API + RestApiUrl.ADD_URL
				+ " : saveInvoiceWithConsumerApplicationNmber()";
		logger.info(method);

		String invoiceid = invoiceRepository.getInvoiceNo();
		ConsumerApplicationDetail consumerApplicationDetail = applicationDetailService
				.findConsumerApplicationDetailByApplicationNo(invoice.getConsumerApplicatinoNumber());

		ErpEstimateAmountData erpEstimateAmountData = null;
		if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId()
				.compareTo(ApplicationStatusEnum.ACCEPTANCE_OF_APPLICATION_AT_DC.getId()) == 0) {
			logger.error("invalid consumerApplication status");
			response2.setCode(HttpCode.NOT_ACCEPTABLE);
			response2.setMessage(ResponseMessage.PAYMENT_ALREADY_DONE);
			throw new InvoiceException(response2);
		}
		/*
		 * find application status
		 */

		if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId()
				.compareTo(ApplicationStatusEnum.PENDING_FOR_REGISTRATION_FEES.getId()) == 0) {
			// if application status == 5

			System.out.println(PaymentTypeEnum.REGISTRATION_FEES.getId());
			Optional<PaymentType> paymentType = paymentTypeRepository
					.findById(PaymentTypeEnum.REGISTRATION_FEES.getId());

			Response<Invoice> response1 = new Response<>();
//			ConsumerApplicationDetail DAD = applicationDetailService
//					.findConsumerApplicationDetailByApplicationNo(invoice.getConsumerApplicatinoNumber());

			ApplicationHeadCharges applicationHeadCharges = applicationHeadChargesService
					.findByConsumerApplicationIdChargesTypeIdPaymentTypeId(
							consumerApplicationDetail.getConsumerApplicationId(), ChargesTypeEnum.TOTAL_AMOUNT.getId(),
							PaymentTypeEnum.REGISTRATION_FEES.getId());

			if (Objects.isNull(consumerApplicationDetail)) {
				logger.error("consumerApplicationetails  object is null");
				response1.setCode(HttpCode.NULL_OBJECT);
				response1.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
				throw new InvoiceException(response1);
			}

//			List<FeeCharges> feecharges= feeChargesService.save(consumerApplicationDetail,paymentType);

			Invoice invoice1 = new Invoice();
			invoice1.setConsumerApplicatinoNumber(consumerApplicationDetail.getConsumerApplicationNo());
			invoice1.setConsumerName(consumerApplicationDetail.getConsumerName());
			invoice1.setDateOfPayment(DateTimeUtility.getCurrentTimeStamp());
			invoice1.setEmail(consumerApplicationDetail.getConsumers().getConsumerEmailId());
			invoice1.setInvoiceId(invoiceid);
			invoice1.setMobileNumber(consumerApplicationDetail.getConsumers().getConsumerMobileNo());
			invoice1.setPayBy("consumer");
			invoice1.setPayeeId(invoiceid);
			invoice1.setPaymentStatus(1);
			invoice1.setPaymentType(paymentType.get().getPaymentType());
//			invoice1.setTotalAmount(paymentType.get().getAmount().doubleValue());

			invoice1.setTotalAmount(applicationHeadCharges.getChargeAmount().doubleValue());

			double i = (Math.random());
			String s = String.valueOf(i);
			String s1 = s.substring(s.indexOf(".") + 1, 12);
			System.out.println(s1);
		
			if(consumerApplicationDetail.getSchemeType().getSchemeTypeId()==1) {
				String tranasactionNumer = invoiceid + "SV" + paymentType.get().getPaymentTypeId() + s1;
				invoice1.setTranasactionNo(tranasactionNumer);
			}
			if(consumerApplicationDetail.getSchemeType().getSchemeTypeId()==2) {
				String tranasactionNumer = invoiceid + "DE" + paymentType.get().getPaymentTypeId() + s1;
				invoice1.setTranasactionNo(tranasactionNumer);
			}
			if(consumerApplicationDetail.getSchemeType().getSchemeTypeId()==4) {
				String tranasactionNumer = invoiceid + "DEP" + paymentType.get().getPaymentTypeId() + s1;
				invoice1.setTranasactionNo(tranasactionNumer);
			}
//			String tranasactionNumer = invoiceid + "DE" + paymentType.get().getPaymentTypeId() + s1;
//			invoice1.setTranasactionNo(tranasactionNumer);
			invoice1.setTypeOfInvoice("unpaid");
			invoice1.setConsumerId((consumerApplicationDetail.getConsumers().getConsumerId()));
			invoice1.setTypeOfInvoice("unpaid");
			invoice2 = invoiceRepository.save(invoice1);
			if (Objects.isNull(invoice2)) {
				logger.error("consumerApplicationetails  object is null");
				response1.setCode(HttpCode.NULL_OBJECT);
				response1.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
				throw new InvoiceException(response1);
			}

		} else if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId()
				.compareTo(ApplicationStatusEnum.DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId()) == 0) {
			// if application status == 11

			System.out.println(PaymentTypeEnum.DEMAND_FEES.getId());
			Optional<PaymentType> paymentType = paymentTypeRepository.findById(PaymentTypeEnum.DEMAND_FEES.getId());

			Response<Invoice> response1 = new Response<>();

//			Demand demand = demandService
//					.findByConsumerApplicationId(consumerApplicationDetail.getConsumerApplicationId());
			
//			ErpworkflowNumber ke ander =ERP number aa raha hai
			try {
				erpEstimateAmountData =
						erpEstimateAmountService.findByEstimateNumber(consumerApplicationDetail.getErpWorkFlowNumber());

			}catch (Exception e) {
				
				
			}
		
			Invoice invoice1 = new Invoice();
			invoice1.setConsumerApplicatinoNumber(consumerApplicationDetail.getConsumerApplicationNo());
			invoice1.setConsumerName(consumerApplicationDetail.getConsumerName());
			invoice1.setDateOfPayment(DateTimeUtility.getCurrentTimeStamp());
			invoice1.setEmail(consumerApplicationDetail.getConsumers().getConsumerEmailId());
			invoice1.setInvoiceId(invoiceid);
			invoice1.setMobileNumber(consumerApplicationDetail.getConsumers().getConsumerMobileNo());
			invoice1.setPayBy("consumer");
			invoice1.setPayeeId(invoiceid);
			invoice1.setPaymentStatus(1);
			invoice1.setPaymentType(paymentType.get().getPaymentType());
//			invoice1.setTotalAmount(demand.getDemandRs().doubleValue());
			invoice1.setTotalAmount(erpEstimateAmountData.getEstimateAmount().doubleValue());

			double i = (Math.random());
			String s = String.valueOf(i);
			
			String s1 = s.substring(s.indexOf(".") + 1, 12);
			System.out.println(s1);
			if(consumerApplicationDetail.getSchemeType().getSchemeTypeId()==1) {
				String tranasactionNumer = invoiceid + "SV" + paymentType.get().getPaymentTypeId() + s1;
				invoice1.setTranasactionNo(tranasactionNumer);
			}
			if(consumerApplicationDetail.getSchemeType().getSchemeTypeId()==2) {
				String tranasactionNumer = invoiceid + "SV" + paymentType.get().getPaymentTypeId() + s1;
				invoice1.setTranasactionNo(tranasactionNumer);
			}
			if(consumerApplicationDetail.getSchemeType().getSchemeTypeId()==3) {
				String tranasactionNumer = invoiceid + "DEP" + paymentType.get().getPaymentTypeId() + s1;
				invoice1.setTranasactionNo(tranasactionNumer);
			}
//			invoice1.setTranasactionNo(tranasactionNumer);
			invoice1.setTypeOfInvoice("unpaid");
			invoice1.setConsumerId((consumerApplicationDetail.getConsumers().getConsumerId()));
			invoice1.setTypeOfInvoice("unpaid");
			invoice2 = invoiceRepository.save(invoice1);
			if (Objects.isNull(invoice2)) {
				logger.error("consumerApplicationetails  object is null");
				response1.setCode(HttpCode.NULL_OBJECT);
				response1.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
				throw new InvoiceException(response1);
			}

		}

//		PaymentHistory history = paymentHistoryService.findByApplicationNo(invoice.getConsumerApplicatinoNumber());
//		System.err.println(history);

//		if (Objects.isNull(history)) {
//			System.out.println(PaymentTypeEnum.REGISTRATION_FEES.getId());
//			Optional<PaymentType> paymentType = paymentTypeRepository
//					.findById(PaymentTypeEnum.REGISTRATION_FEES.getId());
//
//			Response<Invoice> response1 = new Response<>();
//			ConsumerApplicationDetail DAD = applicationDetailService
//					.findConsumerApplicationDetailByApplicationNo(invoice.getConsumerApplicatinoNumber());
//
//			if (Objects.isNull(DAD)) {
//				logger.error("consumerApplicationetails  object is null");
//				response1.setCode(HttpCode.NULL_OBJECT);
//				response1.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
//				throw new InvoiceException(response1);
//			}
//
//			Invoice invoice1 = new Invoice();
//
//			invoice1.setConsumerApplicatinoNumber(DAD.getConsumerApplicationNo());
//			invoice1.setConsumerName(DAD.getConsumerName());
//			invoice1.setDateOfPayment(DateTimeUtility.getCurrentTimeStamp());
//			invoice1.setEmail(DAD.getConsumers().getConsumerEmailId());
//			invoice1.setInvoiceId(invoiceid);
//			invoice1.setMobileNumber(DAD.getConsumers().getConsumerMobileNo());
//			invoice1.setPayBy("consumer");
// 
//			invoice1.setPayeeId(invoiceid);
//			invoice1.setPaymentStatus(1);
//			invoice1.setPaymentType(paymentType.get().getPaymentType());
// 
//			invoice1.setTotalAmount(paymentType.get().getAmount());
//			double i = (Math.random());
//			String s = String.valueOf(i);
//			String s1 = s.substring(s.indexOf(".") + 1, 12);
//			System.out.println(s1);
//			String tranasactionNumer = invoiceid + "DE" + paymentType.get().getId() + s1;
//			invoice1.setTranasactionNo(tranasactionNumer);
//			invoice1.setTypeOfInvoice("unpaid");
//			invoice1.setConsumerId((DAD.getConsumers().getConsumerId()));
//			invoice1.setTypeOfInvoice("unpaid");
//
//			invoice2 = invoiceRepository.save(invoice1);
//			if (Objects.isNull(invoice2)) {
//				logger.error("consumerApplicationetails  object is null");
//				response1.setCode(HttpCode.NULL_OBJECT);
//				response1.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
//				throw new InvoiceException(response1);
//			}
// 
//		}
		return invoice2;
	}

	@Override
	public Optional<List<Invoice>> findPaymentTransactionAlreadyInitiated(String consumerApplicationNumber,
			BigDecimal bigDecimal) {

		return invoiceRepository.findPaymentTransactionAlreadyInitiated(consumerApplicationNumber, bigDecimal);
	}

	@Override
	public Invoice findByConsumerApplicationNumberAndTranscationNo(String applicationNo, String transcationNo) {

		return invoiceRepository.findByConsumerApplicatioNoAndTranasactionNO(applicationNo, transcationNo);
	}

}
