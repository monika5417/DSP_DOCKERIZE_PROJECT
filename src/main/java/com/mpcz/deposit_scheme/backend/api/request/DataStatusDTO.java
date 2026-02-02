package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Request UserStatusDTO Form
 * 
 * @author Aditya Vyas
 * @version 1.0
 */
@ApiModel(description = "User Status Model Form . ")
public @Data class DataStatusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;

	private String userId;

	private String mobile;

	private String aadharNumber;

	private String dataName;

	private String dataCode;

	private String idProofNumber;

	@ApiModelProperty(notes = "master name is required", required = true)
	@NotEmpty
	private String master;

	private Long recordId;

//	@Positive
	private Long crudType;

	private String consumerId;

//	sandeep, starts
	private String consumerMobileNo;

	private String consumerEmailId;

	private String aadharNo;

	private String panNo;
//	sandeep, ends

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getIdProofNumber() {
		return idProofNumber;
	}

	public void setIdProofNumber(String idProofNumber) {
		this.idProofNumber = idProofNumber;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getCrudType() {
		return crudType;
	}

	public void setCrudType(Long crudType) {
		this.crudType = crudType;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}

	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}

	public String getConsumerEmailId() {
		return consumerEmailId;
	}

	public void setConsumerEmailId(String consumerEmailId) {
		this.consumerEmailId = consumerEmailId;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
