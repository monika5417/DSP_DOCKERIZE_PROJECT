package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "FEEDER")
@Data
public class Feeder extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FEEDER_SEQ", sequenceName = "FEEDER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEEDER_SEQ")
	@Column(name = "FEEDER_ID", columnDefinition = "serial", updatable = false)
	private Long feederId;

	@ManyToOne
	@JoinColumn(name = "SUBSTATION_ID", referencedColumnName = "SUBSTATION_ID")
	private Substation feederSubstation;


	@Column(name = "FEEDER_NAME")
	private String feederName;

	@Column(name = "FEEDER_CODE")
	private String feederCode;

	public Long getFeederId() {
		return feederId;
	}

	public void setFeederId(Long feederId) {
		this.feederId = feederId;
	}

	public Substation getFeederSubstation() {
		return feederSubstation;
	}

	public void setFeederSubstation(Substation feederSubstation) {
		this.feederSubstation = feederSubstation;
	}

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public String getFeederCode() {
		return feederCode;
	}

	public void setFeederCode(String feederCode) {
		this.feederCode = feederCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	





}