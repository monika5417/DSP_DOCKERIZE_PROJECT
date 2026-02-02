package com.mpcz.deposit_scheme.backend.api.services.impl;

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
import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.exception.LandAreaUnitException;
import com.mpcz.deposit_scheme.backend.api.repository.LandAreaUnitRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.LandAreaUnitService;

@Service
public class LandAreaUnitServiceImpl implements LandAreaUnitService{
	
	
	Logger logger = LoggerFactory.getLogger(LandAreaUnitServiceImpl.class);

	@Autowired
	private LandAreaUnitRepository landAreaUnitRepository;
	
	@Override
	public List<LandAreaUnit> findAllLandAreaUnit() throws LandAreaUnitException {
		final String method = "LandAreaUnitServiceImpl : findAllLandAreaUnit()";
		logger.info(method);

		 List<LandAreaUnit> findAlllandAreaUnit = landAreaUnitRepository.findAll();

		final Response<LandAreaUnit> response = new Response<>();
		if (Objects.isNull(findAlllandAreaUnit) || findAlllandAreaUnit.isEmpty()) {
			logger.error("LoadRequestedRepository.findAll is returning Null when findAllLandAreaUnit call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new LandAreaUnitException(response);
		} else {
			logger.info("Response is returning successfully");
			return findAlllandAreaUnit;
		}

	}
	
	
	@Override
	public LandAreaUnit findById(long landAreaUnitId) throws LandAreaUnitException {
		final String method = "LandAreaUnitServiceImpl : findById()";
		logger.info(method);
		final Response<LandAreaUnit> response = new Response<>();
		Optional<LandAreaUnit> optional = landAreaUnitRepository.findById(landAreaUnitId);
		if (Objects.isNull(optional)) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new LandAreaUnitException(response);
		} else {
			LandAreaUnit landAreaUnit = optional.get();
			
			
			response.setList(Arrays.asList(landAreaUnit));
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return landAreaUnit;
		}
	}

}