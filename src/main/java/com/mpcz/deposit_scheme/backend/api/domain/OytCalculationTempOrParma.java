package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.SequenceGenerators;
import javax.persistence.Table;

@Table(name="OYT_CAL_TEM_OR_PARMA")
@Entity
public class OytCalculationTempOrParma {

	
	@Id
	@SequenceGenerator(name = "OYT_CAL_TEM_OR_PARMA_SEQ",sequenceName = "OYT_CAL_TEM_OR_PARMA_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "OYT_CAL_TEM_OR_PARMA_SEQ")
	@Column(name="ID",columnDefinition = "serial_Number", updatable = false)
	private Long id;
	
	@Column(name="TARIF",columnDefinition = "tarif", updatable = false)
	private String tarif;
	
	@Column(name="PHASE",columnDefinition = "phase", updatable = false)
	private String phase;
	
	@Column(name="AMOUNT_3_HPLOAD",columnDefinition = "phase", updatable = false)
	private Long Amount_3_hpLoad;
	
	
	@Column(name="AMOUNT_5_HPLOAD",columnDefinition = "phase", updatable = false)
	private Long Amount_5_hpLoad;
	
	@Column(name="AMOUNT_8_HPLOAD",columnDefinition = "phase", updatable = false)
	private Long Amount_8_hpLoad;
	
	@Column(name="AMOUNT_10_HPLOAD",columnDefinition = "phase", updatable = false)
	private Long Amount_10_hpLoad;
	
	@Column(name="AMOUNT_13_HPLOAD",columnDefinition = "phase", updatable = false)
	private Long Amount_13_hpLoad;
	
	@Column(name="AMOUNT_15_HPLOAD",columnDefinition = "phase", updatable = false)
	private Long Amount_15_hpLoad;
	
	@Column(name="AMOUNT_20_HPLOAD",columnDefinition = "phase", updatable = false)
	private Long Amount_20_hpLoad;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTarif() {
		return tarif;
	}

	public void setTarif(String tarif) {
		this.tarif = tarif;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Long getAmount_3_hpLoad() {
		return Amount_3_hpLoad;
	}

	public void setAmount_3_hpLoad(Long amount_3_hpLoad) {
		Amount_3_hpLoad = amount_3_hpLoad;
	}

	public Long getAmount_5_hpLoad() {
		return Amount_5_hpLoad;
	}

	public void setAmount_5_hpLoad(Long amount_5_hpLoad) {
		Amount_5_hpLoad = amount_5_hpLoad;
	}

	public Long getAmount_8_hpLoad() {
		return Amount_8_hpLoad;
	}

	public void setAmount_8_hpLoad(Long amount_8_hpLoad) {
		Amount_8_hpLoad = amount_8_hpLoad;
	}

	public Long getAmount_10_hpLoad() {
		return Amount_10_hpLoad;
	}

	public void setAmount_10_hpLoad(Long amount_10_hpLoad) {
		Amount_10_hpLoad = amount_10_hpLoad;
	}

	public Long getAmount_13_hpLoad() {
		return Amount_13_hpLoad;
	}

	public void setAmount_13_hpLoad(Long amount_13_hpLoad) {
		Amount_13_hpLoad = amount_13_hpLoad;
	}

	public Long getAmount_15_hpLoad() {
		return Amount_15_hpLoad;
	}

	public void setAmount_15_hpLoad(Long amount_15_hpLoad) {
		Amount_15_hpLoad = amount_15_hpLoad;
	}

	public Long getAmount_20_hpLoad() {
		return Amount_20_hpLoad;
	}

	public void setAmount_20_hpLoad(Long amount_20_hpLoad) {
		Amount_20_hpLoad = amount_20_hpLoad;
	}
	
	
	
	
	
}
