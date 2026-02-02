package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

@Data
public class BilldeskPaymentRetriveProcess {
	
	private String orderid;
	
	private String transactionid;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	


}
