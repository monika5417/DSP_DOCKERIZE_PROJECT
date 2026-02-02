package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;

import lombok.Data;

public @Data class UserStatusDTO implements Serializable {

	private Long adUserId;
	private Boolean isActive;
	private Boolean isAccountNonLocked;
	private Boolean isAccountNonExpired;
	
	
	public Long getAdUserId() {
		return adUserId;
	}
	public void setAdUserId(Long adUserId) {
		this.adUserId = adUserId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsAccountNonLocked() {
		return isAccountNonLocked;
	}
	public void setIsAccountNonLocked(Boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}
	public Boolean getIsAccountNonExpired() {
		return isAccountNonExpired;
	}
	public void setIsAccountNonExpired(Boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}
	
	
	
	


}
