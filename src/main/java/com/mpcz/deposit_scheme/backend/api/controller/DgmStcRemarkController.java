package com.mpcz.deposit_scheme.backend.api.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.dto.DgmStcRemarkDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DgmStcRemarkService;

@RestController
@RequestMapping("/api/dgm_stc_remark")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DgmStcRemarkController {
	
	@Autowired
	private DgmStcRemarkService dgmStcRemarkService;
	
	
	@PostMapping("/saveDgmStcRemark")
	public ResponseEntity<?> saveDgmStcRemark(@RequestBody DgmStcRemarkDTO dgmStcRemarkDTO) {
		return dgmStcRemarkService.saveDgmStcRemark(dgmStcRemarkDTO);
	
	}
	
	@GetMapping("/getAllDgmStcRemark/{consumerAppNo}")
	public ResponseEntity<?> getAllDgmStcRemark(@PathVariable String consumerAppNo){
		return dgmStcRemarkService.getAllDgmStcRemark(consumerAppNo);
		
	}
		
		
	}
	


