package com.mpcz.deposit_scheme.backend.api.services;
import org.springframework.http.ResponseEntity;

import com.mpcz.deposit_scheme.backend.api.dto.MMKYParentChildDTO;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface MMKYParentChildService {

	ResponseEntity<?> submit(String applicationNumber, String mmkyParentChildData, double mmkyLoad, Long kvDistance,
			Long dtr, String cutPoint);
//	ResponseEntity<?> submit(String applicationNumber, String mmkyParentChildData);

	
}
