package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CONTRACTOR_REMARK")
public class ContractorRemark extends Auditable<Long>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "CONTRACTOR_REMARK_SEQ", sequenceName = "CONTRACTOR_REMARK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_REMARK_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
	@Column(name="CONSUMER_APP_NO")
	private String consumerAppNo;
	
	@Column(name="CON_REMARK")
	private String conRemark;
	
	@Column(name="CON_ID")
	private Long conId;
	
	@Column(name="CON_NAME")
	private String conName;
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public String getConRemark() {
		return conRemark;
	}

	public void setConRemark(String conRemark) {
		this.conRemark = conRemark;
	}

	public Long getConId() {
		return conId;
	}

	public void setConId(Long conId) {
		this.conId = conId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}
	
	

}
