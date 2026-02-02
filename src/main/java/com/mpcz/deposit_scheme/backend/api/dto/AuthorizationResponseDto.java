package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

@Data
public class AuthorizationResponseDto {
	
	
	private String authorizationToken;
	private String bdorderid;
	private String mercid;
	public String getAuthorizationToken() {
		return authorizationToken;
	}
	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}
	public String getBdorderid() {
		return bdorderid;
	}
	public void setBdorderid(String bdorderid) {
		this.bdorderid = bdorderid;
	}
	public String getMercid() {
		return mercid;
	}
	public void setMercid(String mercid) {
		this.mercid = mercid;
	}

	

}
