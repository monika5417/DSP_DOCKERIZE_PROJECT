package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity(name = "LoadRequested")
@Table(name = "LOAD_REQUESTED")
public @Data class LoadRequested extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOAD_REQUESTED_SEQ", sequenceName = "LOAD_REQUESTED_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAD_REQUESTED_SEQ")
	@Column(name = "LOAD_REQUESTED_ID", columnDefinition = "serial", updatable = false)
	private Long loadRequestedId;

	
	@Column(name = "LOAD_UNIT_NAME")
	private String loadRequestedName;


	public Long getLoadRequestedId() {
		return loadRequestedId;
	}


	public void setLoadRequestedId(Long loadRequestedId) {
		this.loadRequestedId = loadRequestedId;
	}


	public String getLoadRequestedName() {
		return loadRequestedName;
	}


	public void setLoadRequestedName(String loadRequestedName) {
		this.loadRequestedName = loadRequestedName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


	
	
	
}