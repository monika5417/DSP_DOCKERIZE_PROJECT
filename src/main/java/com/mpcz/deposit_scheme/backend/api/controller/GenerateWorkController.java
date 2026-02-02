package com.mpcz.deposit_scheme.backend.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.dto.GenerateWorkDto;
import com.mpcz.deposit_scheme.backend.api.response.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Generate Work Order Controller", description = "Rest api for Generate Work Order Controller.")
@RestController
@RequestMapping(RestApiUrl.WORK_ORDEER_GENERATOR_API)

public class GenerateWorkController {
	
	Logger logger = LoggerFactory.getLogger(ConsumerApplicationDcCorrectionController.class);

//	@ApiOperation(value = "Voucher generate ", response = VaucherMaster.class)
	@GetMapping("/generateWorkOderNo")
	public ResponseEntity<Response> generateVoucher() {

		Response response = new Response();
		GenerateWorkDto dto = new GenerateWorkDto();
		String workOder="WO_"+LocalDate.now().toString() +  LocalTime.now().getHour()+LocalTime.now().getMinute()+LocalTime.now().getSecond();
		workOder=workOder.replaceAll("\\-|\\:|\\.", "");
		dto.setWorkOderNo(workOder);
		dto.setWorkOderDate(LocalDateTime.now().toString());
		response.setCode("200");
		response.setMessage("Work Oder No generated successfully");
		response.setList(Arrays.asList(dto));
		return ResponseEntity.ok().body(response);

	}

}
