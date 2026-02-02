package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Request RegionForm Form
 * 
 * @author Aditya Vyas
 * @version 1.0
 */
@ApiModel(description = "Region Model Form . ")
public @Data class RegionForm {

	@ApiModelProperty(notes = "Discom Id must be Long and not null", required = true)
//	@Positive
	private Long discomId;

	@ApiModelProperty(notes = "Region Name must be string and not null", required = true)
	@NotEmpty
	private String region;

	@ApiModelProperty(notes = "Region Name must be string and not null", required = true)
	@NotEmpty
	private String regionCode;
	
	

	public Long getDiscomId() {
		return discomId;
	}

	public void setDiscomId(Long discomId) {
		this.discomId = discomId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	
	
}
