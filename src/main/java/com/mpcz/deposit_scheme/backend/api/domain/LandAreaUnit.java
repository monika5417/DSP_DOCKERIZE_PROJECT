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

@Entity(name = "LandAreaUnit")
@Table(name = "LAND_AREA_UNIT")
public @Data class LandAreaUnit extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LAND_AREA_UNIT_SEQ", sequenceName = "LAND_AREA_UNIT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LAND_AREA_UNIT_SEQ")
	@Column(name = "LAND_AREA_UNIT_ID", columnDefinition = "serial", updatable = false)
	private Long landAreaUnitId;

	
	@Column(name = "LAND_AREA_UNIT_NAME")
	private String landAreaUnitName;


	public Long getLandAreaUnitId() {
		return landAreaUnitId;
	}


	public void setLandAreaUnitId(Long landAreaUnitId) {
		this.landAreaUnitId = landAreaUnitId;
	}


	public String getLandAreaUnitName() {
		return landAreaUnitName;
	}


	public void setLandAreaUnitName(String landAreaUnitName) {
		this.landAreaUnitName = landAreaUnitName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
