package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mpcz.deposit_scheme.backend.api.builddesk.CustomerBillDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface BillDeskPaymentService {
	
	//public Response<Object> prePaymentProcessingBillDesk(CustomerBillDTO billDto, List<Object> list , Response<String> response,HttpServletRequest request) throws Exception;

	Response<Object> prePaymentProcessingBillDesk(CustomerBillDTO billDto, List<Object> list, Response<Object> response,
			HttpServletRequest request) throws Exception;

	

}
