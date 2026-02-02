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
import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.repository.DivisionRepository;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DivisionService;
import com.mpcz.deposit_scheme.backend.api.util.MasterDataStatus;
//import com.mpcz.htngb.exception.GroupNotFoundException;
//import com.mpcz.htngb.response.GroupNumberDTO;

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 *
 */
@Service
public class DivisionServiceImpl implements DivisionService {

	Logger logger = LoggerFactory.getLogger(DivisionServiceImpl.class);

	@Autowired
	DivisionRepository repository;

	@Autowired
	CacheStore<Division> divisionTypeCache;

	@Override
	public Response<Division> save(Division division) throws DivisionException {
		final String method = "DivisionServiceImpl : save()";
		logger.info(method);

		final Response<Division> response = new Response<>();

		if (Objects.isNull(division)) {
			logger.error("Division object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DivisionException(response);
		} else {

			Division divisionResponse = repository.save(division);

			if (Objects.isNull(divisionResponse)) {
				logger.error("repository.save(division1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new DivisionException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<Division> list = new ArrayList<>();
				list.add(divisionResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Division> update(Division division) throws DivisionException {
		final String method = "DivisionServiceImpl : update()";
		logger.info(method);

		final Response<Division> response = new Response<>();

		if (Objects.isNull(division)) {
			logger.error("Division object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DivisionException(response);
		} else {

			Division divisionResponse = repository.save(division);

			if (Objects.isNull(divisionResponse)) {
				logger.error("repository.save(division1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new DivisionException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<Division> list = new ArrayList<>();

				list.add(divisionResponse);
				response.setList(list);
				return response;
			}
		}
	}

	@Override
	public Response<Division> delete(long divisionId) throws DivisionException {
		final String method = "DivisionServiceImpl : delete()";
		logger.info(method);
		Division division = this.findDivisionById(divisionId);
		final Response<Division> response = new Response<>();

		if (Objects.isNull(division)) {
			logger.error("Division object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DivisionException(response);
		} else {

			repository.save(division);
			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;
		}

	}

	@Override
	public Response<Division> findById(long divisionId) throws DivisionException {
		final String method = "DivisionServiceImpl : findById()";
		logger.info(method);
		final Response<Division> response = new Response<>();
		Optional<Division> optional = repository.findById(divisionId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DIVISION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DivisionException(response);
		} else {
			Division division = optional.get();
			final List<Division> list = new ArrayList<>();
			list.add(division);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<Division>> findAll() throws DivisionException {
		final String method = "DivisionServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<Division> divisions = repository.findAll();
		if (Objects.isNull(divisions) || divisions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DIVISION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DivisionException(response);
		} else {
			response.setList(divisions);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	@Override
	public List<Division> findAllDivisions() throws DivisionException {
		final String method = "DivisionServiceImpl : findAllDivisions()";
		logger.info(method);
		final Response<Division> response = new Response<>();
		List<Division> divisions = repository.findAll();
		if (Objects.isNull(divisions) || divisions.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllDivisions call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DIVISION_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DivisionException(response);
		} else {
			logger.info("Response is returning successfully");
			return divisions;
		}
	}

	@Override
	public List<Division> findAllDivisionsByCircle(Long circleId) throws DivisionException {
		final String method = "DivisionServiceImpl : findAllDivisionsByCircle()";
		logger.info(method);
		final Response<Division> response = new Response<>();
		List<Division> divisions = null;
		List<Division> returnModel = null;

		divisions = divisionTypeCache.get("allDivisionCircle");

		if (divisions == null || divisions.isEmpty()) {
			divisions = repository.findAllCircleDivision();
			divisionTypeCache.add("allDivisionCircle", divisions);
		}
		if (divisions != null && !divisions.isEmpty()) {
			returnModel = new ArrayList<>();
			for (Division division : divisions) {
				if (circleId != null && division.getDivisionCircle() != null
						&& division.getDivisionCircle().getCircleId() != null
						&& division.getDivisionCircle().getCircleId().equals(circleId)) {
					returnModel.add(division);
				}
			}
		}

		if (Objects.isNull(returnModel) || returnModel.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAllDivisionsByCircle call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DIVISION_RECORD_FETCH_ALL_BY_CIRCLE_ID_FAILED_MESSAGE);
			throw new DivisionException(response);
		} else {
			logger.info("Response is returning successfully");
			return returnModel;
		}
	}

	@Override
	public Division findDivisionById(long divisionId) throws DivisionException {
		final String method = "DivisionServiceImpl : findDivisionById()";
		logger.info(method);
		final Response<Division> response = new Response<>();
		Optional<Division> optional = repository.findById(divisionId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findDivisionById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DIVISION_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DivisionException(response);
		} else {
			Division division = optional.get();
			logger.info("Response is returning successfully");
			return division;
		}
	}

//	@Override
//	public List<GroupNumberDTO> findAllGroupNumbersByDivisionId(long divisionId) throws DivisionException, GroupNotFoundException {
//		final String method = "DivisionServiceImpl : findAllGroupNumbersByDivisionId()";
//		logger.info(method);
//		List<GroupNumberDTO> groupList = new ArrayList<>();
//		final Response response = new Response<>();
//		Optional<Division> optional = repository.findById(divisionId);
//		if (Objects.isNull(optional) || !optional.isPresent()) {
//			logger.error("repository.findById is returning Null when findAllGroupNumbersByDivisionId call");
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(ResponseMessage.GROUP_RECORD_FETCH_ALL_BY_DIVISION_ID_FAILED_MESSAGE);
//			throw new DivisionException(response);
//		} else {
//			Division division = optional.get();
//			GroupNumberDTO dto = new GroupNumberDTO();
// 
//			
//			if (division.isGroupAStatus()) {
//				dto.addValue("GroupA", "A");
//			}
//			if (division.isGroupBStatus()) {
//				dto.addValue("GroupB", "B");
//			}
//			if (division.isGroupCStatus()) {
//				dto.addValue("GroupC", "C");
//			}
//			if (division.isGroupDStatus()) {
//				dto.addValue("GroupD", "D");
//			}
//			if (division.isGroupEStatus()) {
//				dto.addValue("GroupE", "E");
//			}
//
//			if (dto.getValues().isEmpty()) {
//				response.setCode(HttpCode.NULL_OBJECT);
//				response.setMessage(ResponseMessage.GROUP_RECORD_FETCH_ALL_BY_DIVISION_ID_FAILED_MESSAGE);
//				throw new GroupNotFoundException(response);
//			} else {
//
//				groupList.add(dto);
//			}
//
//			logger.info("Response is returning successfully");
//			return groupList;
//		}
//	}

	@Override
	public String isDivisionExist(DataStatusDTO dataStatusDTO) throws DivisionException {
		final String method = "DivisionServiceImpl : isDivisionExist()";
		logger.info(method);
		List<Division> divisions = null;
		if (dataStatusDTO.getCrudType() == 1) {
			divisions = repository.checkDivision(dataStatusDTO.getDataName());
		} else if (dataStatusDTO.getCrudType() == 2) {
			divisions = repository.checkDivisionForUpdate(dataStatusDTO.getDataName(), dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(divisions) || divisions.isEmpty()) {
			logger.error("repository.checkDivision(division) is returning Null when isDivisionExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;
		}
	}

	@Override
	public String isDivisionCodeExist(DataStatusDTO dataStatusDTO) throws DivisionException {
		final String method = "DivisionServiceImpl : isDivisionCodeExist()";
		logger.info(method);
		List<Division> divisions = null;
		if (dataStatusDTO.getCrudType() == 1) {
			divisions = repository.checkDivisionCode(dataStatusDTO.getDataCode());
		} else if (dataStatusDTO.getCrudType() == 2) {
			divisions = repository.checkDivisionCodeForUpdate(dataStatusDTO.getDataCode(), dataStatusDTO.getRecordId());
		}
		if (Objects.isNull(divisions) || divisions.isEmpty()) {
			logger.error("repository.checkDivisionCode(divisionCode) is returning Null when isDivisionCodeExist call");
			return MasterDataStatus.NOT_EXISTS;
		} else {
			logger.info("Response is returning successfully");
			return MasterDataStatus.EXISTS;

		}
	}

}
