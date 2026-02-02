package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Role; 
import com.mpcz.deposit_scheme.backend.api.exception.RoleException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 * @since 2019-08-21
 *
 */

public interface RoleServices {

	/**
	 * public abstract method used to save role data
	 * 
	 * @param Role non-null and has visible content.
	 * @throws RoleException if role details has no visible content.
	 * @return Response<Role>
	 */
	public Response<Role> save(final Role role) throws RoleException;

	/**
	 * public abstract method used to update role data
	 * 
	 * @param Role non-null and has visible content.
	 * @throws RoleException if role details has no visible content.
	 * @return Response<Role>
	 */
	public Response<Role> update(final Role role) throws RoleException;

	/**
	 * public abstract method used to delete role data
	 * 
	 * @param Role id non-null and has visible content.
	 * @throws RoleException if role details has no visible content.
	 * @return Response<Role>
	 */
	public Response<Role> delete(final Long id) throws RoleException;

	/**
	 * public abstract method used to get role data by id
	 * 
	 * @param Role id non-null and has visible content.
	 * @throws RoleException if role details has no visible content.
	 * @return Response<Role>
	 */
	public Response<Role> findById(final Long id) throws RoleException;

	/**
	 * public abstract method used to get all role data
	 * 
	 * @throws RoleException if role details has no visible content.
	 * @return Response<List<Role>>
	 */
	public Response<List<Role>> findAll() throws RoleException;

	/**
	 * public abstract method used to get all role data
	 * 
	 * @throws RoleException if role details has no visible content.
	 * @return List<Role>
	 */
	public List<Role> findAllRoles() throws RoleException;

	/**
	 * public abstract method used to get role data by id
	 * 
	 * @param Role id non-null and has visible content.
	 * @throws RoleException if role details has no visible content.
	 * @return Role
	 */
	public Role findRoleById(Long id) throws RoleException;

}
