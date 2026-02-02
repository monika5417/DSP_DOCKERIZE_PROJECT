package com.mpcz.deposit_scheme.backend.api.domain;

import lombok.Data;

public @Data class Contractor extends Auditable<Long> {

	public String authenticationId;
	public String userZone;
	public String companyName;
	public String email;
	public String mobile;
	public String address;

}
