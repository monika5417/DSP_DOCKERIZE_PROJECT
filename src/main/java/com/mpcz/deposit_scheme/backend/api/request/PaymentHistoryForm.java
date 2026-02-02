package com.mpcz.deposit_scheme.backend.api.request;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description="payment  history Form . ")
public @Data class PaymentHistoryForm {
	
	@ApiModelProperty(notes="PaymentHistory invoiceNo must be string and not null",required=true)
	@NotEmpty
	private String invoiceNo;

	@ApiModelProperty(notes="PaymentHistory applicationNo must be string and not null",required=true)
	@NotEmpty
	private String applicationNo;

	@ApiModelProperty(notes="PaymentHistory transactionNo must be string and not null",required=true)
	@NotEmpty
	private String transactionNo;

	@ApiModelProperty(notes="PaymentHistory paymentType must be string and not null",required=true)
	@NotEmpty
	private String requestStatus;
	@ApiModelProperty(notes="Circle Name must be string and not null",required=true)
	@NotEmpty
	private String paymentType;
	@ApiModelProperty(notes="PaymentHistory bankName must be string and not null",required=true)
	@NotEmpty
	private String bankName;

	@ApiModelProperty(notes="PaymentHistory consumerName must be string and not null",required=true)
	@NotEmpty
	private String consumerName;
	@ApiModelProperty(notes="PaymentHistory consumerMobNo must be string and not null",required=true)
	@NotEmpty
	private String consumerMobNo;
	@ApiModelProperty(notes="PaymentHistory amount must be string and not null",required=true)
	@NotEmpty
	private String amount;
	@ApiModelProperty(notes="PaymentHistory reference must be string and not null",required=true)
	@NotEmpty
	private String reference;
	@ApiModelProperty(notes="PaymentHistory remark must be string and not null",required=true)
	@NotEmpty
	private String remark;
	@ApiModelProperty(notes="PaymentHistory protal must be string and not null",required=true)
	@NotEmpty
	private String protal;

}
