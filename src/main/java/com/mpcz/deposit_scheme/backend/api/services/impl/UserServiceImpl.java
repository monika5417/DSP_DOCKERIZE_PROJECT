package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.request.UserLoginForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.UserService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository repository;

	/**
	 * method: To save user record var: User : is used to store data
	 */
	@Override
	public Response<User> save(final User user) throws UserException {
		final String method = "UserServiceImpl : save()";
		logger.info(method);

		boolean status = true;

		final Response<User> response = new Response<>();
		User userResponse = null;
		if (Objects.isNull(user)) {
			logger.error("User object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new UserException(response);
		} else {
			if (!user.isActive()) {
				status = false;
			}
			user.setAccountNonExpired(false);
			user.setAccountNonLocked(false);
			user.setCredentialsNonExpired(false);
			user.setIsFirstLogin(true);
			user.setLastLoggedInDate(new Date());
			user.setLoginAttemp(null);
			user.setLoginStatus("NA");
			user.setActive(status);
			userResponse = repository.save(user);
		}

		if (Objects.isNull(userResponse)) {
			logger.error("repository.save(user1) is returning Null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CREATE_USER_RECORD_ERROR_MESSAGE);
			throw new UserException(response);
		} else {
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.CREATED);
			response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
			final List<User> list = new ArrayList<>();
			list.add(userResponse);
			response.setList(list);
			return response;
		}

	}

	@Override
	public Response<User> update(final User user) throws UserException {

		final String method = "UserServiceImpl : update()";
		logger.info(method);

		final Response<User> response = new Response<>();
		final User user1 = repository.save(user);
		if (Objects.isNull(user1)) {
			logger.error("repository.update(user1) is returning Null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
			throw new UserException(response);
		} else {
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
			final List<User> list = new ArrayList<>();
			list.add(user1);
			response.setList(list);
			return response;
		}
	}

	@Override
	public Response<User> delete(final User user) throws UserException {
		final String method = "UserServiceImpl : delete(final LoginForm form)";
		logger.info(method);
		final Response<User> response = new Response<>();
		if (Objects.isNull(user)) {
			logger.error("User object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new UserException(response);
		} else {
			repository.delete(user);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<User> findById(final Long id) throws UserException {
		final String method = "UserServiceImpl : findById(final Long id)";
		logger.info(method);
		final Optional<User> userOptional = repository.findById(id);
		final Response<User> response = new Response<>();
		if (Objects.isNull(userOptional) || userOptional.isPresent()) {
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			final List<User> list = new ArrayList<>();
			list.add(userOptional.get());
			response.setList(list);
			return response;

		} else {
			logger.error("repository.findById(final Long id) is returning Null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new UserException(response);
		}

	}

	@Override
	public Response<User> getUserAuthentication(final UserLoginForm form) throws InvalidAuthenticationException {
		final String method = "UserServiceImpl : getUserAuthentication(final LoginForm form)";
		logger.info(method);
		final Response<User> response = new Response<>();
		User userResponse = repository.findByuserIdAndUserCredentials(form.getUserLoginId(), form.getUserPwd());
		if (userResponse != null) {
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.LOGIN_SUCCESSFULLY);
			List<User> list = new ArrayList<>();
			list.add(userResponse);
			response.setList(list);
			return response;
		} else {
			response.setCode(ResponseCode.LOGIN_ATTEMPTS_FAIL);
			response.setMessage(ResponseMessage.AUTHENTICATION_FAIL);
			throw new InvalidAuthenticationException(response);
		}

	}

	@Override
	public Response<List<User>> findAll() throws UserException {
		final String method = "UserServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<User> users = repository.findAll();
		if (Objects.isNull(users)) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new UserException(response);
		} else {

			for (User user : users) {
				if ("1".equals(user.getAccessLevel())) {
					user.setAccessLevel("Discom Level");
				} else if ("2".equals(user.getAccessLevel())) {
					user.setAccessLevel("Region Level");
				} else if ("3".equals(user.getAccessLevel())) {
					user.setAccessLevel("Circle Level");
				}
			}

			response.setList(users);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public String isUserRecordExist(DataStatusDTO dataStatusDTO) throws UserException {
		final String method = "UserServiceImpl : isUserRecordExist()";
		logger.info(method);
		List<User> users = null;
		if (dataStatusDTO.getCrudType() == 1) {
			if (dataStatusDTO.getEmail() != null) {
				users = repository.checkEmail(dataStatusDTO.getEmail());
			} else if (dataStatusDTO.getMobile() != null) {
				users = repository.checkMobileNumber(dataStatusDTO.getMobile());
			} else if (dataStatusDTO.getAadharNumber() != null) {
				users = repository.checkAadharNumber(dataStatusDTO.getAadharNumber());
			} else if (dataStatusDTO.getUserId() != null) {
				users = repository.checkUserId(dataStatusDTO.getUserId());
			}
		} else if (dataStatusDTO.getCrudType() == 2) {
			if (dataStatusDTO.getEmail() != null) {
				users = repository.checkEmailForUpdate(dataStatusDTO.getEmail(), dataStatusDTO.getRecordId());
			} else if (dataStatusDTO.getMobile() != null) {
				users = repository.checkMobileNumberForUpdate(dataStatusDTO.getMobile(), dataStatusDTO.getRecordId());
			} else if (dataStatusDTO.getAadharNumber() != null) {
				users = repository.checkAadharNumberForUpdate(dataStatusDTO.getAadharNumber(),
						dataStatusDTO.getRecordId());
			} else if (dataStatusDTO.getUserId() != null) {
				users = repository.checkUserIdForUpdate(dataStatusDTO.getUserId(), dataStatusDTO.getRecordId());
			}
		}
		if (Objects.isNull(users) || users.isEmpty()) {
			logger.error(
					"repository.checkEmail(dataStatusDTO.getEmail()) or aadhar or mobile is returning Null when isUserRecordExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

	@Override
	public User findByUserIdAndAccountNonLocked(String userId, Boolean accountNonLocked)
			throws InvalidAuthenticationException {
		final String method = "UserServiceImpl : findByUserIdAndAccountNonLocked()";
		logger.info(method);
		return this.repository.findByUserIdAndAccountNonLocked(userId, accountNonLocked)
				.orElseThrow(() -> new InvalidAuthenticationException(
						new Response<String>(ResponseCode.ACCOUNT_LOCKED, ResponseMessage.ACCOUNT_LOCKED)));
	}

	@Override
	public User findByUserIdAndAccountNonExpired(String userId, Boolean accountNonExpired)
			throws InvalidAuthenticationException {
		// TODO Auto-generated method stub
		return this.repository.findByUserIdAndAccountNonExpired(userId, accountNonExpired)
				.orElseThrow(() -> new InvalidAuthenticationException(
						new Response<String>(ResponseCode.ACCOUNT_EXPIRED, ResponseMessage.ACCOUNT_EXPIRED)));
	}

	@Override
	public User findByMobileNo(String mobileNo) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return this.repository.findByMobileNo(mobileNo).orElseThrow(() -> new DataNotFoundException(
				new Response<String>(ResponseCode.DATA_NOT_FOUND, ResponseMessage.MOBILE_NO_NOT_FOUND)));
	}

	@Override
	public User findByUserId(String userId) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return this.repository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException(
				new Response<String>(ResponseCode.DATA_NOT_FOUND, ResponseMessage.USER_DETAILS_NOT_FOUND)));
	}

	@Override
	public User findByEmailId(String emailId) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return this.repository.findByUserEmailId(emailId).orElseThrow(() -> new DataNotFoundException(
				new Response<String>(ResponseCode.DATA_NOT_FOUND, ResponseMessage.MOBILE_NO_NOT_FOUND)));
	}

	@Override
	public List<User> findUsersByCircleId(Long circleId) throws UserException {

		final String method = "UserServiceImpl : findUserByCircleId(Long circleId)";

		logger.info(method);

		final Response<User> response = new Response<>();

		final List<User> users = repository.findUsersByCircleId(circleId);

		if (Objects.isNull(users)) {
			logger.error("repository.findById is returning Null when findBySchemeTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new UserException(response);
		} else {
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return users;
		}
	}

	
}
