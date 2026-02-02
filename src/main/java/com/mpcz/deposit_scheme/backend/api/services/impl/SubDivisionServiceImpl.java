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
import com.mpcz.deposit_scheme.backend.api.domain.SubDivision;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.repository.SubDivisionRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.SubDivisionService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 *
 */
@Service
public class SubDivisionServiceImpl implements SubDivisionService {
	
	Logger logger = LoggerFactory.getLogger(SubDivisionServiceImpl.class);

	 

	@Autowired
	SubDivisionRepository repository;

	@Override
	public Response<SubDivision> save(SubDivision subDivision) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : save()";
		logger.info(method);

		final Response<SubDivision> response = new Response<>();

		if (Objects.isNull(subDivision)) {
			logger.error("SubDivision object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SubDivisionException(response);
		} else {

			SubDivision subDivisionResponse = repository.save(subDivision);

			if (Objects.isNull(subDivisionResponse)) {
				logger.error("repository.save(SubDivision1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new SubDivisionException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<SubDivision> list = new ArrayList<>();
				list.add(subDivisionResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<SubDivision> update(SubDivision subDivision) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : update()";
		logger.info(method);

		final Response<SubDivision> response = new Response<>();

		if (Objects.isNull(subDivision)) {
			logger.error("SubDivision object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SubDivisionException(response);
		} else {
			SubDivision subDivisionResponse = repository.save(subDivision);

			if (Objects.isNull(subDivisionResponse)) {
				logger.error("repository.save(SubDivision1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new SubDivisionException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<SubDivision> list = new ArrayList<>();

				list.add(subDivisionResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<SubDivision> delete(long subDivisionId) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : delete()";
		logger.info(method);
		final Response<SubDivision> response = new Response<>();
		SubDivision subDivision = this.findSubDivisionById(subDivisionId);
		if (Objects.isNull(subDivision)) {
			logger.error("SubDivision object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new SubDivisionException(response);
		} else {

			repository.save(subDivision);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;
		}

	}

	@Override
	public Response<SubDivision> findById(long subDivisionId) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : findById()";
		logger.info(method);
		final Response<SubDivision> response = new Response<>();
		Optional<SubDivision> optional = repository.findById(subDivisionId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_DIVISION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SubDivisionException(response);
		} else {
			SubDivision subDivision = optional.get();
			final List<SubDivision> list = new ArrayList<>();
			list.add(subDivision);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<SubDivision>> findAll() throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<SubDivision> subDivisions = repository.findAll();
		if (Objects.isNull(subDivisions) | subDivisions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_DIVISION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new SubDivisionException(response);
		} else {
			response.setList(subDivisions);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<SubDivision> findAllSubDivisions() throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : findAllSubDivisions()";
		logger.info(method);
		final Response<SubDivision> response = new Response<>();
		List<SubDivision> subDivisions = repository.findAll();
		if (Objects.isNull(subDivisions) || subDivisions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllSubDivisions call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_DIVISION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new SubDivisionException(response);
		} else {
			logger.info("Response is returning successfully");
			return subDivisions;
		}
	}

	@Override
	public SubDivision findSubDivisionById(long SubDivisionId) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : findSubDivisionById()";
		logger.info(method);
		final Response<SubDivision> response = new Response<>();
		Optional<SubDivision> optional = repository.findById(SubDivisionId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findSubDivisionById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_DIVISION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new SubDivisionException(response);
		} else {
			SubDivision subDivision = optional.get();
			logger.info("Response is returning successfully");
			return subDivision;
		}
	}

	@Override
	public List<SubDivision> findAllSubDivisionByDivision(Long divisionId) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : findAllSubDivisionByDivision()";
		logger.info(method);
		final Response<SubDivision> response = new Response<>();
		List<SubDivision> subDivisions = repository.findByDivision(divisionId);
		if (Objects.isNull(subDivisions) || subDivisions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllSubDivisionByDivision call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.SUB_DIVISION_RECORD_FETCH_ALL_BY_DIVISION_ID_FAILED_MESSAGE);
			throw new SubDivisionException(response);
		} else {
			logger.info("Response is returning successfully");
			return subDivisions;
		}
	}

	@Override
	public String isSubDivisionExist(DataStatusDTO dataStatusDTO) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : isSubDivisionExist()";
		logger.info(method);
		List<SubDivision> subDivisions = null;
		if (dataStatusDTO.getCrudType() == 1) {
			subDivisions = repository.checkSubDivision(dataStatusDTO.getDataName());
		} else if (dataStatusDTO.getCrudType() == 2) {
			subDivisions = repository.checkSubDivisionForUpdate(dataStatusDTO.getDataName(),
					dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(subDivisions) || subDivisions.isEmpty()) {
			logger.error("repository.checkSubDivision(subDivision) is returning Null when isSubDivisionExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

	@Override
	public String isSubDivisionCodeExist(DataStatusDTO dataStatusDTO) throws SubDivisionException {
		final String method = "SubDivisionServiceImpl : isSubDivisionCodeExist()";
		logger.info(method);
		List<SubDivision> subDivisions = null;
		if (dataStatusDTO.getCrudType() == 1) {
			subDivisions = repository.checkSubDivisionCode(dataStatusDTO.getDataCode());
		} else if (dataStatusDTO.getCrudType() == 2) {
			subDivisions = repository.checkSubDivisionCodeForUpdate(dataStatusDTO.getDataCode(),
					dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(subDivisions) || subDivisions.isEmpty()) {
			logger.error("repository.checkCircleCode(circleCode) is returning Null when isSubDivisionCodeExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;

		}
	}

}
