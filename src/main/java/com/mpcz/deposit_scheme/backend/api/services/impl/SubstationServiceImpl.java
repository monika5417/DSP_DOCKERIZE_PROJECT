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
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.Substation;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.SubstationException;
import com.mpcz.deposit_scheme.backend.api.repository.SubstationRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.SubstationService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 *
 */
@Service
public class SubstationServiceImpl implements SubstationService {

	Logger logger = LoggerFactory.getLogger(SubstationServiceImpl.class);

	@Autowired
	SubstationRepository repository;

	@Override
	public Response<Substation> save(Substation substation) throws SubstationException {
		final String method = "SubstationServiceImpl : save()";
		logger.info(method);

		final Response<Substation> response = new Response<>();

		if (Objects.isNull(substation)) {
			logger.error("Substation object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SubstationException(response);
		} else {
			Substation substationResponse = null;

			substationResponse = repository.save(substation);

			if (Objects.isNull(substationResponse)) {
				logger.error("repository.save(substation1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new SubstationException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<Substation> list = new ArrayList<>();
				list.add(substationResponse);
				response.setList(list);
				return response;

			}
		}
	}

	@Override
	public Response<Substation> update(Substation substation) throws SubstationException {
		final String method = "SubstationServiceImpl : update()";
		logger.info(method);

		final Response<Substation> response = new Response<>();

		if (Objects.isNull(substation)) {
			logger.error("Substation object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SubstationException(response);
		} else {

			Substation substationResponse = null;

			// save sub-station info
			substationResponse = repository.save(substation);

			if (Objects.isNull(substationResponse)) {
				logger.error("repository.save(substation1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new SubstationException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<Substation> list = new ArrayList<>();

				list.add(substationResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Substation> delete(long substationId) throws SubstationException {
		final String method = "SubstationServiceImpl : delete()";
		logger.info(method);
		final Response<Substation> response = new Response<>();
		Substation substation = this.findSubstationById(substationId);
		if (Objects.isNull(substation)) {
			logger.error("Substation object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SubstationException(response);
		} else {

			repository.save(substation);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;

		}
	}

	@Override
	public Response<Substation> findById(long substationId) throws SubstationException {
		final String method = "SubstationServiceImpl : findById()";
		logger.info(method);
		final Response<Substation> response = new Response<>();
		Optional<Substation> optional = repository.findById(substationId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_STATION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SubstationException(response);
		} else {
			Substation substation = optional.get();
			final List<Substation> list = new ArrayList<>();
			list.add(substation);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<Substation>> findAll() throws SubstationException {
		final String method = "SubstationServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<Substation> substations = repository.findByIsActiveAndIsDeletedOrderBySubStationNameAsc(Boolean.TRUE,
				Boolean.FALSE);
		if (Objects.isNull(substations) || substations.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_STATION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new SubstationException(response);
		} else {
			response.setList(substations);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<Substation> findAllSubstations() throws SubstationException {
		final String method = "SubstationServiceImpl : findAllSubstations()";
		logger.info(method);
		final Response<Substation> response = new Response<>();
		List<Substation> substations = repository.findAll();
		if (Objects.isNull(substations) || substations.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllSubstations call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_STATION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new SubstationException(response);
		} else {
			logger.info("Response is returning successfully");
			return substations;
		}
	}

	@Override
	public Substation findSubstationById(Long substationId) throws SubstationException {
		final String method = "SubstationServiceImpl : findSubstationById()";
		logger.info(method);
		final Response<Substation> response = new Response<>();
		Optional<Substation> optional = repository.findById(substationId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findSubstationById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_STATION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SubstationException(response);
		} else {
			Substation substation = optional.get();
			logger.info("Response is returning successfully");
			return substation;
		}
	}

	@Override
	public List<Substation> findAllSubstationsByDC(Long dcId) throws SubstationException {
		final String method = "SubstationServiceImpl : findAllSubstationsByDC()";
		logger.info(method);
		final Response<Substation> response = new Response<>();
		List<Substation> substations = repository.findByDC(dcId);
		if (Objects.isNull(substations) || substations.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllSubstationsByDC call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_STATION_RECORD_FETCH_ALL_BY_DC_ID_FAILED_MESSAGE);
			throw new SubstationException(response);
		} else {
			logger.info("Response is returning successfully");
			return substations;
		}
	}

	@Override
	public String isSubstationExist(DataStatusDTO dataStatusDTO) throws SubstationException {
		final String method = "SubstationServiceImpl : isSubstationExist()";
		logger.info(method);
		List<Substation> substations = null;
		if (dataStatusDTO.getCrudType() == 1) {
			substations = repository.checkSubstation(dataStatusDTO.getDataName());
		} else if (dataStatusDTO.getCrudType() == 2) {
			substations = repository.checkSubstationForUpdate(dataStatusDTO.getDataName(), dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(substations) || substations.isEmpty()) {
			logger.error("repository.checkSubstation(subStation) is returning Null when isSubstationExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

	@Override
	public String isSubstationCodeExist(DataStatusDTO dataStatusDTO) throws SubstationException {
		final String method = "SubstationServiceImpl : isSubstationCodeExist()";
		logger.info(method);
		List<Substation> substations = null;
		if (dataStatusDTO.getCrudType() == 1) {
			substations = repository.checkSubstationCode(dataStatusDTO.getDataCode());
		} else if (dataStatusDTO.getCrudType() == 2) {
			substations = repository.checkSubstationCodeForUpdate(dataStatusDTO.getDataCode(),
					dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(substations) || substations.isEmpty()) {
			logger.error(
					"repository.checkSubstationCode(subStationCode) is returning Null when isSubstationCodeExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;

		}
	}

	@Override
	public List<Substation> findAllActiveSubstation() throws SubstationException {

		final String method = "SubstationServiceImpl : findAllActiveSubstation()";
		logger.info(method);

		List<Substation> substation = repository.findAllActiveSubstation();

		final Response<Substation> response = new Response<>();
		if (Objects.isNull(substation) || substation.isEmpty()) {
			logger.error(
					"repository.findAllActiveDistributionCenter is returning Null when findAllActiveSubstation call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SubstationException(response);
		} else {
			logger.info("Response is returning successfully");
			return substation;
		}
	}

}
