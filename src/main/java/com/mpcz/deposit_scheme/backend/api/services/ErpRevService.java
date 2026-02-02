package com.mpcz.deposit_scheme.backend.api.services;

import org.springframework.http.ResponseEntity;

import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;

public interface ErpRevService {

	ErpRev save(String erpNo, String applicationNo,Long value);

	ResponseEntity<?> getConsumerApplication(String consumerApp);

	public ErpRev Dynamic(String erpNo, String applicationNo, Long value);
}
