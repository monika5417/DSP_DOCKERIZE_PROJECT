package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDocumentException;

public interface ApplicationDocumentService {

	public ApplicationDocument saveApplicationDocument(ApplicationDocument applicationDocument)
			throws ApplicationDocumentException;

	public ApplicationDocument findByConsumerApplicationDetailId(final Long consumerApplicationDetailId)
			throws ApplicationDocumentException;

}
