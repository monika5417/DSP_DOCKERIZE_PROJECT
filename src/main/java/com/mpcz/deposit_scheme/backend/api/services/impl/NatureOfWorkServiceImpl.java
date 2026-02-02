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
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWorkException;
import com.mpcz.deposit_scheme.backend.api.repository.NatureOfWorkRepository;
import com.mpcz.deposit_scheme.backend.api.request.NatureOfWorkForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.NatureOfWorkService;

@Service
public class NatureOfWorkServiceImpl implements NatureOfWorkService {

	Logger logger = LoggerFactory.getLogger(ApplicationDocumentServiceImpl.class);

	@Autowired
	private NatureOfWorkRepository natureOfWorkRepository;

	@Override
	public NatureOfWork saveNatureOfWork(NatureOfWorkForm natureOfWorkDocumentForm)
			throws NatureOfWorkException {

		final String method = "NatureOfWorkServiceImpl : saveNatureOfWorkDocument()";

		logger.info(method);

		Response<NatureOfWork> response = new Response<>();

		NatureOfWork natureOfWorkResponse = null;

		if (Objects.isNull(natureOfWorkDocumentForm)) {
			logger.error("NatureOfWorkDocument object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new NatureOfWorkException(response);
		} else {

			NatureOfWork natureOfWork = new NatureOfWork();

			natureOfWork.setNatureOfWorkName(natureOfWorkDocumentForm.getNatureOfWorkName().trim());
			
			natureOfWorkResponse = natureOfWorkRepository.save(natureOfWork);
			
			if (Objects.isNull(natureOfWorkResponse)) {
				logger.error("NatureOfWorkRepository.save(natureOfWorkDocument) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new NatureOfWorkException(response);
			} else {
				return natureOfWorkResponse;
			}
		}
	}
	
	@Override
	public List<NatureOfWork> findAllNatureOfWorkDocumentType() throws NatureOfWorkException {

		final String method = "ApplicationTypeServiceImpl : findAllApplicationType()";
		logger.info(method);

		List<NatureOfWork> natureOfWork = natureOfWorkRepository.findAll();

		final Response<NatureOfWork> response = new Response<>();
		if (Objects.isNull(natureOfWork) || natureOfWork.isEmpty()) {
			logger.error(
					"applicationTypeRepository.findAllActiveApplicationType is returning Null when findAllApplicationType call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new NatureOfWorkException(response);
		} else {
			logger.info("Response is returning successfully");
			return natureOfWork;
		}

}
	
	
	@Override
	public NatureOfWork findByNatureOfWorkId(Long applicationTypeId) throws NatureOfWorkException {

		final String method = "ApplicationTypeServiceImpl : findByApplicationTypeId(Long applicationTypeId)";

		logger.info(method);

		final Optional<NatureOfWork> natureOfWorkTypeOptional = natureOfWorkRepository.findById(applicationTypeId);

		final Response<NatureOfWork> response = new Response<>();

		if (Objects.isNull(natureOfWorkTypeOptional) || !natureOfWorkTypeOptional.isPresent()) {
			logger.error("applicationTypeRepository.findById is returning Null when findByApplicationTypeId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new NatureOfWorkException(response);
		} else {
			NatureOfWork natureOfWorkResponse = natureOfWorkTypeOptional.get();
			final List<NatureOfWork> list = new ArrayList<>();
			list.add(natureOfWorkResponse);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return natureOfWorkResponse;
		}
	}
}
