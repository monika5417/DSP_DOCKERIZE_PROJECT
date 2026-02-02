package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "SamagraList")
@Table(name = "SAMAGRA_LIST")
@Data
public class SamagraList {

	@Id
	@SequenceGenerator(name = "SAMAGRA_LIST_SEQ", sequenceName = "SAMAGRA_LIST_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMAGRA_LIST_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long Id;
	
	
	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	@Column(name = "GUARDIAN_NAME")
	private String guardianName;

	@Column(name = "SAMAGRA_ID")
	private String samagraId;

	@Column(name = "KHASRA_NO")
	private String khasraNo;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;
	
	@Column(name = "LOAD_REQUESTED")
	private String loadRequested;

	
	public String getLoadRequested() {
		return loadRequested;
	}

	public void setLoadRequested(String loadRequested) {
		this.loadRequested = loadRequested;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getSamagraId() {
		return samagraId;
	}

	public void setSamagraId(String samagraId) {
		this.samagraId = samagraId;
	}

	public String getKhasraNo() {
		return khasraNo;
	}

	public void setKhasraNo(String khasraNo) {
		this.khasraNo = khasraNo;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	
	
}
