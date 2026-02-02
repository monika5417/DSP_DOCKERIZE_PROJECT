package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ConnectionPraday;
import com.mpcz.deposit_scheme.backend.api.exception.ConnectionPradayException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConnectionPradayService;

@RestController
@RequestMapping("/api/connection_praday")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConnectionPradayController {

	@Autowired
	private ConnectionPradayService connectionPradayService;

	@PostMapping("/saveConnectionPraday")
	public ResponseEntity<Response> saveConnectionPraday(@RequestBody ConnectionPraday connectionPraday) {
		return connectionPradayService.saveConnectionPraday(connectionPraday);

	}

	@GetMapping("/getConnectionByIvrs/{ivrs}")
	public Response getConnectionByIvrs(@PathVariable String ivrs) {
		Response response = new Response();
		
		ConnectionPraday connectionByIvrs = connectionPradayService.getConnectionByIvrs(ivrs);
		if (connectionByIvrs == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("IVRS No. not found in database");
			return response;
		}
		response.setCode(HttpCode.OK);
		response.setMessage("Data Retrive Successfully");
		response.setList(Arrays.asList(connectionByIvrs));
		return response;

	}
	
	@GetMapping("/getConnectionDetailsByConsAppNo/{consAppNo}")
	public Response getConnectionDetailsByConsAppNo(@PathVariable String consAppNo)
	{
		Response response = new Response();
		
		ConnectionPraday connectionDetailsByConsAppNo = connectionPradayService.getConnectionDetailsByConsAppNo(consAppNo);
		if (connectionDetailsByConsAppNo == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application No. not found in database");
			return response;
		}
		response.setCode(HttpCode.OK);
		response.setMessage("Data Retrive Successfully");
		response.setList(Arrays.asList(connectionDetailsByConsAppNo));
		return response;
		
	}
	
	@PostMapping("/saveIvrsConnectionByJE")
	public ResponseEntity<Response> saveIvrsConnectionByJE(@RequestBody ConnectionPraday connectionPraday) throws ConnectionPradayException, ConsumerApplicationDetailException{
		if(Objects.isNull(connectionPraday)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Response(HttpCode.NULL_OBJECT,"Requested Payload is null",null));
		}else {
			ConnectionPraday savedIvrsDB = connectionPradayService.saveIvrsConnectionByJE(connectionPraday);
		return ResponseEntity.ok(savedIvrsDB==null?new Response(HttpCode.NULL_OBJECT,"Data Not Saved") : new Response(HttpCode.CREATED,"Data Saved Successfully",Arrays.asList(savedIvrsDB)));
		}
	
	}
}
