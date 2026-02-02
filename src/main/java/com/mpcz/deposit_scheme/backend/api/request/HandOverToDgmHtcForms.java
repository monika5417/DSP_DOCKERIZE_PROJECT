package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "HandOverToDgmHtcForms Form.")
public @Data class HandOverToDgmHtcForms implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dgmHtcId;
	private String consumerAppNo;
	public String getDgmHtcId() {
		return dgmHtcId;
	}
	public void setDgmHtcId(String dgmHtcId) {
		this.dgmHtcId = dgmHtcId;
	}
	public String getConsumerAppNo() {
		return consumerAppNo;
	}
	public void setConsumerAppNo(String consumerAppNo) {
		this.consumerAppNo = consumerAppNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}