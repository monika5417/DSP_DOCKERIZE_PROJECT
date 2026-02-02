package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "ConsumerType")
@Table(name = "CONSUMER_TYPE")
public @Data class ConsumerType extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONSUMER_TYPE_SEQ", sequenceName = "CONSUMER_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_TYPE_SEQ")
	@Column(name = "CONSUMER_TYPE_ID", columnDefinition = "serial", updatable = false)
	private Long consumerTypeId;

	@Column(name = "CONSUMER_TYPE_NAME")
	private String consumerTypeName;

	public Long getConsumerTypeId() {
		return consumerTypeId;
	}

	public void setConsumerTypeId(Long consumerTypeId) {
		this.consumerTypeId = consumerTypeId;
	}

	public String getConsumerTypeName() {
		return consumerTypeName;
	}

	public void setConsumerTypeName(String consumerTypeName) {
		this.consumerTypeName = consumerTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}