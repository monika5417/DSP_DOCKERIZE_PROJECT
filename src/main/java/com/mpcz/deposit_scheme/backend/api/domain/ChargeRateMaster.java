package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity(name = "CHARGE_RATE_MASTER")
@Table(name = "CHARGE_RATE_MASTER")
public @Data class ChargeRateMaster extends Auditable<Long> {

	@Id
	@SequenceGenerator(name = "CHARGE_RATE_MASTER_SEQ", sequenceName = "CHARGE_RATE_MASTER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHARGE_RATE_MASTER_SEQ")
	@Column(name = "CHARGE_RATE_MASTER_ID")
	private Long chargeRateMasterId;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chargeTypeId", cascade = CascadeType.ALL)
	@OneToOne
	@JoinColumn(name = "CHARGE_TYPE_ID", referencedColumnName = "CHARGE_TYPE_ID")
	private ChargesType chargesType;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "CHARGE_PERCENTAGE")
	private BigDecimal chargePercentage;

	@Column(name = "REMARK")
	private String remark;

	public Long getChargeRateMasterId() {
		return chargeRateMasterId;
	}

	public void setChargeRateMasterId(Long chargeRateMasterId) {
		this.chargeRateMasterId = chargeRateMasterId;
	}

	public ChargesType getChargesType() {
		return chargesType;
	}

	public void setChargesType(ChargesType chargesType) {
		this.chargesType = chargesType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getChargePercentage() {
		return chargePercentage;
	}

	public void setChargePercentage(BigDecimal chargePercentage) {
		this.chargePercentage = chargePercentage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
