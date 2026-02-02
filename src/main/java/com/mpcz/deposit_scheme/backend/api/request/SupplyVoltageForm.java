package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Supply Voltage Model Form.")
public  @Data class SupplyVoltageForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Supply Voltage Name must not be null", required = true)
	@NotEmpty
	private String supplyVoltageName;

	@ApiModelProperty(notes = "Supply Voltage Code must not be null", required = true)
	@NotEmpty
	private String supplyVoltageCode;

	public String getSupplyVoltageName() {
		return supplyVoltageName;
	}

	public void setSupplyVoltageName(String supplyVoltageName) {
		this.supplyVoltageName = supplyVoltageName;
	}

	public String getSupplyVoltageCode() {
		return supplyVoltageCode;
	}

	public void setSupplyVoltageCode(String supplyVoltageCode) {
		this.supplyVoltageCode = supplyVoltageCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
