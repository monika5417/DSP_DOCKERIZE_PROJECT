package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "APP_UPDATION_REMARK")
public class AppUpdationRemark extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APP_UPDATION_REMARK_SEQ", sequenceName = "APP_UPDATION_REMARK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_UPDATION_REMARK_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;

	@Column(name = "CONSUMER_APP_NO")
	private String consumerAppNo;

	@Column(name = "JE_REMARK")
	private String jeRemark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsumerAppNo() {
		return consumerAppNo;
	}

	public void setConsumerAppNo(String consumerAppNo) {
		this.consumerAppNo = consumerAppNo;
	}

	public String getJeRemark() {
		return jeRemark;
	}

	public void setJeRemark(String jeRemark) {
		this.jeRemark = jeRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
