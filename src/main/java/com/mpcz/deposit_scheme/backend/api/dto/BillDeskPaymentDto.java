package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BillDeskPaymentDto implements Serializable{

		private static final long serialVersionUID = 1L;
		private String terminal_state;
		private String orderid;
		private String transaction_response;
		private String return_url;
		
}
