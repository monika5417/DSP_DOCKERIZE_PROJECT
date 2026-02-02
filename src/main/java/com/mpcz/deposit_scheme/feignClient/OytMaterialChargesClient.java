package com.mpcz.deposit_scheme.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mpcz.deposit_scheme.backend.api.dto.ApiResponseOYT;

@FeignClient(name = "oyt-api", url = "https://ssr.mpcz.in:8089")
public interface OytMaterialChargesClient {

	@RequestMapping(method = RequestMethod.GET, value = "/newerp/XXPA_SB_PROJECT_DETAILS_HT_V/{erpNo}")
	ApiResponseOYT getOytMaterialCharges(@PathVariable("erpNo") String erpNo);
	
	
	
}