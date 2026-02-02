package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWorkException;
import com.mpcz.deposit_scheme.backend.api.request.NatureOfWorkForm;

public interface NatureOfWorkService {
	

	public NatureOfWork saveNatureOfWork(NatureOfWorkForm natureOfWork) throws NatureOfWorkException;

	List<NatureOfWork> findAllNatureOfWorkDocumentType() throws NatureOfWorkException;

	public NatureOfWork findByNatureOfWorkId(Long id)throws NatureOfWorkException;



}
