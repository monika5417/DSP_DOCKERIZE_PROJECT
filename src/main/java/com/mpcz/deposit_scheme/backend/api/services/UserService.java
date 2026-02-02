package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.request.UserLoginForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface UserService {

	/**
	 * public abstract method used to save user data
	 * 
	 * @param User non-null and has visible content.
	 * @throws UserException if user details has no visible content.
	 * @return Response<User>
	 */
	public Response<User> save(final User user) throws UserException;

	/**
	 * public abstract method used to update user data
	 * 
	 * @param User non-null and has visible content.
	 * @throws UserException if user details has no visible content.
	 * @return Response<User>
	 */
	public Response<User> update(final User user) throws UserException;

	/**
	 * public abstract method used to delete user data
	 * 
	 * @param User id non-null and has visible content.
	 * @throws UserException if User details has no visible content.
	 * @return Response<User>
	 */
	public Response<User> delete(final User user) throws UserException;

	/**
	 * public abstract method used to get user data by id
	 * 
	 * @param User id non-null and has visible content.
	 * @throws UserException if role details has no visible content.
	 * @return Response<User>
	 */
	public Response<User> findById(final Long id) throws UserException;

	/**
	 * public abstract method used to get all user data
	 * 
	 * @throws UserException if role details has no visible content.
	 * @return Response<List<User>>
	 */
	public Response<List<User>> findAll() throws UserException;

	/**
	 * public abstract method used to get user data by login id and password used to
	 * perform login.
	 * 
	 * @param LoginForm non-null and has visible content.
	 * @throws UserException if role details has no visible content.
	 * @return Response<User>
	 */
	public Response<User> getUserAuthentication(final UserLoginForm form) throws InvalidAuthenticationException;

	/**
	 * public abstract method used to check user data is exist or not.
	 * 
	 * @param DataStatusDTO non-null and has visible content.
	 * @throws UserException if role details has no visible content.
	 * @return String
	 */
	public String isUserRecordExist(DataStatusDTO dataStatusDTO) throws UserException;

	public User findByUserIdAndAccountNonLocked(final String userId, final Boolean accountNonLocked)
			throws InvalidAuthenticationException;

	public User findByUserIdAndAccountNonExpired(final String userId, final Boolean accountNonExpired)
			throws InvalidAuthenticationException;

	/**
	 * public abstract method used to get user data by mobile number
	 * 
	 * @param mobile number non-null and has visible content.
	 * @throws DataNotFoundException if role details has no visible content.
	 * @return User
	 */
	public User findByMobileNo(final String mobileNo) throws DataNotFoundException;

	/**
	 * public abstract method used to get user data by email id
	 * 
	 * @param email id non-null and has visible content.
	 * @throws DataNotFoundException if role details has no visible content.
	 * @return User
	 */
	public User findByEmailId(final String emailId) throws DataNotFoundException;

	/**
	 * public abstract method used to get user data by user id
	 * 
	 * @param user id non-null and has visible content.
	 * @throws DataNotFoundException if role details has no visible content.
	 * @return User
	 */
	public User findByUserId(final String userId) throws DataNotFoundException;

	public List<User> findUsersByCircleId(final Long circleId) throws UserException;
}
