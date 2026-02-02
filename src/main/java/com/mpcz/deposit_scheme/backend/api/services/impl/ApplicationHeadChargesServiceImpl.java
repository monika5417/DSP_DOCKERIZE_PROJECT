package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.constant.HV1;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationHeadCharges;
import com.mpcz.deposit_scheme.backend.api.domain.ChargeRateMaster;
import com.mpcz.deposit_scheme.backend.api.domain.ChargesType;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentType;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentTypeCharges;
import com.mpcz.deposit_scheme.backend.api.enums.ChargesTypeEnum;
import com.mpcz.deposit_scheme.backend.api.enums.PaymentTypeEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationHeadChargesRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationHeadChargesService;
import com.mpcz.deposit_scheme.backend.api.services.ChargeRateMasterService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.PaymentTypeChargesService;
import com.mpcz.deposit_scheme.backend.api.services.PaymentTypeService;
import com.mpcz.deposit_scheme.backend.api.util.BigDecimalFormatUtil;

@Service
public class ApplicationHeadChargesServiceImpl implements ApplicationHeadChargesService {

	@Autowired
	PaymentTypeChargesService paymentTypeChargesService;

	@Autowired
	PaymentTypeService paymentTypeService;

	@Autowired
	ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	ChargeRateMasterService chargeRateMasterService;

