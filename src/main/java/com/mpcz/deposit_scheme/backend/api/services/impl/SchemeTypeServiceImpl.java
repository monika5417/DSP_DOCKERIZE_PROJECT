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
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.SchemeTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.SchemeTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;

@Service
public class SchemeTypeServiceImpl implements SchemeTypeService {

	Logger logger = LoggerFactory.getLogger(SchemeTypeServiceImpl.class);

	@Autowired
	private SchemeTypeRepository schemeTypeRepository;

	@Override
	public SchemeType saveSchemeType(SchemeTypeForm schemeTypeForm) throws SchemeTypeException {

		final String method = "SchemeTypeServiceImpl : saveSchemeType()";

		logger.info(method);

		Response<SchemeType> response = new Response<>();

		SchemeType schemeTypeResponse = null;

		if (Objects.isNull(schemeTypeForm)) {
			logger.error("Scheme Type object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SchemeTypeException(response);
		} else {

			SchemeType schemeType = new SchemeType();

			schemeType.setSchemeTypeName(schemeTypeForm.getSchemeTypeName().trim());

			schemeTypeResponse = schemeTypeRepository.save(schemeType);

			if (Objects.isNull(schemeTypeResponse)) {
				logger.error("schemeTypeRepository.save(schemeType) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new SchemeTypeException(response);
			} else {
				return schemeTypeResponse;
			}

		}
	}

	@Override
	public SchemeType findBySchemeTypeId(Long schemeTypeId) throws SchemeTypeException {

		final String method = "SchemeTypeServiceImpl : findBySchemeTypeId(Long schemeTypeId)";

		logger.info(method);

		final Optional<SchemeType> schemeTypeOptional = schemeTypeRepository.findById(schemeTypeId);

		final Response<SchemeType> response = new Response<>();

		if (Objects.isNull(schemeTypeOptional) || !schemeTypeOptional.isPresent()) {
			logger.error("schemeTypeRepository.findById is returning Null when findBySchemeTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SchemeTypeException(response);
		} else {
			SchemeType schemeType = schemeTypeOptional.get();
			final List<SchemeType> list = new ArrayList<>();
			list.add(schemeType);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return schemeType;
		}
	}

	@Override
	public List<SchemeType> findAllSchemeType() throws SchemeTypeException {

		final String method = "SchemeTypeServiceImpl : findAllSchemeType()";
		logger.info(method);

		List<SchemeType> schemeTypes = schemeTypeRepository.findAllActiveSchemeType();

		final Response<SchemeType> response = new Response<>();
		if (Objects.isNull(schemeTypes) || schemeTypes.isEmpty()) {
			logger.error("schemeTypeRepository.findAllActiveSchemeType is returning Null when findAllSchemeType call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SchemeTypeException(response);
		} else {
			logger.info("Response is returning successfully");
			return schemeTypes;
		}

	}

}
