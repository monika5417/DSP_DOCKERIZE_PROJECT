package com.mpcz.deposit_scheme.backend.api.services;

import org.springframework.http.ResponseEntity;

import com.mpcz.deposit_scheme.backend.api.domain.ConnectionPraday;
import com.mpcz.deposit_scheme.backend.api.exception.ConnectionPradayException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface ConnectionPradayService {

	ResponseEntity<Response> saveConnectionPraday(ConnectionPraday connectionPraday);

	ConnectionPraday getConnectionByIvrs(String ivrs);

	ConnectionPraday getConnectionDetailsByConsAppNo(String consAppNo);

	ConnectionPraday saveIvrsConnectionByJE(ConnectionPraday connectionPraday) throws ConnectionPradayException, ConsumerApplicationDetailException;

	
}
