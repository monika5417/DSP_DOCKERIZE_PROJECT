package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.builddesk.config.CacheStore;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.repository.DistributionCenterRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DivisionRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SubDivisionRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;

@Service
public class DistributionCenterServiceImpl implements DistributionCenterService {

	Logger logger = LoggerFactory.getLogger(DistributionCenterServiceImpl.class);

	@Autowired
	DistributionCenterRepository repository;

	@Autowired
	CacheStore<DistributionCenter> distributionCenterTypeCache;

	@Autowired
	private SubDivisionRepository subDivisionRepository;

	@Override
	public Response<DistributionCenter> save(DistributionCenter distributionCenter) throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : save()";
		logger.info(method);

		final Response<DistributionCenter> response = new Response<>();

		if (Objects.isNull(distributionCenter)) {
			logger.error("DistributionCenter object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DistributionCenterException(response);
		} else {

			DistributionCenter dcResponse = repository.save(distributionCenter);

			if (Objects.isNull(dcResponse)) {
				logger.error("repository.save(distributionCenter1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new DistributionCenterException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<DistributionCenter> list = new ArrayList<>();
				list.add(dcResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<DistributionCenter> update(DistributionCenter distributionCenter)
			throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : update()";
		logger.info(method);

		final Response<DistributionCenter> response = new Response<>();

		if (Objects.isNull(distributionCenter)) {
			logger.error("DistributionCenter object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DistributionCenterException(response);
		} else {

			DistributionCenter dcResponse = repository.save(distributionCenter);

			if (Objects.isNull(dcResponse)) {
				logger.error("repository.save(distributionCenter1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new DistributionCenterException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<DistributionCenter> list = new ArrayList<>();

				list.add(dcResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<DistributionCenter> delete(long distributionCenterId) throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : delete()";
		logger.info(method);
		final Response<DistributionCenter> response = new Response<>();
		DistributionCenter distributionCenter = this.findDistributionCenterById(distributionCenterId);

		if (Objects.isNull(distributionCenter)) {
			logger.error("DistributionCenter object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DistributionCenterException(response);
		} else {

			distributionCenter.setDeleted(true);

			repository.save(distributionCenter);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;
		}

	}

	@Override
	public Response<DistributionCenter> findById(long distributionCenterId) throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : findById()";
		logger.info(method);
		final Response<DistributionCenter> response = new Response<>();
		Optional<DistributionCenter> optional = repository.findById(distributionCenterId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DC_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DistributionCenterException(response);
		} else {
			DistributionCenter distributionCenter = optional.get();
			System.out.println("distributionCenter" + distributionCenter);
			final List<DistributionCenter> list = new ArrayList<>();
			list.add(distributionCenter);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<DistributionCenter>> findAll() throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<DistributionCenter> distributionCenters = repository.findAll();
		if (Objects.isNull(distributionCenters) || distributionCenters.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DC_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DistributionCenterException(response);
		} else {
			response.setList(distributionCenters);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<DistributionCenter> findAllDistributionCenters() throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : findAllDistributionCenters()";
		logger.info(method);
		final Response<DistributionCenter> response = new Response<>();
		List<DistributionCenter> distributionCenters = repository.findAll();
		if (Objects.isNull(distributionCenters) || distributionCenters.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllDistributionCenters call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DC_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DistributionCenterException(response);
		} else {
			logger.info("Response is returning successfully");
			return distributionCenters;
		}
	}

	@Override
	public DistributionCenter findDistributionCenterById(Long distributionCenterId) throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : findDistributionCenterById()";
		logger.info(method);
		final Response<DistributionCenter> response = new Response<>();
		Optional<DistributionCenter> optional = repository.findById(distributionCenterId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findDistributionCenterById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DC_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DistributionCenterException(response);
		} else {
			DistributionCenter distributionCenter = optional.get();
			logger.info("Response is returning successfully");
			return distributionCenter;
		}
	}

	@Override
	public List<DistributionCenter> findAllDistributionCentersBySubDivision(Long subDivisionId)
			throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : findAllDistributionCentersBySubDivision()";
		logger.info(method);
		final Response<DistributionCenter> response = new Response<>();
		List<DistributionCenter> distributionCenters = null;
		List<DistributionCenter> returnModel = null;

//		distributionCenters = distributionCenterTypeCache.get("allDistrictDistributionCenter");

		if (distributionCenters == null || distributionCenters.isEmpty()) {
//			distributionCenters = repository.findAllDistrictDistributionCenter();

//			code start by monika 2-september 2024 for getting all the dc by subDivisionId
			distributionCenters = repository.findAllDistrictDistributionCenter(subDivisionId);
//			code end by monika 2-september 2024 for getting all the dc by subDivisionId
			distributionCenterTypeCache.add("allDistrictDistributionCenter", distributionCenters);
		}
		if (distributionCenters != null && !distributionCenters.isEmpty()) {
			returnModel = new ArrayList<>();
			for (DistributionCenter distributionCenter : distributionCenters) {
				if (subDivisionId != null && distributionCenter.getDcSubdivision() != null
						&& distributionCenter.getDcSubdivision().getSubDivisionId() != null
						&& distributionCenter.getDcSubdivision().getSubDivisionId().equals(subDivisionId)) {
					returnModel.add(distributionCenter);
				}
			}
		}

		if (Objects.isNull(returnModel) || returnModel.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllDistributionCentersBySubDivision call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DC_RECORD_FETCH_ALL_BY_SUB_DIVISION_ID_FAILED_MESSAGE);
			throw new DistributionCenterException(response);
		} else {
			logger.info("Response is returning successfully");
			return returnModel;
		}
	}

	@Override
	public String isDistributionCenterExist(DataStatusDTO dataStatusDTO) throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : isDistributionCenterExist()";
		logger.info(method);
		List<DistributionCenter> dcCenters = null;
		if (dataStatusDTO.getCrudType() == 1) {
			dcCenters = repository.checkDistributionCenter(dataStatusDTO.getDataName());
		} else if (dataStatusDTO.getCrudType() == 2) {
			dcCenters = repository.checkDistributionCenterForUpdate(dataStatusDTO.getDataName(),
					dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(dcCenters) || dcCenters.isEmpty()) {
			logger.error(
					"repository.checkDistributionCenter(dcName) is returning Null when isDistributionCenterExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

	@Override
	public String isDistributionCenterCodeExist(DataStatusDTO dataStatusDTO) throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : isDistributionCenterCodeExist()";
		logger.info(method);
		List<DistributionCenter> dcCenters = null;
		if (dataStatusDTO.getCrudType() == 1) {
			dcCenters = repository.checkDistributionCenterCode(dataStatusDTO.getDataCode());
		} else if (dataStatusDTO.getCrudType() == 2) {
			dcCenters = repository.checkDistributionCenterCodeForUpdate(dataStatusDTO.getDataCode(),
					dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(dcCenters) || dcCenters.isEmpty()) {
			logger.error(
					"repository.checkDistributionCenterCode(dcCode) is returning Null when isDistributionCenterCodeExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;

		}
	}

	@Override
	public List<DistributionCenter> findAllActiveDistributionCenter() throws DistributionCenterException {

		final String method = "DistributionCenterServiceImpl : findAllActiveDistributionCenter()";
		logger.info(method);

		List<DistributionCenter> distributionCenter = repository.findAllActiveDistributionCenter();

		final Response<DistributionCenter> response = new Response<>();
		if (Objects.isNull(distributionCenter) || distributionCenter.isEmpty()) {
			logger.error(
					"repository.findAllActiveDistributionCenter is returning Null when findAllActiveDistributionCenter call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DistributionCenterException(response);
		} else {
			logger.info("Response is returning successfully");
			return distributionCenter;
		}
	}

	@Override
	public DistributionCenter findDistributionCenterByDcCode(String dcCode) throws DistributionCenterException {

		final Response<DistributionCenter> distributionCenter = new Response<>();

		return repository.findDistributionCenterByDcCode(dcCode).orElseThrow(
				() -> new DataNotFoundException(new Response<String>(ResponseCode.DATA_NOT_FOUND, "Dc not found")));
	}

	@Override
	public List<DistributionCenter> findAllDistributionCentersByDistrict(Long districtId)
			throws DistributionCenterException {
		final String method = "DistributionCenterServiceImpl : findAllDistributionCentersByDistrict()";
		logger.info(method);
		final Response<DistributionCenter> response = new Response<>();

		List<DistributionCenter> findByDistrict = repository.findByDistrict(districtId);

		if (Objects.isNull(findByDistrict) || findByDistrict.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllDistributionCentersByDistrict call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DC_RECORD_FETCH_ALL_BY_SUB_DIVISION_ID_FAILED_MESSAGE);
			throw new DistributionCenterException(response);
		} else {
			logger.info("Response is returning successfully");
			return findByDistrict;
		}
	}

	@Override
	public List<DistributionCenter> findAllDistributionCentersBydcIdAnddcSubdivision_subDivisionIdAndSubdivisionDivision_divisionId(
			Long divisionId) {
	
		List<DistributionCenter> list = new ArrayList<DistributionCenter>();
		List<Long> subDivisionId = subDivisionRepository.findByDivisionId(divisionId);
		
		List<DistributionCenter> findByDcIdIn = repository
				.findByDcSubdivisionIn(subDivisionId);
		
		findByDcIdIn.stream().forEach(f->{
			
			DistributionCenter d = new DistributionCenter();
			d.setDcId(f.getDcId());
			d.setDcName(f.getDcName());
			d.setSubstations(null);
			list.add(d);
		});
		return list;
	}

}
