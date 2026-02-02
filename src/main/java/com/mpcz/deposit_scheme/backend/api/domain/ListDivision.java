package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LIST_DIVISION")
public class ListDivision {

	@Id
	@SequenceGenerator(name = "LISTT_DIVISION_SEQ", sequenceName = "LISTT_DIVISION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LISTT_DIVISION_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Integer id;
	
	
	@Column(name = "DIVISION_ID")
	private Integer divisionId;
	
	@Column(name = "DIVISION_NAME")
	private String divisionName;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "ACCESS_LEVEL")
	private String accessLevel;
	
	@Column(name = "CREATED_DATE")
	private String date;
	
	

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
