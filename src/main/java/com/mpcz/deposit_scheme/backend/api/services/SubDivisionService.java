package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.SubDivision;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * This SubDivisionService is used to SubDivisions
 * 
 * @author Aditya Vyas
 * @version 1.0
 */


public interface SubDivisionService {
	public Response<SubDivision> save(final SubDivision subDivision) throws SubDivisionException;

	public Response<SubDivision> update(final SubDivision subDivision) throws SubDivisionException;
	
	public Response<SubDivision> delete(final long subDivisionId) throws SubDivisionException;

	public Response<SubDivision> findById(final long subDivisionId) throws SubDivisionException;
	
	public Response<List<SubDivision>> findAll() throws SubDivisionException;
	
	public SubDivision findSubDivisionById(final long subDivisionId) throws SubDivisionException;

	public List<SubDivision> findAllSubDivisions() throws SubDivisionException;

	public List<SubDivision> findAllSubDivisionByDivision(final Long divisionId) throws SubDivisionException;
	
	public String isSubDivisionExist(DataStatusDTO dataStatusDTO) throws SubDivisionException;
	
	public String isSubDivisionCodeExist(DataStatusDTO dataStatusDTO) throws SubDivisionException;
}