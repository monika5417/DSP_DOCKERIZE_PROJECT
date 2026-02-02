package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.mpcz.deposit_scheme.backend.api.validation.AccessLevelFieldValue;
import com.mpcz.deposit_scheme.backend.api.validation.NotNullIfAnotherFieldHasValue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//@NotNullIfAnotherFieldHasValue.List({
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.DISCOM_LEVEL, dependFieldName = "regionId", message = "please select valid discom"),
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.REGION_LEVEL, dependFieldName = "circleId", message = "please select valid region"),
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.CIRCLE_LEVEL, dependFieldName = "regionId", message = "please select valid cricle"),
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.DIVISION_LEVEL, dependFieldName = "circleId", message = "please select valid disom"),
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.SUBDIVISION_LEVEL, dependFieldName = "regionId", message = "please select valid subdivision"),
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.DISTRIBUTION_LEVEL, dependFieldName = "circleId", message = "please select valid distribution"),
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.SUBSTATION_LEVEL, dependFieldName = "circleId", message = "please select valid substation"),
//	@NotNullIfAnotherFieldHasValue(fieldName = "accessLevel", fieldValue = AccessLevelFieldValue.FEEDER_LEVEL, dependFieldName = "regionId", message = "please select valid feeder") })
@ApiModel(description = "Signup Model Form . ")
public @Data class UserSignUpFormOld implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "userName cannot be null", required = true)
	@NotEmpty(message = "Please enter a name")
	private String userName;
	
	@ApiModelProperty(notes = "userPhoneNumber cannot be null", required = true)
	@NotEmpty(message = "Please enter a phoneNumber")
	private String userLoginId;
	
	@ApiModelProperty(notes = "Enter valid Mobile No", required = true)
	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String userMobileNo;
	
	@ApiModelProperty(notes = "Enter valid Email Id", required = true)
	@NotEmpty
	@Email(message = "Please enter valid Email Id")
	private String userEmailId;
	
	@ApiModelProperty(notes = "Aadhar Number should be must be 12 digits and not null", required = false)
	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{12})")
	private String userAadharNo;
	
	@ApiModelProperty(notes = "Select valid discom", required = true)
	private Long discom;
	
	
	@ApiModelProperty(notes = "Select valid region", required = true)
	private Long region;
	
	@ApiModelProperty(notes = "Select valid Circle", required = true)
	private Long circle;
	
	@ApiModelProperty(notes = "Select valid division", required = true)
	private Long division;
	
	@ApiModelProperty(notes = "Select valid subdivision", required = true)
	private Long subdivision;
	
	@ApiModelProperty(notes = "Select valid distributon Center", required = true)
	private Long distributionCenter;
	
	@ApiModelProperty(notes = "Select valid substation", required = true)
	private Long substation;
	
	@ApiModelProperty(notes = "Select valid feeder", required = true)
	private Long feeder;
	
	@ApiModelProperty(notes = "Select valid designation", required = true)
	private Long designation;
	
	@ApiModelProperty(notes = "Select valid Access Level", required = true)
	private String accessLevel;
	
	private List<Long> userRoles;
}
