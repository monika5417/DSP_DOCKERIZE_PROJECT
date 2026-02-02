package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import lombok.Data;

public @Data class ConsumerApplicationDetailsFilterDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String userLoginId;
	String consumerLoginId;
	
	Long discomId = 0l;
	Long regionId = 0l;
	Long circleId = 0l;
	Long divisionId = 0l;
	Long subDivisionId = 0l;
	Long dcId = 0l;
	
	Long consumerId=0l;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getConsumerLoginId() {
		return consumerLoginId;
	}

	public void setConsumerLoginId(String consumerLoginId) {
		this.consumerLoginId = consumerLoginId;
	}

	public Long getDiscomId() {
		return discomId;
	}

	public void setDiscomId(Long discomId) {
		this.discomId = discomId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getCircleId() {
		return circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public Long getSubDivisionId() {
		return subDivisionId;
	}

	public void setSubDivisionId(Long subDivisionId) {
		this.subDivisionId = subDivisionId;
	}

	public Long getDcId() {
		return dcId;
	}

	public void setDcId(Long dcId) {
		this.dcId = dcId;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
