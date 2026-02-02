package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.IndividualOrGroup;
import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
import com.mpcz.deposit_scheme.backend.api.exception.IndividualOrGroupException;
import com.mpcz.deposit_scheme.backend.api.exception.LandAreaUnitException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.IndividualOrGroupRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.IndividualOrGroupService;

@Service
public class IndividualOrGroupServiceIMp implements IndividualOrGroupService {
	
	Logger logger = LoggerFactory.getLogger(IndividualOrGroupServiceIMp.class);
	
	@Autowired
	private IndividualOrGroupRepository individualOrGropuRepository;

	@Override
	public List<IndividualOrGroup> findAllIndividualOrGroup() throws IndividualOrGroupException {
		final String method = "IndividualOrGroupServiceIMp : findAllIndividualOrGroup()";
		logger.info(method);

		 List<IndividualOrGroup> findAlllandAreaUnit = individualOrGropuRepository.findAll();

		final Response<IndividualOrGroup> response = new Response<>();
		if (Objects.isNull(findAlllandAreaUnit) || findAlllandAreaUnit.isEmpty()) {
			logger.error("individualOrGropuRepository.findAll is returning Null when findAllIndividualOrGroup call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new LandAreaUnitException(response);
		} else {
			logger.info("Response is returning successfully");
			return findAlllandAreaUnit;
		}

	}
	
	
	@Override
	public IndividualOrGroup findById(Long indivisualId) throws IndividualOrGroupException {
		final String method = "IndividualOrGroupServiceIMp : findById(Long workTypeId)";

		logger.info(method);

		final Optional<IndividualOrGroup>  individualGroup= individualOrGropuRepository.findById(indivisualId);

		final Response<IndividualOrGroup> response = new Response<>();

		if (Objects.isNull(individualGroup) || !individualGroup.isPresent()) {
			logger.error("individualOrGropuRepository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new IndividualOrGroupException(response);
		} else {
			IndividualOrGroup individualGroupRes = individualGroup.get();		
			
			response.setList(Arrays.asList(individualGroupRes));
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return individualGroupRes;
		}
	}


}
