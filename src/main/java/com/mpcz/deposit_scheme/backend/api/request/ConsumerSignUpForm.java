package com.mpcz.deposit_scheme.backend.api.request;

import java.io.IOException;
import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Consumer Signup Model Form .")
public @Data class ConsumerSignUpForm implements Serializable {

	private static final long serialVersionUID = 1L;


//	@ApiModelProperty(notes = "consumerId cannot be null", required = true)
//	@NotEmpty(message = "Please enter Consumer Id")
	private String consumerId;



	@ApiModelProperty(notes = "consumerName cannot be null", required = true)
	@NotBlank(message = "Please enter Consumer Name")
	@Pattern(
	    regexp = "^(?!.*\\b(S/O|D/O|W/O|C/O)\\b)[A-Za-z]+( [A-Za-z]+){0,}$",
	    message = "Consumer Name must contain only English alphabets, minimum 0 spaces (1 words), and must not contain relations or special characters"
	)
	private String consumerName;

	@ApiModelProperty(notes = "Enter valid Email Id", required = true)
	@NotEmpty
	@Email(message = "Please enter valid Email Id")
	private String consumerEmailId;

	@ApiModelProperty(notes = "Enter valid Mobile No", required = true)
	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String consumerMobileNo;
	

	@ApiModelProperty(notes = "Password cannot be null", required = true)
	@NotEmpty
	private String consumerPassword;

	
	private String ivrsNo;
	
	
	
	

//	@NotNull
//	@ApiModelProperty(notes = "Select valid Consumer Type", required = true)
//	private Long consumerTypeId;

//	@ApiModelProperty(notes = "aadharNo cannot be null", required = true)
//	@NotEmpty(message = "Please enter aadharNo")
//	private String aadharNo;
//
//	@ApiModelProperty(notes = "panNo cannot be null", required = true)
//	@NotEmpty(message = "Please enter panNo")
//	private String panNo;

	@ApiModelProperty(notes = "address cannot be null", required = true)
	@NotEmpty(message = "Please enter Address")
	private String address;
	
	
	

	public String getConsumerPassword() {
		return consumerPassword;
	}




	public void setConsumerPassword(String consumerPassword) {
		this.consumerPassword = consumerPassword;
	}




	public String getConsumerId() {
		return consumerId;
	}




	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}




	public String getConsumerName() {
		return consumerName;
	}




	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}




	public String getConsumerEmailId() {
		return consumerEmailId;
	}




	public void setConsumerEmailId(String consumerEmailId) {
		this.consumerEmailId = consumerEmailId;
	}




	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}




	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}




	public String getIvrsNo() {
		return ivrsNo;
	}




	public void setIvrsNo(String ivrsNo) {
		this.ivrsNo = ivrsNo;
	}




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public static ConsumerSignUpForm stringToJson(String consumerSignUpForm) {

		ConsumerSignUpForm consumerSignUpJson = new ConsumerSignUpForm();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			consumerSignUpJson = objectMapper.readValue(consumerSignUpForm, ConsumerSignUpForm.class);
		} catch (IOException err) {
			System.out.printf("Error", err.toString());
		}

		return consumerSignUpJson;

	}
}
