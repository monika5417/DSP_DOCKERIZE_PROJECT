package com.mpcz.deposit_scheme.backend.api.services;

import javax.servlet.http.HttpServletRequest;

import com.mpcz.deposit_scheme.backend.api.dto.SSPDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.response.SignUpResponse;

public interface SSPService {

	SignUpResponse sspSignUp(SSPDto sspDto, HttpServletRequest request) throws ConsumerException, DocumentTypeException, DistributionCenterException, DistrictException;

	String sendDataToSspAfterWorkOrder(String consumerApplicationNo, Long applicationStatusId) throws ConsumerApplicationDetailException;

}
