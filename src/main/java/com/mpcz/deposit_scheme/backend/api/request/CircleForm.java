package com.mpcz.deposit_scheme.backend.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description="Circle Model Form . ")
public @Data class CircleForm {

	@ApiModelProperty(notes="Region Id must be Long and not null",required=true)
	@Positive
	private Long regionId;

	@ApiModelProperty(notes="Circle Name must be string and not null",required=true)
	@NotEmpty
	private String circleName;

	@ApiModelProperty(notes="Circle Code must be string and not null",required=true)
	@NotEmpty
	private String circleCode;
}

