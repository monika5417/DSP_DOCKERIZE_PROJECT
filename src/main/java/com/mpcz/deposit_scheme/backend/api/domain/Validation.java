package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VALIDATION")
public class Validation {

	
	@Id
	@Column(name="ID")
	private Long id;
	
	
	@Column(name="PENDENCY_NAME")
	private String pendencyName;
	
	@Column(name="DAYS")
	private Long days;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	

	public String getPendencyName() {
		return pendencyName;
	}

	public void setPendencyName(String pendencyName) {
		this.pendencyName = pendencyName;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}
	
	
	
	
	
	
}
