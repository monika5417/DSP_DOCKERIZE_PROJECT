package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

public @Data class AccessLevelDTO {

	Long accessLevelId;
	String accessLevelName;

	public AccessLevelDTO() {

	}

	public AccessLevelDTO(Long accessLevelId, String accessLevelName) {
		this.accessLevelId = accessLevelId;
		this.accessLevelName = accessLevelName;

	}

}
