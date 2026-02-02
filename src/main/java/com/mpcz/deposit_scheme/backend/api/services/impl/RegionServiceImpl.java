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
import com.mpcz.deposit_scheme.backend.api.domain.Region;
import com.mpcz.deposit_scheme.backend.api.exception.RegionException;
import com.mpcz.deposit_scheme.backend.api.repository.RegionRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.RegionService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 *
 */
@Service
public class RegionServiceImpl implements RegionService {
	
	Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

	 

	@Autowired
	RegionRepository repository;

	@Override
	public Response<Region> save(Region region) throws RegionException {
		final String method = "RegionServiceImpl : save()";
		logger.info(method);

		final Response<Region> response = new Response<>();

		if (Objects.isNull(region)) {
			logger.error("Region object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new RegionException(response);
		} else {
			Region regionResponse = repository.save(region);
		

			if (Objects.isNull(regionResponse)) {
				logger.error("repository.save(region1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new RegionException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<Region> list = new ArrayList<>();
				list.add(regionResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Region> update(Region region) throws RegionException {
		final String method = "RegionServiceImpl : update()";
		logger.info(method);

		System.out.println("ID:" + region.getRegionId());

		final Response<Region> response = new Response<>();

		if (Objects.isNull(region)) {
			logger.error("Region object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new RegionException(response);
		} else {
			Region regionResponse = repository.save(region);

			if (Objects.isNull(regionResponse)) {
				logger.error("repository.save(region1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new RegionException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<Region> list = new ArrayList<>();
				list.add(regionResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Region> delete(Long regionId) throws RegionException {

		final String method = "RegionServiceImpl : delete()";
		logger.info(method);

		final Response<Region> response = new Response<>();

		Region region = this.findRegionById(regionId);
		if (Objects.isNull(region)) {
			logger.error("Region object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new RegionException(response);
		} else {

			repository.save(region);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<Region> findById(Long regionId) throws RegionException {
		final String method = "RegionServiceImpl : findById()";
		logger.info(method);
		final Response<Region> response = new Response<>();
		Optional<Region> optional = repository.findById(regionId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.REGION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new RegionException(response);
		} else {
			Region region = optional.get();
			final List<Region> list = new ArrayList<>();
			list.add(region);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<Region>> findAll() throws RegionException {

		final String method = "RegionServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<Region> regions = repository.findAll();
		if (Objects.isNull(regions) || regions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.REGION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new RegionException(response);
		} else {
			response.setList(regions);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<Region> findAllRegions() throws RegionException {
		final String method = "RegionServiceImpl : findAllRegions()";
		logger.info(method);
		List<Region> regions = repository.findAll();
		final Response<Region> response = new Response<>();
		if (Objects.isNull(regions) || regions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllRegions call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.REGION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new RegionException(response);
		} else {
			logger.info("Response is returning successfully");
			return regions;
		}
	}

	@Override
	public Region findRegionById(Long regionId) throws RegionException {
		final String method = "RegionServiceImpl : findRegionById()";
		logger.info(method);
		final Response<Region> response = new Response<>();
		Optional<Region> optional = repository.findById(regionId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findRegionById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.REGION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new RegionException(response);
		} else {
			Region region = optional.get();
			logger.info("Response is returning successfully");
			return region;
		}
	}

	@Override
	public List<Region> findAllRegionsByDiscom(Long discomId) throws RegionException {
		final String method = "RegionServiceImpl : findAllRegionsByDiscom()";
		logger.info(method);
		final Response<Region> response = new Response<>();
		List<Region> regions = repository.findByDiscom(discomId);
		if (Objects.isNull(regions) || regions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllRegionsByDiscom call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.REGION_RECORD_FETCH_ALL_BY_DISCOM_ID_FAILED_MESSAGE);
			throw new RegionException(response);
		} else {
			logger.info("Response is returning s" + "uccessfully");
			return regions;
		}
	}

	@Override
	public String isRegionExist(DataStatusDTO dataStatusDTO) throws RegionException {
		final String method = "RegionServiceImpl : isRegionExist()";
		logger.info(method);
		List<Region> regions = null;
		if (dataStatusDTO.getCrudType() == 1) {
			regions = repository.checkRegion(dataStatusDTO.getDataName());
		} else if (dataStatusDTO.getCrudType() == 2) {
			regions = repository.checkRegionForUpdate(dataStatusDTO.getDataName(), dataStatusDTO.getRecordId());
		}
		System.out.println(regions);

		if (Objects.isNull(regions) || regions.isEmpty()) {
			logger.error("repository.findAll is returning Null when isRegionExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

	@Override
	public String isRegionCodeExist(DataStatusDTO dataStatusDTO) throws RegionException {
		final String method = "CircleServiceImpl : isCircleCodeExist()";
		logger.info(method);
		List<Region> regions = null;
		if (dataStatusDTO.getCrudType() == 1) {
			regions = repository.checkRegionCode(dataStatusDTO.getDataCode());
		} else if (dataStatusDTO.getCrudType() == 2) {
			regions = repository.checkRegionCodeForUpdate(dataStatusDTO.getDataCode(), dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(regions) || regions.isEmpty()) {
			logger.error("repository.checkRegionCode(regionCode) is returning Null when isRegionCodeExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;

		}
	}
}
