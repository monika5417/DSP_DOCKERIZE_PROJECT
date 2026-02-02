package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name="FEE_CHARGES")
@Table(name="FEE_CHARGES")
public @Data class FeeCharges {

	@Id
	@SequenceGenerator(name = "FEE_CHARGES_SEQ",sequenceName = "FEE_CHARGES_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "FEE_CHARGES_SEQ")
	@Column(name="FEE_CHARGES_ID")
	private Long fee_charges_id;
	
	@OneToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumerApplicationDetails;
	
	@Column(name = "CHARGE_TYPE_ID")
	private ChargesType chargesType;
	
	@Column(name = "CHARGE_RATE")
	private Long chargeRate;
	
	@Column(name = "CHARGE_AMOUNT")
	private Double chargeAmount;
	
	@OneToOne
	@JoinColumn(name = "PAYMENT_TYPE_ID", referencedColumnName = "PAYMENT_TYPE_ID")
	private PaymentType paymentType;
	
	
	
}
