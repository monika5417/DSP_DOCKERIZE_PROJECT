package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateCalculation;
import com.mpcz.deposit_scheme.backend.api.repository.ErpEstimateCalculationRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

@RestController
@RequestMapping("/ErpEstimateCalculationController")
public class ErpEstimateCalculationController {
	
	@Autowired
	private ErpEstimateCalculationRepository erpEstimateCalculationRepository;
	
	public Response<ErpEstimateCalculation> save(@RequestBody ErpEstimateCalculation erpEstimateCalculation) {
		
		Response<ErpEstimateCalculation> res = new Response();
				
		if(erpEstimateCalculation==null && erpEstimateCalculation.getConsumerApplicationNumber()==null) {
			res.setCode("400");
			res.setMessage("object is null");
			return res;
		}
		
		ErpEstimateCalculation save = erpEstimateCalculationRepository.save(erpEstimateCalculation);
		res.setCode("200");
		res.setMessage("object is null");
		res.setList(Arrays.asList(save));
		return res;
	}
}
