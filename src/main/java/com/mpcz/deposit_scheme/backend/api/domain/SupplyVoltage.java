package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "SupplyVoltage")
@Table(name = "SUPPLY_VOLTAGE")
public @Data class SupplyVoltage extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SUPPLY_VOLTAGE_SEQ", sequenceName = "SUPPLY_VOLTAGE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUPPLY_VOLTAGE_SEQ")
	@Column(name = "SUPPLY_VOLTAGE_ID", columnDefinition = "serial", updatable = false)
	private Long supplyVoltageId;

	@Column(name = "SUPPLY_VOLTAGE_NAME")
	private String supplyVoltageName;

	@Column(name = "SUPPLY_VOLTAGE_CODE")
	private String supplyVoltageCode;

	public Long getSupplyVoltageId() {
		return supplyVoltageId;
	}

	public void setSupplyVoltageId(Long supplyVoltageId) {
		this.supplyVoltageId = supplyVoltageId;
	}

	public String getSupplyVoltageName() {
		return supplyVoltageName;
	}

	public void setSupplyVoltageName(String supplyVoltageName) {
		this.supplyVoltageName = supplyVoltageName;
	}

	public String getSupplyVoltageCode() {
		return supplyVoltageCode;
	}

	public void setSupplyVoltageCode(String supplyVoltageCode) {
		this.supplyVoltageCode = supplyVoltageCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
