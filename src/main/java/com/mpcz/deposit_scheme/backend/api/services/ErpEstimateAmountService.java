package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateDomain;
import com.mpcz.deposit_scheme.backend.api.dto.ErpEstimateCalculatedDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.response.EstimateAmountCalculatedDto;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface ErpEstimateAmountService {

	// List<ErpEstimateDomain> saveAllEstimate(List<ErpEstimateDomain> saveAllEstimateList)throws ErpEstimateException;

	List<ErpEstimateAmountData> saveAllEstimateAmount(List<ErpEstimateAmountData> saveAllEstimateAmountList)throws ErpEstimateAmountException;

	 public ErpEstimateAmountData findByEstimateNumber(String erpNumber)throws ErpEstimateAmountException;


	ErpEstimateCalculatedDto calculateEstimateAmount(Long consumerAppId) throws ConsumerApplicationDetailException, SchemeTypeException, Exception;

	ErpEstimateCalculatedDto calculateEstimateAmount1(Long consumerAppId) throws Exception;

	
	
}

