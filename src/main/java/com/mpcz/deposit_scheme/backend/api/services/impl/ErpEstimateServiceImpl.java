package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateDomain;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateException;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateService;

@Service
public class ErpEstimateServiceImpl implements ErpEstimateService {

	Logger logger = LoggerFactory.getLogger(WorkTypeServiceImpl.class);

	@Autowired
	private EstimateRepository estimateRepository;

	

	@Override
	public  List<ErpEstimateDomain> saveAllEstimate(List<ErpEstimateDomain> saveAllEstimateList)throws  ErpEstimateException {
		// TODO Auto-generated method stub
		
		final String method = "ErpEstimateServiceImpl : saveWorkType()";
		logger.info(method);

		Response<ErpEstimateDomain> response = new Response<>();

		WorkType workTypeResponse = null;

		if (saveAllEstimateList.isEmpty()) {
			logger.error("ErpEstimate object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ErpEstimateException(response);
		} else {

			
			 List<ErpEstimateDomain> saveAll = estimateRepository.saveAll(saveAllEstimateList);

			if (Objects.isNull(saveAll)) {
				logger.error("repository.save(ErpEstimate) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ErpEstimateException(response);
			} else {
				return saveAll;
			}

		}
	}
	
	@Override
	public ErpEstimateDomain findByEstimateNumber(String estimateNumber) throws ErpEstimateException {

		final String method = "ApplicationTypeServiceImpl : findByEstimateNumber(Long applicationTypeId)";

		logger.info(method);

		final Optional<ErpEstimateDomain> ErpEstimateDomainOptional = estimateRepository.findById(estimateNumber);

		final Response<ErpEstimateDomain> response = new Response<>();

		if (!ErpEstimateDomainOptional.isPresent()) {
//			logger.error("applicationTypeRepository.findById is returning Null when findByApplicationTypeId call");
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
//			throw new ErpEstimateException(response);
			return null;
		} 
			ErpEstimateDomain estimatekResponse = ErpEstimateDomainOptional.get();
			
			response.setList(Arrays.asList(estimatekResponse));
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return estimatekResponse;
		
	}
	
	
	
	Function<List<ErpEstimateDomain>, List<ErpEstimateDomain>> saveAllEstimateListFunction = saveAllEstimateList -> {
	    final String method = "ErpEstimateServiceImpl : saveWorkType()";
	    logger.info(method);

	    Response<ErpEstimateDomain> response = new Response<>();

	    if (saveAllEstimateList == null || saveAllEstimateList.isEmpty()) {
	        logger.error("ErpEstimate list is null or empty");
	        response.setCode(HttpCode.NULL_OBJECT);
	        response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
	      
	    }

	    List<ErpEstimateDomain> savedList = estimateRepository.saveAll(saveAllEstimateList);

	    if (savedList == null || savedList.isEmpty()) {
	        logger.error("estimateRepository.saveAll() returned null or empty");
	        response.setCode(HttpCode.NULL_OBJECT);
	        response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
	       
	    }

	    return savedList;
	    
	   
	};

}