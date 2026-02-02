package com.mpcz.deposit_scheme.backend.api.request;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Feeder Model Form . ")
public @Data class FeederForm {

@ApiModelProperty(notes="Substation Id must be Long and not null",required=true)
@Positive
private Long substationId;

@ApiModelProperty(notes="Feeder Name must be string and not null",required=true)
@NotEmpty
private String feederName;

@ApiModelProperty(notes="Feeder Code must be string and not null",required=true)
@NotEmpty
private String feederCode;

public Long getSubstationId() {
	return substationId;
}

public void setSubstationId(Long substationId) {
	this.substationId = substationId;
}

public String getFeederName() {
	return feederName;
}

public void setFeederName(String feederName) {
	this.feederName = feederName;
}

public String getFeederCode() {
	return feederCode;
}

public void setFeederCode(String feederCode) {
	this.feederCode = feederCode;
}


}
