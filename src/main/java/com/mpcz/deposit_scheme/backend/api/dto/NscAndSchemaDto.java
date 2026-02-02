package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

@Data
public class NscAndSchemaDto {
	
	private Long nscId;
	
	private  Long schemaTypeId;

	public Long getNscId() {
		return nscId;
	}

	public void setNscId(Long nscId) {
		this.nscId = nscId;
	}

	public Long getSchemaTypeId() {
		return schemaTypeId;
	}

	public void setSchemaTypeId(Long schemaTypeId) {
		this.schemaTypeId = schemaTypeId;
	}
	
	

}
