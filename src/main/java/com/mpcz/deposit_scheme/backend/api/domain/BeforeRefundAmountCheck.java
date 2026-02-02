package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name="BEFORE_REF_AMNT_CHECK")
@Entity
public class BeforeRefundAmountCheck extends Auditable<Long> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BEFORE_REF_AMNT_CHECK_SEQ", sequenceName = "BEFORE_REF_AMNT_CHECK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BEFORE_REF_AMNT_CHECK_SEQ")
	@Column(name="ID")
	private Long id;
	
	@Column(name="APPLICATION_NO")
	private String applicationNo;
	
	@Column(name="REFUNDABLE_AMOUNT")
	private BigDecimal refundableAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public BigDecimal getRefundableAmount() {
		return refundableAmount;
	}

	public void setRefundableAmount(BigDecimal refundableAmount) {
		this.refundableAmount = refundableAmount;
	}
	
	
	

}
