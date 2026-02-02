package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.dto.EstimateDependOnthisCheckBoxDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.impl.ConsumerApplicationDetailServiceImpl;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "EstimateDependOnthisCheckBox", description = "Rest api for ERP Estimate Details Depend on check box.")
@RestController
@RequestMapping("api/user/estimatedependOonthischeckBox")
public class EstimateDependOnthisCheckBoxController {

	@Autowired
	private ConsumerApplicationDetailService ConsumerApplicationDetailService;
	

	@PostMapping("/savedataerpEstimateTable")
	public Response<String> saveErp(@RequestBody EstimateDependOnthisCheckBoxDto estimateDependOnthisCheckBoxDto) {
		Response<String> response =new Response<>();
		try {	
			ConsumerApplicationDetail ConsumerApplicationDetailBy = ConsumerApplicationDetailService.findConsumerApplicationDetailByApplicationNo(estimateDependOnthisCheckBoxDto.getApplicatonNumber());
		
			if(estimateDependOnthisCheckBoxDto.getSelecDtrOrPtr()!=null) {
				ConsumerApplicationDetailBy.setDemandCalculateBycheckbox(estimateDependOnthisCheckBoxDto.getSelecDtrOrPtr());
			}
			ConsumerApplicationDetail saveConsumerApplication = ConsumerApplicationDetailService.saveConsumerApplication(ConsumerApplicationDetailBy);
	     if(saveConsumerApplication.getDemandCalculateBycheckbox() .equals("true")) {
	    	response.setCode("200")	;
	    	response.setList(Arrays.asList("dtr select kiya gya hai"));
	    	return response;
	    	
	     }else {
	    		response.setCode("200")	;
		    	response.setList(Arrays.asList("dtr select nai  kiya gya hai"));
		    	return response;
	     }
		} catch (ConsumerApplicationDetailException e) {
			e.printStackTrace();
		}
		response.setCode("200")	;
    	response.setList(Arrays.asList("data not save"));
    	return response;
		
	}

}
