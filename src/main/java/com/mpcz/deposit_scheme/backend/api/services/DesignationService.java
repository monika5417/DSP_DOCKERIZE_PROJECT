package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Designation;
import com.mpcz.deposit_scheme.backend.api.exception.DesignationException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/**
 * This DesignationService is used to Manage Designations.
 * 
 * @author Aditya
 * @version 1.0.0
 * @since 1.0.0
 *
 */

public interface DesignationService {

	/**
	 * public abstract method used to save Designation data.
	 * 
	 * @param Designation object non-null and has visible content.
	 * @throws DesignationException if Designation object has no visible content.
	 * @return Response<Designation>
	 */
	public Response<Designation> save(final Designation designation) throws DesignationException;

	/**
	 * public abstract method used to update Designation data.
	 * 
	 * @param Designation object non-null and has visible content.
	 * @throws DesignationException if Designation object has no visible content.
	 * @return Response<Designation>
	 */
	public Response<Designation> update(final Designation designation) throws DesignationException;

	/**
	 * public abstract method used to delete Designation data.
	 * 
	 * @param Designation id non-null and has visible content.
	 * @throws DesignationException if Designation id has no visible content.
	 * @return Response<Designation>
	 */
	public Response<Designation> delete(final Long id) throws DesignationException;

	/**
	 * public abstract method used to find Designation data by id.
	 * 
	 * @param Designation id non-null and has visible content.
	 * @throws DesignationException if Designation id has no visible content.
	 * @return Response<Designation>
	 */
	public Response<Designation> findById(final Long id) throws DesignationException;

	/**
	 * public abstract method used to find all Designation data.
	 * 
	 * @throws DesignationException if Designation id has no visible content.
	 * @return Response<List<Designation>>
	 */
	public Response<List<Designation>> findAll() throws DesignationException;

	/**
	 * public abstract method used to find all Designation data.
	 * 
	 * @throws DesignationException if Designation id has no visible content.
	 * @return List<Designation>
	 */
	public List<Designation> findAllDesignations() throws DesignationException;

	/**
	 * public abstract method used to find Designation data by id.
	 * 
	 * @param Designation id non-null and has visible content.
	 * @throws DesignationException if Designation id has no visible content.
	 * @return Designation
	 */
	public Designation findDesignationById(final Long id) throws DesignationException;

	/**
	 * public abstract method used to check weather Designation name is already exits or not in database.
	 * @throws DesignationException if Designation has no visible content.
	 * @return String
	 */
	public String isDesignationExist(DataStatusDTO dataStatusDTO) throws DesignationException;

}













