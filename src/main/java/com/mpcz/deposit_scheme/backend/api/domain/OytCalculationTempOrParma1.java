package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.SequenceGenerators;
import javax.persistence.Table;

@Table(name="APPLICATION_PAYMENT_TYPE_1")
@Entity
public class OytCalculationTempOrParma1 {

	
	@Id
	@SequenceGenerator(name = "APPLICATION_PAYMENT_TYPE_SEQ_1",sequenceName = "APPLICATION_PAYMENT_TYPE_SEQ_1",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "APPLICATION_PAYMENT_TYPE_SEQ_1")
	@Column(name="ID",columnDefinition = "serial_Number", updatable = false)
	private Long id;
	
	@Column(name="TARIF",columnDefinition = "tarif", updatable = false)
	private String tarif;
	
	@Column(name="PHASE",columnDefinition = "phase", updatable = false)
	private String phase;
	
	@Column(name="LOAD",columnDefinition = "phase", updatable = false)
	private Long load;
	
	@Column(name="AMOUNT",columnDefinition = "phase", updatable = false)
	private Long Amount;
	
	
	@Column(name="ADDITINAL_AMOUNT",columnDefinition = "phase", updatable = false)
	private Long addinalAmount;


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


	public Long getLoad() {
		return load;
	}


	public void setLoad(Long load) {
		this.load = load;
	}


	public Long getAmount() {
		return Amount;
	}


	public void setAmount(Long amount) {
		Amount = amount;
	}


	public Long getAddinalAmount() {
		return addinalAmount;
	}


	public void setAddinalAmount(Long addinalAmount) {
		this.addinalAmount = addinalAmount;
	}
	
	

	
	
	
	
	
	
	
}