	@Autowired
	ApplicationHeadChargesRepository applicationHeadChargesRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void prepareRegistrationFeeCharges(Long ConsumerApplicationId)
			throws PaymentTypeException, ConsumerApplicationDetailException, ApplicationHeadChargesException {

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplicationDetailService
				.findByConsumerApplicationId(ConsumerApplicationId);

		// fetch payment type
		PaymentType paymentType = paymentTypeService.findById(PaymentTypeEnum.REGISTRATION_FEES.getId());

		List<PaymentTypeCharges> paymentTypeChargesList = paymentTypeChargesService
				.findByPaymentTypeId(paymentType.getPaymentTypeId());

		BigDecimal totalAmount = new BigDecimal(0);
		List<ApplicationHeadCharges> registrationFeeHeadCharges = new ArrayList<>();

		BigDecimal registrationFeeAmount = new BigDecimal("1000");

		Optional<ChargesType> initialAmountChargeTypeOptional = paymentTypeChargesList.stream()
				.filter(pt -> pt.getChargesType().getChargeTypeId().compareTo(ChargesTypeEnum.AMOUNT.getId()) == 0)
				.map(pt -> pt.getChargesType()).findFirst();

		ChargesType initialAmountChargeType = null;
		if (initialAmountChargeTypeOptional.isPresent()) {
			initialAmountChargeType = initialAmountChargeTypeOptional.get();
		}

		ApplicationHeadCharges applicationHeadCharges = new ApplicationHeadCharges();
		applicationHeadCharges.setChargesType(initialAmountChargeType);
		applicationHeadCharges.setChargeAmount(registrationFeeAmount);
		applicationHeadCharges.setPaymentType(paymentType);
		applicationHeadCharges.setConsumerApplicationDetails(consumerApplicationDetail);
		registrationFeeHeadCharges.add(applicationHeadCharges);

		totalAmount = totalAmount.add(registrationFeeAmount);

		for (PaymentTypeCharges paymentTypeCharge : paymentTypeChargesList) {

			applicationHeadCharges = new ApplicationHeadCharges();

			if (paymentTypeCharge.getChargesType().getChargeTypeId().compareTo(ChargesTypeEnum.CGST.getId()) == 0
					|| paymentTypeCharge.getChargesType().getChargeTypeId()
							.compareTo(ChargesTypeEnum.SGST.getId()) == 0) {

				ChargeRateMaster chargeRateMaster = chargeRateMasterService
						.findChargeRateByType(paymentTypeCharge.getChargesType().getChargeTypeId());

				BigDecimal ChargeAmount = (registrationFeeAmount.multiply(chargeRateMaster.getChargePercentage())
						.divide(new BigDecimal(100)));

				ChargeAmount = BigDecimalFormatUtil.formatZeroZero(ChargeAmount);

				applicationHeadCharges.setChargesType(paymentTypeCharge.getChargesType());
				applicationHeadCharges.setChargeAmount(ChargeAmount);
				applicationHeadCharges
						.setChargeRate(BigDecimalFormatUtil.formatTwoTwo(chargeRateMaster.getChargePercentage()));
				applicationHeadCharges.setPaymentType(paymentType);
				applicationHeadCharges.setConsumerApplicationDetails(consumerApplicationDetail);
				registrationFeeHeadCharges.add(applicationHeadCharges);

				totalAmount = totalAmount.add(ChargeAmount);

			}

		}

		Optional<ChargesType> totalAmountChargeTypeOptional = paymentTypeChargesList.stream().filter(
				pt -> pt.getChargesType().getChargeTypeId().compareTo(ChargesTypeEnum.TOTAL_AMOUNT.getId()) == 0)
				.map(pt -> pt.getChargesType()).findFirst();

		ChargesType totalAmountChargeType = null;
		if (totalAmountChargeTypeOptional.isPresent()) {
			totalAmountChargeType = totalAmountChargeTypeOptional.get();
		}

		totalAmount = BigDecimalFormatUtil.formatZeroZero(totalAmount);
		applicationHeadCharges = new ApplicationHeadCharges();
		applicationHeadCharges.setChargesType(totalAmountChargeType);
		applicationHeadCharges.setChargeAmount(totalAmount);
		applicationHeadCharges.setPaymentType(paymentType);
		applicationHeadCharges.setConsumerApplicationDetails(consumerApplicationDetail);
		registrationFeeHeadCharges.add(applicationHeadCharges);

		registrationFeeHeadCharges.stream().forEach((rf) -> {
			System.out.println(rf);
		});

		saveAll(registrationFeeHeadCharges);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveAll(List<ApplicationHeadCharges> applicationHeadChargesList)
			throws ApplicationHeadChargesException {

		Response<ApplicationHeadCharges> response = new Response<>();

		for (ApplicationHeadCharges applicationHeadCharges : applicationHeadChargesList) {
			ApplicationHeadCharges applicationHeadChargesDb = null;
			try {
				applicationHeadChargesDb = applicationHeadChargesRepository.save(applicationHeadCharges);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (applicationHeadChargesDb == null) {
				response.setMessage("Error Saving Consumer Application !");
				response.setCode(ResponseCode.CONSUMER_APPLICATION_NOT_FOUND);
				throw new ApplicationHeadChargesException(response);
			}

		}

	}

	@Override
	public ApplicationHeadCharges findByConsumerApplicationIdChargesTypeIdPaymentTypeId(Long consumerApplicationId,
			Long chargesTypeId, Long paymentTypeId) throws ApplicationHeadChargesException {

		ApplicationHeadCharges applicationHeadCharges = null;
		Response<ApplicationHeadCharges> response = new Response<>();
		try {
			applicationHeadCharges = applicationHeadChargesRepository
					.findByConsumerApplicationDetailsConsumerApplicationIdAndChargesTypeChargeTypeIdAndPaymentTypePaymentTypeId(
							consumerApplicationId, chargesTypeId, paymentTypeId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (Objects.isNull(applicationHeadCharges)) {
			response.setMessage("Error Saving Consumer Application !");
			response.setCode(ResponseCode.CONSUMER_APPLICATION_NOT_FOUND);
			throw new ApplicationHeadChargesException(response);
		}
		return applicationHeadCharges;
	}

	@Override
	public List<ApplicationHeadCharges> findByApplicationIdPaymentTypeId(Long consumerApplicationId, Long paymentTypeId)
			throws ApplicationHeadChargesException {

		List<ApplicationHeadCharges> ApplicationHeadChargesList = null;
		Response<ApplicationHeadCharges> response = new Response<>();
		try {
			ApplicationHeadChargesList = applicationHeadChargesRepository
					.findByConsumerApplicationDetailsConsumerApplicationIdAndPaymentTypePaymentTypeId(
							consumerApplicationId, paymentTypeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Objects.isNull(ApplicationHeadChargesList)) {
			response.setMessage("Error fetching charges !");
			response.setCode(ResponseCode.CONSUMER_APPLICATION_NOT_FOUND);
			throw new ApplicationHeadChargesException(response);
		}
		return ApplicationHeadChargesList;

	}

}
