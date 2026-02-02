package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorQc;
import com.mpcz.deposit_scheme.backend.api.exception.ContractorForQcException;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForQcRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ContractorForQcService;

public class ContractorForQcServiceImpl implements ContractorForQcService{
	
	Logger logger = LoggerFactory.getLogger(ContractorForQcServiceImpl.class);

	@Autowired
	private ContractorForQcRepository contractorForQcRepository;


	@Override
	public ContractorQc findByContractorId(Long contractorId) throws ContractorForQcException {
		// TODO Auto-generated method stub
		
		final String method = "WorkTypeServiceImpl : findByWorkTypeId(Long workTypeId)";

		logger.info(method);

		final Optional<ContractorQc> contractorForQcTypeOptional = contractorForQcRepository.findById(contractorId);

		final Response<ContractorQc> response = new Response<>();

		if (Objects.isNull(contractorForQcTypeOptional) || !contractorForQcTypeOptional.isPresent()) {
			logger.error("workTypeRepository.findById is returning Null when findByWorkTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CONTRACTOR_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ContractorForQcException(response);
		} else {
			ContractorQc contractorForQcType = contractorForQcTypeOptional.get();
			
			response.setList(Arrays.asList(contractorForQcType));
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return contractorForQcType;
		}
		
	}

}
