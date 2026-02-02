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
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginHistory;
import com.mpcz.deposit_scheme.backend.api.exception.LoginDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerLoginHistoryRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerLoginHistoryService;
import com.mpcz.deposit_scheme.backend.api.util.BrowserDetectionUtil;
import com.mpcz.deposit_scheme.backend.api.util.InternetProtocolAddressUtil;
import com.mpcz.deposit_scheme.backend.api.util.OpreatingSystemDetectionUtil;
import com.mpcz.deposit_scheme.backend.api.utility.DateTimeUtility;

@Service
public class ConsumerLoginHistoryServiceImpl implements ConsumerLoginHistoryService {

	Logger logger = LoggerFactory.getLogger(ConsumerLoginHistoryServiceImpl.class);

	@Autowired
	ConsumerLoginHistoryRepository consumerLoginHistoryRepository;

	@Override
	public Response<ConsumerLoginHistory> save(ConsumerLoginHistory consumerLoginHistory, HttpServletRequest request)
			throws LoginDetailException {

		final String method = "ConsumerLoginHistoryServiceImpl : save()";

		logger.info(method);

		final Response<ConsumerLoginHistory> response = new Response<>();

		if (Objects.isNull(consumerLoginHistory)) {
			logger.error("Consumer Login Histroy object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new LoginDetailException(response);
		} else {

			consumerLoginHistory.setBrowserName(BrowserDetectionUtil.getBrowserName(request));
			consumerLoginHistory.setIpAddress(InternetProtocolAddressUtil.getIpAddress(request));
			consumerLoginHistory.setOsName(OpreatingSystemDetectionUtil.getOSName(request));

			Timestamp currentDate = DateTimeUtility.getCurrentTimeStamp();

			if (Objects.isNull(currentDate))
				return null;
			else {
				consumerLoginHistory.setConsumerLoginTime(currentDate);
			}
			ConsumerLoginHistory consumerLoginHistoryResponse = new ConsumerLoginHistory();

			consumerLoginHistoryResponse = consumerLoginHistoryRepository.save(consumerLoginHistory);

			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.LOGIN_RECORD_INSERTED_MESSAGE);
			final List<ConsumerLoginHistory> list = new ArrayList<>();
			list.add(consumerLoginHistoryResponse);
			response.setList(list);

			return response;
		}
	}

}
