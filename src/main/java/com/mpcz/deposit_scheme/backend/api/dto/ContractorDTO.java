package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import lombok.Data;

public @Data class ContractorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String authenticationId;

	private String userZone;

	private String companyName;

	private String email;

	private String mobile;

	private String address;
	
}
