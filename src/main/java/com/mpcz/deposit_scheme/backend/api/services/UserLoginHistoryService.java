package com.mpcz.deposit_scheme.backend.api.services;

import javax.servlet.http.HttpServletRequest;

import com.mpcz.deposit_scheme.backend.api.domain.UserLoginHistory;
import com.mpcz.deposit_scheme.backend.api.exception.LoginDetailException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface UserLoginHistoryService {

	public Response<UserLoginHistory> save(UserLoginHistory userLoginHistory, HttpServletRequest request)
			throws LoginDetailException;
}
