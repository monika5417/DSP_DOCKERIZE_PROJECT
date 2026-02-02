package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.builddesk.config.CacheStore;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.repository.CircleRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.CircleService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 *
 */
@Service
public class CircleServiceImpl implements CircleService {

	Logger logger = LoggerFactory.getLogger(CircleServiceImpl.class);

	@Autowired
	CircleRepository repository;
	
	@Autowired
	CacheStore<Circle> circleTypeCache;

	@Override
	public Response<Circle> save(Circle circle) throws CircleException {
		final String method = "CircleServiceImpl : save()";
		logger.info(method);

		final Response<Circle> response = new Response<>();

		if (Objects.isNull(circle)) {
			logger.error("Circle object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new CircleException(response);
		} else {

			Circle circleResponse = circleResponse = repository.save(circle);

			if (Objects.isNull(circleResponse)) {
				logger.error("repository.save(circle1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new CircleException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<Circle> list = new ArrayList<>();
				list.add(circleResponse);
				response.setList(list);

				return response;
			}
		}
	}

	@Override
	public Response<Circle> update(Circle circle) throws CircleException {
		final String method = "CircleServiceImpl : update()";
		logger.info(method);

		final Response<Circle> response = new Response<>();

		if (Objects.isNull(circle)) {
			logger.error("Circle object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new CircleException(response);
		} else {
			Circle circleResponse = circleResponse = repository.save(circle);

			if (Objects.isNull(circleResponse)) {
				logger.error("repository.save(circle1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new CircleException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<Circle> list = new ArrayList<>();

				list.add(circleResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Circle> delete(long circleId) throws CircleException {
		final String method = "CircleServiceImpl : delete()";
		logger.info(method);
		final Response<Circle> response = new Response<>();
		Circle circle = this.findCircleById(circleId);
		if (Objects.isNull(circle)) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new CircleException(response);
		} else {

			repository.save(circle);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<Circle> findById(long circleId) throws CircleException {
		final String method = "CircleServiceImpl : findById()";
		logger.info(method);
		final Response<Circle> response = new Response<>();
		Optional<Circle> optional = repository.findById(circleId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CIRCLE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new CircleException(response);
		} else {
			Circle circle = optional.get();
			final List<Circle> list = new ArrayList<>();
			list.add(circle);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<Circle>> findAll() throws CircleException {
		final String method = "CircleServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<Circle> circles = new ArrayList<>();
		try {
//			**************************Sandeep Namdeo, Date:- 05-08-2022-Start************************
//			circles = repository.findAll();
			circles = repository.findAllOrderByAsc();
//			**************************Sandeep Namdeo, Date:- 05-08-2022-End**************************	

		} catch (JpaObjectRetrievalFailureException e) {
			logger.error("Error in Response: " + e.getMessage());
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CIRCLE_RECORD_FETCH_ALL_FAILED_MESSAGE + " in JPA");
			throw new CircleException(response);
		}
		if (Objects.isNull(circles) || circles.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CIRCLE_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new CircleException(response);
		} else {
			response.setList(circles);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<Circle> findAllCircles() throws CircleException {
		final String method = "CircleServiceImpl : findAllCircles()";
		logger.info(method);
		Response response = new Response<>();
		List<Circle> circles = repository.findAll();
		if (Objects.isNull(circles) || circles.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllCircles call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CIRCLE_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new CircleException(response);
		} else {
			logger.info("Response is returning successfully");
			return circles;
		}
	}

	@Override
	public List<Circle> findAllCirclesByRegion(final Long regionId) throws CircleException {
		final String method = "CircleServiceImpl : findAllCirclesByRegion()";
		logger.info(method);
		final Response<Circle> response = new Response<>();
		List<Circle> circles = null;
		List<Circle> returnModel = null;
		
		circles = circleTypeCache.get("allCircle");
		
		if(circles == null || circles.isEmpty()) {
			circles = repository.findAllCircleByRegion();
			circleTypeCache.add("allCircles", circles);
		}
		if(circles != null && !circles.isEmpty()) {
			returnModel = new ArrayList<>();
			for (Circle circle : circles) {
				if(regionId!= null && circle.getCircle()!= null
						&& circle.getCircleId()!=null
						&& circle.getCircleRegion().getRegionId()==regionId) {
					returnModel.add(circle);
				}
			}
		}
		if (Objects.isNull(returnModel) || returnModel.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllCirclesByRegion call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CIRCLE_RECORD_FETCH_ALL_BY_REGION_ID_FAILED_MESSAGE);
			throw new CircleException(response);
		} else {
			logger.info("Response is returning successfully");
			return returnModel;
		}
	}

	@Override
	public Circle findCircleById(Long circleId) throws CircleException {
		final String method = "CircleServiceImpl : findCircleById()";
		logger.info(method);
		final Response<Circle> response = new Response<>();
		Optional<Circle> optional = repository.findById(circleId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findCircleById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CIRCLE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new CircleException(response);
		} else {
			Circle circle = optional.get();
			logger.info("Response is returning successfully");
			return circle;
		}
	}

	@Override
	public String isCircleExist(DataStatusDTO dataStatusDTO) throws CircleException {
		final String method = "CircleServiceImpl : isCircleExist()";
		logger.info(method);
		List<Circle> circles = null;
		if (dataStatusDTO.getCrudType() == 1) {
			circles = repository.checkCircle(dataStatusDTO.getDataName());
		} else if (dataStatusDTO.getCrudType() == 2) {
			circles = repository.checkCircleForUpdate(dataStatusDTO.getDataName(), dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(circles) || circles.isEmpty()) {
			logger.error("repository.checkCircle(circle) is returning Null when isCircleExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

	@Override
	public String isCircleCodeExist(DataStatusDTO dataStatusDTO) throws CircleException {
		final String method = "CircleServiceImpl : isCircleCodeExist()";
		logger.info(method);
		List<Circle> circles = null;
		if (dataStatusDTO.getCrudType() == 1) {
			circles = repository.checkCircleCode(dataStatusDTO.getDataCode());
		} else if (dataStatusDTO.getCrudType() == 2) {
			circles = repository.checkCircleCodeForUpdate(dataStatusDTO.getDataCode(), dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(circles) || circles.isEmpty()) {
			logger.error("repository.checkCircleCode(circleCode) is returning Null when isCircleCodeExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;

		}
	}

}
