package com.mpcz.deposit_scheme.backend.api.services;

import javax.servlet.http.HttpServletRequest;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginHistory;
import com.mpcz.deposit_scheme.backend.api.exception.LoginDetailException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface ConsumerLoginHistoryService {

	public Response<ConsumerLoginHistory> save(ConsumerLoginHistory consumerLoginHistory, HttpServletRequest request)
			throws LoginDetailException;
}
