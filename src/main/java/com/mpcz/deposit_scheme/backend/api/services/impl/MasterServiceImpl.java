package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.AccessLevelEnum;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.exception.MasterException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.MasterService;
 

/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 *
 */
@Service
public class MasterServiceImpl implements MasterService {
	Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);

 
	@Override
	public Response<Map<Long, String>> findAllAccessLevel() throws MasterException {

		final String method = "MasterServiceImpl : findAllAccessLevel()";
		logger.info(method);
		Response response = new Response<>();
		Map<Long, String> accessLevels = AccessLevelEnum.getAccessLevels();
		if (Objects.isNull(accessLevels) || accessLevels.isEmpty()) {
			logger.error("AccessLevelEnum.getaccessLevels is returning Null when findAllAccessLevel call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_FAILED_MESSAGE);
			throw new MasterException(response);
		} else {
			response.setMap(accessLevels);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return response;
		}
	}

	 

}
