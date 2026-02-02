package com.mpcz.deposit_scheme.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mpcz.deposit_scheme.backend.api.dto.ApiResponseForZeroOrOnecaseDTO;

@FeignClient(name = "zero-one-case", url = "https://dsp.mpcz.in:8888")
public interface SaveDataZeroOneCaseClient {

	@RequestMapping(method = RequestMethod.GET, value = "/newerp/xxpa_mat_rec_qty_bal/{erpNo}")
	ApiResponseForZeroOrOnecaseDTO saveDataZeroOneCaseForDemandTime(@PathVariable("erpNo") String erpNo);
	
	
	
}

