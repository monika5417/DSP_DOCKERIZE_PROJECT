package com.mpcz.deposit_scheme.backend.api.request;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Substation Model Form . ")
public @Data class SubstationForm {

	@ApiModelProperty(notes="Distribution Center Id must be Long and not null",required=true)
	private Long dcId;

	@ApiModelProperty(notes="Substation Name must be string and not null",required=true)
	@NotEmpty
	private String subStationName;

	@ApiModelProperty(notes="Substation Code must be string and not null",required=true)
	@NotEmpty
	private String subStationCode;

	public Long getDcId() {
		return dcId;
	}

	public void setDcId(Long dcId) {
		this.dcId = dcId;
	}

	public String getSubStationName() {
		return subStationName;
	}

	public void setSubStationName(String subStationName) {
		this.subStationName = subStationName;
	}

	public String getSubStationCode() {
		return subStationCode;
	}

	public void setSubStationCode(String subStationCode) {
		this.subStationCode = subStationCode;
	}
	
	
}