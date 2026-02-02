package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

@Data
public class GenerateWorkDto {
	
	private String workOderNo;
	
	private String workOderDate;

	public String getWorkOderNo() {
		return workOderNo;
	}

	public void setWorkOderNo(String workOderNo) {
		this.workOderNo = workOderNo;
	}

	public String getWorkOderDate() {
		return workOderDate;
	}

	public void setWorkOderDate(String workOderDate) {
		this.workOderDate = workOderDate;
	}
	
	

}
