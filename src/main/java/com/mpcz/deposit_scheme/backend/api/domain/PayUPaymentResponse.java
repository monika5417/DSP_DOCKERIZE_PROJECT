package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity
@Table(name = "PAYU_PAYMENT_RESPONSE")
@SequenceGenerator(name = "PAYU_PAYMENT_RESPONSE_SEQ", sequenceName = "PAYU_PAYMENT_RESPONSE_SEQ", allocationSize = 1)
public class PayUPaymentResponse extends Auditable<Long>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYU_PAYMENT_RESPONSE_SEQ")
	@Column(name = "PAYU_PAYMENT_RESPONSE_ID")
	private Long payuPaymentResponseId;

	@Column(name = "MIHPAYID")
	private String mihpayid;

	
	//@SerializedName(value = "mode", alternate = "paymentMode")
	
	@Column(name = "PAYMENT_MODE")
	private String mode;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "UNMAPPED_STATUS")
	private String unmappedStatus;

	@Column(name = "KEY")
	private String key;

	@Column(name = "TXNID")
	private String txnid;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "CARDCATEGORY")
	private String cardcategory;

	@Column(name = "DISCOUNT")
	private String discount;

	@Column(name = "NET_AMOUNT_DEBIT")
	private String netAmountDebit;

	@Column(name = "ADDEDON")
	private String addedon;

	@Column(name = "PRODUCTINFO")
	private String productinfo;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

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

	@Column(name = "HASH")
	private String hash;

	@Column(name = "FIELD1")
	private String field1;

	@Column(name = "FIELD2")
	private String field2;

	@Column(name = "FIELD3")
	private String field3;

	@Column(name = "FIELD4")
	private String field4;

	@Column(name = "FIELD5")
	private String field5;

	@Column(name = "FIELD6")
	private String field6;

	@Column(name = "FIELD7")
	private String field7;

	@Column(name = "FIELD8")
	private String field8;

	@Column(name = "FIELD9")
	private String field9;

	@Column(name = "PAYMENT_SOURCE")
	private String paymentSource;

	@Column(name = "PG_TYPE")
	private String pgType;

	@Column(name = "BANK_REF_NUM")
	private String bankRefNum;

	@Column(name = "BANKCODE")
	private String bankcode;

	@Column(name = "ERROR")
	private String error;

	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	@Column(name = "CARDNUM")
	private String cardnum;

	@Column(name = "CARDHASH")
	private String cardhash;
	
	/////////////////////////////////////////////////////

	@Column(name = "CREATED_BY1")
	private String createdBy1;

//	@Column(name = "UPDATED_BY1")
//	private String updatedBy1;

	@Column(name = "IS_ACTIVE1")
	private Boolean isActive1;

	
//	@Column(name="CONSUMER_ID") 
//	private Long consumerId;
}
