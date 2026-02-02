package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationTypeWiseDoc;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationTypeWiseDocException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.request.ApplicationTypeWiseDocForm;

public interface ApplicationTypeWiseDocService {

	public ApplicationTypeWiseDoc saveApplicationTypeWiseDoc(ApplicationTypeWiseDocForm applicationTypeWiseDocForm)
			throws ApplicationTypeWiseDocException, ApplicationTypeException, DocumentTypeException;

	public ApplicationTypeWiseDoc findByApplicationTypeWiseDocId(final Long applicationTypeWiseDocId)
			throws ApplicationTypeWiseDocException;

	public List<ApplicationTypeWiseDoc> findAllApplicationTypeWiseDoc() throws ApplicationTypeWiseDocException;
}
