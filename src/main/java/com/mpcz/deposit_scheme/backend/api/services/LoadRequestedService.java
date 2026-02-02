package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.LoadRequested;
import com.mpcz.deposit_scheme.backend.api.exception.LoadRequestedException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface LoadRequestedService {

	List<LoadRequested> findAllLoadRequested()throws LoadRequestedException;
	LoadRequested findById(long loadRequestedId) throws  LoadRequestedException;
	//List<LoadRequested> findAllLoadRequested()throws LoadRequestedException;
	

}
