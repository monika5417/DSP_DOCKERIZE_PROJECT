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
@Entity
@Table(name = "CONSUMER_HISTORY")
public @Data class ConsumerHistory extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONSUMER_HISTORY_SEQ", sequenceName = "CONSUMER_HISTORY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_HISTORY_SEQ")
	@Column(name = "CONSUMER_HISTORY_ID")
	private Long consumerHistoryId;

	@Column(name = "CONSUMER_LOGIN_ID")
	private String consumerLoginId;

	@Column(name = "PROPERTY_NAME")
	private String propertyName;

	@Column(name = "PROPERTY_VALUE")
	private String propertyValue;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public Long getConsumerHistoryId() {
		return consumerHistoryId;
	}

	public void setConsumerHistoryId(Long consumerHistoryId) {
		this.consumerHistoryId = consumerHistoryId;
	}

	public String getConsumerLoginId() {
		return consumerLoginId;
	}

	public void setConsumerLoginId(String consumerLoginId) {
		this.consumerLoginId = consumerLoginId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	



}
