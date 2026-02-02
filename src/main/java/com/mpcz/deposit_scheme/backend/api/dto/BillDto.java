package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.mpcz.deposit_scheme.backend.api.domain.Additional_info;
import com.mpcz.deposit_scheme.backend.api.domain.Device;

import lombok.Data;

public @Data class BillDto implements Serializable{
	
	private String orderid;
	private String mercid;
	private Date order_date;
	private Double amount;
	private Double currency;
	private String ru;
	private String itemcode;
	private Device device;
	private Additional_info additional_info;
	
	
	

}
