//package com.mpcz.deposit_scheme.backend.api.services.impl;
//
//import java.util.Objects;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.domain.WorkType;
//import com.mpcz.deposit_scheme.backend.api.exception.WorkTypeException;
//import com.mpcz.deposit_scheme.backend.api.repository.WorkTypeRepository;
//import com.mpcz.deposit_scheme.backend.api.request.WorkTypeForm;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.ContractorForBidService;
//import com.mpcz.deposit_scheme.backend.api.services.WorkTypeService;
//
//@Service
//public class  ContractorForBidServiceImpl implements ContractorForBidService {
//
//	Logger logger = LoggerFactory.getLogger(WorkTypeServiceImpl.class);
//
//	@Autowired
//	private WorkTypeRepository workTypeRepository;
//
//	@Override
//	public WorkType saveWorkType(WorkTypeForm workTypeForm) throws WorkTypeException {
//
//		final String method = "WorkTypeServiceImpl : saveWorkType()";
//		logger.info(method);
//
//		Response<WorkType> response = new Response<>();
//
//		WorkType workTypeResponse = null;
//
//		if (Objects.isNull(workTypeForm)) {
//			logger.error("Work Type object is null");
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
//			throw new WorkTypeException(response);
//		} else {
//
//			WorkType workType = new WorkType();
//			workType.setWorkTypeName(workTypeForm.getWorkTypeName().trim());
//
//			workTypeResponse = workTypeRepository.save(workType);
//
//			if (Objects.isNull(workTypeResponse)) {
//				logger.error("repository.save(workType) is returning Null");
//				response.setCode(HttpCode.NULL_OBJECT);
//				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
//				throw new WorkTypeException(response);
//			} else {
//				return workTypeResponse;
//			}
//
//		}
//	}
