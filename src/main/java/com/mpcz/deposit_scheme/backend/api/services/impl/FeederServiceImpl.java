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
import com.mpcz.deposit_scheme.backend.api.domain.Feeder;
import com.mpcz.deposit_scheme.backend.api.exception.FeederException;
import com.mpcz.deposit_scheme.backend.api.repository.FeederRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.FeederService;

/**
 *
 * @author CHARITRA
 * @version 1.0.0
 *
 */
@Service
public class FeederServiceImpl implements FeederService {
	Logger logger = LoggerFactory.getLogger(FeederServiceImpl.class);

	@Autowired
	FeederRepository repository;

	@Override
	public Response<Feeder> save(Feeder feeder) throws FeederException {
		final String method = "FeederServiceImpl : save()";
		logger.info(method);

		final Response<Feeder> response = new Response<>();

		if (Objects.isNull(feeder)) {
			logger.error("Feeder object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new FeederException(response);
		} else {
			Feeder feederResponse = repository.save(feeder);

			if (Objects.isNull(feederResponse)) {
				logger.error("repository.save(feeder1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new FeederException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<Feeder> list = new ArrayList<>();
				list.add(feederResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Feeder> update(Feeder feeder) throws FeederException {
		final String method = "FeederServiceImpl : update()";
		logger.info(method);

		final Response<Feeder> response = new Response<>();

		if (Objects.isNull(feeder)) {
			logger.error("Feeder object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new FeederException(response);
		} else {

			Feeder feederResponse = repository.save(feeder);

			if (Objects.isNull(feederResponse)) {
				logger.error("repository.save(feeder1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new FeederException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<Feeder> list = new ArrayList<>();

				list.add(feederResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Feeder> delete(long feederId) throws FeederException {
		final String method = "FeederServiceImpl : delete()";
		logger.info(method);
		final Response<Feeder> response = new Response<>();
		Feeder feeder = this.findFeederById(feederId);

		repository.save(feeder);
		logger.info("Response is returning successfully");
		response.setCode(ResponseCode.OK);
		response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
		return response;

	}

	@Override
	public Response<Feeder> findById(long feederId) throws FeederException {
		final String method = "FeederServiceImpl : findById()";
		logger.info(method);
		final Response<Feeder> response = new Response<>();
		Optional<Feeder> optional = repository.findById(feederId);
		if (Objects.isNull(optional)) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new FeederException(response);
		} else {
			Feeder feeder = optional.get();
			final List<Feeder> list = new ArrayList<>();
			list.add(feeder);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<Feeder>> findAll() throws FeederException {
		final String method = "FeederServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<Feeder> feeders = repository.findAll();
		if (Objects.isNull(feeders) || feeders.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new FeederException(response);
		} else {
			response.setList(feeders);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<Feeder> findAllFeeders() throws FeederException {
		final String method = "FeederServiceImpl : findAllFeeders()";
		logger.info(method);
		List<Feeder> feeders = repository.findAll();
		Response response = new Response<>();
		if (Objects.isNull(feeders) || feeders.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllFeeders call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new FeederException(response);
		} else {
			logger.info("Response is returning successfully");
			return feeders;
		}
	}

	@Override
	public Feeder findFeederById(Long feederId) throws FeederException {
		final String method = "FeederServiceImpl : findFeederById()";
		logger.info(method);
		final Response<Feeder> response = new Response<>();
		Optional<Feeder> optional = repository.findById(feederId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findFeederById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new FeederException(response);
		} else {
			Feeder feeder = optional.get();
			logger.info("Response is returning successfully");
			return feeder;
		}
	}

	@Override
	public List<Feeder> findAllFeedersBySubstation(Long substationId) throws FeederException {
		final String method = "FeederServiceImpl : findAllFeedersBySubstation()";
		logger.info(method);
		List<Feeder> feeders = repository.findBySubstation(substationId);
		Response response = new Response<>();
		if (Objects.isNull(feeders) || feeders.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllFeedersBySubstation call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_BY_SUB_STATION_ID_FAILED_MESSAGE);
			throw new FeederException(response);
		} else {
			logger.info("Response is returning successfully");
			return feeders;
		}
	}

	@Override
	public List<Feeder> findAllActiveFeeder() throws FeederException {

		final String method = "DistributionCenterServiceImpl : findAllActiveFeeder()";
		logger.info(method);

		List<Feeder> feeder = repository.findAllActiveFeeder();

		final Response<Feeder> response = new Response<>();
		if (Objects.isNull(feeder) || feeder.isEmpty()) {
			logger.error(
					"repository.findAllActiveFeeder is returning Null when findAllActiveFeeder call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new FeederException(response);
		} else {
			logger.info("Response is returning successfully");
			return feeder;
		}
	}

	// Y code chalne k liye database Substation table me Substation_id me 1096 ki entry add krna pdegii otherwise this code will not work
	
//	@Override
//	public List<Feeder> findAllFeedersBySubstation(Long substationId) throws FeederException {
//		final String method = "FeederServiceImpl : findAllFeedersBySubstation()";
//		logger.info(method);
//		final Response<Feeder> response = new Response<>();
//		List<Feeder> feeders = null;
//		List<Feeder> returnModel = null;
//		
//		feeders = feederTypeCache.get("allFeederBySubStation");
//		
//		if(feeders == null || feeders.isEmpty()) {
//			feeders = repository.findAllFeedersBySubstation();
//			feederTypeCache.add("allFeederBySubStation", feeders);
//		}
//		if(feeders != null && !feeders.isEmpty()) {
//			returnModel = new ArrayList<>();
//			for (Feeder feeder : feeders) {
//				if(substationId!= null && feeder.getFeederSubstation()!= null
//						&& feeder.getFeederSubstation().getSubStationId()!=null 
//						&& feeder.getFeederSubstation().getSubStationId().equals(substationId)) {
//					returnModel.add(feeder);
//				}
//			}
//		}
//
//		if (Objects.isNull(returnModel) || returnModel.isEmpty()) {
//			logger.error("repository.findAll is returning Null when findAllFeedersBySubstation call");
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(ResponseMessage.FEEDER_RECORD_FETCH_ALL_BY_SUB_STATION_ID_FAILED_MESSAGE);
//			throw new FeederException(response);
//		} else {
//			logger.info("Response is returning successfully");
//			return feeders;
//		}
//	}

}
