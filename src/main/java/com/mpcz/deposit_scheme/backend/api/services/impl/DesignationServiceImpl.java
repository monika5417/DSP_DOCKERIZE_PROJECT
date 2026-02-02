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
import com.mpcz.deposit_scheme.backend.api.domain.Designation;
import com.mpcz.deposit_scheme.backend.api.exception.DesignationException;
import com.mpcz.deposit_scheme.backend.api.repository.DesignationRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DesignationService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

@Service
public class DesignationServiceImpl implements DesignationService {
	Logger logger = LoggerFactory.getLogger(DesignationServiceImpl.class);

	@Autowired
	private DesignationRepository repository;

	/**
	 * method: To save designation : Designation : is used to store designation
	 * Information
	 */

	@Override
	public Response<Designation> save(Designation designation) throws DesignationException {
		final String method = "DesignationServiceImpl : save()";
		logger.info(method);

		final Response<Designation> response = new Response<>();

		if (Objects.isNull(designation)) {
			logger.error("Designation object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DesignationException(response);
		} else {

			Designation designationResponse = repository.save(designation);

			if (Objects.isNull(designationResponse)) {
				logger.error("repository.save(designation1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new DesignationException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<Designation> list = new ArrayList<>();
				list.add(designationResponse);
				response.setList(list);
				return response;
			}
		}

	}

	@Override
	public Response<Designation> update(Designation designation) throws DesignationException {
		final String method = "DesignationServiceImpl : update()";
		logger.info(method);

		final Response<Designation> response = new Response<>();

		if (Objects.isNull(designation)) {
			logger.error("Designation object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DesignationException(response);
		} else {

			Designation designationResponse = repository.save(designation);

			if (Objects.isNull(designationResponse)) {
				logger.error("repository.update(role1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new DesignationException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<Designation> list = new ArrayList<>();
				list.add(designationResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Designation> delete(Long id) throws DesignationException {
		final String method = "DesignationServiceImpl : delete()";
		logger.info(method);

		final Response<Designation> response = new Response<>();

		Designation designation = this.findDesignationById(id);

		if (Objects.isNull(designation)) {
			logger.error("Designation object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DesignationException(response);
		} else {

			repository.save(designation);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;

		}
	}

	@Override
	public Response<Designation> findById(Long id) throws DesignationException {
		final String method = "DesignationServiceImpl : findById()";
		logger.info(method);
		final Response<Designation> response = new Response<>();
		Optional<Designation> optional = repository.findById(id);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DesignationException(response);
		} else {
			Designation designation = optional.get();
			final List<Designation> list = new ArrayList<>();
			list.add(designation);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<Designation>> findAll() throws DesignationException {
		final String method = "DesignationServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<Designation> designations = repository.findAll();
		if (Objects.isNull(designations) || designations.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DesignationException(response);
		} else {
			response.setList(designations);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<Designation> findAllDesignations() throws DesignationException {
		final String method = "DesignationServiceImpl : findAllDesignations()";
		logger.info(method);
		final Response<Designation> response = new Response<>();
		List<Designation> designations = repository.findAll();
		if (Objects.isNull(designations) || designations.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DesignationException(response);
		} else {
			logger.info("Response is returning successfully");
			return designations;
		}
	}

	@Override
	public Designation findDesignationById(Long id) throws DesignationException {
		final String method = "DesignationServiceImpl : findDesignationById()";
		logger.info(method);
		final Response<Designation> response = new Response<>();
		Optional<Designation> optional = repository.findById(id);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findByRoleId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DesignationException(response);
		} else {
			Designation designation = optional.get();

			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return designation;
		}
	}

	@Override
	public String isDesignationExist(DataStatusDTO dataStatusDTO) throws DesignationException {
		final String method = "DesignationServiceImpl : isDesignationExist()";
		logger.info(method);
		List<Designation> designations = null;
		if (dataStatusDTO.getCrudType() == 1) {
			designations = repository.checkDesignation(dataStatusDTO.getDataName());
		} else if (dataStatusDTO.getCrudType() == 2) {
			designations = repository.checkDesignationForUpdate(dataStatusDTO.getDataName(),
					dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(designations) || designations.isEmpty()) {
			logger.error("repository.checkDesignation(designation) is returning Null when findAll call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

}
