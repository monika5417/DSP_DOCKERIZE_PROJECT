package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateDomain;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateException;

public interface ErpEstimateService {

	 List<ErpEstimateDomain> saveAllEstimate(List<ErpEstimateDomain> saveAllEstimateList)throws ErpEstimateException;

	 public ErpEstimateDomain findByEstimateNumber(String erpNumber)throws ErpEstimateException;


	
}
