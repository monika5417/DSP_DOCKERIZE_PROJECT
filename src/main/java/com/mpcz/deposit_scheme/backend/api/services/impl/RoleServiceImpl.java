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
import com.mpcz.deposit_scheme.backend.api.domain.Role;
import com.mpcz.deposit_scheme.backend.api.exception.RoleException;
import com.mpcz.deposit_scheme.backend.api.repository.RoleRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.RoleServices;

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 * @since 2019-08-21
 *
 */
@Service
public class RoleServiceImpl implements RoleServices {

	Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository repository;

	/**
	 * method: To save role information var: Role : is used to store Role data
	 */
	@Override
	public Response<Role> save(Role role) throws RoleException {

		final String method = "RoleServiceImpl : save()";
		logger.info(method);

		final Response<Role> response = new Response<>();

		if (Objects.isNull(role)) {
			logger.error("Role object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new RoleException(response);
		} else {

			Role roleResponse = repository.save(role);

			if (Objects.isNull(roleResponse)) {
				logger.error("repository.save(role1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new RoleException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<Role> list = new ArrayList<>();
				list.add(roleResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Role> update(Role role) throws RoleException {
		final String method = "RoleServiceImpl : update()";
		logger.info(method);

		final Response<Role> response = new Response<>();

		if (Objects.isNull(role)) {
			logger.error("Role object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new RoleException(response);
		} else {

			Role roleResponse = repository.save(role);

			if (Objects.isNull(roleResponse)) {
				logger.error("repository.update(role1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new RoleException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<Role> list = new ArrayList<>();
				list.add(roleResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Role> delete(Long id) throws RoleException {
		final String method = "RoleServiceImpl : delete()";
		logger.info(method);

		final Response<Role> response = new Response<>();
		// get Role update or create info(TimeStamp)

		Role role = this.findRoleById(id);
		if (Objects.isNull(role)) {
			logger.error("Role object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new RoleException(response);
		} else {

			repository.save(role);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;

		}
	}

	@Override
	public Response<Role> findById(Long id) throws RoleException {
		final String method = "RoleServiceImpl : findById()";
		logger.info(method);
		final Response<Role> response = new Response<>();
		Optional<Role> optional = repository.findById(id);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new RoleException(response);
		} else {
			Role role = optional.get();
			final List<Role> list = new ArrayList<>();
			list.add(role);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;

		}

	}

	@Override
	public Response<List<Role>> findAll() throws RoleException {
		final String method = "RoleServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
//		sandeep, start
//		List<Role> roles = repository.findAll();
		List<Role> roles = repository.findAllOrderByASC();
//		sandeep, end

		if (Objects.isNull(roles) || roles.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new RoleException(response);
		} else {
			response.setList(roles);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}

	}

	@Override
	public List<Role> findAllRoles() throws RoleException {
		final String method = "RoleServiceImpl : findAllRoles()";
		logger.info(method);
		List<Role> roles = repository.findAll();
		final Response<Role> response = new Response<>();
		if (Objects.isNull(roles) || roles.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllRoles call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new RoleException(response);
		} else {
			logger.info("Response is returning successfully");
			return roles;
		}
	}

	@Override
	public Role findRoleById(Long id) throws RoleException {
		final String method = "RoleServiceImpl : findById()";
		logger.info(method);
		final Response<Role> response = new Response<>();
		Optional<Role> optional = repository.findById(id);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new RoleException(response);
		} else {
			Role role = optional.get();
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return role;

		}

	}

}
