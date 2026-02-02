package com.mpcz.deposit_scheme.backend.api.domain;


import java.math.BigDecimal;
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


@Data
@Entity
@Table(name = "PAYU_PAYMENT_REQUEST")
@SequenceGenerator(name="PAYU_PAYMENT_REQUEST_SEQ", sequenceName="PAYU_PAYMENT_REQUEST_SEQ" , allocationSize = 1)
public class PayUPaymentRequest extends Auditable<Long>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYU_PAYMENT_REQUEST_SEQ")
	@Column(name = "PAYU_PAYMENT_REQUEST_ID")
	private Long payuPaymentRequestId;

	@Column(name = "TXN_ID")
	private String txnId;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "PRDUCT_INFO")
	private String prductInfo;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "UDF1")
	private String udf1;

	@Column(name = "UDF2")
	private String udf2;

	@Column(name = "UDF3")
	private String udf3;
	
	@Column(name = "UDF4")
	private String udf4;
	
	@Column(name = "UDF5")
	private String udf5;
	
	@Column(name = "UDF6")
	private String udf6;
	
	@Column(name = "UDF7")
	private String udf7;
	
	@Column(name = "UDF8")
	private String udf8;
	
	@Column(name = "SURL")
	private String surl;

	@Column(name = "FURL")
	private String furl;

	@Column(name = "HASH")
	private String hash;

	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;

	@Column(name = "PAYMENT_STATUS_CODE")
	private String paymentStatusCode;
	
	@Column(name = "MARCHANT_ID")
	private String merchantId;
	
	@Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@Column(name = "SECURITY_ID")
	private String securityId;




	

}
