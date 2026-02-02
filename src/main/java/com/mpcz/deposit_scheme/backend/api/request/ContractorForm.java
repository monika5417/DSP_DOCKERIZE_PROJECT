package com.mpcz.deposit_scheme.backend.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description="Circle Model Form . ")
public @Data class ContractorForm {


	
	private Long consumerAppId;


	private String bidOrderAt;
	
	private String bidAmount;


	private String contractorName;
}