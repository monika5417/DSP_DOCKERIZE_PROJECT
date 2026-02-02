package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;
@Data

public class EstimateDependOnthisCheckBoxDto {
	
	private String applicatonNumber;
	private String selecDtrOrPtr;
	public String getApplicatonNumber() {
		return applicatonNumber;
	}
	public void setApplicatonNumber(String applicatonNumber) {
		this.applicatonNumber = applicatonNumber;
	}
	public String getSelecDtrOrPtr() {
		return selecDtrOrPtr;
	}
	public void setSelecDtrOrPtr(String selecDtrOrPtr) {
		this.selecDtrOrPtr = selecDtrOrPtr;
	}
	
	

}
