package com.mpcz.deposit_scheme.backend.api.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * Used to manage Login History of user.
 * @author Aditya Vyas
 * @version 1.0
 * 
 */
@EqualsAndHashCode(callSuper=false)
@Data 
@Entity(name = "UserLoginHistory")
@Table(name = "user_login_history")
public class UserLoginHistory extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "user_login_history_seq", sequenceName = "user_login_history_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_login_history_seq")
	@Column(name = "login_history_id")
	private int id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "login_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTime;

	@Column(name = "logout_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date logoutTime;

	@Column(name = "browser_name")
	private String browserName;

	@Column(name = "os_name")
	private String osName;

	@Column(name = "ip_address")
	private String ipAddress;

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

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
