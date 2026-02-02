package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * This CircleService is used to Manage Circles
 * 
 * @author Aditya Vays
 * @version 1.0
 */


public interface CircleService {
	/**
	 * public abstract method used to save Circle data.
	 * @param Circle object non-null and has visible content.
	 * @throws CircleException if Circle object has no visible content.
	 * @return Response<Circle>
	 */
	public Response<Circle> save(final Circle circle) throws CircleException;

	/**
	 * public abstract method used to update Circle data.
	 * @param Circle object non-null and has visible content.
	 * @throws CircleException if Circle object has no visible content.
	 * @return Response<Circle>
	 */
	public Response<Circle> update(final Circle circle) throws CircleException;
	
	/**
	 * public abstract method used to delete circle data by using Circle id.
	 * @param circle id non-null and has visible content.
	 * @throws CircleException if Circle id has no visible content.
	 * @return Response<Circle>
	 */
	public Response<Circle> delete(final long circleId) throws CircleException;
	/**
	 * public abstract method used to find circle data by using Circle id.
	 * @param circle id non-null and has visible content.
	 * @throws CircleException if circle id has no visible content.
	 * @return Response<Circle>
	 */
	public Response<Circle> findById(final long circleId) throws CircleException;
	 
	/**
	 * public abstract method used to find all circle data.
	 * @throws CircleException if circle id has no visible content.
	 * @return Response<List<Circle>>
	 */
	public Response<List<Circle>> findAll() throws CircleException;
	/**
	 * public abstract method used to find all circle data.
	 * @throws CircleException if circle id has no visible content.
	 * @return List<Circle>
	 */
	public List<Circle> findAllCircles() throws CircleException;
	/**
	 * public abstract method used to find circle data by using Circle id.
	 * @param circle id non-null and has visible content.
	 * @throws CircleException if circle id has no visible content.
	 * @return Circle
	 */
	public Circle findCircleById(Long circleId) throws CircleException;
	/**
	 * public abstract method used to find all circle data by regionId.
	 * @throws CircleException if region id has no visible content.
	 * @return List<Circle>
	 */
	public List<Circle> findAllCirclesByRegion(final Long regionId) throws CircleException;
	/**
	 * public abstract method used to check weather circle name is already exits or not in database.
	 * @throws CircleException if circle has no visible content.
	 * @return String
	 */
	public String isCircleExist(DataStatusDTO dataStatusDTO) throws CircleException;
	
	/**
	 * public abstract method used to check weather circle code is already exits or not in database.
	 * @throws CircleException if circle has no visible content.
	 * @return String
	 */
	public String isCircleCodeExist(DataStatusDTO dataStatusDTO) throws CircleException;
	
	

}





