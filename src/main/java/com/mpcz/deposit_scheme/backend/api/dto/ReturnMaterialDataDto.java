package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ReturnMaterialData;

public class ReturnMaterialDataDto {

	private List<ReturnMaterialData> returnMaterialData;

	
	public List<ReturnMaterialData> getReturnMaterialData() {
		return returnMaterialData;
	}

	public void setReturnMaterialData(List<ReturnMaterialData> returnMaterialData) {
		this.returnMaterialData = returnMaterialData;
	}
	
	
}
