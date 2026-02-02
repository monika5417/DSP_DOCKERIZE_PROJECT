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
import com.mpcz.deposit_scheme.backend.api.domain.SupplyVoltage;
import com.mpcz.deposit_scheme.backend.api.exception.SupplyVoltageException;
import com.mpcz.deposit_scheme.backend.api.repository.SupplyVoltageRepository;
import com.mpcz.deposit_scheme.backend.api.request.SupplyVoltageForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.SupplyVoltageService;

@Service
public class SupplyVoltageServicesImpl implements SupplyVoltageService {

	Logger logger = LoggerFactory.getLogger(SupplyVoltageServicesImpl.class);

	@Autowired
	private SupplyVoltageRepository supplyVoltageRepository;

	@Override
	public SupplyVoltage saveSupplyVoltage(SupplyVoltageForm supplyVoltageForm) throws SupplyVoltageException {

		final String method = "SupplyVoltageServicesImpl : saveSupplyVoltage()";

		logger.info(method);

		Response<SupplyVoltage> response = new Response<>();

		SupplyVoltage supplyVoltageResponse = null;

		if (Objects.isNull(supplyVoltageForm)) {
			logger.error("supply Voltage object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SupplyVoltageException(response);
		} else {

			SupplyVoltage supplyVoltage = new SupplyVoltage();

			supplyVoltage.setSupplyVoltageName(supplyVoltageForm.getSupplyVoltageName().trim());
			supplyVoltage.setSupplyVoltageCode(supplyVoltageForm.getSupplyVoltageCode().trim());

			supplyVoltageResponse = supplyVoltageRepository.save(supplyVoltage);

			if (Objects.isNull(supplyVoltageResponse)) {
				logger.error("supplyVoltageRepository.save(supplyVoltage) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new SupplyVoltageException(response);
			} else {
				return supplyVoltageResponse;
			}
		}
	}

	@Override
	public SupplyVoltage findBySupplyVoltageId(Long supplyVoltageId) throws SupplyVoltageException {

		final String method = "SupplyVoltageServicesImpl : SupplyVoltageServicesImpl(Long supplyVoltageId)";

		logger.info(method);

		final Optional<SupplyVoltage> supplyVoltageOptional = supplyVoltageRepository.findById(supplyVoltageId);

		final Response<SupplyVoltage> response = new Response<>();

		if (Objects.isNull(supplyVoltageOptional) || !supplyVoltageOptional.isPresent()) {
			logger.error("supplyVoltageRepository.findById is returning Null when findBySupplyVoltageId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SupplyVoltageException(response);
		} else {
			SupplyVoltage supplyVoltage = supplyVoltageOptional.get();
			final List<SupplyVoltage> list = new ArrayList<>();
			list.add(supplyVoltage);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return supplyVoltage;
		}
		
	}

	@Override
	public List<SupplyVoltage> findAllSupplyVoltage() throws SupplyVoltageException {

		final String method = "SupplyVoltageServicesImpl : findAllSupplyVoltage()";
		logger.info(method);

		List<SupplyVoltage> supplyVoltage = supplyVoltageRepository.findAllActiveSupplyVoltage();

		final Response<SupplyVoltage> response = new Response<>();
		if (Objects.isNull(supplyVoltage) || supplyVoltage.isEmpty()) {
			logger.error("supplyVoltageRepository.findAllSupplyVoltage is returning Null when findAllSupplyVoltage call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SupplyVoltageException(response);
		} else {
			logger.info("Response is returning successfully");
			return supplyVoltage;
		}

	}
}
