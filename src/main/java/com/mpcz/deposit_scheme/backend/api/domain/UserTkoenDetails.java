package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TOKEN_DETAILS")
public class UserTkoenDetails {


	@Id
	@SequenceGenerator(name = "USER_TOKEN_DETAILS_SEQ", sequenceName = "USER_TOKEN_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUPPLY_VOLTAGE_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
	@Column(name = "USER_ID")
	private String userId;
	
	
	@Column(name = "TOKEN")
	private String token;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
	
}
