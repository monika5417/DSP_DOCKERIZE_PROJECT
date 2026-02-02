package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.Substation;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.SubstationException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * This SubstationService is used to Manage sub-stations
 * 
 * @author Aditya Vays
 * @version 1.0
 */

public interface SubstationService {
	public Response<Substation> save(final Substation substation) throws SubstationException;

	public Response<Substation> update(final Substation substation) throws SubstationException;

	public Response<Substation> delete(final long substationId) throws SubstationException;

	public Response<Substation> findById(final long substationId) throws SubstationException;

	public Response<List<Substation>> findAll() throws SubstationException;

	public List<Substation> findAllSubstations() throws SubstationException;

	public Substation findSubstationById(Long substationId) throws SubstationException;

	public List<Substation> findAllSubstationsByDC(final Long dcId) throws SubstationException;

	public String isSubstationExist(DataStatusDTO dataStatusDTO) throws SubstationException;

	public String isSubstationCodeExist(DataStatusDTO dataStatusDTO) throws SubstationException;

//	sandeep, starts
	public List<Substation> findAllActiveSubstation() throws SubstationException;
//	sandeep, ends

}