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

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CONSUMER_LOGIN_TOKEN")
public class ConsumerLoginToken extends Auditable<Long> {

	@Id
	@SequenceGenerator(name = "CONSUMER_LOGIN_TOKEN_SEQ", sequenceName = "CONSUMER_LOGIN_TOKEN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_LOGIN_TOKEN_SEQ")
	@Column(name = "CONSUMER_LOGIN_TOKEN_ID")
	private Long consumerLoginTokenId;

	@Column(name = "CONSUMER_TOKEN_TEXT")
	private String consumerTokenText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CONSUMER_TOKEN_EXPIRED_TIME")
	private Date consumerTokenExpiredTime;

	public Long getConsumerLoginTokenId() {
		return consumerLoginTokenId;
	}

	public void setConsumerLoginTokenId(Long consumerLoginTokenId) {
		this.consumerLoginTokenId = consumerLoginTokenId;
	}

	public String getConsumerTokenText() {
		return consumerTokenText;
	}

	public void setConsumerTokenText(String consumerTokenText) {
		this.consumerTokenText = consumerTokenText;
	}

	public Date getConsumerTokenExpiredTime() {
		return consumerTokenExpiredTime;
	}

	public void setConsumerTokenExpiredTime(Date consumerTokenExpiredTime) {
		this.consumerTokenExpiredTime = consumerTokenExpiredTime;
	}
	
	

}
