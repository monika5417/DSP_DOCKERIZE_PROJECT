package com.mpcz.deposit_scheme.backend.api.services;

import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;

public interface UploadService {

	public Upload save(final Upload upload);

	public Upload findUpload(Long uploadId);

	Upload uploadFile(MultipartFile uploadDoc, String fileType) throws DocumentTypeException;
}
