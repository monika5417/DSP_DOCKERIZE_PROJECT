package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the PAYMENT_TYPE database table.
 * 
 */
@Entity(name = "PaymentType")
@Table(name = "PAYMENT_TYPE")
@NoArgsConstructor
@Data
public class PaymentType extends Auditable<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PAYMENT_TYPE_SEQ", sequenceName = "PAYMENT_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PAYMENT_TYPE_SEQ")
	@Column(name = "PAYMENT_TYPE_ID")
	private Long paymentTypeId;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "STATUS")
	private Long status;

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}