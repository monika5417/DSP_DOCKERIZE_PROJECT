package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerType;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerTypeException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerTypeForm;

public interface ConsumerTypeService {

	public ConsumerType saveConsumerType(ConsumerTypeForm consumerTypeForm) throws ConsumerTypeException;

	public ConsumerType findByConsumerTypeId(Long Id) throws ConsumerTypeException;

	
}