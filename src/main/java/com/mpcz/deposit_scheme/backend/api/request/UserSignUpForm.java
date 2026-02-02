package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

//import com.mpcz.htngb.validation.AccessLevelFieldValue;
//import com.mpcz.htngb.validation.NotNullIfAnotherFieldHasValue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Request CircleForm Form
 * 
 * @author Amit
 * @version 1.0
 */
/*
 * JSR-303 Validation and Custom Messages for access level for discom, region &
 * circle.
 */
//@NotNullIfAnotherFieldHasValue.List({
//		@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.CIRCLE_LEVEL, dependFieldName = "regionId", message = "please select valid region"),
//		@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.CIRCLE_LEVEL, dependFieldName = "circleId", message = "please select valid circle"),
//		@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.REGION_LEVEL, dependFieldName = "regionId", message = "please select valid region") })
@ApiModel(description = "Signup Model Form . ")
public @Data class UserSignUpForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "userName cannot be null", required = true)
	@NotEmpty(message = "Please enter a name")
	private String userName;

	@ApiModelProperty(notes = "userId cannot be null and UserId should be between 6 to 8 digits", required = true)
	@NotEmpty(message = "Please enter a UserId")
	@Pattern(regexp = "(^$|[0-9]){6,10}")
	private String userId;

//	@ApiModelProperty(notes = "Enter valid email id", required = true)
//	@NotEmpty
//	@Email(message = "Please enter valid email id")
	private String userEmailId;

	@ApiModelProperty(notes = "Enter valid mobile no", required = true)
	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String mobileNo;

	@ApiModelProperty(notes = "Select valid Access Level", required = true)
	private String accessLevel;

	@ApiModelProperty(notes = "Select valid Designation", required = true)
	private Long designationId;

//	sandeep, starts

	@ApiModelProperty(notes = "Select valid Discom", required = true)
	private Long discomId;

	@ApiModelProperty(notes = "Select valid Region", required = true)
	private Long regionId;

	@ApiModelProperty(notes = "Select valid Circle", required = true)
	private Long circleId;

	@ApiModelProperty(notes = "Select valid division", required = true)
	private Long divisionId;

	@ApiModelProperty(notes = "Select valid Sub Division", required = true)
	private Long subDivisionId;

	@ApiModelProperty(notes = "Select valid Dc", required = true)
	private Long dcId;

//	sandeep, ends

	@ApiModelProperty(notes = "Aadhar Number should be must be 12 digits and not null", required = true)
	@NotEmpty
//	@Pattern(regexp = "(^$|[0-9]{12})")
//	private String aadharNo;

	private List<Long> userRoles;
	
	@ApiModelProperty(notes = "Select valid feederId", required = true)
	private Long feederId;

	@ApiModelProperty(notes = "Select valid substationId", required = true)
	private Long substationId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public Long getDiscomId() {
		return discomId;
	}

	public void setDiscomId(Long discomId) {
		this.discomId = discomId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getCircleId() {
		return circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public Long getSubDivisionId() {
		return subDivisionId;
	}

	public void setSubDivisionId(Long subDivisionId) {
		this.subDivisionId = subDivisionId;
	}

	public Long getDcId() {
		return dcId;
	}

	public void setDcId(Long dcId) {
		this.dcId = dcId;
	}

	public List<Long> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Long> userRoles) {
		this.userRoles = userRoles;
	}

	public Long getFeederId() {
		return feederId;
	}

	public void setFeederId(Long feederId) {
		this.feederId = feederId;
	}

	public Long getSubstationId() {
		return substationId;
	}

	public void setSubstationId(Long substationId) {
		this.substationId = substationId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
