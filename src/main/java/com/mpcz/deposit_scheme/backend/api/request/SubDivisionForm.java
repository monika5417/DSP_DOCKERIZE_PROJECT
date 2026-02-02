package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Request SubDivisionForm Form
 * 
 * @author Ashish Tiwari
 * @version 1.0
 */
@ApiModel(description = "SubDivision Model Form . ")
public @Data class SubDivisionForm {

	private Long divisionId;

	@ApiModelProperty(notes = "Sub division name must be string and not null", required = true)
	@NotEmpty
	private String subDivisionName;
	
	@ApiModelProperty(notes = "Sub division code must be string and not null", required = true)
	@NotEmpty
	private String subDivisionCode;
}
