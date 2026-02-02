package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity(name = "ApplicationHeadCharges")
@Table(name = "APPLICATION_HEAD_CHARGES")
@ToString
public @Data class ApplicationHeadCharges {

	@Id
	@SequenceGenerator(name = "APPLICATION_HEAD_CHARGES_SEQ", sequenceName = "APPLICATION_HEAD_CHARGES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_HEAD_CHARGES_SEQ")
	@Column(name = "APPLICATION_HEAD_CHARGES_ID")
	private Long ApplicationHeadChargesId;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumerApplicationDetails;

	@ManyToOne
	@JoinColumn(name = "CHARGE_TYPE_ID", referencedColumnName = "CHARGE_TYPE_ID")
	private ChargesType chargesType;

	@Column(name = "CHARGE_RATE")
	private BigDecimal chargeRate;

	@Column(name = "CHARGE_AMOUNT")
	private BigDecimal chargeAmount;

	@ManyToOne
	@JoinColumn(name = "PAYMENT_TYPE_ID", referencedColumnName = "PAYMENT_TYPE_ID")
	private PaymentType paymentType;

	public Long getApplicationHeadChargesId() {
		return ApplicationHeadChargesId;
	}

	public void setApplicationHeadChargesId(Long applicationHeadChargesId) {
		ApplicationHeadChargesId = applicationHeadChargesId;
	}

	public ConsumerApplicationDetail getConsumerApplicationDetails() {
		return consumerApplicationDetails;
	}

	public void setConsumerApplicationDetails(ConsumerApplicationDetail consumerApplicationDetails) {
		this.consumerApplicationDetails = consumerApplicationDetails;
	}

	public ChargesType getChargesType() {
		return chargesType;
	}

	public void setChargesType(ChargesType chargesType) {
		this.chargesType = chargesType;
	}

	public BigDecimal getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(BigDecimal chargeRate) {
		this.chargeRate = chargeRate;
	}

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	
	

}
