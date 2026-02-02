package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * Request DivisionForm Form
 * 
 * @author Ashish Tiwari
 * @version 1.0
 */
@ApiModel(description = "Division Model Form . ")
public @Data class DivisionForm {

	@ApiModelProperty(notes = "Circle Id must be Long and not null", required = true)
	@Positive
	private Long circleId;

	@ApiModelProperty(notes = "Division Name must be string and not null", required = true)
	@NotEmpty
	private String divisionName;
	
	@ApiModelProperty(notes="Division Code must be string and not null",required=true)
	@NotEmpty
	private String divisionCode;
	
}