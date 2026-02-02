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
import com.mpcz.deposit_scheme.backend.api.domain.Discom;
import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
import com.mpcz.deposit_scheme.backend.api.repository.DiscomRepository;
import com.mpcz.deposit_scheme.backend.api.request.DiscomForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DiscomService;

@Service
public class DiscomServiceImpl implements DiscomService {

	Logger logger = LoggerFactory.getLogger(SchemeTypeServiceImpl.class);

	@Autowired
	private DiscomRepository discomRepository;

	@Override
	public Discom saveDiscom(DiscomForm discomForm) throws DiscomException {

		final String method = "DiscomServiceImpl : saveDiscom()";

		logger.info(method);

		Response<Discom> response = new Response<>();

		Discom discomResponse = null;

		if (Objects.isNull(discomForm)) {
			logger.error("Discom object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DiscomException(response);
		} else {

			Discom discom = new Discom();

			discom.setDiscomName(discomForm.getDiscomName().trim());
			discom.setDiscomCode(discomForm.getDiscomCode().trim());

			discomResponse = discomRepository.save(discom);

			if (Objects.isNull(discomResponse)) {
				logger.error("discomRepository.save(discom) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new DiscomException(response);
			} else {
				return discomResponse;
			}

		}
	}

	@Override
	public Discom findByDiscomId(Long discomId) throws DiscomException {

		final String method = "DiscomServiceImpl : findByDiscomId(Long discomId)";

		logger.info(method);

		final Optional<Discom> discomOptional = discomRepository.findById(discomId);

		final Response<Discom> response = new Response<>();

		if (Objects.isNull(discomOptional) || !discomOptional.isPresent()) {
			logger.error("discomRepository.findById is returning Null when findByDiscomId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DISCOM_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DiscomException(response);
		} else {
			Discom discom = discomOptional.get();
			final List<Discom> list = new ArrayList<>();
			list.add(discom);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return discom;
		}
	}

	@Override
	public List<Discom> findAllDiscom() throws DiscomException {

		final String method = "DiscomServiceImpl : findAllDiscom()";
		logger.info(method);

		List<Discom> discoms = discomRepository.findAllActiveDiscom();

		final Response<Discom> response = new Response<>();
		if (Objects.isNull(discoms) || discoms.isEmpty()) {
			logger.error("discomRepository.findAllActiveDiscom is returning Null when findAllDiscom call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DiscomException(response);
		} else {
			logger.info("Response is returning successfully");
			return discoms;
		}
	}

	@Override
	public Discom findDiscomById(Long discomId) throws DiscomException {
		final String method = "DiscomServiceImpl : findDiscomById()";
		logger.info(method);
		final Response<Discom> response = new Response<>();
		Optional<Discom> optional = discomRepository.findById(discomId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findDiscomById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DISCOM_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DiscomException(response);
		} else {
			Discom discom = optional.get();
			logger.info("Response is returning successfully");
			return discom;
		}
	}

}
