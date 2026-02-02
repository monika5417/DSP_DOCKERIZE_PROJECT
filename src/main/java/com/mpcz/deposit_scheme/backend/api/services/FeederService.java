package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Feeder;
import com.mpcz.deposit_scheme.backend.api.exception.FeederException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * This FeederService is used to Manage Feeders
 *
 * @author Charitra Prajapati
 * @version 1.0
 */

public interface FeederService {
	public Response<Feeder> save(final Feeder feeder) throws FeederException;

	public Response<Feeder> update(final Feeder feeder) throws FeederException;

	public Response<Feeder> delete(final long feederId) throws FeederException;

	public Response<Feeder> findById(final long feederId) throws FeederException;

	public Response<List<Feeder>> findAll() throws FeederException;

	public List<Feeder> findAllFeeders() throws FeederException;

	public Feeder findFeederById(Long feederId) throws FeederException;

	public List<Feeder> findAllFeedersBySubstation(final Long substationId) throws FeederException;

//	sandeep, starts
	public List<Feeder> findAllActiveFeeder() throws FeederException;
//	sandeep, ends

}
