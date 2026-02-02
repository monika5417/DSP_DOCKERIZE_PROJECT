package com.mpcz.deposit_scheme.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "send-data-meter-reader", url = "https://webapps.mpcz.in")
public interface sendDataMeterReaderVijlanceClient {

	@PostMapping("/vigilance/digi_webapis/insertObservedCases.php")
	public void sendDataMeterReaderVigilance(@RequestParam("ACCT_ID") String consumerApplicationNo,
			@RequestParam("BILL_MONTH") String billMonth, @RequestParam("DC_CODE") String ngbDcCode,
			@RequestParam("REASON") String reason, @RequestParam("CREATED_BY") String createdBy,
			@RequestParam("NAME") String consumerName, @RequestParam("ADDRESS") String address,
			@RequestParam("FEEDER_ID") String feederId, @RequestParam("DTR_ID") String dtrId,
			@RequestParam("GR") String gr, @RequestParam("RD") String rd,
			@RequestParam("BILLING_SYSTEM") String billingSystem, @RequestParam("TARIFF_CODE") String tariffCode,
			@RequestParam("MOBILE_NUMBER") String consumerMobileNo, @RequestParam("POLE_LATITUDE") String poleLatitude,
			@RequestParam("POLE_LONGITUDE") String poleLongitude,
			@RequestParam("REQUIRED_ACTION") String requiredAction, @RequestParam("ENTRY_TYPE") String entryType,
			@RequestParam("REASON_HINDI") String reasonHindi);

	
	
}
