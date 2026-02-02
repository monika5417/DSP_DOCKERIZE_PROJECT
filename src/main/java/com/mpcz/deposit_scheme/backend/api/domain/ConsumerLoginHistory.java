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

@EqualsAndHashCode(callSuper = false)
@Data
@Entity(name = "ConsumerLoginHistory")
@Table(name = "CONSUMER_LOGIN_HISTORY") 
public class ConsumerLoginHistory extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONSUMER_LOGIN_HISTORY_SEQ", sequenceName = "CONSUMER_LOGIN_HISTORY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_LOGIN_HISTORY_SEQ")
	@Column(name = "CONSUMER_LOGIN_HISTORY_ID")
	private Long consumerLoginHistoryId;

	@Column(name = "CONSUMER_ID")
	private String consumerId;

	@Column(name = "CONSUMER_LOGIN_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date consumerLoginTime;

	@Column(name = "CONSUMER_LOGOUT_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date consumerLogoutTime;

	@Column(name = "BROWSER_NAME")
	private String browserName;

	@Column(name = "OS_NAME")
	private String osName;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	public Long getConsumerLoginHistoryId() {
		return consumerLoginHistoryId;
	}

	public void setConsumerLoginHistoryId(Long consumerLoginHistoryId) {
		this.consumerLoginHistoryId = consumerLoginHistoryId;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public Date getConsumerLoginTime() {
		return consumerLoginTime;
	}

	public void setConsumerLoginTime(Date consumerLoginTime) {
		this.consumerLoginTime = consumerLoginTime;
	}

	public Date getConsumerLogoutTime() {
		return consumerLogoutTime;
	}

	public void setConsumerLogoutTime(Date consumerLogoutTime) {
		this.consumerLogoutTime = consumerLogoutTime;
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
