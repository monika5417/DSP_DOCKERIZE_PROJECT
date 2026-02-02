package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString(exclude = { "consumer" })
@Data
@Entity(name = "ConsumerLoginAttempts")
@Table(name = "CONSUMER_LOGIN_ATTEMPTS")
public class ConsumerLoginAttempts extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_LOGIN_ATTEMPTS_SEQ")
	@SequenceGenerator(name = "CONSUMER_LOGIN_ATTEMPTS_SEQ", sequenceName = "CONSUMER_LOGIN_ATTEMPTS_SEQ", allocationSize = 1)
	@Column(name = "CONSUMER_LOGIN_ATTEMPTS_ID")
	private Long consumerLoginAttemptsId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID", nullable = false)
	private Consumer consumer;

	@Column(name = "ATTEMPTS")
	private Long attempts;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_ATTEMPT_TIME")
	private Date lastAttemptTime;

	public Long getConsumerLoginAttemptsId() {
		return consumerLoginAttemptsId;
	}

	public void setConsumerLoginAttemptsId(Long consumerLoginAttemptsId) {
		this.consumerLoginAttemptsId = consumerLoginAttemptsId;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public Long getAttempts() {
		return attempts;
	}

	public void setAttempts(Long attempts) {
		this.attempts = attempts;
	}

	public Date getLastAttemptTime() {
		return lastAttemptTime;
	}

	public void setLastAttemptTime(Date lastAttemptTime) {
		this.lastAttemptTime = lastAttemptTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
