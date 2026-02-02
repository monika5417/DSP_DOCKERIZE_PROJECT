//package com.mpcz.deposit_scheme.backend.api.services.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.domain.ChargesType;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
//import com.mpcz.deposit_scheme.backend.api.domain.ApplicationHeadCharges;
//import com.mpcz.deposit_scheme.backend.api.domain.Feeder;
//import com.mpcz.deposit_scheme.backend.api.domain.PaymentType;
//import com.mpcz.deposit_scheme.backend.api.exception.FeederException;
//import com.mpcz.deposit_scheme.backend.api.repository.ApplicationHeadChargesRepository;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.ChargesTypeService;
//import com.mpcz.deposit_scheme.backend.api.services.FeeChargesService;
//
//
//@Service
//public class FeeChargesServiceImp implements FeeChargesService {
//	
//	@Autowired
//	private ChargesTypeService chargesTypeService;
//	
//	
//	@Autowired
//	private ApplicationHeadChargesRepository chargesRepository;
//	
//	Logger logger =LoggerFactory.getLogger(FeeChargesServiceImp.class);
//
//	
//
//	@Override
//	public List<ApplicationHeadCharges> save(ConsumerApplicationDetail consumerApplicationDetail, Optional<PaymentType> paymentType) {
//		final String method = "FeeChargesServiceImp : save()";
//		logger.info(method);
//
//		ArrayList<ApplicationHeadCharges> arrayList =new ArrayList<ApplicationHeadCharges>();
//		
//		ApplicationHeadCharges feeCharges1 = new ApplicationHeadCharges();
//		feeCharges1.setConsumerApplicationDetails(consumerApplicationDetail);	
//		ChargesType chargesType1= chargesTypeService.findById(1l);	
//		feeCharges1.setChargesType(chargesType1);
//		feeCharges1.setChargeAmount(paymentType.get().getAmount());
//		feeCharges1.setPaymentType(paymentType.get());
//		arrayList.add(feeCharges1);
//		
//		
//		ApplicationHeadCharges feeCharges2 = new ApplicationHeadCharges();
//		feeCharges2.setConsumerApplicationDetails(consumerApplicationDetail);	
//		ChargesType chargesType2= chargesTypeService.findById(2l);	
//		feeCharges2.setChargesType(chargesType2);
//		feeCharges2.setChargeRate(9l);
//		feeCharges2.setChargeAmount(((paymentType.get().getAmount()*9)/100));
//		feeCharges2.setPaymentType(paymentType.get());
//		arrayList.add(feeCharges2);
//		
//		
//		ApplicationHeadCharges feeCharges3 = new ApplicationHeadCharges();
//		feeCharges3.setConsumerApplicationDetails(consumerApplicationDetail);	
//		ChargesType chargesType3= chargesTypeService.findById(3l);	
//		feeCharges3.setChargesType(chargesType3);
//		feeCharges3.setChargeRate(9l);
//		feeCharges3.setChargeAmount(((paymentType.get().getAmount()*9)/100));
//		feeCharges3.setPaymentType(paymentType.get());
//		arrayList.add(feeCharges3);
//		
//		ApplicationHeadCharges feeCharges4 = new ApplicationHeadCharges();
//		feeCharges4.setConsumerApplicationDetails(consumerApplicationDetail);	
//		ChargesType chargesType4= chargesTypeService.findById(4l);	
//		feeCharges4.setChargesType(chargesType3);
//		feeCharges4.setChargeAmount((paymentType.get().getAmount())+((((paymentType.get().getAmount()*9)/100)+(paymentType.get().getAmount()*9)/100)));
//		feeCharges4.setPaymentType(paymentType.get());
//		arrayList.add(feeCharges4);
//		
//		return chargesRepository.saveAll(arrayList) ;
//	}
//
//}
