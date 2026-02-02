package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mpcz.deposit_scheme.backend.api.dto.DgmStcRemarkDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.domain.DgmStcRemark;

public interface DgmStcRemarkService {

	ResponseEntity<?> saveDgmStcRemark(DgmStcRemarkDTO dgmStcRemarkDTO);

	ResponseEntity<Response> getAllDgmStcRemark(String consumerAppNo);

}
