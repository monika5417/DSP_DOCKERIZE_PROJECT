package com.mpcz.deposit_scheme.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mpcz.deposit_scheme.backend.api.dto.ApiResponseOYT;

@FeignClient(name="oyt-api-1", url = "https://dsp.mpcz.in:8888")
public interface OytMaterialChargesClient1 {
	
	@RequestMapping(method = RequestMethod.GET, value = "/newerp/XXPA_PROJECT_DETAILS_HT_V//{erpNo}")
	ApiResponseOYT oytMaterialCharges1(@PathVariable("erpNo") String erpNo);

}
