package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description="Distribution Center Model Form . ")
public @Data class DistributionCenterForm {

	@ApiModelProperty(notes="Subdivision Id must be Long and not null",required=true)
	@Positive
	private Long subDivisionId;

	@ApiModelProperty(notes="Distribution Center Name must be string and not null",required=true)
	@NotEmpty
	private String dcName;

	@ApiModelProperty(notes="Distribution Center Code must be string and not null",required=true)
	@NotEmpty
	private String dcCode;
}