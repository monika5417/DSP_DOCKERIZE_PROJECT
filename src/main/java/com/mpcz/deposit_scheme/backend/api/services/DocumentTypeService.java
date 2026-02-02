package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import javax.validation.Valid;

import com.mpcz.deposit_scheme.backend.api.domain.DocumentType;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.request.DocumentTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface DocumentTypeService {

	DocumentType saveDocumentType(@Valid DocumentTypeForm documentTypeForm) throws DocumentTypeException;

	DocumentType findDepartmentById(long id) throws DocumentTypeException;

	Response<DocumentType> update(DocumentType documentType) throws DocumentTypeException;

	public Response<DocumentType> findById(final long documentId) throws DocumentTypeException;

	public Response<DocumentType> delete(final long departmentId) throws DocumentTypeException;

	public Response<List<DocumentType>> findAll() throws DocumentTypeException;

//	sandeep starts
	public List<DocumentType> findAllDocumentType() throws DocumentTypeException;
//	sandeep ends

	DocumentType findDocumentTypeById(long documentId) throws DocumentTypeException;

}
