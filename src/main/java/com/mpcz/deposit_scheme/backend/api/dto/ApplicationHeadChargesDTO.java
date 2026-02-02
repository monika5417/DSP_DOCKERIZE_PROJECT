package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class ApplicationHeadChargesDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@NotNull
	Long consumerApplicationId;

	@NotNull
	Long paymentTypeId;

	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}

	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
