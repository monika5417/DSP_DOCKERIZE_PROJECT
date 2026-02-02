package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.LoadRequested;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.LoadRequestedException;
import com.mpcz.deposit_scheme.backend.api.repository.LoadRequestedRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.LoadRequestedService;

@Service
public class LoadRequestedServiceImpl implements LoadRequestedService{
	
	
	Logger logger = LoggerFactory.getLogger(LoadRequestedServiceImpl.class);

	@Autowired
	private LoadRequestedRepository loadRequestedRepository;
	
	@Override
	public List<LoadRequested> findAllLoadRequested() throws LoadRequestedException {
		final String method = "LoadRequestedServiceImpl : findAllLoadRequested()";
		logger.info(method);

		 List<LoadRequested> findAllloadRequested = loadRequestedRepository.findAll();

		final Response<LoadRequested> response = new Response<>();
		if (Objects.isNull(findAllloadRequested) || findAllloadRequested.isEmpty()) {
			logger.error("LoadRequestedRepository.findAll is returning Null when findAllLoadRequested call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new LoadRequestedException(response);
		} else {
			logger.info("Response is returning successfully");
			return findAllloadRequested;
		}

	}
	@Override
	public LoadRequested findById(long loadRequestedId) throws  LoadRequestedException{
		final String method = "DistributionCenterServiceImpl : findById()";
		logger.info(method);
		final Response<LoadRequested> response = new Response<>();
		Optional<LoadRequested> optional = loadRequestedRepository.findById(loadRequestedId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DC_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new LoadRequestedException(response);
		} else {
			LoadRequested loadRequested = optional.get();
			return loadRequested;
		}
}
}
