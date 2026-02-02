package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.dto.ConsumerSupervisionResponseDto;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;

public interface ConsumerSupervisionService {

	List<ConsumerSupervisionResponseDto> findConsumerApplicationEstimate()
			throws DemandDetailException, SchemeTypeException;

}
