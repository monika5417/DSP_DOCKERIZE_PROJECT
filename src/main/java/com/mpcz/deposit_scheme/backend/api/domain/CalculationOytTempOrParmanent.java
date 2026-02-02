package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.SequenceGenerators;
import javax.persistence.Table;

@Table(name="APPLICATION_PAYMENT_TYPE")
@Entity
public class CalculationOytTempOrParmanent {

	
	@Id
	@SequenceGenerator(name = "APPLICATION_PAYMENT_TYPE_SEQ",sequenceName = "APPLICATION_PAYMENT_TYPE_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "APPLICATION_PAYMENT_TYPE_SEQ")
	@Column(name="ID")
	private Long id;
	
	@Column(name="TARIF")
	private String tarif;
	
	@Column(name="PHASE")
	private String phase;
	
	@Column(name="LOAD")
	private String load;
	
	
	@Column(name="AMOUNT")
	private Long amount;


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




	


	public String getLoad() {
		return load;
	}


	public void setLoad(String load) {
		this.load = load;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	
	
	
	
}
