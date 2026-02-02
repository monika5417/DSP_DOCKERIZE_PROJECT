package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDocumentException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationDocumentService;

@Service
public class ApplicationDocumentServiceImpl implements ApplicationDocumentService {

	Logger logger = LoggerFactory.getLogger(ApplicationDocumentServiceImpl.class);

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Override
	public ApplicationDocument saveApplicationDocument(ApplicationDocument applicationDocument)
			throws ApplicationDocumentException {

		final String method = "ApplicationDocumentServiceImpl : saveApplicationDocument()";

		logger.info(method);

		Response<ApplicationDocument> response = new Response<>();

		ApplicationDocument applicationDocumentResponse = null;

		if (Objects.isNull(applicationDocument)) {
			logger.error("Application Document object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ApplicationDocumentException(response);
		} else {

			applicationDocumentResponse = applicationDocumentRepository.save(applicationDocument);

			if (Objects.isNull(applicationDocumentResponse)) {
				logger.error("applicationDocumentRepository.save(applicationDocument) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ApplicationDocumentException(response);
			} else {
				return applicationDocumentResponse;
			}

		}
	}

	@Override
	public ApplicationDocument findByConsumerApplicationDetailId(Long consumerApplicationDetailId)
			throws ApplicationDocumentException {

		final String method = "ApplicationDocumentServiceImpl : findByConsumerApplicationDetailId(Long consumerApplicationDetailId)";
		logger.info(method);

		final Response<ApplicationDocument> response = new Response<>();

		ApplicationDocument applicationDocumentDb = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationDetailId);

		if (Objects.isNull(applicationDocumentDb)) {
			logger.error(
					"applicationDocumentRepository.findByConsumerApplicationDetailId is returning Null when findByConsumerApplicationDetailId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Document not found for this application id");
			throw new ApplicationDocumentException(response);
		} else {
			logger.info("Response is returning successfully");
			return applicationDocumentDb;
		}
	}
}
