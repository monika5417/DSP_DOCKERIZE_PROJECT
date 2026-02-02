package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.District;
import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.repository.DistrictRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService {
	
	
	@Autowired
	DistrictRepository   districtRepository;

	Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);

	@Override
	public List<District> findAllDistrict() throws DistrictException {
		final String method = "DistrictServiceImpl : findAllDistrict()";
		logger.info(method);
		final Response<District> response = new Response<>();
		List<District> districtList = districtRepository.findAll();
		if (Objects.isNull(districtList) || districtList.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllDistrict call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DISTRICT_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DistrictException(response);
		} else {
			logger.info("Response is returning successfully");
			return districtList;
		}
	}

	
	@Override
	public District findDistrictById(long districtId) throws DistrictException {
		final String method = "DistrictServiceImpl : findDivisionById()";
		logger.info(method);
		final Response<District> response = new Response<>();
		Optional<District> optional = districtRepository.findById(districtId);
		if (!optional.isPresent()) {
			logger.error("repository.findById is returning Null when findDistrictById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DISTRICT_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DistrictException(response);
		} 
			District district = optional.get();
			logger.info("Response is returning successfully");
			return district;
		
	}
	
	
	
	
	
	
	
}