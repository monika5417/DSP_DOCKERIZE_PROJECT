package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.UserLoginHistory;
import com.mpcz.deposit_scheme.backend.api.exception.LoginDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.UserLoginHistoryRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.UserLoginHistoryService;
import com.mpcz.deposit_scheme.backend.api.util.BrowserDetectionUtil;
import com.mpcz.deposit_scheme.backend.api.util.InternetProtocolAddressUtil;
import com.mpcz.deposit_scheme.backend.api.util.OpreatingSystemDetectionUtil;
import com.mpcz.deposit_scheme.backend.api.utility.DateTimeUtility;

@Service
public class UserLoginHistoryServiceImpl implements UserLoginHistoryService {

	Logger logger = LoggerFactory.getLogger(UserLoginHistoryServiceImpl.class);

	@Autowired
	UserLoginHistoryRepository userLoginHistoryRepository;

	@Override
	public Response<UserLoginHistory> save(UserLoginHistory userLoginHistory, HttpServletRequest request)
			throws LoginDetailException {

		final String method = "UserLoginHistoryServiceImpl : save()";

		logger.info(method);

		final Response<UserLoginHistory> response = new Response<>();

		if (Objects.isNull(userLoginHistory)) {
			logger.error("User Login Histroy object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new LoginDetailException(response);
		} else {

			userLoginHistory.setBrowserName(BrowserDetectionUtil.getBrowserName(request));
			userLoginHistory.setIpAddress(InternetProtocolAddressUtil.getIpAddress(request));
			userLoginHistory.setOsName(OpreatingSystemDetectionUtil.getOSName(request));

			Timestamp currentDate = DateTimeUtility.getCurrentTimeStamp();

			if (Objects.isNull(currentDate))
				return null;
			else {
				userLoginHistory.setLoginTime(currentDate);
			}
			UserLoginHistory userLoginHistoryResponse = new UserLoginHistory();

			userLoginHistoryResponse = userLoginHistoryRepository.save(userLoginHistory);

			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.LOGIN_RECORD_INSERTED_MESSAGE);
			final List<UserLoginHistory> list = new ArrayList<>();
			list.add(userLoginHistoryResponse);
			response.setList(list);

			return response;
		}
	}

}
