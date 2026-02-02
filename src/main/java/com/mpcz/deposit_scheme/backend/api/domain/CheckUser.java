package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ValueGenerationType;

@Entity
@Table(name="CHECK_USER")
public class CheckUser {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "CHECK_USER_SEQ")
	@SequenceGenerator(name = "CHECK_USER_SEQ",sequenceName = "CHECK_USER_SEQ",allocationSize = 1)
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_ID")
	private String userId;
	
	
	@Column(name="CHECK_USER")
	private String checkUser;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getCheckUser() {
		return checkUser;
	}


	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	
	

}
