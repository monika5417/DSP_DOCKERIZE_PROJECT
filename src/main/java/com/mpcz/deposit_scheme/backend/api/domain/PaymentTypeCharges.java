package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.List;

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

import lombok.Data;

@Entity(name="PAYMENT_TYPE_CHARGES")
@Table(name="PAYMENT_TYPE_CHARGES")
public @Data class PaymentTypeCharges {
	
	@Id
	@SequenceGenerator(name = "PAYMENT_TYPE_CHARGES_SEQ",sequenceName = "PAYMENT_TYPE_CHARGES_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PAYMENT_TYPE_CHARGES_SEQ")
	@Column(name ="PAYMENT_TYPE_CHARGES_ID")
	private Long paymentTypeChargesId;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "PAYMENT_TYPE_ID", cascade = CascadeType.ALL)
	@OneToOne
	@JoinColumn(name = "PAYMENT_TYPE_ID",referencedColumnName = "PAYMENT_TYPE_ID")
	private PaymentType paymentType;
	
	@OneToOne
	@JoinColumn(name = "CHARGE_TYPE_ID", referencedColumnName = "CHARGE_TYPE_ID")
	private ChargesType chargesType;

	public Long getPaymentTypeChargesId() {
		return paymentTypeChargesId;
	}

	public void setPaymentTypeChargesId(Long paymentTypeChargesId) {
		this.paymentTypeChargesId = paymentTypeChargesId;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public ChargesType getChargesType() {
		return chargesType;
	}

	public void setChargesType(ChargesType chargesType) {
		this.chargesType = chargesType;
	}
	
	
	
	

}
