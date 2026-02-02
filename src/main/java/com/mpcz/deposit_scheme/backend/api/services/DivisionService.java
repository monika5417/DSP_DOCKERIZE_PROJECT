package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
//import com.mpcz.deposit_scheme.backend.api.exception.GroupNotFoundException;
//import com.mpcz.deposit_scheme.backend.api.response.GroupNumberDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * This DivisionService is used to Manage divisions
 * 
 * @author Aditya Vays
 * @version 1.0
 */


public interface DivisionService {
	public Response<Division> save(final Division division) throws DivisionException;

	public Response<Division> update(final Division division) throws DivisionException;
	
	public Response<Division> delete(final long divisionId) throws DivisionException;

	public Response<Division> findById(final long divisionId) throws DivisionException;
	
	public Response<List<Division>> findAll() throws DivisionException;

	public List<Division> findAllDivisions() throws DivisionException;
	
	public List<Division> findAllDivisionsByCircle(final Long circleId) throws DivisionException;
	
	public Division findDivisionById(final long divisionId) throws DivisionException;
	
//	public List<GroupNumberDTO> findAllGroupNumbersByDivisionId(final long divisionId) throws DivisionException, GroupNotFoundException;
	
	public String isDivisionExist(DataStatusDTO dataStatusDTO) throws DivisionException;
	
	public String isDivisionCodeExist(DataStatusDTO dataStatusDTO) throws DivisionException;
}
