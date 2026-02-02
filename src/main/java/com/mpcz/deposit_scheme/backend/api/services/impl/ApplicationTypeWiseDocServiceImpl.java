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
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationType;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationTypeWiseDoc;
import com.mpcz.deposit_scheme.backend.api.domain.DocumentType;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeWiseDocException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationTypeWiseDocRepository;
import com.mpcz.deposit_scheme.backend.api.request.ApplicationTypeWiseDocForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationTypeService;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationTypeWiseDocService;
import com.mpcz.deposit_scheme.backend.api.services.DocumentTypeService;

@Service
public class ApplicationTypeWiseDocServiceImpl implements ApplicationTypeWiseDocService {

	Logger logger = LoggerFactory.getLogger(ApplicationTypeWiseDocServiceImpl.class);

	@Autowired
	private ApplicationTypeWiseDocRepository applicationTypeWiseDocRepository;

	@Autowired
	private ApplicationTypeService applicationTypeService;

	@Autowired
	private DocumentTypeService documentTypeService;

	@Override
	public ApplicationTypeWiseDoc saveApplicationTypeWiseDoc(ApplicationTypeWiseDocForm applicationTypeWiseDocForm)
			throws ApplicationTypeWiseDocException, ApplicationTypeException, DocumentTypeException {

		final String method = "ApplicationTypeWiseDocServiceImpl : saveApplicationTypeWiseDoc()";

		logger.info(method);

		Response<ApplicationTypeWiseDoc> response = new Response<>();

		ApplicationTypeWiseDoc applicationTypeWiseDocResponse = null;

		if (Objects.isNull(applicationTypeWiseDocForm)) {
			logger.error("Application Type Wise Doc object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ApplicationTypeWiseDocException(response);
		} else {

			ApplicationTypeWiseDoc applicationTypeWiseDoc = new ApplicationTypeWiseDoc();

			if (applicationTypeWiseDocForm.getApplicationTypeId() != null
					&& applicationTypeWiseDocForm.getDocumentTypeId() != null) {

				ApplicationType applicationTypeDb = null;
				DocumentType documentTypeDb = null;

				applicationTypeDb = applicationTypeService
						.findByApplicationTypeId(applicationTypeWiseDocForm.getApplicationTypeId());
				documentTypeDb = documentTypeService
						.findDocumentTypeById(applicationTypeWiseDocForm.getDocumentTypeId());

				if (applicationTypeDb != null && documentTypeDb != null) {
					applicationTypeWiseDoc.setApplicationType(applicationTypeDb);
					applicationTypeWiseDoc.setDocumentType(documentTypeDb);
				}
			}

			applicationTypeWiseDocResponse = applicationTypeWiseDocRepository.save(applicationTypeWiseDoc);

			if (Objects.isNull(applicationTypeWiseDocResponse)) {
				logger.error("applicationTypeWiseDocRepository.save(applicationTypeWiseDoc) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ApplicationTypeWiseDocException(response);
			} else {
				return applicationTypeWiseDocResponse;
			}

		}
	}

	@Override
	public ApplicationTypeWiseDoc findByApplicationTypeWiseDocId(Long applicationTypeWiseDocId)
			throws ApplicationTypeWiseDocException {

		final String method = "ApplicationTypeWiseDocServiceImpl : findByApplicationTypeWiseDocId(Long applicationTypeWiseDocId)";

		logger.info(method);

		final Optional<ApplicationTypeWiseDoc> applicationTypeWiseDocOptional = applicationTypeWiseDocRepository
				.findById(applicationTypeWiseDocId);

		final Response<ApplicationTypeWiseDoc> response = new Response<>();

		if (Objects.isNull(applicationTypeWiseDocOptional) || !applicationTypeWiseDocOptional.isPresent()) {
			logger.error(
					"applicationTypeWiseDocRepository.findById is returning Null when findByApplicationTypeWiseDocId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ApplicationTypeWiseDocException(response);
		} else {
			ApplicationTypeWiseDoc applicationTypeWiseDoc = applicationTypeWiseDocOptional.get();
			final List<ApplicationTypeWiseDoc> list = new ArrayList<>();
			list.add(applicationTypeWiseDoc);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return applicationTypeWiseDoc;
		}
	}

	@Override
	public List<ApplicationTypeWiseDoc> findAllApplicationTypeWiseDoc() throws ApplicationTypeWiseDocException {

		final String method = "ApplicationTypeWiseDocServiceImpl : findAllApplicationTypeWiseDoc()";
		logger.info(method);

		List<ApplicationTypeWiseDoc> applicationTypeWiseDoc = applicationTypeWiseDocRepository
				.findAllActiveApplicationTypeWiseDoc();

		final Response<ApplicationTypeWiseDoc> response = new Response<>();
		if (Objects.isNull(applicationTypeWiseDoc) || applicationTypeWiseDoc.isEmpty()) {
			logger.error(
					"applicationTypeWiseDocRepository.applicationTypeWiseDocRepository is returning Null when findAllApplicationTypeWiseDoc call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new ApplicationTypeWiseDocException(response);
		} else {
			logger.info("Response is returning successfully");
			return applicationTypeWiseDoc;
		}
	}

}
