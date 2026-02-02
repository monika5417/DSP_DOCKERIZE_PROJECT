package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

@Data
public class TestDto {
	
	private String message;
	
	private Long circleId;
	
	//private String circleCode;
	
	private String circleName;
	
	private String gmName;
	
	private String dgmStcName;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCircleId() {
		return circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getGmName() {
		return gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}

	public String getDgmStcName() {
		return dgmStcName;
	}

	public void setDgmStcName(String dgmStcName) {
		this.dgmStcName = dgmStcName;
	}
	
	
	

}
