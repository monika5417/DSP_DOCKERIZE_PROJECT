package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
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
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerType;
import com.mpcz.deposit_scheme.backend.api.domain.DocumentType;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.DocumentTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.DocumentTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DocumentTypeService;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

	Logger logger = LoggerFactory.getLogger(ConsumerTypeServiceImpl.class);

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	@Override
	public DocumentType saveDocumentType(DocumentTypeForm documentTypeForm) throws DocumentTypeException {

		final String method = "DocumentTypeServiceImpl : saveDocumentType()";
		logger.info(method);

		Response<ConsumerType> response = new Response<>();

		DocumentType documentTypeResponse = null;

		if (Objects.isNull(documentTypeForm)) {
			logger.error("Document Type object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DocumentTypeException(response);
		} else {

			DocumentType documentType = new DocumentType();
			documentType.setDocumentTypeName(documentTypeForm.getDocumentTypeName());

			documentTypeResponse = documentTypeRepository.save(documentType);

			if (Objects.isNull(documentTypeResponse)) {
				logger.error("repository.save(documentType) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new DocumentTypeException(response);
			} else {
				return documentTypeResponse;
			}

		}
	}

	public Response<DocumentType> update(DocumentType documentType) throws DocumentTypeException {
		final String method = "DocumentServiceImpl : update()";
		logger.info(method);

		final Response<DocumentType> response = new Response<>();

		if (Objects.isNull(documentType)) {
			logger.error("DocumentType object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DocumentTypeException(response);
		} else {

			DocumentType documentResponse = documentTypeRepository.save(documentType);

			if (Objects.isNull(documentResponse)) {
				logger.error("repository.save(document1) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_UPDATION_FAILED_MESSAGE);
				throw new DocumentTypeException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.UPDATED);
				response.setMessage(ResponseMessage.RECORD_UPDATED_MESSAGE);
				final List<DocumentType> list = new ArrayList<>();

				list.add(documentResponse);
				response.setList(list);
				return response;
			}
		}
	}

	public DocumentType findDepartmentById(long documentId) throws DocumentTypeException {
		final String method = "DocumentServiceImpl : findDocumentById()";
		logger.info(method);
		final Response<DocumentType> response = new Response<>();
		Optional<DocumentType> optional = documentTypeRepository.findById(documentId);
		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findDepartmentById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DOCUMENT_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DocumentTypeException(response);
		} else {
			DocumentType documentType = optional.get();
			logger.info("Response is returning successfully");
			return documentType;
		}
	}

	public Response<DocumentType> findById(long documentId) throws DocumentTypeException {
		final String method = "DocumentServiceImpl : findById()";
		logger.info(method);
		final Response<DocumentType> response = new Response<>();
		java.util.Optional<DocumentType> optional = documentTypeRepository.findById(documentId);

		if (Objects.isNull(optional) || !optional.isPresent()) {
			logger.error("repository.findById is returning Null when findById call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DOCUMENT_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DocumentTypeException(response);
		} else {
			DocumentType document = optional.get();
			final List<DocumentType> list = new ArrayList<>();
			list.add(document);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return response;
		}
	}

	@Override
	public Response<List<DocumentType>> findAll() throws DocumentTypeException {
		final String method = "DocumentServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<DocumentType> documents = documentTypeRepository.findAll();
		if (Objects.isNull(documents) || documents.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.DOCUMENT_RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new DocumentTypeException(response);
		} else {

			documents.forEach(d -> d.setDocumentTypeName(d.getDocumentTypeName().trim()));
			documents.sort(Comparator.comparing(a -> a.getDocumentTypeName(),
					Comparator.nullsLast(Comparator.naturalOrder())));

			response.setList(documents);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}

	}

	@Override
	public Response<DocumentType> delete(long documentId) throws DocumentTypeException {
		final String method = "DocumentServiceImpl : delete()";
		logger.info(method);
		final Response<DocumentType> response = new Response<>();
		DocumentType doument = this.findDepartmentById(documentId);
		// get Department update or create info(TimeStamp)
		if (Objects.isNull(doument)) {
			logger.error("doument object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new DocumentTypeException(response);
		} else {

			documentTypeRepository.delete(doument);

			logger.info("Response is returning successfully");
			response.setCode(ResponseCode.OK);
			response.setMessage(ResponseMessage.RECORD_DELETION_MESSAGE);
			return response;

		}
	}

	@Override
	public List<DocumentType> findAllDocumentType() throws DocumentTypeException {

		final String method = "DocumentTypeServiceImpl : findAllDocumentType()";
		logger.info(method);

		List<DocumentType> documentType = documentTypeRepository.findAll();

		final Response<SchemeType> response = new Response<>();
		if (Objects.isNull(documentType) || documentType.isEmpty()) {
			logger.error("documentTypeRepository.findAll is returning Null when findAllDocumentType call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DocumentTypeException(response);
		} else {
			logger.info("Response is returning successfully");
			return documentType;
		}

	}

	@Override
	public DocumentType findDocumentTypeById(long documentId) throws DocumentTypeException {

		final Response<SchemeType> response = new Response<>();

		DocumentType DocumentTypedb = null;

		try {
			Optional<DocumentType> optional = documentTypeRepository.findById(documentId);
			if (!Objects.isNull(optional) && optional.isPresent()) {
				DocumentTypedb = optional.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (DocumentTypedb == null) {
			logger.error("documentTypeRepository.findAll is returning Null when findAllDocumentType call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DocumentTypeException(response);
		}

		return DocumentTypedb;

	}

}
