package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import lombok.Data;

public @Data class ContractorData implements Serializable {

	private String user_id;
	private String authentication_id;
	private String user_class;
	private String user_zone;
	private String company_name;
	private String email;
	private String mobile;
	private String address;
}
