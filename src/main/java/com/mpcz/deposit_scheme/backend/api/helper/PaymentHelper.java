//package com.mpcz.deposit_scheme.backend.api.helper;
//
//import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
//import com.mpcz.deposit_scheme.backend.api.dto.PaymentProcessDTO;
//import com.mpcz.rooftop.domain.MasterVendor;
//import com.mpcz.rooftop.domain.SolarApplication;
//import com.mpcz.rooftop.dto.HexDTO;
//import com.mpcz.rooftop.enums.ApplicationStatusEnum;
//import com.mpcz.utilities.HexCodeGenerator;
//import com.mpmvvcl.util.BankUtil;
//
//public class PaymentHelper {
//
//	private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
//			.getLogger(PaymentHelper.class);
//
//	public static PaymentProcessDTO preparePaymentProcessDTO(SolarApplication solarApplication, Invoice invoice,
//			BankUtil bankUtil, String baseUrl) {
//
//		PaymentProcessDTO payDTO = new PaymentProcessDTO();
//		try {
//
//			if (ApplicationStatusEnum.INITIATED.getValue().toString().equals(solarApplication.getApplicationStatus())) {
//				payDTO.setSuccessUrl("initial_pay_success");
//			}
//			/*
//			 * if (ApplicationStatusEnum.SANCTION_PROCESS_SUBMITED.getValue().toString()
//			 * .equals(solarApplication.getApplicationStatus())) {
//			 * payDTO.setSuccessUrl("consumer_sanction_process_pay_success"); } if
//			 * (ApplicationStatusEnum.AUGMENTATION_SUBMITED.getValue().toString()
//			 * .equals(solarApplication.getApplicationStatus())) {
//			 * payDTO.setSuccessUrl("consumer_augmentation_process_pay_success"); }
//			 */
//
//			payDTO.setCancelUrl("cancel_payment");
//			payDTO.setFailureUrl("payment_failure");
//			payDTO.setBaseUrl(baseUrl);
//			System.out.println("BaseURL-->" + baseUrl);
//			HexDTO hexDTO = new HexDTO();
//			hexDTO.setTransactionId(invoice.getTranasactionNo());
//			hexDTO.setAmount(invoice.getAmount().toString());
//			hexDTO.setCustomerEmail(solarApplication.getCustomer().getEmail());
//			String firstnameSrr[] = solarApplication.getCustomer().getCustomerName().split(" ");
//			String firstname = firstnameSrr[0];
//			hexDTO.setFirstName(firstname);
//			hexDTO.setMobNo(solarApplication.getCustomer().getMobileNo().toString());
//			payDTO.setHashKey(HexCodeGenerator.getHexCode(hexDTO, bankUtil).toString());
//			payDTO.setHexDTO(hexDTO);
//		} catch (Exception ex) {
//			log.error("", ex.toString());
//			throw ex;
//		}
//		return payDTO;
//	}
//
//	public static PaymentProcessDTO preparePaymentProcessForMeterTest(MasterVendor masterVendor, Invoice invoice,
//			BankUtil bankUtil, String baseUrl) {
//
//		PaymentProcessDTO payDTO = new PaymentProcessDTO();
//		try {
//			System.err.println(masterVendor.toString());
//			payDTO.setSuccessUrl("meter_test_charge_pay_success");
//			payDTO.setCancelUrl("cancel_payment");
//			payDTO.setFailureUrl("payment_failure");
//			payDTO.setBaseUrl(baseUrl);
//			System.out.println("BaseURL-->" + baseUrl);
//			HexDTO hexDTO = new HexDTO();
//			hexDTO.setTransactionId(invoice.getTranasactionNo());
//			hexDTO.setAmount(invoice.getAmount().toString());
//			hexDTO.setCustomerEmail(masterVendor.getEmailId());
//			String firstnameSrr[] = masterVendor.getCompanyName().split(" ");
//			String firstname = firstnameSrr[0];
//			hexDTO.setFirstName(firstname);
//			hexDTO.setMobNo(masterVendor.getMobileNumber().toString());
//			payDTO.setHashKey(HexCodeGenerator.getHexCode(hexDTO, bankUtil).toString());
//			payDTO.setHexDTO(hexDTO);
//		} catch (Exception ex) {
//			log.error("", ex.toString());
//			throw ex;
//		}
//		return payDTO;
//	}
//}
